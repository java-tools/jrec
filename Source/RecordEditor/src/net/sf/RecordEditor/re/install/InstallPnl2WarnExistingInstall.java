/**
 *
 */
package net.sf.RecordEditor.re.install;


import javax.swing.JEditorPane;

import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * This screen warns the user they are about to overwrite an existing RecordEditor
 * install !!!
 *
 * @author Bruce Martin
 *
 */
public class InstallPnl2WarnExistingInstall extends BasicWizardPnl implements IInstallerPnl {

	private InstallDetails details;

	private static final JEditorPane tips = new JEditorPane(
    		"text/html",
    		LangConversion.convertId(LangConversion.ST_MESSAGE, "IP_TIP02",
    				"<h1> ... Warning   ...  Warning  ...  Warning  ...</h1>"
    			+	"<p>You are about to overwrite an Existing <b>RecordEditor</b> install."
    			+	"<ul> "
    			+		"<li>If you are sure this is what you want to do, please continue.<p/>"
    			+		"<li>If unsure, return to the previous screen and choose the"
    			+		"<br/><b>Use Existing Record-Definitions</b> option>."
    			+	"</ul>"
    			+   "<h1> ... Warning   ...  Warning  ...  Warning  ...</h1>"
    		));


	public InstallPnl2WarnExistingInstall() {
		super(new BaseHelpPanel("IP2_Warn"));

		init_200_layoutScreen();
	}

	private void init_200_layoutScreen() {

		panel.addComponent(1, 3, BasePanel.FILL, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.install.BasicWizardPnl#skip()
	 */
	@Override
	public boolean skip() {
		return (! details.existingDefinition)
			|| details.installType == InstallDetails.IT_USE_CURRENT_INSTALL;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
	 */
	@Override
	public void setValues(InstallDetails detail) throws Exception {
		this.details = detail;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public InstallDetails getValues() throws Exception {
		return details;
	}
}
