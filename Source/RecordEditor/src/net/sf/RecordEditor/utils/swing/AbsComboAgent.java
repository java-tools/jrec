/*
 * Created on 12/09/2004
 *
 */
package net.sf.RecordEditor.utils.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;



/**
 * This class is used to allow the user to edit / select a ComboItem
 * (in a Combobox) by entering the letters at the start of the ComboItem
 *
 * @author Bruce Martin
 */
public abstract class AbsComboAgent extends KeyAdapter implements FocusListener {


	protected JComboBox combo;

	protected JTextField textFld = new JTextField();
	protected int size;

	//private ComboBoxModel comboModel;
	private boolean sorted;


	/**
	 * Abstract class to create a Combo agent for automatic selection
	 * of the value from the combo list based on what the user enters
	 */
	protected AbsComboAgent() {
		super();
	}


	/**
	 * Abstract class to create a Combo agent for automatic selection
	 * of the value from the combo list based on what the user enters
	 *
	 * @param comboBox combo box that this agent acts on
	 * @param isSorted wether the combo list is sorted
	 */
	public AbsComboAgent(final JComboBox comboBox,
					  	 final boolean isSorted) {

		super();
		textFld = (JTextField) comboBox.getEditor().getEditorComponent();

		init(comboBox, isSorted);
	}


	/**
	 * Common Initialise code
	 *
	 * @param comboBox Combo Box
	 * @param isSorted wether the combomodel is sorted
	 */
	protected final void init(JComboBox comboBox,
			  boolean isSorted) {

		this.combo = comboBox;
		this.sorted = isSorted;

		textFld.addKeyListener(this);
		textFld.addFocusListener(this);
		size = combo.getItemCount();
	}


	/**
	 *  @see java.awt.Event.KeyAdapter#keyReleased
	 */
	public final void keyReleased(KeyEvent e) {
		int st = 0;
		char ch = e.getKeyChar();

		if (ch == KeyEvent.CHAR_UNDEFINED || Character.isISOControl(ch)) {
			return;
		}

		int pos = textFld.getCaretPosition();

		String str = textFld.getText().toUpperCase();

		if (str.length() == 0) {
			return;
		}

		size = combo.getItemCount();
		if (sorted) {
			st = findStart(st, 3750, str);
			st = findStart(st, 750, str);
			st = findStart(st, 150, str);
			st = findStart(st, 30, str);
			st = findStart(st, 6, str);
		}

		for (int k = st; k < size; k++) {
			String item = getRow(k);

			if ((item == null && (str == null || "".equals(str.trim())))
			||	(item != null && item.toUpperCase().startsWith(str))) {
				if ( item != null
				&& ! item.equalsIgnoreCase(textFld.getText())) {
					textFld.setText(item);
				}

				textFld.moveCaretPosition(pos);

				setRow(k);
				break;
			}
		}
	}


	/**
	 * This method does a rough searchs for a Item starting with
	 * a value entered by the user.
	 *
	 * @param st start point - where to start searching from
	 * @param step the step amount for a rough search
	 * @param str the string being searched for
	 *
	 * @return the next staring position
	 */
	private int findStart(int st, int step, String str) {
		String s;
		int i = st + step;
		int l = str.length();
		while (i < size) {
			s = getRow(i).substring(0, l).toUpperCase();
			if (str.compareTo(s) > 0) {
				st = i;
				/*if (cellRender && l>2) System.out.println(" -  " + i
						+ " " + st + " " + str + " " + s
						+ " " + (str.compareTo(s))
						+ " " + list.getFieldAt(st) );*/
			} else {
				break;
			}

			i += step;
		}

		return st;
	}


	/**
	 * Sets the Selected row to a specific value
	 *
	 * @param row new selected row
	 */
	protected  void setRow(int row) {
		combo.setSelectedIndex(row);
	}


	/**
	 * Retrieves a specified row of the Combo Model
	 *
	 * @param row row to be retrieved
	 *
	 * @return String displayed
	 */
	protected abstract String getRow(int row);


	/**
	 * @see java.awt.Event.FocusListner#FocusEvent
	 */
	public final void focusGained(FocusEvent arg0) {
		if (textFld.getCaretPosition() == textFld.getText().length()) {
			textFld.setCaretPosition(0);
		}
	}

	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public final void focusLost(FocusEvent arg0) {
		//writeField();
	}
}


