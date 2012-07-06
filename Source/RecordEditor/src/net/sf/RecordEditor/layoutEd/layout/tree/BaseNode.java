package net.sf.RecordEditor.layoutEd.layout.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public abstract class BaseNode extends DefaultMutableTreeNode
implements IntSelectionTestNode {


	public BaseNode(Object userObject) {
		super(userObject);
	}

	private static final String[] colNames = LangConversion.convertColHeading("LayoutEdit_RecSel", new String[]{
		"Boolean op 1", "Boolean op 2", "Boolean op 3", "Field Name", "Operator", "Test Value"
	});

	public static String getColumnName(int idx) {
		return colNames[idx];
	}

	public static int getColumnCount() {
		return colNames.length;
	}

	/**
	 * Get Field at Index
	 * @param idx index to get field
	 * @return value
	 */
	@Override
	public Object getField(int idx) {
		Object ret;
		switch (idx) {
		case COLUMN_FIELD_NAME:		ret = getTestFieldName();		break;
		case COLUMN_OPERATOR:		ret = getOperator();			break;
		case COLUMN_FIELD_VALUE:	ret = getTestFieldValue();		break;
		default:					ret = getBooleanOp(idx) + "  ";

		}

		return ret;
	}
}
