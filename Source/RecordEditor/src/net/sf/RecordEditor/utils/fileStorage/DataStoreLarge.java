package net.sf.RecordEditor.utils.fileStorage;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.JRecord.ByteIO.IByteReader;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
//import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;
import net.sf.RecordEditor.utils.approxSearch.ApproximateSearch;
import net.sf.RecordEditor.utils.approxSearch.CheckLong;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.GcManager;
import net.sf.RecordEditor.utils.params.ProgramOptions;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;


@SuppressWarnings({ "rawtypes" })
public class DataStoreLarge<L extends IChunkLine, R extends IRecordStore>
extends AbstractList<AbstractLine>
implements IDataStore<AbstractLine>, ITextInterface, IChunkLengthChangedListner {

//	private static long time = 0;
//	private static long totalTime = 0;

	private static final String LINE_NOT_FOUND = "Line not found: {0}";

	private ArrayList<IFileChunk<L, R>> chunks
			= new ArrayList<IFileChunk<L, R>>(500);

	private FileDetails fileDetails;

//	private int tempLineCount = 0;
	L lastLine = null;
	int lastLineNumber = -1;
//	private IChunkLine[] tempLineArray = new IChunkLine[250];
//	private TIntObjectHashMap<L> tempLines = new TIntObjectHashMap<L>(300);
	private long filePosition = 0;
	private int lastFindIndex = -1;
	
	private SortParent sortMaster = new SortParent();
	
	private boolean embeddedCr = false;
	
	//private LayoutDetail layout;

//	private WeakHashMap<Integer, LineBase> lines = new WeakHashMap<Integer, LineBase>(700);

	
	private Comparator<IFileChunk<L,R>> chunkComparator = new Comparator<IFileChunk<L,R>>() {

		@Override
		public int compare(IFileChunk<L,R> arg0, IFileChunk<L,R> arg1) {
			int ret = 0;
			if (arg0.getFirstLine() + arg0.getCount() <= arg1.getFirstLine()) {
				ret = -1;
			} else if (arg1.getFirstLine() + arg1.getCount() <= arg0.getFirstLine()) {
				ret = 1;
			}

//			System.out.println("+++++ >> " + arg0.getFirstLine()
//					+ " " + arg0.getCount()
//					+ " :: " + arg1.getFirstLine()
//					+ " " + arg1.getCount() + " " + ret);
			return ret;
		}

	};

	public DataStoreLarge(LayoutDetail recordLayout, int type, int recLength) {
		this(recordLayout, type, recLength, null, "");
	}

	public DataStoreLarge(LayoutDetail recordLayout, int type,
			int recLength, IByteReader byteReader, String fname) {
		fileDetails = new FileDetails(recordLayout, type, recLength, this);
		fileDetails.setByteReader(byteReader);
		fileDetails.setMainFileName(fname);

		chunks.add(newFileChunk(fileDetails, 0, 0));
	}

	private void setFixedLengthFileName(String mainFileName) {
		File f = new File(mainFileName);
		fileDetails.setMainFileName(mainFileName);
		if (f.exists()) {
			int recordPerBlock = fileDetails.getFixedLengthRecordsPerBlock();
			long fileLength = f.length();
			long c = (fileLength - 1) / fileDetails.recordLength + 1;
			int count = (int) c;

			int l;
			chunks.clear();

			for (int i = 0; i < count; i+=recordPerBlock) {
				l = Math.min(recordPerBlock, count - i);
				chunks.add(
					(IFileChunk<L, R>) new FileChunkBfFixedLength(fileDetails, i, i * (fileDetails.recordLength + 1) , l)
				);
				
			}

			if (! Common.TEST_MODE) {
				(new LoadFile()).start();
			}
		}


	}

	
	
	
	@Override
	public IChunkLine getTempLineRE(int idx) {
		L ret = null;
//		Integer key = idx;

		fileDetails.setReading(false);
		ChunkLineDtls cd = findLine(idx);
		if (cd != null) {
			ret =  cd.chunk.getLineIfDefined(idx);
			
			if (ret == null && lastLine != null && idx == lastLineNumber) {
				ret = lastLine;
			}
			
			if (ret == null) {
				ret = cd.chunk.getTempLine(idx);
				lastLine = ret;
				lastLineNumber = idx;
			}
			
//			if (ret == null) {
//				ret = tempLines.get(idx);
//			}
//
//			if (ret == null) {
//				if (tempLineArray[tempLineCount] != null) {
//					tempLines.remove(
//							tempLineArray[tempLineCount].getChunk().getFirstLine()
//							+ tempLineArray[tempLineCount].getChunkLine()
//					);
//				}
//
//				ret = cd.chunk.getTempLine(idx);
//
//				tempLineArray[tempLineCount++] = ret;
//				tempLines.put(idx, ret);
//				if (tempLineCount >= tempLineArray.length) {
//					tempLineCount = 0;
//				}
//			}
		} else {
			throw new RecordRunTimeException(LINE_NOT_FOUND, Integer.toString(idx));
		}


		return ret;
	}
	
	

	@Override
	public AbstractLine getNewLineRE(int index) {
		AbstractLine ret = null;

		fileDetails.setReading(false);
		ChunkLineDtls cd = findLine(index);
		if (cd != null) {
			ret = cd.chunk.getTempLine(index).getNewDataLine();
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

			checkForSplit(cd.index);
			fileDetails.setReading(false);
		}
	}


	@Override
	public void addChildViewRE(ISortNotify notify) {
		sortMaster.add(notify);
	}
	
	
	@Override
	public boolean add(AbstractLine line) {
		addToChunks(chunks, line);
		if ((!embeddedCr) && (line != null)) {
			String s = line.getFullLine();
			embeddedCr = s != null && s.contains("\n");
		}
		return true;
	}

//	@Override
//	public AbstractLine addCopy(int lineNo, AbstractLine line) {
//		add(lineNo, line);
//		return get(lineNo);
//	}
//

	private void addToChunks(ArrayList<IFileChunk<L,R>> chunks, AbstractLine line) {
		int chIdx = chunks.size() - 1;
		IFileChunk<L,R> ch = chunks.get(chIdx);

		if (! ch.hasRoomForMore(line)) {
			ch = newFileChunk(fileDetails, ch);
			if (chIdx > 4) {
				chunks.get(chIdx).compress();
			}
			chunks.add(ch);
		}

		ch.add(line);

		if (fileDetails.isReading() && fileDetails.getByteReader() != null) {
			filePosition = fileDetails.getByteReader().getBytesRead();
		}
	}

	

	@Override
	public void addCopyRE(int pos, IDataStore<? extends AbstractLine> cpyLines, IProgressDisplay progressDisplay) {
		//TODO Rewrite ....
		//TODO Rewrite ....

		int numberOfLines = cpyLines.size();
		int aveRecordSize = getAverageRecordSize();
		int cpySize = aveRecordSize * numberOfLines;
		int defaultBlkSize = Common.OPTIONS.chunkSize.get();
		ChunkLineDtls chDtls = findLine(pos);

		embeddedCr = embeddedCr || cpyLines.hasEmbeddedCr();
		updateLines(pos, cpyLines.size());
		if (chDtls == null) {
			int idx = chunks.size()-1;
			chDtls = new ChunkLineDtls(pos - chunks.get(idx).getFirstLine(), chunks.get(idx), idx, pos);
		}
		IFileChunk<L, R> ch = chDtls.chunk;
		if (cpySize + ch.getSize() < defaultBlkSize * 2 || numberOfLines < 32) {
			//ch.addAll(pos - ch.getFirstLine(), cpyLines, 0);
			addCopyAll(ch, pos, cpyLines);
		} else {
			this.splitAt(pos);

			IFileChunk<L, R> nextChunk = chunks.get(chDtls.index + 1);
			if (numberOfLines < 50 || cpySize < defaultBlkSize / 4) {
				if (ch.getSize() > nextChunk.getSize()) {
					//nextChunk.addAll(pos - nextChunk.getFirstLine(), cpyLines, 0);
					addCopyAll(nextChunk, pos, cpyLines);
				} else {
					//ch.addAll(pos - ch.getFirstLine(), cpyLines, 0);
					addCopyAll(ch, pos, cpyLines);
					updateNextChunk(ch, nextChunk, numberOfLines);
				}
//					doBasicCopy(chDtls, pos, cpyLines, numberOfLines, defaultBlkSize);
			} else {
				updateNextChunk(ch, nextChunk, numberOfLines);
				addCopy(ch, chDtls.index, pos, cpyLines, progressDisplay);
////				System.out.print("Starting ... ");
//				while ((start = ch.add(pos + start - ch.getFirstLine(), cpyLines, start)) < cpyLines.size()) {
//					ch = newFileChunk(fileDetails, ch.getFirstLine() + ch.getCount());
//					chunks.add(++idx, ch);
////					System.out.print('.');
//					progressDisplay.updateProgress(start);
//				}
			}	
		}
	}
	
	
	
	private void addCopy(IFileChunk<L, R> ch, int chunkIdx, int pos, IDataStore<? extends AbstractLine> data, IProgressDisplay progressDisplay) {
		int pos1 = pos - ch.getFirstLine();
		int recCount = 0;
		if (useLargeStore(data)) {
//			System.out.print('^');
			FileChunkBase chb = (FileChunkBase) ch;
			ArrayList<? extends IFileChunk> dataChunks = ((DataStoreLarge) data).getChunks();
			IFileChunk dataCh = dataChunks.get(0);
			int st = 0;
			if (chb.getSize() + dataCh.getSize() < Common.OPTIONS.chunkSize.get() * 3 / 2) {
				st = 1;
				IRecordStore rs = dataCh.getRecordStore();
				chb.add(pos1, rs);
				recCount += rs.getRecordCount();
			}
			
			for (int i = st; i < dataChunks.size(); i++) {
				dataCh = dataChunks.get(i);

				if (! progressDisplay.updateProgress(recCount, 0)) return;
				chb = (FileChunkBase) newFileChunk(fileDetails, chb);
				chunks.add(++chunkIdx, chb);
				chb.updateFrom((FileChunkBase) dataCh);
			}
		} else {
//			System.out.print('$');
//			System.out.print("Starting ... ");
			pos1 = pos1 + recCount;
			while ((recCount = ch.add(pos1, data, recCount)) < data.size()) {
				ch = newFileChunk(fileDetails, ch);
				chunks.add(++chunkIdx, ch);
				pos1 = 0;
//				System.out.print('.');
				if (! progressDisplay.updateProgress(recCount, 0)) return;
			}
		}
	}

	
	private void addCopyAll(IFileChunk<L, R> ch, int pos, IDataStore<? extends AbstractLine> data) {
		if (useLargeStore(data)) {
			ArrayList<? extends IFileChunk> chunks = ((DataStoreLarge) data).getChunks();
			for (IFileChunk dataCh : chunks) {
				IRecordStore rs = dataCh.getRecordStore();
				ch.add(pos - ch.getFirstLine(), rs);
				pos += rs.getRecordCount();
			}
		} else {
			ch.addAll(pos - ch.getFirstLine(), data);
		}
	}
	
	private boolean  useLargeStore(IDataStore<? extends AbstractLine> data) {
		return data instanceof DataStoreLarge 
			&& fileDetails.isCompatible(((DataStoreLarge) data).getFileDetails());
	}

	
	private void updateNextChunk(IFileChunk<L, R> ch, IFileChunk<L, R> next, int count) {
		if (ch != next && ch.getFirstLine() + ch.getCount() == next.getFirstLine()) {
			next.setFirstLine(next.getFirstLine() + count);
			next.setFirstTextPosition(ch.getFirstTextPosition() + ch.getTextSize());
		}
	}
	
	
	
	@Override
	public void extractLinesRE(IDataStore<AbstractLine> extractedLines,
			int[] linesToExtract, IProgressDisplay progressDisplay) {
		if (linesToExtract == null || linesToExtract.length == 0) {
		} else if (useLargeStore(extractedLines)) {
			DataStoreLarge lExtractedLines = (DataStoreLarge) extractedLines;
			FileChunkBase chb;
			ChunkLineDtls linePos;
			int i = 0;
			int last, rangeStart, end;
			while (i < linesToExtract.length) {
				rangeStart = linesToExtract[i];
				last = rangeStart;
				while ((++i < linesToExtract.length) && last+1 == linesToExtract[i]) {
					last += 1;
				}
				linePos = findLine(rangeStart);
				
//				while (linePos == null && rangeStart <= last) {
//					extractedLines.add(getTempLine(linesToExtract[i]).getNewDataLine());
//					linePos = findLine(++rangeStart);
//				}
				
				if (rangeStart <= last) {
					end = Math.min(linePos.chunk.getFirstLine() + linePos.chunk.getCount(), last + 1);
					if (linePos.lineInChunk > 0) {
						while (rangeStart < end) {
							extractedLines.add(getTempLineRE(rangeStart++).getNewDataLine());
						}

						linePos = findLine(rangeStart); 
					}
					
					if ((rangeStart < last && linePos != null
					&& (linePos.chunk.getFirstLine() + linePos.chunk.getCount() - 1 <= last))) {
						int removeIdx = -1;
						if (lExtractedLines.chunks.size() > 0 
						&& ((IFileChunk)lExtractedLines.chunks.get(lExtractedLines.chunks.size() - 1)).getCount() == 0) {
							removeIdx = lExtractedLines.chunks.size() - 1;
						}
						do {
							chb = (FileChunkBase) newFileChunk(fileDetails, lExtractedLines.size(), lExtractedLines.getTextSize());
							lExtractedLines.chunks.add(chb);
							chb.updateFrom((FileChunkBase) linePos.chunk);
							
							rangeStart += linePos.chunk.getCount();
							linePos = findLine(rangeStart); 
						} while (rangeStart < last && linePos != null
							 && (linePos.chunk.getFirstLine() + linePos.chunk.getCount() - 1 <= last));
						
						if (removeIdx >= 0) {
							lExtractedLines.chunks.remove(removeIdx);
						}
					}
					while (rangeStart <= last) {
						extractedLines.add(getTempLineRE(rangeStart++).getNewDataLine());
					}

				}
			}
		} else {
			try {
				for (int i = 0; i < linesToExtract.length; i++) {
					extractedLines.add(getTempLineRE(linesToExtract[i]).getNewDataLine());
					if (! progressDisplay.updateProgress(i, 0)) return;
				}
			} finally {
				progressDisplay.done();
	    	}
		}
	}

	private int addToChunk(IFileChunk<L, R> ch, IDataStore<? extends AbstractLine> cpyLines, int start) {
		int s = start;
		int numberOfLines = cpyLines.size();
		AbstractLine l;
		
		while (s < numberOfLines && (ch.getSpaceUsed() + (l = cpyLines.getNewLineRE(s)).getData().length < ch.getSize())) {
			ch.add(s, l);
			s += 1;
		}
		return s;
	}

	private int getAverageRecordSize() {
		int ave = Math.min(10, this.getLayoutRE().getMaximumRecordLength());
		int count = 0, totalSize = 0;
		
		int num2check = Math.min(16, chunks.size());
		for (int i = 0; i < num2check; i++) {
			count += chunks.get(i).getCount();
			totalSize += chunks.get(i).getSize();
		}
		
		if (count > 0) {
			ave = totalSize / count;
		}
		
		return ave;
	}
	private void checkForSplit(int idx) {
		IFileChunk<L, R> ch = chunks.get(idx);
		if (ch.getSize() > fileDetails.dataSize * 4) {
			int i;
			doSplit(ch.split(), idx);
		}
	}
	
	protected final void splitAt(int idx) {
//		if (idx == 1848) {
//			System.out.println();
//		}
		ChunkLineDtls cd = findLine(idx);
		if (cd != null) {
			IFileChunk<L, R> ch = cd.chunk;
			int splitPos = idx - ch.getFirstLine();

			doSplit(ch.splitAt(splitPos), cd.index);
		}
	}
	
	private void doSplit(List<? extends IFileChunk<L, R>> split, int idx) {
		if (split != null) {
			this.clearTempLines();
			int i;
			if (idx >= chunks.size()) {
				for (i = 0 ; i < split.size(); i++) {
					chunks.add(split.get(i));
				}
			} else {
				for (i = split.size() - 1; i >= 0; i--) {
					chunks.add(idx + 1, split.get(i));
				}
			}
		}
		
	}

	@Override
	public void clear() {
		if (fileDetails != null) {
			fileDetails.clear();
			fileDetails = null;
		}
		clearTempLines();
		sortMaster = null;
		if (chunks != null) {
			for (IFileChunk<L, R> ch : chunks) {
				ch.clear();
			}
			chunks.clear();
//			chunks.add(newFileChunk(fileDetails, 0));
		}
	}

	@Override
	public IChunkLine get(int idx) {
		IChunkLine ret = null;
		ChunkLineDtls cd = findLine(idx);

		fileDetails.setReading(false);
		if (cd != null) {
			ret = cd.chunk.getLine(idx);
		} else {
			int expected = 0;
			System.out.println();
			for (IFileChunk f : chunks) {
				System.out.print(" ++ " + f.getFirstLine()
						+ "\t " + f.getCount());
				if ((expected != f.getFirstLine())) {
					System.out.print("\t   <<=====  <<======= <<=======");
				}
				System.out.println();
				expected = f.getFirstLine()	+ f.getCount();
			}
			findLine(idx);
			throw new RecordRunTimeException(LINE_NOT_FOUND, Integer.toString(idx));
		}

		return ret;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#getLineLength(int)
	 */
	@Override
	public int getLineLengthRE(int lineNo) {
		ChunkLineDtls cd = findLine(lineNo);
		if (cd != null) {
			return cd.chunk.getLineLength(lineNo);
		}
		return 0;
	}

	@Override
	public int indexOf(Object arg0) {
		IChunkLine lc;
		if (arg0 instanceof IChunkLine && ( lc = (IChunkLine) arg0).isLive()) {
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
		fileDetails.setReading(false);

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

		fileDetails.setReading(false);
		return line;
	}

	@Override
	public boolean remove(Object arg0) {
		int lineNo = indexOf(arg0);
		boolean found = (lineNo >= 0);
		if (found) {
			remove(lineNo);
		}
		fileDetails.setReading(false);
		return found;
	}

	@Override
	public int size() {

		IFileChunk<L, R> chunk = chunks.get(chunks.size() - 1);

		return chunk.getFirstLine() + chunk.getCount();
	}

	public long getTextSize() {

		IFileChunk<L, R> chunk = chunks.get(chunks.size() - 1);

		return chunk.getFirstTextPosition() + chunk.getTextSize();
	}

	public final ChunkLineDtls findLine(int lineNo) {
		ChunkLineDtls ret = null;
//		System.out.println();
//		System.out.println("Searching for: " + lineNo);

		fileDetails.setReading(false);

		int idx;
		if (lineNo < chunks.get(0).getCount()) {
			idx = 0;
		} else if (lineNo >= chunks.get(chunks.size() - 1).getFirstLine()) {
			idx = chunks.size() - 1;
		} else if (lastFindIndex > 0 && lastFindIndex < chunks.size()
				&& lineNo >= chunks.get(lastFindIndex).getFirstLine()
				&& (lastFindIndex+1 == chunks.size() || lineNo <  chunks.get(lastFindIndex+1).getFirstLine())) {
			idx = lastFindIndex;
		} else {
			idx = Collections.binarySearch(
				chunks,
				newFileChunk(null, lineNo, 0),
				chunkComparator);
			lastFindIndex = idx;
		}

		if (idx >= 0) {
			IFileChunk<L,R> chunk = chunks.get(idx);
			ret = new ChunkLineDtls(lineNo - chunk.getFirstLine(), chunk, idx, lineNo);
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
	
	
	
	
	@Override
	public final DataStorePosition getTextPosition(long pos) {
		int idx = 0;
		int size = chunks.size();
		if (pos >= chunks.get(0).getTextSize()) {
			idx = ApproximateSearch.search(
					size,
					new CheckLong(size, pos, this.fileDetails.dataSize) {
						@Override public long get(int index) {
							return chunks.get(index).getFirstTextPosition();
						}
					});
					
					
					//new CheckTextSize(pos, this.fileDetails.dataSize));
		}
		
		if (idx >= 0 && idx < size) {
			IFileChunk<L, R> ch = chunks.get(idx);
			long firstTextPosition = ch.getFirstTextPosition();
			IRecordStore recordStore = ch.getRecordStore();
			long lastTextPosition = firstTextPosition + ch.getTextSize();
//			System.out.print("^^^^^  " + pos + " <= " + (lastTextPosition) 
//					+ " / " + firstTextPosition + " " + ch.getTextSize());
			if (pos >= firstTextPosition 
			&& (pos < lastTextPosition || (pos == lastTextPosition && idx == size - 1))
			&& recordStore instanceof IDocumentRecordStore) {
				IDocumentRecordStore dRecordStore = (IDocumentRecordStore) recordStore;
				return newDocumentPosition(firstTextPosition, ch.getFirstLine(), dRecordStore.getTextPosition((int) (pos - firstTextPosition)));
			} else {
				
			}
		}
		return null;
	}
	
	
	
	/**
	 * @see net.sf.RecordEditor.utils.fileStorage.ITextInterface#length()
	 */
	@Override
	public long length() {
		IFileChunk<L, R> ch = chunks.get(chunks.size() - 1);
		
		return Math.min(Integer.MAX_VALUE - 1, ch.getFirstTextPosition() + ch.getTextSize() - 1);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.ITextInterface#getLinePosition(int)
	 */
	@Override
	public DataStorePosition getPositionByLineNumber(int lineNo) {
		IRecordStore recordStore;
		ChunkLineDtls lineDtls = findLine(lineNo);
		if (lineDtls != null && lineDtls.chunk != null
		&& (recordStore = lineDtls.chunk.getRecordStore())  instanceof IDocumentRecordStore) {
			IDocumentRecordStore dRecordStore = (IDocumentRecordStore) recordStore;
			return newDocumentPosition(lineDtls.chunk.getFirstTextPosition(), lineDtls.chunk.getFirstLine(),
					dRecordStore.getLinePositionLength(lineNo - lineDtls.chunk.getFirstLine(), 0)); 
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.ITextInterface#getDataStore()
	 */
	@Override
	public IDataStore<? extends AbstractLine> getDataStore() {
		return this;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.ITextInterface#getCharPosition(int)
	 */
	@Override
	public long getCharPosition(int lineNo) {
		ChunkLineDtls lineDtls = findLine(lineNo);
		IRecordStore rs;
		if (lineDtls != null && lineDtls.chunk != null && (rs = lineDtls.chunk.getRecordStore())  instanceof IDocumentRecordStore) {
			IDocumentRecordStore dRs = (IDocumentRecordStore) rs;
			LineDtls linePos = dRs.getLinePositionLength(lineNo - lineDtls.chunk.getFirstLine(), 0);
			return lineDtls.chunk.getFirstTextPosition() + linePos.textPos;
		}
		return 0;
	}



	private DataStorePosition newDocumentPosition(long startPos, int startLineNum, LineDtls textPosition) {
		return new DataStorePosition(startLineNum + textPosition.lineNumber, startPos + textPosition.textPos, textPosition.positionInLine,
				get(startLineNum + textPosition.lineNumber), this);
	}
	
	private void clearTempLines() {
		lastLine = null;
//		tempLineCount = 0;
//		if (tempLines.size() > 0) {
//			tempLines.clear();
//			for (int i = 0; i < tempLineArray.length; i++) {
//				tempLineArray[i] = null;
//			}
//		}
	}

	private void updateLines(int lineNo, int inc) {
		clearTempLines();

		//synchronized (chunks) {
			IFileChunk<L, R> fcc;
//			for (int i = chunks.size() - 1; i >= 0; i--) {
//				fcc = chunks.get(i);
//				if (fcc.getFirstLine() > lineNo) {
//					fcc.setFirstLine(fcc.getFirstLine() + inc);
//				} else {
//					break;
//				}
//			}

			long textPos = 0;
			IFileChunk<L, R> ch;
			for (int i = 0; i < chunks.size(); i++) {
				ch = chunks.get(i);
				ch.setFirstTextPosition(textPos);
				if (ch.getFirstLine() > lineNo) {
					ch.setFirstLine(ch.getFirstLine() + inc);
				} 
				textPos = ch.getFirstTextPosition() + ch.getTextSize();
			}

		//}
	}


	public void sort(final Comparator<AbstractLine> compare) {
		sortRE(compare);
	}

	@Override
	public void sortRE(final Comparator<AbstractLine> compare) {
		int[] order = sortMaster.sort(this, compare);
		int[] map = SortParent.getMapping(order);
		DataStoreLarge<L, R> tmp 
			= new DataStoreLarge<L, R>(fileDetails.getLayout(), fileDetails.type, fileDetails.recordLength);
		TreeMap<Integer, L> mangagedLines = new TreeMap<Integer, L>();
		List<L> chunkManagedLines;

		
		clearTempLines();

		for (IFileChunk<L,R> f : chunks) {
			if ((chunkManagedLines = f.getActiveLines()) != null) {
				for (L lb : chunkManagedLines) {
					mangagedLines.put(map[lb.getActualLine()], lb);
				}
			}
		}
		map = null;


		for (int i = 0; i < chunks.size(); i++) {
			tmp.chunks.add(chunks.get(i)); 
		}
		
		synchronized (this) {
			for (int i = chunks.size() - 1; i >= 0; i--) {
				chunks.remove(i);
			}
			
			chunks.add(newFileChunk(fileDetails, 0, 0));
			
			for (int i = 0; i < order.length; i++) {
				this.add(tmp.get(order[i]));
			}
			
			IFileChunk<L,R> fc;
			Entry<Integer, L> item;
			Iterator<Entry<Integer, L>> linesIterator = mangagedLines.entrySet().iterator();
			int ci = 0;
			
			fc = chunks.get(0);
			while (linesIterator.hasNext()) {
				item = linesIterator.next();
				while(fc.getFirstLine() + fc.getCount() <= item.getKey() && ci < chunks.size() - 1) {
					fc = chunks.get(++ci);
				}
				
				item.getValue().setChunk(fc);
				item.getValue().setChunkLine(item.getKey() - fc.getFirstLine());
				fc.addLineToManagedLines(item.getValue());
			}
		}
	}

	

	@Override
	public void sortRE(int[] rows, Comparator<AbstractLine> compare) {
		int[] order = sortMaster.sort(this, rows, compare);

		ArrayList<AbstractLine> sortList = new ArrayList<AbstractLine>(order.length);
		L l;
		
		for (int i = 0; i < order.length; i++) {
			sortList.add(this.getNewLineRE(order[i]));
		}

		for (int i = 0; i < order.length; i++) {
		//for (int i = order.length - 1; i >= 0; i--) {
//			System.out.println(i + "\t" + rows[i] + "\t" + this.getTempLine(rows[i]).getFullLine() +"\t" + sortList.get(i).getFullLine());
			this.set(rows[i], sortList.get(i));
		}
		
		ChunkLineDtls oldPosCd, newPosCd;
		ArrayList<LineEntry> linesToUpdate = new ArrayList<DataStoreLarge<L,R>.LineEntry>(rows.length);
		int[] map = SortParent.getMapping(order, rows, this.size());
		for (int i = 0; i < rows.length; i++) {
			oldPosCd = findLine(rows[i]);
			l =  oldPosCd.chunk.getLineIfDefined(oldPosCd.lineNo);
			if (l != null && map[oldPosCd.lineNo] >= 0) {
				linesToUpdate.add(new LineEntry(map[oldPosCd.lineNo], l));
				oldPosCd.chunk.removeLineReference(oldPosCd.lineNo);
			}
		}
		
		for (LineEntry e : linesToUpdate) {
			newPosCd = findLine(e.lineNumber);
			e.line.setChunk(newPosCd.chunk);
			e.line.setChunkLine(e.lineNumber - newPosCd.chunk.getFirstLine());
			newPosCd.chunk.addLineToManagedLines(e.line);
		}
	}
	
//	public void oldSort(final Comparator<AbstractLine> comp) {
//		Runtime runtime = Runtime.getRuntime();
//		long free = runtime.maxMemory() - runtime.totalMemory();
//		long size = this.size();
//		long using = 0;
//
//		for (IFileChunk<L, R> ch : chunks) {
//			using += ch.getSpaceUsed();
//		}
//
//
//		if (size > Integer.MAX_VALUE
//		|| free < using * 1.2 + 2000000) {
//			String s = LangConversion.convert("Not enough memory to do a sort !!!");
//			Common.logMsgRaw("\n" + s, null);
//			Common.logMsgRaw(s, null);
//			throw new RuntimeException(s);
//		} else {
//			synchronized (this) {
//				Integer[] array = new Integer[(int) size];
//				for (int i = 0; i < array.length; i++) {
//					array[i] = i;
//				}
//
//				Arrays.sort(array, new Comparator<Integer>() {
//					@Override
//					public int compare(Integer arg0, Integer arg1) {
//
//						return comp.compare(
//								getTempLine(arg0.intValue()),
//								getTempLine(arg1.intValue())
//						);
//					}
//				});
//
//				int i;
//				IChunkLine tmp;
//				L l;
//				//ArrayList<LineBase> activeLines = new ArrayList<LineBase>(500);
//				HashMap<Integer, L> lines = new HashMap<Integer, L>(5000);
//				ArrayList<IFileChunk<L,R>> newChunks = new ArrayList<IFileChunk<L,R>>(chunks.size());
//				List<L> chunkManagedLines;
//				IFileChunk<L,R> fc;
//				Integer lineNo;
//
//				for (IFileChunk<L,R> f : chunks) {
//					if ((chunkManagedLines = f.getActiveLines()) != null) {
//						for (L lb : chunkManagedLines) {
//							lines.put(lb.getActualLine(), lb);
//						}
//					}
//				}
//
//				newChunks.add(newFileChunk(fileDetails, 0));
//
//				for (i = 0; i < array.length; i++) {
//					tmp = getTempLine(array[i].intValue());
//					addToChunks(newChunks, tmp);
//
////					System.out.print(" :: --> " + i
////							+ " " + tmp.getClass().getName()
////							+ " " + tmp.getActualLine()
////							+ " " + tmp.getChunkLine() + " " + (tmp.getChunk() == null)
////							+ " " + chunks.indexOf(tmp.getChunk())
////							+ "\t" + tmp.getField(0,1)
////							+ "\t" + tmp.getField(0,2)
////							+ "\t" + tmp.getField(0,3)
////							+ "\t" + tmp.getField(0,4)
////					);
//
//					lineNo = tmp.getActualLine();
//					if (lines.containsKey(lineNo)) {
//						l = lines.get(lineNo);
//						fc = newChunks.get(newChunks.size() - 1);
//						l.setChunk(fc);
//						l.setChunkLine(fc.getCount() - 1);
//						fc.addLineToManagedLines(l);
//
//						//System.out.print(" active");
//					}
//
//					//System.out.println();
//				}
//
//				clearTempLines();
//
//				chunks = newChunks;
//			}
//		}
//	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.DataStore#getLayout()
	 */
	@Override
	public AbstractLayoutDetails getLayoutRE() {
		return fileDetails.getLayout();
	}

	@Override
	public void setLayoutRE(AbstractLayoutDetails newLayout) {
		if (newLayout instanceof LayoutDetail) {
			LayoutDetail l = (LayoutDetail) newLayout;
			clearTempLines();
			fileDetails.setLayout(l);
			for (IFileChunk ch : chunks) {
				ch.setLayout(l);
			}
		}
	}

	@Override
	public IDataStore<AbstractLine> newDataStoreRE() {
		return new DataStoreLargeView(this).addRange(0, size() - 1);
				//new DataStoreStd<AbstractLine>(fileDetails.getLayout(), this);
	}


	@Override
	public IDataStore<AbstractLine> newDataStoreRE(int[] lines) {
		return new DataStoreLargeView(this, lines); 
	}


	@Override
	public long getSpaceRE() {
		long kb = 0;

		for (IFileChunk<L, R> ch : chunks) {
			kb += ch.getSize();
		}
		return kb;
	}

	@Override
	public String getSizeDisplayRE() {
		long kb = 0;
		long using = 0;
		long kbSize = 1024;
		int max = 0;

		for (IFileChunk<L, R> ch : chunks) {
			kb += ch.getSize();
			using += ch.getSpaceUsed();
			max = Math.max(max, ch.getSize());
		}
		return   "        Record Count : " + size()
			+  "\n           Size (kb) : " + (kb / kbSize)
			+  "\nCompressed Size (kb) : " + (using / kbSize)
			+  "\n  Disk Overflow (kb) : " + fileDetails.getDiskSpaceUsed() / kbSize
			+  "\n    Number of Chunks : " + chunks.size() 
			+  "\n  Maximum chunk size : " + max
			;
	}

	public final FileDetails getFileDetails() {
		return fileDetails;
	}

	protected final ArrayList<IFileChunk<L, R>> getChunks() {
		return chunks;
	}

	public final void rename(String oldName, String newName) {
		fileDetails.rename(oldName, newName);
	}
	
	public final void doCompress(int start, int currentLine) {
		for (int i = chunks.size() - 2; i >= start; i--) {
			if (chunks.get(i).getFirstLine() > currentLine + 30 || chunks.get(i).getFirstLine() + chunks.get(i).getCount() < currentLine - 30) {
				chunks.get(i).compress();
			}
		} 
	}

	private IFileChunk<L, R> newFileChunk(FileDetails details, IFileChunk<L, R> ch) {
		return newFileChunk(details, ch.getFirstLine() + ch.getCount(), ch.getFirstTextPosition() + ch.getTextSize());
	}

	private IFileChunk<L, R> newFileChunk(FileDetails details, int theFirstLine, long theFirstTextPos) {
		switch (this.fileDetails.type) {
		case FileDetails.CHAR_LINE:
			return (IFileChunk<L, R>) new FileChunkCharLine(details, theFirstLine, theFirstTextPos);
		case FileDetails.VARIABLE_LENGTH_BASEFILE:
			if (fileDetails.isReading()
			&&  fileDetails.getByteReader() != null
			&&  (Common.OPTIONS.largeVbOption.get() == ProgramOptions.LARGE_VB_TEST
			 ||  GcManager.getSpaceUsedRatio() > 0.60)) {
				return (IFileChunk<L, R>)
					new FileChunkBfVariableLength(fileDetails, theFirstLine, theFirstTextPos, filePosition);
			}
		}
		return (IFileChunk<L, R>) new FileChunkLine(details, theFirstLine, theFirstTextPos);
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#hasEmbeddedCr()
	 */
	@Override
	public boolean hasEmbeddedCr() {
		return embeddedCr;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#isTextViewPossible()
	 */
	@Override
	public boolean isTextViewPossibleRE() {
		return (! embeddedCr) && chunks.get(0).isDocumentViewAvailable();
	}


	/**
	 * @see net.sf.RecordEditor.utils.fileStorage.IChunkLengthChangedListner#blockChanged(net.sf.RecordEditor.utils.fileStorage.IFileChunk)
	 */
	@Override
	public void blockChanged(IFileChunk ch) {
		int idx = chunks.indexOf(ch);
		
		if (idx >= 0) {  
			long textPos = ch.getFirstTextPosition() + ch.getTextSize();
			for (int i = idx + 1; i < chunks.size(); i++) {
				ch = chunks.get(i);
				ch.setFirstTextPosition(textPos);
				textPos = ch.getFirstTextPosition() + ch.getTextSize();
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStore#getTextInterface()
	 */
	@Override
	public ITextInterface getTextInterfaceRE() {
		return this;
	}


	public static DataStoreLarge<LineChunk, RecordStoreFixedLength>
		getFixedLengthRecordStore(LayoutDetail layout, String fileName) {
		DataStoreLarge<LineChunk, RecordStoreFixedLength> ret
			= new DataStoreLarge<LineChunk, RecordStoreFixedLength>(layout, FileDetails.FIXED_LENGTH_BASEFILE, layout.getMaximumRecordLength());

		ret.setFixedLengthFileName(fileName);

		//System.out.println("Large Fixed Length Model");
		return ret;
	}

	private class ChunkLineDtls {
		public final int //chunksFirstLine,
						 lineInChunk;
		public final IFileChunk<L, R> chunk;
		public final int index, lineNo;

		public ChunkLineDtls(int lineInChunk, IFileChunk<L, R> chunk, int idx, int lineNo) {
			super();
			//this.chunksFirstLine = chunk.getFirstLine();
			this.lineInChunk = lineInChunk;
			this.chunk = chunk;
			this.index = idx;
			this.lineNo = lineNo;
		}
	}

	private class LoadFile extends Thread {

		@Override
		public void run() {
			int i;
			IFileChunk<L, R> fc;
			chunks.get(0).getTempLine(0);
			for (i = Math.min(15, chunks.size() - 1); i > 0; i--) {
				fc = chunks.get(i);
				fc.getTempLine(fc.getFirstLine());
				if (i % 4 == 3) {
					fc.compress();
				}
			}
			GcManager.doGcIfNeccessary(0.50);
			i = 16;
			while (i < 100 && i < chunks.size()
				&& GcManager.getSpaceUsedRatio() > 0.40) {
				fc = chunks.get(i++);
				fc.getTempLine(fc.getFirstLine());
				fc.compress();

				if (i % 8 == 7) {
					try {
						super.wait(120);
					} catch (Exception e) {
					}
				}
			}
		}
	}
	
	private class LineEntry {
		final int lineNumber;
		final L line;
		
		public LineEntry(int lineNumber, L line) {
			super();
			this.lineNumber = lineNumber;
			this.line = line;
		}
	}
	
	
	
//	private class CheckTextSize implements IApproximateCheck {
//		long searchFor;
//		int ave, next;
//		
//		public CheckTextSize(long searchFor, int ave) {
//			super();
//			this.searchFor = searchFor;
//			this.ave = ave;
//			next = (int) (searchFor / ave);
//		}
//
//		@Override
//		public int check(int idx) {
//			int ret = 0;
//			long t;
//			long p = chunks.get(idx).getFirstTextPosition();
//			if (p > searchFor) {
//				ret = -1;
//				next = idx - ((int) ((p - searchFor) / ave)) - 1;
//			} else if ((t = (p +  chunks.get(idx).getTextSize())) <= searchFor && idx < chunks.size()) {
//				ret = 1;
//				next = idx + ((int) ((searchFor - t) / ave)) + 1;
//			}
//			
//			return ret;
//		}
//		
//		@Override
//		public int getNextIndex() {
//			return next;
//		}
//	}
}