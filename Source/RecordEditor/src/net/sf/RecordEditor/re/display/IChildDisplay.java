package net.sf.RecordEditor.re.display;

/**
 * Child screen (linked to a parent screen that selects the record
 * to be displayed).
 *
 * @author Bruce Martin
 *
 */
public interface IChildDisplay {

	/**
	 * @return the sourceDisplay
	 */
	public abstract AbstractFileDisplay getSourceDisplay();

}