package net.sf.RecordEditor.re.util.fw;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.RecordEditor.layoutWizard.ColumnDetails;
import net.sf.RecordEditor.re.util.csv.CharsetDetails;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FixedWidthFieldSelection;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;

/**
 * This class implements a basic Fixed-Width Field Selection-View
 * 
 * @author Bruce Martin
 *
 */
public class FixedWidthSelectionView implements IFixedWidthView {

	public final BaseHelpPanel panel;
	
	private final FileSelectCombo fileCombo = new FileSelectCombo(Parameters.SCHEMA_LIST, 20, false, false);
	private final JCheckBox saveSchemaChk = new JCheckBox();
	private final FontCombo fontCombo = new FontCombo();

	private final FixedWidthFieldSelection fixedWidthSelection; 
	private final IUpdateableFileSummaryModel fileSummaryMdl;

	private ChangeListener fontChangeListner = new ChangeListener() {
		@Override public void stateChanged(ChangeEvent e) {
			fileSummaryMdl.setFontName(fontCombo.getText());
			reloadFromFileModel();
		}
	};

	/**
	 * This method creates a basic Fixed width view.
	 * 
	 * @param typeComboTree 
	 * @param fileDtls
	 * @param showFileCombo
	 * @param goBtn
	 * @return
	 */
	public static FixedWidthSelectionView newFWView(
			TreeComboItem[] typeComboTree, 
			IUpdateableFileSummaryModel fileDtls, boolean showFileCombo,
			JButton goBtn) {
		return new FixedWidthSelectionView(
				new BaseHelpPanel("fwFieldSelection"), 
				typeComboTree, fileDtls,
				new FixedWidthFieldSelection(typeComboTree, fileDtls), 
				showFileCombo, goBtn);
	}
	
//	public FixedWidthSelectionView(
//			TreeComboItem[] typeComboTree, 
//			IUpdateableFileSummaryModel fileDtls, boolean showFileCombo,
//			JButton goBtn) {
//		this(new BaseHelpPanel("fwFieldSelection"), typeComboTree, fileDtls, showFileCombo, goBtn);
//	}
	
	/**
	 * This class implements a basic Fixed-Width Field Selection-View
	 * @param panel
	 * @param typeComboTree
	 * @param fileDtls
	 * @param fixedWidthSelection
	 * @param showFileCombo
	 * @param goBtn
	 */
	public FixedWidthSelectionView(
			BaseHelpPanel panel, TreeComboItem[] typeComboTree,
			IUpdateableFileSummaryModel fileDtls, 
			FixedWidthFieldSelection fixedWidthSelection,
			boolean showFileCombo,
			JButton goBtn) {
		
		this.panel = panel;
		this.fixedWidthSelection = fixedWidthSelection;
		this.fileSummaryMdl = fileDtls;
		
		saveSchemaChk.setSelected(showFileCombo);
		if (showFileCombo) {
			panel.addLineRE("File Schema", fileCombo)
				 .addLineRE("Save Schema", saveSchemaChk)
				 	.setGapRE(BasePanel.GAP0);
		}
		
		panel.setFieldsToActualSize();
		panel.addLineRE("Font", fontCombo)
				.setFieldsToFullSize();
		
		if (goBtn != null) {
			JPanel p = new JPanel(new BorderLayout());
			p.add("East", goBtn);
			panel.addLineRE("", p);
		}
		
		panel.setGapRE(BasePanel.GAP1)
			 .addComponentRE(
					 1, 3, 
					 BasePanel.FILL, BasePanel.GAP,
                     BasePanel.FULL, BasePanel.FULL, fixedWidthSelection.getPane());
		
		fontCombo.addTextChangeListner(fontChangeListner);
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.fw.IFixedWidthDisplay#getPanel()
	 */
	@Override
	public BaseHelpPanel getPanel() {
		return panel;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.fw.IFixedWidthDisplay#getFileName()
	 */
	@Override
	public final String getSchemaFileName() {
		return fileCombo.getText();
	}
	
	@Override
	public final void reloadFromFileModel() {
		fixedWidthSelection.reloadFromFileModel();
		
		CharsetDetails charsetDetails = fileSummaryMdl.getCharsetDetails();
		fontCombo.removeTextChangeListner(fontChangeListner);
		try {
			fontCombo.setText(charsetDetails.charset);
		} finally {
			fontCombo.addTextChangeListner(fontChangeListner);
			fontCombo.setFontList(charsetDetails.likelyCharsets);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.fw.IFixedWidthDisplay#setFileName(java.lang.String)
	 */
	@Override
	public final void setSchemaFileName(String fileName) {
		fileCombo.setText(fileName);
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.fw.IFixedWidthDisplay#getFont()
	 */
	@Override
	public final String getFont() {
		return fontCombo.getText();
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.fw.IFixedWidthDisplay#getFieldSelection()
	 */
	@Override
	public final ColumnDetails[] getFieldSelection() {
		return fixedWidthSelection.getFieldSelection();
	}
 
	@Override
	public boolean isSaveSchemaSelected() {
		return saveSchemaChk.isSelected();
	}
}
