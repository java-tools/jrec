package net.sf.RecordEditor.utils.fileStorage;

import java.lang.ref.WeakReference;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.CharLine;


public class FileChunkCharLine extends FileChunkBase<CharLineBase, RecordStoreCharLine> {

	//private RecordStoreCharLine rs;
	public FileChunkCharLine(FileDetails details, int theFirstLine, long firstTextPosition) {
		super(details, theFirstLine, firstTextPosition);
	}


	@Override
	public  void add(int idx, AbstractLine l) {

		add(idx, l.getFullLine().toCharArray());
	}

	public  void add(int idx, char[] rec) {

		uncompress();

		//synchronized (recordStore) {
		getRecordStore().add(idx, rec);
		count = recordStore.getRecordCount();

		updateLines(idx, 1);
		//}
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
			return doAdd(idx, data, start, checkSize);
			
//			char[] records;
//			if (recordStore.getSize() < details.dataSize) {
//				recordStore.moveData(recordStore.getSize(), details.dataSize);
//			}
//			
//			int last = start;
//			if (data instanceof FileChunkCharLine) {
//				FileChunkCharLine charData = (FileChunkCharLine) data;
//				RecordStoreCharLine dataRecordStore = charData.getRecordStore();
//				if (checkSize && recordStore.getSize() + dataRecordStore.getSize() >= details.dataSize) {
//					System.out.print("%");
//					for (int i = start; i < data.size(); i++)  {
//						records = dataRecordStore.getChar(i);
//						if (checkSize && recordStore.getSize() + records.length >= details.dataSize) {
//							break;
//						}
//						
//						last = i + 1;
//						recordStore.add(recordStore.getRecordCount(), records);
//					}
//				} else {
//					System.out.print("^");
//					recordStore.add(recordStore.getRecordCount(), dataRecordStore);
//					last += dataRecordStore.getSize();
//				}		
//			} else {
//				System.out.print("&");
//				for (int i = start; i < data.size(); i++)  {
//					records = data.getNewLine(i).getFullLine().toCharArray();
//					if (checkSize && recordStore.getSize() + records.length >= details.dataSize) {
//						break;
//					}
//					
//					last = i + 1;
//					recordStore.add(recordStore.getRecordCount(), records);
//				}
//				
//				updateLines(idx, last - start);
//			}
//			return last;
		}
	}
	
	private boolean doSmallOrMiddleAdd(int idx, IDataStore<? extends AbstractLine> data, int start) {
		boolean ret = false;
		int dataSize = data.size();;
		
		if (dataSize - start < 16) {
			for (int i  = start; i < dataSize; i++) {
				this.add(idx++, data.getNewLineRE(i));
			}
			ret = true;
		} else if (idx < getSize()){
//			doAdd(idx, data, start, false);
//			ret = true;
			RecordStoreCharLine tmpStore = (RecordStoreCharLine) details.newRecordStore();
			
			for (int i  = start; i < dataSize; i++) {
				tmpStore.add(i - start, data.getNewLineRE(i).getFullLine().toCharArray());
			}
			ret = true;
			
			recordStore.add(idx, tmpStore);

			updateLines(idx, data.size() - start);
		}
		return ret;
	}

	
	private int doAdd(int idx, IDataStore<? extends AbstractLine> data, int start, boolean checkSize) {
		
		char[] record;
		int last = start;
		
		if (recordStore.getSize() < details.dataSize) {
			recordStore.moveData(recordStore.getSize(), details.dataSize);
		}
/*		if (data instanceof DataStoreLarge && ((DataStoreLarge) data).getFileDetails().type == FileDetails.CHAR_LINE) {
			System.out.print('#');
			ArrayList<? extends IFileChunk> chunks = ((DataStoreLarge) data).getChunks();
			for (IFileChunk ch : chunks) {
				if (last >= ch.getFirstLine() && last < ch.getFirstLine() + ch.getCount()) {
					RecordStoreCharLine dataRecordStore = ((FileChunkCharLine) ch).getRecordStore();

//						System.out.print("^ " + (last) + ":" + (last - start) + ";" + (last + dataRecordStore.getRecordCount()) + "<");
					recordStore.add(idx + last - start, dataRecordStore);
					last += dataRecordStore.getRecordCount();

					if (checkSize && recordStore.getSize() >= details.dataSize) {
						break;
					}
				}
			}
		} else*/

//			System.out.print("&");
		int idx1 = idx;
		for (int i = start; i < data.size(); i++)  {
			record = data.getTempLineRE(i).getFullLine().toCharArray();
			if (checkSize && recordStore.getSize() + record.length >= details.dataSize) {
				break;
			}
			
			last = i + 1;
			recordStore.add(idx1++, record);
		}

		
		updateLines(idx, last - start);

		return last;

	}


	public byte[] get(int idx) {

		return Conversion.getBytes(getString(idx), details.getLayout().getFontName());
	}


	public char[] getChar(int idx) {
		uncompress();
		return getRecordStore().getChar(idx);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IFileChunk#getLineLength(int)
	 */
	@Override
	public int getLineLength(int lineNo) {
		return getChar(lineNo - getFirstLine()).length;
	}



	public String getString(int idx) {
		return new String(getChar(idx));
	}


	@Override
	public void put(int idx, AbstractLine l) {

		put(idx, ((CharLine) l).getFullLine().toCharArray());
	}

	
	protected final void putFromLine(int idx, char[] rec) {
		put(idx, rec);
		details.getChunkLengthChangeListner().blockChanged(this);
	}

	
	protected final void put(int idx, char[] rec) {
		synchronized (this) {
			uncompress();
			getRecordStore().put(idx, rec);
			compressed = null;
		}
	}


	@Override
	public AbstractLine removeLine(int lineNo) {
		uncompress();

		String s = new String(recordStore.getChar(lineNo));
		AbstractLine l = new CharLine(details.getLayout(), s);
		remove(lineNo);
		return l;
	}


	@Override
	public CharLineBase getLine(int lineNo) {
		int cLine = (lineNo - getFirstLine());
		CharLineBase ret = null;

		getLines();
		synchronized (lines) {
			if (lines.containsKey(cLine)) {
				ret =  lines.get(cLine).get();
			}
			if (ret == null) {
				ret = new CharLineChunk(details.getLayout(), this, cLine);
				lines.put(cLine, new WeakReference<CharLineBase>(ret));
			}
		}
		return ret;
	}




	@Override
	public CharLineBase getTempLine(int lineNo) {
		return new CharLineTemp(details.getLayout(), this, (lineNo - getFirstLine()));
	}


	@Override
	public boolean hasRoomForMore(AbstractLine l) {
		int len = l.getData().length;
		uncompress();
//		System.out.println(" --> hasRoom " + recordStore.getSize() + " "
//				+ details.len + " " + recordStore.getStoreSize());

		return (recordStore != null)
			&& (recordStore.getSize() + len) < recordStore.getStoreSize();
	}


//	@Override
//	public List<FileChunkBase<CharLineBase, RecordStoreCharLine>> split() {
//		ArrayList<FileChunkBase<CharLineBase, RecordStoreCharLine>> ret = new ArrayList<FileChunkBase<CharLineBase, RecordStoreCharLine>>();
//		RecordStoreBase r;
//		RecordStoreCharLine[] rs = recordStore.split(details.dataSize);
//		FileChunkBase<CharLineBase, RecordStoreCharLine> fc;
//		int linesSoFar = rs[0].getRecordCount();
//		
////		System.out.println(" !!   " + this.getFirstLine()
////				+ " "  + this.recordStore.recordCount
////				+ " "  + this.recordStore.getSize());
////		System.out.println(" ## " + 0
////				+ " " + this.getFirstLine()
////				+ " " + rs[0].recordCount
////				+ " " + rs[0].getSize());
//		for (int i = 1; i < rs.length; i++) {
//			if (rs[i] != null) {
//				fc = newChunkLine(linesSoFar); //new FileChunkCharLine(details, this.getFirstLine() + linesSoFar);
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
//				WeakReference<CharLineBase> ref;
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

	
	@Override
	protected FileChunkBase<CharLineBase, RecordStoreCharLine> newChunkLine(
			int linesSoFar, long textSoFar) {

		return new FileChunkCharLine(details, this.getFirstLine() + linesSoFar, this.getFirstTextPosition() + textSoFar);
	}


	@Override
	protected int getStorageSize() {
		return 2;
	}

}
