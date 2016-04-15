package net.sf.RecordEditor.test.filestorage1;

import java.util.ArrayList;
import java.util.Random;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.FileChunkLine;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IChunkLengthChangedListner;
import net.sf.RecordEditor.utils.fileStorage.IFileChunk;
import junit.framework.TestCase;

public class TstFileChunk extends TestCase {

	private static byte[][] dataLines = new byte[500][];
	private ArrayList<byte[]> items = new ArrayList<byte[]>();
	private FileChunkLine fc;
	private String lastAction = "";
	
	static {
		for (int i = 0; i < dataLines.length; i++) {
			dataLines[i] = new byte[20];
			for (int j = 0; j < dataLines[i].length; j++) {
				dataLines[i][j] = (byte) (i+j);
			}
		}
	}
	
	private IChunkLengthChangedListner chgListner = new IChunkLengthChangedListner() {
		@Override public void blockChanged(IFileChunk ch) {
		}
	};


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
	}
	
	public void testFixed1() {
		System.out.println("Test 1:");
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst("Fixed 1.1.1 ", 1, 0, 0, 100);
		tst("Fixed 1.1.2 ", 12, 0, 0, 100);
		tst("Fixed 1.1.3 ", 15, 0, 0, 100);
		tst("Fixed 1.1.4 ", 17, 0, 0, 100);
		
		tst("Fixed 1.2.1 ", 121, 0, 0, 400);
		tst("Fixed 1.2.2 ", 1213, 0, 0, 800);
		tst("Fixed 1.2.3 ", 1215, 0, 0, 400);
		tst("Fixed 1.2.4 ", 1217, 0, 0, 800);
	}
	
	public void testFixed2() {
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst("Fixed 2.2.1 ", System.nanoTime(), 0, 0, 100);
		tst("Fixed 2.2.2 ", System.nanoTime() + 12, 0, 0, 100);
		tst("Fixed 2.2.3 ", System.nanoTime() + 15, 0, 0, 500);
		tst("Fixed 2.2.4 ", System.nanoTime() + 17, 0, 0, 100);
		
		tst("Fixed 2.3.1 ", System.nanoTime() + 121, 0, 0, 400);
		tst("Fixed 2.3.2 ", System.nanoTime() + 1213, 0, 0, 800);
		tst("Fixed 2.3.3 ", System.nanoTime() + 1215, 0, 0, 400);
		tst("Fixed 2.3.4 ", System.nanoTime() + 1217, 0, 0, 800);
	}
	
	private void tst(String desc, long seed, int firstLine, int type, int numActions) {
		FileDetails fd1 = new FileDetails(null, type, dataLines[0].length, dataLines.length * dataLines[0].length, chgListner);
		
		items = new ArrayList<byte[]>();
		
		fc = new FileChunkLine(fd1, 0, 0);
		for (int i = 0; i < dataLines.length; i++) {
			System.out.print(i + " ");
			items.add(dataLines[i].clone());
			fc.add(i, dataLines[i].clone());
		}
		System.out.println();
		System.out.println(desc);
		
		Random random = new Random(seed);
		
		for (int i = 0; i < numActions; i++) {
			doAction(random);
			assertTrue(desc + " " + "ActionNo: " + i + " Seed: " + seed, check());
		}
	}
	
	private void doAction(Random random) {
		int from;
		int count = Math.abs(random.nextInt()) % 5 + 1;
		int pos = Math.abs(random.nextInt()) % dataLines.length;
		switch (Math.abs(random.nextInt()) % 4) {
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
			lastAction = " Add: " + pos + " <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.add(fc.getCount(),
						dataLines[getCircular(from+i, dataLines.length)].clone());
				items.add(
						dataLines[getCircular(from+i, dataLines.length)].clone());
			}
		break;
		case 2:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Add: " + pos + " <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.put(getCircular(pos + i, items.size()), 
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
