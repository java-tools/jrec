package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.RecordEditor.edit.display.common.AbstractFieldSequencePnl;
import net.sf.RecordEditor.jibx.compare.EditorTask;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.params.Parameters;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.swing.DirectoryFrame;

@SuppressWarnings("serial")
public class SaveFieldSequenceAction extends ReSpecificScreenAction implements AbstractActiveScreenAction {

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
		super.setEnabled(getDisplay(AbstractFieldSequencePnl.class) != null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFieldSequencePnl sourcePnl = getDisplay(AbstractFieldSequencePnl.class);
		if (sourcePnl != null) {
			String dir = Parameters.getFileName(Parameters.FIELD_SAVE_DIRECTORY);
			new SaveSequence(sourcePnl, dir);
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
