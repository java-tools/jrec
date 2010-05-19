/*
 * @Author Bruce Martin
 * Created on 26/02/2007
 *
 * Purpose:
 * provide a US style date (mm/dd/yy)
 */
package net.sf.RecordEditor.examples;

import net.sf.JRecord.Types.TypeChar;
import net.sf.RecordEditor.record.types.TypeDateWrapper;

/**
 * provide a US style date (mm/dd/yy)
 * 
 * @author Bruce Martin
 *
 */
public class USdate8 extends TypeDateWrapper {

    /**
     * provide a US style date (mm/dd/yy)
     */
    public USdate8() {
        super(new TypeChar(true), "MM/dd/yy");
    }
}
