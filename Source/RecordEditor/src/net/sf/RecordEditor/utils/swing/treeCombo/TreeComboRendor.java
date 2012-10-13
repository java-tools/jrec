package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class TreeComboRendor extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor {

	private final TreeCombo combo;

	public TreeComboRendor(TreeComboItem[] itms) {
		combo = new TreeCombo(itms);
		combo.setBorder(null);
	}

	@Override
	public Object getCellEditorValue() {
		return combo.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		setVal(value);

		return combo;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		setVal(value);
		SwingUtils.setTableCellColors(combo, table, row, isSelected);

		return combo;
	}

	private void setVal(Object value) {

		//System.out.println("!!! " + value + " " + (value instanceof TreeComboItem) + " " + (value instanceof Integer));
		if (value == null) {
			combo.setSelectedItem(TreeComboItem.BLANK_ITEM);
		} else if (value instanceof TreeComboItem) {
			combo.setSelectedItem((TreeComboItem) value);
		} else if (value instanceof Integer) {
			combo.setSelectedKey((Integer) value);
		} else {
			combo.setSelectedItem(TreeComboItem.BLANK_ITEM);
		}
	}
}
