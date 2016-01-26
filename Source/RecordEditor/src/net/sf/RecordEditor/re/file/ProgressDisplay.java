package net.sf.RecordEditor.re.file;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;

/**
 * This class displays a tasks pregress on the screen
 * 
 * @author Bruce Martin
 *
 */
public class ProgressDisplay  {
	private JFrame frame = null;
//	private boolean toInit = true;
	private JTextArea area = new JTextArea();
	private JProgressBar progressBar;
	private final String frameName;



	
	
	public ProgressDisplay(String action, String name) {
		frameName = action + " " + name;
	}
	
	public void updateDisplay(String msg, int progress) {
		area.setText(msg + "          ");
		System.out.println(msg);
		init();
		progressBar.setValue(progress);
	}
	

	public void done() {
		if (frame != null && frame.isVisible()) {
			frame.setVisible(false);
		}
	}
	
	private void init() {
		if (frame == null) {
			synchronized (area) {
				if (frame == null) {
					frame = new JFrame(frameName);
					BasePanel pnl = new BasePanel();
					
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
					progressBar = new JProgressBar(0, 100);
					progressBar.setValue(0);
					//area = new JTextArea();
		
					pnl.addComponentRE(1, 5,
					         BasePanel.PREFERRED,
					         BasePanel.GAP1,
					         BasePanel.FULL, BasePanel.FULL, progressBar);
		

					area.setFont(SwingUtils.getMonoSpacedFont());
					pnl.addComponentRE(1, 5,
					         BasePanel.PREFERRED,
					         BasePanel.GAP1,
					         BasePanel.FULL, BasePanel.FULL, area);
					
					frame.getContentPane().add(pnl);
					frame.pack();
					//frame.setSize(250, frame.getHeight()+25);
		
					frame.setVisible(true);
				}
			}
		}
	}
}
 