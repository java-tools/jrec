package net.sf.RecordEditor.utils.wizards;

import javax.swing.JComponent;

public interface AbstractWizardPanel<Details> {

	   /**
     * Set panel fields from the provided detail record
     *
     * @param detail details to display
     *
     * @throws  Exception any errors
     */
    public abstract void setValues(Details detail) throws Exception;

    /**
     * Update the Details record with values on the screen
     *
     *
     * @param detail record to be updated
     *
     * @throws  Exception any errors
     */
    public abstract Details getValues() throws Exception;
    
    /**
     * Show the next panel ??
     * @return wether to show the next component
     */
    public abstract boolean skip();
    
    /**
     * Get object to display
     * @return display object
     */
    public abstract JComponent getComponent();
    
    /**
     * Show Help Screen ???
     */
    public abstract void showHelp();
    
}
