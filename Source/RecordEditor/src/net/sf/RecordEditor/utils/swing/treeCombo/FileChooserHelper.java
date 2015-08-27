package net.sf.RecordEditor.utils.swing.treeCombo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.UpdatableTextValue;
import net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper;
import net.sf.RecordEditor.utils.swing.filechooser.JRFileChooserWrapper;

/**
 * Class to display File Chooser
 * @author Bruce Martin
 *
 */
public class FileChooserHelper implements ActionListener {

	//private JFileChooser chooseFile = null;
	private final IFileChooserWrapper chooseFile;
    public final ArrayList<FocusListener> listner = new ArrayList<FocusListener>();
 //   private int mode = Constants.NULL_INTEGER;
    private String defaultDirectory = "";
    private boolean open = true;
    public final boolean isDirectory;
    private boolean expandVars = false;

    protected UpdatableTextValue txtC;


	public FileChooserHelper(UpdatableTextValue txtC, boolean open, File[] recentFiles) {
		this(txtC, open, false, recentFiles);
	}


	public FileChooserHelper(UpdatableTextValue txtC, boolean open, boolean isDirectory, File[] recentFiles) {
		super();
		this.txtC = txtC;
		this.open = open;
		this.isDirectory = isDirectory;
		this.chooseFile = JRFileChooserWrapper.newChooser(null, recentFiles);
	}
	
	public FileChooserHelper(UpdatableTextValue txtC, boolean open, boolean isDirectory, IFileChooserWrapper chooseFile) {
		super();
		this.txtC = txtC;
		this.open = open;
		this.isDirectory = isDirectory;
		this.chooseFile = chooseFile;
	}


	/**
	 * @see java.awt.Event.actionPerformed
	 */
	public void actionPerformed(final ActionEvent event) {

		String s = txtC.getText();
		JFileChooser fc = getFileChooser();
		boolean setText = false;

		if (s == null || "".equals(s)) {
			setText = true;
			s = defaultDirectory;
		}

		if (expandVars) {
			s = Parameters.expandVars(s);
		}
		if (s != null) {
			System.out.println(" == Dir ==> " + s);
			if (setText) {
				txtC.setText(defaultDirectory);
			}
			fc.setSelectedFile(new File(s));
		}
		
		int mode = JFileChooser.FILES_ONLY;
		if (isDirectory) {
			mode = JFileChooser.DIRECTORIES_ONLY;
		}
		fc.setFileSelectionMode(mode);

		int ret = chooseFile.showDialog(open, null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			String newPath = chooseFile.getFileChooser().getSelectedFile().getPath();
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
	
	public final JFileChooser getFileChooser() {
		
//		if (chooseFile == null) {
//			chooseFile = new JFileChooser();
//
//			if (isDirectory) {
//				chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//			}
//
//			if ( mode != Constants.NULL_INTEGER) {
//				chooseFile.setFileSelectionMode(mode);
//			}
//		}

		return chooseFile.getFileChooser();
	}

	/**
	 * @param mode
	 * @see javax.swing.JFileChooser#setFileSelectionMode(int)
	 */
	public void setFileSelectionMode(int newMode) {
//		mode = newMode;
//		if (chooseFile != null) {
			chooseFile.getFileChooser().setFileSelectionMode(newMode);
//		}
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
