package net.sf.RecordEditor.layoutEd.utils;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class LeMessages {

	public final static ReMsg ERROR_LOADING_COPYBOOK = new ReMsg("Could not load Copybook");
	public final static ReMsg ERROR_UPDATING_TABLE   = new ReMsg("Error Table:");
	public final static ReMsg DELETE_LAYOUT   = new ReMsg(" --> Deleting Record Layout: {0}");
	public final static ReMsg ADD_LAYOUT      = new ReMsg(" --> Adding Record Layout: {0}");

	public final static ReMsg COPYBOOK_DOES_NOT_EXIST = new ReMsg(
			  "Copybook {0} does not exist");
	public final static ReMsg FILE_IS_DIRECTORY = new ReMsg(
			  "File {0} is a direcory");

	public final static ReMsgId COPYBOOK_LOADED = new ReMsgId("CopybookLoaded",
					  "-->> {0} processed\n\n" + "      Copybook: {1}");

	public final static ReMsgId SQL_ERROR = new ReMsgId("SqlError",
			  "\n    SQL: {0}"
			+ "\nMessage: {1}\n");

	public final static ReMsgId RECORD_POS_GREATER_THAN_0 = new ReMsgId(
			"RecordPnl_Warn_03",
			"Field: {0}  - Position must be > 0 !!! and not {1}");

	public final static ReMsgId DEFINE_RECORD_SELECTION = new ReMsgId(
			"DefRecordSelection",
			"You should define the Record Selections details (Field - Field Value)"
			+ "and check the File Structure on the Extra sceen");

	public final static ReMsgId SCHEMA_EXPORT_TIP = new ReMsgId(
			"SchemaExportTip",
			  "<h1>File Schema Export</h1>"
			+ "This program will let you export one or more File-Schema<br/>(Record-Layouts) as Xml files"
		    + "to a specified directory." 
			+ "<br/>These Xml-File-Schema's can then be imported into another<br/>RecordEditor instance."
		    + "<p>You can use % as a wild card in the Schema (or Record Name)"
	);
	
	public final static ReMsgId SCHEMA_EXPORT_MSG = new ReMsgId(
			"SchemaExportMsg",
			"\n\nExported: {0}; Failed: {1}"
	);

	public final static ReMsg SCHEMA_EXPORT_FAILURE = new ReMsg(
			"Failed: "
	);
	
	public final static ReMsgId SCHEMA_IMPORT_TIP = new ReMsgId(
			"SchemaImportTip",
			  "<h1>File Schema Import</h1>"
			+ "This program will let you import one or more Xml File-Schema<br/>(Record-Layouts) "
		    + "from a specified directory."
			+ "<br>You can use java Regular Expressions in the filename filter"
	);

	public final static ReMsgId SCHEMA_IMPORT_MSG = new ReMsgId(
			"SchemaImportMsg",
			"\n\nImported: {0}; Failed: {1}"
	);

	public final static ReMsgId COBOL_IMPORT_MSG = new ReMsgId(
	  		"CobolImportMsg",
	  		"<h3>Cobol Copybook Import</h3>"
	  				+ "<p>This screen will import a COBOL copybook into the RecordEditor."
	  				+ "If you have a sample file, please enter it. It will be used to get the"
	  				+ "file structure, record-selection-fields and provide a file preview</p>"
	  				+ ""
	  				+ "<h4>Fields on the screen</h4>"
	  				+ "<table>"
	  				+ " <tr><Td><b>Cobol Line Format</b></td><td>Cobol lines are normally"
	  				+ "6-&gt;71 inclusive, but other formats are possible</td></tr>"
	  				+ "<tr><Td><b>Split Copybook</b></td><td>Whether the copybook should be split into "
	  				+ "into seperate <i>Sub<i> copybooks. See <a href=\"#split\">Split Options for more details</td></tr>"
	  				+ "<tr><Td><b>Font Name</b></td>Font or character-set of the data file<td></td></tr>"
	  				+ "<tr><Td><b>Cobol Dialect</b></td>Which Dialect of Cobol ???. "
	  				+ "Different Cobol compilers allocate space differently. Common Options include<ul>"
	  				+ "<li><b>Text IO (byte based)</b> Standard text file but store as bytes</li>"
	  				+ "<li><b>Fixed Length (Binary)</b> Records (lines) are a all a standard length,<br\\>"
	  				+ "no \" line break. May have binary fields</li>"
	  				+ "<li><b>Mainframe VB</b>Mainframe VB file with the RDW (record Length)</li>"
	  				+ "<li><b>Mainframe VB Dump</b>Mainframe VB file with both the "
	  				+ "<i>Record Length</i> and <i>Block Lengths</i> included</li>"
	  				+ "</ul>"
	  				+ "<td></td></tr>"
	  				+ "<tr><Td><b>File Structure</b></td><td>Organization of the data file.</td></tr>"
	  				+ "<tr><Td><b>System</b></td><td></td></tr>"
	  				+ "</table>"
	  				+ ""
	  				+ "<h4>Right hand Tabs</h4>"
	  				+ "<p>On the right hand side of the screen, there are 3 tabs<br\\>"
	  				+ "that could be displayed:"
	  				+ "<table>"
	  				+ " <tr><Td><b>Cobol</b></td><td>Display the Cobol Copybook </td></tr>"
	  				+ " <tr><Td><b>Record</b></td><td>Display / Update record details</td></tr>"
	  				+ " <tr><Td><b>File</b></td><td>Display the file (using the information entered.</td></tr>"
	  				+ "</table>"
	  				+ ""
	  				+ "<h4><a id=\"split\">Split Options</a></h4>"
	  				+ ""
	  				+ "<p><b>Split None</b> is used for single record files: </p>"
	  				+ "<pre><font color=\"#000000\"><span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\"> 7 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>01 Detail-Record.                                                <font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\"> 8 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-1                                <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\"> 9 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-2                                <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">20</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">10 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-3                                <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">11 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "</font></pre>"
	  				+ ""
	  				+ "<p>Split <b>On 01 Level</b> is used when the Copybook contains multiple <b>01</b> level records </p>"
	  				+ "<pre><font color=\"#000000\"><span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   1 </font></span><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\">0</font><font color=\"#ff0066\">1</font><font color=\"#ff0066\"> </font><font color=\"#ff0066\">Header-Record.</font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   2 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Type                            <font color=\"#006699\"><strong>Pic</strong></font> X.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   3 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>      <font color=\"#ff0000\">88</font> Header-Record         <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">H</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   4 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>  <font color=\"#ff0000\">05</font> Creation-Date                           <font color=\"#006699\"><strong>Pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">8</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">   5 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>  <font color=\"#ff0000\">05</font> Version                                 <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">3</font>)V99.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   6 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   7 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#ff0000\">01</font> Detail-Record.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   8 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Type                            <font color=\"#006699\"><strong>Pic</strong></font> X.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   9 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>      <font color=\"#ff0000\">88</font> Detail-Record         <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">D</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  10 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-1                                <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  11 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-2                                <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">20</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  12 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-3                                <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  13 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  14 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#ff0000\">01</font> Trailer-Record.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  15 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Type                            <font color=\"#006699\"><strong>Pic</strong></font> X.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  16 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>      <font color=\"#ff0000\">88</font> Trailer-Record        <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">T</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  17 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Count                           <font color=\"#006699\"><strong>Pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">9</font>).\n"
	  				+ "</font></pre>\n"
	  				+ "<p>Split <b>On Redefine</b> is used when the separate records are defined using"
	  				+ "the redefine clause. </p>"
	  				+ ""
	  				+ "<pre><font color=\"#000000\"><span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   1 </font></span><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\">0</font><font color=\"#ff0066\">1</font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\">Redef-File.</font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   2 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">03</font>  Record-Type                 <font color=\"#006699\"><strong>pic</strong></font> x.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   3 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">88</font> Header-Record  <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">H</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   4 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">88</font> Detail-Record  <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">D</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">   5 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">88</font> Trailer-Record <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">T</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   6 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">03</font>  The-Record                  <font color=\"#006699\"><strong>pic</strong></font> x(<font color=\"#ff0000\">40</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   7 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   8 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">03</font>  Header-Record <font color=\"#006699\"><strong>redefines</strong></font> The-Record.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   9 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> Group1.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  10 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>           <font color=\"#ff0000\">10</font> Field1               <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  11 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>           <font color=\"#ff0000\">10</font> Field2               <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">16</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  12 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> Run-Number               <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">5</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  13 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> Filler1                  <font color=\"#006699\"><strong>pic</strong></font> x.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  14 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> Run-Date                 <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">8</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  15 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  16 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">03</font>  Detail-Record <font color=\"#006699\"><strong>redefines</strong></font> The-Record. \n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  17 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> field1                   <font color=\"#006699\"><strong>pic</strong></font> x(<font color=\"#ff0000\">3</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  18 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> field2                   <font color=\"#006699\"><strong>pic</strong></font> x(<font color=\"#ff0000\">12</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  19 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> field3                   <font color=\"#006699\"><strong>pic</strong></font> x(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  20 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       05 Group2.                                                <font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  21 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>           <font color=\"#ff0000\">10</font> Field3               <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  22 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>           <font color=\"#ff0000\">10</font> Field4               <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">5</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  23 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  24 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">03</font>  Trailer-Record <font color=\"#006699\"><strong>redefines</strong></font> The-Record.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  25 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> record-count             <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">8</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  26 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>       <font color=\"#ff0000\">05</font> Group3.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  27 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>           <font color=\"#ff0000\">10</font> Field5               <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  28 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>           <font color=\"#ff0000\">10</font> Field6               <font color=\"#006699\"><strong>pic</strong></font> x(<font color=\"#ff0000\">21</font>).\n"
	  				+ "</font></pre>");

}
