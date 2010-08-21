package net.sf.RecordEditor.layoutEd.Parameter;

import com.zbluesoftware.java.bm.AbstractPopup;

import net.sf.JRecord.Common.BasicManager;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.RecordEditor.record.format.CellFormat;
import net.sf.RecordEditor.record.types.ReTypeManger;


public class ParameterPopupManager extends BasicManager<BasicParameterEditor> {

	public static final ParameterPopupManager typePopupManager 
		= new ParameterPopupManager(TypeManager.SYSTEM_ENTRIES, Type.USER_RANGE_START, TypeManager.getSystemTypeManager().getUserSize());
	
	
	public static final ParameterPopupManager formatPopupManager
		= new ParameterPopupManager(ReTypeManger.FORMAT_SYSTEM_ENTRIES, CellFormat.USER_RANGE_START, CellFormat.DEFAULT_USER_RANGE_SIZE);
	
	
	static {
		BasicParameterEditor dateEd = new BasicParameterEditor(DatePopup.class);
		BasicParameterEditor ComboEd = new BasicParameterEditor(ComboPopup.class) {
			/**
			 * @see net.sf.RecordEditor.layoutEd.Parameter.BasicParameterEditor#getPopup(int)
			 */
			@Override
			public AbstractPopup getPopup(int databaseId) {
				
				return new ComboPopup(databaseId);
			}
			
		};
		
		typePopupManager.register(Type.ftDate, dateEd);
		typePopupManager.register(Type.ftCsvArray, new BasicParameterEditor(CsvArrayPopup.class));
		
		formatPopupManager.register(CellFormat.FMT_DATE, dateEd);
		formatPopupManager.register(CellFormat.FMT_CHECKBOX, new BasicParameterEditor(CheckBoxPopup.class));
		formatPopupManager.register(CellFormat.FMT_COMBO, ComboEd);
	}
	
	
	public ParameterPopupManager(int numberOfSystemEntries, int startOfUserRange, int numberOfUserEntries) {
		super(numberOfSystemEntries, startOfUserRange, 
				new BasicParameterEditor[numberOfSystemEntries + numberOfUserEntries] );
	}

	
	/**
	 * Returns wether ther is a poput or not
	 * @param type type to check
	 * @return wether there is a poput
	 */
	public boolean hasPopup(int type) {
		BasicParameterEditor ed = super.get(type);
		return ed != null && ed.hasPopup();
	}
	


	/**
	 * @return the typePopupManager
	 */
	public static ParameterPopupManager getTypePopupManager() {
		return typePopupManager;
	}


	/**
	 * @return the formatPopupManager
	 */
	public static ParameterPopupManager getFormatPopupManager() {
		return formatPopupManager;
	}
}
