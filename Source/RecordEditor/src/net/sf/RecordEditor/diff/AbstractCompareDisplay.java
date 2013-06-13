/*
 * Created on 14/05/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Support for sorting, removed unused vars
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Support for Printing, Full-Line Display and Selected views added
 * # Version 0.62 Bruce Martin 26/04/2007
 *   - Split from BaseLineFrame
 */
package net.sf.RecordEditor.diff;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.LineFrame;
import net.sf.RecordEditor.edit.display.LineList;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.LayoutCombo;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * This class displays file details on the screen.  This class has 2 subclasses
 * <ul compact>
 * <li>RecordFrame - displays on record
 * <li>RecordList - displays all records in a table format
 * </ul>
 *
 * <p>This class holds common code / loads buttons etc.
 *
 * @see LineFrame - displays on record
 * @see LineList - displays all records in a table format
 *
 * <p>
 * @author Bruce Martin
 * @version 0.56
 */
@SuppressWarnings("serial")
public abstract class AbstractCompareDisplay extends ReFrame {

	//static ImageIcon searchIcon = new ImageIcon(Common.dir + "searchEye.gif");

	//private static final int RECORDS_TO_CHECK = 30;
	public static final int USE_FULL_LIST = 1;
	public static final int USE_CHANGE_LIST = 2;

	public static final int ALL_FIELDS     = 1;
	public static final int CHANGED_FIELDS = 2;


	protected Rectangle screenSize ;
	    //Toolkit.getDefaultToolkit().getScreenSize();

	protected AbstractLayoutDetails layout;

	protected JTable tblDetails;
	private JTable alternativeTbl = null;
	private LayoutCombo layoutList;

	private JButton fullListBtn = SwingUtils.newButton("All Included Lines");
	private JButton chgListBtn  = SwingUtils.newButton("Changed Lines");
	private JButton allFieldsBtn, changedFieldsBtn ;
	protected final int prefferedIndex;

	protected BaseHelpPanel pnl = new BaseHelpPanel();

	protected TableCellRenderer[] cellRenders;

	private   int maxHeight = -1;
	protected int[] widths;


	protected final ArrayList<LineCompare> fullBefore, fullAfter, changeBefore, changeAfter;
	protected ArrayList<LineCompare> displayBefore, displayAfter;

	protected int displayType = USE_CHANGE_LIST;


	private ActionListener listner = new ActionListener() {
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public final void actionPerformed(final ActionEvent e) {

		    if (e.getSource() == layoutList) {
					setRowHeight();
					changeLayout();
			} else if (e.getSource() == fullListBtn) {
				setDisplay(USE_FULL_LIST);
			} else if (e.getSource() == chgListBtn) {
				setDisplay(USE_CHANGE_LIST);
			} else if (e.getSource() == allFieldsBtn) {
				setDisplayFields(ALL_FIELDS);
			} else if (e.getSource() == changedFieldsBtn) {
				setDisplayFields(CHANGED_FIELDS);
			}
		}

	};

    //private HeaderRender headerRender = new HeaderRender();



	/**
	 * base class for editing a file
	 *
	 * @param formType panel name
	 * @param group record layout group used to display a record
	 * @param viewOfFile current file table representation
	 * @param masterfile internal representation of a File
	 * @param primary wether the screen is a primary screen
	 */
	public AbstractCompareDisplay(final String formType,
					final String name,
					final AbstractLayoutDetails recordLayout,
			  		final ArrayList<LineCompare> before,    final ArrayList<LineCompare> after,
			        final ArrayList<LineCompare> chgBefore, final ArrayList<LineCompare> chgAfter,
	        		final boolean primary,                  final boolean allRows) {
		super("", name, "", formType, before);
		this.setPrimaryView(primary);

		layout = recordLayout;
		fullBefore   = before;
		fullAfter    = after;
		changeBefore = chgBefore;
		changeAfter  = chgAfter;

		displayBefore = chgBefore;
		displayAfter  = chgAfter;
		if (allRows) {
			displayBefore = before;
			displayAfter  = after;
			displayType = USE_FULL_LIST;
		}

		screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();

		init(primary);
		prefferedIndex = layoutList.getPreferedIndex();
		layoutList.setSelectedIndex(prefferedIndex);
	}



