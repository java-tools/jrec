/*
 * @Author Bruce Martin
 * Created on 5/01/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.screenManager;

import java.awt.Dimension;

/**
 * This interface is for recieving notification of change of
 * focus of Forms
 *
 * @author Bruce Martin
 *
 */
public interface ReWindowChanged {

    /**
     * method to be called when the focus has changed
     *
     * @param newFrame frame receiving focus
     */
    public abstract void focusChanged(ReFrame newFrame);


    /**
     * New window created method
     * @param newFrame the new windo
     */
    public abstract void newWindow(ReFrame newFrame);

    /**
     * Called when a window has been deleted
     *
     * @param newFrame frame that was deleted
     */
    public abstract void deleteWindow(ReFrame newFrame);
    
    
    /**
     * Get a very short name for the application
     * @return  very short name for the application
     */
    public abstract String getApplicationId();
    
    
    /**
     * Get the current screen size
     * @return current screen size
     */
    public abstract Dimension getSize();
}
