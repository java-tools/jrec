package net.sf.RecordEditor.test.filestorage2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.test.filestorage1.Code;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.FileDetails;
import net.sf.RecordEditor.utils.params.Parameters;
import junit.framework.TestCase;

public class TstLargeList extends TestCase {

	private static AbstractLine[] dataLines = new Line[500];
	private static LayoutDetail dtar020;
	private ArrayList<AbstractLine> items = new ArrayList<AbstractLine>();
	@SuppressWarnings("rawtypes")
	private DataStoreLarge fc;
	private String lastAction = "";
	
	public boolean checkLines = false;
	
	static {
		try {
			Random random = new Random(1331);
			BigDecimal bd100 = new BigDecimal(100);
			dtar020 = Code.getDTAR020Layout();
			
//			ToLayoutDetail.getInstance().getLayout(
//	                copybookInt.loadCopyBook(
//	                		Code.recordEditorDir + "CopyBook/Cobol/" + "DTAR020.cbl",
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


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Parameters.getInitialisedProperties()
			.put(Parameters.PROPERTY_BIG_FILE_DISK_FLAG, "Y");
	}



	public void testFixed1() {
		checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);

		
		System.out.println("Test 1:");
		tst("Fixed 1.1.1 ", 1, 0, 100);
		tst("Fixed 1.1.2 ", 12, 0, 100);
		tst("Fixed 1.1.3 ", 15, 0, 100);
		tst("Fixed 1.1.4 ", 17, 0, 100);
		
		tst("Fixed 1.2.1 ", 121, 0, 400);
		tst("Fixed 1.2.2 ", 1213, 0, 800);
		tst("Fixed 1.2.3 ", 1215, 0, 400);
		tst("Fixed 1.2.4 ", 1217, 0, 800);
	}
	
	public void testFixed2() {
		checkLines = false;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);

		
		tst("Fixed 2.2.1 ", System.nanoTime(), 0, 100);
		tst("Fixed 2.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("Fixed 2.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("Fixed 2.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("Fixed 2.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("Fixed 2.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("Fixed 2.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("Fixed 2.3.4 ", System.nanoTime() + 1217, 0, 800);
	}
	

	public void testFixed3() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);

		
		System.out.println("Test 3:");
		tst("Fixed 3.1.1 ", 1, 0, 100);
		tst("Fixed 3.1.2 ", 12, 0, 100);
		tst("Fixed 3.1.3 ", 15, 0, 100);
		tst("Fixed 3.1.4 ", 17, 0, 100);
		
		tst("Fixed 3.2.1 ", 121, 0, 400);
		tst("Fixed 3.2.2 ", 1213, 0, 800);
		tst("Fixed 3.2.3 ", 1215, 0, 400);
		tst("Fixed 3.2.4 ", 1217, 0, 800);
	}
	
	public void testFixed4() {
		checkLines = true;
		Parameters.getInitialisedProperties().put(Parameters.PROPERTY_BIG_FILE_CHUNK_SIZE, "1");
		Common.OPTIONS.agressiveCompress.set(false);
		Common.OPTIONS.doCompress.set(false);

		
		tst("Fixed 4.2.1 ", System.nanoTime(), 0, 100);
		tst("Fixed 4.2.2 ", System.nanoTime() + 12, 0, 100);
		tst("Fixed 4.2.3 ", System.nanoTime() + 15, 0, 500);
		tst("Fixed 4.2.4 ", System.nanoTime() + 17, 0, 100);
		
		tst("Fixed 4.3.1 ", System.nanoTime() + 121, 0, 400);
		tst("Fixed 4.3.2 ", System.nanoTime() + 1213, 0, 800);
		tst("Fixed 4.3.3 ", System.nanoTime() + 1215, 0, 400);
		tst("Fixed 4.3.4 ", System.nanoTime() + 1217, 0, 800);
	}



	//TODO  set Chunk Size
	
	@SuppressWarnings("rawtypes")
	public void tst(String desc, long seed, int firstLine, int numActions) {
		int len = dtar020.getMaximumRecordLength();
		Random random = new Random(seed);
		
		items = new ArrayList<AbstractLine>();
		
		fc = new DataStoreLarge(dtar020, FileDetails.FIXED_LENGTH, len);
		for (int i = 0; i < dataLines.length; i++) {
//			System.out.print(i + " ");
			items.add((Line) dataLines[i].getNewDataLine());
			fc.add((Line) dataLines[i].getNewDataLine());
		}
		System.out.println();
		System.out.println(desc);
		
		for (int i = 0; i < numActions; i++) {
			doAction(random, i);
			assertTrue(desc + " " + "ActionNo: " + i + " Seed: " + seed, check());
		}
	}
	
	@SuppressWarnings("unchecked")
	private  void doAction(Random random, int actionNo) {
		lastAction = Code.doAction(dataLines, items, fc, checkLines, random, actionNo);
	}
	
	private boolean check() {
		return Code.check(lastAction, items, fc);
	}

}
