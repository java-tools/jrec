package net.sf.RecordEditor.edit.file.storage;


public class RecordStoreFixedLength extends RecordStoreBase {

	private int len;
	
	
	public RecordStoreFixedLength(int size, int recLen, int recs) {
		super(recs, 0);
		
		if (size >= 0) {
			int num = size / recLen + 1;
			this.store = new byte[num * recLen];
		}
		this.recordCount = recs;
		this.len = recLen;
	}

	
	@Override
	protected void put(LineDtls pos, byte[] rec) {
		put(pos.index, rec);
	}


	@Override
	public void put(int idx, byte[] rec) {
		int pos = idx * len;
		
		System.arraycopy(rec, 0, store, pos, Math.min(len, rec.length));
		for (int i = rec.length; i < len; i++) {
			store[pos + i] = 0;
		}
	}

	@Override
	protected LineDtls getPosLen(int idx, int newLen) {
		return new LineDtls(idx * len, len, len, idx);
	}
	
	public int getSize() {
		return super.recordCount * len;
	}
}
