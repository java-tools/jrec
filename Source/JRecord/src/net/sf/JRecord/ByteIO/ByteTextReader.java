package net.sf.JRecord.ByteIO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.JRecord.Common.Constants;

/**
 * Reads a standard Text file (like standard readLine of Class BufferedReader) except it will return an
 * Array of Bytes (instead of a String). This allows binary data to be in a line (i.e. using X'FF' as a field 
 * seperator).
 * 
 * @author  Bruce Martin
 * @version 0.68
 */
public class ByteTextReader extends AbstractByteReader {

	
	private static int maxLineSize = 65000;
	private static final byte[] NO_EOL = {};
	private byte[] eol = null;
	private BufferedInputStream in = null;
	private boolean eof;
	
	@Override
	public void open(InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
		in = new BufferedInputStream(inputStream);
		eof = false;
	}

	@Override
	public byte[] read() throws IOException {
		if (in == null) {
			throw new IOException("File has not been opened");
		}
		if (eof) {
			return null;
		}
		int  l;
		byte[] ret = null;
		byte[] b = new byte[1];
		byte last = -1;
		int size = 1;
		
		in.mark(maxLineSize);
		l = in.read(b);
		if (eol == null || eol.length == 0) {
			while (l > 0 && b[0] != Constants.BYTE_CR && b[0] != Constants.BYTE_LF) {
				last = b[0];
				size += 1;
				l = in.read(b);
			}
			
			if (l == 0) {
				eol = NO_EOL;
			} else if (b[0] ==  Constants.BYTE_CR) {
				eol = Constants.CR_BYTES;
			} else {
				l = in.read(b);
				if (l > 0 && b[0] ==  Constants.BYTE_CR) {
					eol = Constants.LFCR_BYTES;
					size +=1;
				} else {
					eol = Constants.LF_BYTES;
				}
			}
		} else {
			int idx = eol.length - 1;
			while ((l >0) && ((b[0] != eol[idx]) || (eol.length != 1 && last != eol[0]))) {
				last = b[0];
				size += 1;
				l = in.read(b);
			}
		}
		
		in.reset();
		eof = l <= 0;
		
		if (size == 1) {
			if (eof) {
				return null;
			}
		} else if (eof) {
			ret = new byte[size];
			in.read(ret);
		} else {
			byte[] tmp = new byte[eol.length] ;
			ret = new byte[size - eol.length];
			in.read(ret);
			in.read(tmp);
			//System.out.println(new String(ret));
		}
		return ret;
	}

	@Override
	public void close() throws IOException {
		in.close();
		
		in=null;
	}

	/**
	 * @param eol the eol to set
	 */
	public final ByteTextReader setEol(byte[] eol) {
		this.eol = eol;
		return this;
	}

}
