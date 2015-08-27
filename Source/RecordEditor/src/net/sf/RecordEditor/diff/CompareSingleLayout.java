/**
 *
 */
package net.sf.RecordEditor.diff;

import java.io.File;


import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.util.wizard.AbstractFilePnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.treeCombo.TreeComboFileSelect;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
public class CompareSingleLayout extends AbstractWizard<DiffDefinition> {


	private CmpWizardFinal finalScreen;
	private JibxCall<DiffDefinition> jibx = null;

	/**
	 * Single layout Compare Wizard
	 * @param selection record layout selection class
	 * @param recentFiles recent files file
	 */
	public CompareSingleLayout(AbstractLayoutSelection selection, String recentFiles) {
		this(
			 selection,
			 new net.sf.RecordEditor.jibx.compare.DiffDefinition(),
			 recentFiles);
	}

	/**
	 * Single layout Compare Wizard
	 * @param name compare wizard name
	 * @param selection record layout selection class
	 * @param recentFiles recent files file
	 */
	public CompareSingleLayout(String name, AbstractLayoutSelection selection, String recentFiles) {
		this(name,
			 selection,
			 new net.sf.RecordEditor.jibx.compare.DiffDefinition(),
			 recentFiles);
	}


	/**
	 * Single layout Compare Wizard
	 * @param selection record layout selection class
	 * @param definition record filter definition
	 * @param recentFiles recent files file
	 */
	public CompareSingleLayout(AbstractLayoutSelection selection, DiffDefinition definition, String recentFiles) {
		this("Single Layout Compare", selection, definition, recentFiles);
	}
	
	
	/**
	 * Single layout Compare Wizard
	 * @param name compare wizard name
	 * @param selection record layout selection class
	 * @param definition record filter definition
	 * @param recentFiles recent files file
	 */
	@SuppressWarnings("unchecked")
	public CompareSingleLayout(String name, AbstractLayoutSelection selection, DiffDefinition definition, String recentFiles) {
		super(name, definition);

		AbstractWizardPanel<DiffDefinition>[] pnls = new AbstractWizardPanel[3];

		selection.setMessage(super.getMessage());

		definition.type = DiffDefinition.TYPE_SINGLE_LAYOUT;

		finalScreen = new CmpWizardFinal(selection, null, true);
		pnls[0] = new GetFiles(selection, recentFiles);
		pnls[1] = new CmpFieldSelection(selection);
		pnls[2] = finalScreen;

		super.setPanels(pnls);
	}



	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#finished(java.lang.Object)
	 */
	@Override
	public void finished(DiffDefinition details) {

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
    		   DiffDefinition diff = super.getActivePanel().getValues();

    		   if (! "".equals(diff.saveFile)) {
    			   getJibx().unmarshal(diff.saveFile, diff);
    			   diff.fileSaved = true;
    		   }
    	   } catch (Exception e) {
    		   e.printStackTrace();
    		   Common.logMsgRaw(Common.FILE_SAVE_FAILED, e);
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

	/**
	 * get Jibx interface
	 * @return Jibx interface
	 */
	private JibxCall<DiffDefinition> getJibx() {
	   if (jibx == null) {
		   jibx = new JibxCall<DiffDefinition>(DiffDefinition.class);
	   }
	   return jibx;
	}


	@SuppressWarnings("serial")
	public static class GetFiles extends  AbstractFilePnl<DiffDefinition> {



		private DiffDefinition values = new net.sf.RecordEditor.jibx.compare.DiffDefinition();
//		private JPanel goPanel = new JPanel();
//		private FileChooser newFileName = new FileChooser();
		private TreeComboFileSelect newFileName = new TreeComboFileSelect(true, false, true, getRecentList(), getRecentDirectoryList());
		private AbstractLayoutSelection layoutSelection;


		public GetFiles(AbstractLayoutSelection selection, String recentFiles) {
			super(selection, recentFiles);

			newFileName.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.getWithStar());
			layoutSelection = selection;

			setHelpURLre(Common.formatHelpURL(Common.HELP_DIFF_SL));
		}


		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
		 */
		@Override
		public DiffDefinition getValues() throws Exception {

			values.oldFile.name = getCurrentFileName();
			values.newFile.name = newFileName.getText();

			values.getLayoutDetails().name = layoutSelection.getLayoutName();
			if (layoutSelection.getRecordLayout(getCurrentFileName()) == null) {
				throw new RuntimeException("Layout Does not exist");
			} else if (! new File(values.oldFile.name).exists()) {
				throw new RuntimeException("Layout Does not exist");
			}
			checkFile(values.oldFile.name, super.fileName);
			checkFile(values.newFile.name, newFileName);
			
			return values;
		}
		

		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
		 */
		@Override
		public void setValues(DiffDefinition detail) throws Exception {
			System.out.println("Setting Values ... ");
			values = detail;

			if (! "".equals(values.oldFile.name)) {
				fileName.setText(values.oldFile.name);
			}

			if (! "".equals(values.newFile.name)) {
				newFileName.setText(values.newFile.name);
			}

			if (! "".equals(values.getLayoutDetails().name)) {
				layoutSelection.setLayoutName(values.getLayoutDetails().name);
			}
		}

		@Override
		protected void addFileName(BaseHelpPanel pnl) {

			pnl.addLineRE("Old File", fileName);
//			pnl.addLine("New File", newFileName, newFileName.getChooseFileButton());
			pnl.addLineRE("New File", newFileName);
		}
	}

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		 new ReMainFrame("File Compare", "");
//		 new CmpSingleLayout(new LayoutSelectionDB(new CopyBookDbReader()));
//	}

}
