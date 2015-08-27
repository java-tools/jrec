package net.sf.RecordEditor.re.display;

import net.sf.RecordEditor.utils.screenManager.ReFrame;

public class DisplayDetails {

	

	@SuppressWarnings("rawtypes")
	public static AbstractFileDisplay getDisplayDetails(ReFrame frame) {

		AbstractFileDisplay parentTab = null;
		if (frame instanceof AbstractFileDisplay) {
			parentTab = (AbstractFileDisplay) frame;
		} else if (frame instanceof IChildDisplay) {
			parentTab = ((IChildDisplay) frame).getSourceDisplay();
		} else if (frame instanceof IDisplayFrame) {
			parentTab = ((IDisplayFrame) frame).getActiveDisplay();
		}
		return parentTab;
	}

}
