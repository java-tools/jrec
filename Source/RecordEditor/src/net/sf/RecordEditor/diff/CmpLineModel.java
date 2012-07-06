package net.sf.RecordEditor.diff;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public class CmpLineModel extends AbstractTableModel {

	public static final int BEFORE = 0;
	public static final int AFTER  = 1;


	private static final String[] columnNames = LangConversion.convertColHeading(
			"Compare_Line",
			new String[] {"Field", "Position", "Length", "Old", "New"});

	private ArrayList<LineCompare>[] displayRows;
	private AbstractLayoutDetails<?, ?> description;
	private int[] changedFields = null;

	private static final int COLUMN_COUNT = 5;

	private int recordIdx = 0;
	private int currentRow = 0;
	private boolean displayChangedFields = false;



	@SuppressWarnings("unchecked")
	public CmpLineModel(AbstractLayoutDetails<?, ?> layout,
						  ArrayList<LineCompare> displayBefore, ArrayList<LineCompare> displayAfter) {

		displayRows = new ArrayList[2];

		description = layout;

		displayRows[BEFORE] = displayBefore;
		displayRows[AFTER]  = displayAfter;

		//System.out.println("Changed: " + displayBefore.size() + " " + displayAfter.size());
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public int getRowCount() {
		int inc = 1;
		if (description.isMapPresent()) {
			inc = 2;
		}
		if (isAllFields()) {
			return description.getRecord(recordIdx).getFieldCount() + inc;
		}
		return getChangedFields().length + inc;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object ret = null;
		int idx = columnIndex - 3;
		int id = getAdjustedRow(rowIndex);

		if (columnIndex < 3) {
			if (rowIndex == 0) {
				if (columnIndex == 0) {
					ret = "Line Number";
				}
			} else if (rowIndex == 1 && description.isMapPresent()) {
				if (columnIndex == 0) {
					ret = "Key Value";
				}
			} else {
				FieldDetail fld = description.getRecord(recordIdx).getField(id - 1);
				if (columnIndex == 0) {
					ret = fld.getName();
				} else if (columnIndex == 1) {
					ret = Integer.valueOf(fld.getPos());
				} else if (fld.getLen() < 0){
					ret = "";
				} else {
					ret = Integer.valueOf(fld.getLen());
				}

			}
		} else if (currentRow < displayRows[idx].size()) {
			ret = standardFields(id, idx);
		}

		return ret;
	}

	private Object standardFields(int rowIndex, int idx) {
		Object ret = null;
		LineCompare cmp = displayRows[idx].get(currentRow);

		if (cmp == null) {
			ret = "";
		} else  if (rowIndex == 0) {
			ret = Integer.valueOf(cmp.lineNo);
		} else {
			LineCompare before = displayRows[BEFORE].get(currentRow);
			int lineIdx = rowIndex - 1 ;

			if (idx == 1 && before != null) {
				if (Common.trimRight(before.line.getField(recordIdx, lineIdx))
						.equals(Common.trimRight(cmp.line.getField(recordIdx, lineIdx)))) {
					return  "";
				}
			}

			if (lineIdx >= description.getRecord(recordIdx).getFieldCount()) {
				return "";
			}

			ret = cmp.line.getField(recordIdx, lineIdx);
		}
		return ret;
	}


	/**
	 *
	 * @param rowIndex
	 * @return
	 */
	private int getAdjustedRow(int rowIndex) {

		int id = rowIndex;

		if (description.isMapPresent() && rowIndex > 0) {
			if (rowIndex == 1) {
				return Constants.KEY_INDEX + 1;
			}

			id -= 1;
		}

		if ( displayChangedFields && id > 0 && getChangedFields().length >= id
		&& ! isAllFields()) {
			id = getChangedFields()[id - 1];
		}

		return id;
	}


	/**
	 * check if a particular row has changed
	 * @param rowIdx row to check
	 * @return wether it has changed
	 */
	public final boolean isFieldChanged(int rowIdx) {
		boolean ret = false;
		int row = getAdjustedRow(rowIdx);

		if (row > 0 && currentRow <= displayRows[0].size()) {
			LineCompare before = displayRows[0].get(currentRow);
			LineCompare after  = displayRows[1].get(currentRow);
			int lineIdx = row - 1 ;

			if (before != null && before.line != null
			&& before.code == LineCompare.CHANGED
			&& after != null && after.line != null) {
				if (! Common.trimRight(before.line.getField(recordIdx, lineIdx))
						.equals(Common.trimRight(after.line.getField(recordIdx, lineIdx)))) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	public final void setDisplayRows(ArrayList<LineCompare> displayBefore, ArrayList<LineCompare> displayAfter) {

		displayRows[BEFORE] = displayBefore;
		displayRows[AFTER]  = displayAfter;

		this.fireTableDataChanged();
	}
	/**
	 * @return the recordIdx
	 */
	protected final int getRecordIdx() {
		return recordIdx;
	}

	/**
	 * @param recordIdx the recordIdx to set
	 */
	protected final boolean setRecordIdx(int recordIndex) {
		int idx = recordIndex;

		if (idx >= description.getRecordCount()) {
			LineCompare cmp = this.displayRows[BEFORE].get(currentRow);
			if (cmp == null) {
				idx = this.displayRows[AFTER].get(currentRow).line.getPreferredLayoutIdx();
			} else {
				idx = cmp.line.getPreferredLayoutIdx();
			}
		}

		if (idx >= 0 && recordIdx != idx) {
			changedFields = null;
			this.recordIdx = idx;
			this.fireTableDataChanged();
			return true;
		}
		return false;
	}

	/**
	 * @return the currentRow
	 */
	protected final int getCurrentRow() {
		return currentRow;
	}

	/**
	 * Get the BEFORE compare line detail
	 * @return compare line details
	 */
	public final LineCompare getCurrentCompareLine() {
//		if (displayRows[BEFORE].get(currentRow) != null
//		&&  (displayRows[AFTER].get(currentRow) == null
//		  || displayRows[AFTER].get(currentRow).line == null)) {
//			displayRows[BEFORE].get(currentRow).code = LineCompare.DELETED;
//		}
		return displayRows[BEFORE].get(currentRow);
	}

	/**
	 * @param currentRow the currentRow to set
	 */
	protected final void setCurrentRow(int newRow) {
		if (newRow >= 0 && currentRow != newRow) {
			this.currentRow = newRow;

			changedFields = null;

			if (! setRecordIdx(description.getRecordCount())) {
				this.fireTableDataChanged();
			}
		}
	}

	private int[] getChangedFields() {

		if (changedFields == null) {
			int size = 0;
			int i, j;
			LineCompare before = displayRows[BEFORE].get(currentRow);
			LineCompare after  = displayRows[AFTER].get(currentRow);

			if (before == null || after == null) {
				changedFields = new int[0];
				return changedFields;
			}

			for (i = 0; i < description.getRecord(recordIdx).getFieldCount(); i++) {
				if (! Common.trimRight(before.line.getField(recordIdx, i))
						.equals(Common.trimRight(after .line.getField(recordIdx, i)))
				) {
					size += 1;
				}
			}

			changedFields = new int[size];
			j = 0;
			for (i = 0; i < description.getRecord(recordIdx).getFieldCount(); i++) {
				if (! Common.trimRight(before.line.getField(recordIdx, i))
						.equals(Common.trimRight(after .line.getField(recordIdx, i)))
				) {
					changedFields[j++] = i + 1;
				}
			}
		}
		return changedFields;
	}

	/**
	 * show all fields
	 * @return wether to show all fields
	 */
	private boolean isAllFields() {

		if (! displayChangedFields) {
			return true;
		}

		LineCompare before = displayRows[BEFORE].get(currentRow);

		return (before == null || before.code == LineCompare.DELETED
			||  displayRows[AFTER].get(currentRow) == null);
	}
	/**
	 * @param onlyChangedFields the displayChangedFields to set
	 */
	public final void setDisplayChangedFields(boolean onlyChangedFields) {

		this.displayChangedFields = onlyChangedFields;
		fireTableDataChanged();
	}

}
