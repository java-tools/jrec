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
      
package net.sf.JRecord.zTest.ByteIO;

import java.io.IOException;
import java.util.ArrayList;

import net.sf.JRecord.ByteIO.CsvByteReader;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.zTest.Common.TestCommonCode;
import junit.framework.TestCase;

public class TstCsvByteReader02 extends TestCase {

	String[] eols = {"\r", "\n", "\r\n"};

	String[] simpleFile01 = {
			"11,112,13",
			"21,222,23",
			"31,332,33",
	};

	String[] simpleFile02 = {
			"11,'112',13",
			"21,'222',23",
			"31,'332',33",
	};

	String[] simpleFile03 = {
			"11,'1''12',13",
			"21,'2''2''2','23'",
			"31,'3''''32',33",
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
	};

	String[] x2 = {
			"\"field1\",\"field2\",\"field3\"",
			"\"val-row1\",\"<h1>heading-row1</h1>\n<p>text-row1</p>\",\"lastvalue-1\"",
			"\"val-row2\",\"<h1>heading-row2</h1>\n<p>text-row2</p>\",\"lastvalue-2\"",
	};


	public void testSimpleFiles() throws IOException {
		int j = 0, k = 0;

		for (String eol : eols) {
			k = 0;
			CsvByteReader[] readers = {
					new CsvByteReader("", ",", null, null, true),
					new CsvByteReader("", ",", "'", null, true),
					new CsvByteReader("", ",", "'", "''", true),
			};
			for (CsvByteReader r : readers) {
				//System.out.println(eol.getBytes()[0]);
				tstFile("File01 " + j + " " + k, r, simpleFile01, eol);
				tstFile("File02 " + j + " " + k++, r, simpleFile02, eol);
			}
			//tstFile("File03 " + j + " " + 0, readers[0], simpleFile03, eol);
			tstFile("File03 " + j + " " + 2, readers[2], simpleFile03, eol);
			j += 1;
		}
	}

	public void testComplexFiles() throws IOException {
		int j = 0, k = 0;

		for (String eol : eols) {
			k = 0;

			CsvByteReader[] readers = {
					//new CsvByteReader("", ",", "'", null),
					new CsvByteReader("", ",", "'", "''", true),
			};
			CsvByteReader[] readers1 = {
					//new CsvByteReader("", ",", "'", null),
					new CsvByteReader("", ",", "\"", "\"\"", true),
					new CsvByteReader("", ",", "\"", "\\\"", true),
			};

			System.out.print("== Eol:");
			for (int ii = 0; ii < eol.length(); ii++) {
				System.out.print( "\t" + (0 +eol.charAt(ii)));
			}
//			System.out.println();
			for (CsvByteReader r : readers) {
//				System.out.println(eol.getBytes()[0]);
				tstFile("x1 " + j + " " + k, r, x1, eol);
				tstFile2("x1 a " + j + " " + k++, r, x1, eol);
			}

			k = 0;
			for (CsvByteReader r : readers1) {
				tstFile( "x2 b " + j + " " + k, r, x2, eol);
				tstFile2("x2 c " + j + " " + k, r, x2, eol);
			}
			j += 1;
		}
	}


	private void tstFile2(String id, CsvByteReader r, String[] f, String eol) throws IOException {
		String[] n = new String[f.length];

		for (int i = 0; i < f.length; i++) {
			n[i] = Conversion.replace(new StringBuilder(f[i]), "\n", eol).toString();
		}

		tstFile(id, r, n, eol);
	}
	private void tstFile(String id, CsvByteReader r, String[] f, String eol) throws IOException {

		ArrayList<String> l = new ArrayList<String>();
		byte[] b;

		r.open(TestCommonCode.arrayToStream(f, eol));
		while ((b= r.read()) != null) {
			l.add(new String(b));
		}

		int e = Math.min(f.length, l.size());

		for (int i = 0; i < e; i++) {
			assertEquals(id + ": " + i, f[i], l.get(i));
		}
		assertEquals(id + " Length ", f.length, l.size());
	}
}
