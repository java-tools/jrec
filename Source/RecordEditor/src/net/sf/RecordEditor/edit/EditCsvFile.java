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

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.IO.AbstractLineIOProvider;

import net.sf.RecordEditor.edit.display.Action.CsvUpdateLayoutAction;
import net.sf.RecordEditor.edit.display.Action.LoadFileLayoutFromXml;
import net.sf.RecordEditor.edit.display.Action.NewCsvAction;
import net.sf.RecordEditor.edit.display.Action.SaveFieldSequenceAction;
import net.sf.RecordEditor.edit.display.Action.SaveFileLayout2Xml;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.open.OpenCsvFilePnl;
import net.sf.RecordEditor.edit.open.OpenFile;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.edit.ReIOProvider;


/**
 * This class will Edit a file with a File-Layout (instead of using a DB copybook) 
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class EditCsvFile extends EditRec {

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
        super(false, "reCsv Editor", new NewCsvAction());

        OpenFile open = new OpenFile(
        		new OpenCsvFilePnl(
        				pInFile,
        				Parameters.getApplicationDirectory() + "CsvFiles.txt",
        				pIoProvider),
        		Constants.NULL_INTEGER);
      
        super.setOpenFileWindow(open, null, null, false);
        
        super.getEditMenu().addSeparator();     
        super.getEditMenu().add(addAction(new VisibilityAction()));
        super.getEditMenu().add(addAction(new SaveFieldSequenceAction()));     
        super.getEditMenu().addSeparator();     
        super.getEditMenu().add(addAction(new CsvUpdateLayoutAction()));
        super.getEditMenu().add(addAction(new SaveFileLayout2Xml()));
        super.getEditMenu().add(addAction(new LoadFileLayoutFromXml()));
    }



//	/**
//	 * Add program specific dropdown menus
//	 * @param menubar top level menu
//	 */
//	protected void addProgramSpecificMenus(JMenuBar menubar) {
//
////		JMenu layoutMenu = new JMenu("Record Layouts");
////	    menubar.add(layoutMenu);
////	    layoutMenu.add(new AbstractAction("Layout Wizard") {
////	    	public void actionPerformed(ActionEvent e) {
////	    		new WizardFileLayout(getOpenFileWindow().getOpenFilePanel().getCurrentFileName());
////	    	}
////	    });
//	    super.addProgramSpecificMenus(menubar);
//	}



	/**
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//JFrame.setDefaultLookAndFeelDecorated(true);
			    ParseArgs args = new ParseArgs(pgmArgs);

			    new EditCsvFile(args.getDfltFile(), args.getInitialRow(), ReIOProvider.getInstance());
			        	//new CopyBookDbReader());
			}
		});
	}
}
