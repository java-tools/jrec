/*
 * @Author Bruce Martin
 * Created on 11/11/2005
 *
 * Purpose:
 */
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
      
package net.sf.JRecord.zTest.Common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;




/**
 * Constants for testing
 *
 * @author Bruce Martin
 *
 */
public final class TstConstants {

	public static final String[] EBCDIC_SINGLE_BYTE_CHARSETS =  {
		"IBM037",		"IBM1047",		"IBM273",		"IBM280",
		"IBM285",		"IBM297",		"IBM500",		"IBM930",
		"IBM935",		"IBM937",
		
		"cp037",	"cp273",	"cp277",	"cp278",
		"cp280",	"cp284",	"cp285",	"cp290",
		"cp297",	"cp420",	"cp424",
		"cp500",	"cp833",		"cp838",
		"cp870",	"cp871",	"cp875",	
		"cp1025",	"cp1026",	"cp1112",
		"cp1122",	"cp1123",	
		"cp1140",	"cp1141",	"cp1142",	"cp1143",
		"cp1144",	"cp1145",	"cp1146",	"cp1147",
		"cp1148",	"cp1149",	

		"CP1047",		"CP930",	"CP935",		"CP937",	
		
//		"cp423",	"cp836",	"cp880",	"cp1027",	"cp1130",	"cp1132",
//		"cp5123",	"cp8612",	"cp12708",	"cp28709",	"cp62211",	"cp62224",
//		"cp62235",	"cp62245",
	};


 //   public static final String BM_DIRECTORY = "/home/bm/Programs/";
 //   public static final String TEMP_DIRECTORY = BM_DIRECTORY + "RecordEdit/Test/";
 //   public static final String BM_DIRECTORY = "C:\\Work\\Temp/";
 //   public static final String RE_DIRECTORY = "C:\\Program Files\\RecordEdit\\HSQL\\";
 //   public static final String BM_DIRECTORY = "/home/knoppix/";
 //   public static final String RE_DIRECTORY = "/media/sdc1/RecordEditor/USB/";
 //   public static final String TEMP_DIRECTORY = BM_DIRECTORY;
//    public static final String BM_DIRECTORY = "/home/knoppix/";
//    public static final String RE_DIRECTORY = "/home/bruce/.RecordEditor/HSQLDB/";
//    public static final String TEMP_DIRECTORY = "/home/bruce/.Temp";
//    public static final String BM_DIRECTORY = "/home/knoppix/";
    public static final String RE_DIRECTORY = "G:\\Users\\BruceTst01\\RecordEditor_HSQL";
    public static final String TEMP_DIRECTORY = "G:\\Temp";
    public static final int    DB_INDEX       = 0;
    public static final String SAMPLE_DIRECTORY = RE_DIRECTORY + "SampleFiles/";
    public static final String COBOL_DIRECTORY = RE_DIRECTORY + "CopyBook/Cobol/";
    public static final String RECORD_EDITOR_XML_DIRECTORY = RE_DIRECTORY + "CopyBook/Xml/";

    public static final String CSV_DIRECTORY = RE_DIRECTORY + "CopyBook/Csv/";
    public static final String CSV_DIRECTORY_OUTPUT = RE_DIRECTORY + "CopyBook/Csv/";
    public static final String XML_DIRECTORY = RE_DIRECTORY + "CopyBook/Xml/";
    public static final String XML_DIRECTORY_OUTPUT = RE_DIRECTORY + "CopyBook/Xml/";

//    public static final String COBOL_TEST_DIR = BM_DIRECTORY + "open-cobol-1.0/CobolSrc/";

    public static final String COBOL_TEST_DIR = "/home/bruce/RecordEdit/CobolSrc/";

    public static final String TAB_CSV_LAYOUT =
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"Tab Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Tab Delimited, names on the first line\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

    public static final String TAB_CSV_LAYOUT_NUM =
			  "<?xml version=\"1.0\" ?>"
			+ "<RECORD RECORDNAME=\"Tab Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Tab Delimited, names on the first line\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">"
			+ "	<FIELDS>"
			+ "		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Num (Left Justified)\"/>"
			+ "	</FIELDS>"
			+ "</RECORD>";

    /**
     * tst constants
     */
    private TstConstants() {
        super();
    }



	public static ArrayList<AbstractLine> getLines(LayoutDetail layout, String[] lines) throws Exception {
		ArrayList<AbstractLine> ret = new ArrayList<AbstractLine>();

		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(layout);
		AbstractLine l;

		r.open(getFile(lines), layout);

		while ((l = r.read()) != null) {
			ret.add(l);
		}

		return ret;
	}


	public static InputStream getFile(String[] lines) {
		StringBuilder b = new StringBuilder();

		for (String l : lines) {
			b.append(l).append("\n");
		}

		return new ByteArrayInputStream(b.toString().getBytes());
	}
}
