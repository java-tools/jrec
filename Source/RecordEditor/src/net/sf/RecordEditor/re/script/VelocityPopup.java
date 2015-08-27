package net.sf.RecordEditor.re.script;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;


@SuppressWarnings("serial")
public class VelocityPopup extends FilePopup implements Runnable {

	private static FileItem[] fileList = null;
//	private static boolean doLayout = true;
	private final String velocityDir;
	
	public VelocityPopup(String velocityDirectory) {
		super("Export via Velociy Skelton");
		this.setIcon(Common.getReActionIcon(ReActionHandler.EXPORT_VELOCITY));
		this.velocityDir = velocityDirectory;
		javax.swing.SwingUtilities.invokeLater(this);
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	
		try {
			fileList = getActions(
							fileList, 
							velocityDir, 
							ReActionHandler.EXPORT_VELOCITY,
							"(Velocity)",
							null);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
