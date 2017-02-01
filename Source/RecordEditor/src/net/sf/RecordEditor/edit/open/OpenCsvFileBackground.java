package net.sf.RecordEditor.edit.open;

import java.io.IOException;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.util.StandardLayouts;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.IOpenCsvFile;
import net.sf.RecordEditor.re.script.ScriptData;
import net.sf.RecordEditor.utils.common.Common;

/**
 * Class to edit a CSV file; It is used in the 
 * ScriptData class (for use in scripts)
 * 
 * @author Bruce Martin
 *
 */
public class OpenCsvFileBackground implements IOpenCsvFile {

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.open.IOpenCsvFile#open(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide open(String fileName, String font, String delim, String quote, boolean embededCr) {
		try {
			AbstractLayoutDetails schema = StandardLayouts.getInstance().getCsvLayoutNamesFirstLine(delim, font, quote, embededCr);
			FileView view = new FileView(fileName, schema, false);
			StartEditor startEditor = new StartEditor(view, fileName, false, null, 0);
			
			startEditor.doRead();
			startEditor.done();
			return startEditor.screen;
		} catch (RecordException e) {
			Common.logMsg("IO Error: " + e, null);
		} catch (IOException e) {
			Common.logMsg("Error: " + e, null);
			e.printStackTrace();
		}
		return null;
	}

	public static void register() {
		ScriptData.openCsvFile = new OpenCsvFileBackground();
	}
}
