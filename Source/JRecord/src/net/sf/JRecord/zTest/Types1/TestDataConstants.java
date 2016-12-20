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
      
package net.sf.JRecord.zTest.Types1;

import java.io.File;

import net.sf.JRecord.Common.FieldDetail;

public class TestDataConstants {
	public static final String TEST_DATA_DIRECTORY 
			= (new File(TestDataConstants.class.getResource("TestDataConstants.class").getFile())).getParent() + "/";
			//"F:/Work/EclipseWorkspaces/workspace/JRecord/src/net/sf/JRecord/zTest/Types1/";
	


	public static String getTestDataFileName(String charset) {
		String s = charset;
		if (s.length() == 0) {
			s = "Std";
		}
		
		return TEST_DATA_DIRECTORY + "TestData_" + s +".txt";
	}
	
	
	/**
	 * Create field
	 * 
	 * @param pos field position
	 * @param len field length
	 * @param decimal number of decimal places
	 * @param type type of the field
	 * @param charset character set.
	 * 
	 * @return the requested field
	 */
	public static FieldDetail getType(int pos, int len, int decimal, int type, String charset) {


		FieldDetail field = new FieldDetail("", "", type, decimal, charset, -1, "");

		if (len > 0) {
			field.setPosLen(pos, len);
		} else {
			field.setPosOnly(pos);
		}

		return field;
	}


}
