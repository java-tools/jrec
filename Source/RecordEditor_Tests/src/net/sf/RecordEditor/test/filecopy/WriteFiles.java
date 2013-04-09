package net.sf.RecordEditor.test.filecopy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.sf.JRecord.Common.RecordRunTimeException;

/**
 * This class writes out test Files to a supplied directory
 *
 *
 * @author bm
 *
 */
public class WriteFiles {

	public static final String DataIn    = "DataIn";
	public static final String DataOut   = "DataOut";
	public static final String Copybook  = "Copybook";
	public static final String CopyParms = "CopyParms";

/***************************************************************************************************

  Following is the (Regina) Rexx program to generate the datalines


			say "	private static final String[][] dataLines = {"

			Call Process "DataIn/x_Customer_cn.csv"
	     	Call Process "DataIn/x_Customer.csv"
	     	Call Process "DataIn/x_Customer_Error.xml"
	     	Call Process "DataIn/x_Customer.txt"
	     	Call Process "DataIn/x_Customer.xml"
			Call Process "DataIn/CustomerXml_From_Fixed.xml"
			Call Process "DataIn/CsvDTAR020tabDelim.txt"
			Call Process "DataIn/xmlDtar020.xml"

	     	Call Process "Copybook/Comma Delimited names on the first line.Xml"
			Call Process "Copybook/Tab Delimited names on the first line.Xml"
			Call Process "Copybook/Description_Of_CustomerXmlFile.Xml"
			Call Process "Copybook/x_Customer_Details.Xml"
			Call Process "Copybook/x_Customer_Details_Csv.Xml"
			Call Process "Copybook/x_CustomerCommaCsv.Xml"
			Call Process "Copybook/XML - Build Layout.Xml"
			Call Process "Copybook/DTAR020.cbl"

			Call Process "CopyParms/CopyCsv2Fixed1.xml"
			Call Process "CopyParms/CopyCsv2Fixed.xml"
			Call Process "CopyParms/CopyCsvN2FixedRel.xml"
			Call Process "CopyParms/CopyCsvN2Fixed.xml"
			Call Process "CopyParms/CopyFixed2Csv.xml"
			Call Process "CopyParms/CopyXml2Fixed.xml"
			Call Process "CopyParms/CopyFixed2CommaCsv.xml"
			Call Process "CopyParms/CopyFixed2Xml.xml"
			Call Process "CopyParms/CpyDTAR020toTabDelim.xml"
			Call Process "CopyParms/CpyDTAR020toXml.xml"
			Call Process "CopyParms/CpyTabDelimToDTAR020.xml"



			say "	};"
			say

		return

		Process:
		parse arg infile

			say '		{ "'infile'",	// File Name, Data follows'
			say
			linelimit = 190
			linelimitM1 = linelimit - 1
			do while lines(infile,'N')
			 	line = Linein(infile)

			 	line = changestr('\',line,'\\')
			 	line = changestr('"',line,'\"')
			 	line = changestr(x2c(00),line,'\000')
			 	line = changestr('\000\',line,'\0\')
			 	line = changestr('\000\',line,'\0\')
				if length(line) < linelimit then do
			 	   say '			"'line'",'
				end; else do
				   pref = " "
				   do while length(line) >= linelimit
				     parse var line start 190 line
				     if substr(start,linelimitM1,1) = '\' then do
				     	start = start || substr(line,1,1)
				     	line = substr(line,2)
				     end
				     say '			'pref '"'start'"'
				     pref = "+"
				   end
				   say '			'pref '"'line'",'
				end
			end
			say "		},"
		return

 ***************************************************************************************************/



