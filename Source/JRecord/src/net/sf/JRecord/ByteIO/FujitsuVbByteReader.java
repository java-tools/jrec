/*
 * Purpose: Record oriented reading of Variable Record Length Fujitsu files
 *
 * @Author Jean-Francois Gagnon
 * Created on 26/06/2006
 *
 */
package net.sf.JRecord.ByteIO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;



/**
 * This class performs record oriented reading of:
 * </ul compact>
 *   <li>Fujitsu Cobol Variable Record Length Binary files
 * </ul>
 *
 * into an Array of bytes
 *
 * @author Jean-Francois Gagnon
 *
 */
public class FujitsuVbByteReader extends AbstractByteReader {

	private static final int BUFFER_SIZE = 16384;

    private InputStream inStream;
	private BufferedInputStream stream = null;

	private int lineNumber = 0;

	/**
	 * record descriptor word, it consists of
	 * 2 bytes length
	 * 2 bytes (hex zero)
	 */
	private byte[] rdw = new byte[4];
	private byte[] rdwLength = new byte[2];

	/**
	 * This class provides record oriented reading of Variable
	 * Record Length Binary files where the record length is held in
	 * 4 byte Record-Descriptor-Word at the start of the record.
	 */
	public FujitsuVbByteReader() {
	    super();
	}


	/**
     * @see AbstractByteReader#open(java.io.InputStream)
     */
    public void open(InputStream inputStream) {

        inStream = inputStream;

        stream = new BufferedInputStream(inputStream, BUFFER_SIZE);

    }



    /**
     * @see AbstractByteReader#read()
     */
    public byte[] read()  throws IOException {
        byte[] ret = null;

        if (stream == null) {
            throw new IOException(AbstractByteReader.NOT_OPEN_MESSAGE);
        }

        lineNumber += 1;
        if (readBuffer(stream, rdw) > 0) {
            if (rdw[2] != 0 || rdw[3] != 0) {
                throw new IOException(
                          "Invalid Record Descriptor word at line "
                        + lineNumber
                      );
            }

            rdwLength[0] = rdw[1];
            rdwLength[1] = rdw[0];

        	int lineLength = (new BigInteger(rdwLength)).intValue();
            byte[] inBytes = new byte[lineLength];

            if (readBuffer(stream, inBytes) > 0) {
                ret = inBytes;
                // Read RDW at end of record
                readBuffer(stream, rdw);
			}
        }

        return ret;
    }

    /**
     * @see AbstractByteReader#close()
     */
    public void close() throws IOException {

        inStream.close();
        stream.close();
        stream = null;
    }

}

