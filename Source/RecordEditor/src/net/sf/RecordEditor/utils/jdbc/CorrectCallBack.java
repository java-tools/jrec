/*
 * Created on 31/08/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

/**
 *
 * @author Bruce Martin
 *
 */
public interface CorrectCallBack {
	/**
	 * This method is called when the next button is hit. It validates
	 * the supplied value.
	 *
	 * @param val value to validate
	 * @return whether the value val is valid
	 */
	public abstract String getErrorMsg(String val);

	/**
	 * This method is called when the stop button is hit
	 *
	 */
	public abstract void processStop();
}