package net.sf.RecordEditor.test.file1;

import java.io.ByteArrayInputStream;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.RecordEditor.re.file.FileView;
import junit.framework.TestCase;

/**
 * This Class tests reult in a view after updating the main
 * the original file + view get updated correctly
 * 
 * @author Bruce Martin
 *
 */
public class TstViewUpdateMain extends TestCase {

	private final byte[] schemaLines = 
		( "        01  Rec-20."
		+ "            03 Field-1          Pic x(20).").getBytes();
	
	
	private final LayoutDetail schema;
	private final AbstractLine[] lines;
	
	public TstViewUpdateMain() throws RecordException {
		
				
		CobolCopybookLoader loaderCBL = new CobolCopybookLoader();
		
		ExternalRecord extlayoutCBL = loaderCBL.loadCopyBook(
			    	    new ByteArrayInputStream(schemaLines),
			    	    Conversion.getCopyBookId("Fixed.cbl"),
			    	    CopybookLoader.SPLIT_NONE, 0, "",
		                ICopybookDialects.FMT_MAINFRAME, 0, null);
		schema = ToLayoutDetail.getInstance().getLayout(extlayoutCBL);
		
		AbstractLine[] tmpLines = {
			new Line(schema, "Line 1".getBytes()),
			new Line(schema, "Line 2".getBytes()),
			new Line(schema, "Line 3".getBytes()),
			new Line(schema, "Line 4".getBytes()),
		};
		
		lines = tmpLines;
	}

	public void testAddLines() {
		doTestAddLines(true, true);
		doTestAddLines(false, true);
		doTestAddLines(true, false);

		doTestAddLines(false, false);
	}
	
	
	public void testNewLine() {
		doTestNewLine(true, true);
		doTestNewLine(false, true);
		doTestNewLine(true, false);

		doTestNewLine(false, false);
	}

	
	public void testDelLine1() {
		doTestDelLine1(true, true);
		doTestDelLine1(false, true);
		doTestDelLine1(true, false);

		doTestDelLine1(false, false);
	}
	
	
	public void testDelLine2() {
		doTestDelLine2(true, true);
		doTestDelLine2(false, true);
		doTestDelLine2(true, false);

		doTestDelLine2(false, false);
	}
	

	private void doTestAddLines(boolean parent, boolean view) {
		TestDetails fv = new TestDetails(parent, view);
		String id= "(" + parent + ", " + view + ")";
		
		fv.addLines(0);
		checkFV(id + "Test 1a ~ ", fv);
		
		fv.addLines(1);
		checkFV(id + "Test 1b ~ ", fv);
		
		fv.addLines(0);
		checkFV(id + "Test 1c ~ ", fv);
		
		fv.addLines(fv.baseFv.parentFile.getRowCount());			
		checkFV(id + "Test 1d ~ ", fv);
		
		fv =  new TestDetails(parent, view);
		
		fv.addLines(0);
		checkFV(id + "Test 1e ~ ", fv);
		
		fv.addLines(2);
		checkFV(id + "Test 1f ~ ", fv);
		
		fv.addLines(1);
		checkFV(id + "Test 1g ~ ", fv);
		
		fv.addLines(0);
		checkFV(id + "Test 1h ~ ", fv);
		
		fv.addLines(fv.baseFv.parentFile.getRowCount());		
		checkFV(id + "Test 1i ~ ", fv);
	}
	
	private  void doTestNewLine(boolean parent, boolean view) {
		TestDetails fv = new TestDetails(parent, view);
		String id= "(" + parent + ", " + view + ")";

		fv.addNewLines(0);
		checkFV(id + "Test 2a ~ ", fv);
		
		fv = new TestDetails(parent, view);
		fv.addNewLines(1);
		checkFV(id + "Test 2b ~ ", fv);
		
		fv = new TestDetails(parent, view);
		fv.addNewLines(0);
		checkFV(id + "Test 2c ~ ", fv);
		
		
		fv = new TestDetails(parent, view);
		fv.addNewLines(fv.baseFv.parentFile.getRowCount());
		checkFV(id + "Test 2d ~ ", fv);

	}
	
	private  void doTestDelLine1(boolean parent, boolean view) {
		int[] delLineNums = {-1, 0, 1, 4};
		
		doActualDelTestLine(parent, view, true, delLineNums);
		doActualDelTestLine(parent, view, false, delLineNums);
	}

	
	private  void doTestDelLine2(boolean parent, boolean view) {
		int[] delLineNums = { 1, 2, 3, 4};
		
		doActualDelTestLine(parent, view, true, delLineNums);
		doActualDelTestLine(parent, view, false, delLineNums);
	}
	
