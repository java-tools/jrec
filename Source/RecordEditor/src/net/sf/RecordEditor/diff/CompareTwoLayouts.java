/**
 * 
 */
package net.sf.RecordEditor.diff;


import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.wizards.TwoLayoutsWizard;

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
	public CompareTwoLayouts(AbstractLayoutSelection<?> selection1, AbstractLayoutSelection<?> selection2,
			String recentFiles) {
		this(
			 selection1, 
			 selection2, 
			 new net.sf.RecordEditor.jibx.compare.DiffDefinition(), 
			 recentFiles);
	}
	
	
	/**
	 * Create Single layout
	 * @param selection1 record layout selection class
	 * @param definition record filter definition
	 */

	public CompareTwoLayouts(AbstractLayoutSelection<?> selection1, AbstractLayoutSelection<?> selection2, 
			DiffDefinition definition, String recentFiles) {
		super("Two Layout Compare", definition); 
		
		definition.type = DiffDefinition.TYPE_TWO_LAYOUTS;
		
		finalScreen = new CmpWizardFinal(selection1, selection2);
		
		super.setUpPanels(selection1, selection2, recentFiles, finalScreen, Common.HELP_DIFF_TL);
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