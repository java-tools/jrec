package net.sf.RecordEditor.layoutEd.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.JRecord.External.CopybookWriter;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.RecordEditor.edit.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.filter.DirectoryFrame;
import net.sf.RecordEditor.utils.jdbc.AbsDB;

public class SaveRecordAsXml  implements ActionListener {

	private DirectoryFrame saveFrame = new DirectoryFrame(
			"Save Layout", Common.DEFAULT_COPYBOOK_DIRECTORY, true, true, true);
	private int dbIdx;
	private String idStr;
	
	public SaveRecordAsXml(int databaseIdx, int recordId) {
		
		idStr = recordId + "";
		dbIdx = databaseIdx;
		
		saveFrame.saveBtn.addActionListener(this);
		saveFrame.setVisible(true);
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == saveFrame.saveBtn) {
			String dir = saveFrame.file.getText();
			
			if (dir == null || "".equals(dir)) {
				saveFrame.msg.setText("You must enter a directory to save the layout");
			} else {
				RecordRec r;
			
				ExtendedRecordDB dbFrom = new ExtendedRecordDB();
				
				CopybookLoaderFactoryDB.setCurrentDB(dbIdx);
				dbFrom.setConnection(new ReConnection(dbIdx));
				dbFrom.resetSearch();
				dbFrom.setSearchArg("RecordId", AbsDB.opEquals, idStr);
	
				dbFrom.open();
				
				r = dbFrom.fetch();
				
				if (r != null) {
					CopybookWriter writer = CopybookWriterManager.getInstance()
								.get(CopybookWriterManager.RECORD_EDITOR_XML_WRITER);
					try {
						writer.writeCopyBook(dir, r.getValue(), Common.getLogger());
						saveFrame.setVisible(false);
						saveFrame = null;
					} catch (Exception e) {
						saveFrame.msg.setText("Error saving layout: " + e.getMessage());
						e.printStackTrace();
					}
				}
				
				dbFrom.close();
			}
		}
	}
}
