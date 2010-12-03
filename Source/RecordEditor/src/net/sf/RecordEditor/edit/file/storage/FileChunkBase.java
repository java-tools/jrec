package net.sf.RecordEditor.edit.file.storage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;


@SuppressWarnings("unchecked")
public abstract class FileChunkBase<L extends AbstractChunkLine, S extends RecordStore> {

	protected final FileDetails details;
	
	protected int count = 0;
	private int size = 0;
	private int firstLine;
	protected byte[] compressed = null;
	
	protected S recordStore;
	  
	private final static ArrayBlockingQueue<CompressThread> queue;
	private static boolean useThread = true;
	private static long totalTime = 0;
	private static int writeCount = 0;
	//private WeakHashMap<Integer, LineBase> lines = null;// new WeakHashMap<Integer, LineBase>(700);
	protected HashMap<Integer, WeakReference<L>> lines = null;// new WeakHashMap<Integer, LineBase>(700);

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
	
	public FileChunkBase(FileDetails details, int theFirstLine) {
		super();
	
		this.details = details;

		if (details == null) {
			count = 1;
		} else {
			recordStore = (S) details.getRecordStore();
		}
		firstLine = theFirstLine;
	}


	public final int getCount() {
		int ret = count;
		if (recordStore != null) {
			ret = recordStore.getRecordCount();
		}
		
		return ret;
	}



	public abstract  void add(int idx, AbstractLine l);
	
	public final void addLineToManagedLines(L l) {
		
		getLines().put(l.getChunkLine(), new WeakReference<L>(l));
	}


	public abstract void put(int idx, AbstractLine l);


	public abstract AbstractLine removeLine(int lineNo);

	public final void remove(int idx) {

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
	
	protected abstract L getLine(int lineNo);
	
	protected abstract L getTempLine(int lineNo);
	
	
	protected final L getLineIfDefined(int lineNo) {
		Integer key = (lineNo - firstLine);
		L ret = null;
		if (lines != null && lines.containsKey(key)) {
			ret = (L) lines.get(key).get();
		}
		return ret;
	}
	
	protected final List<L> getActiveLines() {
		List<L> ret = null;
		L tmp;
		
		if (lines != null) {
			Set<Integer> keySet = lines.keySet();
			Integer[] keys = new Integer[keySet.size()];
			ret = new ArrayList<L>(keys.length);
			
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

	public final void compress() {
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
	
	private final void doCompress() {
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
				
				if (compressed != null && compressed.length < size) {
					recordStore = null;
				} else {
					compressed = null;
				}
				details.registerCompress(this);
				clearOldLines();
			}
		}
	}
	
	protected final void uncompress() {
		if (recordStore == null) {
			recordStore = (S) details.getEmptyRecordStore();
			
			recordStore.setCompressed(compressed, size, count);
			clearOldLines();
		}
		details.registerUse(this);
	}
	
	protected final void clearOldLines() {
	
		if (lines != null) {
			synchronized (lines) {
				Set<Integer> keySet = lines.keySet();
				Integer[] keys = new Integer[keySet.size()];
				AbstractChunkLine tmp;
				
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
	

	public abstract boolean hasRoomForMore(AbstractLine l);

	public final int getFirstLine() {
		return firstLine;
	}


	public final void setFirstLine(int firstLine) {
		this.firstLine = firstLine;
	}
	
	public final void setLayout(LayoutDetail layout) {
		List<L> aLines = getActiveLines();
		if (aLines != null) {
			for (AbstractChunkLine l : aLines) {
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
	
	public final int getSize() {
		return size;
	}


	protected final Map<Integer, WeakReference<L>> getLines() {
		if (lines == null) {
			//lines = new HashMap<Integer, LineBase>();
			lines = new HashMap<Integer, WeakReference<L>>();
		}

		return lines;
	}
	protected final void updateLines(int lineNo, int inc) {
		if (lines != null && lineNo < count) {
			synchronized (lines) {
				//int nullC = 0;
				//ArrayList<LineBase> ll = new ArrayList<LineBase>(lines.size() / 2 + 1);
				HashMap<Integer, WeakReference<L>> tmap = new HashMap<Integer, WeakReference<L>>(lines.size());
				WeakReference<L> ref;

				Set<Integer> keySet = lines.keySet();
				Integer[] keys = new Integer[keySet.size()];
				keys = keySet.toArray(keys);

				AbstractChunkLine line;
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

						tmap.put(line.getChunkLine(), ref);
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

		private FileChunkBase ch;
		public void startWith(FileChunkBase chunk) {
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
