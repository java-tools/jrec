package net.sf.RecordEditor.re.script;

import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenu;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReActionActiveScreen;

@SuppressWarnings("serial")
public class FilePopup extends JMenu {

	

	public FilePopup(String s) {
		super(s);
	}
	

	protected final FileItem[] getActions(FileItem[] fileList, String filename, int actionId,
			String defaultName, ValidExtensionCheck checkExtension) {


		try {
			filename = Parameters.dropStar(filename);
			if (fileList == null) {
				fileList = readFiles(filename, actionId, checkExtension);
			}	

			if (defaultName != null) {
				this.add(new ReActionActiveScreen(defaultName, actionId, filename));
			}

			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].action != null) {
					this.add(fileList[i].action);
				} else {
					FilePopup popup = new FilePopup(fileList[i].filename);
					popup.getActions(null, fileList[i].filePathName, actionId, null, checkExtension);
					
					this.add(popup);
				}
			}
		} catch (Exception e) {
			Common.logMsg(e.getMessage(), e);
			e.printStackTrace();
		}
		return fileList;
	}
	
	
	private FileItem[] readFiles(String dirName, int actionId, ValidExtensionCheck checkExtension) {
		File dir ;
		String[] fileList = null;
		FileItem[] files;
		boolean ok;
		
		//System.out.println(dirName);
		if (dirName != null) {
			dirName =  Parameters.dropStar(dirName);
				
			dir = new File(dirName);
			
			fileList = dir.list();
		}
		
		if (fileList == null || fileList.length == 0) {
			files = new FileItem[0];
		} else {
			if (dirName != null && ! (dirName.endsWith("/") || dirName.endsWith("\\"))) {
				dirName += Common.FILE_SEPERATOR;
			}
			ArrayList<FileItem> items = new ArrayList<FilePopup.FileItem>(fileList.length);
	       	for (int i = 0; i < fileList.length; i++) {
	       		if (fileList[i].endsWith("~") || fileList[i].toLowerCase().endsWith(".bak")) {
	       		} else {
	       			ok = checkExtension == null 
	       			  || checkExtension.isValidExtension(Parameters.getExtensionOnly(fileList[i]));

	       			if (ok) {
	       				String filePathName = dirName + fileList[i];
	       				AbstractAction action = null;
	       				if (! (new File(filePathName)).isDirectory()) {
	       					action = getAction(actionId, fileList[i], filePathName);
	       				}
	       				
		       			items.add(new FileItem(fileList[i], 
		       					filePathName, 
			       				action));
	       			}
	       		}
	       	}
			files = new FileItem[items.size()];
			files = items.toArray(files);
		} 
		return files;
	}
	
	protected AbstractAction getAction(int actionId, String filename, String filePathName) {
		return new ReActionActiveScreen(
						filename, 
						actionId, 
						filePathName);
	}
	
	public final static class FileItem {
		public final String filename, filePathName;
		
		public final AbstractAction action;
		
		public FileItem(String filename, String filePathName, AbstractAction action) {
			super();
			this.filename = filename;
			this.filePathName = filePathName;
			this.action = action;
		}
	}
}
