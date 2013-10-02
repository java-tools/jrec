/*
 * Purpose: Record orientated reading of Text files
 *
 * @Author Bruce Martin
 * Created on 27/08/2005
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Started work on seperating Record section out, so removing
 *     all reference to the Common module and used a new Constants
 *     module
 */
package net.sf.JRecord.IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.CsvParser.ICsvLineParser;
import net.sf.JRecord.CsvParser.CsvDefinition;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.Type;
import net.sf.JRecord.charIO.ICharReader;
import net.sf.JRecord.charIO.StandardCharReader;


/**
 * This class reads "Line's" from a text file
 *
 * @author Bruce Martin
 *
 */
public class TextLineReader extends StandardLineReader {

    private InputStream inStream;
//	private InputStreamReader stdReader;
//	protected BufferedReader reader = null;
    ICharReader reader = null;
	private boolean namesInFile = false;

    private String defaultDelim  = ",";
    private String defaultQuote  = "'";



	/**
	 * This class provides record oriented reading of a Text files
	 */
	public TextLineReader() {
	    super();
	}


	/**
	 * This class provides record oriented reading of a Text files
	 * using a user supplied Line Provider.
	 * <p><b>Note:</b> A line provider creates lines. This allows
	 * you to use your own Line's
	 *
	 * @param provider line provider
	 */
	public TextLineReader(@SuppressWarnings("rawtypes") final LineProvider provider) {
	    super(provider);
	}

	/**
	 *  This class provides record oriented reading of a Text files
	 * using a user supplied Line Provider.
	 * <p><b>Note:</> A line provider creates lines. This allows
	 * you to use your own Line's
	 *
	 * @param provider line provider used to create lines
	 * @param namesOn1stLine wether names are stored on the first line of
	 *        a file
	 */
	public TextLineReader(@SuppressWarnings("rawtypes") final LineProvider provider,
	        			  final boolean namesOn1stLine) {
	    this(provider, namesOn1stLine, null);
	}

	public TextLineReader(@SuppressWarnings("rawtypes") final LineProvider provider,
			  final boolean namesOn1stLine,
			  ICharReader r) {
		super(provider);

		namesInFile = namesOn1stLine;
		reader = r;
	}


    /**
     * @see net.sf.JRecord.IO.StandardLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
     */
    public void open(InputStream inputStream, LayoutDetail layout)
    throws IOException, RecordException {
    	String font = "";
        inStream = inputStream;
        setLayout(layout);

		if (layout != null) {
			font = layout.getFontName();
		}
		if (reader == null) {
			reader = new StandardCharReader();
		}
		reader.open(inputStream, font);

		if (namesInFile) {
			if (layout != null && layout.useThisLayout()) {
				read();
			} else {
				createLayout(reader, inputStream, font);
			}
		}
    }


    /**
     * create a layout
     *
     * @param pReader file read
     *
     * @throws IOException sny IO error that occurs
     */
    protected void createLayout(ICharReader pReader, InputStream inputStream, String font) throws IOException, RecordException {
        LayoutDetail layout;

        RecordDetail rec = null;
	    int fieldType = Type.ftChar;
        int decimal   = 0;
        int format    = 0;
        int parser    = 0;
        int structure = Constants.IO_NAME_1ST_LINE;
        String param  = "";
        String delim  = defaultDelim;
        String quote  = defaultQuote;

        byte[] recordSep = Constants.SYSTEM_EOL_BYTES;
        boolean embeddedCr = false;

	    try {
	    	int ts = getLayout().getFileStructure();
	    	if (ts != Constants.IO_GENERIC_CSV) {
	    		structure = ts;
	    	}

	    	delim     = getLayout().getDelimiter();
	        rec = getLayout().getRecord(0);
	        quote     = rec.getQuote();
	        parser    = rec.getRecordStyle();

	        fieldType = Type.ftChar;
	        decimal   = 0;
	        format    = 0;
	        param     = "";

	        if (rec.getFieldCount() == 1) {
		        fieldType = rec.getField(0).getType();
		        decimal   = rec.getField(0).getDecimal();
		        format    = rec.getField(0).getFormat();
		        param     = rec.getField(0).getParamater();
	        }
	        recordSep = getLayout().getRecordSep();
	        font      = getLayout().getFontName();

	        if (rec instanceof RecordDetail) {
	        	embeddedCr = ((RecordDetail) rec).isEmbeddedNewLine();
	        }
	    } catch (Exception e) {
        }

	    //System.out.println(" Quote  ->" + quote + " " + (getLayout() == null));

	    layout = createLayout(pReader.read(), rec,
	    		recordSep, structure, font,  delim,
                quote, parser, fieldType, decimal, format, param, embeddedCr);
	    //System.out.println(" Quote  ->");

	    if (layout != null) {
	        setLayout(layout);
	    }
    }

