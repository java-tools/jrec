/**
 *
 */
package net.sf.RecordEditor.utils.swing.ComboBoxs;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;

/**
 * @author mum
 *
 */
@SuppressWarnings("serial")
public class EnglishComboEditorAdaptor extends AbstractCellEditor
implements 	TableCellEditor, TableCellRenderer {

	EnglishCombo<? extends Object> combo;
	public EnglishComboEditorAdaptor(EnglishCombo<? extends Object> combo) {
		this.combo = combo;

	}
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		Object val = combo.getSelectedItem();
		if (val != null && val instanceof ComboStdOption) {
			return ((ComboStdOption) val).key;
		}
		return val;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		SwingUtils.setTableCellColors(combo, table, row, isSelected);

		SwingUtils.setCombo(combo, value);

        return combo;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		return getTableCellRendererComponent(table, value, isSelected, true, row, column);
	}

}
