/*
 * @Author Bruce Martin
 * Created on 7/09/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.examples;

import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.RecordEditor.re.jrecord.format.CellFormat;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.AbstractRowList;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBoxRender;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;


/**
 * Thic class implements a Generic Combo Box format that uses the
 * Field parameter to decide which combo to display
 *
 * @author Bruce Martin
 *
 */
public class FormatComboExample implements CellFormat {


    private static final int CELL_HEIGHT = 22;
    private static final int CELL_WIDTH  = 80;

    private HashMap map = new HashMap();

    /**
     * Combobox type
     */
    public FormatComboExample() {
        super();
    }


    /**
     * registers a Combo and the id / display values. The
     * id must be the same as the parameter
     *
     * @param comboId Combo Identifier
     * @param comboItems Combo Key/Display items
     */
    public void register(String comboId, Object[][] comboItems) {

    	AbsRowList list = new AbsRowList(0, 1, false, false);

    	list.loadData(comboItems);

    	//System.out.println("== >" + comboId + "<");
    	map.put(comboId.toUpperCase(), list);
    }


    /**
     * @see net.sf.JRecord.Types.Type#getTableCellEditor
     *
     * <b>Note:</b> you should always return a new Editor rather than a
     * the same editor each time
     */
    public TableCellEditor getTableCellEditor(FieldDetail fld) {
       	String key = fld.getParamater().toUpperCase();

       	//System.out.println("~~ >" + key + "<");
       	if (map.containsKey(key)) {
    		return new DefaultCellEditor(
    				new BmKeyedComboBox(
    						(AbstractRowList) map.get(key),
							false
						)
				   );
    	}
        return null;
    }

    /**
     * @see net.sf.JRecord.Types.Type#getTableCellRenderer()
     */
    public TableCellRenderer getTableCellRenderer(FieldDetail fld) {
    	String key = fld.getParamater().toUpperCase();

    	if (map.containsKey(key)) {
    		return new BmKeyedComboBoxRender(
    				new BmKeyedComboModel((AbstractRowList) map.get(key)),
					false
					);
    	}
        return null;
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
}