package net.sf.RecordEditor.re.util.csv;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.ByteIO.BaseByteTextReader;
import net.sf.JRecord.ByteIO.ByteTextReader;
import net.sf.JRecord.ByteIO.CsvByteReader;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.JRecord.CsvParser.BasicCsvLineParser;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.CsvParser.ICsvLineParser;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.charIO.CsvCharReader;
import net.sf.JRecord.charIO.ICharReader;
import net.sf.JRecord.charIO.StandardCharReader;
import net.sf.RecordEditor.utils.common.Common;

@SuppressWarnings("serial")
public class CsvSelectionStringTblMdl
     extends AbstractTableModel
  implements AbstractCsvTblMdl {

	private static final int LINES_TO_READ = 60;

	private byte[] data;

	private int columnCount = 1;
	private int lines2display = 0;
	private String[] lines = null;
	private String seperator = "";
	private String quote = "";
	private String charset = "";
	private ParserManager parserManager;
	private int parserType = 0;

	private boolean namesOnLine;
	private boolean embeddedCr;
	private int lineNoFieldNames = 1;

	private int lines2hide = 0;

	private String[] columnNames = null;
	
	private CsvDefinition csvDef;


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
		throw new RecordRunTimeException("Not Implemented");
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

		columnNames = null;
		namesOnLine = hide;

		setupColumnNames();
	}



	/**
	 * @see net.sf.RecordEditor.re.util.csv.AbstractCsvTblMdl#setFieldLineNo(int)
	 */
	@Override
	public void setFieldLineNo(int lineNo) {
		lineNoFieldNames = lineNo;
		setupColumnNames();
	}


	private void setupColumnNames() {

		lines2hide = 0;
		if (namesOnLine && lines[0].length() >= lineNoFieldNames) {
			if (lineNoFieldNames == 1) {
				lines2hide = 1;
			}

			ICsvLineParser p = parserManager.get(parserType);
			List<String> colnames = p.getColumnNames(getLine(lineNoFieldNames - 1), getCsvDef());
//					new CsvDefinition(seperator, quote, embeddedCr));
			columnNames = new String[colnames.size()] ;
			columnNames = colnames.toArray(columnNames);

		}
	}

	@Override
	public void setLines(byte[][] lines, String font) {
		throw new RecordRunTimeException("Not Implemented");
	}

	@Override
	public void setLines(String[] lines) {
		this.lines = lines;
	}

//	public void setData(byte[] data) {
//		this.data = data;
//
//		readData();
//	}

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
		if (! this.charset.equals(font)) {
			this.charset = font;
			readData();
		}
	}


	/**
	 * @param font the font to set
	 */
	@Override
	public void setDataFont(byte[] data, String font, boolean embeddedCr) {

		this.data = data;
		this.charset = font;
		this.embeddedCr = embeddedCr;
		csvDef = null;

		readData();
	}

	@Override
	public void setQuote(String quote) {
		this.quote = quote;
		csvDef = null;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.re.util.csv.AbstractCsvTblMdl#setEmbedded(boolean)
	 */
	@Override
	public void setEmbedded(boolean newEmbedded) {
		embeddedCr = newEmbedded;
		csvDef = null;
	}

	/**
	 * @param newSeperator the seperator to set
	 */
	@Override
	public void setSeperator(String newSeperator) {
		this.seperator = newSeperator;
		csvDef = null;
	}

	@Override
	public void setupColumnCount() {

		//String sep = seperator;
		BasicCsvLineParser parser = new BasicCsvLineParser(false);

		columnCount = 1;
		CsvDefinition csvDef = getCsvDef(); //new CsvDefinition(sep, quote, embeddedCr);
		for (int i = 0; i < lines2display; i++) {
			columnCount = Math.max(columnCount,
								   parser.getFieldCount(lines[i], csvDef));
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		if (! namesOnLine
		|| columnNames == null     || col >= columnNames.length
		|| columnNames[col] == null || "".equals(columnNames[col])) {
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
        	ICsvLineParser p = getParser();
            s = p.getField(
            		columnIndex,
            		lines[rowIndex + lines2hide],
            		getCsvDef() //new CsvDefinition(seperator, quote, embeddedCr)
            		);
        }

        return s;
    }

	private CsvDefinition getCsvDef() {
		if (csvDef == null) {
			csvDef = new CsvDefinition(
					Conversion.decodeFieldDelim(seperator, charset),
					Conversion.decodeCharStr(quote, charset),
					embeddedCr);
		}
		
		return csvDef;
	}

	/* (non-Javadoc)
	 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getAnalyser()
	 */
	@Override
	public CsvAnalyser getAnalyser(int option) {


		if (option == DERIVE_SEPERATOR) {
			return new CsvAnalyser(lines, getLines2display(), charset, embeddedCr);
		}

		char b = ',';
		if (seperator != null
		&& seperator.length() > 0) {
			b = getCsvDef().getDelimiter().charAt(0);
		}
		return new CsvAnalyser(lines, getLines2display(), charset, b, embeddedCr);
	}



	/**
	 * Get the index of the parser
	 * @return
	 */
	private ICsvLineParser getParser() {
		return parserManager.get(parserType);
	}


	private void readData() {
		if (data != null) {
			lines = new String[LINES_TO_READ];
			int i = 0;
			InputStream in = new ByteArrayInputStream(data);
			String q = getCsvDef().getDelimiter();
			String quoteEsc = quote == null ? "" : q + q;
			if (Conversion.isMultiByte(charset)) {
				ICharReader r;

				if (embeddedCr) {
					r = new CsvCharReader(getCsvDef().getDelimiter(), q, quoteEsc, namesOnLine);
				} else {
					r = new StandardCharReader();
				}

				try {
					r.open(in, charset);

					while (i < lines.length && (lines[i] = r.read()) != null) {
						i +=1;
					}
					r.close();
				} catch (Exception e) {
					Common.logMsg(AbsSSLogger.ERROR, "Error loading CSV Preview:", e.toString(), null);
					e.printStackTrace();
				}
			} else {
				BaseByteTextReader r;
				byte[] b;

				if (embeddedCr) {
					r = new CsvByteReader(charset, getCsvDef().getDelimiter(), getCsvDef().getQuote(), quoteEsc, namesOnLine);
				} else {
					r = new ByteTextReader(charset);
				}

				try {
					r.open(in);

					while (i < lines.length && (b = r.read()) != null) {
						lines[i++] = Conversion.toString(b,  charset);
					}
					r.close();
				} catch (Exception e) {
					Common.logMsg(AbsSSLogger.ERROR, "Error loading CSV Preview:", e.toString(), null);
					e.printStackTrace();
				}
			}

			lines2display = i;
		}
	}
}
