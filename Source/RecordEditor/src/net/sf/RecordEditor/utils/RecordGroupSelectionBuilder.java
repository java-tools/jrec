package net.sf.RecordEditor.utils;

import net.sf.JRecord.Common.FieldDetail;

/**
 * Build Group Record Selection
 * @author Bruce Martin
 *
 */
public class RecordGroupSelectionBuilder extends BaseSelectionBuilder {

    public RecordGroupSelectionBuilder(FieldDetail[][] recFields) {
        super(null, recFields);
    }

    /**
     * Add Group Criteria to the boolean Exprression
     *
     * @param booleanOp Boolean Operator (see  Common.BOOLEAN_OPERATOR_OR &  Common.BOOLEAN_OPERATOR_AND)
     * @param recNo Record Number
     * @param fldNo field no to be tested
     * @param operator comparison operator
     * @param summaryOp Summary function (ie Sum, ave, first, last)
     *        see FieldSelectX.G_FIRST --> FieldSelectX.G_ANY
     * @param value value to test against
     * @param caseSensitive wether it is case sensitive or not
     */
    public final void add(int booleanOp, int recNo, int fldNo, String operator, int summaryOp, String value, boolean caseSensitive) {
    	add(getFieldDef(recNo, recFields[recNo][fldNo], operator,  summaryOp, value, caseSensitive), booleanOp);
    }
}
