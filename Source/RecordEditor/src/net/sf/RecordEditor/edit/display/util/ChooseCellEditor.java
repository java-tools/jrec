package net.sf.RecordEditor.edit.display.util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * Table cell editor that will fram a list supplied as an array
 * 
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ChooseCellEditor extends AbstractCellEditor implements
TableCellEditor {

    private JTextField stdField = new JTextField();
    private int lastRow = -1;
    
    private TableCellEditor[] cellEditors;
    private JTable tblDetails;

    /**
     * Cell editor that chooses different editors depending
     * on which row is being displayed
     */
    public ChooseCellEditor(JTable tbl, TableCellEditor[] tableCellEditors) {
        super();
        stdField.setBorder(BorderFactory.createEmptyBorder());
        
        cellEditors = tableCellEditors;
        tblDetails  = tbl;
    }


    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        int row = lastRow;
        if (row < 0) {
            row = tblDetails.getSelectedRow();
        }

        if (row >= 0 && cellEditors[row] != null) {
            //System.out.println("~~ 1 ~~ " + cellEditors[row].getCellEditorValue());
            return cellEditors[row].getCellEditorValue();
        }
        //System.out.println("~~ 2 ~~ > " + row + " < " + stdField.getText());
        return stdField.getText();
    }


    /**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     */
    public void addCellEditorListener(CellEditorListener l) {
        int row;

        for (row = 0; row < cellEditors.length; row++) {
            if (cellEditors[row] != null) {
                cellEditors[row].addCellEditorListener(l);
            }
        }

        super.addCellEditorListener(l);
    }


    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     */
    public void removeCellEditorListener(CellEditorListener l) {
        int row;

        for (row = 0; row < cellEditors.length; row++) {
            if (cellEditors[row] != null) {
                cellEditors[row].removeCellEditorListener(l);
            }
        }
        //stdEditor.removeCellEditorListener(l);
        super.removeCellEditorListener(l);
    }


    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     */
    public void cancelCellEditing() {
        int row;

        row = tblDetails.getEditingRow();
        if (row > 0 && cellEditors[row] != null) {
        	cellEditors[row].cancelCellEditing();
        }
       // for (row = 0; row < cellEditors.length; row++) {
       //     if (cellEditors[row] != null) {
       //         cellEditors[row].cancelCellEditing();
       //     }
       // }
        //stdEditor.cancelCellEditing();
        super.cancelCellEditing();
    }



    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     */
//    public boolean isCellEditable(EventObject anEvent) {
//        if (anEvent instanceof MouseEvent) { 
//    		return ((MouseEvent)anEvent).getClickCount() >= 2;
//   	    }
//       return true;
//    }


//    /**
//     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
//     */
//    public boolean shouldSelectCell(EventObject anEvent) {
//
//        return super.shouldSelectCell(anEvent);
//    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     */
    public boolean stopCellEditing() {
        int row;

        for (row = 0; row < cellEditors.length; row++) {
            if (cellEditors[row] != null) {
                cellEditors[row].stopCellEditing();
            }
        }
        //stdEditor.stopCellEditing();
        return super.stopCellEditing();
    }


    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent
     * (javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int column) {

       lastRow = row;
       if (cellEditors[row] == null) {
           if (value == null) {
               stdField.setText("");
           } else {
               stdField.setText(value.toString());
           }

           return stdField;
       }

       return cellEditors[row].getTableCellEditorComponent(table,
                value, isSelected, row, column);
    }
}
