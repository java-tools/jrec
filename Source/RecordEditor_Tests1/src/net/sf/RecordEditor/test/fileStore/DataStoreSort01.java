package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreSort01 extends TestCase {
	TestDataStoreSort tst = new TestDataStoreSort();
	
	public void testNormal() {
		tst.tstSortMain("Normal (file & View)", new FileAndView(TestCode.schema, FileAndView.NORMAL_FILE, true));
		tst.tstSortMain("Normal File largeview)", new FileAndView(TestCode.schema, FileAndView.NORMAL_FILE, false));
		tst.tstSortView("Normal (file & View)", new FileAndView(TestCode.schema, FileAndView.NORMAL_FILE, true));
		tst.tstSortView("Normal File largeview)", new FileAndView(TestCode.schema, FileAndView.NORMAL_FILE, false));
		
		tst.tstSort("Normal Single Sort", FileAndView.NORMAL_FILE);
	}
	
	
	public void testSingleFile() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstSort("Normal Single Sort", FileAndView.NORMAL_FILE);
		tst.tstSort("Fixed Single Sort", FileAndView.LARGE_FL_FILE);
	}
	
	
	public void testFixed() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.tstSortMain("Fixed file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, true));
		tst.tstSortMain("Fixed File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, false));
		tst.tstSortView("Fixed file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, true));
		tst.tstSortView("Fixed File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, false));
	}

	
	public void testVb() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.tstSortMain("VB file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, true));
		tst.tstSortMain("VB File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, false));
		tst.tstSortView("VB file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, true));
		tst.tstSortView("VB File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, false));
	}
	
	
	public void testChar() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.tstSortMain("Char file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, true));
		tst.tstSortMain("Char File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, false));
		tst.tstSortView("Char file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, true));
		tst.tstSortView("Char File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, false));
	}
}
