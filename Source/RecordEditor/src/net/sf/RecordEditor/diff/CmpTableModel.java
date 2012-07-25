package net.sf.RecordEditor.diff;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public class CmpTableModel extends AbstractTableModel {

	public static final int BEFORE = 0;
	public static final int AFTER  = 1;

	private ArrayList<LineCompare>[] displayRows;
	private AbstractLayoutDetails<?, ?> description;

	private int colCount = 0;

	private int recordIndex = 0;

	private int additionalFields = 3;
	private int keyIdx = -1;




	@SuppressWarnings("unchecked")
	public CmpTableModel(@SuppressWarnings("rawtypes") AbstractLayoutDetails layout,
//						  ArrayList<LineCompare> before,    ArrayList<LineCompare> after,
						  ArrayList<LineCompare> displayBefore, ArrayList<LineCompare> displayAfter) {

		displayRows = new ArrayList[2];

		description = layout;

		displayRows[BEFORE] = displayBefore;
		displayRows[AFTER]  = displayAfter;

//		System.out.println("Changed: " + displayBefore.size() + " " + displayAfter.size());



		for (int i = 0; i < description.getRecordCount(); i++) {
			colCount = Math.max(colCount, description.getRecord(i).getFieldCount());
		}

		if (layout.isMapPresent()) {
			keyIdx = additionalFields;
			additionalFields += 1;
		}
		colCount += additionalFields;
	}


	@Override
	public int getColumnCount() {
		if (recordIndex >= description.getRecordCount()) {
			return colCount;
		}

		return description.getRecord(recordIndex).getFieldCount() + additionalFields;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return displayRows[0].size() * 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		int row = rowIndex / 2;
		int idx = rowIndex - row * 2;

		if (row >=  displayRows[idx].size()) {
			return "";
		}
		LineCompare cmp = displayRows[idx].get(row);
		if (columnIndex == 0 || cmp == null) {
			return "";
		} else if (columnIndex == 1 && idx == 0) {
			if (cmp.code == LineCompare.DELETED) {
				return "Deleted";
			}
			return "Old";

		} else if (columnIndex == 1 && idx == 1) {
			if (cmp.code == LineCompare.INSERT) {
				return "Inserted";
			}
			return "New";

		} else  if (columnIndex == 2) {
			return Integer.valueOf(cmp.lineNo);
		} else {
			LineCompare before = displayRows[0].get(row);
			//int pref = cmp.line.getPreferredLayoutIdx();
			int lineIdx = columnIndex - additionalFields;
			int r = recordIndex;

			if (columnIndex == keyIdx) {
				lineIdx = Constants.KEY_INDEX;
			}
			if (r >= description.getRecordCount()) {
				if (idx == 1 && before != null) {
					r = before.line.getPreferredLayoutIdx();
				} else {
					r = cmp.line.getPreferredLayoutIdx();
				}
			}


			if (idx == 1 && before != null) {
				if (lineIdx >= before.line.getLayout().getRecord(r).getFieldCount()
				||	Common.trimRight(before.line.getField(r, lineIdx))
						.equals(Common.trimRight(cmp.line.getField(r, lineIdx)))) {
					return "";
				}
			}

			if (lineIdx >= description.getRecord(r).getFieldCount()) {
				return "";
			}

			return cmp.line.getField(r, lineIdx);
		}
	}

	@Override
	public String getColumnName(int column) {

		String ret = "";
		switch (column) {
		case 0:
		case 1:
			break;
		case 2:
			ret = LangConversion.convert(LangConversion.ST_COLUMN_HEADING, "Line No");
			break;

		default:
			if (column == keyIdx) {
				ret = LangConversion.convert(LangConversion.ST_COLUMN_HEADING, "Key Field");
			} else {
				int r = recordIndex;
				if (r >= description.getRecordCount()) {
					r = 0;
				}
				if (column - additionalFields < description.getRecord(r).getFieldCount()) {
					ret = description.getRecord(r).getField(column - additionalFields).getName();
				}
			}
			break;
		}
		return ret;
	}

	public void setDisplayRows(ArrayList<LineCompare> displayBefore, ArrayList<LineCompare> displayAfter) {

		displayRows[BEFORE] = displayBefore;
		displayRows[AFTER]  = displayAfter;

		this.fireTableDataChanged();
	}

	/**
	 * @return the recordIdx
	 */
	protected int getRecordIndex() {
		return recordIndex;
	}

	/**
	 * @param recordIdx the recordIdx to set
	 */
	protected boolean setRecordIndex(int recordIdx) {
		int idx = Math.min(recordIdx, description.getRecordCount());

		if (idx >= 0 && recordIndex != idx) {
			this.recordIndex = idx;
			this.fireTableStructureChanged();
			return true;
		}
		return false;
	}

}
