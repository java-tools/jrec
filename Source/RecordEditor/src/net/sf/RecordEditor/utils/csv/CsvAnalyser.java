package net.sf.RecordEditor.utils.csv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Types.Type;
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

	public static final HashSet<String> STANDARD_CHARS = new HashSet<String>();
	
	static {
		String s = Common.STANDARD_CHARS;
		
		for (int i = 0; i < s.length(); i++) {
			STANDARD_CHARS.add(s.substring(i, i+1));
		}
		
		for (int i = 0 ; i < Common.FIELD_SEPARATOR_TEXT_LIST.length; i++) {
			STANDARD_CHARS.add(Common.FIELD_SEPARATOR_LIST1_VALUES[i]);
		}
		STANDARD_CHARS.add("\n");
	}

	private int seperatorIdx = 0;
	private int quoteIdx = 0;
	private int colNamesOnFirstLine = COLUMN_NAMES_MAYBE;
	private int fieldNameLineNo = 1;
	private int numberOfColumns = 0;
	private int[] colTypes;
	
	private String font;
	
	private ArrayList<ArrayList<String>> listOfLines = null;
	//public boolean validChars = true;
	private double ratio;
	
	public CsvAnalyser(byte[][] lines, int numberOfLines, String fontname) {
		font = fontname;
		
		if (lines != null) {
			int noLines = lines.length;
			
			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}
			
			byte sep = getSeperator(lines, noLines);
			
			listOfLines = getList(lines, noLines, sep);
			
			checkForColNames(listOfLines);
			//findColTypes(listOfLines);
		}	
	}
	
	public CsvAnalyser(byte[][] lines, int numberOfLines, String fontname, byte sep) {
		font = fontname;
		
		if (lines != null) {
			int noLines = lines.length;
			
			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}
			
			listOfLines = getList(lines, noLines, sep);
			
			checkForColNames(listOfLines);
		}	
	}

	public CsvAnalyser(String[] lines, int numberOfLines, String fontname) {
		font = fontname;
		
		if (lines != null && lines.length > 0 && lines[0] != null) {
			int noLines = lines.length;
			String s = lines[0];
			
			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}
			
			ratio = getValidCharsRatio(s);
			
			char sep = getSeperatorString(lines, noLines);
			listOfLines = getList(lines, noLines, sep);
			
			checkForColNames(listOfLines);
		}	
	}

	public CsvAnalyser(String[] lines, int numberOfLines, String fontname, char sep) {
		font = fontname;
		
		if (lines != null && lines.length > 0 && lines[0] != null) {
			int noLines = lines.length;
			String s = lines[0];
			
			if (numberOfLines >= 0 && numberOfLines < noLines) {
				noLines = numberOfLines;
			}
			
			ratio = getValidCharsRatio(s);
			
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
		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}
		
		for (i = 0; i < count.length; i++) {
			count[i] = 0;
			sepChars[i] = Common.FIELD_SEPARATOR_LIST1_VALUES[i].charAt(0);
		}
		
		for (i = Math.max(0, numberOfLines - 45); i < numberOfLines; i++) {
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
		
		seperatorIdx = getMax(count);
	
		
		int st = 2;
		char[] quoteChars = new char[Common.QUOTE_LIST.length - st];
		int[] quoteCount = new int[Common.QUOTE_LIST.length - st];
		char last;
		char sepChar = sepChars[seperatorIdx];
		
		for (i = st; i < Common.QUOTE_LIST.length; i++) {
			quoteCount[i - st] = 0;
			quoteChars[i - st] = Common.QUOTE_LIST[i].charAt(0);
		}
		
		for (i = 1; i < numberOfLines; i++) {
			if (lines[i] != null) {
				last = 0;
				for (j = 0; j < lines[i].length() - 1; j++) {
					if (lines[i].charAt(j) == sepChar) {
						for (k = 0; k < quoteChars.length; k++) {
							if (last == quoteChars[k]) {
								quoteCount[k] += 1;
							}
							if (lines[i].charAt(j+1) == quoteChars[k]) {
								quoteCount[k] += 1;
							}
						}
					}
					
					last = lines[i].charAt(j);
				}
			}
		}
		
		int quoteIdxMax = getMax(quoteCount);
		
		if (quoteCount[quoteIdxMax] > 7) {
			quoteIdx = quoteIdxMax + st;
		}
		
		return sepChars[seperatorIdx];

	}

	private byte getSeperator(byte[][] lines, int numberOfLines) {

		int i,j, k;
		String s;
		int[] count = new int[Common.FIELD_SEPARATOR_LIST1_VALUES.length];
		String[] sep = /*(String[])*/ Common.FIELD_SEPARATOR_LIST1_VALUES.clone();
		byte[] sepBytes = new byte[sep.length];
		if (numberOfLines < 0) {
			numberOfLines = lines.length;
		}
		//sep[0] = ",";
		//sep[1] = "\t";
		
		for (i = 0; i < count.length; i++) {
			count[i] = 0;
		}
		for (k = 0; k < sep.length; k++) {
			sepBytes[k] = 0;
			try {
				if (sep[k].startsWith("x'")) {
					sepBytes[k] = Conversion.getByteFromHexString(sep[k]);
				} else {
					sepBytes[k] = Conversion.getBytes(sep[k], font)[0];
				}
			} catch (Exception e) {
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
		
		seperatorIdx = getMax(count);
	
		
		int st = 2;
		byte[] quoteBytes = new byte[Common.QUOTE_LIST.length - st];
		int[] quoteCount = new int[Common.QUOTE_LIST.length - st];
		byte last = 0;
		byte sepByte = sepBytes[seperatorIdx];
		
		if (sepByte == 0) {
			try {
				sepByte = Conversion.getBytes(sep[seperatorIdx], "")[0];
			} catch (Exception e) {
			}
		}
		
		for (i = st; i < Common.QUOTE_LIST.length; i++) {
			quoteCount[i - st] = 0;
			quoteBytes[i - st] = Conversion.getBytes(Common.QUOTE_LIST[i], "")[0];
		}
		
		for (i = Math.max(0, numberOfLines - 15); i < numberOfLines; i++) {
			if (lines[i] != null) {
				last = 0;
				for (j = 0; j < lines[i].length - 1; j++) {
					if (lines[i][j] == sepByte) {
						for (k = 0; k < quoteBytes.length; k++) {
							if (last == quoteBytes[k]) {
								quoteCount[k] += 1;
							}
							if (lines[i][j+1] == quoteBytes[k]) {
								quoteCount[k] += 1;
							}
						}
					}
					
					last = lines[i][j];
				}
			}
		}
		
		int quoteIdxMax = getMax(quoteCount);
		
		if (quoteCount[quoteIdxMax] > 7) {
			quoteIdx = quoteIdxMax + st;
		}
		
		return sepBytes[seperatorIdx];
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
		
		if (colNamesOnFirstLine != COLUMN_NAMES_NO && lines.size() > fieldNameLineNo + 4) {
			int noNums;

			int limit = (lines.size() - fieldNameLineNo) / 3;
//			System.out.println();
			System.out.println("Limit: " + limit);
			
			colTypes = new int[numberOfColumns];
			for (int j = 0; j < numberOfColumns; j++) {
				noNums = 0;
				colTypes[j] = Type.ftChar;
				for (i = fieldNameLineNo; i < lines.size(); i++) {
					line = lines.get(i);
					if (j < line.size()) {
						try {
							new BigDecimal(line.get(j));
							noNums += 1;
						} catch (Exception e) {
						}
					}
				}
				
				System.out.print("\t" + noNums);
				if (noNums > 3 && noNums > limit) {
					colNamesOnFirstLine = COLUMN_NAMES_YES;
					colTypes[j] = Type.ftNumAnyDecimal;
				}
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

	
	
	private int getMax(int[] array) {

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

	/**
	 * @return the validChars
	 */
	public boolean isValidChars() {
		return ratio > 0.75;
	}

	/**
	 * @return the validChars
	 */
	public static double getValidCharsRatio(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (STANDARD_CHARS.contains(s.substring(i, i+1))) {
				count += 1;
			}
		}
		return (double)count / (double)  s.length();
	}
}
