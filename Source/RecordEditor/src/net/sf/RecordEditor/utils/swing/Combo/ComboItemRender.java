/*
 * @Author Bruce Martin
 * Created on 23/01/2007 added version 0.60
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing.Combo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * This class will display a combo box in a Table Cell
 *
 * use method BmComboBox.getTableCellRenderer()
 *
 * @author Bruce Martin
 *
 */
public class ComboItemRender 
						 implements TableCellRenderer {

	private static final int COMBO_HEIGHT = SwingUtils.COMBO_TABLE_ROW_HEIGHT;

    private JComboBox combo = new JComboBox();
    private DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    private boolean useCombo;
     
	/**
	 * This class will display a combo box in a JTable
	 *
	 * @param mdl combo model to use
	 */
	private ComboItemRender() {
		super();
		//System.out.println("Create Combo Item Render ...");
		combo.setBorder(BorderFactory.createEmptyBorder());
		combo.setBounds(combo.getY(), combo.getX(), combo.getWidth(), COMBO_HEIGHT);
		combo.setOpaque(true);
	}


	public ComboItemRender(ComboBoxModel model) {
		this();
		combo.setModel(model);
	}
	
	/**
	 * @see javax.swing.table.TableCellRender#getTableCellRendererComponent
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
		
		//useCombo = (value instanceof ComboItemInterface) ;
		useCombo = value != null;
		if (useCombo) {
			Color foreground, background;
			
			//System.out.print("## Combo: Setting :" + value + ": " + (value ==Common.NULL_OBJECT)
			//		+ " " + combo.getSelectedIndex() + " -> ");
			
			if (Common.isEmpty(value)) {
				combo.setSelectedIndex(0);
			} else {
				combo.setSelectedItem(value);
			}
			//System.out.println(combo.getSelectedIndex());
			
	       if (isSelected) {
	            foreground = table.getSelectionForeground();
	            background = table.getSelectionBackground();
	        } else {
	        	foreground = table.getForeground();

			    if ( row % 2 == 0 ) {
			    	background = UIManager.getColor("Table.alternateRowColor");
			    } else { 
			    	background = table.getBackground();
			    }

	        }

			if (Common.OPTIONS.highlightEmpty.isSelected() && value == Common.MISSING_VALUE) {
				background = Common.EMPTY_COLOR;
			} else if (value == Common.MISSING_REQUIRED_VALUE) {
				background = Common.MISSING_COLOR;
			}
	 
			combo.setForeground(foreground);
			combo.setBackground(background);
	      
	        return combo;
		} 
		

		return render.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
	
	public Object getValue() {
		if (useCombo) {
			return combo.getSelectedItem();
		}
		return render.toString();
	}
}
