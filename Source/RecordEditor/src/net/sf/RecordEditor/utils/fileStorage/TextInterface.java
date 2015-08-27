package net.sf.RecordEditor.utils.fileStorage;


import javax.swing.event.TableModelEvent;

import net.sf.JRecord.Details.AbstractLine;


public class TextInterface implements ITextInterface { 

	private static final int INDEX_SIZE = 16;
	private int lastValidCount = -3;
	private int used = 0;
	private int[] charCount = null;  // array of chars at the start of every 16th line

	
	private final IDataStore<? extends AbstractLine> ds;
	
	

	public TextInterface(IDataStore<? extends AbstractLine> ds) {
		super();
		this.ds = ds;
	}



	@Override
	public final long length() {
		int lineNo = ds.size() - 1;
		return getCharPosition(lineNo) + ds.getLineLengthRE(lineNo);
	}


	@Override
	public final DataStorePosition getTextPosition(long pos) {
		long p = 0;
		int start = 0;

		int charIdx = calcPosIndex((int)pos);
		if (charIdx >= 0) {
			p = charCount[charIdx];
			start = charIdx * INDEX_SIZE + INDEX_SIZE;
		}
		int len;
		int size = ds.size();
		for (int i = start; i < size; i++) {
			len = ds.getLineLengthRE(i) + 1;
			if (p + len > pos) {
				return new DataStorePosition(i, p, (int) (pos - p), ds.get(i), this);
			}
			p += len;
		}

		size = size - 1;
		int lineSize = ds.getLineLengthRE(size);
		return new DataStorePosition(size, p - lineSize - 1, lineSize, ds.get(size), this);
	}


//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.utils.fileStorage.zzzTxt#updatePosition(net.sf.RecordEditor.utils.fileStorage.DataStorePosition)
//	 */
//	@Override
//	public final void updatePosition(DataStorePosition pos) {
//		DataStorePosition.updatePosition(pos);
////		if (pos.lineNumber >= ds.size() || pos.lineNumber < 0 || ds.get(pos.lineNumber) != pos.line) {
////			int idx = ds.indexOf(pos.line);
////			if (idx >= 0) {
////				pos.lineNumber = idx;
////			} else if (pos.lineNumber >= ds.size() || pos.lineNumber < 0) {
////				return;
//////				throw new RuntimeException("Position is no longer valid");
////			} else  {
////				if (pos.lineNumber > 0) {
////					pos.lineNumber -= 1;
////				}
////				pos.line = ds.get(pos.lineNumber);
////			}
////		}
////
////		pos.lookupRequired = false;
////		pos.lineStart = getCharPosition(pos.lineNumber);
//	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.DataStoreTextContent#getLinePosition(int)
	 */
	@Override
	public final DataStorePosition getPositionByLineNumber(int lineNo) {
		int size = ds.size();
		if (lineNo == size) {
			int lastLineNo = lineNo - 1;
			AbstractLine line = ds.get(lastLineNo);
			return new DataStorePosition(lastLineNo, (int) getCharPosition(lastLineNo), ds.getLineLengthRE(lastLineNo), line, this);
		} else if (lineNo > size || lineNo < 0) {
			return null;
		}

		int charPos = (int) getCharPosition(lineNo);
		return new DataStorePosition(lineNo, charPos, 0, ds.get(lineNo), this);
	}


	@Override
	public IDataStore<? extends AbstractLine> getDataStore() {
		return ds;
	}

	private int calcPosIndex(int charPos) {
		calcCharPositionsTbl(ds.size() - 1);
		if (used <= 0 || charPos < charCount[0]) return -1;

		int low=0, high=used, mid;

		if (charPos > charCount[high]) return high;

		while (high > low) {
			mid = (high + low) / 2;
			if (high - 1 == low) {
				high = low;
			} else if (charPos > charCount[mid]) {
				low = mid;
			} else if (charPos < charCount[mid]) {
				high = mid;
			} else {
				return mid;
			}
		}

		return low;
	}



	@Override
	public long getCharPosition(int lineNo) {
		calcCharPositionsTbl(lineNo);

		long p = 0;
		int d = lineNo / INDEX_SIZE;
		if (d > 0) {
			p = charCount[d - 1];
			d = d * INDEX_SIZE;
		}

		for (int i = d; i < lineNo; i++) {
			p += ds.getLineLengthRE(i);
		}

		//System.out.println("   =========== " + lineNo + " " + d + " " + (p + lineNo - d) + " -> " + lineLength(lineNo));
		return p + lineNo - d;
	}


	private void calcCharPositionsTbl(int pos) {

		if (pos / INDEX_SIZE - 1 > lastValidCount) {
			int sizeReq = ds.size() / INDEX_SIZE;
			int p = 0, j = 0;
			if (charCount == null || charCount.length < sizeReq) {
				charCount = new int[sizeReq + INDEX_SIZE];
			}

			for (int i = 0; i < ds.size(); i++) {
				if (i > 0 && i % INDEX_SIZE == 0) {
					charCount[j++] = p;

					if (j > sizeReq) {
						break;
					}
				}
				p += ds.getLineLengthRE(i) + 1;
			}

			used = Math.min(j - 1, sizeReq);
			lastValidCount = used;
		}
	}

	
	public final void resetPosition(TableModelEvent e) {
		lastValidCount = -3;
	}
}