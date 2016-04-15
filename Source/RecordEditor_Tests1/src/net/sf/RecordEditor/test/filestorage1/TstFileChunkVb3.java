package net.sf.RecordEditor.test.filestorage1;

import java.util.ArrayList;
import java.util.Random;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.FileChunkLine;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IChunkLengthChangedListner;
import net.sf.RecordEditor.utils.fileStorage.IFileChunk;
import junit.framework.TestCase;

public class TstFileChunkVb3 extends TestCase {

	private static byte[][] dataLines = new byte[500][];
	private ArrayList<byte[]> items = new ArrayList<byte[]>();
	private FileChunkLine fc;
	private String lastAction = "";
	
	private IChunkLengthChangedListner chgListner = new IChunkLengthChangedListner() {
		@Override public void blockChanged(IFileChunk ch) {
		}
	};

	static {
		int len;
		for (int i = 0; i < dataLines.length -10; i++) {
			len = (i % 170) * 3 + 10;
			dataLines[i] = new byte[len];
			for (int j = 0; j < len; j++) {
				dataLines[i][j] = (byte) (i+j);
			}
		}
		for (int i = dataLines.length -10; i < dataLines.length; i++) {
			len = 66000;
			dataLines[i] = new byte[len];
			for (int j = 0; j < len; j++) {
				dataLines[i][j] = (byte) (i+j);
			}
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
	}
	
	public void testVB31() {
		System.out.println("Test 1:");
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst("VB 3.1.1 ", 1, 0, 100);
		tst("VB 3.1.2 ", 12, 0, 100);
		tst("VB 3.1.3 ", 15, 0, 100);
		tst("VB 3.1.4 ", 17, 0, 100);
		
		tst("VB 3.1.5 ", 121, 0, 400);
		tst("VB 3.1.6 ", 1213, 0, 800);
		tst("VB 3.1.7 ", 1215, 0, 400);
		tst("VB 3.1.8 ", 1217, 0, 800);
	}
	
	public void testVB32() {
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst("VB 3.2.1 ", System.nanoTime(), 0, 100);
		tst("VB 3.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("VB 3.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 3.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("VB 3.2.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 3.2.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 3.2.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 3.2.4 ", System.nanoTime() + 1217, 0, 800);
	}
	
	public void testVB33() {
		Common.OPTIONS.agressiveCompress.set(false);
		
		String desc = "VB 3";
		setupTestData(desc, 0);
		
		tstAdd(desc, 1, 498, 1);
		tstAdd(desc, 2, 400, 491);
		tstAdd(desc, 3, 10, 491);
		tstAdd(desc, 4, 0, 492);
		



		fc.remove(499);	
		items.remove(499);
		assertTrue(desc + "  ActionNo: 5 (remove 499)", check());

		int end = items.size() - 1;
		fc.remove(end);	
		items.remove(end);
		assertTrue(desc + "  ActionNo: 6 (remove " + end +")", check());
	}
	
	private void tstAdd(String desc, int id, int dest, int from) {
		fc.add(dest, dataLines[from].clone());
		items.add(dest, dataLines[from].clone());

		assertTrue(desc + "  ActionNo: " + id + " add " + dest, check());

	}
	
	public void testVB34() {
		String desc = "VB 4";
		setupTestData(desc, 0);
		
		tstPut(desc, 1, 498, 1);
		tstPut(desc, 2, 400, 491);
		tstPut(desc, 3, 10, 491);
		tstPut(desc, 4, 0, 492);
	}
	
	private void tstPut(String desc, int id, int dest, int from) {
		fc.putFromLine(dest, dataLines[from].clone());
		items.set(dest, dataLines[from].clone());

		assertTrue(desc + "  ActionNo: " + id + " add " + dest, check());

	}
	
	private void tst(String desc, long seed, int firstLine, int numActions) {
		
		Random random = new Random(seed);
		
		setupTestData(desc, firstLine);
		
		for (int i = 0; i < numActions; i++) {
			doAction(random, i);
			assertTrue(desc + " " + "ActionNo: " + i + " Seed: " + seed, check());
		}
	}
	
	private void setupTestData(String desc, int firstLine) {
		int len = 0;
		
		for (int i = 0; i < dataLines.length; i++) {
			len += dataLines.length;
		}
		
		FileDetails fd1 = new FileDetails(null, FileDetails.VARIABLE_LENGTH, 100000, len, chgListner);
		
		items = new ArrayList<byte[]>();
		
		fc = new FileChunkLine(fd1, 0, 0);
		for (int i = 0; i < dataLines.length; i++) {
			System.out.print(i + " ");
			items.add(dataLines[i].clone());
			fc.add(i, dataLines[i].clone());
		}
		System.out.println();
		System.out.println(desc);
		assertTrue(desc + " Check Initial Values: ", check());

	}
	
	private void doAction(Random random, int actionNo) {
		int from;
		int count = Math.abs(random.nextInt()) % 5 + 1;
		int pos = Math.abs(random.nextInt()) % dataLines.length;
		int id = Math.abs(random.nextInt()) % 4;
		System.out.println("Starting action " + actionNo + " - " + id);
		switch (id) {
		case 0:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Add: " + pos + " <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.add(getCircular(pos + i, items.size()), 
						dataLines[getCircular(from+i, dataLines.length)].clone());
				items.add(getCircular(pos + i, items.size()),
						dataLines[getCircular(from+i, dataLines.length)].clone());
			}
		break;
		case 1:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Add at End " + fc.getCount()  + " <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.add(fc.getCount(),
						dataLines[getCircular(from+i, dataLines.length)].clone());
				items.add(
						dataLines[getCircular(from+i, dataLines.length)].clone());
			}
		break;
		case 2:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Put: " + pos + " <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.putFromLine(getCircular(pos + i, items.size()), 
						dataLines[getCircular(from+i, dataLines.length)].clone());
				items.set(getCircular(pos + i, items.size()),
						dataLines[getCircular(from+i, dataLines.length)].clone());
			}
		break;
		case 3:
			lastAction = " Del: " + pos + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.remove(getCircular(pos + i, items.size()));
		
				items.remove(getCircular(pos + i, items.size()));
			}
		break;
		}
		
		System.out.println("End  action " + actionNo + " - " + lastAction);
	}
	
	private int getCircular(int i, int size) {
		if (i >= size) {
			return i - size;
		}
		return i;
	}
	private boolean check() {
		boolean ret = fc.getCount() == items.size();
		
		if (! ret) { 
			System.out.print("Error - Different sizes - " + fc.getCount() + " == " + items.size()
					+ lastAction + " : ");
		}
		
		for (int i = 0; i < Math.min(fc.getCount(), items.size()); i++) {
			if (! Code.compare(fc.get(i), items.get(i))) {
				if (ret) {
					System.out.print("Error line " + i + lastAction + " : ");
				}
				System.out.println("-- " + i + " ");
				
				Code.writeByteArray(fc.get(i));		
				Code.writeByteArray(items.get(i));
				
				System.out.println();
				System.out.println();
				ret = false;
			}
		}
		System.out.println();
		
		return ret;
	}

}
