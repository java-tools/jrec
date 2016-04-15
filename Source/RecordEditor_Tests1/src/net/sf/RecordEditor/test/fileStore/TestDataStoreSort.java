package net.sf.RecordEditor.test.fileStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import junit.framework.TestCase;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

public class TestDataStoreSort {
	private static final int TEST_SIZE = 20000;
	
	private static final Comparator<AbstractLine> CMP = new Comparator<AbstractLine>() {
		
		@Override
		public int compare(AbstractLine o1, AbstractLine o2) {
			return String.CASE_INSENSITIVE_ORDER.compare(o1.getFullLine(), o2.getFullLine());
		}
	};

	private static final ArrayList<String> LIST = new ArrayList<String>(TEST_SIZE);
	private static final ArrayList<String> SORTED_LIST = new ArrayList<String>(TEST_SIZE);
	private static final int[] EXTRACT_LINE_NUMBERS = new int[800];
	private static final ArrayList<String> EXTRACT_LIST = new ArrayList<String>(EXTRACT_LINE_NUMBERS.length);
	
	private static final int[][] ROW_LISTS = {
		EXTRACT_LINE_NUMBERS,
		getList(0, 20),
		getList(1000, 20),
		getList(TEST_SIZE - 20, 20),
		getList(0, 200),
		getList(1000, 200),
		getList(TEST_SIZE - 200, 200),
	}; 
	
	private static final StrList[] SORTED_ROW_LISTS = new StrList[ROW_LISTS.length]; 
	
	static {
		for (int i = 0; i < TEST_SIZE; i++) {
			String lineVal = TestCode.lineVal(i, "");
			LIST.add(lineVal);
			SORTED_LIST.add(lineVal);
		}
		
		Collections.sort(SORTED_LIST, String.CASE_INSENSITIVE_ORDER);
		
		for (int i = 0; i < EXTRACT_LINE_NUMBERS.length; i++) {
			EXTRACT_LINE_NUMBERS[i] = i * 5;
			EXTRACT_LIST.add(LIST.get(i * 5));
		}
		
		for (int i = 0; i < ROW_LISTS.length; i++) {
			String[] tmpSort = new String[ROW_LISTS[i].length];
			StrList tmpList = new StrList(LIST);
			for (int j = 0; j < tmpSort.length; j++) {
				tmpSort[j] = tmpList.get(ROW_LISTS[i][j]);
			}
			Arrays.sort(tmpSort);
			for (int j = 0; j < tmpSort.length; j++) {
				tmpList.set(ROW_LISTS[i][j], tmpSort[j]);
			}
			
			SORTED_ROW_LISTS[i] = tmpList;
		}
	}
	
	
	public static int[] getList(int start, int size) {
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = start + i;
		}
		return ret;
	}
	
	public void tstSortMain(String id, FileAndView fv) {
		tst(id + " Sort Main", fv.viewFile, fv.parentStore, fv.viewStore);
	}
	
	
	public void tstSortView(String id, FileAndView fv) {
		tst(id + " Sort View", fv.viewFile, fv.viewStore, fv.parentStore);
	}
	
	
	public void tstSort(String id, int fileType) {
		IDataStore<AbstractLine> sortStore;
		LayoutDetail schema = TestCode.schema;
		
		switch (fileType) {
//		case NORMAL_FILE: parentStore = DataStoreStd.newStore(schema); break;
		case FileAndView.LARGE_FL_FILE: sortStore = new DataStoreLarge(schema, FileDetails.FIXED_LENGTH, schema.getMaximumRecordLength()); break;
		case FileAndView.LARGE_VL_FILE: sortStore = new DataStoreLarge(schema, FileDetails.VARIABLE_LENGTH, schema.getMaximumRecordLength()); break;
		case FileAndView.LARGE_CHAR_FILE: sortStore = new DataStoreLarge(schema, FileDetails.CHAR_LINE, schema.getMaximumRecordLength()); break;
		default:
			sortStore = DataStoreStd.newStore(schema); break;
		}

		tstSort(id, new FileView(sortStore, null, null), sortStore);

	}
	
	
	private void tstSort(String id, FileView loadView, IDataStore<AbstractLine> sortStore) {
		TestCode.load(loadView, TEST_SIZE, "");
		sortStore.sortRE(CMP);
		
		check(id + " Sorted", SORTED_LIST, sortStore);
	}

	
	private void tst(String id, FileView loadView, IDataStore<AbstractLine> sortStore, IDataStore<AbstractLine> otherStore) {
		TestCode.load(loadView, TEST_SIZE, "");
		
		IDataStore<AbstractLine> sortExtract  = sortStore.newDataStoreRE(EXTRACT_LINE_NUMBERS);
		IDataStore<AbstractLine> otherExtract = otherStore.newDataStoreRE(EXTRACT_LINE_NUMBERS);
		
		sortStore.sortRE(CMP);
		
		check(id + " Sorted", SORTED_LIST, sortStore);
		check(id + " Other", LIST, otherStore);
		check(id + " SortExtract", EXTRACT_LIST, sortExtract);
		check(id + " OtherExtract", EXTRACT_LIST, otherExtract);
	}
	
	public void tstSortSelection(String id, int fileType, boolean viewType) {
		FileAndView fv;
		System.out.println(id);
		for (int i = 0; i < ROW_LISTS.length; i++) {
			fv = new FileAndView(TestCode.schema, fileType, viewType);
			tstSortSelection(id + " sort-parent; idx=" + i, i, fv.viewFile, fv.parentStore, fv.viewStore);
			fv = new FileAndView(TestCode.schema, fileType, viewType);
			tstSortSelection(id + " sort-view; idx=" + i, i, fv.viewFile, fv.viewStore, fv.parentStore);
		}
	}
	
	private void tstSortSelection(String id, int listId, FileView loadView, IDataStore<AbstractLine> sortStore, IDataStore<AbstractLine> otherStore) {
		TestCode.load(loadView, TEST_SIZE, "");

		sortStore.sortRE(ROW_LISTS[listId], CMP);
		
//		System.out.println();
//		for (int i = 0; i < Math.min(90, ROW_LISTS[listId].length); i++) {
//			if (i % 3 == 0) System.out.println();
//			System.out.print("\t\t" + i + "\t" + LIST.get(ROW_LISTS[listId][i]) + "\t" + otherStore.get(ROW_LISTS[listId][i]).getFullLine());
//		}
//		System.out.println();
		
		check(id + " Sorted", SORTED_ROW_LISTS[listId], sortStore);
		check(id + " Other", LIST, otherStore);
	}
	
	private void check(String id, ArrayList<String> expected, IDataStore<AbstractLine> store) {
		for (int i = 0; i < expected.size(); i++) { 
			TestCase.assertEquals(id + ": " + i, expected.get(i), store.get(i).getFullLine()); 
		}
	}
	
	private static class StrList extends ArrayList<String> {

		public StrList() {
			super();
			// TODO Auto-generated constructor stub
		}

		public StrList(Collection<? extends String> c) {
			super(c);
			// TODO Auto-generated constructor stub
		}

		public StrList(int initialCapacity) {
			super(initialCapacity);
			// TODO Auto-generated constructor stub
		}
		
	}
}
