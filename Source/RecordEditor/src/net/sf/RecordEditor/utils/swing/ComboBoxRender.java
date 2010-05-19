/*
 * @Author Bruce Martin
 * Created on 23/01/2007 added version 0.60
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * This class will display a combo box in a Table Cell
 *
 * use method BmComboBox.getTableCellRenderer()
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ComboBoxRender extends JComboBox
						 implements TableCellRenderer {

    private static final int COMBO_HEIGHT = 20;

    
	/**
	 * This class will display a combo box in a JTable
	 *
	 * @param mdl combo model to use
	 */
	public ComboBoxRender(final ComboBoxModel mdl) {
		super(mdl);

		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBounds(this.getY(), this.getX(), this.getWidth(), COMBO_HEIGHT);
	}


	public ComboBoxRender(Object[] items) {
		super(items);

		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBounds(this.getY(), this.getX(), this.getWidth(), COMBO_HEIGHT);
	}


	/**
	 * @see javax.swing.table.TableCellRender#getTableCellRendererComponent
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

		this.setSelectedItem(value);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        return this;
    }
}
