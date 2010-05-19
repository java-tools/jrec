/*
 * RecEdit.java
 *
 * Created on 20 May 2004, 08:05
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - lookup Layout last used to edit a file (so the user does not have to
 *     retype it)
 *   - move layout selection to its own class so the code can be shared
 *     with the run velocity program
 *   - Added support of overiding methods (used by the cobol editor)
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Remove call to BasePanel.done()
 *   - JRecord Spun off, code reorg
 *   
 *
 * # Version 0.62 Bruce Martin 2007/04/30
 *   - adding support for enter key
 */

package net.sf.RecordEditor.edit.display;





import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPanel;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;

import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.edit.tree.LineNodeChild;
import net.sf.RecordEditor.edit.tree.TreeParserXml;

import net.sf.RecordEditor.utils.LayoutConnection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.openFile.AbstractOpenFilePnl;
import net.sf.RecordEditor.utils.openFile.RecentFilesList;



/**
 * This class displays the open file dialog for the RecordEditor
 *
 * @author  bymartin
 * @version 0.56
 */
public class OpenFileEditPnl<Layout extends AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail<FieldDetail>>> 
extends AbstractOpenFilePnl<Layout> 
implements ActionListener, LayoutConnection {
	
	private int initialRow;
	private JPanel goPanel = null; 

	private JButton browse;
	private JButton edit ;

	private JPanel nullComponent;
	
	private RecentFilesList recentList;

	/**
	 * Creating the File & record selection screenm
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initinial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybooks
	 * @param layoutCreate1 button 1 to create a layout
	 * @param layoutCreate2 button 2 to create a layout
	 */
	public OpenFileEditPnl(final String pInFile,
	        	   final int pInitialRow,
	        	   final AbstractLineIOProvider pIoProvider,
	        	   final JButton layoutCreate1,
	        	   final JButton layoutCreate2,
	        	   final AbstractLayoutSelection newLayoutSelection) {
	    this(pInFile, pInitialRow, pIoProvider, 
	         layoutCreate1, layoutCreate2,
	         Parameters.getApplicationDirectory() + "Files.txt",
	         Common.HELP_RECORD_MAIN,
	         newLayoutSelection);
	}

	/**
	 * Creating the File & record selection screenm
	 *
	 * @param pInFile File to be read (optional)
	 * @param pInitialRow initinial Row (optional)
	 * @param pIoProvider ioProvider to use when creating
	 *        lines
	 * @param pInterfaceToCopyBooks interface to copybooks
	 * @param layoutCreate1 button 1 to create a layout
	 * @param layoutCreate2 button 2 to create a layout
	 * @param propertiesFiles properties file holding the recent files
	 * @param helpScreen help screen to display
	 */
	public OpenFileEditPnl(final String pInFile,
     	   final int pInitialRow,
     	   final AbstractLineIOProvider pIoProvider,
     	   final JButton layoutCreate1,
     	   final JButton layoutCreate2,
     	   final String propertiesFiles,
     	   final String helpScreen,
     	   final AbstractLayoutSelection newLayoutSelection) {
		super(pInFile,
		     	   pIoProvider,
		     	   layoutCreate1,
		     	   layoutCreate2,
		     	   propertiesFiles,
		     	   helpScreen,
		     	   newLayoutSelection);
		
		initialRow = pInitialRow - 1;

		recentList = new RecentFilesList(recent, this);

//		getGoPanel();
//		browse.addActionListener(this);
//		edit.addActionListener(this);
	}

	//@Override
	public void processFile(String sFileName,
			Layout layoutDetails,
			AbstractLineIOProvider ioProvider,
			boolean pBrowse) throws Exception {
	
		FileView file = new FileView(sFileName,
				layoutDetails,
    			ioProvider,
    			pBrowse);
		AbstractLayoutDetails layoutDtls = file.getLayout();

		if (layoutDtls.hasChildren()) {
			display = new LineTreeChild(file, new LineNodeChild("File", file), true, 0);
			if (file.getRowCount() == 0 && ! pBrowse) {
				display.insertLine();
			}
		} else if (layoutDtls.isXml()) {
			display = new LineTree(file, TreeParserXml.getInstance(), true, 1);
		} else {
			display = new LineList(layoutDtls, file, file);
			display.setCurrRow(initialRow, -1, -1);
			
			if (file.getRowCount() == 0 && ! pBrowse) {
				display.insertLine();
			}
		}


		message.setText(file.getMsg());

		try {
			if ("".equals(file.getMsg())) {
				String layoutName = getCurrentLayout();
				//list.moveToFront();

				recent.putFileLayout(sFileName, layoutName);
				recentList.update();
			}
		} catch (Exception e) {
		    Common.logMsg("Error Updating recent files" + e.getMessage(), null);
		}

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == edit) {
			loadFile(false);
		} else if (e.getSource() == browse) {
			loadFile(true);
		}
	}

    /**
     * @return Returns the goPanel.
     */
    protected JPanel getGoPanel() {
    	if (goPanel == null) {
    		goPanel = new JPanel();
    		browse = new JButton("Browse", Common.getRecordIcon(Common.ID_OPEN_ICON));
    		edit   = new JButton("Edit", Common.getRecordIcon(Common.ID_OPEN_ICON));

    		nullComponent = new JPanel();
   		
    		goPanel.setLayout(new BorderLayout());
    		goPanel.add(BorderLayout.NORTH, edit);
    		goPanel.add(BorderLayout.CENTER, nullComponent);
    		goPanel.add(BorderLayout.SOUTH, browse);
    		
    		browse.addActionListener(this);
    		edit.addActionListener(this);
    	}
        return goPanel;
    }


    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentDbIdentifier()
     */
    public int getCurrentDbIdentifier() {
        return getLayoutSelection().getDatabaseIdx(); 
    }
    

    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#getCurrentDbName()
     */
    public String getCurrentDbName() {
        return getLayoutSelection().getDatabaseName(); 
    }

    
    /**
     * @see net.sf.RecordEditor.utils.LayoutConnection#setRecordLayout(int)
     */
    public void setRecordLayout(int recordId, String layoutName, String newFileName) {

    	super.setDoListener(false);
        AbstractLayoutSelection selection = getLayoutSelection();
        
        selection.reload();
        selection.setLayoutName(layoutName);
        
		if (newFileName != null && ! "".equals(newFileName)) {
		    fileName.setText(newFileName);
			loadFile(false);
		}
		super.setDoListener(true);
    }

	/**
	 * @return
	 * @see net.sf.RecordEditor.utils.openFile.RecentFilesList#getMenu()
	 */
	public final JMenu getRecentFileMenu() {
		return recentList.getMenu();
	}
}