/*
 * Created on 29/05/2004
 *
 * This class represents one line (or Record) in the File. It contains
 * methods to Get / update fields (for a specified layouts) get the
 * prefered layout etc
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - remove unused fields
 */
package net.sf.JRecord.Details;

import java.math.BigInteger;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.Type;

/**
 * This class represents one line (or Record) in the File. It contains
 * methods to Get / update fields (for a specified layouts) get the
 * preferred layout etc
 *
 * <p>The one important method is getFieldValue
 * 
 * <p>Creating:
 * <pre>
 *              <font color="brown"><b>Line</b></font> outLine = new Line(oLayout);
 * </pre>
 * 
 * <p>Getting a field value:
 * <pre>
 *              <font color="brown"><b>long</b></font> sku = saleRecord.getFieldValue("<font color="blue"><b>KEYCODE-NO</b></font>").asLong();
 * </pre>
 * 
 * <p>Updating a field:
 * <pre>
 *              saleRecord.getFieldValue("<font color="blue"><b>KEYCODE-NO</b></font>").set(1331);
 * </pre>
 * 
 * @author Bruce Martin
 * @version 0.55
 */
public class Line extends BasicLine<Line> {

	private static final AbstractTreeDetails<FieldDetail, RecordDetail, LayoutDetail, Line>
			NULL_TREE_DETAILS = new NullTreeDtls<FieldDetail, RecordDetail, LayoutDetail, AbstractChildDetails<RecordDetail>, Line>();

	private static LineProvider<LayoutDetail> defaultProvider = new DefaultLineProvider();


	private byte[] data;


	private boolean newRecord   = false;

	/**
	 * Define a Null record
	 *
	 * @param group - Group of Record Layouts
	 */
	public Line(final LayoutDetail group) {
		super(defaultProvider, group, NULL_TREE_DETAILS);

		newRecord = true;
		data = NULL_RECORD;
	}



	/**
	 * Define a String record
	 *
	 * @param group - Group of Record Layouts
	 * @param rec - record
	 */
	public Line(final LayoutDetail group, final String rec) {
		super(defaultProvider, group, NULL_TREE_DETAILS);

		data = Conversion.getBytes(rec, group.getFontName());
		
		init();
	}


	/**
	 * Define a Byte record
	 *
	 * @param group - Group of Record Layouts
	 * @param rec - record
	 */
	public Line(final LayoutDetail group, final byte[] rec) {
		super(defaultProvider, group, NULL_TREE_DETAILS);

		data = rec;
		
		init();
	}


	/**
	 * Create  a line from a selected part of a supplied byte array
	 *
	 * @param group current group of records
	 * @param buf input buffer
	 * @param start start of the record (in the input buffer)
	 * @param recordLen Record (or Line length)
	 */
	public Line(final LayoutDetail group, final byte[] buf,
	        	final int start, final int recordLen) {
		super(defaultProvider, group, NULL_TREE_DETAILS);

		data = new byte[Math.max(0, recordLen)];

		if (recordLen > 0) {
		    System.arraycopy(buf, start, data, 0, recordLen);
		}
		
		init();
	}

	
	/**
	 *   This method completely replaces a lines value. It is used to determine
	 * a records prefered record layout
	 *
	 * @param rec buffer holding the record
	 * @param start Start of the record
	 * @param len length of the record
	 */
	public final void replace(final byte[] rec, final int start, final int len) {

		System.arraycopy(rec, start, data, 0,
					java.lang.Math.min(len, data.length));
		preferredLayoutAlt = Constants.NULL_INTEGER;
	}





	/**
	 * Get the field values as raw Text
	 *
	 * @param recordIdx Index of the current layout used to retrieve the field
	 * @param fieldIdx Index of the current field
	 *
	 * @return field value (raw Text)
	 */
	public String getFieldText(final int recordIdx, final int fieldIdx) {

		try {
			if (fieldIdx == Constants.FULL_LINE) {
				return getFullLine();
			}

		    FieldDetail field = layout.getField(recordIdx, fieldIdx);
			return layout.getField(getLineData(),
			        				Type.ftChar,
			        				field).toString();

		} catch (final Exception ex) {
			return "";
		}
	}

	/**
	 * Get the full line as text
	 *
	 * @return line as text
	 */
	public String getFullLine() {
	    String s;
	    byte[] d = getLineData();

	    if ("".equals(layout.getFontName())) {
	        s = new String(d);
	    } else {
	        try {
	            s = new String(d, layout.getFontName());
	        } catch (Exception e) {
	            s = new String(d);
            }
	    }

	    return s;
	}





	public byte[] getFieldBytes(final int recordIdx, final int fieldIdx) {
	    FieldDetail field = layout.getField(recordIdx, fieldIdx);
	    int len = field.getLen();
	    if (field.getType() == Type.ftCharRestOfRecord) {
	    	len = getLineData().length - field.getPos();
	    }

	    return getData(field.getPos(), len);
	}





	/**
	 * Get the Prefered Record Layou Index for this record
	 *
	 * @return Index of the Record Layout based on the Values
	 */
	@Override
	public int getPreferredLayoutIdx() {
		int ret = preferredLayout;
		
		if (ret == Constants.NULL_INTEGER) {
			ret = getPreferredLayoutIdxAlt();
			
			if (ret < 0) {				
				for (int i=0; i< layout.getRecordCount(); i++) {
					if (getLineData().length == layout.getRecord(i).getLength()) {
						ret = i;
						preferredLayout = i;
						break;
					}
				}
			}
		}
		
		return ret;
	}
	


