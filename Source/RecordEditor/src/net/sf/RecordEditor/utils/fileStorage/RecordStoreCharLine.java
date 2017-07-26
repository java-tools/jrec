package net.sf.RecordEditor.utils.fileStorage;


import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;


public class RecordStoreCharLine implements ISimpleRecStore, IDocumentRecordStore  {

	private char[] store = null;
	private int recordCount=0;
	private int size = 0;
	private static final int lengthSize = 1;

//	private static final int INDEX_RECORD = 8;
	private int maxLen;
//	private int[] recIndex = null;
	private final RecordIndexMgr ri;
	
	private String font;

	public RecordStoreCharLine(int size, int mLen, int recCount, String fontName) {
		this(size, mLen, fontName);
		recordCount = recCount;
	}

	public RecordStoreCharLine(int size, int mLen, String fontName) {
		super();

		//this.recordCount = recs;
		ri = new RecordIndexMgr(this, mLen, 1, false);
		this.font = fontName;
		this.maxLen = mLen;

		if (size > 0) {
			store = new char[size];
		}
	}


	public void add(int idx, char[] rec) {

		if (idx > recordCount) {
			idx = recordCount + 1;
		}

		LineDtls p = getLinePositionLength(idx, rec.length);

//		System.out.println("Add Check move: "
//				+ idx + " < " + recordCount
//				+ " Pos: " + (p.pos - lengthSize)
//				+ " New Pos: " + (p.pos + p.newLength)
//				+ " Size: " + getSize());
		if (idx < recordCount || p.pos + p.newLength + lengthSize > store.length) {
			moveData(p.pos, p.pos + p.newLength + lengthSize);
		}

		put(p, rec);
		recordCount += 1;
		size += rec.length + lengthSize;
		checkSize();

		ri.checkRecordIndex(idx);
//		if (recIndex != null
//		&& ( idx < recordCount - 1
//		 ||  idx % INDEX_RECORD == INDEX_RECORD - 1)) {
//			buildIndex();
//		}
	}
	
	
	@Override
	public void add(int idx, IRecordStore rStore) {

		if (rStore.getRecordCount() <= 0) return;
		if (rStore instanceof RecordStoreCharLine) {
			RecordStoreCharLine recStore = (RecordStoreCharLine) rStore;
			if (idx > recordCount) {
				idx = recordCount + 1;
			}
	
			LineDtls p = getLinePositionLength(idx, recStore.getChar(0).length); 
	
	//		if ((p == null) || (store == null) ) {
	//			System.out.println("$$$ " + (p == null) + " " + (store == null));
	//	}
	//		System.out.println("Add Check move: "
	//				+ idx + " < " + recordCount
	//				+ " Pos: " + (p.pos - lengthSize)
	//				+ " New Pos: " + (p.pos + p.newLength)
	//				+ " Size: " + getSize());
			if (idx < recordCount) {
				moveData(p.pos, p.pos + recStore.getSize());
			} else if (getSize() + recStore.getSize() > store.length) {
				moveData(getSize(), getSize() + recStore.getSize());
			}
	
			System.arraycopy(recStore.store, 0, store, p.pos, recStore.size);

			recordCount += recStore.getRecordCount();
			size += recStore.size;
			
			ri.checkRecordIndex(idx);
//			if (recIndex != null
//			&& (  idx < recordCount - 1
//			  ||  idx % INDEX_RECORD == INDEX_RECORD - 1)) {
//				buildIndex();
//			}
			return;
		}
		throw new RuntimeException("Internal Error, wrong Record Store passed to RecordStoreBase");
	}



	public final char[] getChar(int idx) {
		LineDtls p =  getLinePositionLength(idx, 0);

		char[] ret = new char[p.len];
		if (p.len > 0) {
//		try {
//			if (p.len == 0) {
//				System.out.println(" Get: " + + idx + " " + p.pos
//						+ " " + p.len);
//			}
			System.arraycopy(store, p.pos, ret, 0, p.len);
//		} catch (Exception e) {
//			System.out.println("Error Retrieving Line Data: " + e.getMessage());
//
//			e.printStackTrace();
//		}
//		} else {
//		System.out.println(" Get: " + + idx
//				+ " " + this.recordCount
//				+ " " + p.pos
//				+ " " + p.len
//				+ " " + ret.length);
		}

		return ret;
	}


