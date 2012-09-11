package net.sf.RecordEditor.diff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.jibx.compare.Layout;
import net.sf.RecordEditor.utils.ExpandLineTree;
import net.sf.RecordEditor.utils.common.Common;

public class LineBufferedReader extends BufferedReader {
	
	private int currLine = 0;
	private List<AbstractLine> lines; 
	private AbstractLayoutDetails detail;
	
	private AbstractLayoutDetails filteredLayout;
	private final boolean stripSpaces;
	private ExpandLineTree expand;

	public LineBufferedReader(
			String fileName, 
			AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail> dtl, 
			List<AbstractLine> lineArray,
			boolean stripTrailingSpaces) 
	throws IOException, RecordException {
		super(new FileReader(fileName), 4); 
		super.close();
		
		filteredLayout = dtl;
		detail = dtl;

		stripSpaces = stripTrailingSpaces;
		
		if (dtl.hasChildren()) {
			lines  = new ArrayList<AbstractLine>(lineArray.size() * 2);
			expand = ExpandLineTree.newExpandLineTree(lines);
			
			for (AbstractLine line : lineArray) {
				expand.expand(line);
			}
		} else {
			lines  = lineArray;	
		}
	}
	
	
	public LineBufferedReader(String fileName, AbstractLayoutDetails dtl, AbstractLayoutDetails newDtl, Layout layoutDef,
			boolean stripTrailingSpaces) 
	throws IOException, RecordException {
		super(new FileReader(fileName), 4);
		super.close();
		
		boolean noFilter = (layoutDef == null) || layoutDef.records == null || layoutDef.records.size() == 0;
		
		AbstractLineIOProvider ioProvider = LineIOProvider.getInstance();
		
		AbstractLineReader reader = ioProvider.getLineReader(dtl.getFileStructure());
		AbstractLine line;
		
		filteredLayout = dtl;
		detail = dtl;
		stripSpaces = stripTrailingSpaces;
		
		
		lines  = new ArrayList<AbstractLine>(256);
		if (detail.hasChildren()) {
			expand = ExpandLineTree.newExpandLineTree(lines);
		}
		
		reader.open(fileName, detail);
		
		if (noFilter) {
			while ((line = reader.read()) != null) {
				addLine(line);
			}
		} else {
			boolean[] include = new boolean[dtl.getRecordCount()];
			List<AbstractLine> list;			
				
			filteredLayout = newDtl;
			if (newDtl == null) {
				filteredLayout = dtl.getFilteredLayout(layoutDef.getFilteredRecords());
			}
			
			if (detail.getRecordCount() == 1) {
				while ((line = reader.read()) != null) {
					list = ExpandLineTree.expandTree(line);
					for (AbstractLine xLine: list) {
						xLine.setLayout(filteredLayout);
						lines.add(xLine);
					}
				}
			} else {
				int idx, j;
				for (j = 0; j < include.length; j++) {
					include[j] = include.length == layoutDef.records.size();
				}
				
				if (include.length != layoutDef.records.size()) {
					for (j = 0; j < layoutDef.records.size(); j++) {
						include[dtl.getRecordIndex(layoutDef.records.get(j).name)] = true;
					}
				}
				

				//AbstractLine xLine;
				while ((line = reader.read()) != null) {
					list = ExpandLineTree.expandTree(line);
					for (AbstractLine xLine: list) {
						idx = xLine.getPreferredLayoutIdx();
						if (idx >= 0 && idx < include.length && include[idx]) {
							xLine.setLayout(filteredLayout);
							lines.add(xLine);
						}
					}
				}
			}
		}
		reader.close();
	}

	/**
	 * Add a line to the list
	 * @param l
	 */
	private void addLine(AbstractLine l) {
		if (detail.hasChildren()) {
			expand.expand(l);
		} else {
			lines.add(l);
		}
	}
	
	
	@Override
	public void close() throws IOException {
	
	}

	@Override
	public boolean markSupported() {
		return false;
	}

	@Override
	public int read() throws IOException {
		throw new RuntimeException("read not supported");
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		throw new RuntimeException("read not supported");
	}

	@Override
	public String readLine() throws IOException {

		return readLine(currLine++);
	}

	public String readLine(int lineNo) throws IOException {
		if (lineNo < lines.size()) {
			AbstractLine line = lines.get(lineNo);
			StringBuffer buf = new StringBuffer();
			int pref = line.getPreferredLayoutIdx();
			
			if (detail.isMapPresent()) {
				Object o = line.getField(pref, Constants.KEY_INDEX);
				
				if (o != null) {
					buf.append(Common.trimRight(o)).append("\t");
				}
			}
			
			if (filteredLayout.getRecord(pref) == null) {
				Common.logMsg("Layout Record Does not exist !!!", null);
			} else if (stripSpaces) {
				for (int i = 0; i < filteredLayout.getRecord(pref).getFieldCount(); i++) {
					buf.append(Common.trimRight(line.getField(pref,i))).append("\t");
				}
			} else {
				Object s;
				for (int i = 0; i < filteredLayout.getRecord(pref).getFieldCount(); i++) {
					s = line.getField(pref,i);
					if (s == null) {
						s = "";
					}
					buf.append(s).append("\t");
				}
			}
		
//			if (lineNo < 25) {
//			System.out.println("!! " + lineNo + " "+ buf.toString() + ": " + pref + " " + filteredLayout.getLayoutName() 
//					+ " " +  filteredLayout.getRecord(pref).getRecordName()
//					+ " " + filteredLayout.getRecord(pref).getFieldCount());
//			}
			return buf.toString();
		}
		return null;
	}

	@Override
	public boolean ready() throws IOException {
		return true;
	}

	@Override
	public void reset() throws IOException {
		currLine = 0;
	}

	@Override
	public long skip(long n) throws IOException {
		throw new RuntimeException("skip not supported");
	}
	
	public int getCount() {
		return lines.size();
	}

	public AbstractLine getLine(int index) {
		return lines.get(index);
	}


	/**
	 * @return the filteredLayout
	 */
	public final AbstractLayoutDetails<? extends FieldDetail, ? extends AbstractRecordDetail> getFilteredLayout() {
		return filteredLayout;
	}
}
