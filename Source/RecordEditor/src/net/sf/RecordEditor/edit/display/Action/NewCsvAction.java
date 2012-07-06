package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.util.NewCsvFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class NewCsvAction extends ReAbstractAction {

	public NewCsvAction() {
		super("New Csv File",
        	  Common.getRecordIcon(Common.ID_NEW_ICON));
	}

	public void actionPerformed(ActionEvent e) {
		new NewCsvFile();
    }

}
