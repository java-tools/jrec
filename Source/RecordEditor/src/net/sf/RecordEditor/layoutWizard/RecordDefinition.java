package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;

public class RecordDefinition {

	protected boolean searchForFields = true;
	protected boolean displayedFieldSelection = false;
	protected boolean displayedFieldNames = false;
	private Object[] keyValue;
	protected Object[] keyValueHold;
	protected Boolean defaultRec = Boolean.FALSE,
					  include    = Boolean.TRUE;
	protected String name = "";
	protected     int numRecords = 0;
	protected byte[][] records = new byte[60][];
	protected ArrayList<ColumnDetails> columnDtls = new ArrayList<ColumnDetails>();

	public final void addKeyField(Details detail, boolean addRest) {
		ColumnDetails colDtls;
		KeyField k;
		
		for (int i = 0; i < detail.keyFields.size(); i++) {
			if (keyValue == null 
			||  defaultRec.booleanValue()
			|| (i < keyValue.length && keyValue[i] != null)) {
				k = detail.keyFields.get(i);
				colDtls = new ColumnDetails(k.keyStart, k.keyType.intValue());
				colDtls.name = k.keyName; 
				colDtls.length = k.keyLength;
				
				columnDtls.add(colDtls);
			}
		}
		
		if (addRest) {
			k = detail.keyFields.get(detail.keyFields.size() - 1);
			columnDtls.add(
					new ColumnDetails(
							k.keyStart + k.keyLength, 
							detail.defaultType.intValue()));
		}
	}

	/**
	 * @return the keyValue
	 */
	public Object[] getKeyValue() {
		return keyValue;
	}

	/**
	 * @param keyValue the keyValue to set
	 */
	public void setKeyValue(Object[] keyValue) {
		this.keyValue = keyValue;
		keyValueHold = keyValue.clone();
	}
	
	
	public String getStringKey() {
		return getStringKey(keyValue, "~");
	}
	
	
	public String getStringKey(String sep) {
		return getStringKey(keyValue, sep);
	}
	
	
	public static String getStringKey(Object[] keys) {
		return getStringKey(keys, "~");
	}
	
	
	public static String getStringKey(Object[] keys, String seperator) {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		
		for (int i = 0; i < keys.length; i++) {	
			if (keys[i] != null) {
				sb.append(sep);
				sb.append(keys[i]);
				sep = seperator;
			}
		}
		return sb.toString();
	}
}
