/**
 *
 */
package net.sf.RecordEditor.copy;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.treeCombo.FileSelectCombo;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class CopyWizardFinalPnl extends BaseHelpPanel implements AbstractWizardPanel<CopyDefinition> {

//	private static final ArrayList<FileTreeComboItem> ERROR_FILE_LIST = new ArrayList<FileTreeComboItem>();

	private FileSelectCombo saveFileName   = new FileSelectCombo(Parameters.SAVED_COPY_LIST, 25, true, false, true);
	private FileSelectCombo fieldErrorFile = new FileSelectCombo(Parameters.ERROR_FILES, 8, false, false, true);
			//new TreeComboFileSelect(false, false, true, ERROR_FILE_LIST);
	private JTextField  maxErrors      = new JTextField();

	private AbstractLayoutSelection selection;
	private AbstractLayoutSelection selection2;

	private JButton saveBtn = SwingUtils.newButton("Save", Common.getRecordIcon(Common.ID_SAVE_ICON));
	private JButton runBtn  = SwingUtils.newButton("Copy");
//	private JButton htmlBtn = SwingUtils.newButton("Compare (HTML)");

	private JRadioButton stripTrailingSpaces = new JRadioButton("Strip Trailing Spaces");

//	private JTextField message        = new JTextField();


	private CopyDefinition copyDetail;

	private JibxCall<CopyDefinition> jibx = null;

	private boolean toRun = true;




	private AbstractAction btnAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == runBtn) {
				run();
			} else {
				save();
			}
		}

	};


	/**
	 * Final wizard Screen
	 */
	public CopyWizardFinalPnl(
			AbstractLayoutSelection layoutSelection,
			AbstractLayoutSelection layoutSelection2) {
		super();
		JPanel pnl = new JPanel(new GridLayout(3,2));
		//JPanel pnlStrip = new JPanel();


		selection = layoutSelection ;
		selection2 = layoutSelection2;

		saveFileName.setText(Parameters.getFileName(Parameters.COPY_SAVE_DIRECTORY));
		saveFileName.setDefaultDirectory(Parameters.getFileName(Parameters.COPY_SAVE_DIRECTORY));

		saveBtn.addActionListener(btnAction);
		runBtn.addActionListener(btnAction);

		super.setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF_SL));

		super.addLineRE("Save File", saveFileName);
		super.setGapRE(BasePanel.GAP1);
		super.addLineRE("Field Error File", fieldErrorFile);
		super.addLineRE("Max Error Limit", maxErrors);
		super.setGapRE(BasePanel.GAP1);
		super.addLineRE("", stripTrailingSpaces);
		super.addLineRE("", pnl);
		super.setHeightRE(BasePanel.GAP5);

		super.addLineRE("", null, saveBtn);
		super.setGapRE(BasePanel.GAP0);
		super.addLineRE("", null, runBtn);
		super.setGapRE(BasePanel.GAP2);
		super.addMessageRE();
		super.setHeightRE(BasePanel.HEIGHT_1P6);
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
	public final CopyDefinition getValues() throws Exception {

		if (! "".equals(saveFileName.getText())) {
			copyDetail.saveFile = saveFileName.getText();
		}

		copyDetail.fieldErrorFile = fieldErrorFile.getText();
		if ("".equals(copyDetail.fieldErrorFile.trim())) {
			copyDetail.fieldErrorFile = null;
		}

		if ("".equals(maxErrors.getText().trim())) {
			copyDetail.maxErrors = -1;
		} else {
			try {
				copyDetail.maxErrors = Integer.parseInt(maxErrors.getText());
			} catch (Exception e) {
				String em = LangConversion.convert("Invalid max errors value: ") + e.getMessage();

				super.setMessageRawTxtRE(em);
				copyDetail.maxErrors = -1;
				throw new RuntimeException(em, e);
			}
		}

		copyDetail.stripTrailingSpaces = stripTrailingSpaces.isSelected();
		//System.out.println("Final Get -Layout Name: " + diffDetail.getLayoutDetails().name);

		return copyDetail;
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
	public final void setValues(CopyDefinition detail) throws Exception {

		saveFileName.setText(detail.saveFile);
		copyDetail = detail;
		copyDetail.fileSaved = false;
		copyDetail.complete = "YES";

		if (copyDetail.fieldErrorFile == null) {
			fieldErrorFile.setText("");
		} else {
			fieldErrorFile.setText(copyDetail.fieldErrorFile);
		}

		maxErrors.setText("");
		if (copyDetail.maxErrors >= 0) {
			maxErrors.setText(Integer.toString(copyDetail.maxErrors));
		}

		stripTrailingSpaces.setSelected(copyDetail.stripTrailingSpaces);
	}

	public final void run() {

		try {
			copyDetail = getValues();

			boolean ok = DoCopy.copy(selection, selection2, copyDetail);

			toRun = false;
			if (ok) {
				setMessageTxtRE("Copy Done !!!");
			} else {
				setMessageTxtRE("Copy Done !!!, check log for errors");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Common.logMsg("Copy Failed:", e);
			setMessageTxtRE("Copy Failed:", e.getMessage());
		}
	}



	/**
	 * Save the file
	 */
	public final void save() {
		try {
			copyDetail = getValues();

		    if (jibx == null) {
			   jibx = new JibxCall<CopyDefinition>(CopyDefinition.class);
		    }
		    jibx.unmarshal(copyDetail.saveFile, copyDetail);
			copyDetail.fileSaved = true;
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
