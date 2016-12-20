package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.swing.SwingUtils;

@SuppressWarnings("serial")
public class TreeComboRendor extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor {

	private final TreeCombo combo;
	private int clickCountToStart = 1;
	private final TreeComboItem[] comboItems;
	private final String[] strItems;

	public TreeComboRendor(TreeComboItem[] itms) {
		this(null, itms); 
	}

	public TreeComboRendor(String[] strItms) {
		this(strItms, TreeComboItem.toTreeItemArray(strItms)); 
	}
	private TreeComboRendor(String[] strItms, TreeComboItem[] itms) {
		comboItems = itms;
		strItems = strItms;
		combo = new TreeCombo(itms);
		combo.setBorder(null);
	}

	@Override
	public Object getCellEditorValue() {
		TreeComboItem selectedItem = combo.getSelectedItem();
		if (selectedItem == null || selectedItem.getString().length() == 0) {
			return combo.getText();
		}
		return selectedItem;
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

    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
        }
        return true;
    }


	/**
	 * @param clickCountToStart the clickCountToStart to set
	 */
	public void setClickCountToStart(int clickCountToStart) {
		this.clickCountToStart = clickCountToStart;
	}

	private void setVal(Object value) {

		if (strItems != null && value != null && value instanceof String) {
			String v = (String) value;
			if (v.length() == 0) {
				value = TreeComboItem.BLANK_ITEM;
			} else {
				for (int i = 0; i < strItems.length; i++) {
					if (v.equalsIgnoreCase(strItems[i])) {
						value = comboItems[i];
						break;
					}
				}
			}
		}
		if (value == null) {
			combo.setSelectedItem(TreeComboItem.BLANK_ITEM);
		} else if (value instanceof TreeComboItem) {
			combo.setSelectedItem((TreeComboItem) value);
		} else if (value instanceof Integer) {
			combo.setSelectedKey((Integer) value);
		} else if (value instanceof String) {
			combo.setSelectedString((String) value); 
			//combo.setOnlySelectedItem(TreeComboItem.BLANK_ITEM);
			//combo.setText((String) value);
		} else {
			combo.setSelectedItem(TreeComboItem.BLANK_ITEM);
		}
	}
}
