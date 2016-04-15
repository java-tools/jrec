package net.sf.RecordEditor.test.largeFileView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.test.filestorage1.Code;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.IChunkLine;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;



public class ViewTest {

	public static String  recordEditorDir = "G:\\Programs\\RecordEditor\\HSQL\\"; //"/media/sdc1/RecordEditor/USB/";
	

//				).getBytes();
	private AbstractLine[] stdDataLines = new AbstractLine[500];
	private AbstractLine[] dataLines;

	public static final LayoutDetail DTAR020 ;
	public static final LayoutDetail CSV_DBL_BYTE_SCHEMA; 
	static {
		ExternalField[] flds = {
			new ExternalField(1, -1, "Sku", "", Type.ftChar, 0, 0, "", "", "", 0),
			new ExternalField(2, -1, "Store", "", Type.ftNumLeftJustified, 0, 0, "", "", "", 0),
			new ExternalField(3, -1, "Qty", "", Type.ftNumLeftJustified, 0, 0, "", "", "", 0),
			new ExternalField(4, -1, "dept", "", Type.ftNumLeftJustified, 0, 0, "", "", "", 0),
			new ExternalField(5, -1, "xx", "", Type.ftNumLeftJustified, 0, 0, "", "", "", 0),
			new ExternalField(6, -1, "Price", "", Type.ftNumAnyDecimal, 0, 0, "", "", "", 0),
		};
		ArrayList<ExternalField> fields = new ArrayList<ExternalField>(flds.length) ;
		LayoutDetail dt = null;
		LayoutDetail csv = null;
		
		for (ExternalField f :flds) {
			fields.add(f);
		}
		
		try {
			dt = getDTAR020Layout();
			csv = (LayoutDetail) StandardLayouts.getInstance()
					.getCsvLayout(fields, Constants.IO_UNICODE_CSV + "", "|", "utf16", "'", false);			
			
		} catch (Exception e) { 
			e.printStackTrace();
			//dtar020 = null;
		}
		DTAR020 = dt;
		CSV_DBL_BYTE_SCHEMA = csv;
	}
	
	{
		try {
			loadDataLines(stdDataLines, DTAR020);
			dataLines = getStdDataLines();
		} catch (RecordException e) {
			e.printStackTrace();
		}
				
	}
	
