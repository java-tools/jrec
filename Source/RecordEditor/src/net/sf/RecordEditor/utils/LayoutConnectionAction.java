/*
 * @Author Bruce Martin
 * Created on 12/01/2007
 *
 * Purpose:
 * This class performs an action on a LayoutConnection
 * (callback class to get/set layout information).
 * The actual action
 * performed is implemented by sub classing this class
 *
 * Version 0.61 (Bruce Martin)
 * - added setCallback to allow the Callback to be set after
 *   the creation of the class (part of fixing bug in displaying
 *   create layout menu in the full editor).
 */
package net.sf.RecordEditor.utils;

import javax.swing.AbstractAction;


/**
 * This class performs an action on a LayoutConnection
 * (callback class to get/set layout information).
 * The actual action
 * performed is implemented by sub classing this class
 *
 * @author Bruce Martin
 *
 */
public abstract class LayoutConnectionAction extends AbstractAction {

    private LayoutConnection callback;

    /**
     * This class performs an action on a LayoutConnection
     * (callback class to get/set layout information).
     * The actual action
     * performed is implemented by sub classing this class
     * @param name of Action
     * @param callbackClass dbDetails callback class
     */
    public LayoutConnectionAction(final String name,
            					  final LayoutConnection callbackClass) {
        super(name);
        callback = callbackClass;
     }



    /**
     * @return Returns the dbDetails.
     */
    public LayoutConnection getCallback() {
        return callback;
    }


    /**
     * @param newCallback The callback to set.
     */
    public void setCallback(LayoutConnection newCallback) {
        this.callback = newCallback;
    }
}
