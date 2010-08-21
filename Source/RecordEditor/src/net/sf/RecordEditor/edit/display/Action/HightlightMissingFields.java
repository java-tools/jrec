package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTable;

import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.screenManager.ReFrame;



@SuppressWarnings("serial")
public class HightlightMissingFields extends JCheckBoxMenuItem {
	
	private AbstractAction action = new AbstractAction("Highlight Missing Fields") {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Common.setHighlightEmpty(getState());
				
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
		setState(Common.isHighlightEmpty());
		
		Common.setHighlightEmptyActive(true);
	}
}
