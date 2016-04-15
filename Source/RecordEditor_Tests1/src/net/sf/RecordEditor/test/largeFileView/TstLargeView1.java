package net.sf.RecordEditor.test.largeFileView;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Options;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class TstLargeView1 extends TestCase {

	public static LayoutDetail dtar020 = ViewTest.DTAR020;
	private ViewTest tst1 = new ViewTest();
	private boolean checkHeldLines;
	
	
	public void testUpdateFile() {
		checkHeldLines = false;
		tstLv("Update Parent File 1", ViewTestData.UPD_PARENT);
	}

	
	public void testUpdateView() {
		checkHeldLines = false;
		tstLv("Update View 2", ViewTestData.UPD_VIEW);
	}
	
	public void testUpdateBoth() {
		checkHeldLines = false;
		tstLv("Update Both 3", ViewTestData.UPD_BOTH);
	}

	
	// This could fail in lines check 
	public void testUpdateFile1() {
		checkHeldLines = true;
		tstLvA("Update Parent File 4", ViewTestData.UPD_PARENT, 0);
	}

	
	public void testUpdateView1() {
		checkHeldLines = true;
		tstLvA("Update View 5", ViewTestData.UPD_VIEW, 100);
	}
	
	
	


	
	public void testUpdateVbFile() {
		checkHeldLines = false;
		String desc = "Update VB Parent File";
		int seed= 25;
		int updateType = ViewTestData.UPD_PARENT;
		
		System.out.println("LayoutDetail: " 
				+ dtar020.getFileStructure() 
				+ " " + dtar020.getOption(Options.OPT_STORAGE_TYPE));
		tst1.doTest(new ViewTestData(FileAndView.LARGE_VL_FILE, true, updateType), 
				desc, checkHeldLines, seed, 0, 1600, dtar020.getMaximumRecordLength());
		tst1.doTest(new ViewTestData(FileAndView.LARGE_VL_FILE, false, updateType), 
				desc, checkHeldLines, seed, 0, 1600, dtar020.getMaximumRecordLength());
		
		
		desc = "Update VB View";
		seed= 27;
		updateType = ViewTestData.UPD_VIEW;
		
		tst1.doTest(new ViewTestData(FileAndView.LARGE_VL_FILE, true, updateType), 
				desc, checkHeldLines, seed, 0, 1600, dtar020.getMaximumRecordLength());
		tst1.doTest(new ViewTestData(FileAndView.LARGE_VL_FILE, false, updateType), 
				desc, checkHeldLines, seed, 0, 1600, dtar020.getMaximumRecordLength());

	}

	
	public void testUpdateFile1a() {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		
		checkHeldLines = true;
		tstLvA("Update Parent File 4", ViewTestData.UPD_PARENT, 600);
		
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	
	public void testUpdateView1a() {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		
		checkHeldLines = true;
		tstLvA("Update View 5", ViewTestData.UPD_VIEW, 700);
		
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	// This could fail in lines check 
	public void testUpdateBoth1() {
		String id = "Update Both 6";
		int updateType = ViewTestData.UPD_BOTH;

		checkHeldLines = true;

		tstLvA(id, updateType, 200);

	}
	
	public void tstLv(String id, int updateType) {

		tst(id + ".1.1 ", updateType, 1, 0, 200);
		tst(id + ".1.2 ", updateType, 12, 0, 200);

		tst(id + ".2.1 ", updateType,  1217, 0, 1600);
		
		tst(id + ".3.1 ", updateType,  System.nanoTime() + 1217, 0, 800);
	}

	public void tstLvA(String id, int updateType, int inc) {

		tstA(id + ".1.2 ", updateType, 112 + inc, 0, 100);

		tstA(id + ".2.1 ", updateType,  11217  + inc, 0, 800);
		
		tstA(id + ".3.1 ", updateType,  System.nanoTime() + 1217, 0, 800);
	}
	
	
	private void tst(String desc, int updateType, long seed, int firstLine, int numActions) {
		

		tst1.doTest(new ViewTestData(true, false, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, dtar020.getMaximumRecordLength());
		tst1.doTest(new ViewTestData(false, true, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, dtar020.getMaximumRecordLength());
		tst1.doTest(new ViewTestData(false, false, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, dtar020.getMaximumRecordLength());
	}
	


	private void tstA(String desc, int updateType, long seed, int firstLine, int numActions) {

		tst1.doTest(new ViewTestData(false, true, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, dtar020.getMaximumRecordLength());
		tst1.doTest(new ViewTestData(false, false, updateType), 
				desc, checkHeldLines, seed, firstLine, numActions, dtar020.getMaximumRecordLength());
		
		//tst(lv, desc, seed, firstLine, numActions, dtar020.getMaximumRecordLength());
	}
}
