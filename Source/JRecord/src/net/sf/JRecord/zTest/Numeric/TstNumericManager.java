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
      
package net.sf.JRecord.zTest.Numeric;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Numeric.ConversionManager;
import net.sf.JRecord.Numeric.Convert;
import net.sf.JRecord.Numeric.ICopybookDialects;
import junit.framework.TestCase;

public class TstNumericManager extends TestCase {

	public void testManagerVbFileStructure() {
		int[] openCob = {
				ICopybookDialects.FMT_OPEN_COBOL,     ICopybookDialects.FMT_OPEN_COBOL_BE,
				ICopybookDialects.FMT_OPEN_COBOL_MVS, ICopybookDialects.FMT_OPEN_COBOL_MVS_BE,
				ICopybookDialects.FMT_OC_MICRO_FOCUS, ICopybookDialects.FMT_OC_MICRO_FOCUS_BE,
				ICopybookDialects.FMT_FS2000,         ICopybookDialects.FMT_FS2000_BE,
		};
		ConversionManager m = ConversionManager.getInstance();
		Convert c;

		for (int i = 0; i < openCob.length; i++) {
			c = m.getConverter4code(openCob[i]);
			assertEquals("GNU Cobol " + c.getName() + " " + i,
					Constants.IO_VB_GNU_COBOL, c.getFileStructure(true, true));
		}
		assertEquals("Mainframe", Constants.IO_VB, m.getConverter4code(ICopybookDialects.FMT_MAINFRAME).getFileStructure(true, true));
		assertEquals("Fujitsu", Constants.IO_VB_FUJITSU, m.getConverter4code(ICopybookDialects.FMT_FUJITSU).getFileStructure(true, true));
	}

}
