/*
 * This class holds a Record Layout (ie it describes one line in
 * the file).
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - remove unused field editorStatus
 */
package net.sf.JRecord.Details;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Types.TypeManager;
import net.sf.JRecord.detailsSelection.FieldSelectX;




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
implements AbstractRecordDetail<FieldDetail> {

    //private static final int STATUS_EXISTS         =  1;

	private String recordName;

	//private FieldDetail selectionFld = null;
	//private int    selectionFieldIdx = Constants.NULL_INTEGER;
	private int    recordType;

	//private String selectionField;
	//private String selectionValue;
	private RecordSelection recordSelection = new RecordSelection(this);

	private String delimiter;
	private int    length = 0;
	private String fontName;
	private String quote;

	private int    recordStyle;

	private int    numberOfFieldsAdded = 0;

	private int    childId=0;
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
						final int pRecordStyle,
						final int childId
						) {
		this(pRecordName, pRecordType, pDelim,
			 pQuote, pFontName, pFields, pRecordStyle, childId);

		if (!"".equals(pSelectionField)) {
			recordSelection.setRecSel(FieldSelectX.get(pSelectionField, pSelectionValue, "=", -1, getField(pSelectionField)));
		}
	}

	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimeted files)
	 * @param pFontName fontname to be used
	 * @param pFields Fields belonging to the record
	 * @param pRecordStyle Record Style
	 * @param selection RecordSelection
	 */
	public RecordDetail(final String pRecordName,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final FieldDetail[] pFields,
						final int pRecordStyle,
						final RecordSelection selection,
						final int childId
						) {
		this(pRecordName, pRecordType, pDelim,
			 pQuote, pFontName, pFields, pRecordStyle, childId);

		recordSelection = selection;
	}

	/**
	 * Create a Record
	 *
	 * @param pRecordName  Record Name
	 * @param pRecordType Record Type
	 * @param pDelim Record Delimiter
	 * @param pQuote String Quote (for Comma / Tab Delimited files)
	 * @param pFontName font name to be used
	 * @param pFields Fields belonging to the record
	 * @param pRecordStyle Record Style
	 */
	public RecordDetail(final String pRecordName,
						final int pRecordType,
						final String pDelim,
						final String pQuote,
						final String pFontName,
						final FieldDetail[] pFields,
						final int pRecordStyle,
						final int childId
						) {
		super();

		int j, l;
		this.recordName = pRecordName;
		this.recordType = pRecordType;

		this.fields   = pFields;
		this.quote    = pQuote;
		this.fontName = pFontName;
		this.recordStyle = pRecordStyle;
		this.childId = childId;

		this.fieldCount = pFields.length;
		while (fieldCount > 0 && fields[fieldCount - 1] == null) {
		    fieldCount -= 1;
		}

		delimiter = convertFieldDelim(pDelim);

		//System.out.println("Quote 1 ==>" + pQuote + "<==");
		for (j = 0; j < fieldCount; j++) {
		    pFields[j].setRecord(this);
		    l = pFields[j].getPos() + pFields[j].getLen() - 1;
		    if (pFields[j].getLen() >= 0 && length < l) {
		    	length = l;
		    }
		}
	}

//	/**
//	 * if it is null then return "" else return s
//	 *
//	 * @param str string to test
//	 *
//	 * @return Corrected string
//	 */
//	private String correct(String str) {
//		if (str == null) {
//			return "";
//		}
//		return str;
//	}


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



	/**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionFieldIdx()
	 * @deprecated use getSelectionField
	 */
	public int getSelectionFieldIdx() {
		 if (recordSelection.getElementCount() <= 0) {
			 return Constants.NULL_INTEGER;
		 }
		 return getFieldIndex(recordSelection.getFirstField().getFieldName());
	}

//
//	/**
//	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionField()
//	 */ @Deprecated
//	public final FieldDetail getSelectionField() {
//		 if (recordSelection.size() <= 0) {
//			 return null;
//		 }
//		return recordSelection.get(0).field;
//	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#setSelectionField(net.sf.JRecord.Common.FieldDetail)
	 */ @Deprecated
	public final void setSelectionField(FieldDetail newSelectionField) {

		 recordSelection.setRecSel(
				 FieldSelectX.get(newSelectionField.getName(), getSelectionValue(), "=", -1, newSelectionField));
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getSelectionValue()
	 */ @Deprecated
	public String getSelectionValue() {
		 if (recordSelection.size() <= 0) {
			 return null;
		 }
		 return recordSelection.getFirstField().getFieldValue();
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


	/**
	 * @return the recordSelection
	 */
	public RecordSelection getRecordSelection() {
		return recordSelection;
	}

	/**
	 * @return the childId
	 */
	public int getChildId() {
		return childId;
	}

	@Override
	public int getOption(int option) {
		switch (option) {
		case Options.OPT_SELECTION_PRESENT:
			return Options.getValue(recordSelection != null);
		}
		return Options.UNKNOWN;
	}
}
