/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
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