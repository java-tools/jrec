/**
 *
 */
package net.sf.RecordEditor.test.po;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.po.PoMessageLineReader;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.po.def.PoLine;
import net.sf.RecordEditor.po.display.DuplicateFilter;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import junit.framework.TestCase;

/**
 * @author mum
 *
 */
public class TstDuplicateFilter extends TestCase {

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates1() throws IOException, RecordException {
		int[] duplIndexs = {1, 9, };
		int[] viewIndexs = {1, 2, 4, 7, 8, 9, };

		System.out.println();
		System.out.println( "Test 1 !!!");

		check1(duplIndexs, viewIndexs, true, true);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates2() throws IOException, RecordException {
		int[] duplIndexs = {4, 5, 1, 9, 8, 3, };
		int[] viewIndexs = {1, 2, 3, 4, 5, 7, 8, 9, };

		System.out.println();
		System.out.println( "Test 2 !!!");
		check1(duplIndexs, viewIndexs, true, false);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates3() throws IOException, RecordException {
		int[] duplIndexs = {1, 9, };
		int[] viewIndexs = {1, 2, 4, 7, 8, 9, };

		System.out.println();
		System.out.println( "Test 3 !!!");
		check1(duplIndexs, viewIndexs, false, true);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates4() throws IOException, RecordException {
		int[] duplIndexs = {4, 5, 1, 9, 8, 3, };
		int[] viewIndexs = {1, 2, 3, 4, 5, 7, 8, 9, };

		System.out.println();
		System.out.println( "Test 4 !!!");
		check1(duplIndexs, viewIndexs, false, false);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates21() throws IOException, RecordException {
		int[] duplIndexs = {1, 9, };
		int[] viewIndexs = {1, 2, 4, 7, 8, 9, 12, 14, 15, 17, };

		System.out.println();
		System.out.println( "Test 21 !!!");

		check2(duplIndexs, viewIndexs, true, true);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates22() throws IOException, RecordException {
		int[] duplIndexs = {4, 5, 1, 9, 8, 3, 17, 13, 12, 16, };
		int[] viewIndexs = {1, 2, 3, 4, 5, 7, 8, 9, 12, 13, 14, 15, 16, 17, };

		System.out.println();
		System.out.println( "Test 22 !!!");
		check2(duplIndexs, viewIndexs, true, false);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates23() throws IOException, RecordException {
		int[] duplIndexs1 = {1, 9, 15, 14, };
		int[] duplIndexs2 = {1, 9, 11, 14, };
		int[] viewIndexs = {1, 2, 4, 7, 8, 9, 11, 12, 14, 17, };
//		int[] viewIndexs = {1, 2, 4, 7, 8, 9, 12, 14, 15, 17, };

		System.out.println();
		System.out.println( "Test 23 !!!");
		check("2", duplIndexs1, duplIndexs2, viewIndexs, false, true);
	}

	/**
	 * Test method for {@link net.sf.RecordEditor.po.display.DuplicateFilter#getDuplicates()}.
	 */
	public void testGetDuplicates24() throws IOException, RecordException {
		int[] duplIndexs1 = {4, 5, 1, 9, 8, 3, 15, 17, 13, 14, 12, 16, };
		int[] duplIndexs2 = {4, 5, 1, 9, 8, 3, 11, 17, 13, 14, 12, 16, };

		int[] viewIndexs = {1, 2, 3, 4, 5, 7, 8, 9, 11, 12, 13, 14, 16, 17, };

		System.out.println();
		System.out.println( "Test 24 !!!");
		check("2", duplIndexs1, duplIndexs2, viewIndexs, false, false);
	}


	private void check1(int[] duplIndexs, int[] viewIndexs, boolean removeDuplicate, boolean removeBlankDuplicate ) throws IOException, RecordException {
		check("1", duplIndexs, viewIndexs, removeDuplicate, removeBlankDuplicate);
	}


	private void check2(int[] duplIndexs, int[] viewIndexs, boolean removeDuplicate, boolean removeBlankDuplicate ) throws IOException, RecordException {
		check("2", duplIndexs, viewIndexs, removeDuplicate, removeBlankDuplicate);
	}


	private void check(String id, int[] duplIndexs, int[] viewIndexs, boolean removeDuplicate, boolean removeBlankDuplicate ) throws IOException, RecordException {
		check(id, duplIndexs, duplIndexs, viewIndexs, removeDuplicate, removeBlankDuplicate);
	}


	private void check(String id, int[] duplIndexs, int[] duplIndexs2, int[] viewIndexs, boolean removeDuplicate, boolean removeBlankDuplicate ) throws IOException, RecordException {
		FileView view = getFileView(id);
		DuplicateFilter filter =  new DuplicateFilter(null, view);

		filter.setOptions(removeDuplicate, removeBlankDuplicate);

		List<AbstractLine> initialLines = new ArrayList<AbstractLine>(view.getLines());
		IDataStore<AbstractLine> duplicateList = filter.getDuplicates();

		System.out.println();
		System.out.print("{");

		for (int i = 0; i < duplicateList.size(); i++) {
			System.out.print(initialLines.indexOf(duplicateList.get(i)) + ", ");
		}
		System.out.print("}");
		System.out.println();

		System.out.println();
		System.out.print("{");

		for (int i = 0; i < view.getRowCount(); i++) {
			System.out.print(initialLines.indexOf(view.getLine(i)) + ", ");
		}
		System.out.print("}");



		int idx;
		assertEquals("Expecting Size Of " + duplIndexs.length, duplIndexs.length, duplicateList.size());
		for (int i = 0; i < duplicateList.size(); i++) {
			idx = initialLines.indexOf(duplicateList.get(i));

			if (duplIndexs[i] == idx || duplIndexs2[i] == idx) {

			} else {
				assertEquals("Expecting " + duplIndexs[i], duplIndexs[i], idx);
			}
		}


		assertEquals("Expecting Size Of " + viewIndexs.length, viewIndexs.length, view.getRowCount());
		for (int i = 0; i < view.getRowCount(); i++) {
			assertEquals(i + ") Expecting " + viewIndexs[i], viewIndexs[i], initialLines.indexOf(view.getLine(i)));
		}
	}

	private FileView getFileView(String id) throws IOException, RecordException {
		PoMessageLineReader r = new PoMessageLineReader();
		PoLine ll;
		DataStoreStd<AbstractLine> st = DataStoreStd.newStore(PoLayoutMgr.PO_LAYOUT);
		URL url = this.getClass().getResource("/net/sf/RecordEditor/test/po/TstDupFilter0" + id + ".po");
		System.out.println("!!!! " + url.getFile() + " " + url.getPath());
		System.out.println("!!!! " + url.getFile() + " " + url.getPath());

		r.open(url.getFile(), PoLayoutMgr.PO_LAYOUT);

		while ((ll = r.read()) != null) {
			st.add(ll);
		}

		r.close();

		return  new FileView("TstFilter01", st, PoLayoutMgr.PO_LAYOUT);
	}
}
