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
package net.sf.RecordEditor.re.file.filter;

import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.Combo.ComboStdOption;

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

    public static final int FLD_OR_VAL         = 0;
    public static final int FLD_AND_VAL        = 1;
    public static final int FLD_FIELD_NUMBER   = 2;
    public static final int FLD_CASE_SENSITIVE = 3;
    public static final int FLD_OPERATOR       = 4;
    public static final int FLD_VALUE          = 5;
    public static final int FLD_GROUPING       = 4;
    public static final int FLD_GROUP_OPERATOR = 5;
    public static final int FLD_GROUP_VALUE    = 6;

    public static final int[] STANDARD_FIELDS = {
    	FLD_OR_VAL, FLD_AND_VAL, FLD_FIELD_NUMBER, FLD_CASE_SENSITIVE, FLD_GROUP_OPERATOR, FLD_GROUP_VALUE,
    };
    public static final int[] GROUPING_FIELDS = {
    	FLD_OR_VAL, FLD_AND_VAL, FLD_FIELD_NUMBER, FLD_CASE_SENSITIVE, FLD_GROUPING, FLD_GROUP_OPERATOR, FLD_GROUP_VALUE,
    };

	private final String[] OR_LIST  = {LangConversion.convert(LangConversion.ST_MESSAGE, "Or"), ""};
	private final String[] AND_LIST = {"", LangConversion.convert(LangConversion.ST_MESSAGE, "And")};

	private int  booleanOperator=Common.BOOLEAN_OPERATOR_AND;
    private int fieldNumber = NULL_FIELD;
    private Boolean ignoreCase = Boolean.TRUE;
    private int grouping = FieldSelectX.G_FIRST;
    private int operator = Compare.OP_CONTAINS;
    private String value = "";

    public final int[] fieldsUsed;


    public static FilterField newStandardFilterFields() {
    	return new FilterField(false);
    }

    public static FilterField newGroupFilterFields() {
    	return new FilterField(true);
    }

//    public FilterField() {
//		super();
//		fieldsUsed = STANDARD_FIELDS;
//	}



    public FilterField(boolean useGroup) {
		super();
		if (useGroup) {
			fieldsUsed = GROUPING_FIELDS;
		} else {
			fieldsUsed = STANDARD_FIELDS;
		}
	}



	/**
     * Set field by field Index
     *
     * @param fieldIndex field number to be updated
     * @param newValue new value for the field
     */
    @SuppressWarnings("rawtypes")
	public void setField(int fieldIndex, Object newValue) {

    	if (newValue instanceof ComboStdOption) {
    		newValue = ((ComboStdOption) newValue).key;
    	}
    	if (fieldIndex >= 0 && fieldIndex < fieldsUsed.length) {
	    	int idx = fieldsUsed[fieldIndex];
	        switch (idx) {
				case FLD_OR_VAL: 		 booleanOperator = (searchArray(newValue, OR_LIST));	break;
				case FLD_AND_VAL: 		 booleanOperator = (searchArray(newValue, AND_LIST));	break;
	        	case FLD_FIELD_NUMBER:	 fieldNumber = asInt(newValue) - 1;						break;
	            case FLD_CASE_SENSITIVE: ignoreCase = (Boolean) newValue;						break;
	            case FLD_GROUPING:		 grouping = asInt(newValue);							break;
	            case FLD_GROUP_OPERATOR:
	                operator = Compare.getForeignOperator(newValue.toString(), Compare.OP_CONTAINS);
	    	    break;
	            case FLD_GROUP_VALUE:
	                value = newValue.toString();
	            break;
	            default:
	        }
    	}
    }


    private int asInt(Object newValue) {
    	if (newValue instanceof Integer) {
    		return (Integer) newValue;
    	} else {
    		return Integer.parseInt(newValue.toString());
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

      	if (fieldIndex >= 0 && fieldIndex < fieldsUsed.length) {
	    	int idx = fieldsUsed[fieldIndex];

	        switch (idx) {
        	case FLD_OR_VAL: 			return OR_LIST[booleanOperator];
        	case FLD_AND_VAL: 			return AND_LIST[booleanOperator];
        	case FLD_FIELD_NUMBER: 		return Integer.valueOf(fieldNumber + 1);
            case FLD_CASE_SENSITIVE:	return ignoreCase;
            case FLD_GROUPING:		 	return Integer.valueOf(grouping);
            case FLD_GROUP_OPERATOR:	return Compare.OPERATOR_STRING_FOREIGN_VALUES[operator];
            case FLD_GROUP_VALUE:		return value;
            default:
	        }
        }
      	return null;
   }


	private int searchArray(Object val, String[] array) {
		if (val instanceof Number) {
			return ((Number) val).intValue();
		}

		String v = null;
		if (val != null) {
			v = val.toString();
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equalsIgnoreCase(v)) {
				return i;
			}
		}
		return 0;
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
     * Get field number
     *
     * @return field number
     */
    public int getRecordNumber() {
        return (fieldNumber - 1) / Compare.RECORD_MULTIPLE;
    }
    /**
     * Get field number
     *
     * @return field number
     */
    public int getRecFieldNumber() {
        return (fieldNumber - 1) % Compare.RECORD_MULTIPLE;
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
     * set the field number
     *
     * @param val new field number
     */
    public void setFieldNumber(int recNo, int fldNo) {
        this.fieldNumber = Compare.RECORD_MULTIPLE * recNo + fldNo + 1;
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



	/**
	 * @return the booleanOperator
	 */
	public int getBooleanOperator() {
		return booleanOperator;
	}



	/**
	 * @param booleanOperator the booleanOperator to set
	 */
	public void setBooleanOperator(int booleanOperator) {
		this.booleanOperator = booleanOperator;
	}



	/**
	 * @return the grouping
	 */
	public int getGrouping() {
		return grouping;
	}



	/**
	 * @param grouping the grouping to set
	 */
	public void setGrouping(int grouping) {
		this.grouping = grouping;
	}
}
