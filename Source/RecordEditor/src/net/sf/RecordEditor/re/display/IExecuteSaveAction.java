package net.sf.RecordEditor.re.display;

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