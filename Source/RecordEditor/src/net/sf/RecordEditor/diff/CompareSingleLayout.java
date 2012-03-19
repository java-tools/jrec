/**
 * 
 */
package net.sf.RecordEditor.diff;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.re.util.wizard.AbstractFilePnl;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
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
	 * Create Single layout
	 * @param selection record layout selection class
	 */
	public CompareSingleLayout(AbstractLayoutSelection<?> selection, String recentFiles) {
		this(
			 selection, 
			 new net.sf.RecordEditor.jibx.compare.DiffDefinition(), 
			 recentFiles);
	}
	
	
	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 * @param definition record filter definition
	 */
	@SuppressWarnings("unchecked")
	public CompareSingleLayout(AbstractLayoutSelection selection, DiffDefinition definition, String recentFiles) {
		super("Single Layout Compare", definition);
		
		AbstractWizardPanel<DiffDefinition>[] pnls = new AbstractWizardPanel[3]; 

		selection.setMessage(super.getMessage());
		
		definition.type = DiffDefinition.TYPE_SINGLE_LAYOUT;
		
		finalScreen = new CmpWizardFinal(selection, null);
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
    		   Common.logMsg("File Save Failed:", e);
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
		private FileChooser newFileName = new FileChooser();
		private AbstractLayoutSelection<?> layoutSelection;
		
		
		public GetFiles(AbstractLayoutSelection<?> selection, String recentFiles) {
			super(selection, recentFiles);
			
			newFileName.setText(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());
			layoutSelection = selection;
			
			setHelpURL(Common.formatHelpURL(Common.HELP_DIFF_SL));
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
			}

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
					
			pnl.addLine("Old File", fileName, fileName.getChooseFileButton());
			pnl.addLine("New File", newFileName, newFileName.getChooseFileButton());
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
