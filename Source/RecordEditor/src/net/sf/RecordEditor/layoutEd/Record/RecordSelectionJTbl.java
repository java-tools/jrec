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
 * This class implements a JTable to display Child Records of a parent
 * record
 *
 * @author Bruce Martin
 *
 */



import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.JRecord.Common.AbsRow;
import net.sf.RecordEditor.re.db.Record.RecordSelectionDB;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;
import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.AbstractRowList;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.ButtonTableRendor;



/**
 * Child record Table
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class RecordSelectionJTbl extends AbsJTable {

	private RecordSelectionDB recordSelDb = new RecordSelectionDB();
    private BmKeyedComboBox locChildRecord;

    private DefaultCellEditor childEditor;

	private ButtonTableRendor tableBtn = new ButtonTableRendor();



    /**
     * Create the Child record Table
     *
     * @param dbIdx Database Index
     */
    public RecordSelectionJTbl(final int dbIdx) {
        super();

        commonInit(dbIdx);
    }


  /**
   * Create the Child record Table
   *
   * @param mdl Table model
   * @param dbIdx Database Index
   */
  public RecordSelectionJTbl(final TableModel mdl, final int dbIdx) {
      super(mdl);

      commonInit(dbIdx);
  }


  /**
   * common initialisation
   *
   * @param dbIdx Database Index
   */
  private void commonInit(int dbIdx) {

	 
     // childEditor = new DefaultCellEditor(locChildRecord);

      setColumnSizes();
  }


  	/**
  	 * Set the column sizes
  	 */
	public void setColumnSizes() {
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
