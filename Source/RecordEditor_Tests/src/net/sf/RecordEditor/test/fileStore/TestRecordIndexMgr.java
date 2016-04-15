package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.fileStorage.ISimpleRecStore;
import net.sf.RecordEditor.utils.fileStorage.LineDtls;
import net.sf.RecordEditor.utils.fileStorage.RecordIndexMgr;
import junit.framework.TestCase;

public class TestRecordIndexMgr extends TestCase {

	tstDetails tst;
	
	public void testGetLinePosition01() {
		for (int i = 0; i < 5; i++) {
			doTestGetLinePosition(2000, i);
		}
	}
	
	
	public void testGetTextPosition01() {
		for (int i = 0; i < 5; i++) {
			doTestGetTextPosition(2000, i);
		}
	}

	private void doTestGetLinePosition(int tstSize, int recOverhead) {
		 tst = new tstDetails(recOverhead, tstSize, 40, 70);
		 System.out.println("~~ TestGetLinePosition " + recOverhead);
		
		 /**
		  * This group of tests might take advantage of each call is
		  * one after the last
		  */
		for (int i = 0; i < tstSize - 1; i++) {
			tstGetLinePos(i, recOverhead);
		}
		
		
		doTestGetLinePositionAll(recOverhead);

		tst.adjRecordLength(351, -3);
		tst.adjRecordLength(1351, 11);
		
		doTestGetLinePositionAll(recOverhead);
		
		tst.delRecord(451);
		tst.delRecord(1331);
		
		doTestGetLinePositionAll(recOverhead);
		
		for (int i = 0; i < 10; i++) {
			tst.adjRecordLength(1700, 2 + i);
			
			doTestGetLinePositionAll(1650, 1750, recOverhead);
		}
		
		for (int i = 9; i >= 0; i--) {
			tst.delRecord(1700 - i);
			
			doTestGetLinePositionAll(1650, 1750, recOverhead);
		}
	}
	
	
	private void doTestGetTextPosition(int tstSize, int recOverhead) {
		tst = new tstDetails(recOverhead, tstSize, 40, 70);


		System.out.println("~~ TestGetTextPosition " + recOverhead);

		/**
		 * This group of tests might take advantage of each call is
		 * one after the last
		 */
		tstGetTextPosAll("Normal:", recOverhead);

		tst.adjRecordLength(351, -3);
		tst.adjRecordLength(1351, 11);
		tstGetTextPosAll("Update Record: ", recOverhead);

		tst.delRecord(451);
		tst.delRecord(1331);
		tstGetTextPosAll("Delete Record: ",recOverhead);
		
		for (int i = 0; i < 10; i++) {
			tst.adjRecordLength(1700, 2 + i);
			
			tstGetTextPosAll("Update " + i, 1650, 1750, recOverhead);
		}

		for (int i = 9; i >= 0; i--) {
			tst.delRecord(1700 - i);
			
			tstGetTextPosAll("del: " + i, 1650, 1750, recOverhead);
		}
	}
	
	private void tstGetTextPosAll(String idx, int recOverhead) {
		tstGetTextPosAll(idx, 0, tst.txtSize(), recOverhead);
	}
	
	private void tstGetTextPosAll(String idx, int start, int size, int recOverhead) {

		for (int i = 0; i < size; i++) {
			tstGetTextPos(idx, i, recOverhead);
		}
	}
		
	private void doTestGetLinePositionAll(int recOverhead) {
		doTestGetLinePositionAll(0, tst.recUsed, recOverhead);
	}
	
