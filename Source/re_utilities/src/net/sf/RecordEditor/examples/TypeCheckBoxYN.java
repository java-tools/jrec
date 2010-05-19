/*
 * @Author Bruce Martin
 * Created on 7/09/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.examples;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.record.format.CellFormat;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;


/**
 * Thic class implements a <b>type</b> that will display a 1 byte field
 * (possible values <b>Y / N</b>) as a checkbox
 *
 * @author Bruce Martin
 *
 */
public class TypeCheckBoxYN implements Type, CellFormat {

    private final CheckBoxTableRender checkBoxRendor = new CheckBoxTableRender();
    private static final byte YES_BYTE = 'Y';
    private static final byte NO_BYTE  = 'N';


    /**
     * @see net.sf.JRecord.Types.Type#formatValueForRecord(record.layout.DetailField, java.lang.String)
     */
    public String formatValueForRecord(FieldDetail field, String val)
            throws RecordException {
        return val;
    }


    /**
     * @see net.sf.JRecord.Types.Type#getField(byte[], record.layout.DetailField)
     */
    public Object getField(byte[] record,
            final int position,
			final FieldDetail currField) {
        Boolean b = Boolean.FALSE;

        if (record[position - 1] == YES_BYTE) {
            b = Boolean.TRUE;
        }
        return b;
    }


    /**
     * @see net.sf.JRecord.Types.Type#setField(byte[], record.layout.DetailField, java.lang.String)
     */
    public byte[] setField(byte[] record,
            final int position,
			final FieldDetail field,
			Object val)
    throws RecordException {

        record[position - 1] = NO_BYTE;

        if ("true".equalsIgnoreCase(val.toString())) {
            record[field.getPos() - 1] = YES_BYTE;
        }
        return record;
    }


    /**
     * @see net.sf.JRecord.Types.Type#getTableCellEditor
     *
     * <b>Note:</b> you should always return a new Editor rather than a
     * the same editor each time
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
         return new DefaultCellEditor(new JCheckBox());
    }


    /**
     * @see net.sf.JRecord.Types.Type#getTableCellRenderer()
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        return checkBoxRendor;
    }


    /**
     * Get the normal width of a field
     *
     * @return field width
     */
    public int getFieldWidth() {
        return Type.NULL_INT;
    }


    /**
     * Get the normal height of a field
     *
     * @return field height
     */
    public int getFieldHeight() {
        return Type.NULL_INT;
    }


    /**
     * @see net.sf.JRecord.Types.Type#isBinary()
     */
    public boolean isBinary() {
        return false;
    }

    /**
     * Wether it is a numeric Type or not
     */
    public boolean isNumeric() {
        return false;
    }


    /**
     * @see net.sf.JRecord.Types.Type#getFieldType()
     */
    public int getFieldType() {
        return Type.NT_TEXT;
    }
}
