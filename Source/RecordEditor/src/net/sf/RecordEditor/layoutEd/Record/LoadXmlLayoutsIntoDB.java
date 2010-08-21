package net.sf.RecordEditor.layoutEd.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.CopybookWriter;
import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.edit.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.filter.DirectoryFrame;


public class LoadXmlLayoutsIntoDB  implements ActionListener {

	private DirectoryFrame loadFrame = new DirectoryFrame(
			"Load Directory", Common.DEFAULT_COPYBOOK_DIRECTORY, true, true, false);
	private int dbIdx;
	
	public LoadXmlLayoutsIntoDB(int databaseIdx) {
		
		dbIdx = databaseIdx;
		
		loadFrame.saveBtn.addActionListener(this);
		loadFrame.setVisible(true);
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == loadFrame.saveBtn) {
			String dir = loadFrame.file.getText();
			
			if (dir == null || "".equals(dir)) {
				loadFrame.msg.setText("You must enter a directory to save the layout");
			} else {
				CopybookWriter writer = CopybookWriterManager.getInstance()
								.get(CopybookWriterManager.RECORD_EDITOR_XML_WRITER);
			
				ExtendedRecordDB dbTo = new ExtendedRecordDB();
				dbTo.setConnection(new ReConnection(dbIdx));
				boolean free = dbTo.isSetDoFree(false);
				
				try {
			        ExternalRecord rec;
					CopybookLoader loader = CopybookLoaderFactoryDB.getInstance()
						.getLoader(CopybookLoaderFactoryDB.RECORD_EDITOR_XML_LOADER);
					
					FilenameFilter filter = new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							// TODO Auto-generated method stub
							return name.toLowerCase().endsWith(".xml");
						}
					};
					
	
					for (File xmlFile : new File(dir).listFiles(filter)) {
						try {
							rec = loader.loadCopyBook(xmlFile.getPath(),
								CopybookLoader.SPLIT_NONE,
				                dbIdx,
				                "",
				                0,
				                0,
				                Common.getLogger());
							dbTo.checkAndUpdate(rec);
							
							Common.logMsg(AbsSSLogger.SHOW, "Loading " + xmlFile.getPath(), null);
						} catch (Exception e) {
							Common.logMsg(e.getMessage(), e);
						}
					}
					
				} catch (Exception e) {
					Common.logMsg(e.getMessage(), e);
				}	finally {
					dbTo.setDoFree(free);
				}
			}
		}
	}
}
