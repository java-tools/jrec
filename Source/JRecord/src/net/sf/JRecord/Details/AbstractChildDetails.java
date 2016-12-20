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

/**
 * Description of the Record Tree Structure (used in Protocol buffers and Avro
 * File definitions)
 *
 * @author Bruce Martin
 *
 * @param <RecordDef> Record Definition
 */
public interface AbstractChildDetails<RecordDef extends AbstractRecordDetail> {

	/**
	 * Get name of Child records
	 * @return name of Child records
	 */
	public String getName();

	/**
	 * Get Record Definition
	 * @return Record Definition
	 */
	public RecordDef getChildRecord();

	/**
	 * Whether there are multiple records or not
	 * @return  Whether there are multiple records or not
	 */
	public boolean isRepeated();

	/**
	 * Whether the child is required ???
	 * @return  Whether the child is required ???
	 */
	public boolean isRequired();

	/**
	 * @return the recordIndex
	 */
	public int getRecordIndex();

	/**
	 * @return the childIndex
	 */
	public int getChildIndex();

	/**
	 * is a this a map field
	 * @return if it is a map field
	 */
	public boolean isMap();
}
