package net.sf.RecordEditor.edit.display.util;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReAction;

@SuppressWarnings("serial")
public class OptionPnl extends JPanel {
	public final static int BROWSE_PANEL = 1;
	public final static int EDIT_PANEL = 2;
	public final static int TREE_PANEL = 3;
	public final static int NO_FILTER = 4;

	private static String[] hints =  LangConversion.convertArray(LangConversion.ST_FIELD_HINT, "FileDisplayBtns", new String[] {
			"Find", "Filter", "Save", "Save As",
			"Copy", "Cut", "Paste", "Paste Prior", "New Record",
			"Set the length based on Current Layout", "Delete", "Help" });

	private static int[] actionIds = { ReActionHandler.FIND,
			ReActionHandler.FILTER, ReActionHandler.SAVE,
			ReActionHandler.SAVE_AS, ReActionHandler.COPY_RECORD,
			ReActionHandler.CUT_RECORD, ReActionHandler.PASTE_RECORD,
			ReActionHandler.PASTE_RECORD_PRIOR, ReActionHandler.INSERT_RECORDS,
			ReActionHandler.CORRECT_RECORD_LENGTH,
			ReActionHandler.DELETE_RECORD, ReActionHandler.HELP };

	private JButton[] btn;

	public OptionPnl(int option, ReActionHandler actionHandler, List<Action> actions) {

		int num = hints.length - 1;
		int count = hints.length - 1;
		
		switch (option) {
		case BROWSE_PANEL:	
			num = 5;
			count = num;
			break;
		case NO_FILTER:		num = num - 1;
		}
		
		num +=  (actions != null)?actions.size():0; 

		btn = new JButton[num];

		Icon searchIcon = Common.getRecordIcon(Common.ID_SEARCH_ICON);
		int iconHeight = searchIcon.getIconHeight() + 5;
		Dimension stdSize = new Dimension(iconHeight,
				searchIcon.getIconWidth() + 5);

		int j=0;
		for (int i = 0; i < count; i++) {
			if ((option == BROWSE_PANEL && i == Common.ID_SAVE_ICON)
			|| (option == NO_FILTER && i == Common.ID_FILTER_ICON)) {
			} else {
				btn[j++] = defineBtn(stdSize, i, actionHandler);
			}
		}
		
		if (actions != null) {
			for (Action action : actions) {
				btn[j] = new JButton(action);
				this.add(btn[j++]);
			}
		}
	}
	
	public List<JButton> getButtons() {
		return Arrays.asList(btn);
	}

	/**
	 * Define a Button
	 *
	 * @param stdSize
	 *            size of button
	 * @param iconIdx
	 *            icon index
	 *
	 * @return button just defined
	 */
	private JButton defineBtn(Dimension stdSize, int iconIdx,
			ReActionHandler action) {

		JButton button = new JButton(new ReAction("", "",
				Common.getRecordIcon(iconIdx), actionIds[iconIdx], action));
		button.setPreferredSize(stdSize);
		this.add(button);
		button.setToolTipText(hints[iconIdx]);

		return button;
	}
}
