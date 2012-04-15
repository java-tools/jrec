package net.sf.RecordEditor.re.file;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;

public interface AbstractLineNode extends MutableTreeNode {

	
	/**
	 * insert a child 
	 * @param child child to insert
	 */
	public void add(MutableTreeNode child);
	
	/**
	 * Get the record definition
	 * @return record definition
	 */
	public abstract AbstractLayoutDetails getLayout();

	/**
	 * Get the record (line)
	 * @return record (line)
	 */
	public abstract AbstractLine getLine();

	/**
	 * Get the view being displayed
	 * @return view being displayed
	 */
	public abstract FileView getView();

	/**
	 * Set Summary Line
	 * @param summaryLine new summary line
	 * @param fieldName
	 */
	public abstract void setSummaryLine(AbstractLine summaryLine,
			String fieldName);

	/**
	 * Get the type of line being displayed
	 * @return
	 */
	public abstract String getLineType();

	/**
	 * Get the path to the current node
	 * @return
	 */
	public abstract TreeNode[] 	getPath();
	
	/**
	 * get the default line number
	 * @return default line number
	 */
	public int getDefaultLineNumber();
	
	/**
	 * Get the line number 
	 * @return line number 
	 */
	public int getLineNumber();
	
	/**
	 * 
	 * @return SortValue
	 */
	public abstract String getSortValue();
	
	/**
	 * Remove line from parent
	 */
	public void removeFromParent();
	
	/**
	 * add a line as a child node to this node
	 * @param line line to insert
	 * @param lineNumber line number of the line
	 * @param pos position to insert at (negative = at the end)
	 */
	public <X extends AbstractLineNode> X insert(AbstractLine line, int lineNumber, int pos);

	/**
	 * remove all children from a node
	 */
	public void removeAllChildren();

	
	/**
	 * Create a new node at the specified location and return to the calling program
	 * 
	 * @param location to insert the new node
	 * @param nodeName node name
	 * @param fileView file it pertains to
	 * @param theLine relevant line in the file
	 * @return
	 */
	public AbstractLineNode insertNode(
			int location, String nodeName,
			FileView fileView, AbstractLine theLine);
}