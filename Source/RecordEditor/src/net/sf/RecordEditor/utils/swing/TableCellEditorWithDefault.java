package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class TableCellEditorWithDefault extends DefaultCellEditor {

	private Object defaultVal;
	
	public TableCellEditorWithDefault(Object defaultValue) {
		this(new JTextField(), defaultValue);
	}

	public TableCellEditorWithDefault(JTextField fld, Object defaultValue) {
		super(fld);
	 
		fld.setBorder(BorderFactory.createEmptyBorder());
		defaultVal = defaultValue;
	}


	/* (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		Object val = value;
		if (val == null || "".equals(value.toString())) {
			val = defaultVal;
		}
		return super.getTableCellEditorComponent(table, val, isSelected, row, column);
	}

	
	
}
