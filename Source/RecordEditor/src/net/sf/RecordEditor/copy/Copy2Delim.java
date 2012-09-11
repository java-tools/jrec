/**
 *
 */
package net.sf.RecordEditor.copy;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.util.wizard.AbstractFilePnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.swing.ComboBoxs.DelimiterCombo;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;


/**
 * @author Bruce Martin
 *
 */
public class Copy2Delim extends AbstractWizard<CopyDefinition> {


	private CopyWizardFinalPnl finalScreen;
	private JibxCall<CopyDefinition> jibx = null;
//	private final  LayoutSelectionFile recordSelection = new LayoutSelectionFile();


	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 */
	@SuppressWarnings("unchecked")
	public Copy2Delim(AbstractLayoutSelection recordSelection1) {
		this(recordSelection1, new net.sf.RecordEditor.jibx.compare.CopyDefinition());
	}


	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 * @param definition record filter definition
	 */
	@SuppressWarnings("unchecked")
	public Copy2Delim(AbstractLayoutSelection recordSelection1 , CopyDefinition definition) {
		super("Copy to Delimited file", definition);

		AbstractWizardPanel<CopyDefinition>[] pnls = new AbstractWizardPanel[3];

		recordSelection1.setMessage(super.getMessage());

		definition.type = CopyDefinition.DELIM_COPY;

		finalScreen = new CopyWizardFinalPnl(recordSelection1, null);
		pnls[0] = new GetFiles(recordSelection1);
		pnls[1] = new FieldSelection(recordSelection1, null, "");
		pnls[2] = finalScreen;

		super.setPanels(pnls);
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#finished(java.lang.Object)
	 */
	@Override
	public void finished(CopyDefinition details) {

		if (finalScreen.isToRun()) {
			finalScreen.run();
		}
	}




	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
       if (action == ReActionHandler.SAVE) {
    	   try {
    		   CopyDefinition diff = super.getActivePanel().getValues();

    		   if (! "".equals(diff.saveFile)) {
    			   if (jibx == null) {
    				   jibx = new JibxCall<CopyDefinition>(CopyDefinition.class);
    			   }

    			   jibx.unmarshal(diff.saveFile, diff);
    			   diff.fileSaved = true;
    		   }
    	   } catch (Exception e) {
    		   e.printStackTrace();
    		   Common.logMsgRaw(FILE_SAVE_FAILED, e);
    	   }
        } else {
            super.executeAction(action);
        }
	}

	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#isActionAvailable(int)
	 */
	@Override
	public boolean isActionAvailable(int action) {
		if (action == ReActionHandler.SAVE) {
			return true;
		}
		return super.isActionAvailable(action);
	}



	public static class GetFiles extends  AbstractFilePnl<CopyDefinition> {

		private CopyDefinition values = new net.sf.RecordEditor.jibx.compare.CopyDefinition();
//		private JPanel goPanel = new JPanel();
		private FileChooser newFileName = new FileChooser();
		@SuppressWarnings("unchecked")
		private AbstractLayoutSelection layoutSelection1;

		private DelimiterCombo delimCombo = DelimiterCombo.NewDelimCombo();
		private JTextField delimTxt = new JTextField(8);
		private JCheckBox names1stLineChk = new JCheckBox();
		private JTextField quoteTxt = new JTextField();
		private JTextField fontTxt = new JTextField();


		@SuppressWarnings("unchecked")
		public GetFiles(AbstractLayoutSelection selection1) {
			super(selection1, "CobolFiles.txt");

			newFileName.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());
			layoutSelection1 = selection1;

			setHelpURL(Common.formatHelpURL(Common.HELP_DIFF_SL));
		}


		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
		 */
		@Override
		public CopyDefinition getValues() throws Exception {

			values.oldFile.name = getCurrentFileName();
			values.newFile.name = newFileName.getText();

			values.oldFile.getLayoutDetails().name  = layoutSelection1.getLayoutName();

			values.delimiter = getDelim();
			values.namesOnFirstLine = names1stLineChk.isSelected();
			values.quote = quoteTxt.getText();
			values.font = fontTxt.getText();

			if (layoutSelection1.getRecordLayout(getCurrentFileName()) == null) {
				throw new RuntimeException("Layout Does not exist");
			}

			return values;
		}

		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
		 */
		@Override
		public void setValues(CopyDefinition detail) throws Exception {
			System.out.println("Setting Values ... ");
			values = detail;

			if (! "".equals(values.oldFile.name)) {
				fileName.setText(values.oldFile.name);
			}

			if (! "".equals(values.newFile.name)) {
				newFileName.setText(values.newFile.name);
			}

			if (! "".equals(values.oldFile.getLayoutDetails().name)) {
				layoutSelection1.setLayoutName(values.oldFile.getLayoutDetails().name);
			}

			delimTxt.setText("");
			delimCombo.setEnglish(values.delimiter);
			if (values.delimiter != null && ! values.delimiter.equals(delimCombo.getSelectedEnglish())) {
				delimTxt.setText(values.delimiter);
			}
			names1stLineChk.setSelected(values.namesOnFirstLine);
			quoteTxt.setText(values.quote);
			fontTxt.setText(values.font);
		}

		@Override
		protected void addFileName(BaseHelpPanel pnl) {

			pnl.addLine("Old File", fileName, fileName.getChooseFileButton());
			pnl.addLine("New File", newFileName, newFileName.getChooseFileButton());
		}

		@Override
		protected void addLayoutSelection() {

			layoutSelection1.addLayoutSelection(this, fileName, new JPanel(), null, null);
			JPanel delimPnl = new JPanel(new BorderLayout());
			JPanel orLabel = new JPanel();
			orLabel.add(new JLabel("or"));

			delimPnl.add(BorderLayout.WEST, delimCombo);
			delimPnl.add(BorderLayout.CENTER, orLabel);
			delimPnl.add(BorderLayout.EAST, delimTxt);

			this.setGap(GAP3);
			this.addLine("Field Delimiter", delimPnl);
			this.setHeight(HEIGHT_1P1);
			this.setGap(GAP1);

			this.addLine("Names on 1st Line", names1stLineChk);
			this.addLine("Quote", quoteTxt);
			this.addLine("Font Name", fontTxt);

			this.setGap(GAP3);
		}


		private String getDelim() {
			String ret = delimTxt.getText();

			if ("".equals(ret)) {
				ret = delimCombo.getSelectedEnglish();
			} else {
				String v = delimTxt.getText();

				if (v.length() < 2) {
				} else if (((v.length() == 5) && v.toLowerCase().startsWith("x'") && v.endsWith("'"))) {
					try {
						Conversion.getByteFromHexString(v);
					} catch (Exception e) {
						String msg1 = LangConversion.convert(
								LangConversion.ST_ERROR,
								"Invalid Delimiter - Invalid  hex string: {0}",
								v.substring(2, 3));
						//Common.logMsg(msg1, null);
						throw new RuntimeException(msg1);
					}
				} else {
					String msg1 = "Invalid Delimiter, should be a single character or a hex character";
					//Common.logMsg(msg1, null);
					throw new RecordRunTimeException(msg1);
				}

			}

			return ret;
		}
	}

}
