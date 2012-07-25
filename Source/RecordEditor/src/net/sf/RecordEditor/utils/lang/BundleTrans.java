package net.sf.RecordEditor.utils.lang;

import java.util.ResourceBundle;


public class BundleTrans extends BasicTrans {
	private ResourceBundle rb;

	public BundleTrans(ResourceBundle rb) {
		this.rb = rb;

//		Enumeration<String> e = rb.getKeys();
//		int i = 0;
//
//		while (e.hasMoreElements()) {
//			System.out.print("\t\t" + e.nextElement());
//
//			if (i++ % 3 == 0) System.out.println();
//		}
	}


	/**
	 * @see net.sf.JRecord.Common.ITranslation#convert(java.lang.String, java.lang.String)
	 */
	@Override
	public String convert(String s, String defaultStr) {
		if (s == null || "".equals(s.trim())) return defaultStr;
		if (rb.containsKey(s)) {
			try {
				String ret =  rb.getString(s);
				if (ret != null && ! "".equals(ret)) {
					return ret;
				}
				//return s;
			} catch (Exception e) {
			}
		}
		if (HIGHLIGHT_TEXT) {
			return "#" + defaultStr;
		}
		return defaultStr;
	}
}
