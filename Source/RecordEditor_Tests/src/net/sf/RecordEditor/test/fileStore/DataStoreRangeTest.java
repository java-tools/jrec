package net.sf.RecordEditor.test.fileStore;

import junit.framework.TestCase;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.testcode.StdSchemas;
import net.sf.RecordEditor.utils.fileStorage.DataStoreRange;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd.DataStoreStdBinary;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;

public class DataStoreRangeTest extends TestCase {
	private static final int TEST_DATA_SIZE = 1000;
	private LayoutDetail schema = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;

	public void test01() {
		IDataStore<AbstractLine> base = load(new DataStoreStdBinary<AbstractLine>(schema), TEST_DATA_SIZE);
		
		for (int i = 0; i < TEST_DATA_SIZE; i++) {
			int spaceLeft = TEST_DATA_SIZE - i;
			int[] sizes = {1, 2, 16, 17, spaceLeft / 2, spaceLeft - 17, spaceLeft - 2, spaceLeft - 1, spaceLeft};
			for (int size : sizes) {
				if (size > 0 && size < spaceLeft) {
					IDataStore<AbstractLine> tst = new DataStoreRange(base, i, size);
					
					for (int j = 0; j < size; j++) {
						String message = i + ", " + j + "Size: " + size;
						String tstValue = lineVal(i + j);
						assertEquals("Check 1 For: " + message, tstValue, tst.get(j).getFullLine());
						assertEquals("Check 1 For: " + message, tstValue, tst.getNewLineRE(j).getFullLine());
						assertEquals("Check 1 For: " + message, tstValue, tst.getTempLineRE(j).getFullLine());
					}
				}
			}
		}
	}
	
	private IDataStore<AbstractLine> load(IDataStore<AbstractLine> r, int testSize) {
		System.out.print("Starting load " + testSize);
		long c = System.currentTimeMillis();
		for (int i = 0; i < testSize; i++) {
			r.add(i, new CharLine(schema, lineVal(i)));
		}
		System.out.println("  Finished - " + ((System.currentTimeMillis() - c) / 1000));
		
		return r;
	}
	

	private static String lineVal(int i) {
		return ("line " + i + "                      ").substring(0, 20);
	}
}
