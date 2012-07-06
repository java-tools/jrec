package net.sf.RecordEditor.utils.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.JRecord.ByteIO.VbByteReader;
import net.sf.JRecord.ByteIO.VbByteWriter;
import net.sf.RecordEditor.utils.params.Parameters;

/**
 * Putpose: To convert a string from English to the Users Language
 * @author Bruce Martin
 *
 */
public class LangConversion {

	public static final String[] MSG_NAMES = {
		"", "Unknown", "Message", "Action", "Menu", "Column_Heading",
		"Frame_Heading", "Field_Prompt", "Button", "Tab_Heading",
		"Combobox_Entry", "Field_Hint", "External"
	};
	public static final int ST_UNKOWN = TextItem.UNKOWN;
	public static final int ST_MESSAGE = 2;
	public static final int ST_ACTION = 3;
	public static final int ST_MENU = 4;
	public static final int ST_COLUMN_HEADING = 5;
	public static final int ST_FRAME_HEADING = 6;
	public static final int ST_FIELD = 7;
	public static final int ST_BUTTON = 8;
	public static final int ST_TAB = 9;
	public static final int ST_COMBO = 10;
	public static final int ST_FIELD_HINT = 11;
	public static final int ST_EXTERNAL = 12;

	public static final int FLUSH_PNL = 1;
	public static final int FLUSH_PROGRAM = 2;

	private static final String txtItmFile = Parameters.getPropertiesDirectoryWithFinalSlash() + "TextItems.bin";
	private static HashMap<String, TextItem> txtItms = null;

	public static IStringTrans conv = getConv();

	private static boolean  createLangFile =
						"y".equalsIgnoreCase(
									Parameters.getString(Parameters.LOG_TEXT_FIELDS)),
						okToRun = true,
						changed = false;

