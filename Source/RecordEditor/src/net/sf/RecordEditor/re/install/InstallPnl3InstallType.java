/**
 *
 */
package net.sf.RecordEditor.re.install;


import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;

import net.sf.RecordEditor.re.editProperties.CommonCode;
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
public class InstallPnl3InstallType extends BasicWizardPnl implements IInstallerPnl {

	private InstallDetails details;

	private static final JEditorPane tips = new JEditorPane(
    		"text/html",
    		LangConversion.convertId(LangConversion.ST_MESSAGE, "IP_TIP03",
    				"<h3>Type of H2 Database</h3>"
    			+	"<p>On this screen you specify the prefered DB connection method "
    			+	"(If in doubt, choose mixed Mode)"
    			+	"<br>Options are:<ul> "
    			+		"<li><b>Mixed Mode</b> - start in Embedded mode but allow other connect to the DB (like in server mode)<p/>"
    			+		"<li><b>Server Mode</b> - Connect to H2 Database Server."
    			+		"<li><b>Embedded Mode</b> - Connect to H2 Database in embedded (i.e. Exclusive access to DB)."
    			+	"</ul>"
    ));

	private final JRadioButton mixedModeBtn = new JRadioButton("H2 Mixed Mode");
	private final JRadioButton ServerBtn = new JRadioButton("H2 Server Mode");
	private final JRadioButton EmbeddedBtn = new JRadioButton("H2 Embedded Mode");


	public InstallPnl3InstallType() {
		super(new BaseHelpPanel("IP3_DestinationDb"));

		init_200_layoutScreen();
	}

	private void init_200_layoutScreen() {
		ButtonGroup grp = new ButtonGroup();

		panel.addComponent(1, 3, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		panel.addLine("Type of H2 DB connection:", null);

		init_220_addOption(grp, mixedModeBtn);
		init_220_addOption(grp, ServerBtn);
		init_220_addOption(grp, EmbeddedBtn);

		mixedModeBtn.setSelected(true);
	}

	private void init_220_addOption(ButtonGroup grp, JRadioButton btn) {

    	grp.add(btn);
    	panel.addLine((String) null, btn);
	}
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.install.BasicWizardPnl#skip()
	 */
	@Override
	public boolean skip() {
		return details.installType == InstallDetails.IT_USE_CURRENT_INSTALL;
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
