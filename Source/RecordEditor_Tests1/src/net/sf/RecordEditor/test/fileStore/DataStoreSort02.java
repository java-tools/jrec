package net.sf.RecordEditor.test.fileStore;

import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreSort02 extends TestCase {
	TestDataStoreSort tst = new TestDataStoreSort();

	
	public void testFixed() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");

		tst.tstSortMain("Fixed file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, true));
		tst.tstSortMain("Fixed File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, false));
		tst.tstSortView("Fixed file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, true));
		tst.tstSortView("Fixed File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_FL_FILE, false));
		
		tst.tstSort("Fixed Single Sort", FileAndView.LARGE_FL_FILE);
		
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testVb() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");

		tst.tstSortMain("VB file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, true));
		tst.tstSortMain("VB File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, false));
		tst.tstSortView("VB file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, true));
		tst.tstSortView("VB File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_VL_FILE, false));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	
	public void testChar() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");

		tst.tstSortMain("Char file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, true));
		tst.tstSortMain("Char File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, false));
		tst.tstSortView("Char file & Normal-View", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, true));
		tst.tstSortView("Char File & large-view", new FileAndView(TestCode.schema, FileAndView.LARGE_CHAR_FILE, false));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
}
