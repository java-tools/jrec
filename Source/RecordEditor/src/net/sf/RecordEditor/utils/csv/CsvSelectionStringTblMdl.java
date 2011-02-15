package net.sf.RecordEditor.utils.csv;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.CsvParser.AbstractParser;
import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class CsvSelectionStringTblMdl 
     extends AbstractTableModel 
  implements AbstractCsvTblMdl {
	
	private final int lines2read = 30;
	
	private byte[] data;
	private int columnCount = 1;
	private int lines2display = 0;		
	private String[] lines = null;
	private String seperator = "";
	private String quote = "";
	private String font = "";
	private ParserManager parserManager;
	private int parserType = 0;
	
	private int lines2hide = 0;
	
	private String[] columnNames = null;
    
	
	public CsvSelectionStringTblMdl() {
		this(ParserManager.getInstance());
	}
	/**
	 * CsvSelection Table model 
	 * @param theParserManager CSV parser manager
	 */		
	public CsvSelectionStringTblMdl(ParserManager theParserManager) {
		super();
		parserManager = theParserManager;
	}
	
	@Override
	public String getLine(int idx) {
		return lines[idx];
	}

	@Override
	public byte[][] getLines() {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public String[] getLinesString() {
		return lines;
	}

	@Override
	public int getLines2display() {
		return lines2display;
	}

	@Override
	public int getParserType() {
		return parserType;
	}

	@Override
	public void setHideFirstLine(boolean hide) {
		lines2hide = 0;
		columnNames = null;
		if (hide && lines[0].length() > 0) {
			lines2hide = 1;
			
			AbstractParser p = parserManager.get(parserType);
			List<String> colnames = p.getColumnNames(getLine(0), seperator, quote);
			columnNames = new String[colnames.size()] ;
			columnNames = colnames.toArray(columnNames);
			
		}
	}

	@Override
	public void setLines(byte[][] lines, String font) {
		throw new RuntimeException("Not Implemented");
	}
	
	@Override
	public void setLines(String[] lines) {
		this.lines = lines;
	}

	public void setData(byte[] data) {
		this.data = data;
		
		readData();
	}

	@Override
	public void setLines2display(int lines2display) {
		this.lines2display = lines2display;

	}

	@Override
	public void setParserType(int parserType) {
		this.parserType = parserType;
	}
	
	/**
	 * @param font the font to set
	 */
	@Override
	public void setFont(String font) {
		if (! this.font.equals(font)) {
			this.font = font;
			readData();
		}
	}
	
	
	/**
	 * @param font the font to set
	 */
	public void setDataFont(byte[] data, String font) {
		this.data = data;
		this.font = font;
		readData();
		
	}

	@Override
	public void setQuote(String quote) {
		this.quote = quote;
	}

	/**
	 * @param newSeperator the seperator to set
	 */
	@Override
	public void setSeperator(String newSeperator) {
		this.seperator = newSeperator;
	}

	@Override
	public void setupColumnCount() {

		String sep = seperator;
		BasicParser parser = new BasicParser(false);
					
		columnCount = 1;
		for (int i = 0; i < lines2display; i++) {
			columnCount = Math.max(columnCount, 
								   parser.getFieldCount(lines[i], sep, quote));
		}	
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		if (columnNames == null     || col >= columnNames.length
		|| columnNames[col] == null || "".equals(columnNames[col] )) {
			return super.getColumnName(col);
		}
		return columnNames[col];
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public int getRowCount() {

		return lines2display - lines2hide;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
        String s = "";
        if ((rowIndex < lines2display - lines2hide)
        && columnIndex < columnCount) {
        	AbstractParser p = getParser();
            s = p.getField(columnIndex, lines[rowIndex + lines2hide], seperator, quote);
        }

        return s;	
    }
	
	
	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getAnalyser()
	 */
	@Override
	public CsvAnalyser getAnalyser() {
		
		char b = ',';
		if (seperator != null 
		&& seperator.length() > 0) {
			b = seperator.charAt(0);
		}
		return new CsvAnalyser(lines, getLines2display(), font, b);
	}

	
	
	/**
	 * Get the index of the parser
	 * @return
	 */
	private AbstractParser getParser() {
		return parserManager.get(parserType);
	}

	
	private void readData() {
		if (data != null) {
			InputStream in = new ByteArrayInputStream(data);
			int i = 0;
			BufferedReader r;
			
			lines = new String[lines2read];
			
	
			try {
				if ("".equals(font)) {
					r = new BufferedReader(new InputStreamReader(in));
				} else {
					try {
						r = new BufferedReader(new InputStreamReader(in, font));
					} catch (UnsupportedEncodingException e) {
						Common.logMsg("Invalid Fon Specified on CSV Preview screen", null);
						r = new BufferedReader(new InputStreamReader(in));
					}
				}
				
			
				while (i < lines.length && (lines[i] = r.readLine()) != null) {
					i +=1;
				}
			} catch (Exception e) {
				Common.logMsg("Error loading CSV Preview " + e.getMessage(), null);
			}
			
			lines2display = i;
		}
	}
}
