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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.JRecord.External.base.ExternalConversion;
import net.sf.RecordEditor.re.openFile.RecentFiles;
import net.sf.RecordEditor.re.util.BuildTypeComboList;
import net.sf.RecordEditor.re.util.fw.FixedWidthSelectionView;
import net.sf.RecordEditor.re.util.fw.IUpdateableFileSummaryModel;
import net.sf.RecordEditor.re.util.fw.UpdateableFileSummayModel;
import net.sf.RecordEditor.utils.charsets.FontCombo;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeCombo;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboItem;

/**
 * 1st panel of the wizard where we get File and
 * Record Layout details
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl1FileX extends WizardPanel  {
	
//	private static final TreeComboItem[] TEXT_TYPE_COMBO;
	private static final TreeComboItem[] ALL_TYPE_COMBO;
	static {
		 AbsRowList rl = new AbsRowList(0, 1, false, false).loadData(ExternalConversion.getTypes(0));
//		 TEXT_TYPE_COMBO = BuildTypeComboList.getTextTypes(rl);
		 ALL_TYPE_COMBO = BuildTypeComboList.getList(rl);
	}

	
   private final String formDescription = LangConversion.convertId(LangConversion.ST_MESSAGE, "FileWizard_1",
				"This wizard will build a <b>Record-Layout</b> from "
				    	+ "a sample file. <p> you need to enter a<ol>"
				    	+ "<li>sample file in the new layout</li>"
				    	+ "<li>the file structure (use <b>Default Reader</b>"
				    	+      " for standard windows / Unix files) "
				    	+      " for unknown file format use <b>Unknown Format</b></li>"
				    	+ "<li>the new layout name</li>");
    private final JEditorPane tips = new JEditorPane("text/html", formDescription);
    private final TreeComboFileSelect filenameFld = new TreeComboFileSelect(true, false, true, RecentFiles.getMainRecentFile());


    private final ButtonGroup grp = new ButtonGroup();
	private final JRadioButton simpleFixedBtn 	= new JRadioButton("Simple Fixed Length Fields");
	private final JRadioButton simpleCsvBtn 	= new JRadioButton("Simple Delimited File");
	private final JRadioButton fixedLengthBtn 	= new JRadioButton("Fixed Length Fields");
	private final JRadioButton delimitedBtn 	= new JRadioButton("Delimited Fields");
	private final JRadioButton fixedMultiBtn 	= new JRadioButton("Multiple Records (fixed length)");
	private final JPanel       recordTypePnl	= new JPanel(new GridLayout(2,3));

	private BmKeyedComboBox fileStructure;

	private final FontCombo         fontname = new FontCombo();
//	private BmKeyedComboBox defaultType;
	private TreeCombo defaultType;
	
	private final IUpdateableFileSummaryModel fwModel;
	private FixedWidthSelectionView fwView;


    private Details wizardDetail;
    
    public static Pnl1FileX newPane(AbsRowList  structureList, AbsRowList typeList) {
    	IUpdateableFileSummaryModel mdl = new UpdateableFileSummayModel();
    	return new Pnl1FileX(structureList, typeList, mdl);
    }
 
    /**
     * Panel1 File Details
     *
     * @param connectionId connection Identifier
     */
    private Pnl1FileX(AbsRowList  structureList, AbsRowList typeList, IUpdateableFileSummaryModel mdl) {
        super();
        
        fwModel = mdl;

//		defaultType = new BmKeyedComboBox(typeList, false);
		defaultType = new TreeCombo(BuildTypeComboList.getList(typeList));

		fileStructure  = new BmKeyedComboBox(structureList, false);

		//ButtonGroup grp = new ButtonGroup();
		//JPanel recordTypePnl = new JPanel(new GridLayout(2,3));
		addRadioGrp(recordTypePnl, grp, simpleFixedBtn);
		addRadioGrp(recordTypePnl, grp, fixedLengthBtn);
		addRadioGrp(recordTypePnl, grp, fixedMultiBtn);
		addRadioGrp(recordTypePnl, grp, simpleCsvBtn);
		addRadioGrp(recordTypePnl, grp, delimitedBtn);
		//addRadioGrp(recordTypePnl, grp, delimitedUnicodeBtn);

		fixedLengthBtn.setSelected(true);

		layoutScreen();

		//this.done();
    }

    private void addListners() {
    	filenameFld.addTextChangeListner(new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    }

	private void layoutScreen() {


		this.setHelpURLre(Common.formatHelpURL(Common.HELP_WIZARD));
		this.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		this.setGapRE(BasePanel.GAP1);

		this.addLineRE("File Name", filenameFld)
			.addLineRE("File Structure", fileStructure)
				.setGapRE(BasePanel.GAP0)
			.addLineRE("Record Type", recordTypePnl)
				.setHeightRE(PREFERRED)
				.setGapRE(BasePanel.GAP0);

		if (simpleFixedBtn.isSelected()) {
			FixedWidthSelectionView.newFWView(ALL_TYPE_COMBO, fwModel, true, null);
		} else if (simpleCsvBtn.isSelected()) {
			
		} else {
			this.addLineRE("Font Name", fontname);
			this.addLineRE("Default Type", defaultType);
			this.setGapRE(BasePanel.GAP1);
		}
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
