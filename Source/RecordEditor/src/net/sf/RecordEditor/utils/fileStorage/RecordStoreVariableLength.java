package net.sf.RecordEditor.utils.fileStorage;

import net.sf.RecordEditor.utils.common.Common;


public class RecordStoreVariableLength extends RecordStoreBase implements ISimpleRecStore, IDocumentRecordStore {
	
//	private static final int INDEX_RECORD = 8;

	private int maxLen;
/*	private int[] recIndex = null;
	private int recIndexValidTo = -1;
	private int lastLookUpPosition = -11;
	private int lastLookUpBlockIndex = -1;*/
	
	protected final RecordIndexMgr ri;


	public RecordStoreVariableLength(int length, int recLen, int recordOverhead, int recs) {
		super(recs, recordOverhead);
		
		ri = new RecordIndexMgr(this, recLen, recordOverhead, true);
		if (length >= 0) {
			this.store = new byte[length];
		}

		this.maxLen = Math.max(1, recLen);
	}


	@Override
	public void add(int idx, byte[] rec) {
		byte[] tRec = rec;
		if (rec.length > ri.maxRecordLength) {
			tRec = new byte[ri.maxRecordLength]; 
			System.arraycopy(rec, 0, tRec, 0, ri.maxRecordLength);
		}
		super.add(idx, tRec);
		size += rec.length + super.recordOverhead;
//		System.out.println("       Add: " + this.hashCode()
//				+ " > " + idx + " " + super.recordCount
//				+ " > " + rec.length + " " + size);
		ri.checkRecordIndex(idx);
	}
	
	


	@Override
	public void add(int idx, IRecordStore rStore) {
		
		super.add(idx, rStore);
		ri.checkRecordIndex(idx);
	}
	


	@Override
	public byte[] getCompressed() {
		byte[] b = super.getCompressed();
		if (store == null) {
			ri.clear();
		}
		return b;
	}


	@Override
	public int remove(int idx) {
		int len = super.remove(idx);
		size -= len ;
		ri.resetIndex(idx);
		
		return len;
	}


	@Override
	public void put(int idx, byte[] rec) {
		LineDtls p = getLinePositionLength(idx, rec.length);
		int len = Math.min(rec.length, ri.maxRecordLength);
	
		
		if (idx >= recordCount) {
			size += len + super.recordOverhead;
//			System.out.print(" +" + idx);
		} else if (len != p.len) {
			super.moveData(p.pos + p.len, p.pos + len);
			
			ri.adjRecordIndex(idx, len - p.len);
			
			size += len - p.len;
		} 
//		System.out.println(" put " + this.hashCode() + " " + size + " " 
//				+ rec.length);
		
		put(p, len, rec);
	}
	
	

	
//
//	@Override
//	public void add(byte[] rec) {
//		int p = size+lengthSize;
//		System.arraycopy(rec, 0, store, p, rec.length);
//
//		addLength(p, rec.length);
//		
//		size += rec.length+lengthSize;
//		recordCount += 1;
//	}
	
	@Override
	protected void put(LineDtls p, byte[] rec) { 
		put(p, rec.length, rec);
	}
	protected void put(LineDtls p, int len, byte[] rec) { 
//		System.out.println("^^ " + p.index + " -- " + p.pos + " " + p.len + " " + b.length
//				+ " " + store.length + " " + rec.length);
		System.arraycopy(rec, 0, store, p.pos, len);
		addLength(p.pos, len);
	}
	
	private void addLength(int pos, int length) {
		
		
		switch (super.recordOverhead) {
		case 4: store[pos-4] = (byte) ((length >>>  24) & 0xFF);
		case 3: store[pos-3] = (byte) ((length >>>  16) & 0xFF);
		case 2: store[pos-2] = (byte) ((length >>>  8) & 0xFF);
		case 1: store[pos-1] = (byte) ((length >>>  0) & 0xFF);
		}
//		byte[] b = BigInteger.valueOf(length).toByteArray();
//	
//		//System.out.println("^^ " + p.index + " -- " + p.pos + " " + p.len + " " + b.length);
//		store[pos-1] = b[b.length - 1];
//		
//		switch (super.lengthSize) {
//		case 3:
//			store[pos-3] = 0;
//			if (b.length > 2) {
//				store[pos-3] = b[b.length - 3];
//			}	
//		case 2:
//			store[pos-2] = 0;
//			if (b.length > 1) {
//				store[pos-2] = b[b.length - 2];
//			}
//		}	
	}

	

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public LineDtls getLinePositionLength(int idx, int newLen) {
		return ri.getLinePositionLength(idx, newLen, size);
	}
	
