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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenu;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.diff.CompareSingleLayout;
import net.sf.RecordEditor.diff.CompareTwoLayouts;
import net.sf.RecordEditor.edit.display.Action.CsvUpdateLayoutAction;
import net.sf.RecordEditor.edit.display.Action.LoadFileLayoutFromXml;
import net.sf.RecordEditor.edit.display.Action.NewCsvAction;
import net.sf.RecordEditor.edit.display.Action.SaveFieldSequenceAction;
import net.sf.RecordEditor.edit.display.Action.SaveFileLayout2Xml;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.display.util.ErrorScreen;
import net.sf.RecordEditor.edit.open.OpenCsvFilePnl;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.edit.util.NewCsvFile;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.openFile.LayoutSelectionCsvCreator;
import net.sf.RecordEditor.re.openFile.LayoutSelectionPoTipCreator;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.CheckUserData;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swingx.TipsManager;




/**
 * This class will Edit a file with a File-Layout (instead of using a DB copybook)
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditCsvFile extends EditRec {

	private static final String RECENT_CSV_FILES = "CsvFiles.txt";
	private final OpenFile open;
	private final String recentFileName;
	
    private static boolean showTips = true;

	
	private ComponentAdapter resizeListner = new ComponentAdapter() {

		/* (non-Javadoc)
		 * @see java.awt.event.ComponentAdapter#componentResized(java.awt.event.ComponentEvent)
		 */
		@Override
		public void componentResized(ComponentEvent e) {
			//System.out.println("Resize ... " + EditCsvFile.super.getWidth());
			EditCsvFile.super.setLogFrameSize();
			open.setSize(EditCsvFile.super.getLogWidth(), getOpenHeight());
		}
	};

    /**
	 * Creating the File & record selection screen
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pInterfaceToCopyBooks interface to copybooks
	 */
	public EditCsvFile(final String pInFile,
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
    public EditCsvFile(final String pInFile,
     	   final int pInitialRow,
    	   final AbstractLineIOProvider pIoProvider) {
        super(false, "reCsv Editor", Common.CSV_PROGRAM_ID, new NewCsvAction(), true);
        
        JMenu compareMenu = new JMenu("Compare");
        JMenu newMenu = new JMenu("New Files");
        newMenu.add(new ReAbstractAction("New Csv file") { 		
 			@Override public void actionPerformed(ActionEvent e) {
 				new NewCsvFile();
 			}
 		});

        newMenu.add(new ReAbstractAction("New GetText-PO file") { 		
			@Override public void actionPerformed(ActionEvent e) {
				DisplayBuilderFactory.startEditorNewFile(net.sf.RecordEditor.po.def.PoLayoutMgr.getPoLayout());
			}
		});
        newMenu.add(new ReAbstractAction("New SwingX-Tip file") { 		
			@Override public void actionPerformed(ActionEvent e) {
				DisplayBuilderFactory.startEditorNewFile(net.sf.RecordEditor.tip.def.TipLayoutMgr.getTipLayout());
			}
		});

        recentFileName = Parameters.getApplicationDirectory() + RECENT_CSV_FILES;
        compareMenu.add(new ReAbstractAction("Csv Compare") { 
			@Override public void actionPerformed(ActionEvent e) {
				LayoutSelectionCsvCreator csvl = new LayoutSelectionCsvCreator();
				new CompareTwoLayouts(csvl.create(), csvl.create(), RECENT_CSV_FILES, false);
			}
		});
        compareMenu.add(new ReAbstractAction("Get-Text PO Compare") { 
 			@Override public void actionPerformed(ActionEvent e) {
 				LayoutSelectionPoTipCreator newPoCreator = LayoutSelectionPoTipCreator.newPoCreator();
 				new CompareSingleLayout("GetText-PO Compare Wizard -", newPoCreator.create(), RECENT_CSV_FILES);
 			}
 		});
        compareMenu.add(new ReAbstractAction("Swingx-Tip Compare") { 
 			@Override public void actionPerformed(ActionEvent e) {
 				LayoutSelectionPoTipCreator newTipCreator = LayoutSelectionPoTipCreator.newTipCreator();
 				new CompareSingleLayout("SwingX-Tip Compare Wizard -", newTipCreator.create(), RECENT_CSV_FILES);
 			}
 		});
       

//        long time = System.nanoTime();
//        System.out.println("EditCsvFile: Create OpenCsvFilePnl");
		OpenCsvFilePnl csvPnl = new OpenCsvFilePnl(
        				pInFile,
        				recentFileName,
        				pIoProvider,
        				true);

//        long time1 = System.nanoTime();
//        System.out.println("EditCsvFile: Create OpenFile");
		open = new OpenFile(csvPnl, super.getLogWidth(), getOpenHeight());

//	    System.out.println("EditCsvFile: Set Parent Frame");
        csvPnl.setParentFrame(open);
//        long time2 = System.nanoTime();
//	    System.out.println("EditCsvFile: Set Open Window");
//        super.setOpenFileWindow(open, null, new ReAbstractAction("Compare Menu") {
//				@Override public void actionPerformed(ActionEvent e) {
//					new MenuCsv(recentFileName);
////					LayoutSelectionCsvCreator csvl = new LayoutSelectionCsvCreator();
////					new CompareTwoLayouts(csvl.create(), csvl.create(), recentFileName, false);
//				}
//        	}, 
//        	newMenu, 
//        	false);
        
        super.setOpenFileWindow(open, null, compareMenu, newMenu, false);

        
//        long time3 = System.nanoTime();
//	    System.out.println("Time 2 (csv pnl) : " + ((time1 - time) / 100000000));
//	    System.out.println("Time 3 (open pnl): " + ((time2 - time1) / 100000000));
//	    System.out.println("Time 4 (set open): " + ((time3 - time2) / 100000000));


        super.getEditMenu().addSeparator();
        super.getEditMenu().add(addAction(new VisibilityAction()));
        super.getEditMenu().add(addAction(new SaveFieldSequenceAction()));
        super.getEditMenu().addSeparator();
        super.getEditMenu().add(addAction(new CsvUpdateLayoutAction()));
        super.getEditMenu().add(addAction(new SaveFileLayout2Xml()));
        super.getEditMenu().add(addAction(new LoadFileLayoutFromXml()));

        super.addComponentListener(resizeListner);
        
        
        if (Common.OPTIONS.showRecordEditorTips.isSelected() && TipsManager.tipsModulePresent()
        && (showTips)) {
        	TipsManager.startTips(this, Parameters.getSytemJarFileDirectory() + "/ReCsvEditor_TipOfTheDay.properties",
        					  Parameters.SHOW_RECORDEDITOR_TIPS);
        }
        
        //EditCommon.doStandardInit();
    }

	private int getOpenHeight() {
		int height = -1;
        int logHeight = getLogHeight();
        if (logHeight > 20) {
        	height = getDesktopHeight() - logHeight;// + SwingUtils.STANDARD_FONT_HEIGHT;
        }
		return height;
	}




	protected void addWebsitesToHelpMenu(JMenu helpMenu) {

		try {
			helpMenu.add(
					new ShowURI(
							"ReCsvEditor Documentations",
							Common.formatHelpURL("reCsvEdFR.htm").toURI()));
			helpMenu.add(
					new ShowURI(
							"HowTo Documentations",
							Common.formatHelpURL("howTo.htm").toURI()));
			helpMenu.addSeparator();
			helpMenu.add(new ShowURI("ReCsvEditor Web Page", new URI("http://recsveditor.sourceforge.net/")));
			helpMenu.add(new ShowURI("ReCsvEditor Forum", new URI("https://sourceforge.net/p/recsveditor/discussion/")));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReMainFrame#showAbout()
	 */
	@Override
	protected void showAbout() {
		String currentVersion = Common.currentVersion();
		showAbout(
				"The <b>reCsvEditor</b> (" + currentVersion + ")  is an editor for CSV "
			  + "data files. It is built on top of the RecordEditor.<br><br><pre>"
			  + "<br> <b>Version: </b> " + currentVersion +"<br>"
			  +	"<br> <b>Authors:</b><br> "
			  + "\t<b>Bruce Martin</b>: Main author<br/><br/>"

			  + " <b>Websites:</b><br><br> "
			  + "\t<b>reCsvEditor:</b> http://recsveditor.sourceforge.net<br>"
			  + "\t<b>RecordEditor:</b> http://record-editor.sourceforge.net<br>"

			  + "</pre><br>"
		);
	}

	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		try {
			Common.OPTIONS.addTextDisplay.set(true);
		    Common.OPTIONS.loadPoScreens.set(true);
		    Common.setDBstatus(false);
		    net.sf.JRecord.CsvParser.ParserManager.setUseNewCsvParsers(true);
		    
		    CheckUserData.setUseCsvLine();
			CheckUserData.checkAndCreate(CheckUserData.EXTRACT_USER, false, null);;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);

			    try {
					ParseArgs args = new ParseArgs(pgmArgs);
					Common.OPTIONS.overWriteOutputFile.set(args.isOverWiteOutputFile());
					//long time1 = System.nanoTime();
					
					String fileName = args.getDfltFile();
			   		showTips = fileName == null || "".equals(fileName);

					new EditCsvFile(fileName, args.getInitialRow(), ReIOProvider.getInstance());
					    	//new CopyBookDbReader());
					//long time2 = System.nanoTime();

					//System.out.println("Time 9: " + ((time2 - time1) / 100000000));
					
					CheckUserData.checkJavaVersion("ReCsvEditor");

//			    if (Common.IS_MAC && ReMainFrame.isUsingSystemLaf()) {
//			    	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "reCsvEditor");
//			    }
			   	} catch(Exception e) {
			   		new ErrorScreen("ReCsvEditor", e);
				}
			}
		});
	}
}
