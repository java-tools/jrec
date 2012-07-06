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


import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
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
public class Pnl3Table extends WizardPanel {


    private static final int FILE_HEIGHT = SwingUtils.TABLE_ROW_HEIGHT * 15 - 3;

	public int stdLineHeight;


    private JEditorPane tips;

    private ColumnSelector columnSelector;

    /**
     * Panel1 File Details
     *
     */
    public Pnl3Table(JTextComponent msg) {
        super();

        columnSelector = new ColumnSelector(msg);

		String formDescription
		    = LangConversion.convertId(LangConversion.ST_MESSAGE, "FileWizard_3b",
		    		  "This screen will display the first 60 lines of the file. "
		    		+ "<br>Indicate the <i>start</i> of a <b>field</b> by clicking on the starting column"
		    		+ "<br>Each succesive <b>field</b> will have alternating background color"
		    		+ "<p>To remove a <b>field</b> click on the starting column again.");
		tips = new JEditorPane("text/html", formDescription);

		this.setHelpURL(Common.formatHelpURL(Common.HELP_WIZARD_PNL2));
		this.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP0,
		        BasePanel.FULL, BasePanel.FULL,
				tips);

		columnSelector.addFields(this, FILE_HEIGHT);
//		this.addComponent("Show Hex", columnSelector.hexChk);
//		this.setGap(BasePanel.GAP1);
//		this.addComponent(1, 5, FILE_HEIGHT, BasePanel.GAP1,
//		        BasePanel.FULL, BasePanel.FULL,
//				new JScrollPane(columnSelector.fileTbl));
		//this.setGap(BasePanel.GAP1);

		columnSelector.addMouseListner();
    }


    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#getValues(net.sf.RecordEditor.layoutWizard.LayoutWizard.Details)
     */
    public Details getValues() throws Exception {
    	return columnSelector.getCurrentDetails();
    }

    /**
     * @see net.sf.RecordEditor.layoutWizard.WizardPanel#setValues(net.sf.RecordEditor.layoutWizard.LayoutWizard.Details)
     */
    public final void setValues(Details detail) throws Exception {

        columnSelector.readFile(detail, detail.standardRecord);
        columnSelector.setValues(detail, detail.standardRecord, true);
    }
}
