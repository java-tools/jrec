/*
 * Created on 18/09/2004
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - refactry changes (methods moved from LayoutCommon to
 *     Common).
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Use new TableList class (which includes Types defined in the
 *     parameter File as well as those in the DB).
 *
 * # version 0.61 (Bruce Martin) 2007/04/14
 *   - changed to use new TypeList, variable name changes
 */
package net.sf.RecordEditor.layoutEd.Record;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.layoutEd.Parameter.ParameterTableEditor;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.AbsJTable;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;



/**
 * This class displays all the fields belonging to a record on a swing JTable.
 *
 * @author Bruce Martin
 * @version 0.51
 */
@SuppressWarnings("serial")
public class RecordFieldsJTbl extends AbsJTable {

    private static final int FW_INT_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 11/ 2;
    private static final int FW_NAME_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 11;
    private static final int FW_TEXT_WIDTH = FW_NAME_WIDTH * 2;
    private static final int FW_WIDE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 25;

    private static final int FLD_FIELD_TYPE = 4;
    private static final int FLD_DECIMAL    = 5;
    private static final int FLD_FORMAT     = 6;
    private static final int FLD_PARAMETER  = 7;
    private static final int FLD_DEFAULT    = 8;
    private static final int FLD_COBOL_NAME = 9;

	private BmKeyedComboModel typeModel;
	private BmKeyedComboModel formatModel;
	private BmKeyedComboBox locType;
	private BmKeyedComboBox formatCombo;

	private int connectionIdx;

	//DBComboBoxRender  recordRendor;
	private DefaultCellEditor typeEditor;
	private DefaultCellEditor formatEditor;


	/**
	 * Record Fields Table Creater
	 *
	 * @param connectionIndex Database Connection Index
	 */
	public RecordFieldsJTbl(final int connectionIndex) {
		super();

		this.connectionIdx = connectionIndex;
		commonInit();
	}


	/**
	 * Record Fields Table Creater
	 *
	 * @param connectionIndex Database Connection Index
	 * @param mdl Table Model
	 */
	public RecordFieldsJTbl(final int connectionIndex, final TableModel mdl) {
	      super(mdl);

	      this.connectionIdx = connectionIndex;
	      commonInit();
	}


	/**
	 * Common Initialise
	 *
	 */
	public void commonInit() {
	    //Connection con = Common.getDBConnectionLogErrors(connectionIdx);
	    typeModel = new BmKeyedComboModel(new TypeList(connectionIdx, true, false));
		formatModel = new BmKeyedComboModel(
		        new TableList(connectionIdx, Common.TI_FORMAT, true, false,
		                Parameters.FORMAT_NUMBER_PREFIX, Parameters.FORMAT_NAME_PREFIX,
		                Parameters.NUMBER_OF_FORMATS));

	    locType = new BmKeyedComboBox(typeModel, false);
	    formatCombo = new BmKeyedComboBox(formatModel, false);

	    typeEditor = new DefaultCellEditor(locType);
	    typeEditor.setClickCountToStart(1);
	    formatEditor = new DefaultCellEditor(formatCombo);
	    formatEditor.setClickCountToStart(1);

	    setColumnSizes();
	}


	/**
	 * Set the column sizes
	 */
	public void setColumnSizes() {

	  	 this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	  	 TableColumnModel tcm = this.getColumnModel();

	  	 tcm.getColumn(0).setPreferredWidth(FW_INT_WIDTH);
	  	 tcm.getColumn(1).setPreferredWidth(FW_INT_WIDTH);
	  	 tcm.getColumn(2).setPreferredWidth(FW_NAME_WIDTH);
	  	 tcm.getColumn(3).setPreferredWidth(FW_TEXT_WIDTH);
	  	 tcm.getColumn(FLD_FIELD_TYPE).setPreferredWidth(FW_WIDE_WIDTH);
	  	 tcm.getColumn(FLD_DECIMAL).setPreferredWidth(FW_INT_WIDTH);
	  	 tcm.getColumn(FLD_FORMAT).setPreferredWidth(FW_TEXT_WIDTH);
	  	 tcm.getColumn(FLD_PARAMETER).setPreferredWidth(FW_WIDE_WIDTH);
	  	 tcm.getColumn(FLD_DEFAULT).setPreferredWidth(FW_NAME_WIDTH);
	  	 tcm.getColumn(FLD_COBOL_NAME).setPreferredWidth(FW_WIDE_WIDTH);

	     tcm.getColumn(FLD_FIELD_TYPE).setCellRenderer(locType.getTableCellRenderer());
	     tcm.getColumn(FLD_FIELD_TYPE).setCellEditor(typeEditor);
	     tcm.getColumn(FLD_FORMAT).setCellRenderer(formatCombo.getTableCellRenderer());
	     tcm.getColumn(FLD_FORMAT).setCellEditor(formatEditor);
	     
	     tcm.getColumn(FLD_PARAMETER).setCellRenderer(new ParameterTableEditor(connectionIdx));
	     tcm.getColumn(FLD_PARAMETER).setCellEditor(new ParameterTableEditor(connectionIdx));
	}
}
