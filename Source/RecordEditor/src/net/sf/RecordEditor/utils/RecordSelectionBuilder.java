package net.sf.RecordEditor.utils;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.detailsSelection.FieldSelect;

public class RecordSelectionBuilder extends BaseSelectionBuilder {
    public RecordSelectionBuilder(IFieldDetail[] fields) {
        super(fields, null);
    }


    /**
     * Add Single field criteria to the expression
     * @param booleanOp Boolean Operator  (see  Common.BOOLEAN_OPERATOR_OR &  Common.BOOLEAN_OPERATOR_AND)
     * @param fieldName Field Name
     * @param operator Comparison Operator (i.e "=")
     * @param value value to compare the field against
     * @param caseSensitive wether to do a case sensitive compare.
     */
    public final void add(int booleanOp, String fieldName, String operator, String value, boolean caseSensitive) {
    	FieldSelect fs = getFieldDef(fieldName, operator,  value);
	    fs.setCaseSensitive(caseSensitive);
		add(fs, booleanOp);
    }

    public final void add(int booleanOp, IFieldDetail field, String operator, String value, boolean caseSensitive) {
    	FieldSelect fs = getFieldDef(field, operator,  value);
	    fs.setCaseSensitive(caseSensitive);
		add(fs, booleanOp);
    }

}
