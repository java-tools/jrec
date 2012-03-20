/*
 * @Author Bruce Martin
 * Created on 31/07/2005
 *
 * Purpose:
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - JRecord Spun off
 */
package net.sf.RecordEditor.re.file.filter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;


/**
 * Table model where the user enters field base selections.
 *
 * @author Bruce Martin
 *
 * @version 0.53
 */
public class FilterFieldList extends AbstractTableModel {

    private static final String[] FIELD_FILTER_COLUMN_HEADINGS =
	{"Field", "Ignore Case", "Operator", "Value"};
    public static final int NUMBER_FIELD_FILTER_ROWS = 4;
    public static final int FIELD_NAME_COLUMN = 0;

    private static final FilterField NULL_FILTER_FIELD =  new FilterField();

    private FilterField[][] filterFields;

     private AbstractLayoutDetails layout;


    private int layoutIndex = 0;

    private int modelIndex = -1;
    private DefaultComboBoxModel[] fieldModels =
        {new DefaultComboBoxModel(), new DefaultComboBoxModel()};


    /**
     * Create filter details list
     *
     * @param recordLayout record Layout
     */
    public FilterFieldList(final AbstractLayoutDetails recordLayout) { //final RecordDetail[] pRecords) {
        super();
        int i;

        layout = recordLayout;

        filterFields = new FilterField[recordLayout.getRecordCount()][];
        for (i = 0; i < filterFields.length; i++) {
            //records[i]      = recordLayout.getRecord(i);
            filterFields[i] = null;
        }

        assignComboOptions();
    }


    /**
     * set layout index
     *
     * @param pLayoutIndex new layout index
     */
    public void setLayoutIndex(int pLayoutIndex) {
        this.layoutIndex = pLayoutIndex;

        assignComboOptions();
    }


    /**
     * get a specific filter details
     *
     * @param layoutIdx layout index
     * @param idx index of
     *
     * @return requested filter details
     */
    public FilterField getFilterField(int layoutIdx, int idx) {

        if (filterFields[layoutIdx] == null
        || filterFields[layoutIdx][idx] == null) {
                return NULL_FILTER_FIELD;
        }

        return filterFields[layoutIdx][idx];
    }

    /**
     * Set the filtered field
     * @param layoutIdx record number
     * @param idx index 
     * @param value new fitered field
     */
    public void setFilterField(int layoutIdx, int fieldIdx, FilterField value) {

    	ensureArrayOk(layoutIdx, fieldIdx);

    	filterFields[layoutIdx][fieldIdx] = value;
    }

    /**
     * Assign the field combo options
     *
     */
    private void assignComboOptions() {
        int i, j;

        for (j = 0; j < fieldModels.length; j++) {
            fieldModels[j].removeAllElements();
            
             fieldModels[j].addElement("");

            for (i = 0; i < getFieldCount(layoutIndex); i++) {
                fieldModels[j].addElement(
                        layout.getAdjField(layoutIndex, i).getName());
            }
        }
    }


    /**
     * get the combo box for field options
     * @return field combo box
     */
    public DefaultComboBoxModel getFieldModel() {
        if (modelIndex - 1 < fieldModels.length) {
            modelIndex += 1;
        }
        return fieldModels[modelIndex];
    }


    /**
     * @see javax.swing.table.TableModel#getColumnCount
     */
    public int getColumnCount() {
        return FIELD_FILTER_COLUMN_HEADINGS.length;
    }


    /**
     * @see javax.swing.table.TableModel#getColumnName
     */
    public String getColumnName(int columnIndex) {
        return FIELD_FILTER_COLUMN_HEADINGS[columnIndex];
    }


    /**
     * @see javax.swing.table.TableModel#isCellEditable
     */
    public int getRowCount() {
         return NUMBER_FIELD_FILTER_ROWS;
    }


    /**
     * @see javax.swing.table.TableModel#getValueAt
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        FilterField fld = NULL_FILTER_FIELD;


        if (filterFields[layoutIndex] != null
        &&  filterFields[layoutIndex][rowIndex] != null) {
            fld = filterFields[layoutIndex][rowIndex];
        }

        if (columnIndex == FIELD_NAME_COLUMN) {
            if (fld.getFieldNumber() == FilterField.NULL_FIELD) {
                return null;
            }
            return layout.getAdjField(layoutIndex, fld.getFieldNumber()).getName();
        }

        /*System.out.println("--> " + rowIndex
                + " " + columnIndex + " : "
                + fld == NULL_FILTER_FIELD
                + " : " + fld.getField(columnIndex));*/
        return fld.getField(columnIndex);
     }


    /**
     * @see javax.swing.table.TableModel#isCellEditable
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }


    /**
     * @see javax.swing.table.TableModel#setValueAt
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if ((aValue == null)
        &&    (filterFields[layoutIndex] == null
            || filterFields[layoutIndex][rowIndex] == null)) {
            return;
        }

        ensureArrayOk(layoutIndex, rowIndex);

        if (filterFields[layoutIndex][rowIndex] == null) {
            filterFields[layoutIndex][rowIndex] = new FilterField();
        }

        if (columnIndex > 0) {
            filterFields[layoutIndex][rowIndex].setField(columnIndex, aValue);
        } else {
            int res = FilterField.NULL_FIELD;
            if (! (aValue == null || "".equals(aValue.toString()))) {
                int i;
                String val = aValue.toString();

                for (i = 0; i < getFieldCount(layoutIndex); i++) {
                    if (val.equalsIgnoreCase(layout.getAdjField(layoutIndex, i).getName())) {
                        res = i;
                        break;
                    }
                }
            }
            filterFields[layoutIndex][rowIndex].setFieldNumber(res);
        }
    }
    
    /**
     * Ensure the array is initialized
     * @param layoutIdx layout index (record number)
     * @param fieldIndex field number
     */
    private void ensureArrayOk(int layoutIdx, int fieldIndex) {
    	
        if (filterFields[layoutIdx] == null) {
            filterFields[layoutIdx] = new FilterField[NUMBER_FIELD_FILTER_ROWS];
        }
    }
    

    /**
     * Get the number of fields in a specified record
     * @param recordIdx record index
     * @return number of fields
     */
    private int getFieldCount(int recordIdx) {
    	if (recordIdx < 0 || recordIdx >= layout.getRecordCount()) {
    		return -1;
    	}
        return layout.getRecord(recordIdx).getFieldCount();
    }
}
