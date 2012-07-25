package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTable;

import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;



@SuppressWarnings("serial")
public class HightlightMissingFields extends JCheckBoxMenuItem {

	private ReAbstractAction action = new ReAbstractAction("Highlight Missing Fields") {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.OPTIONS.highlightEmpty.set(getState());

				JTable tbl;
				ReFrame[] frames = ReFrame.getAllFrames();
				for (int i = 0; i < frames.length; i++) {
					if (frames[i] instanceof AbstractFileDisplay) {
						tbl = ((AbstractFileDisplay) frames[i]).getJTable();

						if (tbl != null) {
							tbl.repaint();
						}
					}
				}

			}
	};

	public HightlightMissingFields() {
		super();
		super.setAction(action);
		setState(Common.OPTIONS.highlightEmpty.isSelected());

		Common.OPTIONS.highlightEmptyActive.set(true);
	}
}
