package net.sf.RecordEditor.utils.swing;

import java.util.StringTokenizer;

import javax.swing.table.AbstractTableModel;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.CsvParser.AbstractParser;
import net.sf.JRecord.CsvParser.BinaryCsvParser;
import net.sf.JRecord.CsvParser.ParserManager;



public class CsvSelectionTblMdl extends AbstractTableModel {
	
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
			if (columnNames == null     || col >= columnNames.length
			|| columnNames[col] == null || "".equals(columnNames[col] )) {
				return super.getColumnName(col);
			}
			return columnNames[col];
		}
		
		/**
		 * Set up the column details
		 */
		public final void setupColumnCount() {
		
			//AbstractParser parser = getParser();
			StringTokenizer t ;
			String sep = seperator;
						
			columnCount = 1;
			if (isBinSeperator()) {
				for (int i = lines2hide; i < lines2display; i++) {
					columnCount = Math.max(columnCount, 
							(new BinaryCsvParser(sepByte)).countTokens(lines[i]));
				}
			} else {
				for (int i = lines2hide; i < lines2display; i++) {
					t = new StringTokenizer(getLine(i), sep);
					
					columnCount = Math.max(columnCount, t.countTokens());
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
        public int getColumnCount() {
            return columnCount;
        }

        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            return lines2display - lines2hide;
        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
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
		public int getLines2display() {
			return lines2display;
		}

		/**
		 * @param lines2display the lines2display to set
		 */
		public void setLines2display(int lines2display) {
			this.lines2display = lines2display;
		}

//		/**
//		 * @return the lines
//		 */
//		public String[] getLines() {
//			return lines;
//		}

		private String getLine(int idx) {
			return Conversion.toString(lines[idx], fontname);
		}
		
		
		/**
		 * @param lines the lines to set
		 * @param font font name to use
		 */
		public void setLines(byte[][] lines, String font) {
			this.fontname = font;
			this.lines = lines;
		}


		/**
		 * @param quote the quote to set
		 */
		public void setQuote(String quote) {
			this.quote = quote;
		}

		/**
		 * @param newSeperator the seperator to set
		 */
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
		

		/**
		 * change wether the first line is hidden
		 * @param hide wether to hide first line
		 */
		public void setHideFirstLine(boolean hide) {
			lines2hide = 0;
			columnNames = null;
			if (hide && lines[0].length > 0) {
				int i;
				lines2hide = 1;
				if (isBinSeperator()) {
					BinaryCsvParser bParser = new BinaryCsvParser(seperator);
					columnNames = new String[bParser.countTokens(lines[0])] ;
					for (i = 0; i < columnNames.length; i++) {
						columnNames[i] = bParser.getValue(lines[0], i+1, fontname);
					}
				} else {
					StringTokenizer t = new StringTokenizer(getLine(0), seperator);
					columnNames = new String[t.countTokens()] ;
					for (i = 0; i < columnNames.length; i++) {
						columnNames[i] = t.nextToken();
					}
				}
			}
		}
		
		private boolean isBinSeperator() {
			return seperator != null && seperator.toLowerCase().startsWith("x'");
		}
}
