package net.sf.RecordEditor.utils.csv;

import javax.swing.JButton;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

public interface FilePreview {

	public static final String SEP = "~";
	public static final String NULL_STR = "Empty";
	
	public abstract BaseHelpPanel getPanel();
	public abstract JButton getGoButton();

	public abstract boolean setData(byte[] data, boolean checkCharset);

	/**
	 * Get The field seperator 
	 * @return field seperator 
	 */
	public abstract String getSeperator();

	/**
	 * Get The Quote
	 * @return Quote
	 */
	public abstract String getQuote();

	/**
	 * @param newLines the lines to set
	 * @param numberOfLines the actual number of lines used
	 */
	public abstract boolean setLines(byte[][] newLines, String font,
			int numberOfLines);

	/**
	 * @param newLines the lines to set
	 * @param numberOfLines the actual number of lines used
	 */
	public abstract void setLines(String[] newLines, String font,
			int numberOfLines);

	/**
	 * @return column count
	 * @see net.sf.RecordEditor.utils.csv.CsvSelectionTblMdl#getColumnCount()
	 */
	public abstract int getColumnCount();

	/**
	 * @return the Column Name
	 */
	public abstract String getColumnName(int idx);

	public abstract LayoutDetail getLayout(String font, byte[] recordSep);

	public abstract String getFileDescription();

	public abstract void setFileDescription(String val);

	public abstract String getFontName();

}