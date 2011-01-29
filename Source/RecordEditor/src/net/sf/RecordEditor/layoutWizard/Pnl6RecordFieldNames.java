/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * 3rd and final wizard panel where the user enters field
 * details
 *
 * Changes
 * # Version 0.61 Bruce Martin 2007/04/14
 *   - Name changes, remove done method call
 *
 * # Version 0.61b Bruce Martin 2007/05/05
 *  - Changes o support user selecting the default type
 */
package net.sf.RecordEditor.layoutWizard;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.AbsRowList;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;


/**
 * 3rd and final wizard panel where the user enters field
 * details
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class Pnl6RecordFieldNames extends WizardPanel {

    private static final int COLUMN_HEIGHT  = SwingUtils.TABLE_ROW_HEIGHT * 9;
    private static final int FILE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 15 / 2;

    private JTextField message = new JTextField();
    
    private ColumnNames columnNames;
    private RecordComboMgr recordMgr = new RecordComboMgr(
    		new AbstractAction() {
						/* (non-Javadoc)
						 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
						 */
						@Override
						public void actionPerformed(ActionEvent arg0) {
							changeRecord();
						}
    });

    
    /**
     * Panel1 File Details
     *
     * @param connectionIdx Conection Identifier
     */
    public Pnl6RecordFieldNames(AbsRowList typeList) {
        super();

		String formDescription
		    = "This screen will display the Column Details (for each Record) and allow you to change them. "
		    + "<br>The Record controls which Records columns are displayed.";
		JEditorPane tips = new JEditorPane("text/html", formDescription);
		
		columnNames = new ColumnNames(typeList);


		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD_RECORD_FIELD_NAMES));
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(tips));
//		this.setGap(BasePanel.GAP1);
		this.addLine("Record", recordMgr.recordCombo);
		this.setGap(BasePanel.GAP0);

		this.addComponent(1, 5, COLUMN_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(columnNames.columnTbl));
		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				new JScrollPane(columnNames.fileTbl));
		
		this.addMessage(message);
    }




    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#getValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public Details getValues() throws Exception {
		RecordDefinition recdef;
		Details detail = columnNames.getValues();

		for (int i = 0; i < detail.recordDtls.size(); i++) {
			recdef = detail.recordDtls.get(i);
			if (! recdef.displayedFieldNames) {
				recordMgr.recordCombo.setSelectedIndex(i);
				throw new RecordException("You must define the field Names in all Records, "
						+ "please update: " + recdef.name);
			}
		}
    
        return detail;
    }

    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.LayoutWizard.Details)
     */
    public void setValues(Details detail) throws Exception {

   		RecordDefinition recDef = detail.recordDtls.get(0);
    	columnNames.setValues(detail, recDef);
        recDef.displayedFieldNames = true;
    	
    	recordMgr.load(detail.recordDtls);
     }
    
    private void changeRecord() {
		message.setText("");
		try {
			Details detail = columnNames.getValues();
			RecordDefinition recDef = detail.recordDtls.get(recordMgr.recordCombo.getSelectedIndex());
			columnNames.setValues(detail, recDef);
			recDef.displayedFieldNames = true;
		} catch (Exception e) {
			message.setText("Error Changing Record: " + e.getMessage());
    		e.printStackTrace();
		}

    }
}
