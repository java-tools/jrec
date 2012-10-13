package net.sf.RecordEditor.utils.swing.saveRestore;

import javax.swing.JPanel;

public class SaveLoadPnl<What> {

	public final JPanel panel = new JPanel();
	public final SaveButton<What> saveBtn;
	public final LoadButton<What> updateBtn;

	public SaveLoadPnl(
			ISaveUpdateDetails<What> sourcePnl,
			String directory,
			@SuppressWarnings("rawtypes") Class classOfExternal) {
		this(sourcePnl, sourcePnl, directory, classOfExternal);
	}


	public SaveLoadPnl(
			ISaveDetails<What> sourcePnl1,
			IUpdateDetails<What> sourcePnl2,
			String directory,
			@SuppressWarnings("rawtypes") Class classOfExternal) {

		saveBtn   = new SaveButton<What>(sourcePnl1, directory);
		updateBtn = new LoadButton<What>(sourcePnl2, directory, classOfExternal);

		panel.add(saveBtn);
		panel.add(updateBtn);
	}
}
