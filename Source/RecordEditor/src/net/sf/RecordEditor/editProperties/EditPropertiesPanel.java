/*
 * @Author Bruce Martin
 * Created on 24/01/2007 for version 0.60
 *
 * Purpose:
 * edit a group of related properties
 */
package net.sf.RecordEditor.editProperties;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * edit a group of related properties
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditPropertiesPanel extends BasePanel {

    //private static final int TIP_HEIGHT = 150;
    private static final int TABLE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 12;
    private static final int VALUE_COLUMN = 2;
    private static final int NAME_COLUMN = 0;
    private static final int WIDTH_PADDING = 20;

    private JEditorPane tips;
    private JTable paramTbl;

    private String[][] tableData;
    private String[] colNames = {
            "Parameter Name", "Parameter Description", " Value "
    };

    private EditParams pgmParams;


    /**
     * Edit Properties panel. this panel lets the user edit a group of related
     * properties
     * @param params general program params
     * @param description description of theproperties to be displayed
     * @param tblData properties details
     */
    public EditPropertiesPanel(final EditParams params, final String description, final String[][] tblData) {
        super();

        pgmParams = params;
        tableData = tblData;
        //System.out.println(description);
        tips = new JEditorPane("text/html", description);

        init_100_Fields();
        init_200_Screen();
    }

    /**
     * Initialise Fields
     *
     */
    private void init_100_Fields() {
        TableColumn tc;

        for (int i = 0; i < tableData.length; i++) {
            tableData[i][VALUE_COLUMN]
                         = Parameters.getString(tableData[i][NAME_COLUMN]);
        }

        paramTbl = new JTable(new ParamTblModel(tableData, colNames));
        paramTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Common.calcColumnWidths(paramTbl, 1);

        paramTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);
        tc = paramTbl.getColumnModel().getColumn(1);

        tc.setPreferredWidth(tc.getPreferredWidth() + WIDTH_PADDING);
    }


    /**
     * setup the screen
     *
     */
    private void init_200_Screen() {
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//int inset = Common.getSpaceAtLeftOfScreen() + Common.getSpaceAtRightOfScreen();

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
		//this.setGap(BasePanel.GAP1);
		this.addComponent(1, 5, TABLE_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(paramTbl));
		//this.done();
        //this.setBounds(getX(), getY(), screenSize.width  - inset, getHeight());

    }

    /**
     * table model for the properties being editted
     *
     *
     * @author Bruce Martin
     *
     */
    private class ParamTblModel extends DefaultTableModel {


        /**
         * @param data data to be displayed
         * @param columnNames Column names
         */
        public ParamTblModel(final Object[][] data, final Object[] columnNames) {
            super(data, columnNames);
         }

        /**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int row, int column) {

            return column == VALUE_COLUMN;
        }


         /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);

            String propertyName = tableData[row][NAME_COLUMN];

            String oldValue = pgmParams.getProperty(propertyName);

            if (aValue == null || "".equals(aValue.toString().trim())) {
                pgmParams.remove(propertyName);
                if (oldValue != null && ! oldValue.trim().equals("")) {
                    pgmParams.propertiesChanged = true;
                }
            } else if (oldValue == null || ! oldValue.equals(aValue.toString())) {
                pgmParams.setProperty(propertyName, aValue.toString());
                pgmParams.propertiesChanged = true;
            }
        }
}

}
