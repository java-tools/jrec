package net.sf.RecordEditor.test.largeFileView;

import junit.framework.TestCase;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.filestorage1.Code;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.DataStoreStd;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;
import net.sf.RecordEditor.utils.params.Parameters;

/**
 * This class tests paste in various File/View combinations.
 * 
 * 
 * @author Bruce
 *
 */
public class TstPaste extends TestCase {

	private static final int START = -1;
	private static final int END = -2;
	private static final int END_MINUS_16 = -16;
	private static final int END_MINUS_1 = -3;
	private static final int HALF_WAY = -4;
	private static final LayoutDetail SCHEMA = ViewTest.DTAR020;
	
	private static int SMALL =  1;
	private static int STANDARD =  2;
	private static int EXTRA_LARGE =  3;
	
	AbstractLine[] datalines;
	
	
	public void testNormalNormal()  throws RecordException {
		tstNormalFile(FileAndView.NORMAL_FILE);
	}
	
	public void testNormalFL()  throws RecordException {
		tstNormalFile(FileAndView.LARGE_FL_FILE);
	}

	public void testNormalVL()  throws RecordException {
		tstNormalFile(FileAndView.LARGE_VL_FILE);
	}

	public void testFixedNormal()  throws RecordException {
		tstFbFile(FileAndView.NORMAL_FILE);
	}
	
	public void testFixedFL()  throws RecordException  {
		tstFbFile(FileAndView.LARGE_FL_FILE);
	}

	public void testFixedVL()  throws RecordException  {
		tstFbFile(FileAndView.LARGE_VL_FILE);
	}
	

	public void testVlNormal()  throws RecordException  {
		tstVbFile(FileAndView.NORMAL_FILE);
	}
	
	public void testVlFL()  throws RecordException  {
		tstVbFile(FileAndView.LARGE_FL_FILE);
	}

	public void testVlVL()  throws RecordException  {
		tstVbFile(FileAndView.LARGE_VL_FILE);
	}


	public void testCharNormal()  throws RecordException {
		tstCharFile(FileAndView.NORMAL_FILE);
	}

	public void testCharChar()  throws RecordException  {
		tstCharFile(FileAndView.LARGE_CHAR_FILE);
	}
	/* -------------------------------------------------- */

