package net.sf.RecordEditor.re.openFile;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

/**
 *
 * @author Bruce Martin
 *
 * These class's will -
 * <ol>
 *  <li>Add RecordLayout Selection fields to panels
 *  <li>Get / Set Record names
 *  <li>Read a record Layout from external storage
 * </ol>
 *
 * Basically it holds everything necessary for the user to Select a
 * record layout from an external storage medium (be it Database or File
 * storage).
 *
 */
public abstract class AbstractLayoutSelection
extends ReadLayout implements FormatFileName, ISchemaProvider {

	private StartActionInterface executeAction = null;

	/**
	 * This method adds layout selection fields to the panel
	 *
	 * @param pnl panel to add the fields to
	 * @param file file to which
	 * @param goPanel edit browse etc panel
	 * @param layoutCreate1 layout create button 1
	 * @param layoutCreate2 layout create button 2
	 */
	public abstract void addLayoutSelection(BasePanel pnl, TreeComboFileSelect file, JPanel goPanel,
			final JButton layoutCreate1, final JButton layoutCreate2);

	public void setFileNameField(TreeComboFileSelect fileNameField) {

	}

	/**
	 * get the Database names
	 * @return Database names
	 */
	public abstract String[] getDataBaseNames();

	/**
	 * set the database index
	 * @param idx new index
	 */
	public abstract void setDatabaseIdx(int idx);


	/**
	 *
	 * @return Database index
	 */
	public abstract int getDatabaseIdx();

	/**
	 *
	 * @return Database name
	 */
	public abstract String getDatabaseName();


	/**
	 * Reload Database details
	 */
	public abstract void reload();


	/**
	 * Set the layout name
	 * @param layoutName new layout
	 *
	 * @return wether the layout was found
	 */
	public abstract boolean setLayoutName(String layoutName);

	/**
	 *
	 * @param newFileName
	 */
	public void notifyFileNameChanged(String newFileName) {

	}

	/**
	 * Get the layout Name
	 * @return layout name
	 */
	public abstract String getLayoutName();

	/**
	 * retrieve the layout
	 * @return the layout
	 */
	public abstract AbstractLayoutDetails getRecordLayout(String fileName);

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.openFile.ISchemaProvider#getRecordLayout(java.lang.String, java.lang.String)
	 */
	@Override
	public abstract AbstractLayoutDetails getRecordLayout(String name, String fileName);

	/**
	 * set the message field
	 * @param message
	 */
	public abstract	void setMessage(JTextArea message);

	public abstract boolean isFileBasedLayout();

	public void forceLayoutReload() {

	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.openFile.FormatFileName#formatLayoutName(java.lang.String)
	 */
	public String formatLayoutName(String layoutName) {
		return layoutName;
	}

	/**
	 * @param executeAction the executeAction to set
	 */
	public void setExecuteAction(StartActionInterface executeAction) {
		this.executeAction = executeAction;
	}

	/**
	 * @param pBrowse
	 * @see net.sf.RecordEditor.re.openFile.StartActionInterface#loadFile(boolean)
	 */
	public void loadFile(boolean pBrowse) {
		if (executeAction == null) {
			Common.logMsgRaw("", null);
		} else {
			executeAction.loadFile(pBrowse);
		}
	}
}