package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.util.NewFile;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class NewFileAction extends ReAbstractAction {
	private AbstractLayoutSelectCreator<?> create;
	public NewFileAction(AbstractLayoutSelectCreator<?> layoutSelectCreate) {
		super("New File",
        	  Common.getRecordIcon(Common.ID_NEW_ICON));
		create = layoutSelectCreate;
	}

	public void actionPerformed(ActionEvent e) {
		ReFrame activeFrame = ReFrame.getActiveFrame();
		if (activeFrame != null && activeFrame.isActionAvailable(ReActionHandler.NEW)) {
			activeFrame.executeAction(ReActionHandler.NEW);
		} else {
			new NewFile(create.create());
		}
    }

}
