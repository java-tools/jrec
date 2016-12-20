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

import net.sf.JRecord.Details.LayoutDetail;
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
public abstract class StandardLineReader extends AbstractLineReader<LayoutDetail> {

	/**
	 *
	 */
	public StandardLineReader() {
		super();
	}

	/**
	 * @param provider
	 */
	public StandardLineReader(LineProvider provider) {
		super(provider);
	}

}
