/*
 * Created on 15/01/2005
 *
 */
package net.sf.RecordEditor.utils.swing;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * My combo box, allows editing of combo field (and auto selecting
 * options by entering the required value).
 *
 * @author Bruce Martin
 */
public class BmComboBox extends JComboBox {


	private BmComboAgent agent = null;
	private boolean sorted = false;


	/**
	 * Create a Combobox with automatic selection of the item based
	 * partially entered value
	 *
	 * @param model Combo box model to be used
	 * @param editable wether the Combo Field is editable by the user
	 */
	public BmComboBox(final ComboBoxModel model, final boolean editable) {
		super(model);

		setEditable(editable);
	}


	/**
	 * Create a Combobox with automatic selection of the item based
	 * partially entered value
	 *
	 * @param model Combo box model to be used
	 */
	public BmComboBox(final ComboBoxModel model) {
		super(model);
	}


	/**
	 * Create a Combobox with automatic selection of the item based
	 * partially entered value. Drop down list comes from an array
	 *
	 * @param list list to be used in combos drop down
	 */
	public BmComboBox(final Object[] list) {
		super(list);
	}

	/**
	 * Create a Combobox with automatic selection of the item based
	 * partially entered value. Drop down list comes from an supplied
	 * vector
	 *
	 * @param vector Vector to be used in combos drop down
	 */
	@SuppressWarnings("unchecked")
	public BmComboBox(final Vector vector) {
		super(vector);
	}

	/**
	 * Create a Combobox with automatic selection of the item based
	 * partially entered value.
	 */
	public BmComboBox() {
		super();
	}


	/**
	 * @see javax.swing.JComboBox#setEditable(boolean)
	 */
	public void setEditable(boolean edit) {


		if (edit && (agent == null)) {
			agent = new BmComboAgent(this, sorted);
		}

		super.setEditable(edit);
	}

}
