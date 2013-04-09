package net.sf.RecordEditor.test.file1;

import junit.framework.TestCase;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.Compare;
import net.sf.RecordEditor.test.TstConstants;

public class TstFindMultiRecords extends TestCase  {
	private String AMS_PO_LAYOUT = TstConstants.AMS_PO_LAYOUT;



	private TstConstants constants = new TstConstants();

	public void testFind1() throws Exception {
		int[][] result = {
				{0, 6, 1, 0},
				{10, 6, 1, 0},
				{23, 6, 1, 0},
				{33, 6, 1, 0},
				{37, 6, 1, 0},
				{44, 6, 1, 0},
				{48, 6, 1, 0},
				{59, 6, 1, 0},
				{70, 19, 2, 0},
				{71, 19, 2, 0},
				{72, 19, 2, 0},
				{73, 19, 2, 0},

		};

		System.out.println();
		System.out.println("Search 00 equals: ");
		tstFind("00 equals", result, "00", Compare.OP_EQUALS);
	}

	public void testFind2() throws Exception {
		int[][] result = {
				{0, 6, 1, 0},
				{0, 8, 1, 1},
				{1, 1, 0, 2},
				{1, 1, 0, 4},
				{1, 2, 0, 2},
				{1, 3, 0, 5},
				{1, 3, 0, 7},
				{1, 3, 0, 9},
				{1, 3, 0, 11},
				{1, 8, 0, 11},
				{3, 1, 0, 2},
				{3, 1, 0, 4},
				{3, 2, 0, 6},
				{3, 8, 0, 9},
				{5, 1, 0, 3},
				{5, 1, 0, 5},
				{5, 2, 0, 6},
				{5, 8, 0, 11},
				{8, 1, 0, 2},
				{8, 1, 0, 4},
				{8, 2, 0, 6},
				{8, 8, 0, 9},
				{10, 6, 1, 0},
				{10, 8, 1, 1},
				{11, 1, 0, 3},
				{11, 1, 0, 5},
				{11, 2, 0, 10},
				{11, 8, 0, 11},
				{14, 1, 0, 2},
				{14, 1, 0, 4},
				{14, 2, 0, 6},
				{14, 8, 0, 9},
				{16, 1, 0, 4},
				{16, 1, 0, 6},
				{16, 2, 0, 6},
				{16, 8, 0, 11},
				{21, 1, 0, 2},
				{21, 1, 0, 4},
				{21, 2, 0, 6},
				{21, 8, 0, 11},
				{23, 6, 1, 0},
				{23, 8, 1, 1},
				{24, 1, 0, 2},
				{24, 1, 0, 4},
				{24, 2, 0, 6},
				{24, 8, 0, 11},
				{26, 1, 0, 2},
				{26, 1, 0, 4},
		};

		System.out.println();
		System.out.println("Search 00 contains: ");
		tstFind("00 contains", result, "00", Compare.OP_CONTAINS);
	}

	public void testFind3() throws Exception {
		int[][] result = {
				{1, 1, 0, 2},
				{1, 3, 0, 5},
				{1, 3, 0, 9},
				{3, 1, 0, 2},
				{5, 1, 0, 3},
				{8, 1, 0, 2},
				{11, 1, 0, 3},
				{14, 1, 0, 2},
				{16, 1, 0, 4},
				{21, 1, 0, 2},
				{24, 1, 0, 2},
				{26, 1, 0, 2},
		};

		System.out.println();
		System.out.println("Search 0000 contains: ");
		tstFind("0000 contains", result, "0000", Compare.OP_CONTAINS);
	}

	private void tstFind(String id, int[][] expected, String searchFor, int op) throws Exception{

		@SuppressWarnings("rawtypes")
		AbstractLayoutDetails layout = getLayout();
		FileView f = TstConstants.readFileView(layout, constants.AMS_PO_LINES, layout.getFileStructure());
		FilePosition pos = new  FilePosition(0, 0, 0, FilePosition.ALL_FIELDS_IDX, true, f.getRowCount());
		String s;

		for (int i = 0; i < expected.length; i++) {
			f.find(searchFor, pos, true, op, false);
//			System.out.println(
//					"{"
//					+ (pos.row)
//			    	+ ", " + pos.currentFieldNumber
//			    	+ ", " + pos.layoutIdxUsed
//			    	+ ", " + pos.col
//			    	+ "}, "
//			);
			s = id + " " + i + " ";
			assertEquals(s + "row", expected[i][0], pos.row);
			assertEquals(s + "Fld", expected[i][1], pos.currentFieldNumber);
			assertEquals(s + "layout", expected[i][2], pos.layoutIdxUsed);
			assertEquals(s + "col", expected[i][3], pos.col);

			pos.adjustPosition(searchFor.length(),  FilePosition.ALL_FIELDS_IDX);
		}
	}

	private LayoutDetail getLayout() throws Exception {
		return getExternalLayout().asLayoutDetail();
	}

	private ExternalRecord getExternalLayout() throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(AMS_PO_LAYOUT, "Ams PO Layout");
	}
}
