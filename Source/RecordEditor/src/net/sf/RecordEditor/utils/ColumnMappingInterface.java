package net.sf.RecordEditor.utils;

public interface ColumnMappingInterface {

	/**
	 * Converts the supplied (ie column viewed by the user) to the real
	 * column in the line
	 *
	 * @param layoutIndex layout to be used
	 * @param inColumn input column
	 *
	 * @return column of the field in the line
	 */
	public abstract int getRealColumn(int layoutIndex, int inColumn);

}