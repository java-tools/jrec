package net.sf.RecordEditor.utils.fileStorage;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ProgramOptions;


@SuppressWarnings("unchecked")
public abstract class FileChunkBase<L extends AbstractChunkLine, S extends RecordStore> implements FileChunk<L, S> {

	protected final FileDetails details;
	
	protected int count = 0;
	private int size = 0;
	private int firstLine;
	protected byte[] compressed = null;
	
	protected S recordStore = null;
	
	private long diskAddress = Constants.NULL_INTEGER;
	
	  
	private static final  ArrayBlockingQueue<CompressThread> queue;
	private static boolean useThread = true;
	private static boolean COMPRESS_DISK = Common.OPTIONS.compressOption.get() != ProgramOptions.COMPRESS_NO;
	private static long totalTime = 0;
	private static int writeCount = 0;
	//private WeakHashMap<Integer, LineBase> lines = null;// new WeakHashMap<Integer, LineBase>(700);
	protected HashMap<Integer, WeakReference<L>> lines = null;// new WeakHashMap<Integer, LineBase>(700);

	static {
		int processors = FileDetails.PROCESSORS + 1;
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
		} else if (details.type == FileDetails.FIXED_LENGTH_BASEFILE
				|| details.type == FileDetails.VARIABLE_LENGTH_BASEFILE) {
		} else {
			recordStore = (S) details.getRecordStore();
		}
		firstLine = theFirstLine;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#getCount()
	 */ @Override
	public final int getCount() {
		int ret = count;
		if (recordStore != null) {
			ret = recordStore.getRecordCount();
		}
		
		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#add(net.sf.JRecord.Details.AbstractLine)
	 */ @Override
	public  void add(AbstractLine l) {
		add(getCount(), l);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#add(int, net.sf.JRecord.Details.AbstractLine)
	 */
	public abstract  void add(int idx, AbstractLine l);
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#addLineToManagedLines(L)
	 */ @Override
	public final void addLineToManagedLines(L l) {
		
		getLines().put(l.getChunkLine(), new WeakReference<L>(l));
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#put(int, net.sf.JRecord.Details.AbstractLine)
	 */ @Override
	public abstract void put(int idx, AbstractLine l);


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#removeLine(int)
	 */ @Override
	public abstract AbstractLine removeLine(int lineNo);

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#remove(int)
	 */ @Override
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
	
	@Override
	public final L getLineIfDefined(int lineNo) {
		Integer key = (lineNo - firstLine);
		L ret = null;
		if (lines != null && lines.containsKey(key)) {
			ret = (L) lines.get(key).get();
		}
		return ret;
	}
	
	@Override
	public final List<L> getActiveLines() {
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
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#split()
	 */
	public abstract List<FileChunkBase<L, S>> split();


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#compress()
	 */
	public final void compress() {
		if (isOkToCompress(false)) {
			if (useThread) {
				try {
					queue.take().startWith(this);
				} catch (InterruptedException e) {
					doCompress(true);
				}
			} else {
				doCompress(true);
			}
		}
	}
	
	private final void doCompress(boolean force) {
		
		synchronized (this) {
			if (isOkToCompress(force)) {	
				try {
					Runtime rt = Runtime.getRuntime();
					long free = rt.maxMemory() - rt.totalMemory();
					long time = System.nanoTime();
	
					String msg ;
					count = recordStore.getRecordCount();
					size = recordStore.getSize();
					compressed = recordStore.getCompressed();
					
					totalTime += System.nanoTime() - time; 
					
					if (compressed != null && writeCount++ > 10) {
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
					
					if (compressed != null 
					&& compressed.length + 40 < size * getStorageSize()) {
						recordStore = null;
					} else {
						compressed = null;
//						System.out.println("Compress Check "
//								+ (compressed.length+40) + " " + size
//								+ size * getStorageSize());
					}
					details.registerCompress(this);
				} catch (StackOverflowError e) {

				} catch (Exception e) {

				}
				clearOldLines();
			}
		}
	}
	
	private boolean isOkToCompress(boolean force) {
		synchronized (this) {
			return recordStore != null && compressed == null
				&& (force || details.isOkToCompress());
		}
	}
	
	protected int getStorageSize() {
		return 1;
	}
	
	protected void uncompress() {
		synchronized (this) {
			if (recordStore == null) {
				clearOldLines();

				if (diskAddress >= 0 && compressed == null) {
					ByteArray b = details.readFromDisk(diskAddress);
					if (b.compressed) {
						compressed = b.bytes;
					} else {
						recordStore = (S) details.getEmptyRecordStore();
						recordStore.setBytes(b.bytes, b.size, count);
						details.registerUse(this);
						return;
					}
				}
				
				if (compressed == null) {
					recordStore = (S) details.getRecordStore();
				} else {
					recordStore = (S) details.getEmptyRecordStore();
					recordStore.setCompressed(compressed, size, count);
				}
			}
		}
		details.registerUse(this);
	}
	
	protected void saveToDisk() {
		synchronized (this) {
			if (compressed != null || recordStore != null) {
				byte[] save;
				boolean compressedBytes = true;
				if (compressed == null) {
					doCompress(COMPRESS_DISK);
				}
				
				save = compressed;
				if (save == null) {
					save = recordStore.getBytes();
					compressedBytes = false;
//					System.out.println("++++++++++   No Compressed Data +++++++");
				}
				
				try {
//					System.out.print("Save: " + getSize() + " "
//							+ " " + compressedBytes + " ");
					diskAddress = details.saveToDisk(diskAddress, save, compressedBytes);
		
					compressed = null;
					recordStore = null;
				} catch (IOException e) {
					Common.logMsg("Error Saving to Disk", e);
				}
			}
		}
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
	

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#hasRoomForMore(net.sf.JRecord.Details.AbstractLine)
	 */
	public abstract boolean hasRoomForMore(AbstractLine l);

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#getFirstLine()
	 */
	public final int getFirstLine() {
		return firstLine;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#setFirstLine(int)
	 */
	public final void setFirstLine(int firstLine) {
		this.firstLine = firstLine;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#setLayout(net.sf.JRecord.Details.LayoutDetail)
	 */
	public final void setLayout(LayoutDetail layout) {
		List<L> aLines = getActiveLines();
		if (aLines != null) {
			for (AbstractChunkLine l : aLines) {
				l.setLayout(layout);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#getSpaceUsed()
	 */
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
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.FileChunk#getSize()
	 */
	public int getSize() {
		if(recordStore != null) {
			size = recordStore.getSize();
		}
		return size;
	}


	protected final Map<Integer, WeakReference<L>> getLines() {
		if (lines == null) {
			//lines = new HashMap<Integer, LineBase>();
			lines = new HashMap<Integer, WeakReference<L>>();
		}

		return lines;
	}
	public long getDiskAddress() {
		return diskAddress;
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

//						if (line.getChunkLine() >= 498 && line.getChunkLine() <= 502) {
//						System.out.println("Update: " + line.getActualLine()
//								+ " :: " + lineNo + " <= " + line.getChunkLine() + " + " + inc);
//						}
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
		public void start() {
			try {
				ch.doCompress(true);
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
