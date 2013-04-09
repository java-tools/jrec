package net.sf.RecordEditor.edit.display.util;

import net.sf.JRecord.Details.AbstractLine;

public class LinePosition {

	public final AbstractLine line;
	public final boolean before;


	public LinePosition(AbstractLine line, boolean prev) {
		super();
		this.line = line;
		this.before = prev;
	}
}
