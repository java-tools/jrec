package net.sf.RecordEditor.re.util.csv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;
import net.sf.RecordEditor.utils.common.Common;

/**
 *
 * This Class Evaluates a CSV file and tries to decide on the
 * fields type and wether there the column names are on the first
 * line or not
 * @author Bruce Martin
 *
 */
public class CsvAnalyser {

	public static final int COLUMN_NAMES_YES   = 1;
	public static final int COLUMN_NAMES_NO    = 2;
	public static final int COLUMN_NAMES_MAYBE = 3;
	
	private static char[] SPECIAL_CHARS = {'~', '@', '#', '$', '%', '^', '*', };

//	public static final HashSet<String> STANDARD_CHARS = new HashSet<String>();
//
//	static {
//		String s = Common.STANDARD_CHARS;
//
//		for (int i = 0; i < s.length(); i++) {
//			STANDARD_CHARS.add(s.substring(i, i+1));
//		}
//
//		for (int i = 0 ; i < Common.FIELD_SEPARATOR_TEXT_LIST.length; i++) {
//			STANDARD_CHARS.add(Common.FIELD_SEPARATOR_LIST1_VALUES[i]);
//		}
//		STANDARD_CHARS.add("\n");
//	}

	private int seperatorIdx = 0;
	private int quoteIdx = 0;
	private int colNamesOnFirstLine = COLUMN_NAMES_MAYBE;
	private int fieldNameLineNo = 1;
	private int numberOfColumns = 0;
	private int[] colTypes, textTypes = null;
	private Character specialQuote = null;

	private String font;

	private ArrayList<ArrayList<String>> listOfLines = null;
	//public boolean validChars = true;
	private double ratio;

	private boolean embeddedCr = false;

	public CsvAnalyser(byte[][] lines, int numberOfLines, String fontname, boolean embeddedCr) {
		font = fontname;
		this.embeddedCr = embeddedCr;

		if (lines != null) {
			int noLines = lines.length;

			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}

			byte sep = getSeperatorQuote(lines, noLines);

			listOfLines = getList(lines, noLines, sep);

			checkForColNames(listOfLines);
			//findColTypes(listOfLines);
		}
	}

	public CsvAnalyser(byte[][] lines, int numberOfLines, String fontname, byte sep, boolean embeddedCr) {
		font = fontname;
		this.embeddedCr = embeddedCr;

		if (lines != null) {
			int noLines = lines.length;

			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}

			listOfLines = getList(lines, noLines, sep);

			checkForColNames(listOfLines);
		}
	}

	public CsvAnalyser(String[] lines, int numberOfLines, String fontname, boolean embeddedCr) {
		font = fontname;
		this.embeddedCr = embeddedCr;

		if (lines != null && lines.length > 0 && lines[0] != null) {
			int noLines = lines.length;
			String s = lines[0];

			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}

			ratio = BasicCharsetChecker.getValidCharsRatio(s);

			char sep = getSeperatorString(lines, noLines);
			listOfLines = getList(lines, noLines, sep);

			checkForColNames(listOfLines);
		}
	}

	public CsvAnalyser(String[] lines, int numberOfLines, String fontname, char sep, boolean embeddedCr) {
		font = fontname;
		this.embeddedCr = embeddedCr;

		if (lines != null && lines.length > 0 && lines[0] != null) {
			int noLines = lines.length;
			String s = lines[0];

			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}

			ratio = BasicCharsetChecker.getValidCharsRatio(s);

			listOfLines = getList(lines, noLines, sep);

			checkForColNames(listOfLines);
		}
	}

	private ArrayList<ArrayList<String>> getList(byte[][] lines, int noLines, byte sep) {
		ArrayList<ArrayList<String>> listOfLines = new ArrayList<ArrayList<String>>(noLines);
		ArrayList<String> line;
		int st;

		for (int i = 0; i < noLines; i++) {
			st = 0;
			line = new ArrayList<String>();

			if (lines[i] != null) {
				for (int j = 0; j < lines[i].length; j++) {
					if (lines[i][j] == sep) {
						line.add(getField(lines[i], st, j));
						st = j+1;
					}
				}
				line.add(getField(lines[i], st, lines[i].length));
			}

			listOfLines.add(line);
		}
		return listOfLines;
	}


	private String getField(byte[] line, int st, int en) {
		String ret = "";
		if (en > st) {
			try {
				ret = Conversion.getString(line, st, en, font);
			} catch (Exception e) {
			}
		}

		return ret;
	}

	private ArrayList<ArrayList<String>> getList(String[] lines, int noLines, char sep) {
		ArrayList<ArrayList<String>> listOfLines = new ArrayList<ArrayList<String>>(noLines);
		ArrayList<String> line;
		int st;

		for (int i = 0; i < noLines; i++) {
			st = 0;
			line = new ArrayList<String>();

			if (lines[i] != null) {
				for (int j = 0; j < lines[i].length(); j++) {
					if (lines[i].charAt(j) == sep) {
						line.add(getField(lines[i], st, j));
						st = j+1;
					}
				}
				line.add(getField(lines[i], st, lines[i].length()));
			}

			listOfLines.add(line);
		}
		return listOfLines;
	}

	private String getField(String line, int st, int en) {
		String ret = "";
		if (en > st) {
			try {
				ret = line.substring(st, en);
			} catch (Exception e) {
			}
		}

		return ret;
	}

	private char getSeperatorString(String[] lines, int numberOfLines) {

		int i,j, k;
		String s;
		int[] count = new int[Common.FIELD_SEPARATOR_TEXT_LIST.length];
		char[] sepChars = new char[count.length];
		int spaceIdx = -1;
		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}

		for (i = 0; i < count.length; i++) {
			count[i] = 0;
			sepChars[i] = Common.FIELD_SEPARATOR_LIST1_VALUES[i].charAt(0);
			if (sepChars[i] == ' ') {
				spaceIdx = i;
			}
		}

