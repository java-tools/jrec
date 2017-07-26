package net.sf.RecordEditor.layoutWizard;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.BasicFileSchema;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.re.util.csv.CharsetDetails;
import net.sf.RecordEditor.re.util.csv.CheckEncoding;
import net.sf.RecordEditor.utils.common.Common;


public class FileAnalyser {

    private static final int LAST_7_BITS_SET = 127;
    private static final boolean[] ASCII_CHAR = init(new boolean[128], false);
    private static final String TEXT_CHARS = Common.STANDARD_CHARS0 + "\n\t ;:?{}<>";
    private static final boolean[] EBCDIC_CHAR = getTextChars("cp037");
    private static final byte[] ASCII_XML_TAG = "<>".getBytes();
    private static final byte[] EBCDIC_XML_TAG = Conversion.getBytes("<>", "cp037");

    static {
    	for (int i = 0; i < TEXT_CHARS.length(); i++) {
    		ASCII_CHAR[TEXT_CHARS.charAt(i)] = true;
    	}
    }


	@SuppressWarnings("deprecation")
	private checkFile[] fileChecks = {
     		new VbDumpCheck(),
    		new StandardCheckFile(Constants.IO_VB, "cp037"),
    		new StandardCheckFile(Constants.IO_VB_FUJITSU),
    		new StandardCheckFile(Constants.IO_VB_GNU_COBOL),
    		new BinTextCheck(),
      		new StandardCheckFile(Constants.IO_VBS, "cp037"),
    		new BinTextCheck(EBCDIC_CHAR, "CP037", 7),
//    		new StandardCheckFile(Constants.IO_MICROFOCUS),
//    		new StandardCheckFile(Constants.IO_UNICODE_TEXT),
    };

    private boolean ebcdicFile;
    private static int maxRun;

    private int textPct=0;
    private int recordLength=100;
    private int fileStructure=Constants.NULL_INTEGER;
    private String fontName="";
    private CharsetDetails charsetDetails;
    private int xmlTagStartCount=0, xmlTagEndCount=0;
    

    private byte[] fData;

    private int linesRead = 0;

    public static FileAnalyser getAnaylserNoLengthCheck(byte[] fileData, String lengthStr) {
    	return new FileAnalyser(fileData, lengthStr, true, false);
    }

    public static FileAnalyser getAnaylser(byte[] fileData, String lengthStr) {
    	return new FileAnalyser(fileData, lengthStr, true, true);
    }

    public static FileAnalyser getFixedAnalyser(byte[] fileData, String lengthStr) {
    	return new FileAnalyser(fileData, lengthStr, false, true);
    }



    /**
     * Analyze file and try and determine its File-Structure (.e. Text, VB, Fixed-Width etc)
     * 
     * @param fileData sample file-data
     * @param lengthStr length (in string format)
     * @param searchFileStructures wether to search the file structures
     * @param searchFixedWidthLength search for fixed length file
     */
    private FileAnalyser(byte[] fileData, String lengthStr, boolean searchFileStructures, boolean searchFixedWidthLength) {
		super();

		fData = fileData;

		int ebcdicCount, asciiCount, ebcdicRun, asciiRun, textPerc= 0;
    	ebcdicCount = countTextChars(fileData, EBCDIC_CHAR);
    	ebcdicRun = maxRun;
    	asciiCount  = countTextChars(fileData, ASCII_CHAR);
    	asciiRun = maxRun;
    	if (fileData.length > 0) {
    		textPerc = 100 * Math.max(ebcdicCount, asciiCount) / fileData.length;
    	}
    	textPct = textPerc;
    	charsetDetails = CheckEncoding.determineCharSet(fileData, true);

    	ebcdicFile = (ebcdicCount - asciiCount) * 20 / fileData.length > 3
    			|| (ebcdicCount > asciiCount && ebcdicRun > 2 && ebcdicRun > asciiRun);
    	if (ebcdicFile) {
    		check4XmlTags(EBCDIC_XML_TAG);
    		fontName = "cp037";
    	} else {
    		check4XmlTags(ASCII_XML_TAG);
    		fontName = charsetDetails.charset;
    	}

    	if (searchFileStructures) {
	    	for (int i = 0; i < fileChecks.length; i++) {
				if (fileChecks[i].check(fileData)) {
					fileStructure = fileChecks[i].getFileStructure();
					fontName = fileChecks[i].getFontName();
					linesRead = fileChecks[i].getLinesRead();
					return;
				}
			}
    	}


//    	System.out.println("Ebcidic Counts " + ebcdicCount + " " + asciiCount
//    			+ " -- " + ebcdicRun + " " + asciiRun);
    	fileStructure = Constants.IO_FIXED_LENGTH;

    	if (searchFixedWidthLength) {
    		recordLength = checkLengthString(lengthStr);
    		if (recordLength <= 0) {
    			findRecordLength(fileData, lengthStr);
    		}
    	}
	}

