package net.sf.RecordEditor.po.def;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.common.Common;

public class PoLayoutMgr {


	public static final LayoutDetail PO_LAYOUT = getPoLayout();
	private static ExternalRecord poLayout = null;


	public static LayoutDetail getPoLayout() {
		LayoutDetail t = null;

		try {
//			long time = System.currentTimeMillis();
			if (poLayout == null) {
				poLayout = RecordEditorXmlLoader.getExternalRecord(Common.GETTEXT_PO_LAYOUT, "PO Layout");
			}
			t = poLayout.asLayoutDetail();
//			System.out.println("Load layout: " + (((double) (System.currentTimeMillis()-time)) / 1000));
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
