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

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.charIO.ICharReader;

public abstract class BasicTextLineReader extends StandardLineReader {

	private InputStream inStream = null;
	private ICharReader reader;

	public BasicTextLineReader() {
		super();
	}

	public BasicTextLineReader(LineProvider provider) {
		super(provider);
	}

	  protected void open(ICharReader r, InputStream inputStream, LayoutDetail layout, String font)
	  throws IOException, RecordException {

		  reader = r;
		  inStream = inputStream;
		  setLayout(layout);

		  reader.open(inputStream, font);
	  }

	/**
	 * @see net.sf.JRecord.IO.StandardLineReader#read()
	 */
	public AbstractLine read() throws IOException {
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
	
	    reader.close();
	    if (inStream != null) {
			inStream.close();
	    }
	
		inStream  = null;
	}

	public final ICharReader getReader() {
		return reader;
	}

}