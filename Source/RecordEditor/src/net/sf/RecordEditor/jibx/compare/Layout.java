package net.sf.RecordEditor.jibx.compare;

import java.util.ArrayList;

public class Layout {
	public String name = "";
	public ArrayList<Record> records = null;
	public String saveFile = "";
	
	/**
	 * @return the records
	 */
	public ArrayList<Record> getRecords() {
		if (records == null) {
			records = new ArrayList<Record>();
		}
		return records;
	}
	
	/**
	 * @return the records
	 */
	public ArrayList<FilteredRecord> getFilteredRecords() {
		ArrayList<FilteredRecord> list = new ArrayList<FilteredRecord>(records.size());
		FilteredRecord fr;
		
		for (Record rec : getRecords()) {
			fr = new FilteredRecord();
			fr.fields = rec.fields;
			fr.fieldTest = rec.fieldTest;
			fr.name = rec.name;
			
			list.add(fr);
		}
		return list;
	}
}
