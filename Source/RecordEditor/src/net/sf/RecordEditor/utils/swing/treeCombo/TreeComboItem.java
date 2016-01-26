package net.sf.RecordEditor.utils.swing.treeCombo;

import java.util.List;

import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;

public class TreeComboItem extends ComboStdOption<Integer> {

	public static final TreeComboItem BLANK_ITEM = new TreeComboItem(Integer.MIN_VALUE+1, "", "");

	private TreeComboItem parentItem = null;
	private final TreeComboItem[] children;
	private boolean showParent = false;
	private boolean required   = false;


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

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	public final int getLastKey() {
		if (children == null && children.length > 0) {
			return children[children.length - 1].getLastKey();
		}

		return key;
	}

	public String getEditString() {
		return this.toString();
	}
	
	public static TreeComboItem[] toTreeItemArray(List<String> items) {
		return toTreeItemArray(items.toArray(new String[items.size()]));
	}
	
	public static TreeComboItem[] toTreeItemArray(String[] items) {
		TreeComboItem[] ret = new TreeComboItem[items.length];
		
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null || items[i].length() == 0) {
				ret[i] = BLANK_ITEM;
			} else {
				ret[i] = new TreeComboItem(i, items[i], items[i]);
			}
		}
		return ret;
	}
}
