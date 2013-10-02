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

import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;



/**
 * implement file chooser Text box & button
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class FileChooser extends JTextField implements UpdatableTextValue  {
    private JButton chooseFileButton;
//    private JFileChooser chooseFile = null;
//    private ArrayList<FocusListener> listner = new ArrayList<FocusListener>();
//    private int mode = Constants.NULL_INTEGER;
//    private String defaultDirectory = "";
//    private boolean open = true;
//    private boolean expandVars = false;

    private final FileChooserHelper fChoose;

    /**
     * Create File chooser code
     *
     */
    public FileChooser() {
        this(true, "Choose File");
    }

    public FileChooser(final boolean isOpen) {
        this(isOpen, "Choose File");
    }

    /**
     * Create File chooser
     * @param buttonPrompt prompt to display on the button
     */
    public FileChooser(final boolean isOpen, final String buttonPrompt) {
        super();

        fChoose = new FileChooserHelper(this, isOpen);

        chooseFileButton  = SwingUtils.newButton(
        		buttonPrompt,
        		Common.getRecordIcon(Common.ID_FILE_SEARCH_ICON));
        chooseFileButton.addActionListener(fChoose);
    }


    /**
     * @return Returns the chooseFileButton.
     */
    public JButton getChooseFileButton() {
        return chooseFileButton;
    }




    /**
     * Special file chooser Focus listner.
     * @param fcListner focus listner
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void addFcFocusListener(FocusListener fcListner) {

       	super.addFocusListener(fcListner);
       	fChoose.listner.add(fcListner);
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
		fChoose.setFileSelectionMode(newMode);

		if (newMode == JFileChooser.DIRECTORIES_ONLY) {
			chooseFileButton.setIcon(Common.getRecordIcon(Common.ID_DIRECTORY_SEARCH_ICON));
		} else {
			chooseFileButton.setIcon(Common.getRecordIcon(Common.ID_FILE_SEARCH_ICON));
		}
	}

	/**
	 * @param defaultDirectory the defaultDirectory to set
	 */
	public final void setDefaultDirectory(String defaultDirectory) {
		fChoose.setDefaultDirectory(defaultDirectory);
	}

	/**
	 * @param expandVars the expandVars to set
	 */
	public void setExpandVars(boolean expandVars) {
		fChoose.setExpandVars(true);
	}
}
