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
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;


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
	    setKey();
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
	    setKey();
    }

    public ReActionActiveScreen(final String name, final int actionIdentifier, final Object paramValue) {
    	super(name);
    	param = paramValue;
    	actionId = actionIdentifier;
    	setKey();
    }


    private void setKey() {
    	KeyStroke k = null;

    	switch (actionId) {
    	case ReActionHandler.INSERT_RECORDS:
    		k = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,ActionEvent.CTRL_MASK);
    		break;
    	//case ReActionHandler.DELETE_RECORD: k = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);	break;
      	case ReActionHandler.FIND: 		k = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);	break;
        case ReActionHandler.SAVE:		k = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);	break;
        case ReActionHandler.SAVE_AS:
        	k = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK );
        	break;
    	case ReActionHandler.PRINT: 	k = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);	break;
    	case ReActionHandler.CLOSE_TAB: k = KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK);	break;
   	}

    	if ( k != null) {
    		this.putValue(Action.ACCELERATOR_KEY, k);
    	}
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
