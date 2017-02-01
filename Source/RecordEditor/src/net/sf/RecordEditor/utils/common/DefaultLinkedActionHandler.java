package net.sf.RecordEditor.utils.common;

public class DefaultLinkedActionHandler implements ILinkedReActionHandler {

	protected ReActionHandler parent;

	@Override
	public void executeAction(int action, Object o) {
		if (isActionHandledHere(action)) {
			executeAction(action);
			return;
		}
		parent.executeAction(action, o);
	}

	@Override
	public void executeAction(int action) {
		parent.executeAction(action);
	}


	@Override
	public final boolean isActionAvailable(int action) {
		return isActionHandledHere(action) || parent.isActionAvailable(action);
	}
	
	protected boolean isActionHandledHere(int action) {
		return false;
	}

	@Override
	public void setParentActionHandler(ReActionHandler parentHandler) {
		parent = parentHandler;
	}

}
