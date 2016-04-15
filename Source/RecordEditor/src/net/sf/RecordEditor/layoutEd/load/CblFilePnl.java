package net.sf.RecordEditor.layoutEd.load;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JTabbedPane;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;

import net.sf.RecordEditor.layoutWizard.Details;
import net.sf.RecordEditor.layoutWizard.FieldSearch;
import net.sf.RecordEditor.layoutWizard.RecordDefinition;
import net.sf.RecordEditor.trove.map.hash.TIntObjectHashMap;

public class CblFilePnl {

	public final JTabbedPane fileTab = new JTabbedPane();
	
	private final CblLoadData dtls;
	private ReadFile rf = null;
	
	private static final int O_LOAD = 1; 
	private static final int O_LOAD_UPDATE = 2; 
	private static final int O_LOAD_ANALYSE = 3; 
	
	
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
				ArrayList<AbstractLine> allLines = new ArrayList<AbstractLine>(); 
				try {
					r.open(new ByteArrayInputStream(b), schema);
				
					while ((l = r.read()) != null) {
						if (! cont) { return;}
	
						allLines.add(l);
					}
				} finally {
					try {
						r.close();
						r = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
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
				doLineAnalysis(schema, allLines);
				updateDone = true;
			}
			
			if (cont) {
				loadTabs(lines, allLines);
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
			
			for (int i = 0; i < schema.getRecordCount(); i++) {
				if (dtls.getRecordSelection(i, 0).length() == 0) {
					
				} else if ( dtls.getRecordSelection(i, 1).length() > 0
						|| dtls.getRecordSelection(i, 2).length() > 0
						|| dtls.getRecordSelection(i, 3).length() > 0) {
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
		
		
		/**
		 * Look at data in the lines to try and work out the Record-Type values
		 * and corresponding record Layouts.
		 * 
		 * @param schema
		 * @param allLines all the lines read in for comparison.
		 */
		private void doLineAnalysis(LayoutDetail schema, ArrayList<AbstractLine> allLines) {
			String keyFieldName;
			IFieldDetail keyFld = null;
			
			// Find the Record-Type fields
			int recordCount = schema.getRecordCount();
			for (int i = 0; i < recordCount; i++) {
				keyFieldName = dtls.getRecordSelection(i, 0);
				if (dtls.getRecordSelection(i, 0).length() > 0 
				&& (keyFld = schema.getFieldFromName(keyFieldName)) != null) {
					break;
				}
			}
			if (keyFld != null) {
				HashMap<String, ArrayList<AbstractLine>> linesByKey = new HashMap<String, ArrayList<AbstractLine>>(recordCount * 3 + 10);
				int intialCount = Math.max(10, allLines.size() / recordCount);
				String key;
				
				// Group the lines by record type
				for (AbstractLine l : allLines) {
					if (! cont) { return;}
					key = l.getFieldValue(keyFld).asString();
					if (key != null) {
						key = key.toLowerCase();
						ArrayList<AbstractLine> list = linesByKey.get(key);
						if (list == null) {
							list = new ArrayList<AbstractLine>(intialCount);
							linesByKey.put(key, list);
						}
						list.add(l);
					}
				}
				Set<Entry<String, ArrayList<AbstractLine>>> keyListSet = linesByKey.entrySet();
				
				// For each record-type group, try and work out the fields 
				// and compare with the fields in the copybook.
				for (Entry<String, ArrayList<AbstractLine>> e : keyListSet) {
					ArrayList<AbstractLine> matchingLines = e.getValue();
					int numRecords = matchingLines.size();
					if (numRecords > 4) {
						Details details = new Details();
						RecordDefinition recordDefinition = details.standardRecord;
						details.fileStructure = schema.getFileStructure();
						details.fontName = schema.getFontName();
						
						recordDefinition.columnDtls.clear();
						
						if (recordDefinition.records.length < numRecords) {
							recordDefinition.records = new byte[numRecords][];
						}
						recordDefinition.numRecords = numRecords;
						
						for (int i = 0; i < numRecords; i++) { 
							recordDefinition.records[i] = matchingLines.get(i).getData();
						}
						
						FieldSearch fieldSearch = new FieldSearch(details, recordDefinition);
						
						fieldSearch.findFields(true, false, true, true, false);
						
						int currRec = -1, currMatch = -1;
						for (int j = 0; j < recordCount; j++) {
							if (! cont) {return ;}
							RecordDetail record = schema.getRecord(j);
							int i1 =0, i2 = 0, match = 0, matchType = 0;
							while (i1 < record.getFieldCount() && i2 < recordDefinition.columnDtls.size()) {
								if (record.getField(i1).getPos() < recordDefinition.columnDtls.get(i2).getStart()) {
									i1 += 1;
								} else if (record.getField(i1).getPos() > recordDefinition.columnDtls.get(i2).getStart()) {
									i2 += 1;
								} else {
									match += 1;
					
									if (record.getField(i1).getType() == recordDefinition.columnDtls.get(i2).getType()) {
										matchType +=1;
									}
									i1 += 1;
									i2 += 1;
								}
							}
							if (match * 2 + matchType > currMatch) {
								currRec = j;
								currMatch = match * 2 + matchType;
							}
						}
						dtls.addRecordSelection(currRec,  matchingLines.get(0).getFieldValue(keyFld).asString(), false);
					}
				}
				LayoutDetail newSchema = dtls.getXRecord().asLayoutDetail();
				for (AbstractLine l : allLines) {
					if (! cont) {return ;}
					l.setLayout(newSchema);
				}
			}
		}
		
		
		/**
		 * @param lines
		 * @param allLines
		 */
		private boolean loadTabs(ArrayList<AbstractLine>[] lines,
				ArrayList<AbstractLine> allLines) {
			for (AbstractLine l : allLines) {
				if (! cont) { return false;}
				int idx = Math.max(0, l.getPreferredLayoutIdx());
				if (lines[idx] == null) {
					lines[idx] = new ArrayList<AbstractLine>();
				}
				lines[idx].add(l);
			}
			return true;
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
	
	private static class Counter {
		int count = 1;
	}
}

