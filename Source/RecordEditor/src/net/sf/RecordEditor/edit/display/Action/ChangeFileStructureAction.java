package net.sf.RecordEditor.edit.display.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;

import net.sf.JRecord.Details.Attribute;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.edit.util.ReMessages;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;
import net.sf.RecordEditor.re.util.FileStructureDtls;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.msg.UtMessages;
import net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.Combo.ComboOption;


@SuppressWarnings("serial")
public class ChangeFileStructureAction extends ReSpecificScreenAction implements AbstractActiveScreenAction {

	private static final int TIP_HEIGHT  = SwingUtils.STANDARD_FONT_HEIGHT * 10 + 5;

	/**
	 * @param creator
	 */
	public ChangeFileStructureAction() {
		super("Change File Structure");

		checkActionEnabled();
	}

	/**
	 * @see net.sf.RecordEditor.utils.screenManager.AbstractActiveScreenAction#checkActionEnabled()
	 */
	@Override
	public void checkActionEnabled() {
		super.setEnabled(isActive(
				getDisplay(AbstractFileDisplay.class)
		));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AbstractFileDisplay fileDisplay = getDisplay(AbstractFileDisplay.class);
		if (isActive(fileDisplay)) {
			FileView f = fileDisplay.getFileView();

			if (f != null) {
				new ChangeLayout(f);
			}
		}
	}


	private boolean isActive(AbstractFileDisplay activeScreen) {
		return activeScreen != null && activeScreen.getFileView() != null;
	}


	private static class ChangeLayout extends ReFrame implements ActionListener {
		final FileView view;
		private JEditorPane tips;
		private BasePanel pnl = new BasePanel();
		private JComboBox  fileStructures = FileStructureDtls.getFileStructureCombo();
		private JButton updateBtn = new JButton("Apply Update");


		public ChangeLayout(FileView view) {
			super(view.getFileNameNoDirectory(), "Change File Structure", view.getBaseFile());
			this.view = view;

			tips = new JEditorPane("text/html", ReMessages.CHANGE_FILE_STRUCTURE.get());

			pnl.addComponent(1, 5, TIP_HEIGHT, BasePanel.GAP3,
			        BasePanel.FULL, BasePanel.FULL,
					tips);
			pnl.setGap(BasePanel.GAP2);
			pnl.addLine("new File Structure", fileStructures, updateBtn);

			fileStructures.setSelectedIndex(FileStructureDtls.getComboIndex(view.getLayout().getFileStructure()));

			super.addMainComponent(pnl);

			super.setVisible(true);
			super.setToMaximum(false);

			updateBtn.addActionListener(this);
		}


		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ComboOption opt = (ComboOption) fileStructures.getSelectedItem();

			if (opt != null) {
				view.getLayout().setAttribute(Attribute.FILE_STRUCTURE, opt.index);
				view.setChanged(true);
				Common.logMsgRaw(
						AbsSSLogger.LOG,
						UtMessages.FILE_FORMAT_CHANGED.get(
								new Object[] {
										view.getFileNameNoDirectory(),
										FileStructureDtls.getStructureName(opt.index)}),
						null);
			}
		}
	}
}
