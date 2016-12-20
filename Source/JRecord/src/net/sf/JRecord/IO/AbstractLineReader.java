/*
 * @Author Bruce Martin
 * Created on 26/08/2005
 *
 * Purpose:  reading Record Orientated files
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.DefaultLineProvider;
import net.sf.JRecord.Details.ISetLineProvider;
import net.sf.JRecord.Details.LineProvider;



/**
 * This abstract class is the base class for all <b>Line~Reader</b>
 * classes. A LineReader reads a file as a series of AbstractLines.
 *
 * <pre>
 * <b>Usage:</b>
 *
 *         CopybookLoader loader = <font color="brown"><b>new</b></font> RecordEditorXmlLoader();
 *         LayoutDetail layout = loader.loadCopyBook(copybookName, 0, 0, "", 0, 0, <font color="brown"><b>null</b></font>).asLayoutDetail();
 *
 *         <b>AbstractLineReader</b> reader = LineIOProvider.getInstance().getLineReader(layout.getFileStructure());
 * </pre>
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractLineReader<Layout extends AbstractLayoutDetails> {

    public static final String NOT_OPEN_MESSAGE = "File has not been opened";

	private LineProvider lineProvider;
	private Layout layout = null;


	/**
	 * create Binary Line Reader
	 */
	public AbstractLineReader() {
	    this(null);
	}


	/**
	 * create reader with a user sup[plied line provider. This allows
	 * you to use your own Classes that extend "Line"
	 *
	 * @param provider line provider
	 */
	public AbstractLineReader(final LineProvider provider) {
	    super();
	    lineProvider = provider;

	    if (provider == null) {
	    	lineProvider =  new DefaultLineProvider();
	    }
	}

	/**
	 * Open a file where the layout can be built from the file contents.
	 * Possible files are:
	 * - XML files
	 * - CSV files where the field names are stored on the first line
	 * @param fileName file to be opened.
	 */
	 public void open(String fileName) throws IOException, RecordException {
		 open(fileName, (Layout) null);
	 }
    /**
     * Open file for input
     *
     * @param fileName filename to be opened
     * @param pLayout record layout
     *
     * @throws IOException any IOerror
     */
    public void open(String fileName, Layout pLayout) throws IOException, RecordException {
        open(new FileInputStream(fileName), fileName, pLayout);

        if (layout == null) {
            layout = pLayout;
        }
    }

    /**
     * Several IO readers generate a layout as part of the open file process
     * (i.e. XML, Csv File withe the names on the first line)
     * But if there is no file to open, no layout is generated.
     * This method can be overwritten to provided as an alternative when there is no file.
     *
     * @param pLayout default standard layout.
     */
    public void generateLayout(Layout pLayout) {
    	layout = pLayout;
    }


//    /**
//     * Open a file using an external record Definition
//     * @param inputStream input
//     * @param recordLayout recordlayout to use
//     * @throws IOException any IOError that occurs
//     * @throws RecordException any other error
//     */
//    public void open(InputStream inputStream, ExternalRecord recordLayout)
//    throws IOException, RecordException {
//    	Layout pLayout = (Layout) recordLayout.asLayoutDetail();
//    	open(inputStream, pLayout);
//
//        if (layout == null) {
//            layout = pLayout;
//        }
//    }


    /**
     * Open file for input
     *
     * @param inputStream input stream to be read
     * @param pLayout record layout
     *
     * @throws IOException any IOerror
     */
    public void open(InputStream inputStream, String filename, Layout pLayout)
    throws IOException, RecordException{
    	open(inputStream, pLayout);
    }



    /**
     * Open file for input
     *
     * @param inputStream input stream to be read
     * @param pLayout record layout
     *
     * @throws IOException any IOerror
     */
    public abstract void open(InputStream inputStream, Layout pLayout)
    throws IOException, RecordException;


    /**
     * Read one line from the input file
     *
     * @return line read in
     *
     * @throws IOException io error
     */
    public abstract AbstractLine read() throws IOException;


    /**
     * Closes the file
     *
     * @throws IOException io error
     */
    public abstract void close() throws IOException;



	/**
	 * Read a complete buffers worth of data into buf from a input stream.
	 *
	 * @param in stream to be read.
	 * @param buf buffer to be loaded with data
	 *
	 * @return the number of bytes read
	 * @throws IOException IO Exception
	 */
	protected final int readBuffer(final InputStream in,
	        					   final byte[] buf)
				throws IOException {
	    int num;
	    int total;

	    num = in.read(buf);
	    total = num;

	    while (num >= 0 && total < buf.length) {
	        num = in.read(buf, total, buf.length - total);
	        total += num;
	    }

	    return total;
	}


	/**
	 * Create a Line using supplied details
	 *
	 * @param record record contents
	 *
	 * @return line just created
	 */
	protected final AbstractLine getLine(byte[] record) {
	    AbstractLine ret = lineProvider.getLine(layout, record);

	    if (ret instanceof ISetLineProvider) {
	    	((ISetLineProvider) ret).setLineProvider(lineProvider);
	    }
	    return ret;
	}


	/**
	 * Create a Line using supplied details
	 *
	 * @param record record contents
	 *
	 * @return line just created
	 */
	protected final AbstractLine getLine(String record) {
	    AbstractLine ret = lineProvider.getLine(layout, record);

	    if (ret instanceof ISetLineProvider) {
	    	((ISetLineProvider) ret).setLineProvider(lineProvider);
	    }
	    return ret;
	}


	/**
	 * get the record layout
	 * @return the layout
	 */
	public final Layout getLayout() {
	    return layout;
	}

	/**
	 * set the layout to be used
	 *
	 * @param pLayout layout to be used
	 */
    public final void setLayout(Layout pLayout) {
        this.layout = pLayout;
    }

    public boolean canWrite() {
    	return true;
    }
}
