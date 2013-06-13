package net.sf.RecordEditor.edit.display.extension;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.RecordEditor.edit.display.AbstractCreateChildScreen;
import net.sf.RecordEditor.edit.display.BaseDisplay;
import net.sf.RecordEditor.edit.display.DisplayFrame;
import net.sf.RecordEditor.edit.display.Action.AutofitAction;
import net.sf.RecordEditor.edit.display.Action.GotoLineAction;
import net.sf.RecordEditor.edit.display.common.AbstractRowChangedListner;
import net.sf.RecordEditor.edit.display.util.RowChangeListner;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FieldMapping;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;
import net.sf.RecordEditor.utils.swing.FixedColumnScrollPane;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public abstract class EditPaneListScreen extends BaseDisplay
implements AbstractRowChangedListner, TableModelListener, AbstractFileDisplayWithFieldHide, AbstractCreateChildScreen {

	private static final int NUMBER_OF_CONTROL_COLUMNS = 2;
	private static final int DEFAULT_ROW_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 7 / 2;

	protected int fixedPopupCol = 1;
	protected int mainPopupCol  = 1;
	protected int popupRow      = 1;
	private int lastRow;
	protected int currChildScreenPosition;
	private HeaderRender headerRender   = new HeaderRender();
	private FixedColumnScrollPane tblScrollPane = null;
	private ButtonTableRendor tableBtn  = new ButtonTableRendor();
	private RowChangeListner keyListner, keyListnerFixed;
	protected FieldMapping fieldMapping = null;


	protected MenuPopupListener mainPopup, fixedPopupMenu;

    protected IChildScreen childScreen;


	public EditPaneListScreen(
			String formType, FileView viewOfFile,
			boolean primary, boolean addFullLine, boolean fullList,
			boolean prefered, boolean hex, int option) {
		super(formType, viewOfFile, primary, addFullLine, fullList, prefered, hex, option);

		fieldMapping = new FieldMapping(getFieldCounts());

		init_100_SetupJtables(viewOfFile);

		init_200_LayoutScreen();
	}

	/**
	 * Setup the JTables etc
	 * @param viewOfFile current file view
	 */
	@SuppressWarnings("serial")
	protected void init_100_SetupJtables(final FileView viewOfFile) {

	        ReAction sort = new ReAction(ReActionHandler.SORT, this);
	        AbstractAction editRecord
	        	= new ReAbstractAction(
	        		"Edit Record",
	        		Common.getRecordIcon(Common.ID_EDIT_RECORD_ICON)) {
	            public void actionPerformed(ActionEvent e) {
	            	newLineFrame(fileView, popupRow);
	            	//DisplayBuilder.addToScreen(getParentFrame(), new PoSingleRecordScreen(viewOfFile, popupRow));
	           }
	        };
	        AbstractAction gotoLine   = new GotoLineAction(this, fileView);



	        AbstractAction[] mainActions = {
	                sort,
	                null,
	                editRecord,
	                gotoLine,
	                null,
	                new AutofitAction(this),
	                null,
	                new ReAbstractAction("Fix Column") {
	                    public void actionPerformed(ActionEvent e) {
	                        //int col = tblDetails.getSelectedColumn();

	                        tblScrollPane.doFixColumn(mainPopupCol);
	                    };
	                },
	                new ReAbstractAction("Hide Column") {
	                    public void actionPerformed(ActionEvent e) {
	                    	hideColumn(mainPopupCol);
	                    }
	                },
	         };

	        AbstractAction[] fixedActions = {
	                sort,
	                null,
	                editRecord,
	                gotoLine,
	                null,
	                new ReAbstractAction("Unfix Column") {
	                    public void actionPerformed(ActionEvent e) {
	                       // int col = tblScrollPane.getFixedTable().getSelectedColumn();

	                        if (fixedPopupCol > 0) {
	                            //System.out.println("Un-Fix ~~ " + tblDetails.getSelectedColumn());
	                            tblScrollPane.doUnFixColumn(fixedPopupCol);
	                        }
	                    }
	                }
	        };

	        JTable tableDetails = new JTable(viewOfFile);

	        this.setJTable(tableDetails);
	        tblScrollPane = new FixedColumnScrollPane(tableDetails);

	        keyListner    = new RowChangeListner(tableDetails, this);
	        keyListnerFixed = new RowChangeListner(tblScrollPane.getFixedTable(), this);

	        mainPopup = new MenuPopupListener(mainActions, true, tableDetails) {

	 		   /**
			     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			     */
			    public void mousePressed(MouseEvent e) {
			    	super.mousePressed(e);
			    	JTable table = getJTable();

		      		int col = table.columnAtPoint(e.getPoint());
		            int row = table.rowAtPoint(e.getPoint());

		            checkForTblRowChange(row);
		            if (row >= 0 && row != table.getEditingRow()
		        	&&  col >= 0 && col != table.getEditingColumn()
		        	&& cellEditors != null && col < cellEditors.length && cellEditors[col] != null
		        	) {
		            	table.editCellAt(row, col);
		            }
			    }

	        	protected final boolean isOkToShowPopup(MouseEvent e) {
	        		JTable tblDetails = getJTable();

	                mainPopupCol = tblDetails.columnAtPoint(e.getPoint());
	                popupRow = tblDetails.rowAtPoint(e.getPoint());

	                return true;
	            }
	        };
	        mainPopup.getPopup().add(tblScrollPane.getShowFieldsMenu());

	        fixedPopupMenu = new MenuPopupListener(fixedActions, true, tblScrollPane.getFixedTable()) {
	            public void mousePressed(MouseEvent m) {
	                JTable tbl = tblScrollPane.getFixedTable();
	                int col = tbl.columnAtPoint(m.getPoint());

	                popupRow = tbl.rowAtPoint(m.getPoint());

	                checkForTblRowChange(popupRow);
	                if (col == 0) {
	                	newLineFrame(fileView, popupRow);
	                } else {
	                	super.mousePressed(m);
	                }
	            }

	            protected boolean isOkToShowPopup(MouseEvent e) {
	                JTable tbl = tblScrollPane.getFixedTable();
	                 fixedPopupCol = tbl.columnAtPoint(e.getPoint());
	                 popupRow = tbl.rowAtPoint(e.getPoint());

	                return fixedPopupCol > 0;
	            }

	        };



	        viewOfFile.setFrame(ReMainFrame.getMasterFrame());

	//        tblDetails = new JTable(viewOfFile);
	        tableDetails.addMouseListener(mainPopup);

	        initToolTips(2);

	        tableDetails.getTableHeader().addMouseListener(new HeaderSort());

	        this.setAlternativeTbl(tblScrollPane.getFixedTable());

	        actualPnl.registerComponent(tableDetails);
	        actualPnl.registerComponent(tblScrollPane.getFixedTable());
	        defColumns();

	        tblScrollPane.getFixedTable().getTableHeader().addMouseListener(new HeaderSort());
			tblScrollPane.getFixedTable().addMouseListener(fixedPopupMenu);

	       // if (viewOfFile.getLayout().isOkToAddAttributes()) {
	       	viewOfFile.getBaseFile().addTableModelListener(this);
	       // }

	       	this.setRowHeight(DEFAULT_ROW_HEIGHT);
	       	setCellEditorRendor(tblScrollPane.getFixedTable());
	    }

	protected void init_200_LayoutScreen() {

		this.actualPnl.addReKeyListener(new DelKeyWatcher());
	    //actualPnl.setHelpURL(Common.formatHelpURL(Common.HELP_RECORD_TABLE));

	    actualPnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
	                     BasePanel.FULL, BasePanel.FULL,
	                     tblScrollPane);


	    setLayoutIdx();

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

		Common.calcColumnWidths(tblDetails, 2);
	}

	@Override
	public void fireLayoutIndexChanged() {

	}

	@Override
	protected int getInsertAfterPosition() {
		return getStandardPosition();
	}

	@Override
	public int getCurrRow() {
		if (tblDetails.getSelectedRowCount() > 0) {
            return tblDetails.getSelectedRow();
        }
        return -1;
	}


    /* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getPopupPosition()
	 */
	@Override
	protected int getPopupPosition() {
		return popupRow;
	}

	private void hideColumn(int col) {
	 	tblScrollPane.doHideColumn(col);
	}

	/**
	 * This method defines the Column Headings
	 */
	private void defColumns() {
		defColumns(getJTable(), fileView, tblScrollPane);
	}

	private JTable defColumns(JTable tbl, FileView view, FixedColumnScrollPane scrollPane) {
	    TableColumnModel tcm = tbl.getColumnModel();

	    defineColumns(tbl, view.getColumnCount(), 2, 0);

	    for (int i = 2; i < tcm.getColumnCount(); i++) {
	        tcm.getColumn(i).setHeaderRenderer(headerRender);
	    }


	    setKeylistner(tbl);

	    if (scrollPane != null) {
		   int rowCount = view.getRowCount();
		   int width = 5;
		   //System.out.println("Setup fixed columns ... ");
	       scrollPane.setFixedColumns(NUMBER_OF_CONTROL_COLUMNS);
	       TableColumn tc = scrollPane.getFixedTable().getColumnModel().getColumn(0);
	       tc.setCellRenderer(tableBtn);
	       tc.setPreferredWidth(5);
	       if ( scrollPane.getFixedTable().getColumnModel().getColumnCount() <= 1) {
	    	   Common.logMsg("Error No Fields defined in the layout !!!", null);
	       } else {
	           tc = scrollPane.getFixedTable().getColumnModel().getColumn(1);
	           if (rowCount > 10000000) {
	        	   width = 8;
	           } else if (rowCount > 1000000) {
	        	   width = 7;
	           } else if (rowCount > 100000) {
	        	   width = 6;
	           }
	           tc.setPreferredWidth(SwingUtils.STANDARD_FONT_WIDTH * width);
	           tc.setResizable(false);

	           scrollPane.correctFixedSize();
	       }
	       setKeylistner(scrollPane.getFixedTable());
	    }

	  	this.setRowHeight(DEFAULT_ROW_HEIGHT);
	    setCellEditorRendor(tbl);

	    return tbl;
	}

	private void setCellEditorRendor(JTable tbl) {

//	    TableColumnModel tcm = tbl.getColumnModel();
//
//	    for (int i = 0; i < tcm.getColumnCount(); i++) {
//	    	if (tcm.getColumn(i).getModelIndex() > 1) {
//		        tcm.getColumn(i).setCellRenderer(textRendor);
//		        tcm.getColumn(i).setCellEditor(new TextAreaTableCellEditor());
////		        System.out.println(i + " --> " + tbl.getColumnName(i) + " -- " + textRendor.getClass().getSimpleName()
////		        		+ " " + TextAreaTableCellEditor.class.getSimpleName());
//	    	}
//	    }
	}

	protected void setKeylistner(JTable tbl) {

		if (tbl == null) {

		} else if (childScreen != null) {
	    	if (tbl == tblDetails) {
	    		tbl.addKeyListener(keyListner);
	    	} else {
	    		tbl.addKeyListener(keyListnerFixed);
	    	}
	    	lastRow = - 1;
	    	checkForTblRowChange(tbl.getSelectedRow());
	    } else if (tbl == tblDetails) {
	    	tbl.removeKeyListener(keyListner);
	    } else {
	    	tbl.removeKeyListener(keyListnerFixed);
	    }
	}

	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
	    if ((newRow >= 0) && newRow <= fileView.getRowCount()) {
	    	checkForTblRowChange(newRow);
	        if ((getCurrRow() != newRow)) {
	            fileView.fireTableDataChanged();
	            tblDetails.changeSelection(newRow, 1, false, false);
	        }

	        if (fieldNum >= 0 && isCurrLayoutIdx(layoutId))  {
	            stopCellEditing();
	            tblDetails.editCellAt(newRow, FieldMapping.getAdjColumn(fieldMapping, layoutId, fieldNum));
	            //System.out.println("Found " + newRow + " " + fieldNum);
	        }
	    }
	}

	@Override
	public void tableChanged(TableModelEvent e) {

	}

	@Override
	public void checkForTblRowChange(int row) {
		if (lastRow != row) {
	    	if (childScreen != null) {
	    		childScreen.setCurrRow(row);
	    	}
		}
		lastRow = row;
	}

	/**
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide#getFieldVisibility(int)
	 */
	@Override
	public boolean[] getFieldVisibility(int recordIndex) {
		return tblScrollPane.getFieldVisibility(NUMBER_OF_CONTROL_COLUMNS);
	}

	/**
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide#setFieldVisibility(int, boolean[])
	 */
	@Override
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisibility) {
		fieldMapping.setFieldVisibilty(recordIndex, fieldVisibility);

		tblScrollPane.setFieldVisibility(NUMBER_OF_CONTROL_COLUMNS, fieldVisibility);

		DisplayFrame parentFrame = getParentFrame();
		if (parentFrame != null) {
			parentFrame.setToActiveFrame();
			//parentFrame.revalidate();
		}
	}


	public int getCurrentChildScreenPostion() {
		return currChildScreenPosition;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#removeChildScreen()
	 */
	@Override
	public void removeChildScreen() {
		if (childScreen != null) {
			childScreen.doClose();
		}
		childScreen = null;
		setKeylistner(tblDetails);
		setKeylistner(tblScrollPane.getFixedTable());
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getChildScreen()
	 */
	@Override
	public AbstractFileDisplay getChildScreen() {
		return childScreen;
	}

	   /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.REPEAT_RECORD_POPUP) {
            fileView.repeatLine(popupRow);
            setCurrRow(popupRow+1, 0, 0);
        } else {
        	super.executeAction(action);
        }
    }


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
       return (action == ReActionHandler.REPEAT_RECORD_POPUP) || super.isActionAvailable(action);
	}

	@Override
    public void insertLine(int adj) {
    	if (childScreen == null) {
    		super.insertLine(adj);
    	} else {
    		int pos = fileView.newLine(getInsertAfterPosition(), adj);
    		setCurrRow(pos, 0, 0);
    	}
    }
}