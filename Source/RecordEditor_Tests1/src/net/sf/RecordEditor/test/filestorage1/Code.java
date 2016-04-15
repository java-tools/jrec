package net.sf.RecordEditor.test.filestorage1;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.fileStorage.DataStoreLarge;
import net.sf.RecordEditor.utils.fileStorage.IChunkLine;
import net.sf.RecordEditor.utils.fileStorage.IDataStore;



public class Code {

	public static final String  recordEditorDir = "G:\\Programs\\RecordEditor\\HSQL\\"; //"/media/sdc1/RecordEditor/USB/";
	public static final String  recordEditorOutputDir = "G:\\Users\\Bruce01\\RecordEditor_HSQL\\"; //"/media/sdc1/RecordEditor/USB/";
	
	private static final byte[] copyBookDTAR020Bytes
				= (
					  "              03  DTAR020-KCODE-STORE-KEY.\n"
					+ "                  05 DTAR020-KEYCODE-NO      PIC X(08).\n"
					+ "                  05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.\n"
					+ "              03  DTAR020-DATE               PIC S9(07)   COMP-3.\n"
					+ "              03  DTAR020-DEPT-NO            PIC S9(03)   COMP-3.\n"
					+ "              03  DTAR020-QTY-SOLD           PIC S9(9)    COMP-3.\n"
					+ "              03  DTAR020-SALE-PRICE         PIC S9(9)V99 COMP-3.\n"
				).getBytes();

	public static LayoutDetail getDTAR020Layout() throws RecordException {
		
		//ByteArrayInputStream bs = new ByteArrayInputStream(copyBookDTAR020Bytes);
		CobolCopybookLoader loaderCBL = new CobolCopybookLoader();
		
		ExternalRecord extlayoutCBL = loaderCBL.loadCopyBook(
	    	    new ByteArrayInputStream(copyBookDTAR020Bytes),
	    	    Conversion.getCopyBookId("DTAR020.cbl"),
	    	    CopybookLoader.SPLIT_NONE, 0, "cp037",
                ICopybookDialects.FMT_MAINFRAME, 0, null);
		return ToLayoutDetail.getInstance().getLayout(extlayoutCBL);
	}

	
	public static String doAction(
			AbstractLine[] dataLines,
			ArrayList<AbstractLine> items, 
			/*DataStoreLarge<AbstractChunkLine<FileChunk<AbstractChunkLine, IRecordStore>>, IRecordStore>*/ IDataStore<AbstractLine> fc,
			boolean checkLines,
			Random random, int actionNo) {
		
		return doAction(dataLines, items, fc, checkLines, random, actionNo, new HashSet<Integer>());
	}

