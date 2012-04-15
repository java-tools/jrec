package net.sf.RecordEditor.re.script;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;


@SuppressWarnings("serial")
public class VelocityPopup extends FilePopup {

	private static FileItem[] fileList = null;
//	private static boolean doLayout = true;
	
	public VelocityPopup(String dir) {
		super("Export via Velociy Skelton");
		this.setIcon(Common.getReActionIcon(ReActionHandler.EXPORT_VELOCITY));
		
		fileList = getActions(
						fileList, 
						dir, 
						ReActionHandler.EXPORT_VELOCITY,
						"(Velocity)",
						null);
	}


	public static final VelocityPopup getPopup() {
		VelocityPopup ret = null;
		if (Common.isVelocityAvailable()) {
			ret = new VelocityPopup(Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get());
		}
		return ret;
	}
	
	public static final VelocityPopup getLayoutPopup() {
		VelocityPopup ret = null;
		if (Common.isVelocityAvailable()) {
			ret = new VelocityPopup(Common.OPTIONS.copybookVelocityDirectory.get());
		}
		return ret;
	}
}
