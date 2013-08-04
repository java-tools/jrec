package net.sf.RecordEditor.re.display;

/**
 * Description of a class that will display a view of a file on
 * the screen that also allows fields to be hidden and latter redisplayed
 *
 * @author Bruce Martin
 *
 */
public interface AbstractFileDisplayWithFieldHide extends AbstractFileDisplay {

	/**
	 * get The visibility of the fields
	 * @return visibility of the fields
	 */
	public boolean[] getFieldVisibility(int recordIndex);

	/**
	 * Set Field visibility
	 * @param recordIndex current Record index
	 * @param fieldVisibility visibility of the fields
	 */
	public void setFieldVisibility(int recordIndex, boolean[] fieldVisibility);
}
