package net.sf.RecordEditor.utils.swing.filechooser;

import java.awt.event.ActionListener;

public abstract class JRFileChooserWrapperBase implements IFileChooserWrapper {

	
	@Override
	public final void addActionListener(ActionListener l) {
		getFileChooser().addActionListener(l);
	}
}
