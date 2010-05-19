package net.sf.RecordEditor.edit.tree;

import javax.swing.JOptionPane;
import javax.swing.tree.MutableTreeNode;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.edit.file.AbstractChangeNotify;
import net.sf.RecordEditor.edit.file.AbstractLineNode;
import net.sf.RecordEditor.utils.swing.treeTable.AbstractTreeTableModel;
import net.sf.RecordEditor.utils.swing.treeTable.TreeTableModel;
import net.sf.RecordEditor.utils.swing.treeTable.TreeTableNotify;

public class LineTreeTabelModel extends AbstractTreeTableModel 
implements TreeTableNotify {
    /** Node currently being reloaded, this becomes somewhat muddy if
     * reloading is happening in multiple threads. */

	private static final  int MAP_SKIP_COLUMNS = 3;
	private static final  int MAP_SKIP_COLUMN = MAP_SKIP_COLUMNS - 1;
	private int defaultPreferedIndex = 0;
    private int maxNumColumns = -1;
	private int columnShift;
    private int treeColumn  = 1;
    //private int tableSkipColumns;
    private final int skipColumns;
    private int recordIndex = 0;
    
    private AbstractLayoutDetails<?, ?> layout;
    private AbstractChangeNotify notify;



    /**
     * Creates a FileSystemModel2 with the root being <code>rootPath</code>.
     * This does not load it, you should invoke
     * <code>reloadChildren</code> with the root to start loading.
     *
     * @param rootNode rote node to use as the base for the tree table
     */
    public LineTreeTabelModel(
    		final AbstractChangeNotify changeNotify, final AbstractLineNode rootNode, 
    		final int columnsToSkip, boolean showKey) {
        super(null);
        root   = rootNode;
        columnShift = columnsToSkip;
        notify = changeNotify;
            
        layout = ((AbstractLineNode) root).getLayout();
        
         if (showKey) {
        	skipColumns = MAP_SKIP_COLUMNS;
        } else {
        	skipColumns = 2;
        }
    }

    //
    // The TreeModel interface
    //

    /**
     * Returns the number of children of <code>node</code>.
     *
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object node) {
        return ((AbstractLineNode) node).getChildCount();
    }

    /**
     * Returns the child of <code>node</code> at index <code>i</code>.
     *
     * @see  javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object node, int i) {
        return ((AbstractLineNode) node).getChildAt(i);
    }

    /**
     * Returns true if the passed in object represents a leaf, false
     * otherwise.
     *
     * @param node to test if it is a leaf node
     *
     * @return weather it is a leaf node
     */
    public boolean isLeaf(Object node) {
        //System.out.println(" is Leaf " + ((AbstractLineNode)node).isLeaf());
        return ((AbstractLineNode) node).isLeaf();
    }

    //
    //  The TreeTableNode interface.
    //

    /**
     * Returns the number of columns.
     *
     * @return Column Count
     *
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
//    	System.out.println(" -- Column Count --> " + recordIndex + " == " + layout.getRecordCount()
//    			+ " " + maxNumColumns + " " + skipColumns + " - " + columnShift);
    	if (recordIndex == layout.getRecordCount()) {
           	if (maxNumColumns < 0) {
        		for (int i = 0; i <layout.getRecordCount(); i++) {
        			maxNumColumns = Math.max(maxNumColumns, layout.getRecord(i).getFieldCount());
        		}
        	}
        	return maxNumColumns + skipColumns - columnShift;
    	}
        return getRecord().getFieldCount() + skipColumns - columnShift;
    }

    /**
     * Returns the name for a particular column.
     *
     * @param column column to get the name of
     *
     * @return requested column name
     *
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
    	if (column == treeColumn) {
    		return "Tree";
    	} else if (skipColumns == MAP_SKIP_COLUMNS && column == MAP_SKIP_COLUMN) {
    		return "Map Key";
    	} else if (column < skipColumns) {
   		return " ";
    	}
    	int col = adjustColumn(null, column);
    	AbstractRecordDetail<?> rec = getRecord();
    	if (col >= rec.getFieldCount()) {
    		return " ";
    	}
        return rec.getField(col).getName();
    }

    /**
     * Returns the class for the particular column.
     *
     * @param column column to get the name of
     *
     * @return Columns class
     *
     *  @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class<?> getColumnClass(int column) {

        int col = adjustColumn(null, column);
        if (column == treeColumn) {           
            return TreeTableModel.class;
        }
        return super.getColumnClass(col);
    }

    /**
     * Gets the value of a certain column
     *
     * @param node Tree node to get the value of
     * @param column column that value is required
     *
     * @return the value of the particular column.
     *
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(Object node, int column) {
	AbstractLine<?> rec = ((AbstractLineNode) node).getLine();

		int col;
		if (rec == null || column < MAP_SKIP_COLUMN) {
		    return null;
		} else if (skipColumns == MAP_SKIP_COLUMNS && column == MAP_SKIP_COLUMN) {
			col = Constants.KEY_INDEX;
		} else {	
			col = adjustColumn(rec, column);
		}

		try {
			return rec.getField(getRecordIndex(rec), col);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }


    /**
	 * @see net.sf.RecordEditor.utils.swing.treeTable.AbstractTreeTableModel#isCellEditable(java.lang.Object, int)
	 */
	@Override
	public boolean isCellEditable(Object node, int column) {
		return true; //column > 0 || ((AbstractLineNode) node).getLineNumber() >= 0;
	}

	/**
	 * @see net.sf.RecordEditor.utils.swing.treeTable.AbstractTreeTableModel#setValueAt(java.lang.Object, java.lang.Object, int)
	 */
	@Override
	public void setValueAt(Object newValue, Object node, int column) {
		AbstractLineNode lNode = (AbstractLineNode) node;
		AbstractLine<?> rec = lNode.getLine();
		int recordIdx = getRecordIndex(rec);
		int col;
		
		if (rec == null || column < MAP_SKIP_COLUMN) {
		    return;
		} else if (skipColumns == MAP_SKIP_COLUMNS && column == MAP_SKIP_COLUMN) {
			col = Constants.KEY_INDEX;
		} else {	
			col = adjustColumn(rec, column);
		}
		Object oldValue = rec.getField(recordIdx,  col);
		
		if (! ((newValue == null && oldValue == null)
			|| newValue.equals(oldValue))) {
//			System.out.println("## " + lNode.getLineNumber() + " " + column 
//					+ " " + adjustColumn(column) + " >" + newValue);
	
			//lNode.getView().setValueAt(null, recordIndex, lNode.getLineNumber(), adjustColumn(column), aValue);
			
			String eMsg = doFieldUpdate(lNode, rec, recordIdx, col, oldValue, newValue);
			while (eMsg != null) {
		        newValue =  JOptionPane.showInputDialog(null,
		                eMsg,
		                "Conversion Error",
		                JOptionPane.ERROR_MESSAGE,
		                null, null, newValue);

		        eMsg = null;
		        if (newValue != null) {
		        	eMsg = doFieldUpdate(lNode, rec, recordIdx, col, oldValue, newValue);
		        }
			}
		}
	}
	
	private String doFieldUpdate(
			AbstractLineNode lNode, AbstractLine<?> rec, 
			int recordIdx, int col, Object oldValue, Object newValue) {
		String ret = null;
		try {
			rec.setField(recordIdx,  col, newValue);
			if (oldValue == null || ! oldValue.equals(rec.getField(recordIdx, col))) {
				notify.setChanged(true);
			}
			lNode.getView().fireRowUpdated(lNode.getLineNumber(), rec);			
		} catch (Exception e) {
			ret = e.getMessage();
		}
		return ret;
	}

	/**
     * adjusts the column, it allows subclasses to change the order
     * of fields on the screen
     *
     * @param column input column
     * @return adjusted column
     */
    private int adjustColumn(AbstractLine<?> rec, int column) {
        return layout.getAdjFieldNumber(getRecordIndex(rec), column - skipColumns + columnShift);
    }


    /**
     * Set the Tree Column
     *
     * @param newTreeColumn The treeColumn to set.
     */
    public void setTreeColumn(int newTreeColumn) {
        this.treeColumn = newTreeColumn;
    }
    
    /**
     * Get the current record
     * @return requested record
     */
    private AbstractRecordDetail<?> getRecord() {
    	return layout.getRecord(getRecordIndex(null));
    }

    /**
     * Get the current record
     * @return current record id
     */
	public int getRecordIndex() {
		return recordIndex;
	}


    /**
     * Get the current record
     * @return current record id
     */
	private int getRecordIndex(AbstractLine<?> rec) {
		if (recordIndex < layout.getRecordCount()) {
			return recordIndex;
		} else if (rec == null) {
			return defaultPreferedIndex;
		}
		return rec.getPreferredLayoutIdx();
	}


	/**
	 * Set the current record index
	 * @param recordIndex
	 */
	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}
	
	/**
	 * @return the defaultPreferedIndex
	 */
	public final int getDefaultPreferredIndex() {
		return defaultPreferedIndex;
	}

	/**
	 * @param defaultPreferedIndex the defaultPreferedIndex to set
	 */
	public final void setDefaultPreferredIndex(int defaultPreferedIndex) {
		this.defaultPreferedIndex = defaultPreferedIndex;
	}

	/**
	 * Fire nodes inserted for one nore
	 * @param node node tha6t was inserted
	 */
	public void fireTreeNodesInserted(MutableTreeNode node) {
		AbstractLineNode parentNode = (AbstractLineNode) node.getParent();
		if (parentNode != null) {
			int[] ic = {parentNode.getIndex(node)};
			Object[] c = {node};
			fireTreeNodesInserted(parentNode, parentNode.getPath(), ic, c);
		}
	}
	
	/**
	 * Fire nodes deleted for one node
	 * @param node node that was deleted
	 */
	public void fireTreeNodesRemoved(MutableTreeNode node) {
		AbstractLineNode parentNode = (AbstractLineNode) node.getParent();
		if (parentNode != null) {
			int[] ic = {parentNode.getIndex(node)};
			Object[] c = {node};
			fireTreeNodesRemoved(parentNode, parentNode.getPath(), ic, c);
		}
	}

}
