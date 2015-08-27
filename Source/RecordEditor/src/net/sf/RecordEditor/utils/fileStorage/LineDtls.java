package net.sf.RecordEditor.utils.fileStorage;

public class LineDtls {
	public final int pos, len, newLength, lineNumber, positionInLine, textPos;

//	public LineDtls(int pos, int len, int newLen, int idx) {
//		this(pos, len, newLen, idx, 0);
//	}
	
		
	public LineDtls(int pos, int len, int newLen, int lineNumber, int positionInLine, int recordOverhead, boolean lengthAtStartOfLine) {
		super();
		int p = pos;
		if (lengthAtStartOfLine) {
			p += recordOverhead;
		}
		this.lineNumber = lineNumber;
		this.pos = p;
		this.len = len;
		this.newLength = newLen;
		this.positionInLine = positionInLine;
		this.textPos = pos + lineNumber * (1 - recordOverhead);
	}
}
