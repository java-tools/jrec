/**
 * 
 */
package net.sf.RecordEditor.copy;


import net.sf.RecordEditor.utils.openFile.LayoutSelectionFile;


/**
 * @author Bruce Martin
 *
 */
public class BatchCopyFileLayout {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args == null || args.length == 0) {
			System.out.println("You must supply a Copy definition !!! ");
		} else {
			new BatchCopy(new LayoutSelectionFile(), new LayoutSelectionFile(), args);
		}
	}

}
