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
      
package net.sf.JRecord.Details;

import net.sf.JRecord.Common.Conversion;


/**
 * Create CharLine's from supplied line (text / bytes)
 *
 * @author Bruce Martin
 *
 */
public class CharLineProvider implements LineProvider<LayoutDetail, CharLine> {

	@Override
	public CharLine getLine(LayoutDetail recordDescription) {

		return new CharLine(recordDescription, "");
	}

	@Override
	public CharLine getLine(LayoutDetail recordDescription, String linesText) {

		return new CharLine(recordDescription, linesText);
	}

	@Override
	public CharLine getLine(LayoutDetail recordDescription, byte[] lineBytes) {
		// TODO Auto-generated method stub
		return new CharLine(recordDescription,
				Conversion.toString(lineBytes, recordDescription.getFontName()));
	}

}
