package net.sf.RecordEditor.edit.display.util;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReAction;

@SuppressWarnings("serial")
public class OptionPnl extends JPanel {
	public final static int BROWSE_PANEL = 1;
	public final static int EDIT_PANEL = 2;
	public final static int TREE_PANEL = 3;

	private static String[] hints = { "Find", "Filter", "Save", "Save As",
			"Copy", "Cut", "Paste", "Paste Prior", "New Record",
			"Set the length based on Current Layout", "Delete", "Help" };

	private static int[] actionIds = { ReActionHandler.FIND,
			ReActionHandler.FILTER, ReActionHandler.SAVE,
			ReActionHandler.SAVE_AS, ReActionHandler.COPY_RECORD,
			ReActionHandler.CUT_RECORD, ReActionHandler.PASTE_RECORD,
			ReActionHandler.PASTE_RECORD_PRIOR, ReActionHandler.INSERT_RECORDS,
			ReActionHandler.CORRECT_RECORD_LENGTH,
			ReActionHandler.DELETE_RECORD, ReActionHandler.HELP };

	private JButton[] btn;

	public OptionPnl(int option, ReActionHandler action) {
		
		int num = option == BROWSE_PANEL ? 5 : hints.length - 1;

		int i;
		btn = new JButton[num];

		Icon searchIcon = Common.getRecordIcon(Common.ID_SEARCH_ICON);
		int iconHeight = searchIcon.getIconHeight() + 5;
		Dimension stdSize = new Dimension(iconHeight,
				searchIcon.getIconWidth() + 5);

		for (i = 0; i < num; i++) {
			if (option != BROWSE_PANEL || (i != Common.ID_SAVE_ICON)) {
				btn[i] = defineBtn(stdSize, i, action);
			}
		}
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

		JButton button = new JButton(new ReAction("", "", Common
				.getRecordIcon(iconIdx), actionIds[iconIdx], action));
		button.setPreferredSize(stdSize);
		this.add(button);
		button.setToolTipText(hints[iconIdx]);

		return button;
	}
}
