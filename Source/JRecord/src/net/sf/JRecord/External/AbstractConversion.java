package net.sf.JRecord.External;


/**
 * This interface describes a class that will convert a Type/Format from the
 * external String representation to the internal integer value
 *
 *  @author Bruce Martin
 *
 */
public interface AbstractConversion {

	/**
	 * Convert a String to a Type value
	 * @param idx db index
	 * @param type Type (String)
	 *
	 * @return integer type value
	 */
	public abstract int getType(int idx, String type);


	/**
	 * Convert a String to a Formay value
	 * @param idx db index
	 * @param format Format (String)
	 *
	 * @return format
	 */
	public abstract int getFormat(int idx, String format);


	/**
	 * Convert type to a string
	 * @param idx db index
	 * @param type type Id
	 * @return Type as a String
	 */
	public abstract String getTypeAsString(int idx, int type);

//	/**
//	 * Get list of available types
//	 * @param idx Database Index
//	 * @return all types
//	 */
//	public abstract List<BasicKeyedField> getTypes(int idx);


	/**
	 * Convert format to a string
	 * @param idx db index
	 * @return format as a String
	 */
	public abstract String getFormatAsString(int idx, int format);


}
