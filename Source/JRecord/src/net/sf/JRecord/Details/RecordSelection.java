package net.sf.JRecord.Details;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.External.TstField;

public class RecordSelection {
	
	private ArrayList<SelectionField> flds;

	private final RecordDetail parent;
	private boolean defaultRecord = false;
	
	
	public RecordSelection(RecordDetail parent) {
		super();
		this.parent = parent;
	}
	
	
	/**
	 * @param index
	 * @return
	 * @see java.util.ArrayList#get(int)
	 */
	public SelectionField get(int index) {
		return flds.get(index);
	}

	/**
	 * @return
	 * @see java.util.ArrayList#size()
	 */
	public int size() {
		if (flds == null) {
			return 0;
		}
		return flds.size();
	}

	/**
	 * Add a Field/Value that should be tested to determine if this is the valid
	 * Sub-Record for the current line.
	 * 
	 * @param tstField the tstField to set
	 * @param value Value to compare field to
	 */ 
	public void addTstField(String tstField, String value) {
		if (tstField == null || "".equals(tstField)) {
			if ("*".equals(value)) {
				defaultRecord = true;
			}
		} else {
			FieldDetail fld = parent.getField(tstField);
			
			getFields().add( new SelectionField(tstField, fld, value));
		}
	}
	
	/**
	 * Add a list of TstFields
	 * @param flds fields to add
	 */
	public void add(List<TstField> flds) {
		for (TstField fld : flds) {
    		addTstField(fld.fieldName, fld.value);
    	}
	}
	
	public void setTstField(int idx, String tstField, FieldDetail fld, String value) {
		if (getFields().size() == 0) {
			flds.add(null);
		}
		flds.set(idx, new SelectionField(tstField, fld, value));
	}
	
	public RecordSelectionResult isSelected(AbstractLine line) {
		RecordSelectionResult ret = RecordSelectionResult.NO;
		
		if (size() > 0) {
			boolean ok = true;
			Object o;
			SelectionField sel;
			for (int i = 0; ok && i < flds.size(); i++) {
				sel = flds.get(i);
				
				o = line.getField(sel.field);
				
				ok =  o != null 
				   && (o.toString().equals(sel.value));
			
			}
			if (ok) {
				if (defaultRecord) {
					ret = RecordSelectionResult.DEFAULT;
				} else {
					ret = RecordSelectionResult.YES;
				}
			}
		}
		
		return ret;
	}

	private ArrayList<SelectionField> getFields() {
		
		if (flds == null) {
			flds = new ArrayList<SelectionField>(5);
		}
		
		return flds;
	}
	
	/**
	 * @return the defaultRecord
	 */
	public boolean isDefaultRecord() {
		return defaultRecord;
	}


	/**
	 * @param defaultRecord the defaultRecord to set
	 */
	public void setDefaultRecord(boolean defaultRecord) {
		this.defaultRecord = defaultRecord;
	}
	
	public static enum RecordSelectionResult {
		NO,
		DEFAULT,
		YES
	}

}
