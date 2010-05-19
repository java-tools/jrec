/**
 * 
 */
package net.sf.RecordEditor.utils;

import net.sf.RecordEditor.edit.file.FileView;


/**
 * @author Bruce Martin
 *
 */
public interface Plugin {

	/**
	 * Execute Function. This is called by the RecordEditor when a plugin
	 * is selected by the user
	 * 
	 * @param param a parameter entered at plugin definition time
	 * @param view file view currently being displed
	 * @param selected rows that are currently selected
	 */
	public void execute(String param, FileView view, int[] selected);
}
