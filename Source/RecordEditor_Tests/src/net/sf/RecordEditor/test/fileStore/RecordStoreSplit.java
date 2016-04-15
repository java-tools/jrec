package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.utils.fileStorage.IRecordStore;
import net.sf.RecordEditor.utils.fileStorage.RecordStoreCharLine;
import net.sf.RecordEditor.utils.fileStorage.RecordStoreFixedLength;
import net.sf.RecordEditor.utils.fileStorage.RecordStoreVariableLength;
import junit.framework.TestCase;

public class RecordStoreSplit extends TestCase {

	private static final int TEST_DATA_SIZE = 1200;


	public void testSplitFixed() {	
		tstSplit(new RecordStoreFixedLength(8000, 20, 0));
	}

	public void testSplitVariable() {	
		tstSplit(new RecordStoreVariableLength(8000, 20, 1, 0));
	}


	public void testSplitChar() {
		tstSplit(new RecordStoreCharLine(8000, 20, ""));
	}


	public void testSplitAtFixed() {	
		tstSplitAt(new IRecordStoreCreator() {
			@Override public RecordStoreFixedLength create() {
				return new RecordStoreFixedLength(8000, 20, 0);
			}
		}); 
	}
	
	public void testSplitAtVariable() {	
		tstSplitAt(new IRecordStoreCreator() {
			@Override public RecordStoreVariableLength create() {
				return new RecordStoreVariableLength(8000, 20, 1, 0);
			}
		}); 
	}

	
	public void testSplitAtChar() {	
		tstSplitAt(new IRecordStoreCreator() {
			@Override public RecordStoreCharLine create() {
				return new RecordStoreCharLine(8000, 20, "");
			}
		}); 
	}



	private void tstSplitAt(IRecordStoreCreator c) {
		tstSplitAt(c.create(), 0);
		tstSplitAt(c.create(), 1);
		tstSplitAt(c.create(), 15);
		tstSplitAt(c.create(), 16);
		tstSplitAt(c.create(), 17);
		tstSplitAt(c.create(), TEST_DATA_SIZE / 2);
		tstSplitAt(c.create(), TEST_DATA_SIZE - 17);
		tstSplitAt(c.create(), TEST_DATA_SIZE - 2);
		tstSplitAt(c.create(), TEST_DATA_SIZE - 1);
	}

	private void tstSplitAt(IRecordStore r, int pos) {
		load(r);
		
		IRecordStore[] splitAt = r.splitAt(pos);

		assertEquals("Incorrect size - first split", pos, splitAt[0].getRecordCount());
		assertEquals("Incorrect size - 2nd split", r.getRecordCount() - pos, splitAt[1].getRecordCount());

		check(r, splitAt);
	}

	private void tstSplit(IRecordStore r) {
		load(r);
		
		check(r, r.split(4000));
	}	
	
	
	private void check(IRecordStore r, IRecordStore[] rr) {
		System.out.println("Num RecordStores: " + rr.length);
		int c = 0;
		
		for (int i = 0; i < rr.length; i++) {
			c += rr[i].getRecordCount();
		}
		
		assertEquals("Check Count", r.getRecordCount(), c);
		
		c = 0;
		for (int i = 0; i < rr.length; i++) {
			int recordCount = rr[i].getRecordCount();
			for (int j = 0; j < recordCount; j++) {
				if (rr[i] instanceof RecordStoreCharLine) {
					assertEquals("Check Line: " + i + ", " + j + ", " + c, lineVal(c + j), new String(((RecordStoreCharLine)rr[i]).getChar(j)));
				} else {
					assertEquals("Check Line: " + i + ", " + j + ", " + c, lineVal(c + j), new String(rr[i].get(j)));
				}
			}
			c += recordCount;
		}
	}
	
	private void load(IRecordStore r) {
		
		if (r instanceof RecordStoreCharLine) {
			RecordStoreCharLine recordStoreCharLine = (RecordStoreCharLine)r;
			for (int i = 0; i < TEST_DATA_SIZE; i++) {
				recordStoreCharLine.add(i, lineVal(i).toCharArray());
			}
		} else {
			for (int i = 0; i < TEST_DATA_SIZE; i++) {
				r.add(i, lineVal(i).getBytes());
			}
		}
	}
	

	private static String lineVal(int i) {
		return ("line " + i + "                      ").substring(0, 20);
	}
	
	
	private interface IRecordStoreCreator {
		IRecordStore create(); 
	}
}
