package net.sf.RecordEditor.edit.file.storage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.Line;


public class FileChunkLine
extends FileChunkBase<LineBase, RecordStoreBase> {


	public FileChunkLine(FileDetails details, int theFirstLine) {
		super(details, theFirstLine);
	}


//	@SuppressWarnings("unchecked")
//	@Override
//	public  void add(AbstractLine l) {
//
//		recordStore.add(l.getData());
//	}


	@SuppressWarnings("unchecked")
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
	


	public byte[] get(int idx) {
		uncompress();
		return recordStore.get(idx);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(int idx, AbstractLine l) {
		put(idx, l.getData());
	}

	public void put(int idx, byte[] rec) {
		synchronized (this) {
			uncompress();
			recordStore.put(idx, rec);
			compressed = null;
		}
	} 


	@SuppressWarnings("unchecked")
	@Override
	public AbstractLine removeLine(int lineNo) {
		uncompress();
		AbstractLine l = new Line(details.getLayout(), get(lineNo));
		remove(lineNo);
		return l;
	}


	@Override
	public LineBase getLine(int lineNo) {
		Integer cLine = (lineNo - getFirstLine());
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
	public LineBase getTempLine(int lineNo) {
		return new LineTemp(details.getLayout(), this, (lineNo - getFirstLine()));
	}
	

	
	
	
	@SuppressWarnings("unchecked")
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


	@Override
	public List<FileChunkBase<LineBase, RecordStoreBase>> split() {
		ArrayList<FileChunkBase<LineBase, RecordStoreBase>> ret = new ArrayList<FileChunkBase<LineBase, RecordStoreBase>>();
		RecordStoreBase r;
		RecordStoreBase[] rs = recordStore.split(details.dataSize);
		FileChunkLine fc;
		int linesSoFar = rs[0].recordCount;
		
		System.out.println(" !!   " + this.getFirstLine()
				+ " "  + this.recordStore.recordCount
				+ " "  + this.recordStore.getSize());
		System.out.println(" ## " + 0
				+ " " + this.getFirstLine()
				+ " " + rs[0].recordCount
				+ " " + rs[0].getSize());
		for (int i = 1; i < rs.length; i++) {
			if (rs[i] != null) {
				fc = new FileChunkLine(details, this.getFirstLine() + linesSoFar);
				fc.recordStore = rs[i];
				ret.add(fc);
				
				fc.count = fc.recordStore.getRecordCount();
				
				linesSoFar += rs[i].getRecordCount();
				
				System.out.println(" ** " + i
						+ " " + fc.getFirstLine()
						+ " " + fc.recordStore.recordCount
						+ " " + fc.recordStore.getSize());
			}
		}
		
		System.out.println();
		if (lines != null) {
			synchronized (lines) {
				Set<Integer> keySet = lines.keySet();
				WeakReference<LineBase> ref;
				Integer[] keys = new Integer[keySet.size()];
				keys = keySet.toArray(keys);
				int ikey;

				AbstractChunkLine line;
				FileChunkBase f;
				
				for (Integer key : keys) {
					ikey = key;
					if (ikey >= rs[0].getRecordCount()) {
						linesSoFar = rs[0].getRecordCount();
						boolean search = true;
						for (int j = 0; j < ret.size(); j++) {
							f = ret.get(j);
							if (ikey >= linesSoFar
							&&  ikey < linesSoFar + f.getCount()){
								ref = lines.get(key);
								line = ref.get();
								if (line != null) {
									line.setChunk(f);
									line.setChunkLine(ikey - linesSoFar);
									f.getLines().put(line.getChunkLine(), ref);
									
									if (ikey > 41 && ikey < 46) {
									System.out.println(" Renumber: " 
											+ j + ": " + ikey + " "
											+ linesSoFar + " " + line.getChunkLine()
											+ " " + f.getFirstLine());
									}
								}
								
								lines.remove(key);
								search = false;
								break;
//							} else {
//								System.out.print(" #> " + j + " " + ikey + " " + linesSoFar + 
//										" " + f.getCount() + "<# ");
							}
							
							linesSoFar += f.getCount();
						}
//						if (search) System.out.println("$$$");
//					} else {
//						System.out.print(" ! " + ikey + " < " + rs[0].getRecordCount());
					}
				}
			}
		}
		
		super.recordStore = rs[0];
		super.compressed = null;
		super.count = recordStore.getRecordCount();

		return ret;
	}
}
