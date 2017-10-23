package edit.open;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import utils.XTstCopybookInterface;

public class TstAskForFont {

	private static final String CASE1_SHIFT_JIS = "case1_ShiftJis";
	private static final String CASE1_ANY       = "case1_Any";
	private static final String CASE1_ANY_FIXED = "case1_AnyFixed";

	private static final String CASE2_SHIFT_JIS = "case2_ShiftJis";
	private static final String CASE2_ANY       = "case2_Any";
	private static final String CASE2_ANY_FIXED = "case2_AnyFixed";


	@Test
	public void test() throws IOException {
		fail("Not yet implemented");
		
		XTstOpenFileEditPnl.TstDBSelection tst = new XTstOpenFileEditPnl.TstDBSelection(
				createCopybookLoader(), "");
	}
	
	
	private CopyBookInterface createCopybookLoader() throws IOException {
		ArrayList<ExternalRecord> records = new ArrayList<ExternalRecord>(6);
		
		records.add(case1ExternalRec(CASE1_SHIFT_JIS, Constants.IO_UNICODE_TEXT));		
		records.add(case1ExternalRec(CASE1_ANY,       Constants.IO_TEXT_CHAR_ENTER_FONT));
		records.add(case1ExternalRec(CASE1_ANY_FIXED, Constants.IO_FIXED_CHAR_ENTER_FONT));
		
		records.add(case1ExternalRec(CASE2_SHIFT_JIS, Constants.IO_BIN_TEXT));		
		records.add(case1ExternalRec(CASE2_ANY,       Constants.IO_TEXT_BYTE_ENTER_FONT));
		records.add(case1ExternalRec(CASE2_ANY_FIXED, Constants.IO_FIXED_BYTE_ENTER_FONT));
		
		return new XTstCopybookInterface(records);
	}
	
	private ExternalRecord case1ExternalRec(String name, int fileStructure) throws IOException {
		String s 
					= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<RECORD RECORDNAME=\"" + name + "\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\""
					+ "        FONTNAME=\"Shift_JIS\" FILESTRUCTURE=\"" + fileStructure +"\"\n"
					+ "        STYLE=\"0\" RECORDTYPE=\"BinaryRecord\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"AsianTst\" LINE_NO_FIELD_NAMES=\"1\">\n"
					+ "	<FIELDS>\n"
					+ "		<FIELD NAME=\"f1\" POSITION=\"1\" LENGTH=\"3\" TYPE=\"Char\"/>\n"
					+ "		<FIELD NAME=\"f2\" POSITION=\"4\" LENGTH=\"6\" TYPE=\"Char\"/>\n"
					+ "		<FIELD NAME=\"f3\" POSITION=\"10\" LENGTH=\"10\" TYPE=\"Char\"/>\n"
					+ "		<FIELD NAME=\"f4\" POSITION=\"20\" LENGTH=\"3\" TYPE=\"Char\"/>\n"
					+ "	</FIELDS>\n"
					+ "</RECORD>\n";
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
		
		return loader.loadCopyBook(new ByteArrayInputStream(s.getBytes()), name);
	}


	
	private ExternalRecord case2ExternalRec(String name, int fileStructure) throws IOException {
		String s 
					= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<RECORD RECORDNAME=\"" + name + "\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\""
					+ "        FONTNAME=\"Shift_JIS\" FILESTRUCTURE=\"" + fileStructure +"\"\n"
					+ "        STYLE=\"0\" RECORDTYPE=\"BinaryRecord\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\" SYSTEMNAME=\"AsianTst\" LINE_NO_FIELD_NAMES=\"1\">\n"
					+ "	<FIELDS>\n"
					+ "		<FIELD NAME=\"f1\" POSITION=\"1\" LENGTH=\"3\" TYPE=\"Char\"/>\n"
					+ "		<FIELD NAME=\"f2\" POSITION=\"4\" LENGTH=\"12\" TYPE=\"Char\"/>\n"
					+ "		<FIELD NAME=\"f3\" POSITION=\"16\" LENGTH=\"10\" TYPE=\"Char\"/>\n"
					+ "		<FIELD NAME=\"f4\" POSITION=\"26\" LENGTH=\"3\" TYPE=\"Char\"/>\n"
					+ "	</FIELDS>\n"
					+ "</RECORD>\n";
		RecordEditorXmlLoader loader = new RecordEditorXmlLoader();
		
		return loader.loadCopyBook(new ByteArrayInputStream(s.getBytes()), name);
	}


}