//		for (i = Math.max(0, numberOfLines - 45); i < numberOfLines; i++) {
		for (i = 0; i < numberOfLines; i++) {
			if (lines[i] != null) {
				s = lines[i];
				if (s != null) {
					for (j = 0; j < s.length(); j++) {
						for (k = 0; k < sepChars.length; k++) {
							if (sepChars[k] == s.charAt(j)) {
								count[k] += 1;
								break;
							}
						}
					}
				}
			}
		}

		seperatorIdx = getSeperatorIdx(count, numberOfLines, spaceIdx);


		int st = 2;
		char[] quoteChars = new char[Common.QUOTE_LIST.length - st];
		int[] quoteCount = new int[Common.QUOTE_LIST.length - st];
		char last;
		char sepChar = sepChars[seperatorIdx];
		char firstChar;
		TIntObjectHashMap<Integer> specialCount = new TIntObjectHashMap<Integer>(SPECIAL_CHARS.length * 2);
		for (char c : SPECIAL_CHARS) {
			specialCount.put(c, 0);
		}

		for (i = st; i < Common.QUOTE_LIST.length; i++) {
			quoteCount[i - st] = 0;
			quoteChars[i - st] = Common.QUOTE_LIST[i].charAt(0);
		}

		for (i = 0; i < numberOfLines; i++) {
			if (lines[i] != null && lines[i].length() > 0) {
				last = 0;
//				System.out.println();
				firstChar = lines[i].charAt(0);
				for (j = 0; j < lines[i].length() - 1; j++) {
					if (j == 0 || j == lines[i].length() - 2 || lines[i].charAt(j) == sepChar) {
						for (k = 0; k < quoteChars.length; k++) {
							if (last == quoteChars[k]) {
								quoteCount[k] += 1;
							}
							
//							System.out.print("\t*" + lines[i].charAt(j+1));
							if (lines[i].charAt(j+1) == quoteChars[k]) {
								quoteCount[k] += 1;
							}
							
							if (firstChar == last) {
								if (specialCount.containsKey(firstChar)) {
									specialCount.put(firstChar, specialCount.get(firstChar) + 1);
								}
							}
							firstChar = lines[i].charAt(j+1);
						}
					}

					last = lines[i].charAt(j);
//					System.out.print(last);
				}
				
				if (firstChar == lines[i].charAt(lines[i].length() - 1)) {
					if (specialCount.containsKey(firstChar)) {
						specialCount.put(firstChar, specialCount.get(firstChar) + 1);
					}
				}

			}
		}

		int quoteIdxMax = getMax(quoteCount);

		specialQuote = null;
		if (quoteCount[quoteIdxMax] > 5
		|| (embeddedCr && quoteCount[quoteIdxMax] > 1)) {
			quoteIdx = quoteIdxMax + st;
		} else {
			setSpecialQuote(lines.length * 2, specialCount);
		}

		return sepChars[seperatorIdx];
	}

	private void setSpecialQuote(int compare, TIntObjectHashMap<Integer> specialCount) {
		char cc = 0;
		int max = -1;
		int v;
		for (char c : SPECIAL_CHARS) {
			v =  specialCount.get(c);
			if (v > compare && v > max) {
				cc = c;
				max = v;
			}
		}
		
		if (max > 0) {
			specialQuote = cc;
		}
	
	}

	private byte getSeperatorQuote(byte[][] lines, int numberOfLines) {
		int i,j, k;

//		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES.clone();
		byte[] sepBytes = getSepBytes(font);
		byte sepByte;
		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES;

		int st = 2;
		byte[][] quoteBytes = new byte[Common.QUOTE_LIST.length - st][];
		int[] quoteCount = new int[Common.QUOTE_LIST.length - st];

		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}

		seperatorIdx = getSeperatorIndex(lines, numberOfLines, font, sepBytes);
		sepByte = sepBytes[seperatorIdx];
		if (sepByte == 0) {
			try {
				sepByte = Conversion.getBytes(sep[seperatorIdx], "")[0];
			} catch (Exception e) {
			}
		}
		
		byte firstByte;
		char[] byteMap = new char[256];
		TIntObjectHashMap<Integer> specialCount = new TIntObjectHashMap<Integer>(SPECIAL_CHARS.length * 2);
		Arrays.fill(byteMap, (char) 0);
		byte[] b = Conversion.getBytes(new String(SPECIAL_CHARS), font);
		
		
		for (j = 0; j < SPECIAL_CHARS.length; j++) {
			char c = SPECIAL_CHARS[j];
			specialCount.put(c, 0);
			byteMap[128 + b[j]] = SPECIAL_CHARS[j];
		}


		for (i = st; i < Common.QUOTE_LIST.length; i++) {
			quoteCount[i - st] = 0;
			quoteBytes[i - st] = Conversion.getBytes(Common.QUOTE_LIST[i], font);
		}

