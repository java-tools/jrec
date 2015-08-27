package net.sf.RecordEditor.utils.fileStorage;

import java.util.List;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;

/**
 * This interface describes a portion of the file as 
 * stored in the LargeDataStore and the operations
 * that it can executed on this chunk
 * 
 * @author Bruce Martin
 *
 * @param <L> Line Type stored in tghe chunk
 * @param <S> RecordStore Used
 */
public interface IFileChunk<L extends IChunkLine, S extends IRecordStore> {

	public abstract void add(AbstractLine l);

	public abstract void add(int idx, AbstractLine l);

	public int add(int idx, IDataStore<? extends AbstractLine> data, int start);


	public abstract void add(int pos, IRecordStore store);

	public void addAll(int idx, IDataStore<? extends AbstractLine> data);

	public abstract void addLineToManagedLines(L l);

	
	public abstract void clear();
	
	public abstract void compress();

	public abstract List<L> getActiveLines();
	
	public abstract int getCount();
	
	
	public abstract int getFirstLine();

	public abstract L getLine(int lineNo);
	
	public abstract int getLineLength(int lineNo);

	public abstract L getLineIfDefined(int lineNo);

	public abstract S getRecordStore();

	public abstract int getSize();

	public abstract int getSpaceUsed();

	public abstract L getTempLine(int lineNo);
	
	public abstract boolean isDocumentViewAvailable();

	public abstract boolean hasRoomForMore(AbstractLine l);

	public abstract void put(int idx, AbstractLine l);

	public abstract void remove(int idx);

	public abstract AbstractLine removeLine(int lineNo);

	public abstract void removeLineReference(int lineNo);

	public abstract void setFirstLine(int firstLine);

	public abstract void setLayout(LayoutDetail layout);

	public abstract List<? extends IFileChunk<L, S>> split();

	public abstract List<FileChunkBase<L, S>> splitAt(int pos);

	public abstract long getTextSize();

	public abstract void setFirstTextPosition(long firstTextPosition);

	public abstract long getFirstTextPosition();

	public abstract long calculateTextPosition(long size, int count);

}