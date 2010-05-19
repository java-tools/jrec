package net.sf.RecordEditor.utils.swing;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public interface AbstractHexDisplay {

	/**
	 * Set the display value
	 * @param bytes  bytes value to display as hex
	 */
	public abstract void setHex(byte[] bytes);

	/**
	 * Set the display value
	 * @param text text to display
	 * @param hex equivalent hex value
	 */
	public abstract void setHex(String text, String hex);

	/**
	 * Get The value in bytes
	 * @param oldValue old value of the field
	 * @return value (in bytes)
	 */
	public abstract byte[] getBytes(byte[] oldValue);

	/**
	 * Get The display  componenet
	 * @return The actual component
	 */
	public abstract JComponent getComponent();
	
	/**
	 * Clone the component
	 * @return clone of the component
	 */
	public abstract AbstractHexDisplay clone();
	
	/**
	 * Whether 2 bytes are used to display each character
	 * @return Whether 2 bytes are used to display each character
	 */
	public abstract boolean isTwoBytesPerCharacter();

	/**
	 * Set the font
	 */
	public void setFont(Font f);
}