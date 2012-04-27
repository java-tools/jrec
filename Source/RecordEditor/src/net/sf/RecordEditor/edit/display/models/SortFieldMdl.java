/*
 * @Author Bruce Martin
 * Created on 23/01/2007
 *
 * Purpose:
 * Table model to display/enter sort options in a JTable
 */
package net.sf.RecordEditor.edit.display.models;

import javax.swing.table.AbstractTableModel;

import net.sf.RecordEditor.jibx.compare.SortFields;


/**
 * Table model to display/enter sort options in a JTable
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public final class SortFieldMdl extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"Field", "Ascending" };
    private String[] fieldName;
    private boolean[] ascending;
    
    private int columnCount = 2;

    /**
     * Table model of fields to sort on
     * @param tableSize size of the table
     */
    public SortFieldMdl(final int tableSize) {
        super();

        fieldName = new String[tableSize];
        ascending = new boolean[tableSize];

        resetFields();
     }

    /**
     * Resets the table values (after change record ?)
     *
     */
    public void resetFields() {

        for (int i = 0; i < fieldName.length; i++) {
            ascending[i] = true;
            fieldName[i] = " ";
        }
   }


    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if (aValue != null) {
            if (columnIndex == 0) {
                this.fieldName[rowIndex] = aValue.toString();
            } else {
                this.ascending[rowIndex] = ((Boolean) aValue).booleanValue();
            }
        }
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnCount;
    }
    
    

    /**
	 * @param newColumnCount the columnCount to set
	 */
	public void setColumnCount(int newColumnCount) {
		this.columnCount = newColumnCount;
	}

	/**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return ascending.length;
    }


    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return this.fieldName[rowIndex];
        }
        return Boolean.valueOf(this.ascending[rowIndex]);
    }


    /**
     * @param index index of the required ascending value
     * @return Returns the ascending.
     */
    public boolean getAscending(int index) {
        return ascending[index];
    }


    /**
     * @param index index of the required field name
     * @return Returns the fieldName.
     */
    public String getFieldName(int index) {
        return fieldName[index];
    }
    
    /**
     * get model details 
     * @return model details
     */
    public SortFields[] getSortFields() {
    	int i, j, size;
    	SortFields[] ret;
    	
    	size = 0;
    	for (i = 0; i < fieldName.length; i++) {
    		if (fieldName[i] != null && ! "".equals(fieldName[i])) {
    			size += 1 ;
    		}
    	}
    	
    	j = 0;
    	ret = new SortFields[size];
    	for (i = 0; i < ret.length; i++) {
    		if (fieldName[i] != null && ! "".equals(fieldName[i])) {
    			ret[j] = new net.sf.RecordEditor.jibx.compare.SortFields();
    			ret[j].fieldName = fieldName[i];
    			ret[j].ascending = ascending[i];
    			j += 1 ;
    		}
    	}
    	
    	return ret;
     }
    
    /**
     * 
     * @param fieldDetails
     */
    public void setSortTree(SortFields[] fieldDetails) {
    	int i;
    	
    	for (i = 0; i < fieldDetails.length; i++) {
    		fieldName[i] = fieldDetails[i].fieldName;
    		ascending[i] = fieldDetails[i].ascending;
    	}
    	
    	for (i = fieldDetails.length; i < fieldName.length; i++) {
    		fieldName[i] = " ";
    		ascending[i] = false;
    	}
    }
}
