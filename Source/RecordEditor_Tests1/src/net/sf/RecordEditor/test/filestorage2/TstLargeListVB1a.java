package net.sf.RecordEditor.test.filestorage2;

import net.sf.RecordEditor.test.filestorage1.TstLargeListVB1;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class TstLargeListVB1a extends TestCase {


	//private static LayoutDetail dtar020;

	net.sf.RecordEditor.test.filestorage1.TstLargeListVB1 main = 
		new net.sf.RecordEditor.test.filestorage1.TstLargeListVB1();
	
	public boolean checkLines = false;
	


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parameters.getInitialisedProperties()
			.put(Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Y");
		Common.OPTIONS.agressiveCompress.set(false);
	}


	public void testVb1() {
		checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 1:");
		tst("VB 1.1.1 ", 1, 0, 100);
		tst("VB 1.1.2 ", 12, 0, 100);
		tst("VB 1.1.3 ", 15, 0, 100);
		tst("VB 1.1.4 ", 17, 0, 100);
		
		tst("VB 1.2.1 ", 121, 0, 400);
		tst("VB 1.2.2 ", 1213, 0, 800);
		tst("VB 1.2.3 ", 1215, 0, 400);
		tst("VB 1.2.4 ", 1217, 0, 800);
	}
	
	public void testVB2() {
		checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		tst("VB 2.2.1 ", System.nanoTime(), 0, 100);
		tst("VB 2.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("VB 2.2.3 ", System.nanoTime() + 15, 0, 100);
		tst("VB 2.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("VB 2.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 2.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 2.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 2.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	
	public void testVB3() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 3:");
		tst("VB 3.1.1 ", 1, 0, 100);
		tst("VB 3.1.2 ", 12, 0, 100);
		tst("VB 3.1.3 ", 15, 0, 100);
		tst("VB 3.1.4 ", 17, 0, 100);
		
		tst("VB 3.2.1 ", 121, 0, 400);
		tst("VB 3.2.2 ", 1213, 0, 800);
		tst("VB 3.2.3 ", 1215, 0, 400);
		tst("VB 3.2.4 ", 1217, 0, 800);
	}
	
	public void testVB4() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		tst("VB 4.2.1 ", System.nanoTime(), 0, 100);
		tst("VB 4.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("VB 4.2.3 ", System.nanoTime() + 15, 0, 100);
		tst("VB 4.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("VB 4.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 4.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 4.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 4.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	


	//TODO  set Chunk Size
	
	public void tst(String desc, long seed, int firstLine, int numActions) {
		main.checkLines = checkLines;
		main.tst(desc, seed, firstLine, numActions, TstLargeListVB1.dtar020.getMaximumRecordLength());
	}

	
//	public void tst(String desc, long seed, int firstLine, int numActions, int len) {
//		main.checkLines = checkLines;
//		main.tst(desc, seed, firstLine, numActions);
//	}
}
