package net.sf.RecordEditor.utils.swing.treeCombo;

import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;

@SuppressWarnings("unchecked")
public class TreeComboItem extends ComboStdOption<Integer> {

	public static final TreeComboItem BLANK_ITEM = new TreeComboItem(Integer.MIN_VALUE+1, "", "");

	private TreeComboItem parentItem = null;
	private final TreeComboItem[] children;
	private boolean showParent = false;


	public TreeComboItem(Integer idx, String str, String englishString) {
		this(idx, str, englishString, true);
	}

	public TreeComboItem(Integer idx, String str, String englishString, boolean showParentName) {
		super(idx, str, englishString);
		children = null;
		showParent = showParentName;
	}


	public TreeComboItem(Integer idx, String str, String englishString, TreeComboItem[] children) {
		super(idx, str, englishString);
		for (TreeComboItem itm : children) {
			itm.setParentItm(this);
		}

		this.children = children;
	}


	/*
	 * @see net.sf.RecordEditor.utils.swing.Combo.ComboStdOption#toString()
	 */
	@Override
	public String toString() {
		return getFullName();
	}


	private String getFullName() {
		if (parentItem == null) {
			return super.string;
		} else if (showParent) {
			return parentItem.getFullName() + "." + super.string;
		}
		return super.string;
	}
	/**
	 * @return the parentItem
	 */
	public TreeComboItem getParentItem() {
		return parentItem;
	}


	/**
	 * @return the children
	 */
	public TreeComboItem[] getChildren() {
		return children;
	}


	/**
	 * @param parentItm the parentItem to set
	 */
	public void setParentItm(TreeComboItem parentItem) {
		this.parentItem = parentItem;
	}


}
