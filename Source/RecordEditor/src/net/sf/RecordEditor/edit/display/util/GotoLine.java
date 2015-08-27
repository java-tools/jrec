package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import net.sf.RecordEditor.re.display.AbstractFileDisplay;
import net.sf.RecordEditor.re.display.IChildDisplay;
import net.sf.RecordEditor.re.file.FilePosition;
import net.sf.RecordEditor.re.file.FileView;
import net.sf.RecordEditor.utils.lang.LangConversion;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
/**
 * Purpose: To get a line number from the user
 *
 * @author Bruce Martin
 *
 * Released under GPL license
 */
@SuppressWarnings("serial")
public class GotoLine extends ReFrame implements ActionListener, IChildDisplay {

	private AbstractFileDisplay source;

	private JTextField lineTxt = new JTextField(8);
	private    JButton gotoBtn = SwingUtils.newButton("Goto");
	private BaseHelpPanel pnl = new BaseHelpPanel();
	//private JTextField msgTxt     = new JTextField();

	private KeyAdapter listner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {

	        	switch (event.getKeyCode()) {
	        	case KeyEvent.VK_ENTER:		doGoto();								break;
	        	case KeyEvent.VK_ESCAPE:	GotoLine.this.doDefaultCloseAction();	break;
	        	}
	        }
	};


	public GotoLine(final AbstractFileDisplay src, FileView master) {
		super(master.getFileNameNoDirectory(), "Goto Line",
				master);
		source = src;

		//BaseHelpPanel pnl = new BaseHelpPanel();
		pnl.addReKeyListener(listner);

		pnl.addLineRE("Line Number", lineTxt, gotoBtn);
		pnl.setGapRE(BasePanel.GAP1);
		pnl.addMessageRE();

		gotoBtn.addActionListener(this);
		pnl.addReKeyListener(listner);

		this.addMainComponent(pnl);
		this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setToMaximum(false);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == gotoBtn) {
			doGoto();
		}
	}

	private void doGoto() {
		String s = lineTxt.getText();
		if ("".equals(s)) {
			pnl.setMessageTxtRE("You must enter a line number");
		} else {
			try {
				int lineNo = Integer.parseInt(s);
				if (lineNo < 1) {
					pnl.setMessageTxtRE("line number must be > 0");
					return;
				} else if (lineNo >= source.getFileView().getRowCount()) {
					pnl.setMessageRawTxtRE(LangConversion.convert("line number must be <") + " " + source.getFileView().getRowCount());
					return;
				}
				FilePosition position = new FilePosition(lineNo-1, 0, source.getLayoutIndex(), 0, true, source.getFileView().getRowCount());

				source.setCurrRow(position);

				super.setVisible(false);
				super.doDefaultCloseAction();
			} catch (Exception e) {
				pnl.setMessageTxtRE("Invalid line number");
			}
		}
	}


	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.display.IChildDisplay#getSourceDisplay()
	 */
	@Override
	public AbstractFileDisplay getSourceDisplay() {
		return source;
	}



}
