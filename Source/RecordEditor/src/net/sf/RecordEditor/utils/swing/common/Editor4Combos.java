package net.sf.RecordEditor.utils.swing.common;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;



@SuppressWarnings("serial")
public class Editor4Combos extends AbstractCellEditor implements TableCellEditor {

	private final TextRender combo;


	public Editor4Combos(TextRender combo) {
		super();
		this.combo = combo;
	}


	@Override
	public Object getCellEditorValue() {
		return combo.getText();
	}


	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		return combo.getTableCellRendererComponent(table, value, isSelected, true, row, column);
	}
}
