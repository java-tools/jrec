package net.sf.RecordEditor.utils.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.JRecord.Common.Conversion;

public class PoUtil {
	private static final int NORMAL_MODE = 0;
	private static final int TRANS_MODE = 1;

	public static BufferedReader getPoReader(File f) throws IOException {

		BufferedReader r;
		PoHeader header = parsePOHeader(f);

		if (header.font.equals("")) {
			 r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} else {
			try {
				r = new BufferedReader(new InputStreamReader(new FileInputStream(f), header.font));
			} catch (Exception e) {
				r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			}
		}

		return r;
	}


	public static PoHeader parsePOHeader(File f) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String l;
		String lookup = null;
		StringBuilder b = null;
		int mode = NORMAL_MODE;
		String font = "";

		try {
			while ((l = r.readLine()) != null) {
				if (! "".equals(l.trim())) {
					break;
				}
			}
			do {
				if (l == null || "".equals(l.trim())) {
					break;
				} else if (mode == TRANS_MODE) {
					b.append(strip(l));
				} else {
					mode = NORMAL_MODE;
					if (l.toLowerCase().startsWith("msgid ")) {
						lookup = strip(l.substring(6));
					} else if (l.toLowerCase().startsWith("msgstr ")) {
						b = new StringBuilder(strip(l.substring(7)));
						mode = TRANS_MODE;
					}
				}
			} while ((l = r.readLine()) != null);
		} finally {
			r.close();
		}

		if ((lookup == null || "".equals(lookup)) && b != null) {
			String s= b.toString().toLowerCase();
			int pos = s.indexOf("charset=");
			if (pos >= 0) {
				int pos1, pos2, pos3;
				s= s.substring(pos+8);
				pos1 = s.indexOf(' ');
				pos2 = s.indexOf("\\n");
				pos3 = s.indexOf(';');
				pos = pos1;
				if (pos < 0 || pos2>=0 && pos2 < pos) {
					pos = pos2;
				}
				if (pos < 0 || pos3>=0 && pos3 < pos) {
					pos = pos3;
				}

				if (pos > 0) {
					font = s.substring(0, pos);
				}
			}
		}


		return new PoHeader(font);
	}


	protected static String strip(String s) {
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

}
