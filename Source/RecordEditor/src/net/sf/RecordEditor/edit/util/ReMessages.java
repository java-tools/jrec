package net.sf.RecordEditor.edit.util;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class ReMessages {

	public static final ReMsg EDIT_OPTIONS      = new ReMsg("Edit Options");
	public static final ReMsg NOT_A_SORT        = new ReMsg("File was not a sort / Sort tree definition");
	public static final ReMsg LINE_DELETE_CHECK = new ReMsg("Line Delete confirmation");
	public static final ReMsg LINE_DELETE_MSG   = new ReMsg("Do you want to delete the selected {0} lines ?");

	public static final ReMsg SAVE			    = new ReMsg(LangConversion.ST_BUTTON, "Save");
	public static final ReMsg SAVE_MESSAGE		= new ReMsg("Save File !!!");
	public static final ReMsg SAVE_CHANGES      = new ReMsg("Save changes");
	public static final ReMsg SAVE_CHANGES_FILE = new ReMsg("Save Changes to file: {0}");

	public static final ReMsg BEFORE_FIELD = new ReMsg(LangConversion.ST_ACTION, "Before {0}");
	public static final ReMsg AFTER_FIELD  = new ReMsg(LangConversion.ST_ACTION, "After {0}");
	public static final ReMsg EMPTY_VIEW   = new ReMsg(LangConversion.ST_ACTION, "Empty View can not add Child Record");

	public static final ReMsg TO_MANY_ROWS   = new ReMsg(LangConversion.ST_ACTION, "To Many Rows Selected ({0} > {1}) for ColumnDisplay");

	public static final ReMsg SELECT_DIRECTORY_DIALOG   = new ReMsg("Select Directory Dialog");
	public static final ReMsg SELECT_FILE_DIALOG  		= new ReMsg("Select File Dialog");

	public static final ReMsgId EXPORT_FILE_STRUCTURE = new ReMsgId("ExportFSDesc",
			"This option exports the file with a new File-Structure\nThis option is most use to Cobol Programers"
		+ "\n\nMainframe VB* - Mainframe VB files        Fujitsu Variable Binary - Fujitsu Cobol VB "
		+ "\nText IO       - Normal PC/Unix Text file  Fixed Length Binary - Fixed length File (no CR/LF)");


	public static final ReMsgId EXPORT_DATA_DESC = new ReMsgId("ExportDataDesc",
						"Export data in native format\n\nChange the tab to change Data format");
	public static final ReMsgId EXPORT_XML_DESC = new ReMsgId(
						"ExportXmlDesc",  "Export data as an XML file");
	public static final ReMsgId VELOCITY_NOT_INSTALLED = new ReMsgId(
			"RunVelocityHint",
            "<h2>Velocity is not Installed</h2>"
          + "Velocity has not been installed into the <b>record editor</b>.<br>"
          + "The easiest way to install Velocity is to go to the RecordEditor Download page<br>"
          + "<b>http://sourceforge.net/project/showfiles.php?group_id=139274</b> "
          + "and download file <b>ru_velocity_1.4*.zip</b>.<br>"
          + "From this zip file and copy the 2 jars files to the <b>&lt;RecordEditor install directory&gt;/lib</b> directory. "
          + "<br><br>Alternately in the <b>HowTo</b> document, there is a discussion on installing velocity.");

	public static final ReMsgId CHANGE_FILE_STRUCTURE = new ReMsgId(
			"ChgFileStructure",
            "<h2>Change File Structure</h2>"
          + "This option is mainly for <b>Cobol</b> Programmers. It allows you to change the<br>"
          + "File format between <b>Text-IO</b> (standard text file - Line Sequential in Cobol),<br>"
          + "various <b>VB</b> Formats and <b>Fixed Record Length</b> file format.<br>"
          + "You will still need to save the file to make the changes permanent.<br>"
          + "<p>If you do not understand the above, this option is probably not for you");
}
