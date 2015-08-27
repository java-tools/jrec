/*
 * Created on 25/08/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JInternalFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 */
package net.sf.RecordEditor.layoutEd;

//import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.TableColumn;

import net.sf.RecordEditor.layoutEd.panels.TableListPnl;
import net.sf.RecordEditor.re.db.Table.TableListDB;
import net.sf.RecordEditor.re.db.Table.TableListRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.screenManager.ReFrame;




/**
 * This class will display:
 * <ul compact>
 * <li>A pane where the user can select various tables
 * <li>A pane where a table details is updated
 * </ul>
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class TblEdit extends ReFrame {

	private TableListDB dbTblList = new TableListDB();
	private DBtableModel<TableListRec> dbTblListModel;
	private JTable tblTableList;

	private TableListPnl pnlTableList;

	private JSplitPane splitPane;
	private JScrollPane scrollTblList;

	private JTextArea message = new JTextArea(" ");

	private int currRow = 0;
	private int connectionId;

	private JFrame frame;


	/**
	 * Edit a specified table
	 *
	 * @param dbName Database Name
	 * @param jframe parent frame
	 * @param connectionIdx Database connection index
	 * @param tableId table to edit
	 */
	public TblEdit(final String dbName,
	               final JFrame jframe,
	               final int connectionIdx,
	               final int tableId)   {
	    this(dbName, jframe, connectionIdx);

	   boolean free = Common.isSetDoFree(false);
	    String table = "" + tableId;
	    int i;

	    for (i = 0; i < dbTblListModel.getRowCount(); i++) {
	        if (table.equals(dbTblListModel.getValueAt(i, 0).toString())) {
	            this.setTable(i);
	            scrollTblList.setVisible(false);
	    		this.setBounds(1, 1, getWidth() - 100, getHeight());

	        }
	    }
	    Common.setDoFree(free, connectionIdx);
	}

	/**
	 * This class lets the user edit the tables database
	 *
	 * @param dbName Database Name
	 * @param jframe parent frame
	 * @param connectionIdx Database connection index
	 */
	public TblEdit(final String dbName, final JFrame jframe, final int connectionIdx)   {
		super(dbName, "Edit Tables DB", null);
		boolean free = Common.isSetDoFree(false);
		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());

		frame = jframe;
		connectionId = connectionIdx;

		defLeftPnl();
		defRightPanel();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
								   scrollTblList,
								   pnlTableList);

		message.setEnabled(false);
		message.setText(Common.getJdbcMsg(connectionIdx));

		cont.add(BorderLayout.NORTH, splitPane);
		cont.add(BorderLayout.SOUTH, message);

		pack();

		this.setBounds(1, 1, getWidth() + 60, getHeight()+ 5);

		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosed(final InternalFrameEvent e) {
				saveRecord();
			}
		});

		setVisible(true);
		Common.setDoFree(free, connectionIdx);
	}


	/**
	 * Define the left panel
	 *
	 */
	public void defLeftPnl() {

		dbTblList.setConnection(new ReConnection(connectionId));

		dbTblListModel   = new DBtableModel<TableListRec>(dbTblList);
		tblTableList  = new JTable(dbTblListModel);

		tblTableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn tc = tblTableList.getColumnModel().getColumn(0);
		tc.setPreferredWidth(50);
		tc = tblTableList.getColumnModel().getColumn(1);
		tc.setPreferredWidth(80);

		tblTableList.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent m) {
			    pnlTableList.stopCellEditing();
				setTable(tblTableList.getSelectedRow());
			}
		});

		scrollTblList = new JScrollPane(tblTableList);

		scrollTblList.setPreferredSize(new Dimension(250, 50));
	}


	/**
	 * Define the right panel
	 *
	 */
	public void defRightPanel() {

		pnlTableList = new TableListPnl(frame, dbTblList,
										dbTblListModel.getRecord(0),
										connectionId);

		pnlTableList.registerComponentRE(tblTableList);
		pnlTableList.setMinimumSize(new Dimension(400, 50));
	}

	/**
	 * This method changes the row being displayed
	 *
	 * @param row new row to be displayed
	 */
	private void setTable(final int row) {

		if (currRow != row) {
			TableListRec rec = saveRecord();

			if (rec.isUpdateSuccessful()) {
				currRow = row;
				rec =  dbTblListModel.getRecord(row);
				pnlTableList.setValues(rec);

				dbTblListModel.fireTableDataChanged();
				message.setText("");
			}
		}
	}



	/**
	 * Save the Table record being displayed
	 *
	 * @return Table record just saved
	 */
	private TableListRec saveRecord() {

		boolean free = Common.isSetDoFree(false);
		TableListRec rec = pnlTableList.getValues();

		if (rec.isUpdateSuccessful()) {
			dbTblListModel.setRecord(currRow, rec);

			pnlTableList.saveTableDetails();
		} else {
			message.setText(pnlTableList.getMsg());
		}

		Common.setDoFree(free, connectionId);
		return rec;
	}


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            pnlTableList.showHelpRE();
        } else {
            pnlTableList.getLinesActionHandler().executeAction(action);
        }
    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
        return action == ReActionHandler.HELP
    		|| pnlTableList.getLinesActionHandler().isActionAvailable(action);
    }
}
