package net.sf.RecordEditor.utils.swing.treeCombo;



import java.io.File;
import java.util.List;

import net.sf.RecordEditor.utils.swing.UpdatableTextValue;
import net.sf.RecordEditor.utils.swing.extraColumn.IHasExtraComponent;
import net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper;

@SuppressWarnings("serial")
public class TreeComboFileSelect extends AbstractTreeComboFileSelect implements UpdatableTextValue, IHasExtraComponent {



	private final boolean changeList;
	private int nextKey = Integer.MIN_VALUE;

//	private JButton openFileDialogBtn = null;
	
//	private final IExtraComponent fileComponent;
	public TreeComboFileSelect(boolean isOpen, boolean isDirectory, boolean canChangeList, IFileLists files) {
		this(isOpen, isDirectory, canChangeList, files==null?null:files.getFileComboList(), files==null?null:files.getDirectoryList());
	}

	public TreeComboFileSelect(boolean isOpen, boolean isDirectory, boolean canChangeList, List<FileTreeComboItem> itms, List<File> dirs) {
		super(  isOpen, isDirectory, 
				itms, 
				dirs,
				stdFileButton(isDirectory),
				null);
		this.changeList = canChangeList;
	}

	
	public TreeComboFileSelect(boolean isOpen, boolean isDirectory,
			List<FileTreeComboItem> itms,
			IFileChooserWrapper ch, List<File> directoryList) {
		super(isOpen, isDirectory, itms, ch, directoryList, stdFileButton(isDirectory), null);
		this.changeList = true;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.ComboLikeObject#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {

		List<FileTreeComboItem> fileList = getFileComboList(); 
		if (changeList && fileList != null && text != null && ! "".equals(text)) {
			try {
				int requiredCount = 0;
				int insertPos = fileList.size() - 1;
				for (int i = fileList.size() - 1; i >= 0; i--) {
//					System.out.println("+++> " + fileList.get(i).getFullname() + " " + t);
					if (fileList.get(i).isRequired()) {
						requiredCount += 1;
					} else if (fileList.get(i).getFullname().equals(text)) {
						if (i == 0) {
							super.setText(text);
							return;
						}
						fileList.remove(i);
						insertPos -= 1;
					} else {
						insertPos = i;
					}
				}
				fileList.add(Math.max(0, insertPos), new FileTreeComboItem(nextKey++, new File(text)));
				if (fileList.size() > LIST_SIZE
						&& fileList.size() > requiredCount + OPTIONAL_LIST_SIZE) {
					fileList.remove(fileList.size() - 1);
				}
				super.setFileList(fileList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		super.setText(text);
	}
	
//	public void setTextStd(String t) {
//		super.setText(t);
//
//	}

//	public final JButton getOpenFileDialogBtn() {
//		return getOpenFileDialogBtn("Choose File");
//	}
//
//	public final JButton getOpenFileDialogBtn(String buttonPrompt) {
//		if (openFileDialogBtn == null) {
//			int iconId = Common.ID_DIRECTORY_SEARCH_ICON;
//			if (chooseHelper.isDirectory) {
//				iconId = Common.ID_DIRECTORY_SEARCH_ICON;
//			}
//			openFileDialogBtn  = SwingUtils.newButton(
//	        		buttonPrompt,
//	        		Common.getRecordIcon(iconId));
//			openFileDialogBtn.addActionListener(chooseHelper);
//		}
//
//		return openFileDialogBtn;
//	}

}
