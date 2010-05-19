/*
 * This class holds a Record Layout (ie it describes one line in
 * the file).
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - remove unused field editorStatus
 */
package net.sf.JRecord.Details;

import net.sf.JRecord.Common.AbstractRecord;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Types.TypeManager;




/**
 * This class holds a Record Description. A <b>Record</b> consists of
 * one ore more <b>Fields</b> (Class FieldDetail). The class is used by
 * {@link LayoutDetail}. Each LayoutDetail holds one or more RecordDetail's 
 * 
 * <pre>
 *     LayoutDetail  - Describes a file
 *       |
 *       +----- RecordDetail (1 or More) - Describes one record in the file
 *                |
 *                +------  FieldDetail (1 or More)  - Describes one field in the file 
 * </pre>
 *
 * @param pRecordName  Record Name
 * @param pSelectionField Selection Field
 * @param pSelectionValue Selection Value
 * @param pRecordType Record Type
 * @param pDelim Record Delimiter
 * @param pQuote String Quote (for Comma / Tab Delimeted files)
 * @param pFontName fontname to be used
 * @param pFields Fields belonging to the record
 *
 *
 * @author Bruce Martin
 * @version 0.55
 */
public class RecordDetail extends BasicRecordDetail<FieldDetail, RecordDetail, AbstractChildDetails<RecordDetail>>
implements AbstractRecord, AbstractRecordDetail<FieldDetail> {

    //private static final int STATUS_EXISTS         =  1;

	private String recordName;

	private FieldDetail selectionFld = null;
	private int    selectionFieldIdx = Constants.NULL_INTEGER;
	private int    recordType;

	private String selectionField;
	private String selectionValue;
	private String delimiter;
	private int length = 0;
	private String fontName;
	private String quote;
	
	private int recordStyle;
	
	private int numberOfFieldsAdded = 0;
	//private int editorStatus = STATUS_UNKOWN;


	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pSelectionField Selection Field
	 * @param pSelectionValue Selection Value
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimeted files)
	 * @param pFontName fontname to be used
	 * @param pFields Fields belonging to the record
	 */
	public RecordDetail(final String pRecordName,
	        			final String pSelectionField,
	        			final String pSelectionValue,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final FieldDetail[] pFields,
						final int pRecordStyle
						) {
		super();

		int j;
		this.recordName = pRecordName;
		this.recordType = pRecordType;
		this.selectionField = correct(pSelectionField);
		this.selectionValue = correct(pSelectionValue);

		this.fields   = pFields;
		this.quote    = pQuote;
		this.fontName = pFontName;
		this.recordStyle = pRecordStyle;

		this.fieldCount = pFields.length;
		while (fieldCount > 0 && fields[fieldCount - 1] == null) {
		    fieldCount -= 1;
		}

		delimiter = convertFieldDelim(pDelim);

		//System.out.println("Quote 1 ==>" + pQuote + "<==");
		for (j = 0; j < fieldCount; j++) {
		    pFields[j].setRecord(this);
		}

		try {
			length = fields[fieldCount - 1].getEnd();
			if (! "".equals(selectionField)) {
				for (j = 0; j < fieldCount; j++) {
					if (selectionField.trim().equalsIgnoreCase(fields[j].getName())) {
						selectionFieldIdx = j;
						selectionFld = fields[j];
					}
					if (fields[j].getEnd() > length) {
						length = fields[j].getEnd();
					}
				}
			}
		} catch (Exception ex1) { }
		

	}


	/**
	 * if it is null then return "" else return s
	 *
	 * @param str string to test
	 *
	 * @return Corrected string
	 */
	private String correct(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

//	/**
//	 * Get all the fields in the record
//	 *
//	 * @return the fields Array
//	 *
//	 * @deprecated use getField / getFieldCount instead
//	 */
/*	public FieldDetail[] getFields() {
		return fields;
	}
*/

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#addField(net.sf.JRecord.Common.FieldDetail)
	 */
	public void addField(FieldDetail field) {

	    if (fieldCount >= fields.length) {
	        FieldDetail[] temp = fields;
	        fields = new FieldDetail[fieldCount + 5];
	        System.arraycopy(temp, 0, fields, 0, temp.length);
	        fieldCount = temp.length;
	    }
	    field.setRecord(this);
	    fields[fieldCount] = field;
	    fieldCount += 1;
	    numberOfFieldsAdded += 1;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getRecordName()
	 */
	public String getRecordName() {
		return recordName;
	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionFieldIdx()
	 */
	public int getSelectionFieldIdx() {
		return selectionFieldIdx;
	}

	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionField()
	 */
	public final FieldDetail getSelectionField() {
		return selectionFld;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#setSelectionField(net.sf.JRecord.Common.FieldDetail)
	 */
	public final void setSelectionField(FieldDetail newSelectionField) {
		this.selectionFld = newSelectionField;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionValue()
	 */
	public String getSelectionValue() {
		return selectionValue;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getRecordType()
	 */
	public int getRecordType() {
		return recordType;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getLength()
	 */
	public int getLength() {
		return length;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFontName()
	 */
    public String getFontName() {
        return fontName;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#isBinary(int)
	 */
    public boolean isBinary(int fldNum) {
        return TypeManager.getSystemTypeManager()
        		.getType(fields[fldNum].getType()).isBinary();
    }

    /**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#isNumericField(int)
	 * @deprecated use getFieldsNumericType == Type.NT_NUMBER
	 */
    public boolean isNumericField(int fldNum) {
        return TypeManager.getSystemTypeManager()
        		.getType(fields[fldNum].getType()).isNumeric();
    }



    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFieldsNumericType(int)
	 */
    public int getFieldsNumericType(int idx) {
        return TypeManager.getSystemTypeManager()
        		.getType(this.fields[idx].getType())
        		.getFieldType();
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getWidths()
	 */
    public int[] getWidths() {
        return null;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getDelimiter()
	 */
    public String getDelimiter() {
        return delimiter;
    }

    protected void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getQuote()
	 */
    public String getQuote() {
        return quote;
    }


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getRecordStyle()
	 */
	public int getRecordStyle() {
		return recordStyle;
	}


	/**
	 * @return the numberOfFieldsAdded
	 */
	protected final int getNumberOfFieldsAdded() {
		return numberOfFieldsAdded;
	}


	/**
	 * @param numberOfFieldsAdded the numberOfFieldsAdded to set
	 */
	protected final void setNumberOfFieldsAdded(int numberOfFieldsAdded) {
		this.numberOfFieldsAdded = numberOfFieldsAdded;
	}
	
	public final static String convertFieldDelim(String pDelim) {
		String delimiter = pDelim;
		if ((pDelim == null) || (pDelim.trim().equalsIgnoreCase("<tab>"))) {
			delimiter = "\t";
		} else if (pDelim.trim().equalsIgnoreCase("<space>")) {
			delimiter = " ";
		}
		return delimiter;
	}

	
	
}
