package net.sf.JRecord.IO;

import net.sf.JRecord.Common.AbstractManager;
import net.sf.JRecord.Common.IBasicFileSchema;
import net.sf.JRecord.Details.LineProvider;

public interface AbstractLineIOProvider extends AbstractManager {

	/**
	 * Gets a Record Reader Class that is appropriate for reading the
	 * supplied file-structure
	 *
	 * @param fileStructure File Structure of the required reader
	 *
	 * @return line reader
	 */
	public AbstractLineReader getLineReader(int fileStructure);

	public abstract AbstractLineReader getLineReader(IBasicFileSchema schema,
			LineProvider lineProvider);


	public abstract AbstractLineReader getLineReader(IBasicFileSchema schema);


	/**
	 * Gets a Record Reader Class that is appropriate for writing the
	 * supplied file-structure
	 *
	 * @param fileStructure File Structure of the required reader
	 * @param schema file description
	 * @param lineProvider Line-Provider used to create lines
	 *
	*/
	public abstract AbstractLineReader getLineReader(int fileStructure, IBasicFileSchema schema,
			LineProvider lineProvider);


	/**
	 * Gets a Record Reader Class that is appropriate for writing the
	 * supplied file-structure
	 *
	 * @param fileStructure File Structure of the required reader
	 * @param lineProvider Line-Provider used to create lines
	 *
	 * @return line reader
	 */
	public abstract AbstractLineReader getLineReader(int fileStructure,
			LineProvider lineProvider);

	/**
	 * Gets a Record Reader Class
	 *
	 * @param fileStructure File Structure
	 *
	 * @return record reader
	 */
	public abstract AbstractLineWriter getLineWriter(int fileStructure);

	/**
	 * Gets a Record Reader Class
	 *
	 * @param fileStructure File Structure
	 *
	 * @return record reader
	 */
	public abstract AbstractLineWriter getLineWriter(int fileStructure, String charset);

	/**
	 * wether a Copybook file is required
	 *
	 * @return wether a Copybook file is required
	 */
	public abstract boolean isCopyBookFileRequired(int fileStructure);

	/**
	 * Convert a file structure to a String
	 * @param fileStructure
	 * @return Name of the File Structure
	 */
	public abstract String getStructureName(int fileStructure);

	/**
	 * Get the file structure name for a particular index
	 * @param index index to get the name for
	 * @return requested name
	 */
	public abstract String getStructureNameForIndex(int index);

	/**
	 * Get line provider appropriate to the file Structure
	 * @return Returns the provider.
	 */
	public  abstract LineProvider getLineProvider(IBasicFileSchema schema);

	/**
	 * Get line provider appropriate to the file Structure
	 * @return Returns the provider.
	 */
	public  abstract LineProvider getLineProvider(int fileStructure, String charset);

}