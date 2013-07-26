/*
 * @Author Bruce Martin
 * Created on 12/01/2007
 *
 * Purpose:
 * Extended RecordEditor with basic RecordLayout edit function
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Added EditOptions function to the Edit top level menu
 *   - added support for the Java~Run programs (to replace bat files etc)
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - JRecord Spun off, code reorg
 *   - Add icon to Edit Startup options
 */
package net.sf.RecordEditor.edit;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuBar;

import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.copy.CopyDBLayout;
import net.sf.RecordEditor.diff.CompareDBLayout;
import net.sf.RecordEditor.edit.display.Action.ChangeLayoutAction;
import net.sf.RecordEditor.edit.display.Action.CsvOpenAction;
import net.sf.RecordEditor.edit.display.Action.CsvUpdateLayoutAction;
import net.sf.RecordEditor.edit.display.Action.NewCsvAction;
import net.sf.RecordEditor.edit.display.Action.NewFileAction;
import net.sf.RecordEditor.edit.display.Action.SaveFieldSequenceAction;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.layoutEd.LayoutMenu;
import net.sf.RecordEditor.layoutWizard.Wizard;
import net.sf.RecordEditor.re.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.re.openFile.LayoutSelectionDBCreator;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.re.util.UpgradeDB;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swingx.TipsManager;

/**
 * Extended RecordEditor with basic RecordLayout edit function.
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class FullEditor extends EditRec {

    private LayoutMenu layoutMenu;


    /**
	 * Creating the File & record selection screen
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pInterfaceToCopyBooks interface to copybook's
	 */
	public FullEditor(final String pInFile,
	        	      final int pInitialRow,
	        	      final CopyBookInterface pInterfaceToCopyBooks) {
		this(pInFile, pInitialRow, ReIOProvider.getInstance(), pInterfaceToCopyBooks);
		UpgradeDB.checkForUpdate(Common.getConnectionIndex());
	}

	/**
	 * Creating the File & record selection screenm
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybook's
	 */
    public FullEditor(final String pInFile,
     	   final int pInitialRow,
    	   final AbstractLineIOProvider pIoProvider,
    	   final CopyBookInterface pInterfaceToCopyBooks) {
        super(true,
        	  "Record Editor",
        	  new CsvOpenAction(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get(), pIoProvider),
        	  new NewFileAction(
        			  new LayoutSelectionDBCreator(pInterfaceToCopyBooks)
        	  ),
        	  new NewCsvAction("New Basic Csv file")
        );


        setupOpenWindow(pInFile, pInitialRow,
          	    pIoProvider,  pInterfaceToCopyBooks);

        if (Common.OPTIONS.showRecordEditorTips.isSelected() && TipsManager.tipsModulePresent()) {
        	TipsManager.startTips(this, Parameters.getSytemJarFileDirectory() + "/RecordEditor_TipOfTheDay.properties",
        					  Parameters.SHOW_RECORDEDITOR_TIPS);
        }
    }



    /**
     * Create the openFile window
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybooks
     */
    private void setupOpenWindow(final String pInFile,
      	   final int pInitialRow,
     	   final AbstractLineIOProvider pIoProvider,
     	   final CopyBookInterface pInterfaceToCopyBooks) {
        //JButton createLayout = new JButton();
        JButton createLayoutWizard = new JButton("XX");

        CopybookLoaderFactory.setInstance(new CopybookLoaderFactoryDB());

        OpenFile open = new OpenFile(pInFile, pInitialRow, pIoProvider,
                createLayoutWizard, null /* createLayout */,
                new LayoutSelectionDB(pInterfaceToCopyBooks, null, true));
        ChangeLayoutAction changeLayout = new ChangeLayoutAction(
     		   new LayoutSelectionDBCreator(pInterfaceToCopyBooks));

        //createLayout.setAction(RecordEdit1Record.getAction(open.getOpenFilePanel()));
        createLayoutWizard.setAction(Wizard.getAction(open.getOpenFilePanel()));

        layoutMenu.setDatabaseDetails(open.getOpenFilePanel());

        super.getEditMenu().addSeparator();
        super.getEditMenu().add(addAction(new VisibilityAction()));
        super.getEditMenu().add(addAction(new SaveFieldSequenceAction()));
        super.getEditMenu().addSeparator();
        super.getEditMenu().add(addAction(changeLayout));
        super.getEditMenu().add(addAction(new CsvUpdateLayoutAction()));

        this.setOpenFileWindow(open,
        		new ReAbstractAction("File Copy Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						CopyDBLayout.newMenu(pInterfaceToCopyBooks);
					}
		        },
   				new ReAbstractAction("Compare Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						CompareDBLayout.newMenu(pInterfaceToCopyBooks);
					}
		        },
		        true
       );
       AbsDB.setSystemLog(super.getLog());
   }


	/**
	 * Add program specific dropdown menus
	 * @param menubar top level menu
	 */
	protected void addProgramSpecificMenus(JMenuBar menubar) {

	    layoutMenu = new LayoutMenu(null);
	    menubar.add(layoutMenu);
	    super.addProgramSpecificMenus(menubar);
	}

	/**
	 * Close the application and Database (used in automated testing)
	 */
	public static void close() {
		try {
			ReFrame.closeAllFrames();
			Common.closeConnection();
		} catch (Exception e) {

		}
	}

	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		//System.out.println("Max Memmory: " + Common.MAX_MEMORY);

		Common.OPTIONS.fileWizardAvailable.set(true);
		Common.OPTIONS.standardEditor.set(true);
		Common.OPTIONS.addTextDisplay.set(true);
		Common.OPTIONS.loadPoScreens.set(true);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

			    ParseArgs args = new ParseArgs(pgmArgs);
			    //JFrame.setDefaultLookAndFeelDecorated(true);

			   	boolean free = Common.isSetDoFree(false);

			   	try {
				    new FullEditor(args.getDfltFile(),
				            args.getInitialRow(),
				        	CopyBookDbReader.getInstance());
					//UpgradeDB.checkForUpdate(idx);

				    if (Common.IS_MAC && ReMainFrame.isUsingSystemLaf()) {
				    	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "RecordEditor");
				    }
			   	} finally {
			   		Common.setDoFree(free, Common.getConnectionIndex());
			   	}
			}
		});
	}
}
