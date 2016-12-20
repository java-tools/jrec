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
public class TreeComboGeneric<KeyType, Item extends TreeComboItemGeneric<KeyType, Item>> extends ComboLikeObject {// implements IGetSetObj {

	private final List<Item> EMPTY_TREE = new ArrayList<Item>();
//	private static final T[] EMPTY_ITEM_LIST = {};
	
	protected final Item BLANK_ITEM;
	private Item selectedItem = null;
	private HashMap<KeyType, Item> itemMap = new HashMap<KeyType, Item>();
	private HashMap<String, Item> nameMap = new HashMap<String, Item>();
	protected List<Item> items;
	private JPopupMenu menu;
	private HashMap<Item, JMenu> menuMap = new HashMap<Item, JMenu>(15);
	private HashMap<Item, JMenuItem> menuItemMap = new HashMap<Item, JMenuItem>(25);



	public TreeComboGeneric(Item blankItem, List<Item> itms) {
		super();

		this.BLANK_ITEM = blankItem;
		generatePopup(getPopup(), itms, true);
	}


	public TreeComboGeneric(String name, Item blankItem, boolean addBlank, List<Item> itms) {
		super(name, new JPopupMenu());

		this.BLANK_ITEM = blankItem;
		generatePopup(getPopup(), itms, addBlank);
	}


	public TreeComboGeneric(Item blankItem, String txtFldName, List<Item> itms, JButton...btns) {
		super(txtFldName, btns);

		this.BLANK_ITEM = blankItem;
		generatePopup(getPopup(), itms, true);
	}


	public TreeComboGeneric(Item blankItem, JPopupMenu popup, List<Item> itms, JButton...btns) {
		super(null, popup, btns);

		this.BLANK_ITEM = blankItem;
		generatePopup(getPopup(), itms, true);
	}

//	protected static T[] list2Array(List<? extends T> list) {
//		if (list == null) {
//			return null; // new T[0];
//		}
//
//		return list.toArray(new FileTreeComboItem[list.size()]);
//	}
//

	public final void setTree(List<Item> itms) {
		setTree(itms, false);
	}


	public final void setTree(List<Item> itms, boolean setSize) {
		JPopupMenu popup = getPopup();
		popup.removeAll();

		generatePopup(popup, itms, true);
		
		if (setSize) {
			popup.setFont(getTextCompenent().getFont());
		}
	}


	private JMenu generatePopup(Item itm, JMenu menu, List<Item> items) {

		menuMap.put(itm, menu);
		for (Item item : items) {
			List<Item> children = item.getChildren();
			addItem(item);
			if (children == null) {
				menu.add(new ComboAction(item));
			} else {
				menu.add(generatePopup(item, new JMenu(item.toString()), children));
			}
		}

		return menu;
	}


	private JPopupMenu generatePopup(JPopupMenu menu, List<Item> itms, boolean addBlank) {

		items = itms;
		if (items == null) {
			items = EMPTY_TREE;
		}
		itemMap = new HashMap<KeyType, Item>();
		nameMap = new HashMap<String, Item>();
		
		if (addBlank) {
			menu.add(new ComboAction(BLANK_ITEM));
		}
		for (Item item : items) {
			if (item != null) {
				List<Item> children = item.getChildren();
				addItem(item);
				if (children == null) {
					menu.add(new ComboAction(item));
				} else {
					menu.add(generatePopup(item, new JMenu(item.toString()), children));
				}
			}
		}

		super.setPopupHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT * Math.min(16, items.size() + 1));

		this.menu = menu;
		return menu;
	}

	private void addItem(Item item) {
		String displayString = item.getString();

		itemMap.put(updateKeyForMap(item.key), item);
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
			searchChildren(new ArrayList<Item>(), items);
		}  else {
			unhighlightChildren(items);
		}
	}




	private boolean searchAddItem(ArrayList<Item> parents, Item item) {
		parents.add(item);

		boolean ret = searchChildren(parents, item.getChildren());

		parents.remove(item);

		return ret;

	}

	private boolean searchChildren(ArrayList<Item> parents, List<Item> children) {

		for (int i = 0; i < children.size(); i++) {
			Item child = children.get(i);
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
	//				for (T p : parents) {
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
				} else if (child.getChildren() != null && child.getChildren().size() > 0) {
					if (searchAddItem(parents, child)) {
						return true;
					}
				}
			}
		}

		return false;
	}


	private void unhighlightChildren(List<Item> menuItems) {

		for (int i = 0; i < menuItems.size(); i++) {
			Item child = menuItems.get(i);
			if (child != null) {
				List<Item> children = child.getChildren();
				JMenu m;
	
				if (children != null && children.size() > 0) {
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
	public Item getSelectedItem() {
		String t = super.getText();
		
		if (selectedItem != null && t != null && ! t.equalsIgnoreCase(selectedItem.getEnglish())) {
			Item tmp = searchFor(t, items);
			if (tmp != null) {
				return tmp;
			}
		}
		return selectedItem;
	}
	
	private Item searchFor(String t, List<Item> itms) {

		if (itms != null) {
			for (Item itm : itms) {
				if (t.equalsIgnoreCase(itm.getEditString())) {
					return itm;
				}
				Item tmp = searchFor(t, itm.getChildren());
				if (tmp != null) {
					return tmp;
				}
			}
		}
		
		return null;
	}


	public void setSelectedIndex(int idx) {
		if (items == null || idx < 0 || idx >= items.size()) {
			setSelectedItem(null);
		} else {
			setSelectedItem(items.get(idx));
		}
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(Item selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
			super.setTextInternal(selectedItem.getEditString());
		}
	}
	
	public void setSelectedItemSilently(Item selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
			super.setTextSilentlyInternal(selectedItem.getEditString());
		}
	}


	public void setOnlySelectedItem(Item selectedItem) {
		super.hidePopup();
		if (selectedItem != null) {
			this.selectedItem = selectedItem;
		}
	}

//	//@TODO for String key !!!!
//	//@TODO for String key !!!!
//	//@TODO for String key !!!!
//	public void setSelectedKey(KeyType key) {
//		Item item = itemMap.get(updateKeyForMap(key));
//		if (item == null && key instanceof String) {
//			item = nameMap.get(key);
//		}
//		if (item == null) {
//			item = BLANK_ITEM;
//		}
//		setSelectedItem(item);
//	}


	public void setSelectedString(String s) {
		Item item = BLANK_ITEM;
		
		if (s != null && s.length() > 0) {
			item = nameMap.get(s);
			if (item == null) {
				setOnlySelectedItem(BLANK_ITEM);
				this.setTextInternal(s);
				return;
			}
		}
		setSelectedItem(item);
	}
	
	public final Item getFirstItem() {
		
		if (items == null || items.size() == 0) {
			
		} else {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i) != null) {
					return items.get(i);
				}
			}
		} 
		
		return null;
	}
	
	protected KeyType updateKeyForMap(KeyType k) {
		return k;
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
		private final Item itm;

		public ComboAction(Item itm) {
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
