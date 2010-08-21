/**
 * 
 */
package net.sf.RecordEditor.edit.display.models;

import net.sf.RecordEditor.edit.file.FileView;

/**
 * @author Bruce Martin
 *
 */
public class Line2ColModel extends BaseLineModel {


	/**
	 * @param file
	 */
	public Line2ColModel(FileView file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.models.BaseLineModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return getFileView().getRowCount() + FIRST_DATA_COLUMN;
	}

	@Override
	public String getColumnName(int col) {
		
		if (col >= FIRST_DATA_COLUMN) {
			return "Row " + (col - FIRST_DATA_COLUMN + 1);
		} 		 
		return super.getColumnName(col);
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.models.BaseLineModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		
		if (col >= FIRST_DATA_COLUMN) {
			return getFileView().getValueAt1(
					super.getCurrentLayout(), 
					col - FIRST_DATA_COLUMN, 
					getRowLocal(row) + 2);
		} 		 
		return super.getValueAt(row, col);
	}
	
	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex >= FIRST_DATA_COLUMN;
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.models.BaseLineModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object val, int row, int col) {
		
//		System.out.println("set Value: " + super.getCurrentLayout() + " " + col + " " + val
//				+ " " + (col - FIRST_DATA_COLUMN) 
//				+ " " + (getRowLocal(row) + 2));
		
		if (col >= FIRST_DATA_COLUMN) {
			getFileView().setValueAt(super.getCurrentLayout(), val, col - FIRST_DATA_COLUMN, getRowLocal(row) + 2);
		} 		 
	}


}
