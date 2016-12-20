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
      
package net.sf.JRecord.zExamples.checks;

import net.sf.JRecord.External.Def.BasicConversion;

public class CheckTypes {

	public static String[][] types = {
		{"0", "Char"},
		{"1", "Char (right justified)"},
		{"2", "Char Null terminated"},
		{"3", "Char Null padded"},
		{"4", "Hex Field"},
		{"5", "Num (Left Justified)"},
		{"6", "Num (Right Justified space padded)"},
		{"7", "Num (Right Justified zero padded)"},
		{"8", "Num Assumed Decimal (Zero padded)"},
		{"9", "Num Sign Separate Leading"},
		{"10", "Num Sign Separate Trailing"},
		{"11", "Decimal"},
		{"15", "Binary Integer"},
		{"16", "Postive Binary Integer"},
		{"17", "Float"},
		{"18", "Double"},
		{"19", "Number any decimal"},
		{"20", "Number (+ve) any decimal"},
		{"21", "Bit"},
		{"22", "Num Assumed Decimal (+ve)"},
		{"23", "Binary Integer (only +ve)"},
		{"24", "Zero Padded Number with sign=+/-"},
		{"25", "Positive Zero Padded Number"},
		{"26", "Zero Padded Number decimal=\",\""},
		{"27", "Zero Padded Number decimal=\",\" sign=+/-"},
		{"28", "Zero Padded Number decimal=\",\" (only +ve)"},
		{"29", "Num (Right Justified space padded) +/- sign"},
		{"31", "Mainframe Packed Decimal (comp-3)"},
		{"32", "Mainframe Zoned Numeric"},
		{"33", "Mainframe Packed Decimal (+ve)"},
		{"35", "Binary Integer Big Endian (Mainframe?)"},
		{"36", "Positive Integer (Big Endian)"},
		{"37", "RM Cobol Comp"},
		{"38", "RM Cobol Positive Comp"},
		{"39", "Binary Integer Big Endian (only +ve )"},
		{"41", "Fujitsu Zoned Numeric"},
		{"42", "Num (Right Just space padded, \",\" Decimal)"},
		{"43", "Num (Right Just space padded, \",\" Decimal) +/- sig"},
		{"71", "Date - Format in Parameter field"},
		{"72", "Date - YYMMDD"},
		{"73", "Date - YYYYMMDD"},
		{"74", "Date - DDMMYY"},
		{"75", "Date - DDMMYYYY"},
		{"81", "Char Rest of Record"},
		{"92", "Array Field"},
		{"109", "CheckBox Y/null"},
		{"110", "Check Box True / Space"},
		{"111", "Checkbox Y/N"},
		{"112", "Checkbox T/F"},
		{"115", "CSV array"},
		{"116", "XML Name Tag"},
		{"117", "Edit Multi Line field"},
		{"118", "Char Multi Line"},
		{"119", "Html Field"},
		
	};

	public static void main(String[] args) {
		BasicConversion b = new BasicConversion();
		
		for (String[] l : types) {
			
			int type = b.getType(0, l[1]);
			if (type == 0) {
				System.out.println(l[0] + "\t" + l[1]);
			} else if (type != Integer.parseInt(l[0])) {
				System.out.println(type + " - " + l[0] + "\t" + l[1]);
			}
		}

	}

}
