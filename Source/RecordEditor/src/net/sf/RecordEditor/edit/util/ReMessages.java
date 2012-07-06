package net.sf.RecordEditor.edit.util;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.screenManager.ReMsgId;

public class ReMessages {

	public static final ReMsg EDIT_OPTIONS      = new ReMsg("Edit Options");
	public static final ReMsg LINE_DELETE_CHECK = new ReMsg("Line Delete confirmation");
	public static final ReMsg LINE_DELETE_MSG   = new ReMsg("Do you want to delete the selected {0} lines ?");

	public static final ReMsg SAVE_CHANGES      = new ReMsg("Save changes");
	public static final ReMsg SAVE_CHANGES_FILE = new ReMsg("Save Changes to file: {0}");


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
}
