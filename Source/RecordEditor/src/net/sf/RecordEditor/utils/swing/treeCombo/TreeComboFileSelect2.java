package net.sf.RecordEditor.utils.swing.treeCombo;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;

import com.sun.xml.internal.ws.resources.UtilMessages;

import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.FileChooserHelper;
import net.sf.RecordEditor.utils.swing.UpdatableTextValue;

@SuppressWarnings("serial")
public class TreeComboFileSelect2 extends TreeCombo2 implements UpdatableTextValue {

	private final int LIST_SIZE = 15;
	private final int OPTIONAL_LIST_SIZE = 10;

	//private final JButton fileListBtn;
	private final FileChooserHelper chooseHelper;
	private final boolean changeList;

	private ArrayList<FileTreeComboItem> fileList;

	private int maxListSize;

	private int nextKey = Integer.MIN_VALUE;

	public TreeComboFileSelect2(boolean isOpen, boolean isDirectory, boolean canChangeList, List<FileTreeComboItem> itms) {
		this(isOpen, isDirectory, itms, null, canChangeList, stdFileButton(isDirectory), (JButton[]) null);
	}

	public TreeComboFileSelect2(boolean isOpen, boolean isDirectory, boolean canChangeList, List<FileTreeComboItem> itms, JButton... btns) {
		this(isOpen, isDirectory, itms, null, canChangeList, stdFileButton(isDirectory), btns);
	}


	public TreeComboFileSelect2(
			boolean isOpen, boolean isDirectory, List<FileTreeComboItem> itms,
			UpdatableTextValue txtUpdate,
			boolean canChangeList,
			JButton... btns) {
		this(isOpen, isDirectory, itms, txtUpdate, canChangeList, stdFileButton(isDirectory), btns);
	}

	public static JButton stdFileButton(boolean isDirectory) {
		JButton btn;
		if (isDirectory) {
			btn = new JButton(Common.getRecordIcon(Common.ID_DIRECTORY_SEARCH_ICON));
			btn.setToolTipText(ReMessages.SELECT_DIRECTORY_DIALOG.get());
			return btn;
		}
		btn = new JButton(Common.getRecordIcon(Common.ID_FILE_SEARCH_ICON));
		btn.setToolTipText(ReMessages.SELECT_FILE_DIALOG.get());
		return btn;
	}


	private TreeComboFileSelect2(
			boolean isOpen, boolean isDirectory, List<FileTreeComboItem> itms,
			UpdatableTextValue txtUpdate,
			boolean canChangeList,
			JButton fileSelBtn, JButton... btns) {
		super(itms.toArray(new FileTreeComboItem[itms.size()]), addToArray(fileSelBtn, btns));

		if (txtUpdate == null) {
			txtUpdate = this;
		}
		chooseHelper = new FileChooserHelper(txtUpdate, isOpen, isDirectory);
		changeList = canChangeList;

		fileSelBtn.addActionListener(chooseHelper);

		maxListSize = Math.max(LIST_SIZE, itms.size() + 1);

		fileList = new ArrayList<FileTreeComboItem>(maxListSize);
		fileList.addAll(itms);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.ComboLikeObject#setText(java.lang.String)
	 */
	@Override
	public void setText(String t) {

		if (changeList) {
			try {
				int requiredCount = 0;
				int insertPos = fileList.size() - 1;
				for (int i = fileList.size() - 1; i >= 0; i--) {
//					System.out.println("+++> " + fileList.get(i).getFullname() + " " + t);
					if (fileList.get(i).isRequired()) {
						requiredCount += 1;
					} else if (fileList.get(i).getFullname().equals(t)) {
						if (i == 0) {
							super.setText(t);
							return;
						}
						fileList.remove(i);
						insertPos -= 1;
					} else {
						insertPos = i;
					}
				}
				fileList.add(Math.max(0, insertPos), new FileTreeComboItem(nextKey++, new File(t)));
				if (fileList.size() > LIST_SIZE
						&& fileList.size() > requiredCount + OPTIONAL_LIST_SIZE) {
					fileList.remove(fileList.size() - 1);
				}
				super.setTree(fileList.toArray(new TreeComboItem[fileList.size()]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.setText(t);

//		fireFileChangeListner(null);
	}


	/**
	 * @return
	 * @see java.util.List#iterator()
	 */
	public ListIterator<FileTreeComboItem> fileListIterator() {
		return fileList.listIterator();
	}

}
