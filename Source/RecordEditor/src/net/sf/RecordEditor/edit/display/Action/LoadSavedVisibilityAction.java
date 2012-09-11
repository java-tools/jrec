package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.utils.common.Common;
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
		super.setEnabled(getDisplay(AbstractFileDisplayWithFieldHide.class) != null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFileDisplayWithFieldHide sourcePnl = getDisplay(AbstractFileDisplayWithFieldHide.class);
		if (sourcePnl != null) {
			try {
				net.sf.RecordEditor.edit.display.util.SaveRestoreHiddenFields.restoreHiddenFields(sourcePnl);
			} catch (NoClassDefFoundError e) {
				Common.logMsg("Unable to loaved saved definition: jibx not present ???", null);
			}
		}
	}}
