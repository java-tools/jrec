package net.sf.RecordEditor.diff;

import net.sf.JRecord.Details.AbstractLine;

public class LineCompare {

	public static final int UNCHANGED = 0;
	public static final int INSERT  = 1;
	public static final int CHANGED = 2;
	public static final int DELETED = 3;
	
	protected final AbstractLine line;
		
	protected int lineNo;
	protected int code;
	
	public LineCompare(int lineCode, int lineNumber, AbstractLine actualLine) {
		line   = actualLine;
		lineNo = lineNumber;
		code   = lineCode;
	}
}
