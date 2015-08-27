/*
 * @Author Bruce Martin
 * Created on 25/01/2007 for version 0.60
 *
 * Purpose:
 * Edit the JDBC parameters screen. It allows the user to update
 * the JDBC parameters
 */
package net.sf.RecordEditor.re.editProperties;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * Edit the JDBC parameters screen. It allows the user to update
 * the JDBC parameters
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditJdbcParamsPanel extends BasePanel {

    private static final int TABLE_HEIGHT = SwingUtils.NORMAL_FIELD_HEIGHT * 14 / 2;;
    private static final int JDBC_TABLE_SIZE = 16;

    private PropertiesTableModel model;
    private JEditorPane tips;
    private JTable paramTbl;

    private JTextField sourceName = new JTextField();
    private JTextField driver     = new JTextField();
    private JTextField source     = new JTextField();
    private JTextField readOnly   = new JTextField();
    private JTextField user       = new JTextField();
    private JTextField password   = new JTextField();
    //private JTextField commit     = new JTextField();
    //private JTextField checkpoint = new JTextField();

    private ReAbstractAction startAction;
    private JComboBox jdbcJarsCombo = new JComboBox();

    private JTextField[] screenFields =  {
            sourceName, driver, source, readOnly, user, password
    };
    private int currentRow;


    private static final String[] COL_HEADERS = {
            "Source Name", "Driver", "Source", "Read Only Source", " User ", "Password",
            "Commit", "Checkpoint", "Expand variables (Y/N)", "Auto Close Connections",
            "Drop ; from SQL statements"
    };
    private static final String[] COL_NAMES = {
    		Parameters.DB_SOURCE_NAME, Parameters.DB_DRIVER, Parameters.DB_SOURCE,
    		Parameters.DB_READ_ONLY_SOURCE,  Parameters.DB_USER, Parameters.DB_PASSWORD,
    		Parameters.DB_COMMIT, Parameters.DB_CHECKPOINT, Parameters.DB_EXPAND_VARS,
    		Parameters.DB_CLOSE_AFTER_EXEC,
            Parameters.DB_DROP_SEMI
    };
    private String description
        = LangConversion.convertId(LangConversion.ST_MESSAGE, "EditProps_JDBC_Params",

          "<h1>JDBC Parameters</h1>"
        + "These parameters Control the Database connection "
        + "that the <b>RecordEditor</b> uses<br>to connect to the "
        + "RecordLayout Database (where all the Record Layouts are stored)."
        + "<br>If you click on a row in the table, the fields at the bottom "
        + "will be updated with the values from the selected row.<br>"
        + "You can update values either using the fields at the bottom "
        + "of the screen or directly into the table itself.");

    private EditParams pgmParams;
    private JarGroup jdbcJarHolder;


    /**
     * Edit the JDBC properties
     * @param params all properties etc
     * @param jdbcJars all the jdbc jars
     */
    public EditJdbcParamsPanel(final EditParams params, final JarGroup jdbcJars) {

        pgmParams = params;
        jdbcJarHolder = jdbcJars;

        init_100_ScreenFields();
        init_200_Screen();

        g200_SetScreenFields(0);
    }

    /**
     * setup screen fields
     *
     */
    private void init_100_ScreenFields() {

        tips = new JEditorPane("text/html", description);
        model    = new PropertiesTableModel(pgmParams, COL_NAMES, COL_HEADERS, "PropsEd_JDBC", JDBC_TABLE_SIZE) {
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(aValue, rowIndex, columnIndex);
                if (aValue != null && columnIndex < screenFields.length) {
                    screenFields[columnIndex].setText(aValue.toString());
                }
           }
        };
        paramTbl = new JTable(model);
        paramTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Common.calcColumnWidths(paramTbl, 1);

        paramTbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent m) {
			    g200_SetScreenFields(paramTbl.getSelectedRow());
			}
		});

        for (int i = 0; i < screenFields.length; i++) {
            new FieldAddapter(screenFields[i], i);
        }

        startAction = new ReAbstractAction("Test Driver") {
            public void actionPerformed(ActionEvent e) {
                g500_TestConnection();
            }
        };

        buildJarCombo();
        jdbcJarsCombo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                buildJarCombo();
            }
        });

        //jdbcJarsCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //jdbcJarsCombo.setBackground(Color.RED);
    }

    /**
     * build the screen
     *
     */
    private void init_200_Screen() {

		this.addComponentRE(1, 5, TABLE_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		//this.setGap(BasePanel.GAP1);
		this.addComponentRE(1, 5, TABLE_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(paramTbl));

		this.addLineRE("Source Name", sourceName);
		this.addLineRE("Driver", driver);
		this.addLineRE("Source", source);
		//this.addLine("Read Only Source Name", readOnly);
		//this.setGap(BasePanel.GAP0);
		this.addLineRE("User", user);
		this.addLineRE("Password", password);
		//this.setGap(BasePanel.GAP0);
		this.addLineRE("JDBC JAR", jdbcJarsCombo, new JButton(startAction));
		this.setGapRE(BasePanel.GAP0);
		//this.addComponent("Commit", commit    );
		//this.addComponent("Checkpoint", checkpoint);

		//this.done();
    }

    /**
     * Build a cell name
     * @param row row of the cell
     * @param col cells column
     * @return cells name
     */
//    private String g100_GetCellName(int row, int col) {
//        return COL_NAMES[col] + "." + row;
//    }


    /**
     * Set The screen fields
     * @param row table row to use
     */
    private void g200_SetScreenFields(int row) {
        int i;
        currentRow = row;

        for (i = 0; i < screenFields.length; i++) {
            screenFields[i].setText(model.getStringValueAt(row, i));
        }
        buildJarCombo();
    }

    /**
     * Get a value of a specific cell in the table
     * @param rowIndex row of the cell
     * @param columnIndex column of the cell
     * @return cells value
     */
//    private String g300_GetValueAt(int rowIndex, int columnIndex) {
//        String colName = g100_GetCellName(rowIndex, columnIndex);
//        String s = pgmParams.properties.getProperty(colName);
//
//        if (s == null) {
//            s = ReadProperties.getString(colName);
//        }
//        return s;
//    }



    /**
     * Test Database Connection
     *
     */
    private void g500_TestConnection() {
        String jarName = null;
        if (jdbcJarsCombo.getSelectedItem() != null) {
            jarName = jdbcJarsCombo.getSelectedItem().toString();
        }

        TestJdbcConnection connectionTester = new TestJdbcConnection();

        connectionTester.testConnection(sourceName.getText(),
                driver.getText(),
                source.getText(),
                user.getText(),
                password.getText(),
                jarName);
     }

    /**
     * build the jar combo
     *
     */
    public void buildJarCombo() {

        String name;
        jdbcJarsCombo.removeAllItems();
        jdbcJarsCombo.addItem("");

        try {
        for (int i = 0; i < jdbcJarHolder.jars.length; i++) {
            name = jdbcJarHolder.jars[i][JarGroup.JAR_COLUMN];
            if (name != null && ! "".equals(name.trim())) {
                jdbcJarsCombo.addItem(name);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * class to update the table when the user exits the field
     *
     *
     * @author Bruce Martin
     *
     */
    private class FieldAddapter extends FocusAdapter {
        private JTextField fld;
        private int col;

        /**
         * create a field adapter to update the tables values
         * @param field screen field to monitor
         * @param column table column
         */
        public FieldAddapter(final JTextField field, final int column) {
            super();

            fld = field;
            col = column;

            field.addFocusListener(this);
        }


        /**
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
//        public void focusGained(FocusEvent e) {
//            g400_BuildJarCombo();
//            super.focusGained(e);
//        }
        /**
         * Update the Tables with values from the field
         * @param e focus event
         */
    	public void focusLost(FocusEvent e) {
    	    //System.out.println("@@@ " + currentRow + " " + typeFld.getText());
            pgmParams.setProperty(model.getCellName(currentRow, col), fld.getText());
            model.fireTableRowsUpdated(currentRow, currentRow);
    	}
    }

}
