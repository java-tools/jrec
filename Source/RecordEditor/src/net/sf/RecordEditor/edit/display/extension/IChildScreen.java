package net.sf.RecordEditor.edit.display.extension;

import net.sf.RecordEditor.re.script.AbstractFileDisplay;

public interface IChildScreen extends AbstractFileDisplay {

	/**
	 * Set the current display row
	 *
	 * @param newRow new row to be displayed
	 */
	public abstract void setCurrRow(int newRow);

	public abstract void flush();

}