package net.sf.RecordEditor.edit.display.Action;

import javax.swing.Icon;

import net.sf.RecordEditor.re.script.IDisplayFrame;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public abstract class ReSpecificScreenAction extends ReAbstractAction {

	public ReSpecificScreenAction(String name, Icon icon) {
		super(name, icon);
	}

	public ReSpecificScreenAction(String name) {
		super(name);
	}


	@SuppressWarnings("unchecked")
	protected <CLASS extends Object> CLASS getDisplay(Class<CLASS> c) {
		CLASS ret = null;
		ReFrame actionHandler = ReFrame.getActiveFrame();

		if (actionHandler == null || c == null) {

		} else if (c.isAssignableFrom(actionHandler.getClass())) {
			ret = (CLASS) actionHandler;
		} else if (actionHandler instanceof IDisplayFrame) {
			@SuppressWarnings("rawtypes")
			Object displ = ((IDisplayFrame) actionHandler).getActiveDisplay();
			if (displ != null && c.isAssignableFrom(displ.getClass())) {
				ret = (CLASS) displ;
			}
		}
		return ret;
	}
}
