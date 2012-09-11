package net.sf.RecordEditor.po.def;

import net.sf.JRecord.Common.FieldDetail;

import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.RecordEditor.po.display.MsgstrArray;

public final class PoLine extends ArrayListLine<FieldDetail, RecordDetail, LayoutDetail> {

	public PoLine() {
		super(PoLayoutMgr.PO_LAYOUT, 0);
	}

	public PoLine(LayoutDetail l) {
		super(l, 0);
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

	/**
	 * @see net.sf.JRecord.Details.AbstractLine#getField(net.sf.JRecord.Common.FieldDetail)
	 */
	@Override
	public Object getField(FieldDetail field) {
		if (field == null) return null;
	    return getField(preferredLayout, field.getPos());
	}


	public final Object getMsgstrPlural() {
		return super.getFieldRaw(0, PoField.msgstrPlural.fieldIdx);
	}

}
