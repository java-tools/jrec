package net.sf.RecordEditor.edit.file.storage;

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
public interface FileChunk<L extends AbstractChunkLine, S extends RecordStore> {

	public abstract int getCount();

	public abstract void add(AbstractLine l);

	public abstract void add(int idx, AbstractLine l);

	public abstract void addLineToManagedLines(L l);

	
	public abstract L getLine(int lineNo);
	
	public abstract L getTempLine(int lineNo);

	public abstract L getLineIfDefined(int lineNo);
	
	public abstract List<L> getActiveLines();
	
	
	public abstract void put(int idx, AbstractLine l);

	public abstract AbstractLine removeLine(int lineNo);

	public abstract void remove(int idx);

	public abstract List<FileChunkBase<L, S>> split();

	public abstract void compress();

	public abstract boolean hasRoomForMore(AbstractLine l);

	public abstract int getFirstLine();

	public abstract void setFirstLine(int firstLine);

	public abstract void setLayout(LayoutDetail layout);

	public abstract int getSpaceUsed();

	public abstract int getSize();

}