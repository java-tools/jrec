package net.sf.RecordEditor.edit.file.storage;

public class ByteArray {
	public final int size;
	public final byte[] bytes;
	public final boolean compressed;
	
	public ByteArray(int size, byte[] bytes, boolean comp) {
		super();
		this.size = size;
		this.bytes = bytes;
		this.compressed = comp;
	}
}
