/**
 *
 */
package net.sf.RecordEditor.utils.common;

/**
 * @author Bruce Martin
 *
 */
public class DefaultActionHandler implements ReActionHandler {

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int, java.lang.Object)
	 */
	@Override
	public void executeAction(int action, Object o) {
		executeAction(action);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		return false;
	}

}
