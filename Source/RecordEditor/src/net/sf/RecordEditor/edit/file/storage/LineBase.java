package net.sf.RecordEditor.edit.file.storage;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;

public abstract class LineBase extends Line {

	protected FileChunk chunk;
	protected int chunkLine;
	
	public LineBase(LayoutDetail group, FileChunk fileChunk, int line) {
		super(group);
		
		
		chunk = fileChunk;
		chunkLine = line;
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


	public int getChunkLine() {
		return chunkLine;
	} 


	public int getActualLine() {
		return chunkLine + chunk.getFirstLine();
	} 

	public void setChunkLine(int chunkLine) {
		this.chunkLine = chunkLine;
	}

	public FileChunk getChunk() {
		return chunk;
	}

	public void setChunk(FileChunk chunk) {
		this.chunk = chunk;
	}


	protected final void updateChunk() {
		byte[] b = super.getLineData();
		chunk.put(chunkLine, b);
		//System.out.print("Update Chunk with");
		super.clearData();
	}



	@SuppressWarnings("unchecked")
	@Override
	public Line getNewDataLine() {

		return new Line(layout, chunk.get(chunkLine));
	}
//
//	
//	public static void writeByteArray(byte[] b) {
//		System.out.println();
//		
//		System.out.print(b.length + "-" );
//		for (int j = 0; j < b.length; j++) {
//			System.out.print("\t" + b[j] );
//		}
//	}
}
