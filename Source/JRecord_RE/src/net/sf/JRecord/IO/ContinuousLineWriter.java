/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose: writes "Line's" to a binary file
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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;


/**
 *  writes "Line's" to a binary file
 *
 * @author Bruce Martin
 *
 */
public class ContinuousLineWriter extends AbstractLineWriter {
    private OutputStream outStream = null;

    private boolean toInit = true;
    private byte fillByte = 0;
    



    /**
     * create binary line writer
     */
    public ContinuousLineWriter() {
        super();
    }


  

    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#open(java.io.OutputStream)
     */
    public void open(OutputStream outputStream) throws IOException {

        outStream = outputStream;
        if (! (outStream instanceof BufferedOutputStream)) {
        	outStream = new BufferedOutputStream(outStream);
        }
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
        int pref = line.getPreferredLayoutIdx();
        AbstractLayoutDetails l = line.getLayout();
        int prefLength;
        
        if (pref < 0 || ((prefLength = l.getRecord(pref).getLength()) == rec.length) ) {
        	outStream.write(rec);    	
        } else if (prefLength < rec.length) {
        	outStream.write(rec, 0, prefLength);
        } else {
        	if (toInit) {
        		byte[] bytes;
        		toInit = false;

        		if ((! line.getLayout().isBinary())
        		&& ((bytes = Conversion.getBytes(" ", line.getLayout().getFontName()))!= null)
        		&& (bytes.length == 1) ) {
					fillByte = bytes[0];
        		}
        	}
    		outStream.write(rec);

    		for (int i = rec.length; i < prefLength; i++) {
    			outStream.write(fillByte);
    		}
        }
    }


    /**
     * @see net.sf.JRecord.IO.AbstractLineWriter#close()
     */
    public void close() throws IOException {

        outStream.close();
        outStream = null;
    }
}
