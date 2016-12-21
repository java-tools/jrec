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

import net.sf.JRecord.Common.AbstractFieldValue;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;

/**
 * Base class for all the "Line" classes to extends. It implements
 * basic common methods for all the "Line" classes
 *
 * @author Bruce Martin
 *
 * @param <Layout> Schema Type to be used by this line
 */
public abstract class BaseLine<Layout extends AbstractLayoutDetails>  implements AbstractLine {

	protected Layout layout;

//	public BaseLine() {
//		super();
//	}

	public BaseLine(Layout layout) {
		super();
		this.layout = layout;
	}

//	/**
//	 * @param pLayout The layouts to set.
//	 */
//	public void setLayout(final AbstractLayoutDetails pLayout) {
//		this.layout = (Layout) pLayout;
//	}

	/**
	 * Get the Layout
	 * @return Returns the layouts.
	 */
	public final Layout getLayout() {
	    return layout;
	}


	/**
	 * Get a fields value
	 *
	 * @param fieldName field to retrieve
	 *
	 * @return fields Value
	 */
	public Object getField(String fieldName) {
		IFieldDetail fld = layout.getFieldFromName(fieldName);

	   	if (fld == null) {
	   		return null;
	   	}

	   	return getField(fld);
	}



	@Override
	public final AbstractFieldValue getFieldValue(IFieldDetail field) {
		return new FieldValue(this, field);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractIndexedLine#getField(int, int)
	 */
	@Override
	public Object getField(int recordIdx, int fieldIdx) {
		return getFieldValue(recordIdx, fieldIdx);
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractIndexedLine#getField(net.sf.JRecord.Common.IFieldDetail)
	 */
	@Override
	public Object getField(IFieldDetail field) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractIndexedLine#getPreferredLayoutIdx()
	 */
	@Override
	public int getPreferredLayoutIdx() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractIndexedLine#setField(int, int, java.lang.Object)
	 */
	@Override
	public void setField(int recordIdx, int fieldIdx, Object val) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.AbstractIndexedLine#setField(net.sf.JRecord.Common.IFieldDetail, java.lang.Object)
	 */
	@Override
	public void setField(IFieldDetail field, Object value) {
		// TODO Auto-generated method stub
		
	}

//	/* (non-Javadoc)
//	 * @see net.sf.JRecord.Details.AbstractLine#getFieldValueIfExists(int, int)
//	 */
//	@Override
//	public AbstractFieldValue getFieldValueIfExists(int recordIdx, int fieldIdx) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public final  AbstractFieldValue getFieldValue(int recordIdx, int fieldIdx) {
		return new FieldValue(this, recordIdx, fieldIdx);
	}

	@Override
	public final AbstractFieldValue getFieldValue(String fieldName) {
		return  getFieldValue(layout.getFieldFromName(fieldName));
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLine#getFieldValueIfExists(java.lang.String)
	 */
	@Override
	public AbstractFieldValue getFieldValueIfExists(String fieldName) {
		return getFieldValue(fieldName);
	}

	/**
	 * Set a field via its name
	 *
	 * @param fieldName fieldname to be updated
	 * @param value value to be applied to the field
	 *
	 * @throws RecordException any conversion error
	 */
	public final void setField(String fieldName, Object value) throws RecordException {
		IFieldDetail fld = layout.getFieldFromName(fieldName);

		if (fld != null) {
			setField(fld, value);
		}
	}

	/**
	 * Get Field Iterator for the requested Record-Type
	 * @param recordNumber Record Name
	 * @return Field Iterator
	 */
	public final FieldIterator getFieldIterator(String recordName) {
		int recordNumber = layout.getRecordIndex(recordName);
		if (recordNumber < 0) {
			throw new RuntimeException("Record: " + recordName + " does not exist in layout");
		}
		return new FieldIterator(this, recordNumber);
	}

	/**
	 * Get Field Iterator for the preferred-Record-Type
	 * @return Field Iterator
	 */
	public final FieldIterator getFieldIterator() {
		return new FieldIterator(this, getPreferredLayoutIdx());
	}

	/**
	 * Get Field Iterator for the requested Record-Type
	 * @param recordNumber record number
	 * @return Field Iterator
	 */
	public final FieldIterator getFieldIterator(int recordNumber) {
		return new FieldIterator(this, recordNumber);
	}

	@Override
	public int getOption(int optionId) {
		switch (optionId) {
		case Options.OPT_GET_FIELD_COUNT:
			int preferredLayoutIdx = getPreferredLayoutIdx();
			if (preferredLayoutIdx >= 0) {
				return layout.getRecord(preferredLayoutIdx).getFieldCount();
			}
			break;
		}
		return Options.UNKNOWN;
	}

	
	
}