/*
 * @Author Bruce Martin
 * Created on 4/01/2007
 *
 * Purpose:
 * Create Record Editor Action on the currently active screen
 * (ie screen with focus).
 */
package net.sf.RecordEditor.utils.screenManager;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.sf.RecordEditor.utils.common.Common;


/**
 * Create Record Editor Action on the currently active screen
 * (ie screen with focus).
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ReActionActiveScreen 
extends AbstractAction 
implements AbstractActiveScreenAction {

    private int actionId;
    
	private Object param = null;

    /**
     * Create Record Editor Action on the currently active screen
     * (ie screen with focus).
     *
     * @param actionIdentifier  id of the action to be performed
     */
    public ReActionActiveScreen(final int actionIdentifier) {
        this(Common.getReActionNames(actionIdentifier),
             Common.getReActionDescription(actionIdentifier),
             Common.getReActionIcon(actionIdentifier),
             actionIdentifier);
    }
    /**
     * Create Record Editor Action (no Icon)
     *
     * @param name Action name
     * @param description short description of action
     * @param actionIdentifier id of the action to be performed
    */
    public ReActionActiveScreen(final String name,
            final String description,
            final int actionIdentifier) {
        super(name);
        String s = description;

        actionId = actionIdentifier;
        if (description == null) {
            s = name;
        }
	    putValue(AbstractAction.SHORT_DESCRIPTION, s);

    }

    /**
     * Create Record Editor Action (Icon)
     *
     * @param name Action name
     * @param description short description of action
     * @param icon Action Icon
     * @param actionIdentifier id of the action to be performed
     */
    public ReActionActiveScreen(final String name,
            final String description,
            final Icon icon,
            final int actionIdentifier) {
        super(name, icon);


        actionId = actionIdentifier;
	    putValue(AbstractAction.SHORT_DESCRIPTION, description);
    }

    public ReActionActiveScreen(final String name, final int actionIdentifier, final Object paramValue) {
    	super(name);
    	param = paramValue;
    	actionId = actionIdentifier;
    }

    /**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#actionPerformed(java.awt.event.ActionEvent)
	 */
    public void actionPerformed(ActionEvent e) {

        ReFrame actionHandler = ReFrame.getActiveFrame();

        if (actionHandler != null) {
            actionHandler.executeAction(actionId, param);
        }

    }


    /**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
    public void checkActionEnabled() {

        ReFrame actionHandler = ReFrame.getActiveFrame();

        super.setEnabled(actionHandler != null && actionHandler.isActionAvailable(actionId));
        //if (actionHandler == null) System.out.print(" >> no active screen");
        //else System.out.print(" >> " + actionHandler.getClass().getName());
    }
    
	/**
	 * @return the param
	 */
	public Object getParam() {
		return param;
	}
}
