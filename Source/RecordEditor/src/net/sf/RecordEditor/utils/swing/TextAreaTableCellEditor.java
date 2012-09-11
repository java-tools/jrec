package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

/**
 * Table Cell editor using a Text Area
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class TextAreaTableCellEditor
extends AbstractCellEditor implements TableCellEditor {

	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollArea = new JScrollPane(textArea);

	public TextAreaTableCellEditor() {
		scrollArea.setBorder(BorderFactory.createEmptyBorder());
	}


	@Override
	public Object getCellEditorValue() {
		return textArea.getText();
	}


	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(
			JTable table, Object value,
            boolean isSelected,
            int row, int column) {

		String s = "";
		if (value != null) {
			s = value.toString();
		}

		textArea.setText(s);
		return scrollArea;
	}
}
