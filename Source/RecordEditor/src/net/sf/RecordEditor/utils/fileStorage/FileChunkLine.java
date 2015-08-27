package net.sf.RecordEditor.utils.fileStorage;

import java.lang.ref.WeakReference;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.Line;


public class FileChunkLine
extends FileChunkBase<LineBase, RecordStoreBase> {


	public FileChunkLine(FileDetails details, int theFirstLine, long firstTextPosition) {
		super(details, theFirstLine, firstTextPosition);
	}


//	@SuppressWarnings("unchecked")
//	@Override
//	public  void add(AbstractLine l) {
//
//		recordStore.add(l.getData());
//	}


	@Override
	public  void add(int idx, AbstractLine l) {

		add(idx, l.getData());
	}

	public  void add(int idx, byte[] rec) {

		uncompress();
		
		recordStore.add(idx, rec); 
		count = recordStore.getRecordCount();
			
		updateLines(idx, 1);
	}
	
	@Override
	public int add(int idx, IDataStore<? extends AbstractLine> data, int start) {
		return add(idx, data, start, true);
	}
	
	@Override
	public void addAll(int idx, IDataStore<? extends AbstractLine> data) {
		add(idx, data, 0, false);
	}

	private int add(int idx, IDataStore<? extends AbstractLine> data, int start, boolean checkSize) {
		uncompress();
		if (doSmallOrMiddleAdd(idx, data, start)) {
			return data.size();
		} else {
			byte[] record;
			if (recordStore.getSize() < details.dataSize) {
				recordStore.moveData(recordStore.getSize(), details.dataSize);
			}
			
			int last = start;
			for (int i = start; i < data.size(); i++)  {
				record = data.getNewLineRE(i).getData();
				if (checkSize && recordStore.getSize() + record.length + recordStore.recordOverhead >= details.dataSize) {
					break;
				}
				
				last = i + 1;
				recordStore.add(recordStore.getRecordCount(), record);
				//if (i % 50000 == 0) System.out.print('.' + i + " " + recordStore.getSize() );
			}
			
			updateLines(idx, last - start);
			return last;
		}
	}
	
	private final boolean doSmallOrMiddleAdd(int idx, IDataStore<? extends AbstractLine> data, int start) {
		boolean ret = false;
		int dataSize = data.size();
		
		if (dataSize - start < 16) {
			for (int i  = start; i < dataSize; i++) {
				this.add(idx++, data.getNewLineRE(i));
			}
			ret = true;
		} else if (idx < getCount()){
			IRecordStore tmpStore = details.newRecordStore(); 
			((RecordStoreBase)tmpStore).moveData(0, data.size());

			for (int i  = start; i < dataSize; i++) {
				tmpStore.add(i - start, data.getNewLineRE(i).getData());
			}
			ret = true;
			
			add(idx, tmpStore);
		}
		return ret;
		
	}
	
	public byte[] get(int idx) {
//		if (recordStore == null) {
//			System.out.print('.');
//		}
		uncompress();
		return recordStore.get(idx);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IFileChunk#getLineLength(int)
	 */
	@Override
	public int getLineLength(int lineNo) {
		return get(lineNo - getFirstLine()).length;
	}

	@Override
	public LineBase getLine(int lineNo) {
		int cLine = (lineNo - getFirstLine());
		LineBase ret = null;
		
		getLines();
		synchronized (lines) {
			if (lines.containsKey(cLine)) {
				ret = (LineBase) lines.get(cLine).get();
			}
			if (ret == null) {
				ret = new LineChunk(details.getLayout(), this, cLine);
				lines.put(cLine, new WeakReference<LineBase>(ret));
			}
		}
		return ret;
	}



	@Override
	public void put(int idx, AbstractLine l) {
		put(idx, l.getData());
	}

	public final void putFromLine(int idx, byte[] rec) {
		put(idx, rec);
		details.getChunkLengthChangeListner().blockChanged(this);
	}

	public void put(int idx, byte[] rec) {
		synchronized (this) {
			uncompress();
			recordStore.put(idx, rec);
			compressed = null;
		}
	} 


	@Override
	public AbstractLine removeLine(int lineNo) {
		uncompress();
		AbstractLine l = new Line(details.getLayout(), get(lineNo));
		remove(lineNo);
		return l;
	}

	

	@Override
	public LineBase getTempLine(int lineNo) {
		return new LineTemp(details.getLayout(), this, (lineNo - getFirstLine()));
	}
	

	@Override
	public boolean hasRoomForMore(AbstractLine l) {
		int len = l.getData().length;
		int size = getSize();
		
		if (recordStore != null && (size + len) < recordStore.getStoreSize()) {
			return true;
		}
		uncompress();
		return  (size + len) < recordStore.getStoreSize();
	}

	
	@Override
	public int getSpaceUsed() {
		int ret = 0;
		
		if (this.recordStore != null) {
			ret = this.recordStore.getStoreSize();
		}
		if (this.compressed != null) {
			ret += this.compressed.length;
		}
		
		return ret;
	}


//	@Override
//	public List<FileChunkBase<LineBase, RecordStoreBase>> split() {
//		ArrayList<FileChunkBase<LineBase, RecordStoreBase>> ret = new ArrayList<FileChunkBase<LineBase, RecordStoreBase>>();
////		RecordStoreBase r;
//		RecordStoreBase[] rs = recordStore.split(details.dataSize);
//		FileChunkLine fc;
//		int linesSoFar = rs[0].getRecordCount();
//		
////		System.out.println(" !!   " + this.getFirstLine()
////				+ " "  + this.recordStore.recordCount
////				+ " "  + this.recordStore.getSize());
////		System.out.println(" ##  " + 0
////				+ " " + this.getFirstLine()
////				+ " " + rs[0].recordCount
////				+ " " + rs[0].getSize());
//		for (int i = 1; i < rs.length; i++) {
//			if (rs[i] != null) {
//				fc = newChunkLine(linesSoFar);//new FileChunkLine(details, this.getFirstLine() + linesSoFar);
//				fc.recordStore = rs[i];
//				ret.add(fc);
//				
//				fc.count = fc.recordStore.getRecordCount();
//				
//				linesSoFar += rs[i].getRecordCount();
//				
//				details.registerUse(fc);
//				details.registerUse(this);
////				System.out.println(" ** " + i
////						+ " " + fc.getFirstLine()
////						+ " " + fc.recordStore.recordCount
////						+ " " + fc.recordStore.getSize());
//			}
//		}
//		
//		//System.out.println();
//		if (lines != null) {
//			synchronized (lines) {
//				Set<Integer> keySet = lines.keySet();
//				WeakReference<LineBase> ref;
//				Integer[] keys = new Integer[keySet.size()];
//				keys = keySet.toArray(keys);
//				int ikey;
//
//				IChunkLine line;
//				FileChunkBase f;
//				
//				for (Integer key : keys) {
//					ikey = key;
//					if (ikey >= rs[0].getRecordCount()) {
//						linesSoFar = rs[0].getRecordCount();
//
//						for (int j = 0; j < ret.size(); j++) {
//							f = ret.get(j);
//							if (ikey >= linesSoFar
//							&&  ikey < linesSoFar + f.getCount()){
//								ref = lines.get(key);
//								line = ref.get();
//								if (line != null) {
//									line.setChunk(f);
//									line.setChunkLine(ikey - linesSoFar);
//									f.getLines().put(line.getChunkLine(), ref);
//									
////									if (ikey > 41 && ikey < 46) {
////									System.out.println(" Renumber: " 
////											+ j + ": " + ikey + " "
////											+ linesSoFar + " " + line.getChunkLine()
////											+ " " + f.getFirstLine());
////									}
//								}
//								
//								lines.remove(key);
//
//								break;
////							} else {
////								System.out.print(" #> " + j + " " + ikey + " " + linesSoFar + 
////										" " + f.getCount() + "<# ");
//							}
//							
//							linesSoFar += f.getCount();
//						}
////						if (search) System.out.println("$$$");
////					} else {
////						System.out.print(" ! " + ikey + " < " + rs[0].getRecordCount());
//					}
//				}
//			}
//		}
//		
//		super.recordStore = rs[0];
//		super.compressed = null;
//		super.count = recordStore.getRecordCount();
//
//		return ret;
//	}
	
	
	protected FileChunkLine newChunkLine(int linesSoFar, long textSoFar) {
		return new FileChunkLine(details, this.getFirstLine() + linesSoFar, this.getFirstTextPosition() + textSoFar);
	}
}