	private void doTestGetLinePositionAll(int start, int size,  int recOverhead) {
		/**
		 * This test can not take advantage of getting successive positions:
		 */
		for (int i = 0; i < tst.recUsed; i++) {
			tstGetLinePos(i, recOverhead);
			tstGetLinePos(tst.recUsed - 1 - i, recOverhead);
		}
	}
	
	
	private void tstGetLinePos(int i, int recOverhead) {
		String id = i + ", " + recOverhead;
		
//		if (i == 2997) {
//			System.out.print('.');
//		}
		
		LineDtls linePos = tst.ri.getLinePositionLength(i, i % 70, tst.size());
		assertEquals("Check Line Number: "  + id, i, linePos.lineNumber);
		assertEquals("Check new length: " + id, i % 70, linePos.newLength);
		assertEquals("Check position in line: " + id, 0, linePos.positionInLine);
		assertEquals("Check length: " + id, tst.a[i+1] - tst.a[i], linePos.len);
		assertEquals("Check position: " + id, tst.a[i] + (i+1) * recOverhead, linePos.pos);
		
	}
	
	
	private void tstGetTextPos(String idx, int textPos, int recOverhead) {
		
		int size = tst.txtSize();
		int i = 0;
		while ( i < tst.a.length - 1 && tst.a[i+1] + i < textPos) {
			i += 1;
		}
		String id = idx + " " + textPos + ", "  + i + ", "+ recOverhead;
		
		if (textPos == 94595 && recOverhead == 1) {
			System.out.println("~~ " + id);
		}
		try {
			LineDtls linePos = tst.ri.getTextPosition(textPos, size);
			if (linePos == null) {
				System.out.println("Null error at: " + id);
			}
			assertEquals("Check Line Number: "  + id, i, linePos.lineNumber);
			assertEquals("Check new length: " + id, 0, linePos.newLength);
			assertEquals("Check position in line: " + id, textPos - tst.a[i] - i, linePos.positionInLine);
			assertEquals("Check length: " + id, tst.a[i+1] - tst.a[i], linePos.len);
			assertEquals("Check position: " + id, tst.a[i] + (i+1) * recOverhead, linePos.pos);
		} catch (Exception x) {
			System.out.println("Error at " + id + " - " + tst.size());
			throw new RuntimeException(x);
		}
	}

	private class tstDetails { 
		int[] a, revIdx;
		int recordOverhead = 1;
		int recUsed;

		
		RecordIndexMgr ri;
		
		ISimpleRecStore rs = new ISimpleRecStore() {
		
			
			@Override
			public void setSize(int storeSize) {
//				size = storeSize;
//				
//				int ru = 0;
////				int p = 0;
////				while (ru < a.length - 1 && a[ru] + ru * recordOverhead <= size) {
////					ru += 1;
//////					p += a[ru] + recordOverhead;
////				}
//				recUsed = revIdx[storeSize - 1];
			}
			
			@Override
			public int getStoreSize() {
				return a[a.length - 1] + (a.length - 1) * recordOverhead;
			}
			
			@Override
			public int getRecordCount() {
				return recUsed;
			}
			
			@Override
			public int getLength(int position) {
				if (position >= revIdx.length  ) {
					System.out.println('.' + position + " >= " + revIdx.length);
				}
				int idx = revIdx[position];
				return a[idx + 1] - a[idx];
			}
		};
		
		
		tstDetails(int recordOverhead, int testSize, int minRecLen, int maxRecLength) {

			a = TstConstants.getRandomIncreasingArray(testSize, minRecLen, maxRecLength);
			this.recUsed = a.length - 1;
			this.recordOverhead = recordOverhead;
			
			buildRevIdx();
			ri = new RecordIndexMgr(rs, maxRecLength, recordOverhead, true) ;
		}
		
		void buildRevIdx() {
			revIdx = new int[size()];

			int p = 0;
			for (int i = 0; i < recUsed; i++) {
				while (p < a[i+1] + (i + 1) * recordOverhead) {
					revIdx[p++] = i;
				}
			}
			//System.out.println("===============  " + size() + " " + p + " "  + recUsed);
		}
		
		int size() {
			return a[recUsed] + (recUsed) * recordOverhead;
		}
		
		int txtSize() {
			return a[recUsed] + (recUsed) ;
		}
	
		void adjRecordLength(int rec, int amount) {
			for (int i = rec+1; i < a.length; i++) {
				a[i] += amount;
			}
			buildRevIdx();
			
			ri.adjRecordIndex(rec, amount);
		}
		
		void delRecord(int rec) {
			for (int i = rec; i < recUsed; i++) {
				a[i] = a[i+1];
			}
			recUsed -= 1;
			buildRevIdx();
			ri.resetIndex(rec);
		}
	}
}
