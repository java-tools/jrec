package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.edit.display.util.HideFields;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class VisibilityAction extends AbstractAction implements AbstractActiveScreenAction {

	/**
	 * @param creator
	 */
	public VisibilityAction() {
		super("Show / Hide Fields");

		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void checkActionEnabled() {
		ReFrame actionHandler = ReFrame.getActiveFrame();
		boolean enable = false;
		
		if (actionHandler != null && actionHandler instanceof AbstractFileDisplayWithFieldHide) {
			AbstractFileDisplayWithFieldHide sourcePnl =(AbstractFileDisplayWithFieldHide) actionHandler;

	    	AbstractLayoutDetails layout = sourcePnl.getFileView().getLayout();
	    	int recordIndex = sourcePnl.getLayoutIndex();
	    	enable =  (recordIndex <= layout.getRecordCount() && recordIndex >= 0);
			
		}
		super.setEnabled(enable);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ReFrame actionHandler = ReFrame.getActiveFrame();
		if (actionHandler instanceof AbstractFileDisplayWithFieldHide) {
			new HideFields((AbstractFileDisplayWithFieldHide) actionHandler);
		}
	}
}
