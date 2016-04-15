package net.sf.JRecord.Details;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.FieldDetail;


public class TreeDetails<	FieldDtls extends FieldDetail,
							RecordDtls extends AbstractRecordDetail,
							Layout extends AbstractLayoutDetails,
							ChildDtls extends AbstractChildDetails<RecordDtls>,
                            LineType extends AbstractLine
                                     >
implements AbstractTreeDetails<FieldDtls, RecordDtls, Layout, LineType> {

	protected ArrayList<List<LineType>> list = new ArrayList<List<LineType>>();
	//private ArrayList<String> names = new ArrayList<String>();
	protected  ArrayList<ChildDtls> childDescription
		= new ArrayList<ChildDtls>();
	protected boolean convertNullToArrayList = true;
	protected LineType parentLine = null;
	public LineType line;
	private int parentIndex = -1;
	protected ChildDtls definitionInParent = null;


    /**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#getChildCount()
	 */
    public int getChildCount() {
    	return childDescription.size();
    }

    /**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#getChildName(int)
	 */
    public String getChildName(int idx) {
    	return childDescription.get(idx).getName();
    }

    /**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#hasLines(int)
	 */
	@Override
	public boolean hasLines(int idx) {
		return list.get(idx) != null && list.get(idx).size() > 0;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#getLines(int)
	 */
    public List<LineType> getLines(int idx) {
    	return list.get(idx);
    }

    /**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#getLines(java.lang.String)
	 */
    public List<LineType> getLines(String name) {
    	List<LineType> ret = null;

    	for (int i = 0; i < childDescription.size(); i++) {
    		if (name.equalsIgnoreCase(getChildName(i))) {
    			ret = getLines(i);
    			break;
    		}
    	}

    	return ret;
    }

    /**
     * Add Child details
     * @param childDtls child description
     * @param lines lines to add
     */
    protected void addChildDefinition(ChildDtls childDtls, List<LineType> lines) {
    	childDescription.add(childDtls);

    	if (convertNullToArrayList && lines == null) {
    		lines = new ArrayList<LineType>();
    	}
    	list.add(lines);
    }

	/**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#getChildDetails(int)
	 */
	public ChildDtls getChildDetails(int idx) {
		return childDescription.get(idx);
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#addChild(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void addChild(LineType newLine, int location) {
		List<LineType> lines = getLines(newLine.getTreeDetails().getChildDefinitionInParent().getChildIndex());
		LineType l = newLine;
		if (location < 0) {
			lines.add(l);
		} else {
			lines.add(location, l);
		}
	}



	@Override
	public <childDtls extends AbstractChildDetails<RecordDtls>> LineType addChild(childDtls childDef, int location) {
		return null;
	}


	/**
	 * @return the parentLine
	 */
	public final LineType getParentLine() {
		return parentLine;
	}


	/**
	 * @param parentLine the parentLine to set
	 */
	public final void setParentLine(LineType parentLine, int index) {
		this.parentLine = parentLine;
		this.parentIndex = index;
	}


//
//	/**
//	 * @see net.sf.JRecord.Details.AbstractLine#getTreeNode()
//	 */
//	@Override
//	public TreeNode getTreeNode() {
//		return treeNode;
//	}
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Common.AbstractTreeDetails#setTreeNode(java.lang.Object)
//	 */
//	@Override
//	public  void setTreeNode(TreeNode node) {
//		treeNode = node;
//	}

	/**
	 * @see net.sf.JRecord.Details.AbstractLine#getRootLine()
	 */
	@Override
	public final LineType getRootLine() {
		LineType root = line;
		while (root.getTreeDetails().getParentLine() != null) {
			root = (LineType) root.getTreeDetails().getParentLine();
		}
		return root;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractTreeDetails#getChildDefinitionInParent()
	 */
	@Override
	public ChildDtls getChildDefinitionInParent() {
		return definitionInParent;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractTreeDetails#removeChild(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void removeChild(AbstractLine child) {

	}

	@Override
	public boolean removeChildren(AbstractChildDetails childDef) {

		list.set(childDef.getChildIndex(), null);
		return true;
	}

//	/**
//	 * @return the definitionInParent
//	 */
//	public final ChildDtls getDefinitionInParent() {
//		return definitionInParent;
//	}

	/**
	 * @return the parentIndex
	 */
	public final int getParentIndex() {
		return parentIndex;
	}

	/**
	 * @param parentIndex the parentIndex to set
	 */
	public final void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	/**
	 * Get The valid child Lines for the current line
	 * @return list of valid child details
	 */
	public List<ChildDtls> getInsertRecordOptions() {
		ArrayList<ChildDtls> ret = new ArrayList<ChildDtls>();

		for (int i =0; i < childDescription.size(); i++) {
			if ( ! hasLines(i) || childDescription.get(i).isRepeated()) {
				ret.add(childDescription.get(i));
			}
		}

		return ret;
	}

}
