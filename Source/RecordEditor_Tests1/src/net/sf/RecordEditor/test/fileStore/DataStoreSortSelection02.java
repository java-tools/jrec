package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreSortSelection02 extends TestCase {
	TestDataStoreSort tst = new TestDataStoreSort();


	public void testFixed() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_FL_FILE, true);
		tst.tstSortSelection("Normal file & Large-View", FileAndView.LARGE_FL_FILE, false);	
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void testVB() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_VL_FILE, true);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testChar() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_CHAR_FILE, true);
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_CHAR_FILE, false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
}
