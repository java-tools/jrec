package net.sf.RecordEditor.utils.lang;

public class StdTrans extends BasicTrans {

	/**
	 * @see net.sf.JRecord.Common.ITranslation#convert(java.lang.String, java.lang.String)
	 */
	@Override
	public String convert(String s, String defaultStr) {
		return defaultStr;
	}
}
