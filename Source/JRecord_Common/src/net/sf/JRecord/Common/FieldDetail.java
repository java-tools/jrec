/*
 * Created on 7/05/2004
 *
 * This class stores the description of one field in a record.
 */
package net.sf.JRecord.Common;

//import net.sf.JRecord.Details.RecordDetail;


/**
 * This class stores the description of one field in a record (or Line).
 * It is used by the {@link RecordDetail} class 
 *  
 * <pre>
 *     LayoutDetail  - Describes a file
 *       |
 *       +----- RecordDetail (1 or More) - Describes one record in the file
 *                |
 *                +------  FieldDetail (1 or More)  - Describes one field in the file 
 * </pre>
 *
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class FieldDetail {

	private static final AbstractRecord DEFAULT_RECORD = new AbstractRecord() {

		/**
		 * @see net.sf.JRecord.Common.AbstractRecord#getParentRecordIndex()
		 */
		public int getParentRecordIndex() {
			return 0;
		}

		/**
		 * @see net.sf.JRecord.Common.AbstractRecord#getQuote()
		 */
		public String getQuote() {
			return null;
		}

		/**
		 * @see net.sf.JRecord.Common.AbstractRecord#getRecordStyle()
		 */
		public int getRecordStyle() {
			return 0;
		}
		
		public int getSourceIndex() {
			return 0;
		}
		
	};
	private int pos;
	private int len;
	private int end;
	private String name;
	private final String description;
	private int type;
	private final int decimal;
	private final String fontName;
	private final int format;
	private final String paramater;
	//private String quote;
	private AbstractRecord record = DEFAULT_RECORD;
	private Object defaultValue = null;


	/**
	 * Create a field definition
	 *
	 * @param pName field name
	 * @param pDescription field description
	 * @param pType field type
	 * @param pDecimal number of decimal places
	 * @param pFont fontname
	 * @param pFormat screen format id
	 * @param pParamater Field paramater
	 */
	public FieldDetail(final String pName,
	        		   final String pDescription,
	        		   final int pType,
	        		   final int pDecimal,
	        		   final String pFont,
					   final int pFormat,
					   final String pParamater) {

		name        = pName;
		type        = pType;
		decimal     = pDecimal;
		fontName    = pFont;
		format      = pFormat;
		paramater   = pParamater;

		if (pDescription == null) {
		    description = "";
		} else {
		    description = pDescription;
		}
	}


	/**
	 * Sets the position and length
	 *
	 * @param pPosition Fields Position
	 * @param pLength Fields Length
	 */
	public final FieldDetail setPosLen(final int pPosition, final int pLength) {
		pos = pPosition;
		len = pLength;
		end = pos + len - 1;

		return this;
	}


	/**
	 * Set the position
	 *
	 * @param pPosition position
	 */
	public final FieldDetail setPosOnly(final int pPosition) {
		pos = pPosition;
		len = Constants.NULL_INTEGER;
		end = Constants.NULL_INTEGER;

		return this;
	}



	/**
	 * get the number of places after the decimal point
	 *
	 * @return get the decimal position
	 */
	public final int getDecimal() {
		return decimal;
	}


	/**
	 * get the field length
	 *
	 * @return the field length
	 */
	public int getLen() {
		return len;
	}


	/**
	 * get the field name
	 *
	 * @return the field name
	 */
	public String getName() {
		return name;
	}


	/**
	 * get the starting position of the field in the record
	 *
	 * @return the position of the field
	 */
	public int getPos() {
		return pos;
	}


	/**
	 * Get the Field Type Identifier
	 *
	 * @return the field type
	 */
	public int getType() {
		return type;
	}


	/**
	 * get the Field's Description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the end position of the field in the record
	 *
	 * @return field end position
	 */
	public int getEnd() {
		return end;
	}


	/**
	 * is it a fixed fixed format record
	 *
	 * @return wether it is a fixed format field
	 */
	public boolean isFixedFormat() {
		return end != Constants.NULL_INTEGER;
	}
  

	/**
	 * Get the fields fontname
	 *
     * @return Returns the fontName.
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * get the fields format Identifier
     *
     * @return field format
     */
    public int getFormat() {
		return format;
	}

	/**
	 * get field's user parameter details. This is the parameter
	 * entered against the field in the RecordLayout definition
	 *
	 * @return field parameter details
	 */
    public String getParamater() {
		return paramater;
	}

    /**
     * get the Quote character
     * @return Quote
     */
    public String getQuote() {
        return record.getQuote();
    }


	/**
	 * @return the record
	 */
	public AbstractRecord getRecord() {
		return record;
	}


	/**
	 * @param record the record to set
	 */
	public void setRecord(AbstractRecord record) {
		this.record = record;
	}
	
	public void setNameType(String newName, int newType) {
		this.name = newName;
		this.type = newType;
	}


	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}


	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
 }
