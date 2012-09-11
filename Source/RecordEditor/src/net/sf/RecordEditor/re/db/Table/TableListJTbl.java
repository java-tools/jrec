/*
 * Created on 21/09/2004
 *
 */
package net.sf.RecordEditor.re.db.Table;

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
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * This class control the column sizes of the Table List
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public final class TableListJTbl extends AbsJTable {

    private static final int KEY_WIDTH          = SwingUtils.STANDARD_FONT_WIDTH * 7;
    private static final int DESCRIPTION_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 33;


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
