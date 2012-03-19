package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.RecordEditor.edit.util.NewCsvFile;
import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class NewCsvAction extends AbstractAction {

	public NewCsvAction() {
		super("New Csv File",
        	  Common.getRecordIcon(Common.ID_NEW_ICON));
	}
	
	public void actionPerformed(ActionEvent e) {
		new NewCsvFile();
    }

}
