package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.util.UpdateCsvLayout;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;

@SuppressWarnings("serial")
public class CsvUpdateLayoutAction
extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	/**
	 * @param creator
	 */
	public CsvUpdateLayoutAction() {
		super("Update Csv Columns",
			  Common.getRecordIcon(Common.ID_COLUMN_DTLS_ICON));

		checkActionEnabled();
	}


	/**
	 * @param creator
	 */
	public CsvUpdateLayoutAction(boolean evaluate) {
		super("Update Csv Columns", Common.getRecordIcon(Common.ID_COLUMN_DTLS_ICON));
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(getDisplay(AbstractFileDisplay.class) != null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFileDisplay sourcePnl = getDisplay(AbstractFileDisplay.class);
		if (sourcePnl != null) {
			new UpdateCsvLayout(sourcePnl);
		}
	}
}
//	private boolean isActive(ReFrame activeScreen) {
//		boolean active = false;
//
//		if (activeScreen instanceof AbstractFileDisplay) {
//			AbstractFileDisplay source = (AbstractFileDisplay) activeScreen;
//			active =  source.getFileView().isSimpleCsvFile();
//		}
//
//		return active;
//	}
//}
