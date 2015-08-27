/**
 *
 */
package net.sf.RecordEditor.diff;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.HtmlWindow;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CmpWizardFinal extends BaseHelpPanel implements AbstractWizardPanel<DiffDefinition> {

	private FileSelectCombo saveFileName = new FileSelectCombo(Parameters.SAVED_DIFF_LIST, 15, false, false, true);
	private FileSelectCombo htmlFileName = new FileSelectCombo(Parameters.HTML_OUTPUT_LIST, 15, false, false, true);

	private AbstractLayoutSelection selection;
	private AbstractLayoutSelection selection2;

	private JButton saveBtn = SwingUtils.newButton("Save", Common.getRecordIcon(Common.ID_SAVE_ICON));
	private JButton runBtn  = SwingUtils.newButton("Compare");
	private JButton htmlBtn = SwingUtils.newButton("Compare (HTML)");

	private JRadioButton stripTrailingSpaces = SwingUtils.newRadioButton("Strip Trailing Spaces");
	private JRadioButton singleTblBtn    = SwingUtils.newRadioButton("Single Table");
	private JRadioButton multipleTblsBtn = SwingUtils.newRadioButton("Table per row");

	private JRadioButton allRows = SwingUtils.newRadioButton("All Rows");
	private JRadioButton chgRows = SwingUtils.newRadioButton("Only Changed Rows");

	private JRadioButton allFieldsBtn = SwingUtils.newRadioButton("All Fields");
	private JRadioButton chgFieldsBtn = SwingUtils.newRadioButton("Only Changed Fields");

	private JCheckBox showHtmlCheckBox = SwingUtils.newCheckBox("Show Html");

	private JTextField message        = new JTextField();


	private DiffDefinition diffDetail;

	private JibxCall<DiffDefinition> jibx = null;

	private boolean toRun = true;


	private AbstractAction btnAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == runBtn) {
				run();
			} else if (e.getSource() == htmlBtn) {
				runHtml();
			} else {
				save();
			}
		}

	};


	/**
	 * Final wizard Screen
	 */
	public CmpWizardFinal(AbstractLayoutSelection layoutSelection,
			AbstractLayoutSelection layoutSelection2, boolean offerSaveOption) {
		super();
		JPanel pnl = new JPanel(new GridLayout(3,2));
		//JPanel pnlStrip = new JPanel();
		saveFileName.setDefaultDirectory(Parameters.getFileName(Parameters.COMPARE_SAVE_DIRECTORY));


		selection = layoutSelection ;
		selection2 = layoutSelection2;

		saveFileName.setText(Parameters.getFileName(Parameters.COMPARE_SAVE_DIRECTORY));

		showHtmlCheckBox.setSelected(true);
		
		saveBtn.addActionListener(btnAction);
		runBtn.addActionListener(btnAction);
		htmlBtn.addActionListener(btnAction);

		super.setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF_SL));

		if (offerSaveOption) {
			super.addLineRE("Save File", saveFileName);
			super.setGapRE(BasePanel.GAP1);
		}
		super.addLineRE("Html File", htmlFileName);
		super.setGapRE(BasePanel.GAP1);
		super.addLineRE("", stripTrailingSpaces);
		super.setGapRE(BasePanel.GAP1);
		super.addLineRE("HTML options:", null);

		addRadioGrp(pnl, singleTblBtn, multipleTblsBtn);
		addRadioGrp(pnl, allRows, chgRows);
		addRadioGrp(pnl, allFieldsBtn, chgFieldsBtn);

		super.addLineRE("", pnl);
		super.setHeightRE(BasePanel.GAP5);

		super.addLineRE("", null, saveBtn);
		super.setGapRE(BasePanel.GAP0);
		super.addLineRE("", null, runBtn);
		super.setGapRE(BasePanel.GAP0);
		super.addLineRE("", showHtmlCheckBox, htmlBtn);
		super.setGapRE(BasePanel.GAP2);
		super.addMessage(message);
		super.setHeightRE(BasePanel.HEIGHT_1P6);
	}

	private void addRadioGrp(JPanel pnl, JRadioButton btn1, JRadioButton btn2) {
		ButtonGroup grp = new ButtonGroup();
		grp.add(btn1);
		grp.add(btn2);

		pnl.add(btn1);
		pnl.add(btn2);
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
	 */
	@Override
	public final JComponent getComponent() {
		return this;
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
	 */
	@Override
	public final DiffDefinition getValues() throws Exception {

		if (! "".equals(saveFileName.getText())) {
			diffDetail.saveFile = saveFileName.getText();
		}
		if (! "".equals(htmlFileName.getText())) {
			diffDetail.htmlFile = htmlFileName.getText();
		}

		diffDetail.stripTrailingSpaces = stripTrailingSpaces.isSelected();
		diffDetail.allFields = allFieldsBtn.isSelected();
		diffDetail.allRows   = allRows.isSelected();
		diffDetail.singleTable = singleTblBtn.isSelected();
		//System.out.println("Final Get -Layout Name: " + diffDetail.getLayoutDetails().name);

		return diffDetail;
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#isMore()
	 */
	@Override
	public final boolean skip() {
		return false;
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
	 */
	@Override
	public final void setValues(DiffDefinition detail) throws Exception {

		saveFileName.setText(detail.saveFile);
		htmlFileName.setText(detail.htmlFile);
		diffDetail = detail;
		diffDetail.fileSaved = false;
		diffDetail.complete = "YES";

		stripTrailingSpaces.setSelected(diffDetail.stripTrailingSpaces);
		allFieldsBtn.setSelected(diffDetail.allFields);
		chgFieldsBtn.setSelected(! diffDetail.allFields);
		allRows.setSelected(diffDetail.allRows);
		chgRows.setSelected(! diffDetail.allRows);
		singleTblBtn.setSelected(diffDetail.singleTable);
		multipleTblsBtn.setSelected(! diffDetail.singleTable);
	}

	public final void run() {

		try {
			message.setText("");
			diffDetail = getValues();

			DoCompare.getInstance().compare(selection, selection2, diffDetail);

			toRun = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public final void runHtml() {

		if ("".equals(htmlFileName.getText())) {
			setMessageTxtRE("You must enter a HTML file");
			htmlFileName.requestFocus();
		} else {
			try {
				message.setText("");
				diffDetail = getValues();

				DoCompare.getInstance().writeHtml(selection, selection2, diffDetail);

				if (showHtmlCheckBox.isSelected()) {
					new HtmlWindow(diffDetail.htmlFile, "Generated Html");
				}
				toRun = false;
			} catch (Exception e) {
				e.printStackTrace();
				Common.logMsg("Error generating HTML", e);
			}
		}
	}


	/**
	 * Save the file
	 */
	public final void save() {
		try {
			diffDetail = getValues();

			if (jibx == null) {
				jibx = new JibxCall<DiffDefinition>(DiffDefinition.class);
			}
			jibx.unmarshal(diffDetail.saveFile, diffDetail);
			diffDetail.fileSaved = true;
		} catch (Exception e) {
 		   e.printStackTrace();
		   Common.logMsgRaw(Common.FILE_SAVE_FAILED, e);
		}
	}

	/**
	 * @return the toRun
	 */
	public final boolean isToRun() {
		return toRun;
	}
}
