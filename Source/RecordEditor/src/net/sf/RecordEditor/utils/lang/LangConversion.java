package net.sf.RecordEditor.utils.lang;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.JRecord.Common.BasicTranslation;
import net.sf.JRecord.Common.ITranslation;
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
		"Combobox_Entry", "Field_Hint", "External", "", "Error"
	};
	public static final int ST_UNKOWN = TextItem.UNKOWN;
	public static final int ST_MESSAGE = ITranslation.ST_MESSAGE;
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
	public static final int ST_ERROR    = ITranslation.ST_ERROR;



	public static ITranslation conv = getConv();




	public static void logMsg(String t) {
		BasicTrans.put(new TextItem(ST_MESSAGE, "", t, ""));
	}

	public static void logMsg(String id, String t) {
		BasicTrans.put(new TextItem(ST_MESSAGE, id, t, ""));
	}


	public static String convert(String s) {

		return convert(ST_UNKOWN, s);
	}


	public static String convert(int type, String s, String param) {
		return conv.convert(type, s, param);
	}


	public static String convertMsg(int type, String s, Object... params) {
		return conv.convertMsg(type, s, params);
	}


	public static String convert(int type, String s) {

		return conv.convert(type, s);
	}

	public static String[] convertColHeading(String name, String[] flds) {
		return convertArray(ST_COLUMN_HEADING, "Tbl- " + name, flds);
	}

	public static String convertComboItms(String name, String fld) {
		return convertDesc(ST_COMBO, fld,  "Combo- " + name);
	}

	public static String[] convertComboItms(String name, String[] flds) {
		return convertArray(ST_COMBO, "Combo- " + name, flds);
	}

	public static String[] convertArray(int type, String name, String[] flds) {
		TextItem ti;

		for (int i = 0; i < flds.length; i++) {
			ti = new TextItem(type, "", flds[i], "");
			ti.desc = name + " " + i;
			BasicTrans.put(ti);
			flds[i] = conv.convert(flds[i]);
		}

		return flds;
	}


	public static String convert(String s, String param1) {
		return conv.convert(ST_MESSAGE, s, param1);
	}

	public static String convertFld(String name, String s) {
		BasicTrans.put(new TextItem(ST_FIELD, "", s, name));
		return conv.convert(s);
	}

	public static String convertId(int type, String id, String s) {
		BasicTrans.put(new TextItem(type, id, s, ""));
		return conv.convert(id, s);
	}

	public static String convertId(int type, String id, String s, Object... params) {
		//put(new TextItem(type, id, s, ""));
		return MessageFormat.format(convertId(type, id, s), params);
	}

	public static String convertDesc(int type, String s, String desc) {
		TextItem t = new TextItem(type, "", s, "");
		t.desc = desc;
		BasicTrans.put(t);
		return conv.convert(s);
	}

	public static String convertMsg(String msg, Object... params) {
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



	public static void setConversion() {
		conv = getConv();
	}

	public static ITranslation getConv() {
		ITranslation ret = getConversion();

		BasicTranslation.setTrans(ret);
		return ret;
	}

	private static ITranslation getConversion() {
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

