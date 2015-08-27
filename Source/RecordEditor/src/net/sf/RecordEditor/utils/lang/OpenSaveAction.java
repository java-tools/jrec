package net.sf.RecordEditor.utils.lang;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import net.sf.RecordEditor.utils.common.Common;


@SuppressWarnings("serial")
public abstract class OpenSaveAction extends ReAbstractAction {
	private final boolean open;
	private JFileChooser fileChooser;
	private final Component parent;

	public OpenSaveAction(Component parent, boolean open) {
		this(parent, open, getName(open));
	}


	public OpenSaveAction(Component parent, boolean open, String name) {
		super(name, getIcon(open));

		this.parent = parent;
		this.open = open;
	}

	private static String getName(boolean open) {
		String ret = "Save As";
		if (open) {
			ret = "Open";
		}

		return ret;
	}


	private static ImageIcon getIcon(boolean open) {
		ImageIcon ret = Common.getRecordIcon(Common.ID_SAVE_AS_ICON);
		if (open) {
			ret = Common.getRecordIcon(Common.ID_OPEN_ICON);
		}

		return ret;
	}

	/**
	 * @return the fileChoose
	 */
	private JFileChooser getFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
		}
		return fileChooser;
	}

	/**
	 * Set the initial file / directory
	 * @param f initial file
	 */
	public void setFileName(File f) {
		if (f != null) {
			getFileChooser().setSelectedFile(f);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		askUser(getDefaultFile());
	}
	
	public void askUser(File defaultFile) {
		setFileName(defaultFile);

		JFileChooser fc = getFileChooser();
		int ret;

		if (open) {
			ret = fc.showOpenDialog(parent);
		} else {
			ret = fc.showSaveDialog(parent);
		}

		if (ret == JFileChooser.APPROVE_OPTION) {
			processFile(fc.getSelectedFile());
		}
	}
	

	/**
	 * process the file selected by the user
	 * @param selectedFile file selected by the user
	 */
	public abstract File getDefaultFile();

	/**
	 * process the file selected by the user
	 * @param selectedFile file selected by the user
	 */
	public abstract void processFile(File selectedFile);
}
