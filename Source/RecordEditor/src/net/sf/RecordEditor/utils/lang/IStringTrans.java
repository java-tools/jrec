package net.sf.RecordEditor.utils.lang;

import net.sf.RecordEditor.utils.params.Parameters;

public interface IStringTrans {

	public static final boolean HIGHLIGHT_TEXT =
			"y".equalsIgnoreCase(
						Parameters.getString(Parameters.HIGHLIGHT_MISSING_TRANSLATIONS));
	public String convert(String s);
	public String convert(String s, String defaultStr);
}
