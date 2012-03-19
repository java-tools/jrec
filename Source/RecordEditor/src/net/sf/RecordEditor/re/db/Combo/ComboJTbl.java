package net.sf.RecordEditor.re.db.Combo;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.re.db.Table.TableDB;
import net.sf.RecordEditor.re.db.Table.TableRec;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.jdbc.DBComboModel;

import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBoxRender;

/**
 * This class implements a Jtable. It is intended for use with the DBTable Model
 *
 */
public class ComboJTbl extends AbsJTable {

	private TableDB System_Table = new TableDB();
    private DBComboModel<TableRec> System_Model = new DBComboModel<TableRec>(System_Table, 0, 1 , false, false);
    private BmKeyedComboBox locSystem;

//  public ComboJTbl(final int dbIdx) {
//      super();
//
//      commonInit(dbIdx);
//  }


  /**
   * Create ComboList JTable
   * @param mdl table model
   * @param dbIdx DB index
   */
  public ComboJTbl(final TableModel mdl, final int dbIdx) {
      super(mdl);

      commonInit(dbIdx);
  }


  /**
   *  This method performs common initialisation. It is called by all the constructors
   */
  private void commonInit(final int dbIdx) {

      System_Table.setConnection(new ReConnection(dbIdx));
      System_Table.setParams(3);
      locSystem = new BmKeyedComboBox(System_Model, false);

      setColumnSizes();
  }

  /**
   *  This method sets up column sizes / Renders / Editors
   *
   */
  public void setColumnSizes() {
	  
	     TableColumnModel tcm = this.getColumnModel();

	      tcm.getColumn(0).setCellRenderer(new BmKeyedComboBoxRender(System_Model, false));
	      tcm.getColumn(0).setCellEditor(new DefaultCellEditor(locSystem));
 }
}
