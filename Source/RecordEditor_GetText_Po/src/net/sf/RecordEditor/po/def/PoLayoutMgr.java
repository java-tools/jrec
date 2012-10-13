package net.sf.RecordEditor.po.def;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;

public class PoLayoutMgr {


	public static final LayoutDetail PO_LAYOUT = getPoLayout();



	public static LayoutDetail getPoLayout() {
		LayoutDetail t = null;

		try {
			t = RecordEditorXmlLoader.getExternalRecord(Common.GETTEXT_PO_LAYOUT, "PO Layout").asLayoutDetail();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}

	public static int getIndexOf(String name) {
		int ret = -1;
		if (PO_LAYOUT != null) {
			ret = PO_LAYOUT.getRecord(0).getFieldIndex(name);
		}

		return ret;
	}
}
