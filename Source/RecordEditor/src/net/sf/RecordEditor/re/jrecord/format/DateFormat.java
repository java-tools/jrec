/*
 * @Author Bruce Martin
 * Created on 9/02/2007 version 0.60
 *
 * Purpose:
 * Provide a date formater with a popup date selector
 */
package net.sf.RecordEditor.re.jrecord.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.zbluesoftware.java.bm.ZDateTableRender;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Provide a date formater with a popup date selector
 *
 * @author Bruce Martin
 *
 */
public class DateFormat implements CellFormat {

    private static final int CELL_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 8;

    private ZDateTableRender render = null;
    private boolean useDateFormat;
    //private SimpleDateFormat df = null;
    private String dateFormat;

    /**
     * Date format for the record editor. It provides
     * basic date selection capability
     *
     * @param isDateFormat wether it will receive/send dates in date
     * format (or string format)
     *
     * @param dateFormatString date format to use
     */
    public DateFormat(final boolean isDateFormat, final String dateFormatString) {
        useDateFormat = isDateFormat;

        dateFormat = dateFormatString;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return SwingUtils.COMBO_TABLE_ROW_HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return CELL_WIDTH;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
        return getDateDisplay(fld);
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        if (render == null) {
            render = getDateDisplay(fld);
        }
        return render;
    }

    /**
     * Get the Swing checkbox
     * @param fld field definition
     * @return Swing checkbox
     */
    private ZDateTableRender getDateDisplay(FieldDetail fld) {
        String s = dateFormat;
        ZDateTableRender ret = null;

        if (s == null) {
            s = fld.getParamater();
        }

        try {
            ret = new ZDateTableRender(useDateFormat, s);
        } catch (Exception e) {
            System.out.println("@@ format > " + s);
            e.printStackTrace();
        }

        return ret;
    }
}
