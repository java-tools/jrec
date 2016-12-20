package net.sf.RecordEditor.utils.msg;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class UtMessages {

	public static final ReMsg INVALID_FONT  = new ReMsg("Font (character set) {0} is not supported");
	public static final ReMsg SINGLE_BYTE_FONT  = new ReMsg("Only single-byte Fonts (character sets) allowed; {0} is multi-byte");

	public static final ReMsg SAMPLE_FILE_MSG  = new ReMsg("You must enter a sample file");
	public static final ReMsg LAYOUT_MSG       = new ReMsg("You must enter a LayoutName");
	public static final ReMsg INPUT_COPYBOOK   = new ReMsg("You must enter a input copybook");
	public final static ReMsg ERROR_CONVERTING_COPYBOOK = new ReMsg("Error Converting Copybook:");
	public static final ReMsg FIELD_SELECTION  = new ReMsg("Field Selection: {0}");
	public static final ReMsg NOT_A_RECORD_TREE = new ReMsg("File was not a Record Tree definition");

	public static final ReMsg NOT_A_FILTER          = new ReMsg("The specified file was not a filter definition");
	public static final ReMsg NOT_A_SIMPLE_FILTER   = new ReMsg("Can not load a Group Filter to a simple filter");
	public static final ReMsg SHOULD_NOT_HAPEN      = new ReMsg("Internal error, should not get here");

	public static final ReMsg FILTER_LIMIT_REACHED  = new ReMsg("The Filter limit of {0} has been reached, do you wish to continue?");
	public static final ReMsg FILTER_LIMIT_EXCEEDED = new ReMsg("Filter limit of {0} exceeded; only the first {0} lines in the filtered view");

	public static final ReMsg FILE_FORMAT_USED      = new ReMsg("File Format of {1} used to read File {0}");
	public static final ReMsg FILE_FORMAT_CHANGED   = new ReMsg("File Format of File {0} changed to {1}");
	public static final ReMsg SAVE_FILE             = new ReMsg("Save File");
	public static final ReMsg SAVE_FILE_NAME        = new ReMsg("Save File: {0} ?");

	public static final ReMsg ERROR_SAVING_FILE     = new ReMsg("\n\n Error saving file: {0}, message: {1}");

	public static final ReMsg LAYOUT_CANT_BE_LOADED = new ReMsg("Record Layout {0} can not be loaded:");
	
	public static final ReMsg COPIED                = new ReMsg("Copied: {0} of {1}");
	public static final ReMsg PASTE                 = new ReMsg("Paste: {0} of {1} done");
	public static final ReMsg FILTER                = new ReMsg("    Found: {2}\nProcessed: {0} of {1}");
	public static final ReMsg REPLACE_ALL           = new ReMsg("Replaced {0} occurences of {1}");

	public static final ReMsg FILE_DOES_NOT_EXIST   = new ReMsg("File: {0} does not exist");
	public static final ReMsg DIRECTORY_NOT_ALLOWED = new ReMsg("Directory: {0} is not allowed");

	public static final ReMsgId LANGUAGE_WARNING    = new ReMsgId("TranslationStatus", "");

	public static final ReMsg CAN_NOT_RETRIEVE_SCHEMA_MSG = new ReMsg("Can not retrieve schema:  nothing generated");
	public static final ReMsg INVALID_FILE_TYPE_MSG = new ReMsg("Invalid file Type, this template is for {0} files");

	public final static ReMsg COPYBOOK_DOES_NOT_EXIST = new ReMsg(
			  "Copybook {0} does not exist");
	public final static ReMsg FILE_IS_DIRECTORY = new ReMsg(
			  "File {0} is a direcory");

	public static final ReMsgId FIELD_COLOR_TIP     = new ReMsgId("FieldColorTip", 
					"<h3>Field Colors</h3>"
				+	"This screen lets you set the various field colors to be used<br/>"
				+	"in the TextEditor view."
	);
	
	public static final ReMsgId NEWER_DB     = new ReMsgId("NewDB", 
			"The backend DB appears to be from a newer version ({0} or later)\n"
		+	"than the code {1}.\n\n"
		+	"Proceed with caution"
	);


	public static final ReMsgId SPECIAL_COLOR_TIP     = new ReMsgId("SpecialColorTip", 
					"<h3>Special Colors</h3>"
				+	"This screen lets you set special colors (like field seperator)."
	);

	public static final ReMsgId LANG_RESTART = new ReMsgId(
			"LangRestart",
			"The Language has been changed to {0}, you need to restart the RecordEditor, "
		  + "for the change to come into affect."
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
	  				+ " <tr><Td><b>Cobol Line Format</b></td><td>Cobol lines are normally "
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
	  				+ "<pre><font color=\"#000000\"><span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\"> 7 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>01 Detail-Record.                                          <font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\"> 8 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-1                          <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\"> 9 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-2                          <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">20</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">10 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-3                          <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">11 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "</font></pre>"
	  				+ ""
	  				+ "<p>Split <b>On 01 Level</b> is used when the Copybook contains multiple <b>01</b> level records </p>"
	  				+ "<pre><font color=\"#000000\"><span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   1 </font></span><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\"> </font><font color=\"#ff0066\">0</font><font color=\"#ff0066\">1</font><font color=\"#ff0066\"> </font><font color=\"#ff0066\">Header-Record.</font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   2 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>  <font color=\"#ff0000\">05</font> Record-Type                      <font color=\"#006699\"><strong>Pic</strong></font> X.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   3 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>      <font color=\"#ff0000\">88</font> Header-Record        <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">H</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   4 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>  <font color=\"#ff0000\">05</font> Creation-Date                    <font color=\"#006699\"><strong>Pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">8</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">   5 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>  <font color=\"#ff0000\">05</font> Version                          <font color=\"#006699\"><strong>pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">3</font>)V99.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   6 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   7 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#ff0000\">01</font> Detail-Record.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   8 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Type                     <font color=\"#006699\"><strong>Pic</strong></font> X.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">   9 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>      <font color=\"#ff0000\">88</font> Detail-Record        <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">D</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  10 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-1                         <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  11 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-2                         <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">20</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  12 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Field-3                         <font color=\"#006699\"><strong>Pic</strong></font> X(<font color=\"#ff0000\">10</font>).\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  13 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  14 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#ff0000\">01</font> Trailer-Record.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#990066\">  15 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Type                     <font color=\"#006699\"><strong>Pic</strong></font> X.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  16 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>      <font color=\"#ff0000\">88</font> Trailer-Record   <font color=\"#006699\"><strong>value</strong></font> <font color=\"#ff00cc\">'</font><font color=\"#ff00cc\">T</font><font color=\"#ff00cc\">'</font>.\n"
	  				+ "<span style=\"background:#dbdbdb; border-right:solid 2px black; margin-right:5px; \"><font color=\"#000000\">  17 </font></span><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font><font color=\"#cc6600\"> </font>   <font color=\"#ff0000\">05</font> Record-Count                    <font color=\"#006699\"><strong>Pic</strong></font> <font color=\"#ff0000\">9</font>(<font color=\"#ff0000\">9</font>).\n"
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

	public final static ReMsgId CODE_GEN_MSG = new ReMsgId(
	  		"CodeGenMsg",
	  		 "<h3>Java~JRecord Code Generation</h3>"
	  				+ "<p>This function will generate <b>Java~JRecord</b> "
	  				+ "code from a Cobol Copybook<br/>"
	  				+ "(JRecord: <b>https://sourceforge.net/projects/jrecord/</b>). "
	  				+ "It includes several <br/><i>Code Templates</i> which determine what code "
	  				+ "is generated. The Templates include:"
	  				+ ""
	  				+ "<table>"
	  				+ " <tr><td><b>standard</b></td><td>This template generates a <i>Cobol Field Name</i>"
	  				+ "class and sample read / write programs.</td></tr>"
	  				+ " <tr><td><b>lineWrapper</b></td><td> This template generates<ul>"
	  				+ " <li>a <i>Cobol Field Name</i> class</li>"
	  				+ " <li>a <i>Line Wrapper</i> class where access the fields by java getter/setter's for "
	  				+ "each field.</li>"
	  				+ " <li>Sample read / write programs</li>"
	  				+ "</ul></td></tr>"
	  				+ "<tr><td><b>stdPojo</b></td><td>This is an extension of the <b>lineWrapper</b> "
	  				+ "template. It adds these extra classes:<ul>"
	  				+ " <li>a <i>a Java Pojo</i> class for each Record type.</li>"
	  				+ " <li>a class to copy the <i>LineWrapper</i> class to/from the <i>pojo-line</i>"
	  				+ " class</li>"
	  				+ "</ul></td></tr></table>"
	  		
	  		);
	
	public final static ReMsgId CODE_GEN_MAIN_MSG = new ReMsgId(
	  		"CodeGenMainMsg",
	  		"<h2>JRecord CodeGen option</h2>\n"
	  				+ "<p>In this function you need to enter a\n"
	  				+ "Cobol Copybook, Cobol Data file, Cobol Dialect\n"
	  				+ "and fix any attributes as needed.\n"
	  				+ "<p>From the generate menu, you then hacve the option of\n"
	  				+ "<ul>\n"
	  				+ " <li>Generating Java / JRecord code to read/write the cobol data file\n"
	  				+ " <li>Generating Java, Java Script, bat and shell scripts to run the \n"
	  				+ "various JRecord based utilities.\n"
	  				+ "</ul><p><b>Note:</b>\tJRecord is available at<br>"
	  				+ "\t <b>https://sourceforge.net/projects/jrecord/</b>");

}
