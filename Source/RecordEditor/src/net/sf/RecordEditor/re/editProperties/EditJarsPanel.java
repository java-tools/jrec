/*
 * @Author Bruce Martin
 * Created on 25/01/2007 for version 0.60
 *
 * Purpose:
 * This class will display a list of Jars to be used
 * when running the RecordEditor
 */
package net.sf.RecordEditor.re.editProperties;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * This class will display a list of Jars to be used
 * when running the RecordEditor
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditJarsPanel extends BasePanel {

    private static final int TABLE_HEIGHT = SwingUtils.NORMAL_FIELD_HEIGHT * 15 / 2;;

    private JEditorPane tips;
    private JarTblModel jarMdl;
    private JTable jarsTbl;
    private JTextField typeFld = new JTextField();
    private FileChooser jarFld = new FileChooser();
    private JTextArea descriptionFld = new JTextArea();

    private JarGroup jarsList;
    private String typeName;
    private final static String[] columnHeadings = {"Name", "Jar", "Description"};

    private boolean update;
    private int currentRow = -1;

    /**
     * Edit Jars File
     * @param params editor parameters
     * @param description description of the jars in this group
     * @param jars jars to be displayed
     * @param namePrefix type name prefix
     */
    public EditJarsPanel(final EditParams params, final String description,
            final JarGroup jars, final String namePrefix, boolean updatePnl) {
        super();

        jarsList = jars;
        typeName = namePrefix;
        update = updatePnl;

        tips = new JEditorPane("text/html", description);

        init_100_ScreenFields();
        init_200_Screen();
    }

    /**
     * Initialise screen fields
     *
     */
    private void init_100_ScreenFields() {

        jarsTbl = new JTable();
        jarsTbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent m) {
			    g100_SetFields(jarsTbl.getSelectedRow());
			}
		});

        typeFld.setEditable(update && "".equals(typeName));
        typeFld.addFocusListener(new FocusAdapter() {
            	public void focusLost(FocusEvent e) {
            	    //System.out.println("@@@ " + currentRow + " " + typeFld.getText());
            	    jarsList.jars[currentRow][JarGroup.TYPE_COLUMN]
            	                              = typeFld.getText();
            	    jarMdl.fireTableRowsUpdated(currentRow, currentRow);
            	}
        });

        jarFld.addFcFocusListener(new FocusAdapter() {
        	public void focusLost(FocusEvent e) {
        	    jarsList.jars[currentRow][JarGroup.JAR_COLUMN]
        	                              = jarFld.getText();
        	    g200_checkForFixedTypeName(currentRow);
        	    jarMdl.fireTableRowsUpdated(currentRow, currentRow);
           	}
        });

        jarFld.setEditable(update);
        descriptionFld.setEditable(false);
    }

    /**
     * Setup the screen
     */
    private void init_200_Screen() {

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));

		this.addComponent(1, 5, TABLE_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(jarsTbl));

		this.addLine("Jar Type", typeFld);
		this.addLine("Jar", jarFld, jarFld.getChooseFileButton());
		this.addLine("Description", descriptionFld);
		this.setHeight(BasePanel.GAP3);
		this.setGap(BasePanel.GAP2);

		//this.done();
    }

    /**
     * Set the fields to the first row of the table
     *
     */
    public void setInitialValues() {

        jarMdl = new JarTblModel(jarsList.jars, columnHeadings);
        jarsTbl.setModel(jarMdl);

        g100_SetFields(0);
        jarMdl.fireTableDataChanged();
    }

    /**
     * set the fields based on a table column
     * @param row row to use as a basis
     */
    private void g100_SetFields(int row) {

        if (currentRow >= 0) {
            jarsList.jars[currentRow][JarGroup.JAR_COLUMN] = jarFld.getText();
        }
        currentRow = row;
        typeFld.setText(jarsList.jars[row][JarGroup.TYPE_COLUMN]);
        jarFld.setText(jarsList.jars[row][JarGroup.JAR_COLUMN]);
        descriptionFld.setText(jarsList.jars[row][JarGroup.DESCRIPTION_COLUMN]);
    }

    /**
     * Check if the type name is fixed, if so assign the correct type name
     * @param row row to be checked
     */
    private void g200_checkForFixedTypeName(int row) {

        if (! "".equals(typeName)) {
            jarsList.jars[row][0] = typeName + row;
            typeFld.setText(jarsList.jars[row][0]);
    	    jarMdl.fireTableRowsUpdated(row, row);
        }
    }


    /**
     * Table model for the Jars being displayed
     *
     *
     * @author Bruce Martin
     *
     */
    private class JarTblModel extends DefaultTableModel {

        private String[][] tableData;

        /**
         * Table model for the Jars being displayed
         *
         * @param data data to be displayed
         * @param columnNames Column names
         */
        public JarTblModel(final String[][] data, final Object[] columnNames) {
            super(data, columnNames);

            tableData = data;
         }

        /**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int row, int column) {

            return update
            	&& (       column == JarGroup.JAR_COLUMN
            		|| (   column == JarGroup.TYPE_COLUMN
            	        && "".equals(typeName)));
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

         /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);

            String s = "";
            if (s != null) {
                s = aValue.toString();
            }

            if (tableData[row] == null) {
                tableData[row] = new String[3];
            }

            tableData[row][column] = s;

            g200_checkForFixedTypeName(row);
            if (row == currentRow) {
                if (column == JarGroup.TYPE_COLUMN) {
                    typeFld.setText(s);
                } else if (column == JarGroup.JAR_COLUMN) {
                    jarFld.setText(s);
                }
            }
        }
}

}
