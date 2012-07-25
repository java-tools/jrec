package net.sf.RecordEditor.utils.msg;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class UtMessages {

	public static final ReMsg SAMPLE_FILE_MSG  = new ReMsg("You must enter a sample file");
	public static final ReMsg LAYOUT_MSG       = new ReMsg("You must enter a LayoutName");
	public static final ReMsg INPUT_COPYBOOK   = new ReMsg("You must enter a input copybook");
	public final static ReMsg ERROR_CONVERTING_COPYBOOK = new ReMsg("Error Converting Copybook:");


	public static final ReMsgId LANGUAGE_WARNING = new ReMsgId("TranslationStatus", "");

	public static final ReMsgId LANG_RESTART = new ReMsgId(
			"LangRestart",
			"The Language has been changed to {0}, you need to restart the RecordEditor, "
		  + "for the change to come into affect."
	);
}
