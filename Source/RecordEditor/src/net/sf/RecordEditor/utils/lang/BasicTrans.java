package net.sf.RecordEditor.utils.lang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.JRecord.ByteIO.VbByteReader;
import net.sf.JRecord.ByteIO.VbByteWriter;
import net.sf.JRecord.Common.ITranslation;
import net.sf.RecordEditor.utils.params.Parameters;

public abstract class BasicTrans implements ITranslation {

	public static final int FLUSH_PNL = 1;
	public static final int FLUSH_PROGRAM = 2;

	private static final String txtItmFile = Parameters.getPropertiesDirectoryWithFinalSlash() + "TextItems.bin";
	private static HashMap<String, TextItem> txtItms = null;

	private static boolean  createLangFile =
						"y".equalsIgnoreCase(
									Parameters.getString(Parameters.LOG_TEXT_FIELDS)),
						okToRun = true,
						changed = false;

	public static final boolean HIGHLIGHT_TEXT =
			"y".equalsIgnoreCase(
						Parameters.getString(Parameters.HIGHLIGHT_MISSING_TRANSLATIONS));


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
				//System.out.println(" ~ " + txtItms.size());
				r.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (txtItms.size() == 0) {
				read("RecordEditorMessages.txt", ST_MESSAGE);
				read("RecordEditorErrors.txt", ST_ERROR);
			}
		}
	}

	public static void read(String filename, int type) {
		try {
			String s, s1, s2;
			TextItem ti;
			StringTokenizer tok;
			BufferedReader br = new BufferedReader(new FileReader(
					Parameters.getPropertiesDirectoryWithFinalSlash() + filename));

			while ((s = br.readLine()) != null) {
				if (! "".equals(s.trim())) {
					s2 = "";
					tok = new StringTokenizer(s, "\t");
					s1 = tok.nextToken();
					if (tok.hasMoreTokens()) {
						s2 = tok.nextToken();
					}
					ti = new TextItem(type, "", s1, "");
					ti.desc = s2;
					put(ti);
				}
			}
			//System.out.println(" ~ " + txtItms.size());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void put(TextItem t) {

		if ((! createLangFile) ||t.key == null || "".equals(t.key.trim())) {

		} else if (txtItms.containsKey(t.key)) {
			txtItms.get(t.key).add(t);
			changed = true;
		} else {
			txtItms.put(t.key, t);
			changed = true;
		}
	}



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

							//System.out.print("Flush: ");
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


	@Override
	public final String convert(int type, String s, String param) {
		return MessageFormat.format(convert(type, s), param);
	}

	@Override
	public final String convert(int type, String s, Object[] params) {
		return MessageFormat.format(convert(type, s), params);
	}

	@Override
	public final String convert(int type, String s) {
		if (s == null || "".equals(s)) return s;

		put(new TextItem(type, "", s, ""));
		return convert(s);
	}


	/**
	 * @see net.sf.JRecord.Common.ITranslation#convert(java.lang.String)
	 */
	@Override
	public final String convert(String s) {
		return convert(s, s);
	}

}
