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

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReActionActiveScreen;


/**
 * Create a popup menu listner (with edit options)
 *
 * @author Bruce Martin
 *
 */
public class MenuPopupListener extends MouseAdapter {
    private JPopupMenu popup = new JPopupMenu();
    
    private JTable tbl= null;
    
    private int popupCol, popupRow;

    private static final ReActionActiveScreen COPY_RECORDS        = new ReActionActiveScreen(ReActionHandler.COPY_RECORD);
    private static final ReActionActiveScreen CUT_RECORDS         = new ReActionActiveScreen(ReActionHandler.CUT_RECORD);
    private static final ReActionActiveScreen PASTE_RECORDS       = new ReActionActiveScreen(ReActionHandler.PASTE_RECORD);
    private static final ReActionActiveScreen PASTE_RECORDS_PRIOR = new ReActionActiveScreen(ReActionHandler.PASTE_RECORD_PRIOR);
    private static final ReActionActiveScreen DELETE_RECORDS      = new ReActionActiveScreen(ReActionHandler.DELETE_RECORD);
    private static final ReActionActiveScreen INSERT_RECORDS      = new ReActionActiveScreen(ReActionHandler.INSERT_RECORDS);
	private static final ReActionActiveScreen FIND_ACTION         = new ReActionActiveScreen(ReActionHandler.FIND);
	private static final ReActionActiveScreen FILTER_ACTION       = new ReActionActiveScreen(ReActionHandler.FILTER);
	private static final ReActionActiveScreen SAVE                = new ReActionActiveScreen(ReActionHandler.SAVE);
	private static final ReActionActiveScreen SAVE_AS             = new ReActionActiveScreen(ReActionHandler.SAVE_AS);
	private static final ReActionActiveScreen SAVE_AS_HTML        = new ReActionActiveScreen(ReActionHandler.SAVE_AS_HTML);
	private static final ReActionActiveScreen SAVE_AS_HTML_TBLS   = new ReActionActiveScreen(ReActionHandler.SAVE_AS_HTML_TBL_PER_ROW);
	private static final ReActionActiveScreen SAVE_AS_HTML_TREE   = new ReActionActiveScreen(ReActionHandler.SAVE_AS_HTML_TREE);
	private static final ReActionActiveScreen SAVE_AS_VELOCITY    = new ReActionActiveScreen(ReActionHandler.SAVE_AS_VELOCITY);
	private static final ReActionActiveScreen REPEAT_RECORD       = new ReActionActiveScreen(ReActionHandler.REPEAT_RECORD);

    /**
     * Create a popup menu listner (with edit options)
     *
     * @param userAction any user actions
     * @param isFileEdit wether to add file editing actions action
     */
    public MenuPopupListener(final Action[] userAction, final boolean isFileEdit, final JTable table) {
        super();
        
        tbl = table;
	    popup.add(COPY_RECORDS);
	    popup.add(CUT_RECORDS);
	    popup.add(PASTE_RECORDS);
	    popup.add(PASTE_RECORDS_PRIOR);
	    popup.add(INSERT_RECORDS);
	    popup.add(DELETE_RECORDS);

	    if (isFileEdit) {
	    	JMenu saveMenu = new JMenu("Save ...");
	    	
	        popup.add(REPEAT_RECORD);
	        
	        if (Common.isHighlightEmpty()) {
		        popup.add(new AbstractAction("Clear Field") {
	
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (popupRow >= 0) {
							tbl.setValueAt(Common.MISSING_VALUE, popupRow, popupCol);
						}
					}
		        });
	        }
	        
	        popup.addSeparator();
	        popup.add(FIND_ACTION);
	        popup.add(FILTER_ACTION);
	        popup.addSeparator();
	        popup.add(saveMenu);
	        
	        saveMenu.add(SAVE);
	        saveMenu.add(SAVE_AS);
	        if (Common.isVelocityAvailable()) {
	        	saveMenu.addSeparator();
	        }
	        saveMenu.add(SAVE_AS_HTML);
	        saveMenu.add(SAVE_AS_HTML_TBLS);
	        saveMenu.add(SAVE_AS_HTML_TREE);
	        if (Common.isVelocityAvailable()) {
	        	saveMenu.add(SAVE_AS_VELOCITY);
	        	saveMenu.add(new VelocityPopup());
	        }
	    }
	    
	    if (userAction != null) {
	        popup.addSeparator();

	        addUserActions(userAction);
	    }
    }
 
	public MenuPopupListener(final Action userAction) {
    	Action[] actions = {userAction};
    	addUserActions(actions);
    }

    /**
     * Menu with no default options
     * @param userAction user actions
     */
    public MenuPopupListener(final Action[] userAction) {
    	addUserActions(userAction);
    }
   
    private void addUserActions(final Action[] userAction) {
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
         if (e.isPopupTrigger() && isOkToShowPopup(e)) {
        	 popupRow = -1;
        	 if (tbl != null && e != null) {
        		popupCol = tbl.columnAtPoint(e.getPoint());
             	popupRow = tbl.rowAtPoint(e.getPoint());
        	 }

            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

    /**
     * Check if it is ok to show the popu menu
     * @param e the mouse event that triggered this action
     * @return wether to show the popu or not
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
