/*
 * @Author Bruce Martin
 * Created on 4/01/2007 for version 0.56
 *
 * Purpose:
 * Standard Action for record editor
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Added similified Constructor
 *     ReAction(final int actionIdentifier,
 *              final ReActionHandler handler)
 */
package net.sf.RecordEditor.utils.screenManager;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;

/**
 * Standard Action for record editor.
 *
 * @author Bruce Martin
 *
 */
public class ReAction extends AbstractAction {


    private int actionId;
    private ReActionHandler actionHandler;

    /**
     * Create Record Editor Action (no Icon)
     *
     * @param actionIdentifier id of the action to be performed
     * @param handler class to execute action
     * */
    public ReAction(final int actionIdentifier,
            final ReActionHandler handler) {
        this(Common.getReActionNames(actionIdentifier),
             Common.getReActionDescription(actionIdentifier),
             Common.getReActionIcon(actionIdentifier),
             actionIdentifier,
             handler);
    }

    /**
     * Create Record Editor Action (no Icon)
     *
     * @param name Action name
     * @param description short description of action
     * @param actionIdentifier id of the action to be performed
     * @param handler class to execute action
    */
    public ReAction(final String name,
            final String description,
            final int actionIdentifier,
            final ReActionHandler handler) {
        super(name);

        actionId = actionIdentifier;
        actionHandler = handler;
	    putValue(AbstractAction.SHORT_DESCRIPTION, description);
    }

    /**
     * Create Record Editor Action (Icon)
     *
     * @param name Action name
     * @param description short description of action
     * @param icon Action Icon
     * @param actionIdentifier id of the action to be performed
     * @param handler class to execute action
     */
    public ReAction(final String name,
            final String description,
            final Icon icon,
            final int actionIdentifier,
            final ReActionHandler handler) {
        super(name, icon);


        actionId = actionIdentifier;
        actionHandler = handler;
	    putValue(AbstractAction.SHORT_DESCRIPTION, description);
 }


    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        actionHandler.executeAction(actionId);

    }

    /**
     * @see javax.swing.Action#isEnabled()
     */
    public boolean isEnabled() {
        return actionHandler.isActionAvailable(actionId); // super.isEnabled();
    }


}
