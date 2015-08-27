/**
 *
 */
package net.sf.RecordEditor.diff;

import javax.swing.JComponent;

import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.re.file.filter.BaseFieldSelection;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CmpFieldSelection extends BaseFieldSelection implements AbstractWizardPanel<DiffDefinition> {

	private DiffDefinition values;
	private AbstractLayoutSelection selection;
	private String lastLayoutName = "";
	private boolean toInit = true;

	/**
	 * @param layout
	 * @param addExecute
	 */
	public CmpFieldSelection(AbstractLayoutSelection layoutSelection) {
		super();

		selection = layoutSelection;
		//super.done();
		setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF_SL));
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		return this;
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public DiffDefinition getValues() throws Exception {
		String sold = values.layoutDetails.name;

		values.layoutDetails = super.getFilter().getExternalLayout();
		values.layoutDetails.name = sold;
		return values;
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
	 */
	@Override
	public void setValues(DiffDefinition detail) throws Exception {
		values = detail;

		if (! lastLayoutName.equalsIgnoreCase(values.layoutDetails.name)) {
			super.setRecordLayout(
					selection.getRecordLayout(values.layoutDetails.name),
					null, false,
					SwingUtils.NORMAL_FIELD_HEIGHT * 4);
			lastLayoutName = values.layoutDetails.name;
			toInit = true;
		}

		if (toInit) {
			super.getFilter().updateFromExternalLayout(values.layoutDetails);
			toInit = false;
		}
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#isMore()
	 */
	@Override
	public boolean skip() {
		return false;
	}
}
