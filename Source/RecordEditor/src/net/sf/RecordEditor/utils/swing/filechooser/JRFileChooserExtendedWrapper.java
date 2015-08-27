/**
 * 
 */
package net.sf.RecordEditor.utils.swing.filechooser;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 * @author Bruce Martin
 *
 */
public class JRFileChooserExtendedWrapper extends JRFileChooserWrapperBase {

	private final JRFileChooserPnlX fileChooserX;
	
	protected JRFileChooserExtendedWrapper(String initialFile, File[] recentDirs, boolean visible) {
		fileChooserX = new JRFileChooserPnlX(initialFile, recentDirs, visible);
	}
	
	
	public JFileChooser getFileChooser() {
		return fileChooserX.getFilechooser();
	}

	public final JComponent getDisplayItem() {
		return fileChooserX;
	}

	
	@Override
	public int showDialog(boolean open, Component parent) {
		return showDialogImpl(open, parent);
	}

	@Override
	public int showOpenDialog(Component parent) throws HeadlessException {
		return showDialogImpl(true, parent);
	}

	@Override
	public int showSaveDialog(Component parent) throws HeadlessException {
		return showDialogImpl(false, parent);
	}
	
	
	private int showDialogImpl(boolean open, Component parent)
			throws HeadlessException {
							
		FileChooserDialog dialog;
		
		fileChooserX.doTheLayout();
		
		if (parent != null && parent instanceof Window) {
			dialog = new FileChooserDialog((Window) parent, fileChooserX, open);
		} else {
			dialog = new FileChooserDialog(fileChooserX, open);
		}
		

		dialog.setVisible(true);
		
		return dialog.dialogReturn;
	}
	
	@Override
	public void updateRecentDirectories(List<File> recentDirs) {
		if (recentDirs != null) {
			fileChooserX.setRecentList(recentDirs.toArray(new File[recentDirs.size()]));
		}
	}
	/**
	 * Modal Dialog to display the JFileChooser 
	 * @author Bruce Martin
	 *
	 */
	@SuppressWarnings("serial")
	private static class FileChooserDialog extends JDialog implements ActionListener {
		int dialogReturn = JFileChooser.CANCEL_OPTION;
		
		final JRFileChooserPnlX fileChooserX;
		
		
		public FileChooserDialog(Window parent, JRFileChooserPnlX fileChooserX, boolean open) {
			super(parent, open?"Open":"Save");
			this.fileChooserX = fileChooserX;
			init(open);
		}
		public FileChooserDialog(JRFileChooserPnlX fileChooserX, boolean open) {
			this(null, fileChooserX, open);
//			this.fileChooserX = fileChooserX;
//			init(open);
		}
		
		private void init(boolean open) {
			JFileChooser filechooser = fileChooserX.getFilechooser();
			if (open) {
				filechooser.setDialogType(JFileChooser.OPEN_DIALOG);
			} else {
				filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
			}
			getContentPane().add(fileChooserX);
			pack();
			
			this.setModal(true);
			filechooser.removeActionListener(this);
			filechooser.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
				dialogReturn = JFileChooser.APPROVE_OPTION;
			}
			this.setVisible(false);
		}
		
	}
//
//	public static void main(String[] a) {
//		String[] r = {
//				"F:\\Work\\RecordEditor\\Build\\Instalation\\GeneralDb\\GenericDB", 
//				"F:\\Work\\RecordEditor\\Build\\Instalation\\hsqldb_izpack\\lib",
//				"F:\\Shared"
//		};
//
//		JFileChooserExtendedWrapper w = new JFileChooserExtendedWrapper("C:\\Users", r);
//		
//		System.out.println("1> " + w.showOpenDialog(null));
//		
////		w = new JFileChooserExtendedWrapper("C:\\Users", r);
//		System.out.println("2> " + w.showOpenDialog(null));
//		
//		System.out.println("3> " + w.showSaveDialog(null));
//		
//		System.out.println("4> " + w.showSaveDialog(null));
//	
//	}
}
