package net.sf.JRecord.Details;

public interface IGetSchema {
//	public static final int ST_TEXT_CSV   = 1;
//	public static final int ST_CSV        = 2;
//	public static final int ST_TEXT_FIXED = 3;
//	public static final int ST_FIXED      = 4;
//	public static final int ST_TEXT_CSV_FIXED = 5;
//	public static final int ST_ANY        = 6;
	
	public static final int ST_NO_CHECK_ON_SCHEMA = 1;
	public static final int ST_CHECK_SCHEMA= 2;
	public static final int ST_OTHER_SCHEMA= 3;
	
	public static final int ST_CSV         = 11;
	public static final int ST_TEXT_CSV    = 12;

	public static final int ST_FIXED       = 14;
//	public static final int ST_BIN_FIXED   = 15;


	public abstract AbstractLayoutDetails getSchema();
	public abstract int schemaChecking();
}
