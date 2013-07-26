/**
 *
 */
package net.sf.RecordEditor.re.util.wizard;

import net.sf.RecordEditor.jibx.JibxCall;
import net.sf.RecordEditor.jibx.compare.BaseCopyDif;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReActionHandler;
import net.sf.RecordEditor.utils.wizards.AbstractWizard;
import net.sf.RecordEditor.utils.wizards.AbstractWizardPanel;


/**
 * @author Bruce Martin
 *
 */
public abstract class TwoLayoutsWizard<Save extends BaseCopyDif> extends AbstractWizard<Save> {

	private JibxCall<Save> jibx = null;

	private AbstractLayoutSelection[] recordSelection = new AbstractLayoutSelection[2];



	/**
	 * Create Single layout
	 * @param selection1 record layout selection class
	 * @param definition record filter definition
	 */
	public TwoLayoutsWizard(String id, Save definition) {
		super(id, definition);

	}



	@SuppressWarnings("rawtypes")
	protected void setUpPanels(AbstractLayoutSelection selection1, AbstractLayoutSelection selection2,
			String recentFiles, AbstractWizardPanel<Save> finalScreen,
			String help) {
		AbstractWizardPanel<Save>[] pnls = new AbstractWizardPanel[4];

		int oldIdx = StandardGetFiles.OLD;
		int newIdx = StandardGetFiles.NEW;

		recordSelection[oldIdx] = selection1;
		recordSelection[newIdx] = selection2;

		pnls[0] = new StandardGetFiles(recordSelection[oldIdx], oldIdx, recentFiles, help);
		pnls[1] = new StandardGetFiles(recordSelection[newIdx], newIdx, recentFiles, help);
		pnls[2] = new FieldChoice<Save>(selection1, selection2, help);
		pnls[3] = finalScreen;

		super.setPanels(pnls);
	}

//	/**
//	 * @see net.sf.RecordEditor.utils.screenManager.AbstractWizard#finished(java.lang.Object)
//	 */
//	@Override
//	public void finished(save details) {
//
//		if (finalScreen.isToRun()) {
//			finalScreen.run();
//		}
//	}




	/**
	 * @see net.sf.RecordEditor.utils.wizards.AbstractWizard#executeAction(int)
	 */
	@Override
	public void executeAction(int action) {
		if (action == ReActionHandler.SAVE) {
			try {
				Save diff = super.getActivePanel().getValues();

				if (! "".equals(diff.saveFile)) {
					saveJibx(diff);
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
	protected void saveJibx(Save save) throws Exception {
		if (save == null) {
			return;
		}
	   if (jibx == null) {
		   jibx = new JibxCall<Save>(save.getClass());
	   }

	   jibx.unmarshal(save.saveFile, save);
	}
}
