package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;

import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.ComboLikeObject;


/**
 * TreeCombo Swing util
 *
 * @author mum
 *
 */
@SuppressWarnings("serial")
public class TreeCombo extends ComboLikeObject {// implements IGetSetObj {

	private static final TreeComboItem[] EMPTY_TREE = {};
	private static final TreeComboItem[] EMPTY_ITEM_LIST = {};
	
	private TreeComboItem selectedItem = null;
	private HashMap<Integer, TreeComboItem> itemMap = new HashMap<Integer, TreeComboItem>();
	private HashMap<String, TreeComboItem> nameMap = new HashMap<String, TreeComboItem>();
	private TreeComboItem[] items;
	private JPopupMenu menu;
	private HashMap<TreeComboItem, JMenu> menuMap = new HashMap<TreeComboItem, JMenu>(15);
	private HashMap<TreeComboItem, JMenuItem> menuItemMap = new HashMap<TreeComboItem, JMenuItem>(25);


	public TreeCombo() {
		this(EMPTY_ITEM_LIST);
	}
	
	public TreeCombo(TreeComboItem[] itms) {
		super();

		generatePopup(getPopup(), itms);
	}


	public TreeCombo(String txtFldName, TreeComboItem[] itms, JButton...btns) {
		super(txtFldName, btns);

		generatePopup(getPopup(), itms);
	}


	public TreeCombo(JPopupMenu popup, TreeComboItem[] itms, JButton...btns) {
		super(null, popup, btns);

		generatePopup(getPopup(), itms);
	}

	protected static TreeComboItem[] list2Array(List<? extends TreeComboItem> list) {
		if (list == null) {
			return null; // new TreeComboItem[0];
		}

		return list.toArray(new FileTreeComboItem[list.size()]);
	}


	public final void setTree(TreeComboItem[] itms) {
		setTree(itms, false);
	}


	public final void setTree(TreeComboItem[] itms, boolean setSize) {
		JPopupMenu popup = getPopup();
		popup.removeAll();

		generatePopup(popup, itms);
		
		if (setSize) {
			popup.setFont(getTextCompenent().getFont());
		}
	}


	private JMenu generatePopup(TreeComboItem itm, JMenu menu, TreeComboItem[] items) {

		menuMap.put(itm, menu);
		for (TreeComboItem item : items) {
			TreeComboItem[] children = item.getChildren();
			addItem(item);
			if (children == null) {
				menu.add(new ComboAction(item));
			} else {
				menu.add(generatePopup(item, new JMenu(item.toString()), children));
			}
		}

		return menu;
	}


	private JPopupMenu generatePopup(JPopupMenu menu, TreeComboItem[] itms) {

		items = itms;
		if (items == null) {
			items = EMPTY_TREE;
		}
		itemMap = new HashMap<Integer, TreeComboItem>();
		nameMap = new HashMap<String, TreeComboItem>();
		menu.add(new ComboAction(TreeComboItem.BLANK_ITEM));
		for (TreeComboItem item : items) {
			if (item != null) {
				TreeComboItem[] children = item.getChildren();
				addItem(item);
				if (children == null) {
					menu.add(new ComboAction(item));
				} else {
					menu.add(generatePopup(item, new JMenu(item.toString()), children));
				}
			}
		}

		super.setPopupHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT * Math.min(16, items.length + 1));

