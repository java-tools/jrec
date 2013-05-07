/*
 * @Author Bruce Martin
 * Created on 10/02/2007 new version 0.60
 *
 * Purpose:
 * Provides a DateType as a wrapper around an existing type
 * this allows for Character, Numeric and Binary Numeric types
 *
 */
package net.sf.RecordEditor.re.jrecord.types;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.ISizeInformation;
import net.sf.JRecord.Types.Type;

import com.zbluesoftware.java.bm.ZDateField;

/**
 * Provides a DateType as a wrapper around an existing type
 * this allows for Character, Numeric and Binary Numeric types
 *
 * @author Bruce Martin
 *
 */
public class TypeDateWrapper implements Type, ISizeInformation {

    private Type baseType;
    private SimpleDateFormat df = null;
    private String dateFormatStr;
    private final int defaultSize;

    /**
     * Implements Date as a wrapper around another Type
     * @param type base type
     * @param dateFormat format of the date
     */
    public TypeDateWrapper(final Type type, final String dateFormat) {
        super();

        baseType = type;


        dateFormatStr = dateFormat;
        if (dateFormat == null) {
        	defaultSize = 20;
        } else {
            df = new SimpleDateFormat(dateFormat);
            defaultSize = dateFormat.length();
        }
    }


    /**
     * Format a value for storing in the record, it
     * has 3 uses
     * <ol compact>
     *  <li>Format the string for storing in a record.
     *  <li>Format for storing as a String in a comma / tab delimited files.
     *  <li>Validate a value
     * </ol>
     *
     * @param field field definition
     * @param val value to be formated
     *
     * @return value value as it is store in the record
     * @throws RecordException any conversion errors
     */
    public String formatValueForRecord(IFieldDetail field, String val)
            throws RecordException {
        return val;
    }


    /**
     * Extracts a field out of a data record
     *
     * @param data record which is to have the field extracted
     * @param position position in the record
     * @param currField Field Details
     *
     * @return the request field (formated)
     */
    public Object getField(byte[] data, int position, IFieldDetail currField) {
        Object ret = baseType.getField(data, position, currField);

        if (ret != null && ! "".equals(ret.toString())) {
            try {
                String s = ZDateField.padZeros(dateFormatStr, ret.toString());
                SimpleDateFormat f = getDateFormater(currField);

                ret = f.parse(s);
//                if ("Date YMD".equals(currField.getName().trim()))
//                System.out.println(currField.getName() + " :: " + dateFormatStr
//                        + " ==>" +  o + "< -->" + ret + "< "
//                        + ret.getClass().getName());
           } catch (Exception e) {
                System.out.println("Format error > " + e.getMessage()
                        + " Value: " + ret + " Format: " + dateFormatStr);
 //               e.printStackTrace();
            }
        }

        return ret;
    }

    /**
     * Get the type of field
     * @return type of field
     */
   public int getFieldType() {
        return Type.NT_DATE;
    }

    /**
     * wether it is a binary field
     *
     * @return wether it is binary field
     */
    public boolean isBinary() {
        return baseType.isBinary();
    }


    /**
     * Sets a field to a new value in the supplied data record
     *
     * @param data record which is to be update
     * @param position position in the record
     * @param field Field Details
     * @param val new value
     *
     * @return updated record
     * @throws RecordException any error that occurs during the save
     */
    public byte[] setField(byte[] data, int position, IFieldDetail field,
            Object val) throws RecordException {

        Object o = val;
        if (o instanceof Date) {
            SimpleDateFormat f = getDateFormater(field);
            o = f.format((Date) val);
//            if ("Date YMD".equals(field.getName().trim()))
//            System.out.println(field.getName() + " ~ " + dateFormatStr
//                    + " ==>" +  val + "< -->" + o + "< "
//                    + val.getClass().getName());
//        } else {
//            System.out.println("~~>" + val + "< " + val.getClass().getName());
        }
        return baseType.setField(data, position, field, o);
    }


    /**
     * Get the date formater
     * @param field screen field
     * @return date formater
     */
    private SimpleDateFormat getDateFormater(IFieldDetail field) {

        if (df == null) {
            String s = field.getParamater();
            if (s == null || "".equals(s)) {
                s = "yyMMdd";
            }
            //System.out.print("~~ " + s + "  ");
            return new SimpleDateFormat(s);
        }
        return df;
    }


	public boolean isNumeric() {
		return baseType.isNumeric();
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Types.ISizeInformation#getNormalSize()
	 */
	@Override
	public int getNormalSize() {
		return defaultSize;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Types.Type#getDecimalChar()
	 */
	@Override
	public char getDecimalChar() {
		return '.';
	}

}
