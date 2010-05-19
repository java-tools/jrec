/*
 * Created on 12/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;


//import javax.swing.*;

/**
 * This class implements a ComboBox Agent to allow user
 * to partially enter an item in a Combo Box
 *
 * @author Bruce Martin
 */
public class BmComboAgent extends AbsComboAgent  {


	private ComboBoxModel comboModel;


	/**
	 * This class implements a ComboBox Agent to allow user
	 * to partially enter an item in a Combo Box
	 *
	 * @param comboBox ComboBox that the user will partially enter
	 *                 values for
	 * @param sorted wether the items are sorted or not.
	 */
	public BmComboAgent(final JComboBox comboBox,
					    final boolean sorted) {

		super(comboBox, sorted);
		this.comboModel = comboBox.getModel();
	}


	/**
	 * Retrieves a specified row of the Combo Model
	 *
	 * @param row row to be retrieved
	 *
	 * @return String displayed
	 */
	protected String getRow(int row) {

		Object obj = comboModel.getElementAt(row);

		if (obj == null) {
		    return null;
		}
		return obj.toString();
	}

}


