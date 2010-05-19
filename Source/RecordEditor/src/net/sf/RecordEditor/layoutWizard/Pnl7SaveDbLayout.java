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


		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD_SAVE));

		this.setGap(BasePanel.GAP2);

		this.addComponent("Layout Name", layoutName);
		this.addComponent("Layout Description", layoutDescription);
		this.setHeight(BasePanel.GAP3);
		this.setGap(BasePanel.GAP1);
		
		system         = new BmKeyedComboBox(systemList, false);
		this.addComponent("System", system);


		this.setGap(BasePanel.GAP3);
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
    	system.setSelectedItem(new Integer(detail.system));
		
		if (detail.layoutWriterIdx < 0) {
			detail.layoutWriterIdx = Common.getCopybookWriterIndex();
		}
	}

}
