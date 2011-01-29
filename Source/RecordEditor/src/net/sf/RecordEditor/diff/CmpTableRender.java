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
package net.sf.RecordEditor.diff;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * A checkbox Table Cell Rendor
 *
 * @author Bruce Martin
 *
 */
public class CmpTableRender
implements TableCellRenderer {

    public static Color ADDED_COLOR    = Color.yellow;
    public static Color DELETED_COLOR  = Color.cyan;
    public static Color MODIFIED_COLOR = Color.green;

    private JButton button = new JButton();
    private DefaultTableCellRenderer text    = new DefaultTableCellRenderer();
    
    private ArrayList<LineCompare> records = null;
    
    public void setList(ArrayList<LineCompare> list) {
    	records = list;
    	text.setBorder(BorderFactory.createEmptyBorder());
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

	    if (column == 00 && row % 2 == 0) {
	    	return button;
	    } else {
	   		int idx = row / 2;
	    	if (records == null || idx >= records.size()) {
	    		text.setBackground(Color.WHITE);
	    	} else {
	    		LineCompare cmp  = records.get(idx);
	    		
	    		if (cmp == null) {
	    			text.setBackground(ADDED_COLOR);
	    		} else if (cmp.code == LineCompare.DELETED) {
	    			text.setBackground(DELETED_COLOR);
	    		} else if (cmp.code == LineCompare.CHANGED) {
	    			text.setBackground(MODIFIED_COLOR);
	    		} else {
	    			text.setBackground(Color.WHITE);
	    		}
	    	}
	    }
	    return text.getTableCellRendererComponent(
	    				tbl,
	    				value,
	    				isSelected,
	    				hasFocus,
	    				row,
	    				column);
	}


}
