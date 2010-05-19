package net.sf.RecordEditor.utils.Combo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.layoutEd.Table.TableDB;
import net.sf.RecordEditor.layoutEd.Table.TableRec;
import net.sf.RecordEditor.layoutEd.utils.TableUpdatePnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;

import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;

public class ComboPnl extends BaseHelpPanel implements ActionListener {



	//private JTextField sfCombo_Id = new JTextField();
	private static final String[] COLUMN_TYPE_LIST = {"Standard Combo", "Key / Value Combo"};

	private TableDB systemTable = new TableDB();
	private DBComboModel<TableRec> systemModel = new DBComboModel<TableRec>(systemTable, 0, 1 , false, false);
	private BmKeyedComboBox sfSystem;
	private JTextField sfComboName = new JTextField();
	private JComboBox sfColumnType = new JComboBox(COLUMN_TYPE_LIST);

	private JTextField message = new JTextField();

	private TableUpdatePnl<ComboValuesRec> updateOptions;

	private String msgText = "";
	private ComboRec currVal;
	private int connectionIdx;

	private ComboValuesDB valuesDB = new ComboValuesDB();

	private DBtableModel<ComboValuesRec> comboDetailsModel;
	private AbsJTable comboDetails;

	public  ComboPnl(ComboRec value, int connectionIndex) {
		super();
		
		connectionIdx = connectionIndex;		

		init_100_setupScreenFields();
		setValues(value);

		//addComponent("Combo_Id", sfCombo_Id);
		addComponent("System", sfSystem);
		addComponent("Combo_Name", sfComboName);
		addComponent("Combo Type", sfColumnType);
		setGap(BasePanel.GAP0);
		updateOptions = new TableUpdatePnl<ComboValuesRec>(this, null);

		addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
				BasePanel.FULL, BasePanel.FULL,
				comboDetails);

		addMessage(message);

		updateOptions.setTableDtls(comboDetailsModel, comboDetails, new ComboValuesRec());
	}

	private void init_100_setupScreenFields() {
		ReConnection con = new ReConnection(connectionIdx);
		
		this.setHelpURL(Common.formatHelpURL(Common.HELP_COMBO_EDIT));

		systemTable.setConnection(con);
		systemTable.setParams(3);
		sfSystem = new BmKeyedComboBox(systemModel, false);
		
		valuesDB.setConnection(con);
		//valuesDB.setParams(currVal.getComboId());

		comboDetailsModel = new DBtableModel<ComboValuesRec>(ReMainFrame.getMasterFrame(), valuesDB);
		comboDetailsModel.setCellEditable(true);
		
		comboDetails = new AbsJTable(comboDetailsModel);
	
		sfColumnType.addActionListener(this);
	}



	/**
	 *  This method sets the value of the Screen values from the values in  Val.
	 *  
	 *  @param val holds the values to be assigned to screen fields
	 */
	public void setValues(ComboRec val) {

		currVal = val;
		if (val == null) {
			//sfSystem.setSelectedItem(0);
			sfComboName.setText("");
			sfColumnType.setSelectedIndex(0);
			valuesDB.setParams(Constants.NULL_INTEGER);
			comboDetailsModel.removeAll();
		} else {
			sfSystem.setSelectedItem(new Integer(val.getSystem()));
			sfComboName.setText(val.getComboName());
			sfColumnType.setSelectedIndex(val.getColumnType() - 1);
			valuesDB.setParams(currVal.getComboId());
			comboDetailsModel.load();
		}
		valuesDB.setColumnCount(sfColumnType.getSelectedIndex() + 1);
		comboDetailsModel.fireTableStructureChanged();
	}


	/**
	 * Clone and return the current record
	 * @return cloned record
	 */
	public ComboRec getClone(String newName) {
		ComboRec ret = null;
		ComboRec temp = getValues();
		
		if (temp.isUpdateSuccessful()) {
			ComboValuesRec rec;
			int numRecs = comboDetailsModel.getRowCount();
			
			ret = (ComboRec) temp.clone();
			ret.setComboName(newName);
			sfComboName.setText(newName);
			
			for (int i = 0; i < numRecs; i++) {
				rec = (ComboValuesRec) comboDetailsModel.getRecord(i).clone();
				rec.setNew(true);
				comboDetailsModel.setRecord(i, rec);
			}
			currVal = ret;
		}
		
		return ret;
	}
	

	/**
	 *  This method gets the Screen values as a ComboListRec
	 *
	 *  @return values of screen fields as a ComboListRec
	 */
	public ComboRec getValues() {
		String fld="";
		
		if (currVal == null) {
			currVal = new ComboRec();
		}

		try {
			currVal.setUpdateSuccessful(true);
			fld = "System";
			currVal.setSystem(((Integer) sfSystem.getSelectedItem()).intValue());
			fld = "Combo_Name";
			currVal.setComboName(sfComboName.getText());
			fld = "Column_Type";
			currVal.setColumnType(sfColumnType.getSelectedIndex() + 1);
		} catch (Exception ex) {
			currVal.setUpdateSuccessful(false);
			setMessage("Invalid Field " + fld + " - " + ex.getMessage());
		}
		return currVal;
	}
	
	/**
	 * Save Combo Items back to the DB
	 */
	public void saveItems() {
		valuesDB.setParams(currVal.getComboId());
		comboDetailsModel.updateDB();
	}


	private void setMessage(String msg) {
		message.setText(msg);
		msgText = msg;
	}

	/**
	 *  Get the error Message
	 */
	public String getMsg() {
		return msgText;
	}
	
	/**
	 * Stop cell editing
	 *
	 */
	public void stopCellEditing() {
	    Common.stopCellEditing(comboDetails);
	}


	/**
	 * Get action handler for editing Table lines
	 *
	 * @return requested action handler
	 */
	public ReActionHandler getLinesActionHandler() {
	    return updateOptions;
	}

	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
	
		if (event.getSource() == sfColumnType) {
			valuesDB.setColumnCount(sfColumnType.getSelectedIndex() + 1);
			comboDetailsModel.fireTableStructureChanged();
		}
		
	}

	

	/**
	 * Check if the user really wants to delete a record Layout
	 * @return wether to delete the layout
	 */
	public final boolean isOkToDelete() {
		boolean ret = false;
		
		if (currVal != null) {
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete record layout: " + sfComboName.getText(),
					"Delete: " + sfComboName.getText(),
					JOptionPane.YES_NO_OPTION);

			ret = result == JOptionPane.YES_OPTION;
			if (ret) {
				valuesDB.setParams(currVal.getComboId());
				valuesDB.deleteAll();
			}
		}
		return true;
	}
}