    private void check4XmlTags(byte[] tags) {
    	for (int i = 0; i < fData.length; i++) {
    		if (fData[i] == tags[0]) {
    			xmlTagStartCount += 1;
    		} else if (fData[i] == tags[1]) {
    			xmlTagEndCount += 1;
    		}
    	}
    }
    
    
    /**
     * Get the Record-Length from the Record-Length-String
     * 
     * @param lengthStr possibly the Record-Length in String format.
     * 
     * @return RecordLength
     */
    private int checkLengthString(String lengthStr) {
    	int recLength = Constants.NULL_INTEGER;

    	if (lengthStr != null && lengthStr.length() > 0) {
    		try {
    			recLength = Integer.parseInt(lengthStr);
    		} catch (Exception e) {
    		}
    	}
    	return recLength;
    }
    /**
     * Try and work out the Record-Length for a file with
     * fixed-Length Records. It does this by Checking which
     * Record-Length has the most fields.
     * 
     * @param fileData Data from the file
     * @param lengthStr Record-Length (as a String)
     */
   private void findRecordLength(byte[] fileData, String lengthStr) {

	   boolean lookMainframeZoned = ebcdicFile,
			   lookPcZoned=false, lookComp3=ebcdicFile, lookCompBigEndian=true, lookCompLittleEndian=false;
	   FieldSearch fieldSearch;

	   Details details = new Details();
	   RecordDefinition recordDefinition = details.standardRecord;
	   int fieldCount, relCount = 0, maxCount=0, len = Common.NULL_INTEGER;

	   details.fileStructure = Constants.IO_FIXED_LENGTH;
	   details.fontName = fontName;


	   //lookMainframeZoned = false;
	   //lookComp3=false;

	   System.out.println("File Length: " + fileData.length);
	   fieldSearch = new FieldSearch(details, recordDefinition);
	   for (int i = 5; i < 200; i++) {
		   details.recordLength = i;

		   recordDefinition.numRecords = 0;
		   for (int j = 0; j < recordDefinition.records.length && (j+1) * i < fileData.length; j++) {
			   recordDefinition.records[j] = new byte[i];
			   System.arraycopy(fileData, j*i, recordDefinition.records[j], 0, i);
			   recordDefinition.numRecords = j+1;
		   }

//			   if (recordDefinition.numRecords+1 < recordDefinition.records.length && recordDefinition.numRecords*i < fileData.length) {
//				   int sz = fileData.length - recordDefinition.numRecords*i;
//				   recordDefinition.records[recordDefinition.numRecords] = new byte[sz];
//				   System.arraycopy(fileData, recordDefinition.numRecords*i, recordDefinition.records[recordDefinition.numRecords], 0, sz);
//				   recordDefinition.numRecords += 1;
//			   }
		   if (recordDefinition.numRecords < 4 || i > 99 && recordDefinition.numRecords < 5) {
			   break;
		   }


		   recordDefinition.columnDtls.clear();
		   fieldSearch.findFields(lookMainframeZoned, lookPcZoned, lookComp3, lookCompBigEndian, lookCompLittleEndian);
		   fieldCount = recordDefinition.columnDtls.size();
		   relCount =  fieldCount * 10000 / i;

		   if (fieldCount > 1 && relCount > maxCount) {
			   maxCount = relCount;
			   len = i;
//				   if (relCount > 2000) {
//					   for (int j = 0; j < recordDefinition.columnDtls.size(); j++) {
//						   System.out.print(",  " + recordDefinition.columnDtls.get(j).type
//								   + " " + recordDefinition.columnDtls.get(j).start);
//					   }
//					   System.out.println("Record Length=" + i + ", Field count " + recordDefinition.columnDtls.size()
//							   + " Relative " + relCount);
//				   }
		   }

		   if (len < 1) {
			   len = 100;
		   }
		   recordLength = len;
		   }
	   
   }


