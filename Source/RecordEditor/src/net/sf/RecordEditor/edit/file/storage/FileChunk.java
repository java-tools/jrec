package net.sf.RecordEditor.edit.file.storage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;


public class FileChunk {

	private final FileDetails details;
	
	private int count = 0;
	private int size = 0;
	private int firstLine;
	private byte[] compressed = null;
	
	private RecordStore recordStore;
	  
	private final static ArrayBlockingQueue<CompressThread> queue;
	private static boolean useThread = true;
	private static long totalTime = 0;
	private static int writeCount = 0;
	//private WeakHashMap<Integer, LineBase> lines = null;// new WeakHashMap<Integer, LineBase>(700);
	private HashMap<Integer, WeakReference<LineBase>> lines = null;// new WeakHashMap<Integer, LineBase>(700);

	static {
		int processors = Runtime.getRuntime().availableProcessors() + 1;
		queue = new ArrayBlockingQueue<CompressThread>(
				processors
		);
		
		for (int i = 0; i < processors; i++) {
			queue.offer(new CompressThread());
		}
		
		System.out.println("Processors: " + processors);
	}
	
	public FileChunk(FileDetails details, int theFirstLine) {
		super();
	
		this.details = details;

		if (details == null) {
			count = 1;
		} else {
			recordStore = details.getRecordStore();
		}
		firstLine = theFirstLine;
	}


	public int getCount() {
		int ret = count;
		if (recordStore != null) {
			ret = recordStore.getRecordCount();
		}
		
		return ret;
	}


	public  void add(int idx, byte[] rec) {

		uncompress();
		
		//synchronized (recordStore) {
			recordStore.add(idx, rec); 
			count = recordStore.getRecordCount();
			
			updateLines(idx, 1);
		//}
	}
	
	public void addLineToManagedLines(LineBase l) {
		
		getLines().put(l.chunkLine, new WeakReference<LineBase>(l));
	}


	public byte[] get(int idx) {
		uncompress();
		return recordStore.get(idx);
	}


	public void put(int idx, byte[] rec) {
		synchronized (this) {
			uncompress();
			recordStore.put(idx, rec);
			compressed = null;
		}
	} 


	public void remove(int idx) {

		uncompress();
		synchronized (recordStore) {
			recordStore.remove(idx);
			count = recordStore.getRecordCount();
			compressed = null;
			
			Integer key = idx;
			if (lines != null && lines.containsKey(key)) {
				lines.remove(key);
			}
			
			updateLines(idx, -1);
		}
	}
	
	protected LineBase getLine(int lineNo) {
		Integer cLine = (lineNo - firstLine);
		LineBase ret = null;
		
		getLines();
		synchronized (lines) {
			if (lines.containsKey(cLine)) {
				ret = lines.get(cLine).get();
			}
			if (ret == null) {
				ret = new LineChunk(details.getLayout(), this, cLine);
				lines.put(cLine, new WeakReference<LineBase>(ret));
			}
		}
		return ret;
	}
	
	
	protected LineBase getLineIfDefined(int lineNo) {
		Integer key = (lineNo - firstLine);
		LineBase ret = null;
		if (lines != null && lines.containsKey(key)) {
			ret = lines.get(key).get();
		}
		return ret;
	}
	
	protected List<LineBase> getActiveLines() {
		List<LineBase> ret = null;
		LineBase tmp;
		
		if (lines != null) {
			Set<Integer> keySet = lines.keySet();
			Integer[] keys = new Integer[keySet.size()];
			ret = new ArrayList<LineBase>(keys.length);
			
			keys = keySet.toArray(keys);
			
			for (Integer key : keys) {
				tmp = lines.get(key).get();
				if (tmp == null) {
					lines.remove(key);
				} else {
					ret.add(tmp);
				}
			}
		}
		return ret;
	}

	public void compress() {
		if (recordStore != null && compressed == null) {
			if (useThread) {
				try {
					queue.take().startWith(this);
				} catch (InterruptedException e) {
					doCompress();
				}
			} else {
				doCompress();
			}
		}
	}
	
	private void doCompress() {
		if (recordStore != null && compressed == null) {
			
			synchronized (this) {
				Runtime rt = Runtime.getRuntime();
				long free = rt.maxMemory() - rt.totalMemory();
				long time = System.nanoTime();

				String msg ;
				count = recordStore.getRecordCount();
				size = recordStore.getSize();
				compressed = recordStore.getCompressed();
				
				totalTime += System.nanoTime() - time; 
				
				if (writeCount++ > 10) {
					writeCount = 0;

					msg = " -- " + this.firstLine 
						+ " size: " + size 
						+ " Compressed: " + compressed.length 
						+ " " + count
						+ " Free: " + free + " " + rt.totalMemory()
						+ "\t Time:" + ( System.nanoTime() - time)
						+ "\t Total Time:" + ( totalTime / 100000000);
	
					System.out.println(msg);
					//Common.logMsg(AbsSSLogger.WARNING, msg, null);
				}
				
				if (compressed.length < size) {
					recordStore = null;
				} else {
					compressed = null;
				}
				details.registerCompress(this);
				clearOldLines();
			}
		}
	}
	
