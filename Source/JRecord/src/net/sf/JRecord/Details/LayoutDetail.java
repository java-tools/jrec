/*
 * Created on 8/05/2004
 *
 *  This class represents a group of records
 *
 * Modification log:
 * On 2006/06/28 by Jean-Francois Gagnon:
 *    - Made sure a Group of Records is tested to see if
 *      there is binary format to be handled
 *
 * Version 0.61 (2007/03/29)
 *    - CSV Split for when there a blank columns in the CSV file
 */
package net.sf.JRecord.Details;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.JRecord.Common.AbstractRecord;
import net.sf.JRecord.Common.BasicTranslation;
import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.CsvParser.ICsvLineParser;
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import net.sf.JRecord.CsvParser.ICsvDefinition;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Types.ISizeInformation;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;




/**
 * This class represents a <b>record-layout</b> description. i.e.
 * It describes the Structure of both a File and the lines in it.
 * <p>A <b>Layout</b> can have
 * one or more <b>Records</b> (class RecordDetail) which intern
 * holds one or more <b>fields</b> (class FieldDetail).
 *
 * <pre>
 *     LayoutDetail  - Describes a file
 *       |
 *       +----- RecordDetail (1 or More) - Describes one record in the file
 *                |
 *                +------  FieldDetail (1 or More)  - Describes one field in the file
 * </pre>
 *
 * <p>There are several ways to load a RecordLayout
 * <pre>
 * <b>Loading an RecordEditor-XML:</b>
 *          LayoutDetail layout = CopybookLoaderFactory.getInstance().getLayoutRecordEditXml(copybookName, null);
 *
 * <b>Using the Loader Factory CopybookLoaderFactory:</b>
 *          CopybookLoader loader = CopybookLoaderFactory.getInstance()
 *                  .getLoader(CopybookLoaderFactory.RECORD_EDITOR_XML_LOADER);
 *          LayoutDetail layout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, null).asLayoutDetail();
 *
 * <b>Creating the loader:</b>
 *          CopybookLoader loader = new RecordEditorXmlLoader();
 *          LayoutDetail layout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, null).asLayoutDetail();
 *
 * @param pLayoutName Record Layout Name
 * @param pRecords All the sub records
 * @param pDescription Records Description
 * @param pLayoutType Record Type
 * @param pRecordSep Record Separator
 * @param pEolIndicator End of line indicator
 * @param pFontName Canonical Name
 * @param pRecordDecider used to decide which layout to use
 * @param pFileStructure file structure
 *
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class LayoutDetail
extends BasicLayout<RecordDetail> {

	
	private String layoutName;
	private String description;
	private byte[] recordSep;
	private int layoutType;
	// private RecordDetail[] records;
	private boolean binary = false;
	private String fontName = "";
	private String eolString;
	//private TypeManager typeManager;
	private RecordDecider decider;

	private HashMap<String, IFieldDetail> fieldNameMap = null;
	private String delimiter = "";
	private int fileStructure, recordFileStructure = -1;

	private int recordCount, lineNumberOfFieldNames = 1;

	private boolean treeStructure = false;

	private boolean allowChildren =false;
	private boolean fixedLength = true;
	private boolean useThisLayout = false;

	private Object extraDetails = null;
	private boolean multiLineField = false;
	private boolean multiByteCharset;
	private final boolean isCsv;


	/**
	 * This class holds a one or more records
	 *
	 * @param pLayoutName Record Layout Name
	 * @param pRecords All the sub records
	 * @param pDescription Records Description
	 * @param pLayoutType Record Type
	 * @param pRecordSep Record Separator
	 * @param pEolIndicator End of line indicator
	 * @param pFontName Canonical Name
	 * @param pRecordDecider used to decide which layout to use
	 * @param pFileStructure file structure
	 *
	 * @throws RecordException multiple field delimiters used
	 */
	public LayoutDetail(final String pLayoutName,
	        		   final RecordDetail[] pRecords,
	        		   final String pDescription,
	        		   final int pLayoutType,
	        		   final byte[] pRecordSep,
	        		   final String pEolIndicator,
	        		   final String pFontName,
	        		   final RecordDecider pRecordDecider,
	        		   final int pFileStructure)
	{
	    super();

        int i, j;
        boolean first = true;
        int lastSize = -1;


	    this.layoutName    = pLayoutName;
		this.records       = pRecords;
		this.description   = pDescription;
		this.layoutType    = pLayoutType;
		this.recordSep     = pRecordSep;
		this.fontName      = pFontName;
		this.decider       = pRecordDecider;
		this.fileStructure = pFileStructure;
		this.recordCount   = pRecords.length;

		if (fontName == null) {
		    fontName = "";
		}

		while (recordCount > 0 && pRecords[recordCount - 1] == null) {
		    recordCount -= 1;
		}

		if (recordSep == null) {
			if (fontName == null || "".equals(fontName)) {
				recordSep = Constants.SYSTEM_EOL_BYTES;
			} else {
				recordSep = CommonBits.getEolBytes(null, "", fontName);
						//Conversion.getBytes(System.getProperty("line.separator"), fontName);
			}
			
			recordSep = CommonBits.getEolBytes(recordSep, pEolIndicator, fontName);
		}

		if (Constants.DEFAULT_STRING.equals(pEolIndicator)
		||  pRecordSep == null) {
		    eolString = System.getProperty("line.separator");
		    if (recordSep != null && recordSep.length < eolString.length()) {
		    	eolString = Conversion.toString(recordSep, pFontName);
		    }
		} else {
		    eolString = Conversion.toString(recordSep, pFontName);
		}


		RecordDetail recordDetail;
		switch (pLayoutType) {
			case Constants.rtGroupOfBinaryRecords:
			case Constants.rtFixedLengthRecords:
			case Constants.rtBinaryRecord:
			    binary = true;
			break;
            case Constants.rtGroupOfRecords:
			case Constants.rtRecordLayout:
			    if (recordCount >= 1) {
			        int numFields;
			        for (j = 0; (! binary) && j < recordCount; j++) {
			        	recordDetail = pRecords[j];
			            numFields =  recordDetail.getFieldCount();
			            for (i = 0; (! binary) && i < numFields; i++) {
			                binary = recordDetail.isBinary(i);
			            }
			        }
			    }
			break;
			default:
		}
		
		
        boolean namesFirstLine = false;

        switch (fileStructure) {
        case Constants.IO_BIN_NAME_1ST_LINE:
        case Constants.IO_NAME_1ST_LINE:
        case Constants.IO_UNICODE_NAME_1ST_LINE:
        	namesFirstLine = true;
        }

        
        boolean tmpCsv = false;
	    for (j = 0; j < recordCount; j++) {
	    	recordDetail = pRecords[j];
	    	if (recordDetail != null) {
	    		if (recordDetail != null && recordDetail.isDelimited()) {
					tmpCsv = true;				
				}
	    		if (recordDetail.getFieldCount() > 0) {
		    		if ((lastSize >= 0 && lastSize != recordDetail.getLength())
		    		||  (recordDetail.getField(recordDetail.getFieldCount() - 1).getType()
		    				== Type.ftCharRestOfRecord )){
		    			fixedLength = false;
		    		}
		    		lastSize = recordDetail.getLength();
	
			    	treeStructure = treeStructure || (recordDetail.getParentRecordIndex() >= 0);
			        if (recordDetail.isDelimited()) {
			        	fixedLength = false;
			            if (first) {
			                delimiter = recordDetail.getDelimiter();
			                first = false;
			            } else if (! delimiter.equals(recordDetail.getDelimiter())) {
			                throw new RuntimeException(
			                        	"only one field delimiter may be used in a Detail-Group "
			                        +   "you have used \'" + delimiter
			                        +   "\' and \'"
			                        +  recordDetail.getDelimiter() + "\'"
			                );
			            }
			            if (recordFileStructure < 0) {
			       			recordFileStructure = recordDetail.getCsvParser().getFileStructure(recordDetail, namesFirstLine, isBinCSV());
			    		}
			        }
			        
			        if (! multiLineField) {
			        	for (int k = 0; (! multiLineField) && k < recordDetail.getFieldCount(); k++) {
			        		int type = recordDetail.getField(k).getType();
							switch (type) {
							case  Type.ftHtmlField:
							case  Type.ftMultiLineChar:
							case  Type.ftMultiLineEdit:
							case  Type.ftCharMultiLine:
			        			multiLineField = true;
			        			break;
			        		}
			        	}
			        }
	    		}
	    	}
	    }
		isCsv = tmpCsv;

		System.out.print("Font >" + fontName + "<" +  " || " + Conversion.isAlwaysUseDefaultSingByteCharset());
		if ((binary || Conversion.isAlwaysUseDefaultSingByteCharset())
		&& fontName.length() == 0 && Conversion.DEFAULT_CHARSET_DETAILS.isMultiByte) {
			fontName = Conversion.getDefaultSingleByteCharacterset();
		}
		this.multiByteCharset = Conversion.isMultiByte(fontName);
		System.out.println(" >" + fontName + "< " + multiByteCharset);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getFieldDescriptions(int, int)
	 */
	public String[] getFieldDescriptions(final int layoutIdx, int columnsToSkip) {
	    if (layoutIdx >= recordCount) {
	        return null;
	    }
	    RecordDetail rec = records[layoutIdx];
		String[] ret = new String[rec.getFieldCount() - columnsToSkip];
		int i, idx;

		for (i = 0; i < rec.getFieldCount() - columnsToSkip; i++) {
		    idx = getAdjFieldNumber(layoutIdx, i + columnsToSkip);
			ret[i] = rec.getField(idx).getDescription();
			if (ret[i] == null || "".equals(ret[i])) {
			    ret[i] = rec.getField(idx).getName();
			}
		}

		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getDescription()
	 */
	public String getDescription() {
		return description;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getLayoutName()
	 */
	public String getLayoutName() {
		return layoutName;
	}

	/**
	 * Get all the record Details.
	 *
	 * @return all the record layouts
	 * @deprecated use getRecord instead
	 */
	public RecordDetail[] getRecords() {
		return records;
	}

	/**
	 * Add a record to the layout
	 * @param record new record
	 */
	public void addRecord(RecordDetail record) {
	    if (recordCount >= records.length) {
	    	RecordDetail[] temp = records;
	        records = new RecordDetail[recordCount + 5];
	        System.arraycopy(temp, 0, records, 0, temp.length);
	        recordCount = temp.length;
	    }
	    records[recordCount] = record;
	    recordCount += 1;
	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getRecordCount()
	 */
	public int getRecordCount() {
		return recordCount;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getLayoutType()
	 */
	public int getLayoutType() {
		return layoutType;
	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getRecordSep()
	 */
	public byte[] getRecordSep() {
		return recordSep;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#isBinary()
	 */
    public boolean isBinary() {
        return binary;
    }

    public boolean isFixedLength() {
    	return fixedLength;
    }

    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getFontName()
	 */
    public String getFontName() {
        return fontName;
    }


    /**
	 * @param fontName the fontName to set
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
		System.out.println("New Font= " + fontName);
		this.multiByteCharset = Conversion.isMultiByte(fontName);
		System.out.println("New Font= " + fontName + " " + multiByteCharset);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getEolString()
	 */
    public String getEolString() {
        return eolString;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getFileStructure()
	 */
    public int getFileStructure() {
        int ret = fileStructure;
 
        switch (fileStructure) {
        case Constants.IO_TEXT_LINE:
        case Constants.IO_BIN_NAME_1ST_LINE:
        case Constants.IO_NAME_1ST_LINE:
        case Constants.IO_UNICODE_NAME_1ST_LINE:
        case Constants.IO_DEFAULT:
        case Constants.IO_BIN_TEXT:
        	if (recordFileStructure > 0) {
        		return recordFileStructure;
        	}
        }

        if (fileStructure == Constants.IO_NAME_1ST_LINE &&  isBinCSV()) {
        	ret = Constants.IO_BIN_NAME_1ST_LINE;
        } else if (fileStructure > Constants.IO_TEXT_LINE) {
        } else if (fileStructure == Constants.IO_TEXT_LINE) {
			ret = checkTextType();
        } else if (getLayoutType() == Constants.rtGroupOfBinaryRecords
               &&  recordCount > 1) {
		    ret = Constants.IO_BINARY_IBM_4680;
		} else if (isBinary()) {
		    ret = Constants.IO_FIXED_LENGTH;
		} else {
			ret = checkTextType();
		}
       //System.out.println(" ~~ getFileStructure " + fileStructure + " " + ret);

		return ret;
    }


    private int checkTextType() {
    	int ret = fileStructure;
    	if ( isBinCSV()) {
			ret = Constants.IO_BIN_TEXT;
		} else if (multiByteCharset) {
    		return Constants.IO_UNICODE_TEXT;
		} else if (fontName != null && ! "".equals(fontName) && ! fontName.equals(Conversion.getDefaultSingleByteCharacterset())){
		    ret = Constants.IO_TEXT_LINE;
		} else {
			ret = Constants.IO_BIN_TEXT;
		}
    	
//    	System.out.println("Get File Structure: " + ret + " " + isBinCSV() + " " + multiByteCharset + " " + fontName 
//    			+ " " + Conversion.getDefaultSingleByteCharacterset());

    	return ret;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getDecider()
	 */
    public RecordDecider getDecider() {
        return decider;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLayoutDetails#getField(byte[], int, net.sf.JRecord.Common.IFieldDetail)
	 */
	@Override
	public Object getField(byte[] record, int type, IFieldDetail field) {

    	//System.out.print(" ---> getField ~ 1");
        if (field.isFixedFormat()) {
            return TypeManager.getSystemTypeManager().getType(type) //field.getType())
					.getField(record, field.getPos(), field);
        }

        if (isBinCSV()) {
        	//System.out.print(" 3 ");
        	String value = (new BinaryCsvParser(delimiter)).getValue(record, field);

        	return formatField(field,  type, value);
        } else {
	        return formatCsvField(field,  type, Conversion.toString(record, field.getFontName()));
        }
    }

    public final Object formatCsvField(IFieldDetail field,  int type, String value) {
        String val = "";
    	ICsvLineParser parser = ParserManager.getInstance().get(field.getRecord().getRecordStyle());
    	val = parser.getField(field.getPos() - 1, value, getCsvDef(field));

        return formatField(field,  type, val);
    }


    private Object formatField(IFieldDetail field,  int type, String value) {

        //System.out.print(" ~ " + delimiter + " ~ " + new String(record));

        if (value != null && ! "".equals(value)) {
        	byte[] rec = Conversion.getBytes(value, field.getFontName());
            FieldDetail fldDef
        		= new FieldDetail(field.getName(), "", type,
        		        		   field.getDecimal(), field.getFontName(),
        		        		   field.getFormat(), field.getParamater());

            fldDef.setRecord(field.getRecord());

            fldDef.setPosLen(1, rec.length);

//            System.out.println(" ~ " + TypeManager.getSystemTypeManager().getType(type)
//					.getField(Conversion.getBytes(value, font),
//					          1,
//					          fldDef));
            return TypeManager.getSystemTypeManager().getType(type)
					.getField(rec,
					          1,
					          fldDef);
        }
        //System.out.println();

        return "";
    }


    /**
     * Set a fields value
     *
     * @param record record containg the field
     * @param field field to retrieve
     * @param value value to set
     *
     * @return byte[] updated record
     *
     * @throws RecordException any conversion error
     */
    public byte[] setField(byte[] record, IFieldDetail field, Object value)
    throws RecordException {
        return setField(record, field.getType(), field, value);
    }

    /**
     * Set a fields value
     *
     * @param record record containg the field
     * @param type type to use in the conversion
     * @param field field to retrieve
     * @param value value to set
     *
     * @return byte[] updated record
     *
     * @throws RecordException any conversion error
     */
    public byte[] setField(byte[] record, int type, IFieldDetail field, Object value)
    throws RecordException {
        if (field.isFixedFormat()) {
        	if (value != null && value instanceof String) {
        		String v = value.toString();
        		if (v.startsWith("\r")) {
        			value  = v.substring(1);
        		}
        	}
            record = TypeManager.getSystemTypeManager().getType(type)
            				    .setField(record, field.getPos(), field, value);
        } else  {
            String font = field.getFontName();
            ICsvLineParser parser = ParserManager.getInstance().get(field.getRecord().getRecordStyle());

            Type typeVal = TypeManager.getSystemTypeManager().getType(type);
            String s = "";
            if ((value == null || value == CommonBits.NULL_VALUE)) {
            	if (TypeManager.isNumeric(field.getType())) {
            		s = typeVal.formatValueForRecord(field, "0");
            	}
            } else if (value instanceof String) {
            	s = typeVal.formatValueForRecord(field, (String) value);
            } else if (typeVal instanceof ISizeInformation){
            	byte[] data = new byte[((ISizeInformation) typeVal).getNormalSize()];
                FieldDetail fldDef
            		= new FieldDetail(field.getName(), "", type,
            		        		   field.getDecimal(), field.getFontName(),
            		        		   field.getFormat(), field.getParamater());

                fldDef.setRecord(field.getRecord());

                fldDef.setPosLen(1, data.length);

            	typeVal.setField(data, 1, fldDef, value);
            	s = Conversion.toString(data, field.getFontName());
            } else {
            	s = typeVal.formatValueForRecord(field, value.toString());
            }
            //System.out.println(" ---> setField ~ " + delimiter + " ~ " + s + " ~ " + new String(record));
            if  (isBinCSV()) {
             	record = (new BinaryCsvParser(delimiter)).updateValue(record, field, s);
            } else {
            	String newLine = parser.setField(field.getPos() - 1,
	            		typeVal.getFieldType(),
	            		Conversion.toString(record, font),
	            		getCsvDef(field), s);

                record = Conversion.getBytes(newLine, font);
            }
        }
        //System.out.println(" ---> setField ~ Done");
        return record;
    }
    
    /**
     * Get the number of fields in a Csv line
     * @param idx record index
     * @param line line to analyze 
     * @return number of Csv fields
     */
    public final int getCsvFieldCount(int idx, String line) {
    	int ret = 0;
    	if (idx >= 0 && idx < getRecordCount()) {
    		RecordDetail record = getRecord(idx);
    		
    		ret = record.getFieldCount();
    		if (record.isDelimited()) {
    			ICsvLineParser parser = ParserManager.getInstance().get(record.getRecordStyle());

    			ret = parser.getFieldList(line, getCsvDef(record, record.getQuote())).size();
    		}
    	}
    	return ret;
    }
    
    private ICsvDefinition getCsvDef(IFieldDetail field) {
    	return getCsvDef(field.getRecord(), field.getQuote());
    }
    
    public final ICsvDefinition getCsvDef(AbstractRecord rec, String quote) {
    	ICsvDefinition csvDef;
    	if (rec instanceof ICsvDefinition) {
    		csvDef = (ICsvDefinition) rec;
    	} else {
    		csvDef = new CsvDefinition(delimiter, quote);
    	}
    	return csvDef;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getFieldFromName(java.lang.String)
	 */
    public IFieldDetail getFieldFromName(String fieldName) {
    	IFieldDetail ret = null;
    	String key = fieldName.toUpperCase();
	
		buildFieldNameMap();
		
    	if (fieldNameMap.containsKey(key)) {
    		ret = fieldNameMap.get(key);
    	}

    	return ret;
    }

    private void buildFieldNameMap() {
    	IFieldDetail fld;
    	if (fieldNameMap == null) {
    		int i, j, k, size;
			String name, nameTmp;

    		size = 0;
    		for (i = 0; i < recordCount; i++) {
    		    size += records[i].getFieldCount();
    		}
    		size = (size * 5) / 4 + 4;

    		fieldNameMap = new HashMap<String, IFieldDetail>(size);

    		for (i = 0; i < recordCount; i++) {
    			//FieldDetail[] flds = records[i].getFields();
    			for (j = 0; j < records[i].getFieldCount(); j++) {
    			    fld = records[i].getField(j);
    			    nameTmp = fld.getName();
    			    name = nameTmp;
    			    nameTmp = nameTmp + "~";
    			    k = 1;
    			    while (fieldNameMap.containsKey(name.toUpperCase())) {
    			    	name = nameTmp + k++;
    			    }
    			    fld.setLookupName(name);
					fieldNameMap.put(name.toUpperCase(), fld);
    			}
    			records[i].setNumberOfFieldsAdded(0);
    		}
     	} else if (this.isBuildLayout()) {
     		int j;

       		for (int i = 0; i < recordCount; i++) {
       			if (records[i].getNumberOfFieldsAdded() > 0) {
       				for (j = 1; j <=  records[i].getFieldCount(); j++) {
       	   			    fld = records[i].getField(records[i].getFieldCount() - j);
//       	   			    System.out.println("Adding ... " + (records[i].getFieldCount() - j)
//       	   			    		+ " " + fld.getName());
        				fieldNameMap.put(fld.getName().toUpperCase(), fld);
      				}
       	    		records[i].setNumberOfFieldsAdded(0);
      			}
       		}

    	}
    }



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getDelimiter()
	 */
    public String getDelimiter() {
        return delimiter;
    }




	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getDelimiterBytes()
	 */
    public byte[] getDelimiterBytes() {
    	byte[] ret;
    	if (isBinCSV()) {
    		ret = new byte[1];
    		ret[0] = Conversion.getByteFromHexString(delimiter);
    	} else {
    		ret = Conversion.getBytes(delimiter, fontName);
    	}
        return ret;
    }


    /**
     * set the delimiter
     * @param delimiter new delimiter
     */
    public void setDelimiter(String delimiter) {
    	String delim = RecordDetail.convertFieldDelim(delimiter);
    	if (this.records != null) {
    		for (int i = 0; i < records.length; i++) {
    			records[i].setDelimiter(delim);
    		}
    	}
		this.delimiter = delim;
	}


	/**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getAdjField(int, int)
	 */
    public IFieldDetail getAdjField(int layoutIdx, int fieldIdx) {
        return getRecord(layoutIdx)
                       .getField(getAdjFieldNumber(layoutIdx, fieldIdx));
    }


    /**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getAdjFieldNumber(int, int)
	 */
    public int getAdjFieldNumber(int recordIndex, int inColumn) {

	    int ret = inColumn;

	    //System.out.println("~~ " + inColumn + " " + ret + " "
	    //        + layout.getRecord(layoutIndex).getRecordName() + " " + layout.getRecord(layoutIndex).getFieldCount());

	    if (ret > 0 && getFileStructure() == Constants.IO_XML_BUILD_LAYOUT) {
	        int len = getRecord(recordIndex).getFieldCount();

            if (ret > len - 3) {
                ret -= len - 3;
            } else {
                ret += 2;
            }
	    }
	    return ret;
    }

    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getUnAdjFieldNumber(int, int)
	 */
    public int getUnAdjFieldNumber(int recordIndex, int inColumn) {

	    int ret = inColumn;

	    //System.out.println("~~ " + inColumn + " " + ret + " "
	    //        + layout.getRecord(layoutIndex).getRecordName() + " " + layout.getRecord(layoutIndex).getFieldCount());

	    if (ret > 0 && getFileStructure() == Constants.IO_XML_BUILD_LAYOUT) {
	        int len = getRecord(recordIndex).getFieldCount();

            if (ret < 3) {
                ret += len - 3;
            } else {
                ret -= 2;
            }
	    }
	    return ret;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#isXml()
	 */
    public final boolean isXml() {
        return fileStructure == Constants.IO_XML_USE_LAYOUT
            || fileStructure == Constants.IO_XML_BUILD_LAYOUT;
    }

    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#isOkToAddAttributes()
	 */
    public final boolean isOkToAddAttributes() {
    	return fileStructure == Constants.IO_XML_BUILD_LAYOUT;
    }

    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#isBuildLayout()
	 */
    public final boolean isBuildLayout() {
    	return fileStructure == Constants.IO_XML_BUILD_LAYOUT
    	    || fileStructure == Constants.IO_NAME_1ST_LINE;
    }


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#hasTreeStructure()
	 */
	public final boolean hasTreeStructure() {
		return treeStructure;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#isBinCSV()
	 */
	public boolean isBinCSV() {
		boolean ret = false;
		if (delimiter != null && delimiter.length() > 1) {
			ret = delimiter.toLowerCase().startsWith("x'");
		}
		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#isAllowChildren()
	 */
	public final boolean hasChildren() {
		return allowChildren;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#setAllowChildren(boolean)
	 */
	public final void setAllowChildren(boolean allowChildren) {
		this.allowChildren = allowChildren;
	}


	/**
	 * @see net.sf.JRecord.Details.BasicLayout#getNewLayout(java.util.ArrayList)
	 */
	@Override
	protected LayoutDetail getNewLayout(
			ArrayList<RecordDetail> recordList) {
		RecordDetail[] recs = new RecordDetail[recordList.size()];
		recs = recordList.toArray(recs);
		return new LayoutDetail(getLayoutName(), recs, getDescription(),
				getLayoutType(), getRecordSep(), getEolString(),
				getFontName(), getDecider(), getFileStructure());
	}

//	@Override
//	protected RecordDetail getNewRecord(RecordDetail record, ArrayList<FieldDetails> fields) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * @see net.sf.JRecord.Details.BasicLayout#getNewRecord(net.sf.JRecord.Details.AbstractRecordDetail, java.util.ArrayList)
	 */
	@Override
	protected RecordDetail getNewRecord(RecordDetail record, ArrayList<? extends AbstractRecordDetail.FieldDetails> fields) {
		RecordDetail.FieldDetails[] flds = new RecordDetail.FieldDetails[fields.size()];
		RecordDetail ret;

		flds = fields.toArray(flds);
		ret = new RecordDetail(record.getRecordName(),
				record.getRecordType(), record.getDelimiter(), record.getQuote(),
				record.getFontName(), flds, record.getRecordStyle(),
				record.getRecordSelection(), record.getChildId());

		return ret;
	}


	/**
	 * @return the fieldNamesLine
	 */
	public int getLineNumberOfFieldNames() {
		return lineNumberOfFieldNames;
	}


	/**
	 * @param fieldNamesLine the fieldNamesLine to set
	 */
	public void setLineNumberOfFieldNames(int fieldNamesLine) {
		this.lineNumberOfFieldNames = fieldNamesLine;
	}


	/**
	 * @return the extraDetails
	 */
	public Object getExtraDetails() {
		return extraDetails;
	}


	/**
	 * @param extraDetails the extraDetails to set
	 */
	public void setExtraDetails(Object extraDetails) {
		this.extraDetails = extraDetails;
	}


	/**
	 * @return the useThisLayout
	 */
	public boolean useThisLayout() {
		return useThisLayout;
	}


	/**
	 * @param useThisLayout the useThisLayout to set
	 */
	public void setUseThisLayout(boolean useThisLayout) {
		this.useThisLayout = useThisLayout;
	}


	public final void checkLayout() {
		for (RecordDetail r : records) {
			for (int i = 0; i < r.getFieldCount(); i++) {
				IFieldDetail field = r.getField(i);
				if (field.getPos() < 1) {
					throw new RuntimeException(
							BasicTranslation.getTrans().convert(
									BasicTranslation.ST_ERROR,
									"Invalid field position for Record: {0} {1}, it should be greater than zero and not {2}",
									new Object[] {r.getRecordName(), field.getName(), field.getPos()}));
				}
			}
		}
	}

	@Override
	public int getOption(int option) {
		switch (option) {
		case Options.OPT_CHECK_LAYOUT_OK:
			checkLayout();
			return Options.YES;
		case Options.OPT_STORAGE_TYPE:
			switch (getFileStructure()) {
	    	case Constants.IO_XML_BUILD_LAYOUT:
	    	case Constants.IO_XML_USE_LAYOUT:
		      	return Options.OTHER_STORAGE;
	    	case Constants.IO_GETTEXT_PO:
	    	case Constants.IO_TIP:
//	    	case Constants.IO_FIXED_LENGTH:
		      	return Options.OTHER_STORAGE;
	    	case Constants.IO_UNICODE_NAME_1ST_LINE:
	    	case Constants.IO_UNICODE_TEXT:
	    	case Constants.IO_UNICODE_CSV:
	    		return Options.TEXT_STORAGE;
	    	case Constants.IO_TEXT_LINE:
	    		if (multiByteCharset && ! binary) {
	    			return Options.TEXT_STORAGE;
	    		}
			}
			return Options.BINARY_STORAGE;
		case Options.OPT_CHECK_4_STANDARD_FILE_STRUCTURES:
			return Options.getValue( fileStructure == Constants.IO_DEFAULT
								||	 fileStructure == Constants.IO_VB
								||	 fileStructure == Constants.IO_VB_DUMP
								||	 fileStructure == Constants.IO_VB_FUJITSU
								||	 fileStructure == Constants.IO_VB_OPEN_COBOL
								);
		case Options.OPT_CHECK_4_STANDARD_FILE_STRUCTURES2:
			return Options.getValue( fileStructure == Constants.IO_BIN_TEXT
								||	 fileStructure == Constants.IO_FIXED_LENGTH);
		case Options.OPT_SUPPORTS_BLOCK_STORAGE:
//			if (! isXml()) {
				switch (fileStructure) {
				case Constants.IO_BIN_CSV:
				case Constants.IO_BIN_CSV_NAME_1ST_LINE:
				case Constants.IO_BIN_NAME_1ST_LINE:
				case Constants.IO_BIN_TEXT:
				case Constants.IO_BINARY_IBM_4680:
				case Constants.IO_CSV:
				case Constants.IO_CSV_NAME_1ST_LINE:
				case Constants.IO_FIXED_LENGTH:
				case Constants.IO_DEFAULT:
				case Constants.IO_GENERIC_CSV:
				case Constants.IO_NAME_1ST_LINE:
				case Constants.IO_TEXT_LINE:
				case Constants.IO_UNICODE_CSV:
				case Constants.IO_UNICODE_CSV_NAME_1ST_LINE:
				case Constants.IO_UNICODE_NAME_1ST_LINE:
				case Constants.IO_UNICODE_TEXT:
				case Constants.IO_VB:
				case Constants.IO_VB_DUMP:
				case Constants.IO_VB_FUJITSU:
				case Constants.IO_VB_OPEN_COBOL:
				case Constants.IO_WIZARD:
					return Options.YES;
				}
//			}
			return Options.NO;
		case Options.OPT_IS_FIXED_LENGTH:
			return Options.getValue(isFixedLength());
		case Options.OPT_CAN_ADD_DELETE_FIELD_VAlUES:
		case Options.OPT_IS_CSV:
			return Options.getValue(isCsv);
		case Options.OPT_IS_TEXT_EDITTING_POSSIBLE:
			int fs = getFileStructure();
			if (isBinary() || isBinCSV() || multiLineField) {
				return Options.NO;
			}
			switch (fs) {
			case Constants.IO_GETTEXT_PO:
			case Constants.IO_TIP:
			case Constants.IO_CSV:
			case Constants.IO_UNICODE_CSV:
			case Constants.IO_BIN_CSV:
			case Constants.IO_BIN_CSV_NAME_1ST_LINE:
			case Constants.IO_CSV_NAME_1ST_LINE:
			case Constants.IO_UNICODE_CSV_NAME_1ST_LINE:
				return Options.NO;
			}
			return Options.YES;
		case Options.OPT_TABLE_ROW_HEIGHT:
			if (multiLineField) {
				return 3;
			}
//			for (RecordDetail r : records) {
//				for (int i = 0; i < r.getFieldCount(); i++) {
//					int type = r.getField(i).getType();
//					switch (type) {
//					case  Type.ftHtmlField:
//					case  Type.ftMultiLineChar:
//					case  Type.ftMultiLineEdit:
//					case  Type.ftCharMultiLine:
//						return 3;
//					}
//				}
//			}
			return 1;

//			switch (fs) {
//			case Constants.IO_FIXED_LENGTH:
//			case Constants.IO_DEFAULT:
////			case Constants.IO_GENERIC_CSV:
//			case Constants.IO_NAME_1ST_LINE:
//			case Constants.IO_TEXT_LINE:
//			case Constants.IO_BIN_TEXT:
//			case Constants.IO_BIN_NAME_1ST_LINE:
//			case Constants.IO_UNICODE_NAME_1ST_LINE:
//			case Constants.IO_UNICODE_TEXT:
//			case Constants.IO_VB:
//			case Constants.IO_VB_DUMP:
//			case Constants.IO_VB_FUJITSU:
//			case Constants.IO_VB_OPEN_COBOL:
//			case Constants.IO_WIZARD:
//				if (! isBinary()) {
//					return Options.YES;
//				}
//			}
//			return Options.NO;
		
		}
		return Options.UNKNOWN;
	}


//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Details.AbstractLayoutDetails#getAttribute(net.sf.JRecord.Details.IAttribute)
//	 */
//	@Override
//	public Object getAttribute(IAttribute attr) {
//
//		return null;
//	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLayoutDetails#setAttribute(net.sf.JRecord.Details.IAttribute, java.lang.Object)
	 */
	@Override
	public void setAttribute(IAttribute attr, Object value) {

		if (attr == Attribute.FILE_STRUCTURE && value instanceof Number) {
			fileStructure = ((Number) value).intValue();
		}
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.IBasicFileSchema#getQuote()
	 */
	@Override
	public String getQuote() {
		if (recordCount > 0) {
			return getRecord(0).getQuote();
		}
		return null;
	}
}


