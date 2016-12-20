package net.sf.RecordEditor.utils.lang;

import javax.swing.Icon;

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
		net.sf.RecordEditor.utils.screenManager.ReFrame actionHandler 
				= net.sf.RecordEditor.utils.screenManager.ReFrame.getActiveFrame();

		if (actionHandler == null || c == null) {

		} else if (c.isAssignableFrom(actionHandler.getClass())) {
			ret = (CLASS) actionHandler;
		} else if (actionHandler instanceof net.sf.RecordEditor.re.display.IDisplayFrame) {
			@SuppressWarnings("rawtypes")
			Object displ = ((net.sf.RecordEditor.re.display.IDisplayFrame) actionHandler).getActiveDisplay();
			if (displ != null && c.isAssignableFrom(displ.getClass())) {
				ret = (CLASS) displ;
			}
		}
		return ret;
	}
}
