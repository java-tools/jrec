package net.sf.RecordEditor.utils.lang;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import net.sf.JRecord.CsvParser.BasicParser;

/**
 * Represents the various Text items in the program that will need translating into
 * Foreign languages
 *
 * @author Bruce Martin
 *
 * License GPL (any)
 *
 */
public class TextItem {
	public static final int UNKOWN = 1;

	private static final String DELIM = "`";
	private static final String NULL_STR = "";
	public  String desc = "";

	public final String id, key, text;

	private HashSet<Integer> types = new HashSet<Integer>();
	private HashSet<String> pnls = new HashSet<String>();

	private int date;

	{
		try {
			date = Integer.parseInt((new SimpleDateFormat("yyyyMMdd")).format(new Date()));
		} catch (Exception e) {
		}
	}

	public TextItem(int type, String id, String text, String name) {
		super();

		this.id = id;
		this.text = text;

		if (text != null && text.toLowerCase().startsWith("c:")) {
			throw new RuntimeException("Field starting with file name");
		}
		addPnl(name);

		key = getKey();

		types.add(type);
	}


	public TextItem(String sepValue) {
		BasicParser t = new BasicParser(false);
		String[] flds = t.split(sepValue, DELIM, "", 0);
		String s = flds[0];
		String s1 = getItem(flds, 3);
		String dateStr = getItem(flds, 5);

		this.id = getItem(flds, 1);
		this.text = getItem(flds, 2);

		this.desc = getItem(flds, 4);
		this.key = getKey();

		if (! "".equals(dateStr) && ! "0".equals(dateStr)) {
			try {
				date = Integer.parseInt(dateStr);
			} catch (Exception e) {
			}
		}


		flds = t.split(s, ":", "", 0);
		for (String ss : flds) {
			try {
				types.add(Integer.parseInt(ss));
			} catch (Exception e) {
			}
		}
		flds = t.split(s1, ":", "", 0);
		for (String ss : flds) {
			addPnl(ss);
		}
	}

	private  String getItem(String[] t,  int i) {
		if (i < t.length) {
			return t[i];
		}

		return "";
	}

	private String getKey() {
		String k;
		if (id == null || "".equals(id)) {
			k = text;
		} else {
			k = id;
		}

		return k;
	}


	public final String asDelimString(int idx) {
		String s = "", s1= "", sep = "";
		for (int i : types) {
			s = s + sep + i;
			sep = ":";
		}
		sep = "";
		for (String s2 : pnls) {
			s1 = s1 + sep + s2;
			sep = ":";
		}

		return format(s) + DELIM + format(id)  + DELIM + format(text)  + DELIM + format(s1) + DELIM + desc
				+ DELIM + date + DELIM + idx;
	}

	private String format(String s) {
		if (s == null || "".equals(s)) {
			return NULL_STR;
		}

		return s;
	}

	public void addPnl(String pnl) {
		if (pnl != null && ! "".equals(pnl)) {
			pnls.add(pnl);
		}
	}


	public void add(TextItem t) {


		types.addAll(t.types);
		if (types.size() > 1 && types.contains(UNKOWN)) {
			types.remove(UNKOWN);
		}
		pnls.addAll(t.pnls);

		if (t.desc != null && ! "".equals(t.desc)
		&& (desc == null || desc.indexOf(t.desc) < 0)) {
			desc = (desc + " " + t.desc).trim();
		}

		if (date > t.date) {
			date = t.date;
		}
	}
}
