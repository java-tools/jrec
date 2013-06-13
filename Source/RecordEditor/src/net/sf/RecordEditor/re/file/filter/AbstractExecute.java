package net.sf.RecordEditor.re.file.filter;

import net.sf.RecordEditor.re.display.AbstractFileDisplay;

public interface AbstractExecute<details> {

	/**
	 * Execute action
	 * @param saveDetails details read in from a file
	 */
	public AbstractFileDisplay execute(details saveDetails);

	/**
	 * Execute action dialogue
	 * @param saveDetails details read in from a file
	 */
	public void executeDialog(details saveDetails);

}
