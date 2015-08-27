package net.sf.RecordEditor.utils.swing.treeCombo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.utils.params.Parameters;

/**
 * This class creates a File Selection field and uses the parameter
 * file to store / retrieve the last used files
 * 
 * @author Bruce
 *
 */
@SuppressWarnings("serial")
public class FileSelectCombo extends AbstractTreeComboFileSelect {

	private String paramKey;
	private final boolean isDirectory, allowNewFiles;
	private File lastFile = null;
	
	/**
	 * 
	 * This class creates a File Selection field and uses the paramter
	 * file to store / retrieve the last used files
	 * 
	 * @param paramKey file name key for extracting last used files
	 * @param numEntries number of entries to display in the list
	 * @param isOpen
	 * @param isDirectory wether the files are directories or not
	 */
	public FileSelectCombo(String paramKey, int numEntries, boolean isOpen, boolean isDirectory) {
		this(paramKey, numEntries, isOpen, isDirectory, false);
	}
	
	
	/**
	 * 
	 * This class creates a File Selection field and uses the paramter
	 * file to store / retrieve the last used files
	 * 
	 * @param paramKey file name key for extracting last used files
	 * @param numEntries number of entries to display in the list
	 * @param isOpen
	 * @param isDirectory wether the files are directories or not
	 * @param allowNewFiles allow new files
	 */
	public FileSelectCombo(String paramKey, int numEntries, boolean isOpen, boolean isDirectory, boolean allowNewFiles) {
		super(  isOpen, isDirectory, 
				CommonCode.getFileList(paramKey, numEntries, allowNewFiles, isDirectory), 
				CommonCode.getDirectoryList(paramKey, numEntries, allowNewFiles), 
				stdFileButton(isDirectory), null);
		this.paramKey = paramKey;
		this.isDirectory = isDirectory;
		this.allowNewFiles = allowNewFiles;
		init();
	}

//	public FileSelectComboStoreParam(String paramKey, int numEntries, boolean isOpen, boolean isDirectory,
//			boolean canChangeList, JButton... btns) {
//		super(isOpen, isDirectory, canChangeList, CommonCode.getFileList(paramKey, numEntries, true), btns);
//
//		this.paramKey = paramKey;
//		this.isDirectory = isDirectory;
//		init();
//	}
	
	private void init() {
		this.addTextChangeListner(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				addFileToList();
			}
		});
	}
		
//	
//	@Override
//	public void setText(String t) {
//		super.setTextStd(t);
//	}


	private void addFileToList() {

		String filename = this.getText();
		if (filename != null && filename.length() > 0) {
			File currFile = new File(filename);
	
			if ((allowNewFiles || currFile.exists())
			&& (currFile.isDirectory() == isDirectory)
			&& (! currFile.equals(lastFile))) {
				List<FileTreeComboItem> list = super.getFileComboList();
				FileTreeComboItem itm = null;
				int key = 0;
				
				if (list == null) {
					list = new ArrayList<FileTreeComboItem>();
				} else {
					for (int i = 0; i < list.size(); i++) {
						FileTreeComboItem fi = list.get(i);
						if (currFile.equals(fi.theFile)) {
							itm = fi;
							list.remove(i);
							break;
						}
						key = Math.max(key, fi.getKey());
					}
				}
				
				if (itm == null) {
					itm = new FileTreeComboItem(key + 1, currFile);
				}
				list.add(0, itm);
				super.setFileList(list);
	
	
				CommonCode.saveFileList(paramKey, this.fileListIterator(), false);
				CommonCode.saveDirectoryList(paramKey, this.directoryListIterator());
	//			Parameters.writeProperties();
	//			ListIterator<FileTreeComboItem> list = copybookDir.fileListIterator();
	//			int i = 0;
	//
	//			Parameters.setSavePropertyChanges(false);
	//			while (list.hasNext()) {
	//				FileTreeComboItem itm = list.next();
	//				Parameters.setArrayItem(Parameters.RECENT_COPYBOOK_DIRS, i++, itm.getFullname());
	//			}
	//			Parameters.setSavePropertyChanges(true);
	//			Parameters.writeProperties();
	
				lastFile = currFile;
			}
		}
	}
	

//
//	private List<TreeComboItem> bldComboTree(File treeParent, File f, int idx) {
//		if (f.exists() && f.isDirectory()) {
//			File[] files = f.listFiles();
//			Arrays.sort(files, new Comparator<File>() {
//				@Override public int compare(File f1, File f2) {
//					if (f1.isDirectory() == f2.isDirectory()) {
//						f2.getName().compareTo(f1.getName());
//					} else if (f1.isDirectory()){
//						return -11;
//					}
//					return 1;
//				}
//			});
//
//			List<TreeComboItem> cis = new ArrayList<TreeComboItem>(files.length);
//
//			for (File file : files) {
//				String s = f.getName();
//				if (s.endsWith("~") || s.toLowerCase().endsWith(".bak")) {
//				} else if (file.isDirectory()) {
//					int holdIdx = idx;
//					List<TreeComboItem> l = bldComboTree(treeParent, file, idx + 1);
//					if (l.size() > 0) {
//						cis.add(new TreeComboItem(holdIdx, s, s, l.toArray(new TreeComboItem[l.size()])));
//						idx = l.get(l.size() - 1).getLastKey() + 1;
//					}
//				} else {
//					cis.add(new FileTreeComboItem(idx++, treeParent, f, file));
//				}
//			}
//			return cis;
//		}
//
//		return new ArrayList<TreeComboItem>(0);
//	}
//

}
