package net.sf.RecordEditor.utils.fileStorage;

import net.sf.RecordEditor.utils.approxSearch.ApproximateSearch;
import net.sf.RecordEditor.utils.approxSearch.CheckLong;


/**
 * This class maintains a index of every 8 records in a large store.
 * This allows quick access to record Position and searching by textPosition 
 * (i.e. assume a cr after each record) for use in TextViews.
 * 
 * @author Bruce Martin
 *
 */
public final class RecordIndexMgr  {
	
	public final static int INDEX_RECORD = 8;
	private final static int[] MAX_LENGTHS = {255, 256 * 256 - 1, 256 * 256 * 256 - 1, Integer.MAX_VALUE, Integer.MAX_VALUE};

	private final int recordOverhead;
	private final boolean lengthAtFrontOfRecord;
	private int maxLen;
	private int[] recIndex = null;
//	int used = -1;
	private int recIndexValidTo = -1;
	private int lastLookUpPosition = -11; //, lastTextPosition = -11;
	private int lastLookUpBlockIndex = -1; //, lastTextIndex = -1, lastTextPosnLine = -1;
	private final ISimpleRecStore parent;
	protected final int maxRecordLength;


	public RecordIndexMgr( ISimpleRecStore parent, int recLen, int recordOverhead, boolean lengthAtFrontOfRecord) {
		
		this.parent = parent;
		this.maxLen = Math.max(1, recLen);
		this.recordOverhead = recordOverhead;
		this.lengthAtFrontOfRecord = lengthAtFrontOfRecord;
		if (lengthAtFrontOfRecord) {
			maxRecordLength = MAX_LENGTHS[recordOverhead];
		} else {
			maxRecordLength = Integer.MAX_VALUE;
		}
	}


	protected final void clear() {
		recIndex = null;
		resetIndex();
	}
	
	public int get(int idx) {
		return recIndex[idx];
	}
		
	public final void checkRecordIndex(int idx) {
		
		if (recIndex != null
		&& ( idx < parent.getRecordCount() - 1
		||  (idx % INDEX_RECORD == INDEX_RECORD - 1))) { 
			resetIndex(idx);
		} else {
			lastLookUpPosition = -11;
		}
	}

		
	
	public final void adjRecordIndex(int idx, int diff) {
		
		if (recIndex != null) {
			synchronized (recIndex) {
				int index = idx / INDEX_RECORD;
				for (int i = Math.max(0, index); i < Math.min(recIndex.length, parent.getRecordCount() / INDEX_RECORD); i++) {
					recIndex[i] += diff;
				}
				
				resetIndex();
//				System.out.print(" @" + idx);
//				if (lastLookUpBlockIndex >= idx) {
//					System.out.print('@' + " " + lastLookUpBlockIndex + " " + idx + " "+ diff + "! ");
//					lastLookUpPosition += diff;
//				}
			}
		}
		
		if (idx < lastLookUpPosition) {
			resetIndex();
		} 
	}



	

	public void resetIndex(int resetAt) {
		recIndexValidTo = Math.min(recIndexValidTo, (resetAt - 1) / INDEX_RECORD  - 1);
		if (resetAt < lastLookUpPosition) {
			lastLookUpPosition = -11;
		}
		
		if (recIndexValidTo < 0) {
			recIndex = null;
		}
//		lastTextPosition = -11;
	}

	public void resetIndex() {
		
		lastLookUpPosition = -11;
//		lastTextPosition = -11;
	}


	protected void buildIndex() {
		if (parent.getRecordCount() < INDEX_RECORD) {
			return;
		}
		int i, l;
		int j = 0;
		int k = 0;
		int p = 0;
		lastLookUpPosition = -11;

		if (recIndex == null || recIndex.length < parent.getRecordCount() / INDEX_RECORD) {
			//System.out.println("Index ");
			recIndex = new int[Math.max(parent.getRecordCount(), parent.getStoreSize() / maxLen) / INDEX_RECORD + 10];
		}
		
		synchronized (recIndex) {
//			used = 0;
			for (i = 0; i < parent.getRecordCount(); i++) {
				l = parent.getLength(p);
				p += l + this.recordOverhead;
				
				//System.out.println(" --> " + j + "\t" + p + "\t" + l);
				
				j += 1;
				if (j >= INDEX_RECORD) {
					j = 0;
					if (k < recIndex.length) {
						recIndexValidTo = k;
						recIndex[k++] = p;
//						used = k;
					}
				}
			}
			
			//System.out.print("$");
			parent.setSize(p);

		}

	}
	
	

