package net.sf.RecordEditor.jibx.compare;

import net.sf.JRecord.Details.RecordFilter;

public class FilteredRecord 
extends Record implements  RecordFilter {

	/**
	 * @see net.sf.JRecord.Details.RecordFilter#getFields()
	 */
	@Override
	public String[] getFields() {
		return fields;
	}

	/**
	 * @see net.sf.JRecord.Details.RecordFilter#getRecordName()
	 */
	@Override
	public String getRecordName() {
		// TODO Auto-generated method stub
		return name;
	}

	
}
