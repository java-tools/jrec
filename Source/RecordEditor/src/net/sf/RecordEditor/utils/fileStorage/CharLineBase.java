package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;

public abstract class CharLineBase extends CharLine 
implements AbstractChunkLine<FileChunkCharLine> {

	protected FileChunkCharLine chunk;
	protected int chunkLine;

	public CharLineBase(LayoutDetail group, FileChunkCharLine fileChunk,
			int line) {
		super(group, null);
		
		chunk = fileChunk;
		chunkLine = line;
	}





	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.AbstractChunkLine#getChunkLine()
	 */
	public int getChunkLine() {
		return chunkLine;
	} 


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.AbstractChunkLine#getActualLine()
	 */
	public int getActualLine() {
		return chunkLine + chunk.getFirstLine();
	} 

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.AbstractChunkLine#setChunkLine(int)
	 */
	public void setChunkLine(int chunkLine) {
		this.chunkLine = chunkLine;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.AbstractChunkLine#getChunk()
	 */
	public FileChunkCharLine getChunk() {
		return chunk;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.AbstractChunkLine#setChunk(net.sf.RecordEditor.edit.file.storage.FileChunk)
	 */
	public void setChunk(FileChunkCharLine chunk) {
		this.chunk = chunk;
	}


	protected final void updateChunk() {
		String b = super.getLineData();
		chunk.put(chunkLine, b.toCharArray());
		//System.out.print("Update Chunk with");
		super.clearData();
	}



	@SuppressWarnings("unchecked")
	@Override
	public CharLine getNewDataLine() {

		return new CharLine(layout, chunk.getString(chunkLine));
	}
	
	@Override
	public byte[] getData() {
		synchronized (this) {
			return chunk.get(chunkLine);
		}
	}

	
	@Override
	protected String getLineData() {
		synchronized (this) {
			return chunk.getString(chunkLine);
		}
	}


}
