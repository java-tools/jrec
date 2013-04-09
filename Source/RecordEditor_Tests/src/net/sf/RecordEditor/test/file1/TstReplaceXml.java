package net.sf.RecordEditor.test.file1;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;

public class TstReplaceXml extends TestCase {
	private String TAB_CSV_LAYOUT =
					  "<?xml version=\"1.0\" ?>"
					+ "<RECORD RECORDNAME=\"XML - Build Layout\" COPYBOOK=\"\" DELIMITER=\"|\" DESCRIPTION=\"XML file, build the layout based on the files contents\" FILESTRUCTURE=\"XML_Build_Layout\" STYLE=\"0\" RECORDTYPE=\"XML\" LIST=\"Y\" QUOTE=\"'\" RecSep=\"default\" LINE_NO_FIELD_NAMES=\"1\">"
					+ "	<FIELDS>"
					+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>"
					+ "	</FIELDS>"
					+ "</RECORD>";

	private String csvLines = "<?xml version=\"1.0\" ?>"
			+ "<ExportData>"
			+ "    <DTAR020 KEYCODE-NO=\"63604808\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"170\" QTY-SOLD=\"1\" SALE-PRICE=\"4.87\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"69684558\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"280\" QTY-SOLD=\"1\" SALE-PRICE=\"19.00\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"69684558\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"280\" QTY-SOLD=\"-1\" SALE-PRICE=\"-19.00\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"69694158\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"280\" QTY-SOLD=\"1\" SALE-PRICE=\"5.01\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"62684671\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"685\" QTY-SOLD=\"1\" SALE-PRICE=\"69.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"62684671\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"685\" QTY-SOLD=\"-1\" SALE-PRICE=\"-69.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"61664713\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"335\" QTY-SOLD=\"1\" SALE-PRICE=\"17.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"61664713\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"335\" QTY-SOLD=\"-1\" SALE-PRICE=\"-17.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"61684613\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"335\" QTY-SOLD=\"1\" SALE-PRICE=\"12.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"68634752\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"410\" QTY-SOLD=\"1\" SALE-PRICE=\"8.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"60694698\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"620\" QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"60664659\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"620\" QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"60614487\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"878\" QTY-SOLD=\"1\" SALE-PRICE=\"5.95\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"68654655\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"60\" QTY-SOLD=\"1\" SALE-PRICE=\"5.08\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"69624033\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"80\" QTY-SOLD=\"1\" SALE-PRICE=\"18.19\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"60604100\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"80\" QTY-SOLD=\"1\" SALE-PRICE=\"13.30\"/>"
			+ "    <DTAR020 KEYCODE-NO=\"68674560\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"170\" QTY-SOLD=\"1\" SALE-PRICE=\"5.99\"/>"
			+ "</ExportData>";

	public void testRepl1() throws Exception {
		chkRepl1("Test1,1: ",  "66", "77");
		chkRepl1("Test1,2: ",  "66", "787");
		chkRepl1("Test1,3: ",  "80", "787");
		chkRepl1("Test1,4: ",  "80", "1");
		chkRepl1("Test1,5: ",  "0", "7");
		chkRepl1("Test1,6: ",  "0", "79");
		chkRepl1("Test1,7: ",  "40118", "12");
		chkRepl1("Test1,8: ",  "40118", "1");
		chkRepl1("Test1,9: ",  "1", "7");
		chkRepl1("Test1,10: ",  "1", "79");
		chkRepl1("Test1,11: ",  "66", "7");
		chkRepl1("Test1,12: ",  "66", "79");
		chkRepl1("Test1,13: ",  "66", "791");
	}

	public void testReplField() throws Exception {
		chkRepl2("Test2,1: ",  "1", "7");
		chkRepl2("Test2,2: ",  "1", "787");
		chkRepl2("Test2,3: ",  "80", "7");
		chkRepl2("Test2,4: ",  "80", "71");
		chkRepl2("Test2,5: ",  "80", "787");
		chkRepl2("Test2,6: ",  "40118", "7");
		chkRepl2("Test2,7: ",  "40118", "787");
		chkRepl2("Test2,8: ",  "40118", "78790");
		chkRepl2("Test2,9: ",  "40118", "7879023");
	}

