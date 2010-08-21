/*
 * @Author Bruce Martin
 * Created on 13/04/2007
 *
 * Purpose:
 */
package net.sf.JRecord.CsvParser;

import java.util.List;

/**
 * Interface describing a CSV line parser - 
 * A class to break a <b>line</b> into the fields using a field-Seperator String
 *
 * @author Bruce Martin
 *
 */
public interface AbstractParser {
	
	/**
	 * Controls wether Column names on the first line are in Quotes
	 * @return wether Column names on the first line are in Quotes
	 */
	public abstract boolean isQuoteInColumnNames();
	
	/**
	 * Extract a field from a string
	 * @param fieldNumber  field to retrieve
	 * @param line line to parse for fields
	 * @param delimiter field delimitier
	 * @param quote quote char
	 * @return requested field value
	 */
    public abstract String getField(int fieldNumber, String line, String delimiter, String quote);

    
    /**
     * Update the value of a field in a line
     * @param fieldNumber field to be updated
     * @param fieldType Type of Field (Text / Numeric / Date)
     * @param line line to update
 	 * @param delimiter field delimiter
	 * @param quote quote char
     * @param newValue new value of the field
     * @return updated line
     */
    public abstract String setField(int fieldNumber, int fieldType, String line, String delimiter, String quote, String newValue);
    
    /**
     * This method converts a Line into a list of column names
     * 
     * @param line line containing column names
     * @param delimiter field delimiter
     * @param quote quote to use
     * 
     * @return list of column names
     */
    public List<String> getColumnNames(String line, String delimiter, String quote);
    
    
    /**
     * This method converts a list of column names to a line to be written to the file
     * 
     * @param names column names
     * @param delim field delimiter
     * @param quote quote to use
     * 
     * @return column name line
     */
    public String getColumnNameLine(List<String> names, String delim, String quote);
}
