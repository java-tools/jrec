package net.sf.RecordEditor.edit.display;


import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.event.TableModelEvent;
import javax.swing.tree.TreePath;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.AbstractLineNodeTreeParser;
import net.sf.RecordEditor.re.tree.LineNode;
import net.sf.RecordEditor.re.tree.TreeToXml;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class LineTree extends BaseLineTree<LineNode> {

	private static final String NO_DESTINATION_LINE = "No Destination line";
	private AbstractLineNodeTreeParser parser;


	protected LineTree(FileView viewOfFile, AbstractLineNodeTreeParser treeParser,
			boolean mainView, final int columnsToSkip) {
		super(viewOfFile, mainView, true, columnsToSkip, NO_OPTION_PANEL);

		parser = treeParser;
		root = parser.parse(view);


		AbstractAction[] extraActions = {
                new ReAbstractAction("Rebuild Tree") {
                    public void actionPerformed(ActionEvent e) {
                    	rebuildNode(getNodeForRow(popupRow));
                    }
                },
                new ReAbstractAction("Print Line Details") {
                    public void actionPerformed(ActionEvent e) {
                    	int[] selected = treeTable.getSelectedRows();

                    	//System.out.println();
                    	for (int i = 0; i < selected.length; i++) {
                    		getNodeForRow(selected[i]);
//                    		if (node != null) {
//                    			System.out.println(">> Line Details "
//                    					+ node.getLevel() + "             ".substring(12 - node.getLevel())
//                    					+ node.getFirstLeafLine() + " " + node.getLastLeafLine()
//                    					+ " :: " + node.getLineNumber());
//                    		}
                    	}
                    }
                }
		};

		init_100_setupScreenFields(extraActions);
	//	treeTable.setTableCellAdapter(new TblCellAdapter(this, 2));

		init_200_LayoutScreen();
	}


	@Override
	public void setScreenSize(boolean mainframe) {

		DisplayFrame parentFrame = getParentFrame();
		parentFrame.bldScreen();

        parentFrame.setBounds(1, 1,
                screenSize.width  - 1,
                screenSize.height - 1);

        parentFrame.setToMaximum(true);
		parentFrame.setVisible(true);
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {

		if (action == ReActionHandler.REBUILD_TREE) {
			root.removeAllChildren();
			parser.parseAppend(view, root);
			tableModel.fireTableStructureChanged();
			defColumns(getLayoutIndex());
		} else if (action == ReActionHandler.REPEAT_RECORD_POPUP) {
			getFileView().repeatLine(getNodeForRow(popupRow).getLineNumber());
		} else if (action == ReActionHandler.FULL_TREE_REBUILD) {
			root.removeAllChildren();
			rebuildNode(root);
			//parser.parseAppend(view, root, 0, fileView.getRowCount());
			//tableModel.fireTableDataChanged();
			//model.f
			//model.fireTreeStructureChanged(node, node.getPath(),
			//		childIdx, children);
		} else if (action == ReActionHandler.SAVE_AS_XML) {
			JFileChooser chooseFile = new JFileChooser();
			chooseFile.setSelectedFile(new File(view.getFileName() + ".xml"));

			int ret = chooseFile.showOpenDialog(null);

			if (ret == JFileChooser.APPROVE_OPTION) {
				if (view.getLayout().isXml()) {
					try {
						view.getBaseFile().writeFile(chooseFile.getSelectedFile().getPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					new TreeToXml(chooseFile.getSelectedFile().getPath(), root);
				}
			}
		} else {
			super.executeAction(action);
		}
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {

		return action == ReActionHandler.REBUILD_TREE
		    || action == ReActionHandler.REPEAT_RECORD_POPUP
		    || action == ReActionHandler.FULL_TREE_REBUILD
		    || action == ReActionHandler.SAVE_AS_XML
		    || super.isActionAvailable(action);
	}



//   /**
//     * get the prefered layout for a row. note: this is overriden in LineTree
//     * @param row table row to get the layout for
//     * @return rows prefered layout
//     */
//	@Override
//    protected int getLayoutIdx_100_getLayout4Row(int row) {
//    	int ret = -1;
//    	AbstractLineNode node = getNodeForRow(row);
//
//    	if (node != null && node.getLine() != null) {
//    		ret = node.getLine().getPreferredLayoutIdx();
//    	}
//
//    	return ret;
//    }


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#setCurrRow(int, int, int)
	 */
	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {

		//System.out.print("### " + newRow);

		LineNode node = findNode4LineNumber(root, newRow);
		//System.out.print(" ### " + fieldNum + " " + newRow);

		if (node != null && fieldNum >= 0) {
			int row;
			int fNo = super.getAdjColumn(layoutId, fieldNum);
			TreePath path = new TreePath(node.getPath());
			//System.out.println("  " + path);
			treeTable.getTree().makeVisible(path);
			row = treeTable.getTree().getRowForPath(path);

			try {
	            if ((getCurrRow() != newRow)) {
	           		tblDetails.changeSelection(row, fNo, false, false);
	            }

	           	treeTable.editCellAt(row, fNo);
			} catch (Exception e) {
				// TODO: handle exception
			}
			//System.out.println("Check Row :: " + treeTable.getEditingRow() + " " + treeTable.getSelectedRow()
			//		+ " " + treeTable.getSelectedRowCount());
		}
		if (newRow >= 0) {
			checkForRowChange(newRow);
		}
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getCurrRow()
	 */
	@Override
	public int getCurrRow() {
		return convertRow(getTreeTblRow());
	}

    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getPopupPosition()
	 */
	@Override
	protected int getPopupPosition() {
		return convertRow(popupRow);
	}

	private int convertRow(int row) {
		if (row >= 0) {
			LineNode node = getNodeForRow(row);
//			System.out.println("Got Node:: " + row + " " + treeTable.getSelectedRow() + " " + node.getLineNumber()
//					+ " -->" + node.getLineNumber());

			return node.getLineNumber();

//			return node.getFirstLeafLine() - 1;
		}
		return -1;
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getSelectedRows()
	 */
	@Override
	public int[] getSelectedRows() {
		//int[] selected = super.getSelectedRows();
		int[] selected = treeTable.getSelectedRows();

		//System.out.println("gSel 1 >> " + (selected == null));
		if (selected == null || selected.length == 0) {
			return selected;
		}
		//System.out.println("gSel 2 >> " + selected.length);
		ArrayList<Integer> list = new ArrayList<Integer>();
		TreePath treePath;
		LineNode node;
		int[] ret;
		int i;

		for (i = 0; i < selected.length; i++) {
			treePath = treeTable.getPathForRow(selected[i]);
			node = (LineNode) treePath.getLastPathComponent();

			if (node.getLineNumber() >= 0) {
				list.add(Integer.valueOf(node.getLineNumber()));
			}

			//System.out.println("--> " + node.isLeaf() + " " + treeTable.getTree().isExpanded(treePath)
			//		+ " " + node.getChildCount());
			if (! node.isLeaf() && ! treeTable.getTree().isExpanded(treePath)) {
				int end = node.getLastLeafLine();
				//System.out.println(" getSelectedRows  > " + node.getFirstLeafLine() + 1 + " -> " + end);
				for (int j = node.getFirstLeafLine(); j <= end; j++) {
					list.add(Integer.valueOf(j));
				}
			}
		}

		//System.out.print("Selection: -- ");
		ret = new int[list.size()];
		for (i = 0; i < ret.length; i++) {
			ret[i] = list.get(i).intValue();
			//System.out.print("  " + ret[i]);
		}
		//System.out.println();

		return ret;
	}


	/**
	 * Get the insert / paste after position
	 * @return insert / paste after position
	 *
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertAfterPosition()
	 */
	@Override
	protected int getInsertAfterPosition() {
		int row = getTreeTblRow();
		//TreePath treePath;

		if (row < 0) {
			throw new RuntimeException(NO_DESTINATION_LINE);
		}

		TreePath treePath = treeTable.getPathForRow(row);
		LineNode node = (LineNode) treePath.getLastPathComponent();
		int lineNum;

		if (node.isLeaf() || treeTable.getTree().isExpanded(treePath)) {
			lineNum = node.getFirstLeafLine() - 1;
		} else {
			lineNum = node.getLastLeafLine();
		}

		return lineNum;
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getInsertBeforePosition()
	 */
	@Override
	protected int getInsertBeforePosition() {
		int row = getTreeTblRow();
		//TreePath treePath;

		if (row < 0) {
			throw new RuntimeException(NO_DESTINATION_LINE);
		}

		TreePath treePath = treeTable.getPathForRow(row);
		LineNode node = (LineNode) treePath.getLastPathComponent();
		int ret;

		ret = node.getLineNumber();

		if (ret < 0) {
			ret = node.getFirstLeafLine();
		}

		return Math.max(-1, ret - 1);
	}

	/**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent event) {
        int lastRowChanged  = event.getLastRow();
        int firstRowChanged =  event.getFirstRow();

        //System.out.println(":: Changed " + event.getType() + " " + TableModelEvent.INSERT);
		switch (event.getType()) {
			case (TableModelEvent.INSERT):
				tableChanged_100_insertLines(firstRowChanged, lastRowChanged);
				checkForResize(event);
			break;
			case (TableModelEvent.DELETE):
				tableChanged_200_deletedLines(firstRowChanged, lastRowChanged);
			break;
			case (TableModelEvent.UPDATE):
				LineNode n = findNode4LineNumber(root, firstRowChanged);
				AbstractLine l;

				if (n == null
				|| ((l =  n.getLine()) != null && l.isError())
				|| (n.getLineNumber() != firstRowChanged && tableChanged_300_isNotEnd(n.getLine()))) {
					tableChanged_400_Update(firstRowChanged);
				}
				checkForResize(event);
			break;
			default:
		}
		/*System.out.println("Table Changed --> " + event.getType()
				+ " " + event.getFirstRow() + " " + event.getLastRow()
				+ " ~~ " + event.ALL_COLUMNS + " " + event.getColumn()
				+ " ~ " + event.toString()
				+ " ->> " + TableModelEvent.INSERT + " " + TableModelEvent.DELETE
				+ " " + TableModelEvent.UPDATE);*/
		//treeTable.getTree().fireTreeExpanded(arg0);

		if (super.hasTheFormatChanged(event)) {
			tableModel.fireTableStructureChanged();
			defColumns(getLayoutIndex());
		} else {
			tableModel.fireTableDataChanged();
		}
	}


	/**
	 * update tree for inserted lines
	 * @param start first inserted line
	 * @param end   last inserted line
	 */
	private void tableChanged_100_insertLines(int start, int end) {
		LineNode node = findNode4LineNumber(root, start - 1);
		LineNode parent;
		int diff = end - start + 1;

		if (node == null) {
			node = root;
		}

		while (node != null && node.getLineNumber() < start - 2) {
			node = (LineNode) node.getNextNode();
		}

//		if (node != null) {
//		System.out.println("Insert Find Parent:: start " + start + " " + node.getLineNumber()
//				+ " " + node.getUserObject());
//		}
		parent = getParent(node, end + 1);
//		System.out.println();
//		System.out.println("Found Parent:: start " + parent.getLineNumber()
//				+ " " + parent.getFirstLeafLine()
//				+ " " + parent.getLastLeafLine()
//				+ " " + parent.getUserObject());

		updateLineNumber(start, diff);

//		System.out.println("Parent:: start " + parent.getLineNumber()
//				+ " " + parent.getFirstLeafLine()
//				+ " " + parent.getLastLeafLine()
//				+ " " + parent.getUserObject());

		parseLater(parent);
	}


	/**
	 * update tree for deleted lines
	 * @param start first deleted line
	 * @param end   last deleted line
	 */
	private void tableChanged_200_deletedLines(int start, int end) {
		LineNode parent, p, temp;
		LineNode node = findNode4LineNumber(root, start);
		int diff = end - start + 1;

//		System.out.println();
//		System.out.print("LineTree - Delete:: " + start + " " + end + " " + (root == null));
		if (node == null) {
			node = root;
		}
//		System.out.print(" " + node.getLineNumber());

		while (node != null && node.getLineNumber() < start) {
			node = (LineNode) node.getNextNode();
		}
		parent = getParent(node, end + 1);
		while (node != null && node.getLineNumber() <= end) {
			temp = node;
			p = (LineNode) node.getParent();
			node = (LineNode) node.getNextNode();
			if (p != null) {
				System.out.println("Deleting node - " + temp.getLineNumber()
						+ " " + temp.getFirstLeafLine() + " " + temp.getLastLeafLine());
				p.remove(temp);
			}
		}

		updateLineNumber(end, -diff);

		parseLater(parent);
	}

	/**
	 * Check if it is a Xml end tag
	 * @param l line to check
	 * @return if it is a Xml end tag
	 */
	private boolean tableChanged_300_isNotEnd(AbstractLine l) {
		Object o = l.getField(l.getPreferredLayoutIdx(), 0);
		String s = "";
		if (o != null) {
			s = o.toString();
		}
		return ! (l.getLayout().isXml() && s.startsWith("/"));
	}


	/**
	 * update tree for inserted lines
	 * @param start first inserted line
	 */
	private void tableChanged_400_Update(int start) {
		LineNode node = findNode4LineNumber(root, start);

		if (node == null) {
			node = root;
		}

		while (node != null && node.getLineNumber() < start) {
			node = (LineNode) node.getNextNode();
		}

//		System.out.print("Update:: >> " + start + " << ");

		rebuildNode(getParent(node, start + 1));
	}


	private void parseLater(LineNode parent) {
		javax.swing.SwingUtilities.invokeLater(new RebuildLater(parent));
	}

	/**
	 * update nodes for insert / delete
	 * @param start starting line number
	 * @param difference difference to apply
	 */
	private void updateLineNumber(int start, int difference) {

		LineNode node = root;

		while (node != null) {
			node.adjustLineNumbers(start, difference);
			node = (LineNode) node.getNextNode();
		}
	}


	/**
	 * rebuild the tree starting at a specific node
	 * @param node to rebuild
	 */
	private void rebuildNode(final LineNode node) {

		if (node != null) {
			int start = node.getFirstLeafLine();
			int end = node.getLastLeafLine();

			if (node.getLevel() == 0) { // ie is thew root
				start = 0;
				end = view.getRowCount();
			}

			parser.parseAppend(view, node, start, end);

//			System.out.println("Line Numbers > > " );
			int[] childIdx = new int[node.getChildCount()];
			Object[] children = new Object[node.getChildCount()];
			for (int j = 0; j < node.getChildCount(); j++) {
				childIdx[j] = j;
				children[j] = node.getChildAt(j);
//				System.out.print(", " + ((LineNode) children[j]).getLineNumber());
			}
			//System.out.println("<<< " + node.getChildCount() + " Firing model changed ");

			treeTable.getTree().expandPath(new TreePath(node.getPath()));
			model.fireTreeStructureChanged(node, node.getPath(),
					childIdx, children);
		}
	}





	/**
	 * Find the Node corresponding to a line number
	 * @param node root node
	 * @param lineNumber line number to search for
	 * @return requested node
	 */
	private LineNode findNode4LineNumber(LineNode node, int lineNumber) {

		LineNode child = null;

//		System.out.println();
//		System.out.println("++ " + node.getLineNumber() + " " + lineNumber
//				+ " " +  node.isLeaf() );
		if (! node.isLeaf()) {

			for (int i = 0;     i < node.getChildCount()
			                && ((child = (LineNode) node.getChildAt(i)).getLastLeafLine() < lineNumber); i++) {
			}

//			if (child != null) {
//				System.out.println("Searching for " + lineNumber
//						+ " " + child.getLineNumber() + " " + child.getLastLeafLine());
//			}
			if (child != null && child.getLineNumber() < lineNumber && ! child.isLeaf()) {
				child = findNode4LineNumber(child, lineNumber);
			}
		}
		return child;
	}

	/**
	 * Get the parent of both the node and the end line number
	 * @param node node to retrieve the parent of
	 * @param lastLineNum line number that the parent must include
	 * @return parent of both the node and the end-line-number
	 */
	private LineNode getParent(LineNode node, int lastLineNum) {
		LineNode parent;

		if (node == null || node == root) {
			parent = root;
		} else {
			parent = (LineNode) node.getParent();
			if (parent != null) {
				int end = Math.min(lastLineNum, fileView.getRowCount());
//				System.out.println();
//				System.out.print("GetParent end = " + end);
				while (parent.getLastLeafLine() < end && parent.getParent() != null) {
//					System.out.print(" >> " + parent.getLastLeafLine()  + " " + parent.getLevel());
					parent = (LineNode) parent.getParent();
				}
				//System.out.println(" >> " + parent.getLastLeafLine() + " Depth = " + parent.getLevel());
			}
		}

		return parent;
	}


	 public void cb2xmlStuff() {
		 String s;
		 doFullExpansion(root);
		 getLayoutCombo().setSelectedItem("item");

		 int idx = getLayoutCombo().getSelectedIndex();
		 boolean[] fields = super.getFieldVisibility(idx);
		 AbstractRecordDetail rec = layout.getRecord(idx);

		 for (int i = 1; i < fields.length; i++) {
			 s = rec.getField(layout.getAdjFieldNumber(idx, i)).getName();
			 if (s.indexOf('~') > 0 || s.equals("numeric")) {
				 fields[i] = false;
			 }
		 }

		 super.setFieldVisibility(idx, fields);
		 super.setCopyHiddenFields(true);
	 }




	 /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getNewDisplay(net.sf.RecordEditor.edit.file.FileView)
	 */
	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new LineTree(view, this.parser, false, this.cols2skip);
	}



	/**
	  * rebuild the tree
	  */
	 private class RebuildLater implements Runnable {
		 private final LineNode parent;
		 public RebuildLater(LineNode rebuildFrom) {
			 parent = rebuildFrom;
		 }
		 public void run() {
			// try {  wait(200); } catch (Exception e) { }
			 //System.out.println("Running Rebuild ... ");
			 rebuildNode(parent);
		 }
	 }


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.edit.display.AbstractRowChangedImplementor#getChildScreen()
		 */
		@Override
		public AbstractFileDisplay createChildScreen(int pos) {
			LineFrame f = new LineFrame("ChildRecord:", super.fileView, Math.max(0, getCurrRow()), false);

			setChildScreen(f);

			return f;
		}

	 /*	public static void main(final String[] pgmArgs) {
		String fn = "/home/knoppix/RecordEdit/HSQLDB/SampleFiles/Xml/IVM0034_Map.XML";

		try {
			CopyBookInterface copyBookInterface = new CopyBookDbReader();
			LayoutDetail layout = copyBookInterface.getLayout("XML");
			FileView view = new FileView(fn, layout, false);

			new ReMainFrame("Specific file", "");
			new LineTree(view);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


}
