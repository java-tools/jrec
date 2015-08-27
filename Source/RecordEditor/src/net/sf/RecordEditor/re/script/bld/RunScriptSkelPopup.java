package net.sf.RecordEditor.re.script.bld;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenu;

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.DisplayDetails;
import net.sf.RecordEditor.re.script.FilePopup;
import net.sf.RecordEditor.re.script.IEditor;
import net.sf.RecordEditor.re.script.ScriptMgr;
import net.sf.RecordEditor.re.script.ValidExtensionCheck;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;


@SuppressWarnings("serial")
public class RunScriptSkelPopup extends FilePopup implements Runnable {

	//private static FileItem[] fileList = null;
	private static ValidExtensionCheck checkExtension = new ValidExtensionCheck() {
		@Override public boolean isValidExtension(String extension) {
			return extension != null && extension.equalsIgnoreCase("vm");
		}
	};

	private final String dir, extension;
	private final IEditor editor;


	public RunScriptSkelPopup(IEditor editor, String dir, String name, String extension, boolean runInBackground) {
		super(name);

//		this.setIcon(Common.getReActionIcon(ReActionHandler.RUN_SCRIPT));
		this.dir = dir;
		this.editor = editor;
		this.extension = extension;

		// load the script options later to speedup program start up
		if (runInBackground) {
			javax.swing.SwingUtilities.invokeLater(this);
		} else {
			run();
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			getActions(
						null,
						dir,
						ReActionHandler.RUN_SCRIPT,
						null,
						checkExtension);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * @see net.sf.RecordEditor.re.script.FilePopup#getAction(int, java.lang.String, java.lang.String)
	 */
	@Override
	protected AbstractAction getAction(int actionId, String filename,
			String filePathName) {
		String s = filename;
		if (s != null && s.toLowerCase().endsWith(".vm")) {
			s = s.substring(0, s.length() - 3);
		}
		return new GenSkel(s, filePathName);
	}



//	public static final RunScriptSkelPopup getPopup() {
//		return new RunScriptSkelPopup(Common.OPTIONS.DEFAULT_SCRIPT_DIRECTORY.get());
//	}

	private class GenSkel extends AbstractAction {
		private final String filePathName, name;

		public GenSkel(final String name, final String filePathName) {
			super(name);
			this.name = name;
			this.filePathName = filePathName;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ReFrame activeFrame = ReFrame.getActiveFrame();
				AbstractFileDisplay disp = DisplayDetails.getDisplayDetails(activeFrame);
				if (disp != null) {
					new ScriptBld(activeFrame, disp, editor, filePathName, name, extension);
				}
				//editor.setFileName(filePathName);
			} catch (Exception ex) {
				Common.logMsg(
						AbsSSLogger.ERROR,
						"Script execution failed !!!",
						ex.getClass().getName() + " " + ex.getMessage(),
						ex);
				ex.printStackTrace();
			}
		}
	}
	

	public static JMenu buildScriptMenu(IEditor ed, String name) {
		JMenu buildMenu = null;
		String velocityDir = Common.OPTIONS.velocityScriptDir.getNoStar();
		File velocityDirFile = new File(velocityDir);
		if (velocityDirFile.exists() && velocityDirFile.isDirectory()) {
			File[] files = velocityDirFile.listFiles();
			ArrayList<File> dirFiles = new ArrayList<File>();
			for (File f : files) {
				if (f.isDirectory()) {
					dirFiles.add(f);
				}
			}
			if (dirFiles.size() == 1) {
				File vmDir = dirFiles.get(0);
				buildMenu = new RunScriptSkelPopup(ed, vmDir.getPath(), name, ScriptMgr.getExtension(vmDir.getName()), true);
			} else if (dirFiles.size() > 1){
				buildMenu = new JMenu(name);
				javax.swing.SwingUtilities.invokeLater(new BldSubMenus(buildMenu, dirFiles, ed) );
			}
		}
		return buildMenu;
	}

	/**
	 * Build the script menu's in the background
	 * 
	 * @author Bruce Martin
	 *
	 */
	public static class BldSubMenus implements Runnable {
		final JMenu buildMenu;
		final ArrayList<File> dirFiles;
		final IEditor ed;

		public BldSubMenus(JMenu buildMenu, ArrayList<File> dirFiles, IEditor ed) {
			super();
			this.buildMenu = buildMenu;
			this.dirFiles = dirFiles;
			this.ed = ed;
		}

		@Override
		public void run() {
			
			for (File dirs : dirFiles) {
				File[] files = dirs.listFiles();
				boolean addMenu = false;
				
				for (File f : files) {
					if (f.isDirectory() || checkExtension.isValidExtension(f.getName())) {
						addMenu = true;
						break;
					}
				}
				
				if (addMenu) {
					buildMenu.add(new RunScriptSkelPopup(ed, dirs.getPath(), dirs.getName(), ScriptMgr.getExtension(dirs.getName()), false));
				}
			}
		}
		
	}
}
