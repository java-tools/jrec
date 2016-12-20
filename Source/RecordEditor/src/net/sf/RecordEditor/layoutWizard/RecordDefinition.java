package net.sf.RecordEditor.layoutWizard;

import java.util.ArrayList;


public class RecordDefinition {

	public boolean searchForFields = true;
	public boolean displayedFieldSelection = false;
	public boolean displayedFieldNames = false;
	public Object[] keyValue;
	public Object[] keyValueHold;
	public Boolean defaultRec = Boolean.FALSE,
					  include    = Boolean.TRUE;
	public String name = "";
	public     int numRecords = 0;
	public byte[][] records = new byte[600][];
	public ArrayList<ColumnDetails> columnDtls = new ArrayList<ColumnDetails>();

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
	
	public int getMaxRecordLength() {
		int rl = 0;
		
		for (int i = 0; i < numRecords; i++) {
			rl = Math.max(rl, records[i].length);
		}
		
		return rl;
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
