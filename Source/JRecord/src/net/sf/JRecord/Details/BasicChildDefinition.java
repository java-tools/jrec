package net.sf.JRecord.Details;

import net.sf.JRecord.Common.FieldDetail;


public class BasicChildDefinition<RecordDtl extends AbstractRecordDetail<? extends FieldDetail>>
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
