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

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.CsvParser.AbstractParser;
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import net.sf.JRecord.CsvParser.ParserManager;
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
extends BasicLayout<FieldDetail, RecordDetail> {

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

	private HashMap<String, FieldDetail> fieldNameMap = null;
	private String delimiter = "";
	private int fileStructure;

	private int recordCount;
	
	private boolean treeStructure = false;
	
	private boolean allowChildren =false;
	private boolean fixedLength = true;


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

		while (recordCount > 0 && pRecords[recordCount - 1] == null) {
		    recordCount -= 1;
		}

		if (recordSep == null) {
		    recordSep = Constants.SYSTEM_EOL_BYTES;;
		}

		if (Constants.DEFAULT_STRING.equals(pEolIndicator)
		||  pRecordSep == null) {
		    eolString = System.getProperty("line.separator");
		} else {
		    eolString = new String(pRecordSep);
		}

		if (fontName == null) {
		    fontName = "";
		}

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
			            numFields =  pRecords[j].getFieldCount();
			            for (i = 0; (! binary) && i < numFields; i++) {
			                binary = pRecords[j].isBinary(i);
			            }
			        }
			    }
			break;
			default:
		}

	    for (j = 0; j < recordCount; j++) {
	    	if (pRecords[j] != null) {
	    		if (lastSize >= 0 && lastSize != pRecords[j].getLength()) {
	    			fixedLength = false;
	    		}
	    		lastSize = pRecords[j].getLength();
	    		
		    	treeStructure = treeStructure || (pRecords[j].getParentRecordIndex() >= 0);
		        if ((pRecords[j].getRecordType() == Constants.rtDelimitedAndQuote
		          || pRecords[j].getRecordType() == Constants.rtDelimited)
		        &&  (!delimiter.equals(pRecords[j].getDelimiter()))) {
		        	fixedLength = false;
		            if (first) {
		                delimiter = pRecords[j].getDelimiter();
		                first = false;
		            } else {
		                throw new RuntimeException(
		                        	"only one field delimiter may be used in a Detail-Group "
		                        +   "you have used \'" + delimiter
		                        +   "\' and \'"
		                        +  pRecords[j].getDelimiter() + "\'"
		                );
		            }
		        }
	    	}
	    }

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

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#addRecord(net.sf.JRecord.Details.RecordDetail)
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
        int ret;

         if (fileStructure == Constants.IO_NAME_1ST_LINE &&  isBinCSV()) {
        	ret = Constants.IO_BIN_NAME_1ST_LINE;
        } else if (fileStructure > Constants.IO_TEXT_LINE) {
            ret = fileStructure;
        } else if (getLayoutType() == Constants.rtGroupOfBinaryRecords
               &&  recordCount > 1) {
		    ret = Constants.IO_BINARY;
		} else if (isBinary()) {
		    ret = Constants.IO_FIXED_LENGTH;
		} else if ( isBinCSV()) {
			ret = Constants.IO_BIN_TEXT;
		} else {
		    ret = Constants.IO_TEXT_LINE;
		}
       //System.out.println(" ~~ getFileStructure " + fileStructure + " " + ret);

		return ret;
    }



    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getDecider()
	 */
    public RecordDecider getDecider() {
        return decider;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getField(byte[], int, net.sf.JRecord.Common.FieldDetail)
	 */
    public Object getField(final byte[] record, int type, FieldDetail field) {

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
    
    public final Object formatCsvField(FieldDetail field,  int type, String value) {
        AbstractParser parser = ParserManager.getInstance().get(field.getRecord().getRecordStyle());
        String val = parser.getField(field.getPos() - 1, 
        		value, 
        		delimiter, field.getQuote());
        
        return formatField(field,  type, val);
    }
    
    
    private Object formatField(FieldDetail field,  int type, String value) {
        
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
    public byte[] setField(byte[] record, FieldDetail field, Object value)
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
    public byte[] setField(byte[] record, int type, FieldDetail field, Object value)
    throws RecordException {
        if (field.isFixedFormat()) {
            record = TypeManager.getSystemTypeManager().getType(type)
				.setField(record, field.getPos(), field, value);
        } else  {
            String font = field.getFontName();
            AbstractParser parser = ParserManager.getInstance().get(field.getRecord().getRecordStyle());

            Type typeVal = TypeManager.getSystemTypeManager().getType(type);
            String s = typeVal.formatValueForRecord(field, value.toString());
            //System.out.println(" ---> setField ~ " + delimiter + " ~ " + s + " ~ " + new String(record));
            if  (isBinCSV()) {
             	record = (new BinaryCsvParser(delimiter)).updateValue(record, field, s);
            } else {
	            String newLine = parser.setField(field.getPos() - 1, 
	            		typeVal.getFieldType(),
	            		Conversion.toString(record, font), 
	            		delimiter, field.getQuote(), s);
	
	            record = Conversion.getBytes(newLine, font);
            }
        }
        //System.out.println(" ---> setField ~ Done");
        return record;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getFieldFromName(java.lang.String)
	 */
    public FieldDetail getFieldFromName(String fieldName) {
    	FieldDetail ret = null;
    	String key = fieldName.toUpperCase();

    	if (fieldNameMap == null) {
    		int i, j, size;
    		FieldDetail fld;

    		size = 0;
    		for (i = 0; i < recordCount; i++) {
    		    size += records[i].getFieldCount();
    		}
    		size = (size * 5) / 4 + 4;

    		fieldNameMap = new HashMap<String, FieldDetail>(size);

    		for (i = 0; i < recordCount; i++) {
    			//FieldDetail[] flds = records[i].getFields();
    			for (j = 0; j < records[i].getFieldCount(); j++) {
    			    fld = records[i].getField(j);
    				fieldNameMap.put(fld.getName().toUpperCase(), fld);
    			}
    			records[i].setNumberOfFieldsAdded(0);
    		}
     	} else if (this.isBuildLayout()) {
     		int j;
       		FieldDetail fld;
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

    	if (fieldNameMap.containsKey(key)) {
    		ret = fieldNameMap.get(key);
    	}

    	return ret;
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
    		for (int i=0; i < records.length; i++) {
    			records[i].setDelimiter(delim);
    		}
    	}
		this.delimiter = delim;
	}


	/**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getAdjField(int, int)
	 */
    public FieldDetail getAdjField(int layoutIdx, int fieldIdx) {
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
	protected AbstractLayoutDetails<FieldDetail, RecordDetail> getNewLayout(
			ArrayList<RecordDetail> recordList) {
		RecordDetail[] recs = new RecordDetail[recordList.size()];
		recs = recordList.toArray(recs);
		return new LayoutDetail(getLayoutName(), recs, getDescription(),
				getLayoutType(), getRecordSep(), getEolString(),
				getFontName(), getDecider(), getFileStructure());
	}


	/**
	 * @see net.sf.JRecord.Details.BasicLayout#getNewRecord(net.sf.JRecord.Details.AbstractRecordDetail, java.util.ArrayList)
	 */
	@Override
	protected RecordDetail getNewRecord(RecordDetail record, ArrayList<FieldDetail> fields) {
		FieldDetail[] flds = new FieldDetail[fields.size()];
		RecordDetail ret;
		String selectionField = "";
		
		if (record.getSelectionField() != null) {
			selectionField = record.getSelectionField().getName();
		}

		flds = fields.toArray(flds);
		ret = new RecordDetail(record.getRecordName(),
				selectionField,
				record.getSelectionValue(),
				record.getRecordType(), record.getDelimiter(), record.getQuote(),
				record.getFontName(), flds, record.getRecordStyle());
		ret.setSelectionField(record.getSelectionField());
		return ret;
	}

}	