   public ExternalRecord getLayoutDetails(boolean nameFields) {
	   return  getFileDetails(true, nameFields).createRecordLayout();
   }
   
   public Details getFileDetails(boolean useDetailReadlimit, boolean nameFields) {
	   Details details = new Details();
	   RecordDefinition recordDefinition = details.standardRecord;
	   AbstractByteReader r = null;

	   ByteArrayInputStream is = new ByteArrayInputStream(fData);
	   try {
		   details.fileStructure = fileStructure;
		   details.fontName = fontName;
		   details.recordLength = recordLength;

		   byte[] l;
		   int i = 0;
		   r = ByteIOProvider.getInstance()
		   			.getByteReader(BasicFileSchema.newFixedSchema(fileStructure, true, recordLength, fontName));

		   r.setLineLength(recordLength);
		   r.open(is);
		   if (useDetailReadlimit) {
			   while (i < recordDefinition.records.length && (l = r.read()) != null) {
				   recordDefinition.records[i++] = l;
				   recordDefinition.numRecords = i;
			   }
		   } else {
			   ArrayList<byte[]> lines = new ArrayList<byte[]>(600);
			   while ((l = r.read()) != null) {
				   lines.add(l);
			   }
			   recordDefinition.numRecords = lines.size();
			   recordDefinition.records = lines.toArray(recordDefinition.records);
		   }
		   
		   
	   } catch (Exception e) {
		   e.printStackTrace();
	   } finally {
		   if (r != null) {
			   try {
				   r.close();
			   } catch (Exception e) {
			   // TODO: handle exception
			   }
		   }
	   }


	   boolean lookMainframeZoned = ebcdicFile,
			   lookPcZoned=false, lookComp3=ebcdicFile, lookCompBigEndian=true, lookCompLittleEndian=false;


	   (new FieldSearch(details, recordDefinition)).findFields(lookMainframeZoned, lookPcZoned, lookComp3, lookCompBigEndian, lookCompLittleEndian);
	   System.out.println("Column Count: " + recordDefinition.columnDtls.size()
			   + " Number of Records: " + recordDefinition.numRecords);

	   if (nameFields) {
		   for (int i = 0; i < recordDefinition.columnDtls.size(); i++) {
			   recordDefinition.columnDtls.get(i).name = "Field_" + i;
		   }
	   }

	   return details;
   }



    private static boolean[] init(boolean[] b, boolean val) {
    	for (int i = 0; i < b.length; i++) {
    		b[i] = val;
    	}

    	return b;
    }



    public final static int getTextPct(byte[] data, String font) {
    	if (data == null || data.length < 10) {
    		return 100;
    	}
    	return  countTextChars(data, getTextChars(font)) * 100 / data.length;
    }


    public final static int countTextChars(byte[] data, String font) {
    	return countTextChars(data, getTextChars(font));
    }


	private final static int countTextChars(byte[] data, boolean[] check) {
 		int count = 0;
 		int run = 1;
 		int j;
 		boolean last = false;
 		int cc = 0;

 		maxRun = 0;
 		for (int i = 0; i < data.length; i++) {
 			j = Common.toInt(data[i]);
 			if (j >= check.length) {
 				last = false;
 			} else {
     			if (last && check[j]) {
     				count += 1;
     				run += 1;
     				maxRun = Math.max(maxRun, run);
     				if (cc == 0) {
     					count += 1;
     				}

     				cc += 1;
     			}
     			last = check[j];
 			}
 			if (! last) {
 				cc = 0;
 				run = 1;
 			}
 		}

 		return count;
     }



    public int getRecordLength() {
		return recordLength;
	}



	public int getTextPct() {
		return textPct;
	}



	public int getFileStructure() {
		return fileStructure;
	}



	public String getFontName() {
		return fontName;
	}


	/**
	 * @return the linesRead
	 */
	public int getLinesRead() {
		return linesRead;
	}

	/**
	 * @return the charsetDetails
	 */
	public final CharsetDetails getCharsetDetails() {
		return charsetDetails;
	}

