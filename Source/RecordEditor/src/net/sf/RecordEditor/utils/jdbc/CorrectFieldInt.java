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
public class CorrectFieldInt implements CorrectCallBack {


	private static final String INVALID_INTEGER = LangConversion.convert("Invalid Integer");
	private int cVal, ret;

	/**
	 * Correct integer value
	 * @param frame frame being displayed
	 * @param currVal current field value
	 * @param nVal new value to try and apply
	 * @param name field name
	 *
	 * @return value to apply
	 */
	public int correctInt(JFrame frame, int currVal, String nVal, String name) {
		String msg = getErrorMsg(nVal);
		if (! "".equals(msg)) {
			CorrectFieldDialog cField = new CorrectFieldDialog(frame, this, msg);
			cVal = currVal;
			ret = cVal;

			cField.setFields("" + currVal, nVal, name);
			cField.setVisible(true);
			//System.out.println("correct Int " + nVal + " " + ret);
		}

		//System.out.println("correct Int >> " + ret);
		return ret;
	}




	/**
	 * This method is called when the next button is hit
	 *
	 * @param val error message
	 *
	 * @return the error message
	 */
	public String getErrorMsg(String val) {
		String retVal = INVALID_INTEGER;
		//System.out.println("correct Int next");

		try {
			String ss = val.trim();
			if (!ss.equals("")) {
				ret = Integer.parseInt(ss);
				//System.out.println("correct Int next " + ss + " ^ " + ret);
				retVal = "";
			}
		} catch (Exception ex) {
		}

		return retVal;
	}



	/**
	 * This method is called when the stop button is hit
	 *
	 */
	public void processStop() {
		ret = cVal;
		System.out.println("correct Int Stop");
	}


}
