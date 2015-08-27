package net.sf.RecordEditor.re.util.csv;

import java.util.HashSet;

import net.sf.RecordEditor.utils.common.Common;

public class BasicCharsetChecker {

	
	public static final HashSet<String> STANDARD_CHARS = new HashSet<String>();

	static {
		String s = Common.STANDARD_CHARS;

		for (int i = 0; i < s.length(); i++) {
			STANDARD_CHARS.add(s.substring(i, i+1));
		}

		for (int i = 0 ; i < Common.FIELD_SEPARATOR_TEXT_LIST.length; i++) {
			STANDARD_CHARS.add(Common.FIELD_SEPARATOR_LIST1_VALUES[i]);
		}
		STANDARD_CHARS.add("\n");
	}

	

	/**
	 * @return the validChars
	 */
	public static double getValidCharsRatio(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (STANDARD_CHARS.contains(s.substring(i, i+1))) {
				count += 1;
			}
		}
		return (double)count / (double)  s.length();
	}
}
