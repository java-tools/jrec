package xCommon;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;

public class XSchemas {

	public static enum CharSetType {
		EBCDIC("cp037"),
		ASCII(Conversion.DEFAULT_ASCII_CHARSET),
		SHIFT_JIS("Shift_JIS"),
		UTF_8("UTF-8");
		
		public final String charset;
		CharSetType(String charset) {
			this.charset = charset;
		}
	}
	
	public static final XSchemas DTAR020 = new XSchemas(
			  "DTAR020",
			  "		<FIELD NAME=\"KEYCODE-NO\" POSITION=\"1\"  LENGTH=\"8\" TYPE=\"Char\" />"
			+ "		<FIELD NAME=\"STORE-NO\"   POSITION=\"9\"  LENGTH=\"2\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"DATE\"       POSITION=\"11\" LENGTH=\"4\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"DEPT-NO\"    POSITION=\"15\" LENGTH=\"2\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"QTY-SOLD\"   POSITION=\"17\" LENGTH=\"5\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
			+ "		<FIELD NAME=\"SALE-PRICE\" POSITION=\"22\" LENGTH=\"6\" DECIMAL=\"2\" TYPE=\"Mainframe Packed Decimal (comp-3)\" />"
	);

	
	public static final XSchemas DTAR021 = new XSchemas(
			  "DTAR021", 
			  "		<FIELD NAME=\"KEYCODE-NO\" POSITION=\"1\" LENGTH=\"8\" TYPE=\"Char\" COBOLNAME=\"DTAR020-KEYCODE-NO\"/>"
			+ "		<FIELD NAME=\"STORE-NO\" POSITION=\"9\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-STORE-NO\"/>"
			+ "		<FIELD NAME=\"DATE\" POSITION=\"12\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-DATE\"/>"
			+ "		<FIELD NAME=\"DEPT-NO\" POSITION=\"18\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-DEPT-NO\"/>"
			+ "		<FIELD NAME=\"QTY-SOLD\" POSITION=\"21\" LENGTH=\"5\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-QTY-SOLD\"/>"
			+ "		<FIELD NAME=\"SALE-PRICE\" POSITION=\"26\" LENGTH=\"9\" DECIMAL=\"2\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-SALE-PRICE\"/>"
	);
	
	public static final XSchemas DTAR022 = new XSchemas(
			  "DTAR022", 
			  "		<FIELD NAME=\"KEYCODE-NO\" POSITION=\"1\" LENGTH=\"8\" TYPE=\"Char\" COBOLNAME=\"DTAR020-KEYCODE-NO\"/>"
			+ "		<FIELD NAME=\"STORE-NO\" POSITION=\"9\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-STORE-NO\"/>"
			+ "		<FIELD NAME=\"DATE\" POSITION=\"12\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-DATE\"/>"
			+ "		<FIELD NAME=\"DEPT-NO\" POSITION=\"18\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-DEPT-NO\"/>"
			+ "		<FIELD NAME=\"QTY-SOLD\" POSITION=\"21\" LENGTH=\"5\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-QTY-SOLD\"/>"
			+ "		<FIELD NAME=\"SALE-PRICE\" POSITION=\"26\" LENGTH=\"9\" DECIMAL=\"2\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-SALE-PRICE\"/>"
			+ "		<FIELD NAME=\"SALE-PRICE-DOLLARS-US\" POSITION=\"27\" LENGTH=\"6\" DECIMAL=\"0\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-SALE-PRICE\"/>"
	);
	
	public static final XSchemas DTAR023 = new XSchemas(
			  "DTAR023", 
			  "		<FIELD NAME=\"KEYCODE-NO\" POSITION=\"1\" LENGTH=\"8\" TYPE=\"Char\" COBOLNAME=\"DTAR020-KEYCODE-NO\"/>"
			+ "		<FIELD NAME=\"STORE-NO\" POSITION=\"9\" LENGTH=\"3\" TYPE=\"Mainframe Packed Decimal (comp-3)\" COBOLNAME=\"DTAR020-STORE-NO\"/>"
			+ "		<FIELD NAME=\"DATE\" POSITION=\"12\" LENGTH=\"6\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-DATE\"/>"
			+ "		<FIELD NAME=\"DEPT-NO\" POSITION=\"18\" LENGTH=\"3\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-DEPT-NO\"/>"
			+ "		<FIELD NAME=\"QTY-SOLD\" POSITION=\"21\" LENGTH=\"5\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-QTY-SOLD\"/>"
			+ "		<FIELD NAME=\"SALE-PRICE\" POSITION=\"26\" LENGTH=\"9\" DECIMAL=\"2\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-SALE-PRICE\"/>"
			+ "		<FIELD NAME=\"SALE-PRICE-DOLLARS-US\" POSITION=\"27\" LENGTH=\"6\" DECIMAL=\"0\" TYPE=\"Num (Right Justified zero padded)\" COBOLNAME=\"DTAR020-SALE-PRICE\"/>"
	);
	
