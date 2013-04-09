/*
 * Created on 21/09/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JInternalFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Improved mouse over messages
 *   - New EditChild and NewChild buttons added +
 *     code to hide / unhide buttons
 */
package net.sf.RecordEditor.layoutEd.panels;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.BasePanel;




/**
 * This class implements Insert/Delete Buttons for a JTable & DBModel
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class TableUpdatePnl<record extends AbsRecord> extends javax.swing.JPanel implements  ReActionHandler {

	private BasePanel bPanel;

	@SuppressWarnings("rawtypes")
	private DBtableModel dbTableModel;
	private AbsJTable jTable;


	private JTextField insLines  = new JTextField();

	private JButton btnIns   = getActionButton("Insert", "Insert record(s) after selected records", ReActionHandler.INSERT_RECORDS);
	private JButton btnDel   = getActionButton("Delete", "Delete selected records from the Table below", ReActionHandler.DELETE_RECORD);
	private JButton btnCopy  = getActionButton("Copy", "Copy the selected record from the table", ReActionHandler.COPY_RECORD);
	private JButton btnCut   = getActionButton("Cut", "Cut the selected record from the table", ReActionHandler.CUT_RECORD);
	private JButton btnPaste = getActionButton("Paste", "Paste the records into the table", ReActionHandler.PASTE_RECORD);
	private JButton btnPastePrior  = getActionButton("Paste Prior", "Paste the record into the table, before the current record", ReActionHandler.PASTE_RECORD_PRIOR);

	private JButton btnNewChildRecord;
	private JButton btnEditChildRecords;

	private record blankRecord;

	/**
	 * This class implements Insert/Delete Buttons for a JTable & DBModel
	 *
	 * @param parent parent Panel
	 * @param childHandler handle child actions
	 */
	public TableUpdatePnl(final BasePanel parent, final ReActionHandler childHandler) {
		super();

		bPanel = parent;

		add(btnIns);
	  	add(btnDel);
	  	add(btnCopy);
	  	add(btnCut);
	  	add(btnPaste);
	  	add(btnPastePrior);

	  	if (childHandler != null) {
	  	    btnNewChildRecord = getActionButton(childHandler, "Create Child",
	  	            "Create Child Record", ReActionHandler.CREATE_CHILD);
	  	    btnEditChildRecords = getActionButton(childHandler, "Edit Child",
	  	            "Edit Child Record", ReActionHandler.EDIT_CHILD);
	  	    add(btnEditChildRecords);
	  	    add(btnNewChildRecord);
	  	}


	  	bPanel.addLine("Lines to Insert", insLines);
	   // bPanel.setGap(BasePanel.GAP0);

		bPanel.addComponent(1, 3, BasePanel.PREFERRED, 2,
		        BasePanel.FULL, BasePanel.FULL, this);
	}


	/**
	 * build a Button to perform the requested action
	 * @param name action name
	 * @param desc buttons description
	 * @param action action Id
	 * @return new button
	 */
	private JButton getActionButton(String name, String desc, int action) {
	    return getActionButton(this, name, desc, action);
	}

	/**
	 * build a Button to perform the requested action
	 * @param handler action handler to execute the action
	 * @param name action name
	 * @param action action Id
	 * @param desc description of the action
	 * @return new button
	 */
	private JButton getActionButton(ReActionHandler handler, String name, String desc, int action) {
	    ImageIcon icon = Common.getReActionIcon(action);
	    ReAction reAction;

	    name = LangConversion.convert(LangConversion.ST_ACTION, name);
	    desc = LangConversion.convert(LangConversion.ST_ACTION, desc);
	    if (icon == null) {
	        reAction = new ReAction(name, desc, action, handler);
	    } else {
	        reAction = new ReAction(name, desc, icon, action, handler);
	    }

	    return new JButton(reAction);
	}

	/**
	 * enable / disable child record buttons
	 * @param visible wether they are enabled
	 */
	public void setChildVisible(boolean visible) {

	    btnNewChildRecord.setVisible(visible);
	    btnEditChildRecords.setVisible(visible);
	    btnPastePrior.setVisible(! visible);
	}


	/**
	 * Defines the table on which insert / deletes etc are to operate on
	 *
	 * @param dbTblModel Table model being displayed
	 * @param jtable Table being displayed
	 * @param pBlankRecord a blank record
	 */

	public void setTableDtls(@SuppressWarnings("rawtypes") final DBtableModel dbTblModel,
							 final AbsJTable jtable,
							 final record pBlankRecord) {

		this.dbTableModel = dbTblModel;
		this.jTable = jtable;
		this.blankRecord = pBlankRecord;
	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
//	public void actionPerformed(final ActionEvent arg0) {
//
//		if (jTable == null) {
//			Common.logMsg("Table Details have not been defined", null);
//		} else {
//			Common.stopCellEditing(jTable);
//
//			if (arg0.getSource() == btnIns) {
//				int i;
//				int inRow = jTable.getSelectedRow() + 1;
//				int lines = cntInt(1, insLines.getText());
//
//				if (inRow == 0) {
//				    inRow = dbTableModel.getRowCount();
//				}
//
//				for (i = 0; i < lines; i++) {
//				    dbTableModel.addRow(inRow, (AbsRecord) blankRecord.clone());
//				}
//
//			} else if (arg0.getSource() == btnDel) {
//				dbTableModel.deleteRows(jTable.getSelectedRows());
//			} else if (arg0.getSource() == btnCopy) {
//				dbTableModel.setCopyLines(jTable.getSelectedRows());
//			} else {
//				dbTableModel.pasteLines(jTable.getSelectedRow());
//			}
//			dbTableModel.fireTableStructureChanged();
//			jTable.setColumnSizes();
//		}
//	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    @SuppressWarnings("unchecked")
	public void executeAction(int action) {

		if (jTable == null) {
			Common.logMsg("Table Details have not been defined", null);
		} else {
			Common.stopCellEditing(jTable);

			switch (action) {
			case ReActionHandler.INSERT_RECORDS:
			case ReActionHandler.INSERT_RECORDS_POPUP:

				int i;
				int inRow = jTable.getSelectedRow() + 1;
				int lines = cntInt(1, insLines.getText());

				if (inRow == 0) {
				    inRow = dbTableModel.getRowCount();
				}

				for (i = 0; i < lines; i++) {
				    dbTableModel.addRow(inRow, (record) blankRecord.clone());
				}
				break;
			case ReActionHandler.DELETE_BUTTON:
			case ReActionHandler.DELETE_RECORD:
			case ReActionHandler.DELETE_RECORD_POPUP:
				dbTableModel.deleteRows(jTable.getSelectedRows());
				break;
			case ReActionHandler.COPY_RECORD:
				dbTableModel.setCopyLines(jTable.getSelectedRows());
				break;
			case ReActionHandler.CUT_RECORD:
				dbTableModel.setCopyLines(jTable.getSelectedRows());
				dbTableModel.deleteRows(jTable.getSelectedRows());
				break;
			case ReActionHandler.PASTE_RECORD:
			case ReActionHandler.PASTE_RECORD_POPUP:
				dbTableModel.pasteLines(jTable.getSelectedRow());
				break;
			case ReActionHandler.PASTE_RECORD_PRIOR:
			case ReActionHandler.PASTE_RECORD_PRIOR_POPUP:
				dbTableModel.pasteLines(jTable.getSelectedRow() - 1);
				break;
			default:
				return;
			}
			dbTableModel.fireTableStructureChanged();
			jTable.setColumnSizes();
		}
    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return (action == ReActionHandler.INSERT_RECORDS)
        	|| (action == ReActionHandler.INSERT_RECORDS_POPUP)
        	|| (action == ReActionHandler.DELETE_BUTTON)
        	|| (action == ReActionHandler.DELETE_RECORD)
        	|| (action == ReActionHandler.DELETE_RECORD_POPUP)
			|| (action == ReActionHandler.COPY_RECORD)
			|| (action == ReActionHandler.CUT_RECORD)
			|| (action == ReActionHandler.PASTE_RECORD)
			|| (action == ReActionHandler.PASTE_RECORD_PRIOR)
			|| (action == ReActionHandler.PASTE_RECORD_POPUP)
			|| (action == ReActionHandler.PASTE_RECORD_PRIOR_POPUP);
    }

    /**
     * converts a string to Integer (or return the default int)
     *
     * @param def
     *            default integer
     * @param s
     *            string to convert to integer
     * @return integer value derived from string
     */
    private static final int cntInt(final int def, final String s) {
        int ret = def;

        try {
            String ss = s.trim();
            if (!ss.equals("")) {
                ret = Integer.parseInt(ss);
            }
        } catch (Exception ex) {
        }
        return ret;
    }
}
