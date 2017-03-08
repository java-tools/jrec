package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.edit.open.OpenCsvFilePnl;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class CsvOpenAction extends ReAbstractAction {

	private OpenFile open = null;
	private  OpenCsvFilePnl csvPnl;
	private final String fileName;
	private final AbstractLineIOProvider ioProvider;

	public CsvOpenAction(final String fileName, AbstractLineIOProvider pIoProvider) {
		super("Open Basic CSV file",
        	 Common.ID_OPEN_ICON);

		this.fileName = fileName;
		this.ioProvider = pIoProvider;
	}

	public void actionPerformed(ActionEvent e) {

		if (open == null) {
			csvPnl = new OpenCsvFilePnl(
								fileName,
								net.sf.RecordEditor.utils.params.Parameters
										.getApplicationDirectory() + "CsvFiles.txt",
								ioProvider,
								false,
								false);

//long time1 = System.nanoTime();
        	open = new OpenFile(csvPnl, getLogWidth(), getOpenHeight());
        	csvPnl.setParentFrame(open);
			csvPnl.selectCsvExt();
		}
		open.setVisible(true);
	}


	private int getOpenHeight() {
        return net.sf.RecordEditor.utils.screenManager.ReMainFrame
        				.getMasterFrame().getDesktopHeight() * 4 / 5;
	}


	private int getLogWidth() {
        return net.sf.RecordEditor.utils.screenManager.ReMainFrame
        				.getMasterFrame().getDesktop().getWidth() - 
        	   net.sf.RecordEditor.utils.swing.SwingUtils.STANDARD_FONT_WIDTH;
	}

}
