package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;

@SuppressWarnings("serial")
public class AutofitAction extends ReAbstractAction {
	private AbstractFileDisplay display;

	public AutofitAction(AbstractFileDisplay displ) {
		super("Autofit Columns",
        	  Common.ID_AUTOFIT_ICON);
		this.display = displ;
	}

    public void actionPerformed(ActionEvent e) {
        Common.calcColumnWidths(display.getJTable(), 1);
    }

}