	@Override
	public final LineDtls getTextPosition(final int textPos) {
		return ri.getTextPosition(textPos, size);
	}

	
	public final int getLength(int p) {
		if (p+super.recordOverhead  > store.length) {
			return 0;
		}
		int r = (store[p]) & 0xFF;
		if (recordOverhead >= 2) {
			r = (r << 8) + ((store[p+1]) & 0xFF);
			if (recordOverhead > 2) {
				r = (r << 8) + ((store[p+2]) & 0xFF);
			}
		}
		return r;
		
//		byte[] b = {0,0,0,0};
//		
//		if (p+super.lengthSize  > store.length) {
//			return 0;
//		}
//
//		b[4-super.lengthSize] = store[p];
//		if (super.lengthSize == 2) {
//			b[3] = store[p+1];
//		}
//		if (super.lengthSize > 2) {
//			b[2] = store[p+1];
//			b[3] = store[p+2];
//		}
//		return (new BigInteger(b)).intValue();
	}

	@Override
	public RecordStoreVariableLength[] split(int blockSize) {
		ri.buildIndex();
		RecordStoreVariableLength[] ret = new RecordStoreVariableLength[ ((size -1)) / (blockSize) + 1];
		int used = 0;
		int last = -1;
		int j = 0;
		int max = recordCount / RecordIndexMgr.INDEX_RECORD;
		for (int i = 0; i < max; i++) {
			if (ri.get(i) - used > blockSize) {
				ret[j] = allocateStore(ri.get(i) - used, used, (i - last) * RecordIndexMgr.INDEX_RECORD, ri.get(i) - used);
//				ret[j] = new RecordStoreVariableLength(recIndex[i] - used, maxLen, (i - last) * INDEX_RECORD);
////				System.out.println(" --> " + i + " pos: "+ recIndex[i]
////				                  + " used: " + used
////				                  + " Records: " + ((i - last) * INDEX_RECORD)
////				                  + " From: " + (used - super.lengthSize)
////				                  + " Length: " + (recIndex[i] - used)
////				                  + " From Size: " + store.length
////				                  + " To Size: " + ret[j].store.length);
//				System.arraycopy(store, used, ret[j].store, 0, recIndex[i] - used);
//				ret[j].size = recIndex[i] - used;
				used = ri.get(i);
			
				last = i;
				j += 1;
			}
		}
		
		ri.resetIndex();

		
		if (used != size) {
			int l = size - used; 
			ret[j] = allocateStore(l, used, recordCount - (last + 1) * RecordIndexMgr.INDEX_RECORD, l);
//			ret[j] = new RecordStoreVariableLength(l, maxLen, recordCount - (last + 1) * INDEX_RECORD);
//			System.arraycopy(store, used, ret[j].store, 0, l);
//			ret[j].size = l;
		}
		return ret;
	}
	
	public RecordStoreVariableLength[] splitAt(int recNumber) {
		RecordStoreVariableLength[] ret = new RecordStoreVariableLength[2];
		LineDtls posLen = getLinePositionLength(recNumber, 0);
		int cpyLen0 = posLen.pos - super.recordOverhead;
		int cpyLen1 = size - cpyLen0;
		int defaultBlkSize = Common.OPTIONS.chunkSize.get();
		
		ret[0] = allocateStore(Math.max(defaultBlkSize, cpyLen0), 0,       recNumber,               cpyLen0);
		ret[1] = allocateStore(Math.max(defaultBlkSize, cpyLen1), cpyLen0, recordCount - recNumber, cpyLen1);
		
		return ret;
	} 

	
	private RecordStoreVariableLength allocateStore(int bsize, int start, int recCount, int cpyLen) {

		RecordStoreVariableLength ret = new RecordStoreVariableLength(bsize, maxLen, recordOverhead, recCount);
		                             // new RecordStoreVariableLength(recIndex[i] - used, maxLen, (i - last) * INDEX_RECORD);

		if (cpyLen >= 0) {
			System.arraycopy(store, start, ret.store, 0, cpyLen);
		}
		ret.size = cpyLen;
		
		return ret;
	}
}
