package net.sf.JRecord.Details;

/**
 * Create a XmlLine from a String / Byte array
 * 
 * @author Bruce Martin
 *
 */
public class XmlLineProvider implements LineProvider<LayoutDetail> {

	/**
	 * {@link LineProvider#getLine(LayoutDetail)}
	 */
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription) {
		return new XmlLine(recordDescription, -1);
	}

	/**
	 * @see LineProvider#getLine(LayoutDetail, String)
	 */
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription, String linesText) {
		return getLine(recordDescription);
	}

	/**
	 * @see LineProvider#getLine(LayoutDetail, byte[])
	 */
	public AbstractLine<LayoutDetail> getLine(LayoutDetail recordDescription, byte[] lineBytes) {
		return getLine(recordDescription);
	}

}
