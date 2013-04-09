/**
 *
 */
package net.sf.RecordEditor.edit.display.models;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
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
		return getFileView().getRowCount() + firstDataColumn;
	}

	@Override
	public String getColumnName(int col) {

		if (col >= firstDataColumn) {
			return "Row " + (col - firstDataColumn + 1);
		}
		return super.getColumnName(col);
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.models.BaseLineModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {

		if (col >= firstDataColumn) {
			return getFileView().getValueAt1(
					super.getCurrentLayout(),
					col - firstDataColumn,
					getRowLocal(row) + 2);
		}
		return super.getValueAt(row, col);
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex >= firstDataColumn
			&& (getFixedCurrentLayout() < layout.getRecordCount()
			    ||  (   (! getFileView().isBinaryFile())
			    	 && Common.OPTIONS.allowTextEditting.isSelected())
				    );

	}

	/**
	 * @see net.sf.RecordEditor.edit.display.models.BaseLineModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object val, int row, int col) {

//		System.out.println("set Value: " + super.getCurrentLayout() + " " + col + " " + val
//				+ " " + (col - FIRST_DATA_COLUMN)
//				+ " " + (getRowLocal(row) + 2));

		if (col >= firstDataColumn) {
			getFileView().setValueAt(super.getCurrentLayout(), val, col - firstDataColumn, getRowLocal(row) + 2);
		}
	}


}
