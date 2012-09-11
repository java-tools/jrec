package net.sf.RecordEditor.copy;

import javax.swing.JComponent;

import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.RecordEditor.edit.display.util.CreateRecordTreePnl;
import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.util.wizard.AbstractFilePnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

public class Copy2Xml extends AbstractWizard<CopyDefinition> {

	private CopyWizardFinalPnl finalScreen;
	private JibxCall<CopyDefinition> jibx = null;

	@SuppressWarnings("unchecked")
	public Copy2Xml(AbstractLayoutSelection recordSelection1) {
		this(recordSelection1, new net.sf.RecordEditor.jibx.compare.CopyDefinition(), "");
	}
	/**
	 *
	 * @param recordSelection1
	 * @param definition
	 */
	@SuppressWarnings("unchecked")
	public Copy2Xml(AbstractLayoutSelection recordSelection1, CopyDefinition definition, String recentFiles) {
		super("Copy to Xml file", definition);

		AbstractWizardPanel<CopyDefinition>[] pnls = new AbstractWizardPanel[4];

		recordSelection1.setMessage(super.getMessage());

		definition.type = CopyDefinition.XML_COPY;

		finalScreen = new CopyWizardFinalPnl(recordSelection1, null);
		pnls[0] = new GetFiles(recordSelection1, recentFiles);
		pnls[1] = new FieldSelection(recordSelection1, null, "");
		pnls[2] = new CreateTree(recordSelection1);
		pnls[3] = finalScreen;

		super.setPanels(pnls);
	}


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

	private static class CreateTree  implements AbstractWizardPanel<CopyDefinition> {


		private CreateRecordTreePnl recordTree;
		private CopyDefinition copydef;
		@SuppressWarnings("unchecked")
		private AbstractLayoutSelection recordSelection;

		/**
		 * @param recordSelection1
		 */
		@SuppressWarnings("unchecked")
		public CreateTree(AbstractLayoutSelection recordSelection1) {
			this.recordSelection = recordSelection1;
			recordTree = new CreateRecordTreePnl();
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getComponent()
		 */
		@Override
		public JComponent getComponent() {
			return recordTree.getPanel();
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
		 */
		@Override
		public CopyDefinition getValues() throws Exception {
			copydef.treeDefinition = recordTree.getSaveDetails().recordTree;
			return copydef;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
		 */
		@Override
		public void setValues(CopyDefinition detail) throws Exception {
			copydef = detail;
			recordTree.setLayout(
					recordSelection.getRecordLayout(detail.oldFile.name)
			);
			recordTree.setFromSavedDetails(copydef.treeDefinition);
			recordTree.getPanel().doLayout();
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#showHelp()
		 */
		@Override
		public void showHelp() {
			recordTree.getPanel().showHelp();
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#skip()
		 */
		@Override
		public boolean skip() {
			return recordTree.getLayout().getRecordCount() == 1;
		}
	}

	public static class GetFiles extends  AbstractFilePnl<CopyDefinition> {

		private CopyDefinition values = new net.sf.RecordEditor.jibx.compare.CopyDefinition();

		private FileChooser xmlFileName = new FileChooser();
		private AbstractLayoutSelection<?> layoutSelection;


		public GetFiles(AbstractLayoutSelection<?> selection, String recentFiles) {
			super(selection, recentFiles);

			xmlFileName.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());
			layoutSelection = selection;

			//setHelpURL(Common.formatHelpURL(Common.HELP_DIFF_SL));
		}


		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
		 */
		@Override
		public CopyDefinition getValues() throws Exception {

			values.oldFile.name = getCurrentFileName();
			values.oldFile.getLayoutDetails().name = layoutSelection.getLayoutName();

			values.newFile.name = xmlFileName.getText();

			if (layoutSelection.getRecordLayout(getCurrentFileName()) == null) {
				throw new RecordRunTimeException("Layout Does not exist");
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
				xmlFileName.setText(values.newFile.name);
			}

			if (! "".equals(values.oldFile.getLayoutDetails().name)) {
				layoutSelection.setLayoutName(values.oldFile.getLayoutDetails().name);
			}
		}

		@Override
		protected void addFileName(BaseHelpPanel pnl) {

			pnl.addLine("Input File", fileName, fileName.getChooseFileButton());
			pnl.addLine("Xml output File", xmlFileName, xmlFileName.getChooseFileButton());
		}
	}

}
