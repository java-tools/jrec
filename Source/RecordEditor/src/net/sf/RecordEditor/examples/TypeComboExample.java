/*
 * @Author Bruce Martin
 * Created on 7/09/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.examples;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.TypeChar;
import net.sf.RecordEditor.record.format.CellFormat;


/**
 * Thic class implements a type that will display a 1 byte field
 * (possible values Y / N) as a checkbox
 * 
 * @author Bruce Martin
 *
 */
public class TypeComboExample extends TypeChar implements CellFormat {

    private static final String[] FILE_VALUES  = {"S", "DC", "RO", "H"};
    private static final String[] DISPLAY_VALUES =
    	{"Store", "Distribution Center", "Regional Office", "Head Office"};

    private static final int CELL_HEIGHT = 22;
    private static final int CELL_WIDTH  = 270;

    private ComboRendor comboRendor = new ComboRendor(new JComboBox(DISPLAY_VALUES));

    /**
     * Combobox type
     */
    public TypeComboExample() {
        super(true);
    }


    /**
     * @see net.sf.JRecord.Types.Type#formatValueForRecord(record.layout.DetailField, java.lang.String)
     */
    public String formatValueForRecord(FieldDetail field, String val)
            throws RecordException {
        return findCorrespondingString(val, DISPLAY_VALUES, FILE_VALUES);
    }


    /**
     * @see net.sf.JRecord.Types.Type#getField(byte[], record.layout.DetailField)
     */
    public Object getField(byte[] record,
            final int position,
			final FieldDetail field) {

        return findCorrespondingString(super.getField(record, position, field).toString(),
                FILE_VALUES, DISPLAY_VALUES);
    }


    /**
     * @see net.sf.JRecord.Types.Type#setField(byte[], record.layout.DetailField, java.lang.String)
     */
    public byte[] setField(byte[] record,
            final int position,
			final FieldDetail field, Object val)
    throws RecordException {

        super.setField(record, position, field,
                findCorrespondingString(val.toString(),
                        				DISPLAY_VALUES,
                        				FILE_VALUES));
        return record;
    }


    /**
     * Finds corresponding values
     *
     * @param value value to search for
     * @param compareVals array to search
     * @param resultVals array to get return value from
     *
     * @return corresponding value
     */
    private String findCorrespondingString(String value,
            							   String[] compareVals,
            							   String[] resultVals) {
        String ret = resultVals[0];
        String val = value.trim();
        int i;

        for (i = 0; i < compareVals.length; i++) {
            if (val.equals(compareVals[i])) {
                ret = resultVals[i];
                break;
            }
        }

        return ret;
    }


    /**
     * @see net.sf.JRecord.Types.Type#getTableCellEditor
     *
     * <b>Note:</b> you should always return a new Editor rather than a
     * the same editor each time
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
         return new DefaultCellEditor(new JComboBox(DISPLAY_VALUES));
    }

    /**
     * @see net.sf.JRecord.Types.Type#getTableCellRenderer()
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        return comboRendor;
    }


    /**
     * Get the normal height of a field
     *
     * @return field height
     */
    public int getFieldHeight() {
        return CELL_HEIGHT;
    }


    /**
     * @see net.sf.JRecord.Types.Type#getFieldWidth()
     */
    public int getFieldWidth() {
         return CELL_WIDTH;
    }



    /**
     * Combo box table rendor
     *
     *
     * @author Bruce Martin
     *
     */
    private static class ComboRendor implements TableCellRenderer {
        private JComboBox combo;


        /**
         * Combobox table rendor
         *
         * @param comboBox combobox to display
         */
        public ComboRendor(final JComboBox comboBox) {
            combo = comboBox;
            combo.setBorder(BorderFactory.createEmptyBorder());
        }


    	/**
    	 * @see javax.swing.table.TableCellRender#getTableCellRendererComponent
    	 */
    	public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            if (isSelected) {
                combo.setForeground(table.getSelectionForeground());
                combo.setBackground(table.getSelectionBackground());
            } else {
                combo.setForeground(table.getForeground());
                combo.setBackground(table.getBackground());
            }

            combo.setSelectedItem(value);
            return combo;
        }
    }

}
