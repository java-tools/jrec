package net.sf.RecordEditor.re.cobol;

import java.util.ArrayList;
import java.util.Arrays;

import net.sf.JRecord.External.ExternalRecord;

public class RecordSelectionDetails {
	
	private final static String[] EMPTY_FIELDS = {"", "", "", "", ""};
	public final static int FIELD_VALUE_COUNT = EMPTY_FIELDS.length - 1;

	private ArrayList<String[]> recordSelect = new ArrayList<String[]>();

	public int size() {
		return recordSelect.size();
	}
	
	
	public String getFieldName(int idx) {
		return getRecordSelection(idx, 0);
	}
	

	public String getFieldValue(int idx, int number) {
		return getRecordSelection(idx, number + 1);
	}
	
	
	public boolean setFieldName(int idx, String name) {
		return setRecordSelection(idx, 0, name);
	}
	
	public boolean setFieldValue(int idx, int fieldNumber, String value) {
		return setRecordSelection(idx, fieldNumber, value);
	}

	private String getRecordSelection(int recordIdx, int fieldIdx) {
		if (recordIdx >= recordSelect.size()) {return "";}
		return recordSelect.get(recordIdx)[fieldIdx];
	}

	
	private boolean setRecordSelection(int recordIdx, int fieldIdx, String value) {
		boolean changed = false;
		initRecordSelect(recordIdx);

		if (recordSelect.get(recordIdx)[fieldIdx] != value) {
			changed = true;
			
			recordSelect.get(recordIdx)[fieldIdx] = value;
		}
		
		return changed;
	}

	public void addRecordSelection(int recordIdx, String value, boolean check) {
		initRecordSelect(recordIdx);

		String[] currValues = recordSelect.get(recordIdx);
		for (int i = 1; i < currValues.length; i++) {
			if ( currValues[i].equalsIgnoreCase(value)) {
				return;
			}
		}
		for (int i = 1; i < currValues.length; i++) {
			if ( currValues[i].length() == 0) {
				currValues[i] = value;
				break;
			}
		}
	}

	
	/**
	 * @param recordIdx
	 */
	private void initRecordSelect(int recordIdx) {
		while (recordIdx >= recordSelect.size()) {
			recordSelect.add(EMPTY_FIELDS.clone());
		}
	}

	

	public void initRecordSelection(ExternalRecord xRec) {
		int size =  xRec.getNumberOfRecords();

		recordSelect.ensureCapacity(size);

		for (int i = 0; i < size; i++) {
			if (i < recordSelect.size()) {
				Arrays.fill(recordSelect.get(i), "");
			} else {
				recordSelect.add(EMPTY_FIELDS.clone());
			}
		}
	}

	
	public boolean areAllSelectionsDefined(ExternalRecord xRecord, int defaultRow) {
		boolean ret = false;
		if (xRecord != null && xRecord.getNumberOfRecords() > 1 
		&& (recordSelect.size() >= xRecord.getNumberOfRecords()
		   || (recordSelect.size() == xRecord.getNumberOfRecords()-1)
		     && defaultRow == recordSelect.size())) {
			
			for (int i = 0; i < recordSelect.size(); i++) {
				if (defaultRow == i || recordSelect.get(i)[0].length() > 0) {
					
				} else {
					return false;
				}
			}
			ret = true;
		}
		return ret;
	}

}
