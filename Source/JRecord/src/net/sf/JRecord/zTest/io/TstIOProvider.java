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

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.CharLineProvider;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.Details.XmlLineProvider;
import net.sf.JRecord.IO.StandardLineIOProvider;
import junit.framework.TestCase;

public class TstIOProvider extends TestCase {

	public void testGetLineProvider() {
		StandardLineIOProvider iop = new StandardLineIOProvider();
		int[] stdTests = {
				Constants.IO_DEFAULT, Constants.IO_BIN_TEXT, Constants.IO_BIN_CSV, 
				Constants.IO_BIN_CSV_NAME_1ST_LINE, Constants.IO_TEXT_LINE, 
				Constants.IO_NAME_1ST_LINE, Constants.IO_CSV, Constants.IO_CSV_NAME_1ST_LINE, 
		};
		int[] unicodeTests = {
				Constants.IO_UNICODE_TEXT, 	Constants.IO_UNICODE_CSV, 
				Constants.IO_UNICODE_CSV_NAME_1ST_LINE, Constants.IO_UNICODE_NAME_1ST_LINE
		};
	
		for (int io : stdTests) {
			assertTrue(iop.getLineProvider(io, Conversion.DEFAULT_ASCII_CHARSET) instanceof DefaultLineProvider);
			assertTrue(iop.getLineProvider(io, "CP037") instanceof DefaultLineProvider);
			assertTrue(iop.getLineProvider(io, "UTF-016") instanceof CharLineProvider);
		}
		for (int io : unicodeTests) {
			assertTrue(iop.getLineProvider(io, Conversion.DEFAULT_ASCII_CHARSET) instanceof CharLineProvider);
			assertTrue(iop.getLineProvider(io, "CP037") instanceof CharLineProvider);
			assertTrue(iop.getLineProvider(io, "UTF-016") instanceof CharLineProvider);
		}
		
		assertTrue(iop.getLineProvider(Constants.IO_XML_BUILD_LAYOUT, "") instanceof XmlLineProvider);
		assertTrue(iop.getLineProvider(Constants.IO_XML_USE_LAYOUT, "") instanceof XmlLineProvider);
	}
}
