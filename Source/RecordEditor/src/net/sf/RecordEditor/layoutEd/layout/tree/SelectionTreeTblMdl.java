package net.sf.RecordEditor.layoutEd.layout.tree;

import javax.swing.table.AbstractTableModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.treeTable.AbstractTreeTableModel;
import net.sf.RecordEditor.utils.swing.treeTable.TreeTableModel;

public class SelectionTreeTblMdl extends AbstractTreeTableModel {

	public static final int SPECIAL_COLUMNS = 2;
	private static final int colCount = BaseNode.getColumnCount() + SPECIAL_COLUMNS;
	private static final int treeColumn = SPECIAL_COLUMNS - 1;
	private final AbstractTableModel tblMdl;

	public SelectionTreeTblMdl(Object root, AbstractTableModel baseModel) {
		super(root);
		tblMdl = baseModel;
	}

	@Override
	public int getColumnCount() {
		return colCount;
	}

	@Override
	public String getColumnName(int column) {

		if (column < SPECIAL_COLUMNS) {
			if (column == treeColumn) {
				return "Record Name       ";
			}
			if (Common.TEST_MODE) {
				return "s";
			}
			return "";
		}
		return BaseNode.getColumnName(column - SPECIAL_COLUMNS);
	}

	@Override
	public Object getValueAt(Object node, int column) {
		Object ret = null;

		if (column >= SPECIAL_COLUMNS && node instanceof  IntSelectionTest) {
			ret = ((IntSelectionTest) node).getField(column - SPECIAL_COLUMNS);
		}
		return ret;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeTable.AbstractTreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public void setValueAt(Object aValue, Object node, int column) {
		if (column >= SPECIAL_COLUMNS && node instanceof  IntSelectionTest) {
			((IntSelectionTest) node).setField(column - SPECIAL_COLUMNS, aValue);
		}

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.treeTable.AbstractTreeTableModel#isCellEditable(java.lang.Object, int)
	 */
	@Override
	public boolean isCellEditable(Object node, int column) {
		boolean ret = column == treeColumn;
		if (column >= SPECIAL_COLUMNS && node instanceof  IntSelectionTest) {
			ret = ((IntSelectionTest) node).isUpdateAble(column - SPECIAL_COLUMNS);

			if (node instanceof RecordNode) {
				tblMdl.fireTableDataChanged();
			}
		}
		return ret;
	}

    public Class<?> getColumnClass(int column) {


        if (column == treeColumn) {
            return TreeTableModel.class;
        }
        return super.getColumnClass(column);
    }


    public void load() {
    	if (root instanceof MutableTreeNode) {
    		TreeNode t;

    		RecordNode rn;
    		MutableTreeNode n = (MutableTreeNode) root;
    		TreeNode[] children = new TreeNode[n.getChildCount()];
    		int[] childIdx = new int[children.length];
    		for (int i = 0; i < n.getChildCount(); i++) {
    			t = n.getChildAt(i);
    			childIdx[i] = i;
    			children[i] = t;
    			if (t instanceof RecordNode) {
    				rn = ((RecordNode) t);
    				if (rn.getChildCount() > 0) {
    					fireTreeNodesRemoved(rn, rn.getPath(), getChildrenIdx(rn), getChildren(rn));
    				}
    				rn.reload();
    				if (rn.getChildCount() > 0) {
    					fireTreeNodesInserted(rn, rn.getPath(), getChildrenIdx(rn), getChildren(rn));
    				}
    			}
    		}



    		tblMdl.fireTableDataChanged();
    	}
    }

    private TreeNode[] getChildren(RecordNode rn) {
    	TreeNode[] ret = new TreeNode[rn.getChildCount()];

    	for (int i = 0; i < ret.length; i++) {
    		ret[i] = rn.getChildAt(i);
    	}

    	return ret;
    }

    private int[] getChildrenIdx(RecordNode rn) {
    	int[] ret = new int[rn.getChildCount()];
    	for (int i = 0; i < ret.length; i++) {
    		ret[i] = i;
    	}
    	return ret;
    }
}
