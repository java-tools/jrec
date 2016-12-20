package net.sf.RecordEditor.utils.swing.treeCombo;


import java.util.List;

import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;

public class TreeComboItemGeneric<KeyType, Item extends TreeComboItemGeneric<KeyType, Item> > extends ComboStdOption<KeyType> {

//	public static final TreeComboItem3 BLANK_ITEM = new TreeComboItem3(Integer.MIN_VALUE+1, "", "");

	Item parentItem = null;
	private final List<Item> children;
	private boolean showParent = false;
	private boolean required   = false;


	public TreeComboItemGeneric(KeyType idx, String str, String englishString) {
		this(idx, str, englishString, true);
	}

	public TreeComboItemGeneric(KeyType idx, String str, String englishString, boolean showParentName) {
		super(idx, str, englishString);
		children = null;
		showParent = showParentName;
	}


	public TreeComboItemGeneric(KeyType idx, String str, String englishString, List<Item> children) {
		super(idx, str, englishString);
		for (Item itm : children) {
			itm.parentItem = (Item) this;
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


	String getFullName() {
		if (showParent && parentItem != null) {
			return parentItem.getFullName() + "." + super.string;
		}
		return super.string;
	}
	/**
	 * @return the parentItem
	 */
	public Item getParentItem() {
		return parentItem;
	}


	/**
	 * @return the children
	 */
	public List<Item> getChildren() {
		return children;
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

	public final KeyType getLastKey() {
		if (children == null && children.size() > 0) {
			return children.get(children.size() - 1).getLastKey();
		}

		return key;
	}

	public String getEditString() {
		return this.toString();
	}
	
//	public TreeComboItem3<T>[] toTreeItemArray(List<T> items) {
//		return toTreeItemArray(items.toArray(new TreeComboItem3<T>[items.size()]));
//	}
//	
//	public TreeComboItem3<T>[] toTreeItemArray(T[] items) {
//		TreeComboItem3[] ret = new TreeComboItem3[items.length];
//		
//		for (int i = 0; i < items.length; i++) {
//			if (items[i] == null ) {
//				ret[i] = BLANK_ITEM;
//			} else {
//				ret[i] = new TreeComboItem3(, items[i], items[i]);
//			}
//		}
//		return ret;
//	}
}
