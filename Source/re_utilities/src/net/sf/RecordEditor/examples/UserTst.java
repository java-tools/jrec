package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.Plugin;
import net.sf.RecordEditor.utils.common.Common;

public class UserTst implements Plugin {

	@Override
	public void execute(String param, FileView view, int[] selected) {

		Common.logMsg("         Parameter: " + param, null);
		Common.logMsg("File View contains: " + view.getRowCount() + " lines", null);
		Common.logMsg("         There are: " + selected.length + " selected lines", null);
	}

}
