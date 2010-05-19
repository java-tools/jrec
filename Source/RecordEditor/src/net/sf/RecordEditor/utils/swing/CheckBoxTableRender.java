/*
 * @Author Bruce Martin
 * Created on 22/07/2005
 *
 * Purpose:
 * A checkbox Table Cell Rendor
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - added error trapping
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.common.Common;

/**
 * A checkbox Table Cell Rendor
 *
 * @author Bruce Martin
 *
 */
public class CheckBoxTableRender extends JCheckBox
implements TableCellRenderer {
    public static final int CELL_WIDTH  = 20;
    public static final int CELL_HEIGHT = 20;


    private JCheckBox checkBox = this;
    
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
	    boolean val = false;

	    if (Common.isEmpty(value)) {
	    	checkBox.setSelected(false);
	    } else {

	    	try {
	    		val = ((Boolean) value).booleanValue();
	    	} catch (Exception e) {
	    	}
	    	checkBox.setSelected(val);
	    }  

	    if (Common.isHighlightEmpty() && value == Common.MISSING_VALUE) {
	    	checkBox.setBackground(Common.EMPTY_COLOR);
	    } else if (value == Common.MISSING_REQUIRED_VALUE) {
	    	checkBox.setBackground(Common.MISSING_COLOR);
	    } else if (isSelected) {
	    	checkBox.setBackground(tbl.getSelectionBackground());
	    } else {
	    	checkBox.setBackground(tbl.getBackground());
	    }

	    checkBox.setSelected(val);
	    return checkBox;
	}
}
