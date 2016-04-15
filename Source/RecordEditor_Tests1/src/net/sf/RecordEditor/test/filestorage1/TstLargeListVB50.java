package net.sf.RecordEditor.test.filestorage1;


import java.util.ArrayList;
import java.util.Random;

import junit.framework.TestCase;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.utils.fileStorage.FileDetails; 
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
 
public class TstLargeListVB50  extends TestCase {

	public static final int NUMBER_OF_DATA_LINES = 500;
	private LayoutDetail layout;
	private AbstractLine[] dataLines = new AbstractLine[NUMBER_OF_DATA_LINES];

	private ArrayList<AbstractLine> items = new ArrayList<AbstractLine>();
	private DataStoreLarge fc;
	private String lastAction = "";

	public boolean checkLines = false;

	public TstLargeListVB50() throws Exception {
		
	}
	
	public void setup(LayoutDetail l) {
		int rec;
		
		layout = l;
		try {
			Random random = new Random(1331);
			
			for (int i = 0; i < dataLines.length; i++) {
				rec = Math.abs(random.nextInt()) % 3;
				//System.out.println("Init: " + i + " idx:" + rec);
				if (layout.getFileStructure() == Constants.IO_UNICODE_TEXT) {
					dataLines[i] = new CharLine(layout, "");
				} else {
					dataLines[i] = new Line(layout);
				}
				dataLines[i].setField(rec, 0, new Long(Math.abs(random.nextLong()) % 100000));
				dataLines[i].setField(rec, 1, new Long(Math.abs(random.nextLong()) % 1000));
				dataLines[i].setField(rec, 2, new Long(Math.abs(random.nextLong()) % 100000));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test1() {
		assertTrue(true);
	}
	
	public void tst(String desc, long seed, int firstLine, int numActions, int len) {
		tst( FileDetails.VARIABLE_LENGTH, desc, seed, firstLine, numActions, len);
	}
	
	public void tst(int type, String desc, long seed, int firstLine, int numActions, int len) {

		Random random = new Random(seed);
		
		items = new ArrayList<AbstractLine>();
		
		fc = new DataStoreLarge(layout, type, len);
		try {
			for (int i = 0; i < dataLines.length; i++) {
				//System.out.print(i + " ");
				items.add((AbstractLine) dataLines[i].getNewDataLine());
				fc.add((AbstractLine) dataLines[i].getNewDataLine());
			}
			System.out.println();
			System.out.println(desc);
			
			for (int i = 0; i < numActions; i++) {
				doAction(random, i);
				assertTrue(desc + " " + "ActionNo: " + i + " Seed: " + seed, check());
			}
		} finally {
			fc.clear();
		}
	}
	
	//static Line[] hold;
	private  void doAction(Random random, int actionNo) {
		lastAction = Code.doAction(dataLines, items, fc, checkLines, random, actionNo);
	}
	

	private boolean check() {
		return Code.check(lastAction, items, fc);
	}

	public  void doAdd(LayoutDetail layout, int pos, int actionNo) {
		int len = layout.getMaximumRecordLength();
		ArrayList<AbstractLine> items = new ArrayList<AbstractLine>();
		DataStoreLarge fc = new DataStoreLarge(layout, FileDetails.VARIABLE_LENGTH, len);
		try {
			for (int i = 0; i < dataLines.length; i++) {
				System.out.print(i + " ");
				items.add((Line) dataLines[i].getNewDataLine());
				fc.add((Line) dataLines[i].getNewDataLine());
			}
			
			lastAction = Code.doAdd(dataLines, items, fc, checkLines, pos, actionNo);
			assertTrue("Add pos=" + pos + " ActionNo=" + actionNo,
				Code.check(lastAction, items, fc));
		} finally {
			fc.clear();
		}
	}
}
