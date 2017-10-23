package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.UpdatableTextValue;
import net.sf.RecordEditor.utils.swing.extraColumn.ExtraSwingComponent;
import net.sf.RecordEditor.utils.swing.extraColumn.IExtraComponent;
import net.sf.RecordEditor.utils.swing.extraColumn.IHasExtraComponent;
import net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper;

@SuppressWarnings("serial")
public class AbstractTreeComboFileSelect extends TreeCombo implements UpdatableTextValue, IHasExtraComponent, IFileLists {

	protected final int LIST_SIZE = 15;
	protected final int OPTIONAL_LIST_SIZE = 10;
	private final JButton fileListBtn;
	private final FileChooserHelper chooseHelper;
	
	private List<FileTreeComboItem> fileList;
	private final List<File> dirList;
	private int maxListSize = LIST_SIZE; 

//	public AbstractTreeComboFileSelect(TreeComboItem[] itms) {
//		super(itms);
//	}
//
//	public AbstractTreeComboFileSelect(String txtFldName, TreeComboItem[] itms,
//			JButton... btns) {
//		super(txtFldName, itms, btns);
//	}
//
//	public AbstractTreeComboFileSelect(JPopupMenu popup, TreeComboItem[] itms,
//			JButton... btns) {
//		super(popup, itms, btns);
//	}
//	
//	protected AbstractTreeComboFileSelect(boolean isOpen, boolean isDirectory, List<FileTreeComboItem> itms) {
//		this(isOpen, isDirectory, itms, stdFileButton(isDirectory), null);
//	}

	protected AbstractTreeComboFileSelect(
			boolean isOpen, boolean isDirectory, 
			List<FileTreeComboItem> itms, List<File> directoryList,
			JButton fileSelBtn, JButton[] btns) { 
		super("FileNameTxtFld", list2Array(itms), btns /*addToArray(fileSelBtn, btns)*/);
		this.fileList = itms;
		this.dirList = directoryList;
		this.fileListBtn = fileSelBtn;

		if (directoryList == null) {
			chooseHelper = new FileChooserHelper(this, isOpen, isDirectory, new File[0]);
		} else {
			chooseHelper = new FileChooserHelper(this, isOpen, isDirectory, directoryList.toArray(new File[directoryList.size()]));
		}
	
		fileListBtn.addActionListener(chooseHelper);
		createFileList(itms);
	}
	
	
	protected AbstractTreeComboFileSelect(
			boolean isOpen, boolean isDirectory,
			List<FileTreeComboItem> itms,
			IFileChooserWrapper ch, List<File> directoryList,
			JButton fileSelBtn, JButton[] btns) {
		super("FileNameTxtFld", list2Array(itms), btns /*addToArray(fileSelBtn, btns)*/);
		fileListBtn = fileSelBtn;
		dirList = directoryList;
		chooseHelper = new FileChooserHelper(this, isOpen, isDirectory, ch);
	
		fileListBtn.addActionListener(chooseHelper);
		createFileList(itms);
	}

	private  void createFileList(List<FileTreeComboItem> itms) {

		List<FileTreeComboItem> fileList;
		if (itms == null) {
			fileList = new ArrayList<FileTreeComboItem>(maxListSize);
		} else {
			maxListSize = Math.max(LIST_SIZE, itms.size() + 1);
			fileList = new ArrayList<FileTreeComboItem>(maxListSize);
			fileList.addAll(itms);
		}
	}


	public static JButton stdFileButton(boolean isDirectory) {
		JButton btn;
		ImageIcon fileSearchIcon;
		String msg;
		if (isDirectory) {
			fileSearchIcon = Common.getRecordIcon(Common.ID_DIRECTORY_SEARCH_ICON);
			msg = ReMessages.SELECT_DIRECTORY_DIALOG.get();
		} else {
			fileSearchIcon = Common.getRecordIcon(Common.ID_FILE_SEARCH_ICON);
			msg = ReMessages.SELECT_FILE_DIALOG.get();
		}
	
		btn = new JButton(fileSearchIcon);
		btn.setToolTipText(msg);
		
		Dimension btnSize = btn.getPreferredSize();
		
		btn.setPreferredSize(new Dimension(fileSearchIcon.getIconWidth() + 4,
				(int) btnSize.getHeight() + 2));
		return btn;
	}


	public final void setFileSelectToolTip(String tip) {
		fileListBtn.setToolTipText(tip);
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public ListIterator<FileTreeComboItem> fileListIterator() {
		if (fileList == null) { return null; }
		return fileList.listIterator();
	}

	public ListIterator<File> directoryListIterator() {
		return dirList.listIterator();
	}

	public final List<File> getDirectoryList() {
		return dirList;
	}

//
//	@Override
//	public List<FileTreeComboItem> getFileComboList() {
//		// TODO Auto-generated method stub
//		return fileList;
//	}


	public final List<FileTreeComboItem> getFileComboList() {
		return fileList;
	}

	protected final void setFileList(List<FileTreeComboItem> fileList) {
		this.fileList = fileList;
		if (fileList.size() > 0) {
			updateDirectoryList(fileList.get(0).getFullname());
		}
		this.setTree(fileList.toArray(new TreeComboItem[fileList.size()]));
	}

	@Override
	public IExtraComponent getExtraComponentDetails() {
		return new ExtraSwingComponent(fileListBtn);
	}

	/**
	 * Special file chooser Focus listner.
	 * @param fcListner focus listner
	 * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	 */
	public final void addFcFocusListener(FocusListener fcListner) {
	
	   	super.getTextCompenent().addFocusListener(fcListner);
	   	chooseHelper.listner.add(fcListner);
	}

	public final void setEditable(boolean editable) {
		super.getTextCompenent().setEditable(editable);
		
		this.showListBtn.setEnabled(editable);
		fileListBtn.setEnabled(editable);
		
	}

	protected final FileChooserHelper getChooseHelper() {
		return chooseHelper;
	}

	public final void setExpandVars(boolean expandVars) {
		chooseHelper.setExpandVars(expandVars);
	}

	/**
	 * @param defaultDirectory
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.FileChooserHelper#setDefaultDirectory(java.lang.String)
	 */
	public final void setDefaultDirectory(String defaultDirectory) {
		chooseHelper.setDefaultDirectory(defaultDirectory);
	}

	protected final void updateDirectoryList(String filename) {

		if (dirList != null && filename != null && filename.length() > 0) {
			File f = new File(filename);
			if (! f.isDirectory()) {
				f = f.getParentFile();
				dirList.remove(f);
//				for (int i = dirList.size() - 1; i >= 0; i-- ) {
//					if (f.equals(dirList.get(i))) {
//						dirList.remove(i);
//					}
//				}
				
				dirList.add(0, f);
				while (dirList.size() > OPTIONAL_LIST_SIZE) {
					dirList.remove(dirList.size() - 1);
				}
			}
		}
	}
}