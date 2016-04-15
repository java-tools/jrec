package net.sf.RecordEditor.test.fileStore;

import junit.framework.TestCase;

import net.sf.RecordEditor.utils.fileStorage.FileDetails;



public class DataStoreAddCopy03 extends TestCase {
	
	private TestDataStoreAddCopy.IDataStoreCreator charCreator     = new TestDataStoreAddCopy.DataStoreCreator2(FileDetails.FIXED_LENGTH, FileDetails.CHAR_LINE);
//	private TestDataStoreAddCopy.IDataStoreCreator fixedCreator    = new TestDataStoreAddCopy.DataStoreCreator2(FileDetails.FIXED_LENGTH, FileDetails.FIXED_LENGTH);
	private TestDataStoreAddCopy.IDataStoreCreator variableCreator = new TestDataStoreAddCopy.DataStoreCreator2(FileDetails.FIXED_LENGTH, FileDetails.VARIABLE_LENGTH);
	private TestDataStoreAddCopy.IDataStoreCreator standardCreator 
			= new TestDataStoreAddCopy.DataStoreCreator2(FileDetails.FIXED_LENGTH, TestDataStoreAddCopy.STANDARD_STORAGE);
	
	
	public TestDataStoreAddCopy tst = new TestDataStoreAddCopy();
	


	public void testChar1() {
		tst.doTst("Char 1 ~", false, charCreator);
	}

	
//	public void testFixed1() {
//		tst.doTst("Fixed 1 ~", false, fixedCreator);
//	}
	
	public void testVariable1() {
		tst.doTst("VB 1 ~", false,  variableCreator);
	}
	
	public void testStandard1() {
		tst.doTst("Standard 1 ~", false,  standardCreator);
	}

	public void testChar2() {
		tst.doTst("Char 2 ~", true, charCreator);
	}
	
//	public void testFixed2() {
//		tst.doTst("Fixed 2 ~", true, fixedCreator);
//	}
	
	public void testVariable2() {
		tst.doTst("VB 2 ~", true, variableCreator);
	}
	

	public void testStandard2() {
		tst.doTst("Standard 2 ~", true,  standardCreator);
	}


}
