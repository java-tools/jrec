package net.sf.RecordEditor.test.filestorage2;

import junit.framework.TestCase;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.test.filestorage1.TstLargeListVB1;

public class TstLargeListVB31 extends TestCase {

	private	TstLargeListVB1 main = new TstLargeListVB1();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parameters.getInitialisedProperties()
			.put(Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Y");
	}

	public void testVB31() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 1:");
		tst("VB 31.1.1 ", 1, 0, 100);
		tst("VB 31.1.2 ", 12, 0, 100);
		tst("VB 31.1.3 ", 15, 0, 100);
		tst("VB 31.1.4 ", 17, 0, 100);
		
		tst("VB 31.2.1 ", 121, 0, 400);
		tst("VB 31.2.2 ", 1213, 0, 800);
		tst("VB 31.2.3 ", 1215, 0, 400);
		tst("VB 31.2.4 ", 1217, 0, 800);
	}
	
	public void testVB3() {
		main.checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		tst("VB 32.2.1 ", System.nanoTime(), 0, 100);
		tst("VB 32.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("VB 32.2.3 ", System.nanoTime() + 15, 0, 100);
		tst("VB 32.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("VB 32.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 32.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 32.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 32.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	
	public void testVB33() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		System.out.println("Test 3:");
		tst("VB 33.1.1 ", 1, 0, 100);
		tst("VB 33.1.2 ", 12, 0, 100);
		tst("VB 33.1.3 ", 15, 0, 100);
		tst("VB 33.1.4 ", 17, 0, 100);
		
		tst("VB 33.2.1 ", 121, 0, 400);
		tst("VB 33.2.2 ", 1213, 0, 800);
		tst("VB 33.2.3 ", 1215, 0, 400);
		tst("VB 33.2.4 ", 1217, 0, 800);
	}
	
	public void testVB34() {
		main.checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		tst("VB 34.2.1 ", System.nanoTime(), 0, 100);
		tst("VB 34.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("VB 34.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("VB 34.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("VB 34.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("VB 34.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("VB 34.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("VB 34.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testVB35() {
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);
		
		main.checkLines = false;
		System.out.println("Test 1:");
		tst("VB 35.1.1 ", 1, 0, 100);
		tst("VB 35.1.2 ", 12, 0, 100);
		tst("VB 35.1.3 ", 15, 0, 100);
		tst("VB 35.1.4 ", 17, 0, 100);
		
		tst("VB 35.2.1 ", 121, 0, 400);
		tst("VB 35.2.2 ", 1213, 0, 800);
		tst("VB 35.2.3 ", 1215, 0, 400);
		tst("VB 35.2.4 ", 1217, 0, 800);
	}
	

	public void tst(String desc, long seed, int firstLine, int numActions) {
		main.tst(desc, seed, firstLine, numActions, 70600);
	}


}
