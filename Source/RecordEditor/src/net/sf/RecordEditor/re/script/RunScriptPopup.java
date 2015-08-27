package net.sf.RecordEditor.re.script;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


@SuppressWarnings("serial")
public class RunScriptPopup extends FilePopup implements Runnable {

	private static FileItem[] fileList = null;

	private final String dir;


	public RunScriptPopup(String dir) {
		super("Run Script");

		this.setIcon(Common.getReActionIcon(ReActionHandler.RUN_SCRIPT));
		this.dir = dir;

		// load the script options later to speedup program start up
		javax.swing.SwingUtilities.invokeLater(this);
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		fileList = getActions(
						fileList,
						dir,
						ReActionHandler.RUN_SCRIPT,
						null,
						new net.sf.RecordEditor.re.script.ScriptMgr());
	}



	/**
	 * @see net.sf.RecordEditor.re.script.FilePopup#getAction(int, java.lang.String, java.lang.String)
	 */
	@Override
	protected AbstractAction getAction(int actionId, String filename,
			String filePathName) {
		return new RunScript(filename, filePathName);
	}



	public static final RunScriptPopup getPopup() {
		return new RunScriptPopup(Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.getNoStar());
	}

	private static class RunScript extends AbstractAction {
		private final String filePathName;

		public RunScript(final String name, final String filePathName) {
			super(name);
			this.filePathName = filePathName;

//			System.out.println("~~> " + name + "\t " + filePathName);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {


			try {
				ScriptData  data = ScriptData.getScriptData( ReFrame.getActiveFrame(), filePathName);

				(new net.sf.RecordEditor.re.script.ScriptMgr()).runScript(filePathName, data);
			} catch (Exception ex) {
				Common.logMsg(
						AbsSSLogger.ERROR,
						"Script execution failed !!!",
						ex.getClass().getName() + " " + ex.getMessage(),
						ex);
			}
		}

	}
}
