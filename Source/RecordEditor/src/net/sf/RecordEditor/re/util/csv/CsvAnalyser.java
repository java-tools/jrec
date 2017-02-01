package net.sf.RecordEditor.re.util.csv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.Types.TypeManager;
import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.common.CsvTextItem;

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
	private static final char[] STANDARD_FIELD_DELIMITERS = {',', ';', '\t', '|'};
	private static final char[] STANDARD_QUOTES = {'\'', '"'};


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

//	private int seperatorIdx = 0;
	private String  seperatorId = "<tab>";
//	private int quoteIdx = 0;
	private String quote;
	private int colNamesOnFirstLine = COLUMN_NAMES_MAYBE;
	private int fieldNameLineNo = 1;
	private int numberOfColumns = 0;
	private int[] colTypes, textTypes = null;
	private Character specialQuote = null;
	private boolean csvFile;

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
		
		//List<TextItems> csvDelimiterList = TextItems.DELIMITER.getCsvList(false, false);
		CharData delimiterCharDtls = toCharArray(CsvTextItem.DELIMITER.getCsvList(false, false), STANDARD_FIELD_DELIMITERS, font);
//		char[] sepChars = delimiterCharDtls.chars;
//		int[] count = new int[sepChars.length];



//		for (i = Math.max(0, numberOfLines - 45); i < numberOfLines; i++) {
		for (i = 0; i < numberOfLines; i++) {
			if (lines[i] != null) {
				s = lines[i];
				if (s != null) {
					for (j = 0; j < s.length(); j++) {
						delimiterCharDtls.updateCounts(s.charAt(j));
//						for (k = 0; k < delimiterCharDtls.length; k++) {
//							if (sepChars[k] == s.charAt(j)) {
//								count[k] += 1;
//								break;
//							}
//						}
					}
				}
			}
		}

		int seperatorIdx = getSeperatorIdx(delimiterCharDtls.count, numberOfLines, delimiterCharDtls.spaceIdx);
		seperatorId = delimiterCharDtls.items[seperatorIdx].value;

		CharData quoteDtls = toCharArray(CsvTextItem.QUOTE.getCsvList(false, false), STANDARD_QUOTES, font);
		//char[] quoteChars = new char[csvQuoteList.size()];
//		int[] quoteCount = new int[quoteDtls.length];
		char last;
		char sepChar = delimiterCharDtls.chars[seperatorIdx];
		char firstChar;
//		TIntObjectHashMap<Integer> specialCount = new TIntObjectHashMap<Integer>(SPECIAL_CHARS.length * 2);
//		for (char c : SPECIAL_CHARS) {
//			specialCount.put(c, 0);
//		}
		
		CharData specialDtls = new CharData(SPECIAL_CHARS, null, null);
		specialDtls.setLength(SPECIAL_CHARS.length);

