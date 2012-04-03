package net.sf.RecordEditor.re.openFile;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.RecordEditor.utils.swing.BasePanel;

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
public abstract class AbstractLayoutSelection<Layout extends AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail>> 
extends ReadLayout implements FormatFileName {

	
	/**
	 * This method adds layout selection fields to the panel
	 *
	 * @param pnl panel to add the fields to
	 * @param file file to which
	 * @param goPanel edit browse etc panel
	 * @param layoutCreate1 layout create button 1
	 * @param layoutCreate2 layout create button 2
	 */
	public abstract void addLayoutSelection(BasePanel pnl, JTextField file, JPanel goPanel,
			final JButton layoutCreate1, final JButton layoutCreate2);

	public void setFileNameField(JTextField fileNameField) {
		
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
	 * Get the layout Name
	 * @return layout name
	 */
	public abstract String getLayoutName();
	
	/**
	 * retrieve the layout
	 * @return the layout
	 */
	public abstract Layout getRecordLayout(String fileName);

	/**
	 * retrieve the layout
	 * @return the layout
	 */
	public abstract Layout getRecordLayout(String name, String fileName);

	/**
	 * set the message field
	 * @param message
	 */
	public abstract	void setMessage(JTextArea message);
	
	public abstract boolean isFileBasedLayout();
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.openFile.FormatFileName#formatLayoutName(java.lang.String)
	 */
	public String formatLayoutName(String layoutName) {
		return layoutName;
	}
}