package net.sf.RecordEditor.utils.fileStorage;

/**
 * This Interface describes a File Chunk where the data
 * is initially read from the source files as required.
 * 
 * @author Bruce Martin
 *
 * @param <L> Line Type stored in tghe chunk
 * @param <S> RecordStore Used
 */
public interface FileChunkFileBase<L extends AbstractChunkLine, S extends RecordStore> extends FileChunk<L, S> {

	
	
	
}
