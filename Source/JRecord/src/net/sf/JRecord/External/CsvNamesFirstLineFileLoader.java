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
      
package net.sf.JRecord.External;

import java.io.IOException;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.IO.TextLineReader;
import net.sf.JRecord.Log.AbsSSLogger;

/**
 * Class to build a copybook (ExternalRecord) from a CSV file with
 * the names on the first line
 * 
 * @author Bruce Martin
 *
 */
public class CsvNamesFirstLineFileLoader implements CopybookLoader {

	private String fieldSeperator = ",";
	
	/**
	 * Create class to load Layout from CSV file with names on the first Line
	 */
	public CsvNamesFirstLineFileLoader() {
		
	}
	
	/**
	 * Create class to load Layout from CSV file with names on the first Line
	 * @param sep Field Seperator
	 */
	public CsvNamesFirstLineFileLoader(String sep) {
		fieldSeperator = sep;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.External.CopybookLoader#loadCopyBook(java.lang.String, int, int, java.lang.String, int, int, int, net.sf.JRecord.Log.AbsSSLogger)
	 */
	@Override
	public ExternalRecord loadCopyBook(String copyBookFile,
			int splitCopybookOption, int dbIdx, String font,
			int copybookFormat, int binFormat, int systemId, AbsSSLogger log)
			throws Exception {
		return loadCopyBook(copyBookFile, splitCopybookOption, dbIdx, font, binFormat, systemId, log);
	}


	/**
	 * @see net.sf.JRecord.External.CopybookLoader#loadCopyBook(java.lang.String, int, int, java.lang.String, int, int, net.sf.JRecord.Log.AbsSSLogger)
	 */
	public ExternalRecord loadCopyBook(String copyBookFile, int splitCopybookOption, int dbIdx, String font, int binFormat, int systemId, AbsSSLogger log) 
	throws IOException, RecordException {
		TextLineReader r = new TextLineReader(new DefaultLineProvider(),  true);
		r.setDefaultDelim(fieldSeperator);
		r.open(copyBookFile);
		
		r.read();
		
		r.close();
		
		return ToExternalRecord.getInstance()
				.getExternalRecord(r.getLayout(), Conversion.getCopyBookId(copyBookFile), systemId);
	}

	/**
	 * Create class to load Layout from CSV file with names on the first Line with Tab used
	 * as the field seperator
	 * @author Bruce Martin
	 *
	 */
	public static final class Tab extends CsvNamesFirstLineFileLoader {
		public Tab() {
			super("\t");
		}
	}
}
