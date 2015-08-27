/**
 * 
 */
package net.sf.RecordEditor.utils.swing.common;

/**
 * 
 * A progress Display that does not do anything.
 * It can be used when you only some times want to use a progress display
 * 
 * @author Bruce Martin
 *
 */
public class EmptyProgressDisplay implements IProgressDisplay {



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.IProgressDisplay#updateProgress(int)
	 */
	@Override
	public boolean updateProgress(int count, int x) {
		return true;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.common.IProgressDisplay#done()
	 */
	@Override
	public void done() {

	}

}
