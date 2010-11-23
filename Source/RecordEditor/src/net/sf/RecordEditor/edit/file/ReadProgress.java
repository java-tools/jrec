package net.sf.RecordEditor.edit.file;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BasePanel;

public class ReadProgress {
	private JFrame frame;
	private boolean toInit = true;
	private JTextArea area = new JTextArea();
	private JProgressBar progressBar;
	
	public ReadProgress(String name) {
		frame = new JFrame("Reading " + name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void updateDisplay(String msg, int progress) {
		area.setText(msg + "          ");
		init();
		progressBar.setValue(progress);
		System.out.print(" " + progress);
	}
	
	public void done() {
		if (! toInit) {
			frame.setVisible(false);
		}
	}
	
	private void init() {
		if (toInit) {
			BasePanel pnl = new BasePanel();
			progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			//area = new JTextArea();
			area.setFont(Common.getMonoSpacedFont());
			
			pnl.addComponent(1, 5,
			         BasePanel.PREFERRED,
			         BasePanel.GAP1,
			         BasePanel.FULL, BasePanel.FULL, progressBar);

			pnl.addComponent(1, 5,
			         BasePanel.PREFERRED,
			         BasePanel.GAP1,
			         BasePanel.FULL, BasePanel.FULL, area);
			
			frame.getContentPane().add(pnl);
			frame.pack();
			//frame.setSize(250, frame.getHeight()+25);
			frame.setVisible(true);
			toInit = false;
		}
	}
}
 