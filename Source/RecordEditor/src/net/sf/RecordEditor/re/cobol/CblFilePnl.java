package net.sf.RecordEditor.re.cobol;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTabbedPane;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;

import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.swing.BaseHelpPanel;

public class CblFilePnl {

	public final JTabbedPane fileTab = new JTabbedPane();
	
	private final CblLoadData dtls;
	private final BaseHelpPanel parentPnl;
	private ReadFile rf = null;
	
	private static final int O_LOAD = 1; 
	private static final int O_LOAD_UPDATE = 2; 
	private static final int O_LOAD_ANALYSE = 3; 
	
	
	protected CblFilePnl(CblLoadData dtls, BaseHelpPanel pnl) {
		super();
		this.dtls = dtls;
		this.parentPnl = pnl;
		
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

	
	public void redoAnalysis() {
		
		try {
			ExternalRecord xRec = dtls.getXRecord();

			ArrayList<AbstractLine>[] lines = new ArrayList[Math.max(1, xRec.getNumberOfRecords())];
			LayoutDetail schema = xRec.asLayoutDetail();
			
			byte[] b = dtls.getFileBytes();
			if (b == null) {return;}
			ArrayList<AbstractLine> allLines = readFileLines(null, b, schema);

			if (allLines != null && allLines.size() > 1) {
				doLineAnalysis(null, schema, allLines);
	
				loadTabs(null, lines, allLines);
				dtls.setDataLines(lines, true, true);
			}
		} catch (IOException e) {
			Common.logMsg(e.toString(), e);
			e.printStackTrace();
		}
	
	}

	
	/**
	 * @param b
	 * @param schema
	 * @return
	 * @throws IOException
	 * @throws RecordException
	 */
	private ArrayList<AbstractLine> readFileLines(
			ReadFile readf, byte[] b, LayoutDetail schema) throws IOException, RecordException {
		AbstractLine l;
		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(schema);
		ArrayList<AbstractLine> allLines = new ArrayList<AbstractLine>(); 
		try {
			r.open(new ByteArrayInputStream(b), schema);
		
			if (readf == null) {
				while ((l = r.read()) != null) {
					allLines.add(l);
				}
			} else {
				while ((l = r.read()) != null) {
					if (! readf.cont) { return allLines;}
	
					allLines.add(l);
				}
			}
		} finally {
			try {
				r.close();
				r = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return allLines;
	}
	
	/**
	 * Look at data in the lines to try and work out the Record-Type values
	 * and corresponding record Layouts.
	 * 
	 * @param schema
	 * @param allLines all the lines read in for comparison.
	 */
	private void doLineAnalysis(IContinueCheck continueCheck, LayoutDetail schema, ArrayList<AbstractLine> allLines) {
		CobolCopybookAnalyser.doLineAnalysis(continueCheck, dtls.getRecordSelection(), dtls, schema, allLines);
	}
	
	
	/**
	 * @param lines
	 * @param allLines
	 */
	private boolean loadTabs(ReadFile readf, ArrayList<AbstractLine>[] lines,
			ArrayList<AbstractLine> allLines) {
		for (AbstractLine l : allLines) {
			if (readf != null && ! readf.cont) { return false;}
			int idx = Math.max(0, l.getPreferredLayoutIdx());
			if (lines[idx] == null) {
				lines[idx] = new ArrayList<AbstractLine>();
			}
			lines[idx].add(l);
		}
		return true;
	}

	private class ReadFile implements Runnable, IContinueCheck {

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
			try {
				ArrayList<AbstractLine> allLines = readFileLines(this, b, schema);

				if (! cont) {
					return;
				} 
				if (lines.length == 1) {
					lines[0] = allLines;
				} else {
					analyseLines(schema, lines, allLines);
				}
				
//				while ((l = r.read()) != null) {
//					if (! cont) { return;}
//					
//					int idx = Math.max(0, l.getPreferredLayoutIdx());
//					if (lines[idx] == null) {
//						lines[idx] = new ArrayList<AbstractLine>();
//					}
//					lines[idx].add(l);
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			update = new UpdateTab(lines, schema);
			javax.swing.SwingUtilities.invokeLater(update);
			//dtls.setDataLines(lines);
		}
		
		private void analyseLines(LayoutDetail schema, ArrayList<AbstractLine>[] lines, ArrayList<AbstractLine> allLines) {
			int option = getLoadOption(schema, allLines);
			
			boolean doUpdate = true;
			boolean updateDone = false;
			
			switch (option) {
			case O_LOAD:
				doUpdate = false;
				break;
//			case O_LOAD_ANALYSE:
//				loadTabs(lines, allLines);
//				break;
			case O_LOAD_UPDATE:
				doLineAnalysis(this, schema, allLines);
				updateDone = true;
			}
			
			if (cont) {
				loadTabs(this, lines, allLines);
				dtls.setDataLines(lines, doUpdate, updateDone);
			}
		}
		/**
		 * @param schema
		 * @return
		 */
		private int getLoadOption(LayoutDetail schema, ArrayList<AbstractLine> allLines) {
			int fileStructure = schema.getFileStructure();
			int option = O_LOAD_ANALYSE;
			boolean diffSizes = true;
			TIntObjectHashMap<Integer> recSizes = new TIntObjectHashMap<Integer>(schema.getRecordCount() * 3 + 5);
			
			if (dtls.isAnalysisRequired()) {
				option = O_LOAD_UPDATE;
			} else {
				for (int i = 0; i < schema.getRecordCount(); i++) {
					if (dtls.getFieldName(i).length() == 0) {
						
					} else if ( dtls.getFieldValue(i, 0).length() > 0
							|| dtls.getFieldValue(i, 1).length() > 0
							|| dtls.getFieldValue(i, 2).length() > 0) {
						option = O_LOAD;
						break;
					} else {
						option = O_LOAD_UPDATE;
					}
					
					Integer recLength = Integer.valueOf(schema.getRecord(i).getLength());
					if (recSizes.contains(recLength)) {
						diffSizes = false;
					} else {
						recSizes.put(recLength, i);
					}
				}
			}
			
			if (option == O_LOAD_UPDATE
			&& diffSizes
			&& fileStructure != Constants.IO_FIXED_LENGTH
			&& fileStructure != Constants.IO_FIXED_LENGTH_CHAR
			&& allLines.size() > 100) {
				int[] counts = new int[schema.getRecordCount()] ;
				Arrays.fill(counts, 0);
				
				for (AbstractLine l : allLines) {
					int length = l  instanceof Line ? l.getData().length : l.getFullLine().length();
					Integer recIdx = recSizes.get(length);
					if (recIdx == null) { return O_LOAD_UPDATE;}
					counts[recIdx] += 1;
				}
				
				int cmp = (allLines.size() * 9) / 10;
				for (int i = 0; i < counts.length; i++) {
					if (counts[i] >= cmp ) {return O_LOAD_UPDATE;}
				}
				option = O_LOAD_ANALYSE;
			}

			return option;
		}

		@Override
		public boolean isOkToContinue() {
			return cont;
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
						CblFileRecordPane cblFileRecordPane = new CblFileRecordPane(record, lines[i]);
						
						fileTab.remove(tabCount - j++);
						fileTab.addTab(
							record.getRecordName(),
							cblFileRecordPane.pane);
						parentPnl.registerComponentRE(cblFileRecordPane.pane);
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
	
	private static class Counter {
		int count = 1;
	}
}

