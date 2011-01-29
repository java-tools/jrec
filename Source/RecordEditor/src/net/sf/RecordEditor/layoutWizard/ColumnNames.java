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

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.RecordEditor.utils.MenuPopupListener;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
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
public class ColumnNames {


    private static final int NAME_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 19;
    private static final int INT_TABLE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 13 / 2;
    private static final int INCLUDE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 3 + 3;
    private static final int TYPE_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 18;

    private ColumnTblModel columnMdl;
    private FileTblModel fileMdl;

    public final JTable columnTbl = new JTable();
    public final JTable fileTbl   = new JTable();

    private BmKeyedComboBox typeCombo;
	private DefaultCellEditor typeEditor;


    private Details currentDetails;
    private RecordDefinition recordDefinition;


 
    @SuppressWarnings("serial")
	public ColumnNames(AbsRowList typeList) {
		columnTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
		columnTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fileTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        typeCombo = new BmKeyedComboBox(typeList, false);
	    typeEditor = new DefaultCellEditor(typeCombo);
	    typeEditor.setClickCountToStart(1);
	    
	    columnTbl.addMouseListener(new MenuPopupListener(new AbstractAction("Generate Field Names") {
                    public void actionPerformed(ActionEvent e) {
                    	nameColumns();
                    } 
	    }));
    }



    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#getValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public Details getValues() throws Exception {
        Common.stopCellEditing(columnTbl);
        
