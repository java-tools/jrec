/**
 * @Author Bruce Martin
 * Created on 30/07/2005
 * @version 0.55
 *
 * Purpose: This class holds field filter details. These details are
 * used to select records to be displayed in a filtered view based
 * on the record values.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Changed final Arrays to protected to limit external
 *     updates
 */
package net.sf.RecordEditor.utils.filter;

/**
 * This class holds field filter details. These details are
 * used to select records to be displayed in a filtered view based
 * on the record values.
 *
 * @author Bruce Martin
 *
 * @version 0.53
 */
public final class FilterField {

    public static final int NULL_FIELD  = -1;

    public static final int FLD_FIELD_NUMBER   = 0;
    public static final int FLD_CASE_SENSITIVE = 1;
    public static final int FLD_OPERATOR       = 2;
    public static final int FLD_VALUE          = 3;

    private int fieldNumber = NULL_FIELD;
    private Boolean ignoreCase = Boolean.TRUE;
    private int operator = Compare.OP_CONTAINS;
    private String value = "";


    /**
     * Set field by field Index
     *
     * @param fieldIndex field number to be updated
     * @param newValue new value for the field
     */
    public void setField(int fieldIndex, Object newValue) {

        switch (fieldIndex) {
        	case FLD_FIELD_NUMBER:
        	    fieldNumber = Integer.parseInt(newValue.toString());
        	break;
            case FLD_CASE_SENSITIVE:
                ignoreCase = (Boolean) newValue;
          	break;
            case FLD_OPERATOR:
                operator = Compare.OP_CONTAINS;
                for (int i = Compare.OP_EQUALS; i <= Compare.OP_LAST_OPERATOR; i++)
                if (Compare.OPERATOR_STRING_VALUES[i].equalsIgnoreCase(newValue.toString())) {
                    operator = i;
                }
    	    break;
            case FLD_VALUE:
                value = newValue.toString();
            break;
            default:
        }
    }



    /**
     * Get field by field Index
     *
     * @param fieldIndex field number to be updated
     *
     * @return requested fields value
     */
    public Object getField(int fieldIndex) {

        switch (fieldIndex) {
        	case FLD_FIELD_NUMBER:
        	    return Integer.valueOf(fieldNumber + 1);
            case FLD_CASE_SENSITIVE:
                return ignoreCase;
            case FLD_OPERATOR:
                return Compare.OPERATOR_STRING_VALUES[operator];
            case FLD_VALUE:
                return value;
             default:
                return null;
        }
    }


    /**
     * Get Case Senstive Status
     *
     * @return Case Senstive Status
     */
    public Boolean getIgnoreCase() {
        return ignoreCase;
    }


    /**
     * Get field number
     *
     * @return field number
     */
    public int getFieldNumber() {
        return fieldNumber;
    }


    /**
     * set the field number
     *
     * @param val new field number
     */
    public void setFieldNumber(int val) {
        this.fieldNumber = val;
    }


    /**
     * get the Comparison Operator
     *
     * @return Comparison Operator
     */
    public int getOperator() {
        return operator;
    }

    /**
     * Get the Comparison value
     *
     * @return Comparison value
     */
    public String getValue() {
        return value;
    }



	/**
	 * @param operator the operator to set
	 */
	public final void setOperator(int operator) {
		this.operator = operator;
	}



	/**
	 * @param value the value to set
	 */
	public final void setValue(String value) {
		this.value = value;
	}
}
