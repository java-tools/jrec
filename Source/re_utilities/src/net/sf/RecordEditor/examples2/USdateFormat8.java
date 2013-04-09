/*
 * @Author Bruce Martin
 * Created on 26/02/2007
 *
 * Purpose:
 * US date format (MM/DD/YY)
 */
package net.sf.RecordEditor.examples2;

import net.sf.RecordEditor.re.jrecord.format.DateFormat;

/**
 * US date cell format (MM/DD/YY)
 *
 * @author Bruce Martin
 *
 */
public class USdateFormat8 extends DateFormat {

    /** 
     * US date cell format (MM/DD/YY)
     */
    public USdateFormat8() {
        super(true, "MM/dd/yy");
    }
}
