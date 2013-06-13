package net.sf.RecordEditor.re.openFile;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.utils.BasicLayoutCallback;
import net.sf.RecordEditor.utils.swing.SwingUtils;



public class RecentFilesList implements ChangeListener {

	private JMenu menu = SwingUtils.newMenu("Recent Files");
	private RecentFiles recentFiles;
	private BasicLayoutCallback connection;
	private FileAction[] actions = new FileAction[RecentFiles.RECENT_FILE_LIST];

	/**
	 * @param recentfiles recent files details
	 * @param callback callback function;
	 */
	public RecentFilesList(RecentFiles recentfiles, BasicLayoutCallback callback) {
		this.recentFiles = recentfiles;
		this.connection = callback;

		recentFiles.addChangeListner(this);

		stateChanged(null);
	}


	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		String s;
		for (int i = 0; i < RecentFiles.RECENT_FILE_LIST; i++) {
			s = recentFiles.getRecentFileName(i);
			if (! "".equals(s)) {
				if (actions[i] == null) {
					actions[i] = new FileAction(i, s);
					menu.add(new JMenuItem(actions[i]));
				} else {
					actions[i].putValue("Name", s);
				}
			}
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


}