	public void testFixedNormalSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		tstFbFile(FileAndView.NORMAL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	public void testFixedFLSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstFbFile(FileAndView.LARGE_FL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testFixedVLSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstFbFile(FileAndView.LARGE_VL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
 	public void testVlNormalSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstVbFile(FileAndView.NORMAL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	public void testVlFLSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstVbFile(FileAndView.LARGE_FL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testVlVLSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);
		
		tstVbFile(FileAndView.LARGE_VL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}
	
	

	public void testCharNormalSmallChunk()  throws RecordException {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.doCompress.set(false);

		tstCharFile(FileAndView.NORMAL_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testCharCharSmallChunk()  throws RecordException  {
		Parameters.setSavePropertyChanges(false);
		String p  = Parameters.getString(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);

		tstCharFile(FileAndView.LARGE_CHAR_FILE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	/* ------------------------------------------------------------------------------------------- */
	
	public void tstNormalFile(int pasteSrcType)  throws RecordException  {
		Common.OPTIONS.agressiveCompress.set(false);
		doTstNormalFile("Normal Test Start ", pasteSrcType, START);
		doTstNormalFile("Normal Test 0 ", pasteSrcType, 0);
		doTstNormalFile("Normal Test 1 ", pasteSrcType, 1);
		doTstNormalFile("Normal Test 5 ", pasteSrcType, 5);
		doTstNormalFile("Normal Test Half-Way ", pasteSrcType, HALF_WAY);
		doTstNormalFile("Normal Test End-1 ", pasteSrcType, END_MINUS_1);
		doTstNormalFile("Normal Test End ", pasteSrcType, END);
	}


	public void tstFbFile(int pasteSrcType)  throws RecordException  {
		Common.OPTIONS.agressiveCompress.set(false);
		doTstFbFile("Fixed Test tart ", pasteSrcType, START);
		for (int i = 0; i < 60; i++) {
			doTstFbFile("Fixed Test " + i + " ", pasteSrcType, i);
		}
//		doTstFbFile("Fixed Test 0 ", pasteSrcType, 0);
//		doTstFbFile("Fixed Test 1 ", pasteSrcType, 1);
//		doTstFbFile("Fixed Test 5 ", pasteSrcType, 5);
//		doTstFbFile("Fixed Test 15 ", pasteSrcType, 15);
//		doTstFbFile("Fixed Test 16 ", pasteSrcType, 16);
//		doTstFbFile("Fixed Test 17 ", pasteSrcType, 17);
		doTstFbFile("Fixed Test Half-Way ", pasteSrcType, HALF_WAY);
		doTstFbFile("Fixed Test End-16 ", pasteSrcType, END_MINUS_16);
		doTstFbFile("Fixed Test End-1 ", pasteSrcType, END_MINUS_1);
		doTstFbFile("Fixed Test End ", pasteSrcType, END);
	}


	public void tstVbFile(int pasteSrcType)  throws RecordException  {
		Common.OPTIONS.agressiveCompress.set(false);
		doTstVbFile("Variable-Length Test Start ", pasteSrcType, START);
		for (int i = 0; i < 60; i++) {
			doTstVbFile("Variable-Length Test " + i + " ", pasteSrcType, i);
		}
//		doTstVbFile("Variable-Length Test 0 ", pasteSrcType, 0);
//		doTstVbFile("Variable-Length Test 1 ", pasteSrcType, 1);
//		doTstVbFile("Variable-Length Test 5 ", pasteSrcType, 5);
//		doTstVbFile("Variable-Length Test 7 ", pasteSrcType, 7);
//		doTstVbFile("Variable-Length Test 8 ", pasteSrcType, 8);
//		doTstVbFile("Variable-Length Test 9 ", pasteSrcType, 9);
		doTstVbFile("Variable-Length Test Half-Way ", pasteSrcType, HALF_WAY);
		doTstVbFile("Variable-Length Test End-16 ", pasteSrcType, END_MINUS_16);
		doTstVbFile("Variable-Length Test End-1 ", pasteSrcType, END_MINUS_1);
		doTstVbFile("Variable-Length Test End ", pasteSrcType, END);
	}



	public void tstCharFile(int pasteSrcType)  throws RecordException  {
		Common.OPTIONS.agressiveCompress.set(false);
		doTstCharFile("Char Test Start ", pasteSrcType, START);
		for (int i = 0; i < 60; i++) {
			doTstCharFile("Char Test " + i + " ", pasteSrcType, i);
		}
//		doTstCharFile("Char Test 0 ", pasteSrcType, 0);
//		doTstCharFile("Char Test 1 ", pasteSrcType, 1);
//		doTstCharFile("Char Test 5 ", pasteSrcType, 5);
//		doTstCharFile("Char Test 15 ", pasteSrcType, 15);
//		doTstCharFile("Char Test 16 ", pasteSrcType, 16);
//		doTstCharFile("Char Test 17 ", pasteSrcType, 17);
//		doTstCharFile("Char Test 60 ", pasteSrcType, 60);
		doTstCharFile("Char Test Half-Way ", pasteSrcType, HALF_WAY);
		doTstCharFile("Char Test End-16 ", pasteSrcType, END_MINUS_16);
		doTstCharFile("Char Test End-1 ", pasteSrcType, END_MINUS_1);
		doTstCharFile("Char Test End ", pasteSrcType, END);
	}
	
	
	private void doTstNormalFile(String desc, int pasteSrcType, int idx)  throws RecordException  {
		
		setCpySrc(false, pasteSrcType);
		doTst(desc, new ViewTestData(true, true, ViewTestData.UPD_PARENT), false, pasteSrcType, idx);
		doTst(desc, new ViewTestData(true, true, ViewTestData.UPD_VIEW), false, pasteSrcType, idx);

		doTst(desc, new ViewTestData(true, false, ViewTestData.UPD_PARENT), false, pasteSrcType, idx);
		doTst(desc, new ViewTestData(true, false, ViewTestData.UPD_VIEW), false, pasteSrcType, idx);

		setCpySrc(true, pasteSrcType);
		doTst(desc, new ViewTestData(true, true, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(true, true, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);

		doTst(desc, new ViewTestData(true, false, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(true, false, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);
	}


	private void doTstFbFile(String desc, int pasteSrcType, int idx)  throws RecordException  {

		setCpySrc(false, pasteSrcType);
		doTst(desc, new ViewTestData(false, true, ViewTestData.UPD_PARENT), false, pasteSrcType, idx);
		doTst(desc, new ViewTestData(false, true, ViewTestData.UPD_VIEW), false, pasteSrcType, idx);

		doTst(desc, new ViewTestData(false, false, ViewTestData.UPD_PARENT), false, pasteSrcType, idx);
		doTst(desc, new ViewTestData(false, false, ViewTestData.UPD_VIEW), false, pasteSrcType, idx);
		
		setCpySrc(true, pasteSrcType);
		doTst(desc, new ViewTestData(false, true, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(false, true, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);

		doTst(desc, new ViewTestData(false, false, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(false, false, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);
	}


	private void doTstVbFile(String desc, int pasteSrcType, int idx)  throws RecordException  {
		doTstFile(desc, ViewTest.DTAR020, pasteSrcType, FileAndView.LARGE_VL_FILE, idx);
	}
	

	private void doTstCharFile(String desc, int pasteSrcType, int idx)  throws RecordException  {
		doTstFile(desc, ViewTest.CSV_DBL_BYTE_SCHEMA, pasteSrcType, FileAndView.LARGE_CHAR_FILE, idx);
	}
	
	private static long rtime = System.nanoTime();
	private void doTstFile(String desc, LayoutDetail schema, int pasteSrcType, int fileType, int idx) throws RecordException {

		System.out.print("--> " + desc + " ~ " + ((System.nanoTime()-rtime) / 1000000));
		rtime = System.nanoTime();

		setCpySrc(schema, false, pasteSrcType);
		doTst(desc, new ViewTestData(schema, fileType, true, ViewTestData.UPD_PARENT), false, pasteSrcType, idx);
		doTst(desc, new ViewTestData(schema, fileType, true, ViewTestData.UPD_VIEW), false, pasteSrcType, idx);

		doTst(desc, new ViewTestData(schema, fileType, false, ViewTestData.UPD_PARENT), false, pasteSrcType, idx);
		doTst(desc, new ViewTestData(schema, fileType, false, ViewTestData.UPD_VIEW), false, pasteSrcType, idx);
		
		setCpySrc(schema, true, pasteSrcType);
		doTst(desc, new ViewTestData(schema, fileType, true, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(schema, fileType, true, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);

		doTst(desc, new ViewTestData(schema, fileType, false, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(schema, fileType, false, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);

		setCpySrc(schema, EXTRA_LARGE, pasteSrcType);
		System.out.print("\t" +  ((System.nanoTime()-rtime) / 1000000));
		long xtime = System.nanoTime();
		doTst(desc, new ViewTestData(schema, fileType, true, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(schema, fileType, true, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);

		doTst(desc, new ViewTestData(schema, fileType, false, ViewTestData.UPD_PARENT), true, pasteSrcType, idx);
		doTst(desc, new ViewTestData(schema, fileType, false, ViewTestData.UPD_VIEW), true, pasteSrcType, idx);
		System.out.println("\t" +  ((System.nanoTime()-xtime) / 1000000));
	}
	
	private void doTst(String desc, ViewTestData fv, boolean largePaste, int pasteSrcType, int idx) {
		
//		System.out.println("--> " + desc + datalines.length + " " + largePaste );
		for (int i =0; i < 3; i++) {
			for (int j = 0; j < datalines.length; j++) {
				fv.baselineFV.viewFile.addLine(j, datalines[j].getNewDataLine());
				fv.testFV.viewFile.addLine(j, datalines[j].getNewDataLine());
			}
		}
		
		int pos = idx;
		
		switch (idx) {
		case HALF_WAY : pos = datalines.length; break;
		case END_MINUS_16 : pos = Math.max(0, fv.baseLineFile.length - 16);
		case END_MINUS_1 : pos = fv.baseLineFile.length - 1;
		case END : pos = fv.baseLineFile.length;
		}
		
		fv.baseLineFile[0].pasteLines(pos);
	
		fv.testFile[0].pasteLines(pos);
		
		
		TestCase.assertTrue(desc + " " + idx, Code.check("paste 1: " + idx, fv.baselineFV.parentStore, fv.testFV.parentStore));
		TestCase.assertTrue(desc + " " + idx, Code.check("paste 2: " + idx, fv.baselineFV.viewStore,   fv.testFV.viewStore));
		
		if (fv.baseLineFile[0].getRowCount() > pos + 9) {
			fv.baseLineFile[0].pasteLines(pos);
			
			fv.testFile[0].pasteLines(pos);
			
			
			TestCase.assertTrue(desc + " " + idx, Code.check("paste 1: " + idx, fv.baselineFV.parentStore, fv.testFV.parentStore));
			TestCase.assertTrue(desc + " " + idx, Code.check("paste 2: " + idx, fv.baselineFV.viewStore,   fv.testFV.viewStore));
		}
	}
	
	
	private void setCpySrc(boolean largePaste, int pasteSrcType) throws RecordException {
		setCpySrc(SCHEMA, largePaste, pasteSrcType);
	}
	
	
	private void setCpySrc(LayoutDetail schema, boolean largePaste, int pasteSrcType) throws RecordException {
		if (largePaste) {
			setCpySrc(schema, STANDARD, pasteSrcType);
		} else {
			setCpySrc(schema, SMALL, pasteSrcType);
		}
	}
	
	
	private void setCpySrc(LayoutDetail schema, int pasteSize, int pasteSrcType) throws RecordException {
	
		boolean largePaste = pasteSize != SMALL;
		int largeSize = 500;
		if (pasteSize == EXTRA_LARGE) {
			largeSize = 2000;
		}
		
		datalines = ViewTest.loadDataLines(new AbstractLine[largeSize], schema);
		if (largePaste) {
			switch (pasteSrcType) {
			case FileAndView.NORMAL_FILE: addLargePasteSrc(DataStoreStd.newStore(schema)); break;
			case FileAndView.LARGE_FL_FILE: addLargePasteSrc(new DataStoreLarge(schema, FileDetails.FIXED_LENGTH, schema.getMaximumRecordLength()));	break;
			case FileAndView.LARGE_VL_FILE: addLargePasteSrc(new DataStoreLarge(schema, FileDetails.VARIABLE_LENGTH, schema.getMaximumRecordLength())); break;
			case FileAndView.LARGE_CHAR_FILE: 
				addLargePasteSrc(new DataStoreLarge(schema, FileDetails.CHAR_LINE, schema.getMaximumRecordLength()));	
			break;
			}
		} else {
			switch (pasteSrcType) {
			case FileAndView.NORMAL_FILE: addSmallPasteSrc(DataStoreStd.newStore(schema)); break;
			case FileAndView.LARGE_FL_FILE: addSmallPasteSrc(new DataStoreLarge(schema, FileDetails.FIXED_LENGTH, schema.getMaximumRecordLength()));	break;
			case FileAndView.LARGE_VL_FILE: addSmallPasteSrc(new DataStoreLarge(schema, FileDetails.VARIABLE_LENGTH, schema.getMaximumRecordLength()));	break;
			case FileAndView.LARGE_CHAR_FILE: 
				addSmallPasteSrc(new DataStoreLarge(schema, FileDetails.CHAR_LINE, schema.getMaximumRecordLength()));	
			break;
			}
		}

	}
	
	
	private void addLargePasteSrc(IDataStore<AbstractLine> copySrc) {
		
		for (AbstractLine l : datalines) {
			copySrc.add(l);
		}
		FileView.setCopyRecords(copySrc);
	}
	
	
	private void addSmallPasteSrc(IDataStore<AbstractLine> copySrc) {
		
		for (int i = 5; i < 10; i++) {
			copySrc.add(datalines[i]);
		}
		FileView.setCopyRecords(copySrc);
	}

}
