package net.sf.JRecord.Details;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;

public abstract class BasicRecordDetail<FieldDefinition extends FieldDetail,
										recordDtls 		extends AbstractRecordDetail<FieldDefinition>,
										ChildDtls 		extends AbstractChildDetails<recordDtls>> {

	protected int fieldCount;
	protected FieldDefinition[] fields;
	private int sourceIndex = 0;
	private int parentRecordIdx =  Constants.NULL_INTEGER;
	protected ChildDtls[] childRecords = null;


	public final int getFieldCount() {
	    return fieldCount;
	}

	public final int getSourceIndex() {
		return sourceIndex;
	}

	public final void setSourceIndex(int sourceIndex) {
		this.sourceIndex = sourceIndex;
	}


    /**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getField(int)
	 */
    public final FieldDefinition getField(int idx) {
        return this.fields[idx];
    }


    /**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFieldIndex(java.lang.String)
	 */
    public final int getFieldIndex(String fieldName) {
        int ret = Constants.NULL_INTEGER;
        int i;

        if (fieldName != null) {
            for (i = 0; i < fieldCount; i++) {
                if (fieldName.equalsIgnoreCase(fields[i].getName())) {
                    ret = i;
                    break;
                }
            }
        }
        return ret;
    }

    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getField(java.lang.String)
	 */
    public final FieldDefinition getField(String fieldName) {
        FieldDefinition ret = null;
        int idx = getFieldIndex(fieldName);

        if (idx >= 0) {
            ret = fields[idx];
        }

        return ret;
    }


	public int getParentRecordIndex() {
		return parentRecordIdx;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#setParentRecordIndex(int)
	 */
	public void setParentRecordIndex(int parentRecordIndex) {
		parentRecordIdx = parentRecordIndex;
	}
	
	public int getChildRecordCount() {
		if (childRecords == null) {
			return 0;
		}
		return childRecords.length;
	}

	public ChildDtls getChildRecord(int idx) {
		return childRecords[idx];
	}
}