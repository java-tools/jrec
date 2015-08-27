package net.sf.RecordEditor.re.openFile;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

/**
 * Create Csv Schema (or layout) based on user input
 * 
 * @author Bruce Martin
 *
 */
public abstract class LayoutSelectionBasicGenerated extends AbstractLayoutSelection  {

	protected static final String SEPERATOR = "~";


	protected JTextArea message = null;



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#addLayoutSelection(net.sf.RecordEditor.utils.swing.BasePanel, net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect, javax.swing.JPanel, javax.swing.JButton, javax.swing.JButton)
	 */
	@Override
	public void addLayoutSelection(BasePanel pnl, TreeComboFileSelect file,
			JPanel goPanel, JButton layoutCreate1, JButton layoutCreate2) {
		addLayoutSelection(pnl, goPanel, layoutCreate1, layoutCreate2, null);
	}

	/**
	 * Add Selection with external layout file
	 * @param pnl panel to add fields to
	 * @param goPanel go panel to use
	 * @param layoutFile layout file
	 */
	public void addLayoutSelection(BasePanel pnl, JPanel goPanel, 	FileSelectCombo layoutFile) {

		addLayoutSelection(pnl, goPanel, null, null, layoutFile);
	}

	/**
	 * Add layout Selection to panel
	 * @param pnl panel to be updated
	 * @param goPanel panel holding go button
	 * @param layoutCreate1 layout create button1
	 * @param layoutCreate2  layout create button2
	 * @param layoutFile layout file
	 */
	protected abstract void addLayoutSelection(BasePanel pnl, JPanel goPanel,
			JButton layoutCreate1, JButton layoutCreate2, FileSelectCombo layoutFile);


	@Override
	public final void forceLayoutReload() {
	}


	/**
	 * Get Layout details
	 * @param layoutName record layout name
	 * @return record layout
	 */
	@Override
	public final AbstractLayoutDetails getRecordLayout(String layoutName, String fileName) {
		setLayoutName(layoutName);
		return getRecordLayout(fileName);
	}





	@Override
	public final void reload() {

	}

	@Override
	public final boolean setLayoutName(String layoutName) {
		return false;
	}



	/**
	 * @param message the message to set
	 */
	public final void setMessage(JTextArea message) {
		this.message = message;
	}


	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDataBaseNames()
	 */
	@Override
	public final String[] getDataBaseNames() {
		return null;
	}


	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#setDatabaseIdx(int)
	 */
	@Override
	public final void setDatabaseIdx(int idx) {

	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDatabaseIdx()
	 */
	@Override
	public final int getDatabaseIdx() {
		return 0;
	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#getDatabaseName()
	 */
	@Override
	public final String getDatabaseName() {
		return null;
	}

	/**
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#formatLayoutName(java.lang.String)
	 */
	@Override
	public final String formatLayoutName(String layoutName) {
		return layoutName;
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#isFileBasedLayout()
	 */
	@Override
	public final boolean isFileBasedLayout() {
		return false;
	}
}
