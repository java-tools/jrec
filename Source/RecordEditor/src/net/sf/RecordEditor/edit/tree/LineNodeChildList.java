/**
 * 
 */
package net.sf.RecordEditor.edit.tree;


import java.util.List;
import java.util.Vector;

import javax.swing.tree.MutableTreeNode;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.edit.file.AbstractLineNode;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode;


/**
 * @author bm
 *
 */
public class LineNodeChildList 
extends BmDefaultMutableTreeNode 
implements AbstractLineNode {

	
	public final String nodeName;
	private FileView view;
	private static final AbstractLine line = null;
	
	private boolean toBuild = true;
	
	private AbstractTreeDetails childLineDtls;
	private int childIdx;

	/**
	 * create tree node based on File View
	 *
	 * @param pNodeName name of the root node
	 * @param fileView File View (or storage)
	 * @param childLineDetails child details
	 * @param child index
	 */
	public LineNodeChildList(final String pNodeName,
					final FileView fileView,
					final AbstractTreeDetails childLineDetails,
					final int childIndex) {
		super(pNodeName);
		
		nodeName = pNodeName;
		view = fileView;
		childLineDtls = childLineDetails;
		childIdx =  childIndex;
	}

	/**
	 * @see net.sf.RecordEditor.edit.file.AbstractLineNode#getLayout()
	 */
	@Override
	public AbstractLayoutDetails getLayout() {
		return view.getLayout();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getLine()
	 */
	@Override
	public AbstractLine getLine() {
		return line;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getLineType()
	 */
	@Override
	public String getLineType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getView()
	 */
	@Override
	public FileView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#setSummaryLine(net.sf.JRecord.Details.AbstractLine, java.lang.String)
	 */
	@Override
	public void setSummaryLine(AbstractLine summaryLine, String fieldName) {

	}

	
	
	/**
	 * @see net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		if (toBuild) {
			return ! childLineDtls.hasLines(childIdx);
		}
		return super.isLeaf();
	}

	@Override
	protected Vector<MutableTreeNode> getChildren() {
		if (toBuild) {
			toBuild = false;
			List<AbstractLine> lines = childLineDtls.getLines(childIdx);
			int idx;
			String name;
			
			for (AbstractLine theLine : lines) {
				name = nodeName;
				idx = theLine.getPreferredLayoutIdx();
				if (idx >= 0) {
					name = getLayout().getRecord(idx).getRecordName();
				}
				//System.out.println(" >>> ### 4 add " + theLine.getFullLine());
				add(new LineNodeChild(name, view, theLine));
			}
			childLineDtls = null;
		}
		return super.getChildren();
	}
	

	/**
	 * @see net.sf.RecordEditor.edit.file.AbstractLineNode#getDefaultLineNumber()
	 */
	@Override
	public int getDefaultLineNumber() {
		// TODO Auto-generated method stub
		return getLineNumber();
	}

	/**
	 * @see net.sf.RecordEditor.edit.file.AbstractLineNode#getLineNumber()
	 */
	@Override
	public int getLineNumber() {
	    if (line != null) {
	    	return view.indexOf(line);
	    }
		return -1;
	}

	/**
	 * @see net.sf.RecordEditor.edit.file.AbstractLineNode#getSortValue()
	 */
	@Override
	public String getSortValue() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#insert(net.sf.JRecord.Details.AbstractLine, int, int)
	 */
	@Override
	public AbstractLineNode insert(AbstractLine line, int lineNumber, int pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode#remove(int)
	 */
	@Override
	public void remove(int index) {
		super.remove(index);
		if (getChildCount() == 0) {
			removeFromParent();
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode#remove(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void remove(MutableTreeNode node) {
		super.remove(node);
		if (getChildCount() == 0) {
			view.getTreeTableNotify().fireTreeNodesRemoved(this);
			removeFromParent();
		}

	}

	public void removeAllChildren() {
	}
	
	public boolean isBuilt() {
		return ! toBuild;
	}
}
