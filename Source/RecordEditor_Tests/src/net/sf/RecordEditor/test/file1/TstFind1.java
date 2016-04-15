package net.sf.RecordEditor.test.file1;


import java.util.ArrayList;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.test.TstConstants;


public class TstFind1 extends TestCase {


	private String[] resultStart60 = {
			"60694698	59	40118	620	1	3.99",
			"60664659	59	40118	620	1	3.99",
			"60614487	59	40118	878	1	5.95",
			"60604100	166	40118	80	1	13.30",
	};

	private String[] resultTextStore20 = {
			"63604808	20	40118	170	1	4.87",
			"69684558	20	40118	280	1	19.00",
			"69684558	20	40118	280	-1	-19.00",
			"69694158	20	40118	280	1	5.01",
			"62684671	20	-40118	685	1	69.99",
			"62684671	20	40118	685	-1	-69.99",
	};
	private String[] resultTextStore20166 = {
			"63604808	20	40118	170	1	4.87",
			"69684558	20	40118	280	1	19.00",
			"69684558	20	40118	280	-1	-19.00",
			"69694158	20	40118	280	1	5.01",
			"62684671	20	-40118	685	1	69.99",
			"62684671	20	40118	685	-1	-69.99",
			"68654655	166	-40118	60	1	5.08",
			"69624033	166	40118	80	1	18.19",
			"60604100	166	40118	80	1	13.30",
			"68674560	166	40118	170	1	5.99",
	};
	private String[] resultTextGT500 = {
			"69694158	20	40118	280	1	5.01",
			"62684671	20	-40118	685	1	69.99",
			"68634752	59	40118	410	1	8.99",
			"60614487	59	40118	878	1	5.95",
			"68654655	166	-40118	60	1	5.08",
			"68674560	166	40118	170	1	5.99",
	};
	private String[] resultTextGT501 = {
			"62684671	20	-40118	685	1	69.99",
			"68634752	59	40118	410	1	8.99",
			"60614487	59	40118	878	1	5.95",
			"68654655	166	-40118	60	1	5.08",
			"68674560	166	40118	170	1	5.99",
	};
	private String[] resultLessThanZero = {
			"69684558	20	40118	280	-1	-19.00",
			"62684671	20	40118	685	-1	-69.99",
			"61664713	59	40118	335	-1	-17.99",
	};
	private String[] resultLessThanM1799 = {
			"69684558	20	40118	280	-1	-19.00",
			"62684671	20	40118	685	-1	-69.99",
	};
	private String[] resultGT1798 = {
			"69684558	20	40118	280	1	19.00",
			"62684671	20	-40118	685	1	69.99",
			"61664713	59	40118	335	1	17.99",
			"69624033	166	40118	80	1	18.19",
	};
	private String[] resultGT1799 = {
			"69684558	20	40118	280	1	19.00",
			"62684671	20	-40118	685	1	69.99",
			"69624033	166	40118	80	1	18.19",
	};

	private String[] resultTextLT4 = {
			"69684558	20	40118	280	1	19.00",
			"69684558	20	40118	280	-1	-19.00",
			"62684671	20	40118	685	-1	-69.99",
			"61664713	59	40118	335	1	17.99",
			"61664713	59	40118	335	-1	-17.99",
			"61684613	59	40118	335	1	12.99",
			"60694698	59	40118	620	1	3.99",
			"60664659	59	40118	620	1	3.99",
			"69624033	166	40118	80	1	18.19",
			"60604100	166	40118	80	1	13.30",
	};
	private String[] resultTextLT399 = {
			"69684558	20	40118	280	1	19.00",
			"69684558	20	40118	280	-1	-19.00",
			"62684671	20	40118	685	-1	-69.99",
			"61664713	59	40118	335	1	17.99",
			"61664713	59	40118	335	-1	-17.99",
			"61684613	59	40118	335	1	12.99",
			"69624033	166	40118	80	1	18.19",
			"60604100	166	40118	80	1	13.30",
	};

