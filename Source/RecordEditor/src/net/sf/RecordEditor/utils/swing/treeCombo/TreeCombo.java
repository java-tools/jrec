package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;



@SuppressWarnings("serial")
public class TreeCombo extends ComboLikeObject {

	private TreeComboItem selectedItem = null;
	private HashMap<Integer, TreeComboItem> itemMap = new HashMap<Integer, TreeComboItem>();
	private final TreeComboItem[] items;
	private JPopupMenu menu;
	private HashMap<TreeComboItem, JMenu> menuMap = new HashMap<TreeComboItem, JMenu>(15);
	private HashMap<TreeComboItem, JMenuItem> menuItemMap = new HashMap<TreeComboItem, JMenuItem>(25);

	public TreeCombo(TreeComboItem[] itms) {
		super();
		items = itms;

		generatePopup(getPopup(), itms);
	}


	private JMenu generatePopup(TreeComboItem itm, JMenu menu, TreeComboItem[] items) {

		menuMap.put(itm, menu);
		for (TreeComboItem item : items) {
			TreeComboItem[] children = item.getChildren();
			itemMap.put(item.key, item);
			if (children == null) {
				menu.add(new ComboAction(item));
			} else {
				menu.add(generatePopup(item, new JMenu(item.toString()), children));
			}
		}

		return menu;
	}


	private JPopupMenu generatePopup(JPopupMenu menu, TreeComboItem[] items) {

		menu.add(new ComboAction(TreeComboItem.BLANK_ITEM));
		for (TreeComboItem item : items) {
			TreeComboItem[] children = item.getChildren();
			itemMap.put(item.key, item);
			if (children == null) {
				menu.add(new ComboAction(item));
			} else {
				menu.add(generatePopup(item, new JMenu(item.toString()), children));
			}
		}

		this.menu = menu;
		return menu;
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.treeCombo.ComboLikeObject#highlightItem(javax.swing.JPopupMenu, boolean)
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

		return false;
	}


	private void unhighlightChildren(TreeComboItem[] menuItems) {

		for (int i = 0; i < menuItems.length; i++) {
			TreeComboItem child = menuItems[i];
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

	/**
	 * @return the selectedItem
	 */
	public TreeComboItem getSelectedItem() {
		return selectedItem;
	}


	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(TreeComboItem selectedItem) {
		super.visible = false;
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
			super.setText(selectedItem.toString());
		}
	}

	public void setSelectedKey(int key) {
		TreeComboItem item = itemMap.get(key);
		if (item == null) {
			item = TreeComboItem.BLANK_ITEM;
		}
		setSelectedItem(item);
	}


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
