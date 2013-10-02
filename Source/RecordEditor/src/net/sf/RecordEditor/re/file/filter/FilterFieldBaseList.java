package net.sf.RecordEditor.re.file.filter;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.utils.RecordGroupSelectionBuilder;
import net.sf.RecordEditor.utils.RecordSelectionBuilder;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;
import net.sf.RecordEditor.utils.swing.Combo.IComboOption;

@SuppressWarnings("serial")
public abstract class FilterFieldBaseList extends AbstractTableModel {

	protected static FieldSelect TRUE_SELECT = FieldSelectX.getTrueSelection();

    public static final int NUMBER_FIELD_FILTER_ROWS = 20;
    //public static final int FIELD_NAME_COLUMN = FilterField.FLD_FIELD_NUMBER;

    private static final FilterField NULL_FILTER_FIELD_STD =  FilterField.newStandardFilterFields();
    private static final FilterField NULL_FILTER_FIELD_GROUP =  FilterField.newGroupFilterFields();

    private final String[] fieldFilterColumnHeadings;
	protected FilterField[][] filterFields;
	private RecordSel[] recSel = null;

	protected AbstractLayoutDetails layout;
	private int layoutIndex = 0;

	private final boolean useGroup;

	private final FilterField nullFilterField;
	private IComboOption<Integer>[] groupingOptions;

	@SuppressWarnings("unchecked")
	public FilterFieldBaseList(String[] columnNames, boolean useGroup) {
		super();

		fieldFilterColumnHeadings = columnNames;
		this.useGroup = useGroup;

		if (useGroup) {
			ComboStdOption<Integer>[] ga = Compare.GROUPING_OPERATORS;
			nullFilterField = NULL_FILTER_FIELD_GROUP;
			groupingOptions = new IComboOption[FieldSelectX.G_HIGHEST_CODE + 6];

			for (int i = 0; i < ga.length; i++) {
				groupingOptions[ga[i].key] = ga[i];
			}
		} else {
			nullFilterField = NULL_FILTER_FIELD_STD;
		}

        if (Common.TEST_MODE) {
        	fieldFilterColumnHeadings[0] = "Or";
        	fieldFilterColumnHeadings[1] = "And";
        }
	}


	/**
	 * @return the layoutIndex
	 */
	private int getFieldLayoutIndex() {
		if (useGroup) {
			return 0;
		}
		return layoutIndex;
	}

	/**
	 * @return the layoutIndex
	 */
	public int getLayoutIndex() {
		return layoutIndex;
	}


	/**
	 * @param pLayoutIndex
	 */
	public void setLayoutIndex(int pLayoutIndex) {
	    this.layoutIndex = pLayoutIndex;
	}

	public RecordSel getGroupRecordSelection() {

		buildGroupRecordSelections();
		return recSel[0];
	}

	public RecordSel getRecordSelection(int layoutIdx) {

		buildRecordSelections();
		return recSel[layoutIdx];
	}

	public boolean isSelectionTests(int layoutIdx) {
		buildRecordSelections();
		return recSel[layoutIdx] != TRUE_SELECT;
	}

	public void setRecordSelection(int layoutIdx, RecordSel rSel) {

		buildRecordSelections();
		recSel[layoutIdx] = rSel;
	}

	private void buildRecordSelections() {

	   	if (recSel == null) {
			FilterField ff;
			RecordSelectionBuilder b;

			recSel = new RecordSel[layout.getRecordCount()];
			for (int i = 0; i < recSel.length; i++) {
				recSel[i] = TRUE_SELECT;
				b = null;
				if (filterFields != null && filterFields[i] != null) {
	    			for (int j = 0; j < filterFields[i].length; j++) {
	    				ff = filterFields[i][j];
	    				if (ff != null && filterFields[i][j].getFieldNumber() >= 0) {
							AbstractRecordDetail rec = layout.getRecord(i);
	    					if (b == null) {
	    						IFieldDetail[] fields = new IFieldDetail[rec.getFieldCount()];
	    						for (int k = 0; k < fields.length; k++) {
	    							fields[k] = rec.getField(k);
	    						}
	    						b = new RecordSelectionBuilder(fields);
	    					}

	    					b.add(  ff.getBooleanOperator(),
	    							rec.getField(layout.getAdjFieldNumber(i, ff.getFieldNumber())),
	    							Compare.OPERATOR_STRING_VALUES[ff.getOperator()],
	    							ff.getValue(),
	    							! ff.getIgnoreCase());
	    				}
	    			}
				}

				if (b != null) {
					recSel[i] = b.build();
				}
			}
		}
	}

