package net.sf.RecordEditor.re.file;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import net.sf.RecordEditor.utils.screenManager.ReMsg;
import net.sf.RecordEditor.utils.swing.BasePanel;
import net.sf.RecordEditor.utils.swing.SwingUtils;
import net.sf.RecordEditor.utils.swing.common.IProgressDisplay;

/**
 * This class displays a tasks pregress on the screen
 * 
 * @author Bruce Martin
 *
 */
public class ProgressDialog implements IProgressDisplay {
	private static final long INTERVAL = 750000000l;
	private JDialog frame = null;
//	private boolean toInit = true;
	private JTextArea area = new JTextArea();
	private JProgressBar progressBar;
	private final String frameName;
	private ReMsg message;
	
	private int max = 100, checkCountAt, countSinceCheck = 0;
	private long lastTime = System.nanoTime() - (INTERVAL / 10) * 9;
	private boolean cont = true;

	public ProgressDialog(String heading, ReMsg msg,  int total, int checkCountAt) {
		this.frameName = heading;
		this.message = msg;
		this.max = total;
		this.checkCountAt = checkCountAt;

//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {	
//				area.setText(message.get(0, max) + "             ");
//				init();
//			}
//		});
	}

	
	/**
	 * @see net.sf.RecordEditor.utils.swing.common.IProgressDisplay#updateProgress(int)
	 */
	@Override
	public boolean updateProgress(final int count, final int other) {
		if (countSinceCheck++ > checkCountAt) {
			long time = System.nanoTime();
			countSinceCheck = 0;
			if (time - lastTime > INTERVAL) {
//				area.setText(count + "          ");
				lastTime = time;
//				System.out.println("--> " + count + " of " + max);
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {	
						area.setText(message.get(count, max, other) + "             ");
						init();
						
						progressBar.setValue(count);
					}
				});
			}
		}
		return cont;
	}
	
	/**
	 * @see net.sf.RecordEditor.utils.swing.common.IProgressDisplay#done()
	 */
	@Override
	public void done() {
		if (frame != null && frame.isVisible()) {
			frame.setVisible(false);
		}
	}
	
	private void init() {
		if (frame == null) {
			synchronized (area) {
				if (frame == null) {
					frame = new JDialog((JFrame) null, frameName);
					BasePanel pnl = new BasePanel();
					
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
					progressBar = new JProgressBar(0, max);
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
					frame.addWindowListener(new WindowAdapter() {

						@Override
						public void windowClosed(WindowEvent e) {
							cont = false;
							super.windowClosed(e);
						}
						
					});
				}
			}
		}
	}
}
 