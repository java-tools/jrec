package net.sf.RecordEditor.utils;

import java.io.File;

import javax.swing.JMenu;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReActionActiveScreen;

public class VelocityPopup extends JMenu {

	private static ReActionActiveScreen[] actionList = null;
	private static boolean doLayout = true;
	
	public VelocityPopup() {
		super("Save via Velociy Skelton");
		
		this.setIcon(Common.getReActionIcon(ReActionHandler.SAVE_AS_VELOCITY));
		
        if (doLayout) {
        	try {
        		if (actionList == null) {
					readFiles();
				}

        		if (doLayout) {
        			for (int i = 0; i < actionList.length; i++) {
        				this.add(actionList[i]);
        			}
        		}
        	} catch (Exception e) {
				Common.logMsg(e.getMessage(), e);
				e.printStackTrace();
			}
        }
     }

	
	private static void readFiles() {
		String dirName = Common.DEFAULT_VELOCITY_DIRECTORY;
		File dir ;
		String[] fileList ;
		
		//System.out.println(dirName);
		if (dirName.endsWith("*")) {
			dirName = dirName.substring(0, dirName.length() - 2);
		} 

		dir = new File(dirName);
		
		System.out.println(dirName + " " + dir.isDirectory());
		fileList = dir.list();
		
		if (fileList != null && fileList.length > 0) {
			dirName += Common.FILE_SEPERATOR;
			actionList = new ReActionActiveScreen[fileList.length];
	       	for (int i = 0; i < fileList.length; i++) {
	       		actionList[i] = new ReActionActiveScreen(fileList[i], 
	       				ReActionHandler.SAVE_AS_VELOCITY, 
	       				dirName + fileList[i]);
	       	}
		} else {
			doLayout = false;
		}
	}
	
	public final static VelocityPopup getPopup() {
		VelocityPopup ret = null;
		if (Common.isVelocityAvailable()) {
			ret = new VelocityPopup();
		}
		return ret;
	}
}
