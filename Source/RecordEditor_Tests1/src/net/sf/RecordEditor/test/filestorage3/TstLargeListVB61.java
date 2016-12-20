package net.sf.RecordEditor.test.filestorage3;

import java.util.Properties;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;


public class TstLargeListVB61 extends TestCase {
	
	public void testVB1() throws Exception {
		
		tst(1, Constants.IO_VB, false);
	}
	
	
	public void testVB2() throws Exception {
		
		tst(2, Constants.IO_VB_FUJITSU, false);
	}
	
	public void testVB3() throws Exception {
		
		tst(3, Constants.IO_VB_GNU_COBOL, false);
	}
	
	public void testVB4() throws Exception {
		
		tst(4, Constants.IO_BIN_TEXT, false);
	}
	
	
	
	public void testVB21() throws Exception {
		
		tst(21, Constants.IO_VB, true);
	}
	
	public void testVB22() throws Exception {
		
		tst(22, Constants.IO_VB_FUJITSU, true);
	}
	
	public void testVB23() throws Exception {
		
		tst(23, Constants.IO_VB_GNU_COBOL, true);
	}
	
	
	public void testVB24() throws Exception {
		
		tst(24, Constants.IO_BIN_TEXT, true);
	}

	
	public void tst(int testNo, int structure, boolean check) throws Exception {
		Properties prop = Parameters.getInitialisedProperties();
		prop.put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		prop.put(Parameters.PROPERTY_BIG_FILE_LARGE_VB, "T");
		Common.OPTIONS.doCompress.set(false);
		
		
		TstLargeListVB60 main = new TstLargeListVB60(structure);
		
		for (int i = 0; i < 40; i ++) {
			main.tstMultipleChanges(check, "VB " + testNo + ".1." + i + " ", i * 10 + 1217 + structure, 0, 15 + i);
		}

		 main = new TstLargeListVB60(structure);
		
		for (int i = 0; i < 15; i ++) {
			main.tstMultipleChanges(check, "VB " + testNo + ".2." + i + " ", i * 10 + 1417 + structure, 0, 15 + i);
		}
	
		main = new TstLargeListVB60(structure);
		
		for (int i = 0; i < 5; i ++) {
			main.tstMultipleChanges(check, "VB " + testNo + ".3." + i + " ", i * 10 + 1517 + structure, 0, 15 + i);
		}
	}
}
