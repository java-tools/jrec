/*
 * @Author Bruce Martin
 * Created on 23/01/2007
 *
 * Purpose:
 * Table model to display/enter sort options in a JTable
 */
package net.sf.RecordEditor.edit.display.util;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.tree.FieldSummaryDetails;

/**
 * Table model to display/enter sort options in a JTable
 *
 * @author Bruce Martin
 *
 */
public final class SortFieldSummaryMdl extends AbstractTableModel {
	
	
//	public static final String[] OPERATOR_NAMES = new String[5];
//	static {
//		OPERATOR_NAMES[FieldSummaryDetails.OP_NONE] = "";
//		OPERATOR_NAMES[FieldSummaryDetails.OP_SUM] = "Sum";
//		OPERATOR_NAMES[FieldSummaryDetails.OP_MIN] = "Minimum";
//		OPERATOR_NAMES[FieldSummaryDetails.OP_MAX] = "Maximum";
//		OPERATOR_NAMES[FieldSummaryDetails.OP_AVE] = "Average";
//	};
//	private static final int[] OPERATORS = {
//		FieldSummaryDetails.OP_NONE, FieldSummaryDetails.OP_SUM, 
//		FieldSummaryDetails.OP_MIN, FieldSummaryDetails.OP_MAX, FieldSummaryDetails.OP_AVE
//	};



    private static final String[] COLUMN_NAMES = {"Field", "Function" };
 //   private String[] fieldName;
 //   private boolean[] ascending;
    private FieldSummaryDetails fieldSummary;
    private int columnCount = 2;

    /**
     * Table model of fields to sort on
     * @param tableSize size of the table
     */
    public SortFieldSummaryMdl(AbstractLayoutDetails recordLayout) {
        super();

        fieldSummary = new FieldSummaryDetails(recordLayout);
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
        return columnIndex > 0;
    }

    /**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	if (rowIndex >= 0) {
	    	int val = FieldSummaryDetails.OP_NONE;
	    	
	        if (aValue != null && ! "".equals(aValue)) {
	        	String s = aValue.toString();
	        	for (int i = 0; i < FieldSummaryDetails.OPERATOR_NAMES.length; i++) {
	        		if (FieldSummaryDetails.OPERATOR_NAMES[i].equals(s)) {
	        			val = i;
	        			break;
	        		}
	        	}
	        }
	    	
	    	fieldSummary.setOperator(rowIndex, val);
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
//	public void setColumnCount(int newColumnCount) {
//		this.columnCount = newColumnCount;
//	}

	/**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return fieldSummary.getFieldCount();
    }


    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return fieldSummary.getFieldName(rowIndex);
        }

        return FieldSummaryDetails.OPERATOR_NAMES[fieldSummary.getOperator(rowIndex)];
    }
    

	public FieldSummaryDetails getFieldSummary() {
		return fieldSummary;
	}



	public int getRecordIndex() {
		return fieldSummary.getRecordIndex();
	}



	public void setRecordIndex(int layoutIndex) {
		fieldSummary.setRecordIndex(layoutIndex);
		
		this.fireTableDataChanged();
	}
}
