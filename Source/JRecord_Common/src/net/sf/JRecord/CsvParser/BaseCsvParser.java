package net.sf.JRecord.CsvParser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class contains comon code for use in other CSV parser's
 * 
 * 
 * @author Bruce Martin
 *
 */
public abstract class BaseCsvParser  {


    private boolean quoteInColNames;
    
    public BaseCsvParser(boolean quoteInColumnNames) {
    	quoteInColNames = quoteInColumnNames;
    }
    
    /**
     * Wether Quote is to be used in column names
     * @return
     */
	public boolean isQuoteInColumnNames() {
		return quoteInColNames;
	}

	/**
	 * Convert a line of column names into a list of column names
	 * 
	 * @param line line of column names
	 * @param delimiter field delimiter
	 * @param quote quote
	 * @return list of column names
	 */
	public List<String> getColumnNames(String line, String delimiter, String quote) {
		ArrayList<String> ret = new ArrayList<String>();
		
        StringTokenizer tok = new StringTokenizer(
                line, delimiter, false);
        String s;

        if (quoteInColNames && quote != null && ! quote.equals("")) {
	        while (tok.hasMoreElements()) {
	            s = tok.nextToken();
	            if (s.startsWith(quote)) {
	            	s = s.substring(1);
	            }
	            if (s.endsWith(quote)) {
	            	s = s.substring(0, s.length() - 1);
	            }
	            ret.add(s);
	        }
        } else {
	        while (tok.hasMoreElements()) {
	            ret.add(tok.nextToken());
	        }
        }

        return ret;
	}
	
	/**
	 * Convert a list of column names into a line
	 * 
	 * @param names list of column names
	 * @param delim field delimiter
	 * @param quote quote to use
	 * @return
	 */
	public String getColumnNameLine(List<String> names, String delim, String quote) {
		StringBuilder buf = new StringBuilder();
		String currDelim = "";
		
		for (int i = 0; i < names.size(); i++) {
	           buf.append(currDelim)
               .append(quote)
               .append(names.get(i))
               .append(quote);
            currDelim = delim;

		}
		
		return buf.toString();
	}
}
