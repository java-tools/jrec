package net.sf.RecordEditor.tip.def;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;

public class TipLayoutMgr {


	public static final LayoutDetail TIP_LAYOUT = getTipLayout();



	public static LayoutDetail getTipLayout() {
		LayoutDetail t = null;

		try {
			t = RecordEditorXmlLoader.getExternalRecord(Common.TIP_LAYOUT, "PO Layout").asLayoutDetail();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}

	public static int getIndexOf(String name) {
		int ret = -1;
		if (TIP_LAYOUT != null) {
			ret = TIP_LAYOUT.getRecord(0).getFieldIndex(name);
		}

		return ret;
	}

	public static ArrayListLine<FieldDetail, RecordDetail, LayoutDetail> getLine() {
		return new ArrayListLine<FieldDetail, RecordDetail, LayoutDetail>(TIP_LAYOUT, 0, 1);
	}
}
