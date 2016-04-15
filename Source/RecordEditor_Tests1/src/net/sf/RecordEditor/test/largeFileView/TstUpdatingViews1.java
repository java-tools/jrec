package net.sf.RecordEditor.test.largeFileView;

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
import net.sf.RecordEditor.utils.common.Common;
import junit.framework.TestCase;

/**
 * This Class tests updating (delete, insert) file-views nd makes sure
 * the original file + view get updated correctly
 * 
 * @author Bruce Martin
 *
 */
public class TstUpdatingViews1 extends TestCase {

	private final byte[] schemaLines = 
		( "        01  Rec-20."
		+ "            03 Field-1          Pic x(20).").getBytes();
	
	
	private final LayoutDetail schema;
	private final AbstractLine[] lines;
	
	public TstUpdatingViews1() throws RecordException {
		
				
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
		Common.OPTIONS.agressiveCompress.set(false);
		
		doTestAddLines(true, true);
		doTestAddLines(false, true);
		doTestAddLines(true, false);

		doTestAddLines(false, false);
	}
	
	
	public void testNewLine() {
		Common.OPTIONS.agressiveCompress.set(false);
		
		doTestNewLine(true, true);
		doTestNewLine(false, true);
		doTestNewLine(true, false);

		doTestNewLine(false, false);
	}

	
	public void testDelLine() {
		Common.OPTIONS.agressiveCompress.set(false);
		
		doTestDelLine(true, true);
		doTestDelLine(false, true);
		doTestDelLine(true, false);

		doTestDelLine(false, false);
	}
	
	private void doTestAddLines(boolean parent, boolean view) {
		FileAndView fv = new FileAndView(schema, parent, view);
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.addLine(i, lines[i].getNewDataLine());
		}
		
		String id= "(" + parent + ", " + view + ")";
		
		checkFV(id + "Test 1a ~ ", fv);
		
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.addLine(i + 1, lines[i].getNewDataLine());
		}
		
		checkFV(id + "Test 1b ~ ", fv);
		
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.addLine(i , lines[i].getNewDataLine());
		}
			
		checkFV(id + "Test 1c ~ ", fv);
		
		int st = fv.viewFile.getRowCount();
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.addLine(st + i, lines[i].getNewDataLine());
		}
			
		checkFV(id + "Test 1d ~ ", fv);
		
		fv = new FileAndView(schema, parent, view);
		
		fv.viewFile.addLines(0, 0, newLines());
		
		checkFV(id + "Test 1e ~ ", fv);
		
		fv.viewFile.addLines(2, 0, newLines());
		
		checkFV(id + "Test 1f ~ ", fv);
		
		fv.viewFile.addLines(1, 0, newLines());
		
		checkFV(id + "Test 1g ~ ", fv);
		
		fv.viewFile.addLines(0, 0, newLines());
		
		checkFV(id + "Test 1h ~ ", fv);
		
		fv.viewFile.addLines(fv.viewFile.getRowCount(), 0, newLines());
		
		checkFV(id + "Test 1i ~ ", fv);
	}
	
	private  void doTestNewLine(boolean parent, boolean view) {
		FileAndView fv = new FileAndView(schema, parent, view);
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.getLine(fv.viewFile.newLine(i, 0)).setData(lines[i].getData());
		}
		
		String id= "(" + parent + ", " + view + ")";
		
		checkFV(id + "Test 2a ~ ", fv);
		
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.getLine(fv.viewFile.newLine(i + 1, 0)).setData(lines[i].getData());
		}
		
		checkFV(id + "Test 2b ~ ", fv);
		
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.getLine(fv.viewFile.newLine(i, 0)).setData(lines[i].getData());
		}
			
		checkFV(id + "Test 2c ~ ", fv);
		
		int st = fv.viewFile.getRowCount();
		for (int i = 0; i < lines.length; i++) {
			fv.viewFile.getLine(fv.viewFile.newLine(i + st, 0)).setData(lines[i].getData());
		}
			
		checkFV(id + "Test 2d ~ ", fv);

	}
	
	private  void doTestDelLine(boolean parent, boolean view) {
		doActualDelTestLine(parent, view, true);
		doActualDelTestLine(parent, view, false);
	}

	private  void doActualDelTestLine(boolean parent, boolean view, boolean delView) {
		FileAndView fv;
		int[] delLineNums = {0, 0, 1, 4};
		String id= "(" + parent + ", " + view + ")";
		
		FileView deleteFile = delTstSetup((fv = new FileAndView(schema, parent, view)), delLineNums, delView);
		
		for (int l : delLineNums) {
			deleteFile.deleteLine(l);
			checkFV(id + "Test 3a: " + l + " ~ ", fv);
		}
		
		deleteFile = delTstSetup((fv = new FileAndView(schema, parent, view)), delLineNums, delView);
		
		for (int l : delLineNums) {
			deleteFile.deleteLine(fv.parentFile.getLine(l));
			checkFV(id + "Test 3b: " + l + " ~ ", fv);
		}
		
		deleteFile = delTstSetup((fv = new FileAndView(schema, parent, view)), delLineNums, delView);
		
		for (int l : delLineNums) {
			deleteFile.deleteLine(fv.viewFile.getLine(l));
			checkFV(id + "Test 3c: " + l + " ~ ", fv);
		}
		
		deleteFile = delTstSetup((fv = new FileAndView(schema, parent, view)), delLineNums, delView);
		deleteFile.deleteLines(delLineNums);
		checkFV(id + "Test 3d: ~ ", fv);
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
	
	private void checkFV(String id, FileAndView fv) {
		System.out.println(fv.parentFile.getRowCount() + " " + fv.viewFile.getRowCount());
		for (int i = 0; i < fv.parentFile.getRowCount(); i++) {
			System.out.print("\t> " + fv.parentFile.getLine(i).getFullLine() + " " + fv.viewFile.getLine(i).getFullLine());
		}
		System.out.println();

		for (int i = 0; i < fv.parentFile.getRowCount(); i++) {
			assertTrue(id + " Check: " + i, fv.parentFile.getLine(i) == fv.viewFile.getLine(i));
		}
	}
}
