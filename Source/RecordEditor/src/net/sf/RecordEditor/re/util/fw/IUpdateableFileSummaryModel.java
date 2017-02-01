package net.sf.RecordEditor.re.util.fw;

import net.sf.JRecord.External.ExternalRecord;
import net.sf.RecordEditor.re.util.csv.CharsetDetails;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection;

public interface IUpdateableFileSummaryModel extends FixedWidthFieldSelection.IFileSummaryModel {
	
	/**
	 * Set the File Details
	 * @param filename name of the files
	 * @param data file-data
	 * @param checkCharset wether to check the character-set 
	 * 
	 * @return 
	 */
	public boolean setData(String filename, byte[] data, boolean checkCharset);
	
	/**
	 * Get a modifiable schema (ExternalRecord) 
	 * @param name
	 * @param fontName
	 * @return
	 */
	public ExternalRecord asExtenalRecord(String name, String fontName);

	/**
	 * Get the character-set details (character set + list of likely character-sets)
	 * @return character-set details (character set + list of likely character-sets)
	 */
	public CharsetDetails getCharsetDetails();

	/**
	 * Set the font (character-set)
	 * @param font new font (character-set)
	 */
	public void setFontName(String font);

	
	/**
	 * check to see if a field name has been defined
	 * 
	 * @return wether use has entered a field name.
	 */
	boolean hasAFieldName();

}
