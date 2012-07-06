package net.sf.RecordEditor.layoutEd.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import net.sf.RecordEditor.layoutEd.Record.LoadCobolIntoDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;

public class LoadCobolIntoDBScreen  implements ActionListener {

	private CobolDirectoryFrame loadFrame;
	private int dbIdx;

	public LoadCobolIntoDBScreen(int databaseIdx) {

		dbIdx = databaseIdx;

		loadFrame = new CobolDirectoryFrame(
				"Load Directory",
				stripStar(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get()),
				//true, true,
				dbIdx);

		loadFrame.setActionListner(this);
		loadFrame.setVisible(true);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		String dir = loadFrame.getFileName();

		if (dir == null || "".equals(dir)) {
			loadFrame.panel.setMessageTxt("You must enter a directory where cobol copybooks are located");
			return;
		}

		File dirFile = new File(stripStar(dir));

		if ((! dirFile.exists()) || ! dirFile.isDirectory()) {
			loadFrame.panel.setMessageRplTxt("{0} is not a directory !!!", dir);
			return;
		}


		try {
			int binaryFormat = loadFrame.binaryOptions.getSelectedValue();
			int split = loadFrame.splitOptions.getSelectedValue();
			int systemId = ((Integer) loadFrame.system.getSelectedItem()).intValue();
			String fontname = loadFrame.fontName.getText();

			(new LoadCobolIntoDB()).load(dbIdx, dir, binaryFormat, split, systemId, fontname, null);
		} catch (Exception e) {
			Common.logMsgRaw(e.getMessage(), e);
		}
	}

	private static String stripStar(String dir) {
		return Parameters.dropStar(dir);
	}
}
