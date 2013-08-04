/**
 *
 */
package net.sf.JRecord.Details;

/**
 * Basic attribute class
 *
 * @author Bruce Martin
 *
 */
public class Attribute implements IAttribute {

	public static final Attribute FILE_STRUCTURE = new Attribute(TYPE_INT);
	private static int count;

	public final int id = count++;
	private final int type;



	public Attribute(int type) {
		super();
		this.type = type;
	}



	/* (non-Javadoc)
	 * @see net.sf.JRecord.Details.IAttribute#isStoredInMap()
	 */
	@Override
	public int getType() {
		return type;
	}

}
