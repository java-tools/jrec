package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;



@SuppressWarnings("serial")
public class TreeCombo extends ComboLikeObject {

	private TreeComboItem selectedItem;
	private HashMap<Integer, TreeComboItem> itemMap = new HashMap<Integer, TreeComboItem>();

	public TreeCombo(TreeComboItem[] itms) {
		super();

		generatePopup(getPopup(), itms);
	}


	private JMenu generatePopup(JMenu menu, TreeComboItem[] items) {

		for (TreeComboItem item : items) {
			TreeComboItem[] children = item.getChildren();
			itemMap.put(item.key, item);
			if (children == null) {
				menu.add(new ComboAction(item));
			} else {
				menu.add(generatePopup(new JMenu(item.toString()), children));
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
				menu.add(generatePopup(new JMenu(item.toString()), children));
			}
		}
		return menu;
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

	private class ComboAction extends AbstractAction {
		private final TreeComboItem itm;

		public ComboAction(TreeComboItem itm) {
			super(itm.getString());
			this.itm = itm;
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
