/*
 * Created on 19/09/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - Added new Child Record Popup
 *   - ensure all Child Record layout are in the Combo Box
 *     (even those in other Systems)
 */
package net.sf.RecordEditor.layoutEd.Record;

/**
 * This class implements a JTable to display Records Selection Expression
 *
 * @author Bruce Martin
 *
 */



import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableColumnModel;

import net.sf.RecordEditor.re.db.Record.ChildRecordsRec;
import net.sf.RecordEditor.re.db.Record.RecordFieldsDB;
import net.sf.RecordEditor.re.db.Record.RecordFieldsRec;
import net.sf.RecordEditor.re.db.Record.RecordSelectionRec;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBtableModel;
import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.ComboBoxs.EnglishComboEditorAdaptor;
import net.sf.RecordEditor.utils.swing.ComboBoxs.RecordSelOperatorCombo;



/**
 * This class implements a JTable to display Records Selection Expression
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RecordSelectionJTbl extends AbsJTable {

	private static final int FLD_OR = 0;
	private static final int FLD_AND = 1;
	private static final int FLD_FIELD_NAME = 2;
	private static final int FLD_OPERATOR   = 3;
	private static final int FLD_VALUE      = 4;

    private static final int FW_BOOL_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 11/ 2;
    private static final int FW_VALUE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 11;
    private static final int FW_WIDE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 25;


//	private RecordSelectionDB recordSelDb = new RecordSelectionDB();


    //private static final String[] OPERATORS = {"=", "!=", "<>", ">", ">=",  "<", "<=",};
    private EnglishComboEditorAdaptor operatorRender = new EnglishComboEditorAdaptor(new RecordSelOperatorCombo());

	private EnglishComboEditorAdaptor operatorEditor = new EnglishComboEditorAdaptor(new RecordSelOperatorCombo());
	//DBComboBoxRender  recordRendor;
	private String[] fields;



  /**
   * Create the Child record Table
   *
   * @param mdl Table model
   * @param dbIdx Database Index
   */
  public RecordSelectionJTbl(
		  final DBtableModel<RecordSelectionRec> mdl,
		  final int dbIdx,
		  final ChildRecordsRec parent) {
      super(mdl);

      String s;
      RecordFieldsDB db = new RecordFieldsDB();
      RecordFieldsRec rec;
      ArrayList<String> fieldsList = new ArrayList<String>();
      HashSet<String> used = new HashSet<String>();
//      HashSet<String>

	  fieldsList.add("");
      for (int i = 0; i < mdl.getRowCount(); i++) {
    	  s = mdl.getRecord(i).getFieldName();
    	  if (s != null && ! "".equals(s) && ! used.contains(s)) {
    		  fieldsList.add(s);
    		  used.add(s);
    	  }
      }
      db.setConnection(new ReConnection(dbIdx));
      db.setOrderBy(" Order By Field_Name");
      db.resetSearch();
      db.setParams(parent.getChildRecordId());
      db.open();
     // System.out.println(" Field Search: " + parent.getChildRecord());
      while ((rec = db.fetch()) != null) {
    	  s = rec.getValue().getName();
    	  if (! used.contains(s)) {
    		  fieldsList.add(s);
    		  used.add(s);
    	  }
      }
      db.close();
      fields = new String[fieldsList.size()];
      fields = fieldsList.toArray(fields);

      setColumnSizes();
      setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
  }


  	/**
  	 * Set the column sizes
  	 */
	public void setColumnSizes() {

	  	 this.setAutoResizeMode(AUTO_RESIZE_OFF);
	  	 TableColumnModel tcm = this.getColumnModel();

	  	 tcm.getColumn(FLD_OR).setPreferredWidth(FW_BOOL_WIDTH);
	  	 tcm.getColumn(FLD_AND).setPreferredWidth(FW_BOOL_WIDTH);
	  	 tcm.getColumn(FLD_FIELD_NAME).setPreferredWidth(FW_WIDE_WIDTH);
	  	 tcm.getColumn(FLD_OPERATOR).setPreferredWidth(FW_BOOL_WIDTH);
	  	 tcm.getColumn(FLD_VALUE).setPreferredWidth(FW_VALUE_WIDTH);

	     tcm.getColumn(FLD_OPERATOR).setCellRenderer(operatorRender);
	     tcm.getColumn(FLD_OPERATOR).setCellEditor(operatorEditor);

	     tcm.getColumn(FLD_FIELD_NAME).setCellRenderer(new ComboBoxRender(fields));
	     tcm.getColumn(FLD_FIELD_NAME).setCellEditor(new DefaultCellEditor(new JComboBox(fields)));



	     //ParentList = new ParentList()
//		TableColumnModel tcm = this.getColumnModel();
//
//		tcm.getColumn(0).setCellRenderer(tableBtn);
//		tcm.getColumn(0).setMaxWidth(5);
//		tcm.getColumn(1).setCellRenderer(tableBtn);
//		tcm.getColumn(1).setMaxWidth(5);
//
//		tcm.getColumn(2).setCellRenderer(locChildRecord.getTableCellRenderer());
//		tcm.getColumn(2).setCellEditor(childEditor);
//
//		tcm.getColumn(6).setCellRenderer(parentCombo.getTableCellRenderer());
//		tcm.getColumn(6).setCellEditor(new DefaultCellEditor(parentCombo));
//
//
//		tcm.moveColumn(1, 5);

	}


}
