package net.sf.JRecord.Details;

public class Options {

	public static final int OPT_SELECTION_PRESENT = 1;

	public static final int NO = 0;
	public static final int UNKNOWN = 1;
	public static final int YES = 2;


	public static int getValue(boolean ok) {
		if (ok) {
			return YES;
		}
		return NO;
	}
}
