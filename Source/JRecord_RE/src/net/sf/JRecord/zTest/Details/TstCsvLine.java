/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
package net.sf.JRecord.zTest.Details;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.CsvParser.CsvParserManagerChar;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.BasicLine;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.Details.Options;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.zTest.Common.TestCommonCode;
import junit.framework.TestCase;


/**
 * This class checks reading CSV files (with embedded New-Lines) and
 * setting lines with values containing embedded New-Lines
 *
 * @author Bruce Martin
 *
 */
public class TstCsvLine extends TestCase {

	private static final String[] EOLS = {"\n", "\r", "\r\n"};


	private static final String[] CHARSETS = {
		Conversion.DEFAULT_ASCII_CHARSET, "CP037", "UTF-8", "CP273"
	};

	private static final int[] FILE_STRUCTURES = {
//		Constants.IO_CSV,
		Constants.IO_CSV_NAME_1ST_LINE,
//		Constants.IO_BIN_CSV,
		Constants.IO_BIN_CSV_NAME_1ST_LINE,
//		Constants.IO_UNICODE_CSV,
		Constants.IO_UNICODE_CSV_NAME_1ST_LINE,
		Constants.IO_NAME_1ST_LINE,
		Constants.IO_BIN_NAME_1ST_LINE,
		Constants.IO_UNICODE_NAME_1ST_LINE,
	};

	private static final int[] UNICODE_FILE_STRUCTURES = {

		Constants.IO_UNICODE_CSV_NAME_1ST_LINE,
		Constants.IO_UNICODE_NAME_1ST_LINE,
	};

	private static final int[] SIMPLE_CSV_FILE_STRUCTURES = {
		Constants.IO_CSV,
		Constants.IO_BIN_CSV,
		Constants.IO_UNICODE_CSV,
	};

	private static final int[] CSV_PARSERS = {
		CsvParserManagerChar.BASIC_EMBEDDED_CR,
		CsvParserManagerChar.BASIC_EMBEDDED_CR_NAMES_IN_QUOTES,
		CsvParserManagerChar.STANDARD_EMBEDDED_CR,
		CsvParserManagerChar.STANDARD_EMBEDDED_CR_NAMES_INQUOTE,
	};


	private static final int[] STANDARD_CSV_PARSERS = {
		CsvParserManagerChar.STANDARD_EMBEDDED_CR,
		CsvParserManagerChar.STANDARD_EMBEDDED_CR_NAMES_INQUOTE,
	};

	String[] x1 = {
			"f1,f2,f3",
			"11,'1\n12',13",
			"21,'2''\n''n22',23",
			"31,'3''\n3''2',33",
			"41,'4''4\n4''2',33",

			"'1\n12',111,13",
			"'2''\n''n22',211,23",
			"'3''\n3''2',311,33",
			"'4''4\n4''2',411,33",

			"112,13,'1\n12'",
			"212,23,'2''\n''n22'",
			"312,33,'3''\n3''2'",
			"412,33,'4''4\n4''2'",


			"112a,13,'1\n\n\n12'",
			"212,23,'2''\n \n bbb\naaa \n''n22'",
			"312,33,'3''\n\naaaa\nbbb\nccc\n3''2'",
			"412,33,'4''4\n1234\n\n45678\n\n4''2'",


			"'\n112',13,'1\n12'",
			"212,'\n23','2''\n''n22'",
			"312,33,'\n3''\n3''2'",
			"'\n412','\n33','\n4''4\n4''2'",

			"'\n112',13,'1\n12'",
			"212,'\n23','2''\n''n22'",
			"312,33,'\n3''\n3''2'",
			"'\n412','\n33','\n4''4\n4''2'",

			/* Extra */
			"11,112,13",
			"',11',112,13",
			"11,',112',13",
			"11,112,',13'",

			"'11,',112,13",
			"11,'112,',13",
			"11,112,'13,'",

			"',11',',112',',13'",
			"'11,','112,','13,'",
			"',11az,',',112az,',',13qaz,'",
	};

