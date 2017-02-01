package net.sf.RecordEditor.re.openFile;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.re.util.csv.CsvTabPane;
import net.sf.RecordEditor.re.util.csv.FilePreview;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;


/**
 * Create Csv Schema (or layout) based on user input
 * 
 * @author Bruce Martin
 *
 */
public class LayoutSelectionCsv extends LayoutSelectionBasicGenerated  {

    private CsvTabPane csvPane = null;
    



//	/* (non-Javadoc)
//	 * @see net.sf.RecordEditor.re.openFile.AbstractLayoutSelection#addLayoutSelection(net.sf.RecordEditor.utils.swing.BasePanel, net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect, javax.swing.JPanel, javax.swing.JButton, javax.swing.JButton)
//	 */
//	@Override
//	public void addLayoutSelection(BasePanel pnl, TreeComboFileSelect file,
//			JPanel goPanel, JButton layoutCreate1, JButton layoutCreate2) {
//		addLayoutSelection(pnl, goPanel, layoutCreate1, layoutCreate2, null);
//	}
//
//	/**
//	 * Add Selection with external layout file
//	 * @param pnl panel to add fields to
//	 * @param goPanel go panel to use
//	 * @param layoutFile layout file
//	 */
//	public void addLayoutSelection(BasePanel pnl, JPanel goPanel, 	FileSelectCombo layoutFile) {
//
//		addLayoutSelection(pnl, goPanel, null, null, layoutFile);
//	}

	/**
	 * Add layout Selection to panel
	 * @param pnl panel to be updated
	 * @param goPanel panel holding go button
	 * @param layoutCreate1 layout create button1
	 * @param layoutCreate2  layout create button2
	 * @param layoutFile layout file
	 */
	protected void addLayoutSelection(BasePanel pnl, JPanel goPanel,
			JButton layoutCreate1, JButton layoutCreate2, FileSelectCombo layoutFile) {
  	    setupFields();

  	    pnl.addComponentRE(1, 5, BasePanel.FILL, BasePanel.GAP,
                BasePanel.FULL, BasePanel.FULL,
                csvPane.tab);


	}


	public void notifyFileNameChanged(String newFileName) {
		csvPane.readCheckPreview(new File(newFileName), true, null);
	}


	@Override
	public String getLayoutName() {
		FilePreview selectedCsvDetails = csvPane.getSelectedCsvDetails();
		return    "CsvSchema"
				+ SEPERATOR + csvPane.tab.getSelectedIndex()
				+ SEPERATOR + selectedCsvDetails.getFileDescription(); 
	}


	/**
	 * Get Layout details
	 * @return record layout
	 */
	@Override
	public final AbstractLayoutDetails getRecordLayout(String fileName) {
		FilePreview selectedCsvDetails = csvPane.getSelectedCsvDetails();
		return selectedCsvDetails.getLayout(selectedCsvDetails.getFontName(), null);
	}

//	/**
//	 * Get Layout details
//	 * @param layoutName record layout name
//	 * @return record layout
//	 */
//	@Override
//	public final AbstractLayoutDetails getRecordLayout(String layoutName, String fileName) {
//		setLayoutName(layoutName);
//		return getRecordLayout(fileName);
//	}



//	@Override
//	public boolean setLayoutName(String layoutName) {
//		return false;
//	}

	private void setupFields() {

		if (csvPane == null) {
			csvPane = new CsvTabPane(message, false, false, false, null);
			csvPane.setGoVisible(false);
		}
	}
}
