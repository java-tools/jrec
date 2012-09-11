package net.sf.RecordEditor.po.def;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.Types.Type;

public class PoLayoutMgr {
	private static String poLayout =
					  "<?xml version=\"1.0\" ?>"
					+ "<RECORD RECORDNAME=\"GetText_PO\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" FILESTRUCTURE=\"" + Constants.IO_GETTEXT_PO + "\" "
						+ "STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
					+ "	<FIELDS>"
					+ "		<FIELD NAME=\"msgctxt\" POSITION=\"1\" TYPE=\"Char\"/>"
					+ "		<FIELD NAME=\"msgid\" POSITION=\"2\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"msgstr\" POSITION=\"3\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"comments\" POSITION=\"4\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"OriginalText\" POSITION=\"5\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"msgidPlural\" POSITION=\"6\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"msgstrPlural\" POSITION=\"7\" TYPE=\"" + Type.ftArrayField + "\"/>"
					+ "		<FIELD NAME=\"extractedComments\" POSITION=\"8\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"reference\" POSITION=\"9\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"flags\" POSITION=\"10\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"previousMsgctx\" POSITION=\"11\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"previousMsgId\" POSITION=\"12\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"previousMsgidPlural\" POSITION=\"13\" TYPE=\"" + Type.ftMultiLineChar + "\"/>"
					+ "		<FIELD NAME=\"fuzzy\" POSITION=\"14\" TYPE=\"" + Type.ftCheckBoxY + "\"/>"
					+ "		<FIELD NAME=\"obsolete\" POSITION=\"15\" TYPE=\"" + Type.ftCheckBoxY + "\"/>"
					+ "	</FIELDS>"
					+ "</RECORD>";

	public static final LayoutDetail PO_LAYOUT = getPoLayout();



	public static LayoutDetail getPoLayout() {
		LayoutDetail t = null;

		try {
			t = RecordEditorXmlLoader.getExternalRecord(poLayout, "PO Layout").asLayoutDetail();
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
