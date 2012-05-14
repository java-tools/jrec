package net.sf.RecordEditor.edit.display;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;


import javax.swing.AbstractAction;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;


import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.RecordEditor.edit.display.Action.AutofitAction;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.common.AbstractRowChanged;
import net.sf.RecordEditor.edit.display.util.RowChangeListner;

import net.sf.RecordEditor.re.file.AbstractLineNode;
import net.sf.RecordEditor.re.file.AbstractTreeFrame;
import net.sf.RecordEditor.re.file.FieldMapping;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.tree.LineTreeTabelModel;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.ShowFieldsMenu;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeTable.JTreeTable;
import net.sf.RecordEditor.utils.swing.treeTable.TreeTableModelAdapter;

@SuppressWarnings("serial")
public abstract class BaseLineTree<LNode extends AbstractLineNode>
extends BaseDisplay
implements AbstractFileDisplayWithFieldHide, TableModelListener, AbstractRowChanged, AbstractTreeFrame<LNode>  {

	private static final int MINIMUM_TREE_COLUMN_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 22;
	private static final int STANDARD_COLUMNS = 2;
	//private BasePanel pnl = new BasePanel();
	protected JTreeTable treeTable;
	private JScrollPane treeTablePane;
	protected LineTreeTabelModel model;
	protected TreeTableModelAdapter tableModel;
	protected FileView<?> view;
	protected LNode root;

	protected ButtonTableRendor buttonRendor = new ButtonTableRendor();

//	protected int firstDisplayColumn = 1;
	protected int cols2skip, lastRow;
	protected int popupRow, popupCol = 0;

	private boolean isPrefered = false;

	private FieldMapping fieldMapping;

	private KeyListener keyListner;

//    private JMenu showColumnsMenu = new JMenu("Show Column");
//    private ArrayList<ShowFieldAction> hiddenColumns = new ArrayList<ShowFieldAction>();

	private ShowFieldsMenu showFields = new ShowFieldsMenu() {
	    public boolean showColumn(TableColumn colDef, int originalColumn) {
			TableColumnModel mdl = tblDetails.getColumnModel();
			if (getLayoutIndex() < layout.getRecordCount()) {
				fieldMapping.showColumn(getLayoutIndex(), colDef.getModelIndex() - getColAdjust());
			}
			mdl.addColumn(colDef);
//			System.out.println(" -- display " + colDef.getHeaderValue()
//					+ " " + colDef.getModelIndex()
//					+ " ! "+ originalColumn
//					+" $ " + mdl.getColumnCount());

			mdl.moveColumn(mdl.getColumnCount() - 1, originalColumn);
			return true;
	    }
	};

	public BaseLineTree(
			FileView<?> viewOfFile,
			boolean mainView,
			boolean prefered,
			final int columnsToSkip,
			final int option) {
		super("Tree View", viewOfFile, mainView, false, false, prefered, false, option);

		view = viewOfFile;
		cols2skip = columnsToSkip;

		fieldMapping = new FieldMapping(getFieldCounts());

		super.actualPnl.addReKeyListener(new DelKeyWatcher());
	}

	protected final void init_100_setupScreenFields(AbstractAction[] extraActions) {

		view.addTableModelListener(this);

		model = new LineTreeTabelModel(view, root, cols2skip, view.getLayout().isMapPresent());
		treeTable = new JTreeTable(model);
		treeTablePane = new JScrollPane(treeTable);
		tableModel = treeTable.getTableModel();
		treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setJTable(treeTable);
		treeTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		treeTable.getTree().setRootVisible(false);
		treeTable.getTree().setShowsRootHandles(true);
		keyListner =    new RowChangeListner(treeTable, this);

		actualPnl.setHelpURL(Common.formatHelpURL(Common.HELP_TREE_VIEW));


		initToolTips();

		defColumns(0);

		AbstractAction[] mainActions = {
	        		new AbstractAction("Edit Record") {
	    	            public void actionPerformed(ActionEvent e) {
	    	            	newLineFrame(getNodeForRow(popupRow));
	    	           }
	    	        },
	                null,
	                new AutofitAction(this),
	    	        null,
	                new AbstractAction("Expand Tree") {
	                    public void actionPerformed(ActionEvent e) {
	                    	treeTable.getTree().expandRow(popupRow);
	                    }
	                },
	                new AbstractAction("Fully Expand Tree") {
	                    public void actionPerformed(ActionEvent e) {
	                    	doFullExpansion(getNodeForRow(popupRow));

	                    }
	                },
	                new AbstractAction("Collapse Tree") {
	                    public void actionPerformed(ActionEvent e) {
	                    	treeTable.getTree().collapseRow(popupRow);
	                    }
	                },

        };


        if (extraActions != null && mainActions.length > 0) {
        	AbstractAction[] tmpActions = mainActions;
        	mainActions = new AbstractAction[tmpActions.length + extraActions.length];
        	//System.out.println(" @@@ " + mainActions.length + " " +  tmpActions.length + " " + extraActions.length);
         	System.arraycopy(tmpActions, 0, mainActions, 0, tmpActions.length);
         	System.arraycopy(extraActions,0, mainActions, tmpActions.length, extraActions.length);
        }
        MenuPopupListener mainPopup = new MenuPopupListener(mainActions, true, treeTable) {
            public void mousePressed(MouseEvent m) {
                int col = treeTable.columnAtPoint(m.getPoint());

                popupRow = treeTable.rowAtPoint(m.getPoint());

                checkRowChange(popupRow);
                if (treeTable.getColumnModel().getColumn(col).getModelIndex() == 0) {
                	newLineFrame(getNodeForRow(popupRow));
                } else {
                	super.mousePressed(m);
                }
            }

            protected boolean isOkToShowPopup(MouseEvent e) {
                 popupCol = treeTable.columnAtPoint(e.getPoint());
                 popupRow = treeTable.rowAtPoint(e.getPoint());

                return popupCol > 0;
            }
        };
        mainPopup.getPopup().addSeparator();
        mainPopup.getPopup().add(new AbstractAction("Hide Column") {
            public void actionPerformed(ActionEvent e) {
            	hideColumn(popupCol);
            }
        });

        mainPopup.getPopup().add(showFields.getMenu());

		treeTable.addMouseListener(mainPopup);

		treeTable.getTree().addTreeExpansionListener(new TreeExpansionListener() {
			 public void treeCollapsed(TreeExpansionEvent event) {

			 }
			 public void treeExpanded(TreeExpansionEvent event) {
				 defColumns(getLayoutIndex());
			 }
		});
		//treeTable.add

		if (Common.OPTIONS.useNewTreeExpansion.isSelected()) {
			//int count = treeTable.getTree().getRowCount();

			for (int i = 0; i < treeTable.getTree().getRowCount() && treeTable.getTree().getRowCount() < 50; i++) {
				treeTable.getTree().expandRow(i);
			}
		} else if (tableModel.getRowCount() == 1) {
			treeTable.getTree().expandRow(0);
		}
	}

	protected final void init_200_LayoutScreen()  {
		Rectangle scrSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();


//		pnl.addComponent("Layouts", getLayoutList());
		actualPnl.setGap(BasePanel.GAP1);

	    actualPnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
                   BasePanel.FULL, BasePanel.FULL,
                   treeTablePane);

	    Common.calcColumnWidths(treeTable, 3);

		this.addMainComponent(actualPnl);
	    this.setSize(scrSize.width  - 1, scrSize.height - 1);
	    setToMaximum(true);
		//this.pack();

	    LayoutCombo combo = getLayoutCombo();
	    if (layout.getRecordCount() > 1 && combo.getPreferedIndex() > 0 && Common.usePrefered()) {
        	combo.setSelectedIndex(combo.getPreferedIndex());
        	setTableFormatDetails(combo.getPreferedIndex());
        	fireLayoutIndexChanged();
        } else {
	 		setLayoutIdx();
        }
       	fileView.setCurrLayoutIdx(super.getLayoutIndex());

		this.setVisible(true);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#newLayout()
	 */
	@Override
	public void setNewLayout(@SuppressWarnings("rawtypes") AbstractLayoutDetails newLayout) {

		super.setNewLayout(newLayout);
		LayoutCombo combo = getLayoutCombo();
	    if (layout.getRecordCount() > 1 && combo.getPreferedIndex() > 0 && Common.usePrefered()) {
        	combo.setSelectedIndex(combo.getPreferedIndex());
        	setTableFormatDetails(combo.getPreferedIndex());
        	fireLayoutIndexChanged();
        } else {
	 		setLayoutIdx();
	 		defColumns(combo.getSelectedIndex());
        }
	    fieldMapping.resetMapping(getFieldCounts());
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent event) {
		// TODO Auto-generated method stub
		//System.out.println(" ^^^^ 1 " + event.getType() + " " + event.getFirstRow());


	}

	private void newLineFrame(LNode n) {

       	int lineNumber = n.getLineNumber();

    	if (lineNumber < 0) {
    		lineNumber = n.getDefaultLineNumber();
    	}

    	if (lineNumber < 0) {
    		new LineFrameTree(getFileView(), n.getLine());
    	} else {
    		newLineFrame(getFileView(), lineNumber);
    	}
	}


	/**
	 * Get the number of fields for each record
	 * @return field counts for for each record
	 */
	private int[] getFieldCounts() {
       int[] rows = new int[layout.getRecordCount()];

        for (int i = 0; i < layout.getRecordCount(); i++) {
        	rows[i] = fileView.getLayoutColumnCount(i);
        }

        return rows;
	}

	/**
     * Changes the record layout used to display the file
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#fireLayoutIndexChanged()
	 */
	@Override
	public void fireLayoutIndexChanged() {

		int idx = getLayoutIndex();
		fileView.setCurrLayoutIdx(idx);
		//System.out.println("BaseLine Tree: " + model.getRecordIndex() + " != " + idx);
		if (model.getRecordIndex() != idx) {
			model.setRecordIndex(idx);
			tableModel.fireTableStructureChanged();
			defColumns(idx);
		}
	}


	/**
	 * Get the node at a specified TreeTable Row
	 * @param row row to get the node for
	 * @return requested node
	 */
	@SuppressWarnings("unchecked")
	protected final LNode getNodeForRow(int row) {
		if (row >= 0) {
			TreePath treePath = treeTable.getPathForRow(row);
			if (treePath != null) {
				return (LNode) treePath.getLastPathComponent();
			}
		}
		return null;
	}

	/**
	 * define columns for a specified record index
	 * @param idx record index
	 */
	public final void defColumns(int idx) {
		int columns = model.getColumnCount();
			//layout.getRecord(idx).getFieldCount() + 2 - firstDisplayColumn;
		defineColumns(columns, model.getSkipColumns(), cols2skip);

		TableColumn tc = treeTable.getColumnModel().getColumn(0);
		tc.setCellRenderer(buttonRendor);
		//tc.setCellEditor(buttonRendor);
		tc.setMaxWidth(5);
		tc.setResizable(false);

		if (treeTable.getColumnModel().getColumnCount() > 1) {
			tc = treeTable.getColumnModel().getColumn(1);
	//		System.out.println(">> " + tc.getPreferredWidth()
	//				+ " " + MINIMUM_TREE_COLUMN_WIDTH
	//				+ " " + tc.getMaxWidth()
	//				+ " " + tc.getMinWidth());
			tc.setPreferredWidth(Math.max(tc.getPreferredWidth(), MINIMUM_TREE_COLUMN_WIDTH));
			//System.out.println(">> " + tc.getPreferredWidth());

			int layoutIdx = getLayoutIndex();
		    isPrefered = layoutIdx == getLayoutCombo().getPreferedIndex();
		   	treeTable.removeKeyListener(keyListner);
		    if (isPrefered) {
		    	treeTable.addKeyListener(keyListner);
	        	lastRow = - 1;
	        	checkRowChange(treeTable.getSelectedRow());
		    }
		}
	}



	 /**
	  * Fully expand a node
	  * @param node node to be expanded
	  */
	 protected final void doFullExpansion(AbstractLineNode node) {

		 if (node != null && ! node.isLeaf()) {
			 for (int j = 0; j < node.getChildCount(); j++) {
				 doFullExpansion((AbstractLineNode) node.getChildAt(j));
			 }
			 treeTable.getTree().expandPath(new TreePath(node.getPath()));
		 }
	 }

	/**
	 * get the current row int the Tree Table
	 * @return current row
	 */
	protected final int getTreeTblRow() {

		int row = treeTable.getEditingRow();
		//System.out.println("GetRow:: " + row + " " + treeTable.getSelectedRow()
		//		+ " " + treeTable.getSelectedRowCount());
		if (row < 0 && treeTable.getSelectedRowCount() > 0) {
			row = treeTable.getSelectedRow();
		}
		return row;
	}


    public final void checkRowChange(int row) {
//    	System.out.println("Check Row: " + isPrefered + "   " + lastRow + " " + row);
    	if (isPrefered && lastRow != row) {
    		LNode node = getNodeForRow(row);
    		AbstractLine<?> line;
    		if (node != null && (line = node.getLine()) != null) {
	    		int recordIdx = line.getPreferredLayoutIdx();
	    		if (recordIdx != model.getDefaultPreferredIndex()) {
	    			model.setDefaultPreferredIndex(recordIdx);

		    		TableColumnModel columns = tblDetails.getColumnModel();
		    		int last = Math.min(model.getColumnCount(), columns.getColumnCount());
		    		for (int i = 0; i < last; i++) {
		    			//System.out.print("  " + i + " " + fileView.getColumnName(i));
		    			columns.getColumn(i).setHeaderValue(model.getColumnName(columns.getColumn(i).getModelIndex()));
		    		}
		    		//tblScrollPane.correctFixedSize();
		    		System.out.println("!!! " + (treeTablePane.getColumnHeader() == null)
		    				+" " + (treeTablePane.getRowHeader() == null));
		    		treeTablePane.getColumnHeader().repaint();
//		            treeTable.setPreferredScrollableViewportSize(treeTable.getPreferredSize());
//		            treeTablePane.setRowHeaderView(treeTable);
//		            treeTablePane.setCorner(JScrollPane.UPPER_LEFT_CORNER, treeTable.getTableHeader());
		    	}
    		}
    		lastRow = row;
    	}
    }


	/**
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide#getFieldVisibility(int)
	 */
	@Override
	public boolean[] getFieldVisibility(int recordIndex) {
		return fieldMapping.getFieldVisibility(recordIndex);
	}

	protected final int getColAdjust() {
		return STANDARD_COLUMNS - cols2skip;
	}

	/**
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide#setFieldVisibility(int, boolean[])
	 */
	@Override
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisibile) {

		fieldMapping.setFieldVisibilty(recordIndex, fieldVisibile);

		if (recordIndex == getLayoutIndex()) {
			int adj = getColAdjust();
			showFields.setFieldVisibility(tblDetails.getColumnModel(), adj, adj, fieldVisibile);
		}

		setActiveFrame(this);
	}


    private void hideColumn(int col) {
    	JTable tblDetails = getJTable();
    	TableColumn colDef = tblDetails.getColumnModel().getColumn(col);

    	if (getLayoutIndex() < layout.getRecordCount()) {
    		fieldMapping.hideColumn(getLayoutIndex(), colDef.getModelIndex() - getColAdjust());
    	}

    	tblDetails.getColumnModel().removeColumn(colDef);

    	showFields.hideColumn(colDef, col);
    }

	@Override
	public boolean isActionAvailable(int action) {

		return action == ReActionHandler.AUTOFIT_COLUMNS
			|| super.isActionAvailable(action);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.AbstractTreeFrame#getRoot()
	 */
	@Override
	public LNode getRoot() {
		return root;
	}

	/**
	 * @param recordIdx
	 * @param inRow
	 * @return
	 * @see net.sf.RecordEditor.re.file.FieldMapping#getRealColumn(int, int)
	 */
	public final int getAdjColumn(int recordIdx, int inRow) {
		return FieldMapping.getAdjColumn(fieldMapping, recordIdx, inRow) + getColAdjust();
	}
}
