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

public class Options {

	public static final int OPT_SELECTION_PRESENT = 1;
	public static final int OPT_CHECK_LAYOUT_OK   = 2;
	public static final int OPT_STORAGE_TYPE   = 3;
	public static final int OPT_CHECK_4_STANDARD_FILE_STRUCTURES   = 4;
	public static final int OPT_CHECK_4_STANDARD_FILE_STRUCTURES2  = 5;
	public static final int OPT_SINGLE_RECORD_FILE  = 6;
	public static final int OPT_SUPPORTS_BLOCK_STORAGE  = 7;
	public static final int OPT_IS_FIXED_LENGTH  = 8;
	public static final int OPT_IS_CSV  = 9;
	public static final int OPT_IS_TEXT_EDITTING_POSSIBLE  = 10;
	public static final int OPT_TABLE_ROW_HEIGHT  = 11;
	public static final int OPT_CAN_ADD_DELETE_FIELD_VAlUES  = 12; //TODO 
	public static final int OPT_GET_FIELD_COUNT  = 14; 
	public static final int OPT_MULTIPLE_LINES_UPDATED  = 15; 
	public static final int OPT_LINE_DELETED            = 16; 
	public static final int OPT_HAS_BINARY_FIELD        = 17; 
	public static final int OPT_HAS_REDEFINE            = 18; 
//	public static final int OPT_WAS_UNKOWN              = 17; 

	public static final int NO = 0;
	public static final int UNKNOWN = Integer.MIN_VALUE;
	public static final int YES = 1;

	public static final int BINARY_STORAGE = 3;
	public static final int TEXT_STORAGE   = 4;
	public static final int OTHER_STORAGE   = 5;

	public static final int MULTIPLE_LINES_UPDATED = 5;

	public static int getValue(boolean ok) {
		if (ok) {
			return YES;
		}
		return NO;
	}
}
