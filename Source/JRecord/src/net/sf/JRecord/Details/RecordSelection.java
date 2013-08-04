package net.sf.JRecord.Details;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.RecordSel;


/**
 * Used to check if the line is a specific Record.
 *
 * @author Bruce Martin
 *
 */
public class RecordSelection {

	private RecordSel recSel;

	//private final RecordDetail parent;
	private boolean defaultRecord = false;


	public RecordSelection(RecordDetail parent) {
		super();
		//this.parent = parent;
	}


//	/**
//	 * @param index
//	 * @return
//	 * @see java.util.ArrayList#get(int)
//	 */
//	public SelectionField get(int index) {
//		return flds.get(index);
//	}

	/**
	 * @return
	 * @see java.util.ArrayList#size()
	 */
	public int size() {
		if (recSel == null) {
			return 0;
		}
		return recSel.getSize();
	}


	/**
	 * @return
	 * @see net.sf.JRecord.ExternalRecordSelection.ExternalSelection#getElementCount()
	 */
	public int getElementCount() {
		if (recSel == null) {
			return 0;
		}
		return recSel.getElementCount();
	}


	public FieldSelect getFirstField() {
		return recSel.getFirstField();
	}



	public RecordSelectionResult isSelected(AbstractLine line) {
		RecordSelectionResult ret = RecordSelectionResult.NO;

		if (recSel != null) {

			if (recSel.isSelected(line)) {
				if (defaultRecord) {
					ret = RecordSelectionResult.DEFAULT;
				} else {
					ret = RecordSelectionResult.YES;
				}
			}
		}

		return ret;
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

	/**
	 * @param recSel the recSel to set
	 */
	public void setRecSel(RecordSel recSel) {
		this.recSel = recSel;
	}

	public List<FieldSelect> getAllFields() {
		List<FieldSelect> fields = new ArrayList<FieldSelect>();

		if (recSel != null) {
			recSel.getAllFields(fields);
		}

		return fields;
	}

	public static enum RecordSelectionResult {
		NO,
		DEFAULT,
		YES
	}



}
