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
      
package net.sf.JRecord.zTest.detailsSelection;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.RecordEditorXmlLoader;
import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.JRecord.detailsSelection.FieldSelect;
import net.sf.JRecord.detailsSelection.FieldSelectX;
import net.sf.JRecord.zTest.Common.TstConstants;

public class TstFieldSelectX3 extends TestCase {

	public static final String[][] GET_RESULTS_TEXT_FIELD1 = {
		//				"1", 		"3", 		"5", 		"30", 		"30.0", 	"40", 		"200",		 "299",
		{"=",			"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"true",		"false",	"false",	"false",	"false"},
		{"Doesn't Contain",	"true",	"false",	"true",		"false",	"true",		"true",		"true",		"true"},
		{"Contains",	"false",	"true",		"false",	"true",		"false",	"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)","true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)","false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"true",		"false",	"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"false",	"false",	"false",	"false",	"true",		"true"},
		{">= (Text)",	"true",		"true",		"false",	"true",		"false",	"false",	"true",		"true"},
		{"< (Text)",	"false",	"false",	"true",		"false",	"true",		"true",		"false",	"false"},
		{"<= (Text)",	"false",	"false",	"true",		"true",		"true",		"true",		"false",	"false"},
		{"<> (Numeric)","true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"false",	"true",		"true",		"true",		"true"},

	};
	public static final String[][] GET_RESULTS_TEXT_FIELD2 = {
		//				"0", 		"3", 		"030", 		"030.0", 	"301",		 "310",		 "400"
		{"=",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"false",	"false",	"false",	"false"},
		{"Doesn't Contain",	"false","false",	"true",		"true",		"true",		"true",		"true"},
		{"Contains",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)","true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)","false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"false",	"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{">= (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<> (Numeric)","true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"true",		"true",		"true",		"true"},

	};

	public static final String[][] GET_RESULTS_TEXT_FIELD3 = {
		//				"1", 		"3", 		"5", 		"30", 		"30.0", 	"40", 		"200",		 "299",
		{"=",			"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"true",		"true",		"false",	"false",	"false"},
		{"Doesn't Contain",	"true",	"false",	"true",		"false",	"false",	"true",		"true",		"true"},
		{"Contains",	"false",	"true",		"false",	"true",		"true",		"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)","true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)","false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"false",	"true",		"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"false",	"true",		"false",	"false",	"true",		"true"},
		{">= (Text)",	"true",		"true",		"false",	"true",		"true",		"false",	"true",		"true"},
		{"< (Text)",	"false",	"false",	"true",		"false",	"false",	"true",		"false",	"false"},
		{"<= (Text)",	"false",	"false",	"true",		"false",	"true",		"true",		"false",	"false"},
		{"<> (Numeric)","true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"true",		"false",	"true",		"true",		"true"},
	};

	public static final String[][] GET_RESULTS_TEXT_FIELD4 = {
		//				"0", 		"3", 		"030", 		"030.0", 	"301",		 "310",		 "400"
		{"=",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"false",	"false",	"false",	"false"},
		{"Doesn't Contain",	"false","false",	"true",		"true",		"true",		"true",		"true"},
		{"Contains",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)","true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)","false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"false",	"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{">= (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<> (Numeric)","true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"true",		"true",		"true",		"true"},
	};


	public static final String[][] GET_RESULTS_TEXT_FIELD5 = {
		//				"1", 		"3", 		"5", 		"30", 		"30.0", 	"40", 		"200",		 "299",
		{"=",			"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"true",		"true",		"false",	"false",	"false"},
		{"Doesn't Contain",	"true",	"false",	"true",		"false",	"false",	"true",		"true",		"true"},
		{"Contains",	"false",	"true",		"false",	"true",		"true",		"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)","true",		"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)","false",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"false",	"false",	"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"false",	"true",		"true",		"false",	"true",		"true"},
		{">= (Text)",	"true",		"true",		"false",	"true",		"true",		"false",	"true",		"true"},
		{"< (Text)",	"false",	"false",	"true",		"false",	"false",	"true",		"false",	"false"},
		{"<= (Text)",	"false",	"false",	"true",		"false",	"false",	"true",		"false",	"false"},
		{"<> (Numeric)","true",		"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"true",		"true",		"true",		"true",		"true"},

	};

	public static final String[][] GET_RESULTS_TEXT_FIELD6 = {
		//				"0", 		"3", 		"030", 		"030.0", 	"301",		 "310",		 "400"
		{"=",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"false",	"false",	"false",	"false"},
		{"Doesn't Contain",	"false","false",	"true",		"true",		"true",		"true",		"true"},
		{"Contains",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)",	"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"false",	"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{">= (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<> (Numeric)",	"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"true",		"true",		"true",		"true"},
	};

	public static final String[][] GET_RESULTS_TEXT_FIELD8 = {
		//				"0", 		"3", 		"030", 		"030.0", 	"301",		 "310",		 "400"
		{"=",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"eq",			"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"!=",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<>",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"ne",			"true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{">",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"gt",			"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">=",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"ge",			"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"<",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"lt",			"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<=",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"le",			"false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"Starts With",	"false",	"true",		"false",	"false",	"false",	"false",	"false"},
		{"Doesn't Contain",	"false","false",	"true",		"true",		"true",		"true",		"true"},
		{"Contains",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{"= (Numeric)",	"false",	"false",	"true",		"true",		"false",	"false",	"false"},
		{"> (Numeric)",	"true",		"true",		"false",	"false",	"false",	"false",	"false"},
		{">= (Numeric)","true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Numeric)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Numeric)","false",	"false",	"true",		"true",		"true",		"true",		"true"},
		{"= (Text)",	"false",	"false",	"false",	"false",	"false",	"false",	"false"},
		{"> (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{">= (Text)",	"true",		"true",		"true",		"true",		"false",	"false",	"false"},
		{"< (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<= (Text)",	"false",	"false",	"false",	"false",	"true",		"true",		"true"},
		{"<> (Numeric)","true",		"true",		"false",	"false",	"true",		"true",		"true"},
		{"<> (Text)",	"true",		"true",		"true",		"true",		"true",		"true",		"true"},	};


	private final String[] lines = {
			"f1\tf2\tf3",
			"30\t40\t50",
			"30.0\t40\t50",
			"030.00\t40\t50",
			"030.0\t40\t50",
	};
	private final String[] compare1 = { "1", "3", "5", "30", "30.0", "40", "200", "299"};
	private final String[] compare2 = { "0", "3", "030", "030.0", "301", "310", "400"};

	public void testCharField1() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 0, compare1);
		doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 0, compare1, GET_RESULTS_TEXT_FIELD1);
	}

	public void testCharField2() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 0, compare2);
		doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 0, compare2, GET_RESULTS_TEXT_FIELD2);
	}

	public void testCharField3() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 1, compare1);
		//doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 1, compare1, GET_RESULTS_TEXT_FIELD3);
	}

	public void testCharField4() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 1, compare2);
		//doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 1, compare2, GET_RESULTS_TEXT_FIELD4);
	}

	public void testCharField5() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 2, compare1);
		doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 2, compare1, GET_RESULTS_TEXT_FIELD5);
	}

	public void testCharField6() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 2, compare2);
		doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 2, compare2, GET_RESULTS_TEXT_FIELD6);
	}

	public void testCharField8() throws Exception {
		//printTest(TstConstants.TAB_CSV_LAYOUT_NUM, 3, compare2);
		doTest(TstConstants.TAB_CSV_LAYOUT_NUM, 3, compare2, GET_RESULTS_TEXT_FIELD6);
	}

	private void doTest(String layoutStr, int lineNo, String[] compare, String[][] results) throws Exception {
		AbstractLine l = getLines(layoutStr).get(lineNo);
		AbstractLayoutDetails layout = l.getLayout();

		IFieldDetail fd = layout.getRecord(0).getField(0);
		FieldSelect fs ;
		String s;


		for (int i = 0; i < results.length; i++) {

			s = results[i][0];

			for (int j = 0; j < compare.length; j++) {
				fs = FieldSelectX.get("", compare[j], s, fd);

				assertEquals("Compare: " + i + ", " + j + " " + compare[j],
						results[i][j+1], "" + fs.isSelected(l));
			}
		}

	}

	private void printTest(String layoutStr, int lineNo, String[] compare) throws Exception {
		AbstractLine l = getLines(layoutStr).get(lineNo);
		AbstractLayoutDetails layout = l.getLayout();

		IFieldDetail fd = layout.getRecord(0).getField(0);
		FieldSelect fs ;
		String s;

		System.out.println(":::-->>> " + fd.getType() + " " + ExternalConversion.getTypeAsString(0, fd.getType()));

		for (int i = 0; i < Constants.VALID_COMPARISON_OPERATORS.length; i++) {
//		for (int i = 0; i < GET_RESULTS_TEXT_FIELD.length; i++) {

			s = Constants.VALID_COMPARISON_OPERATORS[i];
			//s = GET_RESULTS_TEXT_FIELD[i][0];

			System.out.print("\t{\"" + s + "\"");
			for (int j = 0; j < compare.length; j++) {
				fs = FieldSelectX.get("", compare[j], s, fd);
				System.out.print(",\t\"" + fs.isSelected(l) + "\"");

				//assertEquals("Compare: " + i + ", " + j + " " + compare[j],
				//		GET_RESULTS_TEXT_FIELD[i][j+1], "" + fs.isSelected(l));
			}
			System.out.println("},");
		}

	}

	private ArrayList<AbstractLine> getLines(String layoutTxt) throws Exception {
		return TstConstants.getLines(getLayout(layoutTxt), lines);
	}


	private LayoutDetail getLayout(String layout) throws RecordException, Exception {
		return getExternalLayout(layout).asLayoutDetail();
	}

	private ExternalRecord getExternalLayout(String layout) throws Exception {
		return RecordEditorXmlLoader.getExternalRecord(layout, "Csv Layout");
	}
}
