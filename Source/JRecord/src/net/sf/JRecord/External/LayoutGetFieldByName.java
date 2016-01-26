package net.sf.JRecord.External;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.IGetFieldByName;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;

public final class LayoutGetFieldByName implements IGetFieldByName {
   	final LayoutDetail layout;
	final RecordDetail rec;

	public LayoutGetFieldByName(LayoutDetail l, RecordDetail rec) {
		super();
		this.rec = rec;
		this.layout = l;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.IGetFieldByName#getField(java.lang.String)
	 */
	@Override
	public IFieldDetail getField(String fieldName) {
		IFieldDetail ret = rec.getField(fieldName);
		if (ret == null) {
			ret = layout.getFieldFromName(fieldName);
		}
		return ret;
	}	

}
