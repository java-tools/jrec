/*
 * @Author Bruce Martin
 * Created on 10/09/2005
 *
 * Purpose:
 *   RecordDecider's are used decide what RecordDetail
 * should be used to display a line (or record)
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
 * RecordDecider's are used decide which specific RecordDetail
 * should be used to format a line (or data record). It allow
 * you to write Java Code to decide which particular Record Should Be used.
 *
 * @author Bruce Martin
 *
 */
public interface RecordDecider {

    /**
     * Get the prefered Layout
     *
     * @param line to decide what the prefered layout is
     *
     * @return the prefered layout
     */
    public abstract int getPreferedIndex(@SuppressWarnings("rawtypes") AbstractLine line);
}
