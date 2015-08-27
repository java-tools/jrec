package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Common.RecordRunTimeException;

public abstract class RecordStoreBase implements IRecordStore {

	protected byte[] store = null;
	protected int recordCount;
	protected int size = 0;
	protected final int recordOverhead;
	
	
	public RecordStoreBase(int recs, int lenSize) {
		super();

		this.recordCount = recs;
		this.recordOverhead = lenSize;
	}


	@Override
	public void add(int idx, byte[] rec) {

		if (idx > recordCount) {
			idx = recordCount;
		}

		LineDtls p = getLinePositionLength(idx, rec.length);

//		if ((p == null) || (store == null) ) {
//			System.out.println("$$$ " + (p == null) + " " + (store == null));
//	}
//		System.out.println("Add Check move: "
//				+ idx + " < " + recordCount
//				+ " Pos: " + (p.pos - lengthSize)
//				+ " New Pos: " + (p.pos + p.newLength)
//				+ " Size: " + getSize());
		if (idx < recordCount || p.pos + p.newLength + recordOverhead > store.length) {
			moveData(p.pos - recordOverhead, p.pos + p.newLength);
		}

//		try {
			put(p, rec);
//		} catch (Exception e) {
//			e.printStackTrace();
//			moveData(p.pos - lengthSize, p.pos + p.newLength);
//			throw new RuntimeException( e);
//		}
		recordCount += 1;
	}
	
	
	@Override
	public void add(int idx, IRecordStore rStore) {

		if (rStore == null || rStore.getRecordCount() <= 0) return;
		if (rStore instanceof RecordStoreBase 
		&& recordOverhead == ((RecordStoreBase) rStore).recordOverhead
		&& rStore.getClass().equals(this.getClass())) {
			RecordStoreBase recStore = (RecordStoreBase) rStore;
			if (idx > recordCount) {
				idx = recordCount + 1;
			}
	
			LineDtls p = getLinePositionLength(idx, recStore.get(0).length); 
	
	//		if ((p == null) || (store == null) ) {
	//			System.out.println("$$$ " + (p == null) + " " + (store == null));
	//	}
	//		System.out.println("Add Check move: "
	//				+ idx + " < " + recordCount
	//				+ " Pos: " + (p.pos - lengthSize)
	//				+ " New Pos: " + (p.pos + p.newLength)
	//				+ " Size: " + getSize());
			if (idx < recordCount) {
				moveData(p.pos - recordOverhead, p.pos - recordOverhead + recStore.getSize());
			} else if (getSize() + recStore.getSize() > store.length) {
				moveData(getSize(), getSize() + recStore.getSize());
			}
	
			System.arraycopy(recStore.store, 0, store, p.pos - recordOverhead, recStore.getSize());

			recordCount += recStore.getRecordCount();
			size += recStore.getSize();
			return;
		}
		throw new RuntimeException("Internal Error, wrong Record Store passed to RecordStoreBase");
	}



//	public abstract void add(byte[] rec);

	protected abstract void put(LineDtls pos, byte[] rec);

	@Override
	public final byte[] get(int idx) {
		LineDtls p =  getLinePositionLength(idx, 0);

		byte[] ret = new byte[p.len];
		try {
//			if (p.len == 0) {
//				System.out.println(" Get: " + + idx + " " + p.pos
//						+ " " + p.len);
//			}
			System.arraycopy(store, p.pos, ret, 0, p.len);
		} catch (Exception e) {
			System.out.println("Error Retrieving Line Data: " + idx + " " + e.getMessage());
			p =  getLinePositionLength(idx, 0);
		}

		return ret;
	}


	protected final void moveData(int oldPos, int newPos) {
		int size = getSize();

		if (/*Math.max(size, newPos)*/ size + newPos - oldPos > store.length) {
			int aveSize = 750;
			if (recordCount > 0) {
				aveSize = Math.max(20, size / recordCount);
			}
			int newSize = size + Math.max(newPos - oldPos, 128 * Math.min(aveSize, newPos - oldPos));
			byte[] newStore = new byte[newSize];
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
					throw new RecordRunTimeException("Internal Error in RecordStoreBase can not copy data, so dropping lines ?");
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
		LineDtls p =  getLinePositionLength(idx, 0);
		//int pos = idx * len;
		if (store.length >= p.pos + p.len) {
//			System.out.println(
//					" remove: " + store.length
//					+ " " + (p.pos -lengthSize) + " " + p.len
//					+ " >> " + (p.pos + p.len ) + " ! " + getSize()
//					+ " ! " + idx);
			System.arraycopy(
					store, p.pos + p.len,
					store, p.pos - recordOverhead, getSize() - p.pos - p.len);
		}

		recordCount -= 1;
		return p.len + recordOverhead;
	}

	@Override
	public final int getRecordCount() {
		return recordCount;
	}


//	@Override
//	public abstract int getSize();

	/**
	 * @param size the size to set
	 */
	public final void setSize(int size) {
		this.size = size;
	}


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

//	public abstract RecordStoreBase[] split(int size);
//	public abstract RecordStoreBase[] splitAt(int recNumber);

	protected abstract LineDtls getLinePositionLength(int idx, int newLength);
}
