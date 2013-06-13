package net.sf.RecordEditor.utils.fileStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.Options;

@SuppressWarnings({ "serial", "unchecked" })
public abstract class DataStoreStd<L extends AbstractLine>
extends ArrayList<L>
implements IDataStoreText<L>, TableModelListener {

	private int lastValidCount = -3;
	private int used = 0;
	private int[] charCount = null;  // array of chars at the start of every 16th line

	private AbstractLayoutDetails layout;
	protected DataStoreStd(AbstractLayoutDetails layoutDtls ) {
		layout = layoutDtls;
	}

	protected DataStoreStd(AbstractLayoutDetails layoutDtls, int arg0) {
		super(arg0);
		layout = layoutDtls;
	}

	protected DataStoreStd(AbstractLayoutDetails layoutDtls, Collection<L> arg0) {
		super(arg0);
		layout = layoutDtls;
	}


	@Override
	public L addCopy(int lineNo, L line) {
		L l = (L) line.getNewDataLine();

		l.setLayout(getLayout());

		add(lineNo, l);

		return l;
	}

	@Override
	public L getTempLine(int idx) {
		return super.get(idx);
	}


	public final AbstractLayoutDetails getLayout() {
		if (layout == null && super.size() > 0) {
			layout = get(0).getLayout();
		}

		return layout;
	}

	@Override
	public void setLayout(AbstractLayoutDetails layout) {
		if (this.layout != layout) {
			this.layout = layout;
			for (int i = size() - 1; i >= 0; i--) {
				get(i).setLayout(layout);
			}
		}
	}

	@Override
	public void sort(Comparator<L> compare) {
		Collections.sort(this, compare);
	}


	@Override
	public long getSpace() {
		return length() - size() + 1;
	}

	@Override
	public String getSizeDisplay() {
		return "Record Count: " + size();
	}

	@Override
	public final int length() {
		int lineNo = size() - 1;
		return getCharPos(lineNo) + getLineLength(lineNo);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.DataStoreTextContent#getPosition(int)
	 */
	@Override
	public final DataStorePosition getPosition(int pos) {
		int p = 0, start = 0;
		int charIdx = calcPosIndex(pos);
		if (charIdx >= 0) {
			p = charCount[charIdx];
			start = charIdx * 16 + 16;
		}
		for (int i = start; i < size(); i++) {
			if (p + getLineLength(i) + 1 > pos) {
				return new DataStorePosition(i, p, pos - p, get(i), this);
			}
			p += getLineLength(i) + 1;
		}

		int lineSize = getLineLength(size() - 1);
		return new DataStorePosition(size() - 1, p - lineSize - 1, lineSize, get(size() - 1), this);
	}


	@Override
	public final void updatePosition(DataStorePosition pos) {
		if (pos.lineNumber >= size() || pos.lineNumber < 0 || get(pos.lineNumber) != pos.line) {
			int idx = this.indexOf(pos.line);
			if (idx >= 0) {
				pos.lineNumber = idx;
			} else if (pos.lineNumber >= size() || pos.lineNumber < 0) {
				return;
//				throw new RuntimeException("Position is no longer valid");
			} else  {
				if (pos.lineNumber > 0) {
					pos.lineNumber -= 1;
				}
				pos.line = get(pos.lineNumber);
			}
		}

		pos.lookupRequired = false;
		pos.lineStart = getCharPos(pos.lineNumber);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.DataStoreTextContent#getLinePosition(int)
	 */
	@Override
	public final DataStorePosition getLinePosition(int lineNo) {
		if (lineNo == size()) {
			int lastLineNo = lineNo - 1;
			L line = get(lastLineNo);
			new DataStorePosition(lastLineNo, getCharPos(lastLineNo), getLineLength(lastLineNo), line, this);
		} else if (lineNo > size() || lineNo < 0) {
			return null;
		}

		int charPos = getCharPos(lineNo);
		return new DataStorePosition(lineNo, charPos, 0, get(lineNo), this);
	}



	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		lastValidCount = -3;
	}


	private int calcPosIndex(int charPos) {
		calcCharPositionsTbl(size() - 1);
		if (used <= 0 || charPos < charCount[0]) return -1;

		int low=0, high=used-1, mid;

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



	private int getCharPos(int lineNo) {
		calcCharPositionsTbl(lineNo);

		int p = 0;
		int d = lineNo / 16;
		if (d > 0) {
			p = charCount[d - 1];
			d = d * 16;
		}

		for (int i = d; i < lineNo; i++) {
			p += getLineLength(i);
		}

		//System.out.println("   =========== " + lineNo + " " + d + " " + (p + lineNo - d) + " -> " + lineLength(lineNo));
		return p + lineNo - d;
	}


	private void calcCharPositionsTbl(int pos) {

		if (pos / 16 >= lastValidCount) {
			int sizeReq = size() / 16;
			int p = 0, j = 0;
			if (charCount == null || charCount.length < sizeReq) {
				charCount = new int[sizeReq + 16];
			}

			for (int i = 0; i < size(); i++) {
				if (i > 0 && i % 16 == 0) {
					charCount[j++] = p;

					if (j > sizeReq) {
						break;
					}
				}
				p += getLineLength(i) + 1;
			}

			used = Math.min(j - 1, sizeReq);
			lastValidCount = used;
		}
	}

	protected abstract int getLineLength(int lineNo);




	public static class DataStoreStdTxt<L extends AbstractLine> extends DataStoreStd<L> {

		public DataStoreStdTxt(AbstractLayoutDetails layoutDtls, Collection<L> arg0) {
			super(layoutDtls, arg0);
		}

		public DataStoreStdTxt(AbstractLayoutDetails layoutDtls, int arg0) {
			super(layoutDtls, arg0);
		}

		public DataStoreStdTxt(AbstractLayoutDetails layoutDtls) {
			super(layoutDtls);
		}

		@Override
		public DataStore<L> newDataStore() {
			return new DataStoreStdTxt<L>(getLayout(), this);
		}


		protected final int getLineLength(int lineNo) {
			if (lineNo < 0 || lineNo >= size()) return 0;
			return get(lineNo).getFullLine().length();
		}
	}


	public static class DataStoreStdBinary<L extends AbstractLine> extends DataStoreStd<L> {

		public DataStoreStdBinary(AbstractLayoutDetails layoutDtls, Collection<L> arg0) {
			super(layoutDtls, arg0);
		}

		public DataStoreStdBinary(AbstractLayoutDetails layoutDtls, int arg0) {
			super(layoutDtls, arg0);
		}

		public DataStoreStdBinary(AbstractLayoutDetails layoutDtls) {
			super(layoutDtls);
		}

		@Override
		public DataStore<L> newDataStore() {
			return new DataStoreStdBinary<L>(getLayout(), this);
		}


		protected final int getLineLength(int lineNo) {
			if (lineNo < 0 || lineNo >= size()) return 0;
			return get(lineNo).getData().length;
		}
	}


	public static <L extends AbstractLine> DataStoreStd<L> newStore(AbstractLayoutDetails layoutDtls, Collection<L> arg0) {
		switch (layoutDtls.getOption(Options.OPT_STORAGE_TYPE)) {
		case Options.TEXT_STORAGE:		return new DataStoreStdTxt<L>(layoutDtls, arg0);
		case Options.BINARY_STORAGE:

		}
		return new DataStoreStdBinary<L>(layoutDtls, arg0);
	}

	public static <L extends AbstractLine> DataStoreStd<L> newStore(AbstractLayoutDetails layout, int lines) {

		switch (layout.getOption(Options.OPT_STORAGE_TYPE)) {
		case Options.TEXT_STORAGE:		return new DataStoreStdTxt<L>(layout, lines);
		case Options.BINARY_STORAGE:
		}
		return new DataStoreStdBinary<L>(layout, lines);
	}

	public static <L extends AbstractLine> DataStoreStd<L> newStore(AbstractLayoutDetails layoutDtls) {

		if (layoutDtls != null) {
			switch (layoutDtls.getOption(Options.OPT_STORAGE_TYPE)) {
			case Options.TEXT_STORAGE:		return new DataStoreStdTxt<L>(layoutDtls);
			case Options.BINARY_STORAGE:

			}
		}
		return new DataStoreStdBinary<L>(layoutDtls);
	}


}