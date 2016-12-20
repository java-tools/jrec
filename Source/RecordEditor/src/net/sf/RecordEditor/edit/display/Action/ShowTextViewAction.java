package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReSpecificScreenAction;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;


@SuppressWarnings("serial")
public class ShowTextViewAction extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	private final boolean selectedRecords, coloredFields;
	private final int screenType;

	public static ShowTextViewAction get(boolean selectedRecords, boolean coloredFields) {
		String s = "Text View";
		if (selectedRecords) {
			s = "Text View (Selected Records)";
		}
		int stype = net.sf.RecordEditor.re.display.IDisplayBuilder.ST_DOCUMENT;
		if (coloredFields) {
			stype = net.sf.RecordEditor.re.display.IDisplayBuilder.ST_COLORED_DOCUMENT;
			s = "Text View (highlight fields)";
			if (selectedRecords) {
				s = "Text View (Selected Records / highlight fields)";
			}
		}

		return new ShowTextViewAction(selectedRecords, s, stype, coloredFields);
	}
	/**
	 * @param creator
	 */
	private ShowTextViewAction(boolean selectedRecords, String msg, int sType, boolean coloredFields) {
		super(msg);
		this.selectedRecords = selectedRecords;
		this.coloredFields = coloredFields;
		this.screenType = sType;

		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(
				   Common.OPTIONS.allowTextEditting.isSelected()
				&& isActive(getDisplay(AbstractFileDisplay.class)));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFileDisplay fileDisplay = getDisplay(AbstractFileDisplay.class);
		if (isActive(fileDisplay)) {
			FileView f = fileDisplay.getFileView();

			if (selectedRecords) {
				if (fileDisplay.isOkToUseSelectedRows()) {
					f = f.getView(fileDisplay.getSelectedRows());
				} else {
					f = f.getView(fileDisplay.getSelectedLines());
				}
			}

			if (f != null) {
				net.sf.RecordEditor.re.display.DisplayBuilderFactory
						.getInstance().newDisplay(screenType, "", fileDisplay.getParentFrame(), f.getLayout(), f, 0);
			}
		}
	}


	private boolean isActive(AbstractFileDisplay activeScreen) {
		boolean active = false;

		if (activeScreen != null) {
			AbstractFileDisplay source = (AbstractFileDisplay) activeScreen;
			active =  source.getFileView().isDocumentViewAvailable(coloredFields);
		}

		return active;
	}
}
