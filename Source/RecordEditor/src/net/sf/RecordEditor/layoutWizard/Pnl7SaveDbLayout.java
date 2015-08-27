/**
 * 
 */
package net.sf.RecordEditor.layoutWizard;


import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;


/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl7SaveDbLayout extends WizardPanel {

	private Details wizardDetails;
//    private FileChooser     saveDirectoryFC   = new FileChooser();
//    private BmKeyedComboBox outputFormatCombo = new BmKeyedComboBox(
//    		new ManagerRowList(CopybookWriterManager.getInstance(), false), 
//    		false);
    private JTextField       layoutName = new JTextField();
    private JTextArea layoutDescription = new JTextArea();
    private BmKeyedComboBox      system = null;
	
	/**
	 * 
	 */
	public Pnl7SaveDbLayout(AbsRowList systemList) {


		this.setHelpURLre(Common.formatHelpURL(Common.HELP_WIZARD_SAVE));

		this.setGapRE(BasePanel.GAP2);

		this.addLineRE("Layout Name", layoutName);
		this.addLineRE("Layout Description", layoutDescription);
		this.setHeightRE(BasePanel.GAP3);
		this.setGapRE(BasePanel.GAP1);
		
		system         = new BmKeyedComboBox(systemList, false);
		this.addLineRE("System", system);


		this.setGapRE(BasePanel.GAP3);
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public Details getValues() throws Exception {
	    wizardDetails.layoutName = layoutName.getText();
        wizardDetails.layoutDescription = layoutDescription.getText();
      	wizardDetails.system = ((Integer) system.getSelectedItem()).intValue();

        if ("".equals(wizardDetails.layoutName)) {
            layoutName.requestFocus();
            throw new Exception("You must enter a layout name");
        }

		return wizardDetails;
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
	 */
	@Override
	public void setValues(Details detail) throws Exception {
		
		wizardDetails = detail;
		
        layoutName.setText(detail.layoutName);
        layoutDescription.setText(detail.layoutDescription);
    	system.setSelectedItem(Integer.valueOf(detail.system));
		
		if (detail.layoutWriterIdx < 0) {
			detail.layoutWriterIdx = Common.getCopybookWriterIndex();
		}
	}

}
