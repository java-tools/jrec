package net.sf.JRecord.Types.smallBin;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.TypeZoned;

public class TypeZonedEbcdicSmall extends TypeBaseXBinary {

	private static final int POSITIVE_SIGN_NYBLE = 0xC0;
	private static final int NEGATIVE_SIGN_NYBLE = 0xD0;
	private TypeZoned typeZoned = new TypeZoned();
	
	public TypeZonedEbcdicSmall() {
		super(false, false, false);
	}

	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.Types.smallBin.TypeBaseXBinary#setField(byte[], int, net.sf.JRecord.Common.IFieldDetail, java.lang.Object)
	 */
	@Override
	public byte[] setField(byte[] record, int position, IFieldDetail field, Object value) {
		if (value == null || value == CommonBits.NULL_VALUE || value instanceof Number) {
			return super.setField(record, position, field, value);
		}
		return typeZoned.setField(record, position, field, value);
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Types.TypeNum#formatValueForRecord(net.sf.JRecord.Common.IFieldDetail, java.lang.String)
	 */
	@Override
	public String formatValueForRecord(IFieldDetail field, String val) {
		return typeZoned.formatValueForRecord(field, val);
	}


	@Override
	public long asUnscaledLong(byte[] record, int position, IFieldDetail field) {
		long val = 0;
		int en = position + field.getLen() - 1;
		int d;
		for (int i = position - 1; i < en; i++) {
			d = record[i] & 0x0F;
			if (d > 9) { 
				throw new RecordException(field.getName() + ": Invalid Zoned Decimal: "
								+ Conversion.getString(record, position, en, "cp037"));
			}
			val = val * 10 + d;
		}
		int xx = record[en-1] & 0xf0;
		byte signNyble = (byte) xx;
		switch (signNyble) {
		case TypeZoned.ZONED_NEGATIVE_NYBLE_VALUE1:
		case TypeZoned.ZONED_NEGATIVE_NYBLE_VALUE2:
			val = -val;
			break;
		case TypeZoned.HIGH_NYBLE:
		case TypeZoned.ZONED_POSITIVE_NYBLE_VALUE1:
		case TypeZoned.ZONED_POSITIVE_NYBLE_VALUE2:
			break;
		default:
			throw new RecordException(field.getName() + ": Invalid Zoned Decimal: " 
						+ Conversion.getString(record, position, en, "cp037"));
		}
		return val;
	}

	@Override
	public byte[] setUnscaledLong(byte[] record, int position, IFieldDetail field, long value) {

		int en = position + field.getLen() - 2;
		int pos = position - 1;
		int signNyble = POSITIVE_SIGN_NYBLE;
		
		if (value < 0) {
			value = - value;
			signNyble = NEGATIVE_SIGN_NYBLE;
		}
		
		record[en] = (byte) ((value % 10) | signNyble);
		for (int i = en-1; i >= pos; i--) {
			value = value / 10;
			record[i] = (byte) ((value % 10) | 0xF0);	
		}
		return record;
	}

}
