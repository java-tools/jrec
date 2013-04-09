package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.AbstractLine;

public interface AbstractChunkLine<fc extends FileChunk>
extends AbstractLine {

	public static final String CAN_NOT_UPDATE_FIELD_IN_TEMPORARY_LINE = "Can not update field in temporary Line";
	public static final String CAN_NOT_UPDATE_TEMPORARY_LINE = "Can not update temporary Line";

	public abstract int getChunkLine();

	public abstract int getActualLine();

	public abstract void setChunkLine(int chunkLine);

	public abstract fc getChunk();

	public abstract void setChunk(fc chunk);

}