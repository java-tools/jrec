package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.script.IDisplayFrame;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;

public class DockingPopupListner extends MouseAdapter {
    private final AbstractFileDisplay pnl;


    /**
     * @param table table being watched
     * @param rowChangeNotify class to notify of the current row;
     */
    public DockingPopupListner(AbstractFileDisplay panel) {
        this.pnl = panel;
    }

    /**
     * @see MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        if (e == null) {
        } else if (e.isPopupTrigger()) {
                int x = e.getX(),
                    y = e.getY();

                getPopup().show(e.getComponent(), x, y);
       } else {
    	   pnl.getParentFrame().setToActiveTab(pnl);
       }
    }


    /* (non-Javadoc)
     * @see net.sf.RecordEditor.utils.MenuPopupListener#getPopup()
     */
    public JPopupMenu getPopup() {

        JPopupMenu ret = new JPopupMenu();
        @SuppressWarnings("rawtypes")
		IDisplayFrame displFrame = pnl.getParentFrame();
        int idx = displFrame.indexOf(pnl);


        addAction(ret, displFrame, idx, ReActionHandler.CLOSE_TAB);

        if (displFrame.isActionAvailable(ReActionHandler.ADD_CHILD_SCREEN_RIGHT)) {
          	addAction(ret, displFrame, idx, ReActionHandler.ADD_CHILD_SCREEN_RIGHT);
          	addAction(ret, displFrame, idx, ReActionHandler.ADD_CHILD_SCREEN_BOTTOM);
        } else {
        	addAction(ret, displFrame, idx, ReActionHandler.ADD_CHILD_SCREEN);
        }
       	addAction(ret, displFrame, idx, ReActionHandler.ADD_CHILD_SCREEN_SWAP);

        addAction(ret, displFrame, idx, ReActionHandler.REMOVE_CHILD_SCREEN);

        if (displFrame.getScreenCount() > 1) {
            //AbstractAction undock;
            addAction(ret, displFrame, idx, ReActionHandler.UNDOCK_TAB);
            addAction(ret, displFrame, idx, ReActionHandler.UNDOCK_ALL_TABS);
        }
        addAction(ret, displFrame, idx, ReActionHandler.DOCK_ALL_SCREENS);

        return ret;
    }

    @SuppressWarnings("rawtypes")
	private void addAction(JPopupMenu ret, IDisplayFrame displFrame, int idx, int action) {

        if (displFrame.isActionAvailable(idx, action)) {
            ret.add(new LocalAction(displFrame, idx, action));
        }
    }


	@SuppressWarnings("serial")
	private static class LocalAction extends AbstractAction {
	    @SuppressWarnings("rawtypes")
		private final IDisplayFrame displFrame;
	    private final int idx, action;


	    public LocalAction(@SuppressWarnings("rawtypes") final IDisplayFrame displFrame, int idx, int action) {
	        super(Common.getReActionNames(action));
	        this.displFrame = displFrame;
	        this.idx = idx;
	        this.action = action;

	        putValue(AbstractAction.SHORT_DESCRIPTION, Common.getReActionDescription(action));
	    }


	    public void actionPerformed(ActionEvent e) {
	        displFrame.executeAction(idx, action);
	    }
	}
}
