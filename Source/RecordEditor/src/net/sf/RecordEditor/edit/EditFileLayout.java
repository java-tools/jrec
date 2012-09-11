/*
 * @Author Bruce Martin
 * Created on 12/01/2007
 *
 * Purpose:
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Refactoring changes from moving classes to new packages, and
 *     creation of JRecord
 *   - Name changes
 *   - Better file exists checking and error messages
 */
package net.sf.RecordEditor.edit;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.copy.CopyFileLayout;
import net.sf.RecordEditor.diff.CompareFileLayout;
import net.sf.RecordEditor.edit.display.Action.NewFileAction;
import net.sf.RecordEditor.edit.display.Action.SaveFieldSequenceAction;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.layoutWizard.WizardFileLayout;
import net.sf.RecordEditor.re.openFile.LayoutSelectionFile;
import net.sf.RecordEditor.re.openFile.LayoutSelectionFileCreator;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * This class will Edit a file with a File-Layout (instead of using a DB copybook)
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditFileLayout extends EditRec {

    /**
	 * Creating the File & record selection screen
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pInterfaceToCopyBooks interface to copybooks
	 */
	public EditFileLayout(final String pInFile,
	        	      final int pInitialRow) {
		this(pInFile, pInitialRow, ReIOProvider.getInstance());
	}

	/**
	 * Creating the File & record selection screen
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybooks
	 */
    public EditFileLayout(final String pInFile,
     	   final int pInitialRow,
    	   final AbstractLineIOProvider pIoProvider) {
        super(false,
        	  "Record Editor",
        	  new NewFileAction(
        			  new LayoutSelectionFileCreator()
        	  ));

        OpenFile open = new OpenFile(pInFile, pInitialRow, pIoProvider,
        		//pInterfaceToCopyBooks,
                null, null, Parameters.getApplicationDirectory() + "CobolFiles.txt",
                Common.HELP_COBOL_EDITOR, new LayoutSelectionFile());

        this.setOpenFileWindow(open,
        		new ReAbstractAction("File Copy Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						CopyFileLayout.newMenu();
					}
        		},
				new ReAbstractAction("Compare Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						CompareFileLayout.newMenu();
					}
				},
				true
		);

        getEditMenu().add(new ReAbstractAction("Layout Wizard") {

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				new WizardFileLayout(getOpenFileWindow().getOpenFilePanel().getCurrentFileName());
			}
        });


        super.getEditMenu().addSeparator();
        super.getEditMenu().add(addAction(new VisibilityAction()));
        super.getEditMenu().add(addAction(new SaveFieldSequenceAction()));
    }



	/**
	 * Add program specific dropdown menus
	 * @param menubar top level menu
	 */
	protected void addProgramSpecificMenus(JMenuBar menubar) {

		JMenu layoutMenu = SwingUtils.newMenu("Record Layouts");
	    menubar.add(layoutMenu);
	    layoutMenu.add(new ReAbstractAction("Layout Wizard") {
	    	public void actionPerformed(ActionEvent e) {
	    		new WizardFileLayout(getOpenFileWindow().getOpenFilePanel().getCurrentFileName());
	    	}
	    });
	    super.addProgramSpecificMenus(menubar);
	}



	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);
			    ParseArgs args = new ParseArgs(pgmArgs);

			    new EditFileLayout(args.getDfltFile(), args.getInitialRow(), ReIOProvider.getInstance());
			        	//new CopyBookDbReader());
			}
		});
	}
}
