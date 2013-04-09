/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose: writes "Line's" to a binary file
 */
package net.sf.JRecord.IO;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLine;


/**
 *  writes "Line's" to a binary file
 *
 * @author Bruce Martin
 *
 */
public class BinaryLineWriter extends AbstractLineWriter {
    private OutputStream outStream = null;
    private boolean addLength = false,  fixedLength;

    private byte[] rdw = new byte[4];

    private int recordLength = Integer.MIN_VALUE;
    private byte fillByte;


    public static BinaryLineWriter newFixedLengthWriter() {
    	return new BinaryLineWriter();
    }

    public static BinaryLineWriter newVBWriter() {
    	return new BinaryLineWriter(true);
    }


    public static BinaryLineWriter newBinaryWriter() {
    	return new BinaryLineWriter(true);
    }

    /**
     * create binary line writer
     */
    private BinaryLineWriter() {
        super();
        fixedLength = true;
    }


    /**
     * create binary line writer
     *
     * @param includeRDW write RDW (record Descriptor Word) at the start
     * of each record. The RDW consists of
     * <ul Commpact>
     *   <li>2 byte length (big endian format)
     *   <li>2 bytes (hex zeros)
     * </ul>
     */
    private BinaryLineWriter(final boolean includeRDW) {
        super();
        addLength = includeRDW;
        fixedLength = false;
        rdw[2]    = 0;
        rdw[3]    = 0;
    }


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#open(java.io.OutputStream)
     */
    public void open(OutputStream outputStream) throws IOException {

        outStream = outputStream;
    }


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#write(net.sf.JRecord.Details.AbstractLine)
     */
    public void write(AbstractLine line) throws IOException {

        if (outStream == null) {
            throw new IOException(AbstractLineWriter.NOT_OPEN_MESSAGE);
        } else if (line == null) {
        	return;
        }

        byte[] rec = line.getData();

        if (addLength) {
            byte[] bytes = (BigInteger.valueOf(rec.length + 4)).toByteArray();

            rdw[1] = bytes[bytes.length - 1];
            rdw[0] = 0;
            if (bytes.length > 1) {
                rdw[0] = bytes[bytes.length - 2];
            }
            outStream.write(rdw);
        } else if (fixedLength) {
        	if (recordLength < 0) {
        		recordLength = line.getLayout().getMaximumRecordLength();

        		fillByte = 0;
        		if (! line.getLayout().isBinary()) {
        			fillByte = Conversion.getBytes(" ", line.getLayout().getFontName())[0];
        		}
        	}
        	if (recordLength != rec.length) {

	        	if (rec.length > recordLength) {
	        		outStream.write(rec, 0, recordLength);
	        	} else {
	        		outStream.write(rec);

	        		for (int i = rec.length; i < recordLength; i++) {
	        			outStream.write(fillByte);
	        		}
	        	}
        	return;
        	}
        }

        outStream.write(rec);
    }


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#close()
     */
    public void close() throws IOException {

        outStream.close();
        outStream = null;
    }
}
