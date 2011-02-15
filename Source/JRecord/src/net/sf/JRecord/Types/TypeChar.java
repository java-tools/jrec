/*
 * @Author Bruce Martin
 * Created on 4/09/2005
 *
 * Purpose:
 *   his class is the interface between the raw data in the file
 * and what is to be displayed on the screen for Character Strings
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - added mehtods getFieldEnd, getPadByte, padByte + associated
 *     code changes to support NullTerminated and Null paded types
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Starting to seperate the Record package out from the RecordEditor
 *     so that it can be used seperately. So classes have been moved
 *     to the record package (ie RecordException + new Constant interface
 *   - new getFieldType method added (for sorting, JasperReports)
 */
package net.sf.JRecord.Types;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;

/**
 * Type Char
 * <p>This class is the interface between the raw data in the file
 * and what is to be displayed on the screen for Character Strings
 *
 * @author Bruce Martin
 *
 * @version 0.55
 */
public class TypeChar implements Type {

    private boolean leftJust;
    private boolean binary = false;
    
    private boolean numeric = false;



    /**
     * Type Char
     * <p>This class is the interface between the raw data in the file
     * and what is to be displayed on the screen for Character Strings.
     * <p>It also acts as the base class to other Text base Types 
     *
     * @param leftJustified left justified option
     */
    public TypeChar(final boolean leftJustified) {
        super();

        leftJust = leftJustified;
    }


    /**
     * Type Char
     * <p>This class is the interface between the raw data in the file
     * and what is to be displayed on the screen for Character Strings
     *
     * @param leftJustified left justified option
     * @param binaryField wether this is a binary field
     */
    public TypeChar(final boolean leftJustified, final boolean binaryField) {
        super();

        leftJust = leftJustified;
        binary   = binaryField;
        numeric  = binary;
    }


    /**
     * @see net.sf.JRecord.Types.Type#formatValueForRecord
     * (record.layout.DetailField, java.lang.String)
     */
    public String formatValueForRecord(FieldDetail field, String val)
            throws RecordException {
        return val;
    }


    /**
     * @see net.sf.JRecord.Types.Type#getField(byte[], int, net.sf.JRecord.Common.FieldDetail)
     */
    public Object getField(final byte[] record,
            			   final int position,
            			   final FieldDetail currField) {
        return getFieldText(record, position, currField);
    }


	/**
	 * Get the field values as raw Text
	 *
	 * @param record record which is to have the field extracted
	 * @param position position in the record
	 * @param currField Field Details
	 *
	 * @return field value (raw Text)
	 */
	protected String getFieldText(final byte[] record,
	        					  final int position,
	        					  final FieldDetail currField) {

			String s;

			s = Conversion.getString(record, position - 1,
			        getFieldEnd(currField, record),
			        currField.getFontName());

			return s;
	}


	/**
	 * Get  actual length of the field
	 *
	 * @param currField field details
	 * @param record current record
	 *
	 * @return actual length
	 */
	protected int getFieldEnd(final FieldDetail currField, final byte[] record) {
	        int ret = java.lang.Math.min(currField.getEnd(), record.length);
	        byte padByte = getPadByte(currField.getFontName());

	        while (ret > 0 && (record[ret - 1] == padByte)) {
	            ret -= 1;
	        }

	        return ret;
	}


    /**
     * @see net.sf.JRecord.Types.Type#setField(byte[], int, net.sf.JRecord.Common.FieldDetail, java.lang.Object)
     */
    public byte[] setField(byte[] record,
            			 final int position,
            			 final FieldDetail field,
            			 Object value)
            throws RecordException {
        String val  = value.toString();
		String font = field.getFontName();
		int pos = position - 1;
		int len = field.getLen();

        if (leftJust) {
		    byte[] byteVal = getBytes(val, font);
			if (val.length() >= len) {
				System.arraycopy(byteVal, 0, record, pos, len);
			} else {
				//System.out.println("::-> " + val.length() + " " + pos + " " + record.length);
				System.arraycopy(byteVal, 0, record, pos, val.length());
				//padWith(record, pos + val.length(), len - val.length(), " ", font);
				padByte(record, pos + val.length(), len - val.length(), getPadByte(font));
			}
        } else {
            copyRightJust(record, val, pos, len, " ", font);
        }

        return record;
    }


	/**
	 * pads a field with a specified character
	 *
	 * @param record record to be updated
	 * @param start where to start inserting the pad char
	 * @param len length to be padded
	 * @param padCh pad char
	 * @param font font name being used
	 */
	protected final void padWith(byte[] record,
	        			 final int start, final int len,
	        			 final String padCh,
	        			 final String font) {
		byte padByte = getBytes(padCh, font)[0];

		padByte(record, start, len, padByte);
	}


	/**
	 * get the pad byte
	 *
	 * @param font current font
	 *
	 * @return the pad byte
	 */
	protected byte getPadByte(String font) {
		return getBytes(" ", font)[0];
	}

	/**
	 * Pad the record with a particular byte
	 * @param record record to padded
	 * @param start start
	 * @param len length to be padded
	 * @param padByte byte to use
	 */
	protected final void padByte(byte[] record,
	        final int start, final int len,
	        final byte padByte) {
	    int i;
	    for (i = start; i < start + len; i++) {
	        record[i] = padByte;
	    }
	}


	/**
	 * Copies a field (right justified)
	 *
	 * @param record record to be updated
	 * @param val value to be copied
	 * @param pos field position
	 * @param len field length
	 * @param pad pad character
	 * @param font fontname to use
	 *
	 * @throws RecordException can through field to big error
	 */
	protected final void copyRightJust(byte[] record,
	        				   String val,
	        				   int pos, int len,
	        				   String pad,
	        				   String font)
					throws RecordException {

	    int l = val.length();

		if (l == len) {
			System.arraycopy(getBytes(val, font), 0, record, pos, len);
		} else if (l > len) {
		    throw new RecordException("Character Field is to big");
		} else {
			padWith(record, pos, len - val.length(), pad, font);
			System.arraycopy(getBytes(val, font), 0, record,
							 pos + len - val.length(), val.length());
		}
	}


	/**
	 * Converts a string to a byte array
	 *
	 * @param s String to be converted
	 * @param fontName font name being used
	 *
	 * @return equivalent byte array
	 */
	protected final byte[] getBytes(String s, String fontName) {
	    return Conversion.getBytes(s, fontName);
	}


    /**
     * wether it is a binary field
     *
     * @return wether it is binary field
     */
    public final boolean isBinary() {
        return binary;
    }
 


    protected final void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}


	/**
     * wether it is a binary field
     *
     * @return wether it is binary field
     */
    public final boolean isNumeric() {
        return numeric;
    }

 
    /**
     * @see net.sf.JRecord.Types.Type#getFieldType()
     */
    public int getFieldType() {
        return Type.NT_TEXT;
    }
}