	static {

		if (createLangFile) {
			VbByteReader r = new VbByteReader(false, false);
			byte[] b;
			txtItms = new HashMap<String, TextItem>(2000);


			try {
				r.open(txtItmFile);

				while ((b = r.read()) != null) {
					put(new TextItem(new String(b)));
				}
				System.out.println(" ~ " + txtItms.size());
				r.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (txtItms.size() == 0) {
				try {
					String s, s1, s2;
					TextItem ti;
					StringTokenizer tok;
					BufferedReader br = new BufferedReader(new FileReader(
							Parameters.getPropertiesDirectoryWithFinalSlash() +"RecordEditorMessages.txt"));

					while ((s = br.readLine()) != null) {
						if (! "".equals(s.trim())) {
							s2 = "";
							tok = new StringTokenizer(s, "\t");
							s1 = tok.nextToken();
							if (tok.hasMoreTokens()) {
								s2 = tok.nextToken();
							}
							ti = new TextItem(ST_MESSAGE, "", s1, "");
							ti.desc = s2;
							put(ti);
						}
					}
					System.out.println(" ~ " + txtItms.size());
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void put(TextItem t) {

		if ((! createLangFile) ||t.key == null || "".equals(t.key.trim())) {

		} else if (txtItms.containsKey(t.key)) {
			txtItms.get(t.key).add(t);
			changed = true;
		} else {
			txtItms.put(t.key, t);
			changed = true;
		}
	}

	public static String convert(String s) {

		return convert(ST_UNKOWN, s);
	}

	public static String convert(int type, String s) {
		if (s == null || "".equals(s)) return s;

		put(new TextItem(type, "", s, ""));
		return conv.convert(s);
	}

	public static String[] convertColHeading(String name, String[] flds) {
		return convert(ST_COLUMN_HEADING, "Tbl- " + name, flds);
	}

	public static String convertComboItms(String name, String fld) {
		return convertDesc(ST_COMBO, fld,  "Combo- " + name);
	}

	public static String[] convertComboItms(String name, String[] flds) {
		return convert(ST_COMBO, "Combo- " + name, flds);
	}

	public static String[] convert(int type, String name, String[] flds) {
		TextItem ti;

		for (int i = 0; i < flds.length; i++) {
			ti = new TextItem(type, "", flds[i], "");
			ti.desc = name + " " + i;
			put(ti);
			flds[i] = conv.convert(flds[i]);
		}

		return flds;
	}

	public static String convert(String s, String param1) {
		return MessageFormat.format(convert(ST_MESSAGE, s), param1);
	}


	public static String convertFld(String name, String s) {
		put(new TextItem(ST_FIELD, "", s, name));
		return conv.convert(s);
	}

	public static String convertId(int type, String id, String s) {
		put(new TextItem(type, id, s, ""));
		return conv.convert(id, s);
	}

	public static String convertId(int type, String id, String s, Object[] params) {
		put(new TextItem(type, id, s, ""));
		return MessageFormat.format(convertId(type, id, s), params);
	}

	public static String convertDesc(int type, String s, String desc) {
		TextItem t = new TextItem(type, "", s, "");
		t.desc = desc;
		put(t);
		return conv.convert(s);
	}

	public static String convert(String msg, Object[] params) {
		return MessageFormat.format(convert(ST_MESSAGE, msg), params);
	}

//	private static String trans(String msg, Object[] params) {
//		StringBuilder b = new StringBuilder(msg);
//		String s;
//
//		for (int i = 0; i < params.length; i++) {
//			s = "";
//			if (params[i] != null) {
//				s = params[i].toString();
//			}
//			Conversion.replace(b, "{" + i + "}", s);
//		}
//
//		return b.toString();
//	}

	public static void flush(int type) {

		if (createLangFile && changed && type >= FLUSH_PNL && (okToRun || type == FLUSH_PROGRAM)) {
			okToRun = false;
			changed = false;
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (txtItmFile) {
						int idx = 0;
						VbByteWriter w = new VbByteWriter(false);
						try {
							Set<String> keys = txtItms.keySet();
							w.open(txtItmFile);

							System.out.print("Flush: ");
							for (String key : keys) {
								w.write(txtItms.get(key).asDelimString(idx++).getBytes());
								//System.out.print('.');
							}
							//System.out.println();
							w.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							okToRun = true;
							try {
								w.close();
							} catch (Exception e) {
							}
						}
					}
				}
			}).start();
		}
	}

	public static void setConversion() {
		conv = getConv();
	}

	public static IStringTrans getConv() {
		String s = Parameters.getString(Parameters.CURRENT_LANGUAGE);
		String langDir = Parameters.expandVars(
							 Parameters.formatLangDir(
									Parameters.getString(Parameters.LANG_DIRECTORY)));
		File dirFile, classFile, poFile=null;

		if (s == null || "".equals(s)) {
		} else if (langDir == null || "".equals(langDir)) {
			System.out.println("Language Directory not specified: " + langDir);
		} else if ((! (dirFile = new File(langDir)).exists()) || (! dirFile.isDirectory())) {
			System.out.println("Language Directory not a Directory: " + langDir);
		} else {
			String[] files = {
					Parameters.LANG_FILE_PREFIX + s + ".po",
					//Parameters.LANG_FILE_PREFIX + s + ".PO",
					Parameters.LANG_FILE_PREFIX.substring(0, Parameters.LANG_FILE_PREFIX.length() - 1)
						+ "." + s + ".po",
			};
			if ((! langDir.endsWith("/")) && (! langDir.endsWith("\\"))) {
				langDir += File.separator;
			}
			classFile = new File(langDir + Parameters.LANG_FILE_PREFIX + s + ".class");


			for (String fn : files) {
				poFile = new File(langDir + fn);
				System.out.println("~~ " + langDir +  fn + " " + poFile.isFile());
				if (poFile.isFile()) {
					break;
				}
			}
			if (classFile.exists()
			&& ((!poFile.isFile()) || (classFile.lastModified() > poFile.lastModified()))) {
				try {
		    		URL[] urls = {dirFile.toURI().toURL()};

			   	 	ResourceBundle rb = ResourceBundle.getBundle(Parameters.LANG_FILE_PREFIX + s,
			   	 			Locale.getDefault(),
			   	 			new URLClassLoader(urls));

			   	 	if (rb != null) {
			   	 		System.out.println("Using resource bundle translation");
			   	 		return new BundleTrans(rb);
			   	 	}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (poFile.isFile()) {
				try {
					System.out.println("Using po translation");
					return new PoTrans(poFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Using No translation");
		return new StdTrans();
	}


	public static boolean isPoTrans() {
		return conv instanceof PoTrans;
	}
}

