package net.sf.RecordEditor.re.display;

/**
 * Description (interface) for a class that will execute
 * saved File-Actions (i.e. Sort Trees).
 * It allows the Script classes access to the
 * Saved-Action tasks defined in the Edit packages
 *
 * @author Bruce Martin
 *
 */
public interface IExecuteSaveAction {

	/**
	 * Execute FilterDialogue with saved filter
	 */
	public abstract void executeSavedFilterDialog();

	/**
	 * Execute Sort-Tree-Dialogue with saved Sort Tree
	 */
	public abstract void executeSavedSortTreeDialog();

	/**
	 * Execute Record-Tree-Dialogue with saved Record Tree
	 */
	public abstract void executeSavedRecordTreeDialog();

	/**
	 * Execute a saved task
	 * @param fileName saved-task to execute.
	 */
	public abstract AbstractFileDisplay executeSavedTask(String fileName);
}