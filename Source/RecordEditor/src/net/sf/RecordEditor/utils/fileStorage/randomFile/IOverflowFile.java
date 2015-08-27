package net.sf.RecordEditor.utils.fileStorage.randomFile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface IOverflowFile {

	/**
	 * Get file writer fora supplied position/length
	 * 
	 * @param pos in the file where data is to be written.
	 * @param length length to be written
	 * @return requested data writer
	 * 
	 * @throws IOException
	 */
	public abstract DataOutput getWriter(long pos, int length)
			throws IOException;

	/**
	 * free the output file
	 * @param out output file to be free'd
	 * 
	 * @throws IOException
	 */
	public abstract void free(DataOutput out) throws IOException;

	/**
	 * Get file reader positioned at pos
	 * @param pos position in the file to seak
	 * @return requested reader
	 * 
	 * @throws IOException
	 */
	public abstract DataInput getReader(long pos) throws IOException;

	/**
	 * free the input file
	 * @param in input file to be free'd
	 * 
	 * @throws IOException
	 */
	public abstract void free(DataInput in) throws IOException;

	public abstract void clear();

}