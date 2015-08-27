package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.AbstractLine;

public class DataStorePosition implements IDataStorePosition {

	public int lineNumber, positionInLine;
	public long lineStart;
	public AbstractLine line;
	private final ITextInterface textInterface;
	protected boolean lookupRequired = false;



	public DataStorePosition(int lineNumber, long lineStart, int offset,
			AbstractLine line, ITextInterface ds) {
		super();
		this.lineNumber = lineNumber;
		this.lineStart = lineStart;
		this.positionInLine = offset;
		this.line = line;
		this.textInterface = ds;

//		}
	}



	/**
	 * @return the line
	 */
	@Override
	public AbstractLine getLineRE() {
		updatePos();
		return line;
	}



	/**
	 * @return the lineNumber
	 */
	@Override
	public int getLineNumberRE() {
		updatePos();
		return lineNumber;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getLineStart()
	 */
	@Override
	public long getLineStartRE() {
		updatePos();
		return lineStart;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getOffset()
	 */
	@Override
	public int getOffset() {
		updatePos();

		return (int) (lineStart + positionInLine);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getPositionInLine()
	 */
	@Override
	public int getPositionInLineRE() {
		return positionInLine;
	}



//	public void setLineStart(int lineStart) {
//		this.lineStart = lineStart;
//	}



	private void updatePos() {
		if (lookupRequired) {
			updatePositionRE();
		}
	}

	public final void setLookupRequiredRE() {
		lookupRequired = true;
	}

	public final boolean isValidPositionRE() {
		boolean revalidateRequired = isRevalidateRequiredRE();
		if (lookupRequired || revalidateRequired) {
			updatePosition(revalidateRequired);
			return ! lookupRequired;
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public final boolean isRevalidateRequiredRE() {

		IDataStore<? extends AbstractLine> dataStore = this.textInterface.getDataStore();
		if (line instanceof IChunkLine) {
			if (((IChunkLine) line).isLive()) {
				return dataStore.indexOf(line) != lineNumber;
			} else {
				return  true;
			}
		} else {
			return lineNumber < 0 || lineNumber >= dataStore.size() || dataStore.get(lineNumber) != line;
		}
	}
	
	public void updatePositionRE() {
		updatePosition(isRevalidateRequiredRE());
	}
	
	private void updatePosition(boolean revalidateRequired) {
	
		IDataStore<? extends AbstractLine> ds = this.textInterface.getDataStore();
		if (revalidateRequired || lineNumber >= ds.size() || lineNumber < 0) {
			int idx = ds.indexOf(line); 
			if (idx >= 0) {
				lineNumber = idx;
			} else if (lineNumber >= ds.size() || lineNumber < 0) {
				return;
//				throw new RuntimeException("Position is no longer valid");
			} else  {
				if (lineNumber > 0) {
					lineNumber -= 1;
				}
				line = ds.get(lineNumber);
			}
		}
 
		lookupRequired = false;
		lineStart = textInterface.getCharPosition(lineNumber);
	}
}
