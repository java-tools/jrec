/*
 * @Author Bruce Martin
 * Created on 13/01/2007
 *
 * Purpose:
 *   Create a popup menu listner (with edit options)
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - added Filter, Save & save_as options
 */
package net.sf.RecordEditor.utils;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import net.sf.RecordEditor.re.script.VelocityPopup;
import net.sf.RecordEditor.re.script.XsltPopup;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReActionActiveScreen;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Create a popup menu listner (with edit options)
 *
 * @author Bruce Martin
 *
 */
public class MenuPopupListener extends MouseAdapter {
    private JPopupMenu popup = new JPopupMenu();

    private JTable tbl = null;

    private int popupCol, popupRow;

    private static final ReActionActiveScreen COPY_RECORDS    		= new ReActionActiveScreen(ReActionHandler.COPY_RECORD);
    private static final ReActionActiveScreen CUT_RECORDS     		= new ReActionActiveScreen(ReActionHandler.CUT_RECORD);
    private static final ReActionActiveScreen PASTE_RECORDS   		= new ReActionActiveScreen(ReActionHandler.PASTE_RECORD_POPUP);
    private static final ReActionActiveScreen PASTE_RECORDS_PRIOR	= new ReActionActiveScreen(ReActionHandler.PASTE_RECORD_PRIOR_POPUP);
    private static final ReActionActiveScreen DELETE_RECORDS  		= new ReActionActiveScreen(ReActionHandler.DELETE_RECORD_POPUP);
    private static final ReActionActiveScreen INSERT_RECORDS  		= new ReActionActiveScreen(ReActionHandler.INSERT_RECORDS_POPUP);
    private static final ReActionActiveScreen INSERT_RECORD_PRIOR	= new ReActionActiveScreen(ReActionHandler.INSERT_RECORD_PRIOR_POPUP);
	private static final ReActionActiveScreen FIND_ACTION			= new ReActionActiveScreen(ReActionHandler.FIND);
	private static final ReActionActiveScreen FILTER_ACTION         = new ReActionActiveScreen(ReActionHandler.FILTER);
	private static final ReActionActiveScreen SAVE                  = new ReActionActiveScreen(ReActionHandler.SAVE);
	private static final ReActionActiveScreen SAVE_AS               = new ReActionActiveScreen(ReActionHandler.SAVE_AS);
	private static final ReActionActiveScreen EXPORT                = new ReActionActiveScreen(ReActionHandler.EXPORT);
	private static final ReActionActiveScreen SAVE_AS_CSV           = new ReActionActiveScreen(ReActionHandler.EXPORT_AS_CSV);
	private static final ReActionActiveScreen SAVE_AS_FIXED         = new ReActionActiveScreen(ReActionHandler.EXPORT_AS_FIXED);
	private static final ReActionActiveScreen SAVE_AS_HTML          = new ReActionActiveScreen(ReActionHandler.EXPORT_AS_HTML);
	private static final ReActionActiveScreen SAVE_AS_HTML_TBLS     = new ReActionActiveScreen(ReActionHandler.EXPORT_AS_HTML_TBL_PER_ROW);
	private static final ReActionActiveScreen SAVE_AS_HTML_TREE     = new ReActionActiveScreen(ReActionHandler.EXPORT_HTML_TREE);
	private static final ReActionActiveScreen SAVE_AS_VELOCITY      = new ReActionActiveScreen(ReActionHandler.EXPORT_VELOCITY);
	private static final ReActionActiveScreen EXPORT_VIA_XSLT       = new ReActionActiveScreen(ReActionHandler.EXPORT_XSLT);
	private static final ReActionActiveScreen REPEAT_RECORD         = new ReActionActiveScreen(ReActionHandler.REPEAT_RECORD_POPUP);
	private static final ReActionActiveScreen COPY_CELLS		    = new ReActionActiveScreen(ReActionHandler.COPY_SELECTED_CELLS);
	private static final ReActionActiveScreen PASTE_OVER			= new ReActionActiveScreen(ReActionHandler.PASTE_TABLE_OVER_SELECTION);
	private static final ReActionActiveScreen PASTE_INSERT_CELLS 	= new ReActionActiveScreen(ReActionHandler.PASTE_INSERT_CELLS);
	private static final ReActionActiveScreen DELETE_SELECTED_CELLS = new ReActionActiveScreen(ReActionHandler.DELETE_SELECTED_CELLS);
	private static final ReActionActiveScreen CUT_SELECTED_CELLS 	= new ReActionActiveScreen(ReActionHandler.CUT_SELECTED_CELLS);
	private static final ReActionActiveScreen CLEAR_SELECTED_CELLS 	= new ReActionActiveScreen(ReActionHandler.CLEAR_SELECTED_CELLS);

	
    /**
     * Create a popup menu listner (with edit options)
     *
     * @param userAction any user actions
     * @param isFileEdit wether to add file editing actions action
     * @param table table being acted on
     */
	public MenuPopupListener(final Action[] userAction, final boolean isFileEdit, final JTable table) {
		this(userAction, isFileEdit, table, null, false);
	}

		
    /**
     * Create a popup menu listner (with edit options)
     *
     * @param userAction any user actions
     * @param isFileEdit wether to add file editing actions action
     * @param table table being acted on
     * @param addCellActions add cell actions
     */
    @SuppressWarnings("serial")
	public MenuPopupListener(
			final Action[] userAction, final boolean isFileEdit, final JTable table,
			JMenuItem extraAction, boolean addCellActions) {
        super();

        tbl = table;
	    popup.add(COPY_RECORDS);
	    popup.add(CUT_RECORDS);
	    popup.add(PASTE_RECORDS_PRIOR);
	    popup.add(PASTE_RECORDS);
	    popup.add(INSERT_RECORD_PRIOR);
	    popup.add(INSERT_RECORDS);
	    popup.add(DELETE_RECORDS);

	    if (isFileEdit) {
	    	JMenu saveMenu = SwingUtils.newMenu("Save ...");
	    	JMenu selectedCellsMenu = SwingUtils.newMenu("Cell Actions");

	        popup.add(REPEAT_RECORD);

//	        if (Common.OPTIONS.highlightEmpty.isSelected()) {
		        popup.add(new ReAbstractAction("Clear Field") {
					@Override public void actionPerformed(ActionEvent arg0) {
						if (popupRow >= 0) {
							tbl.setValueAt(Common.MISSING_VALUE, popupRow, popupCol);
						}
					}
		        });
//		        selectedCellsMenu.add(new ReAbstractAction("Copy Selected Cells") {
//					@Override public void actionPerformed(ActionEvent arg0) {
//						SwingUtils.copySelectedCells(tbl);
//					}
//		        });
		        selectedCellsMenu.add(COPY_CELLS);
		        selectedCellsMenu.add(PASTE_OVER);
//		        ReAbstractAction clearSelectedAction = new ReAbstractAction("Clear Selected Cells") {
//					@Override public void actionPerformed(ActionEvent arg0) {
//						int[] selectedRows = tbl.getSelectedRows();
//						int[] selectedColumns = tbl.getSelectedColumns();
//						if (selectedRows != null && selectedColumns != null) {
//							for (int row : selectedRows) {
//								for (int col : selectedColumns) {
//									tbl.setValueAt(Common.MISSING_VALUE, row, col);
//								}
//							}
//						}
//					}
//		        };
		        if (addCellActions) {
		        	selectedCellsMenu.add(PASTE_INSERT_CELLS);
		        	//selectedCellsMenu.add(clearSelectedAction);
		        	selectedCellsMenu.add(CUT_SELECTED_CELLS);
		        	selectedCellsMenu.add(DELETE_SELECTED_CELLS);
		        //} else {
		        }
	        	selectedCellsMenu.add(CLEAR_SELECTED_CELLS);

		        popup.add(selectedCellsMenu);
		        
		        if (extraAction != null) {
		        	popup.add(extraAction);
		        }
//	        }
		        

	        popup.addSeparator();
	        popup.add(FIND_ACTION);
	        popup.add(FILTER_ACTION);
	        popup.addSeparator();
	        popup.add(saveMenu);

	        saveMenu.add(SAVE);
	        saveMenu.add(SAVE_AS);
	        //if (Common.isVelocityAvailable()) {
	        saveMenu.addSeparator();
	        //}

	        saveMenu.add(EXPORT);
	        saveMenu.add(SAVE_AS_CSV);
	        saveMenu.add(SAVE_AS_FIXED);
	        saveMenu.add(SAVE_AS_HTML);
	        saveMenu.add(SAVE_AS_HTML_TBLS);
	        saveMenu.add(SAVE_AS_HTML_TREE);

	        if (Common.isVelocityAvailable()) {
	        	saveMenu.add(SAVE_AS_VELOCITY);
	        	saveMenu.add(VelocityPopup.getPopup());
	        }

	        if (Common.OPTIONS.xsltAvailable.isSelected()) {
	        	saveMenu.add(EXPORT_VIA_XSLT);
	        	saveMenu.add(new XsltPopup());
	        }
	    }

	    if (userAction != null) {
	        popup.addSeparator();

	        addUserActions(userAction);
	    }
    }

