package net.sf.RecordEditor.re.script;


import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;


@SuppressWarnings("serial")
public class ExportScriptPopup extends FilePopup implements Runnable {

	private static FileItem[] fileList = null;
//	private static boolean doLayout = true;
	private final String dir;

	
	public ExportScriptPopup(String dir) {
		super("Export via Script");
		this.setIcon(Common.getReActionIcon(ReActionHandler.EXPORT_SCRIPT));
		this.dir = dir;
				
		// load the script options later to speedup program start up
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
							dir, 
							ReActionHandler.EXPORT_SCRIPT,
							"(Run Script)",
							new net.sf.RecordEditor.re.script.ScriptMgr());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static final ExportScriptPopup getPopup() {
		return new ExportScriptPopup(Common.OPTIONS.DEFAULT_SCRIPT_EXPORT_DIRECTORY.get());
	}
}
