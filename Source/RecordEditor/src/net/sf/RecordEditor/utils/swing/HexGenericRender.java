/*
 * @Author Bruce Martin
 * Created on 22/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * table rendor for 3 line hex display
 *
 * @author Bruce Martin
 *
 */

public  class HexGenericRender implements TableCellRenderer  {
	
    private AbstractHexDisplay val;

    //JPanel pnl = new JPanel();

    /**
     * @param font fontname
     */
    public HexGenericRender(final String font, AbstractHexDisplay display) {
        super();


        val = display;

        //pnl.add(val);
    }

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
	 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(
		JTable tbl,
		Object value,
		boolean isSelected,
		boolean hasFocus,
		int row,
		int column) {
		JComponent control = val.getComponent();

	    try {
	        val.setHex((byte[]) value);
	    } catch (Exception e) {
        }

        if (isSelected) {
        	control.setForeground(tbl.getSelectionForeground());
        	control.setBackground(tbl.getSelectionBackground());
        } else {
        	control.setForeground(tbl.getForeground());
        	control.setBackground(tbl.getBackground());
        }

		return control;
	}
}
