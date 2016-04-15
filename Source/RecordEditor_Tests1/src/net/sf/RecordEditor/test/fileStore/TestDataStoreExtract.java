package net.sf.RecordEditor.test.fileStore;

import java.util.HashSet;

import junit.framework.TestCase;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.trove.TIntArrayList;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.swing.common.EmptyProgressDisplay;

public class TestDataStoreExtract {

	public final static LayoutDetail schema = TestCode.schema;
	
	private static final EmptyProgressDisplay PROGRESS_DISPLAY = new EmptyProgressDisplay();
	
	private static final int TEST_SIZE = 20000;
	
	private int[] EMPTY_ARRAY = {};
	
	private int[] SKIP1 = {5, 10, 3000, TEST_SIZE-10 };
	private int[] SKIP2 = {5, 3000, TEST_SIZE-1000 };
	

	public final void doTest1(String id, IExtractTestData td) {
		TestCode.load(td.getLoadFile(), TEST_SIZE, ""); 
		
		do1Test(id + " 1", td, loadArray(0, td.getFile().getRowCount(), EMPTY_ARRAY));
		td.newExtract();
		
		do1Test(id + " 2", td, loadArray(1, td.getFile().getRowCount() - 1, EMPTY_ARRAY));
		td.newExtract();

		do1Test(id + " 3", td, loadArray(5, td.getFile().getRowCount() - 5, EMPTY_ARRAY));
		td.newExtract();
		
		do1Test(id + " 4", td, loadArray(0, td.getFile().getRowCount(), SKIP1));
		td.newExtract();

		do1Test(id + " 5", td, loadArray(5, td.getFile().getRowCount() - 5, SKIP2));
		td.newExtract();

		do1Test(id + " 6", td, loadReverse(0, td.getFile().getRowCount(), EMPTY_ARRAY));
		td.newExtract();
		
		do1Test(id + " 7", td, loadReverse(5, td.getFile().getRowCount() - 5, SKIP2));
		td.newExtract();
		
		for (int i = td.getFile().getRowCount()-1; i> 500; i--) {
			td.getFile().deleteLine(i);
		}
		
		do1Test(id + " 8", td, loadArray(0, td.getFile().getRowCount(), EMPTY_ARRAY));
		td.newExtract();

		do1Test(id + " 9", td, loadReverse(0, td.getFile().getRowCount(), EMPTY_ARRAY));
		td.newExtract();

		do1Test(id + " a", td, loadSome(0, td.getFile().getRowCount()));
		td.newExtract();

		do1Test(id + " b", td, loadSome(1, td.getFile().getRowCount() - 1));
	}
	
	
	private int[] loadArray(int start, int end, int skipList[]) {
		int[] ret = new int[end - start - skipList.length];
		HashSet<Integer> skip = new HashSet<Integer>();
		for (int i = 0; i < skipList.length; i++) {
			skip.add(skipList[i]);
		}
		
		int j = 0;
		for (int i = start; i < end && j < ret.length; i++) {
			if (! skip.contains(i)) {
				ret[j++] = i;
			}
		}
		
		return ret;
	}

	
	private int[] loadReverse(int start, int end, int skipList[]) {
		int[] ret = new int[end - start - skipList.length];
		HashSet<Integer> skip = new HashSet<Integer>();
		for (int i = 0; i < skipList.length; i++) {
			skip.add(skipList[i]);
		}
		
		int j = ret.length;
		for (int i = start; i < end; i++) {
			if (! skip.contains(i)) {
				ret[--j] = i;
			}
		}
		
		return ret;
	}
	
	private int[] loadSome(int start, int end) {
		TIntArrayList vals = new TIntArrayList(end-start);
		
		for (int i = start; i < end; i++) {
			if (i % 5000 != 0 || i % 15 != 0) {
				vals.add(i);
			}
		}
		
		return vals.toArray();
	}

	private void do1Test(String id, IExtractTestData td, int[] lineNums) {

		td.getStore().extractLinesRE(td.getExtract(), lineNums, PROGRESS_DISPLAY);
		
		TestCase.assertEquals(id + " size", lineNums.length, td.getExtract().size());
		for (int i = 0; i < lineNums.length; i++) {
			TestCase.assertEquals(id + " " + i, td.getFile().getLine(lineNums[i]).getFullLine(), td.getExtract().getTempLineRE(i).getFullLine());
		}
	}
}
