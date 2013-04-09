package net.sf.JRecord.Details;

public class Options {

	public static final int OPT_SELECTION_PRESENT = 1;
	public static final int OPT_CHECK_LAYOUT_OK   = 2;
	public static final int OPT_STORAGE_TYPE   = 3;
	public static final int OPT_CHECK_4_STANDARD_FILE_STRUCTURES   = 4;
	public static final int OPT_CHECK_4_STANDARD_FILE_STRUCTURES2  = 5;

	public static final int NO = 0;
	public static final int UNKNOWN = Integer.MIN_VALUE;
	public static final int YES = 1;

	public static final int BINARY_STORAGE = 3;
	public static final int TEXT_STORAGE   = 4;
	public static final int OTHER_STORAGE   = 4;

	public static int getValue(boolean ok) {
		if (ok) {
			return YES;
		}
		return NO;
	}
}
