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

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenu;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.edit.display.Action.CsvUpdateLayoutAction;
import net.sf.RecordEditor.edit.display.Action.LoadFileLayoutFromXml;
import net.sf.RecordEditor.edit.display.Action.NewCsvAction;
import net.sf.RecordEditor.edit.display.Action.SaveFieldSequenceAction;
import net.sf.RecordEditor.edit.display.Action.SaveFileLayout2Xml;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.open.OpenCsvFilePnl;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;



/**
 * This class will Edit a file with a File-Layout (instead of using a DB copybook)
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditCsvFile extends EditRec {

	private final OpenFile open;
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


//        long time = System.nanoTime();
        OpenCsvFilePnl csvPnl = new OpenCsvFilePnl(
        				pInFile,
        				Parameters.getApplicationDirectory() + "CsvFiles.txt",
        				pIoProvider,
        				true);

//        long time1 = System.nanoTime();
		open = new OpenFile(csvPnl, super.getLogWidth(), getOpenHeight());

        csvPnl.setParentFrame(open);
//        long time2 = System.nanoTime();
        super.setOpenFileWindow(open, null, null, false);
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
		showAbout(
				"The <b>reCsvEditor</b> is an editor for CSV "
			  + "data files. It is built on top of the RecordEditor.<br><br><pre>"
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
		    Common.OPTIONS.loadPoScreens.set(false);
		} catch (Exception e) {
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);

			    ParseArgs args = new ParseArgs(pgmArgs);
			    long time1 = System.nanoTime();

			    new EditCsvFile(args.getDfltFile(), args.getInitialRow(), ReIOProvider.getInstance());
			        	//new CopyBookDbReader());
			    long time2 = System.nanoTime();

			    System.out.println("Time 9: " + ((time2 - time1) / 100000000));

			    if (Common.IS_MAC && ReMainFrame.isUsingSystemLaf()) {
			    	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "reCsvEditor");
			    }
			}
		});
	}
}
