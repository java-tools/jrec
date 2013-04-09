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

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.RecordEditor.utils.swing.MultiLineCombo;
import net.sf.RecordEditor.utils.swing.SwingUtils;

import com.zbluesoftware.java.bm.AbstractGenericCombo;
import com.zbluesoftware.java.bm.GenericComboTableRender;


/**
 * Provide a date formater with a popup date selector
 *
 * @author Bruce Martin
 *
 */
public class MultiLineFormat implements CellFormat {


   private static int HEIGHT = Math.max(SwingUtils.TABLE_ROW_HEIGHT,
		   Math.min(SwingUtils.CHECK_BOX_HEIGHT, SwingUtils.COMBO_TABLE_ROW_HEIGHT));
   private TableCellRenderer render = null;



    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return Constants.NULL_INTEGER;
    }

    @SuppressWarnings("serial")
	private GenericComboTableRender getTableRender() {
        return new GenericComboTableRender(false, new MultiLineCombo()) {
			@Override
			protected AbstractGenericCombo getCombo() {
				return new MultiLineCombo();
			}
        };
    }


    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    @Override
    public TableCellEditor getTableCellEditor(IFieldDetail fld) {
        return getTableRender();
	//			 						   new MultiLineCombo());
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    @Override
    public TableCellRenderer getTableCellRenderer(IFieldDetail fld) {
        if (render == null) {
            render = getTableRender();
        }
        return render;
    }
}
