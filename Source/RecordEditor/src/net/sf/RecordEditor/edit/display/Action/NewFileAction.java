package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.RecordEditor.edit.display.util.NewFile;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class NewFileAction extends AbstractAction {
	private AbstractLayoutSelectCreator<?> create;
	public NewFileAction(AbstractLayoutSelectCreator<?> layoutSelectCreate) {
		super("New File",
        	  Common.getRecordIcon(Common.ID_NEW_ICON));
		create = layoutSelectCreate;
	}
	
	public void actionPerformed(ActionEvent e) {
		new NewFile(create.create());
    }

}
