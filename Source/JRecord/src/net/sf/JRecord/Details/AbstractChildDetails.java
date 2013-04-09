package net.sf.JRecord.Details;

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
