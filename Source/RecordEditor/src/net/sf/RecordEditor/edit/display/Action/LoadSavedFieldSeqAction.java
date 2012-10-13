package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import net.sf.RecordEditor.edit.display.common.AbstractFieldSequencePnl;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.file.filter.AbstractExecute;
import net.sf.RecordEditor.re.file.filter.ExecuteSavedFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;

@SuppressWarnings("serial")
public class LoadSavedFieldSeqAction
extends ReSpecificScreenAction
implements AbstractActiveScreenAction {

	/**
	 * @param creator
	 */
	public LoadSavedFieldSeqAction() {
		super("Load Saved Field Sequences");

		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(getDisplay(AbstractFieldSequencePnl.class) != null);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFieldSequencePnl sourcePnl = getDisplay(AbstractFieldSequencePnl.class);
		if (sourcePnl != null) {
			try {
				@SuppressWarnings("rawtypes")
				FileView fileView = sourcePnl.getFileView();

				SetFields setFields = new SetFields(sourcePnl);
				new ExecuteSavedFile<EditorTask>(
						fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Filter", fileView,
						Parameters.getFileName(Parameters.FIELD_SAVE_DIRECTORY),
						setFields, EditorTask.class);
			} catch (NoClassDefFoundError e) {
				Common.logMsg("Unable to load saved definition: jibx not present ???", null);
			}
		}
	}



	public static class SetFields implements AbstractExecute<EditorTask>{

		private AbstractFieldSequencePnl lineList;

		public SetFields(AbstractFieldSequencePnl lineList) {
			super();

			this.lineList = lineList;
		}

		@Override
		public void execute(EditorTask saveDetails) {

			if (saveDetails.fieldSequence != null) {
				lineList.setFieldSequence(saveDetails.fieldSequence);
			}
		}

		@Override
		public void executeDialog(EditorTask saveDetails) {
			execute(saveDetails);
		}
	}
}
