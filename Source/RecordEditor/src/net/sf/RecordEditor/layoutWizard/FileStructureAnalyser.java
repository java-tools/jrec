package net.sf.RecordEditor.layoutWizard;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.ByteIOProvider;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.utils.common.Common;


public class FileStructureAnalyser {

    private static final int LAST_7_BITS_SET = 127;
    private static final boolean[] ASCII_CHAR = init(new boolean[128], false);
    private static final boolean[] EBCDIC_CHAR = getTextChars("cp037");
    private static final String TEXT_CHARS = Common.STANDARD_CHARS0 + "\n\t ;:?{}<>";
    
    static {
    	for (int i = 0; i < TEXT_CHARS.length(); i++) {
    		ASCII_CHAR[TEXT_CHARS.charAt(i)] = true;
    	}
    }
    

	private checkFile[] fileChecks = {
     		new VbDumpCheck(),
    		new StandardCheckFile(Constants.IO_VB, "cp037"),
    		new StandardCheckFile(Constants.IO_VB_FUJITSU),
    		new StandardCheckFile(Constants.IO_VB_OPEN_COBOL),
    		new BinTextCheck(),
//    		new StandardCheckFile(Constants.IO_MICROFOCUS),
//    		new StandardCheckFile(Constants.IO_UNICODE_TEXT),
    };
    
    private boolean ebcdicFile;
    private static int maxRun;

    private int textPct=0;
    private int recordLength=100;
    private int fileStructure=Constants.NULL_INTEGER;
    private String fontName="";
    
    private byte[] fData;
    
    public static FileStructureAnalyser getAnaylser(byte[] fileData, String lengthStr) {
    	return new FileStructureAnalyser(fileData, lengthStr, true);
    }
    
    public static FileStructureAnalyser getFixedAnalyser(byte[] fileData, String lengthStr) {
    	return new FileStructureAnalyser(fileData, lengthStr, false);
    }

    private FileStructureAnalyser(byte[] fileData, String lengthStr, boolean searchFileStructures) {
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
    
    	if (searchFileStructures) {
	    	for (int i = 0; i < fileChecks.length; i++) {
				if (fileChecks[i].check(fileData)) {
					fileStructure = fileChecks[i].getFileStructure();
					fontName = fileChecks[i].getFontName();
	
					return;
				}
			}
    	}
    	
    	ebcdicFile = (ebcdicCount - asciiCount) * 20 / fileData.length > 3
    			|| (ebcdicCount > asciiCount && ebcdicRun > 2 && ebcdicRun > asciiRun);
    	
    	if (ebcdicFile) {
    		fontName = "cp037";
    	} else {
    		fontName = "";
    	}
    	System.out.println("Ebcidic Counts " + ebcdicCount + " " + asciiCount
    			+ " -- " + ebcdicRun + " " + asciiRun);
    	fileStructure = Constants.IO_FIXED_LENGTH;

    	findRecordLength(fileData, lengthStr);
	}
    
    
    
   private void findRecordLength(byte[] fileData, String lengthStr) {
	   recordLength = Constants.NULL_INTEGER;
	   try {
		   recordLength = Integer.parseInt(lengthStr);
	   } catch (Exception e) {
		   // TODO: handle exception
	   }
   	
	   if (recordLength <= 0) {
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

			   if (recordDefinition.numRecords+1 < recordDefinition.records.length && recordDefinition.numRecords*i < fileData.length) {
				   int sz = fileData.length - recordDefinition.numRecords*i;
				   recordDefinition.records[recordDefinition.numRecords] = new byte[sz]; 
				   System.arraycopy(fileData, recordDefinition.numRecords*i, recordDefinition.records[recordDefinition.numRecords], 0, sz);
				   recordDefinition.numRecords += 1;
			   }
			   if (recordDefinition.numRecords < 4) {
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
   }


   public ExternalRecord getLayoutDetails(boolean nameFields) {
	   Details details = new Details();
	   RecordDefinition recordDefinition = details.standardRecord;

	   ByteArrayInputStream is = new ByteArrayInputStream(fData);
	   try {
		   details.fileStructure = fileStructure;
		   details.fontName = fontName;
		   details.recordLength = recordLength;

		   byte[] l;
		   int i = 0;
		   AbstractByteReader r = ByteIOProvider.getInstance().getByteReader(fileStructure);

		   r.setLineLength(recordLength);
		   r.open(is);
		   while (i < recordDefinition.records.length && (l = r.read()) != null) {
			   recordDefinition.records[i++] = l;
			   recordDefinition.numRecords = i;
		   }
	   } catch (Exception e) {
		   e.printStackTrace();	
	   } finally {
		   try {
			   is.close();
		   } catch (Exception e) {
			   // TODO: handle exception
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
	   
	   return details.createRecordLayout();
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

	private static interface checkFile {
    	public boolean check(byte[] data);
    	public int getFileStructure();
    	public String getFontName();
    }
    
    private static class StandardCheckFile implements checkFile {
    	private int structure;
    	private String font = "";
    	
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
       			AbstractByteReader reader = ByteIOProvider.getInstance().getByteReader(structure);
       			reader.open(new ByteArrayInputStream(data));
       			
       			for (int i = 0; 
       					i < 20 && len < data.length && ((bytes = reader.read()) != null);
       					i++) {
       				len += bytes.length;
       			}
       			reader.close();
       		} catch (Exception e) {
				ret = false;
			} 
       		return ret;
       	}
       	
    	public int getFileStructure() {
    		return structure;
    	}
    	
    	public String getFontName() {
    		return font;
    	}
    }
    
    private static class BinTextCheck extends StandardCheckFile {
    	public BinTextCheck() {
    		super(Constants.IO_BIN_TEXT);
    	}
    	
    	@Override
    	public boolean check(byte[] data) {
    		boolean ret = false;
    		if (data != null) {
	    		int count = countTextChars(data, ASCII_CHAR);
	    		
	    		//System.out.println(" }}} " + (count * 100 / data.length));
	    		if (count * 100 / data.length >= 70) {
	    			ret = super.check(data);
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
    		if (data == null || data.length < 4) {
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
       				if (data[blockLength + 7] == 0 && data[blockLength + 8] ==0) {
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
