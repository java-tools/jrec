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

package net.sf.RecordEditor.utils.openFile;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.HelpWindow;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * This class displays the open file dialog for the RecordEditor
 *
 * @author  bymartin
 * @version 0.56
 */
public abstract class AbstractOpenFilePnl
						<Layout extends AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail>> 
extends BaseHelpPanel
implements FocusListener {

	protected FileChooser fileName = new FileChooser();
	protected RecentFiles recent;

    private static final int FRAME_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 73;

	protected JTextArea   message     = new JTextArea();

	protected Layout fileDescription;
//	protected BaseDisplay display = null;

	private final AbstractLineIOProvider ioProvider;
//	private CopyBookInterface copyBookInterface;

	private Rectangle frameSize;
	private boolean doListener = true;
	private AbstractLayoutSelection<Layout> layoutSelection;
	
	private JButton lCreate1;
	private JButton lCreate2;
    //private final String recentFileName;
	


	private KeyAdapter listner = new KeyAdapter() {
		/**
		 * @see java.awt.event.KeyAdapter#keyReleased
		 */
		public final void keyReleased(KeyEvent event) {

			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				loadFile(false);
			}
		}
	};



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
	public AbstractOpenFilePnl(final String pInFile,
     	   final AbstractLineIOProvider pIoProvider,
     	   final JButton layoutCreate1,
     	   final JButton layoutCreate2,
     	   final String propertiesFiles,
     	   final String helpScreen,
     	   final AbstractLayoutSelection newLayoutSelection) {
		super();

		ioProvider = pIoProvider;
		recent = new RecentFiles(propertiesFiles, newLayoutSelection);
		layoutSelection = newLayoutSelection;
		lCreate1 = layoutCreate1;
		lCreate2 = layoutCreate2;
		
		layoutSelection.setMessage(message);

		init_100_RecentFiles(pInFile);
		init_200_ScreenFields();
		init_300_BldScreen(helpScreen);
	}

	/**
	 * Load recent files details from the properties file
	 * @param pInFile file to edit
	 */
	protected void init_100_RecentFiles(String pInFile) {
	    Common.setCurrClass(this);
	
		if (pInFile == null || "".equals(pInFile)) {
			//System.out.println("!! ~~ " + Common.DEFAULT_FILE_DIRECTORY);
		    fileName.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());
		} else {
		    fileName.setText(pInFile);
		    updateLayoutForFile(pInFile);
		}
	}
	/**
	 * Initialise Screen Fields
	 */
	private void init_200_ScreenFields() {

		Common.setBounds1(HelpWindow.HELP_FRAME);

//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//
//		HelpWindow.HELP_FRAME.setBounds(Common.getSpaceAtRightOfScreen(),
//		          Common.getSpaceAtTopOfScreen(),
//		          screenSize.width  - Common.getSpaceAtRightOfScreen()
//		          		- Common.getSpaceAtLeftOfScreen(),
//		          screenSize.height - Common.getSpaceAtBottomOfScreen()
//		          		- Common.getSpaceAtTopOfScreen());

		fileName.addFcFocusListener(this);
	}

	/**
	 * Build the screen
	 * @param helpScreen help screen name
	 * @param layoutCreate1 layout create button 1
	 * @param layoutCreate2 layout create button 2
	 */
	private void init_300_BldScreen(String helpScreen) {

		//BaseHelpPanel pnl = this;
		addReKeyListener(listner);

		addHelpBtn(Common.getHelpButton());
		setHelpURL(Common.formatHelpURL(helpScreen));
		//G:\RecordEdit_Prj\Docs\hlpRE_RecordMain.htm
	}
	
	/**
	 * Executed prior to display
	 */
	public void done() {
		
		setGap(BasePanel.GAP1);
		addFileName(this);
		setGap(BasePanel.GAP2);

		addLayoutSelection();

		addMessage(new JScrollPane(message));
		setHeight(BasePanel.NORMAL_HEIGHT * 4);


		//editOptCombo.addActionListener(this);
		//go.addActionListener(this);

		super.done();

		setBounds(getY(), getX(), FRAME_WIDTH, getHeight());
		frameSize = this.getBounds();
	}
	
	protected void addFileName(BaseHelpPanel pnl) {
		
		pnl.addLine("File", fileName, fileName.getChooseFileButton());
	}

	protected void addLayoutSelection() {
		
	    getLayoutSelection().addLayoutSelection(this, fileName, getGoPanel(), lCreate1, lCreate2);
		setGap(BasePanel.GAP2);
	}

	/**
	 * set the bounds to default
	 *
	 */
	public void setTheBounds() {
	    this.setBounds(frameSize);
	}




	/**
	 * loads a file from disk to memory
	 *
	 * @param pBrowse wether to browse (or edit the file)
	 */
	public void loadFile(final boolean pBrowse) {

		String sFileName = fileName.getText();

		if (sFileName.equals("")) {
		    String s = "invalid file ~ " + sFileName;
			Common.logMsg(s, null);
			message.setText(s);
		} else if ((!Common.OPTIONS.asterixInFileName.isSelected()) && (sFileName.indexOf('*') >= 0)) {
		    String s = "invalid file (* present) ~ " + sFileName;
			Common.logMsg(s, null);
			message.setText(s);
		} else {
			fileDescription = getLayoutSelection().getRecordLayout(sFileName);
			if (fileDescription != null) {
			    try {
			    	processFile(sFileName,
			    			fileDescription,
			    		    ioProvider,
			    		    pBrowse);
			    } catch (IOException ioe) {
			    	stdError(ioe);
			    } catch (RecordException e) {
			    	stdError(e);
			    } catch (Exception e) {
				    String s = "Error Loading File: " + e.getMessage();
				    Common.logMsg(s, e);
   					message.setText(s);
   					e.printStackTrace();
				} 
			}
		}
	}
	
	protected abstract 	void processFile(String sFileName,
			Layout layout,
		    AbstractLineIOProvider lineIoProvider,
		    boolean pBrowse) throws Exception ;
	
	private void stdError(Exception e) {
		
	    String s = "Error Loading File: " + e.getMessage();
	    Common.logMsg(s, null);
		message.setText(s);
		e.printStackTrace();
	}


    /**
     * Get the current layout details
     * @return current layout details
     */
    protected String getCurrentLayout() {
        return getLayoutSelection().getLayoutName();
    }


    /**
     * Update the layout selection
     * @param recentLayout layout details
     */
    protected void setLayout(String recentLayout) {
        getLayoutSelection().setLayoutName(recentLayout);
    }


    /**
     * @return Returns the message.
     */
    public JTextArea getMessage() {
        return message;
    }


    /**
     * @return Returns the goPanel.
     */
    protected abstract JPanel getGoPanel();


    /**
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    public void focusGained(FocusEvent e) {
 //   	System.out.println("Filename - gained focus");
    }


    /**
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    public void focusLost(FocusEvent e) {
    	//System.out.println("Filename - lost focus: " + e.isTemporary() + " " + e.paramString() + " " + e.getID());
    	if (doListener && (e == null || ! e.isTemporary())) {
    		updateLayoutForFile(fileName.getText());
    	}
    }


    /**
     * Get the layoutname for the file
     * @param pFile file to find the layout for
     */
    protected void updateLayoutForFile(String pFile) {
	    String recentLayout = recent.getLayoutName(pFile);

	    if (recentLayout != null && ! "".equals(recentLayout)) {
	    	setLayout(recentLayout);
	    	if (recent.isEditorLaunch()) {
	    	    loadFile(false);
	    	}
	    }
    }

	/**
	 * get The current file name
	 */
	public String getCurrentFileName() {
	    return fileName.getText();
	}
	
	
    /**
     * @return Returns the layoutSelection.
     */
    public AbstractLayoutSelection<Layout> getLayoutSelection() {
    	return layoutSelection;
    }

	/**
	 * @param doListener the doListener to set
	 */
	public final void setDoListener(boolean doListener) {
		this.doListener = doListener;
	}

	/**
	 * @param layoutSelection the layoutSelection to set
	 */
//	protected final void setLayoutSelection(AbstractLayoutSelection newLayoutSelection) {
//		this.layoutSelection = newLayoutSelection;
//	}

}