		this.menu = menu;
		return menu;
	}

	private void addItem(TreeComboItem item) {
		String displayString = item.getString();

		itemMap.put(item.key, item);
		if (displayString != null && displayString != null) {
			nameMap.put(displayString.toLowerCase(), item);
		}
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#highlightItem(javax.swing.JPopupMenu, boolean)
	 */
	@Override
	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {
		if (visible) {
			searchChildren(new ArrayList<TreeComboItem>(), items);
		}  else {
			unhighlightChildren(items);
		}
	}




	private boolean searchItem(ArrayList<TreeComboItem> parents, TreeComboItem item) {
		parents.add(item);

		boolean ret = searchChildren(parents, item.getChildren());

		parents.remove(item);

		return ret;

	}

	private boolean searchChildren(ArrayList<TreeComboItem> parents, TreeComboItem[] children) {

		for (int i = 0; i < children.length; i++) {
			TreeComboItem child = children[i];
			if (child != null) {
				JMenu m;
	
				if (child.equals(selectedItem)) {
					MenuElement[] me = new MenuElement[parents.size() * 2 + 2];
	
					me[0] = menu;
					for (int j = 0; j < parents.size(); j++) {
						m =  menuMap.get(parents.get(j));
						me[j*2+1] = m;
						me[j*2+2] = m.getPopupMenu();
					}
					me[me.length - 1] = menuItemMap.get(child);
	
					MenuSelectionManager.defaultManager().setSelectedPath(me);
	//				for (TreeComboItem p : parents) {
	//					m = menuMap.get(p);
	//					if (m != null) {
	//						m.setSelected(true);
	//						m.setPopupMenuVisible(true);
	//					}
	//				}
	//
	//				JMenuItem mi = menuItemMap.get(child);
	//				if (mi != null) {
	//					mi.setArmed(true);
	//				}
	
					return true;
				} else if (child.getChildren() != null && child.getChildren().length > 0) {
					if (searchItem(parents, child)) {
						return true;
					}
				}
			}
		}

		return false;
	}


	private void unhighlightChildren(TreeComboItem[] menuItems) {

		for (int i = 0; i < menuItems.length; i++) {
			TreeComboItem child = menuItems[i];
			if (child != null) {
				TreeComboItem[] children = child.getChildren();
				JMenu m;
	
				if (children != null && children.length > 0) {
					unhighlightChildren(children);
					m = menuMap.get(child);
					if (m != null) {
						m.setSelected(false);
						m.setPopupMenuVisible(false);
					}
				} else {
					JMenuItem mi = menuItemMap.get(child);
					if (mi != null && mi.isArmed()) {
						mi.setArmed(false);
					}
				}
			}
		}

	}

	/**
	 * @return the selectedItem
	 */
	public TreeComboItem getSelectedItem() {
		String t = super.getText();
		
		if (selectedItem != null && t != null && ! t.equalsIgnoreCase(selectedItem.getEnglish())) {
			TreeComboItem item = nameMap.get(t.toLowerCase());
			if (item != null) {
				selectedItem = item;
			}
		}
		return selectedItem;
	}


	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(TreeComboItem selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
			super.setTextInternal(selectedItem.getEditString());
		}
	}
	public void setOnlySelectedItem(TreeComboItem selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
		}
	}

	public void setSelectedKey(int key) {
		TreeComboItem item = itemMap.get(key);
		if (item == null) {
			item = TreeComboItem.BLANK_ITEM;
		}
		setSelectedItem(item);
	}


	public void setSelectedString(String s) {
		TreeComboItem item = TreeComboItem.BLANK_ITEM;
		
		if (s != null && s.length() > 0) {
			item = nameMap.get(s.toLowerCase());
			if (item == null) {
				setOnlySelectedItem(TreeComboItem.BLANK_ITEM);
				this.setTextInternal(s);
				return;
			}
		}
		setSelectedItem(item);
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#textFieldChanged()
	 */
	@Override
	protected void textFieldChanged() {
		
		String s = valueTxt.getText();
		if (s != null && s.length() > 0) {
			TreeComboItem item = nameMap.get(s.toLowerCase());
			if (item != null) {
				setSelectedItem(item);
				return;
			}
		}

		super.textFieldChanged();
	}

	public final TreeComboItem getFirstItem() {
		
		if (items == null || items.length == 0) {
			
		} else {
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					return items[i];
				}
			}
		} 
		
		return null;
	}
	
	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.swing.table.IGetSetObj#getComponent()
//	 */
//	@Override
//	public JComponent getComponent() {
//		return this;
//	}
//	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.swing.table.IGetSetObj#setObject(java.lang.Object)
//	 */
//	@Override
//	public void setObject(Object value) {
//		if (value == null) {
//			this.setSelectedItem(TreeComboItem.BLANK_ITEM);
//		} else if (value instanceof TreeComboItem) {
//			this.setSelectedItem((TreeComboItem) value);
//		} else if (value instanceof Integer) {
//			this.setSelectedKey((Integer) value);
//		} else {
//			this.setSelectedItem(TreeComboItem.BLANK_ITEM);
//		}
//	}
//	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.swing.table.IGetSetObj#getObject()
//	 */
//	@Override
//	public Object getObject() {
//		return this.getSelectedItem();
//	}


	private class ComboAction extends JMenuItem implements ActionListener {
		private final TreeComboItem itm;

		public ComboAction(TreeComboItem itm) {
			super(itm.getString());
			this.itm = itm;
			menuItemMap.put(itm, this);
			super.addActionListener(this);
		}


		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedItem(itm);
		}
	}

}
