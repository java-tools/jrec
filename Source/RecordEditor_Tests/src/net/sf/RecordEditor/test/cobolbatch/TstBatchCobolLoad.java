package net.sf.RecordEditor.test.cobolbatch;


import junit.framework.TestCase;
import net.sf.RecordEditor.layoutEd.BatchLoadCobol;

public class TstBatchCobolLoad extends TestCase {

	private Data testData = new Data();
	
	private String[] file2Prm = {
			"-f", Data.COBOL_DIRECTORY_1 + Data.COBOL_NAME_PREFIX + "2" + Data.COBOL_EXTENSION
	};
	private String[] dirPrm = {
			"-d", Data.COBOL_DIRECTORY_1
	};
	private String[] dirRexExpPrm = {
			"-d", Data.COBOL_DIRECTORY_1, "-r", "3.*"
	};

	
	public void testLoadSinglFile() throws Exception {
		
		testData.deleteCopybooks1();
		testData.writeCopybooks1();
		
		BatchLoadCobol.main(file2Prm);
	
		testData.checkCopybook2("Single File:");
		
		testData.deleteCopybooks1();
	}
	
	
	public void testLoadDirectory() throws Exception {
		
		testData.deleteCopybooks1();
		testData.writeCopybooks1();
		
		BatchLoadCobol.main(dirPrm);
	
		testData.checkCopybook1("Dir:");
		testData.checkCopybook2("Dir:");
		testData.checkCopybook3("Dir:");
		
		testData.deleteCopybooks1();
	}
	



	public void testLoadDirectoryRegExp() throws Exception {
		
		testData.deleteCopybooks1();
		testData.writeCopybooks1();
		
		BatchLoadCobol.main(dirRexExpPrm);
	
		testData.checkCopybook3("Dir Reg Exp:");
		
		testData.deleteCopybooks1();
	}

}

