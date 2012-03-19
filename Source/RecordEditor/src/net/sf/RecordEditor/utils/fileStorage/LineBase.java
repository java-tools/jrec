package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;

public abstract class LineBase extends Line 
implements AbstractChunkLine<FileChunkLine> {


	protected FileChunkLine chunk;
	protected int chunkLine;

	public LineBase(LayoutDetail group, FileChunkLine fileChunk, int line) {
		super(group);
		
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
	public FileChunkLine getChunk() {
		return chunk;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.file.storage.AbstractChunkLine#setChunk(net.sf.RecordEditor.edit.file.storage.FileChunk)
	 */
	public void setChunk(FileChunkLine chunk) {
		this.chunk = chunk;
	}


	protected final void updateChunk() {
		byte[] b = super.getLineData();
		chunk.put(chunkLine, b);
		//System.out.print("Update Chunk with");
		super.clearData();
	}



	//@SuppressWarnings("unchecked")
	@Override
	public Line getNewDataLine() {

		return new Line(layout, chunk.get(chunkLine));
	}
	
	@Override
	public byte[] getData() {
		synchronized (this) {
			return chunk.get(chunkLine);
		}
	}

	
	@Override
	protected byte[] getLineData() {
		synchronized (this) {
			return chunk.get(chunkLine);
		}
	}


}
