/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * implement file chooser Text box & button
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Added Focus Listner (addFcFocusListener) and Enabled methods
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Constants;

/**
 * implement file chooser Text box & button
 *
 * @author Bruce Martin
 *
 */
public class FileChooser extends JTextField implements ActionListener  {
    private JButton chooseFileButton;
    private JFileChooser chooseFile = null;
    private FocusListener listner;
    private int mode = Constants.NULL_INTEGER;
    private String defaultDirectory = "";

    /**
     * Create File chooser code
     *
     */
    public FileChooser() {
        this("Choose File"); 
    }

    /**
     * Create File chooser
     * @param buttonPrompt prompt to display on the button
     */
    public FileChooser(final String buttonPrompt) {
        super();

        chooseFileButton  = new JButton(buttonPrompt);
        chooseFileButton.addActionListener(this);
    }


    /**
     * @return Returns the chooseFileButton.
     */
    public JButton getChooseFileButton() {
        return chooseFileButton;
    }


	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {
		if (chooseFile == null) {
			chooseFile = new JFileChooser();
			
			if ( mode != Constants.NULL_INTEGER) {
				chooseFile.setFileSelectionMode(mode);
			}
		}

		if ("".equals(this.getText())) {
			this.setText(defaultDirectory);
		}
		chooseFile.setSelectedFile(new File(this.getText()));
		int ret = chooseFile.showOpenDialog(null);

		if (ret == JFileChooser.APPROVE_OPTION) {
		    this.setText(chooseFile.getSelectedFile().getPath());
		    if (listner != null) {
		        listner.focusLost(null);
		    }
		}
	}


    /**
     * Special file chooser Focus listner.
     * @param fcListner focus listner
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void addFcFocusListener(FocusListener fcListner) {

        super.addFocusListener(fcListner);
        listner = fcListner;
    }


    /**
     * @see java.awt.Component#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        chooseFileButton.setEnabled(enabled);
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
}
