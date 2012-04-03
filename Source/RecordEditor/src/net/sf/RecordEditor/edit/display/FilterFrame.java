/*
 * @Author Bruce Martin
 * Created on 24/07/2005
 *
 * Purpose:
 *   Allow the user to create a filtered view of the file being
 *  editted.
 *
 * Changes
 * # Version 0.56 Bruce Martin 2007/01/16
 *   - changed to use ReFrame (from JFrame) which keeps track
 *     of the active form
 *   - changed to use ReActionHandler instead of ActionListener
 *   - add Check / uncheck fields buttons
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Changed to use standard ComboBoxRender (instead of internal version
 *
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Removed call to BasePanel.done() (done automatically)
 *   - hex and Text updates
 *   - Creating views from selected records
 *   - JRecord Spun off
 *
 * # Version 0.62 Bruce Martin 2007/04/30
 *   - adding support for enter key
 *
 * $ Version 0.63 create a seperate filterPnl than holds filter selection panel
 **/
package net.sf.RecordEditor.edit.display;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.FilterDetails;
import net.sf.RecordEditor.re.file.filter.FilterPnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * Class to display / update Filter details
 * (i.e. which records are to be displayed).
 *
 * @author Bruce Martin
 *
 * @version 0.56
 */
@SuppressWarnings("serial")
public class FilterFrame extends ReFrame {


    private static final int FORM_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 72;

    private FileView<?> fileTable;
    
    private FilterPnl pnl;

    
    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {
        	
        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:
        		if (! Common.TEST_MODE) {
        			filter();
        		}
        		break;	
        	case KeyEvent.VK_ESCAPE:	FilterFrame.this.doDefaultCloseAction();	break;
        	}
        }
    };


    /**
     * Display Filter on the screen for the user to update
     *
     * @param fileTbl file to be filtered
     */
    public FilterFrame(final FileView<?> fileTbl) {
        super(fileTbl.getFileNameNoDirectory(), "Filter Options",
                fileTbl.getBaseFile());

        fileTable = fileTbl;

        Rectangle screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();
        
		//pnl.done();
		pnl = new FilterPnl(fileTable.getLayout(), true);
		pnl.getExecute().addActionListener(new ActionListener() {

			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				filter();
			}
			
		});
    	//if (! Common.TEST_MODE) {
    		pnl.addReKeyListener(listner);
    	//}

    	
		
		this.getContentPane().add(pnl);
		this.pack();
		this.setSize(FORM_WIDTH, Math.min(this.getHeight(), screenSize.height - 5));
		this.setVisible(true);
		this.setToMaximum(false);
    }
    
    
    private void filter() {

    	FileView<?> fileView = fileTable.getFilteredView(pnl.getFilter());
    	if (fileView == null) {
    		pnl.getMessageFld().setText("No records matched the filter");
    	} else {
    		new LineList(fileTable.getLayout(), fileView, fileTable.getBaseFile());
    	}								

    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            pnl.showHelp();
        }
    }
    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#isActionAvailable(int)
     */
    public boolean isActionAvailable(int action) {
         return action == ReActionHandler.HELP;
    }


	/**
	 * @return
	 * @see net.sf.RecordEditor.re.file.filter.FilterPnl#getFilter()
	 */
	public final FilterDetails getFilter() {
		return pnl.getFilter();
	}
	
	
}
