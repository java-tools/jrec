package net.sf.RecordEditor.layoutEd.layout.tree;


import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;

@SuppressWarnings("serial")
public class SelectionTestNode extends BaseNode {

	private final RecordSelectionRec selection;
	private final int index;
	public SelectionTestNode(int index, RecordSelectionRec selection) {
		super("Check");
		
		this.index = index;
		this.selection = selection;
	}
	
	/**
	 * 
	 * @param idx return the boolean operator (there are 3 levels)
	 * @return boolean operator
	 */
	@Override
	public String getBooleanOp(int idx) {
		String s = "";
		if (index == 0) {
			if (idx == 0) {
				s = "And";
			}
		} else if (idx > 0) {
			s = selection.getField(idx - 1).toString();
		}
		return s;
	}
	
	
	/**
	 * @return field to be tested
	 * @see net.sf.RecordEditor.re.db.Record.RecordSelectionRec#getFieldName()
	 */
	@Override
	public String getTestFieldName() {
		return selection.getFieldName();
	}

	/**
	 * @return comparison operator
	 * @see net.sf.RecordEditor.re.db.Record.RecordSelectionRec#getOperator()
	 */
	@Override
	public String getOperator() {
		return selection.getOperator();
	}

	/**
	 * @return Value to test the field against
	 * @see net.sf.RecordEditor.re.db.Record.RecordSelectionRec#getFieldValue()
	 */
	@Override
	public String getTestFieldValue() {
		return selection.getFieldValue();
	}

	/**
	 * @param fldNum field number
	 * @param val new value of the field number
	 * @see net.sf.RecordEditor.utils.jdbc.AbsRecord#setField(int, java.lang.Object)
	 */
	@Override
	public void setField(int fldNum, Object val) {
		selection.setField(fldNum, val);
	}
	

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#isUpdateAble(int)
	 */
	@Override
	public boolean isUpdateAble(int idx) {
		// TODO Auto-generated method stub
		return idx >= COLUMN_FIELD_NAME;
	}
}
