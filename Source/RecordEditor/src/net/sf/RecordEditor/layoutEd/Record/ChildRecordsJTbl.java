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
public class ChildRecordsJTbl extends AbsJTable {

	private static final Integer NULL_PARENT = new Integer(-1);
    private RecordDB childRecordRecords   = new RecordDB();
    private DBComboModel<RecordRec> childRecordModel
    		= new DBComboModel<RecordRec>(childRecordRecords, 0, 1 , true, false);
    private BmKeyedComboBox locChildRecord;

    private DefaultCellEditor childEditor;

	private ButtonTableRendor tableBtn = new ButtonTableRendor();



    /**
     * Create the Child record Table
     *
     * @param dbIdx Database Index
     */
    public ChildRecordsJTbl(final int dbIdx) {
        super();

        commonInit(dbIdx);
    }


  /**
   * Create the Child record Table
   *
   * @param mdl Table model
   * @param dbIdx Database Index
   */
  public ChildRecordsJTbl(final TableModel mdl, final int dbIdx) {
      super(mdl);

      commonInit(dbIdx);
  }


  /**
   * common initialisation
   *
   * @param dbIdx Database Index
   */
  private void commonInit(int dbIdx) {

      childRecordRecords.setConnection(new ReConnection(dbIdx));
      childRecordRecords.resetSearch();
      childRecordRecords.setSearchRecordType(AbsDB.opLT, Common.rtGroupOfRecords);

      //ChildRecord_Model.setTableCombo(true);
      locChildRecord = new BmKeyedComboBox(childRecordModel, true);
     // ChildRecord_Model_4_Render = new DBComboModel(ChildRecord_Model.getList());
      //ChildRecord_Model_4_Render.setTableCombo(true);

      /*childRender = // new DBComboBox(ChildRecord_Model_4_Render, true).agent;
      				new DBComboBoxRender(ChildRecord_Model_4_Render, false);*/
      childEditor = new DefaultCellEditor(locChildRecord);

      setColumnSizes();
  }


  	/**
  	 * Set the column sizes
  	 */
	public void setColumnSizes() {
		//ParentList = new ParentList()
		BmKeyedComboBox parentCombo = new BmKeyedComboBox(new ParentList(), false);

		TableColumnModel tcm = this.getColumnModel();

		tcm.getColumn(0).setCellRenderer(tableBtn);
		tcm.getColumn(0).setMaxWidth(5);

		tcm.getColumn(1).setCellRenderer(locChildRecord.getTableCellRenderer());
		tcm.getColumn(1).setCellEditor(childEditor);

		tcm.getColumn(5).setCellRenderer(parentCombo.getTableCellRenderer());
		tcm.getColumn(5).setCellEditor(new DefaultCellEditor(parentCombo));
}


	/**
	 * Set the System id
	 *
	 * @param system new system
	 */
	public void setSystem(int system) {

	    childRecordRecords.resetSearch();
	    childRecordRecords.setSearchRecordType(AbsDB.opLT, Common.rtGroupOfRecords);

	    if (system != Common.UNKNOWN_SYSTEM) {
	        TableModel mdl = getModel();
	        StringBuffer b = new StringBuffer();
	        char sep = '(';

	        if (mdl.getRowCount() == 0) {
	            childRecordRecords.setSearchSystem(AbsDB.opEquals, system);
	            //system = NULL_SYSTEM;
	        } else {
	            for (int i = mdl.getRowCount() - 1; i >= 0; i--) {
	                b.append(sep).append(mdl.getValueAt(i, 1));
	                sep = ',';
	            }
	            b.append(')').append(')');
	            childRecordRecords.setSqlOperator(" And (");
	            childRecordRecords.setSearchSystem(AbsDB.opEquals, system);
	            childRecordRecords.setSqlOperator(" Or ");
	            childRecordRecords.setSearchArg("RecordId", AbsDB.opIn, b.toString());
	            //childRecordRecords.printSQL();
	        }

			//ChildRecord_Model_4_Render.setSelectedItem(ChildRecord_Model.getSelectedItem());
			//ChildRecord_Model.copyToModel(ChildRecord_Model_4_Render);
		}
		childRecordModel.reload();
	}
	
	private class ParentList implements AbstractRowList {

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(AbsRow row1, AbsRow row2) {
			return 0;
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getElementAt(int)
		 */
		public Object getElementAt(int idx) {

			if (idx == 0) {
				//System.out.println("1 >> " + idx + " " + NULL_PARENT);
				return NULL_PARENT;
			}
			//System.out.println("2 >> " + idx + " " + getModel().getValueAt(idx - 1, 1));
			return getModel().getValueAt(idx - 1, 1);
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getFieldAt(int)
		 */
		public Object getFieldAt(int idx) {

			if (idx == 0) {
				return " ";
			}
			//System.out.println(">> " + idx + " " + getModel().getValueAt(idx - 1, 1));
			AbstractRowList list =  childRecordModel.getList();
			return list.getFieldFromKey(getModel().getValueAt(idx - 1, 1));
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getFieldFromKey(java.lang.Object)
		 */
		public Object getFieldFromKey(Object key) {
			Integer val = (Integer) key;
			
			if (val == null || val.intValue() < 0) {
				//System.out.println("-->> 0 >>" + key);
				return " ";
			}
			
			AbstractRowList list =  childRecordModel.getList();

			//System.out.println("-->> 1 >>" + key);
			return list.getFieldFromKey(key);
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getIndexOf(java.lang.Object)
		 */
		public int getIndexOf(Object arg0) {
			Integer val = (Integer) arg0;
			
			if (val.intValue() < 0) {
				return 0;
			}
			
			int ret = 0;
			TableModel mdl = getModel();
			Integer tblVal;
			
			for (int i = 0; i < mdl.getRowCount(); i++) {
				tblVal = (Integer) mdl.getValueAt(i, 1);
				if (val.equals(tblVal)) {
					ret = i + 1;
					break;
				}
			}
			return ret;
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#getSize()
		 */
		public int getSize() {
			return getModel().getRowCount() + 1;
		}
		
		public TableModel getModel() {
			return ChildRecordsJTbl.this.getModel();
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#isSorted()
		 */
		public boolean isSorted() {
			return false;
		}

		/**
		 * @see net.sf.RecordEditor.utils.swing.AbstractRowList#reload()
		 */
		public void reload() {
			
		}
		
		
		
	}
}
