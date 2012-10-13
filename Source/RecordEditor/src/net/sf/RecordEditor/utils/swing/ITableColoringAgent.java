package net.sf.RecordEditor.utils.swing;

import java.awt.Component;
import javax.swing.JTable;

public interface ITableColoringAgent {

	public void setTableCellColors(Component component, JTable table, int row, int col, boolean isSelected);
}
