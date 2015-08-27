/**
 * 
 */
package net.sf.RecordEditor.diff;


import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.util.wizard.TwoLayoutsWizard;
import net.sf.RecordEditor.utils.common.Common;

/**
 * @author Bruce Martin
 *
 */
public class CompareTwoLayouts extends TwoLayoutsWizard<DiffDefinition> {

	private CmpWizardFinal finalScreen;
	
	
	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 */
	public CompareTwoLayouts(AbstractLayoutSelection selection1, AbstractLayoutSelection selection2,
			String recentFiles, boolean lookupRecentLayouts) {
		this(
			 selection1, 
			 selection2, 
			 new net.sf.RecordEditor.jibx.compare.DiffDefinition(), 
			 recentFiles,
			 lookupRecentLayouts);
	}
	
	
	/**
	 * Create Single layout
	 * @param selection1 record layout selection class
	 * @param definition record filter definition
	 */

	public CompareTwoLayouts(AbstractLayoutSelection selection1, AbstractLayoutSelection selection2, 
			DiffDefinition definition, String recentFiles, boolean lookupRecentLayouts) {
		super("Two Layout Compare", definition); 
		
		definition.type = DiffDefinition.TYPE_TWO_LAYOUTS;
		
		finalScreen = new CmpWizardFinal(selection1, selection2, lookupRecentLayouts);
		
		super.setUpPanels(selection1, selection2, recentFiles, finalScreen, Common.HELP_DIFF_TL, true, lookupRecentLayouts);
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#finished(java.lang.Object)
	 */
	@Override
	public void finished(DiffDefinition details) {
		
		if (finalScreen.isToRun()) {
			finalScreen.run();
		}
	}
}