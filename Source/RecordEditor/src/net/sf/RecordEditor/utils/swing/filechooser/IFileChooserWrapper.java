package net.sf.RecordEditor.utils.swing.filechooser;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

public interface IFileChooserWrapper {

	public abstract JFileChooser getFileChooser();

	public abstract JComponent getDisplayItem();

	public abstract int showDialog(boolean open, Component parent)
			throws HeadlessException;

	public abstract int showOpenDialog(Component parent)
			throws HeadlessException;

	public abstract int showSaveDialog(Component parent)
			throws HeadlessException;

	public abstract void addActionListener(ActionListener l);
	
	public abstract void updateRecentDirectories(List<File> recentDirs);

}