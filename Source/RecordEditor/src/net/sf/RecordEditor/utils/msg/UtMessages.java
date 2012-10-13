package net.sf.RecordEditor.utils.msg;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class UtMessages {

	public static final ReMsg SAMPLE_FILE_MSG  = new ReMsg("You must enter a sample file");
	public static final ReMsg LAYOUT_MSG       = new ReMsg("You must enter a LayoutName");
	public static final ReMsg INPUT_COPYBOOK   = new ReMsg("You must enter a input copybook");
	public final static ReMsg ERROR_CONVERTING_COPYBOOK = new ReMsg("Error Converting Copybook:");
	public static final ReMsg FIELD_SELECTION   = new ReMsg("Field Selection: {0}");

	public static final ReMsg NOT_A_FILTER         = new ReMsg("The specified file was not a filter definition");
	public static final ReMsg NOT_A_SIMPLE_FILTER  = new ReMsg("Can not load a Group Filter to a simple filter");
	public static final ReMsg SHOULD_NOT_HAPEN     = new ReMsg("Internal error, should not get here");

	public static final ReMsg FILTER_LIMIT_REACHED  = new ReMsg("The Filter limit of {0} has been reached, do you wish to continue?");
	public static final ReMsg FILTER_LIMIT_EXCEEDED = new ReMsg("Filter limit of {0} exceeded; only the first {0} lines in the filtered view");

	public static final ReMsgId LANGUAGE_WARNING = new ReMsgId("TranslationStatus", "");

	public static final ReMsgId LANG_RESTART = new ReMsgId(
			"LangRestart",
			"The Language has been changed to {0}, you need to restart the RecordEditor, "
		  + "for the change to come into affect."
	);
}
