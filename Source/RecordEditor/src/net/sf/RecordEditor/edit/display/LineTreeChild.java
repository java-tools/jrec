package net.sf.RecordEditor.edit.display;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.event.TableModelEvent;
import javax.swing.tree.TreePath;

import net.sf.JRecord.Details.AbstractChildDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.tree.LineNode;
import net.sf.RecordEditor.re.tree.LineNodeChild;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReAction;


@SuppressWarnings("serial")
public class LineTreeChild extends BaseLineTree<AbstractLineNode> {

//	model.fireTreeStructureChanged(node, node.getPath(),
	//		childIdx, children);
	//private static final Object[] rootPath = {};
	protected LineTreeChild(
			@SuppressWarnings("rawtypes") FileView viewOfFile, AbstractLineNode rootNode,
			boolean mainView, final int columnsToSkip) {
		super(viewOfFile, mainView, true, columnsToSkip, TREE_OPTION_PANEL);
		super.setDisplayType(TREE_DISPLAY);

		root = rootNode;


		AbstractAction[] extraActions = {
                null,
                new ReAction(ReActionHandler.SORT, this),
                null,
                new ReAbstractAction("Redisplay Tree") {
                    public void actionPerformed(ActionEvent e) {
                    	AbstractLineNode root = (AbstractLineNode) model.getRoot();
                    	model.fireTreeStructureChanged(root, root.getPath(), null, null);
                    }
                },
  		};

		init_100_setupScreenFields(extraActions);

		init_200_LayoutScreen();

		if ( ! viewOfFile.isView()) {
			viewOfFile.setTreeTableNotify(model);
		}
	}



	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getSelectedLines()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<AbstractLine> getSelectedLines() {

		int[] sel = tblDetails.getSelectedRows();
		ArrayList<AbstractLine> list = new ArrayList<AbstractLine>(sel.length);
		AbstractLineNode node;

		for (int i = 0; i < sel.length; i++) {
			node = getNodeForRow(sel[i]);
			if (node != null && node.getLine() != null) {
				list.add(node.getLine());
			}
		}

		return list;
	}


