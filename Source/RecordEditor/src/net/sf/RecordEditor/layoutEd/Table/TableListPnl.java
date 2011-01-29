/*
 * Created on 21/08/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - added ReActionHandler support
 *   - added right click popup menu
 *   - Changed to use BasePanel constants instead of
 *     TableLayout constants
 */
package net.sf.RecordEditor.layoutEd.Table;


/**
 * This panel display the Table Line details
 *
 * @author Bruce Martin
 * @version 0.51
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.RecordEditor.layoutEd.utils.TableUpdatePnl;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsRecord;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * This panel is for displaying a list of Table from which the user can select one
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public final class TableListPnl extends BaseHelpPanel {


  private static final int DESCRIPTION_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 5;
  private static final int TABLE_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 33;
  private static final int TABLE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 15 + 5;

  private JPanel  btnPanel     = new JPanel();
  private JButton helpBtn      = Common.getHelpButton();
  private JTextField sfTBlId   = new JTextField();
  private JTextField sfTblName = new JTextField();
  private JTextArea sfDescription = new JTextArea();

  private TableListDB dbTblList;
  private TableDB dbTbl = new TableDB();
  private DBtableModel<TableRec> dbTblModel;
  private TableListJTbl tblTable;
  private JScrollPane tblPane;

  private TableUpdatePnl<TableRec> updateOptions;


  private String msg = "";

  private JFrame frame;

  private TableListRec currVal;

  private int connectionId;


  /**
   * This class displays a Table
   *
   * @param jframe Parent Frame
   * @param dbList DB-Query that lists all the tables
   * @param val Initial Table Value
   * @param connectionIdx DB Connection Identifier
   */
  public  TableListPnl(final JFrame jframe,
  					   final TableListDB dbList,
					   final TableListRec val,
					   final int connectionIdx) {
      super();

      frame     = jframe;
      dbTblList = dbList;
      currVal   = val;
      connectionId = connectionIdx;

      this.setHelpURL(Common.formatHelpURL(Common.HELP_TABLE));

      btnPanel.setLayout(new BorderLayout());
      btnPanel.add(BorderLayout.EAST, helpBtn);

      addLine("", btnPanel);
      addLine("Table Id", sfTBlId);
      addLine("Table Name", sfTblName);
      addLine("Description", sfDescription);
      setHeight(DESCRIPTION_HEIGHT);
      setGap(BasePanel.GAP0);

      updateOptions = new TableUpdatePnl<TableRec>(this, null);
      setGap(BasePanel.GAP0);

      defTable();
      addComponent(1, 3, BasePanel.FILL, BasePanel.GAP,
             BasePanel.FULL, BasePanel.FULL,
			 tblPane);

      updateOptions.setTableDtls(dbTblModel, tblTable, new TableRec());

      helpBtn.addActionListener(new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
              showHelp();
          }
      }
      );

      //done();

      dbTblModel.setCellEditable(true);
  }


  /**
   * Set The screen fields to a new Table
   *
   * @param val Table Header Details
   */
  public final void setValues(final TableListRec val) {

     	Common.stopCellEditing(tblTable);

     	setValuesInternal(val);
        if (val != null) {
           	dbTbl.setParams(val.getTBlId());

        	dbTblModel.load();

        	dbTblModel.fireTableDataChanged();
         }
  }



  /**
   * Assign a Table List to be displayed
   *
   * @param tableItem Table Item to be displayed
   */
  private void setValuesInternal(final TableListRec tableItem) {

  	  currVal = tableItem;

      if (tableItem != null) {
           sfTBlId.setText("" + tableItem.getTBlId());
           sfTblName.setText(tableItem.getTblName());
           sfDescription.setText(tableItem.getDescription());
      }
  }


  /**
   * Get The table Header values from the Screen
   *
   * @return Table-Header Details
   */
  public final AbsRecord getAbsValues() {
      return getValues();
  }


  /**
   * Get The table Header values from the Screen
   *
   * @return Table-Header Details
   */
  public final TableListRec getValues() {

      try {
      		currVal.setUpdateSuccessful(true);
      		msg = "";

       		currVal.setTBlId(Integer.parseInt(sfTBlId.getText()));
       		currVal.setTblName(sfTblName.getText());
       		currVal.setDescription(sfDescription.getText());
       } catch (Exception ex) {
    	   ex.printStackTrace();
       		currVal.setUpdateSuccessful(false);
       		msg = "Invalid numeric Field  - " + ex.getMessage();
       }
      return currVal;
  }


  	/**
  	 * Get themessage
  	 *
  	 * @return any error message
  	 */
  	public final String getMsg() {
  	    return msg;
  	}



  	/**
  	 * define the JTable
  	 *
  	 */
	private void defTable() {

		dbTbl.setConnection(new ReConnection(connectionId));

		//System.out.println("TableList " + (currVal== null));
		dbTbl.setParams(currVal.getTBlId());
		setValuesInternal(currVal);

		dbTblModel = new DBtableModel<TableRec>(frame, dbTbl);

		tblTable   = new TableListJTbl(dbTblModel);

		tblTable.setColumnSizes();

		tblPane = new JScrollPane(tblTable);
		tblPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		this.registerComponent(tblTable);
		tblTable.addMouseListener(MenuPopupListener.getEditMenuPopupListner(null, false, null));
	}


	/**
	 * Save Table details back to the DB
	 *
	 */
	public final void saveTableDetails() {

		dbTblList.checkAndUpdate(getValues());

		dbTblModel.updateDB();
	}


	/**
	 * Stop cell editing
	 *
	 */
	public void stopCellEditing() {
	    Common.stopCellEditing(tblTable);
	}


	/**
	 * Get action handler for editing Table lines
	 *
	 * @return requested action handler
	 */
	public ReActionHandler getLinesActionHandler() {
	    return updateOptions;
	}
}