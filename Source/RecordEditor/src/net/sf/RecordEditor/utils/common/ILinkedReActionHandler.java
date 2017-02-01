package net.sf.RecordEditor.utils.common;

/**
 * Action handlers operate in a cooperative fashion;
 * each ActionHandler should either:<ul>
 * <li>handle the request
 * <li>pass it on to its parent
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
public interface ILinkedReActionHandler extends ReActionHandler {

	public void setParentActionHandler(ReActionHandler parentHandler);
}
