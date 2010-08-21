/*
 * @Author Bruce Martin
 * Created on 15/04/2007 for version 0.62
 *
 * Purpose:
 * Edit a CsvArray in a Table
 */
package net.sf.RecordEditor.utils.swing.Combo;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


/**
 * Edit a CsvArray in a Table
 *
 * @author Bruce Martin
 *
 */
public class ComboItemEditor extends AbstractCellEditor
							  implements TableCellEditor {
	
	
    private ComboItemRender comboItemRendor;
 
    public ComboItemEditor(ComboBoxModel model) {
    	comboItemRendor = new ComboItemRender(model);
    }
    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) { 

        return comboItemRendor.getTableCellRendererComponent(table, value, false, true, row, column);
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
    	//System.out.println("Get Value " +  comboItemRendor.getValue());
        return comboItemRendor.getValue();
    }



//    /**
//     * @see javax.swing.CellEditor#cancelCellEditing()
//     */
//    public void cancelCellEditing() {
//
//       // arrayRendor.updateTable();
//        super.cancelCellEditing();
//    }
//
//    /**
//     * @see javax.swing.CellEditor#stopCellEditing()
//     */
//    public boolean stopCellEditing() {
//        //System.out.println("Stopping Editing ... ");
//        //arrayRendor.updateTable();
//        return super.stopCellEditing();
//    }
}
