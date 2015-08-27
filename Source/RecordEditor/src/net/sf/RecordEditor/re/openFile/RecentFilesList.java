package net.sf.RecordEditor.re.openFile;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashSet;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.SwingUtils;



public class RecentFilesList implements ChangeListener {

	private final boolean generateDirMenu;
	private JMenu menu = SwingUtils.newMenu("Recent Files");
	private JMenu directoryMenu = null;
	private RecentFiles recentFiles;
	private BasicLayoutCallback connection;
	private FileAction[] actions = new FileAction[RecentFiles.RECENT_FILE_LIST];
	private DirectoryAction[] dirActions = null;

	/**
	 * @param recentfiles recent files details
	 * @param callback callback function;
	 */
	public RecentFilesList(RecentFiles recentfiles, BasicLayoutCallback callback) {
		this(recentfiles, callback, false);
	}

	/**
	 * @param recentfiles recent files details
	 * @param callback callback function;
	 */
	public RecentFilesList(RecentFiles recentfiles, BasicLayoutCallback callback, boolean generateDirectoryMenu) {

		this.recentFiles = recentfiles;
		this.connection = callback;
		this.generateDirMenu = generateDirectoryMenu;

		recentFiles.addChangeListner(this);
		
		if (generateDirectoryMenu) {
			directoryMenu = SwingUtils.newMenu("Recent Directories");
			dirActions = new DirectoryAction[RecentFiles.RECENT_FILE_LIST];
		}

		stateChanged(null);
	}


	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		String s;
		boolean changed;
		
		if (generateDirMenu) {
			HashSet<String> usedDirs = new HashSet<String>();
			int j = 0;
			changed = false;
			for (int i = 0; i < RecentFiles.RECENT_FILE_LIST; i++) {
				s = new File(recentFiles.getRecentFullFileName(i)).getParent();
				if (s ==null || "".equals(s) || usedDirs.contains(s)) {
					
				} else {
					if (dirActions[j] == null) {				
						dirActions[j] = new DirectoryAction(s);
						directoryMenu.add(new JMenuItem(dirActions[i]));
						changed = true;
					} else {
						dirActions[j].putValue("Name", s);
						dirActions[j].dir = s;
					}
					j += 1;
					usedDirs.add(s);
				}
			}
			
			if (changed && Common.NIMBUS_LAF) {
				directoryMenu.repaint();
			}
		} 
		
		changed = false;
		for (int i = 0; i < RecentFiles.RECENT_FILE_LIST; i++) {
			s = recentFiles.getRecentFileName(i);
			if (s != null && ! "".equals(s)) {
				if (actions[i] == null) {
					actions[i] = new FileAction(i, s);
					menu.add(new JMenuItem(actions[i]));
					changed = true;
				} else {
					actions[i].putValue("Name", s);
				}
			}
		}		
		if (changed && Common.NIMBUS_LAF) {
			menu.repaint();
		}

	}

//
//	public void update() {
//		String s;
//		for (int i = 0; i < RecentFiles.RECENT_FILE_LIST; i++) {
//			s = recentFiles.getRecentFileName(i);
//			if (! "".equals(s)) {
//				if (actions[i] == null) {
//					actions[i] = new FileAction(i, s);
//					menu.add(new JMenuItem(actions[i]));
//				} else {
//					actions[i].putValue("Name", s);
//				}
//			}
//		}
//	}

	/**
	 * @return the menu
	 */
	public final JMenu getMenu() {
		return menu;
	}

	/**
	 * @return the menu
	 */
	public final JMenu getDirectoryMenu() {
		return directoryMenu;
	}

    /**
	 * @return the recentFiles
	 */
	public RecentFiles getRecentFiles() {
		return recentFiles;
	}

	@SuppressWarnings("serial")
	private class FileAction extends AbstractAction {
        private int id;

        /**
         * Create Window action
         * @param label label to display
         * @param frame frame to be displayed if need be
         */
        public FileAction(int idx, String s) {
            super(s);
            id = idx;
        }


        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {

            try {
            	connection.setRecordLayout(-1, recentFiles.getRecentLayoutName(id), recentFiles.getRecentFullFileName(id));
            } catch (Exception ex) { }

        }

    }


	@SuppressWarnings("serial")
	private class DirectoryAction extends AbstractAction {
        private String dir;

        /**
         * Create Window action
         * @param label label to display
         * @param frame frame to be displayed if need be
         */
        public DirectoryAction( String dir) {
            super(dir);

            this.dir = dir;
        }


        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {

            try {
            	connection.setRecordLayout(-1, null, dir);
            } catch (Exception ex) { }

        }

    }

}
