/**
 * 
 */
package net.sf.RecordEditor.utils.openFile;

/**
 * @author Bruce Martin
 *
 *Class to create a LayoutSelection class
 */
public interface AbstractLayoutSelectCreator<LayoutSelection extends AbstractLayoutSelection> {

	/**
	 * Create a layout selection on demand
	 * @return a layout selection
	 */
	public LayoutSelection create();
}
