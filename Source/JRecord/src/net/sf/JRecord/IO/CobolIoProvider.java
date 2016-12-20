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

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.LineProvider;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.External.Def.AbstractConversion;
import net.sf.JRecord.Numeric.ICopybookDialects;


/**
 * This Class Creates a line-reader or line-writer for a Cobol file (Cobol Copybook).
 *
 * <pre>
 * <b>Usage:</b>
 *
 *        CobolIoProvider ioProvider = CobolIoProvider.getInstance();
 *
 *        try {
 *             AbstractLineReader reader  = ioProvider.getLineReader(
 *                 Constants.IO_TEXT_LINE, Convert.FMT_INTEL,
 *                 CopybookLoader.SPLIT_NONE, copybookName, vendorFile
 *             );
 * </pre>
 *
 * @author Bruce Martin
 *
 */
public class CobolIoProvider {

    private static CobolIoProvider instance = new CobolIoProvider();
    private CopybookLoader copybookInt = new CobolCopybookLoader();


    /**
     * Creates a line reader for a Cobol file
     *
     * @param fileStructure Structure of the input file
     * @param numericType Numeric Format data (options include mainframe, Fujitu, PC compiler etc)
     * @param splitOption Option to split the copybook up <ul>
     *  <li>No Split
     *  <li>Split on  redefine
     *  <li>Split on 01 level
     * </ul>
     * @param copybookName Copybook (or Layout) name
     * @param filename input file name
     * @return requested Line Reader
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public AbstractLineReader getLineReader(int fileStructure,
			   int numericType, int splitOption,
			   String copybookName, String filename)
    throws Exception {
        return getLineReader(fileStructure,
 			   numericType, splitOption,
			   copybookName, filename,
			   null);
    }

    /**
     * Creates a line reader for a Cobol file
     *
     * @param fileStructure Structure of the input file
     * @param numericType Numeric Format data (is mainframe, Fujitu PC compiler etc)
     * @param splitOption Option to split the copybook up <ul>
     *  <li>No Split
     *  <li>Split on  redefine
     *  <li>Split on 01 level
     * </ul>
     * @param copybookName Copybook (or Layout) name
     * @param filename input file name
     * @param provider line provider (to build your own lines)
     * @return requested Line Reader
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public AbstractLineReader getLineReader(int fileStructure,
 			   int numericType, int splitOption,
 			   String copybookName, String filename,
 			   LineProvider provider)
     throws Exception {
    	AbstractLineReader ret;
        String font = "";
        if (numericType == ICopybookDialects.FMT_MAINFRAME) {
            font = "cp037";
        }
       	ExternalRecord schemaBldr = copybookInt.loadCopyBook(
		            copybookName,
		            splitOption, AbstractConversion.USE_DEFAULT_IDX, font,
		            numericType, 0, null
		    );
       	schemaBldr.setFileStructure(fileStructure);
		LayoutDetail copyBook = ToLayoutDetail.getInstance().getLayout(schemaBldr);

       	if (provider == null) {
       		provider = LineIOProvider.getInstance().getLineProvider(copyBook);
       	}
       	ret = LineIOProvider.getInstance()
       				.getLineReader(copyBook, provider);
       	ret.open(filename, copyBook);

       	return ret;

    }


    /**
     * Create a line writer for a Cobol File
     *
     * @param fileStructure structure of the output file
     * @return Line writer for the file
     */
    public AbstractLineWriter getLineWriter(int fileStructure, String outputFileName)
    throws IOException {
        AbstractLineWriter ret = LineIOProvider.getInstance()
        			.getLineWriter(fileStructure);
        ret.open(outputFileName);
        return ret;
    }


    /**
     * Get a CobolIoProvider
     *
     * @return Returns the instance.
     */
    public static CobolIoProvider getInstance() {
        return instance;
    }
}
