package net.sf.RecordEditor.utils;

import net.sf.JRecord.Types.TypeManager;

public class TypeNameArray {

	String[] typeNames = new String[TypeManager.SYSTEM_ENTRIES];
	
	public TypeNameArray() {
		for (int i = 0; i < TypeManager.SYSTEM_ENTRIES; i++) {
			typeNames[i] = "";
		}
	}
	
	/**
	 * Get Type Name
	 * @param type type id
	 * @return type name
	 */
	public String get(int type) {
		if (type < 0 || type >= typeNames.length) {
			return "";
		}
		return typeNames[type];
	}
	
	/**
	 * Set the Type name
	 * @param type type id
	 * @param newValue String value
	 */
	public void set(int type, String newValue) {
		if (type >= 0 && type < typeNames.length) {
			typeNames[type] = newValue;
		}
	}

}
