/**
 *
 */
package net.sf.RecordEditor.re.install;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JRadioButton;

import net.sf.RecordEditor.re.editProperties.CommonCode;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;

/**
 * @author mum
 *
 */
public class InstallPnl1GetDataSource extends BasicWizardPnl implements IInstallerPnl {

	private static final String GENERIC_VERSION = "Generic";

	private static final String[] CROSS_PLATFORM_VERSIONS = {
		GENERIC_VERSION, "HSQLDB",
	};

	private static final String[] WINDOWS_VERSIONS = {
		GENERIC_VERSION, CROSS_PLATFORM_VERSIONS[1], "MSaccess", "HSQL",
	};

	private InstallDetails details;

	private List<List<String>> versions;
	private List<JRadioButton> versionBtns = new ArrayList<JRadioButton>();

	private static final JEditorPane tips = new JEditorPane(
    		"text/html",
    		LangConversion.convertId(LangConversion.ST_MESSAGE, "IP_TIP01",
    				"The <b>RecordEditor</b> uses a Database to store its <b>meta-data (Record Definitions)</b>."
    			+	"<br/>On this screen you specify where the <b>meta-data (Record Definitions)</b> should read from."
    			+	"<br/>The choices are:<ul>"
    			+		"<li>From an different installed version of the <b>RecordEditor</b>."
    			+		"<li>Using the existing <b>meta-data (Record Definitions)</b> from the current install."
    			+		"<li>Create a fresh <b>meta-data (Record Definitions)</b>."
    			+	"</ul>"
    		));


	public InstallPnl1GetDataSource(boolean existingInstall) {
		super(new BaseHelpPanel("IP1_Source"));

		init_200_layoutScreen(existingInstall);
	}

	private void init_200_layoutScreen(boolean existingInstall) {
		ButtonGroup grp = new ButtonGroup();

		versions = init_210_layoutScreen();

		panel.addComponent(1, 3, CommonCode.TIP_HEIGHT, BasePanel.GAP1,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		panel.addLine("Source of Record Definitions:", null);

		for (List<String> im : versions) {
			init_220_addOption(grp, "RecordEditor version - " + im.get(0));
		}

		if (existingInstall) {
			panel.setGap(BaseHelpPanel.GAP0);
			init_220_addOption(grp, "Use Existing Record-Definitions");
			versionBtns.get(versionBtns.size() -1).setSelected(true);
		}

		panel.setGap(BaseHelpPanel.GAP0);
		init_220_addOption(grp, "Create New Record-Definitions");
	}

	private List<List<String>> init_210_layoutScreen() {
		TreeSet<String> ts = new TreeSet<String>();
		String[] versions = CROSS_PLATFORM_VERSIONS;
		List<List<String>> ret = new ArrayList<List<String>>();
		if (Common.IS_WINDOWS) {
			ts.add("Program Files");
			ts.add("Program Files (x86)");
			addDir(ts, System.getenv("ProgramFiles"));
			addDir(ts, System.getenv("ProgramFiles(X86)"));

			versions = WINDOWS_VERSIONS;
		} else {
			ts.add("");
		}

		String baseDirectory = Parameters.getBaseDirectory();
		if (baseDirectory != null && ! "".equals(baseDirectory)) {
			File f = new File(baseDirectory);

			if (f.exists() && f.isDirectory()) {
				addDir(ts, f.getParent());
			}
		}

		for (String installDir : ts) {
			for (String version : versions) {
				File f = new File(installDir + Common.FILE_SEPERATOR + version + Common.FILE_SEPERATOR + "lib");
				if (f.isDirectory()) {
					ArrayList<String> versionLocation = new ArrayList<String>(2);
					String homeDir = null;
					versionLocation.add(version);
					versionLocation.add(f.getPath());

					if (GENERIC_VERSION.equals(version)) {
						homeDir = "<home>/.RecordEditor/GenericDB";
					}
					versionLocation.add(homeDir);

					ret.add(versionLocation);
				}
			}
		}
		return ret;
	}

	private void init_220_addOption(ButtonGroup grp, String name) {
		JRadioButton btn = new JRadioButton(name);

    	grp.add(btn);
    	panel.addLine((String) null, btn);

    	if (versionBtns.size() == 0) {
    		btn.setSelected(true);
    	}
    	versionBtns.add(btn);
	}

	private void addDir(TreeSet<String> ts, String s) {

		if (s != null) {
			ts.add(s);
		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.install.BasicWizardPnl#skip()
	 */
	@Override
	public boolean skip() {
		return versionBtns.size() < 2;
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

		for (int i = 0; i < versionBtns.size(); i++) {
			if (versionBtns.get(i).isSelected()) {
				details.installType = InstallDetails.IT_USE_CURRENT_INSTALL;
				if (i < versions.size()) {
					details.installType = InstallDetails.IT_COPY_FROM_EXISTING_INSTALL;

					details.oldInstallDir = versions.get(i).get(1);
					details.oldInstallHomeDir = versions.get(i).get(2);
				} else if (i == versionBtns.size() - 1) {
					details.installType = InstallDetails.IT_NEW_INSTALL;
				}
				break;
			}
		}

		return details;
	}
}
