package net.sf.RecordEditor.test.largeFileView;

import junit.framework.TestCase;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;


/**
 * This class testing updating views / mainfile and checking view main file are updated correctly
 * for variable length block file
 * 
 * @author Bruce
 *
 */
public class TstVbView1 extends TestCase {

	public static LayoutDetail schema = ViewTest.CSV_DBL_BYTE_SCHEMA;
	private ViewTest tst1 = new ViewTest();
	private boolean checkHeldLines;

	
	
	// This could fail in lines check 
	public void testUpdateFile1()  throws RecordException{
		checkHeldLines = false;
		tstLvA("Update Parent File 4", ViewTestData.UPD_PARENT, 0);
	}

	
	public void testUpdateView1() throws RecordException {
		checkHeldLines = false;
		tstLvA("Update View 5", ViewTestData.UPD_VIEW, 100);
	}
	

	// This could fail in lines check 
	public void testUpdateFile2()  throws RecordException{
		checkHeldLines = true;
		tstLvA("Update Parent File 4", ViewTestData.UPD_PARENT, 0);
	}

	
	public void testUpdateView2() throws RecordException {
		checkHeldLines = true;
		Common.OPTIONS.doCompress.set(false);
		tstLvA("Update View 5", ViewTestData.UPD_VIEW, 100);
	}
	
	

	// This could fail in lines check 
	public void testUpdateFile3()  throws RecordException{
		checkHeldLines = false;
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstLvA("Update Parent File 4", ViewTestData.UPD_PARENT, 0);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testUpdateView3() throws RecordException {
		checkHeldLines = false;
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstLvA("Update View 5", ViewTestData.UPD_VIEW, 100);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void tstLvA(String id, int updateType, int inc) throws RecordException {
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst1.setDataLines(ViewTest.loadDataLines(new AbstractLine[500], schema));
		
		tstA(id + ".1.2 ", updateType, 112 + inc, 0, 100);

		tstA(id + ".2.1 ", updateType,  11217  + inc, 0, 800);
		
		tstA(id + ".3.1 ", updateType,  System.nanoTime() + 1217, 0, 800);
	}
	
	
	private void tstA(String desc, int updateType, long seed, int firstLine, int numActions) {
		Common.OPTIONS.agressiveCompress.set(false);
		
		tst1.doTest(new ViewTestData(schema, FileAndView.LARGE_VL_FILE, true, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, schema.getMaximumRecordLength());
		tst1.doTest(new ViewTestData(schema, FileAndView.LARGE_VL_FILE, false, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, schema.getMaximumRecordLength());
	}
}
