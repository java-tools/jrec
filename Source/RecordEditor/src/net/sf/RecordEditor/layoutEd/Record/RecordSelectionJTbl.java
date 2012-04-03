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
import javax.swing.JComboBox;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;



import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * Child record Table
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


    private static final String[] OPERATORS = {"=", "!=", "<>", ">", ">=",  "<", "<=",};
    private ComboBoxRender operatorRender = new ComboBoxRender(OPERATORS);
    //private DefaultTableCellRenderer operatorRender = new DefaultTableCellRenderer()
//	private int connectionIdx;

	
	private DefaultCellEditor operatorEditor = new DefaultCellEditor(new JComboBox<String>(OPERATORS));
	//DBComboBoxRender  recordRendor;

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
