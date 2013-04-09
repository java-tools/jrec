/*
 * @Author Bruce Martin
 * Created on 27/08/2005
 *
 * Purpose: To provide table format details to be used to display a field
 *
 */
package net.sf.RecordEditor.re.jrecord.format;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.IFieldDetail;



/**
 * A "CellFormat" holds table-cell format details to be used to
 * display a field in a JTable.
 *
 * @author Bruce Martin
 *
 * @version 0.55
 */
public interface CellFormat {

    public  static final int USER_RANGE_START        = 1000;
    public  static final int DEFAULT_USER_RANGE_SIZE = 50;

    public  static final int FMT_CHECKBOX            = 1;
    public  static final int FMT_DATE                = 2;
    public  static final int FMT_DATE_DMYY           = 3;
    public  static final int FMT_DATE_YYMD           = 4;
    public  static final int FMT_COMBO               = 15;
    public  static final int FMT_BOLD                = 21;
    public  static final int FMT_COLOR               = 22;

    /**
     * Get Table Cell Render
     * <b>Note:</b> you should always return a new Editor rather than a
     * the same editor each time
     *
     * @param fld field being displayed
     *
     * @return Table Cell Render to be used to display the field
     */
    public abstract TableCellRenderer getTableCellRenderer(IFieldDetail fld);

    /**
     * Get Table Cell Editor
     *
     * @param fld field being displayed
     *
     * @return Table Cell Editor to be used to edit the field
     */
    public abstract TableCellEditor getTableCellEditor(IFieldDetail fld);


    /**
     * Get the normal width of a field
     *
     * @return field width
     */
    public abstract int getFieldWidth();


    /**
     * Get the normal height of a field
     *
     * @return field height
     */
    public abstract int getFieldHeight();
}