    public MenuPopupListener() {

    }

	public MenuPopupListener(final Action userAction) {
		popup.add(userAction);
    }

    /**
     * Menu with no default options
     * @param userAction user actions
     */
    public MenuPopupListener(final Action[] userAction) {
    	addUserActions(userAction);
    }

    protected final void addUserActions(final Action[] userAction) {
	    if (userAction != null) {
	        for (int i = 0; i < userAction.length; i++) {
	            if (userAction[i] == null) {
	        	    popup.addSeparator();
	            } else {
	                popup.add(userAction[i]);
	            }
	        }
	    }
    }

    /**
     * @see MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
      	if (tbl != null) {
       		if (! tbl.hasFocus()) {
       	   		tbl.requestFocusInWindow();
       		}
      	}
        maybeShowPopup(e);
    }

    /**
     * @see MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
    }

    /**
     * show popup if it is a popup request
     * @param e the mouse event that triggered this action
     */
    private void maybeShowPopup(MouseEvent e) {
         if (e != null && e.isPopupTrigger() && isOkToShowPopup(e)) {
        	 int x = e.getX(),
        	     y = e.getY();
        	 popupRow = -1;
        	 if (tbl != null) {
        		popupCol = tbl.columnAtPoint(e.getPoint());
             	popupRow = tbl.rowAtPoint(e.getPoint());
        	 }
        	 JPopupMenu popupMenu = getPopup();
        	 if (Common.TEST_MODE) {
        		 try {
	        		 Dimension d = popupMenu.getPreferredSize();
	        		 int vpHeight = tbl.getParent().getHeight();
	        		 if ( y + d.height > vpHeight) {
	        			 y = Math.max(1, vpHeight - d.height);
	        		 }
        		 } catch (Exception exc) {
				 }
        	 }

             popupMenu.show(e.getComponent(),
                       x, y);
        }
    }

    /**
     * Check if it is ok to show the popup menu
     * @param e the mouse event that triggered this action
     * @return whether to show the popup or not
     */
    protected boolean isOkToShowPopup(MouseEvent e) {
        return true;
    }


    /**
     * @return Returns the popup.
     */
    public JPopupMenu getPopup() {
        return popup;
    }

    /**
	 * @return the popupRow
	 */
	public int getPopupRow() {
		return popupRow;
	}

	/**
	 * @return the popupCol
	 */
	public int getPopupCol() {
		return popupCol;
	}


	/**
	 * @param table the tbl to set
	 */
	public final void setTable(JTable table) {
		this.tbl = table;
	}


    /**
     * Get Popup Menu listner
     * @param userAction list of user actions
     * @param addFind wether to add find button
     * @return the mouse adapter
     */
    public static MouseAdapter getEditMenuPopupListner(final Action[] userAction,
    		final boolean addFind, final JTable table) {
        return new MenuPopupListener(userAction, addFind, table);
    }


}