	/**
	 * Common Initialise
	 */
	private void init(boolean primary) {

		JPanel btnPnl = new JPanel();

		layoutList = new LayoutCombo(layout, false, false, true,  false);

//		setBtnSize(fullListBtn, chgListBtn);
		layoutList.addActionListener(listner);
		fullListBtn.addActionListener(listner);
		chgListBtn.addActionListener(listner);

		btnPnl.add(fullListBtn);
		btnPnl.add(chgListBtn);
		if (! primary) {
			allFieldsBtn  = SwingUtils.newButton("Show All Fields");
			changedFieldsBtn = SwingUtils.newButton("Show Changed Fields");

			btnPnl.add(allFieldsBtn);
			btnPnl.add(changedFieldsBtn);

			allFieldsBtn.addActionListener(listner);
			changedFieldsBtn.addActionListener(listner);
//			setBtnSize(allFieldsBtn, changedFieldsBtn);
		}

		if (super.isPrimaryView()) {
			pnl.addComponent3Lines("Layouts", getLayoutList(), btnPnl);
		} else {
			pnl.addComponent3Lines("Layouts", getLayoutList(), null);
			pnl.addLine("", btnPnl);
		}

		pnl.setHeight(BasePanel.NORMAL_HEIGHT * 2);
//		setBtnSize(fullListBtn, chgListBtn);

		//pnl.setHeight(Math.max(25, iconHeight + 8));


		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	    this.addInternalFrameListener(new InternalFrameAdapter() {
	        public void internalFrameClosed(final InternalFrameEvent e) {
	            closeWindow();
	        }
//            public void internalFrameClosing(InternalFrameEvent e) {
//                 windowClosing();
//            }
	    });
	}

//	private void setBtnSize(JButton btn1, JButton btn2) {
//
//		int width = Math.max(btn1.getWidth(), btn2.getWidth());
//		btn1.setBounds(btn1.getX(), btn1.getY(), width, btn1.getHeight());
//		btn2.setBounds(btn2.getX(), btn2.getY(), width, btn2.getHeight());
//	}


	/**
	 * setup what should be displayed - Full List / Changes
	 * @param type what to display
	 */
	public void setDisplay(int type) {

		displayType = type;
		if (type == USE_FULL_LIST) {
			displayBefore = fullBefore;
			displayAfter  = fullAfter;
		} else {
			displayBefore = changeBefore;
			displayAfter  = changeAfter;
		}

		fullListBtn.setVisible(type == USE_CHANGE_LIST);
		chgListBtn .setVisible(type == USE_FULL_LIST);
	}

	/**
	 * set display fields
	 * @param fieldDisplay
	 */
	public void setDisplayFields(int fieldDisplay) {

		allFieldsBtn.setVisible(fieldDisplay == CHANGED_FIELDS);
		changedFieldsBtn.setVisible(fieldDisplay == ALL_FIELDS);
	}

	/**
	 *
	 * @see net.sf.RecordEditor.utils.screenManager.ReFrame#windowClosing()
	 */ @Override
	public void windowClosing() {

		if (super.isPrimaryView()) {
			ReFrame[] allFrames = ReFrame.getAllFrames();
			System.out.println("closeWindow " + this.getName());

			for (int i = allFrames.length - 1; i >= 0; i--) {
				if (allFrames[i].getDocument() == fullBefore
						&& (allFrames[i] != this)) {
					allFrames[i].doDefaultCloseAction();
				}
			}
		}

		super.windowClosing();
	}
	/**
	 * Close a window
	 *
	 */
	public void closeWindow() {

		try {
			stopCellEditing();
		} catch (Exception ex) {
		}
	}





	/**
	 *  Execute standard RecordEditor actions
	 *
	 * @param action action to perform
	 */
	public void executeAction(int action) {

		stopCellEditing();

		try {
		switch (action) {
		case ReActionHandler.HELP:
			pnl.showHelp();
		break;
		case ReActionHandler.PRINT:
		    try {
		        tblDetails.print(JTable.PrintMode.NORMAL);
		    } catch (Exception e) {
                Common.logMsg("Printing failed (Printing requires Java 1.5)", e);
            }
		break;

		}
		} catch (Exception e) {
			Common.logMsg("Error Executing action:", null);
			Common.logMsg(e.getMessage(), e);
			e.printStackTrace();
		}
	}


