package net.sf.RecordEditor.test.fileStore;

import junit.framework.TestCase;

import net.sf.RecordEditor.utils.fileStorage.FileDetails;



public class DataStoreAddCopy01 extends TestCase {
	
	private TestDataStoreAddCopy.IDataStoreCreator charCreator     = new TestDataStoreAddCopy.DataStoreCreator(FileDetails.CHAR_LINE);
	private TestDataStoreAddCopy.IDataStoreCreator fixedCreator    = new TestDataStoreAddCopy.DataStoreCreator(FileDetails.FIXED_LENGTH);
	private TestDataStoreAddCopy.IDataStoreCreator variableCreator = new TestDataStoreAddCopy.DataStoreCreator(FileDetails.VARIABLE_LENGTH);
	private TestDataStoreAddCopy.IDataStoreCreator standardCreator
			= new TestDataStoreAddCopy.DataStoreCreator(TestDataStoreAddCopy.STANDARD_STORAGE);
	private TestDataStoreAddCopy.IDataStoreCreator largeVbCreator
			= new TestDataStoreAddCopy.DataStoreCreator(TestDataStoreAddCopy.LARGE_VB);
	
	
	public TestDataStoreAddCopy tst = new TestDataStoreAddCopy();
	


	public void testChar1() {
		tst.doTst("Char 1 ~", false, charCreator);
	}

	
	public void testFixed1() {
		tst.doTst("Fixed 1 ~", false, fixedCreator);
	}
	
	public void testVariable1() {
		tst.doTst("VB 1 ~", false,  variableCreator);
	}
	
	public void testlargVariable1() {
		tst.doTst("LargeVB 1 ~", false,  largeVbCreator);
	}
	
	public void testStandard1() {
		tst.doTst("VB 1 ~", false,  standardCreator);
	}

	public void testChar2() {
		tst.doTst("Char 2 ~", true, charCreator);
	}
	
	public void testFixed2() {
		tst.doTst("Fixed 2 ~", true, fixedCreator);

	}
	
	public void testVariable2() {
		tst.doTst("VB 2 ~", true, variableCreator);
	}
	
	public void testLargeVariable2() {
		tst.doTst("Large VB 2 ~", true, largeVbCreator);
	}

}
