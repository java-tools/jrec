package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.utils.lang.ReSpecificScreenAction;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;


@SuppressWarnings("serial")
public class VisibilityAction extends ReSpecificScreenAction implements AbstractActiveScreenAction {

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
	@Override
	public void checkActionEnabled() {
		boolean enable = false;
		net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide sourcePnl 
				= getDisplay(net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide.class);

		if (sourcePnl != null) {
	    	AbstractLayoutDetails layout = sourcePnl.getFileView().getLayout();
	    	int recordIndex = sourcePnl.getLayoutIndex();
	    	enable =  (recordIndex <= layout.getRecordCount() && recordIndex >= 0);

		}
		super.setEnabled(enable);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide sourcePnl 
				= getDisplay(net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide.class);

		if (sourcePnl != null) {
			new net.sf.RecordEditor.edit.display.util.HideFields(sourcePnl);
		}
	}
//
//	private AbstractFileDisplayWithFieldHide getDisplay() {
//		AbstractFileDisplayWithFieldHide ret = null;
//		ReFrame actionHandler = ReFrame.getActiveFrame();
//
//		if (actionHandler == null) {
//
//		} else if (actionHandler instanceof AbstractFileDisplayWithFieldHide) {
//			ret = (AbstractFileDisplayWithFieldHide) actionHandler;
//		} else if (actionHandler instanceof IDisplayFrame) {
//			IDisplayFrame fd = (IDisplayFrame) actionHandler;
//			if (fd.getActiveDisplay() instanceof AbstractFileDisplayWithFieldHide) {
//				ret = (AbstractFileDisplayWithFieldHide) fd.getActiveDisplay();
//			}
//		}
//		return ret;
//	}
}
