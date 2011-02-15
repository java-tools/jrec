package net.sf.RecordEditor.layoutEd.Parameter;

import java.awt.Component;

import javax.swing.JTable;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.utils.swing.BasicGenericPopup;

import com.zbluesoftware.java.bm.AbstractGenericCombo;
import com.zbluesoftware.java.bm.AbstractPopup;
import com.zbluesoftware.java.bm.GenericComboTableRender;

@SuppressWarnings("serial")
public class ParameterTableEditor extends GenericComboTableRender {
	
	private static final int FLD_FIELD_TYPE = 4;
	private static final int FLD_FORMAT     = 6;
	
	private static final ParameterPopupManager TYPE_MANAGER = ParameterPopupManager.getTypePopupManager();
	private static final ParameterPopupManager FORMAT_MANAGER = ParameterPopupManager.getFormatPopupManager();

	private BasicParameterEditor lastParameterEditor = null;
	
	private final int dbId;
	
	/**
	 * create parameter Table Editor
	 *
	 */
	public ParameterTableEditor(final int databaseId) {
		super(true, null);
		
		dbId = databaseId;
		
		super.setComboField(new ParamCombo());
	}
	
	

	/* (non-Javadoc)
	 * @see com.zbluesoftware.java.bm.GenericComboTableRender#getCombo()
	 */
	@Override
	protected AbstractGenericCombo getCombo() {
		// TODO Auto-generated method stub
		return new ParamCombo();
	}



	/**
	 * @see com.zbluesoftware.java.bm.GenericComboTableRender#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		getParameterEditor(table.getValueAt(row, FLD_FIELD_TYPE),
				table.getValueAt(row, FLD_FORMAT));
	
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
	}


	/**
	 * @see com.zbluesoftware.java.bm.GenericComboTableRender#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		
		lastParameterEditor = getParameterEditor(table.getValueAt(row, FLD_FIELD_TYPE),
				table.getValueAt(row, FLD_FORMAT));

		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
	
	private BasicParameterEditor getParameterEditor(Object type,  Object format) {
		BasicParameterEditor ret = null;
		
		ret = TYPE_MANAGER.get(getInt(type));
		
		if (ret == null) {
			ret = FORMAT_MANAGER.get(getInt(format));
		}
		getComboField().setButtonVisible(ret != null && ret.hasPopup());
		
		return ret;
	}
	
	/**
	 * Get the Field Type
	 * @param o Field Type as a Object
	 * @param defaultType default value
	 * @return the type
	 */
	private int getInt(Object o) {
		int intVal = Constants.NULL_INTEGER;
		try {
			intVal = ((Integer) o).intValue();
		} catch (Exception e) {
		}
		return intVal;
	}

	/**
	 * Field Parameter Combobox
	 * 
	 * 
	 * @author Bruce Martin
	 *
	 */
	private class ParamCombo extends BasicGenericPopup {

		/**
		 * @see com.zbluesoftware.java.bm.AbstractGenericCombo#getPopup()
		 */
		@Override
		public AbstractPopup getPopup() {
			AbstractPopup current = null;
			if (lastParameterEditor != null && lastParameterEditor.hasPopup()) {
				current = lastParameterEditor.getPopup(dbId);
			}
			
			setPopup(current);
			return current;
		}
	}

}
