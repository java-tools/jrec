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

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;


/**
 * Table model where the user enters field base selections.
 *
 * @author Bruce Martin
 *
 * @version 0.53
 */
@SuppressWarnings("serial")
public class FilterFieldList extends FilterFieldBaseList {

	protected static final String[] FIELD_FILTER_COLUMN_HEADINGS =
	    		LangConversion.convertColHeading(
	    				"Filter Selecton Field Values",
	    				new String[] {"", "", "Field", "Ignore Case", "Operator", "Value"});

    private int modelIndex = -1;
    DefaultComboBoxModel[] fieldModels =
        {new DefaultComboBoxModel(), new DefaultComboBoxModel()};


    /**
     * Create filter details list
     *
     * @param recordLayout record Layout
     */
    public FilterFieldList(@SuppressWarnings("rawtypes") final AbstractLayoutDetails recordLayout) {
        super(FIELD_FILTER_COLUMN_HEADINGS, false);
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
	 * @param pLayoutIndex
	 */
	public void setLayoutIndex(int pLayoutIndex) {
	    super.setLayoutIndex(pLayoutIndex);

	    assignComboOptions();
	}

	/**
	 * Assign the field combo options
	 *
	 */
	protected void assignComboOptions() {
	    int i, j;

	    for (j = 0; j < fieldModels.length; j++) {
	        fieldModels[j].removeAllElements();

	         fieldModels[j].addElement("");

	        for (i = 0; i < getFieldCount(getLayoutIndex()); i++) {
	            fieldModels[j].addElement(
	                    layout.getAdjField(getLayoutIndex(), i).getName());
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

    @Override
    public TableCellRenderer getTableCellRender() {
    	return new ComboBoxRender(fieldModels[0]);
    }

    @Override
    public TableCellEditor getTableCellEditor() {
    	return new DefaultCellEditor(new ComboBoxRender(fieldModels[1]));
    }
}