	public static String doAction(
			AbstractLine[] dataLines,
			ArrayList<AbstractLine> items, 
			//DataStoreLarge fc,
			IDataStore<AbstractLine> fc,
			boolean checkLines,
			Random random, int actionNo,
			HashSet<Integer> ignore) {

		String lastAction = "";
		int from, pos1, pos2;

		int[] toUpd;
		HeldDetails held = null;
		
		if (checkLines) {
			held = new HeldDetails(fc);
		}
		
		
		Common.OPTIONS.agressiveCompress.set(false);
		
		int count = Math.abs(random.nextInt()) % 5 + 1;
		int pos = Math.abs(random.nextInt()) % dataLines.length;
		int act = Math.abs(random.nextInt()) % 5;
		//System.out.println("Starting action ~ " + act);
		//System.out.print("\t" + act);
		switch (act) {
		case 0:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Add: " + pos + " <- " + from + " :: " + count;
			//System.out.println("Starting: " + lastAction);
			
			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, items.size());
			}
			Arrays.sort(toUpd);
			for (int i = 0; i < count; i++) {
				pos1 = toUpd[i];
				pos2 = getCircular(from+i, dataLines.length);

				fc.add(pos1, (AbstractLine)dataLines[pos2].getNewDataLine());
				items.add(pos1, (AbstractLine)dataLines[pos2].getNewDataLine());
				//ignore.add(pos1);
			}
		break;
		case 1:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Add at End <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				fc.add((AbstractLine)dataLines[getCircular(from+i, dataLines.length)].getNewDataLine());
				items.add((AbstractLine)dataLines[getCircular(from+i, dataLines.length)].getNewDataLine());
			}
		break;
		case 2:
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Set Line: " + pos + " <- " + from + " :: " + count;
			for (int i = 0; i < count; i++) {
				pos1 = getCircular(pos + i, items.size());
				pos2 = getCircular(from+i, dataLines.length);
//				if (pos1 == 28) {
//					System.out.print('.');
//				}
				fc.set(pos1, (AbstractLine)dataLines[pos2].getNewDataLine());
				items.set(pos1, (AbstractLine)dataLines[pos2].getNewDataLine());
				
				ignore.add(pos1);
			}
		break;
		case 3:
			Integer sku;
			from = Math.abs(random.nextInt()) % dataLines.length;
			lastAction = " Set Values: " + pos + " -> " + count;
			for (int i = 0; i < count; i++) {
				pos1 = getCircular(pos + i, items.size());
				sku = Math.abs(random.nextInt()) % 100000000;
				
				try {
					fc.get(pos1).setField(0, 0, sku);
				} catch (Exception e) {
				}
				
				try {
					items.get(pos1).setField(0, 0, sku);
				} catch (Exception e) {
				}
				
				ignore.add(pos1);
			}
		break;
		case 4:
			lastAction = " Del: " + pos + " :: " + count;
			toUpd = new int[count];
			for (int i = 0; i < count; i++) {
				toUpd[i] = getCircular(pos + i, items.size());
			}
			Arrays.sort(toUpd);
			for (int i = count-1; i >= 0; i--) {
				pos1 = toUpd[i];
				fc.remove(pos1);
		
				items.remove(pos1);
				
				ignore.add(pos1);
			}
			break;
		}
		//System.out.println("Action: " + lastAction);
		
		if (checkLines) {
			doCheckLines(actionNo, lastAction, held, ignore);
		}
		return lastAction;
	}
	
	public static String doAdd(
			AbstractLine[] dataLines,
			ArrayList<AbstractLine> items, 
			DataStoreLarge fc,
			boolean checkLines,
			int pos, int actionNo) {
		
		String lastAction = "";
		int pos2;

		//int[] toUpd;
		HashSet<Integer> ignore = new HashSet<Integer>();
		HeldDetails held = null;
		//AbstractLine l;
		
		
		for (int i = 0; i < 200; i++) {
			if (checkLines) {
				held = new HeldDetails(fc);
			}
			
			for (int j = 0; j < 10; j++) {
				pos2 = (i * 10 + j) % dataLines.length;
				fc.add(pos, (AbstractLine)dataLines[pos2].getNewDataLine());
				items.add(pos, (AbstractLine)dataLines[pos2].getNewDataLine());
			}
			lastAction = " i=" + i;
			
			if (checkLines) {
				doCheckLines(actionNo, lastAction, held, ignore);
			}
		}
		
		

	
		return lastAction;
	}

	public static void doCheckLines(
			int actionNo,
			String lastAction,
			HeldDetails h,
			HashSet<Integer> ignore) {
		for (int i = 0; i < h.old.length; i++) {
			if (! ignore.contains(i)) {
				if (! Code.compare(h.hold[i].getData(), h.old[i].getData())) {
					int a = ((IChunkLine) h.hold[i]).getActualLine() ;
					System.out.println();
					System.out.println("Error in held Data line=" + i 
							+ " ActionNo: " + actionNo + "  " + lastAction
							+ " " + h.old[i].getClass().getName()
							+ " " + h.hold[i].getClass().getName());
					if (i > 0) {
						System.out.print("-- Line Details1  " 
							+ ((IChunkLine) h.hold[i-1]).getActualLine() 
							+ ", " + ((IChunkLine) h.hold[i-1]).getChunkLine()
							+ ", " + h.lno[i-1]);
					}
					String ss= " -> "; 
					for (int j =0; j < 5 && i + j <h.hold.length; j++) {
						System.out.print( ss + ((IChunkLine) h.hold[i+j]).getActualLine() 
							+ ", " + ((IChunkLine) h.hold[i+j]).getChunkLine()
							+ ", " + h.lno[i+j]);
						ss = " ++ ";
					}
	


					
					if (a <  h.fc.size()) {
						ss = " ~ ";
						for (int j =0; j < 5 && i + j <h.hold.length; j++) {
							System.out.print( ss + (h.hold[i+j] == h.fc.get(a)));
							ss = "    ";
						}
					}
					
					System.out.println();
					
					for (int j = Math.max(-4, -i); j < 6 && i+j < h.hold.length; j++) {
						System.out.println(" ~~ " + (i+j));
						Code.writeByteArray(h.hold[i+j].getData());		
						Code.writeByteArray(h.old[i+j].getData());
						System.out.println();
					}

					System.out.print("-- Lines: " 
							+ ((IChunkLine) h.hold[i]).getActualLine() 
							+ ", " + ((IChunkLine) h.hold[i]).getChunkLine());
					if (i+1 < h.hold.length) {
						System.out.print(
								  " ++ " + ((IChunkLine) h.hold[i+1]).getActualLine() 
								+ ", " + ((IChunkLine) h.hold[i+1]).getChunkLine());
					}
					System.out.println();
					System.out.println();
					
					throw new RuntimeException("Error in held lines");

				}
			}
		}
	}
	
	private static int getCircular(int i, int size) {
		if (i >= size) {
			return i - size;
		}
		return i;
	}

	public static boolean check(
			String lastAction, 
			List<AbstractLine> items, 
			IDataStore<AbstractLine> fc) 
	{
		boolean ret = fc.size() == items.size();
		
		if (! ret) { 
			System.out.print("  Error - Different sizes - " + items.size() + " == " +fc.size()  
					+ lastAction + " : ");
		}
		
		for (int i = 0; i < Math.min(fc.size(), items.size()); i++) {
			try {
				if (! Code.compare(
						fc.get(i).getData(), 
						items.get(i).getData())) {
					if (ret) {
						System.out.println("Error line " + i + lastAction + " : " + fc.getClass().getName());
					}
					System.out.println("-- " + i + " ");
					
					Code.writeByteArray(items.get(i).getData());
					Code.writeByteArray(fc.get(i).getData());		
					
					//System.out.println();
					System.out.println();
					ret = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error Line: " + i, e);
			}
		}
//		System.out.println();
		
		return ret;
	}

	
	public static boolean compare(byte[] c1, byte[] c2) {
		return ( c1.length == c2.length) && Arrays.equals(c1, c2); 
	}
	
	public static void writeByteArray(byte[] b) {
		System.out.println();
		
		System.out.print(b.length + "-" );
		for (int j = 0; j < b.length; j++) {
			System.out.print("\t" + b[j] );
		}
	}
	
	public static final class HeldDetails {
		final AbstractLine[] hold;
		AbstractLine[] old;
		
		final int[] lno;
		final IDataStore<AbstractLine> fc;
		
		public HeldDetails(IDataStore<AbstractLine>  f) {
			fc = f;
			old = new AbstractLine[fc.size()];
			hold = new AbstractLine[fc.size()];
			lno = new int[fc.size()];
			for (int i = 0; i < fc.size(); i++) {
				hold[i] = fc.get(i);
				old[i] = fc.get(i).getNewDataLine();
				
				lno[i] = ((IChunkLine) hold[i]).getChunkLine();	
			}
		}
	}
}
