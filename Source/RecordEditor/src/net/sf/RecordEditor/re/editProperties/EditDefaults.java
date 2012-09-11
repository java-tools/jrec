/**
 *
 */
package net.sf.RecordEditor.re.editProperties;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.Combo.ComboStrOption;
import net.sf.RecordEditor.utils.swing.ComboBoxs.EnglishStrModel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditDefaults extends BasePanel {

    private static final int TABLE_HEIGHT = SwingUtils.NORMAL_FIELD_HEIGHT * 15 / 2;

    private EditParams editParams;
	private ListModel model;
    private JTextArea descriptionFld = new JTextArea();
    private JEditorPane tips;
    private JTable optionTbl;

    private JComboBox optionCombo = new JComboBox();

    private EnglishStrModel[] comboModels;
    private int currentRow = -1;

    private String[][] data;

    private final static  String[] columnHeadings = LangConversion.convertArray(LangConversion.ST_COLUMN_HEADING, "editProps_DefaultColHeadings",
    		new String[] {"Property Variable", "Description"});

    private AbstractAction action = new AbstractAction() {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

	    	saveProperty();
		}

    };


	/**
	 *
	 */
	public EditDefaults(final EditParams params, final String description,
			final String[][] dataDescription, EnglishStrModel[] comboBoxModels) {

		editParams = params;
		data = dataDescription;
		comboModels = comboBoxModels;

        tips = new JEditorPane("text/html", description);


		init_100_ScreenFields();
		init_200_Screen();
	}


	/**
     * Initialise screen fields
     *
     */
    private void init_100_ScreenFields() {

		model = new ListModel(data, columnHeadings);
        optionTbl = new JTable(model);
        optionTbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent m) {
			    g100_SetFields(optionTbl.getSelectedRow());
			}
		});
        g100_SetFields(0);
    }

    /**
     * Setup the screen
     */
    private void init_200_Screen() {

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		this.addComponent(1, 5, TABLE_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(optionTbl));

		this.addLine("Description", descriptionFld);
		this.setHeight(BasePanel.GAP3);
		this.setGap(BasePanel.GAP2);
		this.addLine("Default Value", optionCombo);
		this.setGap(BasePanel.GAP1);

		this.done();
    }


    /**
     * set the fields based on a table column
     * @param row row to use as a basis
     */
    private void g100_SetFields(int row) {

    	optionCombo.removeActionListener(action);

    	saveProperty();

        currentRow = row;
        descriptionFld.setText(data[row][1]);
        optionCombo.setModel(comboModels[row]);

        Object o = editParams.properties.get(data[row][0]);
        if (o != null) {
        	String s = o.toString();
        	for (int i = 0; i < optionCombo.getItemCount(); i++) {
        		if (s.equals(((ComboStrOption) optionCombo.getItemAt(i)).getEnglish())) {
        			optionCombo.setSelectedIndex(i);
        			break;
        		}
        	}
        }
        optionCombo.addActionListener(action);
    }


    private void saveProperty() {

        Object selectedItem = optionCombo.getSelectedItem();
		if (currentRow >= 0 && selectedItem != null && selectedItem instanceof ComboStrOption) {
        	editParams.properties.setProperty(data[currentRow][0], ((ComboStrOption) selectedItem).getEnglish());
        }
    }

	/**
     * Table model for the Jars being displayed
     *
     *
     * @author Bruce Martin
     *
     */
    private static class ListModel extends DefaultTableModel {

        private String[][] tableData;

        /**
         * Table model for the Jars being displayed
         *
         * @param data data to be displayed
         * @param columnNames Column names
         */
        public ListModel(final String[][] data, final Object[] columnNames) {
            super(data, columnNames);

            tableData = data;
         }

        /**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int row, int column) {

            return  false;
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int row, int column) {

            if (tableData[row] == null) {
                return null;
            }
            return tableData[row][column];
        }

	 }

}
