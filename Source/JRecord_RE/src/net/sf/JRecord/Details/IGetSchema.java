package net.sf.JRecord.Details;

public interface IGetSchema {
//	public static final int ST_TEXT_CSV   = 1;
//	public static final int ST_CSV        = 2;
//	public static final int ST_TEXT_FIXED = 3;
//	public static final int ST_FIXED      = 4;
//	public static final int ST_TEXT_CSV_FIXED = 5;
//	public static final int ST_ANY        = 6;

	public abstract AbstractLayoutDetails getSchema();
	public abstract boolean schemaAvailable4checking();
}
