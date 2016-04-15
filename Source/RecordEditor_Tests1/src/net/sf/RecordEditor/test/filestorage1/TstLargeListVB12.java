package net.sf.RecordEditor.test.filestorage1;

import junit.framework.TestCase;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeListVB12 extends TestCase {


	public void testFixed5() {
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = false;

		main.checkLines = false;
		System.out.println("Test 1:");
		main.tst("VB 5.1.1 ", 1, 0, 100);
		main.tst("VB 5.1.2 ", 12, 0, 100);
		main.tst("VB 5.1.3 ", 15, 0, 100);
		main.tst("VB 5.1.4 ", 17, 0, 100);
		
		main.tst("VB 5.2.1 ", 121, 0, 400);
		main.tst("VB 5.2.2 ", 1213, 0, 800);
		main.tst("VB 5.2.3 ", 1215, 0, 400);
		main.tst("VB 5.2.4 ", 1217, 0, 800);
	}
	
	public void testVB6() {
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "100");
		Common.OPTIONS.agressiveCompress.set(false);
		
		TstLargeListVB1 main = new TstLargeListVB1();
		main.checkLines = false;

		main.tst("VB 6.2.1 ", System.nanoTime(), 0, 100);
		main.tst("VB 6.2.2 ", System.nanoTime() + 12, 0, 100);
		main.tst("VB 6.2.3 ", System.nanoTime() + 15, 0, 500);
		main.tst("VB 6.2.4 ", System.nanoTime() + 17, 0, 100);
		
		main.tst("VB 6.3.1 ", System.nanoTime() + 121, 0, 400);
		main.tst("VB 6.3.2 ", System.nanoTime() + 1213, 0, 800);
		main.tst("VB 6.3.3 ", System.nanoTime() + 1215, 0, 400);
		main.tst("VB 6.3.4 ", System.nanoTime() + 1217, 0, 800);
	}


}
