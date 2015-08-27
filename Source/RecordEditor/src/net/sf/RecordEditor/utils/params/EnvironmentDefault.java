package net.sf.RecordEditor.utils.params;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import net.sf.JRecord.Common.Constants;


/**
 * Generic Environment interface for Windows etc
 * 
 * @author Bruce Martin
 *
 */
public class EnvironmentDefault implements IEnvironmentValues {
	
	private static File documentFile = null; 
	private static File desktopFile = null; 



	@Override
	public File getDocumentDirectory() {
		retrieveVars();
		
		return documentFile;
	}
	
	
	@Override
	public File getDesktopDirectory() {
		retrieveVars();
		
		return desktopFile;
	}
	
	private void retrieveVars() {
		if (documentFile == null ) {
			try {
				FileSystemView fileSystemView = FileSystemView.getFileSystemView();
				File f = new File(System.getProperty("user.home"));
				desktopFile  = checkFile(f, fileSystemView.getHomeDirectory(), "Desktop");
				documentFile = checkFile(f, fileSystemView.getDefaultDirectory(), "Documents");
			} catch (Throwable e) {
			}
		}
	}
	
	private File checkFile(File f1, File f2, String ext) {
		if (f1 != null) {
			if (f1.equals(f2)) {
				File tf = new File(f1.getPath() + Constants.FILE_SEPERATOR + ext);
				f2 = null;
				if (tf.exists()) {
					f2 = tf;
				}
			}
		}
		return f2;
	}

	@Override
	public File[] getStandardMountPoints() {
		return File.listRoots();
	}


	@Override
	public File[] getOtherMountPoints() {
		return null;
	}

}
