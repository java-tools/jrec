package net.sf.RecordEditor.re.db.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.base.CopybookWriter;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.DirectoryFrame;

public class SaveRecordAsXml  implements ActionListener {

	private DirectoryFrame saveFrame = new DirectoryFrame(
			"Save Layout",
			Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get(),
			true, true, true);
	private final int dbIdx;
	//private String idStr;
	private final int recordId;

	public SaveRecordAsXml(int databaseIdx, int recordId) {

		this.recordId = recordId;
		dbIdx = databaseIdx;

		saveFrame.setActionListner(this);
		saveFrame.setVisible(true);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		String dir = saveFrame.getFileName();

		if (dir == null || "".equals(dir)) {
			saveFrame.panel.setMessageTxtRE("You must enter a directory to save the layout");
		} else {
			CopybookLoaderFactoryDB.setCurrentDB(dbIdx);
			RecordRec r = ExtendedRecordDB.getRecord(dbIdx, recordId);

			if (r != null) {
				CopybookWriter writer = CopybookWriterManager.getInstance()
							.get(CopybookWriterManager.RECORD_EDITOR_XML_WRITER);
				try {
					writer.writeCopyBook(dir, r.getValue(), Common.getLogger());
					saveFrame.setVisible(false);
					saveFrame = null;
				} catch (Exception e) {
					saveFrame.panel.setMessageTxtRE("Error saving layout:",  e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}
}
