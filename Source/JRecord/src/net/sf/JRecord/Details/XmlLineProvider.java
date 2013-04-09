package net.sf.JRecord.Details;

/**
 * Create a XmlLine from a String / Byte array
 *
 * @author Bruce Martin
 *
 */
public class XmlLineProvider implements LineProvider<LayoutDetail, XmlLine> {

	/**
	 * {@link LineProvider#getLine(LayoutDetail)}
	 */
	public XmlLine getLine(LayoutDetail recordDescription) {
		return new XmlLine(recordDescription, -1);
	}

	/**
	 * @see LineProvider#getLine(LayoutDetail, String)
	 */
	public XmlLine getLine(LayoutDetail recordDescription, String linesText) {
		return getLine(recordDescription);
	}

	/**
	 * @see LineProvider#getLine(LayoutDetail, byte[])
	 */
	public XmlLine getLine(LayoutDetail recordDescription, byte[] lineBytes) {
		return getLine(recordDescription);
	}

}
