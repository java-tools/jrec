package net.sf.RecordEditor.po.display;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.DisplayBuilderImp;
import net.sf.RecordEditor.edit.display.common.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.script.DisplayBuilderAdapter;
import net.sf.RecordEditor.re.script.IDisplayFrame;
import net.sf.RecordEditor.utils.common.ReActionHandler;

public class PoDisplayBuilder extends DisplayBuilderAdapter {


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.DisplayBuilderAdapter#newDisplay(int, java.lang.String, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.RecordEditor.re.file.FileView, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails<?, ?> group, FileView<?> viewOfFile,
			int lineNo) {
		if (viewOfFile.getLayout().getFileStructure() == Constants.IO_GETTEXT_PO) {
			switch (screenType) {
			case ST_INITIAL_BROWSE:
			case ST_INITIAL_EDIT:
				PoList polist = new PoList(viewOfFile, true);
				if (viewOfFile.getRowCount() == 0 && screenType == ST_INITIAL_EDIT) {
					polist.insertLine(0);
				}

				DisplayBuilderImp.addToScreen(
						parentFrame,
						polist
					);
				polist.getParentFrame().executeAction(ReActionHandler.ADD_CHILD_SCREEN);
				return polist;

			case ST_LIST_SCREEN:
				PoList poList1 = new PoList(viewOfFile, viewOfFile == viewOfFile.getBaseFile());
				DisplayBuilderImp.addToScreen(
							parentFrame,
							poList1
						);
				poList1.getParentFrame().executeAction(ReActionHandler.ADD_CHILD_SCREEN);
				return poList1;
			case ST_RECORD_SCREEN:
				return DisplayBuilderImp.addToScreen(
						parentFrame, new PoRecordScreen(viewOfFile, lineNo, parentFrame.getActiveDisplay().getJTable()));
			}
		}
		return null;
		}

}
