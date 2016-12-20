/*
 * @Author Bruce Martin
 * Created on 18/03/2007
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
      
package net.sf.JRecord.zTest.Common;

import net.sf.JRecord.ByteIO.FixedLengthByteReader;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class ReadFile {

    private static final int    DTAR107_RECORD_LENGTH = 54;
    private static final String DTAR107_FILE
    	= TstConstants.SAMPLE_DIRECTORY + "DTAR107.bin";
    /**
     *
     */
    public ReadFile(String filename, int len) {
        super();
        int i;
        byte[] line;
        FixedLengthByteReader r = new FixedLengthByteReader(len);

        try {
            r.open(filename);

            while ((line = r.read()) != null) {
                System.out.print("        {");
                for (i = 0; i < line.length; i++) {
                    System.out.print(line[i] + ", ");
                    if (i % 19 == 18) {
                        System.out.println();
                        System.out.print("         ");
                    }
                }
                System.out.println("},");
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ReadFile(DTAR107_FILE, DTAR107_RECORD_LENGTH);
    }
}
