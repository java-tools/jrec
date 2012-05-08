package net.sf.RecordEditor.utils.swing.Combo;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ComboBoxModel;


public class KeyedComboMdl<Key> extends ListListner implements  ComboBoxModel<ComboObjOption<Key>> {

	public final ComboObjOption<Key> nullItem;

	public final HashMap<Key, ComboObjOption<Key>> items = new HashMap<Key, ComboObjOption<Key>>();

	private int currIdx = 0;

	public final ArrayList<ComboObjOption<Key>> itemList = new ArrayList<ComboObjOption<Key>>();

	public boolean trace = false;

	public KeyedComboMdl(ComboObjOption<Key> nullItem) {
		super();
		this.nullItem = nullItem;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public ComboObjOption<Key> getElementAt(int row) {
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
		if (item instanceof  ComboObjOption) {
			@SuppressWarnings("unchecked")
			ComboObjOption<Key> parentItem = (ComboObjOption<Key>) item;
			for (int i = 0; i < itemList.size(); i++) {
				if (itemList.get(i).index != null && itemList.get(i).index.equals(parentItem.index)) {
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
	public ComboObjOption<Key> addElement(Key key, String value) {

		ComboObjOption<Key> o = items.get(key);
		if (o == null) {
			o = new ComboObjOption<Key>(key, value);
			items.put(key, o);
		} else {
			o.setString(value);
		}

		addElement(o);
		return o;
	}

	public void addElement(ComboObjOption<Key> o) {
		if (! items.containsKey(o.index)) {
			items.put(o.index, o);
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
