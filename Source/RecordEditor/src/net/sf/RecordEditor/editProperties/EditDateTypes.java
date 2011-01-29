/*
 * @Author Bruce Martin
 * Created on 26/03/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.editProperties;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultCellEditor;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import net.sf.RecordEditor.layoutEd.Record.TableList;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditDateTypes extends BasePanel {
    private static final Calendar XMAS_CAL = new GregorianCalendar(1998, Calendar.DECEMBER, 25);
    private static Date xmas = XMAS_CAL.getTime();

    private static final int TABLE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 10 + 4;
    private static final int NAME_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 13;

    private static final int TYPE_NAME_COLUMN = 0;
    private static final int BASE_TYPE_COLUMN = 1;
    private static final int FORMAT_COLUMN    = 2;

    private static final String DESCRIPTION
		= "<h2>Date Types</h2>"
		+ "This screen lets you define Date Types to the <b>RecordEditor</b>"
		+ "<br><br>" + Common.DATE_FORMAT_DESCRIPTION;

    private static final String[] COL_HEADERS = {
            "Type Name", "Base Type", "Date Format",
    };
    private static final String[] COL_NAMES = {
            Parameters.PROPERTY_DATE_TYPE_NAME,
            Parameters.PROPERTY_DATE_BASE_TYPE,
            Parameters.PROPERTY_DATE_FORMAT
    };

    private JEditorPane tips = new JEditorPane("text/html", DESCRIPTION);

    private PropertiesTableModel typeTblModel;

    private JTable typeTbl;

    private JTextField name = new JTextField();
    private TableList typeModel;
    private BmKeyedComboBox typeName;
    private JTextField dateFormat = new JTextField();

    private JTextField message = new JTextField();

    private EditParams pgmParams;
    private int currentRow = 0;

    private FocusAdapter lostFocus = new FocusAdapter() {
    	public void focusLost(FocusEvent e) {
    	    g100_SetTblFields();
    	}
    };


    /**
     * Edit Date Types
     * @param params program parameters
     */
    public EditDateTypes(final EditParams params) {
        super();

        pgmParams = params;

        init_100_ScreenFields();
        init_200_Screen();
    }

    /**
     * setup screen fields
     */
    private void init_100_ScreenFields() {
        int connectionIdx = Common.getConnectionIndex();
        TableColumn tc;
        //propertiesTbl = new JTable(loaderModel);
        typeModel = new TableList(connectionIdx, Common.TI_FIELD_TYPE, true, false,
	            Parameters.TYPE_NUMBER_PREFIX, Parameters.TYPE_NAME_PREFIX,
	            Parameters.NUMBER_OF_TYPES);
        typeName = new BmKeyedComboBox(typeModel, false);

        typeTblModel = new PropertiesTableModel(pgmParams, COL_NAMES,
                COL_HEADERS, Parameters.DATE_TYPE_TABLE_SIZE) {
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object ret = super.getValueAt(rowIndex, columnIndex);

                if (ret != null && columnIndex == BASE_TYPE_COLUMN) {
                    try {
                        ret = new Integer(ret.toString());
                    } catch (Exception e) {
                    }
                }
                return ret;
            }

            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(aValue, rowIndex, columnIndex);
                if (aValue != null) {
                    if (columnIndex == TYPE_NAME_COLUMN) {
                        String s = aValue.toString();
                    	name.setText(s);
                    } else if (columnIndex == BASE_TYPE_COLUMN) {
                        g300_SetBaseType(rowIndex);
                    } else if (columnIndex == FORMAT_COLUMN) {
                        String s = aValue.toString();
                        dateFormat.setText(s);
                        g400_CheckDateFormat(s);
                    }
                }
           }
        };
        typeTbl = new JTable(typeTblModel);
        typeTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        typeTbl.setRowHeight(SwingUtils.COMBO_TABLE_ROW_HEIGHT);

        BmKeyedComboBox tblTypeCombo = new BmKeyedComboBox(typeModel, false);
        DefaultCellEditor typeEditor = new DefaultCellEditor(tblTypeCombo);
	    typeEditor.setClickCountToStart(1);
	    
	    tips.setCaretPosition(0);

	    tc = typeTbl.getColumnModel().getColumn(BASE_TYPE_COLUMN);
	    tc.setCellRenderer(tblTypeCombo.getTableCellRenderer());
	    tc.setCellEditor(typeEditor);

	    g200_SetScreenFields(currentRow);
	    Common.calcColumnWidths(typeTbl, 1);

	    tc = typeTbl.getColumnModel().getColumn(TYPE_NAME_COLUMN);
	    tc.setPreferredWidth(Math.max(tc.getPreferredWidth(), NAME_WIDTH));

	    message.setEditable(false);
	    name.addFocusListener(lostFocus);
	    typeName.addFocusListener(lostFocus);
	    dateFormat.addFocusListener(lostFocus);

        typeTbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent m) {
			    g200_SetScreenFields(typeTbl.getSelectedRow());
			}
		});

   }

   /**
    * Setup the screen
    *
    */
   private void init_200_Screen() {

		this.addComponent(1, 5, CommonCode.TIP_HEIGHT, BasePanel.GAP2,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));

		this.addComponent(1, 5, TABLE_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(typeTbl));

		this.addLine("Source Name", name);
		this.addLine("Base Type", typeName);
		this.addLine("Date Format", dateFormat);

		this.setGap(BasePanel.GAP2);
		this.addMessage(message);
   }

   /**
    * Set the Table fields from the screen fields
    *
    */
   private void g100_SetTblFields() {

       typeTblModel.setValueAt(name.getText(), currentRow, TYPE_NAME_COLUMN);
       typeTblModel.setValueAt(typeName.getSelectedItem(), currentRow, BASE_TYPE_COLUMN);
       typeTblModel.setValueAt(dateFormat.getText(), currentRow, FORMAT_COLUMN);
       typeTblModel.fireTableDataChanged();
   }

   /**
    * Set The screen fields
    * @param row table row to use
    */
   private void g200_SetScreenFields(int row) {
       currentRow = row;

       name.setText(typeTblModel.getStringValueAt(row, TYPE_NAME_COLUMN));
       g300_SetBaseType(row);
       dateFormat.setText(typeTblModel.getStringValueAt(row, FORMAT_COLUMN));
   }

   /**
    * Set the Screen Base Type
    * @param row row being editted
    */
   private void g300_SetBaseType(int row) {

       try {
           //System.out.println(typeTblModel.getStringValueAt(row, BASE_TYPE_COLUMN));
           typeName.setSelectedItem(new Integer(typeTblModel.getStringValueAt(row, BASE_TYPE_COLUMN)));
       } catch (Exception e) {
       }
   }

   /**
    * Check that the date format is correct
    * @param format date format to check
    */
   private void g400_CheckDateFormat(String format) {

       if (format != null) {
           try {
//               System.out.println("~~ " + format);
               SimpleDateFormat sd = new SimpleDateFormat(format);
               message.setText("25.Dec.98 will be formatted as " + sd.format(xmas));
           } catch (Exception e) {
               message.setText("Date Format Error: " + e.getMessage());
           }
       }
   }

}
