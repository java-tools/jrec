package net.sf.RecordEditor.utils.fileStorage;

import javax.swing.text.Position;

import net.sf.JRecord.Details.AbstractLine;

public interface IDataStorePosition extends Position {

	/**
	 * @see javax.swing.text.Position#getOffset()
	 */
	public abstract int getOffset();

	/**
	 * @return the lineStart
	 */
	public abstract int getLineStart();

//	/**
//	 * @param lineStart the lineStart to set
//	 */
//	public abstract void setLineStart(int lineStart);

	/**
	 * @return the positionInLine
	 */
	public abstract int getPositionInLine();

	public abstract int getLineNumber();

	public abstract AbstractLine getLine();

}