	public static AbstractLine[] loadDataLines(AbstractLine[] lines, LayoutDetail schema) throws RecordException { 
		Random random = new Random(121);
		BigDecimal bd100 = new BigDecimal(100);
		LineProvider lineProvider = LineIOProvider.getInstance().getLineProvider(schema);
		
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lineProvider.getLine(schema);
			lines[i].setField(0, 0, new Long(Math.abs(random.nextLong()) % 100000000));
			lines[i].setField(0, 1, new Long(Math.abs(random.nextLong()) % 1000));
			lines[i].setField(0, 2, new Long(Math.abs(random.nextLong()) % 1000000));
			lines[i].setField(0, 3, new Long(Math.abs(random.nextLong()) % 1000));
			lines[i].setField(0, 4, new Long(Math.abs(random.nextLong()) % 20 - 4));
			lines[i].setField(0, 5, 
					(new BigDecimal(Math.abs(random.nextLong()) % 10000 - 1000)).divide(bd100));			
		}
		return lines;
	}

	public static LayoutDetail getDTAR020Layout() throws RecordException {
		return Code.getDTAR020Layout();
	}

	
	protected AbstractLine[] getStdDataLines() {
		AbstractLine[] lines = new AbstractLine[stdDataLines.length];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = stdDataLines[i].getNewDataLine();
		}
		return lines;
	}

	


	protected void setDataLines(AbstractLine[] dataLines) {
		this.dataLines = dataLines;
	}

	public void doTest(ViewTestData testData,
			String desc, boolean checkHeldLines,long seed, int firstLine, int numActions, int len) {

		Random random = new Random(seed);
		
		String lastAction;
		Common.OPTIONS.agressiveCompress.set(false);
		
		
		try {
			String prevMsg = "";
			for (int i = 0; i < dataLines.length; i++) {
				//System.out.print(i + " ");
				testData.baselineFV.viewFile.addLine(i, dataLines[i].getNewDataLine());
				testData.testFV.viewFile.addLine(i, dataLines[i].getNewDataLine());
			}
			//System.out.println();
			//System.out.println(desc + " : " + numActions);
			TestCase.assertTrue(desc + " Initialise a: " + seed, Code.check(" Initialise", testData.baselineFV.parentStore, testData.testFV.parentStore));
			TestCase.assertTrue(desc + " Initialise b: " + seed, Code.check(" Initialise", testData.baselineFV.viewStore, testData.testFV.viewStore));
			
			for (int i = 0; i < numActions; i++) {
				lastAction = doAction(
						dataLines, 
						testData.baseLineFile[i % 2],
						testData.testFile[i % 2],
						testData.testFV.viewStore,
						checkHeldLines,
						random, i,
						new HashSet<Integer>());

				//System.out.print("\t: " + testData.baselineFV.viewStore.size());
				String message = desc + " " + lastAction + " " + "ActionNo: " + i;
				//System.out.print(message);

				printViewLineNums1(message + " c) Seed: " + seed, testData);
				printViewLineNums2(message + " d) Seed: " + seed, testData);

				TestCase.assertTrue(message + " a) Seed: " + seed, Code.check(lastAction, testData.baselineFV.parentStore, testData.testFV.parentStore));
				if (! Code.check(lastAction, testData.baselineFV.viewStore, testData.testFV.viewStore)) {
					printViewLineNums(testData);
					TestCase.assertTrue(message + " b) Seed: " + seed, false);
				}
				prevMsg = desc;
				//System.out.println();
			}
//		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
		}
	}

	private void printViewLineNums(ViewTestData testData) {
		int len = Math.min(testData.baselineFV.viewStore.size(), testData.testFV.viewStore.size());
		int p1, p2, k= 0;
		
		//System.out.println();
		//System.out.println("Printing index count differences: " + len);
		
		for (int i = 0; i < len; i++) {
			p1 = testData.baselineFV.parentFile.indexOf(testData.baselineFV.viewStore.get(i));
			p2 = testData.testFV.parentFile.indexOf(testData.testFV.viewStore.get(i));
			
			if (p1 != p2) {
				System.out.print("\t: " + i + " - " + p1 + ", " + p2);
				
				if (k++ > 15) {
					k = 0;
					System.out.println();
				}
			}
		}
	}
	

	private void printViewLineNums2(String msg, ViewTestData testData) {
		int len = Math.min(testData.baselineFV.viewStore.size(), testData.testFV.viewStore.size());
		int p1, p2, k= 0, idx = -1;
		AbstractLine l1, l2, l3;

		boolean ok = true;
		
		for (int i = 0; i < len; i++) {
			l1 = testData.baselineFV.viewStore.get(i);
			l2 = testData.testFV.viewStore.get(i);
			p1 = testData.baselineFV.parentFile.indexOf(l1);
			p2 = testData.testFV.parentFile.indexOf(l2);

			
			if (p1 != p2 || ! Code.compare(l1.getData(), l2.getData())) {
				ok = false;
				if (idx < 0) {
					idx = i;
					System.out.println(msg + " at " + idx);
				}
				System.out.print("--> " + i + " - " + p1 + ", " + p2 + " " + (Code.compare(l1.getData(), l2.getData()))	);
				if (l2 instanceof IChunkLine) {
					l3 = testData.testFV.parentFile.getLine(p2);
					System.out.print(" " + (l2 == l3) + " " + ((IChunkLine) l2).getChunkLine() + " ");
					if (l3 instanceof IChunkLine) {
						System.out.print(("~ " + ((IChunkLine) l3).getChunkLine() + " "));
					}
				}
				
				if (i % 6 == 0)  System.out.println();
			}
		}
		TestCase.assertTrue(msg + " at " + idx, ok);
	}


	private void printViewLineNums1(String msg, ViewTestData testData) {
		int len = Math.min(testData.baselineFV.viewStore.size(), testData.testFV.viewStore.size());
		int p1, p2, k= 0, idx = -1;
		AbstractLine l1, l2, l3;

		boolean ok = true;
		
		for (int i = 0; i < len; i++) {
			l1 = testData.baselineFV.parentStore.get(i);
			l2 = testData.testFV.parentStore.get(i);

			
			if (! Code.compare(l1.getData(), l2.getData())) {
				ok = false;
				if (idx < 0) {
					idx = i;
					System.out.println(msg + " at " + idx);
				}
				System.out.print("--> " + i + " - " +  " " + (Code.compare(l1.getData(), l2.getData()))	);
				l2 = testData.testFV.parentStore.get(testData.testFV.parentStore.size() - 1);
				l2 = testData.testFV.parentStore.get(i);
				System.out.print(  " + " + (Code.compare(l1.getData(), l2.getData()))	);
				if (l2 instanceof IChunkLine) {
					System.out.print(" "  + " " + ((IChunkLine) l2).getChunkLine() + " ");
				}
				
				if (i % 9 == 0) System.out.println();
			}
		}
//		TestCase.assertTrue(msg + " at " + idx, ok);
	}


	public static String doAction(
			AbstractLine[] userDataLines,
			FileView updStd,
			FileView updCmp,
			IDataStore<AbstractLine> test1Cmp,
			boolean checkLines,
			Random random, int actionNo,
			HashSet<Integer> ignore) {

		String lastAction = "";
		int from, pos1, pos2, p;

		int[] toUpd;
		Code.HeldDetails held = null;
		
		if (checkLines) {
			held = new Code.HeldDetails(test1Cmp);
		}
		
		
		Common.OPTIONS.agressiveCompress.set(false);
		
		int count = Math.abs(random.nextInt()) % 5 + 1;
		int pos = Math.abs(random.nextInt()) % userDataLines.length;
		int act = Math.abs(random.nextInt()) % 10;
		//System.out.println("Starting action ~ " + act);
		//System.out.print("\t" + act);
		switch (act) {
		case 0:
			from = Math.abs(random.nextInt()) % userDataLines.length;
			lastAction = " Add: " + pos + " <- " + from + " :: " + count;
			//System.out.println("Starting: " + lastAction);

			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, updStd.getRowCount());
			}
			Arrays.sort(toUpd);
			for (int i = 0; i < count; i++) {
				pos1 = toUpd[i];
				pos2 = getCircular(from+i, userDataLines.length);

				updCmp.addLine(pos1, (AbstractLine)userDataLines[pos2].getNewDataLine());
				updStd.addLine(pos1, (AbstractLine)userDataLines[pos2].getNewDataLine());
				//ignore.add(pos1);
			}
			break;
		case 1:
			from = Math.abs(random.nextInt()) % userDataLines.length;
			lastAction = " Add at End <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				int idx = getCircular(from+i, userDataLines.length);

				updCmp.addLine(updCmp.getRowCount(), (AbstractLine)userDataLines[idx].getNewDataLine());
				updStd.addLine(updCmp.getRowCount(), (AbstractLine)userDataLines[idx].getNewDataLine());
			}
			break;
		case 2:

			from = Math.abs(random.nextInt()) % userDataLines.length;
			lastAction = " Addlines: " + pos + " <- " + from + " :: " + count;
			//System.out.println("Starting: " + lastAction);

			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, updStd.getRowCount());
			}
			Arrays.sort(toUpd);
			for (int i = 0; i < count; i++) {
				pos1 = toUpd[i];
				pos2 = getCircular(from+i, userDataLines.length);

				updCmp.addLines(pos1, -1, new AbstractLine[] {userDataLines[pos2].getNewDataLine()});
				updStd.addLines(pos1, -1, new AbstractLine[] {userDataLines[pos2].getNewDataLine()});
				//ignore.add(pos1);
			}

			break;
		case 3:
			Integer sku;
			from = Math.abs(random.nextInt()) % userDataLines.length;
			lastAction = " Set Values: " + pos + " -> " +  count;
			for (int i = 0; i < count; i++) {
				pos1 = getCircular(pos + i, updStd.getRowCount());
				sku = Math.abs(random.nextInt()) % 100000000;

				try {
					AbstractLine line = updCmp.getLine(pos1);
					line.setField(0, 0, sku);
					p = test1Cmp.indexOf(line);
					//System.out.print("\t" + p);
					ignore.add(p);
				} catch (Exception e) {
				}

				try {
					updStd.getLine(pos1).setField(0, 0, sku);
				} catch (Exception e) {
				}

			}
			break;
		case 4:

			lastAction = " Del 1: " + pos + " :: " + count;
			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, updStd.getRowCount());
			}
			Arrays.sort(toUpd);
			//System.out.println();
			for (int i = count-1; i >= 0; i--) {
				pos1 = toUpd[i];
				p = test1Cmp.indexOf(updCmp.getLine(pos1));
				//System.out.print("\t:: " + pos1 + ", "+ p);
				ignore.add(p);
				updCmp.deleteLine(pos1);

				updStd.deleteLine(pos1);

				ignore.add(pos1);
			}
			break;	
		case 5:

			lastAction = " Del 2: " + pos + " :: " + count;
			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, updStd.getRowCount());
			}
			Arrays.sort(toUpd);
			//System.out.println();
			for (int i = count-1; i >= 0; i--) {
				pos1 = toUpd[i];
				p = test1Cmp.indexOf(updCmp.getLine(pos1));
				//System.out.print("\t:: " + pos1 + ", "+ p);
				ignore.add(p);
				updCmp.deleteLine(updCmp.getLine(pos1));

				updStd.deleteLine(updStd.getLine(pos1));

				ignore.add(pos1);
			}
			break;	
			
		case 6:
			lastAction = " Del 3: " + pos + " :: " + count;
			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, updStd.getRowCount());
			}
			Arrays.sort(toUpd);
			//System.out.println();
			int[] lineNums = new int[count];

			for (int i = count-1; i >= 0; i--) {
				pos1 = toUpd[i];
				p = test1Cmp.indexOf(updCmp.getLine(pos1));
				//System.out.print("\t:: " + pos1 + ", "+ p);
				ignore.add(p);
				lineNums[i] = pos1;
				ignore.add(pos1);
			}
			
			updCmp.deleteLines(lineNums.clone());
			updStd.deleteLines(lineNums.clone());
			break;
			
		case 7:
			from = Math.abs(random.nextInt()) % userDataLines.length;
			lastAction = " NewLine: " + pos + " <- " + from + " :: " + count;
			//System.out.println("Starting: " + lastAction);

			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, updStd.getRowCount());
			}
			Arrays.sort(toUpd);
			for (int i = 0; i < count; i++) {
				pos1 = toUpd[i];
				pos2 = getCircular(from+i, userDataLines.length);

				updCmp.getLine(updCmp.newLine(pos1, -1)).setData(userDataLines[pos2].getNewDataLine().getData());
				updStd.getLine(updStd.newLine(pos1, -1)).setData(userDataLines[pos2].getNewDataLine().getData());
				//ignore.add(pos1);
			}
			break;
		case 8:
		case 9:

			from = Math.abs(random.nextInt()) % userDataLines.length;
			lastAction = " Addlines m: " + pos + " <- " + from + " :: " + count;
			//System.out.println("Starting: " + lastAction);

			AbstractLine[] lines1 = new AbstractLine[count];
			AbstractLine[] lines2 = new AbstractLine[count];
			for (int i = 0; i < count; i++) {
				pos2 = getCircular(from+i, userDataLines.length);

				lines1[i] = userDataLines[pos2].getNewDataLine();
				lines2[i] = userDataLines[pos2].getNewDataLine();
				//ignore.add(pos1);
			}
			updCmp.addLines(pos, -1, lines1);
			updStd.addLines(pos, -1, lines2);
			break;
		default:

		}
		//System.out.println("Action: " + lastAction);
		
		if (checkLines) {
			Code.doCheckLines(actionNo, lastAction, held, ignore);
		}
		return lastAction;
	}
	
	public static String doAdd(
			AbstractLine[] userDataLines,
			ArrayList<AbstractLine> items, 
			DataStoreLarge fc,
			boolean checkLines,
			int pos, int actionNo) {
		
		String lastAction = "";
		int pos2;


		HashSet<Integer> ignore = new HashSet<Integer>();
		Code.HeldDetails held = null;

		for (int i = 0; i < 200; i++) {
			if (checkLines) {
				held = new Code.HeldDetails(fc);
			}
			
			for (int j = 0; j < 10; j++) {
				pos2 = (i * 10 + j) % userDataLines.length;
				fc.add(pos, (AbstractLine)userDataLines[pos2].getNewDataLine());
				items.add(pos, (AbstractLine)userDataLines[pos2].getNewDataLine());
			}
			lastAction = " i=" + i;
			
			if (checkLines) {
				Code.doCheckLines(actionNo, lastAction, held, ignore);
			}
		}
		
		

	
		return lastAction;
	}

	
	private static int getCircular(int i, int size) {
		if (i >= size) {
			return i - size;
		}
		return i;
	}
}
