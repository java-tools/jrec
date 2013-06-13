package net.sf.RecordEditor.po;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.RecordEditor.edit.display.extension.FieldDef;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.po.def.PoLayoutMgr;
import net.sf.RecordEditor.po.def.PoLine;

/**
 * Line reader for GetText PO/POT files
 *
 * @author Bruce Martin
 *
 */
public class PoMessageLineReader extends AbstractLineReader<LayoutDetail> {

	private static final int BUFFER_SIZE = 4096 * 4;

	private static final FieldDef[] ALL_FIELDS = PoField.getAllfields();

	private BufferedReader r;


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#open(java.io.InputStream, net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void open(InputStream inputStream, LayoutDetail pLayout)
			throws IOException, RecordException {
		throw new RecordRunTimeException("Not Supported");
	}

	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.AbstractLineReader#open(java.io.InputStream, java.lang.String, net.sf.JRecord.Details.AbstractLayoutDetails)
	 */
	@Override
	public void open(InputStream inputStream, String fileName,
			LayoutDetail pLayout) throws IOException, RecordException {
		r = new BufferedReader(new FileReader(fileName), BUFFER_SIZE);
		pLayout = PoLayoutMgr.getPoLayout();
		super.setLayout(pLayout);


		String font = "";
		PoLine l = read();
		if (l != null) {
			Object o = l.getField(0, PoField.msgstr.fieldIdx);
			Object o2 = l.getField(0, PoField.msgid.fieldIdx);

			r.close();

			if (o != null && o2 != null && "".equals(o2.toString())) {
				String s = o.toString().toLowerCase();
				int pos = s.indexOf("charset=");

				if (pos >= 0) {
					s = s.substring(pos+8);

					int	pos1 = s.indexOf(' ');
					int pos2 = s.indexOf("\\n");
					int pos3 = s.indexOf(';');
					int pos4 = s.indexOf('\n');
					pos = pos1;
					if (pos < 0 || pos2>=0 && pos2 < pos) {
						pos = pos2;
					}
					if (pos < 0 || pos3>=0 && pos3 < pos) {
						pos = pos3;
					}
					if (pos < 0 || pos4>=0 && pos4 < pos) {
						pos = pos4;
					}

					if (pos > 0) {
						font = s.substring(0, pos);
						pLayout.setFontName(font);
					}
				}

				pLayout.setExtraDetails(l);
				if (font.equals("")) {
					 r = new BufferedReader(new FileReader(fileName), BUFFER_SIZE);
				} else {
					 try {
						r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), font), BUFFER_SIZE);
					 } catch (Exception e) {
						r = new BufferedReader(new FileReader(fileName), BUFFER_SIZE);
					 }
				}

