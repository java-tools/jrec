package net.sf.RecordEditor.utils.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.lang.LangConversion;

public class BooleanBtn extends JButton implements ActionListener {
    private static String[] BOOL_OPS = {Common.BOOLEAN_AND_STRING, Common.BOOLEAN_OR_STRING};
    private static int[] BOOL_IDS = {Common.BOOLEAN_OPERATOR_AND, Common.BOOLEAN_OPERATOR_OR};

    private final String[] text;
    private final int[] values;
    private int idx = 0;

    public BooleanBtn() {
    	this( LangConversion.convertArray(LangConversion.ST_BUTTON, "BoolOps", BOOL_OPS.clone()), BOOL_IDS);
    }

    public BooleanBtn(String[] text, int[] values) {
    	super(text[0]);
    	this.text = text;
    	this.values = values;

    	super.addActionListener(this);
    }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		idx = 1 - idx;
		super.setText(text[idx]);
	}

	/**
	 * Get The boolean value selected
	 * @return selected boolean value
	 */
	public int getValue() {
		return values[idx];
	}

	/**
	 * Set the boolean value
	 * @param val boolean value
	 */
	public void setValue(int val) {

		idx = 0;
		if (values[1] == val) {
			idx = 1;
		}
		super.setText(text[idx]);
	}
}
