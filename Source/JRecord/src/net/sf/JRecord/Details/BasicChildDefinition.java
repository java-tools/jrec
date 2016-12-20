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

public class BasicChildDefinition<RecordDtl extends AbstractRecordDetail>
	implements AbstractChildDetails<RecordDtl> {

	private RecordDtl recordDefinition;
	public final int recordIndex;
	public int childIndex;

	public BasicChildDefinition(RecordDtl recordDef, int recordIdx, int childIdx) {

		recordDefinition = recordDef;
		recordIndex = recordIdx;
		childIndex= childIdx;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractChildDetails#getChildRecord()
	 */
	@Override
	public RecordDtl getChildRecord() {
		return recordDefinition;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractChildDetails#getName()
	 */
	@Override
	public String getName() {
		return recordDefinition.getRecordName();
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractChildDetails#isRepeated()
	 */
	@Override
	public boolean isRepeated() {
		return true;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractChildDetails#isRequired()
	 */
	@Override
	public boolean isRequired() {
		return false;
	}

	/**
	 * @return the recordIndex
	 */
	public final int getRecordIndex() {
		return recordIndex;
	}

	/**
	 * @return the childIndex
	 */
	public final int getChildIndex() {
		return childIndex;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  getName();
	}

	public boolean isMap() {
		return false;
	}


}
