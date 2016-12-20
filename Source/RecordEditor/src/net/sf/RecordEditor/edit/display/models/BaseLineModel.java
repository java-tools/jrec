package net.sf.RecordEditor.edit.display.models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.BasicKeyedField;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.RecordEditor.re.file.FieldMapping;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.IGetView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public abstract class BaseLineModel
extends AbstractTableModel
implements IGetView {

	protected static final int FIRST_DATA_COLUMN = 4;

	public final int firstDataColumn;
	public final int hexColumn;

//	public abstract void setValueAt(Object val, int row, int col);

//	public abstract int getColumnCount();

	private FileView fileView;
	protected AbstractLayoutDetails layout;
	private int currentLayout;
	protected String[] columnName = LangConversion.convertColHeading(
			"Single_Record",
			new String[] {"Field", "Start", "Len" , "Type", "Data", "Text", "Hex" });

	private FieldMapping fieldMapping = null;
	private int[]  lastFieldCounts;

	private HashMap<Integer, String> typeNames = new HashMap<Integer, String>(400);


	public BaseLineModel(final FileView file) {
		this(file, Common.OPTIONS.typeOnRecordScreen.isSelected() ? FIRST_DATA_COLUMN : 3);
	}


	public BaseLineModel(final FileView file, int dataColumn) {
		super();

	    fileView = file;
	    firstDataColumn = dataColumn;
	    hexColumn = dataColumn + 2;

        layout  = file.getLayout();

        lastFieldCounts = getFieldCounts();
        fieldMapping = new FieldMapping(lastFieldCounts.clone());


        try {
        	ArrayList<BasicKeyedField> types = ExternalConversion.getTypes(Common.getConnectionIndex());
        	String tblId = Common.getTblLookupKey(Common.TI_FIELD_TYPE);

			for (BasicKeyedField type : types) {
				if ( type.name != null && !  type.name.equals(Integer.toString(type.key))) {
					//System.out.println(type.key + "\t" + type.name + "\t" + LangConversion.convertId(LangConversion.ST_FIELD, tblId + type.key, type.name));
					typeNames.put(type.key, LangConversion.convertId(LangConversion.ST_FIELD, tblId + type.key, type.name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see javax.swing.table.TableModel#getRowCount
	 */
	public int getRowCount() {
	    int layoutIndex = getFixedCurrentLayout();
	    int ret;

	    if (layoutIndex >= layout.getRecordCount()) {
	    	ret = 1;
	    } else {
		    ret = fileView.getLayoutColumnCount(layoutIndex);

		    ret = fieldMapping.getColumnCount(layoutIndex, ret);
	    }

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
		            return LangConversion.convert("Full Line");
		        }
		    } else if (row == layout.getRecord(idx).getFieldCount()) {
		    	 if (col == 0) {
		    		 return LangConversion.convert("Key Value");
		    	 }
			} else if (col < firstDataColumn) { //
			    int column = getRealRow(row);
				AbstractRecordDetail record = fileView.getLayout().getRecord(idx);
				IFieldDetail df = record.getField(column);

				if (df == null) {
					return null;
				} else {
					switch (col) {
					case 0:					return df.getName();
					case 1:					return Integer.valueOf(df.getPos());
					case 2:
						int len = df.getLen();

						if (len < 0) {
							return "";
						}

						return Integer.valueOf(df.getLen());
					default:
						if (fileView.getLayout() instanceof LayoutDetail) {
							return typeNames.get(df.getType());
						}
						return record.getFieldTypeName(column);

//						System.out.println(" Type: " + df.getType() + " " + typeNames.containsKey(df.getType()) + " " + typeNames.get(df.getType()));
//						if (typeNames.containsKey(df.getType()))
//							return typeNames.get(df.getType());
//						else return df.getType();
					}
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


    public final void layoutChanged(AbstractLayoutDetails newLayout) {

    	if (layout != newLayout) {
    		layout = newLayout;

    	    fieldMapping.resetMapping(getFieldCounts());

    		super.fireTableStructureChanged();
    	} else {
    		int[] tfc = getFieldCounts();
    		for (int i = 0 ; i < tfc.length; i++) {
    			if (tfc[i] != lastFieldCounts[i]) {
    				lastFieldCounts = tfc;
    	    		fieldMapping.resetMapping(lastFieldCounts.clone());
    			}
    		}
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

		if (col >= firstDataColumn) {
			return columnName[col - firstDataColumn + FIRST_DATA_COLUMN];
		}
		return columnName[col];
	}

	/**
	 * Get The file view
	 * @return  file view
	 */
	public final FileView getFileView() {
		return fileView;
	}

	/**
	 * @return the fieldMapping
	 */
	public final FieldMapping getFieldMapping() {
		return fieldMapping;
	}
}