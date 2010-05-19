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
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.BmKeyedComboBox;
import net.sf.RecordEditor.utils.swing.FileChooser;

/**
 * 1st panel of the wizard where we get File and
 * Record Layout details
 *
 * @author Bruce Martin
 *
 */
public class Pnl1File extends WizardPanel  {

    private JEditorPane tips;
    private FileChooser     filenameFld = new FileChooser();


	private JRadioButton fixedLengthBtn    = new JRadioButton("Fixed Length Fields");
	private JRadioButton delimitedBtn 		= new JRadioButton("Delimited Fields");
	private JRadioButton fixedMultiBtn 		= new JRadioButton("Multiple Records (fixed length)");

	private BmKeyedComboBox fileStructure;

	private JTextField         fontname = new JTextField();
	private BmKeyedComboBox defaultType;


    private Details wizardDetail;
    
    /**
     * Panel1 File Details
     *
     * @param connectionId connection Identifier
     */
    public Pnl1File(AbsRowList  structureList, AbsRowList typeList ) {
        super();


		defaultType = new BmKeyedComboBox(typeList, false);

		fileStructure  = new BmKeyedComboBox(structureList, false);

		String formDescription = "This wizard will build a <b>Record-Layout</b> from "
		    	+ "a sample file. <p> you need to enter a<ol>"
		    	+ "<li>sample file in the new layout</li>"
		    	+ "<li>the file structure (use <b>Default Reader</b>"
		    	+      " for standard windows / Unix files)</li>"
		    	+ "<li>the new layout name</li>";
		tips = new JEditorPane("text/html", formDescription);
		
		ButtonGroup grp = new ButtonGroup();
		JPanel recordTypePnl = new JPanel(new GridLayout(2,2));
		addRadioGrp(recordTypePnl, grp, fixedLengthBtn);
		addRadioGrp(recordTypePnl, grp, delimitedBtn);
		addRadioGrp(recordTypePnl, grp, fixedMultiBtn);
		fixedLengthBtn.setSelected(true);


		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD));
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP3,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
		this.setGap(BasePanel.GAP1);

		this.addComponent("File Name", filenameFld, filenameFld.getChooseFileButton());
		this.addComponent("File Structure", fileStructure);
		this.setGap(BasePanel.GAP0);
		this.addComponent("Record Type", recordTypePnl);
		this.setHeight(PREFERRED);
		this.setGap(BasePanel.GAP0);

		this.addComponent("Font Name", fontname);
		this.addComponent("Default Type", defaultType);
		this.setGap(BasePanel.GAP1);
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
            wizardDetail.defaultType = (Integer) defaultType.getSelectedItem();
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
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public final void setValues(Details detail) {

    	wizardDetail = detail;
    	
        filenameFld.setText(detail.filename);
        fileStructure.setSelectedItem(new Integer(detail.fileStructure));

        fontname.setText(detail.fontName);
        defaultType.setSelectedItem(detail.defaultType);

    }
}