	String[] x2 = {
			"\"field1\",\"field2\",\"field3\"",
			"\"val-row1\",\"<h1>heading-row1</h1>\n<p>text-row1</p>\",\"lastvalue-1\"",
			"\"val-row2\",\"<h1>heading-row2</h1>\n<p>text-row2</p>\",\"lastvalue-2\"",
			"\"val-row1\",\"<h1>heading-row1</h1>\n<p>text-row1\n<p>text-row1a\n<p>text-row1b</p>\",\"lastvalue-1c\"",
			"\"val-row2\",\"<h1>heading-row2</h1>\n<p>text-row2</p>\n<p>text-row2a\n<p>text-row2b</p>\",\"lastvalue-2c\"",
			"\"val-row1\",\"<h1>heading-row1</h1>\n<p>text-row5</p>\n<p>text-row5a\n<p>text-row5b</p>\",\"lastvalue-5c\"",
			"\"val-row2\",\"<h1>heading-row2</h1>\n<p>text-row6</p>\n<p>text-row6a\n<p>text-row6b</p>\",\"lastvalue-6c\"",
	};


	String[] x3 = {
			"f1,f2,f3",
			"11,'1\n12',13",
			"21,'2''\n''n22',23",
			"31,'3''\n3''2',33",
			"41,'4''4\n4''2',33",

			"'1\n'',''\n12',111,13",
			"'2''\n''\n\n'',''\n22',211,23",
			"'\n'',''\n3''\n3''2',311,33",
			"'4''4\n4''2\n'',''',411,33",

			"'\n'',''\n112',13,'1\n12'",
			"212,23,'2''\n''n22\n'',''\n'",
			"'\n''','''\\n'',''\n312','33,''3''\n3''2\n'',''\n\n'','''",
			"412,33,'4''4\n\n'',''\n\n'',''\n4''2'",


			"112a,13,'1\n\n\n12'",
			"212,23,'2''\n \n bbb\naaa \n''n22\n'',''\n\n'',''\n'",
			"312,33,'\n'',''\n\n'',''\n3''\n\naaaa\nbbb\nccc\n3''2'",
			"412,33,'4''4\n1234\n\n45\n'',''\n\n'',''\n678\n\n4''2'",


			"'\n112',13,'1\n12'",
			"212,'\n23','2''\n''n22'",
			"312,33,'\n3''\n3''2'",
			"'\n412','\n33','\n4''4\n4''2'",

			"'\n112',13,'1\n12'",
			"212,'\n23','2''\n''n22'",
			"312,33,'\n3''\n3''2'",
			"'\n412','\n33','\n4''4\n4''2'",

			/* Extra */
			"11,112,13",
			"''',''11',112,13",
			"11,''',''112',13",
			"11,112,''',''13'",

			"'11'',',112,13",
			"11,'112'',',13",
			"11,112,'13'','",

			"'11'',''',112,13",
			"11,'112'',''',13",
			"11,112,'13'','''",

			"''',11',''',112',''',13'",
			"'11,''','112,''','13,'''",
			"''',''11'',''',''',112,',''',''13'','''",
	};

	public static final String[][] INSERT_LINES = {
		{""},
		{"1"},
		{"12"},
		{"", ""},
		{"1", ""},
		{"", "2"},
		{"1", "2"},
		{"12", "23"},
		{"123", "234"},
		{"", "", ""},
		{"1", "", ""},
		{"", "2", ""},
		{"", "", "3"},
		{"1", "2", ""},
		{"1", "", "3"},
		{"", "2", "3"},
		{"1", "2", "3"},
		{"12", "23", "34"},
		{"123", "234", "345"},
		{"123", "234", "345", "456"},
	};

	public void test01() throws Exception {

		for (int i = 0; i < FILE_STRUCTURES.length; i++) {
			for (int j = 0; j < CSV_PARSERS.length; j++) {
				for (int k = 0; k < CHARSETS.length; k++) {
					LayoutDetail ud;
					LayoutDetail d = TestCommonCode.getCsvLayout(FILE_STRUCTURES[i], CHARSETS[k], ",", "'",
							true, CSV_PARSERS[j]);
					int c = 0;


//					if (i== 2) {
//						System.out.println();
//					}
					String id = c + " " + i + ", " + j + ", " + k + " : ";
					if (CHARSETS[k].startsWith("CP")) { // EBCDIC - no \r char
						ud = tstFile ("x1 a " + id, x1, d, "\n");
						tstFile2("x1 b " + id, x1, d, "\n");
					} else {
						ud = null;
						int ii = 0;
						for (String eol : EOLS) {
							ud = tstFile ("x1 a " + id, x1, d, eol);
							tstFile2("x1 b " + id + " >" + (ii++), x1, d, eol);
	
							c+= 1;
						}
					}
					tstAddDeleteLine("x1 c " + id, x1, ud);
				}
			}
		}
	}


