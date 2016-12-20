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
      
package net.sf.JRecord.zTest.Parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import junit.framework.TestCase;

public class TstBinParser extends TestCase {
	private static final String[][] flds = {
		{""},
		{"", "", "", "", ""},
		{"1"},
		{"12"},
		{"123"},
		{"1", "", "", "", ""},
		{"", "", "1", "", ""},
		{"", "", "", "", "1"},
		{"1", "2", "3", "4", "5"},
		{"11", "22", "33", "44", "55"},
		{"1", "12", "123", "1234", "12345"},
		{"12345", "1234", "123", "12", "1"},
		{"", "2", "3", "4", "5"},
		{"1", "", "3", "4", "5"},
		{"1", "2", "", "4", "5"},
		{"1", "2", "3", "", "5"},
		{"1", "2", "3", "4", ""},
	};
	
	private static String[] charsets = {"", "CP037"};


	public void testGetValue() {
			
		for (byte j = 0; j < 3; j ++) {
			BinaryCsvParser p = new BinaryCsvParser(j);
			for (String c : charsets) {
				for (String[] l : flds) {
					ArrayList<String> al = toList(l);
					byte[] b = p.formatFieldList(al, c);
					String id = c + " sep=" + j + " line: " + al.toString() + " ";
					//tstLine(c + " sep=" + j + " line: " + al.toString(), b, l, c, j);
					//System.out.println(id);
					

					for (int i = 0; i < l.length; i++) {
						assertEquals(id + i, l[i], p.getValue(b, i+1, c));
					}
				}
			}
		}
	}
	

	public void testGetFieldList() {
		
		for (byte j = 0; j < 3; j ++) {
			BinaryCsvParser p = new BinaryCsvParser(j);
			for (String c : charsets) {
				for (String[] l : flds) {
					ArrayList<String> al = toList(l);
					byte[] b = p.formatFieldList(al, c);
					String id = c + " sep=" + j + " line: " + al.toString() + " ";
					//tstLine(c + " sep=" + j + " line: " + al.toString(), b, l, c, j);
					System.out.println(id);
					
					List<String> fldList = p.getFieldList(b, c);
					assertEquals(id + "size", l.length, fldList.size());
					assertEquals(id + "Count", l.length, p.countTokens(b));
					for (int i = 0; i < l.length; i++) {
						assertEquals(id + i, l[i], fldList.get(i));
					}
				}
			}
		}
	}

	public void testUpdateValue() {
		
		for (byte j = 0; j < 3; j ++) {
			BinaryCsvParser p = new BinaryCsvParser(j);
			for (String c : charsets) {
				for (String[] l : flds) {
					byte[] b = {};
					for (int i = 0; i < l.length; i++) {
						FieldDetail fldDtl = new FieldDetail("", "", 0, 0, c, 0, "");
						fldDtl.setPosOnly(i + 1);
						b = p.updateValue(b, fldDtl, l[i]);
					}
					String id = c + " sep=" + j + " line: " + toList(l).toString();
					tstLine(id, b, l, c, j);
					assertEquals(id + " Count", l.length, p.countTokens(b));
				}
			}
		}
	}
	

	public void testFormatFieldList() {
		
		for (byte j = 0; j < 3; j ++) {
			BinaryCsvParser p = new BinaryCsvParser(j);
			for (String c : charsets) {
				for (String[] l : flds) {
					ArrayList<String> al = toList(l);
					byte[] b = p.formatFieldList(al, c);
					String id = c + " sep=" + j + " line: " + al.toString();
					tstLine(id, b, l, c, j);
					assertEquals(id + " Count", l.length, p.countTokens(b));
				}
			}
		}
	}
	
	private ArrayList<String> toList(String[] l) {
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < l.length; i++) {
			al.add(l[i]);
		}
		return al;
	}

	private void tstLine(String id, byte[] line, String[] vals, String charset, byte sep) {
		int pos = 0;
		byte[] b;
		for (int i = 0; i < vals.length - 1; i++) {
			b = Conversion.getBytes(vals[i], charset);
			for (int j = 0; j < b.length; j++) {
				assertEquals(id + " " + i + ", " + j, b[j], line[pos++]);
			}
			assertEquals(id + " " + i + " sep ", sep, line[pos++]);
		}
		
		int i = vals.length - 1;
		b = Conversion.getBytes(vals[i], charset);
		for (int j = 0; j < b.length; j++) {
			assertEquals(id + " " + i + ", " + j, b[j], line[pos++]);
		}
	}
}