    /**
     * create a Layout from the first line in the file
     * @param line line being built
     * @param recordSep record seperator
     * @param structure File structure
     * @param fontName font name
     * @param delimiter field delimiter
     * @param quote Quote Character to use
     * @param style Identifier of the CSV parser to use
     * @param defaultFieldType field type
     * @param defaultDecimal number of decimal places
     * @param defaultFormat format to use
     * @param defaultParam param to add to each field
     * @throws IOException any error
     */
    public static LayoutDetail createLayout(String line, AbstractRecordDetail rec,
    		byte[] recordSep,
    		int structure,
            String fontName, String delimiter, String quote, int style,
            int defaultFieldType, int defaultDecimal, int defaultFormat, String defaultParam,
            boolean embeddedCr) throws IOException {

    	int fldType, idx;
        //int i = 0;
        LayoutDetail ret = null;
        String s;
        int decimal; int format; String param;
        IFieldDetail fldDetail;

        if (line != null) {
        	ICsvLineParser parser = ParserManager.getInstance().get(style);
        	List<String> colNames = parser.getColumnNames(line, new CsvDefinition(delimiter, quote));


            int len = colNames.size();
            RecordDetail.FieldDetails[] flds = new RecordDetail.FieldDetails[len];
            RecordDetail[] recs = new RecordDetail[1];

            if (defaultFieldType < 0) {
            	defaultFieldType = Type.ftChar;
            }

            for (int i = 0; i < colNames.size(); i++) {
                s = colNames.get(i);
                fldType = defaultFieldType;
                decimal = defaultDecimal;
                format = defaultFormat;
                param = defaultParam;
                if (rec != null
                && (idx = rec.getFieldIndex(s)) >= 0) {
                	fldDetail = rec.getField(idx);
                	fldType = fldDetail.getType();
                	decimal = fldDetail.getDecimal();
                    format = fldDetail.getFormat();
                    param = fldDetail.getParamater();
                }
                flds[i] = new RecordDetail.FieldDetails(s, s, fldType, decimal,
                        fontName, format, param);
                flds[i].setPosOnly(i + 1);
            }

            recs[0] = new RecordDetail("", "", "", Constants.rtDelimited,
                    delimiter, quote, fontName, flds, style, 0, embeddedCr);

            try {
                ret =
                    new LayoutDetail("", recs, "",
                        Constants.rtDelimited,
                        recordSep, "", fontName, null,
                        structure
                    );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * @see net.sf.JRecord.IO.StandardLineReader#read()
     */
    public AbstractLine read()  throws IOException {
        AbstractLine ret = null;

        if (reader == null) {
            throw new IOException(StandardLineReader.NOT_OPEN_MESSAGE);
        }
        String s = reader.read();

        if (s != null) {
            ret = getLine(s);
        }

        return ret;
    }


    /**
     * @see net.sf.JRecord.IO.StandardLineReader#close()
     */
    public void close() throws IOException {

        if (reader != null) {
            reader.close();
    		inStream.close();
        }

    	reader    = null;
    	inStream  = null;
    }


    /**
     * set default delimiter
     * @param theDefaultDelim new default field delimeter
     */
	public void setDefaultDelim(String theDefaultDelim) {
		this.defaultDelim = theDefaultDelim;
	}


	public void setDefaultQuote(String theDefaultQuote) {
		this.defaultQuote = theDefaultQuote;
	}


	/**
	 * @return the defaultDelim
	 */
	public String getDefaultDelim() {
		return defaultDelim;
	}


	/**
	 * @return the defaultQuote
	 */
	public String getDefaultQuote() {
		return defaultQuote;
	}

}