	/**
	 * Check if action is available
	 *
	 * @param action action to be checked
	 *
	 * @return wether action is available
	 */
	public boolean isActionAvailable(final int action) {
	    boolean ret =
           (action == ReActionHandler.HELP)
        || (action == ReActionHandler.PRINT);



		return ret;
	}


	protected int getInsertBeforePosition() {
		return getStandardPosition() - 1;
	}


	/**
	 * Standard table position calculation
	 * @return position
	 */
	protected int getStandardPosition() {

		int pos = getCurrRow();
		if (pos < 0) {
			pos = displayBefore.size() - 1;
		}

		return pos;
	}



	/**
	 * Change the record Layout
	 */
	 public abstract void changeLayout();


	 /**
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getCurrRow()
	 */
	 public abstract int getCurrRow();

	 /**
	 * set the current row
	 */
	 public abstract void setCurrRow(int newRow, int layoutId, int fieldNum);




    /**
     * set the record layout based on the most common record
     * in the first 30 lines
     */
    protected final void setLayoutIdx() {

        if (layout.getRecordCount() > 0) {
            int i;
            int record2Use = 0;
            int currMax = 0;
            //int recordsToCheck = Math.min(RECORDS_TO_CHECK,
            //							  displayBefore.size());
            int[] layoutCounts = new int[layout.getRecordCount()];

 //           System.out.println("setLayoutIdx: " + fileView.getRowCount() + " " + tblDetails.getRowCount());

            for (i = 0; i < layoutCounts.length; i++) {
                layoutCounts[i] = 0;
            }

/*            for (i = 0; i < recordsToCheck; i++) {
                l = setLayoutIdx_100_getLayout4Row(i);
                if (l > 0) {
                    layoutCounts[l] += 1;
                }
            }
*/
            for (i = 0; i < layoutCounts.length; i++) {
                if (layoutCounts[i] > currMax) {
                    currMax = layoutCounts[i];
                    record2Use = i;
                }
            }

            setLayoutIndex(record2Use);
        }
    }


    /**
     * get the selected Row count
     * @return selected Row count
     */
    public int getSelectedRowCount() {
    	return tblDetails.getSelectedRowCount();
    }

	/**
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getSelectedRows()
	 */
	public int[] getSelectedRows() {
		return tblDetails.getSelectedRows();
	}


	/**
	 * Stop editing a cell
	 */
	public final void stopCellEditing() {

	    Common.stopCellEditing(tblDetails);
	}




	/**
	 * check if the format has changed
	 * @param event
	 * @return
	 */
	protected final boolean hasTheFormatChanged(TableModelEvent event) {
		boolean changed = false;

		if (event.getType() == TableModelEvent.UPDATE
				&& event.getFirstRow() < 0 && event.getLastRow() < 0) {
		}

		return changed;
	}

	/**
	 * Set the table cell height
	 *
	 */
	protected void setRowHeight() {

	    if (maxHeight > 0) {
	        tblDetails.setRowHeight(maxHeight);
	        if (alternativeTbl != null) {
	        	alternativeTbl.setRowHeight(maxHeight);
	        }
	    }
	}


	/**
	 * @see net.sf.RecordEditor.re.display.AbstractFileDisplay#getLayoutIndex()
	 */
	public final int getLayoutIndex() {
		return layoutList.getLayoutIndex();
	}


	/**
	 * Set the layout index
	 */
	 public final void setLayoutIndex(int recordIndex) {
//		System.out.println("Set Layout Index " + recordIndex);
		//Common.logMsg("Setting LayoutIndex", null);
		layoutList.setLayoutIndex(recordIndex);
	}


	/**
	 * @return the layoutList
	 */
	protected final LayoutCombo getLayoutList() {
		return layoutList;
	}





	/**
	 * @param alternativeTbl the alternativeTbl to set
	 */
	protected void setAlternativeTbl(JTable alternativeTbl) {
		this.alternativeTbl = alternativeTbl;
	}




}
