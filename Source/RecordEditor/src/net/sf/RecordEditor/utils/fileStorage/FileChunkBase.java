package net.sf.RecordEditor.utils.fileStorage;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.trove.map.TIntObjectMap;
import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;
import net.sf.RecordEditor.trove.set.TIntSet;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.ProgramOptions;


@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class FileChunkBase<L extends IChunkLine, S extends IRecordStore> implements IFileChunk<L, S> {

	private static final int NUMBER_OF_COMPRESS_PROCESSES = FileDetails.PROCESSORS + 1;
	protected FileDetails details;

	protected int count = 0;
	private int size = 0;
	private int firstLine;
	private long firstTextPosition;
	
	protected byte[] compressed = null;

	protected S recordStore = null;

	private long diskAddress = Constants.NULL_INTEGER;


	private static final  ArrayBlockingQueue<CompressThread> queue;
	private static boolean useThread = true;
	private static boolean COMPRESS_DISK = Common.OPTIONS.compressOption.get() != ProgramOptions.COMPRESS_NO;
	private static long totalTime = 0;
	private static int writeCount = 0;
	//private WeakHashMap<Integer, LineBase> lines = null;// new WeakHashMap<Integer, LineBase>(700);
	//protected HashMap<Integer, WeakReference<L>> lines = null;// new WeakHashMap<Integer, LineBase>(700);
	protected TIntObjectHashMap<WeakReference<L>> lines = null;
	
	static {
		queue = new ArrayBlockingQueue<CompressThread>(
				NUMBER_OF_COMPRESS_PROCESSES
		);

		for (int i = 0; i < NUMBER_OF_COMPRESS_PROCESSES; i++) {
			queue.offer(new CompressThread());
		}
	}
	
	

	public FileChunkBase(FileDetails details, int theFirstLine, long theFirstTextPosition) {
		super();

		this.details = details;

		if (details == null) {
			count = 1;
		} else if (details.type == FileDetails.FIXED_LENGTH_BASEFILE
				|| details.type == FileDetails.VARIABLE_LENGTH_BASEFILE) {
		} else {
			recordStore = (S) details.newRecordStore();
		}
		firstLine = theFirstLine;
		firstTextPosition = theFirstTextPosition;
	}

	
	public void updateFrom(FileChunkBase fc) {
		
		if (details.isCompatible(fc.details)) {
			fc.loadFromDisk();
			if (fc.compressed != null) {
				this.compressed = fc.compressed.clone();
				this.recordStore = null;
			} else if (fc.recordStore != null) {
				add(0,  fc.recordStore);
				compressed = null;
			} else {
				new RuntimeException("Internal Error: No details in Chunk");
			}
			
			this.count = fc.getCount();
			this.size = fc.getSize();

			return;
		}
		throw new RuntimeException("Internal Error: wrong File Chunk type=" + fc.details.type + " Expecting=" +details.type);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#getCount()
	 */ @Override
	public final int getCount() {
		int ret = count;
		if (recordStore != null) {
			ret = recordStore.getRecordCount();
		}

		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#add(net.sf.JRecord.Details.AbstractLine)
	 */ @Override
	public  void add(AbstractLine l) {
		add(getCount(), l);
	}

	 
	@Override
	public void add(int pos, IRecordStore store) {
		uncompress();
		recordStore.add(pos, store);
	
		updateLines(pos, store.getRecordCount());
		count = recordStore.getRecordCount();
		size = recordStore.getSize();
	}
	
	
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#add(int, net.sf.JRecord.Details.AbstractLine)
//	 */
//	public abstract  void add(int idx, AbstractLine l);

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#addLineToManagedLines(L)
	 */ @Override
	public final void addLineToManagedLines(L l) {

		getLines().put(l.getChunkLine(), new WeakReference<L>(l));
	}


//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#put(int, net.sf.JRecord.Details.AbstractLine)
//	 */ @Override
//	public abstract void put(int idx, AbstractLine l);
//
//
//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#removeLine(int)
//	 */ @Override
//	public abstract AbstractLine removeLine(int lineNo);

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#remove(int)
	 */ @Override
	public final void remove(int idx) {

		uncompress();
		synchronized (recordStore) {
			recordStore.remove(idx);
			count = recordStore.getRecordCount();
			compressed = null;

			//Integer key = idx;
			if (lines != null && lines.containsKey(idx)) {
				synchronized (lines) {
					WeakReference<L> wrLine = lines.get(idx);
					L l = wrLine.get();
					if (l != null) {
						l.setDead();
					}
					lines.remove(idx);
				}
			}

			updateLines(idx, -1);
		}
	}
	 
	 
	@Override
	public void removeLineReference(int lineNo) {
		synchronized (lines) {
			lines.remove(lineNo - getFirstLine());
		}
	}



	@Override
	public final L getLineIfDefined(int lineNo) {
		int key = (lineNo - firstLine);
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
			synchronized (lines) {
				TIntSet keySet = lines.keySet();
				int[] keys = keySet.toArray();
				ret = new ArrayList<L>(keys.length);
	
				for (int key : keys) {
					tmp = lines.get(key).get();
					if (tmp == null) {
						lines.remove(key);
					} else {
						ret.add(tmp);
					}
				}
			}
		}
		return ret;
	}

	@Override
	public final void clear() {
		compressed = null;
		recordStore = null;
		details = null;				
	}

//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#split()
//	 */
//	public abstract List<FileChunkBase<L, S>> split();


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#compress()
	 */
	public final void compress() {
		if (isOkToCompress(false)) {
			if (useThread) {
				try {
					queue.take().startWith(this);
				} catch (InterruptedException e) {
					e.printStackTrace();
					doCompress(true);
				}
			} else {
				doCompress(true);
			}
		}
		clearOldLines();
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
					System.out.println("Stack Overflow: " + e.toString());
				} catch (Exception e) {
					System.out.println("Error: " + e.toString());
				}
			}
			clearOldLines();
		}
	}

	private boolean isOkToCompress(boolean force) {
		synchronized (this) {
			if (compressed != null) {
				recordStore = null;
				return false;
			}
			return recordStore != null // && compressed == null
				&& Common.OPTIONS.doCompress.isSelected()
				&& (    force 
					|| (   (useThread && FileDetails.PROCESSORS > 1 && details.isOkToCompress(FileDetails.AGGRESSIVE_CHECK))
						&&  Common.OPTIONS.agressiveCompress.isSelected()
						&& (   queue.size() == NUMBER_OF_COMPRESS_PROCESSES) 
					       || (useThread && queue.size() > 2 && (queue.size() > NUMBER_OF_COMPRESS_PROCESSES / 2))) 
					||  details.isOkToCompress(FileDetails.NORMAL_CHECK)); 
		}
	}

	protected int getStorageSize() {
		return 1;
	}

	protected void uncompress() {
		if (recordStore == null) {
			synchronized (this) {
				if (recordStore == null) {
					clearOldLines();
	
					loadFromDisk();
				
				
					if (recordStore == null) {
						if (compressed == null) {
							recordStore = (S) details.newRecordStore();
						} else {
							recordStore = (S) details.getEmptyRecordStore();
							recordStore.setCompressed(compressed, size, count);
						}
					}
				}
			}
		}

		details.registerUse(this);
	}
	
	private void loadFromDisk() {
		
		if (diskAddress >= 0 && compressed == null) {
			ByteArray b = details.readFromDisk(diskAddress);
			if (b.compressed) {
				compressed = b.bytes;
			} else {
				recordStore = (S) details.getEmptyRecordStore();
				recordStore.setBytes(b.bytes, b.size, count);
			}
		}
	}

	protected void saveToDisk() {
		if (compressed != null || recordStore != null) {
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
						e.printStackTrace();
						Common.logMsg("Error Saving to Spill file", e);
					}
				}
			}
		}
	}

	protected final void clearOldLines() {

		if (lines != null) {
			synchronized (lines) {
				TIntSet keySet = lines.keySet();
				int[] keys = keySet.toArray();;
				IChunkLine tmp;

				for (int key : keys) {
					tmp = lines.get(key).get();
					if (tmp == null) {
						lines.remove(key);
					}
				}
			}
			if (lines.isEmpty()) {
				lines = null;
			}
		}
	}


