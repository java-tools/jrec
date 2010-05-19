/*
 * @Author Bruce Martin
 * Created on 9/02/2007 version 0.60
 *
 * Purpose:
 * A String based check box format with supplied yes / no strings
 */
package net.sf.RecordEditor.record.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.RecordEditor.utils.swing.CheckboxTableRenderStringBased;


/**
 * A check box format with supplied yes / no strings
 * coming from the field
 *
 * @author Bruce Martin
 *
 */
public class CheckBoxFldFormat implements CellFormat {

    private CheckboxTableRenderStringBased render = null;
    //render = new StringCheckBoxTableRender(yesStr, noStr, defaultVal);


    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getFieldHeight()
     */
    public int getFieldHeight() {
        return CheckboxTableRenderStringBased.CELL_HEIGHT;
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getFieldWidth()
     */
    public int getFieldWidth() {
        return CheckboxTableRenderStringBased.CELL_WIDTH;
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
        return getCheckbox(fld);
    }

    /**
     * @see net.sf.RecordEditor.record.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
        if (render == null) {
            render = getCheckbox(fld);
        }
        return render;
    }

    /**
     * Get the Swing checkbox
     * @param fld field definition
     * @return Swing checkbox
     */
    private CheckboxTableRenderStringBased getCheckbox(FieldDetail fld) {
        String s = fld.getParamater();
        CheckboxTableRenderStringBased ret = null;

        if (s != null) {
            try {
            	BasicParser parser = BasicParser.getInstance();
            	String line = s.substring(1);
            	String delim = s.substring(0, 1);
            	
                String yesStr = parser.getField(0, line, delim, "");
                String noStr  = parser.getField(1, line, delim, "");
                String defaultValue  = parser.getField(2, line, delim, "");
                //System.out.println("Case Sensitive ~~> " + parser.getField(3, line, delim, ""));
                boolean caseSensitive = "Y".equalsIgnoreCase(parser.getField(3, line, delim, "")) ;
               
                ret = new CheckboxTableRenderStringBased(yesStr, noStr, 
                		yesStr.equals(defaultValue), caseSensitive);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
