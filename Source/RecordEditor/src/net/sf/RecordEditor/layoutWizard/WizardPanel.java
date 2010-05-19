/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * Base Wizard panel, all actual Wizard panels extend this
 * class
 */
package net.sf.RecordEditor.layoutWizard;

import javax.swing.JComponent;

import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * Base Wizard panel, all actual Wizard panels extend this
 * class
 *
 * @author Bruce Martin
 *
 */
public abstract class WizardPanel extends BaseHelpPanel implements AbstractWizardPanel<Details> {

    public static final int TIP_HEIGHT  = 130;
   
    
    /**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		return this;
	}

	/**
     * weather to continue on to the next wizard Screen or not
     * @return wether to continue to the next screen
     */
    public boolean skip() {
    	return false;
    }
}
