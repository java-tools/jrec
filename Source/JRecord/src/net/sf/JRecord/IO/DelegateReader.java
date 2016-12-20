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

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LineProvider;


public abstract class DelegateReader<Layout extends AbstractLayoutDetails>
extends AbstractLineReader<Layout> {

	private AbstractLineReader<Layout> reader;


	public DelegateReader() {
		super();
	}

	public DelegateReader(LineProvider provider) {
		super(provider);
	}

	/**
	 * @return
	 * @throws IOException
	 * @see net.sf.JRecord.IO.AbstractLineReader#read()
	 */
	public AbstractLine read() throws IOException {
		return reader.read();
	}


	/**
	 * @throws IOException
	 * @see net.sf.JRecord.IO.AbstractLineReader#close()
	 */
	public void close() throws IOException {
		reader.close();
	}


	/**
	 * @return
	 * @see net.sf.JRecord.IO.AbstractLineReader#canWrite()
	 */
	public boolean canWrite() {
		return reader.canWrite();
	}

	/**
	 * @param reader the reader to set
	 */
	public void setReader(AbstractLineReader<Layout> reader) {
		this.reader = reader;
		super.setLayout(reader.getLayout());
	}



}
