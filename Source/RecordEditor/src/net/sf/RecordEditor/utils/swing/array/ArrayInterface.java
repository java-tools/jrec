package net.sf.RecordEditor.utils.swing.array;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Details.AbstractLine;

public interface ArrayInterface {

	/**
	 * Get Text representation of Array
	 * @return Text representation
	 */
	public abstract String toString();

	/**
	 * @param index list index
	 * @param value new value to add
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public abstract void add(int index, Object value);

	/**
	 * @param value
	 * @return new value to add
	 * @see java.util.List#add(java.lang.Object)
	 */
	public abstract boolean add(Object value);

	/**
	 * @param index list index
	 * @return value for index
	 * @see java.util.List#get(int)
	 */
	public abstract Object get(int index, int column);

	/**
	 * @param index list index
	 * @return removed item
	 * @see java.util.List#remove(int)
	 */
	public abstract Object remove(int index);

	/**
	 * @param index list index
	 * @param value new value
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public abstract Object set(int index, int column, Object value);

	/**
	 * @return size
	 * @see java.util.List#size()
	 */
	public int size();

	/**
	 * Get the column count
	 * @return column Count
	 */
	public abstract int getColumnCount();


	/**
	 * Get the source Line
	 * @return source Line
	 */
	public AbstractLine getLine();

	/**
	 * Tell array Object to refresh its array copy;
	 */
	public void retrieveArray();

	/**
	 * flush changes back to source
	 */
	public void flush();

	public Object getReturn();


	/**
	 * Get Table Cell Render
	 * <b>Note:</b> you should always return a new Editor rather than a
	 * the same editor each time
	 *
	 * @param fld field being displayed
	 *
	 * @return Table Cell Render to be used to display the field
	 */
	public TableCellRenderer getTableCellRenderer();

	/**
	 * Get Table Cell Editor
	 *
	 * @param fld field being displayed
	 *
	 * @return Table Cell Editor to be used to edit the field
	 */
	public TableCellEditor getTableCellEditor();

	/**
	 * Get the normal height of a field
	 *
	 * @return field height
	 */
	public int getFieldHeight();
}