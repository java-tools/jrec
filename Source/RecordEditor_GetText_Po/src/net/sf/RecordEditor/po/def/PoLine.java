package net.sf.RecordEditor.po.def;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.RecordEditor.po.display.MsgstrArray;

public final class PoLine extends ArrayListLine<FieldDetail, RecordDetail, LayoutDetail> {

	public PoLine() {
		super(PoLayoutMgr.PO_LAYOUT, 0, 1);
	}

	public PoLine(LayoutDetail l) {
		super(l, 0, 1);
	}

//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Details.ArrayListLine#getField(int, int)
//	 */
//	@Override
//	public Object getField(int recordIdx, int fieldIdx) {
//		if (recordIdx == 0 && fieldIdx == PoField.msgstrPlural.fieldIdx) {
//			System.out.print('-');
//		}
//		return super.getField(recordIdx, fieldIdx);
//	}


	/**
	 * @see net.sf.JRecord.Details.ArrayListLine#getField(int, int)
	 */
	@Override
	public  Object getField(int recordIdx, int fieldIdx) {
		Object o = super.getFieldRaw(recordIdx, fieldIdx);

		if (recordIdx == 0 && fieldIdx == PoField.msgstrPlural.fieldIdx) {
			return new MsgstrArray(this, fieldIdx);
		}
		return o;
	}

	public Object getRawField(int recordIdx, int fieldIdx) {
		return super.getFieldRaw(recordIdx, fieldIdx);
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractLine#getField(net.sf.JRecord.Common.FieldDetail)
	 */
	@Override
	public Object getField(IFieldDetail field) {
		if (field == null) return null;
	    return getField(preferredLayout, field.getPos() - 1);
	}


	public final Object getMsgstrPlural() {
		return super.getFieldRaw(0, PoField.msgstrPlural.fieldIdx);
	}

}
