package net.sf.RecordEditor.re.util.fw;

import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

public interface IFixedWidthView {

	/**
	 * Get the Panel to be displayed
	 * @return Panel to be displayed
	 */
	BaseHelpPanel getPanel();

	/**
	 * Get the Schema File name
	 * @return Schema File name
	 */
	String getSchemaFileName();

	/**
	 * Set the schema file name
	 * @param fileName new schema file name
	 */
	void setSchemaFileName(String fileName);

	/**
	 * Get the selected font name
	 * @return user selected font name
	 */
	String getFont();

	/**
	 * Get the Fields
	 * @return Record Fields
	 */
	ColumnDetails[] getFieldSelection();

	/**
	 * Tell the display to reload from the model
	 */
	void reloadFromFileModel();

	/**
	 * wether the schema should be saved to disk or not
	 * 
	 * @return if the schema should be saved to disk or not
	 */
	boolean isSaveSchemaSelected();

}