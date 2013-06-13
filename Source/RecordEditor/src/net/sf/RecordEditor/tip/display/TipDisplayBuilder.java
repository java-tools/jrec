package net.sf.RecordEditor.tip.display;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.DisplayBuilderImp;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.DisplayBuilderAdapter;
import net.sf.RecordEditor.utils.common.ReActionHandler;

public class TipDisplayBuilder extends DisplayBuilderAdapter {


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.DisplayBuilderAdapter#newDisplay(int, java.lang.String, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.RecordEditor.re.file.FileView, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			int lineNo) {
		if (viewOfFile.getLayout().getFileStructure() == Constants.IO_TIP) {
			switch (screenType) {
			case ST_INITIAL_BROWSE:
			case ST_INITIAL_EDIT:
				TipList tiplist = new TipList(viewOfFile, true);
				if (viewOfFile.getRowCount() == 0 && screenType == ST_INITIAL_EDIT) {
					tiplist.insertLine(0);
				}

				DisplayBuilderImp.addToScreen(parentFrame, tiplist);

				tiplist.getParentFrame().executeAction(ReActionHandler.ADD_CHILD_SCREEN);
				return tiplist;

			case ST_LIST_SCREEN:
				TipList tipList1 = new TipList(viewOfFile, viewOfFile == viewOfFile.getBaseFile());
				DisplayBuilderImp.addToScreen(parentFrame, tipList1);

				tipList1.getParentFrame().executeAction(ReActionHandler.ADD_CHILD_SCREEN);
				return tipList1;
			case ST_RECORD_SCREEN:
				return DisplayBuilderImp.addToScreen(parentFrame, new TipRecordScreen(viewOfFile, lineNo));
			}
		}
		return null;
		}

}
