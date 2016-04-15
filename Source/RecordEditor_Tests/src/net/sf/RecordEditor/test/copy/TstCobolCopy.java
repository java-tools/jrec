package net.sf.RecordEditor.test.copy;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Numeric.ICopybookDialects;

public class TstCobolCopy extends TestCase {

	private int[] dialect = {ICopybookDialects.FMT_MAINFRAME,  ICopybookDialects.FMT_OPEN_COBOL, ICopybookDialects.FMT_OC_MICRO_FOCUS, ICopybookDialects.FMT_FUJITSU};
	private String[] fonts = {"Cp037", "", "", ""};
	private String[] copybooks = {"cpyComp.cbl", "cpyComp3.cbl", "cpyComp5.cbl"};
	
	 public void testMainframeCopy()  {
		 tstDialect(ICopybookDialects.FMT_MAINFRAME, "cp037");
	 }
	 
		
	 public void testOCCopy()  {
		 tstDialect(ICopybookDialects.FMT_OPEN_COBOL, "");
	 }

		
	 public void testMFCopy()  {
		 tstDialect(ICopybookDialects.FMT_OC_MICRO_FOCUS, "");
	 }

		
	 public void testFjCopy()  {
		 tstDialect(ICopybookDialects.FMT_FUJITSU, "");
	 }

	 private void tstDialect(int binFormat, String font)  {
		 int i, j;
		 
		 System.out.println();
		 System.out.println("-----------------------------------------------------------------------");
		 System.out.println(" ---    Testing " + ConversionManager.getInstance().getConverter(binFormat).getName());
		 System.out.println("-----------------------------------------------------------------------");
		 try {
		 for (i = 0; i < dialect.length; i++) {
			 if (dialect[i] != binFormat) {
				 for (j=0; j < copybooks.length; j++) {
					 Code.copyAndCompare(copybooks[j], false, binFormat, "iFile" + i + ".bin", font, dialect[i], "oFile" + i  + ".bin", fonts[i]);
				 }
			 }
		 }
		 } catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error see trapped error");
		}
	 }
}
