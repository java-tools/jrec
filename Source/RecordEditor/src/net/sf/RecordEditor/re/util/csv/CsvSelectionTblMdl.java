package net.sf.RecordEditor.re.util.csv;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordRunTimeException;
import net.sf.JRecord.CsvParser.AbstractParser;
import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import net.sf.JRecord.CsvParser.ParserManager;



@SuppressWarnings("serial")
public class CsvSelectionTblMdl extends AbstractTableModel implements AbstractCsvTblMdl {

	//	private ParserManager parserManager;
		private int columnCount = 1;
		private int lines2display = 0;
		private byte[][] lines = null;
		private String fontname = "";
		private String seperator = "";
		private byte sepByte;
		private String quote = "";
		private ParserManager parserManager;
		private int parserType = 0;

		private int lines2hide = 0;

		private String[] columnNames = null;

		private boolean namesOnLine;
		private int lineNoFieldNames = 1;

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
				BasicParser parser = new BasicParser(false);
				for (int i = lines2hide; i < lines2display; i++) {
					columnCount = Math.max(
									columnCount,
									parser.getFieldCount(getLine(i), sep, quote));
				}
			}
		}

		/**
		 * Get the index of the parser
		 * @return
		 */
		private AbstractParser getParser() {
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
            		s = (new BinaryCsvParser(seperator)).getValue(lines[rowIndex + lines2hide], columnIndex + 1, fontname);
            	} else {
	            	//AbstractParser p = getParser();
	                s = getParser().getField(columnIndex, getLine(rowIndex + lines2hide), seperator, quote);
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
			return Conversion.toString(lines[idx], fontname);
		}

		/**
		 * @return the lines
		 */
        @Override
		public byte[][] getLines() {
			return lines;
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
			this.fontname = font;
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
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#setFont(java.lang.String)
		 */
		@Override
		public void setFont(String font) {
			// TODO Auto-generated method stub

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
						columnNames[i] = bParser.getValue(lines[lineNoFieldNames - 1], i+1, fontname);
					}
				} else {
					AbstractParser p = parserManager.get(parserType);
					List<String> colnames = p.getColumnNames(getLine(lineNoFieldNames - 1), seperator, quote);
					columnNames = new String[colnames.size()] ;
					columnNames = colnames.toArray(columnNames);
				}
			}
		}

		/* (non-Javadoc)
		 * @see net.sf.RecordEditor.utils.csv.AbstractCsvTblMdl#getAnalyser()
		 */
		@Override
		public CsvAnalyser getAnalyser() {

			byte b = sepByte;
			if (seperator != null
			&& seperator.length() > 0
			&& ! isBinSeperator()) {
				b = seperator.getBytes()[0];
			}
			return new CsvAnalyser(getLines(), getLines2display(), "", b);
		}

		private boolean isBinSeperator() {
			return seperator != null && seperator.toLowerCase().startsWith("x'");
		}


}
