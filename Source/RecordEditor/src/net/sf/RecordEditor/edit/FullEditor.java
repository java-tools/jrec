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
import javax.swing.JMenu;
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
import net.sf.RecordEditor.edit.display.util.ErrorScreen;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.layoutEd.LayoutMenu;
import net.sf.RecordEditor.layoutEd.schema.ImportExport.SchemaBackup;
import net.sf.RecordEditor.layoutWizard.Wizard;
import net.sf.RecordEditor.re.openFile.IOpenFileExtended;
import net.sf.RecordEditor.re.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.re.openFile.LayoutSelectionDBCreator;
import net.sf.RecordEditor.re.openFile.OpenFileInterface;
import net.sf.RecordEditor.re.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.re.util.UpgradeDB;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.CheckUserData;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swingx.TipsManager;

/**
 * Extended RecordEditor with basic RecordLayout edit function.
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class FullEditor extends EditRec {

    private LayoutMenu layoutMenu  = new LayoutMenu(null);


    private static FullEditor instance = null;
    private static boolean showTips = true;
    
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
		SchemaBackup.backupAllSchemas();
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
    	super("Record Editor");
//        super(true,
//        	  "Record Editor",
//        	  new CsvOpenAction(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get(), pIoProvider),
//        	  new NewFileAction(
//        			  new LayoutSelectionDBCreator(pInterfaceToCopyBooks)
//        	  ),
//        	  new NewCsvAction("New Basic Csv file")
//        );
        initMenusToolBars(true,
        	  new CsvOpenAction(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get(), pIoProvider),
        	  new NewFileAction(
        			  new LayoutSelectionDBCreator(pInterfaceToCopyBooks)
        	  ),
        	  new NewCsvAction("New Basic Csv file"), 
        	  layoutMenu.getStartSchemaAction());

        setupOpenWindow(pInFile, pInitialRow,
          	    pIoProvider,  pInterfaceToCopyBooks);

        
        if (Common.OPTIONS.showRecordEditorTips.isSelected() && TipsManager.tipsModulePresent()
        && (showTips)) {
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

        JMenu editMenu = super.getEditMenu();
		editMenu.addSeparator();
        editMenu.add(addAction(new VisibilityAction()));
        editMenu.add(addAction(new SaveFieldSequenceAction()));
        editMenu.addSeparator();
        editMenu.add(addAction(changeLayout));
        editMenu.add(addAction(new CsvUpdateLayoutAction()));

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
		        null,
		        true
       );
       AbsDB.setSystemLog(super.getLog());
   }


	/**
	 * Add program specific dropdown menus
	 * @param menubar top level menu
	 */
	protected void addProgramSpecificMenus(JMenuBar menubar) {

//	    layoutMenu = new LayoutMenu(null);
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
	
	public static void startEditor(String fileName, String schemaName, int initialRow) {
		startEditor(new ParseArgs(fileName, schemaName, initialRow));
	}
	
	public static void startEditor(final ParseArgs... args) {
		
		try {
			Common.OPTIONS.fileWizardAvailable.set(true);
			Common.OPTIONS.standardEditor.set(true);
			Common.OPTIONS.addTextDisplay.set(true);
			Common.OPTIONS.loadPoScreens.set(true);	
			net.sf.JRecord.CsvParser.ParserManager.setUseNewCsvParsers(true);
			
			CheckUserData.checkAndCreate();
			CheckUserData.setUseCsvLine();
			if (args != null && args.length > 0) {
				Common.OPTIONS.overWriteOutputFile.set(args[0].isOverWiteOutputFile());
				Parameters.loadParamProperties(args[0].getParamFilename());
			}
		} catch (Throwable e1) {
			e1.printStackTrace();
		}


		if (args != null && args.length > 0) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
				   	boolean free = Common.isSetDoFree(false);
	
				   	try {
				   		String fileName = args[0].getDfltFile();
				   		showTips = fileName == null || "".equals(fileName);
				   		if (! "".equals(args[0].getSchemaName())) {
				   			fileName = "";
				   		}
				   		instance = new FullEditor(
				   				fileName,
					            args[0].getInitialRow(),
					        	CopyBookDbReader.getInstance());
						//UpgradeDB.checkForUpdate(idx);
	
	//				    if (Common.IS_MAC && ReMainFrame.isUsingSystemLaf()) {
	//				    	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "RecordEditor");
	//				    }
					    
				   		
				   		if (instance.getOpenFileWindow() != null) {
					    	OpenFileInterface openFilePanel = instance.getOpenFileWindow().getOpenFilePanel();
					    	IOpenFileExtended openFileExtended = null;
					    	String[] dbs = Common.getSourceId();
					    	String db;
					    	if (openFilePanel instanceof IOpenFileExtended) {
					    		openFileExtended = (IOpenFileExtended) openFilePanel;
					    	}
						    for (int i = 0; i < args.length; i++) {
						    	db = args[i].getDb();
						    	if (openFileExtended != null && ! "".equals(db)) {
						    		for (int j = 0; j < dbs.length; j++) {
										if (db.equalsIgnoreCase(dbs[j])) {
											openFileExtended.getLayoutSelection().setDatabaseIdx(j);
										}
									}
						    	}
							    if (! "".equals(args[i].getSchemaName())) {
									openFilePanel.setRecordLayout(-1, args[i].getSchemaName(), args[i].getDfltFile());
							    }
						   	}
				   		}
				   	} catch(Exception e) {
				   		new ErrorScreen("RecordEditor", e);
				   	} finally {
				   		CheckUserData.checkJavaVersion("RecordEditor");
				   		Common.setDoFree(free, Common.getConnectionIndex());
				   	}
				}
			});
		}
	}

	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {
		startEditor(new ParseArgs(pgmArgs));
	}
}
