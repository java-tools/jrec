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

import java.util.Arrays;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.zTest.Common.TestCommonCode;
import junit.framework.TestCase;

public class TstLineBin2 extends TestCase {

	private static String HEX_ONES = "f1f1f1f1f1";
	private static String HEX_ZEROS = "0000000000";
	
	private String init = "               ";
	private String cobolCopybook 
			=  "       01 Detail-Record.\n"
			+ "          05 Field-1                                Pic X(03).\n"
			+ "          05 Field-2                                Pic X(05).\n"
			+ "          05 Field-3                                Pic X(07).\n";
	
	
	public void testSetHex() throws RecordException {
		LayoutDetail layout = getLayout();
		Line l;
		
		for (int i = 0; i < 5; i++) {
			String hexVal = HEX_ONES.substring(i * 2);
			System.out.println("Index " + i + "\t\tValue " + hexVal);
			
			l = new Line(layout, init);
			l.setFieldHex(0, 1, hexVal);
			
			testLine(l, i);
			
			l = new Line(layout, init);
			l.setFieldHex(0, 1, HEX_ZEROS.substring((5-i) * 2, 10) + hexVal);
			
			testLine(l, i);
		}
	}
	
	private void testLine(Line l, int i) {
		
		String hexVal = HEX_ONES.substring(i * 2);
		byte[] expected = Conversion.getBytes(init, "CP037");
		for (int j = 0; j < i; j++) {
			expected[3 + j] = 0;
		}
		for (int j =  i; j < 5; j++) {
			expected[3 + j] = ((byte) 0xF1);
		}
		
		byte[] data = l.getData();
		String expectedHexValue = HEX_ZEROS.substring((5-i) * 2, 10) + hexVal;
		if (! Arrays.equals(expected, data)) {
			System.out.println();
			System.out.print(i + "\t->"); printBytes(expected);
			System.out.print(i + "\t++"); printBytes(data);
			System.out.println();
			assertEquals("idx> " + i, expectedHexValue, l.getFieldValue(0, 1).asHex());
			assertTrue("idx> " + i, Arrays.equals(expected, data));
		}
		assertEquals("idx> " + i, expectedHexValue, l.getFieldHex(0, 1));		
		assertEquals("idx> " + i, expectedHexValue, l.getFieldValue(0, 1).asHex());		
	}
		
	
	private void printBytes(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			System.out.print("\t" +b[i]);
		}
		System.out.println();
	}
	
	private LayoutDetail getLayout() throws RecordException {
		return TestCommonCode.getLayoutFromCobolStr(cobolCopybook, "DETAIL-RECORD",
				CopybookLoader.SPLIT_NONE, "CP037", ICopybookDialects.FMT_MAINFRAME);

	}
}
