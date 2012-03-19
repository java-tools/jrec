package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import net.sf.RecordEditor.edit.display.common.AbstractFieldSequencePnl;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.re.util.filter.DirectoryFrame;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;

@SuppressWarnings("serial")
public class SaveFieldSequenceAction extends AbstractAction implements AbstractActiveScreenAction {

	/**
	 * @param creator
	 */
	public SaveFieldSequenceAction() {
		super("Save Field Sequence");

		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		ReFrame actionHandler = ReFrame.getActiveFrame();
		boolean enable = 
					(actionHandler != null
				&&	 actionHandler instanceof AbstractFieldSequencePnl);

		super.setEnabled(enable);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ReFrame actionHandler = ReFrame.getActiveFrame();
		if (actionHandler instanceof AbstractFieldSequencePnl) {
			String dir = Parameters.getFileName(Parameters.HIDDEN_FIELDS_SAVE_DIRECTORY);
			new SaveSequence((AbstractFieldSequencePnl) actionHandler, dir);
		}
	}
	
	public static class SaveSequence 
	extends DirectoryFrame implements ActionListener {

		private AbstractFieldSequencePnl panel;
		public SaveSequence(AbstractFieldSequencePnl pnl, String dir) {
			super("Save Field Sequence to Xml",  dir, false, false, true);
			
			panel = pnl;
			setActionListner(this);
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			EditorTask task = new EditorTask();
			task.type = EditorTask.TASK_FIELD_SEQUENCE;
			task.fieldSequence = panel.getFieldSequence();	
			try {
				(new net.sf.RecordEditor.jibx.JibxCall<EditorTask>(task.getClass()))
					.unmarshal(getFileName(), task);
				this.setVisible(false);
			} catch (Exception ex) {
				Common.logMsg("Can not save Field Sequences", ex);
			}
		}
	}
}
