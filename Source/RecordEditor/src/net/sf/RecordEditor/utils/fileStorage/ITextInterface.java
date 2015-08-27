package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.AbstractLine;

/**
 * This interface acts as an interface between the Document store and 
 * java's "Document" interface
 * 
 * @author Bruce Martin
 *
 */
public interface ITextInterface {

	/**
	 * @return "Document" or Text-Length of the Datastore
	 */
	public abstract long length();

	/**
	 * Get the "Document" position of a "Text Position"
	 * @param pos Relative Text Position
	 * @return "Document" position
	 */
	public abstract DataStorePosition getTextPosition(long pos);

	/**
	 * Get the "Document" position of a line
	 * @param lineNo line Number to get the "Document" position
	 * @return "Document" position
	 */
	public abstract DataStorePosition getPositionByLineNumber(int lineNo);

//	public abstract void updatePosition(DataStorePosition pos);
	
	public abstract IDataStore<? extends AbstractLine> getDataStore();

	public long getCharPosition(int lineNo);

}