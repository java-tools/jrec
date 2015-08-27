/**
 *
 */
package net.sf.RecordEditor.copy;


import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.util.wizard.TwoLayoutsWizard;

/**
 * @author Bruce Martin
 *
 */
public class CopyTwoLayouts extends TwoLayoutsWizard<CopyDefinition> {

	private CopyWizardFinalPnl finalScreen;


	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 */
	public CopyTwoLayouts(AbstractLayoutSelection selection1, AbstractLayoutSelection selection2,
			String recentFiles) {
		this(selection1,
			 selection2,
			 new net.sf.RecordEditor.jibx.compare.CopyDefinition(),
			 recentFiles);
	}


	/**
	 * Create Single layout
	 * @param selection1 record layout selection class
	 * @param definition record filter definition
	 */
	public CopyTwoLayouts(AbstractLayoutSelection selection1, AbstractLayoutSelection selection2,
			CopyDefinition definition, String recentFiles) {
		super("Standard Copy", definition);

		finalScreen = new CopyWizardFinalPnl(selection1, selection2);

		definition.type = CopyDefinition.STANDARD_COPY;

		super.setUpPanels(selection1, selection2, recentFiles, finalScreen, "", false, true);
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#finished(java.lang.Object)
	 */
	@Override
	public void finished(CopyDefinition details) {

		if (finalScreen.isToRun()) {
			finalScreen.run();
		}
	}
}