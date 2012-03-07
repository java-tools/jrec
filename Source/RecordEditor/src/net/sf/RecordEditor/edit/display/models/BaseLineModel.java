package net.sf.RecordEditor.edit.display.models;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.file.FieldMapping;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.edit.file.GetView;
import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public abstract class BaseLineModel 
extends AbstractTableModel 
implements GetView {

	public static final int FIRST_DATA_COLUMN = 3;

//	public abstract void setValueAt(Object val, int row, int col);

//	public abstract int getColumnCount();

	private FileView fileView;
	protected AbstractLayoutDetails<?, ?> layout;
	private int currentLayout;
	protected String[] columnName = {"Field", "Start", "Len" , "Data", "Text", "Hex" };
	
	FieldMapping fieldMapping = null;


	public BaseLineModel(final FileView file) {
		super();

	    fileView = file;

        layout  = file.getLayout();
        
         
        fieldMapping = new FieldMapping(getFieldCounts());
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount
	 */
	public int getRowCount() {
	    int layoutIndex = getFixedCurrentLayout();
	    int ret = fileView.getLayoutColumnCount(layoutIndex);

	    ret = fieldMapping.getColumnCount(layoutIndex, ret);
	    	    
	    if (showKey()) {
	    	ret += 1;
	    }
	    
   	    return ret;
	}

	protected boolean showKey() {
		return false;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int row, final int col) {
	
		    int idx = getFixedCurrentLayout();
		    if (idx >= layout.getRecordCount()) {
		        if (col == 0) {
		            return "Full Line";
		        } //else if (col < FIRST_DATA_COLUMN) {
		      //      return null;
		      //  }
		    } else if (row == layout.getRecord(idx).getFieldCount()) { 
		    	 if (col == 0) { 
		    		 return "Key Value";
		    	 }
		    } else if (col == 0) {                  // Selection Button
			    int column = getRealRow(row);
			    return layout.getRecord(getFixedCurrentLayout()).getField(column).getName();
			} else if (col < FIRST_DATA_COLUMN) { //
				FieldDetail df = fileView.getLayout().getRecord(idx).getField(getRealRow(row));
	
				if (df == null) {
					return null;
				} else if (col == 1) {
					return Integer.valueOf(df.getPos());
				} else {
					int len = df.getLen();
	
					if (len == Common.NULL_INTEGER) {
						return "";
					}
	
					return Integer.valueOf(df.getLen());
				}
			}
            return null;

		}



	/**
	 * Set the layout
	 *
	 * @param newCurrentLayout The currentLayout to set.
	 */
	public void setCurrentLayout(int newCurrentLayout) {
	    this.currentLayout = newCurrentLayout;
	}
	
	public final int getRealRowWithKey(int row) {
		//int idx = getFixedCurrentLayout();
		if (isKeyRow(row)) {
			return Constants.KEY_INDEX;
		}
		return getRealRow(row);
	}
	
    /**
     * Get the real row of the data
     * @param row requested row
     * @return real row
     */
    public final int getRealRow(int row) {
    	int idx = getFixedCurrentLayout();
        return fileView.getRealColumn(idx, getRowLocal(idx, row));
    }

    

	/**
	 * Remap the row (taking into account any hidden rows)
	 * @param inRow initial row
	 */
	public final int getRowLocal(int inRow) {
		if (isKeyRow(inRow)) {
			return Constants.KEY_INDEX;
		}
		return getRowLocal(getFixedCurrentLayout(), inRow);
	}
	
	/**
	 * Remap the row (taking into account any hidden rows)
	 * @param layoutIndex record layout index
	 * @param inRow initial row
	 */
	private final int getRowLocal(int layoutIndex, int inRow) {
	    int ret = inRow;

	    if (inRow >= 0) {
	        ret = fieldMapping.getRealColumn(layoutIndex, inRow);
	    }

	    return ret;
	}

	public final void hideRow(int row) {
		int idx = getFixedCurrentLayout();
		fieldMapping.hideColumn(idx, fieldMapping.getRealColumn(idx, row));
		this.fireTableDataChanged();
	}
	
	public final void showRow(int row) { 
		
	    fieldMapping.showColumn(getFixedCurrentLayout(), row);
	    this.fireTableDataChanged();
	}


	protected final boolean isKeyRow(int row) {
		boolean ret = false;
		int idx = getFixedCurrentLayout();
		if (idx >= 0 && idx < layout.getRecordCount()
		&& row == layout.getRecord(idx).getFieldCount()) {
			ret = true;
		}
		return ret;
	}
	/**
	 * get the current record layout
	 *
	 * @return current record layout
	 */
    public int getFixedCurrentLayout() {
    	
        if (currentLayout < 0) {
            return 0;
        }
        return currentLayout;
    }


    public final void layoutChanged(AbstractLayoutDetails<?, ?> newLayout) {
    	
    	if (layout != newLayout) {
    		layout = newLayout;
    	    
    	    fieldMapping.resetMapping(getFieldCounts());

    		super.fireTableStructureChanged();
    	}
    }  
    
	
	/**
	 * Get the number of fields for each record
	 * @return field counts for for each record
	 */
	private int[] getFieldCounts() {
		int[] rows = new int[layout.getRecordCount()];
        
	    for (int i = 0; i < layout.getRecordCount(); i++) {
	    	rows[i] = fileView.getLayoutColumnCount(i);
	    }
	
	    return rows;
	}

	
	/**
	 * get the current record layout
	 *
	 * @return current record layout
	 */
	public int getCurrentLayout() {
	    return currentLayout;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel.getColumnName
	 */
	public String getColumnName(int col) {

		return columnName[col];
	}

	/**
	 * Get The file view
	 * @return  file view
	 */
	public final FileView<?> getFileView() {
		return fileView;
	}

	/**
	 * @return the fieldMapping
	 */
	public final FieldMapping getFieldMapping() {
		return fieldMapping;
	}
}