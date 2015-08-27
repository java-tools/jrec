/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * 1st panel of the wizard where we get File and
 * Record Layout details
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Name changes, remove done method call
 *
 * # Version 0.61b Bruce Martin 2007/05/05
 *  - Changes o support user selecting the default type
 *
 */
package net.sf.RecordEditor.layoutWizard;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.util.BuildTypeComboList;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;

/**
 * 1st panel of the wizard where we get File and
 * Record Layout details
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl1File extends WizardPanel  {
	
    private JEditorPane tips;
    private TreeComboFileSelect filenameFld = new TreeComboFileSelect(true, false, true, RecentFiles.getMainRecentFile());


	private JRadioButton fixedLengthBtn 	= new JRadioButton("Fixed Length Fields");
	private JRadioButton delimitedBtn 		= new JRadioButton("Delimited Fields");
	private JRadioButton fixedMultiBtn 		= new JRadioButton("Multiple Records (fixed length)");

	private BmKeyedComboBox fileStructure;

	private FontCombo         fontname = new FontCombo();
//	private BmKeyedComboBox defaultType;
	private TreeCombo defaultType;


    private Details wizardDetail;

    /**
     * Panel1 File Details
     *
     * @param connectionId connection Identifier
     */
    public Pnl1File(AbsRowList  structureList, AbsRowList typeList) {
        super();

//		defaultType = new BmKeyedComboBox(typeList, false);
		defaultType = new TreeCombo(BuildTypeComboList.getList(typeList));

		fileStructure  = new BmKeyedComboBox(structureList, false);

		String formDescription = LangConversion.convertId(LangConversion.ST_MESSAGE, "FileWizard_1",
				"This wizard will build a <b>Record-Layout</b> from "
		    	+ "a sample file. <p> you need to enter a<ol>"
		    	+ "<li>sample file in the new layout</li>"
		    	+ "<li>the file structure (use <b>Default Reader</b>"
		    	+      " for standard windows / Unix files) "
		    	+      " for unknown file format use <b>Unknown Format</b></li>"
		    	+ "<li>the new layout name</li>");
		tips = new JEditorPane("text/html", formDescription);

		ButtonGroup grp = new ButtonGroup();
		JPanel recordTypePnl = new JPanel(new GridLayout(2,2));
		addRadioGrp(recordTypePnl, grp, fixedLengthBtn);
		addRadioGrp(recordTypePnl, grp, delimitedBtn);
		//addRadioGrp(recordTypePnl, grp, delimitedUnicodeBtn);
		addRadioGrp(recordTypePnl, grp, fixedMultiBtn);

		fixedLengthBtn.setSelected(true);


		this.setHelpURLre(Common.formatHelpURL(Common.HELP_WIZARD));
		this.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		this.setGapRE(BasePanel.GAP1);

		this.addLineRE("File Name", filenameFld);
		this.addLineRE("File Structure", fileStructure);
		this.setGapRE(BasePanel.GAP0);
		this.addLineRE("Record Type", recordTypePnl);
		this.setHeightRE(PREFERRED);
		this.setGapRE(BasePanel.GAP0);

		this.addLineRE("Font Name", fontname);
		this.addLineRE("Default Type", defaultType);
		this.setGapRE(BasePanel.GAP1);
		//this.done();
    }


	private void addRadioGrp(JPanel pnl, ButtonGroup grp, JRadioButton btn1) {
		grp.add(btn1);

		pnl.add(btn1);
	}

    /**
     *
     */
    public final Details getValues() throws Exception {

        wizardDetail.filename   = filenameFld.getText();
        wizardDetail.fileStructure = ((Integer) fileStructure.getSelectedItem()).intValue();
 //       wizardDetail.layoutName = layoutName.getText();
        try {
//            wizardDetail.defaultType = (Integer) defaultType.getSelectedItem();
            wizardDetail.defaultType =  defaultType.getSelectedItem().key;
        } catch (Exception e) {
        }
        wizardDetail.fontName   = fontname.getText();


        if ("".equals(wizardDetail.filename)) {
            filenameFld.requestFocus();
            throw new Exception("You must enter a file name");
        }

        if (wizardDetail.fileStructure == Common.IO_NAME_1ST_LINE) {
        	delimitedBtn.setSelected(true);
        }

        wizardDetail.recordType = Details.RT_FIXED_LENGTH_FIELDS;
        if (delimitedBtn.isSelected()) {
        	wizardDetail.recordType = Details.RT_DELIMITERED_FIELDS;
        } else if (fixedMultiBtn.isSelected()) {
        	wizardDetail.recordType = Details.RT_MULTIPLE_RECORDS;
        }
        return wizardDetail;
    }

    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.layoutWizard.LayoutWizard.Details)
     */
    public final void setValues(Details detail) {

    	wizardDetail = detail;

        filenameFld.setText(detail.filename);
        fileStructure.setSelectedItem(Integer.valueOf(detail.fileStructure));

        fontname.setText(detail.fontName);
        defaultType.setSelectedKey(detail.defaultType);

    }
}
