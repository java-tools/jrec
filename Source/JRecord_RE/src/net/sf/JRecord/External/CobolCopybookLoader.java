/*
 * @Author Bruce Martin
 * Created on 14/04/2005
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
      
package net.sf.JRecord.External;

import java.io.File;
import java.io.InputStream;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Log.TextLog;

import org.w3c.dom.Document;

/**
 * This class holds routines to load a Cobol Copybook
 * into ExternalRecord.
 *
 * @author Bruce Martin
 *
 */
public class CobolCopybookLoader implements CopybookLoader {

    private static final String PROBLEM_LOADING_COPYBOOK = "Problem loading Copybook: {0}    Cause:\n{1}";
	private static boolean available = true;
    private static boolean toCheck = true;

    private Cb2xmlLoader xmlLoader;

    /**
     * Create Cobol Copybook loader
     */
    public CobolCopybookLoader() {
        this(new Cb2xmlLoader());
    }

    /**
     * Create Cobol Copybook loader
     * @param loader4xml class to load a Cb2Xml XML file
     */
    public CobolCopybookLoader(Cb2xmlLoader loader4xml) {
        super();

        xmlLoader = loader4xml;
       // System.out.println("Cobol Copybook loader");
    }

    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param copyBookFile Copy Book file Name
     * @param splitCopybook wether to split a copy book on a redefine / 01
     * @param dbIdx Database Index
     * @param font font name to use
     * @param binFormat binary format to use
     * @param systemId System Identifier
     * @param log log where any messages should be written
     *
     * @return return the record that has been read in
     * @throws RecordException General Error
     */
    public final ExternalRecord loadCopyBook(String copyBookFile, //Document copyBookXml,
            						  		int splitCopybookOption,
            						  		int dbIdx,
                  						   String font,
                						   int binFormat,
                						   int systemId,
                						   AbsSSLogger log)
    				throws RecordException {
    	return loadCopyBook(copyBookFile, splitCopybookOption, dbIdx, font, 5 /* Cb2xmlConstants.USE_PROPERTIES_FILE*/, binFormat, systemId, log);
    }
    
    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param copyBookFile Copy Book file Name
     * @param splitCopybook wether to split a copy book on a redefine / 01
     * @param dbIdx Database Index
     * @param font font name to use
     * @param binFormat binary format to use
     * @param systemId System Identifier
     * @param log log where any messages should be written
     *
     * @return return the record that has been read in
     */
    public final ExternalRecord loadCopyBook(String copyBookFile, //Document copyBookXml,
            						  		int splitCopybook,
            						  		int dbIdx,
                  						  final String font,
                  						  final int copybookFormat,
                						  final int binFormat,
                						  final int systemId,
                						        AbsSSLogger log)
    				{
        ExternalRecord ret = null;
        //System.out.println("load Copybook (Cobol)");
        try {
        	synchronized (PROBLEM_LOADING_COPYBOOK) {	
	            Document xml = net.sf.JRecord.External.Def.Cb2Xml.convertToXMLDOM(new File(copyBookFile), binFormat, false, copybookFormat, log);
	            String copyBook = Conversion.getCopyBookId(copyBookFile);
	
	            if (xml != null) {
		            ret = xmlLoader.loadDOMCopyBook(xml, copyBook,
		                    splitCopybook, dbIdx,
						    font, binFormat, systemId);
		        } else if (log != null) {
		        	TextLog.getLog(log).logMsg(AbsSSLogger.ERROR, "Error parsing Cobol File ???");
	            }
        	}
        } catch (Exception e) {
        	log = TextLog.getLog(log);
            log.logMsg(AbsSSLogger.ERROR, e.getMessage());
            log.logException(AbsSSLogger.ERROR, e);
            e.printStackTrace();
            throw new RecordException(
            				PROBLEM_LOADING_COPYBOOK,
            				new Object[] {copyBookFile, e.getMessage()},
            				e);
        }

        return ret;
    }

    /**
     * Insert a XML Dom Copybook into the Copybook DB
     *
     * @param copyBookName Copy Book file Name
     * @param splitCopybook wether to split a copy book on a redefine / 01
     * @param dbIdx Database Index
     * @param font font name to use
     * @param binaryFormat binary format to use
     * @param systemId System Identifier
     * @param log log where any messages should be written
     *
     * @return return the record that has been read in
     */
    public final ExternalRecord loadCopyBook(InputStream inputStream, //Document copyBookXml,
    		                             String copyBookName,
            						  		int splitCopybook,
            						  		int dbIdx,
                  						  final String font,
                						  final int binaryFormat,
                						  final int systemId,
                						  AbsSSLogger log)
    				{
        ExternalRecord ret = null;
        //System.out.println("load Copybook (Cobol)");
        try {
        	synchronized (PROBLEM_LOADING_COPYBOOK) {		
	            Document xml = net.sf.JRecord.External.Def.Cb2Xml.convertToXMLDOM(inputStream, copyBookName,  binaryFormat, false, CommonBits.getDefaultCobolTextFormat());

//	            Document xml = net.sf.cb2xml.Cb2Xml2.convertToXMLDOM(inputStream, copyBookName, false);
	
	            if (xml != null) {
		            ret = xmlLoader.loadDOMCopyBook(xml, copyBookName,
		                    splitCopybook, dbIdx,
						    font, binaryFormat, systemId);
		        } else if (log != null) {
		        	TextLog.getLog(log).logMsg(AbsSSLogger.ERROR, "Error parsing Cobol File ???");
	            }
        	}
        } catch (Exception e) {
        	log = TextLog.getLog(log);
            log.logMsg(AbsSSLogger.ERROR, e.getMessage());
            log.logException(AbsSSLogger.ERROR, e);
            e.printStackTrace();
            throw new RecordException(
            					PROBLEM_LOADING_COPYBOOK,
                    			new Object[] {copyBookName, e},
                    			e);
        }

        return ret;
    }

    /**
     * wether cb2xml is available (needed for converting a Cobol Copybook
     * to a XML Dom representation
     *
     * @return wether cb2xml is available on the class path
     */
    public static final boolean isAvailable() {

        if (toCheck) {
            try {
                /*
                 * try to load CobolPreprocessor to see if the cb2xml jar is present
                 * I use the CobolPreprocessor because it only uses IO classes.
                 * This aviods loading unnessary classes before need be
                 */
                available = ((new CobolCopybookLoader()).getClass().getClassLoader().getResource("net/sf/cb2xml/Cb2Xml.class") != null);
            } catch (Exception e) {
                available = false;
            }
            toCheck = false;
        }
        return available;
    }

	/**
	 * @param dropCopybookFromFieldNames
	 * @see net.sf.JRecord.External.Cb2xmlLoader#setDropCopybookFromFieldNames(boolean)
	 */
	public final void setDropCopybookFromFieldNames(
			boolean dropCopybookFromFieldNames) {
		xmlLoader.setDropCopybookFromFieldNames(dropCopybookFromFieldNames);
	}

//	/**
//	 * @param useJRecordNaming
//	 * @see net.sf.JRecord.External.Cb2xmlLoader#setUseJRecordNaming(boolean)
//	 */
//	public final void setUseJRecordNaming(boolean useJRecordNaming) {
//		xmlLoader.setUseJRecordNaming(useJRecordNaming);
//	}
}
