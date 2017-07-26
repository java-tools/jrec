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
      
package net.sf.JRecord.Details;

import net.sf.JRecord.Common.IBasicFileSchema;

public interface IBasicLayout extends IBasicFileSchema {

	public abstract boolean isBinCSV();

	/**
	 * Get the record Seperator bytes
	 *
	 * @return Record Seperator
	 */
	public abstract byte[] getRecordSep();

	
//	/**
//	 * get the field delimiter
//	 * @return the field delimeter
//	 */
//	public abstract byte[] getDelimiterBytes();

}
