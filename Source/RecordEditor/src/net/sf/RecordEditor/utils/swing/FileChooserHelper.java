package net.sf.RecordEditor.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.Constants;
import net.sf.RecordEditor.utils.params.Parameters;

public class FileChooserHelper implements ActionListener {

	protected JFileChooser chooseFile = null;
    protected final ArrayList<FocusListener> listner = new ArrayList<FocusListener>();
    private int mode = Constants.NULL_INTEGER;
    private String defaultDirectory = "";
    private boolean open = true;
    private boolean expandVars = false;

    protected JTextComponent txtC;


	public FileChooserHelper(JTextComponent txtC, boolean open) {
		super();
		this.txtC = txtC;
		this.open = open;
	}


	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {

		String s = txtC.getText();
		if (chooseFile == null) {
			chooseFile = new JFileChooser();

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
