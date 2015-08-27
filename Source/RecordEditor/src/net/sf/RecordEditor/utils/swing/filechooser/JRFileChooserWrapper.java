package net.sf.RecordEditor.utils.swing.filechooser;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.RecordEditor.utils.params.Parameters;

public class JRFileChooserWrapper extends JRFileChooserWrapperBase {

	private JFileChooser fileChooser;
	private String currentDirectoryPath;
//	private final JComponent displayItem;

	protected JRFileChooserWrapper(String currentDirectoryPath) {
		this.currentDirectoryPath = currentDirectoryPath;
	}
	
	
	
	
	protected JRFileChooserWrapper() {
		super();
	}




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper#getFileChooser()
	 */
	@Override
	public JFileChooser getFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			if (currentDirectoryPath != null && currentDirectoryPath.length() > 0) {
				fileChooser.setSelectedFile(new File(currentDirectoryPath));
			}
		}
		return fileChooser;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper#getDisplayItem()
	 */
	@Override
	public JComponent getDisplayItem() {
		return getFileChooser();
	}


	

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper#showDialog(boolean, java.awt.Component)
	 */
	@Override
	public int showDialog(boolean open, Component parent) throws HeadlessException {
		if (open) {
			return showOpenDialog(parent);
		} else {
			return showSaveDialog(parent);
		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper#showOpenDialog(java.awt.Component)
	 */
	@Override
	public int showOpenDialog(Component parent) throws HeadlessException {
		return getFileChooser().showOpenDialog(parent);
	}
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.filechooser.IFileChooserWrapper#showSaveDialog(java.awt.Component)
	 */
	@Override
	public int showSaveDialog(Component parent) throws HeadlessException {
		return getFileChooser().showSaveDialog(parent);
	}

	
	public static IFileChooserWrapper newChooserCsvEditor(int screenWidth, String initialFile, List<File> recentDirs) {
		return newChooser(
				Parameters.FILE_CHOOSER_OPTION, 
				screenWidth <= 0 || screenWidth > 1100, 
				initialFile, recentDirs.toArray(new File[recentDirs.size()]));
	}
	
	public static IFileChooserWrapper newChooser(String initialFile, File[] recentDirs) {
		return newChooser(Parameters.FILE_CHOOSER_OPTION, true, initialFile, recentDirs);
	}
	
	private static IFileChooserWrapper newChooser(String param, boolean suitableScreenSize, String initialFile, File[] recentDirs) {
		String s = Parameters.getString(param);
		boolean extended = false;
		boolean visible = true;
		
		if (Parameters.FILE_CHOOSER_EXTENDED.equals(s)) {
			extended = true; 
//		} else if (Parameters.FILE_CHOOSER_OPTIONAL.equals(s)) {
//			extended = ! Parameters.isWindowsLAF();
		} else if (! Parameters.FILE_CHOOSER_NORMAL.equals(s)) {
			extended = true;
			visible = suitableScreenSize && ! Parameters.isWindowsLAF();
		}
		
		if (extended) {
			return new JRFileChooserExtendedWrapper(initialFile, recentDirs, visible);
		}
		
		return new JRFileChooserWrapper(initialFile);
	}




	@Override
	public void updateRecentDirectories(List<File> recentDirs) {
		
	}
}



