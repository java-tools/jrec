package net.sf.RecordEditor.edit.display;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.swing.interfaces.ITableCellAdapter;

/**
 * Adapter to get cell editor
 * @author Bruce01
 *
 */
public class TblCellAdapter implements ITableCellAdapter  {

	private final int firstDataCol, minCol;
	
	
	private final BaseDisplay display;


	/**
	 * A JTable that selects the appropriate CellRender / CellEditor
	 * @param parent parent Display
	 * @param dm Table model
	 * @param firstDataColumn first column holding data
	 */
	public TblCellAdapter(BaseDisplay display, int firstDataColumn, int minCol) {
		this.firstDataCol = firstDataColumn;
		this.display = display;
		this.minCol = minCol;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.ITableCellEditorAdapter#getCellRenderer(int, int)
	 */
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		TableCellRenderer[] iCellRenders = display.cellRenders;
		
		JTable jTable = display.getJTable();
		if (jTable != null && jTable.getColumnModel() != null && column < jTable.getColumnModel().getColumnCount() ) {
			try {
				int recordIndex = getPreferedIdx(row);
				if (recordIndex >= 0) {
			        iCellRenders = display.getDisplayDetails(recordIndex).getCellRenders();
				}
			
				int col = jTable.getColumnModel().getColumn(column).getModelIndex();
				if (col >= minCol) {
					int adjCol = col - firstDataCol;
					if (isCellEditor(adjCol, row, iCellRenders)) {
						return iCellRenders[adjCol];
				    }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.ITableCellEditorAdapter#getCellEditor(int, int)
	 */
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		TableCellEditor[] iCellEditors = display.cellEditors;
		
		JTable jTable = display.getJTable();
		if (jTable != null && jTable.getColumnModel() != null) {
			try {
				int recordIndex = getPreferedIdx(row);
				if (recordIndex >= 0) {
					iCellEditors = display.getDisplayDetails(recordIndex).getCellEditors();
				}
	
				int col = jTable.getColumnModel().getColumn(column).getModelIndex();
				if (col >= minCol) {
					int adjCol = col - firstDataCol;
					if (isCellEditor(adjCol, column, iCellEditors)) {
						return iCellEditors[adjCol];
				    } 
				}
			} catch (Exception e) {
			}
		}

		return null;
	}
	
	private int getPreferedIdx(int row) {
		return (display.isPreferedIdx()) 
				? display.getRecordIdxForRow(row)
				: -1;
	}
	
	private boolean isCellEditor(int column, int row, Object[]  cellPainters) {
		return ! (	column < 0 
				|| cellPainters == null 
				|| column >= cellPainters.length || cellPainters[column] == null);
	}
}
