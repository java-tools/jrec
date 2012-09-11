package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.util.ChangeLayout;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;

@SuppressWarnings("serial")
public class ChangeLayoutAction extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	private AbstractLayoutSelectCreator<?> creator;


	/**
	 * @param creator
	 */
	public ChangeLayoutAction(AbstractLayoutSelectCreator<?> layoutSelectionCreator) {
		super("Change Layout");
		this.creator = layoutSelectionCreator;
		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(getDisplay(AbstractFileDisplay.class) != null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFileDisplay sourcePnl = getDisplay(AbstractFileDisplay.class);
		if (sourcePnl != null) {
			new ChangeLayout(creator.create(), sourcePnl.getFileView());
		}
	}
}