	public LineDtls getLinePositionLength(int lineNumber, int newLen, int size) {
		
		int lastP;
		int l = recordOverhead;
		if (lineNumber >= parent.getRecordCount()) {
			lastP = size;
			l = 0;
		} else {
			int p = 0;
			int count = lineNumber - lastLookUpPosition;
			if (lastLookUpPosition>= 0 && count >= 0 && (count < 4)) {
				p = this.lastLookUpBlockIndex;
//				System.out.print(" ." + idx);
			} else {
				int index = lineNumber / INDEX_RECORD - 1;
				
				count = lineNumber % INDEX_RECORD;
				
				if (index >= 0) {
					if (recIndex == null || index > recIndexValidTo) {
						buildIndex();
					}
				
					p = recIndex[index];
				}
			}
			lastP = p;
			//System.out.print(" . " + count);
			for (int i = 0; i <= count; i++) {
				lastP = p;
				l = parent.getLength(p);
				p += l + recordOverhead;
			}
		}
		lastLookUpPosition = lineNumber;
		lastLookUpBlockIndex = lastP;
		

		return newLineDetails(lastP, l, newLen, lineNumber, 0);
	}

	public final LineDtls getTextPosition(final int textPos, int size) {
		int lastLineNo;
		LineDtls ld = null;
		
		if (textPos < 0) {
		} else if (textPos >= size) {
			lastLineNo = parent.getRecordCount();
			ld = getLinePositionLength(lastLineNo, 0, size);
			ld = newLineDetails(ld.pos, ld.len, 0, ld.lineNumber, size - ld.pos);
		} else {
			int p = 0, tp = 0;
			int startAt = 0;
			int lineNum = 0;
			if (recIndex == null || recIndexValidTo < 0 || recIndex[recIndexValidTo] < textPos) {
				buildIndex();
			}
			if (recIndexValidTo > 0 && textPos >= getTextPosForIndex(0)) {
				int idx = ApproximateSearch.search(
						recIndexValidTo+1,
						new CheckLong(recIndex.length, textPos, recIndex[recIndexValidTo] / recIndexValidTo) {
							
							@Override public long get(int index) {
								return getTextPosForIndex(index); //recIndex[index] + INDEX_RECORD * (1 + index);
							}
				});

				if (idx >= 0) {
					startAt = recIndex[idx];
					tp = getTextPosForIndex(idx);
					lineNum = (idx + 1) * INDEX_RECORD;
				}
			}
			int l = 0;
			int lastTP = tp;
			int lastP = startAt;
//			int ll = lineNum;
			 
			
			p = startAt;
			do {
				lastTP = tp;
				lastP = p;
				l = parent.getLength(p);
				p += l + recordOverhead;
				tp += l + 1;
				lineNum += 1;
			} while ( tp <= textPos );
			
//			if (lineNum - ll > INDEX_RECORD) {
//				System.out.print("\t" + (lineNum - ll));
//			}
	
			ld = newLineDetails(lastP, l, 0, lineNum-1, textPos - lastTP);
		}
		return ld;
	}
	
	private LineDtls newLineDetails(int pos, int len, int newLen, int lineNumber, int positionInLine) {
		return new LineDtls(pos, len, newLen, lineNumber, positionInLine, recordOverhead, lengthAtFrontOfRecord);
	}
	

	private  int getTextPosForIndex(int idx) {
		return recIndex[idx] + (idx + 1) * INDEX_RECORD * (1 - recordOverhead);
	}
}
