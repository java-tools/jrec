package net.sf.JRecord.Details;

import java.util.List;

import net.sf.JRecord.Common.FieldDetail;

public interface AbstractTreeDetails<
				FieldDtls extends FieldDetail,
				RecordDtls extends AbstractRecordDetail,
				Layout extends AbstractLayoutDetails,
				LineType extends AbstractLine> {
//				TreeNode> {

	/**
	 * Get the number of Child Record Types
	 * @return Number of Different types of child records
	 */
	public abstract int getChildCount();

	/**
	 * Get the name of Children
	 * @param idx child index
	 * @return name
	 */
	public abstract String getChildName(int idx);

	/**
	 * Wether lines are present for a particular child group
	 * @param idx child group index
	 * @return Weather lines are present for a particular child group
	 */
	public abstract boolean hasLines(int idx);

	/**
	 * Get Child list
	 * @param idx
	 * @return requested list
	 */
	public abstract List<LineType> getLines(int idx);

	/**
	 * Get Child list via Name
	 * @param name Child name
	 * @return requested name
	 */
	public abstract List<LineType> getLines(String name);

	/**
	 * Get Child Definition
	 * @return Child Definition
	 */
	public abstract AbstractChildDetails<RecordDtls> getChildDetails(int idx);

	/**
	 * add a line at a location
	 * @param newLine line to add
	 * @param location location of the new line (< 0 add at the end)
	 */
	public  abstract void addChild(LineType newLine, int location);

	/**
	 * Create and Add a child line (based on the child definition
	 * @param childDef definition of the child to be created
	 * @param location location in list (repeated fields)
	 *
	 * @return line that was created
	 */
	public <childDtls extends AbstractChildDetails<RecordDtls>> LineType addChild(childDtls childDef, int location);


    /**
     * Remove a child line
     * @param child line to be removed
     */
    public abstract void removeChild(AbstractLine child);

//    /**
//     * Get the tree node  (typically DefaultMutableTreeNode)
//     * @return tree node  (typically DefaultMutableTreeNode)
//     */
//    public TreeNode  getTreeNode();
//
//    /**
//     * Define the tree node (typically DefaultMutableTreeNode)
//     * @param node node to set
//     */
//    public void  setTreeNode(TreeNode node);

    /**
     * Get Child Definition
     * @return Child Definition
     */
	public <ChildDtls extends AbstractChildDetails<RecordDtls>> ChildDtls getChildDefinitionInParent();


    /**
     * Get parent line
     * @return parent line
     */
    public LineType getParentLine();

    /**
     * Set the parent line
     * @param line parent index
     * @param index line-index in the parent's array of lines for this child type
     */
    public void setParentLine(LineType line, int index);

    /**
     * Get Tree root
     * @return  Tree root
     */
    public AbstractLine getRootLine();


	/**
	 * @return the parentIndex
	 */
	public int getParentIndex();

	/**
	 * Set the parent index
	 * @param index
	 */
	public void setParentIndex(int index);

	/**
	 * Get The valid child Lines for the current line
	 * @return list of valid child details
	 */
	public List<? extends AbstractChildDetails<? extends AbstractRecordDetail>> getInsertRecordOptions();

	/**
	 * Remove children for a child Definition
	 * @param childDef  Child Definition
	 */
	public  boolean removeChildren(AbstractChildDetails childDef);


}