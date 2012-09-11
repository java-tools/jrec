package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.RecordEditor.utils.lang.LangConversion;

@SuppressWarnings("serial")
public final class MovementBtnPnl extends JPanel {

	private static String[] moveHints4 = LangConversion.convertArray(LangConversion.ST_FIELD_HINT, "RecordBtns", new String[] {
        	"Start of File", "Previous Record",
        	"Next Record",   "Last Record"});
	private static String[] moveHints6 = {
			moveHints4[0], moveHints4[1],
			LangConversion.convert(LangConversion.ST_FIELD_HINT, "Parent Record"),
			LangConversion.convert(LangConversion.ST_FIELD_HINT, "Child Record"),
			moveHints4[2], moveHints4[3]};

	private String[] moveHints = moveHints4;

	public final JButton[] buttons;

	public MovementBtnPnl(ImageIcon[] icon, boolean changeRow, ActionListener listner) {
		int i;
		buttons = new JButton[icon.length];

		if (icon.length > 4) {
			moveHints = moveHints6;
		}

		if (changeRow) {
			for (i = 0; i < buttons.length; i++) {
				buttons[i] = new JButton("", icon[i]);
				this.add(buttons[i]);
				buttons[i].addActionListener(listner);
				buttons[i].setToolTipText(moveHints[i]);
			}
			//setDirectionButtonStatus();
		} else {
			for (i = 0; i < buttons.length; i++) {
				buttons[i] = new JButton("", icon[i]);
			}
		}

	}
}
