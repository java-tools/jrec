package net.sf.RecordEditor.re.tree;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.jibx.compare.SortSummary;

public class FieldSummaryDetails {
	
	public static final int OP_NONE = 0;
	public static final int OP_SUM  = 1;
	public static final int OP_MAX  = 3;
	public static final int OP_MIN  = 2;
	public static final int OP_AVE  = 4;
	
	public static final String[] OPERATOR_NAMES = new String[5];
	static {
		OPERATOR_NAMES[FieldSummaryDetails.OP_NONE] = "";
		OPERATOR_NAMES[FieldSummaryDetails.OP_SUM] = "Sum";
		OPERATOR_NAMES[FieldSummaryDetails.OP_MIN] = "Minimum";
		OPERATOR_NAMES[FieldSummaryDetails.OP_MAX] = "Maximum";
		OPERATOR_NAMES[FieldSummaryDetails.OP_AVE] = "Average";
	};
	

	private AbstractLayoutDetails layout;
	private int recordIndex = 0;
	private int[] operator;
	
	public FieldSummaryDetails(AbstractLayoutDetails recordLayout) {
		int max = 1;
		layout = recordLayout;
		for (int i = 0; i < layout.getRecordCount(); i++) {
			max = Math.max(max, layout.getRecord(i).getFieldCount());
		}
		
		operator = new int[max];
		setOperator(OP_NONE);
	}

	/**
	 * Get field type
	 * @param idx field index
	 * @return type
	 */
	public final int getType(int idx) {
		return layout.getRecord(recordIndex).getField(idx).getType();
	}

	/**
	 * get Field name
	 * @param idx field index
	 * @return field name
	 */
	public final String getFieldName(int idx) {
		return layout.getRecord(recordIndex).getField(idx).getName();
	}

	/**
	 * Get operator
	 * @param idx field index
	 * @return
	 */
	public final int getOperator(int idx) {
		return operator[idx];
	}

	public final void setOperator(int idx, int newOption) {
		operator[idx] = newOption;
	}
	
	public final int getFieldCount() {
		return layout.getRecord(recordIndex).getFieldCount();
	}
	
	public final int getRecordIndex() {
		return recordIndex;
	}

	public final void setRecordIndex(int layoutIndex) {
		if (this.recordIndex != layoutIndex) {
			setOperator(OP_NONE);
			this.recordIndex = layoutIndex;
		}
	}
	
	public final void setOperator(int op) {
		
		for (int i = 0; i < operator.length; i++) {
			operator[i] = OP_NONE;
		}
	}

	/**
	 * get summary details
	 * @return summary details
	 */
	public final SortSummary[] getSummary() {
		SortSummary[] ret;
		int i, j, size;
		
		size = 0;
		for (i = 0; i < operator.length; i++) {
			if (operator[i] != OP_NONE) {
				size += 1;
			}
		}
		
		ret = new SortSummary[size];
		
		j= 0;
		for (i = 0; i < operator.length; i++) {
			if (operator[i] != OP_NONE) {
				ret[j] = new net.sf.RecordEditor.jibx.compare.SortSummary();
				ret[j].fieldName = getFieldName(i);
				ret[j++].operator  = OPERATOR_NAMES[getOperator(i)];
			}
		}
		
		return ret;
	}

	/**
	 * set summary details
	 * @param recordIdx record Index
	 * @param summary summary details
	 */
	public final void setSummary(int recordIdx, SortSummary[] summary) {
		int i, j, idx, op;		

		if (summary != null) {
			for (i = 0; i < summary.length; i++) {
				idx = layout.getRecord(recordIdx).getFieldIndex(summary[i].fieldName);
				
				if (idx >= 0) {
					op = OP_NONE;
					for (j = 0; j < OPERATOR_NAMES.length; j++) {
						if (OPERATOR_NAMES[j].equalsIgnoreCase(summary[i].operator)) {
							op = j;
							break;
						}
					}
	
					operator[idx] = op;
				}
			}
		}
	}

}
