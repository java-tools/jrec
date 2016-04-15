package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreExtractNormalTst extends TestCase {


	public final static LayoutDetail schema = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;
	

	private TestDataStoreExtract tst = new TestDataStoreExtract();
	
	public void testStdDataStore1() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Standard 1", new ExtractTestData1(schema, FileAndView.NORMAL_FILE, true, false, FileAndView.NORMAL_FILE));
	}
	
	public void testStdDataStore2() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Standard 2", new ExtractTestData1(schema, FileAndView.NORMAL_FILE, true, true, FileAndView.NORMAL_FILE));
	}
	
	public void testStdDataStore3() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Standard 3", new ExtractTestData1(schema, FileAndView.NORMAL_FILE, false, false, FileAndView.NORMAL_FILE));
	}
	
	public void testStdDataStore4() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Standard 4", new ExtractTestData1(schema, FileAndView.NORMAL_FILE, true, false, FileAndView.LARGE_FL_FILE));
	}

	public void testStdDataStore5() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Standard 5", new ExtractTestData1(schema, FileAndView.NORMAL_FILE, true, false, FileAndView.LARGE_VL_FILE));
	}

	public void testStdDataStore6() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Standard 6", new ExtractTestData1(schema, FileAndView.NORMAL_FILE, true, false, FileAndView.LARGE_CHAR_FILE));
	}
}
