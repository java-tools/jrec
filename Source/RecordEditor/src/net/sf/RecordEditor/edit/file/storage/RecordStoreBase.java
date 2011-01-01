package net.sf.RecordEditor.edit.file.storage;

public abstract class RecordStoreBase<R extends RecordStoreBase<?>> implements RecordStore {

	protected byte[] store = null;
	protected int recordCount;
	protected int size = 0;
	protected final int lengthSize;
	
	
	public RecordStoreBase(int recs, int lenSize) {
		super();
		
		this.recordCount = recs;
		this.lengthSize = lenSize;
	}


	@Override
	public void add(int idx, byte[] rec) {
		
		if (idx > recordCount) {
			idx = recordCount + 1;
		} 

		LineDtls p = getPosLen(idx, rec.length);
		
//		if ((p == null) || (store == null) ) {
//			System.out.println("$$$ " + (p == null) + " " + (store == null));
//	}
//		System.out.println("Add Check move: " 
//				+ idx + " < " + recordCount
//				+ " Pos: " + (p.pos - lengthSize)
//				+ " New Pos: " + (p.pos + p.newLength)
//				+ " Size: " + getSize());
		if (idx < recordCount || p.pos + p.newLength + lengthSize > store.length) {
			moveData(p.pos - lengthSize, p.pos + p.newLength);
		}

		put(p, rec);
		recordCount += 1;
	}
	

//	public abstract void add(byte[] rec);

	protected abstract void put(LineDtls pos, byte[] rec);

	@Override
	public final byte[] get(int idx) {
		LineDtls p =  getPosLen(idx, 0);

		byte[] ret = new byte[p.len];
		try {
//			if (p.len == 0) {
//				System.out.println(" Get: " + + idx + " " + p.pos
//						+ " " + p.len);
//			}
			System.arraycopy(store, p.pos, ret, 0, p.len);
		} catch (Exception e) {
			System.out.println("Error Retrieving Line Data: " + e.getMessage());
		}
		
		return ret;
	} 

	
	protected final void moveData(int oldPos, int newPos) {
		int size = getSize();
		
		if (size + newPos - oldPos > store.length) {
			//System.out.print("*");
//			System.out.print(" Size:" + size + " newPos:"
//					+ newPos + " " + oldPos);
			byte[] newStore = new byte[size + newPos - oldPos];
//			System.out.print(" new len:" + newStore.length
//					+ " " + store.length);
			
			System.arraycopy(store, 0, newStore, 0, oldPos);
			//System.out.print(" test: " + (size > oldPos)
			//		+ " && " + (newStore.length > newPos));
			if (size > oldPos) {
				if (newStore.length > newPos) {
//					System.out.println("$$ " + store.length + " " + newStore.length
//							+ " From " + oldPos + " to " + newPos + " size " + size
//							+ " " + (size - oldPos));
					System.arraycopy(store, oldPos, newStore, newPos, size - oldPos);
				} else {
					throw new RuntimeException("Internal Error in RecordStoreBase can not copy data, so dropping lines ? ");
				}
			}
			store = newStore;
		}  else if (size > oldPos && oldPos != newPos) {
//			System.out.println("Move OldPos: " + oldPos + " to: " + newPos
//					+ " Size: " + size + " amount: " + (size - oldPos)
//					+ " Compare: " + (size + newPos - oldPos) + " <= " + store.length);
			System.arraycopy(store, oldPos, store, newPos, size - oldPos);
		}
	}


	@Override
	public int remove(int idx) {
		LineDtls p =  getPosLen(idx, 0);
		//int pos = idx * len;
		if (store.length >= p.pos + p.len) {
//			System.out.println(
//					" remove: " + store.length
//					+ " " + (p.pos -lengthSize) + " " + p.len
//					+ " >> " + (p.pos + p.len ) + " ! " + getSize()
//					+ " ! " + idx);
			System.arraycopy(
					store, p.pos + p.len, 
					store, p.pos - lengthSize, getSize() - p.pos - p.len);
		}
		
		recordCount -= 1;
		return p.len + lengthSize;
	}

	@Override
	public final int getRecordCount() {
		return recordCount;
	}


	@Override
	public abstract int getSize();
	
	@Override
	public int getStoreSize() {
		return store.length;
	}
	
	
	@Override
	public byte[] getCompressed() {
		
		return Code.compress(getSize(), store);
	}
	
	@Override
	public byte[] getBytes() {
		return store;
	}


	@Override
	public final void setCompressed(byte[] compressedData, int length, int count) {
		this.recordCount = count;
		this.size = length;
		store = Code.uncompressed(compressedData, length).bytes;
	}
	

	@Override
	public final void setBytes(byte[] bytes, int length, int count) {
		this.recordCount = count;
		this.size = length;
		store = bytes;
	}
	
	public abstract R[] split(int size);

	protected abstract LineDtls getPosLen(int idx, int newLength);
	
	
}
