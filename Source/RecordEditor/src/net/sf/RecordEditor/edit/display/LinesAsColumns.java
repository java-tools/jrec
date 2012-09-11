/**
 *
 */
package net.sf.RecordEditor.edit.display;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.Action.AutofitAction;
import net.sf.RecordEditor.edit.display.models.Line2ColModel;
import net.sf.RecordEditor.edit.display.util.ChooseCellEditor;
import net.sf.RecordEditor.re.file.FieldMapping;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FixedColumnScrollPane;

/**
 * Display each line as a column in the table with fields going down the screen
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class LinesAsColumns extends BaseLineAsColumn implements TableModelListener {


	   private FixedColumnScrollPane tblScrollPane = null;
	   private int popupCol = 1;
	   private int popupRow;
	   private Line2ColModel tblMdl;

	/**
	 * Display each line as a column in the table with fields going down the screen
	 *
	 * @param viewOfFile file view
	 */
	protected LinesAsColumns(FileView<?> viewOfFile) {
		super("Column Table", viewOfFile, viewOfFile == viewOfFile.getBaseFile(), true, true);

	    init_100_SetupJtables(viewOfFile);

	    actualPnl.setHelpURL(Common.formatHelpURL(Common.HELP_COLUMN_VIEW));

	    actualPnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
	                         BasePanel.FULL, BasePanel.FULL,
	                         tblScrollPane);
	}



	@Override
	public void setScreenSize(boolean mainframe) {
		DisplayFrame parentFrame = getParentFrame();

		parentFrame.bldScreen();

		if (mainframe) {
			parentFrame.setBounds(1, 1,
		                  screenSize.width  - 1,
		                  parentFrame.getHeight());
		}
	    setLayoutIdx();

	    parentFrame.setVisible(true);
	    super.actualPnl.addReKeyListener(new DelKeyWatcher());

	}



	/**
     * Define the JTables etc
     * @param viewOfFile current file view
     */
    private void init_100_SetupJtables(final FileView<?> viewOfFile) {

    	tblMdl = new Line2ColModel(viewOfFile);
    	JTable fixedTbl;

		setModel(tblMdl);


  //      ReAction sort = new ReAction(ReActionHandler.SORT, this);
        AbstractAction editRecord = new ReAbstractAction("Edit Record") {
            public void actionPerformed(ActionEvent e) {
            	if (popupCol >= 0) {
            		newLineDisp(fileView, popupCol);
            	}
           }
        };
        //MouseAdapter popupListner;
        AbstractAction[] mainActions = {
 //               sort,
 //               null,
                editRecord,
                null,
 /*              new AbstractAction("Fix Column") {
                    public void actionPerformed(ActionEvent e) {
                        //int col = tblDetails.getSelectedColumn();

                        tblScrollPane.doFixColumn(mainPopupCol);
                    };
                },*/
                new AutofitAction(this),
                null,
	            new ReAbstractAction("Hide Column") {
                    public void actionPerformed(ActionEvent e) {
                    	hideRow(popupRow);
                      }
                },

        };

        JTable tableDetails = new JTable(tblMdl);
        setJTable(tableDetails);

        popupListner = //MenuPopupListener.getEditMenuPopupListner(mainActions);
        new MenuPopupListener(mainActions, true, tableDetails) {

		    public void mousePressed(MouseEvent e) {
		    	super.mousePressed(e);
		    	JTable tbl = getJTable();

	      		int col = tbl.columnAtPoint(e.getPoint());
	            popupRow = tbl.rowAtPoint(e.getPoint());

	            if (popupRow >= 0 && popupRow != tbl.getEditingRow()
	        	&&  col >= 0 && col != tbl.getEditingRow()
	        	&& cellEditors != null && cellEditors[col] != null
	        	) {
	            	getJTable().editCellAt(popupRow, col);
	            }

		    }

           protected boolean isOkToShowPopup(MouseEvent e) {
                //mainPopupCol = tblDetails.columnAtPoint(e.getPoint());
            	try {
                popupCol = getJTable().getColumnModel().getColumnIndexAtX(e.getPoint().x);
                		//tblDetails.columnAtPoint(e.getPoint()));
                //System.out.println("Is Ok || Column ~~ " + popupCol);
            	} catch (Exception ex) {
					ex.printStackTrace();
				}
                return true;
            }
        };

        viewOfFile.setFrame(ReMainFrame.getMasterFrame());

       //viewOfFile.setFrame(ReMainFrame.getMasterFrame());

        tableDetails.addMouseListener(popupListner);
        tableDetails.setColumnSelectionAllowed(true);
        tableDetails.setRowSelectionAllowed(true);


        //initToolTips(0);

        setStandardColumnWidths(4);
        tblScrollPane = new FixedColumnScrollPane(tableDetails, tblMdl.firstDataColumn);
        fixedTbl = tblScrollPane.getFixedTable();


        actualPnl.registerComponent(tableDetails);
        actualPnl.registerComponent(fixedTbl);
        super.setAlternativeTbl(fixedTbl);
        setColWidths();


        fixedTbl.addMouseListener(new MenuPopupListener(null, true, null) {
            public void mousePressed(MouseEvent m) {
                JTable tbl = tblScrollPane.getFixedTable();
                popupCol = tbl.columnAtPoint(m.getPoint());

               	super.mousePressed(m);
            }

            protected boolean isOkToShowPopup(MouseEvent e) {
                JTable tbl = tblScrollPane.getFixedTable();
                 int fixedPopupCol = tbl.columnAtPoint(e.getPoint());
                 popupCol = fixedPopupCol;


                return fixedPopupCol > 0;
            }
        });

        if (viewOfFile.getLayout().isOkToAddAttributes()) {
        	viewOfFile.getBaseFile().addTableModelListener(this);
        }
    }


	/*
	 * (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseLineAsColumn#newLayout(net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setNewLayout(AbstractLayoutDetails newLayout) {
		super.setNewLayout(newLayout);
		setLayoutIdx();
		//getModel().layoutChanged(newLayout);
		getModel().fireTableStructureChanged();
		tblScrollPane.setFixedColumns(tblMdl.firstDataColumn);
//		getModel().fireTableDataChanged();
	}

	/*
	 * (non-Javadoc) This method sets the column sizes in the record layout
	 * @see net.sf.RecordEditor.edit.display.BaseLineAsColumn#setColWidths()
	 */
	@Override
	protected final void setColWidths() {

		TableColumn tc;
		JTable tbl = getJTable();

        for (int i =  Math.min(tblMdl.firstDataColumn, tbl.getColumnCount()) - 1; i >= 0; i--) {
        	tc =  tbl.getColumnModel().getColumn(i);
            if (tc.getModelIndex() < tblMdl.firstDataColumn) {
        		tbl.getColumnModel().removeColumn(tc);
        	}
        }

        if (cellRenders != null || cellEditors != null) {
         	for (int i =  0;  i < tbl.getColumnCount(); i++) {
	        	tc =  tbl.getColumnModel().getColumn(i);
	            if (cellRenders != null) {
	            	tc.setCellRenderer(render);
	            	//System.out.println("Lineascolumn Render: ");
	        	}
	            if (cellEditors != null) {
	            	tc.setCellEditor(new ChooseCellEditor(tbl, cellEditors));
	            }
        	}
        }

        if (tblScrollPane != null) {
        	 Common.calcColumnWidths(tbl, 1);
        }
	}



	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getCurrRow()
	 */
	@Override
	public int getCurrRow() {
		int pos = 0;
		JTable tableDetails = getJTable();

	    if (tableDetails.getSelectedColumnCount() > 0) {
	        pos = tableDetails.getSelectedColumn();
		}

		return pos;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getStandardPosition()
	 */
	@Override
	protected final int getStandardPosition() {
		JTable tbl = getJTable();
		int pos = fileView.getRowCount() - 1;
	    if (tbl.getSelectedColumnCount() > 0) {
	        pos = tbl.getSelectedColumn();
		}

		return pos;
	}

   /**
     * get the selected Row count
     * @return selected Row count
     */
    public int getSelectedRowCount() {
    	return tblDetails.getSelectedColumnCount();
    }

	/**
	 * @see net.sf.RecordEditor.re.script.AbstractFileDisplay#getSelectedRows()
	 */
	public int[] getSelectedRows() {
		return getJTable().getSelectedColumns();
	}


	/**
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#setCurrRow(int, int, int)
	 */
	@Override
	public void setCurrRow(int newRow, int layoutId, int fieldNum) {
       if ((newRow >= 0) && newRow <= fileView.getRowCount()) {
    	   int fNo = FieldMapping.getAdjColumn(getModel().getFieldMapping(), layoutId, fieldNum);
    	   JTable tbl = getJTable();
           //if ((getCurrRow() != newRow)) {
                getModel().fireTableDataChanged();
                tbl.changeSelection(fNo, newRow, false, false);
           //}
           if (fieldNum > 0  && getLayoutIndex() == layoutId) {
                stopCellEditing();
                tbl.editCellAt(
                		fNo,
                		newRow);
           }
        }
	}

	/**
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent event) {

		if (super.hasTheFormatChanged(event)) {
			getModel().fireTableDataChanged();
		}
	}

	@Override
	public boolean isActionAvailable(int action) {

		return action == ReActionHandler.AUTOFIT_COLUMNS
			|| super.isActionAvailable(action);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.BaseDisplay#getNewDisplay(net.sf.RecordEditor.edit.file.FileView)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected BaseDisplay getNewDisplay(FileView view) {
		return new LinesAsColumns(view);
	}

}
