package net.sf.RecordEditor.utils.fileStorage;

import javax.swing.text.Position;

import net.sf.JRecord.Details.AbstractLine;

public class DataStorePosition implements Position, IDataStorePosition {

	public int lineNumber, positionInLine;
	public int lineStart;
	public AbstractLine line;
	private final IDataStoreText ds;
	protected boolean lookupRequired = false;



	public DataStorePosition(int lineNumber, int lineStart, int offset,
			AbstractLine line, IDataStoreText ds) {
		super();
		this.lineNumber = lineNumber;
		this.lineStart = lineStart;
		this.positionInLine = offset;
		this.line = line;
		this.ds = ds;

//		}
	}



	/**
	 * @return the line
	 */
	@Override
	public AbstractLine getLine() {
		updatePos();
		return line;
	}



	/**
	 * @return the lineNumber
	 */
	@Override
	public int getLineNumber() {
		updatePos();
		return lineNumber;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getLineStart()
	 */
	@Override
	public int getLineStart() {
		updatePos();
		return lineStart;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getOffset()
	 */
	@Override
	public int getOffset() {
		updatePos();

		return lineStart + positionInLine;
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IDataStorePosition#getPositionInLine()
	 */
	@Override
	public int getPositionInLine() {
		return positionInLine;
	}



//	public void setLineStart(int lineStart) {
//		this.lineStart = lineStart;
//	}



	private void updatePos() {
		if (lookupRequired) {
			ds.updatePosition(this);
		}
	}

	public final void setLookupRequired() {
		lookupRequired = true;
	}

	public final boolean isValidPosition() {
		if (lookupRequired) {
			ds.updatePosition(this);
			return ! lookupRequired;
		}
		return true;
	}
}
