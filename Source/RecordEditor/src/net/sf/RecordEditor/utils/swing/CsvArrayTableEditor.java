/*
 * @Author Bruce Martin
 * Created on 15/04/2007 for version 0.62
 *
 * Purpose:
 * Edit a CsvArray in a Table
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * Edit a CsvArray in a Table
 *
 * @author Bruce Martin
 *
 */
public class CsvArrayTableEditor extends AbstractCellEditor
							  implements TableCellEditor {
    private CsvArray csvArray;

    /**
     * Create Csv Array field
     * @param seperator field seperator
     * @param quote quote char
     */
    public CsvArrayTableEditor(final String seperator, final String quote) {

        csvArray = new CsvArray(seperator, quote);
    }


    /**
     * Create 2 dimensional Csv Array field
     * @param seperator1 field seperator number 1
     * @param seperator2 field seperator number 2
     * @param quote quote char
     */
    public CsvArrayTableEditor(final String seperator1, final String seperator2, final String quote) {

        csvArray = new CsvArray(seperator1, seperator2, quote);
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {

        //CsvArray csvArray = new CsvArray(fldSeperator, fldQuote);
        String s = "";
        if (value != null) {
            s = value.toString();
        }
        csvArray.setTableDetails(table, row, column);

        //System.out.println("~~~ Setting 3 --> " + s);
        csvArray.setText(s);
        return csvArray;
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
        //System.out.println("~~~ Getting  --> " + csvArray.getText());
        return csvArray.getText();
    }



    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     */
    public void cancelCellEditing() {
        //System.out.println("Cancelling Editing ... ");
        csvArray.updateTable();
        super.cancelCellEditing();
    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     */
    public boolean stopCellEditing() {
        //System.out.println("Stopping Editing ... ");
        csvArray.updateTable();
        return super.stopCellEditing();
    }
}
