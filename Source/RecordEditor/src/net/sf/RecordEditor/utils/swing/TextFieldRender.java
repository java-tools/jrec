package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;


@SuppressWarnings("serial")
public class TextFieldRender extends JTextField implements TableCellRenderer {

	private Border columnBorder;


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column)  {
//		if (isSelected) {
//			setForeground(table.getSelectionForeground());
//			setBackground(table.getSelectionBackground());
//		} else {
//			setForeground(table.getForeground());
//			setBackground(table.getBackground());
//		}
		SwingUtils.setTableCellColors(this, table, row, isSelected);
		setFont(table.getFont());

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			if (table.isCellEditable(row, column)) {
				setForeground(UIManager.getColor("Table.focusCellForeground"));
				setBackground(UIManager.getColor("Table.focusCellBackground"));
			}
		} else {
			if (columnBorder != null) {
				setBorder(columnBorder);
//			} else {
//				setBorder(noFocusBorder);
			}
		}
		setText((value == null) ? "" : value.toString());
		return this;
	}



	/**
	 * @return the columnBorder
	 */
	public final Border getColumnBorder() {
		return columnBorder;
	}


	/**
	 * @param columnBorder the columnBorder to set
	 */
	public final TextFieldRender setColumnBorder(Border columnBorder) {
		this.columnBorder = columnBorder;
		return this;
	}

}
