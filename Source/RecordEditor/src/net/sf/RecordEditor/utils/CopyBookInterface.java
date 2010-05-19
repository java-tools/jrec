/*
 * @Author Bruce Martin
 * Created on 26/08/2005
 *
 * Purpose:
 *    A Copybook Interface reads the copybook details from the external
 *  storage medium for use by the RecordEditor
 */
package net.sf.RecordEditor.utils;

import java.util.ArrayList;

import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDecider;



/**
 * A <b>Copybook</b> Interface reads the copybook details 
 * (ie record layout / record description) from the external
 *  storage medium for use by RecordEditor
 *
 * @author Bruce Martin
 * @version 0.55
 *
 */
public interface CopyBookInterface {


    /**
     * Register a record Decider for later use
     *
     * @param layoutName record layout name
     * @param decider record decider
     */
    public abstract void registerDecider(String layoutName,
            							 RecordDecider decider);

	/**
	 * This method gets all the systems that have been defined
	 *
	 * @return ArrayList of Systems
	 * @throws Exception Error recieved
	 */
	public abstract ArrayList<SystemItem> getSystems() throws Exception;

	/**
	 * Loads the various Layouts
	 *
	 * @param layouts record layouts
	 */
	public abstract void loadLayouts(final ArrayList<LayoutItem> layouts);


	/**
	 * Get a group of records from the name
	 *
	 * @param lName Name of the Record Group being Request
	 *
	 * @return Group of records
	 */
	public LayoutDetail getLayout(String lName);


	/**
	 * get the error message
	 * @return last error message
	 */
	public String getMessage();
}