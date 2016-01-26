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
}
