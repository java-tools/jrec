package net.sf.RecordEditor.utils.swing.interfaces;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public interface ITableCellAdapter {

	public abstract TableCellRenderer getCellRenderer(int row, int column);

	public abstract TableCellEditor getCellEditor(int row, int column);

}