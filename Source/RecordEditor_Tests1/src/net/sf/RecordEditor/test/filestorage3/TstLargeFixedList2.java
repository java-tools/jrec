package net.sf.RecordEditor.test.filestorage3;

import java.io.IOException;

import junit.framework.TestCase;
import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeFixedList2 extends TestCase {


	public void testFixed5() throws IOException, RecordException {
		TstLargeFixedList main = new TstLargeFixedList();
		Common.OPTIONS.agressiveCompress.set(false);
		
		main.checkLines = false;

		main.checkLines = false;
		System.out.println("Test 1:");
		main.tst("Fixed 5.1.1 ", 1, 0, 100);
		main.tst("Fixed 5.1.2 ", 12, 0, 100);
		main.tst("Fixed 5.1.3 ", 15, 0, 100);
		main.tst("Fixed 5.1.4 ", 17, 0, 100);
		
		main.tst("Fixed 5.2.1 ", 121, 0, 400);
		main.tst("Fixed 5.2.2 ", 1213, 0, 800);
		main.tst("Fixed 5.2.3 ", 1215, 0, 400);
		main.tst("Fixed 5.2.4 ", 1217, 0, 800);
	}
	
	public void testFixed6() throws IOException, RecordException {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeFixedList main = new TstLargeFixedList();
		main.checkLines = false;

		main.tst("Fixed 6.2.1 ", System.nanoTime(), 0, 100);
		main.tst("Fixed 6.2.2 ", System.nanoTime() + 12, 0, 100);
		main.tst("Fixed 6.2.3 ", System.nanoTime() + 15, 0, 500);
		main.tst("Fixed 6.2.4 ", System.nanoTime() + 17, 0, 100);
		
		main.tst("Fixed 6.3.1 ", System.nanoTime() + 121, 0, 400);
		main.tst("Fixed 6.3.2 ", System.nanoTime() + 1213, 0, 800);
		main.tst("Fixed 6.3.3 ", System.nanoTime() + 1215, 0, 400);
		main.tst("Fixed 6.3.4 ", System.nanoTime() + 1217, 0, 800);
	}


}
