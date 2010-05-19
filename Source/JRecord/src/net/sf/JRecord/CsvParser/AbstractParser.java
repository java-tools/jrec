/*
 * @Author Bruce Martin
 * Created on 13/04/2007
 *
 * Purpose:
 */
package net.sf.JRecord.CsvParser;

/**
 * Interface describing a CSV line parser - 
 * A class to break a <b>line</b> into the fields using a field-Seperator String
 *
 * @author Bruce Martin
 *
 */
public interface AbstractParser {
	
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
 	 * @param delimiter field delimitier
	 * @param quote quote char
     * @param newValue new value of the field
     * @return updated line
     */
    public abstract String setField(int fieldNumber, int fieldType, String line, String delimiter, String quote, String newValue);
}
