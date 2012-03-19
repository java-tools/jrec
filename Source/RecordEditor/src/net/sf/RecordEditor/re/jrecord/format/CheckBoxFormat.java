/*
 * @Author Bruce Martin
 * Created on 9/02/2007 version 0.60
 *
 * Purpose:
 * A String based check box format with supplied yes / no strings
 */
package net.sf.RecordEditor.re.jrecord.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.RecordEditor.utils.swing.CheckboxTableRenderStringBased;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * A String based check box format with supplied yes / no strings
 *
 * @author Bruce Martin
 *
 */
public class CheckBoxFormat implements CellFormat {

    private CheckboxTableRenderStringBased render;
    private String yesStr;
    private String noStr;
    private boolean defaultVal;


    /**
     * A String based check box format with supplied yes / no strings
     * @param yes yes string
     * @param no  no string
     * @param defaultValue default value
     */
    public CheckBoxFormat(final String yes,
                final String no, final boolean defaultValue) {
        yesStr = yes;
        noStr  = no;
        defaultVal = defaultValue;

        render = new CheckboxTableRenderStringBased(yesStr, noStr, defaultVal, false);
    }


    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return SwingUtils.CHECK_BOX_HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return SwingUtils.CHECK_BOX_WIDTH;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
        return new CheckboxTableRenderStringBased(yesStr, noStr, defaultVal, false);
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        return render;
    }
}
