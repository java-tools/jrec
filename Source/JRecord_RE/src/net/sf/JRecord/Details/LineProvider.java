/*
 * @Author Bruce Martin
 * Created on 29/08/2005
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
      
package net.sf.JRecord.Details;

/**
 * A <b>LineProvider</b> creates lines for the calling program.
 * By creating your own <b>LineProvider</b>, you can use your
 * own <b>Line's</b> rather than the System <b>Line</b> / XmlLine class.
 *
 * @author Bruce Martin
 *
 */
public interface LineProvider<Layout extends AbstractLayoutDetails, L extends AbstractLine> {

    /**
     * Create a null line
     *
     * @param recordDescription record description
     *
     * @return new line
     */
    public abstract L getLine(Layout recordDescription);


    /**
     * Line Providers provide lines to the calling program
     *
     * @param recordDescription record layout details
     * @param linesText text to create the line from
     *
     * @return line
     */
    public abstract L getLine(Layout recordDescription, String linesText);

    /**
     * Build a Line
     *
     * @param recordDescription record layout details
     * @param lineBytes bytes to create the line from
     *
     * @return line
     */
    public abstract L getLine(Layout recordDescription, byte[] lineBytes);
}