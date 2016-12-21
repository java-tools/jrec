/*
 * @Author Bruce Martin
 * Created on 19/03/2007
 *
 * Purpose:
 */
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
import java.io.InputStream;

import net.sf.JRecord.ByteIO.AbstractByteReader;
import net.sf.JRecord.ByteIO.IByteReader;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LineProvider;

/**
 * Creates a LineReader from a Byte-Reader (ByteIO package).
 * A Byte-Reader reads Lines from a File as a series of Bytes.
 * There a Byte Readers for <ol compact>
*   <li>Fixed Line Length files
 *   <li>Length Based lines
 * </ol>
 *
 * @author Bruce Martin
 *
 */
public class LineReaderWrapper
extends AbstractLineReader {

    private IByteReader reader;

    /**
     *  Create a LineReader from a Byte reader
     */
    public LineReaderWrapper(IByteReader byteReader) {
        super();

        reader = byteReader;
    }

    /**
     * @param provider
     */
    public LineReaderWrapper(LineProvider provider, IByteReader byteReader) {
        super(provider);

        reader = byteReader;
    }

    /**
     * @see net.sf.JRecord.IO.StandardLineReader#open(java.io.InputStream, net.sf.JRecord.Details.LayoutDetail)
     */ @Override
    public void open(InputStream inputStream, AbstractLayoutDetails pLayout)
            throws IOException, RecordException {

        reader.setLineLength(pLayout.getMaximumRecordLength());
        reader.open(inputStream);
        super.setLayout(pLayout);
    }


    /**
     * @see net.sf.JRecord.IO.StandardLineReader#read()
     */ @Override
    public AbstractLine read() throws IOException {
        byte bytes[] = reader.read();

        if (bytes == null) {
            return null;
        }
        return getLine(bytes);
    }

    protected byte[] rawRead() throws IOException {
    	return reader.read();
    }

    /**
     * @see net.sf.JRecord.IO.StandardLineReader#close()
     */ @Override
    public void close() throws IOException {
        reader.close();
    }

    public IByteReader getByteReader() {
 		return reader;
 	}

	/**
	 * @param reader the reader to set
	 */
	public final void setReader(AbstractByteReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean canWrite() {
		return reader.canWrite();
	}
}
