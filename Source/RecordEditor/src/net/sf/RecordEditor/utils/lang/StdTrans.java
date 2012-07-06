package net.sf.RecordEditor.utils.lang;

public class StdTrans implements IStringTrans {

	/**
	 * @see net.sf.RecordEditor.utils.lang.IStringTrans#convert(java.lang.String)
	 */
	@Override
	public String convert(String s) {
		return s;
	}

	/**
	 * @see net.sf.RecordEditor.utils.lang.IStringTrans#convert(java.lang.String, java.lang.String)
	 */
	@Override
	public String convert(String s, String defaultStr) {
		return defaultStr;
	}
}
