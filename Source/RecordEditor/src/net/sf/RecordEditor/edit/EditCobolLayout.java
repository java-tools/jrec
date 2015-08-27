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
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.copy.Menu;
import net.sf.RecordEditor.edit.display.Action.NewFileAction;
import net.sf.RecordEditor.edit.display.Action.SaveFieldSequenceAction;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.edit.open.OpenFileEditPnl;
import net.sf.RecordEditor.layoutWizard.WizardFileLayout;
import net.sf.RecordEditor.re.openFile.LayoutSelectionCobolAlt;
import net.sf.RecordEditor.re.openFile.LayoutSelectionCobolCreator;
import net.sf.RecordEditor.re.util.ReIOProvider;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.lang.ReAbstractAction;
import net.sf.RecordEditor.utils.params.CheckUserData;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * This class will Edit a file with a File-Layout (instead of using a DB copybook)
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditCobolLayout extends EditRec {

    private static final String COBOL_FILES_TXT = "Cobol2Files.txt";


	/**
	 * Creating the File & record selection screen
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initial Row (optional)
	 * @param pInterfaceToCopyBooks interface to copybooks
	 */
	public EditCobolLayout(final String pInFile,
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
    public EditCobolLayout(final String pInFile,
     	   final int pInitialRow,
    	   final AbstractLineIOProvider pIoProvider) {
        super(false,
        	  "Record Editor - Cobol",
        	  new NewFileAction(
        			  new LayoutSelectionCobolCreator()
        	  ));

        Common.OPTIONS.standardEditor.set(true);
		Common.OPTIONS.addTextDisplay.set(true);
	    Common.setDBstatus(false);

        OpenFile open = new OpenFile(
        		OpenFileEditPnl.NEW_FILE_FORMAT,
        		pInFile, pInitialRow, pIoProvider,
        		//pInterfaceToCopyBooks,
                null, null, Parameters.getApplicationDirectory() + COBOL_FILES_TXT,
                Common.HELP_COBOL_EDITOR, new LayoutSelectionCobolAlt());

        this.setOpenFileWindow(open,
        		new ReAbstractAction("File Copy Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						 new Menu(new LayoutSelectionCobolCreator(), COBOL_FILES_TXT);
					}
        		},
				new ReAbstractAction("Compare Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						new Menu(new LayoutSelectionCobolCreator(), COBOL_FILES_TXT);
					}
				},
				null,
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



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.screenManager.ReMainFrame#addWebsitesToHelpMenu(javax.swing.JMenu)
	 */
	@Override
	protected void addWebsitesToHelpMenu(JMenu helpMenu) {
		try {
			helpMenu.add(
					new ShowURI(
							"JRecord Pages",
							Common.formatHelpURL("index.htm").toURI()));
			helpMenu.add(
					new ShowURI(
							"JRecord Documentation",
							Common.formatHelpURL("Document.html").toURI()));
			helpMenu.addSeparator();
			helpMenu.add(new ShowURI("JRecord Web Page", new URI("http://jrecord.sourceforge.net/")));
			helpMenu.add(new ShowURI("JRecord Forum", new URI("https://sourceforge.net/projects/jrecord/forums")));
			helpMenu.add(new ShowURI("RecordEditor Web Page", new URI("http://record-editor.sourceforge.net/")));
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
				"This is the Cobol-Editor, it is part of the <b>RecordEditor</b> project<br/> "
			  + "It is distributed under a GPL 3 (or later) license<br/><pre>"
			  +	" <br><b>Authors:</b><br><br> "
			  + "\t<b>Bruce Martin</b>: Main author<br>"
			  + "\t<b>Jean-Francois Gagnon</b>: Provided Fujitsu IO / Types<br><br>"
			  + " <b>Associated:</b><br><br> "
			  + "\t<b>Peter Thomas</b>: Wrote the <b>cb2xml</b> which provides the cobol interface<br/><br/> &nbsp; "
			  + " <b>Websites:</b><br><br> "
			  + "\t<b>RecordEditor:</b> http://record-editor.sourceforge.net<br>"
			  + "<br><br>"
			  + "<b>Packages Used:</b><br/>"
			  + "\t<b>cb2xml<b>:\t\tCobol Copybook Analysis<br/>"
			  + "\t<b>jibx<b>:\t\tXml Bindings<br/>"
			  + "\t<b>TableLayout<b>:\tSwing Layout manager used<br>"
			  + "\t<b>jlibdif<b>:\tFile Compare<br/>"
			  + "\t<b>RSyntaxTextArea<b>\tScript Editting Copyright (c) 2012, Robert Futrell"
		);
	}


	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		try {
			Common.OPTIONS.fileWizardAvailable.set(true);
			Common.OPTIONS.standardEditor.set(true);
			Common.OPTIONS.addTextDisplay.set(true);
			net.sf.JRecord.CsvParser.ParserManager.setUseNewCsvParsers(true);

			CheckUserData.setUseCsvLine();
			CheckUserData.checkAndCreate();
		} catch (Throwable e) {
			e.printStackTrace();
		}


		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);
			    ParseArgs args = new ParseArgs(pgmArgs);

			    new EditCobolLayout(args.getDfltFile(), args.getInitialRow(), ReIOProvider.getInstance());
			        	//new CopyBookDbReader());
			}
		});
	}
}
