package net.sf.RecordEditor.edit.open;


import javax.swing.SwingWorker;

import net.sf.RecordEditor.utils.common.Common;

public class StartEditorBackGround extends SwingWorker<Void, Void> {
	StartEditor startEditor;
	//RecentFilesList recentList;


	public StartEditorBackGround(StartEditor startEd) {
		super();
		startEditor = startEd;
	}

	public void doEdit() {
		if (Common.OPTIONS.loadInBackgroundThread.isSelected()) {
			execute();
		} else {
			doInBackground();
			done();
		}
	}

	@Override
	public Void doInBackground() {
		startEditor.doRead();
		return null;
	}

 
	@Override
	public void done() {
		
		startEditor.done();
	}
}
