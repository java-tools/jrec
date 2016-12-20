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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.sf.JRecord.ByteIO.ByteTextReader;

import junit.framework.TestCase;

public class TstBinTextReader2 extends TestCase {
	String[] testData = {
			"",
			"112233",
			"1122\n",
			"1122\n3344",
			"1122\n3344\n",
			"1122\n3344\n5566",
	};
	String[][] testResult = {
			{""},
			{"112233"},
			{"1122", ""},
			{"1122", "3344"},
			{"1122", "3344", ""},
			{"1122", "3344", "5566"},
	};

	public void testReader() throws IOException {
		for (int i = 0; i < testData.length; i++) {
			oneTstCase(i, testData[i], testResult[i]);
		}
	}


	private void oneTstCase(int i, String input, String[] expected) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		ByteTextReader r = new ByteTextReader();
		byte[] b;
		int j = 0;


		r.open(in);
		try {
			while ((b = r.read()) != null) {
				assertEquals("Check: " + i + ",  " + j, expected[j++], new String(b));
			}
		} finally {
			r.close();
		}

	}
}
