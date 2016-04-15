package net.sf.RecordEditor.test.filestorage1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.Numeric.Convert;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class TstLargeListSplit extends TestCase {

	private static Line[] dataLines = new Line[500];
	private static LayoutDetail dtar020;
	//private ArrayList<AbstractLine> items = new ArrayList<AbstractLine>();
	//private DataStoreLarge fc;

	private String lastAction = "";
	
	public boolean checkLines = false;
	
	static {
		try {
			Random random = new Random(1331);
			CopybookLoader copybookInt = new CobolCopybookLoader();
			BigDecimal bd100 = new BigDecimal(100);
			dtar020 = Code.getDTAR020Layout();
			
//			ToLayoutDetail.getInstance().getLayout(
//	                copybookInt.loadCopyBook(
//	                        "/home/knoppix/RecordEdit/HSQLDB/CopyBook/Cobol/" + "DTAR020.cbl",
//	                        CopybookLoader.SPLIT_NONE, 0, "cp037",
//	                        Convert.FMT_MAINFRAME, 0, null
//	                ));
			
			
			for (int i = 0; i < dataLines.length; i++) {
				dataLines[i] = new Line(dtar020);
				dataLines[i].setField(0, 0, new Long(Math.abs(random.nextLong()) % 100000000));
				dataLines[i].setField(0, 1, new Long(Math.abs(random.nextLong()) % 1000));
				dataLines[i].setField(0, 2, new Long(Math.abs(random.nextLong()) % 1000000));
				dataLines[i].setField(0, 3, new Long(Math.abs(random.nextLong()) % 1000));
				dataLines[i].setField(0, 4, new Long(Math.abs(random.nextLong()) % 20 - 4));
				dataLines[i].setField(0, 5, 
						(new BigDecimal(Math.abs(random.nextLong()) % 10000 - 1000)).divide(bd100));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}




	public void testSplit0() {
		checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		System.out.println("Test 0:");
		
		doAdd(0,0);
		doAdd(1,1);
	}


	public void testSplit1() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		System.out.println("Test 1:");
		
		doAdd(0,2);
		doAdd(1,3);
	}

	public void testSplit2() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		
		System.out.println("Test 1:");
		
		doAdd(34,4);
		doAdd(35,5);
		doAdd(36,6);
		doAdd(37,7);
		doAdd(38,8);
	}

	public void testSplit3() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		System.out.println("Test 1:");
		
		doAdd(dataLines.length - 2, 9);
		doAdd(dataLines.length - 1, 10);

	}
	
	private  void doAdd(int pos, int actionNo) {
		int len = dtar020.getMaximumRecordLength();
		ArrayList<AbstractLine> items = new ArrayList<AbstractLine>();
		DataStoreLarge fc = new DataStoreLarge(dtar020, FileDetails.FIXED_LENGTH, len);
		for (int i = 0; i < dataLines.length; i++) {
			System.out.print(i + " ");
			items.add((Line) dataLines[i].getNewDataLine());
			fc.add((Line) dataLines[i].getNewDataLine());
		}
		
		lastAction = Code.doAdd(dataLines, items, fc, checkLines, pos, actionNo);
		assertTrue("Add pos=" + pos + " ActionNo=" + actionNo,
				Code.check(lastAction, items, fc));
	}
}
