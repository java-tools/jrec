/*
 * Created on 22/08/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

import javax.swing.JFrame;



/**
 * This class uses an Abstract DB to get records to be displayed via
 * a Swing JTable
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class DBtableModel<Record extends AbsRecord> extends AbsDBTableModel<Record> {

    private int emptyColumns = 0;

	/**
	 * Create DB table model from database SQL record
	 *
	 * @param db DB SQL access
	 */
	public DBtableModel(final AbsDB<Record> db) {
		super(db);
	}


	/**
	 * Create DB table model from database SQL record.
	 *
	 * @param jframe  Frame where any field error dialogs are displayed
	 * @param db      DB SQL access
	 */
	public DBtableModel(final JFrame jframe, final AbsDB<Record> db) {
		super(jframe, db);
	}


	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		checkLoad();

		//System.out.println( " !! " + lines.size());
		return lines.size();
	}


	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return dataBase.getColumnCount() + emptyColumns;
	}


	/**
	 * This Method gets a column heading name
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		String ret = null;

		col = adjustColumn(col);
		if (dataBase != null) {
			ret = dataBase.getColumnName(col);
		}
		return ret;
	}




	/**
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int col) {
	    col = adjustColumn(col);
		return dataBase.getColumnClass(super.getColumnClass(col), col);
	}


	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {

		checkLoad();

		col = adjustColumn(col);
		if ((row >= getRowCount())
		|| (col >= dataBase.getColumnCount())
		|| (col < 0)) {
			return null;
		}

		return (lines.get(row)).getField(col);
			/*Object o = ((AbsRecord) lines.get(row)).getField(col);
			if (o == null) {
				return null;
			} else {
				return o;
			}*/

	}


	/**
	 * @see net.sf.RecordEditor.utils.jdbc.AbsDBTableModel#setValueAt(Object, int, int)
	 */
	public void setValueAt(Object val, int row, int col) {

		checkLoad();

		col = adjustColumn(col);
		if (val == null) {
			dataBase.setMessage("DBtableModel: Tried to set a value to null", null);
		} else if (col >= 0 && (col < dataBase.getColumnCount())) {
			Record record = lines.get(row);
			
//			changed = changed || val.equals(record.getField(col));
			record.setField(frame, col, val);
			fireTableCellUpdated(row, col);
		}
	}


	/**
	 * Adjusts the supplied column. This method can be overriden
	 * to add / move columns about (ie add a selection button)
	 *
	 * @param column input column
	 * @return adjusted column
	 */
	protected int adjustColumn(int column) {
        return column - emptyColumns;
    }


	/**
	 * Set the number of empty columns at the start of the row.
	 * This is to allow for selection buttons at the start of each
	 * row
	 *
     * @param numEmptyColumns The number emptyColumns to set.
     */
    public void setEmptyColumns(int numEmptyColumns) {
        this.emptyColumns = numEmptyColumns;
    }
}
