/*
 * @Author Bruce Martin
 * Created on 23/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 * Mono spaced text field table rendor
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class MonoSpacedRender extends JTextField implements TableCellRenderer  {

    /**
     * Mono spaced text field table rendor
     */
    public MonoSpacedRender() {
        super();
        this.setFont(SwingUtils.getMonoSpacedFont());
		this.setBorder(BorderFactory.createEmptyBorder());
    }


    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        String val = "";
        if (value != null) {
            val = value.toString();
        }
        setText(val);

        if (isSelected) {
            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            super.setForeground(table.getForeground());
            super.setBackground(table.getBackground());
        }


        return this;
    }
}
