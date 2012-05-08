package net.sf.RecordEditor.layoutEd.layout.tree;

import java.util.ArrayList;

import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.ReConnection;

@SuppressWarnings("serial")
public class RecordNode extends BaseNode {
	private ChildRecordsRec childDef;
	private final int dbIdx, recordId;

	private final ArrayList<RecordSelectionRec> selections = new ArrayList<RecordSelectionRec>();

	public RecordNode(int dbIdx, int recordId, String nodeName, ChildRecordsRec childDef) {
		super(nodeName);
		this.recordId = recordId;
		this.dbIdx = dbIdx;
		this.childDef = childDef;

		load();
	}

	public final void reload() {
		super.removeAllChildren();
		load();
	}

	private void load() {
		int idx=0;
		RecordSelectionRec selectionRec;
		RecordSelectionDB db = new RecordSelectionDB();
		db.setConnection(ReConnection.getConnection(dbIdx));
		db.setParams(recordId, childDef.getChildKey());
		db.resetSearch();
		db.open();

		while ((selectionRec = db.fetch()) != null) {
			super.add(new SelectionTestNode(idx++, selectionRec));
			selections.add(selectionRec);
		}
		db.close();
	}



	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		//System.out.println("isLeaf: " + this.toString() + " " + super.isLeaf() + " " + super.getChildCount());
		return super.isLeaf();
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#setField(int, java.lang.Object)
	 */
	@Override
	public void setField(int fldNum, Object val) {
		String value = "";
		if (val != null) {
			value = val.toString();
		}

		switch (fldNum) {
		case COLUMN_FIELD_NAME:  childDef.setField(value);		break;
		case COLUMN_FIELD_VALUE: childDef.setFieldValue(value);	break;
		}

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#getTestFieldValue()
	 */
	@Override
	public String getTestFieldValue() {
		return childDef.getFieldValue();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#getOperator()
	 */
	@Override
	public String getOperator() {
		return "=";
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#getTestFieldName()
	 */
	@Override
	public String getTestFieldName() {
		return childDef.getField();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#getBooleanOp(int)
	 */
	@Override
	public String getBooleanOp(int idx) {
		return "";
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.layoutEd.layout.tree.IntSelectionTest#isUpdateAble(int)
	 */
	@Override
	public boolean isUpdateAble(int idx) {
		return idx == COLUMN_FIELD_NAME
			|| idx == COLUMN_FIELD_VALUE;
	}

	public void update() {
		RecordSelectionDB db = new RecordSelectionDB();
		db.setConnection(ReConnection.getConnection(dbIdx));
		db.setParams(recordId, childDef.getChildKey());

		System.out.println("~~ " + recordId + " " + childDef.getChildKey()
				+ " " + selections.size());
		for (RecordSelectionRec selRec : selections) {
			if (selRec.getUpdateStatus() == RecordSelectionRec.UPDATED) {

				System.out.println("---> " + selRec.getChildKey() + " " +  selRec.getFieldNo()
						+ " " + selRec.getFieldName()  + " " + selRec.getFieldValue());
				db.update(selRec);
			}
		}
	}



	/**
	 * @return the childDef
	 */
	public ChildRecordsRec getChildDef() {
		return childDef;
	}
}
