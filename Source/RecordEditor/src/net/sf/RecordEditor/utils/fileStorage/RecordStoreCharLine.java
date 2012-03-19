package net.sf.RecordEditor.utils.fileStorage;


import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.common.Common;


public class RecordStoreCharLine implements RecordStore  {

	private char[] store = null;
	private int recordCount=0;
	private int size = 0;
	private static final int lengthSize = 1;
	
	private static final int INDEX_RECORD = 8;
	private int maxLen;
	private int[] recIndex = null;
	private String font;
	
	public RecordStoreCharLine(int size, int mLen, String fontName) {
		super();
		
		//this.recordCount = recs;
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

		LineDtls p = getPosLen(idx, rec.length);
		
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
		
		if (recIndex != null
		&& ( idx < recordCount - 1
		 ||  idx % INDEX_RECORD == INDEX_RECORD - 1)) {
			buildIndex();
		}
	}


	public final char[] getChar(int idx) {
		LineDtls p =  getPosLen(idx, 0);

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
			//System.out.print("*");
			//System.out.print(" Size:" + size + " newPos:"
			//		+ newPos + " " + oldPos);
			char[] newStore = new char[size + newPos - oldPos];
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



	public int remove(int idx) {
		LineDtls p =  getPosLen(idx, 0);
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
		buildIndex();
		
		return p.len + lengthSize;
	}


	public final int getRecordCount() {
		return recordCount;
	}

	

	public int getStoreSize() {
		return store.length;
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
				Common.logMsg("Error uncompressing chunk expected " 
						+ length + " but was " + s.length(), null);
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
		LineDtls p = getPosLen(idx, rec.length);
		
		if (idx >= recordCount) {
			size += rec.length + lengthSize;
		} else if (rec.length != p.len) {
			moveData(p.pos + p.len, p.pos + rec.length);
			
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


	protected LineDtls getPosLen(int idx, int newLen) {
		
		int lastP;
		int l = lengthSize;
		if (idx >= recordCount) {
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
				p += l + lengthSize;
			}
		}
		
		return new LineDtls(lastP, l, newLen, idx);
	}
	
	
	protected void buildIndex() {
		if (recordCount < INDEX_RECORD) {
			return;
		}
		int i, l;
		int j = 0;
		int k = 0;
		int p = 0;

		if (recIndex == null || recIndex.length < recordCount / INDEX_RECORD) {
			//System.out.println("Index ");
			recIndex = new int[Math.max(recordCount, store.length / maxLen) / INDEX_RECORD + 10];
		}
		
		synchronized (recIndex) {
			for (i = 0; i < recordCount; i++) {
				l = getLength(p);
				p += l + lengthSize;
				
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
//			System.out.println(" ==> Size Check: " + this.hashCode()
//					+ " > "+ p + " " + size 
//					+ " > " + store.length
//					+ " " + getRecordCount());
			size = p;
			checkSize();
			
//			System.out.print("Rebuilt Index count=" + recordCount + " " + k + " : ");
//			for (i = 0; i < k; i++) {
//				System.out.print(i + ": " + recIndex[i] + "\t");
//			}
//			System.out.println();
		}

	}
	
	
	
	@Override
	public void add(int idx, byte[] rec) {
		throw new RuntimeException("Can not add Byte Array");
	}


	@Override
	public byte[] get(int idx) {
		throw new RuntimeException("Can not get Byte Array");
	}


	@Override
	public void put(int idx, byte[] rec) {
		throw new RuntimeException("Can not put Byte Array");
	}


	private int getLength(int p) {
		
		int len = 0;
		int i = p;
		
		while (i < size && i < store.length && store[i] != '\n') {
			i   += 1;
			len += 1;
		}
		
		return len;
	}

	
	private void checkSize() {
//		if (size > store.length) {
//			System.out.println("*");
//		}
	}

}
