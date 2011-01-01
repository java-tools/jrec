package net.sf.RecordEditor.edit.display.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import net.sf.RecordEditor.edit.display.common.AbstractFileDisplay;
import net.sf.RecordEditor.edit.file.FilePosition;
import net.sf.RecordEditor.edit.file.FileView;
import net.sf.RecordEditor.utils.screenManager.ReFrame;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;
import net.sf.RecordEditor.utils.swing.BasePanel;
/**
 * Purpose: To get a line number from the user
 * 
 * @author Bruce Martin
 *
 * Released under GPL license
 */
@SuppressWarnings("serial")
public class GotoLine extends ReFrame implements ActionListener {

	private AbstractFileDisplay source;

	private JTextField lineTxt = new JTextField(8);
	private    JButton gotoBtn = new JButton("Goto");

	private JTextField msg     = new JTextField();

	   private KeyAdapter listner = new KeyAdapter() {
	        /**
	         * @see java.awt.event.KeyAdapter#keyReleased
	         */
	        public final void keyReleased(KeyEvent event) {
	        	
	        	if (event.getKeyCode() == KeyEvent.VK_ENTER) {
	        		doGoto();
	        	}
	        }
	    };


	public GotoLine(final AbstractFileDisplay src, FileView master) {
		super(master.getFileNameNoDirectory(), "Find",
				master);
		source = src;
		
		BaseHelpPanel pnl = new BaseHelpPanel();	
		pnl.addReKeyListener(listner);
		
		pnl.addComponent("Line Number", lineTxt, gotoBtn);
		pnl.setGap(BasePanel.GAP1);
		pnl.addMessage(msg);

		gotoBtn.addActionListener(this);
		pnl.addReKeyListener(listner);

		this.addMainComponent(pnl);
		this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
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
			msg.setText("You must enter a line number");
		} else {
			try {
				int lineNo = Integer.parseInt(s);
				if (lineNo < 1) {
					msg.setText("line number must be > 0");
					return;
				}
				FilePosition position = new FilePosition(lineNo-1, 0, source.getLayoutIndex(), 0, true);
				
				source.setCurrRow(position);
				
				super.setVisible(false);
				super.doDefaultCloseAction();
			} catch (Exception e) {
				msg.setText("Invalid line number");
			}
		}
	}
	
}