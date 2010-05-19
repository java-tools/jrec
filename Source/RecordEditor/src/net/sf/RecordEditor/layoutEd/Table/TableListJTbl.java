/*
 * Created on 21/09/2004
 *
 */
package net.sf.RecordEditor.layoutEd.Table;

/**
 * This class displays a list of "Tables" (from the Table DB Tbl_T_Table)
 * in a Swing Jtable. The user is then able to select a specific Table to
 * be displayed.
 *
 * @author Bruce Martin
 *
 */



import javax.swing.JTable;

import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.utils.swing.AbsJTable;



/**
 * This class control the column sizes of the Table List
 *
 * @author Bruce Martin
 *
 */
public final class TableListJTbl extends AbsJTable {

    private static final int KEY_WIDTH          = 60;
    private static final int DESCRIPTION_WIDTH  = 300;


	/**
	 * This class control the column sizes of the Table List
	 *
	 * @param mdl Table Model
	 */
  public TableListJTbl(final TableModel mdl) {
      super(mdl);
  }




  /**
   * Set The column sizes
   */
  public void setColumnSizes() {

	setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	TableColumn tc = getColumnModel().getColumn(0);
	tc.setPreferredWidth(KEY_WIDTH);
	tc = getColumnModel().getColumn(1);
	tc.setPreferredWidth(DESCRIPTION_WIDTH);
  }
}
