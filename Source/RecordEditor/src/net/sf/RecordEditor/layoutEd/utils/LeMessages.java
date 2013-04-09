package net.sf.RecordEditor.layoutEd.utils;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class LeMessages {

	public final static ReMsg ERROR_LOADING_COPYBOOK = new ReMsg("Could not load Copybook");
	public final static ReMsg ERROR_UPDATING_TABLE   = new ReMsg("Error Table:");
	public final static ReMsg DELETE_LAYOUT   = new ReMsg(" --> Deleting Record Layout: {0}");
	public final static ReMsg ADD_LAYOUT      = new ReMsg(" --> Adding Record Layout: {0}");


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

}
