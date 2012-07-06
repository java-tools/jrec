/*
 * Created on 31/08/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.sf.RecordEditor.utils.jdbc;

import javax.swing.JFrame;

import net.sf.RecordEditor.utils.lang.LangConversion;

/**
 * @author Bruce Martin
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CorrectFieldBoolean implements CorrectCallBack {



	private static final String INVALID_BOOLEAN = LangConversion.convert("Invalid Boolean");
	private boolean cVal, ret;


	/**
	 * Correct a boolean value by checking with the user
	 *
	 * @param frame parent frame
	 * @param currVal current (default) value of the field
	 * @param newVal  the new value to assign if correct
	 * @param name field name
	 *
	 * @return wether field is valid
	 */
	public boolean correctBoolean(final JFrame frame,
	        					  final boolean currVal,
	        					  final String newVal,
	        					  final String name) {
		String msg = getErrorMsg(newVal);
		if (! "".equals(msg)) {
			CorrectFieldDialog cField = new CorrectFieldDialog(frame, this, msg);
			cVal = currVal;
			ret = cVal;

			cField.setFields("" + currVal, newVal, name);
			cField.setVisible(true);
		}

		return ret;
	}




	/**
	 * This method is called when the next button is hit
	 *
	 * @param val new boolean value to test
	 *
	 * @return Error message if any
	 *
	 * @see jdbcUtil.CorrectCallBack#getErrorMsg(String);
	 */
	public String getErrorMsg(String val) {
		String retVal = "";
		//System.out.println("correct Int next");


		String ss = val.trim().toUpperCase();
		if (ss.equals("TRUE") || ss.equals("YES")) {
			ret = true;
		} else if (ss.equals("TRUE") || ss.equals("YES")) {
			ret = false;
		} else {
			retVal = INVALID_BOOLEAN;
		}

		return retVal;
	}



	/**
	 * This method is called when the stop button is hit
	 *
	 */
	public void processStop() {
		ret = cVal;
		System.out.println("correct boolean Stop");
	}


}
