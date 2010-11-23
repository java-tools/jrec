package net.sf.RecordEditor.edit.file.storage;

import java.math.BigInteger;


public class RecordStoreVariableLength extends RecordStoreBase {

	private static final int INDEX_RECORD = 16;
	private int maxLen;
	private int[] recIndex = null;
	
	
	public RecordStoreVariableLength(int length, int recLen, int recs) {
		super(recs, calcBytes(recLen));
		
		if (length >= 0) {
			this.store = new byte[length];
		}

		this.maxLen = Math.max(1, recLen);
	}
	
	private static int calcBytes(int len) {
		int ret = 1;
		
		if (len > 65025) {
			ret = 3;
		} else if (len > 255) {
			ret = 2;
		}
		
		return ret;
	}


	@Override
	public void add(int idx, byte[] rec) {
		super.add(idx, rec);
		size += rec.length + super.lengthSize;
//		System.out.println("       Add: " + this.hashCode()
//				+ " > " + idx + " " + super.recordCount
//				+ " > " + rec.length + " " + size);
		if (recIndex != null
		&& ( idx < recordCount - 1
		 ||  idx % INDEX_RECORD == INDEX_RECORD - 1)) {
			buildIndex();
		}
	}


	@Override
	public byte[] getCompressed() {
		byte[] b = super.getCompressed();
		if (store == null) {
			recIndex = null;
		}
		return b;
	}


	@Override
	public int remove(int idx) {
		int len = super.remove(idx);
		size -= len ;
		buildIndex();
		
		return len;
	}


	@Override
	public void put(int idx, byte[] rec) {
		PosLen p = getPosLen(idx, rec.length);
		
		if (idx >= recordCount) {
			size += rec.length + super.lengthSize;
		} else if (rec.length != p.len) {
			super.moveData(p.pos + p.len, p.pos + rec.length);
			
			if (recIndex != null) {
				synchronized (recIndex) {
					int index = idx / INDEX_RECORD;
					int diff = rec.length - p.len;
//					System.out.println("Update Index: " + idx + " " 
//							+ diff + " " + p.len + ">");
					for (int i = Math.max(0, index); i < recordCount / INDEX_RECORD; i++) {
						//System.out.print("\t" + i + ": " + recIndex[i]);
						recIndex[i] += diff;
					}
				}
			}
			size += rec.length - p.len;
		} 
//		System.out.println(" put " + this.hashCode() + " " + size + " " 
//				+ rec.length);
		
		put(p, rec);
	}

	
	
	
	@Override
	protected void put(PosLen p, byte[] rec) {
		byte[] b = BigInteger.valueOf(rec.length).toByteArray();

		System.arraycopy(rec, 0, store, p.pos, rec.length);
		//System.out.println("^^ " + p.index + " -- " + p.pos + " " + p.len + " " + b.length);
		store[p.pos-1] = b[b.length - 1];
		
		switch (super.lengthSize) {
		case 3:
			store[p.pos-3] = 0;
			if (b.length > 2) {
				store[p.pos-3] = b[b.length - 3];
			}	
		case 2:
			store[p.pos-2] = 0;
			if (b.length > 1) {
				store[p.pos-2] = b[b.length - 2];
			}
		}	
	}

	

	@Override
	public int getSize() {
		return size;
	}

	@Override
	protected PosLen getPosLen(int idx, int newLen) {
		
		int lastP;
		int l = super.lengthSize;
		if (idx >= super.recordCount) {
			lastP = size;
			l = 0;
		} else {
			int p = 0;
			int index = idx / INDEX_RECORD - 1;
			int count = idx % INDEX_RECORD;
			
			if (index >= 0) {
				if (recIndex == null) {
					buildIndex();
				}
			
				p = recIndex[index];
			}
			lastP = p;
			//System.out.print(" . " + count);
			for (int i = 0; i <= count; i++) {
				lastP = p;
				l = getLength(p);
				p += l + super.lengthSize;
			}
		}
		
		return new PosLen(lastP + super.lengthSize, l, newLen, idx);
	}
	
	
	protected void buildIndex() {
		if (recordCount < INDEX_RECORD) {
			return;
		}
		int i, l;
		int j = 0;
		int k = 0;
		int p = 0;

		if (recIndex == null || recIndex.length < super.recordCount / INDEX_RECORD) {
			//System.out.println("Index ");
			recIndex = new int[Math.max(super.getRecordCount(), store.length / maxLen) / INDEX_RECORD + 10];
		}
		
		synchronized (recIndex) {
			for (i = 0; i < super.recordCount; i++) {
				l = getLength(p);
				p += l + super.lengthSize;
				
				//System.out.println(" --> " + j + "\t" + p + "\t" + l);
				
				j += 1;
				if (j >= INDEX_RECORD) {
					j = 0;
					if (k < recIndex.length) {
						recIndex[k++] = p;
					}
				}
			}
			//System.out.print("$");
			size = p;
//			System.out.println(" ==> Size Check: " + this.hashCode()
//					+ " > "+ p + " " + size + " " + super.getRecordCount());
			
//			System.out.print("Rebuilt Index count=" + recordCount + " " + k + " : ");
//			for (i = 0; i < k; i++) {
//				System.out.print(i + ": " + recIndex[i] + "\t");
//			}
//			System.out.println();
		}

	}
	
	private int getLength(int p) {
		byte[] b = {0,0,0,0};
		
		if (p+super.lengthSize  > store.length) {
			return 0;
		}

		b[4-super.lengthSize] = store[p];
		if (super.lengthSize == 2) {
			b[3] = store[p+1];
		}
		if (super.lengthSize > 2) {
			b[2] = store[p+1];
			b[3] = store[p+2];
		}
		return (new BigInteger(b)).intValue();
	}
}
