package net.sf.RecordEditor.layoutEd.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import net.sf.RecordEditor.utils.common.Common;

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
			loadFrame.msg.setText("You must enter a directory to save the layout");
			return;
		} 
		
		File dirFile = new File(stripStar(dir));
			
		if ((! dirFile.exists()) || ! dirFile.isDirectory()) {
			loadFrame.msg.setText(dir + " is not a directory !!!");
			return;
		}
		
		
		try {
			int binaryFormat = loadFrame.binaryOptions.getSelectedValue();
			int split = loadFrame.splitOptions.getSelectedValue();
			int systemId = ((Integer) loadFrame.system.getSelectedItem()).intValue();
			String fontname = loadFrame.fontName.getText();
			
			(new LoadCobolIntoDB()).load(dbIdx, dir, binaryFormat, split, systemId, fontname, null);
		} catch (Exception e) {
			Common.logMsg(e.getMessage(), e);
		}
	}
	
	private static String stripStar(String dir) {
		if (dir != null && dir.endsWith("*")) {
			dir = dir.substring(0, dir.length() - 1);
		}
		return dir;
	}
}
