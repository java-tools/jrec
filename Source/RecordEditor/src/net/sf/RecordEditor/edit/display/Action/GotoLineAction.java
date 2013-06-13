package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.util.GotoLine;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class GotoLineAction extends ReAbstractAction {
	private final AbstractFileDisplay src;
	private FileView master;

	public GotoLineAction(AbstractFileDisplay src, FileView master) {
		super("Goto Line",
        	  Common.getRecordIcon(Common.ID_GOTO_ICON));
		this.src = src;
		this.master = master;
	}

	public void actionPerformed(ActionEvent e) {
		new GotoLine(src, master);
	}
}
