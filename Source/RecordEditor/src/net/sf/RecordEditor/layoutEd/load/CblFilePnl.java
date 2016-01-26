package net.sf.RecordEditor.layoutEd.load;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;

public class CblFilePnl {

	public final JTabbedPane fileTab = new JTabbedPane();
	
	private final CblLoadData dtls;
	private ReadFile rf = null;
	
	
	protected CblFilePnl(CblLoadData dtls) {
		super();
		this.dtls = dtls;
		
		fileTab.addFocusListener(new FocusAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.FocusAdapter#focusGained(java.awt.event.FocusEvent)
			 */
			@Override
			public void focusGained(FocusEvent e) {
				updateScreen();
			}
		});
	}
	
	
	public void updateScreen() {
		if (dtls.isCopybookChanged()) {
			if (rf != null) {
				rf.doStop();
			}
			
			rf = new ReadFile();
			(new Thread(rf)).start();
			dtls.setCopybookChanged(false);
		}
	}
	
	private class ReadFile implements Runnable {

		private boolean cont = true;
		private UpdateTab update = null;
		
		
		private void doStop() {
			cont = false;
			if (update != null) {
				update.cont = false;
			}
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			ExternalRecord xRec = dtls.getXRecord();
			byte[] b = dtls.getFileBytes();
			if (b == null || xRec == null) {
				rf = null;
				return;
			}
			@SuppressWarnings("unchecked")
			ArrayList<AbstractLine>[] lines = new ArrayList[Math.max(1, xRec.getNumberOfRecords())];
			LayoutDetail schema = xRec.asLayoutDetail();
			AbstractLine l;
			AbstractLineReader r = LineIOProvider.getInstance().getLineReader(schema);
			try {
				r.open(new ByteArrayInputStream(b), schema);
			
				while ((l = r.read()) != null) {
					if (! cont) { return;}
					
					int idx = Math.max(0, l.getPreferredLayoutIdx());
					if (lines[idx] == null) {
						lines[idx] = new ArrayList<AbstractLine>();
					}
					lines[idx].add(l);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			update = new UpdateTab(lines, schema);
			javax.swing.SwingUtilities.invokeLater(update);
			dtls.setDataLines(lines);
		}
		
		
	}
	private class UpdateTab implements Runnable {
		private final ArrayList<AbstractLine>[] lines;
		private final LayoutDetail layout;
		private boolean cont = true;

		protected UpdateTab(ArrayList<AbstractLine>[] lines, LayoutDetail l) {
			super();
			this.lines = lines;
			this.layout = l;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			synchronized (fileTab) {
				int tabCount = fileTab.getTabCount();
				int m = Math.min(tabCount, lines.length);
				int j = 1;
				
				for (int i = 0; cont && i < m; i++) {
					if (lines[i] != null && lines[i].size() > 0) {
						RecordDetail record = layout.getRecord(i);
						fileTab.remove(tabCount - j++);
						fileTab.addTab(
							record.getRecordName(),
							(new CblFileRecordPane(record, lines[i])).pane);
					}
				}
				
				for (int i = tabCount - j; cont && i >= 0; i--) {
					fileTab.remove(i);
				}
				
				for (int i = m; cont && i < lines.length; i++) {
					if (lines[i] != null && lines[i].size() > 0) {
						RecordDetail record = layout.getRecord(i);
						fileTab.addTab(
							record.getRecordName(),
							(new CblFileRecordPane(record, lines[i])).pane);
					}
				}
			}
			
		}
	}
}

