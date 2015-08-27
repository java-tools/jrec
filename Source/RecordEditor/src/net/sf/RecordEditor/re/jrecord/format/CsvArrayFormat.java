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
import net.sf.JRecord.CsvParser.BasicCsvLineParser;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.RecordEditor.utils.swing.CsvArray;
import net.sf.RecordEditor.utils.swing.CsvArrayTableEditor;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Provide a date formater with a popup date selector
 *
 * @author Bruce Martin
 *
 */
public class CsvArrayFormat implements CellFormat {


	private static int HEIGHT = Math.max(SwingUtils.TABLE_ROW_HEIGHT,
			Math.min(SwingUtils.CHECK_BOX_HEIGHT, SwingUtils.COMBO_TABLE_ROW_HEIGHT));
    private CsvArray render = null;




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

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    @Override
    public TableCellEditor getTableCellEditor(IFieldDetail fld) {
        String[] f = getFields(fld);
        return new CsvArrayTableEditor(f[1], f[2], f[0]);
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    @Override
    public TableCellRenderer getTableCellRenderer(IFieldDetail fld) {
        if (render == null) {
            String[] f = getFields(fld);
            render = new CsvArray(f[1], f[2], f[0]);
        }
        return render;
    }

//    /**
//     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
//     */
//    public boolean isCellEditable(EventObject anEvent) {
//        if (anEvent instanceof MouseEvent) {
//    		return ((MouseEvent)anEvent).getClickCount() >= 2;
//   	    }
//       return true;
//    }

    /**
     * Get the Swing checkbox
     * @param fld field definition
     * @return Swing checkbox
     */
    private String[] getFields(IFieldDetail fld) {
        String s = fld.getParamater();
        BasicCsvLineParser p = BasicCsvLineParser.getInstance();

        return p.split(s.substring(1), new CsvDefinition(s.substring(0, 1), ""), 3);

        /*String[] r = p.split(s.substring(1), s.substring(0, 1), "", 3);

        System.out.print("~~>" + s + " > " + r.length + "  ");

        for (int i = 0; i < r.length; i++) {
            System.out.print(": " + r[i] + " :");
        }

        return r;*/
    }
}
