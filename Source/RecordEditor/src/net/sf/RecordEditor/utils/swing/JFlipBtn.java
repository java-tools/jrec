package net.sf.RecordEditor.utils.swing;

import javax.swing.Icon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JFlipBtn extends JButton {

	private String altText, text;
	private boolean normalState = true;
	
	public JFlipBtn(String text, String altText, Icon icon) {
		super(text, icon);
		this.text = text;
		this.altText = altText;
	}

	public JFlipBtn(String text, String altText) {
		super(text);
		this.text = text;
		this.altText = altText;
	}

	/**
	 * Flip the state and change the Text displayed
	 */
	public final void flipText() {
		normalState = ! normalState;
		if (normalState) {
			super.setText(text);
		} else {
			super.setText(altText);
		}
	}

	/** 
	 * is it in the normal State
	 * 
	 * @return is in nomal state
	 */
	public final boolean isNormalState() {
		return normalState;
	}
	
	public void setTextValues(String text, String altText) {
		this.text = text;
		this.altText = altText;
	}
}
