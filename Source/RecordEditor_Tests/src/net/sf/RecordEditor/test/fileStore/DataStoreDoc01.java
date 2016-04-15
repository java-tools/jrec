package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLargeView;
import net.sf.RecordEditor.utils.fileStorage.DataStorePosition;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.fileStorage.ITextInterface;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreDoc01 extends TestCase {

	public void testStd() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		doTst(4000, new TestDataStoreAddCopy.DataStoreCreator(TestDataStoreAddCopy.STANDARD_STORAGE));
	}
	
	public void testVb1() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		doTst(2000, new TestDataStoreAddCopy.DataStoreCreator(FileDetails.VARIABLE_LENGTH));
	}
	
	public void testVb2() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		doTst(4000, new TestDataStoreAddCopy.DataStoreCreator(FileDetails.VARIABLE_LENGTH));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testVb3() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		doTst(2000, 21, new TestDataStoreAddCopy.DataStoreCreator(TestDataStoreAddCopy.LARGE_VB));
	}
	
	public void testVb4() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		doTst(4000, 21, new TestDataStoreAddCopy.DataStoreCreator(TestDataStoreAddCopy.LARGE_VB));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	
	public void testChar1() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		doTst(2000, new TestDataStoreAddCopy.DataStoreCreator(FileDetails.CHAR_LINE));
	}
	
	public void testChar2() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		doTst(4000, new TestDataStoreAddCopy.DataStoreCreator(FileDetails.CHAR_LINE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	
	public void testLargeView1() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		doTstLargeView("Large View, large block, Normal", 1000, TestDataStoreAddCopy.STANDARD_STORAGE);
		doTstLargeView("Large View, large block, Char Line", 1000, FileDetails.CHAR_LINE);
		doTstLargeView("Large View, large block, VB", 1000, FileDetails.VARIABLE_LENGTH);
		doTstLargeView("Large View, large block, Large VB", 1000, TestDataStoreAddCopy.LARGE_VB);

	}

	
	public void testLargeView2() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		doTstLargeView("Large View, small block, Char Line", 2000, FileDetails.CHAR_LINE);
		doTstLargeView("Large View, small block, VB", 2000, FileDetails.VARIABLE_LENGTH);
		doTstLargeView("Large View, small block, Large VB", 2000, TestDataStoreAddCopy.LARGE_VB);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

	}

	private void doTst(int size, TestDataStoreAddCopy.IDataStoreCreator creator) {
		doTst(size, -1, creator);
	}

	private void doTst(int size, int lineLength, TestDataStoreAddCopy.IDataStoreCreator creator) {
		IDataStore<AbstractLine> ds = creator.create(0);


		TestDataStoreAddCopy.load(ds, size, "");
		doTst(size, lineLength, ds);
	}
	
	private void doTstLargeView(String id, int size, int type) {
		TestDataStoreAddCopy.IDataStoreCreator creator = new TestDataStoreAddCopy.DataStoreCreator(type);
		IDataStore<AbstractLine> dsBase = creator.create(0);
		TestDataStoreAddCopy.load(dsBase, size, "");
		int[] lines = new int[size];
		for (int i = 0; i < size; i++) {
			lines[i] = i;
		}
		
		System.out.println();
		System.out.println("-----------" + id);
		System.out.println();
		doTst(size, 21, new DataStoreLargeView(dsBase, lines));
	}
	
	
	private void doTst(int size, int lineLength, IDataStore<AbstractLine> ds) {
		ITextInterface ti = ds.getTextInterfaceRE();
		if (lineLength < 0) {
			lineLength = ds.getLayoutRE().getMaximumRecordLength() + 1;
		}
		int textSize = size * lineLength - 1;
		assertEquals("Checking Document Size", textSize, ti.length());
		for (int i = 0; i < size; i++) {
			int pos = i * lineLength;
			if (i == 56) {
				System.out.print('.');
			}
			check("Line Search: " + i, i, pos, 0, ti.getPositionByLineNumber(i));
			assertEquals("Check Position of line: " + i, pos, ti.getCharPosition(i));
		}
		
		for (int i = 0; i < size; i++) {
			int pos = i * lineLength;
			int rev = size - 1 - i;
			int posRev = rev * lineLength;
			if (i == 56) {
				System.out.print('.');
			}
			check("Line Search: " + i, i, pos, 0, ti.getPositionByLineNumber(i));
			assertEquals("Check Position of line: " + i, pos, ti.getCharPosition(i));
			check("Line Search (rev): " + rev, rev, posRev, 0, ti.getPositionByLineNumber(rev));
			assertEquals("Check Position of line: " + i, posRev, ti.getCharPosition(rev));
		}

		System.out.println("------------------------");
		
		for (int pos = 0; pos < textSize; pos++) {
//			if (pos % 500 == 0) System.out.print("\t" + pos);
//			if (pos % 10000 == 0) System.out.println();
			DataStorePosition textPosition = ti.getTextPosition(pos);
			if (textPosition == null) {
				textPosition = ti.getTextPosition(pos);
			}
			check("Text Search: " + pos, pos / lineLength, pos, pos % lineLength, textPosition);
		}
		
		System.out.println();
		System.out.println("========================");
		
		for (int pos = 0; pos < textSize; pos++) {
//			if (pos % 500 == 0) System.out.print("\t" + pos);
//			if (pos % 10000 == 0) System.out.println();
			int rev = textSize - 1 - pos;
			DataStorePosition textPosition = ti.getTextPosition(pos);
			if (textPosition == null) {
				textPosition = ti.getTextPosition(pos);
			}
			check("Text Search: " + pos, pos / lineLength, pos, pos % lineLength, textPosition);
			textPosition = ti.getTextPosition(rev);
			check("Text Search (rev): " + rev, rev / lineLength, rev, rev % lineLength, textPosition);
		}

	}
	
	private void check(String id, int lineNumber, int pos, int posInLine, DataStorePosition linePosition) {
		assertEquals(id + " Line start", pos - posInLine, linePosition.getLineStartRE());
		assertEquals(id + " Offset", pos, linePosition.getOffset());
		assertEquals(id + " Line Num", lineNumber, linePosition.getLineNumberRE());
		assertEquals(id + " Position in line", posInLine, linePosition.getPositionInLineRE());
	
	}
}
