package net.sf.RecordEditor.diff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jlibdiff.Diff;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.re.openFile.ISchemaProvider;
import net.sf.RecordEditor.utils.common.Common;


public class DoCompare  {

//	private static final String DIR = "/home/bm/RecordEdit/HSQLDB/SampleFiles/";

	private static DoCompare instance = new DoCompare();

	/**
	 * Do compare
	 * @param layoutReader layout reader
	 * @param layoutReader2 layout reader
	 * @param def Diff (compare) definition
	 *
	 * @throws Exception any error
	 */
	public final void compare(ISchemaProvider layoutReader,
			ISchemaProvider layoutReader2, DiffDefinition def)
	throws Exception {

		if (DiffDefinition.TYPE_SINGLE_LAYOUT.equals(def.type)) {
			AbstractLayoutDetails dtl = getLayout(layoutReader, def.layoutDetails.name, def.oldFile.name);
			compare1Layout(dtl, def);
		} else {
			AbstractLayoutDetails dtl1 = getLayout(layoutReader,  def.oldFile.layoutDetails.name, def.oldFile.name);
			AbstractLayoutDetails dtl2 = getLayout(layoutReader2, def.newFile.layoutDetails.name, def.newFile.name);

			compare2Layouts(dtl1, dtl2, def);
		}
	}

	/**
	 * write HTML file
	 *
	 * @param layoutReader  layout reader
	 * @param layoutReader2 layout reader 2
	 * @param def diff definition
	 *
	 * @throws Exception any error
	 */
	public final void writeHtml(ISchemaProvider layoutReader,
			ISchemaProvider layoutReader2, DiffDefinition def)
	throws Exception {

		Diff diff = new Diff();
		LineBufferedReader oldReader ;
		LineBufferedReader newReader;
		AbstractLayoutDetails dtl1, dtl2, dtl3;
		Visitor vis;
		ArrayList<LineCompare> before, after;

		if (DiffDefinition.TYPE_SINGLE_LAYOUT.equals(def.type)) {
			dtl1 = getLayout(layoutReader, def.layoutDetails.name, def.oldFile.name);
			oldReader = new LineBufferedReader(def.oldFile.name,
					dtl1, null, def.layoutDetails, def.stripTrailingSpaces);
			newReader = new LineBufferedReader(def.newFile.name,
					dtl1, oldReader.getFilteredLayout(), def.layoutDetails, def.stripTrailingSpaces);


		} else {
			dtl1 = getLayout(layoutReader,  def.oldFile.layoutDetails.name, def.oldFile.name);
			dtl2 = getLayout(layoutReader2, def.newFile.layoutDetails.name, def.newFile.name);

			oldReader = new LineBufferedReader(def.oldFile.name,
					dtl1, null, def.oldFile.layoutDetails, def.stripTrailingSpaces);
			newReader = new LineBufferedReader(def.newFile.name,
					dtl2, null, def.newFile.layoutDetails, def.stripTrailingSpaces);
		}

		vis = new Visitor(oldReader, newReader);

		diff.diffBuffer(oldReader, newReader);
		diff.accept(vis);

		if (def.allRows && def.allFields) {
			before = vis.getOldList();
			after  = vis.getNewList();
		} else {
			before = vis.getOldChanged();
			after  = vis.getNewChanged();
		}

		dtl3 = oldReader.getFilteredLayout();
		if (dtl3 == null) {
			dtl3 = dtl1;
		}
		WriteHtml writeHtml = WriteHtml.getInstance();
		if (def.singleTable) {
			writeHtml.writeSingleTbl(def, dtl3, before, after);
		} else if (def.allFields) {
			writeHtml.writeTblAllFields(def, dtl3, before, after);
		} else {
			writeHtml.writeTblChgFields(def, dtl3, before, after);
		}
	}


	/**
	 * Get a record layout
	 *
	 * @param layoutReader Layout Reader
	 * @param name Layout name
	 * @param fileName Sample file name
	 *
	 * @return requested layout
	 * @throws Exception any error
	 */
	private AbstractLayoutDetails getLayout(ISchemaProvider layoutReader,
			String name, String fileName) throws Exception {

		try {
			return layoutReader.getRecordLayout(name, fileName);
		} catch (Exception e) {
			String s = "Error Loading Layout:";
			e.printStackTrace();
			Common.logMsg(s, e);
			throw e;
		}
	}

	/**
	 * Compare files (using 1 layout)
	 *
	 * @param dtl Record Layout
	 * @param diffDefinition diff Definition
	 * @throws IOException any IO Error
	 * @throws RecordException any record-editor exception
	 */
	private final void compare1Layout(
			AbstractLayoutDetails dtl, DiffDefinition diffDefinition)
	throws IOException, RecordException {

		LineBufferedReader oldReader = new LineBufferedReader(diffDefinition.oldFile.name,
				dtl, null, diffDefinition.layoutDetails, diffDefinition.stripTrailingSpaces);
		LineBufferedReader newReader = new LineBufferedReader(diffDefinition.newFile.name,
				dtl, oldReader.getFilteredLayout(), diffDefinition.layoutDetails,
				diffDefinition.stripTrailingSpaces);

		compare1Layout(oldReader, newReader);
	}


