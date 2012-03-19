package net.sf.RecordEditor.re.openFile;

import javax.swing.JMenu;

import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.swing.BasePanel;

public interface OpenFileInterface extends LayoutConnection {

	public BasePanel getPanel();

	public JMenu getRecentFileMenu();
}
