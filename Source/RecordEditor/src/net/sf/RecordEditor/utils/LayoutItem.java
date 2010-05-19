/*
 * @Author Bruce Martin
 * Created on 20/03/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils;

/**
 * A Record Layout Details description. Used to build a Combobox
 * in the RecordEditor so the user can select the appropriate Layout.
 *
 * @author Bruce Martin
 * @version 0.51
 */
public final class LayoutItem {
	private String recordName;
	private String description;
	private int    system;


	/**
	 * Record Layout details
	 *
	 * @param pRecordName record Layout Name
	 * @param pDescription record Layout Description
	 * @param pSystem System Key or id
	 */
	public LayoutItem(final String pRecordName,
	        		  final String pDescription,
	        		  final int    pSystem) {

	    recordName  = pRecordName;
	    description = pDescription;
	    system      = pSystem;

	}

	/**
	 * get the desciption
	 *
	 * @return the description
	 */
    public String getDescription() {
        return description;
    }

    /**
     * Get record name
     * @return record name
     */
    public String getRecordName() {
        return recordName;
    }


    /**
     * Get System name
     * @return System Name
     */
    public int getSystem() {
        return system;
    }
}
