package net.sf.RecordEditor.test.filestorage2;

import junit.framework.TestCase;
import net.sf.RecordEditor.test.filestorage1.TstLargeListVB1;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class TstLargeListVB12 extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parameters.getInitialisedProperties()
			.put(Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Y");
	}


	public void testFixed5() {
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
	


}
