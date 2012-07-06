package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.util.NewFile;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelectCreator;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class NewFileAction extends ReAbstractAction {
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
