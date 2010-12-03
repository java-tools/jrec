package net.sf.RecordEditor.edit.file.storage;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;

public interface AbstractChunkLine<fc extends FileChunkBase>
extends AbstractLine<LayoutDetail> {

	public abstract int getChunkLine();

	public abstract int getActualLine();

	public abstract void setChunkLine(int chunkLine);

	public abstract fc getChunk();

	public abstract void setChunk(fc chunk);

}