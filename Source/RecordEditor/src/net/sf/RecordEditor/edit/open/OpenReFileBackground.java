package net.sf.RecordEditor.edit.open;

import java.io.IOException;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.IOpenReFile;
import net.sf.RecordEditor.re.script.ScriptData;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;

/**
 * This class will open a file (with a specified layout)
 * and put the user in edit o it. It use from ScriptData
 * in user written scripts.
 * 
 * @author Bruce Martin
 *
 */
public class OpenReFileBackground implements IOpenReFile {

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.open.IOpenReFile#openFile(java.lang.String, java.lang.String)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide openFile(String fileName, String layoutName) {
		AbstractFileDisplayWithFieldHide ret = null;
		try {
			CopyBookDbReader layoutReader = new CopyBookDbReader();
			LayoutDetail schema = layoutReader.getLayout(layoutName);	
			FileView view = new FileView(fileName, schema, false);
			StartEditor startEditor = new StartEditor(view, fileName, false, null, 0);
			
			startEditor.doRead();
			startEditor.done();
			ret = startEditor.screen;
		} catch (RecordException e) {
			Common.logMsg("IO Error: " + e, null);
		} catch (IOException e) {
			Common.logMsg("Error: " + e, null);
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static void register() {
		ScriptData.openReFile = new OpenReFileBackground();
	}
}
