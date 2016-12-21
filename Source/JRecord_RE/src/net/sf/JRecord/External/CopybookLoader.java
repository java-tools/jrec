/*
 * @Author Bruce Martin
 * Created on 22/01/2007
 *
 * Purpose:
 *   Interface of a Dabase loader class
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

import net.sf.JRecord.Log.AbsSSLogger;
import net.sf.JRecord.Option.ICobolSplitOptions;



/**
 * description of a class to load a copybook (i.e. Record Layout or Record Description)
 * into a ExteranlRecord (general purpose interface class).
 *
 * @author Bruce Martin
 *
 */
public interface CopybookLoader extends ICobolSplitOptions {

//    public static final int FMT_INTEL      = 0;
//    public static final int FMT_MAINFRAME  = 1;
//    public static final int FMT_FUJITSU    = 2;
//    public static final int FMT_BIG_ENDIAN = 3;


    /**
     * Read an Copybook from a file into the internal exchange format (ExternalRecord)
     * This can be converted to a LayoutDetail via
     *
     * @param copyBookFile Copy Book file Name
     * @param splitCopybookOption weather to split a copy book on a redefine
     * @param dbIdx Database Index
     * @param font font name to use
     * @param binFormat binary format to use
     * @param systemId System Identifier
     * @param log log where any messages should be written
     *
     * @return Copybook that has been read in  (ExternalRecord)
     *
     * @throws Exception any error that occurs
     */
    public abstract ExternalRecord loadCopyBook(final String copyBookFile,
            final int splitCopybookOption, final int dbIdx, final String font,
            final int binFormat,
            final int systemId,
            final AbsSSLogger log) throws Exception;
    
    /**
     * Read an Copybook from a file into the internal exchange format (ExternalRecord)
     * This can be converted to a LayoutDetail via
     *
     * @param copyBookFile Copy Book file Name
     * @param splitCopybookOption weather to split a copy book on a redefine
     * @param dbIdx Database Index
     * @param font font name to use
     * @param copybookFormat Copbook line format
     * @param binFormat binary format to use
     * @param systemId System Identifier
     * @param log log where any messages should be written
     *
     * @return Copybook that has been read in  (ExternalRecord)
     *
     * @throws Exception any error that occurs
     */
    public abstract ExternalRecord loadCopyBook(final String copyBookFile,
            final int splitCopybookOption, final int dbIdx, final String font,
            final int copybookFormat,
            final int binFormat,
            final int systemId,
            final AbsSSLogger log) throws Exception;

}