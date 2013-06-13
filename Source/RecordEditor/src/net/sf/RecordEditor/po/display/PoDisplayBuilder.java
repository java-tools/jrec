package net.sf.RecordEditor.po.display;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.edit.display.DisplayBuilderImp;
import net.sf.RecordEditor.edit.display.DisplayFrame;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.AbstractFileDisplayWithFieldHide;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.DisplayBuilderAdapter;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.params.Parameters;

public class PoDisplayBuilder extends DisplayBuilderAdapter {

	private static final TableCellColoringAgent coloringAgent = new TableCellColoringAgent();
	private static final String PO_LIST = "PO List";

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.script.DisplayBuilderAdapter#newDisplay(int, java.lang.String, net.sf.RecordEditor.re.script.IDisplayFrame, net.sf.JRecord.Details.AbstractLayoutDetails, net.sf.RecordEditor.re.file.FileView, int)
	 */
	@Override
	public AbstractFileDisplayWithFieldHide newDisplay(int screenType,
			String screenName,
			IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			int lineNo) {
		if (viewOfFile.getLayout().getFileStructure() == Constants.IO_GETTEXT_PO) {
			switch (screenType) {
			case ST_INITIAL_BROWSE:
			case ST_INITIAL_EDIT:
				if (screenName == null || "".equals(screenName)) {
					screenName = PO_LIST;
				}
				PoList polist = getListScreen(screenName, parentFrame, group, viewOfFile, true, viewOfFile.getRowCount() == 0);

				if (Common.OPTIONS.openPoFuzzyWindow.isSelected()) {
					FileView fuzzyView = net.sf.RecordEditor.po.display.FuzzyFilter.getFuzzyView(viewOfFile);

					if (fuzzyView != null && fuzzyView.getRowCount() > 0) {
						PoList screen = getListScreen(
												"Fuzzy/Blank",	polist.getParentFrame(), group, fuzzyView, false, false);
						if (screen != null) {
							DisplayFrame parentFrame2 = screen.getParentFrame();
							parentFrame2.executeAction(screen, ReActionHandler.ADD_CHILD_SCREEN);
						}

					}
				}
				return polist;

			case ST_LIST_SCREEN:
				if (screenName == null || "".equals(screenName)) {
					screenName = PO_LIST;
				}
				return getListScreen(screenName, parentFrame, group, viewOfFile, viewOfFile == viewOfFile.getBaseFile(), false);

			case ST_RECORD_SCREEN:
				return DisplayBuilderImp.addToScreen(
						parentFrame, new PoRecordScreen(viewOfFile, lineNo, parentFrame.getActiveDisplay().getJTable()));
			}
		}
		return null;
	}

	private PoList getListScreen(String screenName, IDisplayFrame<? extends AbstractFileDisplay> parentFrame,
			AbstractLayoutDetails group, FileView viewOfFile,
			boolean primaryScreen, boolean addBlankLine) {

		PoList polist = new PoList(screenName, viewOfFile, primaryScreen);
		polist.setTableCellColoringAgent(coloringAgent);

		DisplayBuilderImp.addToScreen(
				parentFrame,
				polist
			);

		if (addBlankLine) {
			polist.insertLine(0);
			polist.getParentFrame().executeAction(0, getChildOption());
			polist.getParentFrame().setActiveIdx(1);
		} else {
			polist.getParentFrame().executeAction(0, getChildOption());
		}

		return polist;
	}

	public static int getChildOption() {
		int ret = ReActionHandler.ADD_CHILD_SCREEN_RIGHT;
		if (Parameters.CHILD_SCREEN_BOTTOM.equals(Common.OPTIONS.poChildScreenPosition.get())) {
			ret = ReActionHandler.ADD_CHILD_SCREEN_BOTTOM;
		}
		return ret;
	}
}
