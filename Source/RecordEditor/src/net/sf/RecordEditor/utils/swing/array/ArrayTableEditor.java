/*
 * @Author Bruce Martin
 * Created on 15/04/2007 for version 0.62
 *
 * Purpose:
 * Edit a CsvArray in a Table
 */
package net.sf.RecordEditor.utils.swing.array;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import net.sf.RecordEditor.utils.screenManager.ReFrame;

/**
 * Edit a CsvArray in a Table
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ArrayTableEditor extends AbstractCellEditor
							  implements TableCellEditor {
	
	
    private ArrayRender arrayRendor = new ArrayRender();

 
    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) { 
        
    	ReFrame f = ReFrame.getActiveFrame();
        arrayRendor.setTableDetails(table, row, column, value, f.getDocumentName(), f.getDocument());

        return arrayRendor;
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        return arrayRendor.getValue();
    }
}
