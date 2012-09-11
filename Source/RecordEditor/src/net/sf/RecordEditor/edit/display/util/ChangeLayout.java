package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.re.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class ChangeLayout implements ActionListener {
	private static final String CHANGE_LAYOUT_ERROR = LangConversion.convert("Error Changing Layout:");

	private ReFrame frame;

	@SuppressWarnings("rawtypes")
	private FileView masterView;
	private AbstractLayoutSelection<?> layoutReader;

	private JButton goButton = SwingUtils.newButton("Go");
	private JTextArea msgTxt = new JTextArea();

	public ChangeLayout(AbstractLayoutSelection<?> layoutSelection, FileView<?> file) {
		BaseHelpPanel pnl = new BaseHelpPanel();

		layoutReader = layoutSelection;
		masterView =  file.getBaseFile();

		layoutReader.setMessage(msgTxt);

		frame = new ReFrame(masterView.getBaseFile().getFileNameNoDirectory(), "Change Layout", masterView);
		frame.addCloseOnEsc(pnl);

		layoutReader.addLayoutSelection(pnl, null, null, null, null);

		pnl.setGap(BasePanel.GAP1);
		pnl.addLine("", null, goButton);
		pnl.setGap(BasePanel.GAP2);

		pnl.addMessage(msgTxt);
		goButton.addActionListener(this);

		frame.setDefaultCloseOperation(ReFrame.DISPOSE_ON_CLOSE);
		frame.addMainComponent(pnl);

		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			AbstractLayoutDetails<?, ?> layout = layoutReader.getRecordLayout(masterView.getFileName());

			if (layout != null) {
				goButton.removeActionListener(this);
				frame.setVisible(false);

				Code.notifyFramesOfNewLayout(masterView, layout);
			}
		} catch (Exception e) {
			String s = CHANGE_LAYOUT_ERROR + e.getMessage();
			msgTxt.setText(s);
			Common.logMsgRaw(s, e);
		}
	}


}
