/*
 * @Author Bruce Martin
 * Created on 13/04/2007
 *
 * Purpose:
 */
package net.sf.JRecord.CsvParser;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Types.Type;

/**
 * Basic CSV line parser. Basically
 *
 *   - If the Field start with {Quote}; the fields is ended by {Quote}{Field-Seperator}
 *   - Otherwise the field ends with a {Field-Seperator}
 *
 * @author Bruce Martin
 *
 */
public final class BasicCsvLineParserExtended extends BasicCsvLineParser implements ICsvLineParser {

    private static BasicCsvLineParserExtended instance = new BasicCsvLineParserExtended(false);



    public BasicCsvLineParserExtended(boolean quoteInColumnNames) {
    	super(quoteInColumnNames);
    }



	public BasicCsvLineParserExtended(boolean quoteInColumnNames, int delimiterOrganisation) {
		super(quoteInColumnNames, delimiterOrganisation);
	}


	public BasicCsvLineParserExtended(boolean quoteInColumnNames, int delimiterOrganisation,
			boolean allowReturnInFields, boolean textFieldsInQuotes) {
		super(quoteInColumnNames, delimiterOrganisation, allowReturnInFields, textFieldsInQuotes);
	}

	
	/**
	 * check if it is end of the fields
	 */
	@Override
	protected boolean  endOfField(int startAt, String s, String quote) {
		int ql = quote.length();
		int pos = s.length();
		int i = 0;
	
//		String t = s.substring(pos -  ql, pos);
//		System.out.print(s.substring(pos -  ql, pos) + " " + s.substring(pos -  ql, pos).equals(quote));
		while ((pos > ql || (startAt > 0 && pos == ql))
			&& s.substring(pos -  ql, pos).equals(quote) ) {
			pos -= ql;
			i += 1;
		}
		//System.out.println("--)) " + s.length() + " > " + (ql * i) + ", i=" + i + " " + (i % 2) + " " + (s.length() > ql));
		return startAt + s.length() > ql * i && i % 2 == 1;
	}


	@Override
    protected void update4quote(String[] fields, int fieldNumber, String quote ) {
        if (isQuote(quote)
        && fields[fieldNumber].startsWith(quote)
        && fields[fieldNumber].endsWith(quote)) {
        	String v = "";

        	if (fields[fieldNumber].length() >= quote.length() * 2) {
	            int quoteLength = quote.length();
				v = fields[fieldNumber].substring(
						quoteLength, fields[fieldNumber].length() - quoteLength
				);
				v = Conversion.replace(v, quote + quote, quote).toString();
	        }
        	fields[fieldNumber] = v;
        }
    }

	@Override
    protected String formatField(String s, int fieldType, ICsvDefinition lineDef) {
    	String quote = lineDef.getQuote();
    	if (s == null) {
    		s = "";
    	} else if  (quote != null && quote.length() > 0
		        && ( (textFieldsInQuotes & (fieldType != Type.NT_NUMBER))
			        ||	  s.indexOf(lineDef.getDelimiter()) >= 0
		        	|| s.indexOf(quote) >= 0
		        	|| s.startsWith(quote)
		        	|| s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0)) {
            s = quote + Conversion.replace(s, quote, quote + quote).append(quote).toString();
        }
        return s;
    }
	
	
    /**
     * Return a basic CSV line parser
     * @return Returns the instance.
     */
    public static BasicCsvLineParserExtended getInstance() {
        return instance;
    }
}
