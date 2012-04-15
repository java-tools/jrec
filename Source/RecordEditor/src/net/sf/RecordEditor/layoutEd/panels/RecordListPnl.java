/*
 * Created on 23/09/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - sort the record layout list prior to display
 */
package net.sf.RecordEditor.layoutEd.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.Comparator;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.layoutEd.Record.RecordListMdl;
import net.sf.RecordEditor.layoutEd.Record.SearchArgAction;
import net.sf.RecordEditor.re.db.Record.RecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;


/**
 * This class displays the record Search / list panel
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RecordListPnl extends BaseHelpPanel
						implements ActionListener, FocusListener {

	private String[] listOptions = {"", "Yes", "No"};

	private TableDB recTypeTbl = new TableDB();
	private DBComboModel<TableRec> recordTypeMdl
				= new DBComboModel<TableRec>(recTypeTbl, 0, 1, true, true);

	private TableDB systemTable = new TableDB();
	private DBComboModel<TableRec>  systemModel
				= new DBComboModel<TableRec>(systemTable, 0, 1 , true, true);


	private  JTextField  sfRecordName  = new JTextField();
	private JTextField  sfDescription = new JTextField();
	private BmKeyedComboBox  sfRecordType;

	private BmKeyedComboBox sfSystem;
	private JComboBox  sfList = new BmComboBox(listOptions);

	private String  oldRecordName;
	private String  oldDescription;
	private int  oldRecordType;

	private int oldSystem;
	private int oldList;


	private RecordDB dbRecord = new RecordDB();
	private RecordListMdl dbRecordModel;
	private JTable tbRecord;

	private JScrollPane scrollrecordList;

	//private RecordPnl pnlRecord;
	//private int currRow = 0;

	private SearchArgAction searchAction;


	/**
	 *
	 * @param frame parent frame
	 * @param connectionIdx Database Connection Index
	 * @param recordPanel parent Panel
	 *
	 * @param action Various actions to that are called
	 */
	public RecordListPnl(final JFrame frame,
	        			 final int connectionIdx,
	        			 final RecordPnl recordPanel,
	        			 final SearchArgAction action) {
		super();


		//this.pnlRecord = recordPanel;
		this.searchAction = action;

		setFieldNamePrefix("RecordList");
		initScreenFields(frame, connectionIdx);

	    addLine("Record Name", sfRecordName);
	    addLine("Description", sfDescription);
	    addLine("Record Type", sfRecordType);
	    addLine("System", sfSystem);
	    addLine("List", sfList);

	  	addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
	  	         BasePanel.FULL, BasePanel.FULL,
				 scrollrecordList);


		//this.setPreferredSize(new Dimension(250, 180));
		saveScreenVals();
	}


	/**
	 * Initialise the screen fields
	 *
	 * @param frame Parent Screen Frame
	 * @param connectionIdx The Database connection index
	 */
	public void initScreenFields(JFrame frame, int connectionIdx) {

	    ReConnection dbConnection = new ReConnection(connectionIdx);
	    recTypeTbl.setConnection(dbConnection);
	    systemTable.setConnection(dbConnection);
	    dbRecord.setConnection(dbConnection);

	    recTypeTbl.setParams(Common.TI_RECORD_TYPE);
	    systemTable.setParams(Common.TI_SYSTEMS);

	    sfRecordType = new BmKeyedComboBox(recordTypeMdl, true);
	    sfSystem = new BmKeyedComboBox(systemModel, false);
	    sfList.setEditable(true);


		dbRecordModel   = new RecordListMdl(frame, dbRecord);
		dbRecordModel.sort(new Comparator<RecordRec>() {
		    public int compare(RecordRec r1, RecordRec r2) {
		        //RecordRec r1 = (RecordRec) o1;
		        //RecordRec r2 = (RecordRec) o2;
		        return r1.getRecordName()
		        		 .compareToIgnoreCase(r2.getRecordName());
		    }
		});
		tbRecord  = new JTable(dbRecordModel);

		setColumnDetails();

		scrollrecordList = new JScrollPane(tbRecord);
		this.registerComponent(tbRecord);
		setComponentName(tbRecord, "RecordListTbl");

		scrollrecordList.setPreferredSize(new Dimension(150, 100));

			/* setting up Action to be performed after filters have changed */
		sfRecordName.addFocusListener(this);
		sfDescription.addFocusListener(this);

		sfRecordType.addActionListener(this);
		sfSystem.addActionListener(this);
		sfList.addActionListener(this);

		this.setHelpURL(Common.formatHelpURL(Common.HELP_LAYOUT_LIST));
	}


	/**
	 * @param listener Mouse listner
	 */
	public void addMouseListener(MouseListener listener) {
		tbRecord.addMouseListener(listener);
	}


	/**
	 * Sets the Search Action listener field
	 * @param action Action method to be assigned
	 */
	public void addActionListener(SearchArgAction action) {
		searchAction = action;
	}


	/**
	 * Get the row selected on the table
	 *
	 * @return the selected Row
	 */
	public int getSelectedRow() {
		return tbRecord.getSelectedRow();
	}


	/**
	 * Get a Selected row from the table
	 *
	 * @param row to be retrieved
	 *
	 * @return the selected row
	 *
	 */
	public RecordRec getRecord(int row) {
		return dbRecordModel.getRecord(row);
	}


	/**
	 * This method updates the value of a row in both the internal array and
	 * the DB
	 *
	 * @param row row to be updated
	 * @param rec new value of the record
	 *
	 * @return Error message (if any)
	 */
	public String setRecord(int row, RecordRec rec) {

		String s = "";

		if (row < 0) {
			AbsDB.getSystemLog()
				 .logMsg(AbsSSLogger.WARNING, "RecordListPnl - setRecord error row <0");
			return "Program error: Row < 0  ??";
		}

		//currRow = row;
		s = dbRecordModel.setRecordAndDB(row, rec);
		//System.out.println("PnlRecList - update Record  >> !" + s + "! " + rec.getRecordName());

		if ("".equals(s)) {
	//		dbRecord.checkAndUpdate(rec);

			dbRecordModel.fireTableDataChanged();
		}

		return s;
	}


	/**
	 * Add a record to the DB & model
	 *
	 * @param rec record to add
	 *
	 * @return Error message (if any)
	 */
	public int addRecord(RecordRec rec) {
		int ret = -1;

		String s = dbRecordModel.addRecordtoDB(rec);
		//System.out.println("PnlRecList - Add Record  >> !" + s + "! " + rec.getRecordName());

		if ("".equals(s)) {
			dbRecord.checkAndUpdate(rec);

			dbRecordModel.fireTableDataChanged();

			ret = dbRecordModel.getRowCount() - 1;
		}

		return ret;
	}

	/**
	 * Delete a record to the DB & model
	 *
	 * @param row row to delete
	 *
	 * @return Error message (if any)
	 */
	public String deleteRecord(int row) {

		String s = dbRecordModel.deleteRecordFromDB(row);

		if ("".equals(s)) {
			dbRecordModel.fireTableDataChanged();
		}

		return s;
	}


	/**
	 * This method updates the search parameters
	 *
	 */
	public void changeSearchArgs() {

		int listId = sfList.getSelectedIndex();

		dbRecord.resetSearch();

		String s = sfRecordName.getText();
		if (! s.equals("")) {
			dbRecord.setSearchRecordName(AbsDB.opLike, s);
		}

		s = sfDescription.getText();
		if (! s.equals("")) {
			dbRecord.setSearchDescription(AbsDB.opLike, s);
		}

		if (sfRecordType.getSelectedIndex() >= 0) {
			dbRecord.setSearchRecordType(
			        AbsDB.opEquals,
			        ((Integer) sfRecordType.getSelectedItem()).intValue());
		}

		if (sfSystem.getSelectedIndex() >= 0) {
			dbRecord.setSearchSystem(
			        AbsDB.opEquals,
			        ((Integer) sfSystem.getSelectedItem()).intValue());
		}

		if (listId > 0) {
			dbRecord.setSearchListChar(AbsDB.opEquals, listId == 1 ? "Y" : "N");
		}

		dbRecordModel.load();
		dbRecordModel.fireTableStructureChanged();
		setColumnDetails();
	}


	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {

		check4change();
	}

	/**
	 * Set the table column details
	 *
	 */
	private void setColumnDetails() {

		tbRecord.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn tc = tbRecord.getColumnModel().getColumn(0);
		tc.setPreferredWidth(110);
		tc = tbRecord.getColumnModel().getColumn(1);
		tc.setPreferredWidth(150);
	}


	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent arg0) {
	}


	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent arg0) {

		check4change();
	}


	/**
	 * Check for a change of the screen values
	 *
	 */
	public void check4change() {

		if (! ((oldRecordName.equals(sfRecordName.getText()))
			&& (oldDescription.equals(sfDescription.getText()))
			&& (oldRecordType == sfRecordType.getSelectedIndex())
			&& (oldSystem     == sfSystem.getSelectedIndex())
			&& (oldList       == sfList.getSelectedIndex()))) {

			searchAction.searchArgChanged();
			saveScreenVals();

		}
	}


	/**
	 * take a copy of the current Screen values
	 *
	 */
	private void saveScreenVals() {

		oldRecordName  = sfRecordName.getText();
		oldDescription = sfDescription.getText();
		oldRecordType  = sfRecordType.getSelectedIndex();

		oldSystem  = sfSystem.getSelectedIndex();
		oldList    = sfList.getSelectedIndex();
	}

}
