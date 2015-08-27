package net.sf.RecordEditor.re.script;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.AbstractAction;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReMenu;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReActionActiveScreen;

@SuppressWarnings("serial")
public class FilePopup extends ReMenu implements Comparator<File> {



	public FilePopup(String s) {
		super(s);
	}


	protected final FileItem[] getActions(FileItem[] fileList, String filename, int actionId,
			String defaultName, ValidExtensionCheck checkExtension) {
		return getActions(this, fileList, filename, actionId, defaultName, checkExtension);
	}


	private final FileItem[] getActions(FilePopup popup, FileItem[] fileList, String filename, int actionId,
			String defaultName, ValidExtensionCheck checkExtension) {


		try {
			filename = Parameters.dropStar(filename);
			if (fileList == null) {
				fileList = readFiles(filename, actionId, checkExtension);
			}

			if (defaultName != null) {
				popup.add(new ReActionActiveScreen(defaultName, actionId, filename));
			}

			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].action != null) {
					popup.add(fileList[i].action);
				} else {
					FilePopup newPopup = new FilePopup(fileList[i].filename);
					getActions(newPopup, null, fileList[i].filePathName, actionId, null, checkExtension);
					newPopup.setIcon(Common.getRecordIcon(Common.ID_MENU_FOLDER));
					popup.add(newPopup);
				}
			}
		} catch (Exception e) {
			Common.logMsgRaw(e.getMessage(), e);
			e.printStackTrace();
		}
		return fileList;
	}


	private FileItem[] readFiles(String dirName, int actionId, ValidExtensionCheck checkExtension) {
		File dir ;
		File[] fileList = null;
		FileItem[] files;
		boolean ok;

		//System.out.println(dirName);
		if (dirName != null) {
			dirName =  Parameters.dropStar(dirName);

			dir = new File(dirName);

			fileList = dir.listFiles();
		}

		if (fileList == null || fileList.length == 0) {
			files = new FileItem[0];
		} else {
			if (dirName != null && ! (dirName.endsWith("/") || dirName.endsWith("\\"))) {
				dirName += Common.FILE_SEPERATOR;
			}
			ArrayList<FileItem> items = new ArrayList<FilePopup.FileItem>(fileList.length);

			Arrays.sort(fileList, this);
	       	for (int i = 0; i < fileList.length; i++) {
	       		String fileName = fileList[i].getName();
				if (fileName.endsWith("~") || fileName.toLowerCase().endsWith(".bak")) {
	       		} else {
	       			try {
	       				String filePathName = fileList[i].getCanonicalPath();//dirName + fileList[i];
	       				boolean isDirectory = fileList[i].isDirectory();
	       				boolean addDirectory = false;
	       				
	       				if (isDirectory && checkExtension != null) {
		    				File[] dirFiles = fileList[i].listFiles();
		    				
		    				
		    				if (dirFiles != null && dirFiles.length > 0) {
			    				for (File f : dirFiles) {
			    					if (f != null && (f.isDirectory() || checkExtension.isValidExtension(Parameters.getExtensionOnly(f.getName())))) {
			    						addDirectory = true;
			    						break;
			    					}
			    					//System.out.print("\t" + f.getName());
			    				}
		    				}
		    				//System.out.println(" Checked: " + fileList[i].getName() + " " + addDirectory + " " + dirFiles.length);
	       				}

		       			ok = checkExtension == null
		       			  || (isDirectory && addDirectory)
		       			  || checkExtension.isValidExtension(Parameters.getExtensionOnly(fileName));

		       			if (ok) {
		       				AbstractAction action = null;
		       				if (! isDirectory) {
		       					action = getAction(actionId, fileName, filePathName);
		       				}

			       			items.add(new FileItem(fileName,
			       					filePathName,
				       				action));
		       			}
	       			} catch (Exception e) {
						e.printStackTrace();
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



	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(File o1, File o2) {
		if (o1.isDirectory() == o2.isDirectory()) {
			return o1.getName().compareTo(o2.getName());
		} else if (o1.isDirectory()) {
			return -1;
		}
		return 1;
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
