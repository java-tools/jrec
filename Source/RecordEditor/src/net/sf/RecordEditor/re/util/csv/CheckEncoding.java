package net.sf.RecordEditor.re.util.csv;

import org.mozilla.intl.chardet.nsDetector;

public class CheckEncoding {
	
	public static String determineCharSet(byte[] bytes) {
		String s = "";
		nsDetector det = new nsDetector();
		if (! det.isAscii(bytes, bytes.length)) {
			String t;
			String[] charSets;
			det.DoIt(bytes, bytes.length, false);
			det.DataEnd();
			charSets = det.getProbableCharsets();
			if (charSets != null && charSets.length > 0) {
				s = charSets[0];
				if ("windows-1252".equalsIgnoreCase(s) && charSets.length > 1
				&&  ((t = charSets[1].toLowerCase()).startsWith("cp")
				  || (t.startsWith("utf") && (bytes[0] == -1 || bytes[0] == -2)) )) {
					s = charSets[1];
				}
			}
		}
		
		return s;
	}
}