	public final void compare1Layout(AbstractLayoutDetails dtl, String oldFile, String newFile)
	throws IOException {
		LineBufferedReader oldReader = new LineBufferedReader(
				oldFile, dtl, null, null, false);
		if (dtl.getFileStructure() == Constants.IO_XML_BUILD_LAYOUT) {
			dtl = oldReader.getLayout();
		}
		LineBufferedReader newReader = new LineBufferedReader(
				newFile, dtl, null, null, false);

		String oldFn = new File(oldFile).getName();
		String newFn = new File(newFile).getName();
		
		if (! oldFn.equalsIgnoreCase(newFn)) {
			oldFn = oldFn + " ~ " + newFn;
		}
		
		Visitor vis = getVisitor(oldReader, newReader);

		new TableDisplay("Compare: " + oldFn, oldReader.getFilteredLayout(),
				vis.getOldList(),    vis.getNewList(),
				vis.getOldChanged(), vis.getNewChanged(), true);
	}


	public final void compare1Layout(LineBufferedReader oldReader, LineBufferedReader newReader)
	throws IOException {

		Visitor vis = getVisitor(oldReader, newReader);

		new TableDisplay("Single Layout Compare", oldReader.getFilteredLayout(),
				vis.getOldList(),    vis.getNewList(),
				vis.getOldChanged(), vis.getNewChanged(), true);
	}

	private Visitor getVisitor(LineBufferedReader oldReader, LineBufferedReader newReader) throws IOException {
		Diff diff = new Diff();
		Visitor vis = new Visitor(oldReader, newReader);

		//System.out.println("Starting ... ");
		diff.diffBuffer(oldReader, newReader);
		diff.accept(vis);
		return vis;
	}
	
	public final boolean isDifferent1Layout(AbstractLayoutDetails dtl, String filename1, String filename2)
	throws IOException {

		AbstractLineIOProvider ioProvider = LineIOProvider.getInstance();

		AbstractLineReader reader1 = ioProvider.getLineReader(dtl);

		AbstractLine line1, line2;
		Object o1, o2;
		
		reader1.open(filename1);
		
		line1 = reader1.read();
		boolean ok = true;

		AbstractLineReader reader2 = ioProvider.getLineReader(dtl);
		if (dtl.getFileStructure() == Constants.IO_XML_BUILD_LAYOUT) {
			dtl = reader1.getLayout();
		}
		reader2.open(filename2, dtl);

		while ((line2 = reader2.read()) != null && line1 != null && ok) {
			int recordIdx = line1.getPreferredLayoutIdx();
			if (recordIdx != line2.getPreferredLayoutIdx()) {
				ok = false;
			} else {
				recordIdx = Math.max(0, recordIdx);
				AbstractRecordDetail r = dtl.getRecord(recordIdx);
				for (int i = 0; ok && i < r.getFieldCount(); i++ ) {
					o1 = line1.getField(recordIdx, i);
					o2 = line2.getField(recordIdx, i);
					
					ok = o1 == o2 || (o1 != null && o1.equals(o2));
					if (! ok) { 
						System.out.println("====>> " + i);
					}
				}
				if (! ok) {
					System.out.println(line1.getFullLine());
					System.out.println(line2.getFullLine());
				}
			}
			line1 = reader1.read();
		}
		ok = ok && (line1 == line2); // sneaky test of line1 == null && line2 == null
		
		reader1.close();
		reader2.close();
		
		return ! ok;
	}


	/**
	 * Compare files using 2 layouts
	 * @param dtl1 record layout 1
	 * @param dtl2 record layout 2
	 * @param diffDefinition diff Definition
	 * @throws IOException any IO Error
	 * @throws RecordException any record-editor exception
	 */
	private final void compare2Layouts(AbstractLayoutDetails dtl1,
			                                                  AbstractLayoutDetails dtl2,
								DiffDefinition diffDefinition)
	throws IOException, RecordException {

		Diff diff = new Diff();
		LineBufferedReader oldReader = new LineBufferedReader(diffDefinition.oldFile.name,
				dtl1, null, diffDefinition.oldFile.layoutDetails, diffDefinition.stripTrailingSpaces);
		LineBufferedReader newReader = new LineBufferedReader(diffDefinition.newFile.name,
				dtl2, null, diffDefinition.newFile.layoutDetails, diffDefinition.stripTrailingSpaces);


		Visitor vis = new Visitor(oldReader, newReader);

		//diff.diffString(arg0, arg1)
		diff.diffBuffer(oldReader, newReader);
		diff.accept(vis);

		new TableDisplay("Single Layout Compare", oldReader.getFilteredLayout(),
				vis.getOldList(),    vis.getNewList(),
				vis.getOldChanged(), vis.getNewChanged(), true);
	}


   /**
    * @return the instance of compare files
    */
   public static final DoCompare getInstance() {
	   return instance;
   }
}
