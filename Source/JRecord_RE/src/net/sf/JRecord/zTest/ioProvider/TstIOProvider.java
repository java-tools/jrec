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
      
package net.sf.JRecord.zTest.ioProvider;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.BinTextReader;
import net.sf.JRecord.IO.FixedLengthTextReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.TextLineReader;
import junit.framework.TestCase;

public class TstIOProvider extends TestCase {

	public void testGetReader() {
		LineIOProvider iop = LineIOProvider.getInstance();
	
		AbstractLineReader lineReader = iop.getLineReader(Constants.IO_FIXED_LENGTH_CHAR);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof FixedLengthTextReader);
		
		lineReader = iop.getLineReader(Constants.IO_UNICODE_TEXT);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof TextLineReader);
		
		lineReader = iop.getLineReader(Constants.IO_UNICODE_NAME_1ST_LINE);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof TextLineReader);
		
		lineReader = iop.getLineReader(Constants.IO_BIN_TEXT);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof BinTextReader);
		
		lineReader = iop.getLineReader(Constants.IO_BIN_NAME_1ST_LINE);
		assertTrue(lineReader.getClass().getSimpleName(), lineReader instanceof BinTextReader);
	}

}
