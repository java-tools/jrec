/*
 * Created on 8/09/2004
 *
 */
package net.sf.RecordEditor.layoutEd.Record;

import javax.swing.JFrame;

import net.sf.RecordEditor.utils.jdbc.DBtableModel;



/**
 * This class implements a JTable to display a list of records. The user is then
 * able to select a specific record to be displayed
 *
 * @author Bruce Martin
 * @version 0.51
 *
 */
@SuppressWarnings("serial")
public class RecordListMdl extends DBtableModel<RecordRec> {

    /**
     * This class implements a JTable to display a list of records. The user is then
     * able to select a specific record to be displayed
     *
     * @param jframe parent frame
     * @param db Database interface
     */
	public RecordListMdl(final JFrame jframe, final RecordDB db) {
		super(jframe, db);
	}


	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 2;
	}


	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(final int col) {
		if ((col == 0) || (col == 1)) {
			return super.getColumnName(col + 1);
		}
		return null;

	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
/*	public int getRowCount() {
		return super.getRowCount();
	}*/
	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int row, final int col) {
		if ((col == 0) || (col == 1)) {
			return super.getValueAt(row, col + 1);
		}
		return null;
	}



}
