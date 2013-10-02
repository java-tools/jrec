package net.sf.RecordEditor.re.util.csv;

import java.nio.charset.Charset;

import org.mozilla.intl.chardet.nsDetector;

public class CheckEncoding {

	/**
	 *
	 * @param bytes Data to check
	 * @param returnLikelyCharsets wether to retur a list of likely charsets
	 *
	 * @return charset Details
	 */
	public static CharsetDetails determineCharSet(byte[] bytes, boolean returnLikelyCharsets) {
		String s = "";
		nsDetector det = new nsDetector();
		boolean possibleUtf16 = utf16Check(bytes);
		String[] charSets = null;

		if (returnLikelyCharsets || utfCheck(bytes) || possibleUtf16 || ! det.isAscii(bytes, bytes.length)) {
			String t;
			det.DoIt(bytes, bytes.length, false);
			det.DataEnd();
			charSets = det.getProbableCharsets();
			if (charSets != null && charSets.length > 0) {
				s = charSets[0];

				if (Charset.defaultCharset().displayName().equalsIgnoreCase(s)) {
					s = "";
					charSets[0] = "";
				}

				if ("".equals(s)
				&& charSets.length > 1
				&&  ((t = charSets[1].toLowerCase()).startsWith("cp")
				  || (t.startsWith("utf") && utfCheck(bytes)) )) {
					s = charSets[1];
				} else {
					if (possibleUtf16) {
						int check = Math.min(5, charSets.length);
						for (int i = 0; i < check; i++) {
							if (charSets[i].toLowerCase().startsWith("utf-16")) {
								s = charSets[i];
								break;
							}
						}
					}
				}
			}
		} else {
			System.out.println("is ascii " + Charset.defaultCharset().displayName() + "windows-1252");
		}

		return new CharsetDetails(s, charSets);
	}

	private static boolean utfCheck(byte[] bytes) {
		return bytes != null && bytes.length > 0 && (bytes[0] == -1 || bytes[0] == -2);
	}

	/**
	 * Check if po9ssibly UTF16 (containing mostly ascii chars)
	 *
	 * @param bytes vbytes to check
	 * @return
	 */
	private static boolean utf16Check(byte[] bytes) {
		boolean ret = false;
		if (bytes != null && bytes.length > 0) {
			int count = 0;
			for (int i = 1; i < bytes.length; i++) {
				if (bytes[i] != 0 && bytes[i-1] == 0) {
					count += 1;
				}
			}

			ret = count > bytes.length * 3 / 8 && bytes.length > 15;
		}

		return ret;
	}
}
