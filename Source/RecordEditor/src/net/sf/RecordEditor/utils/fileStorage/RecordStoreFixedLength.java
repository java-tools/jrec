package net.sf.RecordEditor.utils.fileStorage;


public class RecordStoreFixedLength extends RecordStoreBase<RecordStoreFixedLength> {

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

//	@Override
//	public void add(byte[] rec) {
//		int pos = recordCount * len;
//		
//		System.arraycopy(rec, 0, store, pos, Math.min(len, rec.length));
//		recordCount += 1;
//	}
	


	@Override
	public void put(int idx, byte[] rec) {
		int pos = idx * len;
		
		System.arraycopy(rec, 0, store, pos, Math.min(len, rec.length));
//		for (int i = rec.length; i < len; i++) {
//			store[pos + i] = 0;
//		}
	}

	@Override
	protected LineDtls getPosLen(int idx, int newLen) {
		return new LineDtls(idx * len, len, len, idx);
	}
	
	public int getSize() {
		return super.recordCount * len;
	}


	@Override
	public RecordStoreFixedLength[] split(int blockSize) {
		int c = (blockSize - 1) / len + 1;
		RecordStoreFixedLength[] ret = new RecordStoreFixedLength[(recordCount - 1) / c + 1];
		int i, l;
		int bsize = c * len;

		for (i = 0; i < ret.length - 1; i++) {
			ret[i] = new RecordStoreFixedLength(bsize, len, c);
			System.arraycopy(store, i * bsize, ret[i].store, 0, bsize);
			ret[i].size = bsize;
//			System.out.println(" --> " + i + " " + (i * c)
//					+ " " + ret[i].recordCount
//					+ " " + bsize);
		}

		l = len * (recordCount - c * (ret.length - 1));
		i = ret.length - 1;
		ret[i] = new RecordStoreFixedLength(bsize, len, recordCount - c * i);
		System.arraycopy(store, bsize * i, ret[i].store, 0, l);
		ret[i].size = l;
//		System.out.println(" --> " + (ret.length - 1)
//				+ " " + ((ret.length - 1) * c)
//				+ " " + ret[ret.length - 1].recordCount
//				+ " " + l);
		
		return ret;
	}
	
	
}
