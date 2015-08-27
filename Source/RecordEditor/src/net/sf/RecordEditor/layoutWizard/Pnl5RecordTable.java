/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * 2nd wizard screen where the user selects the starting
 * position of every field in the file
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Name changes, remove done method call
 *   - JRecord creation changes
 *
 * # Version 0.61b Bruce Martin 2007/05/05
 *  - Changes o support user selecting the default type
 *  - Changing Column heading from "" to " " so it will be
 *    displayed under Windows look and Feel
 *
 */
package net.sf.RecordEditor.layoutWizard;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.text.JTextComponent;

import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * 2nd wizard screen where the user selects the starting
 * position of every field in the file
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl5RecordTable extends WizardPanel {


    private static final int FILE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 12 - 3;;

	public int stdLineHeight;


    private JEditorPane tips;
   // private JComboBox recordCombo = new JComboBox();
    //private JTextField message = new JTextField();
    private RecordComboMgr recordMgr = new RecordComboMgr(
    		new AbstractAction() {
						/* (non-Javadoc)
						 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
						 */
						@Override
						public void actionPerformed(ActionEvent arg0) {
							setRecord();
						}
    });

    private ColumnSelector columnSelector;

    /**
     * Panel1 File Details
     *
     */
    public Pnl5RecordTable(JTextComponent msg) {
        super();
        columnSelector = new ColumnSelector(msg);

		String formDescription
		    = "This screen will display the first 60 lines for each Record Type. "
		   	+ "<br>Use the Record Combo to switch between the various record layouts.<br/>"
		    + "<br>Indicate the <i>start</i> of a <b>field</b> by clicking on the starting column"
		    + "<br>Each succesive <b>field</b> will have alternating background color"
	    	+ "<p>To remove a <b>field</b> click on the starting column again.";
		tips = new JEditorPane("text/html", formDescription);

		this.setHelpURLre(Common.formatHelpURL(Common.HELP_WIZARD_RECORD_FIELD_DEF));
		this.addComponentRE(1, 5, TIP_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				tips);
		//this.setGap(BasePanel.GAP1);
		this.addLineRE("Record", recordMgr.recordCombo);

		columnSelector.addFields(this, FILE_HEIGHT);
//		this.addComponent("Show Hex", columnSelector.hexChk);
//		this.setGap(BasePanel.GAP1);
//		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP1,
//		        BasePanel.FULL, BasePanel.FULL,
//				new JScrollPane(columnSelector.fileTbl));

		this.addMessageRE();

		columnSelector.addMouseListner();
    }


    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#getValues(net.sf.RecordEditor.layoutWizard.LayoutWizard.Details)
     */
    public Details getValues() throws Exception {
		RecordDefinition recdef;
		Details detail = columnSelector.getCurrentDetails();

		for (int i = 0; i < detail.recordDtls.size(); i++) {
			recdef = detail.recordDtls.get(i);
			if (! recdef.displayedFieldSelection) {
				recordMgr.recordCombo.setSelectedIndex(i);
				throw new RecordException(
								"You must define the Fields for all Records. Please update - {0}",
								recdef.name);
			}
		}

    	return detail;
    }

    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.layoutWizard.LayoutWizard.Details)
     */
    public final void setValues(Details detail) throws Exception {

    	if (detail.recordDtls.size() == 0) {
    		setMessageTxtRE("No Records to display");
    	} else {
    		RecordDefinition recDef = detail.recordDtls.get(0);
	        columnSelector.setValues(detail, recDef, true);
	        recDef.displayedFieldSelection = true;

	        recordMgr.load(detail.recordDtls);
    	}
    }

    /**
     * Set
     */
    private void setRecord() {
    	Details detail = columnSelector.getCurrentDetails();
    	try {
    		RecordDefinition recDef = detail.recordDtls.get(recordMgr.recordCombo.getSelectedIndex());
    		setMessageRawTxtRE("");
    		columnSelector.setValues(detail, recDef, true);
    		recDef.displayedFieldSelection = true;
    	} catch (Exception e) {
    		setMessageTxtRE("Error Changing Record: " + e.getMessage());
    		e.printStackTrace();
		}
    }
}
