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

import net.sf.JRecord.ByteIO.MicroFocusByteWriter;
import net.sf.JRecord.ByteIO.MicroFocusFileHeader;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;


/**
 * Purpose: Write a Microfocus Cobol Sequential file with a Header Record
 * 
 * @author Bruce Martin
 *
 */
public class MicroFocusLineWriter extends AbstractLineWriter {
	
	private MicroFocusByteWriter writer = new MicroFocusByteWriter();
	private boolean toInit;
	
	@Override
	public void open(String fileName) throws IOException {
		writer.open(fileName);
		toInit = true;
	}

	@Override
	public void open(OutputStream outputStream) throws IOException {
		writer.open(outputStream);
		toInit = true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setLayout(AbstractLayoutDetails layout) throws IOException {

	
		if (toInit) {
			int len;
			int maxLen = 0;
			int minLen = Integer.MAX_VALUE;
			
			for (int i = 0; i < layout.getRecordCount(); i++) {
				len = layout.getRecord(i).getLength();
				maxLen = Math.max(maxLen, len);
				minLen = Math.min(minLen, len);
			}
			
	
			writer.writeHeader(
					new MicroFocusFileHeader(MicroFocusFileHeader.FORMAT_SEQUENTIAL, minLen, maxLen)
			);
			
			
			super.setLayout(layout);
			toInit = false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(AbstractLine line) throws IOException {
		
		setLayout(line.getLayout());
		writer.write(line.getData());
	}

	
		
	@Override
	public void close() throws IOException {
		writer.close();
	}
}
