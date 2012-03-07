package net.sf.RecordEditor.utils;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;


@SuppressWarnings("serial")
public class VelocityPopup extends FilePopup {

	private static FileItem[] fileList = null;
//	private static boolean doLayout = true;
	
	public VelocityPopup() {
		super("Save via Velociy Skelton");
		this.setIcon(Common.getReActionIcon(ReActionHandler.SAVE_AS_VELOCITY));
		
		fileList = getActions(
						fileList, 
						Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get(), 
						ReActionHandler.SAVE_AS_VELOCITY,
						null);
	}


	public static final VelocityPopup getPopup() {
		VelocityPopup ret = null;
		if (Common.isVelocityAvailable()) {
			ret = new VelocityPopup();
		}
		return ret;
	}
}
