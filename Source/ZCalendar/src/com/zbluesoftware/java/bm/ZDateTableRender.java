/*
 * @Author Bruce Martin
 * Created on 9/02/2007
 *
 * Purpose:
 */
package com.zbluesoftware.java.bm;

/**
 *
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class ZDateTableRender extends GenericComboTableRender {

	ZDateField fld;
    /**
     * Create a Checkbox table render based on Strings
     * @param useDateClass wether to return dates or Text to the Table
     * @param dateFormat format of the date
     */
    public ZDateTableRender(final boolean useDateClass, final String dateFormat) {
    	super(useDateClass, null);
    	fld = new ZDateField(dateFormat);
    	setComboField(fld);
    }

	/* (non-Javadoc)
	 * @see com.zbluesoftware.java.bm.GenericComboTableRender#getCombo()
	 */
	@Override
	protected AbstractGenericCombo getCombo() {
		return fld;
	}
    
    
}
