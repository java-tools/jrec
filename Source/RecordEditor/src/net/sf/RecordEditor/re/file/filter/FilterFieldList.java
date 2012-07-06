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

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.detailsSelection.RecordSel;
import net.sf.RecordEditor.utils.RecordSelectionBuilder;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;


/**
 * Table model where the user enters field base selections.
 *
 * @author Bruce Martin
 *
 * @version 0.53
 */
@SuppressWarnings("serial")
public class FilterFieldList extends AbstractTableModel {

    private static final String[] FIELD_FILTER_COLUMN_HEADINGS =
    		LangConversion.convertColHeading(
    				"Filter Selecton Field Values",
    				new String[] {"", "", "Field", "Ignore Case", "Operator", "Value"});
    public static final int NUMBER_FIELD_FILTER_ROWS = 16;
    public static final int FIELD_NAME_COLUMN = FilterField.FLD_FIELD_NUMBER;

    private static final FilterField NULL_FILTER_FIELD =  new FilterField();

    private FilterField[][] filterFields;

    private RecordSel[] recSel = null;

    @SuppressWarnings("rawtypes")
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
    public FilterFieldList(@SuppressWarnings("rawtypes") final AbstractLayoutDetails recordLayout) {
        super();
        int i;

        layout = recordLayout;

        filterFields = new FilterField[recordLayout.getRecordCount()][];
        for (i = 0; i < filterFields.length; i++) {
            //records[i]      = recordLayout.getRecord(i);
            filterFields[i] = null;
        }

        assignComboOptions();

        if (Common.TEST_MODE) {
        	FIELD_FILTER_COLUMN_HEADINGS[0] = "Or";
        	FIELD_FILTER_COLUMN_HEADINGS[1] = "And";
        }
    }


    /**
     * set layout index
     *
     * @param pLayoutIndex new layout index
     */
    /**
     * @param pLayoutIndex
     */
    public void setLayoutIndex(int pLayoutIndex) {
        this.layoutIndex = pLayoutIndex;

        assignComboOptions();
    }


    public RecordSel getRecordSelection(int layoutIdx) {

    	buildRecordSelections();
    	return recSel[layoutIdx];
    }

    public void setRecordSelection(int layoutIdx, RecordSel rSel) {

    	buildRecordSelections();
    	recSel[layoutIdx] = rSel;
    }


    private void buildRecordSelections() {

       	if (recSel == null) {
    		FieldSelect trueSel = FieldSelectX.getTrueSelection();
    		RecordSel rsel;
    		FilterField ff;
    		RecordSelectionBuilder b;

    		recSel = new RecordSel[layout.getRecordCount()];
    		for (int i = 0; i < recSel.length; i++) {
    			recSel[i] = trueSel;
    			b = null;
    			if (filterFields != null && filterFields[i] != null) {
	    			for (int j = 0; j < filterFields[i].length; j++) {
	    				ff = filterFields[i][j];
	    				if (ff != null && filterFields[i][j].getFieldNumber() >= 0) {
    						AbstractRecordDetail<FieldDetail> rec = layout.getRecord(i);
	    					if (b == null) {
	    						FieldDetail[] fields = new FieldDetail[rec.getFieldCount()];
	    						for (int k = 0; k < fields.length; k++) {
	    							fields[k] = rec.getField(k);
	    						}
	    						b = new RecordSelectionBuilder(fields);
	    					}

	    					b.add(  ff.getBooleanOperator(),
	    							rec.getField(ff.getFieldNumber()).getName(),
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

        switch (columnIndex) {
        case FilterField.FLD_AND_VAL:
        case FilterField.FLD_OR_VAL:
        	if (rowIndex == 0) {
        		return "";
        	}
        	break;
        case FIELD_NAME_COLUMN:
             if (fld.getFieldNumber() == FilterField.NULL_FIELD) {
                return null;
            }
            return layout.getAdjField(layoutIndex, fld.getFieldNumber()).getName();
       }
        if (columnIndex == FIELD_NAME_COLUMN) {
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
        return columnIndex >= FilterField.FLD_FIELD_NUMBER;
    }

    public void clearRecordSelection() {
    	recSel = null;
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

        recSel = null;
        ensureArrayOk(layoutIndex, rowIndex);

        if (filterFields[layoutIndex][rowIndex] == null) {
            filterFields[layoutIndex][rowIndex] = new FilterField();
        }

        if (columnIndex != FilterField.FLD_FIELD_NUMBER) {
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
