package net.sf.RecordEditor.utils.swing.treeTable;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.MutableTreeNode;


public interface TreeTableNotify {

	public abstract Object getRoot();
	
	public abstract void addTreeModelListener(TreeModelListener l);

	public abstract void removeTreeModelListener(TreeModelListener l);

	/*
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	public abstract void fireTreeNodesChanged(Object source, Object[] path,
			int[] childIndices, Object[] children);

	/*
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	public abstract void fireTreeNodesInserted(Object source, Object[] path,
			int[] childIndices, Object[] children);

	/*
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	public abstract void fireTreeNodesRemoved(Object source, Object[] path,
			int[] childIndices, Object[] children);

	/*
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	public abstract void fireTreeStructureChanged(Object source, Object[] path,
			int[] childIndices, Object[] children);

	/**
	 * Fire nodes inserted for one nore
	 * @param node node tha6t was inserted
	 */
	public void fireTreeNodesInserted(MutableTreeNode node);

	/**
	 * Fire nodes deleted for one node
	 * @param node node that was deleted
	 */
	public void fireTreeNodesRemoved(MutableTreeNode node);

	//
	// Default impelmentations for methods in the TreeTableModel interface.
	//
}