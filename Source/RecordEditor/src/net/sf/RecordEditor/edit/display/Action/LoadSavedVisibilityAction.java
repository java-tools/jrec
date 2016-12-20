package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.utils.lang.ReSpecificScreenAction;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;

@SuppressWarnings("serial")
public class LoadSavedVisibilityAction
extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	/**
	 * @param creator
	 */
	public LoadSavedVisibilityAction() {
		super("Load Saved Hidden Fields");

		checkActionEnabled();
	}
	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(getDisplay(net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide.class) != null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide sourcePnl
					= getDisplay(net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide.class);
		if (sourcePnl != null) {
			try {
				net.sf.RecordEditor.edit.display.util.SaveRestoreHiddenFields.restoreHiddenFields(sourcePnl);
			} catch (NoClassDefFoundError e) {
				net.sf.RecordEditor.utils.common.Common
						.logMsg("Unable to loaved saved definition: jibx not present ???", null);
			}
		}
	}}
