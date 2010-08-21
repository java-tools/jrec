/*
 * @Author Bruce Martin
 * Created on 7/02/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.editProperties;

import javax.swing.table.AbstractTableModel;

import net.sf.RecordEditor.utils.common.Parameters;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class PropertiesTableModel extends AbstractTableModel {

    private String[] colHeadings;
    private String[] colNames;
    private int tblSize;
    private EditParams pgmProperties;


    /**
     * create a Properties Table model
     *
     * @param params standard Edit Option parameter
     * @param columnNames column names (of the properties)
     * @param columnHeader column table headings
     * @param tableSize table size
     */
    public PropertiesTableModel(final EditParams params,
            final String[] columnNames, final String[] columnHeader,
            final int tableSize) {
        super();

        pgmProperties = params;
        colHeadings = columnHeader;
        colNames = columnNames;
        tblSize = tableSize;
    }

    /**
     * Build a cell name
     * @param row row of the cell
     * @param col cells column
     * @return cells name
     */
    protected String getCellName(int row, int col) {
        return colNames[col] + row;
    }

    /**
     * Get a value of a specific cell in the table
     * @param rowIndex row of the cell
     * @param columnIndex column of the cell
     * @return cells value
     */
    protected String getStringValueAt(int rowIndex, int columnIndex) {
        String colName = getCellName(rowIndex, columnIndex);
        String s = pgmProperties.getProperty(colName);

        if (s == null) {
            s = Parameters.getString(colName);
        }
        return s;
    }


    /**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        pgmProperties.setProperty(getCellName(rowIndex, columnIndex), aValue.toString());
   }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return colHeadings.length;
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return tblSize;
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {

        return getStringValueAt(rowIndex, columnIndex);
    }

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return colHeadings[column];
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return true;
    }
}
