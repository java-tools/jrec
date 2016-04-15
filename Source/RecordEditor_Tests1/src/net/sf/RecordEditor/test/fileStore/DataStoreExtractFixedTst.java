package net.sf.RecordEditor.test.fileStore;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.test.largeFileView.FileAndView;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class DataStoreExtractFixedTst extends TestCase {


	public final static LayoutDetail schema = StdSchemas.TWENTY_BYTE_RECORD_SCHEMA;
	

	private TestDataStoreExtract tst = new TestDataStoreExtract();
	
	
	public void testFixedDataStore01() {
		
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Fixed 1", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 4", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 5", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 6", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.LARGE_CHAR_FILE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	public void testFixedDataStore11() {
		
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Fixed 11", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 14", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 15", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 16", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.LARGE_CHAR_FILE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testFixedDataStore21() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.doTest1("Fixed 21", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 24", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 25", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 26", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.LARGE_CHAR_FILE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	public void testFixedDataStore31() {
		
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.doTest1("Fixed 31", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 34", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 35", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 36", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, false, FileAndView.LARGE_CHAR_FILE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	public void testFixedDataStore41() {
		
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.doTest1("Fixed 41", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 44", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 45", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 46", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, true, true, FileAndView.LARGE_CHAR_FILE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testFixedDataStore51() {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.doTest1("Fixed 51", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 54", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 55", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 56", new ExtractTestData1(schema, FileAndView.LARGE_FL_FILE, false, true, FileAndView.LARGE_CHAR_FILE));
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testFixedDataStore61() {
		
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");

		tst.doTest1("Fixed 61", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 62", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 63", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 64", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.LARGE_CHAR_FILE));

		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	public void testFixedDataStore71() {
		
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");

		tst.doTest1("Fixed 71", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.NORMAL_FILE));
		tst.doTest1("Fixed 72", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.LARGE_FL_FILE));
		tst.doTest1("Fixed 73", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.LARGE_VL_FILE));
		tst.doTest1("Fixed 74", new ExtractTestData2(schema, FileAndView.LARGE_FL_FILE, FileAndView.LARGE_CHAR_FILE));

		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

}