	private  void doActualDelTestLine(boolean parent, boolean view, boolean delView, int[] delLineNums) {
		TestDetails fv = new TestDetails(parent, view, delView);
		String id= "(" + parent + ", " + view + ")";
		
		(new TestDetails(parent, view, delView)).delLines(id + "Test 3a: ", 0, delLineNums);
				
		
		fv = new TestDetails(parent, view, delView);
		for (int l : delLineNums) {
			l = fixIdx(fv.baseFv.parentFile, l);
			fv.baseFileToUse.deleteLine(fv.baseFv.parentFile.getLine(l));
			fv.testFileToUse.deleteLine(fv.testFv.parentFile.getLine(l));
			checkFV(id + "Test 3b: " + l + " ~ ", fv);
		}
		
		fv = new TestDetails(parent, view, delView);
		for (int l : delLineNums) {
			l = fixIdx(fv.baseFv.parentFile, l);
			fv.baseFileToUse.deleteLine(fv.baseFv.viewFile.getLine(l));
			fv.testFileToUse.deleteLine(fv.testFv.viewFile.getLine(l));

			checkFV(id + "Test 3c: " + l + " ~ ", fv);
		}
		
		fv = new TestDetails(parent, view, delView);
		for (int l : delLineNums) {
			l = fixIdx(fv.baseFv.parentFile, l);
			fv.baseFileToUse.deleteLine(fv.baseFv.parentFile.getLine(l));
			fv.testFileToUse.deleteLine(fv.testFv.viewFile.getLine(l));

			checkFV(id + "Test 3d: " + l + " ~ ", fv);
		}
		
		fv = new TestDetails(parent, view, delView);
		for (int l : delLineNums) {
			l = fixIdx(fv.baseFv.parentFile, l);
			fv.baseFileToUse.deleteLine(fv.baseFv.viewFile.getLine(l));
			fv.testFileToUse.deleteLine(fv.testFv.parentFile.getLine(l));

			checkFV(id + "Test 3e: " + l + " ~ ", fv);
		}
		
		fv = new TestDetails(parent, view, delView);
		delLineNums = delLineNums.clone();
		if (delLineNums[0] < 0) {
			delLineNums[0] = fv.baseFv.parentFile.getRowCount()-1;
		}
		
		fv.baseFileToUse.deleteLines(delLineNums);
		fv.testFileToUse.deleteLines(delLineNums);
		checkFV(id + "Test 3f: ~ ", fv);
	}
	
	private static int fixIdx(FileView f, int idx) {

		if (idx < 0) {
			idx = f.getRowCount() - 1;
		}
		
		return idx;
	}
	
	private FileView delTstSetup(FileAndView fv, int[] delLineNums, boolean delView) {
		FileView ret = fv.parentFile;
		fv.viewFile.addLines(0, 0, newLines());
		fv.viewFile.addLines(0, 0, newLines());
		
		if (delView) {
			ret = fv.viewFile;
		}
		
		delLineNums[0] = ret.getRowCount() - 1;
		return ret;
	}

	private  AbstractLine[] newLines() {
		 AbstractLine[] ret = new  AbstractLine[lines.length];
		 
		 for (int i = 0; i < lines.length; i++) {
			 ret[i] = lines[i].getNewDataLine();
		 }
		 
		 return ret;
	}
	
	private void checkFV(String id, TestDetails fv) {
		System.out.println(fv.baseFv.viewFile.getRowCount() + " " + fv.testFv.viewFile.getRowCount());
		for (int i = 0; i < fv.baseFv.viewFile.getRowCount(); i++) {
			System.out.print("\t>> " + fv.baseFv.viewFile.getLine(i).getFullLine() + " " + fv.testFv.viewFile.getLine(i).getFullLine()
					+ " // " + fv.baseFv.parentFile.indexOf(fv.baseFv.viewFile.getLine(i))
					+ " ~ "  + fv.testFv.parentFile.indexOf(fv.testFv.viewFile.getLine(i))
			);
		}
		System.out.println();

		for (int i = 0; i < fv.baseFv.viewFile.getRowCount(); i++) {
			assertTrue(id + " Check: " + i, 
					fv.baseFv.parentFile.indexOf(fv.baseFv.viewFile.getLine(i)) == fv.testFv.parentFile.indexOf(fv.testFv.viewFile.getLine(i)));
		}
	}
	
	
	private class TestDetails {
		public final FileAndView baseFv = new FileAndView(schema, true, true);
		public final FileAndView testFv;
		private boolean delView = false;
		
		FileView baseFileToUse, testFileToUse;
		
		public TestDetails(boolean parent, boolean view) {
			testFv = new FileAndView(schema, parent, view);
			
			addLinesView(0);
		}

		
		public TestDetails(boolean parent, boolean view, boolean delView) {
			this(parent, delView);
			this.delView = delView;
			
			addLinesView(0);
			addLinesView(0);
			
			baseFileToUse = this.baseFv.parentFile;
			testFileToUse = this.testFv.parentFile;
			if (delView) {
				baseFileToUse = this.baseFv.viewFile;
				testFileToUse = this.testFv.viewFile;
			}
		}

		public void addLinesView(int pos) {
			
			baseFv.viewFile.addLines(pos, 0, newLines());
			testFv.viewFile.addLines(pos, 0, newLines());
		}
		
		public void addLines(int pos) {
			for (int i = 0; i < lines.length; i++) {
				baseFv.parentFile.addLine(pos + i, lines[i].getNewDataLine());
				testFv.parentFile.addLine(pos + i, lines[i].getNewDataLine());
			}
		}
		
		public void addLinesProc(int pos) {
			
			baseFv.parentFile.addLines(pos, 0, newLines());
			testFv.parentFile.addLines(pos, 0, newLines());
			
		}
		
		public void addNewLines(int pos) {
			for (int i = 0; i < lines.length; i++) {
				baseFv.parentFile.getLine(baseFv.parentFile.newLine(i, 0)).setData(lines[i].getData());
				testFv.parentFile.getLine(testFv.parentFile.newLine(i, 0)).setData(lines[i].getData());
			}
		}
		
		public void delLines(String id, int pos, int[] delLineNums) {

			for (int l : delLineNums) {
				l = fixIdx(baseFileToUse, l);
				baseFileToUse.deleteLine(l);
				testFileToUse.deleteLine(l);
				checkFV(id + "Test View 3a: " + l + " ~ ", this);
			}
		}
	}
}
