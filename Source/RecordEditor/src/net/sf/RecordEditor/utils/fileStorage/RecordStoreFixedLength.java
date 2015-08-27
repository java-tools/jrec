package net.sf.RecordEditor.utils.fileStorage;

import net.sf.RecordEditor.utils.common.Common;


public class RecordStoreFixedLength extends RecordStoreBase {

	private int len;
	
	
	public RecordStoreFixedLength(int size, int recLen, int recs) {
		super(recs, 0);
		
		if (size >= 0 && recLen > 0) {
			int num = size / recLen + 1;
			this.store = new byte[num * recLen];
		} else {
			this.store = new byte[0];
		}
		this.recordCount = recs;
		this.len = recLen;
	}

	
	@Override
	protected void put(LineDtls pos, byte[] rec) {
		put(pos.lineNumber, rec);
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
		for (int i = rec.length; i < len; i++) {
			store[pos + i] = 0;
		}
	}

	@Override
	protected LineDtls getLinePositionLength(int idx, int newLen) {
		return new LineDtls(idx * len, len, len, idx, 0, 0, false);
	}
	
	public int getSize() {
		return super.recordCount * len;
	}


	@Override
	public RecordStoreFixedLength[] split(int blockSize) {
		int c = (4 * (blockSize - 1)) / (5 * len) + 1;
		RecordStoreFixedLength[] ret = new RecordStoreFixedLength[(recordCount - 1) / c + 1];
		int i, l;
		int bsize = c * len;

		for (i = 0; i < ret.length - 1; i++) {
			ret[i] = allocateStore(bsize, bsize * i, c, bsize);
//			ret[i] = new RecordStoreFixedLength(bsize, len, c);
//			System.arraycopy(store, i * bsize, ret[i].store, 0, bsize);
//			ret[i].size = bsize;
//			System.out.println(" --> " + i + " " + (i * c)
//					+ " " + ret[i].recordCount
//					+ " " + bsize);
		}

		l = len * (recordCount - c * (ret.length - 1));
		i = ret.length - 1;
//		ret[i] = new RecordStoreFixedLength(bsize, len, recordCount - c * i);
//		System.arraycopy(store, bsize * i, ret[i].store, 0, l);
//		ret[i].size = l;
//		System.out.println(" --> " + (ret.length - 1)
//				+ " " + ((ret.length - 1) * c)
//				+ " " + ret[ret.length - 1].recordCount
//				+ " " + l);
		ret[i] = allocateStore(bsize, bsize * i, recordCount - c * i, l);
		return ret;
	}
	
	public RecordStoreFixedLength[] splitAt(int recNumber) {
//		int c = (4 * (blockSize - 1)) / (5 * len) + 1;
		RecordStoreFixedLength[] ret = new RecordStoreFixedLength[2];
		//int count1stBlock = recNum - 1;
		int cpyLen0 = recNumber * len;
		int cpyLen1 = (recordCount - recNumber) * len;
		int defaultBlkSize = Common.OPTIONS.chunkSize.get();
		
		ret[0] = allocateStore(Math.max(defaultBlkSize, cpyLen0), 0,       recNumber,               cpyLen0);
		ret[1] = allocateStore(Math.max(defaultBlkSize, cpyLen1), cpyLen0, recordCount - recNumber, cpyLen1);
		
		return ret;
	}



	private RecordStoreFixedLength allocateStore(int bsize, int start, int recCount, int cpyLen) {

		RecordStoreFixedLength ret = new RecordStoreFixedLength(bsize, len, recCount);

		if (cpyLen >= 0) {
//			System.out.println("==> " + start + ", " + cpyLen + ", " + recCount + " | " + store.length + " " + recordCount);
			System.arraycopy(store, start, ret.store, 0, cpyLen);
		}
		ret.setSize(cpyLen);
		
		return ret;
	}
	
}
