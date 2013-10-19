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

package net.sf.RecordEditor.edit.open;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.openFile.OpenFileInterface;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * This class displays the open file dialog for the RecordEditor
 *
 * @author  bymartin
 * @version 0.56
 */
@SuppressWarnings("serial")
public class OpenFile extends ReFrame {

	private OpenFileInterface openFilePanel;
    private static final int FRAME_WIDTH  = SwingUtils.STANDARD_FONT_WIDTH * 72;

//	private BaseDisplay display = null;

	private Rectangle frameSize;




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
	public OpenFile(final String pInFile,
	        	   final int pInitialRow,
	        	   final AbstractLineIOProvider pIoProvider,
//	        	   final CopyBookInterface pInterfaceToCopyBooks,
	        	   final JButton layoutCreate1,
	        	   final JButton layoutCreate2,
	        	   final AbstractLayoutSelection selection) {
	    this(pInFile, pInitialRow, pIoProvider,
    		//pInterfaceToCopyBooks,
	         layoutCreate1, layoutCreate2,
	         Parameters.getApplicationDirectory() + "Files.txt",
	         Common.HELP_RECORD_MAIN,
	         selection);
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
	public OpenFile(final String pInFile,
     	   final int pInitialRow,
     	   final AbstractLineIOProvider pIoProvider,
//     	   final CopyBookInterface pInterfaceToCopyBooks,
     	   final JButton layoutCreate1,
     	   final JButton layoutCreate2,
     	   final String propertiesFiles,
     	   final String helpScreen,
     	   final AbstractLayoutSelection selection) {
		this(	new OpenFileEditPnl(pInFile,
						pInitialRow,
						pIoProvider,
						layoutCreate1,
						layoutCreate2,
						propertiesFiles,
						helpScreen,
						selection)
		);
	}

	public OpenFile(final OpenFileInterface openFile) {
		this(openFile, FRAME_WIDTH, -1);
	}

	public OpenFile(final OpenFileInterface openFile, int width, int height) {
		super("", "Open File", "", null);

//		copyBookInterface = pInterfaceToCopyBooks;

		openFilePanel = openFile;

		this.getContentPane().add(openFilePanel.getPanel());
		this.pack();

		ReMainFrame masterFrame = ReMainFrame.getMasterFrame();
		if (height < 0) {
			height = getHeight();
		}

		height = Math.min(height, masterFrame.getDesktopHeight() - 2);

		if (width > 0) {
			setBounds(1 /*getY()*/,  getX(), width, height);
		} else {
//			JDesktopPane desktop = masterFrame.getDesktop();
			Rectangle screenSize = masterFrame.getBounds();
//			Dimension preferredSize1 = desktop.getPreferredSize();

//			System.out.println("*===* " + getY() + " " + getX() + " " + (screenSize.width - 1) + " " + getHeight()
//					+ " " + screenSize.height + " " + masterFrame.isVisible()
//				    + " " + masterFrame.getClass().getName()
//					+ " " + preferredSize1.width + " "  + preferredSize1.height
//					+ " ~~~ " + desktop.getSize().width + " " + desktop.getHeight());

			//if (getWidth() > screenSize.width) {
				setBounds(getY(), getX(), screenSize.width  - 7, height);
			//}
		}
		frameSize = this.getBounds();

		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);


		setVisible(true);

		setToMaximum(false);

//		this.addFocusListener(
//				new FocusAdapter() {
//
//					/* (non-Javadoc)
//					 * @see java.awt.event.FocusAdapter#focusGained(java.awt.event.FocusEvent)
//					 */
//					@Override
//					public void focusGained(FocusEvent e) {
//						// TODO Auto-generated method stub
//						super.focusGained(e);
//						System.out.println("Frame Focus Gainded");
//					}
//
//					/* (non-Javadoc)
//					 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
//					 */
//					@Override
//					public void focusLost(FocusEvent e) {
//						// TODO Auto-generated method stub
//						super.focusLost(e);
//						System.out.println("Frame Focus lost");
//					}
//				}
//			);

	}



	/**
	 * set the bounds to default
	 *
	 */
	public final void setTheBounds() {
	    this.setBounds(frameSize);
	    this.setToMaximum(false);
	}


	/**
	 * @return the openFilePanel
	 */
	public final OpenFileInterface getOpenFilePanel() {
		return openFilePanel;
	}
}