	private static final String[][] dataLines = {
		{ "DataIn/x_Customer_cn.csv",	// File Name, Data follows

			"Customer,Account,Location,Agreement,OtherFields,Election",
			"1,101,101 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,10109",
			"2,202,202 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,20209",
			"3,303,303 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,30309",
			"4,404,404 Loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,40409",
			"1,101,101 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,10109",
			"2,202,202 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,20209",
			"3,303,303 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,30309",
			"4,404,404 Loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,40409",
			"1,101,101 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,10109",
			"2,202,202 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,20209",
			"3,303,303 loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,30309",
			"4,404,404 Loc,Y,\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000,40409",
		},
		{ "DataIn/x_Customer.csv",	// File Name, Data follows

			"1	1	101	101 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	10109",
			"2	2	202	202 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	20209",
			"3	3	303	303 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	30309",
			"4	4	404	404 Loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	40409",
			"5	1	101	101 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	10109",
			"6	2	202	202 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	20209",
			"7	3	303	303 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	30309",
			"8	4	404	404 Loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	40409",
			"9	1	101	101 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	10109",
			"10	2	202	202 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	20209",
			"11	3	303	303 loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	30309",
			"12	4	404	404 Loc	Y	\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000	40409",
		},
		{ "DataIn/x_Customer_Error.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			"<ExportData>",
			"	<x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"10109\"/>",
			"	<x_Customer_Details Customer=\"2\" Account=\"202\" Location=\"202 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"20209\"/>",
			"	<x_Customer_Details Customer=\"3\" Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"30309\"/>",
			"	<x_Customer_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"40409\"/>",
			"	<x_Customer_Details Customer=\"a\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"40409\"/>",
			"	<x_Customer_Details Customer=\"4\" Account=\"bbb\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"40409\"/>",
			"	<x_Customer_Details Customer=\"4\" Account=\"bbb\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"cc\"/>",
			"</ExportData>",
		},
		{ "DataIn/x_Customer.txt",	// File Name, Data follows

			"100101101 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000010109",
			"200202202 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000020209",
			"300303303 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000030309",
			"400404404 Loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000040409",
			"100101101 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000010109",
			"200202202 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000020209",
			"300303303 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000030309",
			"400404404 Loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000040409",
			"100101101 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000010109",
			"200202202 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000020209",
			"300303303 loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000030309",
			"400404404 Loc   Y\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\000040409",
		},
		{ "DataIn/x_Customer.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			"<ExportData>",
			"	<x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"10109\"/>",
			"	<x_Customer_Details Customer=\"2\" Account=\"202\" Location=\"202 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"20209\"/>",
			"	<x_Customer_Details Customer=\"3\" Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"30309\"/>",
			"	<x_Customer_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"40409\"/>",
			"	<x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"10109\"/>",
			"	<x_Customer_Details Customer=\"2\" Account=\"202\" Location=\"202 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"20209\"/>",
			"	<x_Customer_Details Customer=\"3\" Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"30309\"/>",
			"	<x_Customer_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"40409\"/>",
			"	<x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"10109\"/>",
			"	<x_Customer_Details Customer=\"2\" Account=\"202\" Location=\"202 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"20209\"/>",
			"	<x_Customer_Details Customer=\"3\" Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"30309\"/>",
			"	<x_Customer_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\" Election=\"40409\"/>",
			"</ExportData>",
		},
		{ "DataIn/CustomerXml_From_Fixed.xml",	// File Name, Data follows

			  "<ExportData><x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"
			+ "\0\0\0\0\0\0\" Election=\"10109\"/><x_Customer_Details Customer=\"2\" Account=\"202\" Location=\"202 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"
			+ "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"20209\"/><x_Customer_Details Customer=\"3\" Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0"
			+ "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"30309\"/><x_Customer_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\0\0"
			+ "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"40409\"/><x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement="
			+ "\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"10109\"/><x_Customer_Details Customer=\"2\" Account=\"202\" Locatio"
			+ "n=\"202 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"20209\"/><x_Customer_Details Customer=\"3\""
			+ " Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"30309\"/><x_Custome"
			+ "r_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Electi"
			+ "on=\"40409\"/><x_Customer_Details Customer=\"1\" Account=\"101\" Location=\"101 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"
			+ "\0\0\0\0\0\0\0\" Election=\"10109\"/><x_Customer_Details Customer=\"2\" Account=\"202\" Location=\"202 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"
			+ "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"20209\"/><x_Customer_Details Customer=\"3\" Account=\"303\" Location=\"303 loc\" Agreement=\"Y\" OtherFields=\"\0\0\0\0\0\0\0\0\0\0\0\0\0"
			+ "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"30309\"/><x_Customer_Details Customer=\"4\" Account=\"404\" Location=\"404 Loc\" Agreement=\"Y\" OtherFields=\"\0"
			+ "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\" Election=\"40409\"/></ExportData>",
		},
		{ "DataIn/CsvTAR020tabDelim.txt",	// File Name, Data follows

			"KEYCODE-NO	STORE-NO	DATE	DEPT-NO	QTY-SOLD	SALE-PRICE",
			"64634429	20	40118	957	1	3.99",
			"66624458	20	40118	957	1	0.89",
			"63674861	20	40118	957	10	2.70",
			"65674532	20	40118	929	1	3.59",
			"64614401	59	40118	957	1	1.99",
			"64614401	59	40118	957	1	1.99",
			"61664713	59	40118	335	1	17.99",
			"61664713	59	40118	335	-1	-17.99",
			"68634752	59	40118	410	1	8.99",
			"60614487	59	40118	878	1	5.95",
			"63644339	59	40118	878	1	12.65",
			"60694698	59	40118	620	1	3.99",
			"60664659	59	40118	620	1	3.99",
			"62684217	59	40118	957	1	9.99",
			"67674686	59	40118	929	1	3.99",
			"61684613	59	40118	335	1	12.99",
			"64624770	59	40118	957	1	2.59",
			"69694814	166	40118	360	1	2.50",
			"69694814	166	40118	360	1	2.50",
			"69644164	166	40118	193	1	21.59",
			"62684907	166	40118	375	1	13.99",
			"62694193	166	40118	375	1	13.99",
			"62694193	166	40118	375	-1	-13.99",
			"62694193	166	40118	375	1	11.99",
			"63654450	166	40118	320	1	13.99",
			"62664576	166	40118	320	1	9.72",
			"63634260	166	40118	320	1	5.59",
			"64684534	166	40118	440	1	14.99",
			"64674965	166	40118	235	1	19.99",
			"64674965	166	40118	235	-1	-19.99",
			"64674965	166	40118	235	1	12.00",
			"60624523	166	40118	261	1	12.00",
			"66624253	166	40118	957	1	3.49",
			"66624253	166	40118	957	1	3.49",
			"64654284	166	40118	957	1	3.99",
			"60684907	166	40118	805	1	5.50",
			"63624299	166	40118	870	1	10.99",
			"63624367	166	40118	870	1	11.19",
			"62694575	166	40118	475	1	14.99",
			"69614011	166	40118	905	1	6.99",
			"62634996	166	40118	650	1	9.99",
			"67634503	166	40118	970	1	24.99",
			"65604476	166	40118	830	1	19.95",
			"62694170	166	40118	851	1	16.99",
			"63684098	166	40118	410	1	1.98",
			"63684098	166	40118	410	1	1.98",
			"63684098	166	40118	410	1	1.98",
			"64674609	166	40118	485	1	29.99",
			"62614014	166	40118	366	1	14.99",
			"61694741	166	40118	432	1	9.06",
			"62614534	166	40118	432	1	9.09",
		},
		{ "DataIn/xmlDtar020.xml",	// File Name, Data follows

			  "<?xml version=\"1.0\" ?><ExportData><DTAR020 KEYCODE-NO=\"64634429\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/><DTAR020 KEYCODE-NO=\"66624458\" STOR"
			+ "E-NO=\"20\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"0.89\"/><DTAR020 KEYCODE-NO=\"63674861\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"10\" SALE-PRICE=\"2"
			+ ".70\"/><DTAR020 KEYCODE-NO=\"65674532\" STORE-NO=\"20\" DATE=\"40118\" DEPT-NO=\"929\" QTY-SOLD=\"1\" SALE-PRICE=\"3.59\"/><DTAR020 KEYCODE-NO=\"64614401\" STORE-NO=\"59\" DATE=\"40118\" DE"
			+ "PT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"1.99\"/><DTAR020 KEYCODE-NO=\"64614401\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"1.99\"/><DTAR020 KEYCODE-NO=\"6"
			+ "1664713\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"335\" QTY-SOLD=\"1\" SALE-PRICE=\"17.99\"/><DTAR020 KEYCODE-NO=\"61664713\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"335\" QTY-SOLD=\"-1\""
			+ " SALE-PRICE=\"-17.99\"/><DTAR020 KEYCODE-NO=\"68634752\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"410\" QTY-SOLD=\"1\" SALE-PRICE=\"8.99\"/><DTAR020 KEYCODE-NO=\"60614487\" STORE-NO=\"59\" "
			+ "DATE=\"40118\" DEPT-NO=\"878\" QTY-SOLD=\"1\" SALE-PRICE=\"5.95\"/><DTAR020 KEYCODE-NO=\"63644339\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"878\" QTY-SOLD=\"1\" SALE-PRICE=\"12.65\"/><DTAR"
			+ "020 KEYCODE-NO=\"60694698\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"620\" QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/><DTAR020 KEYCODE-NO=\"60664659\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"620\""
			+ " QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/><DTAR020 KEYCODE-NO=\"62684217\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"9.99\"/><DTAR020 KEYCODE-NO=\"67674686\" STO"
			+ "RE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"929\" QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/><DTAR020 KEYCODE-NO=\"61684613\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"335\" QTY-SOLD=\"1\" SALE-PRICE=\"1"
			+ "2.99\"/><DTAR020 KEYCODE-NO=\"64624770\" STORE-NO=\"59\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"2.59\"/><DTAR020 KEYCODE-NO=\"69694814\" STORE-NO=\"166\" DATE=\"40118\" "
			+ "DEPT-NO=\"360\" QTY-SOLD=\"1\" SALE-PRICE=\"2.50\"/><DTAR020 KEYCODE-NO=\"69694814\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"360\" QTY-SOLD=\"1\" SALE-PRICE=\"2.50\"/><DTAR020 KEYCODE-NO="
			+ "\"69644164\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"193\" QTY-SOLD=\"1\" SALE-PRICE=\"21.59\"/><DTAR020 KEYCODE-NO=\"62684907\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"375\" QTY-SOLD=\""
			+ "1\" SALE-PRICE=\"13.99\"/><DTAR020 KEYCODE-NO=\"62694193\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"375\" QTY-SOLD=\"1\" SALE-PRICE=\"13.99\"/><DTAR020 KEYCODE-NO=\"62694193\" STORE-NO=\"1"
			+ "66\" DATE=\"40118\" DEPT-NO=\"375\" QTY-SOLD=\"-1\" SALE-PRICE=\"-13.99\"/><DTAR020 KEYCODE-NO=\"62694193\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"375\" QTY-SOLD=\"1\" SALE-PRICE=\"11.99"
			+ "\"/><DTAR020 KEYCODE-NO=\"63654450\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"320\" QTY-SOLD=\"1\" SALE-PRICE=\"13.99\"/><DTAR020 KEYCODE-NO=\"62664576\" STORE-NO=\"166\" DATE=\"40118\" DE"
			+ "PT-NO=\"320\" QTY-SOLD=\"1\" SALE-PRICE=\"9.72\"/><DTAR020 KEYCODE-NO=\"63634260\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"320\" QTY-SOLD=\"1\" SALE-PRICE=\"5.59\"/><DTAR020 KEYCODE-NO=\""
			+ "64684534\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"440\" QTY-SOLD=\"1\" SALE-PRICE=\"14.99\"/><DTAR020 KEYCODE-NO=\"64674965\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"235\" QTY-SOLD=\"1"
			+ "\" SALE-PRICE=\"19.99\"/><DTAR020 KEYCODE-NO=\"64674965\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"235\" QTY-SOLD=\"-1\" SALE-PRICE=\"-19.99\"/><DTAR020 KEYCODE-NO=\"64674965\" STORE-NO=\""
			+ "166\" DATE=\"40118\" DEPT-NO=\"235\" QTY-SOLD=\"1\" SALE-PRICE=\"12.00\"/><DTAR020 KEYCODE-NO=\"60624523\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"261\" QTY-SOLD=\"1\" SALE-PRICE=\"12.00\""
			+ "/><DTAR020 KEYCODE-NO=\"66624253\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"3.49\"/><DTAR020 KEYCODE-NO=\"66624253\" STORE-NO=\"166\" DATE=\"40118\" DEPT-"
			+ "NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"3.49\"/><DTAR020 KEYCODE-NO=\"64654284\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"957\" QTY-SOLD=\"1\" SALE-PRICE=\"3.99\"/><DTAR020 KEYCODE-NO=\"606"
			+ "84907\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"805\" QTY-SOLD=\"1\" SALE-PRICE=\"5.50\"/><DTAR020 KEYCODE-NO=\"63624299\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"870\" QTY-SOLD=\"1\" S"
			+ "ALE-PRICE=\"10.99\"/><DTAR020 KEYCODE-NO=\"63624367\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"870\" QTY-SOLD=\"1\" SALE-PRICE=\"11.19\"/><DTAR020 KEYCODE-NO=\"62694575\" STORE-NO=\"166\" "
			+ "DATE=\"40118\" DEPT-NO=\"475\" QTY-SOLD=\"1\" SALE-PRICE=\"14.99\"/><DTAR020 KEYCODE-NO=\"69614011\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"905\" QTY-SOLD=\"1\" SALE-PRICE=\"6.99\"/><DTA"
			+ "R020 KEYCODE-NO=\"62634996\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"650\" QTY-SOLD=\"1\" SALE-PRICE=\"9.99\"/><DTAR020 KEYCODE-NO=\"67634503\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"9"
			+ "70\" QTY-SOLD=\"1\" SALE-PRICE=\"24.99\"/><DTAR020 KEYCODE-NO=\"65604476\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"830\" QTY-SOLD=\"1\" SALE-PRICE=\"19.95\"/><DTAR020 KEYCODE-NO=\"6269417"
			+ "0\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"851\" QTY-SOLD=\"1\" SALE-PRICE=\"16.99\"/><DTAR020 KEYCODE-NO=\"63684098\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"410\" QTY-SOLD=\"1\" SALE"
			+ "-PRICE=\"1.98\"/><DTAR020 KEYCODE-NO=\"63684098\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"410\" QTY-SOLD=\"1\" SALE-PRICE=\"1.98\"/><DTAR020 KEYCODE-NO=\"63684098\" STORE-NO=\"166\" DATE="
			+ "\"40118\" DEPT-NO=\"410\" QTY-SOLD=\"1\" SALE-PRICE=\"1.98\"/><DTAR020 KEYCODE-NO=\"64674609\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"485\" QTY-SOLD=\"1\" SALE-PRICE=\"29.99\"/><DTAR020 "
			+ "KEYCODE-NO=\"62614014\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"366\" QTY-SOLD=\"1\" SALE-PRICE=\"14.99\"/><DTAR020 KEYCODE-NO=\"61694741\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"432\""
			+ " QTY-SOLD=\"1\" SALE-PRICE=\"9.06\"/><DTAR020 KEYCODE-NO=\"62614534\" STORE-NO=\"166\" DATE=\"40118\" DEPT-NO=\"432\" QTY-SOLD=\"1\" SALE-PRICE=\"9.09\"/></ExportData>",
		},
		{ "Copybook/Comma Delimited names on the first line.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			  "<RECORD RECORDNAME=\"Comma Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\",\" DESCRIPTION=\"Comma Delimited, names on the first line\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" ST"
			+ "YLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">",
			"	<FIELDS>",
			"		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>",
			"	</FIELDS>",
			"</RECORD>",
		},
		{ "Copybook/Comma_Delimited_names_on_the_first_line.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			  "<RECORD RECORDNAME=\"Comma Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\",\" DESCRIPTION=\"Comma Delimited, names on the first line\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" ST"
			+ "YLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">",
			"	<FIELDS>",
			"		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>",
			"	</FIELDS>",
			"</RECORD>",
		},
		{ "Copybook/Tab Delimited names on the first line.Xml",	// File Name, Data follows

			  "<?xml version=\"1.0\" ?><RECORD RECORDNAME=\"Tab Delimited, names on the first line\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"Tab Delimited, names on the first line\" FILESTRU"
			+ "CTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\"><FIELDS><FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to l"
			+ "oad\" POSITION=\"1\" TYPE=\"Char\"/></FIELDS></RECORD>",
		},
		{ "Copybook/Description_Of_CustomerXmlFile.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			"<RECORD RECORDNAME=\"zzz1\" COPYBOOK=\"zzz1\" FILESTRUCTURE=\"XML_Use_Layout\" STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">",
			"	<RECORDS>",
			"		<RECORD RECORDNAME=\"ExportData\" COPYBOOK=\"zzz1_ExportData\" FILESTRUCTURE=\"XML_Use_Layout\" STYLE=\"0\" RECORDTYPE=\"XML\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\">",
			"			<FIELDS>",
			"				<FIELD NAME=\"Xml~Name\" POSITION=\"0\" TYPE=\"XML Name Tag\"/>",
			"				<FIELD NAME=\"Xml~End\" POSITION=\"1\" TYPE=\"Check Box True / Space\"/>",
			"				<FIELD NAME=\"Following~Text\" POSITION=\"2\" TYPE=\"Edit Multi Line field\"/>",
			"				<FIELD NAME=\"Xml~Prefix\" POSITION=\"3\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Xml~Namespace\" POSITION=\"4\" TYPE=\"Char\"/>",
			"			</FIELDS>",
			"		</RECORD>",
			  "		<RECORD RECORDNAME=\"x_Customer_Details\" COPYBOOK=\"zzz1_x_Customer_Details\" FILESTRUCTURE=\"XML_Use_Layout\" STYLE=\"0\" RECORDTYPE=\"XML\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\" PAR"
			+ "ENT=\"ExportData\">",
			"			<FIELDS>",
			"				<FIELD NAME=\"Xml~Name\" POSITION=\"0\" TYPE=\"XML Name Tag\"/>",
			"				<FIELD NAME=\"Xml~End\" POSITION=\"1\" TYPE=\"Check Box True / Space\"/>",
			"				<FIELD NAME=\"Following~Text\" POSITION=\"2\" TYPE=\"Edit Multi Line field\"/>",
			"				<FIELD NAME=\"Xml~Prefix\" POSITION=\"3\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Xml~Namespace\" POSITION=\"4\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Customer\" POSITION=\"5\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Account\" POSITION=\"6\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Location\" POSITION=\"7\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Agreement\" POSITION=\"8\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"OtherFields\" POSITION=\"9\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Election\" POSITION=\"10\" TYPE=\"Char\"/>",
			"			</FIELDS>",
			"		</RECORD>",
			"		<RECORD RECORDNAME=\"/ExportData\" COPYBOOK=\"zzz1_/ExportData\" FILESTRUCTURE=\"XML_Use_Layout\" STYLE=\"0\" RECORDTYPE=\"XML\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\">",
			"			<FIELDS>",
			"				<FIELD NAME=\"Xml~Name\" POSITION=\"0\" TYPE=\"XML Name Tag\"/>",
			"				<FIELD NAME=\"Xml~End\" POSITION=\"1\" TYPE=\"Check Box True / Space\"/>",
			"				<FIELD NAME=\"Following~Text\" POSITION=\"2\" TYPE=\"Edit Multi Line field\"/>",
			"			</FIELDS>",
			"		</RECORD>",
			"	</RECORDS>",
			"</RECORD>",
		},
		{ "Copybook/x_Customer_Details.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			  "<RECORD RECORDNAME=\"x_Customer_Details\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"User Customer Details\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"RecordLayout\" LIS"
			+ "T=\"Y\" QUOTE=\"\" RecSep=\"default\">",
			"	<FIELDS>",
			"		<FIELD NAME=\"Customer\"    POSITION=\"1\"   LENGTH=\"1\"  TYPE=\"Num (Right Justified zero padded)\"/>",
			"		<FIELD NAME=\"Account\"     POSITION=\"2\"   LENGTH=\"5\"  TYPE=\"Num (Right Justified zero padded)\"/>",
			"		<FIELD NAME=\"Location\"    POSITION=\"7\"   LENGTH=\"10\" TYPE=\"Char\"/>",
			"		<FIELD NAME=\"Agreement\"   POSITION=\"17\"  LENGTH=\"1\"  TYPE=\"Char\"/>",
			"		<FIELD NAME=\"OtherFields\" POSITION=\"18\"  LENGTH=\"44\" TYPE=\"Char\"/>",
			"		<FIELD NAME=\"Election\"    POSITION=\"100\" LENGTH=\"6\"  TYPE=\"Num (Right Justified zero padded)\"/>",
			"	</FIELDS>",
			"</RECORD>",
		},
		{ "Copybook/x_Customer_Details_Csv.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			  "<RECORD RECORDNAME=\"x_Customer_Details_Csv\" COPYBOOK=\"\" DELIMITER=\"&lt;Tab&gt;\" DESCRIPTION=\"User Customer Details (Csv)\" FILESTRUCTURE=\"Default\" STYLE=\"0\" RECORDTYPE=\"Delimite"
			+ "d\" LIST=\"Y\" QUOTE=\"\" RecSep=\"default\">",
			"	<FIELDS>",
			"		<FIELD NAME=\"LineNumber\"  POSITION=\"1\" TYPE=\"Num (Right Justified zero padded)\"/>",
			"		<FIELD NAME=\"Customer\"    POSITION=\"2\" TYPE=\"Num (Right Justified zero padded)\"/>",
			"		<FIELD NAME=\"Account\"     POSITION=\"3\" TYPE=\"Num (Right Justified zero padded)\"/>",
			"		<FIELD NAME=\"Location\"    POSITION=\"4\" TYPE=\"Char\"/>",
			"		<FIELD NAME=\"Agreement\"   POSITION=\"5\" TYPE=\"Char\"/>",
			"		<FIELD NAME=\"OtherFields\" POSITION=\"6\" TYPE=\"Char\"/>",
			"		<FIELD NAME=\"Election\"    POSITION=\"7\" TYPE=\"Num (Right Justified zero padded)\"/>",
			"	</FIELDS>",
			"</RECORD>",
		},
		{ "Copybook/x_CustomerCommaCsv.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			  "<RECORD RECORDNAME=\"CustomerCommaCsv\" COPYBOOK=\"CustomerCommaCsv\" DELIMITER=\",\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"GroupOfRecords\" LIST=\"Y\" QUOTE=\"\" Rec"
			+ "Sep=\"default\">",
			"	<RECORDS>",
			"		<RECORD RECORDNAME=\"\" COPYBOOK=\"CustomerCommaCsv_\" DELIMITER=\",\" FILESTRUCTURE=\"CSV_NAME_1ST_LINE\" STYLE=\"0\" RECORDTYPE=\"Delimited\" LIST=\"N\" QUOTE=\"\" RecSep=\"default\">",
			"			<FIELDS>",
			"				<FIELD NAME=\"Customer\"    DESCRIPTION=\"Customer\"    POSITION=\"1\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Account\"     DESCRIPTION=\"Account\"     POSITION=\"2\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Location\"    DESCRIPTION=\"Location\"    POSITION=\"3\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Agreement\"   DESCRIPTION=\"Agreement\"   POSITION=\"4\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"OtherFields\" DESCRIPTION=\"OtherFields\" POSITION=\"5\" TYPE=\"Char\"/>",
			"				<FIELD NAME=\"Election\"    DESCRIPTION=\"Election\"    POSITION=\"6\" TYPE=\"Char\"/>",
			"			</FIELDS>",
			"		</RECORD>",
			"	</RECORDS>",
			"</RECORD>",
		},
		{ "Copybook/XML - Build Layout.Xml",	// File Name, Data follows

			"<?xml version=\"1.0\" ?>",
			  "<RECORD RECORDNAME=\"XML - Build Layout\" COPYBOOK=\"\" DELIMITER=\"|\" DESCRIPTION=\"XML file, build the layout based on the files contents\" FILESTRUCTURE=\"XML_Build_Layout\" STYLE=\"0\""
			+ " RECORDTYPE=\"XML\" LIST=\"Y\" QUOTE=\"'\" RecSep=\"default\">",
			"	<FIELDS>",
			"		<FIELD NAME=\"Dummy\" DESCRIPTION=\"1 field is Required for the layout to load\" POSITION=\"1\" TYPE=\"Char\"/>",
			"	</FIELDS>",
			"</RECORD>",
		},
		{ "Copybook/DTAR020.cbl",	// File Name, Data follows

			"000100*                                                                         ",
			"000200*   DTAR020 IS THE OUTPUT FROM DTAB020 FROM THE IML                       ",
			"000300*   CENTRAL REPORTING SYSTEM                                              ",
			"000400*                                                                         ",
			"000500*   CREATED BY BRUCE ARTHUR  19/12/90                                     ",
			"000600*                                                                         ",
			"000700*   RECORD LENGTH IS 27.                                                  ",
			"000800*                                                                         ",
			"000900        03  DTAR020-KCODE-STORE-KEY.                                      ",
			"001000            05 DTAR020-KEYCODE-NO      PIC X(08).                         ",
			"001100            05 DTAR020-STORE-NO        PIC S9(03)   COMP-3.               ",
			"001200        03  DTAR020-DATE               PIC S9(07)   COMP-3.               ",
			"001300        03  DTAR020-DEPT-NO            PIC S9(03)   COMP-3.               ",
			"001400        03  DTAR020-QTY-SOLD           PIC S9(9)    COMP-3.               ",
			"001500        03  DTAR020-SALE-PRICE         PIC S9(9)V99 COMP-3.               ",
			"",
		},
		{ "CopyParms/CopyCsv2Fixed1.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"./DataIn/x_Customer.csv\">",
			"      <layout name=\"./Copybook/x_Customer_Details_Csv.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details_Csv\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"./DataOut/FromCsv2.txt\">",
			"      <layout name=\"./Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CopyCsv2Fixed.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"./DataIn/x_Customer.csv\">",
			"      <layout name=\"./Copybook/x_Customer_Details_Csv.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details_Csv\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"./DataOut/CustomerOut1.txt\">",
			"      <layout name=\"./Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CopyCsvN2FixedRel.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"./DataIn/x_Customer_cn.csv\">",
			"      <layout name=\"./Copybook/Comma Delimited names on the first line.Xml~2~0~0~0~0~0\">",
			"        <record name=\"\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"./DataOut/CustomerOut1.txt\">",
			"      <layout name=\"./Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CopyCsvN2Fixed.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"./DataIn/x_Customer_cn.csv\">",
			"      <layout name=\"./Copybook/Comma Delimited names on the first line.Xml~2~0~0~0~0~0\">",
			"        <record name=\"\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"./DataOut/CustomerOut1.txt\">",
			"      <layout name=\"./Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CopyFixed2Csv.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"./DataOut/CustomerOut1aa.txt\">",
			"      <layout name=\"./Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"./DataOut/x_Customer_From_Fixed.csv\">",
			"      <layout name=\"./Copybook/x_Customer_Details_Csv.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details_Csv\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CopyXml2Fixed.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\" fieldErrorFile=\"/home/bm/Work/Temp/FieldErrors.txt\" maxErrors=\"2\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"./DataIn/x_Customer.xml\">",
			"      <layout name=\"./Copybook/XML - Build Layout.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"./DataOut/CopyFromXml.txt\">",
			"      <layout name=\"./Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CopyFixed2CommaCsv.xml",	// File Name, Data follows

		},
		{ "CopyParms/CopyFixed2Xml.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\" maxErrors=\"-1\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"/home/bm/Work/Temp/User/David/DataIn/x_Customer.txt\">",
			"      <layout name=\"/home/bm/Work/Temp/User/David/Copybook/x_Customer_Details.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"/home/bm/Work/Temp/User/David/DataOut/CustomerXml_From_Fixed.xml\">",
			"      <layout name=\"/home/bm/Work/Temp/User/David/Copybook/Description_Of_CustomerXmlFile.Xml~2~0~0~0~0~0\">",
			"        <record name=\"x_Customer_Details\">",
			"          <field>Customer</field>",
			"          <field>Account</field>",
			"          <field>Location</field>",
			"          <field>Agreement</field>",
			"          <field>OtherFields</field>",
			"          <field>Election</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CpyDTAR020toTabDelim.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"DelimCopy\" complete=\"YES\" delimiter=\"&lt;Tab>\" namesOnFirstLine=\"true\" maxErrors=\"-1\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"/home/bm/Programs/JRecord/SampleFiles/DTAR020_Extract.bin\">",
			"      <layout name=\"/home/bm/Programs/RecordEdit/HSQLDB/CopyBook/Cobol/DTAR020.cbl~1~4~0~1~0~0\"/>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"/home/bm/Programs/JRecord/SampleFiles/zCpyDTAR020tabDelim.txt\"/>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CpyDTAR020toXml.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"Xml\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\" maxErrors=\"-1\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"/home/bm/Programs/JRecord/SampleFiles/DTAR020_Extract.bin\">",
			"      <layout name=\"/home/bm/Programs/RecordEdit/HSQLDB/CopyBook/Cobol/DTAR020.cbl~1~4~0~1~0~0\"/>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"/home/bm/Programs/JRecord/SampleFiles/xmlDtar020.xml\"/>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
		{ "CopyParms/CpyTabDelimToDTAR020.xml",	// File Name, Data follows

			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<copy type=\"StandardCopy\" complete=\"YES\" delimiter=\",\" namesOnFirstLine=\"true\" maxErrors=\"-1\">",
			"  <StripTrailingSpaces>true</StripTrailingSpaces>",
			"  <quote/>",
			"  <font/>",
			"  <velocityTemplate/>",
			"  <oldfile>",
			"    <file name=\"/home/bm/Programs/JRecord/SampleFiles/zCpyDTAR020tabDelim.txt\">",
			"      <layout name=\"/home/bm/Programs/JRecord/CopyBook/Xml/Tab Delimited names on the first line.Xml~2~0~0~0~0~0\">",
			"        <record name=\"\">",
			"          <field>KEYCODE-NO</field>",
			"          <field>STORE-NO</field>",
			"          <field>DATE</field>",
			"          <field>DEPT-NO</field>",
			"          <field>QTY-SOLD</field>",
			"          <field>SALE-PRICE</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </oldfile>",
			"  <newfile>",
			"    <file name=\"/home/bm/Programs/JRecord/SampleFiles/DTAR020fromCsv.bin\">",
			"      <layout name=\"/home/bm/Programs/JRecord/CopyBook/Cobol/DTAR020.cbl~1~4~0~1~0~0\">",
			"        <record name=\"DTAR020\">",
			"          <field>KEYCODE-NO</field>",
			"          <field>STORE-NO</field>",
			"          <field>DATE</field>",
			"          <field>DEPT-NO</field>",
			"          <field>QTY-SOLD</field>",
			"          <field>SALE-PRICE</field>",
			"        </record>",
			"      </layout>",
			"    </file>",
			"  </newfile>",
			"  <RecordTree/>",
			"</copy>",
		},
	};



	public static void writeFiles(String dir) {
		BufferedWriter writer;
		int l;

		if (! (dir.endsWith("/") || dir.endsWith("\\"))) {
			dir += "/";
		}
		checkDir(dir + DataIn);
		checkDir(dir + DataOut);
		checkDir(dir + Copybook);
		checkDir(dir + CopyParms);

		for (int i = 0; i < dataLines.length; i++) {
			try {
				writer = new BufferedWriter(new FileWriter(dir + dataLines[i][0]));

				l = dataLines[i].length - 1;

				System.out.println("Writing File " + dataLines[i][0] + " lines: " + l);

				for (int j = 1; j < l; j++) {
					writer.write(dataLines[i][j]);
					writer.newLine();
				}
				writer.write(dataLines[i][l]);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkDir(String dir) {
		File f = new File(dir);

		if (f.exists()) {
			if (! f.isDirectory())  {
				throw new RecordRunTimeException(
						"Trying to create directory {0} file already exists of the same name", dir);
			}
		} else {
			f.mkdirs();
		}
	}
}
