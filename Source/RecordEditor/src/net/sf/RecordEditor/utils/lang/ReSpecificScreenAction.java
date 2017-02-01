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



	protected <CLASS extends Object> CLASS getDisplay(Class<CLASS> c) {
		return getDisplay(c, net.sf.RecordEditor.utils.screenManager.ReFrame.getActiveFrame());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <CLASS> CLASS getDisplay(Class<CLASS> c, net.sf.RecordEditor.utils.screenManager.ReFrame actionHandler) {
		CLASS ret = null;
		Object o = null;
		if (actionHandler == null || c == null) {

		} else if (c.isAssignableFrom(actionHandler.getClass())) {
			ret = (CLASS) actionHandler;
		} else if (actionHandler instanceof net.sf.RecordEditor.re.display.IDisplayFrame) {
			o = ((net.sf.RecordEditor.re.display.IDisplayFrame) actionHandler).getActiveDisplay();
		} else if (actionHandler instanceof net.sf.RecordEditor.edit.open.OpenFile) {
			o = ((net.sf.RecordEditor.edit.open.OpenFile) actionHandler).getOpenFilePanel();
		}
		if (ret == null && o != null && c.isAssignableFrom(o.getClass())) {
			ret = (CLASS) o;
		}
		return ret;
	}

}