	public void testText1() throws Exception {
		tstFilter(getLayout(), resultStart60, "60", FieldSelectX.STARTS_WITH, 0);
		tstFilter(getLayout(), resultLessThanZero, "-", FieldSelectX.STARTS_WITH, 5);
		tstFilter(getLayout(), resultTextStore20, "20", FieldSelectX.CONTAINS, 1);
		tstFilter(getLayout(), resultTextStore20, "0",  FieldSelectX.CONTAINS, 1);
		tstFilter(getLayout(), resultTextStore20, "2",  FieldSelectX.CONTAINS, 1);
		tstFilter(getLayout(), resultTextStore20, "2",  FieldSelectX.STARTS_WITH, 1);
		tstFilter(getLayout(), resultTextStore20, "20", FieldSelectX.STARTS_WITH, 1);
		tstFilter(getLayout(), resultTextStore20, "20", "=", 1);

		tstFilter(getLayout(), resultTextStore20166, "59", "<>", 1);
		tstFilter(getLayout(), resultTextStore20166, "59", FieldSelectX.DOES_NOT_CONTAIN, 1);
		tstFilter(getLayout(), resultTextStore20166, "9", FieldSelectX.DOES_NOT_CONTAIN, 1);
		tstFilter(getLayout(), resultTextStore20166, "5", FieldSelectX.DOES_NOT_CONTAIN, 1);


//		tstFilter(getLayout(), resultTextLT4, "4", "<", 5);
//		tstFilter(getLayout(), resultTextLT4, "3.99", "<=", 5);
//		tstFilter(getLayout(), resultTextLT399, "3.99", "<", 5);
//		tstFilter(getLayout(), resultTextLT399, "3.98", "<=", 5);

//		tstFilter(getLayout(), resultTextGT500, "5", ">", 5);
//		tstFilter(getLayout(), resultTextGT500, "5.00", ">", 5);
//		tstFilter(getLayout(), resultTextGT500, "5.01", ">=", 5);
//		tstFilter(getLayout(), resultTextGT501, "5.01", ">", 5);
//		tstFilter(getLayout(), resultTextGT501, "5.02", ">=", 5);

		tstFilter(getNumLayout(), resultTextLT4, "4", FieldSelectX.TEXT_LT, 5);
		tstFilter(getNumLayout(), resultTextLT4, "3.99", FieldSelectX.TEXT_LE, 5);
		tstFilter(getNumLayout(), resultTextLT399, "3.99", FieldSelectX.TEXT_LT, 5);
		tstFilter(getNumLayout(), resultTextLT399, "3.98", FieldSelectX.TEXT_LE, 5);

		tstFilter(getNumLayout(), resultTextGT500, "5", FieldSelectX.TEXT_GT, 5);
		tstFilter(getNumLayout(), resultTextGT500, "5.00", FieldSelectX.TEXT_GT, 5);
		tstFilter(getNumLayout(), resultTextGT500, "5.01", FieldSelectX.TEXT_GE, 5);
		tstFilter(getNumLayout(), resultTextGT501, "5.01", FieldSelectX.TEXT_GT, 5);
		tstFilter(getNumLayout(), resultTextGT501, "5.02", FieldSelectX.TEXT_GE, 5);
	}


