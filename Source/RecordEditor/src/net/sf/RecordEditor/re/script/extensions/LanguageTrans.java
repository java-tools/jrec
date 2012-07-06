package net.sf.RecordEditor.re.script.extensions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Purpose Read Google Translations file back into an array list
 *
 * @author Bruce Martin
 *
 * License GPL
 *
 */
public class LanguageTrans {

	private ArrayList<String> list = new ArrayList<String>(1500);
	private static String BLANK = "";

	private static HashMap<String, LanguageTrans> translations = new HashMap<String, LanguageTrans>();


	public static LanguageTrans getTrans(String fname) {
		if (translations.containsKey(fname)) {
			return translations.get(fname);
		}

		LanguageTrans t = new LanguageTrans(fname);
		translations.put(fname, t);

		return t;
	}


	private LanguageTrans(String fileName) {

		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
			String s, l = BLANK;
			boolean newTrans = true;
			int key = 0,  pos;


			for (int i = 0; i < 3000; i++) {
				list.add(BLANK);
			}
			while ((s = r.readLine()) != null) {
				if ("".equals(s.trim())) {

					try {
						list.set(key, l);
					} catch (Exception e) {
						e.printStackTrace();
					}
					newTrans = true;
				} else if (newTrans) {
					pos = Math.max(s.indexOf(":"), s.indexOf("："));
					if (pos > 0) {
						try {
							key = Integer.parseInt(s.substring(0, pos).trim());
							System.out.println("  >> " + key + " " + s);
							l = s.substring(pos + 2);
						} catch (Exception e) {
							e.printStackTrace();
						}
						newTrans = false;
					}
				} else {
					l = l + "\n" + s;
				}
			}

			list.set(key, l);

			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param index
	 * @return
	 * @see java.util.ArrayList#get(int)
	 */
	public String get(int index) {
		return list.get(index);
	}

	public static void clear() {
		translations.clear();
	}
}
