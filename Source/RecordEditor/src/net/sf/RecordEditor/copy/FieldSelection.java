/**
 *
 */
package net.sf.RecordEditor.copy;

import javax.swing.JComponent;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.file.filter.BaseFieldSelection;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.openFile.FormatFileName;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class FieldSelection extends BaseFieldSelection implements AbstractWizardPanel<CopyDefinition> {

	private CopyDefinition values;
	private String lastLayoutName1 = "";

	private AbstractLayoutSelection<?> selection1;


	private static final AbstractLayoutDetails<?, ?> layout2 = null;

	/**
	 * @param layoutSelection
	 */
	public FieldSelection(AbstractLayoutSelection<?> layoutSelection1,
			FormatFileName layoutSelection2,
			String helpName) {
		super();

		selection1 = layoutSelection1;

		if (helpName != null & ! "".equals(helpName)) {
			setHelpURL(Common.formatHelpURL(helpName));
		}
	}


	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public CopyDefinition getValues() throws Exception {
		String sold = values.oldFile.layoutDetails.name;

		values.oldFile.layoutDetails = super.getFilter().getExternalLayout();
		values.oldFile.layoutDetails.name = sold;

		return values;
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(net.sf.RecordEditor.jibx.compare.DiffDefinition)
	 */
	@Override
	public void setValues(CopyDefinition detail) throws Exception {

		values = detail;

		if (! lastLayoutName1.equalsIgnoreCase(values.oldFile.getLayoutDetails().name)) {
			super.setRecordLayout(selection1.getRecordLayout(values.oldFile.getLayoutDetails().name),
					layout2, false, SwingUtils.COMBO_TABLE_ROW_HEIGHT * 4);
			super.getFilter().updateFromExternalLayout(detail.oldFile.layoutDetails);

			lastLayoutName1 = values.oldFile.layoutDetails.name;
		}
	}


	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
	 */
	@Override
	public JComponent getComponent() {
		return this;
	}


	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#isMore()
	 */
	@Override
	public boolean skip() {
		return false;
	}
}