	public static final XSchemas CASE_1 = new XSchemas(
			  "wwCase1_sljs",
			  "        STYLE=\"0\"\n"
			+ "        RECORDTYPE=\"RecordLayout\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">\n",
			  "		<FIELD NAME=\"f1\" POSITION=\"1\" LENGTH=\"3\" TYPE=\"Char\"/>\n"
			+ "		<FIELD NAME=\"f2\" POSITION=\"4\" LENGTH=\"6\" TYPE=\"Char\"/>\n"
			+ "		<FIELD NAME=\"f3\" POSITION=\"10\" LENGTH=\"10\" TYPE=\"Char\"/>\n"
			+ "		<FIELD NAME=\"f4\" POSITION=\"20\" LENGTH=\"3\" TYPE=\"Char\"/>\n"
	);
	
	public static final XSchemas CASE_2 = new XSchemas(
			  "wwCase2_sljs",
			  "        DELIMITER=\"&lt;Tab&gt;\" "
			+ "        STYLE=\"0\""
			+ "        RECORDTYPE=\"BinaryRecord\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">",
			  "		<FIELD NAME=\"f1\" POSITION=\"1\" LENGTH=\"3\" TYPE=\"Char\"/>"
			+ "		<FIELD NAME=\"f2\" POSITION=\"4\" LENGTH=\"12\" TYPE=\"Char\"/>"
			+ "		<FIELD NAME=\"f3\" POSITION=\"16\" LENGTH=\"10\" TYPE=\"Char\"/>"
			+ "		<FIELD NAME=\"f4\" POSITION=\"26\" LENGTH=\"3\" TYPE=\"Char\"/>"
	);

	public static LayoutDetail getDtar020() throws IOException {
		return DTAR020.toLayout("CP037", Constants.IO_FIXED_LENGTH);
	}

	public static LayoutDetail getDtar020(String characterSet, int fileStructure) throws IOException {
		return DTAR020.toLayout(characterSet, fileStructure);
	}
	

	public static LayoutDetail getDtar021(String characterSet, int fileStructure) throws IOException {
		return DTAR021.toLayout(characterSet, fileStructure);
	}
	
	public static CharSetType otherCharset(CharSetType type) {
		CharSetType ret = CharSetType.ASCII;
		switch (type) {
		case ASCII: 	ret = CharSetType.EBCDIC;			break;
		case EBCDIC: 	ret = CharSetType.ASCII;			break;
		case SHIFT_JIS: ret = CharSetType.UTF_8;			break;
		case UTF_8: 	ret = CharSetType.SHIFT_JIS;		break;
		}
				
		return ret;
	}
	

	
	private final String fields, copybook, recordDef;
	
	private XSchemas(String copybook, String fields) {
		this(copybook,   
				  "     COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" STYLE=\"0\""
				+ "     RECORDTYPE=\"RecordLayout\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" "
				+ "     SYSTEMNAME=\"Mainframe\" LINE_NO_FIELD_NAMES=\"1\">",
				fields);
	}
	
	private XSchemas(String copybook, String recordDef, String fields) {
		this.fields = fields;
		this.copybook = copybook;
		this.recordDef = recordDef;
	}
	
	public String getStringSchema(String characterSet, int fileStructure) {
		return 
			  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<RECORD RECORDNAME=\"" + copybook + "\" FONTNAME=\"" + characterSet + "\" FILESTRUCTURE=\"" + fileStructure + "\""
			+ recordDef
			+ "	<FIELDS>"
			+     fields
			+ "	</FIELDS>"
			+ "</RECORD>";
	}
	
	public LayoutDetail toLayout(String characterSet, int fileStructure) throws IOException {
		RecordEditorXmlLoader load = new RecordEditorXmlLoader();
		ByteArrayInputStream is = new ByteArrayInputStream(getStringSchema(characterSet, fileStructure).getBytes());
		
		
		ExternalRecord xRec = load.loadCopyBook(is, copybook);
		
		is.close();
		
		return xRec.asLayoutDetail();
	}


}
