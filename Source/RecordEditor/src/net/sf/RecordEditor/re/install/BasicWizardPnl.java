/**
 *
 */
package net.sf.RecordEditor.re.install;

import javax.swing.JComponent;

import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

/**
 * This class provides basic common wizard panel functionality,
 * it should be extended by specific implementations
 *
 * @author Bruce Martin
 *
 */
public abstract class BasicWizardPnl /*implements AbstractWizardPanel<InstallDetails>*/ {

	protected final BaseHelpPanel panel ;

	/**
	 * Define the main Wizard panel
	 * @param wizardPnl Wizard Panel
	 */
	protected BasicWizardPnl(BaseHelpPanel wizardPnl) {
		panel = wizardPnl;
	}




	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#skip()
	 */
	public boolean skip() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
	 */
	public final JComponent getComponent() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#showHelp()
	 */
	public final void showHelpRE() {
		panel.showHelpRE();
	}

}
