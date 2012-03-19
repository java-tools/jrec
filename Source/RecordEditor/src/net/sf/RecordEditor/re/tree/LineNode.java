package net.sf.RecordEditor.re.tree;


//import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FileView;

@SuppressWarnings("serial")
public final class LineNode extends DefaultMutableTreeNode implements AbstractLineNode {


//	private boolean hasChildren;
	//private boolean loadedChildren = false;

	public final String nodeName;
	@SuppressWarnings("rawtypes")
	private FileView view;
	private int lineNumber;
	private int firstLeafLine;
	private int lastLeafLine;

	@SuppressWarnings("rawtypes")
	private AbstractLine summaryLine = null;
	private String sortField;
	//private AbstractLine line = null;
	
	//private ArrayList<LineNode> children = null;


	/**
	 * create tree node based on File View
	 *
	 * @param pNodeName name of the root node
	 * @param fileView File View (or storage)
	 * @param lineNum line number
	 */
	public LineNode(final String pNodeName,
					@SuppressWarnings("rawtypes") final FileView fileView,
					final int lineNum) {
		super(pNodeName);
		
		nodeName = pNodeName;
		view = fileView;
		setLineNumberEtc(lineNum);
	}


    
    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getLayout()
	 */
    @SuppressWarnings("rawtypes")
	public final AbstractLayoutDetails getLayout() {
    	return view.getLayout();
    }




	public final int getLineNumber() {
		return lineNumber;
	}




	public final void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	
	/**
	 * Set Linenumber + leaf numbers
	 * @param lineNumber new linenumber
	 */
	public final void setLineNumberEtc(int lineNumber) {
		this.lineNumber = lineNumber;
		lastLeafLine = lineNumber;
		firstLeafLine = lineNumber + 1;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getLine()
	 */
	@SuppressWarnings("rawtypes")
	public final AbstractLine getLine() {
		if (lineNumber < 0 || lineNumber >= view.getRowCount()) {
			return summaryLine;
		}
		return view.getLine(lineNumber);
	}




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getView()
	 */
	@SuppressWarnings("rawtypes")
	public final FileView getView() {
		return view;
	}



	/**
	 * @return the lastLeafLine
	 */
	public final int getLastLeafLine() {
		return lastLeafLine;
	}



	/**
	 * @param lastLeafLine the lastLeafLine to set
	 */
	public final void setLastLeafLine(int lastLeafLine) {
		this.lastLeafLine = lastLeafLine;
	}


	

	/**
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#getDefaultLineNumber()
	 */
	@Override
	public int getDefaultLineNumber() {
		return firstLeafLine;
	}



	/**
	 * @return the firstLeafLine
	 */
	public final int getFirstLeafLine() {
		return firstLeafLine;
	}



	/**
	 * @param firstLeafLine the firstLeafLine to set
	 */
	public final void setFirstLeafLine(int firstLeafLine) {
		this.firstLeafLine = firstLeafLine;
	}
	

	/**
	 * Adjust all the line number for a insertion / deletion
	 * @param start first line to change
	 * @param difference difference to apply
	 */
	public final void adjustLineNumbers(int start, int difference) {

//		System.out.print("Updating Pos \t: " + start + "\t" + firstLeafLine 
//				+ "\t" + lastLeafLine + "\t"+ lineNumber );
		if (lineNumber > start) {
			lineNumber += difference;
		}
		if (firstLeafLine > start) {
			firstLeafLine += difference;
		}
		if (lastLeafLine >= start) {
			lastLeafLine += difference;
		}
//		System.out.println("\t Changed \t: " + firstLeafLine 
//				+ "\t" + lastLeafLine + "\t"+ lineNumber );
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#setSummaryLine(net.sf.JRecord.Details.AbstractLine, java.lang.String)
	 */
	public final void setSummaryLine(@SuppressWarnings("rawtypes") AbstractLine summaryLine, String fieldName) {
		this.summaryLine = summaryLine;
		this.sortField = fieldName;
	}

	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.tree.AbstractLineNode#getLineType()
	 */
	public final String getLineType() {
		String ret = "";
		
		if (summaryLine == null) {
			if (lineNumber >= 0) {
				int pref = view.getLine(lineNumber).getPreferredLayoutIdx();
				if (pref < 0 || pref > view.getLayout().getRecordCount()) {
					pref = 0;
				}
				ret = view.getLayout().getRecord(pref).getRecordName();
			} else if (isRoot()) {
				return view.getLayout().getLayoutName();
			}
		} else {
			ret = sortField;
		}
		
		return ret;
	}
	
	public final String getSortValue() {
		String ret = "";
		if (summaryLine != null) {
			ret = nodeName;
		}
		
		return ret;
	}



	/**
	 * @see net.sf.RecordEditor.re.file.AbstractLineNode#insert(net.sf.JRecord.Details.AbstractLine, int, int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public LineNode insert(AbstractLine line, int lineNum, int pos) {

		LineNode n = new LineNode(
					"inserted",
					this.view,
					lineNum
		);
		if (pos < 0 || pos > this.getChildCount()) {
			add(n);
		} else {
			insert(n , pos);
		}
		
		if (view.getBaseFile().getTreeTableNotify() != null) {
			view.getBaseFile().getTreeTableNotify().fireTreeNodesInserted(n);
		}
		return n;
	}

	
//	/**
//	 * @see javax.swing.tree.DefaultMutableTreeNode#getUserObject()
//	 */
//	@Override
//	public Object getUserObject() {
//		
//		if (lineNumber >= 0 && getLine().getPreferredLayoutIdx() >= 0) {
//			int idx = getLine().getPreferredLayoutIdx();
//			
//			try {
//				return this.view.getLayout().getRecord(idx).getRecordName();
//			} catch (Exception e) {
//			}
//		}
//		return super.getUserObject();
//	}
}
