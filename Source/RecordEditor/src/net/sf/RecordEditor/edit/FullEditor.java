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

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuBar;

import net.sf.JRecord.External.CopybookLoaderFactory;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.copy.CopyDBLayout;
import net.sf.RecordEditor.diff.CompareDBLayout;
import net.sf.RecordEditor.edit.display.Action.ChangeLayoutAction;
import net.sf.RecordEditor.edit.display.Action.VisibilityAction;
import net.sf.RecordEditor.edit.util.CopybookLoaderFactoryDB;
import net.sf.RecordEditor.layoutEd.LayoutMenu;
import net.sf.RecordEditor.layoutEd.Record.RecordEdit1Record;
import net.sf.RecordEditor.layoutWizard.Wizard;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.CopyBookInterface;
import net.sf.RecordEditor.utils.edit.ParseArgs;
import net.sf.RecordEditor.utils.edit.ReIOProvider;
import net.sf.RecordEditor.utils.jdbc.AbsDB;
import net.sf.RecordEditor.utils.openFile.LayoutSelectionDB;
import net.sf.RecordEditor.utils.openFile.LayoutSelectionDBCreator;

/**
 * Extended RecordEditor with basic RecordLayout edit function.
 *
 * @author Bruce Martin
 *
 */
public class FullEditor extends EditRec {

    private LayoutMenu layoutMenu;

    /**
     * Start the main screen, the open file dialog needs to be
     * started seperately via setOpenFileWindow
     * methods
     */
    public FullEditor() {
        super(true);
    }

    /**
	 * Creating the File & record selection screen
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initinial Row (optional)
	 * @param pInterfaceToCopyBooks interface to copybooks
	 */
	public FullEditor(final String pInFile,
	        	      final int pInitialRow,
	        	      final CopyBookInterface pInterfaceToCopyBooks) {
		this(pInFile, pInitialRow, ReIOProvider.getInstance(), pInterfaceToCopyBooks);
	}

	/**
	 * Creating the File & record selection screenm
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initinial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybooks
	 */
    public FullEditor(final String pInFile,
     	   final int pInitialRow,
    	   final AbstractLineIOProvider pIoProvider,
    	   final CopyBookInterface pInterfaceToCopyBooks) {
        super(true);
        
//        try {
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//			e.printStackTrace();
//		}

        setupOpenWindow(pInFile, pInitialRow,
          	    pIoProvider,  pInterfaceToCopyBooks);
        
//        getFileMenu().add(new AbstractAction("Compare Menu") {
//
//			/**
//			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//			 */
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				CompareDBLayout.newMenu(pInterfaceToCopyBooks);
//			}
//        });
    }

    
    /**
     * Create the openFile window
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initinial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybooks
     */
    private void setupOpenWindow(final String pInFile,
      	   final int pInitialRow,
     	   final AbstractLineIOProvider pIoProvider,
     	   final CopyBookInterface pInterfaceToCopyBooks) {
        JButton createLayout = new JButton();
        JButton createLayoutWizard = new JButton();
        
        CopybookLoaderFactory.setInstance(new CopybookLoaderFactoryDB());
        
        OpenFile open = new OpenFile(pInFile, pInitialRow, pIoProvider,
                createLayoutWizard, createLayout,
                new LayoutSelectionDB(pInterfaceToCopyBooks, null, true));
        ChangeLayoutAction changeLayout = new ChangeLayoutAction(
     		   new LayoutSelectionDBCreator(pInterfaceToCopyBooks));
         
        createLayout.setAction(RecordEdit1Record.getAction(open.getOpenFilePanel()));
        createLayoutWizard.setAction(Wizard.getAction(open.getOpenFilePanel()));

        layoutMenu.setDatabaseDetails(open.getOpenFilePanel());
        
        super.getEditMenu().addSeparator();     
        super.getEditMenu().add(addAction(new VisibilityAction()));
        super.getEditMenu().addSeparator();
        super.getEditMenu().add(addAction(changeLayout));
       
        this.setOpenFileWindow(open,  
        		new AbstractAction("File Copy Menu") {

					/**
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						CopyDBLayout.newMenu(pInterfaceToCopyBooks);
					}
		        },
   				new AbstractAction("Compare Menu") {

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
	 * Edit a record oriented file
	 * @param pgmArgs program arguments
	 */
	public static void main(final String[] pgmArgs) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

			    ParseArgs args = new ParseArgs(pgmArgs);
			    //JFrame.setDefaultLookAndFeelDecorated(true);

			    new FullEditor(args.getDfltFile(),
			            //"C:\\Program Files\\RecordEdit\\MSaccess\\SampleFiles\\Ams_LocDownload_20041228.txt",
			            args.getInitialRow(),
			        	CopyBookDbReader.getInstance());
			}
		});
	}
}
