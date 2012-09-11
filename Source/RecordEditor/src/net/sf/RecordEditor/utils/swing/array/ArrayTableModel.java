package net.sf.RecordEditor.utils.swing.array;

import javax.swing.table.AbstractTableModel;

import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * create a Table Model from a CSV style field
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ArrayTableModel extends AbstractTableModel {

	private static String[] ARRAY_COLUMN_NAMES = LangConversion.convertColHeading(
			"ArrayRender",
			new String[] {"Index", "Data"});
	private static String[] MAP_COLUMN_NAMES = LangConversion.convertColHeading(
			"MapRender",
			new String[] {"Key", "Data"});


	ArrayInterface array;
	boolean changed = false;
	String[] columnNames = ARRAY_COLUMN_NAMES;
	int colAdj = 1;

    /**
	 * @param array
	 */
	public ArrayTableModel(ArrayInterface array) {
		this.array = array;

		if (array.getColumnCount() == 2) {
			columnNames = MAP_COLUMN_NAMES;
			colAdj = 0;
		}
	}



	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}



	/**
     * Wether the cell is editable
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) {
        return column > 0 || (array.getColumnCount() == 2);
    }

    /**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
	public void setValueAt(Object newValue, int row, int column) {
        changed |= newValue == null || ! newValue.equals(array.get(row, column - colAdj)) ;

        array.set(row, column - colAdj, newValue);
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 2;
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {

        return array.size();
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int column) {

   		if (column == 0 && array.getColumnCount() == 1) {
     		return Integer.valueOf(row);
    	}
    	return array.get(row, column - colAdj);
    }

}
