package xCommon;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.IO.LineIOProvider;

public class XFileData {
//	public static enum DTAR020Type {
//		EBCDIC,
//		ASCII
//	}

	private static final String[][] DTAR020_DATA = {
		{"62684671", "20", "40118", "685", "1", "69.99"},
		{"62684671", "20", "40118", "685", "-1", "-69.99"},
		{"64634429", "20", "40118", "957", "1", "3.99"},
		{"66624458", "20", "40118", "957", "1", "0.89"},
		{"60614487", "59", "40118", "878", "1", "5.95"},
		{"69694814", "166", "40118", "360", "1", "2.50"},
		{"69644164", "166", "40118", "193", "1", "21.59"},
		{"64644495", "166", "40118", "801", "1", "29.65"},
		{"67674299", "184", "40118", "905", "1", "4.99"},
		{"69664620", "184", "40118", "355", "-1", "-11.89"},
		{"60664048", "184", "40118", "60", "-1", "-4.80"},
		{"60664048", "184", "40118", "60", "-1", "-4.80"},
	};
	
	public static final String CASE_1_STR
			= "101\u4E00\u4E8C\u4E09\u3000\u3000\u3000zyxwvu    901\n"
			+ "102\u56DB\u4E94\uFF41\uFF42\uFF43\u3000tsrqpo    902";
	public static final String CASE_2_STR
			= "101\u4E00\u4E8C\u4E09abc   zyxwvu    901\n"
			+ "102\u56DB\u4E94\u516D\u4E03de  tsrqpo    902";
	
	private static final String[][] CASE_1_DATA = {
		{"101", "\u4E00\u4E8C\u4E09\u3000\u3000\u3000", "zyxwvu", "901"},
		{"102", "\u56DB\u4E94\uFF41\uFF42\uFF43\u3000", "tsrqpo", "902"},
	};
	private static final String[][] CASE_2_DATA = {
		{"101", "\u4E00\u4E8C\u4E09abc", "zyxwvu", "901"},
		{"102", "\u56DB\u4E94\u516D\u4E03de", "tsrqpo", "902"},

	};


	public static String[][] dtar020Data() {
		return deepClone(DTAR020_DATA);
	}

	public static String[][] case1Data() {
		return deepClone(CASE_1_DATA);
	}

	public static String[][] case2Data() {
		return deepClone(CASE_2_DATA);
	}
	

	private static String[][] deepClone(String[][] data) {
		String[][] retData = new String[data.length][];
		
		for (int i = 0; i < data.length; i++) {
			retData[i] = data[i].clone();
		}
		
		return retData;
	}
	
	public static String[] toDelim(String[][] lines, String fieldDelim) {
		String[] outLines = new String[lines.length];
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] == null || lines[i].length == 0) {
				outLines[i] = "";
			} else {
				StringBuilder b = new StringBuilder(lines[i][0]);
				for (int j = 1; j < lines[i].length; j++ ) {
					b.append(fieldDelim).append(lines[i][j]);
				}
				outLines[i] = b.toString();
			}
		}
		return outLines;
	}
	
	public static byte[][] toBytes(String[] lines, String font) {
		byte[][] b = new byte[lines.length][];
		for (int i = 0; i < lines.length; i++) {
			b[i] = Conversion.getBytes(lines[i], font);
		}
		return b;
	}
	public static String[][] filterAddQuote(String[][] lines, int[] cols, char quote) {
		String[][] ret = new String[lines.length][];
		
		for (int i = 0; i < lines.length; i++) {
			ret[i] = lines[i].clone();
			for (int col : cols) {
				ret[i][col] = quote + ret[i][col] + quote;
			}
		}
		
		return ret;
	}
	

	public static String[][] filterOnCols(String[][] lines, int[] cols) {
		String[][] ret = new String[lines.length][];
		
		for (int i = 0; i < lines.length; i++) {
			ret[i] = new String[cols.length];
			for (int colNum = 0; colNum < cols.length; colNum++) {
				ret[i][colNum] 	=  lines[i][cols[colNum]];
			}
		}
		
		return ret;
	}

	public static String[][] filterOnRows(String[][] lines, int[] rows) {
		String[][] ret = new String[rows.length][];
		
		for (int i = 0; i < rows.length; i++) {
			ret[i] = lines[rows[i]].clone();
		}
		
		return ret;
	}

//	public static FileView toFileView(LayoutDetail schema) {
//		return toFileView(schema, DTAR020_DATA);
//	}


	public static FileView toFileView(LayoutDetail schema, String[][] data) {
		LineIOProvider ioProvider = LineIOProvider.getInstance();
		FileView view = new FileView(schema, ioProvider, false);
		@SuppressWarnings("rawtypes")
		LineProvider lineProvider = ioProvider.getLineProvider(schema);
		
		for (String[] lineFields : data) {
			@SuppressWarnings("unchecked")
			AbstractLine line = lineProvider.getLine(schema);
			
			for (int i = 0; i < lineFields.length; i++) {
				line.setField(0, i, lineFields[i]);
			}
			
			view.add(line);
		}
		
		return view;
	}
}
