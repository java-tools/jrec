package net.sf.RecordEditor.edit.file;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

public class ProgressDisplay {
	private JFrame frame;
	private boolean toInit = true;
	private JTextArea area = new JTextArea();
	private JProgressBar progressBar;
	
	public ProgressDisplay(String name) {
		this("Reading", name);
	}
	
	
	public ProgressDisplay(String action, String name) {
		frame = new JFrame(action + " " + name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateDisplay(String msg, int progress) {
		area.setText(msg + "          ");
		init();
		progressBar.setValue(progress);
		System.out.print(" " + progress);
	}
	
	public void done() {
		if (frame.isVisible() || ! toInit) {
			frame.setVisible(false);
		}
	}
	
	private void init() {
		if (toInit) {
			BasePanel pnl = new BasePanel();
			progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			//area = new JTextArea();
			area.setFont(SwingUtils.getMonoSpacedFont());
			
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
			toInit = false;
			frame.setVisible(true);
		}
	}
}
 