	public void testReplSpecificField() throws Exception {
		chkReplFld1("Test2,1:", "1", "7", 4);
		chkReplFld1("Test2,2:", "1", "789", 4);
		chkReplFld2("Test2,3:", "1", "7", 4);
		chkReplFld2("Test2,4:", "1", "789", 4);

		chkReplFld1("Test2,5:", "80", "7", 3);
		chkReplFld1("Test2,6:", "80", "71", 3);
		chkReplFld1("Test2,7:", "80", "789", 3);
		chkReplFld1("Test2,8: ",  "40118", "7", 2);
		chkReplFld1("Test2,9: ",  "40118", "78790", 2);
		chkReplFld1("Test2,10: ",  "40118", "7879023", 2);
		chkReplFld2("Test2,11: ",  "40118", "7", 2);
		chkReplFld2("Test2,12: ",  "40118", "78790", 2);
		chkReplFld2("Test2,13: ",  "40118", "7879023", 2);
	}

	private void chkRepl1(String id, String from1, String to1) throws Exception {
		chkRepl(id, from1, from1, null, to1,  to1, null, Compare.OP_CONTAINS, -1);
	}


	private void chkRepl2(String id, String from1, String to1) throws Exception {
		chkRepl(id, from1, "=\"" + from1 + "\" ", null, to1, "=\"" + to1 + "\" ", null, Compare.OP_EQUALS, -1);
	}


	private void chkReplFld1(String id, String from1, String to1, int fld) throws Exception {
		chkRepl(id, from1, "=\"" + from1 + "\" ", null, to1, "=\"" + to1 + "\" ", null, Compare.OP_EQUALS, fld);
	}

	private void chkReplFld2(String id, String from1, String to1, int fld) throws Exception {
		chkRepl(id, from1, "=\"" + from1 + "\" ", "=\"-" + from1 + "\" ", to1, "=\"" + to1 + "\" ", "=\"-" + to1 + "\" ", Compare.OP_CONTAINS, fld);
	}

	@SuppressWarnings("rawtypes")
	private void chkRepl(String id, String from1, String from2,  String from3,
			String to1, String to2, String to3,
			int op, int fld) throws Exception {

		FileView f = getFileView(csvLines);
		AbstractLayoutDetails layout = f.getLayout();
		String s;
		int idx, count;

		FilePosition pos = new  FilePosition(0, 0, 0, fld, true, f.getRowCount());
		f.replaceAll(from1, to1, pos, true, op);

		s = Conversion.replace(new StringBuilder(csvLines), from2, to2).toString();
		if (from3 != null) {
			s = Conversion.replace(new StringBuilder(s), from3, to3).toString();
		}
		FileView g = getFileView(s);

		assertEquals(id + "Check file line count", g.getRowCount(), f.getRowCount());
		for (int i = 0; i < f.getRowCount(); i++) {
			idx = f.getLine(i).getPreferredLayoutIdx();
			assertEquals(id + " Layout Idx", g.getLine(i), idx);

			count = layout.getRecord(idx).getFieldCount();

			for (int j = 0; j < count; j++) {
				assertEquals(id + "Check Line " + i + ", " + j,
						g.getLine(i).getFieldValue(idx, j).asString(),
						f.getLine(i).getFieldValue(idx, j).asString());
			}
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private FileView getFileView(String dataLines) throws Exception {
		AbstractLayoutDetails layout = getLayout();
		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_NAME_1ST_LINE);
		ArrayList<AbstractLine> lines = new ArrayList<AbstractLine>();
		AbstractLine l;

		r.open(getFile(dataLines), layout);

		while ((l = r.read()) != null) {
			lines.add(l);
		}

		layout = r.getLayout();
		DataStoreStd<AbstractLine> ds = DataStoreStd.newStore(layout, lines);
		return new FileView("", ds, layout);

	}

	private InputStream getFile(String lines) {

		return new ByteArrayInputStream(lines.getBytes());
	}

	private LayoutDetail getLayout() throws RecordException, Exception {
		return getExternalLayout().asLayoutDetail();
	}

	private ExternalRecord getExternalLayout() throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(TAB_CSV_LAYOUT, "Csv Layout");
	}
}
