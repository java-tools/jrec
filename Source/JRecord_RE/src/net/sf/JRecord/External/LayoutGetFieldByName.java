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

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.IGetFieldByName;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;

public final class LayoutGetFieldByName implements IGetFieldByName {
   	final LayoutDetail layout;
	final RecordDetail rec;

	public LayoutGetFieldByName(LayoutDetail l, RecordDetail rec) {
		super();
		this.rec = rec;
		this.layout = l;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.JRecord.Common.IGetFieldByName#getField(java.lang.String)
	 */
	@Override
	public IFieldDetail getField(String fieldName) {
		IFieldDetail ret = rec.getField(fieldName);
		if (ret == null) {
			ret = layout.getFieldFromName(fieldName);
		}
		return ret;
	}	

}
