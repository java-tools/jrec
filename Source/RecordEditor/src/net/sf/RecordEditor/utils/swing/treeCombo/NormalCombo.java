package net.sf.RecordEditor.utils.swing.treeCombo;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.ComboLikeObject;


/**
 * TreeCombo Swing util
 *
 * @author mum
 *
 */
@SuppressWarnings("serial")
public class NormalCombo extends ComboLikeObject {// implements IGetSetObj {

//	private static final TreeComboItem[] EMPTY_TREE = {};
//	private static final TreeComboItem[] EMPTY_ITEM_LIST = {};
	
	private JPopupMenu menu;
	
	private String[] items;
	private JList<String> list;
	private ListSelectionListener listListner = new ListSelectionListener() {
		@Override public void valueChanged(ListSelectionEvent e) {		
			setText(list.getSelectedValue());
			menu.setVisible(false);
		}
	};


//	public NormalCombo() {
//		this(EMPTY_ITEM_LIST);
//	}
	
	public NormalCombo(String[] itms) {
		super();

		items = itms;
		generatePopup(getPopup(), itms);
	}



//
//	public final void setTree(TreeComboItem[] itms) {
//		setTree(itms, false);
//	}
//
//
//	public final void setTree(TreeComboItem[] itms, boolean setSize) {
//		JPopupMenu popup = getPopup();
//		popup.removeAll();
//
//		generatePopup(popup, itms);
//		
//		if (setSize) {
//			popup.setFont(getTextCompenent().getFont());
//		}
//	}




	private JPopupMenu generatePopup(JPopupMenu menu, String[] itms) {

		if (list != null) {
			list.removeListSelectionListener(listListner);
		}
		list = new JList<String>(itms);
		menu.add(list);


		super.setPopupHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT * Math.min(16, itms.length + 1));

		this.menu = menu;
		
		list.addListSelectionListener(listListner);
		return menu;
	}


	/**
	 * @see net.sf.RecordEditor.utils.swing.common.ComboLikeObject#highlightItem(javax.swing.JPopupMenu, boolean)
	 */
	@Override
	protected void highlightItem(JPopupMenu currentPopup, boolean visible) {
		if (visible && list != null) {
			String s = getText();
			int selectedIndex = list.getSelectedIndex();
			for (int i = 0; i < items.length; i++) {
				if (s.equalsIgnoreCase(items[i]) && i != selectedIndex) {
					list.removeListSelectionListener(listListner);
					list.setSelectedIndex(i);
					list.addListSelectionListener(listListner);
				}
			}
		}
//		if (visible) {
//			searchChildren(new ArrayList<TreeComboItem>(), items);
//		}  else {
//			unhighlightChildren(items);
//		}
	}




//	private boolean searchItem(ArrayList<TreeComboItem> parents, TreeComboItem item) {
//		parents.add(item);
//
//		boolean ret = searchChildren(parents, item.getChildren());
//
//		parents.remove(item);
//
//		return ret;
//
//	}
//
//	private boolean searchChildren(ArrayList<TreeComboItem> parents, TreeComboItem[] children) {
//
//		for (int i = 0; i < children.length; i++) {
//			TreeComboItem child = children[i];
//			if (child != null) {
//				JMenu m;
//	
//				if (child.equals(selectedItem)) {
//					MenuElement[] me = new MenuElement[parents.size() * 2 + 2];
//	
//					me[0] = menu;
//					for (int j = 0; j < parents.size(); j++) {
//						m =  menuMap.get(parents.get(j));
//						me[j*2+1] = m;
//						me[j*2+2] = m.getPopupMenu();
//					}
//					me[me.length - 1] = menuItemMap.get(child);
//	
//					MenuSelectionManager.defaultManager().setSelectedPath(me);
//	//				for (TreeComboItem p : parents) {
//	//					m = menuMap.get(p);
//	//					if (m != null) {
//	//						m.setSelected(true);
//	//						m.setPopupMenuVisible(true);
//	//					}
//	//				}
//	//
//	//				JMenuItem mi = menuItemMap.get(child);
//	//				if (mi != null) {
//	//					mi.setArmed(true);
//	//				}
//	
//					return true;
//				} else if (child.getChildren() != null && child.getChildren().length > 0) {
//					if (searchItem(parents, child)) {
//						return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}
//
//
//	private void unhighlightChildren(TreeComboItem[] menuItems) {
//
//		for (int i = 0; i < menuItems.length; i++) {
//			TreeComboItem child = menuItems[i];
//			if (child != null) {
//				TreeComboItem[] children = child.getChildren();
//				JMenu m;
//	
//				if (children != null && children.length > 0) {
//					unhighlightChildren(children);
//					m = menuMap.get(child);
//					if (m != null) {
//						m.setSelected(false);
//						m.setPopupMenuVisible(false);
//					}
//				} else {
//					JMenuItem mi = menuItemMap.get(child);
//					if (mi != null && mi.isArmed()) {
//						mi.setArmed(false);
//					}
//				}
//			}
//		}
//
//	}

//	/**
//	 * @return the selectedItem
//	 */
//	public TreeComboItem getSelectedItem() {
//		return selectedItem;
//	}
//
//
//	/**
//	 * @param selectedItem the selectedItem to set
//	 */
//	public void setSelectedItem(TreeComboItem selectedItem) {
//		super.hidePopup();
//		if (selectedItem != null) {
//			this.selectedItem = selectedItem;
//			super.setText(selectedItem.getEditString());
//		}
//	}
//
//	public void setSelectedKey(int key) {
//		TreeComboItem item = itemMap.get(key);
//		if (item == null) {
//			item = TreeComboItem.BLANK_ITEM;
//		}
//		setSelectedItem(item);
//	}
//
//	
//	public final TreeComboItem getFirstItem() {
//		
//		if (items == null || items.length == 0) {
//			
//		} else {
//			for (int i = 0; i < items.length; i++) {
//				if (items[i] != null) {
//					return items[i];
//				}
//			}
//		} 
//		
//		return null;
//	}
	
	
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

//
//	private class ComboAction extends JMenuItem implements ActionListener {
//		private final TreeComboItem itm;
//
//		public ComboAction(TreeComboItem itm) {
//			super(itm.getString());
//			this.itm = itm;
//			menuItemMap.put(itm, this);
//			super.addActionListener(this);
//		}
//
//
//		/* (non-Javadoc)
//		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//		 */
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			setSelectedItem(itm);
//		}
//	}
//
	
//	public static void main(String[] args) {
//		JFrame jf = new JFrame();
//		String[] itms = {
//				"Item 1", "Item 2", "Item 3", "Item 4",
//		};
//		
//		NormalCombo nc = new NormalCombo(itms);
//		nc.setPreferredSize(new Dimension(nc.getPreferredSize().width, 24));
//		nc.setText("item 3");
//		
//		jf.getContentPane().add(nc);
//		
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.pack();
//		jf.setVisible(true);
//		//jf.setPreferredSize(new Dimension(200, 50));
//	}
}
