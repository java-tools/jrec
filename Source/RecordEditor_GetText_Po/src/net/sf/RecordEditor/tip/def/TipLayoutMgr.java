package net.sf.RecordEditor.tip.def;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.ArrayListLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.Types.Type;

public class TipLayoutMgr {
	private static String tipLayout =
					   "<?xml version=\"1.0\" ?>"
					 + "<RECORD RECORDNAME=\"TipDetails\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"Default\""
							  + " STYLE=\"0\" RECORDTYPE=\"" + Constants.IO_TIP + "\" "
							  + " LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
					 + "	<FIELDS>"
					 + "		<FIELD NAME=\"name\" POSITION=\"1\" TYPE=\"Char\"/>"
					 + "		<FIELD NAME=\"description\" POSITION=\"2\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					 + "	</FIELDS>"
					 + "</RECORD>";

	public static final LayoutDetail TIP_LAYOUT = getTipLayout();



	public static LayoutDetail getTipLayout() {
		LayoutDetail t = null;

		try {
			t = RecordEditorXmlLoader.getExternalRecord(tipLayout, "PO Layout").asLayoutDetail();
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
		return new ArrayListLine<FieldDetail, RecordDetail, LayoutDetail>(TIP_LAYOUT, 0);
	}
}
