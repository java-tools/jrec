package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreSortSelection01 extends TestCase {
	TestDataStoreSort tst = new TestDataStoreSort();

	
	
	public void testNormal() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstSortSelection("Normal (file & View)", FileAndView.NORMAL_FILE, true);
		tst.tstSortSelection("Normal file & Large-View", FileAndView.NORMAL_FILE, false);	
	}

	
	public void testFixed() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_FL_FILE, true);
		tst.tstSortSelection("Normal file & Large-View", FileAndView.LARGE_FL_FILE, false);	
	}

	
	public void testVB() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_VL_FILE, true);
	}

	
	public void testChar() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstSortSelection("Fixed  file & Normal-View", FileAndView.LARGE_CHAR_FILE, true);
	}

}
