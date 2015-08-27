package net.sf.JRecord.Details;

import net.sf.JRecord.Common.IBasicFileSchema;

public interface IBasicLayout extends IBasicFileSchema {

	public abstract boolean isBinCSV();

	/**
	 * Get the record Seperator bytes
	 *
	 * @return Record Seperator
	 */
	public abstract byte[] getRecordSep();

	
	/**
	 * get the field delimiter
	 * @return the field delimeter
	 */
	public abstract byte[] getDelimiterBytes();

}
