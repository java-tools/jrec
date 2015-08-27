package net.sf.RecordEditor.utils.fileStorage;

import javax.swing.text.Position;

import net.sf.JRecord.Details.AbstractLine;

public interface IDataStorePosition extends Position {

	/**
	 * @see javax.swing.text.Position#getOffset()
	 */
	@Override
	public abstract int getOffset();

	/**
	 * @return the lineStart
	 */
	public abstract long getLineStartRE();

//	/**
//	 * @param lineStart the lineStart to set
//	 */
//	public abstract void setLineStart(int lineStart);

	/**
	 * @return the positionInLine
	 */
	public abstract int getPositionInLineRE();

	public abstract int getLineNumberRE();

	public abstract AbstractLine getLineRE();

}