	public void testNum1() throws Exception {
		tstFilter(getNumLayout(), resultLessThanZero, "0", "<", 5);
		tstFilter(getNumLayout(), resultLessThanZero, "-17.98", "<", 5);
		tstFilter(getNumLayout(), resultLessThanZero, "-17.99", "<=", 5);
		tstFilter(getNumLayout(), resultLessThanM1799, "-17.99", "<", 5);
		tstFilter(getNumLayout(), resultLessThanM1799, "-18", "<=", 5);

		tstFilter(getNumLayout(), resultGT1798, "17.98", ">", 5);
		tstFilter(getNumLayout(), resultGT1798, "17.99", ">=", 5);
		tstFilter(getNumLayout(), resultGT1799, "17.99", ">", 5);
		tstFilter(getNumLayout(), resultGT1799, "18", ">=", 5);


		tstFilter(getLayout(), resultLessThanZero, "0", FieldSelectX.NUM_LT, 5);
		tstFilter(getLayout(), resultLessThanZero, "-17.98", FieldSelectX.NUM_LT, 5);
		tstFilter(getLayout(), resultLessThanZero, "-17.99", FieldSelectX.NUM_LE, 5);
		tstFilter(getLayout(), resultLessThanM1799, "-17.99", FieldSelectX.NUM_LT, 5);
		tstFilter(getLayout(), resultLessThanM1799, "-18", FieldSelectX.NUM_LE, 5);

		tstFilter(getLayout(), resultGT1798, "17.98", FieldSelectX.NUM_GT, 5);
		tstFilter(getLayout(), resultGT1798, "17.99", FieldSelectX.NUM_GE, 5);
		tstFilter(getLayout(), resultGT1799, "17.99", FieldSelectX.NUM_GT, 5);
		tstFilter(getLayout(), resultGT1799, "18", FieldSelectX.NUM_GE, 5);


		tstFilter(getNumLayout(), resultTextStore20, "20", FieldSelectX.TEXT_EQ, 1);
		tstFilter(getNumLayout(), resultTextStore20, "20.0", FieldSelectX.NUM_EQ, 1);
		tstFilter(getNumLayout(), resultTextStore20166, "59", "<>", 1);

	}



	private void tstFilter(LayoutDetail layout, String[] result, String value, String op, int fldIdx) throws Exception {
		FileView f = TstConstants.readFileView(layout, TstConstants.CSV_SALES_LINES, Constants.IO_NAME_1ST_LINE);

		FilePosition pos = new FilePosition(0, 0, 0, fldIdx, true, f.getRowCount());
		int opIndex = -1;

		op = op.trim();
		for (int i = 0; i < Compare.OPERATOR_SEARCH_OPTIONS.length; i++) {
    		if (Compare.OPERATOR_SEARCH_OPTIONS[i].trim().equalsIgnoreCase(op)) {
    			opIndex = i;
    			break;
    		}
    	}

		if (opIndex < 0) {
			System.out.println("Not Supported: " + op);
			return;
		}

		//filter.getFilterFieldListMdl().setRecordSelection(0, rSel);

		//FileView v = f.getFilteredView(filter);


		ArrayList<String> lines = new ArrayList<String>();
		f.find(value, pos, true, opIndex, false);
		while (pos.row < f.getRowCount()) {
			lines.add(f.getLine(pos.row).getFullLine());
			pos.adjustPosition(value.length(), opIndex);
			f.find(value, pos, true, opIndex, false);
		}

		if (result.length != lines.size()) {
			int c = Math.min(result.length, lines.size());
			System.out.println(" Operator: " + op + " " + opIndex);
			for (int i = 0; i < c; i++) {
				System.out.println();
				System.out.println(result[i]);
				System.out.println(lines.get(i));
			}
			for (int i = c; i < result.length; i++) {
				System.out.println();
				System.out.println(result[i]);
			}
			for (int i = c; i < lines.size(); i++) {
				System.out.println();

				System.out.println(lines.get(i));
			}
		}
		assertEquals("Check Size: ", result.length, lines.size());
		for (int i = 0; i < result.length; i++) {
			assertEquals("Op: " + op  + " val: " + value + " " + i, result[i], lines.get(i));
			pos.adjustPosition(value.length(), opIndex);
			f.find(value, pos, true, opIndex, false);
		}
	}



//	private InputStream getFile(String[] lines) {
//		return TstConstants.getFile(lines);
//	}

	private LayoutDetail getLayout() throws RecordException, Exception {
		return getExternalLayout(TstConstants.TAB_CSV_LAYOUT).asLayoutDetail();
	}

	private LayoutDetail getNumLayout() throws RecordException, Exception {
		return getExternalLayout(TstConstants.TAB_CSV_LAYOUT_NUM).asLayoutDetail();
	}

	private ExternalRecord getExternalLayout(String strLayout) throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(strLayout, "Csv Layout");
	}
}