	public void test02() throws Exception {
		for (int i = 0; i < FILE_STRUCTURES.length; i++) {
			for (int k = 0; k < CHARSETS.length; k++) {
				LayoutDetail d = TestCommonCode.getCsvLayout(FILE_STRUCTURES[i], CHARSETS[k], ",", "\"",
						true, CsvParserManagerChar.STANDARD_EMBEDDED_CR_NAMES_TXT_INQUOTE);
				int c = 0;

				for (String eol : EOLS) {
					tstFile("x2 a " + c + " " + i + ", " + k + " : ", x2, d, eol);
					tstFile2("x2 b " + c + " " + i + ", " + k +" : ", x2, d, eol);

					c+= 1;
				}
			}
		}
	}

	public void test03() throws Exception {

		for (int i = 0; i < SIMPLE_CSV_FILE_STRUCTURES.length; i++) {
			for (int j = 0; j < CSV_PARSERS.length; j++) {
				for (int k = 0; k < CHARSETS.length; k++) {
					ExternalRecord r = TestCommonCode.getCsvExternal("" + SIMPLE_CSV_FILE_STRUCTURES[i],
							CHARSETS[k], ",", "'",
							true, CSV_PARSERS[j]);
					r.addRecordField(new ExternalField(2, Constants.NULL_INTEGER, "b", "", Type.ftChar, 0, 0, "", "", "", 0));
					r.addRecordField(new ExternalField(3, Constants.NULL_INTEGER, "c", "", Type.ftChar, 0, 0, "", "", "", 0));

					LayoutDetail d = r.asLayoutDetail();
					int c = 0;

					if (CHARSETS[k].startsWith("CP")) { // EBCDIC - no \r char
						tstFile("x1 c " + c + " " + i + ", " + j + ", " + k + " : ", x1, d, "\n");
						tstFile2("x1 d " + c + " " + i + ", " + j + ", " + k + " : ", x1, d, "\n");
					} else {
						for (String eol : EOLS) {
							tstFile("x1 c " + c + " " + i + ", " + j + ", " + k + " : ", x1, d, eol);
							tstFile2("x1 d " + c + " " + i + ", " + j + ", " + k + " : ", x1, d, eol);
	
							c+= 1;
						}
					}
				}
			}
		}
	}



	public void test04() throws Exception {

		for (int i = 0; i < UNICODE_FILE_STRUCTURES.length; i++) {
			for (int j = 0; j < CSV_PARSERS.length; j++) {
					LayoutDetail d = TestCommonCode.getCsvLayout(UNICODE_FILE_STRUCTURES[i],
							"UTF-16", ",", "'",
							true, CSV_PARSERS[j]);
					int c = 0;

					for (String eol : EOLS) {
						tstFile("x1 e " + c + " " + i + ", " + j + ", "  + " : ", x1, d, eol);
						tstFile2("x1 f " + c + " " + i + ", " + j + ", " + " : ", x1, d, eol);

						c+= 1;
				}
			}
		}

		for (int j = 0; j < CSV_PARSERS.length; j++) {

			ExternalRecord r = TestCommonCode.getCsvExternal("" + Constants.IO_UNICODE_CSV,
					"UTF-16", ",", "'",
					true, CSV_PARSERS[j]);
			r.addRecordField(new ExternalField(2, Constants.NULL_INTEGER, "b", "", Type.ftChar, 0, 0, "", "", "", 0));
			r.addRecordField(new ExternalField(3, Constants.NULL_INTEGER, "c", "", Type.ftChar, 0, 0, "", "", "", 0));

			LayoutDetail d = r.asLayoutDetail();
			int c = 0;

			for (String eol : EOLS) {
				tstFile("x1 g " + c + " " + j + ", "  + " : ", x1, d, eol);
				tstFile2("x1 h " + c + " " + j + ", "  + " : ", x1, d, eol);

				c+= 1;
			}
		}

	}



