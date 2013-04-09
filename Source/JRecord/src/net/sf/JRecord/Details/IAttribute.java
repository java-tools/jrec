package net.sf.JRecord.Details;

public interface IAttribute {

	public final int TYPE_STORE_IN_MAP = 1;
	public final int TYPE_INT          = 2;

	/**
	 * Type of attribute (i.e. integer, store in map etc)
	 * @return Type of attribute (i.e. integer, store in map etc)
	 */
	public int getType();
}
