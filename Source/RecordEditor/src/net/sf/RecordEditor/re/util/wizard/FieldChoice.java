/**
 *
 */
package net.sf.RecordEditor.re.util.wizard;


import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.jibx.compare.BaseCopyDif;
import net.sf.RecordEditor.re.file.filter.BaseFieldSelection;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.ComboBoxRender;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class FieldChoice<save extends BaseCopyDif> extends BaseFieldSelection
implements AbstractWizardPanel<save> {

	private save values;
	private String lastLayoutName1 = "";
	private String lastLayoutName2 = "";
	private AbstractLayoutSelection selection1;
	private AbstractLayoutSelection selection2;
	private boolean toInit = true;

	private AbstractLayoutDetails layout2 = null;

	/**
	 * @param layoutSelection
	 */
	public FieldChoice(AbstractLayoutSelection layoutSelection1,
			AbstractLayoutSelection layoutSelection2,
			String helpName) {
		super();

		selection1 = layoutSelection1;
		selection2 = layoutSelection2;
		if (helpName != null & ! "".equals(helpName)) {
			setHelpURL(Common.formatHelpURL(helpName));
		}
	}

    /**
     * Set Record Table
     * @param tbl table to update
     */
    protected void setFieldTableDetails(JTable tbl) {
        ComboBoxRender fieldRendor = new ComboBoxRender(
	  	        getFilter().getComboModelLayout2());
        TableColumn tc;

        setTableDetailsCol0(tbl);

		tc = tbl.getColumnModel().getColumn(1);
	  	tc.setCellRenderer(fieldRendor);
		tc.setCellEditor(new DefaultCellEditor(
			    new JComboBox(getFilter().getComboModelLayout2a())));
		tc.setPreferredWidth(FIELD_NAME_WIDTH);

		tbl.setRowHeight(FIELD_VALUE_ROW_HEIGHT);
    }

    /**
     * Set Record Table
     * @param tbl table to update
     */
    protected void setRecordTableDetails(JTable tbl) {

    	TableColumn tc;
    	setTableDetailsCol0(tbl);


    	tc = tbl.getColumnModel().getColumn(1);

    	tc.setCellRenderer(new ComboBoxRender(getFilter().getRecordOptions()));

    	tc.setCellEditor(new DefaultCellEditor(new JComboBox(getFilter().getRecordOptions())));

    	tc.setPreferredWidth(FIELD_NAME_WIDTH);

    	tbl.setRowHeight(FIELD_VALUE_ROW_HEIGHT);
    }


	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public save getValues() throws Exception {
		String sold = values.oldFile.layoutDetails.name;

		values.oldFile.layoutDetails = super.getFilter().getExternalLayout();
		values.oldFile.layoutDetails.name = sold;

		String snew = values.newFile.layoutDetails.name;

		values.newFile.layoutDetails = super.getFilter().getExternalLayout2();
		values.newFile.layoutDetails.name = snew;

		return values;
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(net.sf.RecordEditor.jibx.compare.DiffDefinition)
	 */
	@Override
	public void setValues(save detail) throws Exception {

		values = detail;

		if (! lastLayoutName2.equalsIgnoreCase(values.newFile.getLayoutDetails().name)) {
			lastLayoutName2 = values.newFile.layoutDetails.name;
			layout2 = selection2.getRecordLayout(values.newFile.name);
			toInit = true;
			if (layout2 == null) {
				throw new RecordException("Can not get layout for the New File");
			}
		}

		if (! lastLayoutName1.equalsIgnoreCase(values.oldFile.getLayoutDetails().name)) {
			super.setRecordLayout(selection1.getRecordLayout(values.oldFile.name),
					layout2, true,
					SwingUtils.NORMAL_FIELD_HEIGHT * 4);
			lastLayoutName1 = values.oldFile.layoutDetails.name;
			toInit = true;
		}

		if (toInit) {
			//System.out.println("Layout Name " + values.newFile.getLayoutDetails().name);
			super.getFilter().set2ndLayout(
					layout2,
					values.oldFile.layoutDetails,
					values.newFile.layoutDetails);

			setRecordTableDetails(super.recordTbl);
			toInit = false;
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
