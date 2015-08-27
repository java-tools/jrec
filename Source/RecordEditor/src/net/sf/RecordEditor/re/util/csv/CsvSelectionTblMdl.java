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
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.CsvParser.ICsvLineParser;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.RecordEditor.utils.common.Common;



@SuppressWarnings("serial")
public class CsvSelectionTblMdl extends AbstractTableModel implements AbstractCsvTblMdl {

	private static final int LINES_TO_READ = 60;

	//	private ParserManager parserManager;
	private int columnCount = 1;
	private int lines2display = 0;
	private byte[][] lines = null;
	private String charset = "";
	private String seperator = "";
	private byte sepByte;
	private String quote = "";
	private ParserManager parserManager;
	private int parserType = 0;

	private int lines2hide = 0;

	private String[] columnNames = null;

	private boolean namesOnLine;
	private int lineNoFieldNames = 1;

	private byte[] data = null;
	private boolean embeddedCr;


		public CsvSelectionTblMdl() {
			this(ParserManager.getInstance());
		}

		/**
		 * CsvSelection Table model
		 * @param theParserManager CSV parser manager
		 */
		public CsvSelectionTblMdl(ParserManager theParserManager) {
			super();
			parserManager = theParserManager;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int col) {
			if (! namesOnLine
			|| columnNames == null     || col >= columnNames.length
			|| columnNames[col] == null || "".equals(columnNames[col] )) {
				return super.getColumnName(col);
			}
			return columnNames[col];
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setupColumnCount()
		 */
		@Override
		public final void setupColumnCount() {

			String sep = seperator;

			columnCount = 1;
			if (isBinSeperator()) {
				for (int i = lines2hide; i < lines2display; i++) {
					columnCount = Math.max(columnCount,
							(new BinaryCsvParser(sepByte)).countTokens(lines[i]));
				}
			} else {
				BasicCsvLineParser parser = new BasicCsvLineParser(false);
				CsvDefinition csvDef = new CsvDefinition(sep, quote);
				for (int i = lines2hide; i < lines2display; i++) {
					columnCount = Math.max(
									columnCount,
									parser.getFieldCount(getLine(i), csvDef));
				}
			}
		}

		/**
		 * Get the index of the parser
		 * @return
		 */
		private ICsvLineParser getParser() {
			return parserManager.get(parserType);
		}


		/**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
		@Override
        public int getColumnCount() {
            return columnCount;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
		@Override
        public int getRowCount() {
            return lines2display - lines2hide;
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
		@Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            String s = "";
            if ((rowIndex < lines2display - lines2hide)
            && columnIndex < columnCount) {
            	if (isBinSeperator()) {
            		s = (new BinaryCsvParser(seperator)).getValue(lines[rowIndex + lines2hide], columnIndex + 1, charset);
            	} else {
	            	//AbstractParser p = getParser();
	                s = getParser().getField(columnIndex, getLine(rowIndex + lines2hide), new CsvDefinition(seperator, quote));
            	}
            }

            return s;
        }

		/**
		 * @return the lines2display
		 */
        @Override
		public int getLines2display() {
			return lines2display;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setLines2display(int)
		 */
        @Override
		public void setLines2display(int lines2display) {
			this.lines2display = lines2display;
		}

//		/**
//		 * @return the lines
//		 */
//		public String[] getLines() {
//			return lines;
//		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getLine(int)
		 */
        @Override
		public final String getLine(int idx) {
			return Conversion.toString(lines[idx], charset);
		}

		/**
		 * @return the lines
		 */
        @Override
		public byte[][] getLines() {
			return lines;
		}


    	/**
    	 * @param font the font to set
    	 */
    	public void setDataFont(byte[] data, String font, boolean embeddedCr) {

    		this.data = data;
    		this.charset = font;
    		this.embeddedCr = embeddedCr;

    		readData();
    	}

    	private void readData() {
    		if (data != null) {
    			lines = new byte[LINES_TO_READ][];
    			int i = 0;
    			InputStream in = new ByteArrayInputStream(data);
    			String quoteEsc = quote == null ? "" : quote + quote;

				BaseByteTextReader r;
				byte[] b;

				if (embeddedCr) {
					r = new CsvByteReader(charset, seperator, quote, quoteEsc, namesOnLine);
				} else {
					r = new ByteTextReader(charset);
				}

				try {
					r.open(in);

					while (i < lines.length && (b = r.read()) != null) {
						lines[i++] = b;
					}
					r.close();
				} catch (Exception e) {
					Common.logMsg(AbsSSLogger.ERROR, "Error loading CSV Preview:", e.toString(), null);
					e.printStackTrace();
				}

				lines2display = Math.max(0, i - 1);
			}
    	}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getLinesString()
		 */
		@Override
		public String[] getLinesString() {
			throw new RecordRunTimeException("Not Implemented");
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setLines(java.lang.String[])
		 */
		@Override
		public void setLines(String[] lines) {
			throw new RecordRunTimeException("Not Implemented");
		}
		/**
		 * @param lines the lines to set
		 * @param font font name to use
		 */
        @Override
		public void setLines(byte[][] lines, String font) {
			this.charset = font;
			this.lines = lines;
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getParserType()
		 */
        @Override
		public int getParserType() {
			return parserType;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setParserType(int)
		 */
		@Override
		public void setParserType(int parserType) {
			this.parserType = parserType;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setQuote(java.lang.String)
		 */
		@Override
		public void setQuote(String quote) {
			this.quote = quote;
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.re.util.csv.AbstractCsvTblMdl#setEmbedded(boolean)
		 */
		@Override
		public void setEmbedded(boolean newEmbedded) {
			embeddedCr = newEmbedded;
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setFont(java.lang.String)
		 */
		@Override
		public void setFont(String font) {
			if (! this.charset.equals(font)) {
				this.charset = font;
				readData();
			}
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setSeperator(java.lang.String)
		 */
		@Override
		public void setSeperator(String newSeperator) {
			this.seperator = newSeperator;

			if (isBinSeperator()) {
				try {
					sepByte = Conversion.getByteFromHexString(seperator);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}


		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setHideFirstLine(boolean)
		 */
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
			if (namesOnLine && lines[0].length >= lineNoFieldNames) {
				int i;

				if (lineNoFieldNames == 1) {
					lines2hide = 1;
				}
				if (isBinSeperator()) {
					BinaryCsvParser bParser = new BinaryCsvParser(seperator);
					columnNames = new String[bParser.countTokens(lines[lineNoFieldNames - 1])] ;
					for (i = 0; i < columnNames.length; i++) {
						columnNames[i] = bParser.getValue(lines[lineNoFieldNames - 1], i+1, charset);
					}
				} else {
					ICsvLineParser p = parserManager.get(parserType);
					List<String> colnames = p.getColumnNames(getLine(lineNoFieldNames - 1), new CsvDefinition(seperator, quote));
					columnNames = new String[colnames.size()] ;
					columnNames = colnames.toArray(columnNames);
				}
			}
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getAnalyser()
		 */
		@Override
		public CsvAnalyser getAnalyser(int option) {


			if (option == DERIVE_SEPERATOR) {
				return new CsvAnalyser(getLines(), getLines2display(), charset, embeddedCr);
			}

			byte b = sepByte;
			if (seperator != null
			&& seperator.length() > 0
			&& ! isBinSeperator()) {
				b = seperator.getBytes()[0];
			}
			return new CsvAnalyser(getLines(), getLines2display(), charset, b, embeddedCr);
		}

		private boolean isBinSeperator() {
			return seperator != null && seperator.toLowerCase().startsWith("x'");
		}


}
