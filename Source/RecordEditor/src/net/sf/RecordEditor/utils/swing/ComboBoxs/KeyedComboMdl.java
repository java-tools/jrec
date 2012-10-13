package net.sf.RecordEditor.utils.swing.ComboBoxs;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ComboBoxModel;

import net.sf.RecordEditor.utils.swing.Combo.ComboKeyedOption;
import net.sf.RecordEditor.utils.swing.Combo.ListListner;


public class KeyedComboMdl<Key> extends ListListner implements  ComboBoxModel {

	public final ComboKeyedOption<Key> nullItem;

	public final HashMap<Key, ComboKeyedOption<Key>> items = new HashMap<Key, ComboKeyedOption<Key>>();

	private int currIdx = 0;

	public final ArrayList<ComboKeyedOption<Key>> itemList = new ArrayList<ComboKeyedOption<Key>>();

	public boolean trace = false;

	public KeyedComboMdl(ComboKeyedOption<Key> nullItem) {
		super();
		this.nullItem = nullItem;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public ComboKeyedOption<Key> getElementAt(int row) {
		//System.out.println("Get Element: : " + row + " " + getRowCount());
		if (row < 1 || row > itemList.size()) {
			return nullItem;
		}
		return itemList.get(row - 1);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return itemList.size() + 1;
	}



	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		return getElementAt(currIdx);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object item) {
		int lastIdx = currIdx;

		currIdx = 0;
//		if (trace) {
//			System.out.print(" --> " + item);
//			if (item != null) {
//				System.out.print(" " + item.getClass().getName());
//				if (item instanceof ComboObjOption) {
//					System.out.print(" " + ((ComboObjOption) item).index);
//				}
//			}
//			System.out.println();
//		}
		if (item instanceof  ComboKeyedOption) {
			@SuppressWarnings("unchecked")
			ComboKeyedOption<Key> parentItem = (ComboKeyedOption<Key>) item;
			for (int i = 0; i < itemList.size(); i++) {
				if (itemList.get(i).key != null && itemList.get(i).key.equals(parentItem.key)) {
					currIdx = i + 1;

					break;
				}
			}
		}

		if (lastIdx != currIdx) {
			tellOfUpdates(currIdx);
		}
	}


	/**
	 * @param arg0
	 * @return
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public ComboKeyedOption<Key> addElement(Key key, String value) {

		ComboKeyedOption<Key> o = items.get(key);
		if (o == null) {
			o = new ComboKeyedOption<Key>(key, value, null);
			items.put(key, o);
		} else {
			o.setString(value, null);
		}

		addElement(o);
		return o;
	}

	public void addElement(ComboKeyedOption<Key> o) {
		if (! items.containsKey(o.key)) {
			items.put(o.key, o);
		}
		itemList.add(o);
	}

	/**
	 *
	 * @see java.util.ArrayList#clear()
	 */
	public void removeAllElements() {
		itemList.clear();
	}
}
