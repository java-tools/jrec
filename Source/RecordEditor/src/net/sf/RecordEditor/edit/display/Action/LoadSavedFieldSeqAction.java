package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sf.RecordEditor.edit.display.LineList;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.util.filter.AbstractExecute;
import net.sf.RecordEditor.re.util.filter.ExecuteSavedFile;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class LoadSavedFieldSeqAction 
extends AbstractAction 
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
		ReFrame actionHandler = ReFrame.getActiveFrame();

		super.setEnabled(actionHandler != null && actionHandler instanceof LineList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ReFrame actionHandler = ReFrame.getActiveFrame();
		if (actionHandler instanceof LineList) {
			try {
				LineList lineList = (LineList) actionHandler;
				FileView fileView = lineList.getFileView();
				
				SetFields setFields = new SetFields(lineList);
				new ExecuteSavedFile<EditorTask>(
						fileView.getBaseFile().getFileNameNoDirectory(), "Execute Saved Filter", fileView,
						Parameters.getFileName(Parameters.HIDDEN_FIELDS_SAVE_DIRECTORY), 
						setFields, EditorTask.class);
			} catch (NoClassDefFoundError e) {
				Common.logMsg("Unable to loaved saved definition: jibx not present ???", null);
			}
		}
	}

	
	
	public static class SetFields implements AbstractExecute<EditorTask>{
		
		private LineList lineList;
		
		public SetFields(LineList lineList) {
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
