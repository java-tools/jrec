/*
 * @Author Bruce Martin
 * Created on 9/02/2007
 *
 * Purpose:
 * create a Date field field with a popup date selector
 */

package net.sf.RecordEditor.utils.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;

import com.zbluesoftware.java.bm.ArrowButton;

/**
 * create a Date field field with a popup date selector
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CsvArray extends JPanel implements ActionListener, TableCellRenderer {
    private static final int FIELD_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 20 / 9;
    private JTextField fld = new JTextField();
    private JButton btn = new ArrowButton(ArrowButton.SOUTH);
    private JPopupArray popup;

    private boolean visible = false;

    private JTable parentTable = null;
    private int parentRow, parentColumn;
    private int columnCount = -1;




    /**
     * Create Csv Array field
     * @param seperator field seperator
     * @param quote quote char
     */
    public CsvArray(final String seperator, final String quote) {

        popup = new JPopupArray(seperator, quote);

        init();
    }

    /**
     * Create Csv Array field
     * @param seperator1 first field seperator
     * @param seperator2 second field seperator
     * @param quote quote char
     */
    public CsvArray(final String seperator1, final String seperator2, final String quote) {

        //System.out.println(">>" + seperator1 + "< >" + seperator2 + "< >" + quote);
        popup = new JPopupArray(seperator1, seperator2, quote);

        init();
    }

    /**
     * Perform initialisation
     */
    private void init() {

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, fld);
        this.add(BorderLayout.EAST, btn);

        fld.setMinimumSize(new Dimension(FIELD_WIDTH, fld.getHeight()));

        this.setBorder(fld.getBorder());
        fld.setBorder(BorderFactory.createEmptyBorder());
        btn.addActionListener(this);
    }

    /**
     * Set the parent table details
     * @param tbl table being displayed
     * @param row row being displayed
     * @param col column being displayed
     */
    public void setTableDetails(JTable tbl, int row, int col) {
        parentTable  = tbl;
        parentRow    = row;
        parentColumn = col;
    }


    /**
     * Get the Text fields value
     * @return text value of field
     */
    public String getText() {
        return fld.getText();
    }


    /**
     * Set the fields value
     * @param text new text value of date
     */
    public void setText(String text) {
        //System.out.println("---> Setting to " + text + "<");
        fld.setText(text);
        columnCount = -1;
        popup.fireTableStructureChanged();
    }


    /**
     * update the source table
     */
    public void updateTable() {

         /*if (parentModel != null) {
            System.out.println("~~ fire Row changed " + parentRow + " >>" + fld.getText());
            parentModel.setValueAt(fld.getText(), parentRow, parentColumn);
            parentModel.fireTableRowsUpdated(parentRow, parentRow + 1);
        } else */
        if (parentTable != null) {
            //System.out.println("~~ Setting Table -> " + fld.getText());
            parentTable.setValueAt(fld.getText(), parentRow, parentColumn);
        }
    }

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent
	 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(
		JTable tbl,
		Object value,
		boolean isSelected,
		boolean hasFocus,
		int row,
		int column) {

	    String s = "";

	    if (value != null) {
	        s = value.toString();
	    }

	    this.setText(s);

        if (isSelected) {
            fld.setForeground(tbl.getSelectionForeground());
            fld.setBackground(tbl.getSelectionBackground());
        } else {
            fld.setForeground(tbl.getForeground());
            fld.setBackground(tbl.getBackground());
        }

		return this;
	}


    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn) {
            //System.out.println("~~> " + visible
            //        + " " + e.getActionCommand());
            if (visible) {
                popup.setVisible(false);
                //fld.setText(popup.getValue());
            } else {
                int xCoord = 0;
                int yCoord = fld.getHeight();

                popup.show(fld, xCoord, yCoord);
            }

            visible = ! visible;
        }
    }


    /**
     * Display CSV style array in a Popup Table
     *
     *
     * @author Bruce Martin
     *
     */
    public class JPopupArray extends JPopupMenu {
        //private Calendar date = null;
        private CsvTableModel model;
        private JTable table = new JTable();
        private JScrollPane tablePane = new JScrollPane(table);
        private boolean isPacked = false;
        private Thread packThread;
        private PopupMenuListener listener = new PopupMenuListener() {
            public void 	popupMenuCanceled(PopupMenuEvent e) {
                Common.stopCellEditing(table);
            }
            public void 	popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                Common.stopCellEditing(table);
            }
            public void 	popupMenuWillBecomeVisible(PopupMenuEvent e)  { }
        };

        /**
         * Display CSV style array in a Popup Table
         *
         * @param seperator field seperator
         * @param quote quote char
         */
        public JPopupArray(final String seperator, final String quote) {
            this(seperator, null, quote);
        }


        /**
         * Display CSV style array in a 2 dimensional Popup Table
         * @param seperator1 field seperator 1
         * @param seperator2 field seperator 2
         * @param quote quote char
         */
        public JPopupArray(final String seperator1, final String seperator2, final String quote) {
            super();

            if (seperator2 == null || "".equals(seperator2)) {
                model = new CsvTableModel(seperator1, quote);
            } else {
                model = new CsvTableModelMultiColumn(seperator1, seperator2, quote);
            }
            table.setModel(model);

            this.addPopupMenuListener(listener);
        }


        /**
         * Get the Arrays value
         * @return value
         */
 /*       public String getValue() {
            Common.stopCellEditing(table);
            return model.getValue();
        }
*/
         // public methods
        /**
         * show the JPopupCalendar. Consistent with the original behavior of
         * <tt>show</tt>.
         * @param invoker Component
         * @param x int
         * @param y int
         */
        public void show(Component invoker,
                         int x, int y) {

          // ignore if thread is stuck.
          if (!isThreadDone()) {
              return;
          }

          if (! isPacked) {
            pack();
          }

           super.show(invoker, x, y);
        }


        /**
         * Create an instance of DateChooser before <tt>show</tt> is called and
         * can make the popup window pop up a bit quicker. Internally called by
         * <tt>show</tt>.
         */
        public void pack() {
            //datePane.pack();
            add(tablePane);
            super.pack();
            isPacked = true;
        }


        /**
         * Check if the packing thread has completed.
         * @return boolean
         */
        private boolean isThreadDone() {
          if (packThread != null) {
            try {
              packThread.join();
              return true;
            } catch (Exception e) {
              System.err.println("Can't join pack thread.");
              return false;
            }
          }
          return true;
        }

        /**
         *
         */
        public void fireTableStructureChanged() {
            model.fireTableStructureChanged();
        }
    }

    /**
     * create a Table Model from a CSV style field
     *
     *
     * @author Bruce Martin
     *
     */
    private class CsvTableModel extends AbstractTableModel {
        private BasicParser parser = BasicParser.getInstance();
        //private String value;

        private String delimiter;
        private String quoteStr;

        //protected JTextField field;


        /**
         * create a Table Model from a CSV style field
         *
         * @param seperator array elemtn seperator (ie field seperator)
         * @param quote Quote char
         */
        public CsvTableModel(final String seperator, final String quote) {
            delimiter = seperator;
            quoteStr  = quote;
        }

        /**
         * Wether the cell is editable
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        public boolean isCellEditable(int row, int column) {
            return true;
        }

        /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object newValue, int row, int column) {
            String s = "";
            if (newValue != null) {
                s = newValue.toString();
            }

             String value = parser.setField(row, Type.NT_TEXT, fld.getText(), delimiter, quoteStr, s);
            fld.setText(value);
            updateTable();
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount() {
            return 1;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            int i = parser.getFieldCount(fld.getText(), delimiter, quoteStr);
            //System.out.println("Count " + i);
            return i;
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int row, int column) {
            return getRowsValue(row);
        }

        /**
         * Get the value of a row.
         * @param row row to retrieve
         * @return the value
         */
        public String getRowsValue(int row) {
            return parser.getField(row, fld.getText(), delimiter, quoteStr);
        }
    }

    /**
     * Create a 2 dimensional popup table
     *
     *
     * @author Bruce Martin
     *
     */
    private class CsvTableModelMultiColumn extends CsvTableModel {
        private BasicParser parser = BasicParser.getInstance();
        private String delimiter2, quoteStr;

        /**
         * Create a 2 dimensional popup table
         * @param seperator1 field seperator 1
         * @param seperator2 field seperator 2
         * @param quote quote char
         */
        public CsvTableModelMultiColumn(final String seperator1, final String seperator2,
                final String quote) {
            super(seperator1, quote);
            delimiter2 = seperator2;
            quoteStr  = quote;
        }

        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount() {
            if (columnCount < 0) {
                int rowCount = getRowCount();
                columnCount = 1;
                for (int i = 0; i < rowCount; i++) {
                    //System.out.println("-->" + super.getRowsValue(i) + "< "
                    //       + parser.getFieldCount(super.getRowsValue(i), delimiter2, quoteStr));
                    columnCount = Math.max(columnCount,
                            parser.getFieldCount(super.getRowsValue(i), delimiter2, quoteStr));
                }
                //System.out.println("!! " + rowCount + " >" + fld.getText());
            }
            //System.out.println("~~ Column Count :" + columnCount);
            return columnCount;
        }


        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int row, int column) {
            return parser.getField(column, getRowsValue(row), delimiter2, quoteStr);
        }

        /**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        public void setValueAt(Object newValue, int row, int column) {
            String s = "";
            if (newValue != null) {
                s = newValue.toString();
            }

            String value = parser.setField(column, Type.NT_TEXT,  getRowsValue(row), delimiter2, quoteStr, s);
            super.setValueAt(value, row, 0);
        }

    }

/*    public static void main(String[] arg) {
        JFrame f = new JFrame();

        CsvArray c = new CsvArray(",", "");

        c.setText("1,22,333,4444,5555,6666,777,88,9");
        f.getContentPane().add(c);
        f.pack();
        f.setVisible(true);
    }*/
}
