package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class AutofitAction extends AbstractAction {
	private AbstractFileDisplay display;

	public AutofitAction(AbstractFileDisplay displ) {
		super("Autofit Columns",
        	  Common.getRecordIcon(Common.ID_AUTOFIT_ICON));
		this.display = displ;
	}
	
    public void actionPerformed(ActionEvent e) {
        Common.calcColumnWidths(display.getJTable(), 1);
    }
	
}
