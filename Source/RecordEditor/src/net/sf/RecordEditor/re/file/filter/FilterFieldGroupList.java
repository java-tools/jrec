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

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboRendor;


/**
 * Table model where the user enters field base selections.
 *
 * @author Bruce Martin
 *
 * @version 0.53
 */
@SuppressWarnings("serial")
public class FilterFieldGroupList extends FilterFieldBaseList {


	protected static final String[] FIELD_FILTER_COLUMN_HEADINGS =
	    		LangConversion.convertColHeading(
	    				"Filter Selecton Field Values",
	    				new String[] {"", "", "Field", "Ignore Case", "Group by function", "Operator", "Value"});


	private TreeComboItem[] recordFields;

    /**
     * Create filter details list
     *
     * @param recordLayout record Layout
     */
    public FilterFieldGroupList(final AbstractLayoutDetails recordLayout) {
        super(FIELD_FILTER_COLUMN_HEADINGS, true);
        int i;

        layout = recordLayout;

        filterFields = new FilterField[recordLayout.getRecordCount()][];
        for (i = 0; i < filterFields.length; i++) {
            //records[i]      = recordLayout.getRecord(i);
            filterFields[i] = null;
        }

        recordFields = new TreeComboItem[recordLayout.getRecordCount()];

        for (i = 0; i < recordFields.length; i++) {
        	recordFields[i] = getRecordFields(i, recordLayout.getRecord(i));
        }
    }


    private TreeComboItem getRecordFields(int idx, AbstractRecordDetail rec) {
    	TreeComboItem[] children = new TreeComboItem[rec.getFieldCount()];
    	int pref = idx * Compare.RECORD_MULTIPLE + 2;
    	for (int i = 0; i < children.length; i++) {
    		children[i] = new TreeComboItem(pref + i, rec.getField(i).getName(), "");
    	}

    	return new TreeComboItem(pref - 1, rec.getRecordName(), "", children);
    }


    @Override
    public TableCellRenderer getTableCellRender() {
    	return new TreeComboRendor(recordFields);
    }

    @Override
    public TableCellEditor getTableCellEditor() {
    	return new TreeComboRendor(recordFields);
    }
}