//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#hasRoomForMore(net.sf.JRecord.Details.AbstractLine)
//	 */
//	public abstract boolean hasRoomForMore(AbstractLine l);

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#getFirstLine()
	 */
	public final int getFirstLine() {
		return firstLine;
	}

	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#setFirstLine(int)
	 */
	public final void setFirstLine(int firstLine) {
		this.firstLine = firstLine;
	}

	@Override
	public final long getFirstTextPosition() {
		return firstTextPosition;
	}
	
	

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IFileChunk#isDocumentViewAvailable()
	 */
	@Override
	public boolean isDocumentViewAvailable() {
		return details.isDocumentViewAvailable();
	}


	/**
	 * @param firstTextPosition the firstTextPosition to set
	 */
	@Override
	public final void setFirstTextPosition(long firstTextPosition) {
		this.firstTextPosition = firstTextPosition;
	}

	
	@Override
	public final long getTextSize() {
		return calculateTextPosition(getSize(), getCount());
	}
	
	
	@Override
	public final long calculateTextPosition(long size, int count) {
		return size + count * (1 - details.recordOverhead);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#setLayout(net.sf.JRecord.Details.LayoutDetail)
	 */
	public final void setLayout(LayoutDetail layout) {
		List<L> aLines = getActiveLines();
		if (aLines != null) {
			for (IChunkLine l : aLines) {
				l.setLayout(layout);
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#getSpaceUsed()
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
	 * @see net.sf.RecordEditor.utils.fileStorage.FileChunk#getSize()
	 */
	public int getSize() {
		if(recordStore != null) {
			size = recordStore.getSize();
		}
		return size;
	}


	protected final TIntObjectMap<WeakReference<L>> getLines() {
		if (lines == null) {
			//lines = new HashMap<Integer, LineBase>();
			lines = new TIntObjectHashMap<WeakReference<L>>();
		}

		return lines;
	}
	
	public long getDiskAddress() {
		return diskAddress;
	}

	@Override
	public final S getRecordStore() {
		uncompress();
		return recordStore;
	}


	protected final void updateLines(int lineNo, int inc) {
		if (lines != null && lineNo < count) {
			synchronized (lines) {
				//int nullC = 0;
				//ArrayList<LineBase> ll = new ArrayList<LineBase>(lines.size() / 2 + 1);
				TIntObjectHashMap<WeakReference<L>> tmap = new TIntObjectHashMap<WeakReference<L>>(lines.size());
				WeakReference<L> ref;

				TIntSet keySet = lines.keySet();
				int[] keys = keySet.toArray();

				IChunkLine line;
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
				for (int key : keys) {
					ref = lines.get(key);
					line = ref.get();
					if (line == null) {
						lines.remove(key);
					} else if (key > cmp) {
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

	@Override
	public List<FileChunkBase<L, S>> split() {
		return bldChunkList((S[]) recordStore.split(details.dataSize));
	}
	
	@Override
	public List<FileChunkBase<L, S>> splitAt(int pos) {
		uncompress();
		return bldChunkList((S[]) recordStore.splitAt(pos));
	}
	
	
	private List<FileChunkBase<L, S>> bldChunkList(S[] rs) {
		ArrayList<FileChunkBase<L, S>> ret = new ArrayList<FileChunkBase<L, S>>();
		FileChunkBase<L, S> fc;
		int linesSoFar = rs[0].getRecordCount();
		long textSoFar = calculateTextPosition(rs[0].getSize(), rs[0].getRecordCount());
		
		if (linesSoFar < 0) {
			System.out.println(" !!   " + this.getFirstLine()
			+ " "  + this.recordStore.getRecordCount()
			+ " "  + this.recordStore.getSize());
			
		}
//		System.out.println(" !!   " + this.getFirstLine()
//				+ " "  + this.recordStore.recordCount
//				+ " "  + this.recordStore.getSize());
//		System.out.println(" ## " + 0
//				+ " " + this.getFirstLine()
//				+ " " + rs[0].recordCount
//				+ " " + rs[0].getSize());
		for (int i = 1; i < rs.length; i++) {
			if (rs[i] != null) {
				if (rs[i].getRecordCount() < 0) {
					System.out.println(" !!   " + rs[i].getRecordCount());
				}
				fc = newChunkLine(linesSoFar, textSoFar);//new FileChunkLine(details, this.getFirstLine() + linesSoFar);
				fc.recordStore = rs[i];
				ret.add(fc);
				
				fc.count = fc.recordStore.getRecordCount();
				
				linesSoFar += rs[i].getRecordCount();
				textSoFar += calculateTextPosition(rs[i].getSize(), rs[i].getRecordCount());
				
				details.registerUse(fc);
				details.registerUse(this);
//				System.out.println(" ** " + i
//						+ " " + fc.getFirstLine()
//						+ " " + fc.recordStore.recordCount
//						+ " " + fc.recordStore.getSize());
			}
		}
		
		//System.out.println();
		if (lines != null) {
			synchronized (lines) {
				TIntSet keySet = lines.keySet();
				WeakReference<L> ref;
				int[] keys =  keySet.toArray();
				int ikey;

				IChunkLine line;
				FileChunkBase f;
				
				for (int key : keys) {
					ikey = key;
					if (ikey >= rs[0].getRecordCount()) {
						linesSoFar = rs[0].getRecordCount();

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
									
//									if (ikey > 41 && ikey < 46) {
//									System.out.println(" Renumber: " 
//											+ j + ": " + ikey + " "
//											+ linesSoFar + " " + line.getChunkLine()
//											+ " " + f.getFirstLine());
//									}
								}
								
								lines.remove(key);

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
		
		recordStore = rs[0];
		compressed = null;
		count = recordStore.getRecordCount();

		return ret;
	}
	
	protected  abstract FileChunkBase<L, S> newChunkLine(int linesSoFar, long textSoFar);



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
