package net.sf.RecordEditor.re.script;

import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;

/**
 * Interface to edit a CSV file; It is used in the 
 * ScriptData class (for use in scripts)
 * 
 * @author Bruce Martin
 *
 */

public interface IOpenCsvFile {

	public abstract AbstractFileDisplayWithFieldHide open(String fileName, String font, String delim,
			String quote, boolean embededCr);

}