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
import net.sf.RecordEditor.edit.display.models.BaseLineModel;
import net.sf.RecordEditor.edit.display.models.Line2ColModel;
import net.sf.RecordEditor.edit.display.util.ChooseCellEditor;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
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

	/**
	 * Display each line as a column in the table with fields going down the screen
	 * 
	 * @param viewOfFile file view
	 */
	public LinesAsColumns(FileView<?> viewOfFile) {
		super("Column Table", viewOfFile, viewOfFile == viewOfFile.getBaseFile(), true);
		
	    init_100_SetupJtables(viewOfFile);

	    pnl.setHelpURL(Common.formatHelpURL(Common.HELP_COLUMN_VIEW));

	    pnl.addComponent(1, 5, BasePanel.FILL, BasePanel.GAP,
	                         BasePanel.FULL, BasePanel.FULL,
	                         tblScrollPane);


	    addMainComponent(pnl);


	    setBounds(1, 1,
	                  screenSize.width  - 1,
	                  getHeight());
	    setLayoutIdx();

	    setVisible(true);
	}

	/**
     * Define the JTables etc
     * @param viewOfFile current file view
     */
    private void init_100_SetupJtables(final FileView<?> viewOfFile) {

    	Line2ColModel mdl = new Line2ColModel(viewOfFile); 
    	JTable fixedTbl;
    	
		setModel(mdl);
		

  //      ReAction sort = new ReAction(ReActionHandler.SORT, this);
        AbstractAction editRecord = new AbstractAction("Edit Record") {
            public void actionPerformed(ActionEvent e) {
            	if (popupCol >= 0) {
            		new LineFrame(fileView, popupCol);
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
	            new AbstractAction("Hide Column") {
                    public void actionPerformed(ActionEvent e) {
                    	hideRow(popupRow);
                      }
                },

        };
        
        JTable tableDetails = new JTable(mdl);
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
        

        initToolTips();

        setStandardColumnWidths();
        tblScrollPane = new FixedColumnScrollPane(tableDetails, BaseLineModel.FIRST_DATA_COLUMN);
        fixedTbl = tblScrollPane.getFixedTable();

        
        pnl.registerComponent(tableDetails);
        pnl.registerComponent(fixedTbl);
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
	@SuppressWarnings("unchecked")
	@Override
	public void setNewLayout(AbstractLayoutDetails newLayout) {
		super.setNewLayout(newLayout);
		setLayoutIdx();
		//getModel().layoutChanged(newLayout);
		getModel().fireTableStructureChanged();
		tblScrollPane.setFixedColumns(BaseLineModel.FIRST_DATA_COLUMN);
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
 
        for (int i =  Math.min(BaseLineModel.FIRST_DATA_COLUMN, tbl.getColumnCount()) - 1; i >= 0; i--) {
        	tc =  tbl.getColumnModel().getColumn(i);
            if (tc.getModelIndex() < BaseLineModel.FIRST_DATA_COLUMN) {
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
	 * @see net.sf.RecordEditor.edit.display.common.AbstractFileDisplay#getSelectedRows()
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
    	   JTable tbl = getJTable();
           if ((getCurrRow() != newRow)) {
                getModel().fireTableDataChanged();
                tbl.changeSelection(BaseLineModel.FIRST_DATA_COLUMN, newRow, false, false);
           }
           if (fieldNum > 0  && getLayoutIndex() == layoutId) {
                stopCellEditing();
                tbl.editCellAt(fieldNum + BaseLineModel.FIRST_DATA_COLUMN, newRow);
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
}
