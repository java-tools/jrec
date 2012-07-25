package net.sf.JRecord.Details;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordRunTimeException;

public abstract class BasicLayout<FieldDescription extends FieldDetail, RecordDescription extends AbstractRecordDetail<FieldDescription>>
implements AbstractLayoutDetails<FieldDescription, RecordDescription>{

	protected RecordDescription[] records;


	/**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getField(int, int)
	 */
	public  FieldDescription getField(final int layoutIdx, final int fieldIdx) {
		return records[layoutIdx].getField(fieldIdx);
	}


	/**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getRecord(int)
	 */
	public  RecordDescription getRecord(int recordNum) {
	    if (recordNum < 0 || records.length == 0) {
	        return null;
	    }
		return records[recordNum];
	}


    /**
	 * @see net.sf.JRecord.Details.AbstractLineDetails#getMaximumRecordLength()
	 */
    public  int getMaximumRecordLength() {
        int i;
        int maxSize = 0;
		for (i = 0; i < getRecordCount(); i++) {
			maxSize = java.lang.Math.max(maxSize, records[i].getLength());
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


	public AbstractLayoutDetails<FieldDescription, RecordDescription> getFilteredLayout(List<RecordFilter> filter) {
		AbstractLayoutDetails<FieldDescription, RecordDescription> ret ;
		ArrayList<RecordDescription> recs = new ArrayList<RecordDescription>(filter.size());
		ArrayList<FieldDescription>  fields;
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
				fields = new ArrayList<FieldDescription>(rec.getFieldCount());
				for (int j = 0; j < rec.getFieldCount(); j++) {
					fields.add(rec.getField(j));
				}
			} else {
				fields = new ArrayList<FieldDescription>(fieldDef.length);
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


	protected abstract AbstractLayoutDetails<FieldDescription, RecordDescription>
										getNewLayout(ArrayList<RecordDescription> recs);

	protected abstract  RecordDescription  getNewRecord(RecordDescription record, ArrayList<FieldDescription> fields);
}
