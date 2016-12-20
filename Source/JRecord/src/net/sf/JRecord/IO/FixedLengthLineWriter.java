/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: RecordEditor's version of JRecord 
 *    
 *    Sub-Project purpose: Low-level IO and record translation  
 *                        code + Cobol Copybook Translation
 *    
 *                 Author: Bruce Martin
 *    
 *                License: GPL 2.1 or later
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 * ------------------------------------------------------------------------ */
      
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