	/**
	 * Testing Standard parser with ''<NEW-LINE> and '', combination
	 *
	 * @throws Exception any error that occurs
	 */
	public void test05() throws Exception {

		for (int i = 0; i < FILE_STRUCTURES.length; i++) {
			for (int j = 0; j < STANDARD_CSV_PARSERS.length; j++) {
				for (int k = 0; k < CHARSETS.length; k++) {
					LayoutDetail d = TestCommonCode.getCsvLayout(FILE_STRUCTURES[i], CHARSETS[k], ",", "'",
							true, STANDARD_CSV_PARSERS[j]);
					int c = 0;

					if (CHARSETS[k].startsWith("CP")) { // EBCDIC - no \r char
						tstFile("x3 a " + c + " " + i + ", " + j + ", " + k + " : ", x3, d, "\n");
						tstFile2("x3 b " + c + " " + i + ", " + j + ", " + k + " : ", x3, d, "\n");
					} else {
						for (String eol : EOLS) {
							tstFile("x3 a " + c + " " + i + ", " + j + ", " + k + " : ", x3, d, eol);
							tstFile2("x3 b " + c + " " + i + ", " + j + ", " + k + " : ", x3, d, eol);
	
							c+= 1;
						}
					}
				}
			}
		}
	}




	private void tstFile2(String id, String[] f, LayoutDetail dd, String eol) throws Exception {
		String[] ff = new String[f.length];

		for (int i = 0; i < f.length; i++) {
			ff[i] = Conversion.replace(new StringBuilder(f[i]), "\n", eol).toString();
		}

		tstFile(id, ff, dd, eol);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private LayoutDetail tstFile(String id, String[] f, LayoutDetail dd, String eol) throws Exception {
		LineIOProvider  ioP  = LineIOProvider.getInstance();
		AbstractLineReader r = ioP.getLineReader(dd);
		AbstractLine l;
		ArrayList<AbstractLine> lines = new ArrayList<AbstractLine>();
		String s, stdLine;
		ArrayList<Object> fieldList = new ArrayList<Object>();
		int cc = 0;

//		System.out.println(dd.getFontName());

		r.open(TestCommonCode.arrayToStream(f, eol, dd.getFontName()), dd);
		LayoutDetail d = (LayoutDetail) r.getLayout();
		int c = d.getRecord(0).getFieldCount();
		StringBuilder b = new StringBuilder();

		String q = "";
		if (d.getRecord(0).getRecordStyle() == CsvParserManagerChar.STANDARD_EMBEDDED_CR_NAMES_TXT_INQUOTE) {
			q = d.getQuoteDetails().asString();
		}
		b.append(q +"0" + q);
		for (int i = 1; i < c; i++) {
			b.append(",").append(q + i + q);
		}

		stdLine = b.toString();

		while ((l = r.read()) != null) {
			s = l.getFullLine();
//			System.out.println("-------------------- " + cc);
//			System.out.println("==* " + Conversion.replace(new StringBuilder(l.getFullLine()), "\n", "\\n"));
			fieldList.removeAll(fieldList);
			for (int i = 0; i < c; i++) {
//				System.out.print("\t" + i + "\t" + l.getField(0, i) + " ! ");
				fieldList.add(l.getField(0, i));
				l.setField(0, i, "" + i);
//				System.out.print(l.getField(0, i) + " ~ " + l.getFullLine());
//				System.out.println();
			}
//			System.out.println("~~" + stdLine + " " + c);
//			System.out.println("**" + l.getFullLine());
			assertEquals(id + "Std Update " + cc, stdLine, l.getFullLine());

//				if (cc == 10) {
//					System.out.println();
//				}
			for (int i = 0; i < c; i++) {
				l.setField(0, i, fieldList.get(i));
//				System.out.println("==> " + l.getFullLine() + " <==> " + i + " >" + fieldList.get(i) + "<");
				assertEquals(id + "Field Check: " + cc + ", " + i, fieldList.get(i).toString(), l.getField(0, i).toString());
			}
			assertEquals(id + "Restore Update " + cc, s, l.getFullLine());
			lines.add(l);
			cc += 1;
		}



//		System.out.println(d.getFontName());
		ByteArrayOutputStream os = new ByteArrayOutputStream(500);
		AbstractLineWriter w = ioP.getLineWriter(d.getFileStructure());
		w.open(os);

		for (int i =0; i < lines.size(); i++) {
			w.write(lines.get(i));
		}

		w.close();

		byte[] bout = os.toByteArray();

		String expected = TestCommonCode.arrayToString(f, dd.getEolString());
		String actual = Conversion.toString(bout, dd.getFontName());
		if (expected.equals(actual)) {
//			System.out.println("Success: " + id + " " + lines.size());
		} else {
			System.out.println();
			System.out.println("==========================================================");
			System.out.println("Failure: " + id + " " + lines.size() + " " + dd.getFontName());
//			assertEquals(expected, actual);
			System.out.println("==========================================================");
		}

		int j = 0;
		AbstractLineReader r2 = ioP.getLineReader(d);
		r2.open(new ByteArrayInputStream(bout), d);
		while ((l = r2.read()) != null) {
			assertEquals("WriteRead Tst " + id + " ~ " + j, lines.get(j++).getFullLine(), l.getFullLine());
		}
		j = 0;
		r = ioP.getLineReader(dd);
		r.open(new ByteArrayInputStream(bout), dd);
		while ((l = r.read()) != null) {
			assertEquals("WriteRead Tst " + id + " ~ " + j, lines.get(j++).getFullLine(), l.getFullLine());
		}
		r.close();
		return (LayoutDetail) r.getLayout();
	}

	private void tstAddDeleteLine(String id, String[] f, LayoutDetail dd) {
		Line line = new Line(dd);
		Line tstLine = new Line(dd);
		CharLine charLine = new CharLine(dd, "");
		for (int i = 0; i < f.length; i++) {
			testAddToLine(id, charLine, tstLine, f[i], 3);
			testAddToLine(id, line, tstLine, f[i], 3);
			
			tstDeleteFromLine(id, charLine, tstLine, f[i], 3);
			tstDeleteFromLine(id, line, tstLine, f[i], 3);
		}
	}
	
	private void tstDeleteFromLine(String id, BasicLine<?> l, BasicLine<?> tl, String val, int colCount) {
		tl.setData(val);
		for (int i = 0; i < colCount; i++) {
			for (int j = i; j < colCount; j++) {
				int[] cols2del = new int[j - i + 1];
				for (int k = i; k <= j; k++) {
					cols2del[k-i] = k;
				}
				l.setData(val);
				l.deleteColumns(cols2del);
			}
		}
	}
	
	
	private void testAddToLine(String id, BasicLine<?> l, BasicLine<?> tl, String val, int colCount) {
		tl.setData(val);
		for (String[] s : INSERT_LINES) {
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < s.length; i++) {
				al.add(s[i]);
			}
			String sStr = al.toString();
			for (int i = 0; i < 7; i++) {
				String id1 = id + " " + i + " " + sStr + " ";
				int expectedColCount = Math.max(i, colCount) + s.length;
				l.setData(val);
				l.insetColumns(i, s);

				if (i < colCount || s[s.length - 1].length() > 0 ) {
					assertEquals(id1 + " Check Col Count ",
							expectedColCount, l.getOption(Options.OPT_GET_FIELD_COUNT));
				}
				for (int j = 0; j < Math.min(i, colCount); j++) {
					assertEquals(id1 + j, tl.getFieldValue(0, j).asString(), l.getFieldValue(0, j).asString());
				}
				for (int j = i; j < i + s.length; j++) {
					assertEquals(id1 + j, s[j - i], l.getFieldValue(0, j).asString());
				}
				for (int j = i + s.length; j < colCount + s.length; j++) {
					assertEquals(id1 + j,
							tl.getFieldValue(0, j - s.length).asString(), 
							l.getFieldValue(0, j).asString());
				}
				for (int j = colCount; j < i; j++) {
					assertEquals(id1 + j, "", l.getFieldValue(0, j).asString());
				}
			}
		}
	}
}
