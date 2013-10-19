package net.sf.JRecord.Details;

import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;

/**
 * Parent class for Record (or Message) Definitions used
 * in a Schema Definition
 *
 * @author Bruce Martin
 *
 * @param <FieldDefinition> Field Definition class
 * @param <recordDtls> Record (or Message) Definition class
 * @param <ChildDtls> Child (or Tree) definition class
 */
public abstract class BasicRecordDetail<FieldDefinition extends AbstractRecordDetail.FieldDetails,
										recordDtls 		extends AbstractRecordDetail,
										ChildDtls 		extends AbstractChildDetails<recordDtls>> {

	protected int fieldCount;
	protected FieldDefinition[] fields;
	private int sourceIndex = 0;
	private int parentRecordIdx =  Constants.NULL_INTEGER;
//	protected ChildDtls[] childRecords = null;
	protected List<ChildDtls> childRecords = null;


	public final int getFieldCount() {
	    return fieldCount;
	}

	public final int getSourceIndex() {
		return sourceIndex;
	}

	public final void setSourceIndex(int sourceIndex) {
		this.sourceIndex = sourceIndex;
	}


//    /**
//	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getField(int)
//	 */
//    public final IFieldDetail getField(int idx) {
//        return this.fields[idx];
//    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getField(int)
	 */
	public FieldDefinition getField(int idx) {
		if (idx >= 0 && idx < this.fields.length) {
			return this.fields[idx];
		}
		return null;
	}

	/**
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getFieldIndex(java.lang.String)
	 */
    public final int getFieldIndex(String fieldName) {
        int ret = Constants.NULL_INTEGER;
        int i;

        if (fieldName != null) {
            for (i = 0; i < fieldCount; i++) {
            	//System.out.print("\t >" + fieldName + "< : >" + fields[i].getName() + "< - " + fieldName.equalsIgnoreCase(fields[i].getName()));
                if (fieldName.equalsIgnoreCase(fields[i].getName())) {
                    ret = i;
                    break;
                }
            }
//            System.out.println();
//            System.out.println();
        }
        return ret;
    }


    /* (non-Javadoc)
	 * @see net.sf.JRecord.Details.AbstractRecordDetail#getField(java.lang.String)
	 */
    public final AbstractRecordDetail.FieldDetails getField(String fieldName) {
    	AbstractRecordDetail.FieldDetails ret = null;
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
		return childRecords.size();
	}

	public ChildDtls getChildRecord(int idx) {
		return childRecords.get(idx);
	}

	public int getOption(int option) {
		return Options.UNKNOWN;
	}
}