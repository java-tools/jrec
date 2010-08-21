/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose: This class writes "Line's" to a text file
 *
 * # Version 0.60 Bruce Martin 2007/02/16
 *   - Started work on seperating Record section out, so removing
 *     all reference to the Common module and used a new Constants
 *     module
 *   - removed reference to the depreciated getFields method
 */
package net.sf.JRecord.IO;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.CsvParser.AbstractParser;
import net.sf.JRecord.CsvParser.BasicParser;
import net.sf.JRecord.CsvParser.ParserManager;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;

/**
 * This class writes "Line's" to a text file
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public class TextLineWriter extends AbstractLineWriter {

	private static final int BUFFER_SIZE = 16384;

    private OutputStream outStream;
	private OutputStreamWriter stdWriter = null;
	private BufferedWriter writer = null;
	private boolean namesInFile = false;
	private boolean writeNames;


	/**
	 * create a Text line writer
	 *
	 * @param namesOn1stLine wether the field names should
	 *        be written on the first line
	 */
	public TextLineWriter(final boolean namesOn1stLine) {
	    super();
	    namesInFile = namesOn1stLine;
	}


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#open(java.io.OutputStream)
     */
    public void open(OutputStream outputStream) throws IOException {

        outStream = outputStream;

        writeNames = namesInFile;
    }


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#write(net.sf.JRecord.Details.AbstractLine)
     */
    public void write(AbstractLine line) throws IOException  {

    	AbstractLayoutDetails layout =  line.getLayout();
	    String sep = layout.getEolString();
	    
	    if (stdWriter == null) {
	    	if (layout == null || "".equals(layout.getFontName())) {
	    		stdWriter = new OutputStreamWriter(outStream);
			} else {
				stdWriter = new OutputStreamWriter(outStream, layout.getFontName());
			}
	        writer = new BufferedWriter(stdWriter, BUFFER_SIZE);
	    	
	        if (writeNames) {
	            writeLayout(writer, line.getLayout());
	        }
	    }

        if (writer == null) {
            throw new IOException(AbstractLineWriter.NOT_OPEN_MESSAGE);
        }


		writer.write(line.getField(0, Constants.FULL_LINE).toString());
		writer.write(sep);
    }

    /**
     * Set the Record Layout
     * @param layout record layout to set
     */
    public void setLayout(AbstractLayoutDetails layout) {
        try {
            if (writeNames && writer != null) {
                writeLayout(writer, layout);
            }
        } catch (Exception e) {
        }
    }

    /**
     * writes the field names to the file
     *
     * @param pWriter output writer
     * @param layout record layout to write
     *
     * @throws IOException any error that occurs
     */
    public void writeLayout(BufferedWriter pWriter,
            				AbstractLayoutDetails layout)
    throws IOException {

        int i;
       // FieldDetail[] fields = layout.getRecord(0).getFields();
        AbstractRecordDetail rec = layout.getRecord(0);
        AbstractParser parser = ParserManager.getInstance().get(layout.getRecord(0).getRecordStyle());
        String delim = layout.getRecord(0).getDelimiter();

        String quote = "";

        ArrayList<String> colNames = new ArrayList<String>();
        
        if (parser == null) {
        	parser = BasicParser.getInstance();
        } else if (parser.isQuoteInColumnNames()) {
        	quote = layout.getRecord(0).getQuote();
        }  

        for (i = 0; i < rec.getFieldCount(); i++) {
        	colNames.add(rec.getField(i).getName());
        }

        pWriter.write(parser.getColumnNameLine(colNames, delim, quote)
        		+ layout.getEolString());

        writeNames = false;
    }


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#close()
     */
    public void close() throws IOException {

        writer.close();
        stdWriter.close();
        outStream.close();

        writer    = null;
        stdWriter = null;
        outStream = null;
     }
}
