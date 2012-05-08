/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * 3rd and final wizard panel where the user enters field
 * details
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Name changes, remove done method call
 *
 * # Version 0.61b Bruce Martin 2007/05/05
 *  - Changes o support user selecting the default type
 */
package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.RecordEditor.re.util.csv.CsvSelectionTblMdl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * 3rd and final wizard panel where the user enters field
 * details
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl4CsvNames extends WizardPanel {

    private static final int COLUMN_HEIGHT  = SwingUtils.STANDARD_FONT_HEIGHT * 25 / 2;
    private static final int FILE_HEIGHT = SwingUtils.STANDARD_FONT_HEIGHT * 8 + 4;
    private static final int NAME_WIDTH = 170;
 //   private static final int INT_TABLE_WIDTH = 60;
    private static final int INCLUDE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 3 + 3;
    private static final int TYPE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 18;
    private static final int COL_ADJUST_AMOUNT = 2;


    private JEditorPane tips;
    private ColumnTblModel columnMdl;
    private CsvSelTblMdl fileMdl;

    private JTable columnTbl = new JTable();
    private JTable fileTbl   = new JTable();

	private BmKeyedComboBox typeCombo;
	private DefaultCellEditor typeEditor;


    private Details currentDetails;
    
    private boolean alwayShow; 
    //private int[] colorInd;

    /**
     * Panel1 File Details
     *
     * @param connectionIdx Conection Identifier
     */
    public Pnl4CsvNames(AbsRowList typeList, boolean alwayShowScreen) {
        super();

		String formDescription
		    = "This screen will display the Column Details and allow you to change them. ";
		
		alwayShow = alwayShowScreen;
		tips = new JEditorPane("text/html", formDescription);

		columnTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
		columnTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fileTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        typeCombo = new BmKeyedComboBox(typeList, false);
	    typeEditor = new DefaultCellEditor(typeCombo);
	    typeEditor.setClickCountToStart(1);

		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD_PNL5));
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
		this.setGap(BasePanel.GAP1);
		this.addComponent(1, 5, COLUMN_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(columnTbl));
		this.setGap(BasePanel.GAP1);
		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(fileTbl));
		this.setGap(BasePanel.GAP1);
    }

    /**
     * wether to skip display of the screen
     */
    public final boolean skip() {
     	return (! alwayShow) && currentDetails.fieldNamesOnLine;
    }


    /**
     * get the wizard values
     */
    public final Details getValues() throws Exception {
        Common.stopCellEditing(columnTbl);
     
        return currentDetails;
    }

    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.layoutWizard.LayoutWizard.Details)
     */
    public final void setValues(Details detail) throws Exception {

        currentDetails = detail;

        //setValues_100_SetupColumns();
        setValues_200_SetupColumnTable();
        setValues_300_SetupFileTable();
     }


//    /**
//     * update the column details for display
//     * (ie correct start position
//     */
//    private void setValues_100_SetupColumns() {
//    }


    /**
     * Setup the collumn table
      */
    private void setValues_200_SetupColumnTable() {
        TableColumnModel tcm;
        TableColumn tc;

        columnMdl = new ColumnTblModel(currentDetails.standardRecord.columnDtls);
        columnTbl.setModel(columnMdl);

	    tcm  = columnTbl.getColumnModel();

	    tcm.getColumn(ColumnDetails.NAME_IDX).setPreferredWidth(NAME_WIDTH);

	    tc = tcm.getColumn((ColumnDetails.TYPE_IDX - 1 ));
	    tc.setPreferredWidth(TYPE_WIDTH);
	    tc.setCellRenderer(typeCombo.getTableCellRenderer());
	    tc.setCellEditor(typeEditor);

	    tc = tcm.getColumn((ColumnDetails.INCLUDE_IDX - COL_ADJUST_AMOUNT));
	    tc.setPreferredWidth(INCLUDE_WIDTH);
	    tc.setCellRenderer(new CheckBoxTableRender());
	    tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));

    }

    /**
     * Setup the file display table
     *
     */
    private void setValues_300_SetupFileTable() {
//        TableColumnModel tcm;
//        TableColumn tc;
//        String s;

        fileMdl = new CsvSelTblMdl();
        fileMdl.setLines(currentDetails.standardRecord.records, currentDetails.fontName);
        fileMdl.setLines2display(currentDetails.standardRecord.numRecords);
        fileMdl.setSeperator(currentDetails.actualSeperator);
        fileMdl.setQuote(currentDetails.actualQuote);
        fileMdl.setupColumnCount();
        fileMdl.setHideFirstLine(currentDetails.fieldNamesOnLine);

        fileTbl.setModel(fileMdl);
        fileMdl.fireTableDataChanged();
    }
    
    
    /**
     * Adjust the columns to remove 2 indexs
     * @param col column to be adjusted
     * @return adjusted column
     */
    private static int adjustColumn(int col) {
    	
    	if (col < 2) {
    		return col;
    	} else if (col == 2) {
    		return col + 1;
    	}
    	return col + COL_ADJUST_AMOUNT;
    }



    /**
     * Table model to display column details for the user
     *
     *
     * @author Bruce Martin
     *
     */
    private class ColumnTblModel extends AbstractTableModel {
        //private String[] lines;
        private ArrayList<ColumnDetails> columns;
 
        /**
         * Table model to display column details for the user
         *
         * @param tblColumns Column details
         */
        public ColumnTblModel(final ArrayList<ColumnDetails> tblColumns) {
            super();
            columns = tblColumns;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public final int getColumnCount() {
            return ColumnDetails.NUMBER_OF_COLUMNS - COL_ADJUST_AMOUNT;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public final int getRowCount() {
            return columns.size();
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public final Object getValueAt(int rowIndex, int columnIndex) {
            ColumnDetails colDtls = columns.get(rowIndex);

            return colDtls.getValue(adjustColumn(columnIndex));
        }

        /**
         * @see javax.swing.table.TableModel#getColumnName(int)
         */
        public final String getColumnName(int column) {
             return ColumnDetails.getColumnName(adjustColumn(column));
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public final boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public final void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            ColumnDetails colDtls = columns.get(rowIndex);
            int col = adjustColumn(columnIndex);
            try {
                colDtls.setValue(col, aValue);
                if (col == ColumnDetails.NAME_IDX) {
                    fileMdl.fireTableStructureChanged();
                }
            } catch (Exception e) {
                Common.logMsg(e.getMessage(), null);
            }
        }
    }       
    
    private class CsvSelTblMdl extends CsvSelectionTblMdl {

		/**
		 * @see net.sf.RecordEditor.re.util.csv.CsvSelectionTblMdl#getColumnName(int)
		 */
		@Override
		public final String getColumnName(int col) {
			return currentDetails.standardRecord.columnDtls.get(col).name;
		}
    	
    	
    }
  
 }
