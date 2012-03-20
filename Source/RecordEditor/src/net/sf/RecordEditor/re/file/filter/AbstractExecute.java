package net.sf.RecordEditor.re.util.filter;

public interface AbstractExecute<details> {

	/**
	 * Execute action
	 * @param saveDetails details read in from a file
	 */
	public void execute(details saveDetails);
	
	/**
	 * Execute action dialogue
	 * @param saveDetails details read in from a file
	 */
	public void executeDialog(details saveDetails);

}
