/**
 *
 */
package net.sf.RecordEditor.utils.swing.treeCombo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import net.sf.RecordEditor.utils.params.Parameters;

/**
 * @author Bruce Martin
 *
 */
public class CommonCode {

	private static final String DIREXTORY_EXTENSION = "dir.";

	public static List<File> getDirectoryList(String paramId, int numEntries, boolean canBeNew) {
		List<String> dirs = Parameters.getStringList(paramId + DIREXTORY_EXTENSION, numEntries);
		ArrayList<File> itms = new ArrayList<File>(numEntries);
		HashSet<String> used = new HashSet<String>(dirs.size() * 3 / 2);
		
		File f = null;
		for (String dir : dirs) {
			if ((! used.contains(dir) && ! "".equals(dir.trim()))
			&& ((f = new File(dir)).exists() && (f.isDirectory())) || canBeNew) {
				itms.add(f);
				used.add(dir);
			}
		}
		
		if (itms.size() < numEntries) {
			List<String> files = Parameters.getStringList(paramId + DIREXTORY_EXTENSION, numEntries);
			for (String fn : files) {
				if (fn != null && fn.length() > 1) {
					f = new File(fn).getParentFile();
					if (f != null) {
						String path = f.getPath();
						if ((! used.contains(path)) 
						&& (canBeNew || (f.exists() && (f.isDirectory())))) {
							itms.add(f);
							used.add(path);
						}
					}
				}
			}
		}

		return itms;
	}
	
	public static void saveDirectoryList(String paramId, ListIterator<File> list) {
		paramId = paramId + DIREXTORY_EXTENSION;
		
		int i = 0;

		while (list.hasNext()) {
			File itm = list.next();
			if (itm != null) {
				Parameters.setArrayItem(paramId, i++, itm.getPath());
			}
		}
		Parameters.writeListProperties();
	}
	/**
	 * Retrieve a list of files / dirs from the parameter file
	 *
	 * @param paramId parameter id
	 * @param numEntries maximum number of entries to retrieve
	 * @param directory whether to retrive directories of files
	 *
	 * @return list of files / directories
	 */
	public static List<FileTreeComboItem> getFileList(String paramId, int numEntries, boolean canBeNew, boolean directory) {
		List<String> dirs = Parameters.getStringList(paramId, numEntries);

		ArrayList<FileTreeComboItem> itms = new ArrayList<FileTreeComboItem>(dirs.size());
		HashSet<String> used = new HashSet<String>(dirs.size() * 3 / 2);
		int i = 0;
		File f;
		for (String dir : dirs) {
			if (! used.contains(dir) && ! "".equals(dir.trim())
			&& ((f = new File(dir)).exists() || canBeNew)
			&& (f.isDirectory() == directory)) {
				itms.add(new FileTreeComboItem(i++, f));
				used.add(dir);
			}
		}

		return itms;
	}

	/**
	 * Save a list of Recent Files / Directories back to the parameters file
	 * @param list files/dirs to be saves
	 */
	public static void saveFileList(String paramId, ListIterator<FileTreeComboItem> list, boolean save) {
		if (list != null && list.hasNext()) {
			int i = 0;
	
			do {
				FileTreeComboItem itm = list.next();
				Parameters.setArrayItem(paramId, i++, itm.getFullname());
			} while (list.hasNext());
			
			if (save) {
				Parameters.writeListProperties();
			}
		}
//		Parameters.writeProperties();
	}
}
