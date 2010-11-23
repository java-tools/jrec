/**
 * 
 */
package net.sf.RecordEditor.diff;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.DiffDefinition;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.wizards.AbstractFilePnl;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;
import net.sf.RecordEditor.utils.wizards.FieldChoice;

/**
 * @author Bruce Martin
 *
 */
public class CompareInternalLayout extends AbstractWizard<DiffDefinition> {


	private CmpWizardFinal finalScreen;
	private JibxCall<DiffDefinition> jibx = null;
	
	/**
	 * Create Single layout
	 * @param selection1 record layout selection class 1
	 * @param selection2 record layout selection class 2
	 * @param recentFiles Recent files holder file
	 */
	public CompareInternalLayout(
			AbstractLayoutSelection<?> selection1, AbstractLayoutSelection<?> selection2,
			String recentFiles) {
		this(selection1, 
			 selection2, 
			 new net.sf.RecordEditor.jibx.compare.DiffDefinition(), 
			 recentFiles);
	}
	
	
	/**
	 * Create Single layout
	 * @param selection1 record layout selection class 1
	 * @param selection2 record layout selection class 2
	 * @param definition record filter definition
	 * @param recentFiles Recent files holder file
	 */
	@SuppressWarnings("unchecked")
	public CompareInternalLayout(
			AbstractLayoutSelection selection1, AbstractLayoutSelection selection2, 
			DiffDefinition definition, String recentFiles) {
		super("File Compare", definition);
		
		AbstractWizardPanel<DiffDefinition>[] pnls = new AbstractWizardPanel[3]; 

		selection1.setMessage(super.getMessage());
		
		definition.type = DiffDefinition.TYPE_TWO_LAYOUTS;
		
		finalScreen = new CmpWizardFinal(selection1, selection2);
		pnls[0] = new getFiles(selection1, selection2, recentFiles);
		pnls[1] = new FieldChoice<DiffDefinition>(selection1, selection2, "");
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
	public static class getFiles extends  AbstractFilePnl<DiffDefinition> {



		private DiffDefinition values 
			= new net.sf.RecordEditor.jibx.compare.DiffDefinition();
//		private JPanel goPanel = new JPanel();
		private FileChooser newFileName = new FileChooser();
		private AbstractLayoutSelection<?> layoutSelection1;
		private AbstractLayoutSelection<?> layoutSelection2;
		
		
		public getFiles(AbstractLayoutSelection<?> selection1, AbstractLayoutSelection<?> selection2, String recentFiles) {
			super(selection1, recentFiles);
			
			newFileName.setText(Common.DEFAULT_FILE_DIRECTORY);
			layoutSelection1 = selection1;
			layoutSelection2 = selection2;
			
			layoutSelection1.setFileNameField(fileName);
			layoutSelection2.setFileNameField(newFileName);

			setHelpURL(Common.formatHelpURL(Common.HELP_DIFF_SL));
		}
		

		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#getValues()
		 */
		@Override
		public DiffDefinition getValues() throws Exception {

			values.oldFile.name = getCurrentFileName();
			values.newFile.name = newFileName.getText();

			values.oldFile.getLayoutDetails().name = layoutSelection1.getLayoutName();
			values.newFile.getLayoutDetails().name = layoutSelection2.getLayoutName();

			return values;
		}

		/**
		 * @see net.sf.RecordEditor.utils.wizards.AbstractWizardPanel#setValues(java.lang.Object)
		 */
		@Override
		public void setValues(DiffDefinition detail) throws Exception {
			System.out.println("Setting Values ... ");
			values = detail;
			
			layoutSelection1.setFileNameField(fileName);
			layoutSelection2.setFileNameField(newFileName);
			if (! "".equals(values.oldFile.name)) {
				fileName.setText(values.oldFile.name);
			}
			
			if (! "".equals(values.newFile.name)) {
				newFileName.setText(values.newFile.name);
			}
			
//			if (! "".equals(values.oldFile.getLayoutDetails().name)) {
//				layoutSelection1.setLayoutName(values.oldFile.getLayoutDetails().name);
//			}
		}
		
		@Override
		protected void addFileName(BaseHelpPanel pnl) {
					
			pnl.addComponent("Old File", fileName, fileName.getChooseFileButton());
			pnl.addComponent("New File", newFileName, newFileName.getChooseFileButton());
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
