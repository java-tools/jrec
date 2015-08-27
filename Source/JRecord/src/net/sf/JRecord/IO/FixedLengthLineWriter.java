package net.sf.JRecord.IO;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.JRecord.ByteIO.AbstractByteWriter;
import net.sf.JRecord.ByteIO.FixedLengthByteWriter;
import net.sf.JRecord.Details.AbstractLine;

public class FixedLengthLineWriter extends AbstractLineWriter {

	private AbstractByteWriter writer = null;
	private OutputStream outputStream = null;
	

	
	/**
     * @see net.sf.JRecord.IO.AbstractLineWriter#open(java.io.OutputStream)
     */
    public void open(OutputStream outputStream) {
    	this.outputStream = outputStream;
    }


	/* (non-Javadoc)
	 * @see net.sf.JRecord.IO.LineWriterWrapper#write(net.sf.JRecord.Details.AbstractLine)
	 */
	@Override
	public void write(AbstractLine line) throws IOException {
		if (writer == null) {
			if (outputStream == null) {
				throw new IOException("Writer has not been openned");
			}
			writer = new FixedLengthByteWriter(line.getLayout().getMaximumRecordLength());
			writer.open(outputStream);
		}
		writer.write(line.getData());
	}

   /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#close()
     */
    public void close() throws IOException {
    	if (writer != null) {
    		writer.close();
    		
    		writer = null;
    		outputStream = null;
    	}
    }
}