				read();

			} else {
				r = new BufferedReader(new FileReader(fileName), BUFFER_SIZE);
			}
		}
	}

	public final PoLine read() throws IOException {
		String l, inputLine;

		int fldIdx = -1;

		do {
			if ((l = r.readLine()) == null) {
				return null;
			}
		} while ("".equals(l.trim()));

		PoLine line = new PoLine(getLayout());
		do {
			String lc = l.toLowerCase();
			boolean found = false;

			if (lc.startsWith("#~ ")) {
				try {
					line.setField(0, PoField.obsolete.fieldIdx, "Y");
				} catch (Exception e) {

				}
				lc = lc.substring(3);
				l = l.substring(3);
			}
			for (int i = 0; i < ALL_FIELDS.length; i++) {
				if (ALL_FIELDS[i].isMatch(lc)) {
					Object o = line.getRawField(0, ALL_FIELDS[i].fieldIdx);
					if (l.length() >= ALL_FIELDS[i].name.length()) {
						inputLine = l.substring(ALL_FIELDS[i].name.length());
					} else {
						inputLine = "";
					}

					if (ALL_FIELDS[i].repeating) {
						o = processRepeatingField(i, inputLine, o);
					} else {
						o = updateInputLine(i, inputLine, o);
					}

					updateField(line, ALL_FIELDS[i].fieldIdx, o);

					found = true;
					fldIdx = i;
					break;
				}

			}
			if (fldIdx >= 0 && ! found) {
				Object o = updateInputLine(fldIdx, l, line.getField(0, ALL_FIELDS[fldIdx].fieldIdx));
				updateField(line, ALL_FIELDS[fldIdx].fieldIdx, o);
			}
		} while ((l = r.readLine()) != null && (! "".equals(l.trim())));

		String flags = fix(line.getField(0, PoField.flags.fieldIdx));
		if (flags != null && flags.indexOf(PoField.FUZZY) >= 0) {
			try {
				line.setField(0, PoField.fuzzy.fieldIdx, "Y");
				if (PoField.FUZZY.equals(flags.trim())) {
					flags = "";
				} else if (flags.indexOf(", " + PoField.FUZZY) >= 0) {
					flags = Conversion.replace(new StringBuilder(flags), ", " + PoField.FUZZY, "").toString();
				} else if (flags.indexOf(PoField.FUZZY + ", ") >= 0) {
					flags = Conversion.replace(new StringBuilder(flags), PoField.FUZZY + ", ", "").toString();
				} else {
					flags = Conversion.replace(new StringBuilder(flags), PoField.FUZZY, "").toString();
				}
				line.setField(0, PoField.flags.fieldIdx, flags);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return line;
	}

	private String fix(Object o) {
		String s = "";
		if (o != null) {
			s = o.toString();
		}
		return s;
	}


	private List<String> processRepeatingField(int fldIdx, String inputTxt, Object currValue) {
		List<String> a;
		int pos1 = inputTxt.indexOf('[');
		int pos2 = inputTxt.indexOf(']');
		if (currValue != null &&  currValue instanceof List) {
			a = (List<String>) currValue;
		} else {
			a = new ArrayList<String>();
		}

		if (pos1 < 0 || pos2 < 2 || pos1 >= pos2) {
			a.add(stripQuotes(ALL_FIELDS[fldIdx], inputTxt));
		} else {
			String numStr = inputTxt.substring(pos1+1, pos2);
			inputTxt = stripQuotes(ALL_FIELDS[fldIdx], inputTxt.substring(pos2 + 1));
			try {
				int idx = Integer.parseInt(numStr);
				for (int j = a.size(); j <= idx; j++) {
					a.add(null);
				}
				a.set(idx, inputTxt);
			} catch (Exception e) {
				a.add(inputTxt);
			}
		}
		return a;
	}

	private Object updateInputLine(int fldIdx, String inputTxt, Object currValue) {
		inputTxt = stripQuotes(ALL_FIELDS[fldIdx], inputTxt);
		if (currValue == null) {
			currValue = inputTxt;
		} else if (ALL_FIELDS[fldIdx].stripQuote) {
			currValue = currValue.toString() + inputTxt;
		} else {
			currValue = currValue.toString() + "\n" + inputTxt;
		}
		return currValue;
	}

	private void updateField(PoLine line, int fldNo, Object value) {
		try {
			line.setField(0, fldNo, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * @throws IOException
	 * @see java.io.BufferedReader#close()
	 */
	public void close() throws IOException {
		r.close();
	}

	public static String stripQuotes(FieldDef field, String s) {
		if (field.stripQuote) {
			String tmp = s.trim();
			if (tmp.startsWith("\"")) {
				if (tmp.endsWith("\"")) {
					if (tmp.length() == 2) {
						tmp = "";
					} else {
						tmp =  tmp.substring(1, tmp.length() - 1);
					}
				}
				s = tmp;
			}

			StringBuilder b = new StringBuilder(s);

			s = replace(b).toString();
		}

		return s;
	}

	   /**
     * Replaces on string with another in a String bugffer
     *
     * @param in String buffer to be updated

     */
    public static final StringBuilder replace(StringBuilder in) {
        int start;
        String from = "\\";
        String to;
        int fromLen = from.length() + 1;

        start = in.indexOf(from, 0);
        while (start >= 0 && start+1 < in.length()) {
        	switch (in.charAt(start+1)) {
        	case 'n':	to = "\n";		break;
        	case 'r':	to = "\r";		break;
        	case 't':	to = "\t";		break;
        	case '\"':	to = "\"";		break;
        	case '\\':	to = "\\";		break;
        	default: to = null;
        	}

        	if (to != null) {
        		in.replace(start, start + fromLen, to);
        		//start += 1;
        	}
            start = in.indexOf(from, start + 1);
       }

        return in;
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
