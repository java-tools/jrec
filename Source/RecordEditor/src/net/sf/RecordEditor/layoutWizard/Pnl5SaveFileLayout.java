/**
 * 
 */
package net.sf.RecordEditor.layoutWizard;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl5SaveFileLayout extends WizardPanel {

	private Details wizardDetails;
    private FileChooser     saveDirectoryFC   = new FileChooser(false);
    private BmKeyedComboBox outputFormatCombo = new BmKeyedComboBox(
    		new ManagerRowList(CopybookWriterManager.getInstance(), false), 
    		false);
    private JTextField       layoutName = new JTextField();
    private JTextArea layoutDescription = new JTextArea();


	
	/**
	 * 
	 */
	public Pnl5SaveFileLayout() {

		saveDirectoryFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		this.setGap(BasePanel.GAP2);

		this.addLine("Layout Name", layoutName);
		this.addLine("Layout Description", layoutDescription);
		this.setHeight(BasePanel.GAP3);
		this.setGap(BasePanel.GAP1);
		this.addLine("Record Layout Directory", saveDirectoryFC, saveDirectoryFC.getChooseFileButton());
		this.setGap(BasePanel.GAP1);
		this.addLine("Output Format", outputFormatCombo);
		this.setGap(BasePanel.GAP3);
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public Details getValues() throws Exception {
	    wizardDetails.layoutName = layoutName.getText();
        wizardDetails.layoutDescription = layoutDescription.getText();
		wizardDetails.layoutDirectory = saveDirectoryFC.getText();
		wizardDetails.layoutWriterIdx = outputFormatCombo.getSelectedIndex();
		
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
		saveDirectoryFC.setText(detail.layoutDirectory);
		
		if (detail.layoutWriterIdx < 0) {
			detail.layoutWriterIdx = Common.getCopybookWriterIndex();
		}
		outputFormatCombo.setSelectedIndex(detail.layoutWriterIdx);
	}

}