//		for (i = Math.max(0, numberOfLines - 15); i < numberOfLines; i++) {
		for (i = 0; i < numberOfLines; i++) {
			if (lines[i] != null && lines[i].length > 0) {
				firstByte = lines[i][0];
				for (j = 0; j < lines[i].length - 1; j++) {
					if (j == 0 || lines[i][j] == sepByte) {
						for (k = 0; k < quoteBytes.length; k++) {
							if (equals(lines[i], j - quoteBytes[k].length, quoteBytes[k])) {
								quoteCount[k] += 1;
							}
							if (equals(lines[i], j + 1, quoteBytes[k])) {
								quoteCount[k] += 1;
							}
						}
							
						char c = byteMap[128 + firstByte];
						if (j > 0 && firstByte == lines[i][j-1] && specialCount.contains(c)) {
							specialCount.put(c, specialCount.get(c) + 1);
						}
						firstByte =  lines[i][j+1];
					}
				}
				char c = byteMap[128 + firstByte];
				if (firstByte == lines[i][j-1] && specialCount.contains(c)) {
					specialCount.put(c, specialCount.get(c) + 1);
				}
			}
		}

		int quoteIdxMax = getMax(quoteCount);

		if (quoteCount[quoteIdxMax] > 5
		|| (embeddedCr && quoteCount[quoteIdxMax] > 1)) {
			quoteIdx = quoteIdxMax + st;
		} else {
			setSpecialQuote(lines.length * 2, specialCount);
		}

		return sepBytes[seperatorIdx];
	}
	
	private boolean equals(byte[] line, int pos, byte[] cmp) {
		boolean ret = false;
		if (pos >= 0 && pos + cmp.length <= line.length) {
			ret = true;
			for (int i = 0; i < cmp.length && ret; i++) {
				ret = line[pos + i] == cmp[i];
			}
		}
		return ret;
	}

	public static String getSeperator(byte[][] lines, int numberOfLines, String font) {

		byte[] sepBytes = getSepBytes(font);
		return Common.FIELD_SEPARATOR_LIST1_VALUES[getSeperatorIndex(lines, numberOfLines, font, sepBytes)];
	}

	private static byte[] getSepBytes(String font) {
		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES;
		byte[] sepBytes = new byte[sep.length];
		for (int k = 0; k < sep.length; k++) {
			sepBytes[k] = 0;
			try {
				sepBytes[k] = Conversion.getCsvDelimBytes(sep[k], font)[0];
			} catch (Exception e) {
			}
		}
		return sepBytes;
	}

	public static int getSeperatorIndex(byte[][] lines, int numberOfLines, String font, byte[] sepBytes) {
		int i,j, k;
		String s;
		int[] count = new int[Common.FIELD_SEPARATOR_LIST1_VALUES.length];
		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES.clone();
		int spaceIdx = -1;
//		byte[] sepBytes = new byte[sep.length];
		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}
		//sep[0] = ",";
		//sep[1] = "\t";

		for (i = 0; i < count.length; i++) {
			count[i] = 0;
			if (" ".equals(sep[i])) {
				spaceIdx = i;
			}
		}


		for (i = Math.max(0, numberOfLines - 45); i < numberOfLines; i++) {
			if (lines[i] != null) {
				s = new String(lines[i]);
				for (j = 0; j < s.length(); j++) {
					for (k = 0; k < sep.length; k++) {
						if (sep[k].startsWith("x'")) {
							if (lines[i][j] == sepBytes[k]) {
								count[k] += 1;
								break;
							}
						} else if (sep[k].equals(s.substring(j, j+1))) {
							count[k] += 1;
							break;
						}
					}
				}
			}
		}

		return  getSeperatorIdx(count, numberOfLines, spaceIdx);// getMax(count);
	}

	/**
	 * Check if column names exist in the file
	 * @param lines to check
	 */
	private void checkForColNames(ArrayList<ArrayList<String>> lines) {
		//int fieldsOnLine = 0;
		int i;


		numberOfColumns = 0;
		for (i = 1; i < lines.size(); i++) {
			numberOfColumns = Math.max(numberOfColumns, lines.get(i).size());
		}

		if (lines == null || lines.size() == 0) {
			return;
		}
		int m = Math.min(15, lines.size() - 1);
		while (fieldNameLineNo < m
		&& (   lines.get(fieldNameLineNo - 1) == null
			|| getSize(lines.get(fieldNameLineNo - 1)) < Math.min(5, numberOfColumns-3))) {
			fieldNameLineNo += 1;
		}

		ArrayList<String> line = lines.get(fieldNameLineNo - 1);
		for (i = 0; i < line.size(); i++) {
			try {
				Integer.parseInt(line.get(i));
				colNamesOnFirstLine = COLUMN_NAMES_NO;
				break;
			} catch (Exception e) {
			}
		}

		if (colNamesOnFirstLine != COLUMN_NAMES_NO) {
			int noNums, crCount, htmlCount;

			int limit = (lines.size() - fieldNameLineNo) / 3;
//			System.out.println();
//			System.out.println("Limit: " + limit);

			colTypes = new int[numberOfColumns];
			for (int j = 0; j < numberOfColumns; j++) {
				noNums = 0;
				crCount = 0;
				htmlCount = 0;
				colTypes[j] = Type.ftChar;
				for (i = fieldNameLineNo; i < lines.size(); i++) {
					line = lines.get(i);

					if (j < line.size()) {
						String fieldValue = line.get(j);
						try {
							new BigDecimal(fieldValue);
							noNums += 1;
						} catch (Exception e) {
						}
						if (fieldValue.indexOf('\n') >= 0 || fieldValue.indexOf('\r') >= 0) {
							crCount += 1;
						}
						if (Conversion.isHtml(fieldValue)) {
							htmlCount += 1;
						}
					}
				}

				//System.out.print("\t" + noNums);
				if (noNums > 3 && noNums > limit) {
					colNamesOnFirstLine = COLUMN_NAMES_YES;
					colTypes[j] = Type.ftNumAnyDecimal;
				} else if (htmlCount > limit) {
					colTypes[j] = Type.ftHtmlField;
				} else if (crCount > 0) {
					colTypes[j] = Type.ftMultiLineEdit;
				}
			}

			if (colTypes != null) {
				for (int k = 0; k < colTypes.length && textTypes == null; k++) {
					switch (colTypes[k]) {
					case Type.ftMultiLineEdit:
					case Type.ftHtmlField:
						textTypes = new int[colTypes.length];
						TypeManager typeManager = TypeManager.getInstance();

						for (int j = 0; j < textTypes.length; j++) {
							textTypes[j] = colTypes[j];
							if (typeManager.getType(textTypes[j]).isNumeric()) {
								textTypes[j] = Type.ftChar;
							}
						}

					}
				}
			}

			if (lines.size() <= fieldNameLineNo + 4) {
				colTypes = null;
			}
		}

		if (quoteIdx == 0) {
			int j, k, l;
			for (i = 0; i < lines.size(); i++) {
				line = lines.get(i);
				for (j = 0; j < line.size() - 1; j++) {
					for (k = 2; k < Common.QUOTE_LIST.length; k++) {
						if ( line.get(j).startsWith(Common.QUOTE_LIST[k])
						&& ! line.get(j).endsWith(Common.QUOTE_LIST[k])) {
							for (l = j+1; l < line.size(); l++) {
								if (line.get(l).endsWith(Common.QUOTE_LIST[k])) {
									quoteIdx = k;
									return;
								}
							}
						}
					}

				}
			}
		}
	}


	private int getSize(ArrayList<String> l) {
		int i = l.size() - 1;

		while (i >= 0 && (l.get(i) == null || "".equals(l.get(i)))) {
			i -= 1;
		}

		return i+1;
	}



	private static int getSeperatorIdx(int[] array, int lineCount, int spaceIdx) {

		int idxMax = 0;
		int idxMax2 = 0;
		int max = -1;
		int max2 = -1;
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max2 = max;
				idxMax2 = idxMax;
				max = array[i];
				idxMax = i;
			} else if (max2 < array[i]) {
				max2 = array[i];
				idxMax2 = i;
			}
		}
		
		if (idxMax == spaceIdx && array[idxMax2] >= lineCount * 2) {
			idxMax = idxMax2;
		}

		return idxMax;
	}


	private static int getMax(int[] array) {

		int idxMax = 0;
		int max = -1;
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
				idxMax = i;
			}
		}

		return idxMax;
	}

	/**
	 * @return the seperatorIdx
	 */
	public int getSeperatorIdx() {
		return seperatorIdx;
	}

	/**
	 * @return the quoteIdx
	 */
	public int getQuoteIdx() {
		return quoteIdx;
	}

	public final Character getSpecialQuote() {
		return specialQuote;
	}

	/**
	 * @return the colNamesOnFirstLine
	 */
	public int getColNamesOnFirstLine() {
		return colNamesOnFirstLine;
	}

	public int getFieldNameLineNo() {
		return fieldNameLineNo;
	}

	/**
	 * @return the numberOfColumns
	 */
	public int getNumberOfColumns() {
		return numberOfColumns;
	}


	public int[] getTypes() {
		int[] ret = null;
		if (colNamesOnFirstLine != COLUMN_NAMES_MAYBE && colTypes != null) {
			ret = colTypes.clone();
		}

		return ret;
	}


	public int[] getTextTypes() {

		if (textTypes != null) {
			return textTypes.clone();
		}

		return null;
	}

	/**
	 * @return the validChars
	 */
	public boolean isValidChars() {
		return ratio > 0.75;
	}

}