	private void uncompress() {
		if (recordStore == null) {
			recordStore = details.getEmptyRecordStore();
			
			recordStore.setCompressed(compressed, size, count);
			clearOldLines();
		}
		details.registerUse(this);
	}
	
	private void clearOldLines() {
	
		if (lines != null) {
			synchronized (lines) {
				Set<Integer> keySet = lines.keySet();
				Integer[] keys = new Integer[keySet.size()];
				LineBase tmp;
				
				keys = keySet.toArray(keys);
				
				for (Integer key : keys) {
					tmp = lines.get(key).get();
					if (tmp == null) {
						lines.remove(key);
					}
				}
			}
		}
	}
	
	public boolean hasRoomForMore(int len) {
		
		//System.out.println(" --> " + recordStore.getSize() + " " 
		//		+ details.len + " " + FileDetails.DATA_SIZE);
		
		uncompress();
		return (recordStore != null) 
			&& (recordStore.getSize() + len) < recordStore.getStoreSize();
	}


	public int getFirstLine() {
		return firstLine;
	}


	public void setFirstLine(int firstLine) {
		this.firstLine = firstLine;
	}
	
	public void setLayout(LayoutDetail layout) {
		List<LineBase> aLines = getActiveLines();
		if (aLines != null) {
			for (LineBase l : aLines) {
				l.setLayout(layout);
			}
		}
	}
	
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
	
	public int getSize() {
		return size;
	}


	private Map<Integer, WeakReference<LineBase>> getLines() {
		if (lines == null) {
			//lines = new HashMap<Integer, LineBase>();
			lines = new HashMap<Integer, WeakReference<LineBase>>();
		}

		return lines;
	}
	private void updateLines(int lineNo, int inc) {
		if (lines != null && lineNo < count) {
			synchronized (lines) {
				//int nullC = 0;
				//ArrayList<LineBase> ll = new ArrayList<LineBase>(lines.size() / 2 + 1);
				HashMap<Integer, WeakReference<LineBase>> tmap = new HashMap<Integer, WeakReference<LineBase>>(lines.size());
				WeakReference<LineBase> ref;

				Set<Integer> keySet = lines.keySet();
				Integer[] keys = new Integer[keySet.size()];
				keys = keySet.toArray(keys);

				LineBase line;
				int cmp = lineNo;
				if (inc > 0) {
					cmp -= 1;
				}
//				System.out.println("Update Lines " + keys.length 
//						+ " Line: " + lineNo + " " + inc + " " + getCount()
//						+ " Cmp: " + cmp + " > ");
				
				
//				try {
//					Arrays.sort(keys);
//				} catch (Exception e) {
//				}
				for (Integer key : keys) {
					ref = lines.get(key);
					line = ref.get();
					if (line == null) {
						lines.remove(key);
					} else if (key.intValue() > cmp) {
						lines.remove(key);

//						if (line.getChunkLine() >= 446 && line.getChunkLine() <= 452)
//						System.out.println("Update: " + line.getActualLine()
//								+ " :: " + lineNo + " <= " + line.getChunkLine() + " " + inc);
						line.setChunkLine(line.getChunkLine() + inc);

						tmap.put(line.chunkLine, ref);
					//	ll.add(line);
					//} else {
					//	System.out.print( " " + key);
					//} else if (key == null) {
					//	nullC += 1;
					}
				}
				
				lines.putAll(tmap);
//				LineBase l;
//				for (int i = 0; i < ll.size(); i++) {
//					l = ll.get(i);
//					
//					lines.put(l.getChunkLine(), l);
//				}
//				System.out.println();
//				System.out.println("Update Lines End: " + ll.size() + " nulls=" + nullC
//						+ " Keys Now= " + lines.keySet().size() + " "
//						+ lines.entrySet().size());
			}
//		} else {
//			System.out.println("Skip update " + (lines == null) + " " + lineNo + " < " + count);
		}
		
		compressed = null;
	}

	
	public static class CompressThread extends Thread {

		private FileChunk ch;
		public void startWith(FileChunk chunk) {
			ch = chunk;
			start();
		}
		
		@Override
		public synchronized void start() {
			try {
				ch.doCompress();
			} finally {
				try {
					queue.put(this);
				} catch (Exception e) {
					useThread = false;
					Common.logMsg("Compress Queue Error: Save the file and try again", e);
					Common.logMsg("Compress Queue Error: Save the file and try again", null);
					Common.logMsg("Compress Queue Error: Save the file and try again", null);
				}
			}
		}
		
	}
}
