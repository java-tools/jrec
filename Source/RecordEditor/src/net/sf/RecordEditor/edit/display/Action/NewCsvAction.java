package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.util.NewCsvFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class NewCsvAction extends ReAbstractAction {

	public NewCsvAction() {
		this("New File");
	}

	public NewCsvAction(String text) {
		super(text,
        	  Common.getRecordIcon(Common.ID_NEW_ICON));
	}

	public void actionPerformed(ActionEvent e) {
		ReFrame activeFrame = ReFrame.getActiveFrame();
		if (activeFrame != null && activeFrame.isActionAvailable(ReActionHandler.NEW)) {
			activeFrame.executeAction(ReActionHandler.NEW);
		} else {
			new NewCsvFile();
		}
    }

}
