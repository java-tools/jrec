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
      
package net.sf.JRecord.zTest.io;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.CharLine;
import net.sf.JRecord.Details.CharLineProvider;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.FixedLengthTextReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.zTest.Common.TestCommonCode;
import junit.framework.TestCase;

/**
 * Test FixedLengthCharReader
 * - Character read for Fixed-Length-Character files.
 * @author Bruce Martin
 *
 */
public class TstFixedCharReader extends TestCase {
	private static final String SPACE_20 = "                    ";
	private static final int COUNT = 5000;
	private static final String COBOL_COPYBOOK
			= "      01 COMPANY-RECORD.\n"
			+ "         05 FIELD-1     PIC X(" + SPACE_20.length() + ").\n";


	public void testFixedLengthCharReader1() throws IOException, RecordException {
		FixedLengthTextReader r = new FixedLengthTextReader(new CharLineProvider());
		LayoutDetail schema = getSchema();
		
		r.open(getInputStream(COUNT, null), schema);
		
		for (int i = 0; i < COUNT; i++) {
			assertEquals("Checking line: " + i, formatLine(i), r.read().getFullLine());
		}
		assertTrue(r.read() == null);
	}
	

	public void testFixedLengthCharReader2() throws IOException, RecordException {
		FixedLengthTextReader r = new FixedLengthTextReader(new CharLineProvider());
		LayoutDetail schema = getSchema();
		String extra = "abc";
		
		r.open(getInputStream(COUNT, extra), schema);
		
		for (int i = 0; i < COUNT; i++) {
			assertEquals("Checking line: " + i, formatLine(i), r.read().getFullLine());
		}
		assertEquals("Checking line: " + (COUNT), extra, r.read().getFullLine());
		assertTrue(r.read() == null);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testFixedLengthCharReader3() throws IOException, RecordException {
		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(Constants.IO_FIXED_LENGTH_CHAR);
		LayoutDetail schema = getSchema();
		
		r.open(getInputStream(COUNT, null), schema);
		
		for (int i = 0; i < COUNT; i++) {
			assertEquals("Checking line: " + i, formatLine(i), r.read().getFullLine());
		}
		assertTrue(r.read() == null);
	}
	
	/**
	 * Test a Fixed Length Char write
	 * @throws IOException any IOException
	 * @throws RecordException 
	 */
	public void testFixedLengthCharWriter() throws IOException, RecordException {
		int length = SPACE_20.length();
		int num = COUNT;
		LayoutDetail schema = getSchema();
		AbstractLineWriter w = LineIOProvider.getInstance().getLineWriter(Constants.IO_FIXED_LENGTH_CHAR, "");
		ByteArrayOutputStream os = new ByteArrayOutputStream(num * length);
		
		w.open(os);
		
		for (int i = 0; i < num; i++) {
			w.write(new CharLine(schema, Integer.toString(i)));
		}
		
		w.close();
		
		byte[] byteArray = os.toByteArray();
		String s = new String(byteArray);
		System.out.println("--> " + (num * length) + " " + byteArray.length + " " + s.length());
		
		for (int i = 0; i < num; i++) {
			assertEquals("Checking line: " + i, formatLine(i), s.substring(i * length, i * length + length));
		}

	}


	private LayoutDetail getSchema() throws RecordException {
		return TestCommonCode.getLayoutFromCobolStr(
				COBOL_COPYBOOK, "COMPANY-RECORD",
				CopybookLoader.SPLIT_NONE, "", ICopybookDialects.FMT_FUJITSU);
	}
	
	
	private ByteArrayInputStream getInputStream(int count, String extra) {
		StringBuffer b = new StringBuffer();
		
		for (int i = 0; i < count; i++) {
			b.append(formatLine(i));
		}
		
		if (extra != null) {
			b.append(extra);
		}
		return new ByteArrayInputStream(b.toString().getBytes());
	}
	
	private String formatLine(int id) {
		String s = Integer.toString(id);
		return s + SPACE_20.substring(s.length());
	}
}
