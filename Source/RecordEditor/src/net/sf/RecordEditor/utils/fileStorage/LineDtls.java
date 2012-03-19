package net.sf.RecordEditor.utils.fileStorage;

public class LineDtls {
	public final int pos, len, newLength, index;

	public LineDtls(int pos, int len, int newLen, int idx) {
		super();
		this.index = idx;
		this.pos = pos;
		this.len = len;
		this.newLength = newLen;
	}
}
