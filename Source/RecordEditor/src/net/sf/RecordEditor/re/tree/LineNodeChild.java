/**
 * 
 */
package net.sf.RecordEditor.re.tree;

import java.util.List;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.AbstractTreeDetails;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode;


/**
 * @author bm
 *
 */
public class LineNodeChild 
extends BmDefaultMutableTreeNode 
implements AbstractLineNode {

	private final static int ROOT_NODE = 1;
	private final static int LINE_NODE = 2;
	private final static int CHILD_NODE = 3;
	
//	public final String nodeName;
	private FileView view;
	private AbstractLine line = null;
	
//	private int nodeType = LINE_NODE;
	private boolean hasChildren = false;
	
	private AbstractLineNode[] children;

	/**
	 * create tree node based on File View
	 *
	 * @param pNodeName name of the root node
	 * @param fileView File View (or storage)
	 */
	public LineNodeChild(final String pNodeName,
					final FileView fileView) {
		super(pNodeName);
		
//		nodeName = pNodeName;
		view = fileView;
		//nodeType = ROOT_NODE;
		
		if (view.getRowCount() > 0) {
			String name;
			
			for (int i =0; i < view.getRowCount(); i++) {
				int RecordIdx = view.getLine(i).getPreferredLayoutIdx();

				name = getRootName(RecordIdx);

				super.add(new LineNodeChild(name, view, view.getLine(i)));
			}
			hasChildren = true;
		}
	}
	
	/**
	 * create tree node based on File View
	 *
	 * @param pNodeName name of the root node
	 * @param fileView File View (or storage)
	 * @param theLine line being displayed
	 */
	public LineNodeChild(final String pNodeName,
					final FileView fileView,
					final AbstractLine theLine) {
		super(pNodeName);
//		nodeName = pNodeName;
		view = fileView;
		line = theLine;
		
		view.setNodeForLine(line, this);
		
		if (line != null) {
			int recordIdx = line.getPreferredLayoutIdx();
			
			if (recordIdx >= 0 && getLayout().getRecord(recordIdx) != null) {
				AbstractRecordDetail recordDef = getLayout().getRecord(recordIdx);
				hasChildren = recordDef.getChildRecordCount() > 0;
				
				if (hasChildren) {
					AbstractChildDetails childDtls;
					AbstractTreeDetails childLineDtls = theLine.getTreeDetails();
					List<AbstractLine> childLines;
					children = new AbstractLineNode[recordDef.getChildRecordCount()];
					for (int i = 0; i < recordDef.getChildRecordCount(); i++) {
						childDtls = recordDef.getChildRecord(i);
						if (! childLineDtls.hasLines(i)) {
							children[i] = null;
						} else if (childDtls.isRepeated()) {
							children[i] = new LineNodeChildList(childLineDtls.getChildName(i) + "'s", fileView, childLineDtls, i);
							add(children[i]);
						} else {
							childLines = childLineDtls.getLines(i);
							children[i] = new LineNodeChild(childLineDtls.getChildName(i), fileView, childLines.get(0));
							
							add(children[i]);
						}
					}
				}
			}
		}
	}

	/**
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#getLayout()
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
	 * @see net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode#remove(int)
	 */
	@Override
	public void remove(int index) {
		removeLocal(getChildAt(index));
		super.remove(index);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.BmDefaultMutableTreeNode#remove(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void remove(MutableTreeNode node) {
		super.remove(node);
		removeLocal(node);
	}

	
	private void removeLocal(TreeNode node) {
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (children[i] == node) {
					children[i] = null;
					break;
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#setSummaryLine(net.sf.JRecord.Details.AbstractLine, java.lang.String)
	 */
	@Override
	public void setSummaryLine(AbstractLine summaryLine, String fieldName) {

	}

	/**
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#getLineNumber()
	 */
	@Override
	public int getLineNumber() {
	    if (line != null) {
	    	return view.indexOf(line);
	    }

		return -1;
	}
	

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getDefaultLineNumber()
	 */
	@Override
	public int getDefaultLineNumber() {
		return getLineNumber();
	}

	/**
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#getSortValue()
	 */
	@Override
	public String getSortValue() {
		return null;
	}
	
	

	/**
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#insert(net.sf.JRecord.Details.AbstractLine, int, int)
	 */
	@Override
	public AbstractLineNode insert(AbstractLine newLine, int lineNum, int pos) {
		AbstractLineNode ret;
		if (line != null) {
			ret = insertStd(newLine, lineNum, pos);
		} else {
			ret = new LineNodeChild(
					getRootName(newLine.getPreferredLayoutIdx()),
					view,
					newLine
			);
			super.insert(ret, pos);
		}
		
		if (ret != null && view.getBaseFile().getTreeTableNotify() != null) {
			view.getBaseFile().getTreeTableNotify().fireTreeNodesInserted(ret);
		}
		
		return ret;
	}
	
	
	public AbstractLineNode insertStd(AbstractLine newLine, int lineNum, int pos) {
		AbstractChildDetails childDef = newLine.getTreeDetails().getChildDefinitionInParent();
		AbstractTreeDetails childLineDtls = line.getTreeDetails();
		int idx = childDef.getChildIndex();
		AbstractLineNode n = null;
		int count;
		count = 0;
		for (int i = 0; i < childDef.getChildIndex(); i++) {
			if (children != null) {
				count += 1;
			}
		}
		
		if (childDef.isRepeated()) {
			if (children[idx] == null) {
				//System.out.println(" >>> ### 1 add " + newLine.getFullLine());
				children[idx] = new LineNodeChildList(childLineDtls.getChildName(idx) + "'s", view, childLineDtls, idx);
				super.insert(children[idx], count);
				view.getTreeTableNotify().fireTreeNodesInserted(children[idx]);
			} else {
				//LineNodeChild 
				
				if (children[idx] instanceof LineNodeChildList
				&& ((LineNodeChildList) children[idx]).isBuilt()) {
					n = new LineNodeChild(
									getLayout().getRecord(newLine.getPreferredLayoutIdx()).getRecordName(),
									view,
									newLine
					);
					if (pos < 0 || pos >= children[idx].getChildCount()) {
						//System.out.println(" >>> ### 2 add " + newLine.getFullLine());
						children[idx].add(n);
					} else {
						//System.out.println(" >>> ### 3 add " + newLine.getFullLine());
						children[idx].insert(n	, pos);
					}
				}
			}
		} else {
			//LineNodeChild
			n = new LineNodeChild(
					childLineDtls.getChildName(idx),
					view,
					newLine
			);
			if (children[idx] != null) {
				super.remove(children[idx]);
			}
			children[idx] = n;
			super.insert(n, count);
		}
		
//		
//		if (n != null && view.getBaseFile().getTreeTableNotify() != null) {
//			view.getBaseFile().getTreeTableNotify().fireTreeNodesInserted(n);
//		}
		
		return n;
	}
	
	private String getRootName(int idx) {
		String name = "root";
		AbstractLayoutDetails layout = getLayout();
		if (idx >= 0 && layout != null && layout.getRecord(idx) != null) {
			name = layout.getRecord(idx).getRecordName();
		}
		return name;
	}


	public AbstractLineNode getChildbyCode(int code) {
		return children[code];
	}
	

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#insertNode(int, java.lang.String, net.sf.RecordEditor.re.file.FileView, net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public AbstractLineNode insertNode(int location, String nodeName,
			FileView fileView, AbstractLine theLine) {
		AbstractLineNode node = new LineNodeChild(nodeName, fileView, theLine);
		this.insert(node, location);
		return node;
	}
}
