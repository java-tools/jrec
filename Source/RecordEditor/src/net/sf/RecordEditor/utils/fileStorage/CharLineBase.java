package net.sf.RecordEditor.utils.fileStorage;

import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;

public abstract class CharLineBase extends CharLine 
implements IChunkLine<FileChunkCharLine> {

	protected FileChunkCharLine chunk;
	protected int chunkLine;
	private boolean live = true;

	public CharLineBase(LayoutDetail group, FileChunkCharLine fileChunk,
			int line) {
		super(group, null);
		
		chunk = fileChunk;
		chunkLine = line;
	}





	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.AbstractChunkLine#getChunkLine()
	 */
	public int getChunkLine() {
		return chunkLine;
	} 


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.AbstractChunkLine#getActualLine()
	 */
	public int getActualLine() {
		return chunkLine + chunk.getFirstLine();
	} 

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.AbstractChunkLine#setChunkLine(int)
	 */
	public void setChunkLine(int chunkLine) {
		this.chunkLine = chunkLine;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.AbstractChunkLine#getChunk()
	 */
	public FileChunkCharLine getChunk() {
		return chunk;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.AbstractChunkLine#setChunk(net.sf.RecordEditor.edit.file.storage.FileChunk)
	 */
	public void setChunk(FileChunkCharLine chunk) {
		this.chunk = chunk;
	}


	protected final void updateChunk() {
		String b = super.getLineData();
		chunk.putFromLine(chunkLine, b.toCharArray());
		//System.out.print("Update Chunk with");
		super.clearData();
	}


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





	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IChunkLine#setDead()
	 */
	@Override
	public void setDead() {
		live = false;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.fileStorage.IChunkLine#isLive()
	 */
	@Override
	public boolean isLive() {
		return live;
	}


}
