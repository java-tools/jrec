package net.sf.RecordEditor.re.file;

/**
 * Class to remap Fields
 * @author Bruce Martin
 *
 */
public class FieldMapping {
	private int[][] columnMapping = null;

	private int[] colLengths;
	private int[] totalColumnLengths;


	/**
	 * Setup column Mapping
	 * @param columnLengths the field counts by layout
	 */
	public FieldMapping(final int[] columnLengths) {
		colLengths = columnLengths;
		totalColumnLengths = columnLengths.clone();
	}


	/**
	 * Set up column mapping
	 * @param remapColumns column mapping
	 */
	public FieldMapping(int[][] remapColumns, int[] numCols) {
		columnMapping = remapColumns;
		totalColumnLengths = numCols;

		if (columnMapping == null) {
			colLengths = new int[0];
		} else {
			colLengths = new int[remapColumns.length];
			for (int i = 0; i < colLengths.length; i++) {
				colLengths[i] = 0;
				if (columnMapping[i] != null) {
					colLengths[i] = columnMapping[i].length;
				}
			}
		}
	}

	public void resetMapping(final int[] columnLengths) {
		colLengths = columnLengths;
		columnMapping = null;
	}

	/**
	 * Get the row count
	 * @param recordIdx record index
	 * @param defaultColumnCount  default row count
	 * @return row count
	 */
	public int getColumnCount(int recordIdx, int defaultColumnCount) {

		if (colLengths != null && recordIdx < colLengths.length) {
	    	 defaultColumnCount = colLengths[recordIdx];
   	    }
	    return defaultColumnCount;
	}

	/**
	 * Remap the row (taking into account any hidden rows)
	 * @param recordIdx record layout index
	 * @param inRow initial row
	 */
	public final int getRealColumn(int recordIdx, int inRow) {
	    int ret = inRow;

	    if (inRow >= 0
	    &&  columnMapping != null
	    &&  columnMapping[recordIdx] != null
	    &&  inRow < columnMapping[recordIdx].length) {
	        ret = columnMapping[recordIdx][inRow];
	    }

	    return ret;
	}

	public final int getAdjColumn(int recordIdx, int inRow) {
	    int ret = inRow;

	    if (inRow >= 0
	    &&  columnMapping != null
	    &&  columnMapping[recordIdx] != null
	    &&  columnMapping[recordIdx].length > 0) {
	        ret = 0;
	        while (ret < columnMapping[recordIdx].length && columnMapping[recordIdx][ret] < inRow) {
	        	ret += 1;
	        }
	    }

	    return ret;
	}

	/**
	 * Hide a row from view
	 * @param recordIdx record index
	 * @param row row to be hiddeb
	 */
	public final void hideColumn(int recordIdx, int row) {

		int[] cols = getRemapCols(recordIdx);
		boolean found = false;

		for (int i = 0; i < colLengths[recordIdx]; i++) {
			if (cols[i] == row) {
				found = true;
				colLengths[recordIdx] -= 1;
			}
			if (found && i < cols.length - 1) {
				cols[i] = cols[i+1];
			}
		}
	}

	/**
	 * Redisplay a row
	 * @param recordIdx record index
	 * @param row
	 */
	public final void showColumn(int recordIdx, int row) {
		int[] cols = getRemapCols(recordIdx);
		boolean searching = true;

		for (int i = 0; cols[i]  <= row && i < colLengths[recordIdx]; i++) {
			if (cols[i] == row) {
				searching = false;
				break;
			}
		}

		if (searching) {
			cols[colLengths[recordIdx]] = row;

			for (int i = colLengths[recordIdx] - 1; i >= 0 && cols[i] > row; i--) {
				cols[i+1] = cols[i];
				cols[i] = row;
			}
			colLengths[recordIdx] += 1;
		}
	}


	private int[] getRemapCols(int idx) {

		if (columnMapping == null) {
			columnMapping = new int[colLengths.length][];
		}

		if (columnMapping[idx] == null) {
			columnMapping[idx] = new int[colLengths[idx]];

			for (int i =0; i < colLengths[idx]; i++) {
				columnMapping[idx][i] = i;
			}
		}

		return columnMapping[idx];
	}

	public boolean[] getFieldVisibility(int recordIdx) {
		boolean[] ret = null;

		if (colLengths != null && recordIdx < colLengths.length) {
			int size = colLengths[recordIdx];
			if (totalColumnLengths != null && recordIdx < totalColumnLengths.length) {
				size = totalColumnLengths[recordIdx];
			}

			if (columnMapping == null || columnMapping[recordIdx] ==null) {
				ret = initArray(size, true);
			} else {
				if ( columnMapping[recordIdx].length > size) {
					size = columnMapping[recordIdx].length ;
				}
				ret = initArray(size, false);
				for (int i = 0; i < colLengths[recordIdx]; i++) {
					ret[columnMapping[recordIdx][i]] = true;
				}
			}
		}

		return ret;
	}

	private boolean[] initArray(int size, boolean value) {
		boolean[] array = new boolean[size];
		for (int i = 0; i < size; i++) {
			array[i] = value;
		}
		return array;
	}

	public void setFieldVisibilty(int recordIndex, boolean[] fieldVisible) {
		if (fieldVisible == null) return;
		boolean allFields = true;

		for (int i = 0; i < fieldVisible.length; i++) {
			if (! fieldVisible[i]) {
				allFields = false;
				break;
			}
		}

		if (allFields) {
			if (columnMapping != null && columnMapping[recordIndex] != null) {
				columnMapping[recordIndex] = null;
				colLengths[recordIndex] = fieldVisible.length;
			}
		} else {
			int count = 0;
			if (columnMapping == null) {
				columnMapping = new int[colLengths.length][];
			}

			if (columnMapping[recordIndex] == null) {
				columnMapping[recordIndex] = new int[Math.max(fieldVisible.length, colLengths[recordIndex])];
			}

			for (int i = 0; i < fieldVisible.length; i++) {
				if (fieldVisible[i]) {
					columnMapping[recordIndex][count++] = i;
				}
			}
			colLengths[recordIndex] = count;
		}
	}

	public static int getAdjColumn(FieldMapping mapping, int recordIdx, int inRow) {
		if (mapping != null) {
			return mapping.getAdjColumn(recordIdx, inRow);
		}
		return inRow;
	}

}
