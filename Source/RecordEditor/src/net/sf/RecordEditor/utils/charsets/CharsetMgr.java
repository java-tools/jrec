package net.sf.RecordEditor.utils.charsets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sf.JRecord.CsvParser.BasicCsvLineParserExtended;
import net.sf.JRecord.CsvParser.CsvDefinition;

/**
 * Class holds characterset details + characterset descriptions.
 * it used in {@link CharsetSelection} and {@link FontCombo}
 * 
 * @author Bruce Martin 
 *
 */
public class CharsetMgr {

	private static final List<CharsetDtls> charsets = new ArrayList<CharsetDtls>(80);
	private static Map<String, String> charsetDescriptionMap = new HashMap<String, String>(400);
	private static String[] commonCharsets;
	
	static {
		try {
			Map<String, String> charsetDtls = new TreeMap<String, String>();
			String key, desc, id;
			
			updateFromFile(charsetDtls, "Charsets1.txt");
			updateFromFile(charsetDtls, "Charsets2.txt");
			ArrayList<Charset> charsetList = new ArrayList<Charset>(Charset.availableCharsets().values());

			Set<String> commonCharsetsSet = new TreeSet<String>(
					Arrays.asList(
							"US-ASCII",
							"ISO-8859-1",
							"ISO-8859-19",
							"UTF-8",
							"UTF-16",
							"UTF-32",
							"windows-1252"
					));
			commonCharsetsSet.add(Charset.defaultCharset().displayName());
			commonCharsets = commonCharsetsSet.toArray(new String[commonCharsetsSet.size()]);
			
			for (String s : commonCharsetsSet) {
				key = s.toLowerCase();
				desc = "";
				if (charsetDtls.containsKey(key)) {
					desc = charsetDtls.get(key);
				}
				charsets.add(new CharsetDtls(s, desc, null));
			}
			 
			Collections.sort(charsetList, new CaseInsensitiveCharsetComparitor());
			for (Charset c : charsetList) {
				desc = "";
				id = c.displayName();
				key = id.toLowerCase();

				if (charsetDtls.containsKey(key)) {
					desc = charsetDtls.get(key);
				} else {
					Iterator<String> it = c.aliases().iterator();
					while (it.hasNext()) {
						key = it.next().toLowerCase();
						if (charsetDtls.containsKey(key)) {
							desc = charsetDtls.get(key);
							break;
						}
					}
				}
				if (desc.length() > 0) {
					charsetDescriptionMap.put(key, desc);
					Iterator<String> it = c.aliases().iterator();
					while (it.hasNext()) {
						charsetDescriptionMap.put(it.next(), desc);
					}
				}
				charsets.add(new CharsetDtls(id, desc, c));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the charsets
	 */
	public static final List<CharsetDtls> getCharsets() {
		return charsets;
	}

	/**
	 * @return the commonCharsets
	 */
	public static final String[] getCommonCharsets() {
		return commonCharsets;
	}

	public static String getDescription(String s) {
		String ret = "";
		if (s != null) {
			ret = charsetDescriptionMap.get(s.toLowerCase());
			if (ret == null) {
				ret = "";
			}
		}
		return ret;
	}
	private static void updateFromFile(Map<String, String> charsetDtls, String filename) {
		try {
			InputStream in = CharsetMgr.class.getResource(filename).openStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String s;
			String[] split;
			BasicCsvLineParserExtended p = new BasicCsvLineParserExtended(false);
			CsvDefinition csvDef = new CsvDefinition("\t", "");
			try {
				while ((s = r.readLine()) != null) {
					split = p.split(s, csvDef, 0);
					if (split.length > 2) {
						charsetDtls.put(split[0].toLowerCase(), split[2]);
					}
				}
			} finally {
				r.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private static class CaseInsensitiveCharsetComparitor implements Comparator<Charset> {
		
		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Charset o1, Charset o2) {
			return String.CASE_INSENSITIVE_ORDER.compare(o1.displayName(), o2.displayName());
		}
		
	}
}
