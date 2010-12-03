package net.sf.RecordEditor.edit.file.storage;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.common.Common;


@SuppressWarnings("unchecked")
public class DataStoreLarge<L extends AbstractChunkLine, R extends RecordStore>
extends AbstractList<AbstractLine>
implements DataStore<AbstractLine> {

//	private static long time = 0;
//	private static long totalTime = 0;
	
	private ArrayList<FileChunkBase<L, R>> chunks 
			= new ArrayList<FileChunkBase<L, R>>(1000);
	 
	private FileDetails fileDetails;
	
	private int tempLineCount = 0;
	private AbstractChunkLine[] tempLineArray = new AbstractChunkLine[250]; 
	private HashMap<Integer, L> tempLines = new HashMap<Integer, L>(300);
	//private LayoutDetail layout;
	
//	private WeakHashMap<Integer, LineBase> lines = new WeakHashMap<Integer, LineBase>(700);

	
	public DataStoreLarge(LayoutDetail recordLayout, int type, int recLength) {
		
		fileDetails = new FileDetails(recordLayout, type, recLength);
		
		chunks.add(getFileChunk(fileDetails, 0));
	}
	
	@Override
	public AbstractChunkLine getTempLine(int idx) {
		L ret = null;
		Integer key = idx;
	
		ChunkLineDtls cd = findLine(idx);
		if (cd != null) {
			ret =  cd.chunk.getLineIfDefined(idx);
			
			if (ret == null) {
				ret = tempLines.get(idx);
			}
			
			if (ret == null) {
				if (tempLineArray[tempLineCount] != null) {
					tempLines.remove(
							tempLineArray[tempLineCount].getChunk().getFirstLine()
							+ tempLineArray[tempLineCount].getChunkLine()
					);
				}
				
				ret = cd.chunk.getTempLine(idx);
				
				tempLineArray[tempLineCount++] = ret;
				tempLines.put(key, ret);
				if (tempLineCount >= tempLineArray.length) {
					tempLineCount = 0;
				}
			}
		} else {
			throw new RuntimeException("Can not find line: " + idx
					+ " ");
		}
	
		
		return ret;
	}

	@Override
	public void add(int lineNo, AbstractLine line) {
		ChunkLineDtls cd = findLine(lineNo);
		
		if (cd == null) {
			add(line);
		} else {
			cd.chunk.add(cd.lineInChunk, line);
	
			updateLines(lineNo, 1);
		}
	}

	@Override
	public boolean add(AbstractLine line) {
		addToChunks(chunks, line);
		return true;
	}
	
	@Override
	public AbstractLine addCopy(int lineNo, AbstractLine line) {
		add(lineNo, line);
		return get(lineNo);
	}
	

	private void addToChunks(ArrayList<FileChunkBase<L,R>> chunks, AbstractLine line) {
		int s = chunks.size() - 1;
		FileChunkBase<L,R> ch = chunks.get(s);
		
		if (! ch.hasRoomForMore(line)) {
			if (s > 2) {
				chunks.get(s).compress();
			}
			ch = getFileChunk(fileDetails, ch.getFirstLine() + ch.getCount());
			chunks.add(ch);
			s += 1;

		}
		ch.add(ch.getCount(), line);
	}

	@Override
	public void clear() {
		clearTempLines();
		chunks.clear();
		chunks.add(getFileChunk(fileDetails, 0));
	}

	@Override
	public AbstractLine get(int idx) {
		AbstractLine ret = null;
		ChunkLineDtls cd = findLine(idx);
		
		if (cd != null) {
			ret = cd.chunk.getLine(idx);
		} else { 
			throw new RuntimeException("line not found: " + idx);
		}
		
		return ret;
	}


	@Override
	public int indexOf(Object arg0) {
		if (arg0 instanceof AbstractChunkLine) {
			AbstractChunkLine lc = (AbstractChunkLine) arg0;
			return (lc.getActualLine());
		}
		return -1;
	}

	@Override
	public AbstractLine remove(int lineNo) {
		AbstractLine lc = null;
		
		ChunkLineDtls cd = findLine(lineNo);
		
		if (cd != null) {
			lc = cd.chunk.removeLine(cd.lineInChunk);
				
//				new Line(fileDetails.getLayout(), cd.chunk.get(cd.lineInChunk));
			
//			String s = " Remove Line: " 
//					+ lineNo + " " + cd.chunksFirstLine + " " + cd.lineInChunk
//					+ " ==> " + lc.getField(0, 1) 
//					+ "\t" +lc.getField(0, 2)
//					+ "\t" +lc.getField(0, 3)
//					+ "\t" +lc.getField(0, 4)
//					+ "\t" +lc.getField(0, 5)
//					+ "\t" +cd.chunk.getCount();
			
//			cd.chunk.remove(cd.lineInChunk);
			
//			System.out.println(s + "\t" +cd.chunk.getCount());
						
			updateLines(lineNo, -1);
			
			if (cd.chunk.getCount() == 0 && chunks.size() > 1) {
				chunks.remove(cd.chunk);
			}
		}
		
		return lc;
	}

	@Override
	public AbstractLine set(int lineNo, AbstractLine line) {
		ChunkLineDtls cd = findLine(lineNo);

		if (cd == null) {
			add(line);
		} else {
			cd.chunk.put(cd.lineInChunk, line);
		}

		return line;
	}

	@Override
	public boolean remove(Object arg0) {
		int lineNo = indexOf(arg0);
		boolean found = (lineNo >= 0);
		if (found) {
			remove(lineNo);
		}
		return found;
	}

	@Override
	public int size() {

		FileChunkBase<L,R> chunk = chunks.get(chunks.size() - 1);
		
		return chunk.getFirstLine() + chunk.getCount();
	}
	
	
	private ChunkLineDtls findLine(int lineNo) {
		ChunkLineDtls ret = null;
//		System.out.println();
//		System.out.println("Searching for: " + lineNo);
		int idx;
		if (lineNo < chunks.get(0).getCount()) {
			idx = 0;
		} else {
			idx = Collections.binarySearch(
				chunks,
				getFileChunk(null, lineNo),
				new Comparator<FileChunkBase<L,R>>() {

					@Override
					public int compare(FileChunkBase<L,R> arg0, FileChunkBase<L,R> arg1) {
						int ret = 0;
						if (arg0.getFirstLine() + arg0.getCount() <= arg1.getFirstLine()) {
							ret = -1;
						} else if (arg1.getFirstLine() + arg1.getCount() <= arg0.getFirstLine()) {
							ret = 1;
						}
						
//						System.out.println("+++++ >> " + arg0.getFirstLine()
//								+ " " + arg0.getCount()
//								+ " :: " + arg1.getFirstLine() 
//								+ " " + arg1.getCount() + " " + ret);
						return ret;
					}
			
				});
		}
		
		if (idx >= 0) {
			FileChunkBase<L,R> chunk = chunks.get(idx);
			ret = new ChunkLineDtls(lineNo - chunk.getFirstLine(), chunk);
		}
		return ret;
		
//		int currLine = 0;
//		FileChunk chunk;
//		for (int i = 0; i < chunks.size(); i++) {
//			chunk = chunks.get(i);
//			if (currLine + chunk.getCount() > lineNo) {
//				return new chunkLineDtls(currLine, lineNo - currLine, chunk);
//			}
//		}
		
//		return null;
	}

	private void clearTempLines() {
		tempLineCount = 0;
		tempLines.clear();
		for (int i = 0; i < tempLineArray.length; i++) {
			tempLineArray[i] = null;
		}
	}
	
	private void updateLines(int lineNo, int inc) {
		clearTempLines();
		
		synchronized (chunks) {	
			for (FileChunkBase<L,R> fcc : chunks) {
				if (fcc.getFirstLine() > lineNo) {
					fcc.setFirstLine(fcc.getFirstLine() + inc);
				}
			}
		}
	}

	@Override
	public void sort(final Comparator<AbstractLine> comp) {
		Runtime runtime = Runtime.getRuntime();  
		long free = runtime.maxMemory() - runtime.totalMemory();
		long size = this.size();
		long using = 0;
		
		for (FileChunkBase<L,R> ch : chunks) {
			using += ch.getSpaceUsed();
		}

		
		if (size > Integer.MAX_VALUE
		|| free < using * 1.2 + 2000000) {
			Common.logMsg("\nNot enough memory to do a sort !!!", null);
			Common.logMsg("Not enough memory to do a sort !!!", null);
			throw new RuntimeException("Not enough memory to sort");
		} else {
			synchronized (this) {
				Integer[] array = new Integer[(int) size];
				for (int i = 0; i < array.length; i++) {
					array[i] = i;
				}
				
				Arrays.sort(array, new Comparator<Integer>() {
					@Override
					public int compare(Integer arg0, Integer arg1) {

						return comp.compare(
								getTempLine(arg0.intValue()),
								getTempLine(arg1.intValue())
						);
					}
				});
				
				int i;
				AbstractChunkLine tmp;
				L l;
				//ArrayList<LineBase> activeLines = new ArrayList<LineBase>(500);
				HashMap<Integer, L> lines = new HashMap<Integer, L>(5000);
				ArrayList<FileChunkBase<L,R>> newChunks = new ArrayList<FileChunkBase<L,R>>(chunks.size());
				List<L> chunkManagedLines;
				FileChunkBase<L,R> fc;
				Integer lineNo;
				
				for (FileChunkBase<L,R> f : chunks) {
					if ((chunkManagedLines = f.getActiveLines()) != null) {
						for (L lb : chunkManagedLines) {
							lines.put(lb.getActualLine(), lb);
						}
					}
				}
				
				newChunks.add(getFileChunk(fileDetails, 0));
				
				for (i = 0; i < array.length; i++) {
					tmp = getTempLine(array[i].intValue());
					addToChunks(newChunks, tmp);
					
//					System.out.print(" :: --> " + i 
//							+ " " + tmp.getClass().getName()
//							+ " " + tmp.getActualLine() 
//							+ " " + tmp.getChunkLine() + " " + (tmp.getChunk() == null)
//							+ " " + chunks.indexOf(tmp.getChunk())
//							+ "\t" + tmp.getField(0,1)
//							+ "\t" + tmp.getField(0,2)
//							+ "\t" + tmp.getField(0,3)
//							+ "\t" + tmp.getField(0,4)
//					);
					
					lineNo = tmp.getActualLine();
					if (lines.containsKey(lineNo)) {
						l = lines.get(lineNo);
						fc = newChunks.get(newChunks.size() - 1);
						l.setChunk(fc);
						l.setChunkLine(fc.getCount() - 1);
						fc.addLineToManagedLines(l); 
						
						System.out.print(" active");
					}
					
					System.out.println();
				}
				
				clearTempLines();

				chunks = newChunks;
			}
		}
	}

	@Override
	public void setLayout(AbstractLayoutDetails layout) {
		if (layout instanceof LayoutDetail) {
			LayoutDetail l = (LayoutDetail) layout;
			clearTempLines();
			fileDetails.setLayout(l);
			for (FileChunkBase ch : chunks) {
				ch.setLayout(l);
			}
		}
	}

	@Override
	public DataStore<AbstractLine> newDataStore() {
		return new DataStoreStd<AbstractLine>(fileDetails.getLayout(), this);
	}
	

	@Override
	public String getSizeDisplay() {
		long kb = 0;
		long using = 0;
		
		for (FileChunkBase<L,R> ch : chunks) {
			kb += ch.getSize();
			using += ch.getSpaceUsed();
		}
		return   "        Record Count : " + size()
			+  "\n           Size (kb) : " + (kb / 1024)
			+  "\nCompressed Size (kb) : " + (using / 1024);
	}
	
	private FileChunkBase<L, R> getFileChunk(FileDetails details, int theFirstLine) {
		if (this.fileDetails.type == FileDetails.CHAR_LINE) {
			return (FileChunkBase<L, R>) new FileChunkCharLine(details, theFirstLine);
		}
		return (FileChunkBase<L, R>) new FileChunk(details, theFirstLine);
	}

	private class ChunkLineDtls {
		public final int chunksFirstLine, lineInChunk;
		public final FileChunkBase<L, R> chunk;
		
		public ChunkLineDtls( int lineInChunk, FileChunkBase<L, R> chunk) {
			super();
			this.chunksFirstLine = chunk.getFirstLine();
			this.lineInChunk = lineInChunk;
			this.chunk = chunk;
		}		
	}	
}