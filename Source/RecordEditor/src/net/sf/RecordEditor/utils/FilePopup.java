package net.sf.RecordEditor.utils;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JMenu;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReActionActiveScreen;

@SuppressWarnings("serial")
public class FilePopup extends JMenu {

	

	public FilePopup(String s) {
		super(s);
	}

//	public FilePopup(Action a) {
//	super(a);
//}
//
//public FilePopup(String s, boolean b) {
//	super(s, b);
//}
	

	protected final FileItem[] getActions(FileItem[] fileList, String filename, int actionId,
			String defaultName) {


		try {
			if (fileList == null) {
				fileList = readFiles(filename, actionId);
			}	

			if (defaultName != null) {
				this.add(new ReActionActiveScreen(defaultName, actionId, filename));
			}

			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].action != null) {
					this.add(fileList[i].action);
				} else {
					FilePopup popup = new FilePopup(fileList[i].filename);
					popup.getActions(null, fileList[i].filePathName, actionId, null);
					
					this.add(popup);
				}
			}
		} catch (Exception e) {
			Common.logMsg(e.getMessage(), e);
			e.printStackTrace();
		}
		return fileList;
	}
	
	
	private static FileItem[] readFiles(String dirName, int actionId) {
		File dir ;
		String[] fileList = null;
		FileItem[] files;
		
		//System.out.println(dirName);
		if (dirName != null) {
			if (dirName.endsWith("*")) {
				dirName = dirName.substring(0, dirName.length() - 2);
			} 
		
			dir = new File(dirName);
			
			System.out.println(dirName + " " + dir.isDirectory());
			fileList = dir.list();
		}
		
		if (fileList == null || fileList.length == 0) {
			files = new FileItem[0];
		} else {
			dirName += Common.FILE_SEPERATOR;
			ArrayList<FileItem> items = new ArrayList<FilePopup.FileItem>(fileList.length);
	       	for (int i = 0; i < fileList.length; i++) {
	       		if (fileList[i].endsWith("~") || fileList[i].toLowerCase().endsWith(".bak")) {
	       		} else {
	       			items.add(new FileItem(fileList[i], 
		       				dirName + fileList[i], 
		       				actionId));
	       		}
	       	}
			files = new FileItem[items.size()];
			files = items.toArray(files);
		} 
		return files;
	}
	
	protected static class FileItem {
		String filename, filePathName;
		
		ReActionActiveScreen action = null;
		public FileItem(String filename, String filePathName, int actionId) {
			super();
			this.filename = filename;
			this.filePathName = filePathName;
			
			if (! (new File(filePathName)).isDirectory()) {
				action =  new ReActionActiveScreen(filename, 
	       				actionId, 
	       				filePathName);
			}
		}
		
	}
}
