package net.sf.RecordEditor.utils.swing;

import java.util.Comparator;

import net.sf.JRecord.Common.AbsRow;

public interface AbstractRowList extends Comparator<AbsRow> {

	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public abstract Object getElementAt(int idx);

	/**
	 * @see javax.swing.DefaultComboBoxModel#getIndexOf(java.lang.Object)
	 */
	public abstract int getIndexOf(Object arg0);

	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	public abstract int getSize();

	/**
	 * Get the Display Value at a specified Index
	 *
	 * @param idx index of the required item
	 *
	 * @return value
	 */
	public abstract Object getFieldAt(int idx);

	/**
	 * Convert Key into its equivalent value
	 *
	 * @param key Key Value
	 *
	 * @return Equivalent value
	 */
	public abstract Object getFieldFromKey(Object key);

	/**
	 * @return Returns the sorted.
	 */
	public abstract boolean isSorted();

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public abstract int compare(AbsRow row1, AbsRow row2);

	/**
	 * Reloads the List from the DB
	 *
	 */
	public abstract void reload();

}