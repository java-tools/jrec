package net.sf.RecordEditor.utils.swing.treeCombo;

import java.io.File;
import java.util.List;

/**
 * interface for a classthat stores recent files and directories
 * @author Bruce Martin
 *
 */
public interface IFileLists {

	public abstract List<FileTreeComboItem> getFileComboList();

	public abstract List<File> getDirectoryList();

}