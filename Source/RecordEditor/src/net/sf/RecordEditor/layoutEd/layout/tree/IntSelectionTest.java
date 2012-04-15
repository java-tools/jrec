package net.sf.RecordEditor.layoutEd.layout.tree;

public interface IntSelectionTest {

	public final static int COLUMN_FIELD_NAME  = 3;
	public final static int COLUMN_OPERATOR    = 4;
	public final static int COLUMN_FIELD_VALUE = 5;
	
	/**
	 * @param fldNum field number
	 * @param val new value of the field number
	 */
	public abstract void setField(int fldNum, Object val);

	/**
	 * @return Value to test the field against
	 */
	public abstract String getTestFieldValue();

	/**
	 * @return comparison operator
	 */
	public abstract String getOperator();

	/**
	 * @return field to be tested
	 */
	public abstract String getTestFieldName();

	/**
	 * 
	 * @param idx return the boolean operator (there are 3 levels)
	 */
	public abstract String getBooleanOp(int idx);

	/**
	 * Get Field by index
	 * @param idx
	 * @return
	 */
	public abstract Object getField(int idx);

	/**
	 * Wether a field can be updated at a level
	 * @param idx
	 * @return
	 */
	public abstract boolean isUpdateAble(int idx);
}