	@Override
	public int getCurrRow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getInsertAfterPosition() {
		// TODO Auto-generated method stub
		return -1;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected AbstractLine getInsertAfterLine(boolean prev) {
		AbstractLine ret = null;
		int row = getTreeTblRow();
		if (prev) {
			row -= 1;
		}

		if (row >= 0) {
			AbstractLineNode node = getNodeForRow(row);
			if (node != null) {
				ret = node.getLine();
				while (ret == null
					&& node.getParent() != null
					&& node.getParent() instanceof AbstractLineNode) {
					node = (AbstractLineNode) node.getParent();
					ret = node.getLine();
				}
			}
		}

		return ret;
	}


	 /**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getTreeLine()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public AbstractLine getTreeLine() {
		AbstractLine line = getInsertAfterLine(false);
		if (line == null && view.getRowCount() > 0) {
			line = view.getLine(0);
		}
		return line;
	}


	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {

	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#setCurrRow(net.sf.RecordEditor.re.file.FilePosition)
	 */
	@Override
	public void setCurrRow(FilePosition position) {

		if (position.currentLine != null) {
			int idx = setLayoutForPosition(position);
			int fNo = super.getAdjColumn(idx, position.col) - 1;
			AbstractLineNode node = createNodes(position.currentLine);

			int row = expandNode(node);
            fileView.fireTableDataChanged();

            tblDetails.changeSelection(row, 1, false, false);
//            treeTable.editCellAt(row, fNo);
		}
	}

	@SuppressWarnings("rawtypes")
	private AbstractLineNode createNodes(AbstractLine line) {
		AbstractLineNode node = view.getTreeNode(line);
		if (node == null) {
			AbstractLine p = line.getTreeDetails().getParentLine();
			if (p != null) {
				AbstractChildDetails childDtls = line.getTreeDetails().getChildDefinitionInParent();
				AbstractLineNode parentNode = createNodes(p);

				if (childDtls.isRepeated() && parentNode instanceof LineNodeChild) {
					// Ensure Tree is expanded
					int idx = childDtls.getRecordIndex();
					AbstractLineNode childNode = ((LineNodeChild) parentNode)
							.getChildbyCode(childDtls.getChildIndex());

					if (childNode.getChildCount() > idx) {
						childNode.getChildAt(idx);
					}

				}

				node = view.getTreeNode(line);
			}
		}

		return node;
	}

	private int expandNode(AbstractLineNode node) {
		if (node != null) {
			expandNode((AbstractLineNode) node.getParent());
			TreePath t = new TreePath(node.getPath());
			treeTable.getTree().expandPath(t);
			return treeTable.getTree().getRowForPath(t);
		}
		return -1;
	}

	@Override
	public void tableChanged(TableModelEvent event) {

	}



	public void deleteLines() {
		int[] selRows = treeTable.getSelectedRows();

		if (selRows != null && selRows.length > 0) {
			TreePath treePath;
			int i;
			AbstractLineNode[] nodes = new AbstractLineNode[selRows.length];
			for (i = 0; i < selRows.length; i++) {
				treePath = treeTable.getPathForRow(selRows[i]);
				nodes[i] = (AbstractLineNode) treePath.getLastPathComponent();
			}

			for (i = 0; i < selRows.length; i++) {
				AbstractLineNode parent = (AbstractLineNode) nodes[i].getParent();
				if (nodes[i].getLine() != null) {
					//model.fireTreeNodesRemoved(nodes[i]);
					//System.out.println("-> " + node.getLine().getFullLine());
					fileView.deleteLine(nodes[i].getLine());
					nodes[i].removeFromParent();
				} else if (nodes[i].getChildCount() > 0) {
					AbstractLineNode firstChild = (AbstractLineNode) nodes[i].getChildAt(0);
					AbstractChildDetails childDef = firstChild.getLine().getTreeDetails().getChildDefinitionInParent();
					if (childDef != null && ! childDef.isRequired() && parent.getLine() != null) {
						AbstractLine parentLine = parent.getLine();
						if (parentLine.getTreeDetails().removeChildren(childDef)) {
							model.fireTreeNodesRemoved(nodes[i]);
							nodes[i].removeFromParent();
						}
					}
				}
			}

//			model.fireTreeStructureChanged(node, node.getPath(),
//					childIdx, children);
		}
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {

		switch (action) {
		case(ReActionHandler.REPEAT_RECORD) :
			getFileView().repeatLine(getNodeForRow(popupRow).getLine());
		break;
		case(ReActionHandler.COPY_RECORD):
			 execute_100_Copy() ;
		break;
		case ReActionHandler.CUT_RECORD:
			execute_100_Copy();
			deleteLines();
		break;
		case(ReActionHandler.PASTE_RECORD):
		case ReActionHandler.PASTE_RECORD_PRIOR:
			executeTreeAction(action);
		break;
		case ReActionHandler.TABLE_VIEW_SELECTED: 			createView();					 		   break;
		case ReActionHandler.RECORD_VIEW_SELECTED:		createRecordView();				   break;
		case ReActionHandler.COLUMN_VIEW_SELECTED:		createColumnView();				   break;
		default:
			super.executeAction(action);
		}
	}

	private void execute_100_Copy() {
		int[] selRows = treeTable.getSelectedRows();
		if (selRows != null && selRows.length > 0) {
			TreePath treePath;
			int i, j;

			AbstractLine[] lines = new AbstractLine[selRows.length];
			j = 0;
			for (i = 0; i < selRows.length; i++) {
				treePath = treeTable.getPathForRow(selRows[i]);
				lines[j] = ((AbstractLineNode) treePath.getLastPathComponent()).getLine();
				if (lines[j] != null) {
					j += 1;
				}
			}

			if (j != lines.length) {
				AbstractLine[] temp = lines;
				lines = new AbstractLine[j];
				System.arraycopy(temp, 0, lines, 0, j);
			}

			FileView.setCopyRecords(lines);
		}
	}


	/**
	 * Create a view from the selected records
	 */
	private void createView() {
		List<AbstractLine> selLines = getSelectedLines();

		if (selLines != null && selLines.size() > 0) {
			DisplayBuilderFactory.newLineList(getParentFrame(), fileView.getLayout(), fileView.getView(selLines),
		            fileView.getBaseFile());
		}
	}

	/**
	 * Create a view from the selected records
	 */
	private void createColumnView() {

		if (getSelectedRowCount() > MAXIMUM_NO_COLUMNS) {
			Common.logMsg("To Many Rows Selected for ColumnDisplay", null);
		} else {
			List<AbstractLine> selLines = getSelectedLines();

			if (selLines != null && selLines.size() > 0) {
			    new LinesAsColumns(fileView.getView(selLines));
			}
		}
	}

	/**
	 * Create a view from the selected records
	 */
	private void createRecordView() {
		List<AbstractLine> selLines = getSelectedLines();

		if (selLines != null && selLines.size() > 0) {
			newLineDisp(fileView.getView(selLines), 0);
		}
	}

	public void expandTree(String option) {
		getLayoutCombo().setSelectedItem(option);

		doFullExpansion(root);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Common.calcColumnWidths(tblDetails, 1);
			}
		});



		//setLayoutIdx();
	}
	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {

		return action == ReActionHandler.REPEAT_RECORD
		 		|| (  action != ReActionHandler.SELECTED_VIEW
		 		   && super.isActionAvailable(action));
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.AbstractRowChangedImplementor#getChildScreen()
	 */
	@Override
	public AbstractFileDisplay createChildScreen(int pos) {
		LineFrameTree f = new LineFrameTree(fileView, getInsertAfterLine(false), false);
		setChildScreen(f);
		return f;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getNewDisplay(net.sf.RecordEditor.edit.file.FileView)
	 */
	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		LineNode ln = new LineNode("root", view, -1);
		ln.setFirstLeafLine(0);
		ln.setLastLeafLine(view.getRowCount());
		LineTreeChild tc =  new LineTreeChild(view, ln, false, this.cols2skip);
		//DisplayBuilder.addToScreen(super.getParentFrame(), tc);
		return tc;
	}
}
