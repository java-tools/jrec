/**
 * 
 */
package net.sf.RecordEditor.copy;

import javax.swing.JPanel;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.FileChooser;
import net.sf.RecordEditor.utils.wizards.AbstractFilePnl;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;

/**
 * @author Bruce Martin
 *
 */
public class Copy2Velocity extends AbstractWizard<CopyDefinition> {


	private CopyWizardFinalPnl finalScreen;
	private JibxCall<CopyDefinition> jibx = null;
//	private final  LayoutSelectionFile recordSelection = new LayoutSelectionFile();
	
	
	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 */
	@SuppressWarnings("unchecked")
	public Copy2Velocity(AbstractLayoutSelection recordSelection1) {
		this(new net.sf.RecordEditor.jibx.compare.CopyDefinition(), recordSelection1);
	}
	
	
	/**
	 * Create Single layout
	 * @param selection record layout selection class
	 * @param definition record filter definition
	 */
	@SuppressWarnings("unchecked")
	public Copy2Velocity(CopyDefinition definition, AbstractLayoutSelection recordSelection1) {
		super("Reformat via Velocity Template", definition);
		
		AbstractWizardPanel<CopyDefinition>[] pnls = new AbstractWizardPanel[2]; 

		recordSelection1.setMessage(super.getMessage());
		recordSelection1.setMessage(super.getMessage());
		
		definition.type = CopyDefinition.VELOCITY_COPY;
		
		finalScreen = new CopyWizardFinalPnl(recordSelection1, null);
		pnls[0] = new GetFiles(recordSelection1);
//		pnls[1] = new CmpFieldSelection(selection);
		pnls[1] = finalScreen;
		
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


	/* ---------------------------------------------------------------------------------------*/


	@SuppressWarnings("serial")
	public static class GetFiles extends  AbstractFilePnl<CopyDefinition> {



		private CopyDefinition values = new net.sf.RecordEditor.jibx.compare.CopyDefinition();
//		private JPanel goPanel = new JPanel();
		private FileChooser newFileName = new FileChooser();
		private FileChooser velocityTemplateFile = new FileChooser();
		@SuppressWarnings("unchecked")
		private AbstractLayoutSelection layoutSelection1;
		
		
		@SuppressWarnings("unchecked")
		public GetFiles(AbstractLayoutSelection selection1) {
			super(selection1, "CobolFiles.txt");
			
			layoutSelection1 = selection1;
			newFileName.setDefaultDirectory(Common.OPTIONS.DEFAULT_FILE_DIRECTORY.get());
			velocityTemplateFile.setDefaultDirectory(Common.OPTIONS.DEFAULT_VELOCITY_DIRECTORY.get());

		
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
			values.velocityTemplate = velocityTemplateFile.getText();
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
			
			if (! "".equals(values.velocityTemplate)) {
				velocityTemplateFile.setText(values.velocityTemplate);
			}
		}
		
		@Override
		protected void addFileName(BaseHelpPanel pnl) {
					
			pnl.addLine("Old File", fileName, fileName.getChooseFileButton());
			pnl.addLine("New File", newFileName, newFileName.getChooseFileButton());
		}
		
		@Override
		protected void addLayoutSelection() {
			
			layoutSelection1.addLayoutSelection(this, fileName, new JPanel(), null, null);
			this.setGap(GAP3);
			this.addLine("Velocity Template", velocityTemplateFile, velocityTemplateFile.getChooseFileButton());
			this.setGap(GAP3);
		}
	}

}
