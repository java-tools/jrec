package net.sf.RecordEditor.utils.swing;

/**
 * This interface describes a class that can retrive the value as Text
 * and set its value from text
 *
 * @author Bruce Martin
 *
 */
public interface UpdatableTextValue {

	/**
	 * Get the Text fields value
	 * @return text value of field
	 */
	public abstract String getText();

	/**
	 * Set the fields value
	 * @param text new text value of date
	 */
	public abstract void setText(String text);

}