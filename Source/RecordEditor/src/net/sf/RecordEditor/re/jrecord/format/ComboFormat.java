/*
 * @Author Bruce Martin
 * Created on 9/02/2007 version 0.60
 *
 * Purpose:
 * Provide a date formater with a popup date selector
 */
package net.sf.RecordEditor.re.jrecord.format;

import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.RecordEditor.re.db.Combo.ComboDB;
import net.sf.RecordEditor.re.db.Combo.ComboRec;
import net.sf.RecordEditor.re.db.Combo.ComboValuesDB;
import net.sf.RecordEditor.utils.common.ReConnection;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBoxRender;
import net.sf.RecordEditor.utils.swing.BmKeyedComboModel;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Provide a date formater with a popup date selector
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("unchecked")
public class ComboFormat implements CellFormat {


	//private boolean toInitRender = true;
    //private TableCellRenderer render = null;

    private static HashMap<String, ComboRec>[] map
    	= new HashMap[Constants.NUMBER_OF_COPYBOOK_SOURCES];

//    static {
//    	for (int i =0; i < map.length; i++) {
//    		map[i] = null;
//    	}
//    }


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
        return Constants.NULL_INTEGER;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellEditor(net.sf.RecordEditor.record.types.FieldDetail)
     */
    @Override
    public TableCellEditor getTableCellEditor(IFieldDetail fld) {
    	TableCellEditor ret = null;

    	ComboRec comboDtls = getCombo(fld.getRecord().getSourceIndex(), fld.getParamater());

    	if (comboDtls != null) {
    		//if (comboDtls.getColumnType() == ComboRec.SINGLE_COLUMN) {
	    		ret = new DefaultCellEditor(new BmKeyedComboBox(comboDtls.getRows(), true));
	    	//} else {

	    	//}
    	}
        return ret;
    }

    /**
     * @see net.sf.RecordEditor.re.jrecord.format.CellFormat#getTableCellRenderer(net.sf.RecordEditor.record.types.FieldDetail)
     */
    @Override
    public TableCellRenderer getTableCellRenderer(IFieldDetail fld) {

    	TableCellRenderer render = null;
    	ComboRec comboDtls = getCombo(fld.getRecord().getSourceIndex(), fld.getParamater());

    	if (comboDtls != null) {
    		render = new BmKeyedComboBoxRender(
    				new BmKeyedComboModel(comboDtls.getRows()),
    				true);
    	}

    	return render;
    }

	/**
	 * @param name
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	private ComboRec getCombo(int idx, String name) {

		if (idx < 0 || idx >= map.length || name == null || "".equals(name)) {
			return null;
		}

		if (map[idx] == null) {
			map[idx] = new HashMap<String, ComboRec>();
		}
		ComboRec ret = map[idx].get(name);

		if (ret == null) {
			ReConnection con = new ReConnection(idx);
			ComboDB db = new ComboDB();
			db.setConnection(con);

			ret = db.get(name);

			if (ret != null) {
				ComboValuesDB valuesDB = new ComboValuesDB();
				AbsRowList rows;
				valuesDB.setConnection(con);
				valuesDB.setParams(ret.getComboId());

				if (ret.getColumnType() == ComboRec.SINGLE_COLUMN) {
					rows = new AbsRowList(0, 0, false, false);
				} else {
					rows = new AbsRowList(0, 1, false, false);
				}
				rows.loadData(valuesDB.fetchAll());
				ret.setRows(rows);

				map[idx].put(name, ret);
			}
		}

		return ret;
	}
}
