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
public class ComboBoxRenderAdapter
						 implements TableCellRenderer {

    private static final int COMBO_HEIGHT = SwingUtils.COMBO_TABLE_ROW_HEIGHT;
    private JComboBox combo;

    
	/**
	 * This class will display a combo box in a JTable
	 *
	 * @param comboBox combo model to use
	 */
	public ComboBoxRenderAdapter(final JComboBox comboBox) {
		combo = comboBox;
		
		combo.setBorder(BorderFactory.createEmptyBorder());
		combo.setBounds(combo.getY(), combo.getX(), combo.getWidth(), COMBO_HEIGHT);
	}
	
	/**
	 * This class will display a combo box in a JTable
	 *
	 * @param comboBox combo model to use
	 */
	public ComboBoxRenderAdapter(final ComboBoxModel mdl) {
		combo = new JComboBox(mdl);
		
		combo.setBorder(BorderFactory.createEmptyBorder());
		combo.setBounds(combo.getY(), combo.getX(), combo.getWidth(), COMBO_HEIGHT);
	}


	/**
	 * @see javax.swing.table.TableCellRender#getTableCellRendererComponent
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (isSelected) {
        	combo.setForeground(table.getSelectionForeground());
            combo.setBackground(table.getSelectionBackground());
        } else {
        	combo.setForeground(table.getForeground());
        	combo.setBackground(table.getBackground());
        }

        combo.setSelectedItem(value);
        return combo;
    }
}