//		
//		for (i = 0; i < quoteChars.length; i++) {
//			quoteCount[i] = 0;
//			try {
//				quoteChars[i] = Conversion.decodeChar(csvQuoteList.get(i).value, font)[0];
//			} catch (Exception e) {
//				quoteChars[i] = Short.MAX_VALUE;
//						
//			}
//		}

		for (i = 0; i < numberOfLines; i++) {
			if (lines[i] != null && lines[i].length() > 0) {
				last = 0;
//				System.out.println();
				firstChar = lines[i].charAt(0);
				for (j = 0; j < lines[i].length() - 1; j++) {
					char charAt = lines[i].charAt(j);
					if (j == 0 || j == lines[i].length() - 1) {
						quoteDtls.updateCounts(charAt);
						specialDtls.updateCounts(charAt);
					} else if (charAt == sepChar) {
						char ch = lines[i].charAt(j-1);
						quoteDtls.updateCounts(ch);
						specialDtls.updateCounts(ch);
						
						ch = lines[i].charAt(j+1);
						quoteDtls.updateCounts(ch);
						specialDtls.updateCounts(ch);
					}
//					if (charToCheck >= 0) {
//						for (k = 0; k < quoteDtls.length; k++) {
//							if (last == quoteDtls.chars[k]) {
//								quoteCount[k] += 1;
//							}
//
////							System.out.print("\t*" + lines[i].charAt(j+1));
//							if (lines[i].charAt(charToCheck) == quoteDtls.chars[k]) {
//								quoteCount[k] += 1;
//							}
//							
//							if (firstChar == last) {
//								if (specialCount.containsKey(firstChar)) {
//									specialCount.put(firstChar, specialCount.get(firstChar) + 1);
//								}
//							}
//							firstChar = lines[i].charAt(j+1);
//						}
//					}
//					last = charAt;

//					System.out.print(last);
				}
				
//				if (firstChar == lines[i].charAt(lines[i].length() - 1)) {
//					if (specialCount.containsKey(firstChar)) {
//						specialCount.put(firstChar, specialCount.get(firstChar) + 1);
//					}
//				}

			}
		}

		int quoteIdxMax = getMax(quoteDtls.count);

		specialQuote = null;
		if (quoteDtls.count[quoteIdxMax] > 5
		|| (embeddedCr && quoteDtls.count[quoteIdxMax] > 1)) {
			quote = quoteDtls.items[quoteIdxMax].value;
		} else {
			setSpecialQuote(lines.length * 2, specialDtls);
		}

		return sepChar; //sepChars[seperatorIdx];
	}
	
	protected static CharData toCharArray(List<CsvTextItem> items, char[] extra, String font) {

		CharData cd = new CharData(new char[items.size() + (extra == null ? 0 : extra.length)], null, null);
		char[] chars = new char[items.size() + (extra == null ? 0 : extra.length)];
		int j=0;
		for (CsvTextItem t : items) {
			try {
				char[] decodeChars = Conversion.decodeChar(t.value, font);
				if (decodeChars.length == 1) {
					if (decodeChars[0] == ' ') {
						cd.spaceIdx = j;
					}
					cd.chars[j] = decodeChars[0];
					cd.items[j++] = t;
				}
			} catch (Exception e) {
			}
		}
		if (extra != null) {
			int num = j;
			for (char c : extra) {
				boolean searching = true;
				
				for (int i = 0; i < num; i++) {
					if (cd.items[i].isText && chars[i] == c) {
						searching = false;
						break;
					}
				}
				if (searching) {
					chars[j] = c;
					String t = "\\t";
					if (c != '\t') {
						t = new String(new char[]{c});
					}
					cd.items[j++] = new CsvTextItem(t, t);

				}
			}
		}
		cd.setLength(j);
		return cd;
	}
	
	
	protected static CharData toByteArray(List<CsvTextItem> items, char[] extra, String font, boolean multiByte) {

		int num = items.size() + (extra == null ? 0 : extra.length);
		CharData cd = new CharData(null, new byte[num], new byte[num][]);
		int j=0;
		int extraCount = extra == null ? 0 : extra.length;
		
		extra = extra.clone();
		for (CsvTextItem t : items) {
			try {
				if (t.isText) {
					char[] decodeChar = Conversion.decodeChar(t.value, font);
					byte[] decodeBytes = Conversion.getBytes(new String(decodeChar), font);
					if (multiByte || decodeBytes.length == 1) {
						if (decodeChar[0] == ' ') {
							cd.spaceIdx = j;
						}

						cd.bytes[j] = decodeBytes;
						cd.singleBytes[j] = decodeBytes[0];
						cd.items[j++] = t;
						for (int i = 0; i < extraCount; i++) {
							if (extra[i] == decodeChar[0]) {
								extraCount -= 1;
								for (int k = i; k < extraCount; k++) {
									extra[k] = extra[k+1];
								}
								break;
							}
						}
					}
				} else {
					byte byteFromHexString = Conversion.getByteFromHexString(t.value);
					cd.singleBytes[j] = byteFromHexString;
					cd.bytes[j] = new byte[] { byteFromHexString };
					cd.items[j++] = t;
				}
			} catch (Exception e) {
			}
		}
		if (extra != null) {
			for (int i = 0; i < extraCount; i++) {
				char c = extra[i];
				cd.bytes[j] = Conversion.getBytes(new String(new char[]{c}), font);
				cd.singleBytes[j] = cd.bytes[j][0];
				String t = "\\t";
				if (c != '\t') {
					t = new String(new char[]{c});
				}
				cd.items[j++] = new CsvTextItem(t, t);
			}
		}
		cd.setLength(j);
		return cd;
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

	private void setSpecialQuote(int compare, CharData specialCount) {
		char cc = 0;
		int max = -1;
		int v;
		for (int i = 0; i < specialCount.getLength(); i++) {
			v =  specialCount.count[i];
			if (v > compare && v > max) {
				cc = specialCount.chars[i];
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
		//byte[] sepBytes = getSepBytes(font);
		byte sepByte;
		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES;

//		int st = 2;
		CharData quoteDtls = toByteArray(CsvTextItem.QUOTE.getCsvList(false, false), STANDARD_QUOTES, font, true);
		
//		byte[][] quoteBytes = new byte[charDtls.length][];
		int[] quoteCount = new int[quoteDtls.getLength()];

		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}
		
		CharData charDtls = toByteArray(CsvTextItem.DELIMITER.getCsvList(false, true), STANDARD_FIELD_DELIMITERS, font, false);
//		char[] delimiterCharArray = charDtls;
//		byte[] sepBytes = getBytes(csvDelimiterList, delimiterCharArray, font);

		int sepIdx = getSeperatorIndex2(lines, numberOfLines, font, charDtls);
		int seperatorIdx = Math.max(0, sepIdx);
		sepByte = charDtls.singleBytes[seperatorIdx];
		csvFile = sepIdx >= 0;
		
		if (csvFile) {
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
	
//	
//			for (i = 0; i < quoteDtls.length; i++) {
//				quoteCount[i] = 0;
//				quoteBytes[i - st] = Conversion.getBytes(Common.QUOTE_LIST[i], font);
//			}
	
	//		for (i = Math.max(0, numberOfLines - 15); i < numberOfLines; i++) {
			for (i = 0; i < numberOfLines; i++) {
				if (lines[i] != null && lines[i].length > 0) {
					firstByte = lines[i][0];
					for (j = 0; j < lines[i].length - 1; j++) {
						if (j == 0 || lines[i][j] == sepByte ) {
							for (k = 0; k < quoteDtls.getLength(); k++) {
								if (equals(lines[i], j - quoteDtls.bytes[k].length, quoteDtls.bytes[k])) {
									quoteCount[k] += 1;
								}
								if (equals(lines[i], j + 1, quoteDtls.bytes[k])) {
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
				quote = quoteDtls.items[quoteIdxMax].value;
			} else {
				setSpecialQuote(lines.length * 2, specialCount);
			}
		}
		seperatorId = charDtls.items[seperatorIdx].value;
		return charDtls.singleBytes[seperatorIdx];
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

		CharData charDtls = toByteArray(CsvTextItem.DELIMITER.getCsvList(false, true), STANDARD_FIELD_DELIMITERS, font, false);

		int seperatorIndex = getSeperatorIndex2(lines, numberOfLines, font, charDtls);
		return seperatorIndex < 0 || seperatorIndex > charDtls.getLength()
					? null 
					: charDtls.items[seperatorIndex].value;
	}
//
//	private static byte[] getSepBytes(String font) {
//		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES;
//		byte[] sepBytes = new byte[sep.length];
//		for (int k = 0; k < sep.length; k++) {
//			sepBytes[k] = 0;
//			try {
//				sepBytes[k] = Conversion.getCsvDelimBytes(sep[k], font)[0];
//			} catch (Exception e) {
//			}
//		}
//		return sepBytes;
//	}
//
//	private static int getSeperatorIndex(byte[][] lines, int numberOfLines, String font, byte[] sepBytes) {
//		int i,j, k;
//		String s;
//		int[] count = new int[Common.FIELD_SEPARATOR_LIST1_VALUES.length];
//		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES.clone();
//		int spaceIdx = -1;
////		byte[] sepBytes = new byte[sep.length];
//		if (numberOfLines < 0) {
//			numberOfLines = lines.length;
//		}
//		//sep[0] = ",";
//		//sep[1] = "\t";
//
//		for (i = 0; i < count.length; i++) {
//			count[i] = 0;
//			if (" ".equals(sep[i])) {
//				spaceIdx = i;
//			}
//		}
//
//
//		for (i = Math.max(0, numberOfLines - 45); i < numberOfLines; i++) {
//			if (lines[i] != null) {
//				s = new String(lines[i]);
//				for (j = 0; j < s.length(); j++) {
//					for (k = 0; k < sep.length; k++) {
//						if (sep[k].startsWith("x'")) {
//							if (lines[i][j] == sepBytes[k]) {
//								count[k] += 1;
//								break;
//							}
//						} else if (sep[k].equals(s.substring(j, j+1))) {
//							count[k] += 1;
//							break;
//						}
//					}
//				}
//			}
//		}
//
//		return  getSeperatorIdx(count, numberOfLines, spaceIdx);// getMax(count);
//	}

	private static int getSeperatorIndex2(byte[][] lines, int numberOfLines, String font, CharData charDtls) {
		int i,j, k;
//		int[] count = new int[charDtls.length];
//		int[] lineCount = new int[charDtls.length];
//		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES.clone();
//		int spaceIdx = -1;
//		byte[] sepBytes = new byte[sep.length];
		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}
		//sep[0] = ",";
		//sep[1] = "\t";
		
//		for (i = 0; i < count.length; i++) {
//			count[i] = 0;
//			if (chars[i] == ' ') {
//				spaceIdx = i;
//			}
//		}
		
		int start = Math.max(0, numberOfLines - 200);
		for (i = start; i < numberOfLines; i++) {
			if (lines[i] != null) {	
				charDtls.updateCounts(lines[i]);
//				for (k = 0; k < charDtls.length; k++) {
//					boolean inLine = false;
//					for (j = 0; j < lines[i].length; j++) {
//						if (lines[i][j] == charDtls.singleBytes[k]) {
//							count[k] += 1;
//							if (! inLine) {
//								lineCount[k] += 1;
//								inLine = true;
//							}
//						}
//					}
//				}
			}
		}



//		for (i = 0; i < count.length; i++) {
//			count[i] = 0;
//			if (" ".equals(sep[i])) {
//				spaceIdx = i;
//			}
//		}


//		int start = Math.max(0, numberOfLines - 200);
//		for (i = start; i < numberOfLines; i++) {
//			if (lines[i] != null) {
//				s = new String(lines[i]);
//				for (k = 0; k < sep.length; k++) {
//					boolean inLine = false;
//					for (j = 0; j < s.length(); j++) {
//						if (sep[k].startsWith("x'")) {
//							if (lines[i][j] == sepBytes[k]) {
//								count[k] += 1;
//								if (! inLine) {
//									lineCount[k] += 1;
//									inLine = true;
//								}
//								//break;
//							}
//						} else if (sep[k].equals(s.substring(j, j+1))) {
//							count[k] += 1;
//							if (! inLine) {
//								lineCount[k] += 1;
//								inLine = true;
//							}
//							//break;
//						}
//					}
//				}
//			}
//		}

		return  getSeperatorDtls(charDtls.count, charDtls.lineCount, numberOfLines - start, charDtls.spaceIdx);// getMax(count);
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

		if (quote == null || quote.length() == 0) {
			int j, k, l;
			CharData quoteDtls = toCharArray(CsvTextItem.QUOTE.getCsvList(false, false), STANDARD_QUOTES, font);

			for (i = 0; i < lines.size(); i++) {
				line = lines.get(i);
				for (j = 0; j < line.size() - 1; j++) {
					for (k = 0; k < quoteDtls.getLength(); k++) {
						String lineStr = line.get(j);
						if ( lineStr.length() > 0
						&&	 lineStr.charAt(0) == quoteDtls.chars[k]
						&&   lineStr.charAt(lineStr.length()-1) == quoteDtls.chars[k]) {
							for (l = j+1; l < line.size(); l++) {
								String s = line.get(l);
								if (s.length() > 0 && s.charAt(s.length() - 1) == quoteDtls.chars[k]) {
										//.endsWith(Common.QUOTE_LIST[k])) {
									quote = new String(new char[]{ quoteDtls.chars[k]} );
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


	private static int getSeperatorDtls(int[] array, int[] sepLineCounts, int lineCount, int spaceIdx) {

		int idxMax = 0;
//		int idxMax2 = 0;
		int max = -1;
		int max2 = -1;
		int minLineCount = lineCount * 4 / 5;
		for (int i = 0; i < array.length; i++) {
			if (i == spaceIdx || sepLineCounts[i] < minLineCount) {
			} else if (max < array[i]) {
				max2 = max;
				//idxMax2 = idxMax;
				max = array[i];
				idxMax = i;
			} else if (max2 < array[i]) {
				max2 = array[i];
				//idxMax2 = i;
			}
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
	public String getSeperatorId() {
		return seperatorId;
	}

	/**
	 * @return the quoteIdx
	 */
	public String getQuote() {
		return quote;
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

	public boolean isCsvFile() {
		return csvFile;
	}

	private static final class CharData {
		private int lengthVal = 0;
		int spaceIdx = -1;
		int[] count, lineCount;
		
		final char[] chars;
		final byte[] singleBytes;
		final byte[][] bytes;
		final CsvTextItem[] items;
		
		
		protected CharData(char[] chars, byte[] singleBytes, byte[][] bytes) {
			super();
			this.chars = chars;
			this.singleBytes = singleBytes;
			this.bytes = bytes;
			
			this.items = new CsvTextItem[chars!=null? chars.length : bytes.length];
		}
		/**
		 * @return the length
		 */
		public final int getLength() {
			return lengthVal;
		}
		
		/**
		 * @param length the length to set
		 */
		public final void setLength(int length) {
			this.lengthVal = length;
			count = new int[length];
			lineCount = new int[length];
		}
		
		public final void updateCounts(char ch) {
			for (int i = 0; i < lengthVal; i++) {
				if (chars[i] == ch) {
					count[i] += 1;
					break;
				}
			}
		}
		
		public final void updateCounts(byte[] line) {
			for (int k = 0; k < lengthVal; k++) {
				boolean inLine = false;
				for (int j = 0; j < line.length; j++) {
					if (line[j] == singleBytes[k]) {
						count[k] += 1;
						if (! inLine) {
							lineCount[k] += 1;
							inLine = true;
						}
					}
				}
			}

		}
	}
}
