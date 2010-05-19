/*
 * @Author Bruce Martin
 * Created on 9/02/2007 version 0.60
 *
 * Purpose:
 * Provide a date formater with a popup date selector
 */
package net.sf.RecordEditor.record.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.zbluesoftware.java.bm.GenericComboTableRender;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.MultiLineCombo;


/**
 * Provide a date formater with a popup date selector
 *
 * @author Bruce Martin
 *
 */
public class MultiLineFormat implements CellFormat {


    private TableCellRenderer render = null;



    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return Common.COMBO_TABLE_ROW_HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return Constants.NULL_INTEGER;
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
        return new GenericComboTableRender(false, 
				 						   new MultiLineCombo());
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        if (render == null) {
            render = new GenericComboTableRender(false, 
            									 new MultiLineCombo());
        }
        return render;
    }
}