	private void buildGroupRecordSelections() {

	   	if (recSel == null) {
			FilterField ff;
			RecordGroupSelectionBuilder b;

			recSel = new RecordSel[1];

			recSel[0] = TRUE_SELECT;
			b = null;
			if (filterFields != null && filterFields[0] != null) {
    			for (int j = 0; j < filterFields[0].length; j++) {
    				ff = filterFields[0][j];
    				if (ff != null && filterFields[0][j].getFieldNumber() >= 0) {
    					if (b == null) {
    						IFieldDetail[][] fields = new FieldDetail[layout.getRecordCount()][];
    						for (int i = 0; i < fields.length; i++) {
								AbstractRecordDetail rec = layout.getRecord(i);
								fields[i] = new FieldDetail[rec.getFieldCount()];
	    						for (int k = 0; k < fields[i].length; k++) {
	    							fields[i][k] = rec.getField(k);
	    						}
    						}
    						b = new RecordGroupSelectionBuilder(fields);
    					}

    					int recordNumber = ff.getRecordNumber();
						b.add(  ff.getBooleanOperator(),
    							recordNumber,
    							ff.getRecFieldNumber(),
    							Compare.OPERATOR_STRING_VALUES[ff.getOperator()],
    							ff.getGrouping(),
    							ff.getValue(),
    							! ff.getIgnoreCase());
    				}
    			}
			}

			if (b != null) {
				recSel[0] = b.build();
			}
		}
	}


	public FilterField getFilterField(int idx) {
		return getFilterField(layoutIndex, idx);
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
	            return nullFilterField;
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
	 * @see javax.swing.table.TableModel#getColumnCount
	 */
	public int getColumnCount() {
	    return fieldFilterColumnHeadings.length;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnName
	 */
	public String getColumnName(int columnIndex) {
	    return fieldFilterColumnHeadings[columnIndex];
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
	    FilterField fld = nullFilterField;

	    int recIndex = getFieldLayoutIndex();

	    if (filterFields[recIndex] != null
	    &&  filterFields[recIndex][rowIndex] != null) {
	        fld = filterFields[recIndex][rowIndex];
	    }

	    switch (columnIndex) {
	    case FilterField.FLD_AND_VAL:
	    case FilterField.FLD_OR_VAL:
	    	if (rowIndex == 0) {
	    		return "";
	    	}
	    	break;
	    case FilterField.FLD_FIELD_NUMBER:
	    	if (useGroup) {

	    	} else  if (fld.getFieldNumber() == FilterField.NULL_FIELD) {
	            return null;
	        } else {
	        	return layout.getAdjField(recIndex, fld.getFieldNumber()).getName();
	        }
	    	break;
	    case FilterField.FLD_GROUPING:
	    	if (useGroup) {
	    		int g = fld.getGrouping();
	    		if (groupingOptions != null && g >= 0 && g < groupingOptions.length && groupingOptions[g] != null) {
	    			return groupingOptions[g];
	    		}
	    	}
	    }

	    return fld.getField(columnIndex);
	 }

	/**
	 * @see javax.swing.table.TableModel#isCellEditable
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	    return columnIndex >= FilterField.FLD_FIELD_NUMBER;
	}

	public void clearRecordSelection() {
		recSel = null;
	}

	/**
	 * @see javax.swing.table.TableModel#setValueAt
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		int recIndex = getFieldLayoutIndex();
	    if ((aValue == null)
	    &&    (filterFields[recIndex] == null
	        || filterFields[recIndex][rowIndex] == null)) {
	        return;
	    }

	    recSel = null;
	    ensureArrayOk(recIndex, rowIndex);

	    if (filterFields[recIndex][rowIndex] == null) {
	        filterFields[recIndex][rowIndex] = new FilterField(useGroup);
	    }

	    if (useGroup || columnIndex != FilterField.FLD_FIELD_NUMBER) {
	        filterFields[recIndex][rowIndex].setField(columnIndex, aValue);
	    } else {
	        int res = FilterField.NULL_FIELD;
	        if (! (aValue == null || "".equals(aValue.toString()))) {
	            int i;
	            String val = aValue.toString();

	            for (i = 0; i < getFieldCount(recIndex); i++) {
	                if (val.equalsIgnoreCase(layout.getAdjField(recIndex, i).getName())) {
	                    res = i;
	                    break;
	                }
	            }
	        }
	        filterFields[recIndex][rowIndex].setFieldNumber(res);
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
	protected final int getFieldCount(int recordIdx) {
		if (recordIdx < 0 || recordIdx >= layout.getRecordCount()) {
			return -1;
		}
	    return layout.getRecord(recordIdx).getFieldCount();
	}

	public abstract TableCellRenderer getTableCellRender();
	public abstract TableCellEditor   getTableCellEditor();
}