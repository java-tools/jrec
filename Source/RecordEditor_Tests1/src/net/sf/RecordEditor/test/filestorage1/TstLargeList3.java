package net.sf.RecordEditor.test.filestorage1;

import junit.framework.TestCase;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeList3 extends TestCase {
	
	public void testFixed8() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeList main = new TstLargeList();
		main.checkLines = true;
		main.tst("Fixed 8.2.1 ", System.nanoTime(), 0, 100);
		main.tst("Fixed 8.2.2 ", System.nanoTime() + 12, 0, 100);
		main.tst("Fixed 8.2.3 ", System.nanoTime() + 15, 0, 500);
		main.tst("Fixed 8.2.4 ", System.nanoTime() + 17, 0, 100);
		
		main.tst("Fixed 8.3.1 ", System.nanoTime() + 121, 0, 400);
		main.tst("Fixed 8.3.2 ", System.nanoTime() + 1213, 0, 800);
		main.tst("Fixed 8.3.3 ", System.nanoTime() + 1215, 0, 400);
		main.tst("Fixed 8.3.4 ", System.nanoTime() + 1217, 0, 800);
	}

	public void testFixed7() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeList main = new TstLargeList();
		main.checkLines = true;
		
		System.out.println("Test 7:");
		main.tst("Fixed 7.1.1 ", 1, 0, 1000);
		main.tst("Fixed 7.1.2 ", 12, 0, 1000);
		main.tst("Fixed 7.1.3 ", 15, 0, 1000);
		main.tst("Fixed 7.1.4 ", 17, 0, 1000);
		
		main.tst("Fixed 7.2.1 ", 121, 0, 400);
		main.tst("Fixed 7.2.2 ", 1213, 0, 800);
		main.tst("Fixed 7.2.3 ", 1215, 0, 400);
		main.tst("Fixed 7.2.4 ", 1217, 0, 800);
	}


}