	/**
	 * Adjust the record length if required
	 *
	 * @param field field being updated
	 * @param recordIdx current record layout index
	 */
	private void adjustLengthIfNecessary(final FieldDetail field, final int recordIdx) {

		if (field.getEnd() > getLineData().length) {
			adjustLength(recordIdx);
		}
	}


	/**
	 * Adjust the record length if required
	 *
	 * @param field field being updated
	 */
	private void adjustLengthIfNecessary(final FieldDetail field) {

		if (field.getEnd() > data.length) {
		    newRecord(field.getEnd());
		}
	}


	/**
	 * Adjust the record length
	 *
	 * @param recordIdx Layout index
	 */
	private void adjustLength(final int recordIdx) {

		RecordDetail pref = layout.getRecord(recordIdx);
		int newSize = pref.getLength(); //field.getEnd();

		if (newSize != getLineData().length) {
			if (newRecord) {
				this.writeLayout = recordIdx;
				this.preferredLayoutAlt = recordIdx;
				this.preferredLayout = recordIdx;
			}

			newRecord(newSize);
		}
	}


	/**
	 * Resize the record
	 *
	 * @param newSize new record size
	 */
	private void newRecord(int newSize) {
		byte[] sep = layout.getRecordSep();
		byte[] rec = new byte[newSize];
		//byte[] d = getLineData();

		System.arraycopy(data, 0, rec, 0, data.length);

		if ((layout.getLayoutType() == Constants.rtGroupOfBinaryRecords)
				&& sep != null && sep.length > 0) {
			System.arraycopy(sep, 0, rec, newSize - sep.length, sep.length);
		}

		data = rec;
	}

	public byte[] getData(int start, int len) {
	    byte[] temp = getData();
	    byte[] ret;
	    int tempLen = Math.min(len, temp.length - start + 1);

	    if (temp.length < start || tempLen < 1) {
	        return null;
	    }

	    ret = new byte[tempLen];
	    System.arraycopy(temp, start - 1, ret, 0, tempLen);

	    return ret;
	}


	/**
	 * @return Returns the record.
	 */
	public byte[] getData() {

		if (newRecord && (writeLayout != Constants.NULL_INTEGER)) {
			adjustLength(writeLayout);
		}
		return getLineData();
	}
	
	protected byte[] getLineData() {
		return data;
	}

	protected void clearData() {
		data = NULL_RECORD;
	}
	
	public final void setData(String newVal) {
	    data = Conversion.getBytes(newVal, layout.getFontName());
	}

	/**
	 * stractLine#setData(byte[])
	 */
	@Override
	public void setData(byte[] newVal) {
		if (layout.isFixedLength() && newVal.length != layout.getMaximumRecordLength()) {
			if (data == null || data == NULL_RECORD || data.length != layout.getMaximumRecordLength()) {
				data = new byte[layout.getMaximumRecordLength()];
			}
			System.arraycopy(newVal, 0, data, 0, Math.min(data.length, newVal.length));
		} else {
			data = newVal;
		}
	}
	
	protected void setDataRaw(byte[] newVal) {
		data = newVal;
	}



	/**
	 * @see java.lang.Object#clone()
	 */
	public final Object clone() {

		return lineProvider.getLine(layout, getLineData().clone());
	}



    /**
     * Get a fields value
     *
     * @param field field to retrieve
     *
     * @return fields Value
     */
    public Object getField(FieldDetail field) {
        return layout.getField(getLineData(), field.getType(), field);
    }



 

    /**
     * Set a fields value
     *
     * @param field field to retrieve
     * @param value value to set the field to
     *
     * @throws RecordException any error that occurs
     */
    public void setField(FieldDetail field, Object value)
    throws RecordException {

        adjustLengthIfNecessary(field);
        data = layout.setField(data, field, value);
    }

 
    /**
     * Update field without appling any formatting
     * 
     * @param recordIdx record layout
	 * @param fieldIdx field number in the record
	 * @param value new value
	 *
	 * @throws RecordException any error that occurs during the save
     */
    public void setFieldText(final int recordIdx, final int fieldIdx, String value)
    throws RecordException {

        FieldDetail field = layout.getField(recordIdx, fieldIdx);

        adjustLengthIfNecessary(field);
        data = layout.setField(data, Type.ftChar, field, value);
    }

    /**
     * Set a field to a Hex value
     * @param recordIdx record index
     * @param fieldIdx field index
     * @param val hex value
     */
	public String setFieldHex(final int recordIdx, final int fieldIdx,
	        String val) throws RecordException {
	    FieldDetail field = layout.getField(recordIdx, fieldIdx);
	    String ret = null;

        adjustLengthIfNecessary(field, recordIdx);

        try {
            int i, j;
            BigInteger value = new BigInteger(val, Type.BASE_16);
            byte[] bytes = value.toByteArray();

            j = field.getEnd() - 1;
            for (i = bytes.length - 1; i >= 0 && j >= field.getPos() - 1; i--) {
                data[j--] = bytes[i];
            }
            for (i = j; i >= field.getPos() - 1; i--) {
                data[i] = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RecordException("Error saving Hex value: " + e.getMessage());
        }
        return ret;
	}
}