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
}
