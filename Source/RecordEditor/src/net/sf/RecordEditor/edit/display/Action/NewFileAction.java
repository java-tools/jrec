package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class NewFileAction extends ReAbstractAction {
	private AbstractLayoutSelectCreator<?> create;
	public NewFileAction(AbstractLayoutSelectCreator<?> layoutSelectCreate) {
		super("New File", Common.ID_NEW_ICON);
		create = layoutSelectCreate;
	}

	public void actionPerformed(ActionEvent e) {
		ReActionHandler actionHandler = ReFrame.getActionHandler();
		int newActionId = net.sf.RecordEditor.utils.common.ReActionHandler.NEW;
		if (actionHandler != null && actionHandler.isActionAvailable(newActionId)) {
			actionHandler.executeAction(newActionId);
		} else {
			new net.sf.RecordEditor.edit.display.util.NewFile(create.create());
		}
    }

}
