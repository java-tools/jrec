package net.sf.RecordEditor.test.file1;


import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.test.TstConstants;


public class TstReplace1 extends TestCase {
	private String TAB_CSV_LAYOUT = TstConstants.TAB_CSV_LAYOUT;

	private String[] csvLines = {
			"KEYCODE-NO	STORE-NO	DATE	DEPT-NO	QTY-SOLD	SALE-PRICE",
			"63604808	20	40118	170	1	4.87",
			"69684558	20	40118	280	1	19.00",
			"69684558	20	40118	280	-1	-19.00",
			"69694158	20	40118	280	1	5.01",
			"62684671	20	-40118	685	1	69.99",
			"62684671	20	40118	685	-1	-69.99",
			"61664713	59	40118	335	1	17.99",
			"61664713	59	40118	335	-1	-17.99",
			"61684613	59	40118	335	1	12.99",
			"68634752	59	40118	410	1	8.99",
			"60694698	59	40118	620	1	3.99",
			"60664659	59	40118	620	1	3.99",
			"60614487	59	40118	878	1	5.95",
			"68654655	166	-40118	60	1	5.08",
			"69624033	166	40118	80	1	18.19",
			"60604100	166	40118	80	1	13.30",
			"68674560	166	40118	170	1	5.99",
	};

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
		chkRepl(id, from1, from1, null, to1,  to1, null, Compare.OP_CONTAINS, FilePosition.ALL_FIELDS_IDX);
	}


	private void chkRepl2(String id, String from1, String to1) throws Exception {
		chkRepl(id, from1, "\t" + from1 + "\t", null, to1, "\t" + to1 + "\t", null,
				Compare.OP_EQUALS, FilePosition.ALL_FIELDS_IDX);
	}


	private void chkReplFld1(String id, String from1, String to1, int fld) throws Exception {
		chkRepl(id, from1, "\t" + from1 + "\t", null, to1, "\t" + to1 + "\t", null, Compare.OP_EQUALS, fld);
	}

	private void chkReplFld2(String id, String from1, String to1, int fld) throws Exception {
		chkRepl(id, from1, "\t" + from1 + "\t", "\t-" + from1 + "\t", to1, "\t" + to1 + "\t", "\t-" + to1 + "\t", Compare.OP_CONTAINS, fld);
	}

	@SuppressWarnings({ "rawtypes" })
	private void chkRepl(String id, String from1, String from2,  String from3,
			String to1, String to2, String to3,
			int op, int fld) throws Exception {

		FileView f = TstConstants.readFileView(getLayout(), csvLines, Constants.IO_NAME_1ST_LINE);
		String s, t;

		FilePosition pos = new  FilePosition(0, 0, 0, fld, true, f.getRowCount());
		f.replaceAll(from1, to1, pos, true, op);

		assertEquals(id + "Check file line count", csvLines.length - 1, f.getRowCount());
		for (int i = 1; i < csvLines.length; i++) {
			t = f.getLine(i-1).getFullLine();
			s = Conversion.replace(new StringBuilder(csvLines[i]), from2, to2).toString();
			if (from3 != null) {
				s = Conversion.replace(new StringBuilder(s), from3, to3).toString();
			}

			if (! s.equals(t)) {
				System.out.println(i + "\t" + s);
				System.out.println("\t" + t);
			}
			assertEquals(id + "Check Line " + i, s, t);
		}
	}


//	private InputStream getFile(String[] lines) {
//		return TstConstants.getFile(lines);
//	}

	private LayoutDetail getLayout() throws RecordException, Exception {
		return getExternalLayout().asLayoutDetail();
	}

	private ExternalRecord getExternalLayout() throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(TAB_CSV_LAYOUT, "Csv Layout");
	}
}
