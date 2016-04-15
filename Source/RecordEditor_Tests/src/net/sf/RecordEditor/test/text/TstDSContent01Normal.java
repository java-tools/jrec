package net.sf.RecordEditor.test.text;

import net.sf.RecordEditor.test.TstConstants;

import junit.framework.TestCase;


/**
 * Basic Checks on DataStoreContent class
 *
 * @author Bruce Martin
 *
 */
public class TstDSContent01Normal extends TestCase {

	TstDSContent01_Common tst = new TstDSContent01_Common();
	int dsType = TstConstants.DS_NORMAL;
	
	public void testCreatePosition() throws Exception {
		tst.tstCreatePosition01(dsType);
	}
	
	public void testCreatePosition02() throws Exception {
		tst.tstCreatePosition02(dsType, TstConstants.SIZE_SMALL);
		tst.tstCreatePosition02(dsType, TstConstants.SIZE_LARGE);
	}

	public void testGetLinePosition() throws Exception {
		tst.tstGetLinePosition(dsType, TstConstants.SIZE_SMALL);
		tst.tstGetLinePosition(dsType, TstConstants.SIZE_LARGE);
	}


	public void testLength() throws Exception {
		tst.tstLength(dsType, TstConstants.SIZE_SMALL);
		tst.tstLength(dsType, TstConstants.SIZE_LARGE);
	}

	public void testNumberOfLines() throws Exception {
		tst.tstNumberOfLines(dsType, TstConstants.SIZE_SMALL);
		tst.tstNumberOfLines(dsType, TstConstants.SIZE_LARGE);
	}

	public void testInsertString() throws Exception {
		tst.tstInsertString(dsType);
	}




	public void testInsertString2() throws Exception {
		tst.tstInsertString2(dsType);
	}


	public void testRemove1() throws Exception {
		tst.tstRemove1(dsType);
	}


	public void testRemove2() throws Exception {
		tst.tstRemove2(dsType);
	}



	public void testGetString() throws Exception{
		tst.tstGetString(dsType, TstConstants.SIZE_SMALL);
		tst.tstGetString(dsType, TstConstants.SIZE_LARGE);
	}

	public void testInsertDelete() throws Exception {
		tst.tstInsertDelete(dsType, TstConstants.SIZE_LARGE);
	}
//	public void testTableChanged() {
//		fail("Not yet implemented");
//	}

}
