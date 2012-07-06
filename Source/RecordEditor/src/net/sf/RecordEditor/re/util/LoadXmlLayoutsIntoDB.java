package net.sf.RecordEditor.re.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.DirectoryFrame;


public class LoadXmlLayoutsIntoDB  implements ActionListener {

	private DirectoryFrame loadFrame = new DirectoryFrame(
			"Load Directory",
			stripStar(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.get()),
			true, true, false);
	private int dbIdx;

	public LoadXmlLayoutsIntoDB(int databaseIdx) {

		dbIdx = databaseIdx;

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
			loadFrame.panel.setMessageTxt("You must enter a directory to load layouts from");
			return;
		}

		File dirFile = new File(stripStar(dir));

		if ((! dirFile.exists()) || ! dirFile.isDirectory()) {
			loadFrame.panel.setMessageRplTxt("{0} is not a directory !!!", dir);
			return;
		}

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


			for (File xmlFile : dirFile.listFiles(filter)) {
				try {
					rec = loader.loadCopyBook(xmlFile.getPath(),
						CopybookLoader.SPLIT_NONE,
		                dbIdx,
		                "",
		                0,
		                0,
		                Common.getLogger());
					dbTo.checkAndUpdate(rec);

					Common.logMsg(AbsSSLogger.SHOW, "Loading", xmlFile.getPath(), null);
				} catch (Exception e) {
					Common.logMsgRaw(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			Common.logMsgRaw(e.getMessage(), e);
		}	finally {
			dbTo.setDoFree(free);
		}
	}

	private static String stripStar(String dir) {
		return Parameters.dropStar(dir);
	}
}
