package net.sf.RecordEditor.layoutEd.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.JRecord.External.CopybookWriter;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.swing.DirectoryFrame;

public class SaveLayoutsDBAsXml  implements ActionListener {

	private DirectoryFrame saveFrame = new DirectoryFrame(
			"Save Directory",
			Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get(),
			true, true, true);
	private int dbIdx;

	public SaveLayoutsDBAsXml(int databaseIdx) {

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
			saveFrame.panel.setMessageTxt("You must enter a directory to save the layout");
		} else {
			RecordRec r;
			CopybookWriter writer = CopybookWriterManager.getInstance()
							.get(CopybookWriterManager.RECORD_EDITOR_XML_WRITER);

			ExtendedRecordDB dbFrom = new ExtendedRecordDB();

			CopybookLoaderFactoryDB.setCurrentDB(dbIdx);

			boolean free = dbFrom.isSetDoFree(false);

			try {
				dbFrom.setConnection(new ReConnection(dbIdx));
				dbFrom.resetSearch();
				dbFrom.setSearchListChar(AbsDB.opEquals, "Y");

				dbFrom.open();


				while ((r = dbFrom.fetch()) != null) {
					try {
						writer.writeCopyBook(dir, r.getValue(), Common.getLogger());

						Common.logMsg(AbsSSLogger.SHOW, "Saving", r.getValue().getRecordName(), null);
					} catch (Exception e) {
						saveFrame.panel.setMessageTxt("Error saving layout:", e.getMessage());
						Common.logMsg(AbsSSLogger.SHOW,
								"Error saving layout: " + e.getMessage()
								+ ": " +  r.getValue().getRecordName(), null);
						e.printStackTrace();
					}
				}
				saveFrame.setVisible(false);
				saveFrame = null;
			}	finally {
				dbFrom.setDoFree(free);
			}
		}
	}
}