	protected final void moveData(int oldPos, int newPos) {
		//int size = getSize();

		if (size + newPos - oldPos > store.length) {
			int aveSize = 750;
			if (recordCount > 0) {
				aveSize = Math.max(20, size / recordCount);
			}
			int newSize = size + Math.max(newPos - oldPos, 128 * Math.min(aveSize, newPos - oldPos));
			char[] newStore = new char[newSize];
			//System.out.print(" new len:" + newStore.length);

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



	public int remove(int idx) {
		LineDtls p =  getLinePositionLength(idx, 0);
		//int pos = idx * len;
		if (store.length >= p.pos + p.len) {
//			System.out.println(
//					" remove:" + store.length + " " + (p.pos -lengthSize) + " " + p.len
//					+ " >> " + (p.pos + p.len ) + " ! " + getSize()
//					+ " ! " + idx);
			System.arraycopy(
					store, p.pos + p.len + lengthSize,
					store, p.pos, getSize() - p.pos - p.len - lengthSize);
		}

		recordCount -= 1;

		size -= p.len + lengthSize ;
		checkSize();
		ri.buildIndex();

		return p.len + lengthSize;
	}


	public final int getRecordCount() {
		return recordCount;
	}



	public int getStoreSize() {
		return store.length;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDocumentRecordStore#getTextPosition(int)
	 */
	@Override
	public final LineDtls getTextPosition(final int textPos) {
		return ri.getTextPosition(textPos, size);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.ISimpleRecStore#setSize(int)
	 */
	@Override
	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getCompressed() {
		return Code.compress(size, getBytes());
	}


	public byte[] getBytes() {
		String s =  new String(store, 0, size);

		return Conversion.getBytes(s, font);
	}

	public final void setCompressed(byte[] compressedData, int length, int count) {
		this.size = length;
		recordCount = count;

		ByteArray tmp = Code.uncompressed(compressedData, length * 2 + 20, false);

		if (tmp.size == 0) {
			store = new char[0];
		} else {
			String s = Conversion.getString(tmp.bytes, 0, tmp.size, font);

			if (s.length() != length) {
				Common.logMsgRaw(
						LangConversion.convertMsg(
								"Error uncompressing chunk expected {0} but was {1}",
								new Object[] {length, s.length()}),
						null);
			}
			store = s.toCharArray();
		}
	}



	@Override
	public void setBytes(byte[] bytes, int length, int count) {
		this.size = length;
		recordCount = count;

		//System.out.println(" ~~~~ " + size + " " + bytes.length);
		String s = Conversion.getString(bytes, 0, size, font);

		store = s.toCharArray();
	}


	public void put(int idx, char[] rec) {
		LineDtls p = getLinePositionLength(idx, rec.length);

		if (idx >= recordCount) {
			size += rec.length + lengthSize;
		} else if (rec.length != p.len) {
			moveData(p.pos + p.len, p.pos + rec.length);

			ri.adjRecordIndex(idx, rec.length - p.len);
//			if (recIndex != null) {
//				synchronized (recIndex) {
//					int index = idx / INDEX_RECORD;
//					int diff = rec.length - p.len;
////					System.out.println("Update Index: " + idx + " "
////							+ diff + " " + p.len + ">");
//					for (int i = Math.max(0, index); i < recordCount / INDEX_RECORD; i++) {
//						//System.out.print("\t" + i + ": " + recIndex[i]);
//						recIndex[i] += diff;
//					}
//				}
//			}
			size += rec.length - p.len;
		}
//		System.out.println(" put " + this.hashCode() + " " + size + " "
//				+ rec.length);
		checkSize();

		put(p, rec);
	}





	protected void put(LineDtls p, char[] rec) {

		System.arraycopy(rec, 0, store, p.pos, rec.length);
		//System.out.println("^^ " + p.index + " -- " + p.pos + " " + p.len + " " + b.length);
		store[p.pos + rec.length] = '\n';
	}




	public int getSize() {
		return size;
	}


	@Override
	public LineDtls getLinePositionLength(int idx, int newLen) {
		return ri.getLinePositionLength(idx, newLen, size);
	}

//	protected LineDtls getPosLen(int idx, int newLen) {
//
//		int lastP;
//		int l = lengthSize;
//		if (idx >= recordCount) {
//			lastP = size;
//			l = 0;
//		} else {
//			int p = 0;
//			int index = idx / INDEX_RECORD - 1;
//			int count = idx % INDEX_RECORD;
//
//			if (index >= 0) {
//				if (recIndex == null) {
//					buildIndex();
//				}
//
//				p = recIndex[index];
//			}
//			lastP = p;
//			//System.out.print(" . " + count);
//			for (int i = 0; i <= count; i++) {
//				lastP = p;
//				l = getLength(p);
//				p += l + lengthSize;
//			}
//		}
//
//		return new LineDtls(lastP, l, newLen, idx, 0, 1, false);
//	}
//
//
//	protected void buildIndex() {
//		if (recordCount < INDEX_RECORD) {
//			return;
//		}
//		int i, l;
//		int j = 0;
//		int k = 0;
//		int p = 0;
//
//		if (recIndex == null || recIndex.length < recordCount / INDEX_RECORD) {
//			//System.out.println("Index ");
//			recIndex = new int[Math.max(recordCount, store.length / Math.max(1, maxLen)) / INDEX_RECORD + 10];
//		}
//
//		synchronized (recIndex) {
//			for (i = 0; i < recordCount; i++) {
//				l = getLength(p);
//				p += l + lengthSize;
//
//				//System.out.println(" --> " + j + "\t" + p + "\t" + l);
//
//				j += 1;
//				if (j >= INDEX_RECORD) {
//					j = 0;
//					if (k < recIndex.length) {
//						recIndex[k++] = p;
//					}
//				}
//			}
//			//System.out.print("$");
////			System.out.println(" ==> Size Check: " + this.hashCode()
////					+ " > "+ p + " " + size
////					+ " > " + store.length
////					+ " " + getRecordCount());
//			size = p;
//			checkSize();
//
////			System.out.print("Rebuilt Index count=" + recordCount + " " + k + " : ");
////			for (i = 0; i < k; i++) {
////				System.out.print(i + ": " + recIndex[i] + "\t");
////			}
////			System.out.println();
//		}
//
//	}



	@Override
	public void add(int idx, byte[] rec) {
		throw new RecordRunTimeException("Can not add Byte Array");
	}


	@Override
	public byte[] get(int idx) {
		throw new RecordRunTimeException("Can not get Byte Array");
	}


	@Override
	public void put(int idx, byte[] rec) {
		throw new RecordRunTimeException("Can not put Byte Array");
	}


	public int getLength(int p) {

		int len = 0;
		int i = p;

		while (i < size && i < store.length && store[i] != '\n') {
			i   += 1;
			len += 1;
		}

		return len;
	}

	public RecordStoreCharLine[] split(int blockSize) {
		ri.buildIndex();
		RecordStoreCharLine[] ret = new RecordStoreCharLine[ ((size -1)) / (blockSize) + 1];
		int used = 0;
		int last = -1;
		int j = 0;
		for (int i = 0; i < recordCount / RecordIndexMgr.INDEX_RECORD; i++) {
			if (ri.get(i) - used > blockSize) {
				ret[j] = allocateStore(ri.get(i) - used, used, (i - last) * RecordIndexMgr.INDEX_RECORD, ri.get(i) - used);
//				ret[j] =  new RecordStoreCharLine(recIndex[i] - used, maxLen, (i - last) * INDEX_RECORD, font);
//				//new RecordStoreCharLine(recIndex[i] - used, maxLen, (i - last) * INDEX_RECORD);
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
		
		
		if (used != size) {
			int l = size - used; 
			ret[j] = allocateStore(l, used, recordCount - (last + 1) * RecordIndexMgr.INDEX_RECORD, l);
//			ret[j] = new RecordStoreCharLine(l, maxLen, recordCount - (last + 1) * INDEX_RECORD, font);
//					//new RecordStoreCharLine(l, maxLen, recordCount - (last + 1) * INDEX_RECORD);
//			System.arraycopy(store, used, ret[j].store, 0, l);
//			ret[j].size = l;
		}
		return ret;
	}

	
	public RecordStoreCharLine[] splitAt(int recNumber) {
		RecordStoreCharLine[] ret = new RecordStoreCharLine[2];
		LineDtls posLen = getLinePositionLength(recNumber, 0);
		int cpyLen0 = posLen.pos;
		int cpyLen1 = size - cpyLen0;
		int defaultBlkSize = Common.OPTIONS.chunkSize.get();
		
		ret[0] = allocateStore(Math.max(defaultBlkSize, cpyLen0), 0,       recNumber,               cpyLen0);
		ret[1] = allocateStore(Math.max(defaultBlkSize, cpyLen1), cpyLen0, recordCount - recNumber, cpyLen1);
		
		return ret;
	}


	private RecordStoreCharLine allocateStore(int bsize, int start, int recCount, int cpyLen) {

		RecordStoreCharLine ret = new RecordStoreCharLine(bsize, maxLen, recCount, font);
		                             // new RecordStoreVariableLength(recIndex[i] - used, maxLen, (i - last) * INDEX_RECORD);

		if (cpyLen > 0) {
			System.arraycopy(store, start, ret.store, 0, cpyLen);
		}
		ret.size = cpyLen;
		
		return ret;
	}

	private void checkSize() {
//		if (size > store.length) {
//			System.out.println("*");
//		}
	}

}
