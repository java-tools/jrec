package net.sf.JRecord.Details;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.JRecord.Common.AbstractFieldValue;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.Type;

/**
 * Reference to one field in a line.
 * It allows the user to get / set the field value using either simple types (int, double etc) or
 * as a Object.
 *
 * <p>Getting a field value:
 * <pre>
 * 	            long sku = saleRecord.getFieldValue("<font color="blue"><b>KEYCODE-NO</b></font>").asLong();
 * </pre>
 *
 * <p>Updating a field:
 * <pre>
 * 	            saleRecord.getFieldValue("<font color="blue"><b>KEYCODE-NO</b></font>").set(1331);
 * </pre>

 * @author Bruce Martin
 *
 */
public final class FieldValue implements AbstractFieldValue {

	private AbstractLine theLine;
	private IFieldDetail field;
	private int recordNum = -1;
	private int fieldNum;;

	/**
	 * Create a field value
	 *
	 * @param line line the field value belongs to
	 * @param fieldDetails Field Description
	 */
	public FieldValue(AbstractLine line, IFieldDetail fieldDetails) {
		theLine = line;
		field = fieldDetails;
		recordNum = -1;
	}

	/**
	 * Create a field value (using Record / Field Index's)
	 *
	 * @param line line the field value belongs to
	 * @param recordIndex record index of the field
	 * @param fieldIndex field index of the field
	 */
	public FieldValue(AbstractLine line, int recordIndex, int fieldIndex) {
		theLine = line;
		recordNum = recordIndex;
		fieldNum = fieldIndex;
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asBigDecimal()
	 */
	@Override
	public BigDecimal asBigDecimal() {
		Object ret = getValue();

		if (ret == null) {
			return null;
		} else if (ret instanceof BigDecimal) {
			return (BigDecimal) ret;
		} else {
			return new BigDecimal(ret.toString());
		}
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asBigInteger()
	 */
	@Override
	public BigInteger asBigInteger() {
		Object ret = getValue();

		if (ret == null) {
			return null;
		} else if (ret instanceof BigInteger) {
			return (BigInteger) ret;
		} else {
			return new BigInteger(ret.toString());
		}
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asDouble()
	 */
	@Override
	public double asDouble() {
		Object ret = getValue();

		if (ret == null) {
			return 0;
		} else if (ret instanceof Number) {
			return ((Number) ret).doubleValue();
		} else {
			return Double.parseDouble(ret.toString());
		}
	}


	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asFloat()
	 */
	@Override
	public float asFloat() {
		Object ret = getValue();

		if (ret == null) {
			return 0;
		} else if (ret instanceof Number) {
			return ((Number) ret).floatValue();
		} else {
			return Float.parseFloat(ret.toString());
		}
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asLong()
	 */
	@Override
	public long asLong() {
		Object ret = getValue();

		if (ret == null) {
			return 0;
		} else if (ret instanceof Number) {
			return ((Number) ret).longValue();
		} else {
			return Long.parseLong(ret.toString());
		}
	}


	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asInt()
	 */
	@Override
	public int asInt() {
		return (int) asLong();
	}



	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asBoolean()
	 */
	@Override
	public boolean asBoolean() {
		Object ret = getValue();

		if (ret == null) {
			return false;
		} else if (ret instanceof Boolean) {
			return ((Boolean) ret).booleanValue();
		} else {
			return Boolean.parseBoolean(ret.toString());
		}
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return asString();
	}


	@Override
	public String asString() {
		Object ret = getValue();

		if (ret == null) {
			return "";
		}
		return ret.toString();
	}

	/**
	 * Get The fields value
	 * @return fields value
	 */
	private Object getValue() {
		if (recordNum >= 0) {
			return theLine.getField(recordNum, fieldNum);
		}
		if (field == null) {
			return null;
		}
		return theLine.getField(field);
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#asHex()
	 */
	@Override
	public String asHex() {
		IFieldDetail fld = field;
		if (recordNum >= 0) {
			fld = theLine.getLayout().getField(recordNum, fieldNum);
		}
		return theLine.getLayout().getField(theLine.getData(),
				Type.ftHex,
				fld).toString();
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#set(boolean)
	 */
	@Override
	public void set(boolean value) throws RecordException {
		set(Boolean.valueOf(value));
	}


	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#set(double)
	 */
	@Override
	public void set(double value) throws RecordException {
		set(Double.valueOf(value));
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#set(float)
	 */
	@Override
	public void set(float value) throws RecordException {
		set(Float.valueOf(value));
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#set(long)
	 */
	@Override
	public void set(long value) throws RecordException {
		set(Long.valueOf(value));
	}

	/**
	 * @see net.sf.JRecord.Common.AbstractFieldValue#set(java.lang.Object)
	 */
	@Override
	public void set(Object value) throws RecordException {
		if (recordNum >= 0) {
			theLine.setField(recordNum, fieldNum, value);
		}
		theLine.setField(field, value);
	}

}
