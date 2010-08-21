/*
 * @Author Bruce Martin
 * Created on 9/02/2007 version 0.60
 *
 * Purpose:
 * A String based check box format with supplied yes / no strings
 */
package net.sf.RecordEditor.record.format;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellEditor;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.RecordEditor.utils.swing.CheckBoxTableRender;
import net.sf.RecordEditor.utils.swing.CheckboxTableRenderStringBased;


/**
 * A String based check box format with supplied yes / no strings
 *
 * @author Bruce Martin
 *
 */
public class CheckBoxBooleanFormat implements CellFormat {

    private CheckBoxTableRender render = new CheckBoxTableRender();


  

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return CheckBoxTableRender.CELL_HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return CheckBoxTableRender.CELL_WIDTH;
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
        return new DefaultCellEditor(new CheckBoxTableRender());
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        return render;
    }
}
