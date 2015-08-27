package net.sf.RecordEditor.utils.swing.common;

public interface IProgressDisplay {

	/**
	 * Update progress toward the max count
	 * @param count
	 */
	public abstract boolean updateProgress(int count, int other);

	/**
	 * Long running task has finished so shutting down the prograss display
	 */
	public abstract void done();

}