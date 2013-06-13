package net.sf.RecordEditor.re.display;

import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

public interface IDisplayFrame<BD extends AbstractFileDisplay> extends ReActionHandler {

	public abstract void addScreen(BD d);

	public abstract void close(AbstractFileDisplay d);

	public abstract void reClose();


	public abstract void setToActiveTab(AbstractFileDisplay pnl);

	public abstract void setToActiveFrame();

	public abstract void bldScreen();

	public abstract AbstractFileDisplay getActiveDisplay();

	/**
	 * The number of active screens
	 * @return number of active screens
	 */
	public abstract int getScreenCount();

	public abstract void moveToSeperateScreen(AbstractFileDisplay pnl);

	/**
	 * Get the index of a panel
	 * @param pnl panel to search for
	 * @return index of a panel
	 */
	public abstract int indexOf(AbstractFileDisplay pnl);

	public abstract boolean isActionAvailable(int idx, int action);

	public abstract void executeAction(int idx, int action);

	public abstract ReFrame getReFrame();

	public abstract void moveToFront();
}