	private static boolean[] getTextChars(String font) {
		boolean[] isText = init(new boolean[256], false);
		try {
			byte[] bytes;
			if (font == null || "".equals(font)) {
				bytes= TEXT_CHARS.getBytes();
			} else {
				bytes= TEXT_CHARS.getBytes(font);
			}
			int j;
			for (int i = 0; i < bytes.length; i++) {
				j = Common.toInt(bytes[i]);
				if (j > 32) {
					isText[j] = true;
				}
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return isText;
	}

	/**
	 * @return the xmlTagStartCount
	 */
	public final int getXmlTagStartCount() {
		return xmlTagStartCount;
	}

	/**
	 * @return the xmlTagEndCount
	 */
	public final int getXmlTagEndCount() {
		return xmlTagEndCount;
	}

	/**
	 * Class to test a file against one File-Structure
	 * @author Bruce Martin
	 *
	 */
	private static interface checkFile {
    	public boolean check(byte[] data);
    	public int getFileStructure();
    	public String getFontName();
    	public int getLinesRead();
    }

	/**
	 * Check the standard Byte-IO File Types (VB etc) 
	 * @author bruce
	 *
	 */
    private static class StandardCheckFile implements checkFile {
    	private int structure;
    	private String font = "";
    	protected int linesRead = 0;

       	/**
		 * @param fileStructure
		 */
		public StandardCheckFile(int fileStructure) {
			this.structure = fileStructure;
		}

		public StandardCheckFile(int fileStructure, String fontName) {
			this.structure = fileStructure;
			this.font = fontName;
		}

		public boolean check(byte[] data) {
       		boolean ret = true;
       		try {
       			int len = 0;
       			byte[] bytes;
       			@SuppressWarnings("deprecation")
				AbstractByteReader reader = ByteIOProvider.getInstance().getByteReader(structure);
       			reader.open(new ByteArrayInputStream(data));

       			for (int i = 0;
       					i < 20 && len < data.length && ((bytes = reader.read()) != null);
       					i++) {
       				len += bytes.length;
       				linesRead = i;
       			}
       			reader.close();
       		} catch (Exception e) {
				ret = false;
			}
       		return ret && linesRead > 1;
       	}

    	public int getFileStructure() {
    		return structure;
    	}

    	public String getFontName() {
    		return font;
    	}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.layoutWizard.FileStructureAnalyser.checkFile#getLinesRead()
		 */
		@Override
		public int getLinesRead() {
			return linesRead;
		}
    }

    private static class BinTextCheck extends StandardCheckFile {
    	final boolean[] charMap;
    	final int linesRequired;
    	
    	public BinTextCheck() {
    		this(ASCII_CHAR, "", 3);
    	}
    	public BinTextCheck(final boolean[] chars, String font, int linesRequired) {
    		super(Constants.IO_BIN_TEXT, font);
    		this.charMap = chars;
    		this.linesRequired = linesRequired;
    	}

    	@Override
    	public boolean check(byte[] data) {
    		boolean ret = false;
    		if (data != null) {
	    		int count = countTextChars(data, charMap);

	    		//System.out.println(" }}} " + (count * 100 / data.length));
	    		if ((count * 100) / data.length >= 70) {
	    			ret = super.check(data) && super.linesRead > linesRequired;
	    		}
    		}

    		return ret;
    	}
    }

    private class VbDumpCheck extends StandardCheckFile {

    	public VbDumpCheck() {
    		super(Constants.IO_VB_DUMP, "cp037");
    	}

    	@Override
    	public boolean check(byte[] data) {
    		boolean ret = false;
    		if (data == null || data.length < 8) {
    		} else if (data[6] == 0 && data[7] ==0) {
    			int blockLength;
    			byte[] bdwLength;
       			if (data[0] >= 0) {
       				bdwLength = new byte[2];
    	            bdwLength[0] = data[0];
    	            bdwLength[1] = data[1];
    			} else {
      				bdwLength = new byte[4];
      				bdwLength[0] = (byte) (data[0] & LAST_7_BITS_SET);
       	            bdwLength[1] = data[1];
       	            bdwLength[2] = data[2];
       	            bdwLength[3] = data[3];
    			}
       			blockLength = (new BigInteger(bdwLength)).intValue();
       			if (blockLength + 8 < data.length) {
       				if (data[blockLength + 6] == 0 && data[blockLength + 7] == 0) {
       					ret = super.check(data);
       				}
       			} else {
       				ret = super.check(data);
       			}
    		}
    		return ret;
    	}
    }
}
