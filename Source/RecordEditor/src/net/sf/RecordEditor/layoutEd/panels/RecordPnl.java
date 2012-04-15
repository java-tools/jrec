/*
 * Created on 8/09/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *    *changed to use instead of ActionListener
 *    * Fixed bug with Record Layout combo
 *    * Cosmetic chages
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   * Added Support for arrow buttons
 *   * Code changes due to Changes to RecordRec class
 *   * Code reorg
 */
package net.sf.RecordEditor.layoutEd.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.RecordEditor.layoutEd.Record.ChildRecordsJTbl;
import net.sf.RecordEditor.layoutEd.Record.ChildRecordsTblMdl;
import net.sf.RecordEditor.layoutEd.Record.RecordFieldsJTbl;
import net.sf.RecordEditor.re.db.Record.ChildRecordsDB;
import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordFieldsDB;
import net.sf.RecordEditor.re.db.Record.RecordFieldsRec;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.screenManager.ReAction;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.HelpWindow;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.Combo.ComboObjOption;
import net.sf.RecordEditor.utils.swing.Combo.KeyedComboMdl;




/**
 * Displays Record Header details on the screen
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RecordPnl extends BaseHelpPanel
                    implements ActionListener, ReActionHandler, LayoutConnection {

//	static final int rtGroupOfRecords = 9;
	private static final String REVIEW_EXTRA = "\nI suggest reviewing the File Structure on the Extra Screen";
    private static final int CHILD_PANE_WIDTH  = 33 * SwingUtils.STANDARD_FONT_WIDTH;
    private static final int CHILD_PANE_HEIGHT = 43 * SwingUtils.STANDARD_FONT_HEIGHT / 2;

	private static final int NULL_RECORD = -999;
	private static final int DESCRIPTION_HEIGHT =  SwingUtils.STANDARD_FONT_HEIGHT * 5;

	private static final String[] FIELD_SEPERATOR  = Common.FIELD_SEPARATOR_LIST1; //{"<Tab>", "<Space>", ",", ";", ":", "|", "/", "\\", "~", "!", "*", "#", "@"}; 
	private static final String[] RECORD_SEPERATOR = {Common.DEFAULT_STRING, Common.CRLF_STRING, Common.CR_STRING, Common.LF_STRING};
	private static final byte[][] RECORD_SEP_VALS  = {Common.LFCR_BYTES,     Common.LFCR_BYTES,  Common.CR_BYTES,  Common.LF_BYTES};

	private TableDB recTypeTbl = new TableDB();

	private DBComboModel<TableRec> recordTypeMdl = new DBComboModel<TableRec>(recTypeTbl, 0, 1, true, false);

	private TableDB systemTable      = new TableDB();

	private DBComboModel<TableRec> systemModel = new DBComboModel<TableRec>(systemTable, 0, 1,
			true, false);

	private JTextField sfRecordName  = new JTextField();
	private JTextArea sfDescription  = new JTextArea();

	private BmKeyedComboBox sfRecordType;

	private BmKeyedComboBox sfSystem;
	private BmKeyedComboModel styleModel = new BmKeyedComboModel(
			new ManagerRowList(ParserManager.getInstance(), false));
	private BmKeyedComboBox sfRecordStyle = new BmKeyedComboBox(styleModel, false);
	private JCheckBox sfList = new JCheckBox();
	private JTextField sfCopyBook  = new JTextField();
	private JComboBox sfDelimiter  = new JComboBox(FIELD_SEPERATOR);
	private JTextField sfDelimTxt = new JTextField(5);
	private JTextField sfQuote     = new JTextField();
	private JComboBox sfRecSepList = new JComboBox(RECORD_SEPERATOR);

	private TableDB structureTable    = new TableDB();
//	private DBComboModel<TableRec> structureModel = new DBComboModel<TableRec>(structureTable, 0, 1,
//			true, false);
	private BmKeyedComboModel structureModel
					= new BmKeyedComboModel(
								new ManagerRowList(ReIOProvider.getInstance(), false));

	private BmKeyedComboBox sfStructure;

	private JTextField sfCanonicalName = new JTextField();

	private JPanel headerPnl;

	private JPanel optionPnl   = new JPanel();

/*	public  JButton btnSave    = new JButton("Save");
	public  JButton btnNew     = new JButton("New");
	public  JButton btnRepeat  = new JButton("Repeat");
	public  JButton btnDel     = new JButton("Delete");*/
	private JButton btnRefresh = new JButton("Refresh");
	private JButton btnHelp    = Common.getHelpButton();

	private JButton prevBtn;
	private JButton nextBtn;


	private TableUpdatePnl<AbsRecord> updateOptions;

	private JFrame frame;

	private RecordFieldsDB recFields = new RecordFieldsDB();
	private DBtableModel<RecordFieldsRec> dbRecordModel;
	private RecordFieldsJTbl tblRecord;
	private JScrollPane tblPane;
	private ChildRecordsDB dbChildTbl = new ChildRecordsDB();
	private ChildRecordsTblMdl dbChildModel;

	private ChildRecordsJTbl tblChild;

	private JScrollPane childPane;

	private JTabbedPane tabbed = new JTabbedPane();

	private String msg = "";

	private RecordRec currVal;

	private int connectionIdx;


	private RecordFieldsRec blankRecordFields = new RecordFieldsRec();
	private ChildRecordsRec blankChildRecord  = new ChildRecordsRec();

	private boolean updating = false;


	private HelpWindow childHelp = new HelpWindow(Common.formatHelpURL(Common.HELP_LAYOUT_CHILD));
	private HelpWindow fieldHelp = new HelpWindow(Common.formatHelpURL(Common.HELP_LAYOUT_FIELD));
	
	private final ReActionHandler parentActionHandler;

	/**
	 * Define the Record Definition panel
	 *
	 * @param jframe parent frame
	 * @param dbConnectionIdx DB connection id
	 * @param actionHandler Action to be performed when a button is pressed
	 * @param isRtEditable wether the record type is editable
	 * @param showArrows show next/previous record arrows
	 */
	public RecordPnl(final JFrame jframe,
	        		 final int dbConnectionIdx,
	        		 final ReActionHandler actionHandler,
	        		 final boolean isRtEditable,
	        		 final boolean showArrows) {
		super();

		frame = jframe;
		this.connectionIdx = dbConnectionIdx;
		this.parentActionHandler = actionHandler;

		setFieldNamePrefix("RecordDef");
		
		init_100_ScreenFields(actionHandler, isRtEditable, showArrows);
		sfRecordType.setEditable(isRtEditable);


		if (showArrows) {
		    this.addComponent(1, 5, BasePanel.PREFERRED, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				headerPnl);
		} else {
		    addHeadingComponent(optionPnl);
		}


		addLine("Record Name", sfRecordName);
		addLine("Description", sfDescription);
		setHeight(DESCRIPTION_HEIGHT);
		addLine("Record Type", sfRecordType);
		addLine("System",      sfSystem);
		addLine("List",        sfList);

		setGap(BasePanel.GAP0);

		updateOptions = new TableUpdatePnl<AbsRecord>(this, this);

		defTabpane();

		//done();
	}

	/**
	 * Define the screen related fields (ie DB access etc)
	 *
	 * @param btnAction Action handler for all the buttons
	 * @param isRtEditable wether the Record type fields is editable
	 * @param showArrows show next/previous record arrows
	 */
	private void init_100_ScreenFields(final ReActionHandler btnAction,
	        final boolean isRtEditable, final boolean showArrows) {
	    ReConnection con = new ReConnection(connectionIdx);
	    setHelpURL(Common.formatHelpURL(Common.HELP_LAYOUT_DETAILS));

		recTypeTbl.setConnection(con);
		systemTable.setConnection(con);
		structureTable.setConnection(con);

		recTypeTbl.setParams(Common.TI_RECORD_TYPE);
		systemTable.setParams(Common.TI_SYSTEMS);
		structureTable.setParams(Common.TI_FILE_STRUCTURE);

		sfRecordType = new BmKeyedComboBox(recordTypeMdl, true);
		sfSystem     = new BmKeyedComboBox(systemModel, false);
		sfStructure  = new BmKeyedComboBox(structureModel, false);
		sfRecordType.setEditable(isRtEditable);


		optionPnl.add(buildButton(ReActionHandler.SAVE, btnAction, "Save",
		        "Save the Current record back to the DB (if no errors)"));
		optionPnl.add(buildButton(ReActionHandler.SAVE_AS, btnAction, "Save As",
		        "Save the current layout under a new name"));
		optionPnl.add(buildButton(ReActionHandler.NEW, btnAction, "New",
				"Create a new record layout"));
		optionPnl.add(buildButton(ReActionHandler.DELETE, btnAction, "Delete",
		        "Delete the Current Record"));
		optionPnl.add(btnRefresh);
		optionPnl.add(btnHelp);

		if (showArrows) {
		    headerPnl = new JPanel(new BorderLayout());
		    prevBtn = buildButton(ReActionHandler.PREVIOUS_RECORD, btnAction, "",
        					"Goto Previous Record");
		    nextBtn = buildButton(ReActionHandler.NEXT_RECORD, btnAction, "",
    			"Goto Next Record");
		    headerPnl.add("West", prevBtn);
		    headerPnl.add("Center", optionPnl);
		    headerPnl.add("East", nextBtn);
		}

		btnRefresh.setToolTipText("Reload values - ie abandon any changes");

		btnRefresh.addActionListener(this);
		btnHelp.addActionListener(this);

		sfRecordType.addActionListener(this);
		sfSystem.addActionListener(this);

	}


	/**
	 * Build a button using a RectionHandler to perform the
	 * requested action
	 * @param action action to be performed if the button is pressed
	 * @param handler class that will execute the action
	 * @param text text to be displayed for the action
	 * @param tip Button tip
	 *
	 * @return new button
	 */
	private JButton buildButton(int action, ReActionHandler handler,
	        String text, String tip) {
	    return new JButton(new ReAction(text, tip, Common.getReActionIcon(action),
	            action, handler));
	}

	/**
	 * Set the screen field values from a abstract Record
	 *
	 * @param val
	 *            record values to be assign to the screen fields
	 */
	public void setValues(AbsRecord val) {
		setValues((RecordRec) val);
	}


	/**
	 * Set Screen values
	 *
	 * @param val new screen values
	 */
	private void setValuesInternals(RecordRec val) {

	    //System.out.println("Setting ...");
	    updating = true;
		currVal = val;
		sfRecordType.removeActionListener(this);
		sfSystem.removeActionListener(this);

		if (val == null) {
			sfRecordName.setText("");
			sfDescription.setText("");
			sfRecordType.setSelectedIndex(sfRecordType.getItemCount() - 2);
			sfList.setSelected(true);
			sfCopyBook.setText("");
			sfDelimiter.setSelectedIndex(0);
			sfRecordStyle.setSelectedIndex(0);
			sfQuote.setText("");
			sfRecSepList.setSelectedIndex(0);
			sfStructure.setSelectedIndex(0);

			recFields.setParams(NULL_RECORD);
			dbChildTbl.setParams(NULL_RECORD);

			dbRecordModel.load();
			dbChildModel.load();
			
			setTabs();
			
			checkForBinaryFields();

			fireTableChanged();
		} else {
			String v;
			// sfRecordId.setText("" + val.getRecordId());
			sfRecordName.setText(val.getRecordName());
			//System.out.print(" 1 " + val.getRecordName() + " " + val.getValue().getDescription());
			sfDescription.setText(val.getValue().getDescription());

			recordTypeMdl.setSelectedItem(Integer.valueOf(val.getValue().getRecordType()));
			//System.out.print(" 3 ");
			systemModel.setSelectedItem(Integer.valueOf(val.getSystem()));
			//System.out.print(" 4 ");
			sfList.setSelected("Y".equals(val.getValue().getListChar()));
			//System.out.print(" 5 ");
			/*System.out.println("Set Value "
			        + " " + (currVal.getRecordName() + "           ").substring(0,10)
					+ " " + val.getListChar()
			        + " " + sfList.isSelected());*/
			sfCopyBook.setText(val.getValue().getCopyBook());
			

			v = val.getValue().getDelimiter();
			if ("null".equals(v)) {
				v = "<Tab>";
			}

			sfDelimiter.setSelectedItem(v);
			sfDelimTxt.setText("");
			if (v != null && ! v.equalsIgnoreCase(sfDelimiter.getSelectedItem().toString())) {
				sfDelimTxt.setText(v);
			}
			sfRecordStyle.setSelectedItem(Integer.valueOf(val.getValue().getRecordStyle()));
			sfQuote.setText(val.getValue().getQuote());
			//sfPosRecInd.setText("" + val.getPosRecInd());
			sfRecSepList.setSelectedItem(val.getValue().getRecSepList());
			//sfRecordSep.setText(val.getRecordSep());
			sfStructure.setSelectedItem(Integer.valueOf(val.getValue().getFileStructure()));

			sfCanonicalName.setText(val.getValue().getFontName());

			fixSeperator();

			sfRecordType.repaint();
			sfSystem.repaint();
		}

		sfRecordType.addActionListener(this);
		sfSystem.addActionListener(this);

		updating = false;
	}

	/**
	 * Set the screen field values from a RecRecord Record
	 *
	 * @param val  record values to be assign to the screen fields
	 */
	public void setValues(RecordRec val) {

		setValuesInternals(val);
		if (val != null) {
			Common.stopCellEditing(tblRecord);
			Common.stopCellEditing(tblChild);

			recFields.setParams(val.getRecordId());
			dbRecordModel.load();
			checkForBinaryFields();
			
			dbChildTbl.setParams(val.getRecordId());
			dbChildModel.load();

			setTabs();

			fireTableChanged();
		}
	}


	/**
	 * Repeat the fields after the user has pressed the Repeat button
	 *
	 * @param val new record value
	 */
	public void set2clone(RecordRec val) {

		setValuesInternals(val);
		if (val != null) {
			int i;
			int oldRecId = dbChildTbl.getRecordId();

			recFields.setParams(val.getRecordId());
			dbChildTbl.setParams(val.getRecordId());

			if (isGroup()) {
				ChildRecordsRec oldRec, rec;
				RecordSelectionDB dbFrom = new RecordSelectionDB(),
						          dbTo   = new RecordSelectionDB();
				dbFrom.setConnection(new ReConnection(connectionIdx));
				dbTo.setConnection(new ReConnection(connectionIdx));
				
				boolean from = dbFrom.isSetDoFree(false);
				boolean to   = dbFrom.isSetDoFree(false);
				int numRecs = dbChildModel.getRowCount();
				//System.out.println("set2clone 1 " + numRecs + " " + val.getRecordId());

				for (i = 0; i < numRecs; i++) {
					oldRec = dbChildModel.getRecord(i);
					rec = (ChildRecordsRec) oldRec.clone();
					rec.setNew(true);
					//dbChildModel.setRecord(i, rec);
					try {
						RecordSelectionRec recSelRec;
						int idx = 1;
						
						dbChildModel.setRecordAndDB(i, rec);
						dbFrom.setParams(oldRecId, oldRec.getChildKey());
						dbTo.setParams(val.getRecordId(), rec.getChildKey());
						
						dbFrom.open();
						
						while ((recSelRec = dbFrom.fetch()) != null) {
							recSelRec.setRecordId(val.getRecordId());
							recSelRec.setChildKey(rec.getChildKey());
							recSelRec.setFieldNo(idx++);
							dbTo.insert(recSelRec);
						}
						
						dbFrom.close();
						dbTo.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					dbFrom.setDoFree(from);
					dbFrom.setDoFree(to);
				}
			} else {
				RecordFieldsRec rec;
				int numRecs = dbRecordModel.getRowCount();
				//System.out.println("set2clone 2 " + numRecs + " " + val.getRecordId());

				for (i = 0; i < numRecs; i++) {
					rec = (RecordFieldsRec) dbRecordModel.getRecord(i).clone();
					rec.setNew(true);
					dbRecordModel.setRecord(i, rec);
				}
			}

			setTabs();

			fireTableChanged();
		}
	}


	/**
	 * Redraw the table
	 *
	 */
	private void fireTableChanged() {

		if (isGroup()) {
			dbChildModel.fireTableDataChanged();
		} else {
			dbRecordModel.fireTableDataChanged();
		}
	}



	/**
	 * Get the screen values as a Abstract Record
	 *
	 * @return screen values in a standard record Layout
	 */
	public AbsRecord getAbsValues() {
		return getValues();
	}

	/**
	 * Get the screen values as a RecRecord Record
	 *
	 * @return screen values in a RecRecord record Layout
	 */
	public RecordRec getValues() {
		String fld = "";
		JComponent field = null;

		updating = true;
		if (currVal == null) {
			if (sfRecordName.getText().equals("")) {
				Common.logMsg("Invalid Record Name ", null);
				sfRecordName.requestFocus();
				return null;
			}

			currVal = new RecordRec();
		}

		try {
			currVal.setUpdateSuccessful(true);
			//fld = "RecordId";
			//currVal.setRecordId(Integer.parseInt(sfRecordId.getText()));
			fld = "RecordName";
			field = sfRecordName;
			currVal.getValue().setRecordName(sfRecordName.getText());
			fld = "Description";
			field = sfDescription;
			currVal.getValue().setDescription(sfDescription.getText());
			fld = "RecordType";
			field = sfRecordType;
			currVal.getValue().setRecordType(((Integer) recordTypeMdl.getSelectedItem())
					.intValue());
			fld = "List";
			field = sfList;
			/*System.out.println("Get Value "
			        + " " + (currVal.getRecordName() + "           ").substring(0,10)
			        + " " + currVal.getListChar()
			        + " " + sfList.isSelected()
			        + " " + (sfList.isSelected()?"Y":"N"));*/
			if (sfList.isSelected()) {
			    currVal.getValue().setListChar("Y");
			} else {
			    currVal.getValue().setListChar("N");
			}
			fld = "CopyBook";
			field = sfCopyBook;
			currVal.getValue().setCopyBook(sfCopyBook.getText());

			fld = "Canonical_Name";
			field = sfCanonicalName;
			currVal.getValue().setFontName(sfCanonicalName.getText());

			//String s = (String) sfDelimiter.getSelectedItem();
			
			fld = "Quote";
			field = sfQuote;
			currVal.getValue().setQuote(sfQuote.getText());
			//fld = "PosRecInd";
			//currVal.setPosRecInd(Integer.parseInt(sfPosRecInd.getText()));
			fld = "RecordStyle";
			field = sfRecordStyle;
			currVal.getValue().setRecordStyle(((Integer) sfRecordStyle.getSelectedItem()).intValue());
			fld = "RecSep";
			field = sfRecSepList;
			setSeperator();
			fld = "System";
			field = sfSystem;
			currVal.getValue().setSystem(((Integer) systemModel.getSelectedItem())
					.intValue());

			fld = "Structure";
			field = sfStructure;
			currVal.getValue().setFileStructure(((Integer) structureModel.getSelectedItem())
					.intValue());

			fld = "Delimiter";
			field = sfDelimiter;
			String v = sfDelimTxt.getText();
			if ("".equals(v)) {
				currVal.getValue().setDelimiter("" + (String) sfDelimiter.getSelectedItem());
			} else if (v.length() == 1) { 
				currVal.getValue().setDelimiter(v);
			} else if ((v.length() == 5) && v.toLowerCase().startsWith("x'") && v.endsWith("'") ){
				try {
					Conversion.getByteFromHexString(v);
					//Integer.parseInt(v.substring(2, 3), 16);
					currVal.getValue().setDelimiter(v);
				} catch (Exception e) {
					sfDelimTxt.requestFocus();
					currVal.setUpdateSuccessful(false);
					Common.logMsg("Invalid Delimiter - Invalid  hex string: " + v.substring(2, 3), null);
				}
			} else {
				currVal.setUpdateSuccessful(false);
				Common.logMsg("Invalid Delimiter, should be a single character or a hex character", null);
				sfDelimTxt.requestFocus();
			}
		} catch (Exception ex) {
			currVal.setUpdateSuccessful(false);
			Common.logMsg("Invalid Field " + fld + " - " + ex.getMessage(), ex);
			if (field != null) {
				field.requestFocus();
			}
		}

		updating = false;
		return currVal;
	}


	/**
	 * fix up seperatof value
	 *
	 */
	private void fixSeperator() {

		try {
			setSeperator();
		} catch (Exception e) {
		}
		currVal.setUpdateStatus(AbsRecord.UNCHANGED);
	}


	/**
	 * Set The record seperator
	 *
	 */
	private void setSeperator() {

		currVal.getValue().setRecSepList((String) sfRecSepList.getSelectedItem());
		int idx = sfRecSepList.getSelectedIndex();
		if (idx < 0) {
		    idx = 0;
		}
		currVal.getValue().setRecordSep(RECORD_SEP_VALS[idx]);

	}


	/**
	 * Save Table details back to the DB
	 *
	 */
	public void saveRecordDetails() {
		boolean free = Common.isSetDoFree(false);
	    int recId = currVal.getRecordId();
	    int structure = currVal.getValue().getFileStructure();
	    int binaryStatus = currVal.getBinaryFields();

	    recFields.setParams(recId);
	    dbChildTbl.setParams(recId);
		if (isGroup()) {
			Common.stopCellEditing(tblChild);

			dbChildTbl.setParams(recId);
			dbChildModel.updateDB();
			dbChildModel.clearRecordLookup();
			tblChild.setSystem(currVal.getSystem());
			dbChildModel.fireTableDataChanged();
		} else {
			Common.stopCellEditing(tblRecord);

			recFields.setParams(recId);
			dbRecordModel.updateDB();
			
			checkForBinaryFields();
			if (binaryStatus == currVal.getBinaryFields()) {
				
			} else if (structure == Constants.IO_DEFAULT) {
				if (Common.OPTIONS.warnBinaryFieldsAndStructureDefault.isSelected()) {
					if (currVal.getBinaryFields() == RecordRec.BF_BINARY_FIELDS) {
						warnUser("You have binary fields with a default FileStructure." 
								+ "\nThis will also change the File reader from a Text File Reader "
								+ "\nto a binary Reader (Fixed Length ?)." 
								+	"\nThis may have unexpected consequences (Fields not alligned) ." 
								+ REVIEW_EXTRA);
					} else if (binaryStatus != RecordRec.BF_NOT_DEFINED) {
						warnUser("You removed all binary fields from the record and the FileStructure=Default." 
								+ "\nThis will also change the File reader from a  binary Reader (Fixed Length ?) "
								+ "\nto a Text File Reader" 
								+	"\nThis may have unexpected consequences (Fields not alligned)." 
								+ REVIEW_EXTRA);
					}
				}
			} else if (structure == Constants.IO_BIN_TEXT 
					|| structure == Constants.IO_TEXT_LINE
					|| structure == Constants.IO_UNICODE_TEXT
					|| structure == Constants.IO_UNICODE_NAME_1ST_LINE) {
				if (currVal.getBinaryFields() == RecordRec.BF_BINARY_FIELDS) {
					warnUser("You have binary fields in a Text file that is not a good Idea. " + REVIEW_EXTRA);
				}
			}
		}

		Common.commit(connectionIdx);
		Common.setDoFree(free, connectionIdx);
	}

	
	private void warnUser(String message) {
	
		JOptionPane.showMessageDialog(this, message);

		Common.logMsg(0, message, null);
	}
	
	/**
	 * wether there are binary fields or not
	 * @return binary fields present
	 */
	private void checkForBinaryFields() {
		if (currVal != null && dbRecordModel.getRowCount() > 0) {
			boolean binary = false;
	//		RecordFieldsRec rec;
			int type;
			for (int i = 0; i < dbRecordModel.getRowCount(); i++) {
				type = dbRecordModel.getRecord(i).getValue().getType();
				if (TypeManager.getInstance().getType(type).isBinary()
				&& type != Type.ftCharRestOfRecord) {
					binary = true;
					break;
				}
			}
			
			currVal.setBinaryFields(RecordRec.BF_NO_BINARY_FIELDS);
			if (binary) {
				currVal.setBinaryFields(RecordRec.BF_BINARY_FIELDS);
			}
		}
	}

	/**
	 * Delete Table details from the DB
	 *
	 */
	public void deleteRecordDetails() {

		boolean free = Common.isSetDoFree(false);
		recFields.setParams(currVal.getInitRecordId());
		recFields.deleteAll();
		dbChildTbl.setParams(currVal.getInitRecordId());
		dbChildTbl.deleteAll();

		fireTableChanged();
		Common.setDoFree(free, connectionIdx);
	}


	/**
	 * Get the error Message
	 *
	 * @return error Message
	 */
	public String getMsg() {
		return msg;
	}


	/**
	 * Define the Tab pane that holds Record Layout
	 *
	 */
	private void defTabpane() {
		boolean free = Common.isSetDoFree(false);
		
		defTabTable();
		defTabChild();
		defTabExtra();

		addComponent(1, 3, BasePanel.FILL, BasePanel.GAP, BasePanel.FULL,
		        BasePanel.FULL, tabbed);
		
		Common.setDoFree(free, connectionIdx);
	}


	/**
	 * Define the Record Layout table
	 *
	 */
	private void defTabTable() {

		recFields.setConnection(new ReConnection(connectionIdx));
		dbRecordModel = new DBtableModel<RecordFieldsRec>(frame, recFields);

		dbRecordModel.setCellEditable(true);

		tblRecord = new RecordFieldsJTbl(connectionIdx, dbRecordModel);
		tblRecord.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);

		tblPane = new JScrollPane(tblRecord);
		tblRecord.addKeyListener(fieldHelp);
		tblRecord.addMouseListener(MenuPopupListener.getEditMenuPopupListner(null, false, null));
	}


	/**
	 * Define the Child Record Table
	 *
	 */
	private void defTabChild() {

		dbChildTbl.setConnection(new ReConnection(connectionIdx));
		dbChildModel = new ChildRecordsTblMdl(
				dbChildTbl, 
				new KeyedComboMdl<Integer>(
						new ComboObjOption<Integer>(-1, "")));
		dbChildModel.setEmptyColumns(2);

		dbChildModel.setCellEditable(true);
		//dbChildModel.setEmptyColumns(1);

		tblChild = new ChildRecordsJTbl(dbChildModel, connectionIdx);
		tblChild.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);

		childPane = new JScrollPane(tblChild);

		childPane.setPreferredSize(new Dimension(CHILD_PANE_WIDTH, CHILD_PANE_HEIGHT));

		tblChild.addKeyListener(childHelp);
		tblChild.addMouseListener(new ChildMenu());
	}


	/**
	 * Define the Extra Fields Tab Panel
	 *
	 */
	private void defTabExtra() {

		JPanel delimPnl = new JPanel(new BorderLayout());
		JPanel p1 = new JPanel();
		BaseHelpPanel pnl = new BaseHelpPanel();
		
		p1.add(sfDelimiter);
		p1.add(new JLabel("  or  "));
		p1.add(sfDelimTxt);
		delimPnl.add(BorderLayout.WEST, p1);
		delimPnl.setMinimumSize(new Dimension(delimPnl.getPreferredSize().width, SwingUtils.TABLE_ROW_HEIGHT));
		
		pnl.setHelpURL(Common.formatHelpURL(Common.HELP_LAYOUT_EXTRA));
		
		pnl.setFieldNamePrefix("RcdExtra");

		pnl.addLine("Cobol Copybook"  , sfCopyBook)        .setGap(BasePanel.GAP0);

		pnl.addLine("Font Name"       , sfCanonicalName)   .setGap(BasePanel.GAP0);

		pnl.addLine("Delimiter"       , delimPnl);
		pnl.addLine("Parser"          , sfRecordStyle);
		pnl.addLine("Quote"           , sfQuote);
		pnl.addLine("Record Seperator", sfRecSepList);

		pnl.addLine("File Structure", sfStructure);

		//pnl.done();

		tabbed.addTab("Extras", pnl);
	}


	/**
	 * This method defines the visible Tabs based on the record type
	 *
	 */
	private void setTabs() {
	    boolean group = isGroup();

	    updateOptions.setChildVisible(group);

		if (group) {
			tabbed.remove(tblPane);

			tabbed.insertTab("Child Records", null, childPane,
					"Define Child Records", 0);

			updateOptions.setTableDtls(dbChildModel, tblChild, blankChildRecord);

			if (currVal != null) {
			    tblChild.setSystem(currVal.getSystem());
			}
		} else {
			tabbed.remove(childPane);

			tabbed.insertTab("Fields", null, tblPane, "Field Details", 0);

			updateOptions.setTableDtls(dbRecordModel, tblRecord, blankRecordFields);
		}
		tabbed.setSelectedIndex(0);
	}


	/**
	 * Is this a Record Layout ???
	 *
	 * @return is this a Group of Record layout ???
	 */
	private boolean isGroup() {
	    int rt = ((Integer) recordTypeMdl.getSelectedItem()).intValue();
		return rt >= Common.rtGroupOfRecords && rt != Common.RT_XML;
	}


	/**
	 * Check if the user really wants to delete a record Layout
	 * @return wether to delete the layout
	 */
	public final boolean isOkToDelete() {
		int result = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to delete record layout: " + sfRecordName.getText(),
				"Delete: " + sfRecordName.getText(),
				JOptionPane.YES_NO_OPTION);

		return (result == JOptionPane.YES_OPTION);
	}

	/**
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		Common.stopCellEditing(tblRecord);
		Common.stopCellEditing(tblChild);

		if (arg0.getSource() == sfRecordType) {
			setTabs();
		} else if (updating) {
		    // do nothing
		} else if (arg0.getSource() == btnRefresh) {
			setValues(currVal);
		} else if (arg0.getSource() == sfSystem) {
			getValues();
			if (currVal != null) {
			    tblChild.setSystem(currVal.getSystem());
			    dbChildModel.fireTableDataChanged();
			}
		} else if (arg0.getSource() == btnHelp) {
		    this.showHelp();
		}
	}

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (isGroup()) {
        	switch (action) {
        	case ReActionHandler.REFRESH: 		setValues(currVal);					break;
        	case ReActionHandler.CREATE_CHILD: 	createChildRecord();				break;
        	case ReActionHandler.EDIT_CHILD:
                int row = Math.max(0, tblChild.getSelectedRow());

                editRecordAtRow(row);
            }
        }

    }

    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {

        return ((action == ReActionHandler.CREATE_CHILD)
             || (action == ReActionHandler.REFRESH)
             || (action == ReActionHandler.EDIT_CHILD));
    }


	/**
	 * Get the Record operation action handler
	 *
	 * @return Record operation action handler
	 */
	public ReActionHandler getRecordActionHandler() {
	    return updateOptions;
	}


    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentDbIdentifier()
     */
    public int getCurrentDbIdentifier() {
        return connectionIdx;
    }
    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentDbName()
     */
    public String getCurrentDbName() {
        return "Current DB";
    }

    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentFileName()
     */
    public String getCurrentFileName() {
        return null;
    }

    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#setRecordLayout(java.lang.String)
     */
    public void setRecordLayout(int recordId, String layoutName, String file) {
        int rowCount = dbChildModel.getRowCount();
        boolean insert = true;
        ChildRecordsRec r;

        //** check to see if the record already is in the list
        for (int i = 0; i < rowCount; i++) {
            r = dbChildModel.getRecord(i);

		    if (r != null) {
		        if (recordId == r.getChildRecordId()) {
		            insert = false;
		            break;
		        }
		    }
        }

        //**      if not in the list insert it
        if (insert) {
            ChildRecordsRec child = ChildRecordsRec.getBlankChildRec(recordId);
            child.setNew(true);

            //dbChildModel.addRecordtoDB(new ChildRecordsRec(recordId, 0, "", ""));
            dbChildModel.addRow(dbChildModel.getRowCount(), child);

            getValues();
            tblChild.setSystem(currVal.getSystem());
        }

        dbChildModel.fireTableDataChanged();
    }

	/**
	 * Edit a child record at a specified row
	 * @param row2edit requested row
	 */
	private void editRecordAtRow(int row2edit) {

	    if (dbChildModel.getRowCount() == 0) {
	        createChildRecord();
	    } else {
			parentActionHandler.executeAction(ReActionHandler.SAVE);

	        new RecordEdit1Record("Current DB", connectionIdx, this,
	                row2edit, dbChildModel);
	    }
	}


	/**
	 * Create a new child record of the current record
	 *
	 */
	private void createChildRecord() {

		//dbChildModel.updateDB();
		parentActionHandler.executeAction(ReActionHandler.SAVE);
        new RecordEdit1Record("Current DB", connectionIdx, this, dbChildModel);
	}

	/**
	 * set enable status of the next button
	 * @param enable enable status
	 */
	public void setNextEnabledNext(boolean enable) {
	    nextBtn.setEnabled(enable);
	}

	/**
	 * set enable status of the prev button
	 * @param enable enable status
	 */
	public void setPrevEnabled(boolean enable) {
	    prevBtn.setEnabled(enable);
	}

	/**
	 * create popup menu for Child Records screen
	 *
	 * @author Bruce Martin
	 *
	 */
	private class ChildMenu extends MenuPopupListener {

	    private int col, row;

	    //private LayoutConnection callback = this;

	    /**
	     * setup child records popup menu
	     *
	     */
	    public ChildMenu() {
	        super(null, false, null);

	        this.getPopup().addSeparator();
	        this.getPopup().add(new AbstractAction("Edit Child Record") {
	            /**
	             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	             */
	            public void actionPerformed(ActionEvent e) {
	                editRecordAtRow(row);
	            }
	        });
	        this.getPopup().add(new AbstractAction("Create New Child Record") {
	            /**
	             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	             */
	            public void actionPerformed(ActionEvent e) {
	                createChildRecord();
	            }
	        });
	        this.getPopup().add(new AbstractAction("Edit Record Selections") {
	            public void actionPerformed(ActionEvent e) {
					new RecordSelectionPnl("", connectionIdx, dbChildTbl.getRecordId(), dbChildModel.getRecord(row));
	            }	        	
	        });
	        this.getPopup().add(new AbstractAction("View Record Selections Tree") {
	            public void actionPerformed(ActionEvent e) {
	                new SelectionTreeTablePnl(connectionIdx, "", dbChildModel);
	            }	        	
	        });
	    }


        /**
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
		public void mousePressed(MouseEvent m) {
			col = tblChild.columnAtPoint(m.getPoint());
			row = tblChild.rowAtPoint(m.getPoint());
			int tblCol = tblChild.getColumnModel().getColumn(col).getModelIndex();
			System.out.println("Column: "  + col + " " + tblCol);
			switch (tblCol) {
			case 0: editRecordAtRow(tblChild.getSelectedRow());   break;
			case 1:
				new RecordSelectionPnl("", connectionIdx, dbChildTbl.getRecordId(), dbChildModel.getRecord(row));
				break;
			}
			super.mousePressed(m);
		}
	}
}