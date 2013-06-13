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

import javax.swing.JTabbedPane;

import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.DisplayBuilderFactory;
import net.sf.RecordEditor.re.display.IChildDisplay;
import net.sf.RecordEditor.re.display.IDisplayFrame;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.FilterDetails;
import net.sf.RecordEditor.re.file.filter.FilterPnl2;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.screenManager.ReMainFrame;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.saveRestore.IUpdateDetails;



/**
 * Class to display / update Filter details
 * (i.e. which records are to be displayed).
 *
 * @author Bruce Martin
 *
 * @version 0.56
 */
@SuppressWarnings("serial")
public class FilterFrame extends ReFrame implements IUpdateDetails<EditorTask>, IChildDisplay {


 //   private static final int FORM_WIDTH = SwingUtils.STANDARD_FONT_WIDTH * 81;

    private FileView fileTable;

    private JTabbedPane filterTab = null;

    private FilterPnl2 filter1, filter2;

    private final IDisplayFrame<? extends AbstractFileDisplay> frame;
    private final AbstractFileDisplay sourceDisplay;


    private KeyAdapter listner = new KeyAdapter() {
        /**
         * @see java.awt.event.KeyAdapter#keyReleased
         */
        public final void keyReleased(KeyEvent event) {

        	switch (event.getKeyCode()) {
        	case KeyEvent.VK_ENTER:
        		if (! Common.TEST_MODE) {
        			if (filterTab == null || filterTab.getSelectedIndex() == 0) {
        				filter();
        			} else {
        				groupFilter();
        			}
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
    public FilterFrame(AbstractFileDisplay tab, final FileView fileTbl) {
        super(fileTbl.getFileNameNoDirectory(), "Filter Options",
                fileTbl.getBaseFile());

        fileTable = fileTbl;
        this.sourceDisplay = tab;
        this.frame = tab.getParentFrame();

        Rectangle screenSize = ReMainFrame.getMasterFrame().getDesktop().getBounds();

		//pnl.done();
		filter1 = new FilterPnl2(fileTable.getLayout(), FilterDetails.FT_NORMAL, this);
		filter1.getExecute().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter();
			}

		});
    	//if (! Common.TEST_MODE) {
    	filter1.addReKeyListener(listner);

    	if (fileTable.getLayout().getRecordCount() < 2) {
    		this.getContentPane().add(filter1);
    	} else {
    		filterTab = new JTabbedPane();
	    	filter2 = new FilterPnl2(fileTable.getLayout(), FilterDetails.FT_GROUP, this);
			filter2.getExecute().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					groupFilter();
				}

			});
	    	filter2.addReKeyListener(listner);

	    	filterTab.addKeyListener(listner);
	    	//}
	    	SwingUtils.addTab(filterTab, "Filter", "Normal Filter", filter1);
	    	SwingUtils.addTab(filterTab, "Filter", "Group Filter",  filter2);

			this.getContentPane().add(filterTab);
    	}

		this.pack();
		this.setSize(Math.min(this.getWidth(), screenSize.width - 2), Math.min(this.getHeight(), screenSize.height - 5));
		this.setVisible(true);
		this.setToMaximum(false);
    }


    private void filter() {

    	filterRecs(fileTable.getFilteredView(filter1.getFilter()), filter1);
    }

    private void groupFilter() {

    	filterRecs(
    			fileTable.getFilteredView(filter2.getFilter(), filter2.getGroupRecordId()),
    			filter2);
    }

    private void filterRecs(FileView fileView, FilterPnl2 filterPnl) {
    	if (fileView == null) {
    		filterPnl.getMessageFld().setText("No records matched the filter");
    	} else {
    		AbstractFileDisplay l = DisplayBuilderFactory.newLineList(frame, fileTable.getLayout(), fileView, fileTable.getBaseFile());
    		if (! Common.OPTIONS.useSeperateScreens.isSelected()) {
    			this.moveToBack();
    			l.getParentFrame().moveToFront();
    			l.getParentFrame().setToActiveFrame();
    		}
    		l.getParentFrame().setToActiveTab(l);
    	}
    }


    /**
     * @see net.sf.RecordEditor.utils.common.ReActionHandler#executeAction(int)
     */
    public void executeAction(int action) {

        if (action == ReActionHandler.HELP) {
            filter1.showHelp();
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
	 * @see net.sf.RecordEditor.re.file.filter.BaseFieldSelection#getFilter()
	 */
	public final FilterDetails getFilter() {
		if (filterTab != null && filterTab.getSelectedIndex() == 1) {
			return filter2.getFilter();
		}
		return filter1.getFilter();
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.swing.saveRestore.IUpdateDetails#setFromSavedDetails(net.sf.RecordEditor.jibx.compare.EditorTask)
	 */
	@Override
	public void setFromSavedDetails(EditorTask task) {
		if (task == null || task.filter == null) {
			Common.logMsg(UtMessages.NOT_A_FILTER.get(), null);
		} else {
			updateFromExternalLayout(task.filter);
		}
	}


	public final void update(EditorTask task) {
		setFromSavedDetails(task);
	}

	public final void updateFromExternalLayout(net.sf.RecordEditor.jibx.compare.Layout values) {
		if (filterTab == null) {
			filter1.update(values);
		} else if (values.groupHeader == null || "".equals(values.groupHeader)) {
			filter1.update(values);
			filterTab.setSelectedIndex(0);
		} else {
			filter2.update(values);

			filterTab.setSelectedIndex(1);
		}
	}



	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.edit.display.ChildDisplay#getSourceDisplay()
	 */
	@Override
	public AbstractFileDisplay getSourceDisplay() {
		return sourceDisplay;
	}
}
