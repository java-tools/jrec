/**
 * 
 */
package net.sf.RecordEditor.layoutWizard;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.JRecord.External.CopybookWriterManager;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.edit.ManagerRowList;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl5SaveFileLayout extends WizardPanel {

	private Details wizardDetails;
    private FileSelectCombo     saveDirectoryFC   = new FileSelectCombo(Parameters.SCHEMA_DIRS_LIST, 15, false, true);
    		//new FileChooser(false);
    private BmKeyedComboBox outputFormatCombo = new BmKeyedComboBox(
    		new ManagerRowList(CopybookWriterManager.getInstance(), false), 
    		false);
    private JTextField       layoutName = new JTextField();
    private JTextArea layoutDescription = new JTextArea();


	
	/**
	 * 
	 */
	public Pnl5SaveFileLayout() {

//		saveDirectoryFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		String formDescription
	    = LangConversion.convertId(LangConversion.ST_MESSAGE, "FileWizard_4_csv",
	    		  "<h3>File Schema Save</h3>"
	    		+ "On this screen you enter the <i>Schema name</i> and directory.<br>"
	    		+ "This schema can be used edit files in the future,<br>"
	    		+ "Generate Code or by the JRecord library.");

		JEditorPane tips = new JEditorPane("text/html", formDescription);

		this.addComponentRE(1, 5, TIP_HEIGHT * 3 / 2, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		this.setGapRE(BasePanel.GAP2);

		this.addLineRE("File Schema Name", layoutName);
		this.addLineRE("Schema Description", layoutDescription);
		this.setHeightRE(BasePanel.GAP3);
		this.setGapRE(BasePanel.GAP1);
		this.addLineRE("Schema Directory", saveDirectoryFC);
		this.setGapRE(BasePanel.GAP1);
		this.addLineRE("Output Format", outputFormatCombo);
		this.setGapRE(BasePanel.GAP3);
		
		saveDirectoryFC.setDefaultDirectory(Common.OPTIONS.DEFAULT_COPYBOOK_DIRECTORY.getWithStar());
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
