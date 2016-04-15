package net.sf.RecordEditor.test.text;

import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;


/**
 * Basic Checks on DataStoreContent class
 *
 * @author Bruce Martin
 *
 */
public class TstDSContent01Char extends TestCase {

	TstDSContent01_Common tst = new TstDSContent01_Common();
	int dsType = TstConstants.DS_CHAR;
	public void testCreatePosition() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstCreatePosition01(dsType);
	}
	
	
	public void testCreatePosition02() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstCreatePosition02(dsType, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void testGetLinePosition() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstGetLinePosition(dsType, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstGetLinePosition(dsType, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void testLength() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstLength(dsType, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstLength(dsType, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testNumberOfLines() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstNumberOfLines(dsType, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstNumberOfLines(dsType, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}

	public void testInsertString() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstInsertString(dsType);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}




	public void testInsertString2() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstInsertString2(dsType);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void testRemove1() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstRemove1(dsType);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void testRemove2() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstRemove2(dsType);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}



	public void testGetString() throws Exception{
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstGetString(dsType, TstConstants.SIZE_SMALL);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstGetString(dsType, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
	}


	public void testInsertDelete() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		tst.tstInsertDelete(dsType, TstConstants.SIZE_SMALL);
	}


	public void testInsertDelete02() throws Exception {
		Parameters.setSavePropertyChanges(false);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		tst.tstInsertDelete(dsType, TstConstants.SIZE_LARGE);
		Parameters.setProperty(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "");
		
		
	}
//	public void testTableChanged() {
//		fail("Not yet implemented");
//	}

}
