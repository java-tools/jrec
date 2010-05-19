package net.sf.RecordEditor.editFileLayout;

import net.sf.RecordEditor.edit.EditFileLayout;
import net.sf.RecordEditor.utils.edit.ParseArgs;

public class Edit {

	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);
			    ParseArgs args = new ParseArgs(pgmArgs);

			    new EditFileLayout(args.getDfltFile(), args.getInitialRow());
			}
		});
	}
}
