package net.sf.RecordEditor.utils.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import net.sf.JRecord.Common.Conversion;

public class PoTrans extends BasicTrans {
	private static final int NORMAL_MODE = 0;
	private static final int TRANS_MODE = 1;

	private HashMap<String, String> trans = new HashMap<String, String>(1500);

	protected PoTrans(File f) throws IOException {
		PoHeader poh = PoUtil.parsePOHeader(f);
		BufferedReader r;
		String l;
		String lookup = null;
		StringBuilder b = null;
		int mode = NORMAL_MODE;

		if (poh.font.equals("")) {
			 r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} else {
			 try {
				r = new BufferedReader(new InputStreamReader(new FileInputStream(f), poh.font));
			} catch (Exception e) {
				r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			}
		}

		try {
			while ((l = r.readLine()) != null) {
				if ("".equals(l.trim())) {
					mode = NORMAL_MODE;

					if (lookup != null && b != null) {
						b = Conversion.replace(b, "\\n", "\n");
						trans.put(lookup, b.toString());
						//System.out.println("~~ " + lookup + " " + b.toString());
					}
					b = null;
					lookup = null;
				} else if (mode == TRANS_MODE) {
					b.append(strip(l));
				} else {
					mode = NORMAL_MODE;
					if (l.startsWith("msgid ")) {
						lookup = strip(l.substring(6));
					} else if (l.startsWith("msgstr ")) {
						b = new StringBuilder(strip(l.substring(7)));
						mode = TRANS_MODE;
					}
				}
			}
		} finally {
			r.close();
		}
	}


	private static String strip(String s) {
		s = s.trim();
		if (s.startsWith("\"")) {
			s = s.substring(1);
		}
		if (s.endsWith("\"")) {
			s = s.substring(0, s.length() - 1);
		}
		if (s.indexOf("\"") > 0) {
			s = Conversion.replace(new StringBuilder(s), "\\\"", "\"").toString();
		}

		return s;
	}

	/**
	 * @see net.sf.JRecord.Common.ITranslation#convert(java.lang.String, java.lang.String)
	 */
	@Override
	public String convert(String s, String defaultStr) {
		if (s == null || "".equals(s.trim())) return defaultStr;
		if (trans.containsKey(s)) {
			try {
				String ret =  trans.get(s);
				if (ret != null && ! "".equals(ret)) {
					return ret;
				}

			} catch (Exception e) {
			}
		}
		if (HIGHLIGHT_TEXT) {
			return "#" + defaultStr;
		}
		return defaultStr;
	}
}
