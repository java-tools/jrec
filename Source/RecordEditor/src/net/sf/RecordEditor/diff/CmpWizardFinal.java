/**
 * 
 */
package net.sf.RecordEditor.diff;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CmpWizardFinal extends BaseHelpPanel implements AbstractWizardPanel<DiffDefinition> {

	private FileChooser saveFileName = new FileChooser();
	private FileChooser htmlFileName = new FileChooser();
	
	private AbstractLayoutSelection<?> selection;
	private AbstractLayoutSelection<?> selection2;
	
	private JButton saveBtn = new JButton("Save", Common.getRecordIcon(Common.ID_SAVE_ICON)); 
	private JButton runBtn  = new JButton("Compare");
	private JButton htmlBtn = new JButton("Compare (HTML)");
	
	private JRadioButton stripTrailingSpaces = new JRadioButton("Strip Trailing Spaces");
	private JRadioButton singleTblBtn    = new JRadioButton("Single Table");
	private JRadioButton multipleTblsBtn = new JRadioButton("Table per row");
	
	private JRadioButton allRows = new JRadioButton("All Rows");
	private JRadioButton chgRows = new JRadioButton("Only Changed Rows");

	private JRadioButton allFieldsBtn = new JRadioButton("All Fields");
	private JRadioButton chgFieldsBtn = new JRadioButton("Only Changed Fields");

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
	public CmpWizardFinal(AbstractLayoutSelection<?> layoutSelection, 
			AbstractLayoutSelection<?> layoutSelection2) {
		super();
		JPanel pnl = new JPanel(new GridLayout(3,2));
		//JPanel pnlStrip = new JPanel();
		
		
		selection = layoutSelection ;
		selection2 = layoutSelection2;
		
		saveFileName.setDefaultDirectory(Parameters.getFileName(Parameters.COMPARE_SAVE_DIRECTORY));

		saveBtn.addActionListener(btnAction);
		runBtn.addActionListener(btnAction);
		htmlBtn.addActionListener(btnAction);
	
		super.setHelpURL(Common.formatHelpURL(Common.HELP_DIFF_SL));
		
		super.addComponent("Save File", saveFileName, saveFileName.getChooseFileButton());
		super.setGap(BasePanel.GAP1);
		super.addComponent("Html File", htmlFileName, htmlFileName.getChooseFileButton());
		super.setGap(BasePanel.GAP1);
		super.addComponent("", stripTrailingSpaces);
		super.setGap(BasePanel.GAP1);
		super.addComponent("HTML options:", null);
		
		addRadioGrp(pnl, singleTblBtn, multipleTblsBtn);
		addRadioGrp(pnl, allRows, chgRows);
		addRadioGrp(pnl, allFieldsBtn, chgFieldsBtn);
		
		super.addComponent("", pnl);
		super.setHeight(BasePanel.GAP5);
		
		super.addComponent("", null, saveBtn);
		super.setGap(BasePanel.GAP0);
		super.addComponent("", null, runBtn);
		super.setGap(BasePanel.GAP0);
		super.addComponent("", null, htmlBtn);
		super.setGap(BasePanel.GAP2);
		super.addMessage(message);
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
			diffDetail = getValues();

			DoCompare.getInstance().compare(selection, selection2, diffDetail);

			toRun = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public final void runHtml() {
		
		if ("".equals(htmlFileName.getText())) {
			message.setText("You must enter a HTML file");
			htmlFileName.requestFocus();
		} else {
			try {
				diffDetail = getValues();
	
				DoCompare.getInstance().writeHtml(selection, selection2, diffDetail);
	
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
		   Common.logMsg("File Save Failed:", e);
		}
	}

	/**
	 * @return the toRun
	 */
	public final boolean isToRun() {
		return toRun;
	}
}
