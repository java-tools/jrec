package net.sf.RecordEditor.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.utils.params.Parameters;

/**
 * Class to display File Chooser
 * @author Bruce Martin
 *
 */
public class FileChooserHelper implements ActionListener {

	protected JFileChooser chooseFile = null;
    protected final ArrayList<FocusListener> listner = new ArrayList<FocusListener>();
    private int mode = Constants.NULL_INTEGER;
    private String defaultDirectory = "";
    private boolean open = true;
    private final boolean isDirectory;
    private boolean expandVars = false;

    protected UpdatableTextValue txtC;


	public FileChooserHelper(UpdatableTextValue txtC, boolean open) {
		this(txtC, open, false);
		this.txtC = txtC;
		this.open = open;
	}


	public FileChooserHelper(UpdatableTextValue txtC, boolean open, boolean isDirectory) {
		super();
		this.txtC = txtC;
		this.open = open;
		this.isDirectory = isDirectory;
	}

	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {

		String s = txtC.getText();
		if (chooseFile == null) {
			chooseFile = new JFileChooser();

			if (isDirectory) {
				chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			}

			if ( mode != Constants.NULL_INTEGER) {
				chooseFile.setFileSelectionMode(mode);
			}
		}


		if ("".equals(s)) {
			txtC.setText(defaultDirectory);
			s = defaultDirectory;
		}

		if (expandVars) {
			s = Parameters.expandVars(s);
		}
		chooseFile.setSelectedFile(new File(s));

		int ret;

		if (open) {
			ret = chooseFile.showOpenDialog(null);
		} else {
			ret = chooseFile.showSaveDialog(null);
		}

		if (ret == JFileChooser.APPROVE_OPTION) {
			String newPath = chooseFile.getSelectedFile().getPath();
			//System.out.println("!! 1 " + newPath);
			if (expandVars) {
				newPath = Parameters.encodeVars(newPath);
			}
			//System.out.println("!! 2 " + newPath);

			txtC.setText(newPath);
		    for (FocusListener item : listner) {
		    	item.focusLost(null);
		    }
		}
	}

	/**
	 * @param mode
	 * @see javax.swing.JFileChooser#setFileSelectionMode(int)
	 */
	public void setFileSelectionMode(int newMode) {
		mode = newMode;
		if (chooseFile != null) {
			chooseFile.setFileSelectionMode(newMode);
		}
	}

	/**
	 * @param defaultDirectory the defaultDirectory to set
	 */
	public final void setDefaultDirectory(String defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
	}

	/**
	 * @param expandVars the expandVars to set
	 */
	public void setExpandVars(boolean expandVars) {
		this.expandVars = expandVars;
	}

}