        return currentDetails;
    }

    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public void setValues(Details detail, RecordDefinition recordDef) throws Exception {

        currentDetails = detail;
        recordDefinition = recordDef;

        if (recordDefinition.columnDtls.size() == 0
        ||  recordDefinition.columnDtls.get(0).start != 1) {
            recordDefinition.columnDtls.add(0, new ColumnDetails(1, detail.defaultType.intValue()));
        }

        setValues_100_SetupColumns();
        setValues_200_SetupColumnTable();
        setValues_300_SetupFileTable();
     }


    /**
     * update the column details for display
     * (ie correct start position
     */
    private void setValues_100_SetupColumns() {
        int i, colEnd;
        ColumnDetails colDtls;


        colEnd = 0;
        for (i = 0; i < recordDefinition.numRecords; i++) {
            if (colEnd < recordDefinition.records[i].length) {
                colEnd = recordDefinition.records[i].length;
            }
        }

        for (i = recordDefinition.columnDtls.size() - 1; i >= 0; i--) {
            colDtls = recordDefinition.columnDtls.get(i);
            colDtls.length = colEnd - colDtls.start + 1;
            colEnd = colDtls.start - 1;
        }
    }


    /**
     * Setup the collumn table
      */
    private void setValues_200_SetupColumnTable() {
        TableColumnModel tcm;
        TableColumn tc;

        columnMdl = new ColumnTblModel(recordDefinition.columnDtls);
        columnTbl.setModel(columnMdl);

	    tcm  = columnTbl.getColumnModel();

	    tcm.getColumn(ColumnDetails.NAME_IDX).setPreferredWidth(NAME_WIDTH);
	    tcm.getColumn(ColumnDetails.START_IDX).setPreferredWidth(INT_TABLE_WIDTH);
	    tcm.getColumn(ColumnDetails.LENGTH_IDX).setPreferredWidth(INT_TABLE_WIDTH);
	    tcm.getColumn(ColumnDetails.DECIMAL_IDX).setPreferredWidth(INT_TABLE_WIDTH);

	    tc = tcm.getColumn(ColumnDetails.TYPE_IDX);
	    tc.setPreferredWidth(TYPE_WIDTH);
	    tc.setCellRenderer(typeCombo.getTableCellRenderer());
	    tc.setCellEditor(typeEditor);

	    tc = tcm.getColumn(ColumnDetails.INCLUDE_IDX);
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

        fileMdl = new FileTblModel(
        		recordDefinition.records, currentDetails.fontName, recordDefinition.columnDtls);
        //colorInd = new int[fileMdl.getColumnCount()];
        fileTbl.setModel(fileMdl);
//	    tcm  = fileTbl.getColumnModel();

 //       for (int i = 0; i < fileMdl.getColumnCount(); i++) {
  //          tc = tcm.getColumn(i);
            //tc.setPreferredWidth(20);
            //tc.setCellRenderer(new tblRender());
  //      }
        fileMdl.fireTableDataChanged();
    }

    
    private void nameColumns() {
    	String s = "n";
    	int i = 0;
    	if (recordDefinition.keyValue != null && ! "".equals(recordDefinition.keyValue.toString())) {
    		s = recordDefinition.keyValue.toString();
    	}
    	
    	for (ColumnDetails column : recordDefinition.columnDtls) {
    		if (column.name == null || "".equals(column.name.trim())) {
    			column.name = s + i;
    		}
    		i += 1;
    	}
    	
    	columnMdl.fireTableDataChanged();
    	fileMdl.fireTableStructureChanged();
    }
    
    
    /**
     * Table model to display column details for the user
     *
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
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
        public int getColumnCount() {
            return ColumnDetails.NUMBER_OF_COLUMNS;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            return columns.size();
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            ColumnDetails colDtls = columns.get(rowIndex);

            return colDtls.getValue(columnIndex);
        }

        /**
         * @see javax.swing.table.TableModel#getColumnName(int)
         */
        public String getColumnName(int column) {
             return ColumnDetails.getColumnName(column);
        }


        /**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != ColumnDetails.START_IDX
            	&& columnIndex != ColumnDetails.LENGTH_IDX;
        }


        /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            ColumnDetails colDtls = columns.get(rowIndex);

            try {
                colDtls.setValue(columnIndex, aValue);
                if (columnIndex == ColumnDetails.NAME_IDX
                ||   columnIndex == ColumnDetails.TYPE_IDX
                ||   columnIndex == ColumnDetails.DECIMAL_IDX) {
                    fileMdl.fireTableStructureChanged();
                }
            } catch (Exception e) {
                Common.logMsg(e.getMessage(), null);
                e.printStackTrace();
            }
        }
    }

    /**
     * Table Model for displaying the sample file
     *
     * @author Bruce Martin
     *
     */
    @SuppressWarnings("serial")
	private class FileTblModel extends AbstractTableModel {
        private byte[][] lines;
        private String font;
        private ArrayList<ColumnDetails> columns;


        /**
         * Table Model for displaying the sample file
         *
         * @param lines2show lines from the sample file
         * @param tblColumns table columns
         */
        public FileTblModel(final byte[][] lines2show,
        		final String fontname,
                final ArrayList<ColumnDetails> tblColumns) {
            super();
            lines = lines2show;
            font = fontname;
            columns = tblColumns;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount() {
            return columns.size();
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            return recordDefinition.numRecords;
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            ColumnDetails colDtls = columns.get(columnIndex);
            int colStart = colDtls.start - 1;
            int colEnd   = colDtls.start + colDtls.length - 1;

            if (lines[rowIndex].length < colStart) {
                return "";
            }
            switch (colDtls.type) {
            case(Type.ftChar):
            case(Type.ftCharNullPadded):
            case(Type.ftCharRightJust):
            case(Type.ftCheckBoxTF):            
            case(Type.ftCheckBoxYN):            
	            return getStandardField(rowIndex, colStart, colEnd);
            }

            FieldDetail fldDef = new FieldDetail(
            		"", "", colDtls.type, colDtls.decimal, currentDetails.fontName, 0, "");
            fldDef.setPosLen(colDtls.start, colDtls.length);
            
            try {
            	return TypeManager.getInstance().getType(colDtls.type).getField(
            		lines[rowIndex], colDtls.start, fldDef);
            } catch (Exception e) {
				return getStandardField(rowIndex, colStart, colEnd);
			}
        }
        
        private String getStandardField(int rowIndex, int colStart, int colEnd) {
            if (lines[rowIndex].length < colEnd) {
                return Conversion.getString(lines[rowIndex], colStart, lines[rowIndex].length, font);
                //lines[rowIndex].substring(colStart);
            }
            return Conversion.getString(lines[rowIndex], colStart, colEnd, font);
       	
        }


        /**
         * @see javax.swing.table.TableModel#getColumnName(int)
         */
        public String getColumnName(int column) {
            ColumnDetails colDtls = columns.get(column);

            if ("".equals(colDtls.name)) {
                return super.getColumnName(column);
            }
            return colDtls.name;
        }
    }
}
