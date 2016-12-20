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

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordRunTimeException;

/**
 * Parent class for various Schema (layout) definitions.
 * It contains common methods used by all the Schema types.
 *
 * @author Bruce Martin
 *
 * @param <RecordDescription>
 */
public abstract class BasicLayout<RecordDescription extends AbstractRecordDetail>
implements AbstractLayoutDetails {

	protected RecordDescription[] records;

	int maxSize;
	public BasicLayout() {
		maxSize = -1;
	}

	public BasicLayout(int maximumSize) {
		maxSize = maximumSize;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getField(int, int)
	 */
	public  RecordDescription.FieldDetails getField(final int layoutIdx, final int fieldIdx) {
		return records[layoutIdx].getField(fieldIdx);
	}


	/**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getRecord(int)
	 */
	public  RecordDescription getRecord(int recordNum) {
	    if (recordNum < 0 || recordNum >= records.length) {
	        return null;
	    }
		return records[recordNum];
	}


    /**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getMaximumRecordLength()
	 */
    public  int getMaximumRecordLength() {

        if ( maxSize < 0 ) {
			for (int i = 0; i < getRecordCount(); i++) {
				maxSize = java.lang.Math.max(maxSize, records[i].getLength());
			}
        }

		return maxSize;
    }


    /**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getRecordIndex(java.lang.String)
	 */
    public  int getRecordIndex(String recordName) {
        int ret = Constants.NULL_INTEGER;
        int i;

        if (recordName != null) {
            for (i = 0; i < getRecordCount(); i++) {
                if (recordName.equalsIgnoreCase(records[i].getRecordName())) {
                    ret = i;
                    break;
                }
            }
        }
        return ret;
    }


	public AbstractLayoutDetails getFilteredLayout(List<? extends RecordFilter> filter) {
		AbstractLayoutDetails ret ;
		ArrayList<RecordDescription> recs = new ArrayList<RecordDescription>(filter.size());
		ArrayList<RecordDescription.FieldDetails>  fields;
		int recIdx, fldIdx;

		String[] fieldDef;
		RecordDescription rec;
		//int i = 0;
		for (RecordFilter recDef : filter) {
			recIdx = getRecordIndex(recDef.getRecordName());

			fieldDef = recDef.getFields();

			if (recIdx < 0) {
				throw new RecordRunTimeException(
						"Compare Error- Record {0} from filter was not found in record Layout",
						recDef.getRecordName());
			}

			rec = this.getRecord(recIdx);
			if (fieldDef == null || fieldDef.length == 0) {
				fields = new ArrayList<RecordDescription.FieldDetails>(rec.getFieldCount());
				for (int j = 0; j < rec.getFieldCount(); j++) {
					fields.add(rec.getField(j));
				}
			} else {
				fields = new ArrayList<RecordDescription.FieldDetails>(fieldDef.length);
				for (int j = 0; j < fieldDef.length; j++) {
					fldIdx = rec.getFieldIndex(fieldDef[j]);
					if (fldIdx < 0) {
						throw new RecordRunTimeException(
								"Compare Error- Record/Field {0}/{1} from filter was not found in record Layout",
								new Object[] {recDef.getRecordName(), fieldDef[j]});

					}
					fields.add(rec.getField(fldIdx));
				}
			}
			recs.add(getNewRecord(rec, fields));
		}

		ret = getNewLayout(recs);

//		ret = new LayoutDetail(getLayoutName(), recs, getDescription(),
//				getLayoutType(), getRecordSep(), getEolString(),
//				getFontName(), getDecider(), getFileStructure());

//		System.out.println("Field Counts " + ret.getRecord(0).getFieldCount()
//				+ " ~ "  + ret.getRecord(1).getFieldCount());

		return ret;

	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLayoutDetails#hasMaps()
	 */
	@Override
	public boolean isMapPresent() {
		return false;
	}


	protected abstract AbstractLayoutDetails getNewLayout(ArrayList<RecordDescription> recs);

	protected abstract RecordDescription  getNewRecord(RecordDescription record, ArrayList<? extends RecordDescription.FieldDetails> fields);


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLayoutDetails#getAttribute(net.sf.JRecord.Details.IAttribute)
	 */
	@Override
	public Object getAttribute(IAttribute attr) {

		return null;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractLayoutDetails#setAttribute(net.sf.JRecord.Details.IAttribute, java.lang.Object)
	 */
	@Override
	public void setAttribute(IAttribute attr, Object value) {

	}

	@Override
	public int getOption(int option) {
		return Options.UNKNOWN;
	}


	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.IBasicFileSchema#getQuote()
	 */
	@Override
	public String getQuote() {
		return null;
	}


}
