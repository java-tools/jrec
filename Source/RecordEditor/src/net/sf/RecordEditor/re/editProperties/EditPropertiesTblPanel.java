/*
 * @Author Bruce Martin
 * Created on 25/01/2007 for version 0.60
 *
 * Purpose:
 */
package net.sf.RecordEditor.re.editProperties;


import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * This class will display a list of Jars to be used
 * when running the RecordEditor
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditPropertiesTblPanel extends BasePanel {

    private static final int TABLE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 13;
//    private static final int LOADER_TABLE_SIZE = 20;


    private JEditorPane tips;
    private PropertiesTableModel loaderModel;
    private JTable propertiesTbl;


    private EditParams pgmParams;

    /**
     * Edit Jars File
     * @param params editor parameters
     * @param description description of the jars being editted
     * @param columnNames table column names
     * @param columnHeadings table column headings
     * @param tableSize table size
     */
    public EditPropertiesTblPanel(final EditParams params, final String description,
            final String[]  columnNames, final String[] columnHeadings, final int tableSize) {
        super();

        pgmParams = params;

        tips = new JEditorPane("text/html", description);

        loaderModel = new PropertiesTableModel(pgmParams,
                columnNames, columnHeadings, tableSize);

        init_100_ScreenFields();
        init_200_Screen();
    }

    /**
     * Initialise screen fields
     *
     */
    private void init_100_ScreenFields() {

         propertiesTbl = new JTable(loaderModel);
    }

    /**
     * Setup the screen
     *
     */
    private void init_200_Screen() {

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		this.addComponent(1, 5, TABLE_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(propertiesTbl));

		//this.done();
    }

}
