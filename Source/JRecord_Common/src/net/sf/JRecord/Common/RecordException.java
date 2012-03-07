/*
 * Created on 19/12/2004
 *
 */
package net.sf.JRecord.Common;

/**
 * Error class for Jrecord / RecordEditor
 *
 * @author Bruce Martin
 * @version 0.51
 */
@SuppressWarnings("serial")
public class RecordException extends Exception {


	/**
	 * @param msg Error Message
	 */
	public RecordException(final String msg) {
		super(msg);
	}

	public RecordException(String msg, Throwable exception) {
		super(msg, exception);
		// TODO Auto-generated constructor stub
	}

}
