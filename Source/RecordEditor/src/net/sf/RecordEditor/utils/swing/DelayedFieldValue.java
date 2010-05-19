package net.sf.RecordEditor.utils.swing;

import javax.swing.JFrame;

/**
 * This interface allows a the calculation of a fields value to be delayed
 * to a later point. Typically it will be used after a user input error
 * to display an input JDialog outside the Swing call stack
 * 
 * @author Bruce Martin
 *
 */
public interface DelayedFieldValue {

	/**
	 * Get the fields value
	 * @return fields value
	 */
	public abstract Object getValue(JFrame parentFrame);

}