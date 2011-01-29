/*
 * Created on 16/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;


import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * This class will display a DB result set in a combo in a JTable.
 * It is for internal use.
 *
 * use method BmComboBox.getTableCellRenderer()
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class BmKeyedComboBoxRender extends BmKeyedComboBox
								implements TableCellRenderer {

    private static final int COMBO_HEIGHT = SwingUtils.COMBO_TABLE_ROW_HEIGHT;


	/**
	 * This class will display a DB result set in a combo in a JTable.
	 * It is for internal use
	 *
	 * use method BmComboBox.getTableCellRenderer()
	 *
	 * @param model Combo model being used
	 * @param editable wether it is editable
	 */
	public BmKeyedComboBoxRender(final BmKeyedComboModel model, final boolean editable) {
		super(model, editable);

		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBounds(this.getY(), this.getX(), this.getWidth(), COMBO_HEIGHT);
	}


	/**
	 * @see javax.swing.table.TableCellRender#getTableCellRendererComponent
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (isSelected) {
        	super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
        	super.setForeground(table.getForeground());
        	super.setBackground(table.getBackground());
        }

        setSelectedItem(value);
        return this;
    }
}
