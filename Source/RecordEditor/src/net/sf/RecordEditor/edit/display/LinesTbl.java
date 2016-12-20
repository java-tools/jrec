package net.sf.RecordEditor.edit.display;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.utils.swing.interfaces.ITableCellAdapter;

@SuppressWarnings("serial")
public class LinesTbl extends JTable {
 
	private final ITableCellAdapter tableCellAdapter;

	/**
	 * A JTable that selects the appropriate CellRender / CellEditor
	 * @param parent parent Display
	 * @param dm Table model
	 * @param firstDataColumn first column holding data
	 */
	public LinesTbl(TableModel dm, ITableCellAdapter tableCellAdapter) {
		super(dm);
		
		this.tableCellAdapter = tableCellAdapter;
	}
	
	  
    /* (non-Javadoc)
	 * @see javax.swing.JTable#getCellRenderer(int, int)
	 */
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		TableCellRenderer r;
		if (tableCellAdapter == null || (r = tableCellAdapter.getCellRenderer(row, column)) == null) {
			r = super.getCellRenderer(row, column);
		} 
		return r;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getCellEditor(int, int)
	 */
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		TableCellEditor e;
		if (tableCellAdapter == null || (e = tableCellAdapter.getCellEditor(row, column)) == null) {
			e = super.getCellEditor(row, column);
		}
		return e;
	}
}

